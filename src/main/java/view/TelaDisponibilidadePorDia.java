package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

import controller.InterdicaoController;
import controller.LaboratorioController;
import controller.ReservaController;
import model.HorariosEnum;
import model.Laboratorio;
import model.Reserva;
import model.StatusLab;
import model.Usuario;
import util.ImageUtil;

public class TelaDisponibilidadePorDia extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel painelConteudo;
	private CardLayout cardLayout;
	private JTable tabelaLaboratorios;
	private JTextField textFieldDia;

	private Usuario usuarioLogado;
	private boolean isAdmin;
	private Date dataAtual;
	private StatusLab[][] disponibilidade;
	private List<Laboratorio> laboratorios;
	private DefaultTableModel tabelaHorarios;

	private final LaboratorioController labCtrl = new LaboratorioController();
	private final ReservaController reservaCtrl = new ReservaController();
	private final InterdicaoController interdicaoCtrl = new InterdicaoController();

	private boolean modoSelecao = false;
	private String tipoSelecao = null;
	private final List<int[]> selecionados = new ArrayList<>();
	private JButton btnNavVoltar;
	private JButton btnNavProximo;
	private JButton btnReservar;
	private JButton btnCancelarReserva;
	private JButton btnConfirmar;
	private JButton btnCancelarSelecao;
	private JLabel lblStatusSelecao;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				new TelaDisponibilidadePorDia(null).setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public TelaDisponibilidadePorDia(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
		this.isAdmin = usuarioLogado != null && usuarioLogado.isAdministrador();
		this.dataAtual = new Date(System.currentTimeMillis());
		this.laboratorios = labCtrl.listarLaboratorios();

		setIconImage(Toolkit.getDefaultToolkit().getImage(
				getClass().getResource("/images/logo_qlab_pequena_branca.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1280, 720);
		setTitle("QLAB - Sistema de Gestão de Laboratórios");
		setResizable(false);

		JPanel contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(27, 94, 32));
		setContentPane(contentPane);

		JPanel painelLateral = new JPanel();
		painelLateral.setLayout(null);
		painelLateral.setBounds(0, 0, 190, 682);
		painelLateral.setBackground(new Color(27, 94, 32));
		contentPane.add(painelLateral);

		JLabel labelLogo = new JLabel("", SwingConstants.CENTER);
		ImageIcon iconLogo = new ImageIcon(getClass().getResource("/images/logo_qlab_media.png"));
		labelLogo.setIcon(new ImageIcon(iconLogo.getImage().getScaledInstance(-1, 55, Image.SCALE_SMOOTH)));
		labelLogo.setBounds(15, 12, 160, 62);
		painelLateral.add(labelLogo);

		JPanel painelNav = new JPanel();
		painelNav.setLayout(null);
		painelNav.setBackground(new Color(27, 94, 32));

		JLabel lblLabs = new JLabel("LABORATÓRIOS");
		lblLabs.setFont(new Font("Calibri", Font.BOLD, 10));
		lblLabs.setForeground(new Color(165, 214, 167));
		lblLabs.setBounds(12, 8, 160, 14);
		painelNav.add(lblLabs);

		JButton btnLab01 = criarBotaoMenu("Lab 01", "Redes e Manutenção");
		btnLab01.setBounds(4, 26, 170, 46);
		btnLab01.addActionListener(e -> cardLayout.show(painelConteudo, "LAB01"));
		painelNav.add(btnLab01);

		JButton btnLab02 = criarBotaoMenu("Lab 02", "Informática");
		btnLab02.setBounds(4, 78, 170, 46);
		btnLab02.addActionListener(e -> cardLayout.show(painelConteudo, "LAB02"));
		painelNav.add(btnLab02);

		JButton btnLab03 = criarBotaoMenu("Lab 03", "Desenvolvimento");
		btnLab03.setBounds(4, 130, 170, 46);
		btnLab03.addActionListener(e -> cardLayout.show(painelConteudo, "LAB03"));
		painelNav.add(btnLab03);

		JButton btnLab05 = criarBotaoMenu("Lab 05", "Diretório ADS");
		btnLab05.setBounds(4, 182, 170, 46);
		btnLab05.addActionListener(e -> cardLayout.show(painelConteudo, "LAB05"));
		painelNav.add(btnLab05);

		JButton btnLab06 = criarBotaoMenu("Lab 06", "Prática de IA");
		btnLab06.setBounds(4, 234, 170, 46);
		btnLab06.addActionListener(e -> cardLayout.show(painelConteudo, "LAB06"));
		painelNav.add(btnLab06);

		JButton btnLab07 = criarBotaoMenu("Lab 07", "Informática 2");
		btnLab07.setBounds(4, 286, 170, 46);
		btnLab07.addActionListener(e -> cardLayout.show(painelConteudo, "LAB07"));
		painelNav.add(btnLab07);

		JLabel lblGeral = new JLabel("GERAL");
		lblGeral.setFont(new Font("Calibri", Font.BOLD, 10));
		lblGeral.setForeground(new Color(165, 214, 167));
		lblGeral.setBounds(12, 345, 160, 14);
		painelNav.add(lblGeral);

		JButton btnInicio = criarBotaoMenu("Início");
		btnInicio.setBounds(4, 363, 170, 32);
		btnInicio.addActionListener(e -> cardLayout.show(painelConteudo, "LABS"));
		painelNav.add(btnInicio);

		JButton btnDisponibilidade = criarBotaoMenu("Disponibilidade");
		btnDisponibilidade.setBounds(4, 401, 170, 32);
		btnDisponibilidade.addActionListener(e -> cardLayout.show(painelConteudo, "TABELA"));
		painelNav.add(btnDisponibilidade);

		JLabel lblAdmin = new JLabel("ADMINISTRAÇÃO");
		lblAdmin.setFont(new Font("Calibri", Font.BOLD, 10));
		lblAdmin.setForeground(new Color(165, 214, 167));
		lblAdmin.setBounds(12, 449, 160, 14);
		lblAdmin.setVisible(isAdmin);
		painelNav.add(lblAdmin);

		JButton btnDashboard = criarBotaoMenu("Dashboard");
		btnDashboard.setBounds(4, 467, 170, 32);
		btnDashboard.addActionListener(e -> cardLayout.show(painelConteudo, "DASHBOARD"));
		btnDashboard.setVisible(isAdmin);
		painelNav.add(btnDashboard);

		JButton btnCadastrar = criarBotaoMenu("Cadastrar");
		btnCadastrar.setBounds(4, 505, 170, 32);
		btnCadastrar.addActionListener(e -> new TelaCadastro().setVisible(true));
		btnCadastrar.setVisible(isAdmin);
		painelNav.add(btnCadastrar);

		JButton btnInterditar = criarBotaoMenu("Interditar");
		btnInterditar.setBounds(4, 543, 170, 32);
		btnInterditar.addActionListener(e -> cardLayout.show(painelConteudo, "INTERDITAR"));
		btnInterditar.setVisible(isAdmin);
		painelNav.add(btnInterditar);

		JButton btnEditarUsuario = criarBotaoMenu("Editar Usuário");
		btnEditarUsuario.setBounds(4, 581, 170, 32);
		btnEditarUsuario.addActionListener(e -> {
			String matricula = JOptionPane.showInputDialog(this,
					"Informe a matrícula do usuário:", "Editar Usuário", JOptionPane.PLAIN_MESSAGE);
			if (matricula == null || matricula.trim().isEmpty()) return;
			controller.CadastroController ctrl = new controller.CadastroController();
			model.Usuario u = ctrl.buscarUsuario(matricula.trim());
			if (u == null) {
				JOptionPane.showMessageDialog(this, "Usuário não encontrado.", "Aviso", JOptionPane.WARNING_MESSAGE);
			} else {
				new TelaCadastro(u).setVisible(true);
			}
		});
		btnEditarUsuario.setVisible(isAdmin);
		painelNav.add(btnEditarUsuario);

		painelNav.setPreferredSize(new Dimension(175, 626));

		JScrollPane scrollNav = new JScrollPane(painelNav,
				JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollNav.setBounds(0, 78, 190, 560);
		scrollNav.setBorder(null);
		scrollNav.getViewport().setBackground(new Color(27, 94, 32));
		painelLateral.add(scrollNav);

		JButton btnSair = criarBotaoMenu("Sair");
		btnSair.setBackground(new Color(183, 28, 28));
		btnSair.setBounds(5, 640, 180, 32);
		btnSair.addActionListener(e -> {
			dispose();
			new TelaLogin().setVisible(true);
		});
		painelLateral.add(btnSair);

		cardLayout = new CardLayout();
		painelConteudo = new JPanel(cardLayout);
		painelConteudo.setBounds(193, 5, 1062, 672);
		painelConteudo.setBackground(Color.WHITE);
		contentPane.add(painelConteudo);

		String[] cardKeys = {"LAB01", "LAB02", "LAB03", "LAB05", "LAB06", "LAB07"};

		painelConteudo.add(criarPainelGridLabs(laboratorios, cardKeys), "LABS");
		painelConteudo.add(criarPainelTabela(), "TABELA");

		for (int i = 0; i < laboratorios.size() && i < cardKeys.length; i++) {
			painelConteudo.add(criarPainelLab(laboratorios.get(i)), cardKeys[i]);
		}

		painelConteudo.add(criarPainelDashboard(), "DASHBOARD");
		painelConteudo.add(criarPainelInterdicao(), "INTERDITAR");

		cardLayout.show(painelConteudo, "LABS");
		carregarDisponibilidade(dataAtual);
	}

	private JPanel criarPainelGridLabs(List<Laboratorio> labs, String[] cardKeys) {
		JPanel painel = new JPanel(null);
		painel.setBackground(Color.WHITE);

		JLabel titulo = new JLabel("Selecionar Laboratório", SwingConstants.CENTER);
		titulo.setFont(new Font("Calibri", Font.PLAIN, 32));
		titulo.setBounds(0, 25, 1062, 42);
		painel.add(titulo);

		int[] colX = {100, 420, 740};
		int[] rowY = {150, 390};

		for (int i = 0; i < labs.size() && i < cardKeys.length; i++) {
			Laboratorio lab = labs.get(i);
			int col = i % 3;
			int row = i / 3;
			final String cardKey = cardKeys[i];

			boolean interditado = interdicaoCtrl.isInterditado(lab.getId(), dataAtual);
			String statusTxt = interditado ? "Interditado" : "Disponível";
			Color statusCor = interditado ? new Color(180, 0, 0) : new Color(34, 139, 34);
			Color bordaCor = interditado ? new Color(180, 0, 0) : new Color(34, 139, 34);

			JPanel painelLab = new JPanel(null);
			painelLab.setBounds(colX[col], rowY[row], 220, 170);
			painelLab.setBackground(Color.WHITE);
			painelLab.setBorder(BorderFactory.createLineBorder(bordaCor, 2));
			painelLab.setCursor(new Cursor(Cursor.HAND_CURSOR));
			painelLab.addMouseListener(new java.awt.event.MouseAdapter() {
				@Override public void mouseEntered(java.awt.event.MouseEvent e) {
					painelLab.setBackground(new Color(240, 245, 240));
					painelLab.setBorder(BorderFactory.createLineBorder(bordaCor, 3));
				}
				@Override public void mouseExited(java.awt.event.MouseEvent e) {
					painelLab.setBackground(Color.WHITE);
					painelLab.setBorder(BorderFactory.createLineBorder(bordaCor, 2));
				}
				@Override public void mouseClicked(java.awt.event.MouseEvent e) {
					cardLayout.show(painelConteudo, cardKey);
				}
			});

			JLabel lblNome = new JLabel(lab.getNome(), SwingConstants.CENTER);
			lblNome.setFont(new Font("Calibri", Font.BOLD, 18));
			lblNome.setForeground(statusCor);
			lblNome.setBounds(5, 15, 210, 25);
			painelLab.add(lblNome);

			JLabel lblDescricao = new JLabel(
				"<html><center>" + lab.getDescricao() + "<br>"
				+ lab.getAndar() + "<br><br>"
				+ "<b>Status:</b> " + statusTxt + "<br>"
				+ "<b>Computadores:</b> " + lab.getCapacidade() + "</center></html>",
				SwingConstants.CENTER);
			lblDescricao.setFont(new Font("Calibri", Font.PLAIN, 12));
			lblDescricao.setForeground(Color.GRAY);
			lblDescricao.setBounds(5, 48, 210, 108);
			painelLab.add(lblDescricao);

			painel.add(painelLab);
		}

		return painel;
	}

	private JPanel criarPainelTabela() {
		JPanel painelTabela = new JPanel(null);
		painelTabela.setBackground(Color.WHITE);

		int numLabs = laboratorios.isEmpty() ? 6 : laboratorios.size();
		String[] colunas = new String[numLabs + 1];
		colunas[0] = "Horário";
		for (int i = 0; i < laboratorios.size(); i++) {
			colunas[i + 1] = laboratorios.get(i).getNome();
		}

		disponibilidade = new StatusLab[HorariosEnum.values().length][numLabs];

		tabelaHorarios = new DefaultTableModel(colunas, 0) {
			private static final long serialVersionUID = 1L;
			@Override public boolean isCellEditable(int row, int col) { return false; }
		};

		for (HorariosEnum slot : HorariosEnum.values()) {
			Object[] row = new Object[numLabs + 1];
			row[0] = slot.name() + "  " + slot.getHi() + "–" + slot.getHf();
			for (int j = 1; j <= numLabs; j++) row[j] = "";
			tabelaHorarios.addRow(row);
		}

		tabelaLaboratorios = new JTable(tabelaHorarios);
		tabelaLaboratorios.setRowHeight(30);
		tabelaLaboratorios.setFont(new Font("Calibri", Font.PLAIN, 14));
		tabelaLaboratorios.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 14));
		tabelaLaboratorios.getTableHeader().setReorderingAllowed(false);
		tabelaLaboratorios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaLaboratorios.setDefaultRenderer(Object.class, new ColorRenderer());

		tabelaLaboratorios.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int linha = tabelaLaboratorios.rowAtPoint(e.getPoint());
				int coluna = tabelaLaboratorios.columnAtPoint(e.getPoint());
				if (coluna == 0 || linha < 0) return;
				int labIdx = coluna - 1;
				if (labIdx >= laboratorios.size()) return;
				if (modoSelecao) {
					handleSelecao(linha, labIdx, disponibilidade[linha][labIdx]);
				} else {
					mostrarPopupCelula(laboratorios.get(labIdx), HorariosEnum.values()[linha], disponibilidade[linha][labIdx]);
				}
			}
		});

		painelTabela.addComponentListener(new ComponentAdapter() {
			@Override public void componentShown(ComponentEvent e) {
				carregarDisponibilidade(dataAtual);
			}
		});

		JScrollPane scrollPaneTabela = new JScrollPane(tabelaLaboratorios);
		scrollPaneTabela.setBounds(20, 95, 1011, 543);
		painelTabela.add(scrollPaneTabela);

		textFieldDia = new JTextField();
		textFieldDia.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldDia.setBounds(400, 25, 280, 36);
		textFieldDia.setFont(new Font("Calibri", Font.BOLD, 20));
		textFieldDia.setBorder(null);
		textFieldDia.setEditable(false);
		painelTabela.add(textFieldDia);

		btnNavVoltar = new JButton("");
		btnNavVoltar.setBounds(357, 25, 36, 36);
		ImageIcon iconeVoltar = new ImageIcon(getClass().getResource("/images/setaEsquerda.jpg"));
		btnNavVoltar.setIcon(ImageUtil.redimensionarImagem(iconeVoltar, 28, 28));
		btnNavVoltar.setFocusable(false);
		btnNavVoltar.addActionListener(e -> navegarDia(-1));
		painelTabela.add(btnNavVoltar);

		btnNavProximo = new JButton("");
		btnNavProximo.setBounds(687, 25, 36, 36);
		ImageIcon iconePassar = new ImageIcon(getClass().getResource("/images/setaDireita.jpg"));
		btnNavProximo.setIcon(ImageUtil.redimensionarImagem(iconePassar, 28, 28));
		btnNavProximo.setFocusable(false);
		btnNavProximo.addActionListener(e -> navegarDia(1));
		painelTabela.add(btnNavProximo);

		boolean logado = usuarioLogado != null;

		btnReservar = new JButton("Reservar");
		btnReservar.setFont(new Font("Calibri", Font.BOLD, 13));
		btnReservar.setBackground(new Color(27, 94, 32));
		btnReservar.setForeground(Color.WHITE);
		btnReservar.setFocusable(false);
		btnReservar.setBounds(730, 25, 155, 36);
		btnReservar.setVisible(logado);
		btnReservar.addActionListener(e -> entrarModoSelecao("RESERVAR"));
		painelTabela.add(btnReservar);

		btnCancelarReserva = new JButton("Cancelar Reserva");
		btnCancelarReserva.setFont(new Font("Calibri", Font.BOLD, 13));
		btnCancelarReserva.setBackground(new Color(183, 28, 28));
		btnCancelarReserva.setForeground(Color.WHITE);
		btnCancelarReserva.setFocusable(false);
		btnCancelarReserva.setBounds(892, 25, 145, 36);
		btnCancelarReserva.setVisible(logado);
		btnCancelarReserva.addActionListener(e -> entrarModoSelecao("CANCELAR"));
		painelTabela.add(btnCancelarReserva);

		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setFont(new Font("Calibri", Font.BOLD, 13));
		btnConfirmar.setBackground(new Color(27, 94, 32));
		btnConfirmar.setForeground(Color.WHITE);
		btnConfirmar.setFocusable(false);
		btnConfirmar.setBounds(730, 25, 145, 36);
		btnConfirmar.setEnabled(false);
		btnConfirmar.setVisible(false);
		btnConfirmar.addActionListener(e -> confirmarSelecao());
		painelTabela.add(btnConfirmar);

		btnCancelarSelecao = new JButton("Cancelar Seleção");
		btnCancelarSelecao.setFont(new Font("Calibri", Font.BOLD, 13));
		btnCancelarSelecao.setBackground(new Color(96, 96, 96));
		btnCancelarSelecao.setForeground(Color.WHITE);
		btnCancelarSelecao.setFocusable(false);
		btnCancelarSelecao.setBounds(882, 25, 155, 36);
		btnCancelarSelecao.setVisible(false);
		btnCancelarSelecao.addActionListener(e -> sairModoSelecao());
		painelTabela.add(btnCancelarSelecao);

		lblStatusSelecao = new JLabel("", SwingConstants.LEFT);
		lblStatusSelecao.setFont(new Font("Calibri", Font.ITALIC, 13));
		lblStatusSelecao.setForeground(new Color(13, 71, 161));
		lblStatusSelecao.setBounds(10, 68, 710, 22);
		lblStatusSelecao.setVisible(false);
		painelTabela.add(lblStatusSelecao);

		return painelTabela;
	}

	private JPanel criarPainelDashboard() {
		JPanel painel = new JPanel(null);
		painel.setBackground(Color.WHITE);

		JLabel titulo = new JLabel("Dashboard", SwingConstants.CENTER);
		titulo.setFont(new Font("Calibri", Font.PLAIN, 30));
		titulo.setBounds(0, 18, 1062, 40);
		painel.add(titulo);

		int[] cardsX = {60, 290, 520, 750};
		String[] cardsLabel = {"Laboratórios", "Interditados", "Reservas hoje", "Labs c/ reserva"};
		Color[] cardsCor = {
			new Color(27, 94, 32),
			new Color(183, 28, 28),
			new Color(13, 71, 161),
			new Color(230, 81, 0)
		};

		JLabel[] valoresCards = new JLabel[4];
		for (int i = 0; i < 4; i++) {
			JPanel card = new JPanel(null);
			card.setBounds(cardsX[i], 68, 200, 100);
			card.setBackground(Color.WHITE);
			card.setBorder(BorderFactory.createLineBorder(cardsCor[i], 2));

			JLabel lblValor = new JLabel("–", SwingConstants.CENTER);
			lblValor.setFont(new Font("Calibri", Font.BOLD, 40));
			lblValor.setForeground(cardsCor[i]);
			lblValor.setBounds(0, 10, 200, 50);
			card.add(lblValor);
			valoresCards[i] = lblValor;

			JLabel lblTitulo = new JLabel(cardsLabel[i], SwingConstants.CENTER);
			lblTitulo.setFont(new Font("Calibri", Font.PLAIN, 13));
			lblTitulo.setForeground(Color.GRAY);
			lblTitulo.setBounds(0, 62, 200, 25);
			card.add(lblTitulo);

			painel.add(card);
		}

		JLabel lblTabTitulo = new JLabel("Reservas de hoje", SwingConstants.LEFT);
		lblTabTitulo.setFont(new Font("Calibri", Font.BOLD, 16));
		lblTabTitulo.setForeground(new Color(27, 94, 32));
		lblTabTitulo.setBounds(28, 182, 300, 25);
		painel.add(lblTabTitulo);

		DefaultTableModel dashModel = new DefaultTableModel(
				new String[]{"Lab", "Horário", "Período", "Matrícula", "Disciplina"}, 0) {
			private static final long serialVersionUID = 1L;
			@Override public boolean isCellEditable(int r, int c) { return false; }
		};

		JTable dashTable = new JTable(dashModel);
		dashTable.setRowHeight(26);
		dashTable.setFont(new Font("Calibri", Font.PLAIN, 13));
		dashTable.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 13));
		dashTable.getTableHeader().setReorderingAllowed(false);
		dashTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			@Override public Component getTableCellRendererComponent(
					JTable t, Object v, boolean sel, boolean foc, int r, int c) {
				super.getTableCellRendererComponent(t, v, sel, foc, r, c);
				setHorizontalAlignment(SwingConstants.CENTER);
				setBackground(r % 2 == 0 ? Color.WHITE : new Color(245, 250, 245));
				setForeground(Color.DARK_GRAY);
				return this;
			}
		});

		JScrollPane scroll = new JScrollPane(dashTable);
		scroll.setBounds(28, 212, 1006, 420);
		painel.add(scroll);

		painel.addComponentListener(new ComponentAdapter() {
			@Override public void componentShown(ComponentEvent e) {
				carregarDashboard(dashModel, valoresCards);
			}
		});

		return painel;
	}

	private void carregarDashboard(DefaultTableModel model, JLabel[] valoresCards) {
		List<Laboratorio> labs = labCtrl.listarLaboratorios();
		Date hoje = new Date(System.currentTimeMillis());

		int totalLabs = labs.size();
		int interditados = 0;
		int totalReservas = 0;
		int labsComReserva = 0;

		model.setRowCount(0);

		for (Laboratorio lab : labs) {
			boolean interditado = interdicaoCtrl.isInterditado(lab.getId(), hoje);
			if (interditado) {
				interditados++;
				continue;
			}
			List<Reserva> reservas = reservaCtrl.reservasPorLabEData(lab.getId(), hoje);
			totalReservas += reservas.size();
			if (!reservas.isEmpty()) labsComReserva++;

			for (Reserva r : reservas) {
				HorariosEnum slot = r.getHorario().getHorario();
				model.addRow(new Object[]{
					lab.getNome(),
					slot.name(),
					slot.getHi() + " – " + slot.getHf(),
					r.getMatricula(),
					r.getDisciplina()
				});
			}
		}

		valoresCards[0].setText(String.valueOf(totalLabs));
		valoresCards[1].setText(String.valueOf(interditados));
		valoresCards[2].setText(String.valueOf(totalReservas));
		valoresCards[3].setText(String.valueOf(labsComReserva));
	}

	private void carregarDisponibilidade(Date data) {
		HorariosEnum[] slots = HorariosEnum.values();
		for (int labIdx = 0; labIdx < laboratorios.size(); labIdx++) {
			Laboratorio lab = laboratorios.get(labIdx);
			boolean interditado = interdicaoCtrl.isInterditado(lab.getId(), data);

			Set<HorariosEnum> ocupados = new HashSet<>();
			if (!interditado) {
				List<Reserva> reservas = reservaCtrl.reservasPorLabEData(lab.getId(), data);
				for (Reserva r : reservas) {
					ocupados.add(r.getHorario().getHorario());
				}
			}

			for (int slotIdx = 0; slotIdx < slots.length; slotIdx++) {
				if (interditado) {
					disponibilidade[slotIdx][labIdx] = StatusLab.INTERDITADO;
				} else if (ocupados.contains(slots[slotIdx])) {
					disponibilidade[slotIdx][labIdx] = StatusLab.OCUPADO;
				} else {
					disponibilidade[slotIdx][labIdx] = StatusLab.LIVRE;
				}

				String val = switch (disponibilidade[slotIdx][labIdx]) {
					case OCUPADO -> "OCUPADO";
					case INTERDITADO -> "INTERDITADO";
					default -> "";
				};
				tabelaHorarios.setValueAt(val, slotIdx, labIdx + 1);
			}
		}

		textFieldDia.setText(formatarData(data));
		if (tabelaLaboratorios != null) tabelaLaboratorios.repaint();
	}

	private void navegarDia(int delta) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dataAtual);
		cal.add(Calendar.DATE, delta);
		dataAtual = new Date(cal.getTimeInMillis());
		carregarDisponibilidade(dataAtual);
	}

	private void mostrarPopupCelula(Laboratorio lab, HorariosEnum slot, StatusLab status) {
		JDialog dialog = new JDialog(this, "Detalhes do Horário", true);
		dialog.setSize(360, 280);
		dialog.setLocationRelativeTo(this);
		dialog.setResizable(false);
		dialog.setLayout(null);

		Color corStatus = switch (status) {
			case OCUPADO -> new Color(255, 82, 82);
			case INTERDITADO -> new Color(180, 180, 180);
			default -> new Color(126, 217, 87);
		};
		String textoStatus = switch (status) {
			case OCUPADO -> "OCUPADO";
			case INTERDITADO -> "INTERDITADO";
			default -> "LIVRE";
		};

		JPanel faixa = new JPanel(null);
		faixa.setBounds(0, 0, 360, 8);
		faixa.setBackground(corStatus);
		dialog.add(faixa);

		JLabel lblLab = new JLabel(lab.getNome() + " – " + lab.getDescricao());
		lblLab.setFont(new Font("Calibri", Font.BOLD, 16));
		lblLab.setBounds(20, 20, 320, 22);
		dialog.add(lblLab);

		JLabel lblSlot = new JLabel(slot.name() + "   " + slot.getHi() + " – " + slot.getHf());
		lblSlot.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblSlot.setForeground(Color.DARK_GRAY);
		lblSlot.setBounds(20, 48, 320, 20);
		dialog.add(lblSlot);

		JLabel lblStatus = new JLabel("Status: " + textoStatus);
		lblStatus.setFont(new Font("Calibri", Font.BOLD, 14));
		lblStatus.setForeground(corStatus.darker());
		lblStatus.setBounds(20, 74, 320, 20);
		dialog.add(lblStatus);

		if (status == StatusLab.OCUPADO) {
			List<Reserva> reservas = reservaCtrl.reservasPorLabEData(lab.getId(), dataAtual);
			for (Reserva r : reservas) {
				if (r.getHorario().getHorario() == slot) {
					JLabel lblMatricula = new JLabel("Matrícula: " + r.getMatricula());
					lblMatricula.setFont(new Font("Calibri", Font.PLAIN, 13));
					lblMatricula.setBounds(20, 104, 320, 18);
					dialog.add(lblMatricula);

					JLabel lblDisciplina = new JLabel("Disciplina: " + r.getDisciplina());
					lblDisciplina.setFont(new Font("Calibri", Font.PLAIN, 13));
					lblDisciplina.setBounds(20, 126, 320, 18);
					dialog.add(lblDisciplina);
					break;
				}
			}
		}

		JSeparator sep = new JSeparator();
		sep.setBounds(0, 200, 360, 2);
		dialog.add(sep);

		JButton btnEditar = new JButton("Editar");
		btnEditar.setFont(new Font("Calibri", Font.BOLD, 13));
		btnEditar.setBackground(new Color(27, 94, 32));
		btnEditar.setForeground(Color.WHITE);
		btnEditar.setFocusable(false);
		btnEditar.setBounds(80, 215, 90, 32);
		btnEditar.addActionListener(e -> dialog.dispose());
		dialog.add(btnEditar);

		JButton btnFechar = new JButton("Fechar");
		btnFechar.setFont(new Font("Calibri", Font.PLAIN, 13));
		btnFechar.setFocusable(false);
		btnFechar.setBounds(185, 215, 90, 32);
		btnFechar.addActionListener(e -> dialog.dispose());
		dialog.add(btnFechar);

		dialog.setVisible(true);
	}

	private void entrarModoSelecao(String tipo) {
		modoSelecao = true;
		tipoSelecao = tipo;
		selecionados.clear();
		btnReservar.setVisible(false);
		btnCancelarReserva.setVisible(false);
		btnConfirmar.setVisible(true);
		btnConfirmar.setEnabled(false);
		btnCancelarSelecao.setVisible(true);
		btnNavVoltar.setEnabled(false);
		btnNavProximo.setEnabled(false);
		String instrucao = tipo.equals("RESERVAR")
				? "Modo reserva: clique em horários LIVRES consecutivos no mesmo laboratório"
				: "Modo cancelamento: clique em horários OCUPADOS consecutivos no mesmo laboratório";
		lblStatusSelecao.setText(instrucao);
		lblStatusSelecao.setVisible(true);
		tabelaLaboratorios.repaint();
	}

	private void sairModoSelecao() {
		modoSelecao = false;
		tipoSelecao = null;
		selecionados.clear();
		boolean logado = usuarioLogado != null;
		btnReservar.setVisible(logado);
		btnCancelarReserva.setVisible(logado);
		btnConfirmar.setVisible(false);
		btnCancelarSelecao.setVisible(false);
		btnNavVoltar.setEnabled(true);
		btnNavProximo.setEnabled(true);
		lblStatusSelecao.setVisible(false);
		tabelaLaboratorios.repaint();
	}

	private void handleSelecao(int slotIdx, int labIdx, StatusLab status) {
		if (tipoSelecao.equals("RESERVAR") && status != StatusLab.LIVRE) {
			JOptionPane.showMessageDialog(this, "Selecione apenas horários livres.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}
		if (tipoSelecao.equals("CANCELAR") && status != StatusLab.OCUPADO) {
			JOptionPane.showMessageDialog(this, "Selecione apenas horários ocupados.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		for (int i = 0; i < selecionados.size(); i++) {
			if (selecionados.get(i)[0] == slotIdx && selecionados.get(i)[1] == labIdx) {
				selecionados.clear();
				atualizarStatusSelecao();
				tabelaLaboratorios.repaint();
				return;
			}
		}

		if (!selecionados.isEmpty() && selecionados.get(0)[1] != labIdx) {
			JOptionPane.showMessageDialog(this, "Todos os horários devem ser do mesmo laboratório.", "Aviso", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (!selecionados.isEmpty()) {
			int minSlot = selecionados.stream().mapToInt(a -> a[0]).min().getAsInt();
			int maxSlot = selecionados.stream().mapToInt(a -> a[0]).max().getAsInt();
			if (slotIdx != minSlot - 1 && slotIdx != maxSlot + 1) {
				JOptionPane.showMessageDialog(this, "Os horários devem ser consecutivos.\nClique em um slot adjacente à seleção atual.", "Aviso", JOptionPane.WARNING_MESSAGE);
				return;
			}
		}

		selecionados.add(new int[]{slotIdx, labIdx});
		atualizarStatusSelecao();
		tabelaLaboratorios.repaint();
	}

	private void atualizarStatusSelecao() {
		if (selecionados.isEmpty()) {
			String instrucao = tipoSelecao.equals("RESERVAR")
					? "Modo reserva: clique em horários LIVRES consecutivos no mesmo laboratório"
					: "Modo cancelamento: clique em horários OCUPADOS consecutivos no mesmo laboratório";
			lblStatusSelecao.setText(instrucao);
			btnConfirmar.setEnabled(false);
		} else {
			String labNome = laboratorios.get(selecionados.get(0)[1]).getNome();
			lblStatusSelecao.setText(selecionados.size() + " horário(s) selecionado(s) em " + labNome
					+ " — clique em um já selecionado para limpar");
			btnConfirmar.setEnabled(true);
		}
	}

	private void confirmarSelecao() {
		if (selecionados.isEmpty()) return;
		selecionados.sort((a, b) -> Integer.compare(a[0], b[0]));

		HorariosEnum[] slots = HorariosEnum.values();
		int labIdx = selecionados.get(0)[1];
		Laboratorio lab = laboratorios.get(labIdx);

		if (tipoSelecao.equals("RESERVAR")) {
			String disciplina = JOptionPane.showInputDialog(this,
					"Reservar " + selecionados.size() + " horário(s) em " + lab.getNome()
					+ "\n\nInforme a disciplina:",
					"Confirmar Reserva", JOptionPane.PLAIN_MESSAGE);
			if (disciplina == null || disciplina.trim().isEmpty()) return;

			List<String> erros = new ArrayList<>();
			for (int[] sel : selecionados) {
				String erro = reservaCtrl.realizarReserva(dataAtual, slots[sel[0]], lab.getId(),
						usuarioLogado.getMatricula(), disciplina.trim());
				if (erro != null) erros.add(slots[sel[0]].name() + ": " + erro);
			}
			if (erros.isEmpty()) {
				JOptionPane.showMessageDialog(this, selecionados.size() + " horário(s) reservado(s) com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "Erros:\n" + String.join("\n", erros), "Aviso", JOptionPane.WARNING_MESSAGE);
			}

		} else {
			int confirm = JOptionPane.showConfirmDialog(this,
					"Cancelar " + selecionados.size() + " reserva(s) em " + lab.getNome() + "?",
					"Confirmar Cancelamento", JOptionPane.YES_NO_OPTION);
			if (confirm != JOptionPane.YES_OPTION) return;

			List<Reserva> reservas = reservaCtrl.reservasPorLabEData(lab.getId(), dataAtual);
			List<String> erros = new ArrayList<>();
			for (int[] sel : selecionados) {
				HorariosEnum slot = slots[sel[0]];
				Reserva reserva = null;
				for (Reserva r : reservas) {
					if (r.getHorario().getHorario() == slot) { reserva = r; break; }
				}
				if (reserva == null) continue;
				if (!isAdmin && !reserva.getMatricula().equals(usuarioLogado.getMatricula())) {
					erros.add(slot.name() + ": reserva pertence a outro usuário");
					continue;
				}
				String erro = reservaCtrl.cancelarReserva(reserva.getIdReserva());
				if (erro != null) erros.add(slot.name() + ": " + erro);
			}
			if (erros.isEmpty()) {
				JOptionPane.showMessageDialog(this, selecionados.size() + " reserva(s) cancelada(s) com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "Erros:\n" + String.join("\n", erros), "Aviso", JOptionPane.WARNING_MESSAGE);
			}
		}

		sairModoSelecao();
		carregarDisponibilidade(dataAtual);
	}

	private String formatarData(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE – dd/MM/yyyy", new Locale("pt", "BR"));
		String s = sdf.format(data);
		return Character.toUpperCase(s.charAt(0)) + s.substring(1);
	}

	private JPanel criarPainelInterdicao() {
		JPanel painel = new JPanel(null);
		painel.setBackground(Color.WHITE);

		JLabel titulo = new JLabel("Interditar Laboratório", SwingConstants.CENTER);
		titulo.setFont(new Font("Calibri", Font.PLAIN, 28));
		titulo.setBounds(0, 25, 1062, 42);
		painel.add(titulo);

		JLabel msg = new JLabel("Módulo em desenvolvimento", SwingConstants.CENTER);
		msg.setFont(new Font("Calibri", Font.ITALIC, 16));
		msg.setForeground(Color.GRAY);
		msg.setBounds(0, 260, 1062, 25);
		painel.add(msg);

		return painel;
	}

	private JButton criarBotaoMenu(String texto) {
		JButton btn = new JButton("  " + texto);
		btn.setFont(new Font("Calibri", Font.PLAIN, 13));
		btn.setForeground(Color.WHITE);
		btn.setBackground(new Color(27, 94, 32));
		btn.setBorderPainted(true);
		btn.setBorder(BorderFactory.createLineBorder(new Color(27, 94, 32), 2));
		btn.setFocusable(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setHorizontalAlignment(SwingConstants.LEFT);
		btn.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override public void mouseEntered(java.awt.event.MouseEvent e) {
				btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
				btn.setBackground(new Color(40, 110, 45));
			}
			@Override public void mouseExited(java.awt.event.MouseEvent e) {
				btn.setBorder(BorderFactory.createLineBorder(new Color(27, 94, 32), 2));
				btn.setBackground(new Color(27, 94, 32));
			}
		});
		return btn;
	}

	private JButton criarBotaoMenu(String texto, String descricao) {
		String html = "<html><div style='padding-left:5px;'><b>" + texto + "</b><br>"
				+ "<font size='2' color='#a5d6a7'>" + descricao + "</font></div></html>";
		JButton btn = new JButton(html);
		btn.setFont(new Font("Calibri", Font.PLAIN, 13));
		btn.setForeground(Color.WHITE);
		btn.setBackground(new Color(27, 94, 32));
		btn.setBorderPainted(true);
		btn.setBorder(BorderFactory.createLineBorder(new Color(27, 94, 32), 2));
		btn.setFocusable(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setHorizontalAlignment(SwingConstants.LEFT);
		btn.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override public void mouseEntered(java.awt.event.MouseEvent e) {
				btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
				btn.setBackground(new Color(40, 110, 45));
			}
			@Override public void mouseExited(java.awt.event.MouseEvent e) {
				btn.setBorder(BorderFactory.createLineBorder(new Color(27, 94, 32), 2));
				btn.setBackground(new Color(27, 94, 32));
			}
		});
		return btn;
	}

	private JPanel criarPainelLab(Laboratorio lab) {
		JPanel painel = new JPanel(null);
		painel.setBackground(Color.WHITE);

		JButton btnVoltar = new JButton("← Voltar");
		btnVoltar.setFont(new Font("Calibri", Font.PLAIN, 13));
		btnVoltar.setForeground(new Color(34, 139, 34));
		btnVoltar.setBackground(Color.WHITE);
		btnVoltar.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34)));
		btnVoltar.setBounds(25, 15, 110, 30);
		btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVoltar.setFocusable(false);
		btnVoltar.addActionListener(e -> cardLayout.show(painelConteudo, "LABS"));
		painel.add(btnVoltar);

		JLabel titulo = new JLabel(lab.getNome() + " – " + lab.getDescricao(), SwingConstants.CENTER);
		titulo.setFont(new Font("Calibri", Font.PLAIN, 28));
		titulo.setBounds(0, 12, 1062, 40);
		painel.add(titulo);

		JLabel info = new JLabel(lab.getAndar() + "  •  " + lab.getCapacidade() + " computadores",
				SwingConstants.CENTER);
		info.setFont(new Font("Calibri", Font.ITALIC, 14));
		info.setForeground(Color.GRAY);
		info.setBounds(0, 54, 1062, 22);
		painel.add(info);

		JLabel lblDataLab = new JLabel("", SwingConstants.CENTER);
		lblDataLab.setFont(new Font("Calibri", Font.BOLD, 18));
		lblDataLab.setBounds(390, 84, 280, 30);
		painel.add(lblDataLab);

		JButton btnPrev = new JButton("◀");
		btnPrev.setBounds(348, 84, 36, 30);
		btnPrev.setFocusable(false);
		painel.add(btnPrev);

		JButton btnNext = new JButton("▶");
		btnNext.setBounds(677, 84, 36, 30);
		btnNext.setFocusable(false);
		painel.add(btnNext);

		boolean logado = usuarioLogado != null;
		boolean[] modoSel = {false};
		String[] tipoSel = {null};
		List<Integer> selLab = new ArrayList<>();

		JButton btnResLab = new JButton("Reservar");
		btnResLab.setFont(new Font("Calibri", Font.BOLD, 13));
		btnResLab.setBackground(new Color(27, 94, 32));
		btnResLab.setForeground(Color.WHITE);
		btnResLab.setFocusable(false);
		btnResLab.setBounds(730, 84, 120, 30);
		btnResLab.setVisible(logado);
		painel.add(btnResLab);

		JButton btnCancelResLab = new JButton("Cancelar Reserva");
		btnCancelResLab.setFont(new Font("Calibri", Font.BOLD, 12));
		btnCancelResLab.setBackground(new Color(183, 28, 28));
		btnCancelResLab.setForeground(Color.WHITE);
		btnCancelResLab.setFocusable(false);
		btnCancelResLab.setBounds(858, 84, 150, 30);
		btnCancelResLab.setVisible(logado);
		painel.add(btnCancelResLab);

		JButton btnConfLab = new JButton("Confirmar");
		btnConfLab.setFont(new Font("Calibri", Font.BOLD, 13));
		btnConfLab.setBackground(new Color(27, 94, 32));
		btnConfLab.setForeground(Color.WHITE);
		btnConfLab.setFocusable(false);
		btnConfLab.setBounds(730, 84, 130, 30);
		btnConfLab.setEnabled(false);
		btnConfLab.setVisible(false);
		painel.add(btnConfLab);

		JButton btnCancelSelLab = new JButton("Cancelar Seleção");
		btnCancelSelLab.setFont(new Font("Calibri", Font.BOLD, 12));
		btnCancelSelLab.setBackground(new Color(96, 96, 96));
		btnCancelSelLab.setForeground(Color.WHITE);
		btnCancelSelLab.setFocusable(false);
		btnCancelSelLab.setBounds(868, 84, 140, 30);
		btnCancelSelLab.setVisible(false);
		painel.add(btnCancelSelLab);

		JLabel lblStatusLab = new JLabel("", SwingConstants.LEFT);
		lblStatusLab.setFont(new Font("Calibri", Font.ITALIC, 13));
		lblStatusLab.setForeground(new Color(13, 71, 161));
		lblStatusLab.setBounds(25, 120, 900, 20);
		lblStatusLab.setVisible(false);
		painel.add(lblStatusLab);

		DefaultTableModel labModel = new DefaultTableModel(
				new String[]{"Horário", "Status", isAdmin ? "Reservado por / Disciplina" : "Disciplina"}, 0) {
			private static final long serialVersionUID = 1L;
			@Override public boolean isCellEditable(int r, int c) { return false; }
		};
		for (HorariosEnum slot : HorariosEnum.values()) {
			labModel.addRow(new Object[]{slot.name() + "  " + slot.getHi() + "–" + slot.getHf(), "—", "—"});
		}

		JTable tabelaLab = new JTable(labModel);
		tabelaLab.setRowHeight(28);
		tabelaLab.setFont(new Font("Calibri", Font.PLAIN, 14));
		tabelaLab.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 14));
		tabelaLab.getTableHeader().setReorderingAllowed(false);
		tabelaLab.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaLab.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			private static final long serialVersionUID = 1L;
			@Override
			public Component getTableCellRendererComponent(JTable t, Object v, boolean sel,
					boolean foc, int linha, int coluna) {
				super.getTableCellRendererComponent(t, v, sel, foc, linha, coluna);
				setHorizontalAlignment(coluna == 0 ? SwingConstants.LEFT : SwingConstants.CENTER);
				setFont(new Font("Calibri", coluna == 0 ? Font.BOLD : Font.PLAIN, 13));
				if (coluna == 0) {
					setBackground(new Color(245, 245, 245));
					setForeground(Color.DARK_GRAY);
					return this;
				}
				if (selLab.contains(linha)) {
					setBackground(new Color(25, 118, 210));
					setForeground(Color.WHITE);
					return this;
				}
				String status = (String) t.getModel().getValueAt(linha, 1);
				switch (status) {
					case "OCUPADO"     -> { setBackground(new Color(255, 82, 82)); setForeground(Color.WHITE); }
					case "INTERDITADO" -> { setBackground(new Color(180, 180, 180)); setForeground(Color.DARK_GRAY); }
					case "LIVRE"       -> { setBackground(new Color(126, 217, 87)); setForeground(Color.DARK_GRAY); }
					default            -> { setBackground(Color.WHITE); setForeground(Color.GRAY); }
				}
				return this;
			}
		});
		tabelaLab.getColumnModel().getColumn(0).setPreferredWidth(210);
		tabelaLab.getColumnModel().getColumn(1).setPreferredWidth(130);
		tabelaLab.getColumnModel().getColumn(2).setPreferredWidth(300);

		Runnable atualizarStatusLab = () -> {
			if (selLab.isEmpty()) {
				lblStatusLab.setText(tipoSel[0].equals("RESERVAR")
						? "Modo reserva: clique em horários LIVRES consecutivos"
						: "Modo cancelamento: clique em horários OCUPADOS consecutivos");
				btnConfLab.setEnabled(false);
			} else {
				lblStatusLab.setText(selLab.size() + " horário(s) selecionado(s) — clique em um já selecionado para limpar");
				btnConfLab.setEnabled(true);
			}
		};

		Runnable sairSel = () -> {
			modoSel[0] = false;
			tipoSel[0] = null;
			selLab.clear();
			btnResLab.setVisible(logado);
			btnCancelResLab.setVisible(logado);
			btnConfLab.setVisible(false);
			btnCancelSelLab.setVisible(false);
			btnPrev.setEnabled(true);
			btnNext.setEnabled(true);
			lblStatusLab.setVisible(false);
			tabelaLab.repaint();
		};

		Runnable entrarSel = () -> {
			modoSel[0] = true;
			selLab.clear();
			btnResLab.setVisible(false);
			btnCancelResLab.setVisible(false);
			btnConfLab.setVisible(true);
			btnConfLab.setEnabled(false);
			btnCancelSelLab.setVisible(true);
			btnPrev.setEnabled(false);
			btnNext.setEnabled(false);
			lblStatusLab.setVisible(true);
			atualizarStatusLab.run();
			tabelaLab.repaint();
		};

		tabelaLab.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int linha = tabelaLab.rowAtPoint(e.getPoint());
				if (linha < 0) return;
				String statusStr = (String) labModel.getValueAt(linha, 1);
				if (modoSel[0]) {
					if (tipoSel[0].equals("RESERVAR") && !"LIVRE".equals(statusStr)) {
						JOptionPane.showMessageDialog(painel, "Selecione apenas horários livres.", "Aviso", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (tipoSel[0].equals("CANCELAR") && !"OCUPADO".equals(statusStr)) {
						JOptionPane.showMessageDialog(painel, "Selecione apenas horários ocupados.", "Aviso", JOptionPane.WARNING_MESSAGE);
						return;
					}
					if (selLab.contains(linha)) {
						selLab.clear();
						atualizarStatusLab.run();
						tabelaLab.repaint();
						return;
					}
					if (!selLab.isEmpty()) {
						int min = selLab.stream().mapToInt(Integer::intValue).min().getAsInt();
						int max = selLab.stream().mapToInt(Integer::intValue).max().getAsInt();
						if (linha != min - 1 && linha != max + 1) {
							JOptionPane.showMessageDialog(painel, "Os horários devem ser consecutivos.", "Aviso", JOptionPane.WARNING_MESSAGE);
							return;
						}
					}
					selLab.add(linha);
					atualizarStatusLab.run();
					tabelaLab.repaint();
				} else {
					StatusLab status = switch (statusStr) {
						case "OCUPADO" -> StatusLab.OCUPADO;
						case "INTERDITADO" -> StatusLab.INTERDITADO;
						default -> StatusLab.LIVRE;
					};
					mostrarPopupCelula(lab, HorariosEnum.values()[linha], status);
				}
			}
		});

		btnResLab.addActionListener(e -> { tipoSel[0] = "RESERVAR"; entrarSel.run(); });
		btnCancelResLab.addActionListener(e -> { tipoSel[0] = "CANCELAR"; entrarSel.run(); });
		btnCancelSelLab.addActionListener(e -> sairSel.run());

		btnConfLab.addActionListener(e -> {
			if (selLab.isEmpty()) return;
			List<Integer> sorted = new ArrayList<>(selLab);
			sorted.sort(Integer::compare);
			HorariosEnum[] slots = HorariosEnum.values();

			if (tipoSel[0].equals("RESERVAR")) {
				String disciplina = JOptionPane.showInputDialog(painel,
						"Reservar " + sorted.size() + " horário(s) em " + lab.getNome()
						+ "\n\nInforme a disciplina:", "Confirmar Reserva", JOptionPane.PLAIN_MESSAGE);
				if (disciplina == null || disciplina.trim().isEmpty()) return;
				List<String> erros = new ArrayList<>();
				for (int idx : sorted) {
					String erro = reservaCtrl.realizarReserva(dataAtual, slots[idx], lab.getId(),
							usuarioLogado.getMatricula(), disciplina.trim());
					if (erro != null) erros.add(slots[idx].name() + ": " + erro);
				}
				if (erros.isEmpty()) {
					JOptionPane.showMessageDialog(painel, sorted.size() + " horário(s) reservado(s) com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(painel, "Erros:\n" + String.join("\n", erros), "Aviso", JOptionPane.WARNING_MESSAGE);
				}
			} else {
				int confirm = JOptionPane.showConfirmDialog(painel,
						"Cancelar " + sorted.size() + " reserva(s) em " + lab.getNome() + "?",
						"Confirmar Cancelamento", JOptionPane.YES_NO_OPTION);
				if (confirm != JOptionPane.YES_OPTION) return;
				List<Reserva> reservas = reservaCtrl.reservasPorLabEData(lab.getId(), dataAtual);
				List<String> erros = new ArrayList<>();
				for (int idx : sorted) {
					HorariosEnum slot = slots[idx];
					Reserva reserva = null;
					for (Reserva r : reservas) {
						if (r.getHorario().getHorario() == slot) { reserva = r; break; }
					}
					if (reserva == null) continue;
					if (!isAdmin && !reserva.getMatricula().equals(usuarioLogado.getMatricula())) {
						erros.add(slot.name() + ": reserva pertence a outro usuário");
						continue;
					}
					String erro = reservaCtrl.cancelarReserva(reserva.getIdReserva());
					if (erro != null) erros.add(slot.name() + ": " + erro);
				}
				if (erros.isEmpty()) {
					JOptionPane.showMessageDialog(painel, sorted.size() + " reserva(s) cancelada(s) com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(painel, "Erros:\n" + String.join("\n", erros), "Aviso", JOptionPane.WARNING_MESSAGE);
				}
			}
			sairSel.run();
			carregarPainelLab(lab, labModel, lblDataLab);
		});

		JScrollPane scrollLab = new JScrollPane(tabelaLab);
		scrollLab.setBounds(170, 148, 720, 490);
		painel.add(scrollLab);

		btnPrev.addActionListener(e -> {
			navegarDia(-1);
			carregarPainelLab(lab, labModel, lblDataLab);
		});
		btnNext.addActionListener(e -> {
			navegarDia(1);
			carregarPainelLab(lab, labModel, lblDataLab);
		});

		painel.addComponentListener(new ComponentAdapter() {
			@Override public void componentShown(ComponentEvent e) {
				sairSel.run();
				carregarPainelLab(lab, labModel, lblDataLab);
			}
		});

		return painel;
	}

	private void carregarPainelLab(Laboratorio lab, DefaultTableModel model, JLabel lblData) {
		lblData.setText(formatarData(dataAtual));
		boolean interditado = interdicaoCtrl.isInterditado(lab.getId(), dataAtual);
		List<Reserva> reservas = interditado
				? List.of()
				: reservaCtrl.reservasPorLabEData(lab.getId(), dataAtual);

		Map<HorariosEnum, Reserva> mapa = new HashMap<>();
		for (Reserva r : reservas) mapa.put(r.getHorario().getHorario(), r);

		HorariosEnum[] slots = HorariosEnum.values();
		for (int i = 0; i < slots.length; i++) {
			if (interditado) {
				model.setValueAt("INTERDITADO", i, 1);
				model.setValueAt("—", i, 2);
			} else if (mapa.containsKey(slots[i])) {
				Reserva r = mapa.get(slots[i]);
				model.setValueAt("OCUPADO", i, 1);
				String detalhe = isAdmin
						? r.getMatricula() + " – " + r.getDisciplina()
						: r.getDisciplina();
				model.setValueAt(detalhe, i, 2);
			} else {
				model.setValueAt("LIVRE", i, 1);
				model.setValueAt("—", i, 2);
			}
		}
	}

	class ColorRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable tabela, Object valor,
				boolean isSelected, boolean hasFocus, int linha, int coluna) {
			super.getTableCellRendererComponent(tabela, valor, isSelected, hasFocus, linha, coluna);
			setHorizontalAlignment(SwingConstants.CENTER);

			if (coluna == 0) {
				setBackground(new Color(245, 245, 245));
				setForeground(Color.DARK_GRAY);
				setFont(new Font("Calibri", Font.BOLD, 13));
				return this;
			}

			setFont(new Font("Calibri", Font.PLAIN, 13));
			setForeground(Color.DARK_GRAY);

			int labIdx = coluna - 1;
			for (int[] sel : selecionados) {
				if (sel[0] == linha && sel[1] == labIdx) {
					setBackground(new Color(25, 118, 210));
					setForeground(Color.WHITE);
					return this;
				}
			}

			String v = valor == null ? "" : valor.toString();
			if (v.equals("OCUPADO")) {
				setBackground(new Color(255, 82, 82));
				setForeground(Color.WHITE);
			} else if (v.equals("INTERDITADO")) {
				setBackground(new Color(180, 180, 180));
			} else {
				setBackground(new Color(126, 217, 87));
			}

			return this;
		}
	}
}
