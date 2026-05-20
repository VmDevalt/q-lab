package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import javax.swing.JPasswordField;
import java.awt.Color;
import javax.swing.ImageIcon;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 603);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle("Tela Cadastro");
		
		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setForeground(Color.WHITE);
		btnCadastrar.setBackground(new Color(34, 139, 34));
		btnCadastrar.setBounds(246, 491, 152, 50);
		contentPane.add(btnCadastrar);
		
		campoNome = new JTextField();
		campoNome.setFont(new Font("Tahoma", Font.BOLD, 11));
		campoNome.setBounds(123, 178, 474, 33);
		contentPane.add(campoNome);
		campoNome.setColumns(10);
		
		campoEmail = new JTextField();
		campoEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		campoEmail.setBounds(123, 307, 474, 33);
		contentPane.add(campoEmail);
		campoEmail.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Senha");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(55, 432, 46, 14);
		contentPane.add(lblNewLabel);
		
		JLabel labelNome = new JLabel("Nome");
		labelNome.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelNome.setBounds(55, 185, 46, 14);
		contentPane.add(labelNome);
		
		campoTelefone = new JTextField();
		campoTelefone.setFont(new Font("Tahoma", Font.BOLD, 11));
		campoTelefone.setBounds(123, 368, 474, 33);
		contentPane.add(campoTelefone);
		campoTelefone.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Confirmar Senha");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(308, 432, 141, 14);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Email");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(55, 314, 46, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Telefone");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2_1.setBounds(55, 375, 73, 14);
		contentPane.add(lblNewLabel_2_1);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Tahoma", Font.BOLD, 13));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"    Administrador", "    Professor", "    Técnico", "    Guardião"}));
		comboBox.setBounds(277, 116, 141, 28);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_3 = new JLabel("Perfil");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(234, 121, 46, 14);
		contentPane.add(lblNewLabel_3);
		
		campoSenha = new JPasswordField();
		campoSenha.setFont(new Font("Tahoma", Font.BOLD, 11));
		campoSenha.setBounds(124, 425, 174, 33);
		contentPane.add(campoSenha);
		
		campoConfirmarSenha = new JPasswordField();
		campoConfirmarSenha.setFont(new Font("Tahoma", Font.BOLD, 11));
		campoConfirmarSenha.setBounds(426, 423, 174, 33);
		contentPane.add(campoConfirmarSenha);
		
		JLabel labelCpf = new JLabel("CPF");
		labelCpf.setFont(new Font("Tahoma", Font.PLAIN, 15));
		labelCpf.setBounds(55, 250, 46, 14);
		contentPane.add(labelCpf);
		
		campoCpf = new JTextField();
		campoCpf.setFont(new Font("Tahoma", Font.BOLD, 11));
		campoCpf.setColumns(10);
		campoCpf.setBounds(123, 243, 474, 33);
		contentPane.add(campoCpf);
		
		JLabel labelCadastro = new JLabel("Cadastro");
		labelCadastro.setFont(new Font("Tahoma", Font.PLAIN, 50));
		labelCadastro.setBounds(234, 22, 202, 44);
		contentPane.add(labelCadastro);
		
		JLabel labelIcone = new JLabel("Icone");
		labelIcone.setBounds(71, 53, 46, 14);
		contentPane.add(labelIcone);
		
		
		}
	}


