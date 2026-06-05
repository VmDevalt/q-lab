package view;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.MaskFormatter;

import at.favre.lib.crypto.bcrypt.BCrypt;


import util.ImageUtil;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.Image;
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
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logo_qlab_pequena_branca.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 720);
		setLocationRelativeTo(null); 
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(Color.GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		this.setTitle("QLAB - Cadastro de Usuário");
		setResizable(false);

		JButton btnCadastrar = new JButton("Cadastrar");
		btnCadastrar.setFont(new Font("Calibri", Font.PLAIN, 13));
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
		btnCadastrar.setBounds(566, 510, 152, 50);
		btnCadastrar.setFocusable(false);
		contentPane.add(btnCadastrar);
		
		campoNome = new JTextField();
		campoNome.setFont(new Font("Calibri", Font.BOLD, 11));
		campoNome.setBounds(400, 180, 474, 33);
		contentPane.add(campoNome);
		campoNome.setColumns(10);
		campoNome.setBorder(BorderFactory.createLineBorder(Color.black,2));
		
		campoEmail = new JTextField();
		campoEmail.setFont(new Font("Calibri", Font.BOLD, 11));
		campoEmail.setBounds(400, 310, 474, 33);
		contentPane.add(campoEmail);
		campoEmail.setColumns(10);
		campoEmail.setBorder(BorderFactory.createLineBorder(Color.black,2));

		JLabel labelSenha = new JLabel("Senha");
		labelSenha.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelSenha.setBounds(328, 461, 58, 21);
		contentPane.add(labelSenha);
		
		JLabel labelNome = new JLabel("Nome");
		labelNome.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelNome.setBounds(328, 183, 69, 24);
		contentPane.add(labelNome);
		
		campoTelefone = new JFormattedTextField();
		campoTelefone.setFont(new Font("Calibri", Font.BOLD, 11));
		campoTelefone.setBounds(400, 380, 474, 33);
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
		labelConfirmarSenha.setBounds(573, 454, 141, 34);
		contentPane.add(labelConfirmarSenha);

		JLabel labelEmail = new JLabel("Email");
		labelEmail.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelEmail.setBounds(328, 315, 58, 21);

		contentPane.add(labelEmail);

		JLabel labelTelefone = new JLabel("Telefone");
		labelTelefone.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelTelefone.setBounds(328, 383, 78, 24);
		contentPane.add(labelTelefone);
		
		comboBox = new JComboBox();
		comboBox.setFont(new Font("Calibri", Font.BOLD, 13));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"    Selecione...", "    Administrador", "    Professor", "    Técnico", "    Guardião"}));
		comboBox.setBounds(566, 114, 141, 28);
		contentPane.add(comboBox);

		JLabel lblNewLabel_3 = new JLabel("Perfil");
		lblNewLabel_3.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(521, 118, 53, 21);
		contentPane.add(lblNewLabel_3);

		campoSenha = new JPasswordField();
		campoSenha.setFont(new Font("Calibri", Font.BOLD, 11));
		campoSenha.setBounds(400, 456, 167, 33);
		contentPane.add(campoSenha);

		campoSenha.setBorder(BorderFactory.createLineBorder(Color.black,2));

		campoConfirmarSenha = new JPasswordField();
		campoConfirmarSenha.setFont(new Font("Calibri", Font.BOLD, 11));
		campoConfirmarSenha.setBounds(700, 456, 174, 33);
		contentPane.add(campoConfirmarSenha);

		campoConfirmarSenha.setBorder(BorderFactory.createLineBorder(Color.black,2));
		
		adicionarPlaceholder(campoEmail, "Digite seu email");
		adicionarPlaceholder(campoNome, "Digite seu nome");
		
		JLabel labelCpf = new JLabel("CPF");
		labelCpf.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelCpf.setBounds(328, 247, 58, 26);
		contentPane.add(labelCpf);

		campoCpf = new JFormattedTextField();
		campoCpf.setFont(new Font("Calibri", Font.BOLD, 11));
		campoCpf.setColumns(10);
		campoCpf.setBounds(400, 245, 474, 33);
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
		labelCadastro.setBounds(521, 41, 202, 62);
		contentPane.add(labelCadastro);
		
		JLabel labelIcone = new JLabel("");
		ImageIcon imagemLogoQlab = new ImageIcon(getClass().getResource("/images/logo_qlab_media.png"));
		labelIcone.setIcon(ImageUtil.redimensionarImagem(imagemLogoQlab, 166, 70));
		labelIcone.setBounds(67, 21, 213, 107);
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

