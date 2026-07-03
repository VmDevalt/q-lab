package view;
import java.awt.EventQueue;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JSeparator;

public class ModalDetalhamento extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static final Color VERDE_ESCURO = new Color(46, 125, 50);
	private static final Color VERDE_CLARO = new Color(232, 245, 233);
	private static final Color BRANCO = Color.WHITE;
	private static final Color CINZA_TEXTO = new Color(60, 60, 60);
	private static final Color CINZA_ROTULO = new Color(110, 110, 110);

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModalDetalhamento frame = new ModalDetalhamento();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ModalDetalhamento() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 417, 517);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(BRANCO);
		setResizable(false);
		setContentPane(contentPane);

		
		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(VERDE_CLARO);
		panel.setBorder(new EmptyBorder(15, 20, 15, 20));

		JLabel lblTitulo = new JLabel("Reserva");
		lblTitulo.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTitulo.setForeground(CINZA_ROTULO);
	    panel.add(lblTitulo);
		
	    JLabel lblId = new JLabel("#ADS-2026-0307");
		lblId.setFont(new Font("Arial", Font.BOLD, 18));
		lblId.setForeground(VERDE_ESCURO);
	    panel.add(lblId);
	    
		JLabel lblStatus = new JLabel("Confirmada");
		lblStatus.setOpaque(true); 
		lblStatus.setBackground(new Color(200, 230, 201)); 
		lblStatus.setForeground(new Color(27, 94, 32));   
		lblStatus.setFont(new Font("Arial", Font.BOLD, 12));
		lblStatus.setBorder(new EmptyBorder(5, 12, 5, 12)); 
		
		JPanel panelTopo = new JPanel();
		panelTopo.setLayout(new BorderLayout());
		panelTopo.setBackground(VERDE_CLARO);
		panelTopo.add(panel, BorderLayout.WEST);
		panelTopo.add(lblStatus, BorderLayout.EAST);
		
	
	    JSeparator separador = new JSeparator();
		separador.setForeground(new Color(225, 225, 225));
	    
		
		JPanel panelCampos = new JPanel();
		panelCampos.setLayout(new GridLayout(8, 2, 10, 16));
		panelCampos.setBackground(BRANCO);
		panelCampos.setBorder(new EmptyBorder(20, 20, 10, 20));
		
		JLabel lblCpfRotulo = new JLabel("CPF:");
		lblCpfRotulo.setFont(new Font("Arial", Font.BOLD, 13));
		lblCpfRotulo.setForeground(CINZA_ROTULO);
		JLabel lblCpfValor = new JLabel("000.000.000-00");
		lblCpfValor.setFont(new Font("Arial", Font.PLAIN, 13));
		lblCpfValor.setForeground(CINZA_TEXTO);

		JLabel lblMatriculaRotulo = new JLabel("Matrícula:");
		lblMatriculaRotulo.setFont(new Font("Arial", Font.BOLD, 13));
		lblMatriculaRotulo.setForeground(CINZA_ROTULO);
		JLabel lblMatriculaValor = new JLabel("ADSPL12025");
		lblMatriculaValor.setFont(new Font("Arial", Font.PLAIN, 13));
		lblMatriculaValor.setForeground(CINZA_TEXTO);
		
		JLabel lblResponsavelRotulo = new JLabel("Reservado por:");
		lblResponsavelRotulo.setFont(new Font("Arial", Font.BOLD, 13));
		lblResponsavelRotulo.setForeground(CINZA_ROTULO);
		JLabel lblResponsavelValor = new JLabel("Aluno");
		lblResponsavelValor.setFont(new Font("Arial", Font.PLAIN, 13));
		lblResponsavelValor.setForeground(CINZA_TEXTO);
		
		JLabel lblAprovadoRotulo = new JLabel("Aprovado por:");
		lblResponsavelRotulo.setFont(new Font("Arial", Font.BOLD, 13));
		lblResponsavelRotulo.setForeground(CINZA_ROTULO);
		JLabel lblAprovadoValor = new JLabel("Rodrigo Rocha");
		lblResponsavelValor.setFont(new Font("Arial", Font.PLAIN, 13));
		lblResponsavelValor.setForeground(CINZA_TEXTO);

		JLabel lblMateriaRotulo = new JLabel("Matéria:");
		lblMateriaRotulo.setFont(new Font("Arial", Font.BOLD, 13));
		lblMateriaRotulo.setForeground(CINZA_ROTULO);
		JLabel lblMateriaValor = new JLabel("Banco de dados 2");
		lblMateriaValor.setFont(new Font("Arial", Font.PLAIN, 13));
		lblMateriaValor.setForeground(CINZA_TEXTO);


		JLabel lblLaboratorioRotulo = new JLabel("Laboratório:");
		lblLaboratorioRotulo.setFont(new Font("Arial", Font.BOLD, 13));
		lblLaboratorioRotulo.setForeground(CINZA_ROTULO);
		JLabel lblLaboratorioValor = new JLabel("Lab. de desenvolvimento");
		lblLaboratorioValor.setFont(new Font("Arial", Font.PLAIN, 13));
		lblLaboratorioValor.setForeground(CINZA_TEXTO);

		JLabel lblDataRotulo = new JLabel("Data:");
		lblDataRotulo.setFont(new Font("Arial", Font.BOLD, 13));
		lblDataRotulo.setForeground(CINZA_ROTULO);
		JLabel lblDataValor = new JLabel("00/00/0000");
		lblDataValor.setFont(new Font("Arial", Font.PLAIN, 13));
		lblDataValor.setForeground(CINZA_TEXTO);

		JLabel lblHorarioRotulo = new JLabel("Horário:");
		lblHorarioRotulo.setFont(new Font("Arial", Font.BOLD, 13));
		lblHorarioRotulo.setForeground(CINZA_ROTULO);
		JLabel lblHorarioValor = new JLabel("07:15 - 08:45");
		lblHorarioValor.setFont(new Font("Arial", Font.PLAIN, 13));
		lblHorarioValor.setForeground(CINZA_TEXTO);
		

		panelCampos.add(lblLaboratorioRotulo);
		panelCampos.add(lblLaboratorioValor);
		panelCampos.add(lblResponsavelRotulo);
		panelCampos.add(lblResponsavelValor);
		panelCampos.add(lblCpfRotulo);
		panelCampos.add(lblCpfValor);
		panelCampos.add(lblMatriculaRotulo);
		panelCampos.add(lblMatriculaValor);
		panelCampos.add(lblMateriaRotulo);
		panelCampos.add(lblMateriaValor);
		panelCampos.add(lblDataRotulo);
		panelCampos.add(lblDataValor);
		panelCampos.add(lblHorarioRotulo);
		panelCampos.add(lblHorarioValor);
		panelCampos.add(lblAprovadoRotulo);
		panelCampos.add(lblAprovadoValor);

		
		JPanel panelBotoes = new JPanel();
		panelBotoes.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panelBotoes.setBackground(BRANCO);
		panelBotoes.setBorder(new EmptyBorder(10, 20, 15, 20));
		
		JButton btnFechar = new JButton("Fechar");
		btnFechar.setFont(new Font("Arial", Font.BOLD, 13));
		btnFechar.setBackground(VERDE_ESCURO);
		btnFechar.setForeground(BRANCO);
		btnFechar.setFocusPainted(false);
		btnFechar.setBorderPainted(false);
		btnFechar.setOpaque(true);
		panelBotoes.add(btnFechar);
		
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new BorderLayout());
		panelCentral.setBackground(BRANCO);
		panelCentral.add(separador, BorderLayout.NORTH);
		panelCentral.add(panelCampos, BorderLayout.CENTER);

		

		contentPane.setLayout(new BorderLayout());
		contentPane.add(panelTopo, BorderLayout.NORTH);
		contentPane.add(panelCentral, BorderLayout.CENTER);
		contentPane.add(panelBotoes, BorderLayout.SOUTH);
	}

}
