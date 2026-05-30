package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class TelaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaLogin frame = new TelaLogin();
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
	public TelaLogin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logo_qlab_pequena_branca.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(Color.gray);
		this.setTitle("QLAB - Login");
		setContentPane(contentPane);
		setResizable(false);

		
	    JPanel painelPrincipal = new JPanel();
	    painelPrincipal.setBounds(150, 33, 700, 500);
	    contentPane.add(painelPrincipal);
	    painelPrincipal.setBackground(Color.white);
	    
	    JSeparator linha = new JSeparator(SwingConstants.VERTICAL);
	    linha.setBounds(369, 30, 2, 460);
	    painelPrincipal.setLayout(null);
	    painelPrincipal.add(linha);
	    linha.setForeground(Color.black);
	    
	    JLabel textoLogin = new JLabel("LOGIN");
	    painelPrincipal.add(textoLogin);
	    
	    JLabel labelCadastro = new JLabel("Login");
	    labelCadastro.setHorizontalAlignment(SwingConstants.CENTER);
	    labelCadastro.setFont(new Font("Calibri", Font.PLAIN, 50));
	    labelCadastro.setBounds(397, 53, 239, 70);
	    painelPrincipal.add(labelCadastro);
	    
	    passwordField = new JPasswordField();
	    passwordField.setBounds(444, 277, 207, 26);
	    painelPrincipal.add(passwordField);
	    
	    textField = new JTextField();
	    textField.setForeground(new Color(0, 0, 0));
	    textField.setBounds(444, 199, 207, 26);
	    painelPrincipal.add(textField);
	    textField.setColumns(10);
	    
	    JLabel labelCpf = new JLabel("CPF");
	    labelCpf.setFont(new Font("Calibri", Font.PLAIN, 21));
	    labelCpf.setBounds(381, 200, 124, 25);
	    painelPrincipal.add(labelCpf);
	    
	    JLabel labelSenha = new JLabel("Senha");
	    labelSenha.setFont(new Font("Calibri", Font.PLAIN, 21));
	    labelSenha.setBounds(381, 278, 124, 25);
	    painelPrincipal.add(labelSenha);
	    
	    JButton btnEntrar = new JButton("Entrar");
	    btnEntrar.setFont(new Font("Calibri", Font.PLAIN, 14));
	    btnEntrar.setBounds(396, 333, 124, 26);
	    btnEntrar.setForeground(new Color(255, 255, 255));
	    btnEntrar.setBackground(new Color(34, 139, 34));
	    painelPrincipal.add(btnEntrar);
	    	    
	    JLabel labelLogoQlab = new JLabel("");
	    labelLogoQlab.setHorizontalAlignment(SwingConstants.TRAILING);
	    ImageIcon imagemLogoQlab = new ImageIcon(getClass().getResource("/images/logo_qlab_media.png"));
	    Image imagemLogoRedimensionada = imagemLogoQlab.getImage().getScaledInstance(300, 135, Image.SCALE_SMOOTH);;
	    labelLogoQlab.setIcon(new ImageIcon(imagemLogoRedimensionada));
	    labelLogoQlab.setBounds(10, 30, 349, 119);
	    painelPrincipal.add(labelLogoQlab);
	    
	    JLabel labelImagemPredio = new JLabel("");
	    labelImagemPredio.setHorizontalAlignment(SwingConstants.TRAILING);
	    ImageIcon imagemOriginal = new ImageIcon(getClass().getResource("/images/predio_ifpe.png"));
	    Image imagemRedimensionada = imagemOriginal.getImage().getScaledInstance(350, 400, Image.SCALE_SMOOTH);
	    labelImagemPredio.setIcon(new ImageIcon(imagemRedimensionada));
	    labelImagemPredio.setBounds(10, 98, 349, 392);
	    painelPrincipal.add(labelImagemPredio);
	    
	    JButton btnEsqueciSenha = new JButton("Esqueci a senha");
	    btnEsqueciSenha.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    	}
	    });
	    btnEsqueciSenha.setFont(new Font("Calibri", Font.PLAIN, 14));
	    btnEsqueciSenha.setBounds(530, 333, 131, 26);
	    btnEsqueciSenha.setForeground(new Color(255, 255, 255));
	    btnEsqueciSenha.setBackground(new Color(34, 139, 34));
	    painelPrincipal.add(btnEsqueciSenha);
	    
	    JButton btnCadastrar = new JButton("Cadastrar");
	    btnCadastrar.setFont(new Font("Calibri", Font.PLAIN, 15));
	    btnCadastrar.setBounds(535, 426, 126, 26);
	    btnCadastrar.setForeground(new Color(255, 255, 255));
	    btnCadastrar.setBackground(new Color(34, 139, 34));
	    painelPrincipal.add(btnCadastrar);
	    
	    JLabel labelCadastrar = new JLabel("Cadastre-se aqui:");
	    labelCadastrar.setFont(new Font("Calibri", Font.PLAIN, 21));
	    labelCadastrar.setBounds(381, 426, 170, 25);
	    painelPrincipal.add(labelCadastrar);

	    
	}
}
