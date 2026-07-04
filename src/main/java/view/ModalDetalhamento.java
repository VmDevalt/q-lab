package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JSeparator;

import model.HorariosEnum;
import model.Laboratorio;
import model.Reserva;
import model.Usuario;

public class ModalDetalhamento extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final Color VERDE_ESCURO = new Color(46, 125, 50);
	private static final Color VERDE_CLARO  = new Color(232, 245, 233);
	private static final Color BRANCO       = Color.WHITE;
	private static final Color CINZA_TEXTO  = new Color(60, 60, 60);
	private static final Color CINZA_ROTULO = new Color(110, 110, 110);

	public ModalDetalhamento(Frame owner, Reserva reserva, Laboratorio lab,
			List<HorariosEnum> slots, Date data, Usuario usuario,
			boolean podeEditar, Runnable onEditar) {
		super(owner, "Detalhes da Reserva", true);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		int extraHeight = Math.max(0, slots.size() - 1) * 36;
		setBounds(100, 100, 417, 517 + extraHeight);
		setResizable(false);

		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(BRANCO);
		setContentPane(contentPane);

		JPanel panelInfo = new JPanel();
		panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
		panelInfo.setBackground(VERDE_CLARO);
		panelInfo.setBorder(new EmptyBorder(15, 20, 15, 20));

		JLabel lblTitulo = new JLabel("Reserva");
		lblTitulo.setFont(new Font("Arial", Font.PLAIN, 12));
		lblTitulo.setForeground(CINZA_ROTULO);
		panelInfo.add(lblTitulo);

		String ano = new SimpleDateFormat("yyyy").format(data);
		JLabel lblId = new JLabel(String.format("#ADS-%s-%04d", ano, reserva.getIdReserva()));
		lblId.setFont(new Font("Arial", Font.BOLD, 18));
		lblId.setForeground(VERDE_ESCURO);
		panelInfo.add(lblId);

		JLabel lblStatus = new JLabel("  Confirmada  ");
		lblStatus.setOpaque(true);
		lblStatus.setBackground(new Color(200, 230, 201));
		lblStatus.setForeground(new Color(27, 94, 32));
		lblStatus.setFont(new Font("Arial", Font.BOLD, 12));
		lblStatus.setBorder(new EmptyBorder(5, 12, 5, 12));

		JPanel panelTopo = new JPanel(new BorderLayout());
		panelTopo.setBackground(VERDE_CLARO);
		panelTopo.add(panelInfo, BorderLayout.WEST);
		panelTopo.add(lblStatus, BorderLayout.EAST);

		JSeparator separador = new JSeparator();
		separador.setForeground(new Color(225, 225, 225));

		int extraRows = Math.max(0, slots.size() - 1);
		JPanel panelCampos = new JPanel(new GridLayout(8 + extraRows, 2, 10, 16));
		panelCampos.setBackground(BRANCO);
		panelCampos.setBorder(new EmptyBorder(20, 20, 10, 20));

		String cpf     = usuario != null && usuario.getCpf() != null ? usuario.getCpf() : "—";
		String nome    = usuario != null ? usuario.getNome() : reserva.getMatricula();
		String dataFmt = new SimpleDateFormat("dd/MM/yyyy").format(data);
		String labNome = lab.getNome() + " – " + lab.getDescricao();

		addCampo(panelCampos, "Laboratório:",   labNome);
		addCampo(panelCampos, "Reservado por:", nome);
		addCampo(panelCampos, "CPF:",           cpf);
		addCampo(panelCampos, "Matrícula:",     reserva.getMatricula());
		addCampo(panelCampos, "Matéria:",       reserva.getDisciplina());
		addCampo(panelCampos, "Data:",          dataFmt);
		String labelHorario = slots.size() > 1 ? "Horários:" : "Horário:";
		addCampo(panelCampos, labelHorario,
				slots.get(0).name() + "   " + slots.get(0).getHi() + " – " + slots.get(0).getHf());
		for (int i = 1; i < slots.size(); i++) {
			HorariosEnum s = slots.get(i);
			addCampo(panelCampos, "", s.name() + "   " + s.getHi() + " – " + s.getHf());
		}
		addCampo(panelCampos, "Aprovado por:", "—");

		JPanel panelCentral = new JPanel(new BorderLayout());
		panelCentral.setBackground(BRANCO);
		panelCentral.add(separador, BorderLayout.NORTH);
		panelCentral.add(panelCampos, BorderLayout.CENTER);

		JPanel panelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		panelBotoes.setBackground(BRANCO);
		panelBotoes.setBorder(new EmptyBorder(10, 20, 15, 20));

		if (podeEditar) {
			JButton btnEditar = new JButton("Editar");
			btnEditar.setFont(new Font("Arial", Font.BOLD, 13));
			btnEditar.setBackground(new Color(13, 71, 161));
			btnEditar.setForeground(BRANCO);
			btnEditar.setFocusPainted(false);
			btnEditar.setBorderPainted(false);
			btnEditar.setOpaque(true);
			btnEditar.addActionListener(e -> {
				dispose();
				if (onEditar != null)
					javax.swing.SwingUtilities.invokeLater(onEditar);
			});
			panelBotoes.add(btnEditar);
		}

		JButton btnFechar = new JButton("Fechar");
		btnFechar.setFont(new Font("Arial", Font.BOLD, 13));
		btnFechar.setBackground(VERDE_ESCURO);
		btnFechar.setForeground(BRANCO);
		btnFechar.setFocusPainted(false);
		btnFechar.setBorderPainted(false);
		btnFechar.setOpaque(true);
		btnFechar.addActionListener(e -> dispose());
		panelBotoes.add(btnFechar);

		contentPane.setLayout(new BorderLayout());
		contentPane.add(panelTopo, BorderLayout.NORTH);
		contentPane.add(panelCentral, BorderLayout.CENTER);
		contentPane.add(panelBotoes, BorderLayout.SOUTH);

		setLocationRelativeTo(owner);
	}

	private void addCampo(JPanel panel, String rotulo, String valor) {
		JLabel lblRotulo = new JLabel(rotulo);
		lblRotulo.setFont(new Font("Arial", Font.BOLD, 13));
		lblRotulo.setForeground(CINZA_ROTULO);

		JLabel lblValor = new JLabel(valor);
		lblValor.setFont(new Font("Arial", Font.PLAIN, 13));
		lblValor.setForeground(CINZA_TEXTO);

		panel.add(lblRotulo);
		panel.add(lblValor);
	}
}
