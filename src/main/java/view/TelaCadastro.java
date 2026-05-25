package view;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import at.favre.lib.crypto.bcrypt.BCrypt;

import java.awt.Font;
import java.awt.event.*;
import java.text.ParseException;
import java.awt.Color;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Arrays;

public class TelaCadastro extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField campoNome;
	private JTextField campoEmail;
	private JFormattedTextField campoTelefone;
	private JPasswordField campoSenha;
	private JPasswordField campoConfirmarSenha;
	private JFormattedTextField campoCpf;
	private JComboBox comboBox;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\heito\\OneDrive\\Imagens\\logo - Copia.PNG"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 603);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle("QLAB - Cadastro de Usuário");
		setResizable(false);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nome = campoNome.getText().trim();
				String email = campoEmail.getText().trim();
				String cpf = campoCpf.getText();
				String telefone = campoTelefone.getText();
				char[] senha = campoSenha.getPassword();
				char[] confirmarSenha = campoConfirmarSenha.getPassword();

				if (nome.isEmpty() || nome.equals("Digite seu nome")) {
					JOptionPane.showMessageDialog(null, "O campo Nome não pode estar vazio!");
					return;
				}

				if (email.isEmpty() || email.equals("Digite seu email")) {
					JOptionPane.showMessageDialog(null, "O campo Email não pode estar vazio!");
					return;
				}
				if (!email.contains("@") || !email.contains(".")) {
					JOptionPane.showMessageDialog(null, "Email inválido! Use o formato: usuario@email.com");
					return;
				}

				if (cpf.contains("_") || cpf.replace(".", "").replace("-", "").isBlank()) {
					JOptionPane.showMessageDialog(null, "CPF inválido ou incompleto!");
					return;
				}

				if (telefone.contains("_") || telefone.replace("(", "").replace(")", "").replace(" ", "").replace("-", "").isBlank()) {
					JOptionPane.showMessageDialog(null, "Telefone inválido ou incompleto!");
					return;
				}

				if (senha.length == 0) {
					JOptionPane.showMessageDialog(null, "O campo Senha não pode estar vazio!");
					return;
				}
				if (senha.length < 6) {
					JOptionPane.showMessageDialog(null, "A senha deve ter pelo menos 6 caracteres!");
					return;
				}
				if (!Arrays.equals(senha, confirmarSenha)) {
					JOptionPane.showMessageDialog(null, "As senhas não coincidem!");
					return;
				}

				if (comboBox.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Selecione um perfil!");
					return;
				}

				String hashSenha = BCrypt.with(BCrypt.Version.VERSION_2A).hashToString(12, senha);
				Arrays.fill(senha, '\0');
				Arrays.fill(confirmarSenha, '\0');
				JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
			}
		});
		btnCadastrar.setForeground(new Color(255, 255, 255));
		btnCadastrar.setBackground(new Color(34, 139, 34));
		btnCadastrar.setBounds(266, 490, 152, 50);
		btnCadastrar.setFocusable(false);
		contentPane.add(btnCadastrar);
		
		campoNome = new JTextField();
		campoNome.setFont(new Font("Calibri", Font.BOLD, 11));
		campoNome.setBounds(109, 178, 474, 33);
		contentPane.add(campoNome);
		campoNome.setColumns(10);
		campoNome.setBorder(BorderFactory.createLineBorder(Color.black,2));
		
		campoEmail = new JTextField();
		campoEmail.setFont(new Font("Calibri", Font.BOLD, 11));
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
		
		campoTelefone = new JFormattedTextField();
		campoTelefone.setFont(new Font("Calibri", Font.BOLD, 11));
		campoTelefone.setBounds(109, 368, 474, 33);
		contentPane.add(campoTelefone);
		campoTelefone.setColumns(10);
		campoTelefone.setBorder(BorderFactory.createLineBorder(Color.black,2));
		
		MaskFormatter mascaraTelefone;
		try{
		    mascaraTelefone = new MaskFormatter("(##) #####-####");
		    mascaraTelefone.setPlaceholderCharacter('_');
		    mascaraTelefone.install(campoTelefone);
		}catch (ParseException e){
		    e.printStackTrace();
		}

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
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Calibri", Font.BOLD, 13));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"    Selecione...", "    Administrador", "    Professor", "    Técnico", "    Guardião"}));
		comboBox.setBounds(277, 116, 141, 28);
		contentPane.add(comboBox);

		JLabel lblNewLabel_3 = new JLabel("Perfil");
		lblNewLabel_3.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(234, 121, 46, 14);
		contentPane.add(lblNewLabel_3);

		campoSenha = new JPasswordField();
		campoSenha.setFont(new Font("Calibri", Font.BOLD, 11));
		campoSenha.setBounds(109, 423, 167, 33);
		contentPane.add(campoSenha);

		campoSenha.setBorder(BorderFactory.createLineBorder(Color.black,2));

		campoConfirmarSenha = new JPasswordField();
		campoConfirmarSenha.setFont(new Font("Calibri", Font.BOLD, 11));
		campoConfirmarSenha.setBounds(409, 423, 174, 33);
		contentPane.add(campoConfirmarSenha);

		campoConfirmarSenha.setBorder(BorderFactory.createLineBorder(Color.black,2));
		
		adicionarPlaceholder(campoEmail, "Digite seu email");
		adicionarPlaceholder(campoNome, "Digite seu nome");
		
		JLabel labelCpf = new JLabel("CPF");
		labelCpf.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelCpf.setBounds(44, 247, 58, 26);
		contentPane.add(labelCpf);

		campoCpf = new JFormattedTextField();
		campoCpf.setFont(new Font("Calibri", Font.BOLD, 11));
		campoCpf.setColumns(10);
		campoCpf.setBounds(109, 243, 474, 33);
		contentPane.add(campoCpf);
		campoCpf.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				SwingUtilities.invokeLater(() -> campoCpf.setCaretPosition(0));
			}
		});

		campoCpf.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				campoCpf.setCaretPosition(0);
			}
		});

		MaskFormatter mascaraCpf;
		try {
			mascaraCpf = new MaskFormatter("###.###.###-##");
			mascaraCpf.setPlaceholderCharacter('_');
			mascaraCpf.install(campoCpf);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		campoCpf.setBorder(BorderFactory.createLineBorder(Color.black,2,true));

		JLabel labelCadastro = new JLabel("Cadastro");
		labelCadastro.setFont(new Font("Calibri", Font.PLAIN, 50));
		labelCadastro.setBounds(234, 44, 202, 62);
		contentPane.add(labelCadastro);
		
		JLabel labelIcone = new JLabel("");
		labelIcone.setIcon(new ImageIcon("C:\\Users\\heito\\OneDrive\\Imagens\\logo - Copia.PNG"));
		labelIcone.setBounds(44, 11, 69, 80);
		contentPane.add(labelIcone);
		}
	
	public void adicionarPlaceholder(JTextField campo, String textoPlaceholder) {
		campo.setText(textoPlaceholder);
		campo.setForeground(Color.GRAY);
		
		campo.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				if (campo.getText().equals(textoPlaceholder)) {
					campo.setForeground(Color.BLACK);
					campo.setText("");
				}
			}
			
			@Override
			public void focusLost(FocusEvent e) {
				if (campo.getText().equals("")) {
					campo.setForeground(Color.GRAY);
					campo.setText(textoPlaceholder);
				}
			}
		});
		
		
		
		
		
		
		
	}
}

