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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
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

		JButton btnInicio = criarBotaoMenu("← Início");
		btnInicio.setBounds(4, 4, 170, 32);
		btnInicio.addActionListener(e -> {
			new TelaSelecionarLab(usuarioLogado).setVisible(true);
			dispose();
		});
		painelNav.add(btnInicio);

		JLabel lblLabs = new JLabel("LABORATÓRIOS");
		lblLabs.setFont(new Font("Calibri", Font.BOLD, 10));
		lblLabs.setForeground(new Color(165, 214, 167));
		lblLabs.setBounds(12, 44, 160, 14);
		painelNav.add(lblLabs);

		JButton btnLab01 = criarBotaoMenu("Lab 01", "Redes e Manutenção");
		btnLab01.setBounds(4, 62, 170, 46);
		btnLab01.addActionListener(e -> cardLayout.show(painelConteudo, "LAB01"));
		painelNav.add(btnLab01);

		JButton btnLab02 = criarBotaoMenu("Lab 02", "Informática");
		btnLab02.setBounds(4, 114, 170, 46);
		btnLab02.addActionListener(e -> cardLayout.show(painelConteudo, "LAB02"));
		painelNav.add(btnLab02);

		JButton btnLab03 = criarBotaoMenu("Lab 03", "Desenvolvimento");
		btnLab03.setBounds(4, 166, 170, 46);
		btnLab03.addActionListener(e -> cardLayout.show(painelConteudo, "LAB03"));
		painelNav.add(btnLab03);

		JButton btnLab05 = criarBotaoMenu("Lab 05", "Diretório ADS");
		btnLab05.setBounds(4, 218, 170, 46);
		btnLab05.addActionListener(e -> cardLayout.show(painelConteudo, "LAB05"));
		painelNav.add(btnLab05);

		JButton btnLab06 = criarBotaoMenu("Lab 06", "Prática de IA");
		btnLab06.setBounds(4, 270, 170, 46);
		btnLab06.addActionListener(e -> cardLayout.show(painelConteudo, "LAB06"));
		painelNav.add(btnLab06);

		JButton btnLab07 = criarBotaoMenu("Lab 07", "Informática 2");
		btnLab07.setBounds(4, 322, 170, 46);
		btnLab07.addActionListener(e -> cardLayout.show(painelConteudo, "LAB07"));
		painelNav.add(btnLab07);

		JLabel lblAdmin = new JLabel("ADMINISTRAÇÃO");
		lblAdmin.setFont(new Font("Calibri", Font.BOLD, 10));
		lblAdmin.setForeground(new Color(165, 214, 167));
		lblAdmin.setBounds(12, 381, 160, 14);
		lblAdmin.setVisible(isAdmin);
		painelNav.add(lblAdmin);

		JButton btnCadastrar = criarBotaoMenu("Cadastrar");
		btnCadastrar.setBounds(4, 399, 170, 32);
		btnCadastrar.addActionListener(e -> new TelaCadastro().setVisible(true));
		btnCadastrar.setVisible(isAdmin);
		painelNav.add(btnCadastrar);

		JButton btnInterditar = criarBotaoMenu("Interditar");
		btnInterditar.setBounds(4, 437, 170, 32);
		btnInterditar.addActionListener(e -> cardLayout.show(painelConteudo, "INTERDITAR"));
		btnInterditar.setVisible(isAdmin);
		painelNav.add(btnInterditar);

		JButton btnEditarUsuario = criarBotaoMenu("Editar Usuário");
		btnEditarUsuario.setBounds(4, 475, 170, 32);
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

		painelNav.setPreferredSize(new Dimension(175, 520));

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

		painelConteudo.add(criarPainelTabela(), "TABELA");

		String[] cardKeys = {"LAB01", "LAB02", "LAB03", "LAB05", "LAB06", "LAB07"};
		for (int i = 0; i < laboratorios.size() && i < cardKeys.length; i++) {
			painelConteudo.add(criarPainelLab(laboratorios.get(i)), cardKeys[i]);
		}

		painelConteudo.add(criarPainelInterdicao(), "INTERDITAR");

		carregarDisponibilidade(dataAtual);
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
			@Override
			public boolean isCellEditable(int row, int col) { return false; }
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

				Laboratorio lab = laboratorios.get(labIdx);
				HorariosEnum slot = HorariosEnum.values()[linha];
				StatusLab status = disponibilidade[linha][labIdx];

				tratarCliqueNaCelula(lab, slot, status);
			}
		});

		painelTabela.addComponentListener(new ComponentAdapter() {
			@Override public void componentShown(ComponentEvent e) {
				carregarDisponibilidade(dataAtual);
			}
		});

		JScrollPane scrollPaneTabela = new JScrollPane(tabelaLaboratorios);
		scrollPaneTabela.setBounds(20, 78, 1011, 560);
		painelTabela.add(scrollPaneTabela);

		textFieldDia = new JTextField();
		textFieldDia.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldDia.setBounds(400, 30, 280, 36);
		textFieldDia.setFont(new Font("Calibri", Font.BOLD, 20));
		textFieldDia.setBorder(null);
		textFieldDia.setEditable(false);
		painelTabela.add(textFieldDia);

		JButton btnVoltarDia = new JButton("");
		btnVoltarDia.setBounds(357, 30, 36, 36);
		ImageIcon iconeVoltar = new ImageIcon(getClass().getResource("/images/setaEsquerda.jpg"));
		btnVoltarDia.setIcon(ImageUtil.redimensionarImagem(iconeVoltar, 28, 28));
		btnVoltarDia.setFocusable(false);
		btnVoltarDia.addActionListener(e -> navegarDia(-1));
		painelTabela.add(btnVoltarDia);

		JButton btnPassarDia = new JButton("");
		btnPassarDia.setBounds(687, 30, 36, 36);
		ImageIcon iconePassar = new ImageIcon(getClass().getResource("/images/setaDireita.jpg"));
		btnPassarDia.setIcon(ImageUtil.redimensionarImagem(iconePassar, 28, 28));
		btnPassarDia.setFocusable(false);
		btnPassarDia.addActionListener(e -> navegarDia(1));
		painelTabela.add(btnPassarDia);

		return painelTabela;
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

	private void tratarCliqueNaCelula(Laboratorio lab, HorariosEnum slot, StatusLab status) {
		String slotInfo = lab.getNome() + " – " + slot.name() + "  " + slot.getHi() + "–" + slot.getHf();

		if (status == StatusLab.INTERDITADO) {
			JOptionPane.showMessageDialog(this,
					"O laboratório está interditado nesta data.",
					"Indisponível", JOptionPane.WARNING_MESSAGE);
			return;
		}

		if (status == StatusLab.OCUPADO) {
			List<Reserva> reservas = reservaCtrl.reservasPorLabEData(lab.getId(), dataAtual);
			Reserva reservaAtual = null;
			for (Reserva r : reservas) {
				if (r.getHorario().getHorario() == slot) {
					reservaAtual = r;
					break;
				}
			}

			if (isAdmin && reservaAtual != null) {
				String msg = "Reservado por: " + reservaAtual.getMatricula()
						+ "\nDisciplina: " + reservaAtual.getDisciplina()
						+ "\n\nDeseja cancelar esta reserva?";
				int resp = JOptionPane.showConfirmDialog(this, msg, "Horário Ocupado",
						JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
				if (resp == JOptionPane.YES_OPTION) {
					String erro = reservaCtrl.cancelarReserva(reservaAtual.getIdReserva());
					if (erro == null) {
						carregarDisponibilidade(dataAtual);
					} else {
						JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				JOptionPane.showMessageDialog(this,
						"Este horário já está reservado.", "Horário Ocupado",
						JOptionPane.INFORMATION_MESSAGE);
			}
			return;
		}

		if (usuarioLogado == null) {
			JOptionPane.showMessageDialog(this,
					"Faça login para realizar reservas.",
					"Login necessário", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String disciplina = JOptionPane.showInputDialog(this,
				"Reservar: " + slotInfo + "\n\nInforme a disciplina:",
				"Realizar Reserva", JOptionPane.PLAIN_MESSAGE);

		if (disciplina == null || disciplina.trim().isEmpty()) return;

		String erro = reservaCtrl.realizarReserva(
				dataAtual, slot, lab.getId(),
				usuarioLogado.getMatricula(), disciplina.trim());

		if (erro != null) {
			JOptionPane.showMessageDialog(this, erro, "Erro", JOptionPane.ERROR_MESSAGE);
		} else {
			carregarDisponibilidade(dataAtual);
		}
	}

	private String formatarData(Date data) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE – dd/MM/yyyy", Locale.of("pt", "BR"));
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
		btnVoltar.addActionListener(e -> cardLayout.show(painelConteudo, "TABELA"));
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
		tabelaLab.setDefaultRenderer(Object.class, new LabColorRenderer());
		tabelaLab.getColumnModel().getColumn(0).setPreferredWidth(210);
		tabelaLab.getColumnModel().getColumn(1).setPreferredWidth(130);
		tabelaLab.getColumnModel().getColumn(2).setPreferredWidth(300);

		tabelaLab.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override public void mouseClicked(MouseEvent e) {
				int linha = tabelaLab.rowAtPoint(e.getPoint());
				if (linha < 0) return;
				HorariosEnum slot = HorariosEnum.values()[linha];
				String statusStr = (String) labModel.getValueAt(linha, 1);
				StatusLab status = switch (statusStr) {
					case "OCUPADO" -> StatusLab.OCUPADO;
					case "INTERDITADO" -> StatusLab.INTERDITADO;
					default -> StatusLab.LIVRE;
				};
				tratarCliqueNaCelula(lab, slot, status);
				carregarPainelLab(lab, labModel, lblDataLab);
			}
		});

		JScrollPane scrollLab = new JScrollPane(tabelaLab);
		scrollLab.setBounds(170, 122, 720, 510);
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

	class LabColorRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable tabela, Object valor,
				boolean isSelected, boolean hasFocus, int linha, int coluna) {
			super.getTableCellRendererComponent(tabela, valor, isSelected, hasFocus, linha, coluna);
			setHorizontalAlignment(coluna == 0 ? SwingConstants.LEFT : SwingConstants.CENTER);

			String status = (String) tabela.getModel().getValueAt(linha, 1);
			if (coluna == 0) {
				setBackground(new Color(245, 245, 245));
				setForeground(Color.DARK_GRAY);
				setFont(new Font("Calibri", Font.BOLD, 13));
			} else if ("OCUPADO".equals(status)) {
				setBackground(new Color(255, 82, 82));
				setForeground(Color.WHITE);
				setFont(new Font("Calibri", Font.PLAIN, 13));
			} else if ("INTERDITADO".equals(status)) {
				setBackground(new Color(180, 180, 180));
				setForeground(Color.DARK_GRAY);
				setFont(new Font("Calibri", Font.PLAIN, 13));
			} else if ("LIVRE".equals(status)) {
				setBackground(new Color(126, 217, 87));
				setForeground(Color.DARK_GRAY);
				setFont(new Font("Calibri", Font.PLAIN, 13));
			} else {
				setBackground(Color.WHITE);
				setForeground(Color.GRAY);
				setFont(new Font("Calibri", Font.ITALIC, 12));
			}
			return this;
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
