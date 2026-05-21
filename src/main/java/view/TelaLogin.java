package view;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class TelaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(Color.gray);
		setContentPane(contentPane);

		
	    JPanel painelPrincipal = new JPanel();
	    painelPrincipal.setBounds(150, 33, 700, 500);
	    contentPane.add(painelPrincipal);
	    painelPrincipal.setBackground(Color.white);
	    
	    JSeparator linha = new JSeparator(SwingConstants.VERTICAL);
	    linha.setBounds(350, 20, 2, 460);
	    painelPrincipal.setLayout(null);
	    painelPrincipal.add(linha);
	    linha.setForeground(Color.black);
	    
	    JLabel textoLogin = new JLabel("LOGIN");
	    painelPrincipal.add(textoLogin);
	    
	}

}
