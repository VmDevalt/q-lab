package view;

import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;

import at.favre.lib.crypto.bcrypt.BCrypt;

import controller.CadastroController;
import model.Perfil;
import util.ImageUtil;
import util.ValidarSenhaUtil;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.*;
import java.text.ParseException;
import java.awt.Color;
import java.awt.Toolkit;
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
	private JTextField campoMatricula;
	private JComboBox<String> comboBox;
	private Boolean senhaVisivel = false;
	private JCheckBox checkBoxAdministrador;

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
				String matricula = campoMatricula.getText().trim();
				String email = campoEmail.getText().trim();
				String cpf = campoCpf.getText();
				String telefone = campoTelefone.getText();
				char[] senha = campoSenha.getPassword();
				char[] confirmarSenha = campoConfirmarSenha.getPassword();
				String senhaString = new String (senha);
				String requisitosSenha =  ValidarSenhaUtil.validarSenha(senhaString);

				if (nome.isEmpty() || nome.equals("Digite seu nome")) {
					destacarBorda(campoNome);
					JOptionPane.showMessageDialog(null, "O campo Nome não pode estar vazio!");
					return;
				} else {
					restaurarBorda(campoNome);
				}

				if (matricula.isEmpty()) {
					destacarBorda(campoMatricula);
					JOptionPane.showMessageDialog(null, "O campo Matrícula não pode estar vazio!");
					return;
				} else {
					restaurarBorda(campoMatricula);
				}
				
				if (comboBox.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(null, "Selecione um perfil!");
					return;
				}
				
				Perfil[] perfis = Perfil.values();
				Perfil perfilSelecionado = perfis[comboBox.getSelectedIndex() - 1];
				
				if (perfilSelecionado == Perfil.PROFESSOR || perfilSelecionado == Perfil.TECNICO) {
					if (!campoMatricula.getText().matches("\\d{7,8}")) {
						destacarBorda(campoMatricula);
						JOptionPane.showMessageDialog(null, 
							"Matrícula inválida! Use 7 ou 8 dígitos numéricos.",
							"Erro", 
							JOptionPane.ERROR_MESSAGE);
						return;
					}else {
						restaurarBorda(campoMatricula);
					}
					
				} else if (perfilSelecionado == Perfil.ESTUDANTE_GUARDIAO) {
					if (!campoMatricula.getText().toUpperCase().matches("\\d{5}[A-Z]{5}\\d{4}")) {
						destacarBorda(campoMatricula);
						JOptionPane.showMessageDialog(null, 
							"Matrícula inválida! Use o formato: 20251ADSPL0076",
							"Erro", 
							JOptionPane.ERROR_MESSAGE);
						return;
					} else {
						restaurarBorda(campoMatricula);
					}
				}

				if (email.isEmpty() || email.equals("Digite seu email")) {
					destacarBorda(campoEmail);
					JOptionPane.showMessageDialog(null, "O campo Email não pode estar vazio!");
					return;
				} else {
					restaurarBorda(campoEmail);
				}
				
				
				//regex email
				if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
					destacarBorda(campoEmail);
					JOptionPane.showMessageDialog(null,"Email inválido! Use o formato usuario@email.com");
					return;
				} else {
					restaurarBorda(campoEmail);
				}

				if (cpf.contains("_") || cpf.replace(".", "").replace("-", "").isBlank()) {
					destacarBorda(campoCpf);
					JOptionPane.showMessageDialog(null, "CPF inválido ou incompleto!");
					return;
				} else {
					restaurarBorda(campoCpf);
				}
				
				
				//regex cpf
				if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
					destacarBorda(campoCpf);
					JOptionPane.showMessageDialog(null, "CPF deve estar no formato 000.000.000-00!");
					return;
				} else {
					restaurarBorda(campoCpf);
				}

				if (telefone.contains("_") || telefone.replace("(", "").replace(")", "").replace(" ", "").replace("-", "").isBlank()) {
					destacarBorda(campoTelefone);
					JOptionPane.showMessageDialog(null, "Telefone inválido ou incompleto!");
					return;
				} else {
					restaurarBorda(campoTelefone);
				}

				if (senha.length == 0) {
					destacarBorda(campoSenha);
					JOptionPane.showMessageDialog(null, "O campo Senha não pode estar vazio!");
					return;
				} else {
					restaurarBorda(campoSenha);
				}
				
				if (requisitosSenha != "") {
					destacarBorda(campoSenha);
					JOptionPane.showMessageDialog(null, requisitosSenha);
					return;
				} else {
					restaurarBorda(campoSenha);
				}
				
				if (!Arrays.equals(senha, confirmarSenha)) {
					destacarBorda(campoSenha);
					destacarBorda(campoConfirmarSenha);
					JOptionPane.showMessageDialog(null, "As senhas não coincidem!");
					return;
				} else {
					restaurarBorda(campoSenha);
					restaurarBorda(campoConfirmarSenha);
				}
				
				boolean administrador = false;
				if (perfilSelecionado == Perfil.PROFESSOR || perfilSelecionado == Perfil.TECNICO) {
					administrador = checkBoxAdministrador.isSelected();
				}


				String hashSenha = BCrypt.with(BCrypt.Version.VERSION_2A).hashToString(12, senha);
				Arrays.fill(senha, '\0');
				Arrays.fill(confirmarSenha, '\0');

				CadastroController controller = new CadastroController();
				int sucesso = controller.CadastrarUsuario(nome, email, matricula, cpf, telefone, hashSenha, perfilSelecionado, administrador);

				if (sucesso == 1) {
					System.out.println(sucesso);
					JOptionPane.showMessageDialog(TelaCadastro.this, "Cadastro realizado com sucesso!");
					dispose();
				} else if (sucesso == 2) {
					JOptionPane.showMessageDialog(TelaCadastro.this, "O usuário já está cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
				}else {				
					JOptionPane.showMessageDialog(TelaCadastro.this, "Erro ao realizar cadastro.", "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		btnCadastrar.setForeground(new Color(255, 255, 255));
		btnCadastrar.setBackground(new Color(34, 139, 34));
		btnCadastrar.setBounds(562, 610, 152, 50);
		btnCadastrar.setFocusable(false);
		contentPane.add(btnCadastrar);
		
		campoNome = new JTextField();
		campoNome.setFont(new Font("Calibri", Font.BOLD, 11));
		campoNome.setBounds(400, 180, 474, 33);
		contentPane.add(campoNome);
		campoNome.setColumns(10);
		campoNome.setBorder(BorderFactory.createLineBorder(Color.black,2));

		JLabel labelMatricula = new JLabel("Matrícula");
		labelMatricula.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelMatricula.setBounds(328, 248, 100, 24);
		contentPane.add(labelMatricula);

		campoMatricula = new JTextField();
		campoMatricula.setFont(new Font("Calibri", Font.BOLD, 11));
		campoMatricula.setBounds(400, 245, 474, 33);
		campoMatricula.setColumns(10);
		campoMatricula.setBorder(BorderFactory.createLineBorder(Color.black, 2));
		contentPane.add(campoMatricula);
			
		campoMatricula.getDocument().addDocumentListener(new DocumentListener () {
			public void insertUpdate(DocumentEvent e) {validar();}
			public void removeUpdate(DocumentEvent e) {validar();}
			public void changedUpdate(DocumentEvent e) {validar();}
			
			public void validar() {
				Perfil[] perfis = Perfil.values();
				int perfilIndex = comboBox.getSelectedIndex();
				boolean valido = false;
				
				if (perfilIndex > 0) {
					Perfil perfilSelecionado = perfis[perfilIndex - 1];
					
					if (perfilSelecionado == Perfil.PROFESSOR || perfilSelecionado == Perfil.TECNICO) {
						valido = campoMatricula.getText().matches("\\d{7,8}");
					} else if (perfilSelecionado == Perfil.ESTUDANTE_GUARDIAO) {
						valido = campoMatricula.getText().toUpperCase().matches("\\d{5}[A-Z]{5}\\d{4}");
					}
				}
				
			}
		});
		
		

		campoEmail = new JTextField();
		campoEmail.setFont(new Font("Calibri", Font.BOLD, 11));
		campoEmail.setBounds(400, 375, 474, 33);
		contentPane.add(campoEmail);
		campoEmail.setColumns(10);
		campoEmail.setBorder(BorderFactory.createLineBorder(Color.black,2));

		JLabel labelSenha = new JLabel("Senha");
		labelSenha.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelSenha.setBounds(328, 526, 58, 21);
		contentPane.add(labelSenha);
		
		JLabel labelNome = new JLabel("Nome");
		labelNome.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelNome.setBounds(328, 183, 69, 24);
		contentPane.add(labelNome);
		
		campoTelefone = new JFormattedTextField();
		campoTelefone.setFont(new Font("Calibri", Font.BOLD, 11));
		campoTelefone.setBounds(400, 445, 350, 33);
		contentPane.add(campoTelefone);
		campoTelefone.setColumns(10);
		campoTelefone.setBorder(BorderFactory.createLineBorder(Color.black,2));
		
		try {
			MaskFormatter mascaraTelefone = new MaskFormatter("(##) #####-####");
			mascaraTelefone.setPlaceholderCharacter('_');
			campoTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mascaraTelefone));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		JLabel labelConfirmarSenha = new JLabel("Confirmar Senha");
		labelConfirmarSenha.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelConfirmarSenha.setBounds(562, 519, 141, 34);
		contentPane.add(labelConfirmarSenha);

		JLabel labelEmail = new JLabel("Email");
		labelEmail.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelEmail.setBounds(328, 380, 58, 21);
		contentPane.add(labelEmail);

		JLabel labelTelefone = new JLabel("Telefone");
		labelTelefone.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelTelefone.setBounds(328, 448, 78, 24);
		contentPane.add(labelTelefone);
		
		comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Calibri", Font.BOLD, 13));
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"    Selecione...", "Professor", "Técnico", "Estudante_GUARDIAO"}));
		comboBox.setBounds(410, 114, 167, 28);
		comboBox.addItemListener(new java.awt.event.ItemListener() {
			public void itemStateChanged(java.awt.event.ItemEvent evt) {
				campoMatricula.setText(campoMatricula.getText());
			}
		});
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed (ActionEvent e) {
				String perfilSelecionado = (String) comboBox.getSelectedItem();
				if (perfilSelecionado.contains("Estudante_GUARDIAO")){
					checkBoxAdministrador.setSelected(false);
					checkBoxAdministrador.setEnabled(false);
				} else {
					checkBoxAdministrador.setEnabled(true);
				}
			}
		}); 		
		contentPane.add(comboBox);

		checkBoxAdministrador = new JCheckBox("Administrador");
		checkBoxAdministrador.setFont(new Font("Calibri", Font.PLAIN, 20));
		checkBoxAdministrador.setBackground(Color.WHITE);
		checkBoxAdministrador.setBounds(689, 110, 185, 34);
		contentPane.add(checkBoxAdministrador);

		JLabel lblNewLabel_3 = new JLabel("Perfil");
		lblNewLabel_3.setFont(new Font("Calibri", Font.PLAIN, 15));
		lblNewLabel_3.setBounds(328, 118, 53, 21);
		contentPane.add(lblNewLabel_3);

		campoSenha = new JPasswordField();
		campoSenha.setFont(new Font("Calibri", Font.BOLD, 11));
		campoSenha.setBounds(400, 521, 152, 33);
		contentPane.add(campoSenha);

		campoSenha.setBorder(BorderFactory.createLineBorder(Color.black,2));

		campoConfirmarSenha = new JPasswordField();
		campoConfirmarSenha.setFont(new Font("Calibri", Font.BOLD, 11));
		campoConfirmarSenha.setBounds(689, 521, 161, 33);
		contentPane.add(campoConfirmarSenha);

		campoConfirmarSenha.setBorder(BorderFactory.createLineBorder(Color.black,2));
		
		adicionarPlaceholder(campoEmail, "Digite seu email");
		adicionarPlaceholder(campoNome, "Digite seu nome");
		
		JLabel labelCpf = new JLabel("CPF");
		labelCpf.setFont(new Font("Calibri", Font.PLAIN, 18));
		labelCpf.setBounds(328, 312, 58, 26);
		contentPane.add(labelCpf);

		campoCpf = new JFormattedTextField();
		campoCpf.setFont(new Font("Calibri", Font.BOLD, 11));
		campoCpf.setColumns(10);
		campoCpf.setBounds(400, 310, 474, 33);
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
		
		JButton btnMostrarSenha = new JButton("");
		btnMostrarSenha.setIcon(new ImageIcon(getClass().getResource("/images/olho.png")));
		btnMostrarSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (senhaVisivel){
					campoSenha.setEchoChar('*');
					senhaVisivel = false;
					campoConfirmarSenha.setEchoChar('*');
					btnMostrarSenha.setIcon(new ImageIcon(getClass().getResource("/images/olho.png")));
				} else {
					campoSenha.setEchoChar('\0');
					senhaVisivel = true;
					campoConfirmarSenha.setEchoChar('\0');
					btnMostrarSenha.setIcon(new ImageIcon(getClass().getResource("/images/olhoClos.png")));
				}
			}});
		btnMostrarSenha.setBounds(860, 526, 21, 21);
		contentPane.add(btnMostrarSenha);
		
		JSeparator separator = new JSeparator();
		separator.setBackground(new Color(255, 255, 255));
		separator.setForeground(new Color(0, 0, 0));
		separator.setBounds(281, 162, 655, 2);
		contentPane.add(separator);
		
		JButton dicasSenha = new JButton("");
		dicasSenha.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "A Senha deve conter no mínimo 6 caracteres, incluindo pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial.");
			}
		});
		dicasSenha.setIcon(new ImageIcon(TelaCadastro.class.getResource("/images/dicaSenha.png")));
		dicasSenha.setBounds(892, 526, 21, 21);
		contentPane.add(dicasSenha);
		
		JButton dicasMatricula = new JButton("");
		dicasMatricula.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				Perfil[] perfis = Perfil.values();
				int perfilIndex = comboBox.getSelectedIndex();
				
				if (perfilIndex > 0) {
					Perfil perfilSelecionado = perfis[perfilIndex - 1];
				if(perfilSelecionado == Perfil.ESTUDANTE_GUARDIAO) {
				JOptionPane.showMessageDialog(null, "A matricula deve conter 5 numeros inicias seguidos por 5 letras(maisculas/minusculas) e por fim 4 numeros novamente.   Ex:20231adpls1234.")
				;}else {
					JOptionPane.showMessageDialog(null, "A matricula deve conter 8 numeros.   Ex:11023456.");
				}
				}else {
					JOptionPane.showMessageDialog(null, "Selecione um perfil.");

				}
			}
		});
		dicasMatricula.setIcon(new ImageIcon(TelaCadastro.class.getResource("/images/dicaSenha.png")));
		dicasMatricula.setBounds(884, 251, 21, 21);
		contentPane.add(dicasMatricula);
		
		
		JComboBox<String> tiposTelComboBox = new JComboBox<String>();
		tiposTelComboBox.setModel(new DefaultComboBoxModel(new String[] {"Celular", "Telefone"}));
		tiposTelComboBox.setFont(new Font("Calibri", Font.BOLD, 13));
		tiposTelComboBox.setBounds(760, 449, 114, 28);
		contentPane.add(tiposTelComboBox);

		tiposTelComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				campoTelefone.setValue(null);
				if (tiposTelComboBox.getSelectedItem().equals("Celular")) {
					try {
						MaskFormatter mascaraCelular = new MaskFormatter("(##) #####-####");
						mascaraCelular.setPlaceholderCharacter('_');
						campoTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mascaraCelular));
					} catch (ParseException ex) {
						ex.printStackTrace();
					}
				} else {
					try {
						MaskFormatter mascaraTelefone = new MaskFormatter("(##) ####-####");
						mascaraTelefone.setPlaceholderCharacter('_');
						campoTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mascaraTelefone));
					} catch (ParseException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
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
	
	public void destacarBorda(JTextField campo) {
		campo.setBorder(BorderFactory.createLineBorder(Color.red,3));
	}
	
	public void restaurarBorda(JTextField campo) {
		campo.setBorder(BorderFactory.createLineBorder(Color.black,2));
	}
}