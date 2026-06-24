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

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import model.Usuario;
import util.ImageUtil;

import javax.swing.JTable;
import javax.swing.JTextField;

public class TelaDisponibilidadePorDia extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel painelConteudo;
	private CardLayout cardLayout;
	private JTable tabelaLaboratorios;
	private JTextField textFieldDia;

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
		//painelNav.add(btnDashboard);

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

		painelNav.setPreferredSize(new Dimension(175, 485));

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
		//painelLateral.add(btnSair);

		cardLayout = new CardLayout();
		painelConteudo = new JPanel(cardLayout);
		painelConteudo.setBounds(193, 5, 1062, 672);
		painelConteudo.setBackground(Color.WHITE);
		contentPane.add(painelConteudo);
		
		JPanel painelTabela = new JPanel(null);
		painelTabela.setBackground(Color.WHITE);

		JScrollPane scrollPaneTabela = new JScrollPane();
		scrollPaneTabela.setBounds(20, 78, 1011, 560);
		painelTabela.add(scrollPaneTabela);

		painelConteudo.add(painelTabela, "TABELA");

		String[] colunas = {"", "Lab 1", "Lab 2", "Lab 3", "Lab 5", "Lab 6", "Lab 7"};
		String[] horarios = {"M1", "M2", "M3", "M4", "M5", "M6", "T1", "T2", "T3", "T4", "T5", "T6", "N1", "N2", "N3", "N4", "N5", "N6"};
		
		
		DefaultTableModel tabelaHorarios = new DefaultTableModel(colunas, 0);

		for (String horario : horarios) {
			tabelaHorarios.addRow(new Object[]{horario, "", "", "", "", "", ""});
		}

		tabelaLaboratorios = new JTable(tabelaHorarios);
		tabelaLaboratorios.setRowHeight(50);
		tabelaLaboratorios.setFont(new Font("Calibri", Font.PLAIN, 18));
		tabelaLaboratorios.getTableHeader().setFont(new Font("Calibri", Font.BOLD, 16));
		
		tabelaLaboratorios.setDefaultRenderer(Object.class, new ColorRenderer());
		tabelaLaboratorios.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				String[] opcoesReserva = {"Reservar", "Cancelar", "Interditar"};
				int linha = tabelaLaboratorios.rowAtPoint(e.getPoint());
				int coluna = tabelaLaboratorios.columnAtPoint(e.getPoint());
				//TO DO Recuperar usuario
				//String usuario = 

				var opcao = JOptionPane.showOptionDialog(null, "O que você deseja fazer?", "Reserva de horário", 2, 1, null, opcoesReserva, null);
				
				if (opcao == 0) {

					String disciplina = JOptionPane.showInputDialog("Disciplina: ");
					JOptionPane.showInputDialog("Disciplina: ");

					tabelaLaboratorios.setValueAt("OCUPADO", linha, coluna);
				} else if (opcao == 1) {
					tabelaLaboratorios.setValueAt("", linha, coluna);
				} else if (opcao == 2) {
					tabelaLaboratorios.setValueAt("INTERDITADO", linha, coluna);
				}
			}
		});
		
		scrollPaneTabela.setViewportView(tabelaLaboratorios);
		
		textFieldDia = new JTextField();
		textFieldDia.setHorizontalAlignment(SwingConstants.CENTER);
		textFieldDia.setText("Sábado - 22/06/2026");
		textFieldDia.setBounds(400, 39, 249, 29);
		textFieldDia.setFont(new Font("Calibri", Font.BOLD, 22));
		textFieldDia.setBorder(null);
		
		painelTabela.add(textFieldDia);
		textFieldDia.setColumns(10);
		
		JButton btnVoltarDia = new JButton("");
		btnVoltarDia.setBounds(357, 39, 29, 29);
		ImageIcon iconeVoltar = new ImageIcon(getClass().getResource("/images/setaEsquerda.jpg"));
		btnVoltarDia.setIcon(ImageUtil.redimensionarImagem(iconeVoltar, 28, 28));
		painelTabela.add(btnVoltarDia);
		
		JButton btnPassarDia = new JButton("");
		btnPassarDia.setBounds(654, 39, 29, 29);
		ImageIcon iconePassar = new ImageIcon(getClass().getResource("/images/setaDireita.jpg"));
		btnPassarDia.setIcon(ImageUtil.redimensionarImagem(iconePassar, 28, 28));
		painelTabela.add(btnPassarDia);
		
		painelConteudo.setBounds(193, 5, 1062, 672);
		painelConteudo.setBackground(Color.WHITE);
		contentPane.add(painelConteudo);

		String[][] labs = {
			{"Lab 01", "Redes e Manutenção", "Térreo",     "Ativo",      "15"},
			{"Lab 02", "Informática",     "Térreo",     "Ativo",      "20"},
			{"Lab 03", "Desenvolvimento", "Térreo",     "Ativo",      "18"},
			{"Lab 05", "Sala do Diretório Acadêmico - ADS", "1° Andar", "Ativo", "12"},
			{"Lab 06", "Prática de IA",   "1° Andar",   "Ativo",      "16"},
			{"Lab 07", "Informática 2",   "1° Andar",   "Ativo",      "22"},
		};
		String[] cardKeys = {"LAB01", "LAB02", "LAB03", "LAB05", "LAB06",  "LAB07"};

		for (int i = 0; i < labs.length; i++) {
			painelConteudo.add(criarPainelLab(labs[i]), cardKeys[i]);
		}

		painelConteudo.add(criarPainelPlaceholder("Dashboard"),             "DASHBOARD");
		painelConteudo.add(criarPainelPlaceholder("Interditar Laboratório"), "INTERDITAR");

		
		
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
	
	private JPanel criarPainelLab(String[] lab) {
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
		btnVoltar.addActionListener(e -> cardLayout.show(painelConteudo, "TABELA"));
		painel.add(btnVoltar);

		JLabel titulo = new JLabel(lab[0] + " – " + lab[1], SwingConstants.CENTER);
		titulo.setFont(new Font("Calibri", Font.PLAIN, 32));
		titulo.setBounds(0, 25, 1062, 42);
		painel.add(titulo);

		JLabel andar = new JLabel(lab[2], SwingConstants.CENTER);
		andar.setFont(new Font("Calibri", Font.ITALIC, 16));
		andar.setForeground(Color.GRAY);
		andar.setBounds(0, 75, 1062, 25);
		painel.add(andar);

		JLabel lblStatus = new JLabel("Status: " + lab[3], SwingConstants.CENTER);
		lblStatus.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblStatus.setForeground(new Color(34, 139, 34));
		lblStatus.setBounds(0, 120, 1062, 25);
		painel.add(lblStatus);

		JLabel lblComputadores = new JLabel("Computadores funcionando: " + lab[4], SwingConstants.CENTER);
		lblComputadores.setFont(new Font("Calibri", Font.PLAIN, 14));
		lblComputadores.setForeground(Color.GRAY);
		lblComputadores.setBounds(0, 155, 1062, 25);
		painel.add(lblComputadores);

		JLabel placeholder = new JLabel("Detalhes do laboratório em desenvolvimento", SwingConstants.CENTER);
		placeholder.setFont(new Font("Calibri", Font.ITALIC, 14));
		placeholder.setForeground(new Color(190, 190, 190));
		placeholder.setBounds(0, 330, 1062, 25);
		painel.add(placeholder);

		return painel;
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
	
	class ColorRenderer extends DefaultTableCellRenderer{
		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent (JTable tabela, Object disponibilidade, boolean isSelected, boolean hasFocus, int coluna, int linha) {
			super.getTableCellRendererComponent(tabela, disponibilidade, isSelected, hasFocus, linha, coluna);
			setHorizontalAlignment(SwingConstants.CENTER);
			Color verde = new Color(126, 217, 87);
			Color vermelho = new Color(255, 82, 82);
			Color cinza = new Color(180, 180, 180);
			
			if (disponibilidade.toString().equals("")) {
				setBackground(verde);
			} else if (disponibilidade.toString().equals("INTERDITADO")) {
				setBackground(cinza);
			} else if (disponibilidade.toString().equals("OCUPADO")){
				setBackground(vermelho);
			} else {
				setBackground(Color.white);
			}
			
			return this;
		}
	}
}
