package view;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import controller.InterdicaoController;
import controller.LaboratorioController;
import controller.ReservaController;
import model.HorariosEnum;
import model.Laboratorio;
import model.Reserva;
import model.Usuario;

public class TelaSelecionarLab extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel painelConteudo;
	private CardLayout cardLayout;

	private final LaboratorioController labCtrl = new LaboratorioController();
	private final InterdicaoController interdicaoCtrl = new InterdicaoController();
	private final ReservaController reservaCtrl = new ReservaController();
	private Usuario usuarioLogado;

	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				new TelaSelecionarLab(null).setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	public TelaSelecionarLab(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;

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


		boolean isAdmin = usuarioLogado != null && usuarioLogado.isAdministrador();

		JLabel lblAdmin = new JLabel("ADMINISTRAÇÃO");
		lblAdmin.setFont(new Font("Calibri", Font.BOLD, 10));
		lblAdmin.setForeground(new Color(165, 214, 167));
		lblAdmin.setBounds(12, 345, 160, 14);
		lblAdmin.setVisible(isAdmin);
		painelNav.add(lblAdmin);

		JButton btnDashboard = criarBotaoMenu("Dashboard");
		btnDashboard.setBounds(4, 363, 170, 32);
		btnDashboard.addActionListener(e -> cardLayout.show(painelConteudo, "DASHBOARD"));
		btnDashboard.setVisible(isAdmin);
		painelNav.add(btnDashboard);

		JButton btnCadastrar = criarBotaoMenu("Cadastrar");
		btnCadastrar.setBounds(4, 401, 170, 32);
		btnCadastrar.addActionListener(e -> new TelaCadastro().setVisible(true));
		btnCadastrar.setVisible(isAdmin);
		painelNav.add(btnCadastrar);

		JButton btnInterditar = criarBotaoMenu("Interditar");
		btnInterditar.setBounds(4, 439, 170, 32);
		btnInterditar.addActionListener(e -> cardLayout.show(painelConteudo, "INTERDITAR"));
		btnInterditar.setVisible(isAdmin);
		painelNav.add(btnInterditar);

		JButton btnEditarUsuario = criarBotaoMenu("Editar Usuário");
		btnEditarUsuario.setBounds(4, 477, 170, 32);
		btnEditarUsuario.addActionListener(e -> {
			String matricula = JOptionPane.showInputDialog(this, "Informe a matrícula do usuário:", "Editar Usuário", JOptionPane.PLAIN_MESSAGE);
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

		painelNav.setPreferredSize(new Dimension(175, 523));

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

		List<Laboratorio> laboratorios = labCtrl.listarLaboratorios();
		Date hoje = new Date(System.currentTimeMillis());
		String[] cardKeys = {"LAB01", "LAB02", "LAB03", "LAB05", "LAB06", "LAB07"};

		painelConteudo.add(criarPainelGridLabs(laboratorios, cardKeys, hoje), "LABS");

		for (int i = 0; i < laboratorios.size() && i < cardKeys.length; i++) {
			painelConteudo.add(criarPainelLab(laboratorios.get(i)), cardKeys[i]);
		}

		painelConteudo.add(criarPainelDashboard(),                            "DASHBOARD");
		painelConteudo.add(criarPainelPlaceholder("Interditar Laboratório"),  "INTERDITAR");

		cardLayout.show(painelConteudo, "LABS");
	}


	private JPanel criarPainelGridLabs(List<Laboratorio> labs, String[] cardKeys, Date hoje) {
		JPanel painel = new JPanel();
		painel.setBackground(Color.WHITE);
		painel.setLayout(null);

		JLabel titulo = new JLabel("Selecionar Laboratório", SwingConstants.CENTER);
		titulo.setFont(new Font("Calibri", Font.PLAIN, 32));
		titulo.setBounds(0, 25, 1062, 42);
		painel.add(titulo);

		int[] colX = {100, 420, 740};
		int[] rowY  = {150, 390};

		for (int i = 0; i < labs.size() && i < cardKeys.length; i++) {
			Laboratorio lab = labs.get(i);
			int col = i % 3;
			int row = i / 3;
			final String cardKey = cardKeys[i];

			boolean interditado = interdicaoCtrl.isInterditado(lab.getId(), hoje);
			String statusTxt = interditado ? "Interditado" : "Disponível";
			Color statusCor  = interditado ? new Color(180, 0, 0) : new Color(34, 139, 34);
			Color bordaCor   = interditado ? new Color(180, 0, 0) : new Color(34, 139, 34);

			JPanel painelLab = new JPanel();
			painelLab.setLayout(null);
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
				SwingConstants.CENTER
			);
			lblDescricao.setFont(new Font("Calibri", Font.PLAIN, 12));
			lblDescricao.setForeground(Color.GRAY);
			lblDescricao.setBounds(5, 48, 210, 108);
			painelLab.add(lblDescricao);

			painel.add(painelLab);
		}

		return painel;
	}

	private JPanel criarPainelLab(Laboratorio lab) {
		JPanel painel = new JPanel();
		painel.setBackground(Color.WHITE);
		painel.setLayout(null);

		JButton btnVoltar = new JButton("← Voltar");
		btnVoltar.setFont(new Font("Calibri", Font.PLAIN, 13));
		btnVoltar.setForeground(new Color(34, 139, 34));
		btnVoltar.setBackground(Color.WHITE);
		btnVoltar.setBorder(BorderFactory.createLineBorder(new Color(34, 139, 34)));
		btnVoltar.setBounds(25, 20, 110, 30);
		btnVoltar.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVoltar.setFocusable(false);
		btnVoltar.addActionListener(e -> cardLayout.show(painelConteudo, "LABS"));
		painel.add(btnVoltar);

		JLabel titulo = new JLabel(lab.getNome() + " – " + lab.getDescricao(), SwingConstants.CENTER);
		titulo.setFont(new Font("Calibri", Font.PLAIN, 32));
		titulo.setBounds(0, 25, 1062, 42);
		painel.add(titulo);

		JLabel andar = new JLabel(lab.getAndar(), SwingConstants.CENTER);
		andar.setFont(new Font("Calibri", Font.ITALIC, 16));
		andar.setForeground(Color.GRAY);
		andar.setBounds(0, 75, 1062, 25);
		painel.add(andar);

		JLabel lblComputadores = new JLabel("Computadores: " + lab.getCapacidade(), SwingConstants.CENTER);
		lblComputadores.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblComputadores.setForeground(Color.GRAY);
		lblComputadores.setBounds(0, 110, 1062, 25);
		painel.add(lblComputadores);

		JButton btnVerDisponibilidade = new JButton("Ver Disponibilidade →");
		btnVerDisponibilidade.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnVerDisponibilidade.setForeground(Color.WHITE);
		btnVerDisponibilidade.setBackground(new Color(34, 139, 34));
		btnVerDisponibilidade.setBounds(431, 200, 200, 36);
		btnVerDisponibilidade.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btnVerDisponibilidade.setFocusable(false);
		btnVerDisponibilidade.addActionListener(e -> {
			dispose();
			new TelaDisponibilidadePorDia(usuarioLogado).setVisible(true);
		});
		painel.add(btnVerDisponibilidade);

		return painel;
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
			@Override public java.awt.Component getTableCellRendererComponent(
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

		int totalLabs     = labs.size();
		int interditados  = 0;
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

	private JPanel criarPainelPlaceholder(String titulo) {
		JPanel painel = new JPanel();
		painel.setBackground(Color.WHITE);
		painel.setLayout(null);

		JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Calibri", Font.PLAIN, 32));
		lblTitulo.setBounds(0, 25, 775, 42);
		painel.add(lblTitulo);

		JLabel lblMsg = new JLabel("Módulo em desenvolvimento", SwingConstants.CENTER);
		lblMsg.setFont(new Font("Calibri", Font.ITALIC, 16));
		lblMsg.setForeground(Color.GRAY);
		lblMsg.setBounds(0, 260, 775, 25);
		painel.add(lblMsg);

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
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
				btn.setBackground(new Color(40, 110, 45));
			}
			
			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				btn.setBorder(BorderFactory.createLineBorder(new Color(27, 94, 32), 2));
				btn.setBackground(new Color(27, 94, 32));
			}
		});
		
		return btn;
	}

	private JButton criarBotaoMenu(String texto, String descricao) {
		String htmlTexto = "<html><div style='padding-left: 5px;'>"
				+ "<b>" + texto + "</b><br>"
				+ "<font size='2' color='#a5d6a7'>" + descricao + "</font>"
				+ "</div></html>";
		JButton btn = new JButton(htmlTexto);
		btn.setFont(new Font("Calibri", Font.PLAIN, 13));
		btn.setForeground(Color.WHITE);
		btn.setBackground(new Color(27, 94, 32));
		btn.setBorderPainted(true);
		btn.setBorder(BorderFactory.createLineBorder(new Color(27, 94, 32), 2));
		btn.setFocusable(false);
		btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
		btn.setHorizontalAlignment(SwingConstants.LEFT);
		
		btn.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseEntered(java.awt.event.MouseEvent e) {
				btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
				btn.setBackground(new Color(40, 110, 45));
			}
			
			@Override
			public void mouseExited(java.awt.event.MouseEvent e) {
				btn.setBorder(BorderFactory.createLineBorder(new Color(27, 94, 32), 2));
				btn.setBackground(new Color(27, 94, 32));
			}
		});
		
		return btn;
	}
}
