package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.ImageIcon;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaCadastro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField campoNome;
	private JTextField campoEmail;
	private JTextField campoTelefone;
	private JPasswordField campoSenha;
	private JPasswordField campoConfirmarSenha;
	private JTextField campoCpf;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaCadastro frame = new TelaCadastro();
					frame.setVisible(true);
					frame.setTitle("Tela Cadastro");
					frame.setResizable(false);
					
				
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TelaCadastro() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\heito\\OneDrive\\Imagens\\logo - Copia.PNG"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 603);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCadastrar.setForeground(new Color(255, 255, 255));
		btnCadastrar.setBackground(new Color(34, 139, 34));
		btnCadastrar.setBounds(266, 490, 152, 50);
		btnCadastrar.setFocusable(false);
		contentPane.add(btnCadastrar);
		
		campoNome = new JTextField();
		campoNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		campoNome.setBounds(109, 178, 474, 33);
		contentPane.add(campoNome);
		campoNome.setColumns(10);
		campoNome.setBorder(BorderFactory.createLineBorder(Color.black,2));
		
		campoEmail = new JTextField();
		campoEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		campoEmail.setBounds(109, 307, 474, 33);
		contentPane.add(campoEmail);
		campoEmail.setColumns(10);
		campoEmail.setBorder(BorderFactory.createLineBorder(Color.black,2));
		
		JLabel labelSenha = new JLabel("Senha");
		labelSenha.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelSenha.setBounds(44, 432, 58, 17);
		contentPane.add(labelSenha);
		
		JLabel labelNome = new JLabel("Nome");
		labelNome.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelNome.setBounds(44, 183, 69, 24);
		contentPane.add(labelNome);
		
		campoTelefone = new JTextField();
		campoTelefone.setFont(new Font("Tahoma", Font.BOLD, 11));
		campoTelefone.setBounds(109, 368, 474, 33);
		contentPane.add(campoTelefone);
		campoTelefone.setColumns(10);
		campoTelefone.setBorder(BorderFactory.createLineBorder(Color.black,2));
		
		JLabel labelConfirmarSenha = new JLabel("Confirmar Senha");
		labelConfirmarSenha.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelConfirmarSenha.setBounds(277, 423, 141, 34);
		contentPane.add(labelConfirmarSenha);
		
		JLabel labelEmail = new JLabel("Email");
		labelEmail.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelEmail.setBounds(44, 314, 58, 21);
		
		contentPane.add(labelEmail);
		
		JLabel labelTelefone = new JLabel("Telefone");
		labelTelefone.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelTelefone.setBounds(35, 373, 78, 24);
		contentPane.add(labelTelefone);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 13));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"    Administrador", "    Professor", "    Técnico", "    Guardião"}));
		comboBox.setBounds(277, 116, 141, 28);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_3 = new JLabel("Perfil");
		lblNewLabel_3.setFont(new Font("Calibri", Font.PLAIN, 18));
		lblNewLabel_3.setBounds(218, 116, 58, 40);
		contentPane.add(lblNewLabel_3);
		
		campoSenha = new JPasswordField();
		campoSenha.setFont(new Font("Tahoma", Font.BOLD, 11));
		campoSenha.setBounds(109, 423, 167, 33);
		contentPane.add(campoSenha);
		campoSenha.setBorder(BorderFactory.createLineBorder(Color.black,2));
		
		campoConfirmarSenha = new JPasswordField();
		campoConfirmarSenha.setToolTipText("\r\n");
		campoConfirmarSenha.setFont(new Font("Tahoma", Font.BOLD, 11));
		campoConfirmarSenha.setBounds(409, 423, 174, 33);
		contentPane.add(campoConfirmarSenha);
		campoConfirmarSenha.setBorder(BorderFactory.createLineBorder(Color.black,2));
		
		JLabel labelCpf = new JLabel("CPF");
		labelCpf.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelCpf.setBounds(44, 247, 58, 26);
		contentPane.add(labelCpf);
		
		campoCpf = new JTextField();
		campoCpf.setFont(new Font("Tahoma", Font.BOLD, 11));
		campoCpf.setColumns(10);
		campoCpf.setBounds(109, 243, 474, 33);
		contentPane.add(campoCpf);
		campoCpf.setBorder(BorderFactory.createLineBorder(Color.black,2,true));
		
		JLabel labelCadastro = new JLabel("Cadastro");
		labelCadastro.setFont(new Font("Tahoma", Font.PLAIN, 50));
		labelCadastro.setBounds(234, 22, 202, 44);
		contentPane.add(labelCadastro);
		
		JLabel labelIcone = new JLabel("");
		labelIcone.setIcon(new ImageIcon("C:\\Users\\heito\\OneDrive\\Imagens\\logo - Copia.PNG"));
		labelIcone.setBounds(44, 11, 69, 80);
		contentPane.add(labelIcone);
		
		
		}
	}


