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

import javax.swing.JFormattedTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.util.Arrays;
import controller.LoginController;
import model.Usuario;

public class TelaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JFormattedTextField campoMatricula;
	private boolean senhaVisivel = false;

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

	public TelaLogin() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logo_qlab_pequena_branca.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1280, 720);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setLayout(null);
		contentPane.setBackground(Color.gray);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.setTitle("QLAB - Login");
		setResizable(false);

		JPanel painelPrincipal = new JPanel();
		painelPrincipal.setBounds(0, -30, 1286, 711);
		contentPane.add(painelPrincipal);
		painelPrincipal.setBackground(Color.white);

		JSeparator linha = new JSeparator(SwingConstants.VERTICAL);
		linha.setBounds(616, 89, 2, 460);
		painelPrincipal.setLayout(null);

		ImageIcon iconOlhoClos = new ImageIcon(getClass().getResource("/images/olhoClos.png"));
		ImageIcon iconOlho = new ImageIcon(getClass().getResource("/images/olho.png"));

		JButton btnMostrarSenha = new JButton("");
		btnMostrarSenha.setIcon(iconOlhoClos);
		btnMostrarSenha.setContentAreaFilled(false);
		btnMostrarSenha.setBorderPainted(false);
		btnMostrarSenha.setFocusPainted(false);
		btnMostrarSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (senhaVisivel) {
					passwordField.setEchoChar('*');
					btnMostrarSenha.setIcon(iconOlhoClos);
					senhaVisivel = false;
				} else {
					passwordField.setEchoChar('\0');
					btnMostrarSenha.setIcon(iconOlho);
					senhaVisivel = true;
				}
			}
		});
		btnMostrarSenha.setBounds(935, 310, 27, 26);
		painelPrincipal.add(btnMostrarSenha);
		painelPrincipal.add(linha);
		linha.setForeground(Color.black);

		JLabel textoLogin = new JLabel("LOGIN");
		painelPrincipal.add(textoLogin);

		JLabel labelCadastro = new JLabel("Login");
		labelCadastro.setHorizontalAlignment(SwingConstants.CENTER);
		labelCadastro.setFont(new Font("Calibri", Font.PLAIN, 50));
		labelCadastro.setBounds(660, 114, 239, 70);
		painelPrincipal.add(labelCadastro);

		passwordField = new JPasswordField();
		passwordField.setBounds(716, 310, 207, 26);
		painelPrincipal.add(passwordField);

		campoMatricula = new JFormattedTextField();
		campoMatricula.setForeground(new Color(0, 0, 0));
		campoMatricula.setBounds(716, 229, 207, 26);
		campoMatricula.setColumns(10);
		painelPrincipal.add(campoMatricula);

		JLabel labelMatricula = new JLabel("Matrícula");
		labelMatricula.setFont(new Font("Calibri", Font.PLAIN, 21));
		labelMatricula.setBounds(628, 231, 124, 25);
		painelPrincipal.add(labelMatricula);

		JLabel labelSenha = new JLabel("Senha");
		labelSenha.setFont(new Font("Calibri", Font.PLAIN, 21));
		labelSenha.setBounds(628, 312, 124, 25);
		painelPrincipal.add(labelSenha);

		JButton btnEntrar = new JButton("Entrar");
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String matricula = campoMatricula.getText();
				char[] senhaChars = passwordField.getPassword();
				String senha = new String(senhaChars);
				Arrays.fill(senhaChars, '\0');

				if (matricula.contains("_") || matricula.replace(".", "").replace("-", "").isBlank()) {
					JOptionPane.showMessageDialog(TelaLogin.this, "Informe uma matrícula válida.", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}
				if (senha.isEmpty()) {
					JOptionPane.showMessageDialog(TelaLogin.this, "Informe a senha.", "Aviso", JOptionPane.WARNING_MESSAGE);
					return;
				}

				try {
					LoginController controller = new LoginController();
					Usuario usuario = controller.logarUsuario(matricula, senha);
					TelaSelecionarLab tela = new TelaSelecionarLab(usuario);
					tela.setVisible(true);
					dispose();
				} catch (Exception ex) {
					JOptionPane.showMessageDialog(TelaLogin.this, ex.getMessage(), "Erro de Login", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnEntrar.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnEntrar.setBounds(628, 464, 124, 26);
		btnEntrar.setForeground(new Color(255, 255, 255));
		btnEntrar.setBackground(new Color(34, 139, 34));
		painelPrincipal.add(btnEntrar);

		JLabel labelLogoQlab = new JLabel("");
		labelLogoQlab.setHorizontalAlignment(SwingConstants.TRAILING);
		ImageIcon imagemLogoQlab = new ImageIcon(getClass().getResource("/images/logo_qlab_media.png"));
		Image imagemLogoRedimensionada = imagemLogoQlab.getImage().getScaledInstance(300, 135, Image.SCALE_SMOOTH);
		labelLogoQlab.setIcon(new ImageIcon(imagemLogoRedimensionada));
		labelLogoQlab.setBounds(221, 41, 349, 119);
		painelPrincipal.add(labelLogoQlab);

		JLabel labelImagemPredio = new JLabel("");
		labelImagemPredio.setHorizontalAlignment(SwingConstants.TRAILING);
		ImageIcon imagemOriginal = new ImageIcon(getClass().getResource("/images/predio_ifpe.png"));
		Image imagemRedimensionada = imagemOriginal.getImage().getScaledInstance(350, 400, Image.SCALE_SMOOTH);
		labelImagemPredio.setIcon(new ImageIcon(imagemRedimensionada));
		labelImagemPredio.setBounds(269, 157, 349, 392);
		painelPrincipal.add(labelImagemPredio);

		JButton btnEsqueciSenha = new JButton("Esqueci a senha");
		btnEsqueciSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEsqueciSenha.setFont(new Font("Calibri", Font.PLAIN, 14));
		btnEsqueciSenha.setBounds(792, 464, 131, 26);
		btnEsqueciSenha.setForeground(new Color(255, 255, 255));
		btnEsqueciSenha.setBackground(new Color(34, 139, 34));
		painelPrincipal.add(btnEsqueciSenha);
	}
}
