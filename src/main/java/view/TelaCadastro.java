package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.*;
import java.text.ParseException;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.MaskFormatter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import controller.CadastroController;
import model.Perfil;
import model.Usuario;
import util.ImageUtil;
import util.ValidarSenhaUtil;

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
    private JCheckBox checkBoxAdministrador;
    private JCheckBox chkAtivo;
    private Boolean senhaVisivel = false;

    private Usuario usuarioEncontrado;
    private String matriculaOriginal; 

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                new TelaCadastro().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public TelaCadastro() {
        this(false);
    }
    

    /** Abre em modo edição com o usuário já carregado (vindo do popup de busca). */
    public TelaCadastro(Usuario usuario) {
        this(true);
        preencherCampos(usuario);
    }

    private TelaCadastro(boolean modoEdicao) {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/logo_qlab_pequena_branca.png")));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1280, 720);
        setLocationRelativeTo(null);
        contentPane = new JPanel();
        contentPane.setBackground(Color.WHITE);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        setTitle(modoEdicao ? "QLAB - Editar Usuário" : "QLAB - Cadastro de Usuário");
        setResizable(false);

        JLabel labelIcone = new JLabel("");
        ImageIcon imagemLogoQlab = new ImageIcon(getClass().getResource("/images/logo_qlab_media.png"));
        labelIcone.setIcon(ImageUtil.redimensionarImagem(imagemLogoQlab, 166, 70));
        labelIcone.setBounds(67, 21, 213, 107);
        contentPane.add(labelIcone);

        JLabel labelTitulo = new JLabel(modoEdicao ? "Editar Usuário" : "Cadastro");
        labelTitulo.setFont(new Font("Calibri", Font.PLAIN, 50));
        labelTitulo.setBounds(521, 41, 350, 62);
        contentPane.add(labelTitulo);

        JSeparator separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        separator.setBounds(281, 162, 655, 2);
        contentPane.add(separator);

        JLabel lblPerfil = new JLabel("Perfil");
        lblPerfil.setFont(new Font("Calibri", Font.PLAIN, 15));
        lblPerfil.setBounds(328, 118, 53, 21);
        contentPane.add(lblPerfil);

        comboBox = new JComboBox<>();
        comboBox.setFont(new Font("Calibri", Font.BOLD, 13));
        comboBox.setModel(new DefaultComboBoxModel<>(new String[]{"    Selecione...", "Professor", "Técnico", "Estudante_GUARDIAO"}));
        comboBox.setBounds(410, 114, 167, 28);
        comboBox.addItemListener(evt -> campoMatricula.setText(campoMatricula.getText()));
        comboBox.addActionListener(e -> {
            String selecionado = (String) comboBox.getSelectedItem();
            if (selecionado.contains("Estudante_GUARDIAO")) {
                checkBoxAdministrador.setSelected(false);
                checkBoxAdministrador.setEnabled(false);
            } else {
                checkBoxAdministrador.setEnabled(true);
            }
        });
        contentPane.add(comboBox);

        checkBoxAdministrador = new JCheckBox("Administrador");
        checkBoxAdministrador.setFont(new Font("Calibri", Font.PLAIN, 20));
        checkBoxAdministrador.setBackground(Color.WHITE);
        checkBoxAdministrador.setBounds(689, 110, 185, 34);
        contentPane.add(checkBoxAdministrador);

        JLabel labelNome = new JLabel("Nome");
        labelNome.setFont(new Font("Calibri", Font.PLAIN, 18));
        labelNome.setBounds(328, 183, 69, 24);
        contentPane.add(labelNome);

        campoNome = new JTextField();
        campoNome.setFont(new Font("Calibri", Font.BOLD, 11));
        campoNome.setBounds(400, 180, 474, 33);
        campoNome.setColumns(10);
        campoNome.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        contentPane.add(campoNome);
        adicionarPlaceholder(campoNome, "Digite seu nome");

        JLabel labelMatricula = new JLabel("Matrícula");
        labelMatricula.setFont(new Font("Calibri", Font.PLAIN, 18));
        labelMatricula.setBounds(328, 248, 100, 24);
        contentPane.add(labelMatricula);

        campoMatricula = new JTextField();
        campoMatricula.setFont(new Font("Calibri", Font.BOLD, 11));
        campoMatricula.setBounds(400, 245, 474, 33);
        campoMatricula.setColumns(10);
        campoMatricula.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        contentPane.add(campoMatricula);

        JButton dicasMatricula = new JButton("");
        dicasMatricula.addActionListener(e -> {
            int perfilIndex = comboBox.getSelectedIndex();
            if (perfilIndex > 0) {
                Perfil perfilSelecionado = Perfil.values()[perfilIndex - 1];
                if (perfilSelecionado == Perfil.ESTUDANTE_GUARDIAO) {
                    JOptionPane.showMessageDialog(null, "A matrícula deve conter 5 números seguidos por 5 letras e 4 números. Ex: 202XXADSPLXXXX.");
                } else {
                    JOptionPane.showMessageDialog(null, "A matrícula deve conter 8 números. Ex: 11023456.");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Selecione um perfil.");
            }
        });
        dicasMatricula.setIcon(new ImageIcon(TelaCadastro.class.getResource("/images/dicaSenha.png")));
        dicasMatricula.setBounds(884, 251, 21, 21);
        contentPane.add(dicasMatricula);

        campoMatricula.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { }
            public void removeUpdate(DocumentEvent e) { }
            public void changedUpdate(DocumentEvent e) { }
        });

        JLabel labelCpf = new JLabel("CPF");
        labelCpf.setFont(new Font("Calibri", Font.PLAIN, 18));
        labelCpf.setBounds(328, 312, 58, 26);
        contentPane.add(labelCpf);

        campoCpf = new JFormattedTextField();
        campoCpf.setFont(new Font("Calibri", Font.BOLD, 11));
        campoCpf.setColumns(10);
        campoCpf.setBounds(400, 310, 474, 33);
        campoCpf.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
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
        try {
            MaskFormatter mascaraCpf = new MaskFormatter("###.###.###-##");
            mascaraCpf.setPlaceholderCharacter('_');
            mascaraCpf.install(campoCpf);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        contentPane.add(campoCpf);

        JLabel labelEmail = new JLabel("Email");
        labelEmail.setFont(new Font("Calibri", Font.PLAIN, 18));
        labelEmail.setBounds(328, 380, 58, 21);
        contentPane.add(labelEmail);

        campoEmail = new JTextField();
        campoEmail.setFont(new Font("Calibri", Font.BOLD, 11));
        campoEmail.setBounds(400, 375, 474, 33);
        campoEmail.setColumns(10);
        campoEmail.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        contentPane.add(campoEmail);
        adicionarPlaceholder(campoEmail, "Digite seu email");

        JLabel labelTelefone = new JLabel("Telefone");
        labelTelefone.setFont(new Font("Calibri", Font.PLAIN, 18));
        labelTelefone.setBounds(328, 448, 78, 24);
        contentPane.add(labelTelefone);

        campoTelefone = new JFormattedTextField();
        campoTelefone.setFont(new Font("Calibri", Font.BOLD, 11));
        campoTelefone.setBounds(400, 445, 474, 33);
        campoTelefone.setColumns(10);
        campoTelefone.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        try {
            MaskFormatter mascaraTelefone = new MaskFormatter("(##) #####-####");
            mascaraTelefone.setPlaceholderCharacter('_');
            campoTelefone.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(mascaraTelefone));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        contentPane.add(campoTelefone);

        JLabel labelSenha = new JLabel("Senha");
        labelSenha.setFont(new Font("Calibri", Font.PLAIN, 18));
        labelSenha.setBounds(328, 526, 58, 21);
        labelSenha.setVisible(!modoEdicao);
        contentPane.add(labelSenha);

        campoSenha = new JPasswordField();
        campoSenha.setFont(new Font("Calibri", Font.BOLD, 11));
        campoSenha.setBounds(400, 521, 152, 33);
        campoSenha.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        campoSenha.setVisible(!modoEdicao);
        contentPane.add(campoSenha);

        JLabel labelConfirmarSenha = new JLabel("Confirmar Senha");
        labelConfirmarSenha.setFont(new Font("Calibri", Font.PLAIN, 18));
        labelConfirmarSenha.setBounds(562, 519, 141, 34);
        labelConfirmarSenha.setVisible(!modoEdicao);
        contentPane.add(labelConfirmarSenha);

        campoConfirmarSenha = new JPasswordField();
        campoConfirmarSenha.setFont(new Font("Calibri", Font.BOLD, 11));
        campoConfirmarSenha.setBounds(689, 521, 161, 33);
        campoConfirmarSenha.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        campoConfirmarSenha.setVisible(!modoEdicao);
        contentPane.add(campoConfirmarSenha);

        JButton btnMostrarSenha = new JButton("");
        btnMostrarSenha.setIcon(new ImageIcon(getClass().getResource("/images/olho.png")));
        btnMostrarSenha.addActionListener(e -> {
            if (senhaVisivel) {
                campoSenha.setEchoChar('*');
                campoConfirmarSenha.setEchoChar('*');
                senhaVisivel = false;
                btnMostrarSenha.setIcon(new ImageIcon(getClass().getResource("/images/olho.png")));
            } else {
                campoSenha.setEchoChar('\0');
                campoConfirmarSenha.setEchoChar('\0');
                senhaVisivel = true;
                btnMostrarSenha.setIcon(new ImageIcon(getClass().getResource("/images/olhoClos.png")));
            }
        });
        btnMostrarSenha.setBounds(860, 526, 21, 21);
        btnMostrarSenha.setVisible(!modoEdicao);
        contentPane.add(btnMostrarSenha);

        JButton dicasSenha = new JButton("");
        dicasSenha.addActionListener(e -> JOptionPane.showMessageDialog(null,
                "A Senha deve conter no mínimo 6 caracteres, incluindo pelo menos uma letra maiúscula, uma letra minúscula, um número e um caractere especial."));
        dicasSenha.setIcon(new ImageIcon(TelaCadastro.class.getResource("/images/dicaSenha.png")));
        dicasSenha.setBounds(892, 526, 21, 21);
        dicasSenha.setVisible(!modoEdicao);
        contentPane.add(dicasSenha);

        chkAtivo = new JCheckBox("Usuário ativo");
        chkAtivo.setFont(new Font("Calibri", Font.PLAIN, 16));
        chkAtivo.setBackground(Color.WHITE);
        chkAtivo.setBounds(400, 521, 160, 33);
        chkAtivo.setVisible(modoEdicao);
        contentPane.add(chkAtivo);

        JButton btnCadastrar = new JButton(modoEdicao ? "Salvar Alterações" : "Cadastrar");
        btnCadastrar.setFont(new Font("Calibri", Font.PLAIN, 13));
        btnCadastrar.setForeground(Color.WHITE);
        btnCadastrar.setBackground(new Color(34, 139, 34));
        btnCadastrar.setBounds(562, 610, 152, 50);
        btnCadastrar.setFocusable(false);
        btnCadastrar.addActionListener(e -> {
            if (modoEdicao) salvarEdicao();
            else realizarCadastro();
        });
        contentPane.add(btnCadastrar);

        if (modoEdicao) {
            habilitarCamposEdicao(false);
        }
    }

    private void preencherCampos(Usuario u) {
        usuarioEncontrado = u;

        campoNome.setText(u.getNome());
        campoEmail.setText(u.getEmail());
        campoMatricula.setText(u.getMatricula());
        campoCpf.setText(u.getCpf() != null ? u.getCpf() : "");
        campoTelefone.setText(u.getTelefone() != null ? u.getTelefone() : "");
        this.matriculaOriginal = u.getMatricula();
        
        switch (u.getPerfil()) {
            case PROFESSOR: comboBox.setSelectedIndex(1); break;
            case TECNICO: comboBox.setSelectedIndex(2); break;
            case ESTUDANTE_GUARDIAO: comboBox.setSelectedIndex(3); break;
        }
        checkBoxAdministrador.setSelected(u.isAdministrador());
        chkAtivo.setSelected(u.isAtivo());

        habilitarCamposEdicao(true);
    }

    private void habilitarCamposEdicao(boolean habilitar) {
        campoNome.setEnabled(habilitar);
        campoEmail.setEnabled(habilitar);
        campoTelefone.setEnabled(habilitar);
        comboBox.setEnabled(habilitar);
        checkBoxAdministrador.setEnabled(habilitar);
        chkAtivo.setEnabled(habilitar);
        campoCpf.setEnabled(habilitar);
        campoMatricula.setEnabled(habilitar);
    }

    private void salvarEdicao() {
        if (usuarioEncontrado == null) {
            JOptionPane.showMessageDialog(this, "Nenhum usuário carregado.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nome = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String telefone = campoTelefone.getText();
        String matricula = campoMatricula.getText();
        String cpf = campoCpf.getText();

        if (nome.isEmpty() || nome.equals("Digite seu nome")) {
            destacarBorda(campoNome);
            JOptionPane.showMessageDialog(null, "O campo Nome não pode estar vazio!");
            return;
        } else {
            restaurarBorda(campoNome);
        }

        if (email.isEmpty() || email.equals("Digite seu email") ||
                !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            destacarBorda(campoEmail);
            JOptionPane.showMessageDialog(null, "Informe um email válido!");
            return;
        } else {
            restaurarBorda(campoEmail);
        }

        if (comboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Selecione um perfil!");
            return;
        }

        Perfil perfilSelecionado = Perfil.values()[comboBox.getSelectedIndex() - 1];
        boolean administrador = (perfilSelecionado == Perfil.PROFESSOR || perfilSelecionado == Perfil.TECNICO)
                && checkBoxAdministrador.isSelected();

        if (perfilSelecionado == Perfil.PROFESSOR || perfilSelecionado == Perfil.TECNICO) {
            if (!campoMatricula.getText().matches("\\d{7,8}")) {
                destacarBorda(campoMatricula);
                JOptionPane.showMessageDialog(null, "Matrícula inválida! Use 7 ou 8 dígitos numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            } else { restaurarBorda(campoMatricula); }
        } else if (perfilSelecionado == Perfil.ESTUDANTE_GUARDIAO) {
            if (!campoMatricula.getText().toUpperCase().matches("\\d{5}[A-Z]{5}\\d{4}")) {
                destacarBorda(campoMatricula);
                JOptionPane.showMessageDialog(null, "Matrícula inválida! Use o formato: 202XXADSPLXXXX", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            } else { restaurarBorda(campoMatricula); }
        }
        
        if (cpf.contains("_") || cpf.replace(".", "").replace("-", "").isBlank()) {
            destacarBorda(campoCpf);
            JOptionPane.showMessageDialog(null, "CPF inválido ou incompleto!");
            return;
        } else { restaurarBorda(campoCpf); }

        if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            destacarBorda(campoCpf);
            JOptionPane.showMessageDialog(null, "CPF deve estar no formato 000.000.000-00!");
            return;
        } else { restaurarBorda(campoCpf); }

        
        CadastroController controller = new CadastroController();
        int resultado = controller.editarUsuario(nome, email, matricula, cpf, telefone, perfilSelecionado, administrador, matriculaOriginal);

        if (resultado != 1) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean novoStatus = chkAtivo.isSelected();
        if (novoStatus != usuarioEncontrado.isAtivo()) {
            boolean ok = novoStatus
                    ? controller.reativarUsuario(usuarioEncontrado.getMatricula())
                    : controller.desativarUsuario(usuarioEncontrado.getMatricula());
            if (!ok) {
                JOptionPane.showMessageDialog(this, "Dados salvos, mas erro ao atualizar status.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }
            usuarioEncontrado.setAtivo(novoStatus);
        }

        JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!");
    }

    private void realizarCadastro() {
        String nome = campoNome.getText().trim();
        String matricula = campoMatricula.getText().trim().toUpperCase();
        String email = campoEmail.getText().trim();
        String cpf = campoCpf.getText();
        String telefone = campoTelefone.getText();
        char[] senha = campoSenha.getPassword();
        char[] confirmarSenha = campoConfirmarSenha.getPassword();
        String senhaString = new String(senha);
        String requisitosSenha = ValidarSenhaUtil.validarSenha(senhaString);

        if (nome.isEmpty() || nome.equals("Digite seu nome")) {
            destacarBorda(campoNome);
            JOptionPane.showMessageDialog(null, "O campo Nome não pode estar vazio!");
            return;
        } else { restaurarBorda(campoNome); }

        if (matricula.isEmpty()) {
            destacarBorda(campoMatricula);
            JOptionPane.showMessageDialog(null, "O campo Matrícula não pode estar vazio!");
            return;
        } else { restaurarBorda(campoMatricula); }

        if (comboBox.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Selecione um perfil!");
            return;
        }

        Perfil perfilSelecionado = Perfil.values()[comboBox.getSelectedIndex() - 1];

        if (perfilSelecionado == Perfil.PROFESSOR || perfilSelecionado == Perfil.TECNICO) {
            if (!campoMatricula.getText().matches("\\d{7,8}")) {
                destacarBorda(campoMatricula);
                JOptionPane.showMessageDialog(null, "Matrícula inválida! Use 7 ou 8 dígitos numéricos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            } else { restaurarBorda(campoMatricula); }
        } else if (perfilSelecionado == Perfil.ESTUDANTE_GUARDIAO) {
            if (!campoMatricula.getText().toUpperCase().matches("\\d{5}[A-Z]{5}\\d{4}")) {
                destacarBorda(campoMatricula);
                JOptionPane.showMessageDialog(null, "Matrícula inválida! Use o formato: 202XXADSPLXXXX", "Erro", JOptionPane.ERROR_MESSAGE);
                return;
            } else { restaurarBorda(campoMatricula); }
        }

        if (email.isEmpty() || email.equals("Digite seu email")) {
            destacarBorda(campoEmail);
            JOptionPane.showMessageDialog(null, "O campo Email não pode estar vazio!");
            return;
        } else { restaurarBorda(campoEmail); }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
            destacarBorda(campoEmail);
            JOptionPane.showMessageDialog(null, "Email inválido! Use o formato usuario@email.com");
            return;
        } else { restaurarBorda(campoEmail); }

        if (cpf.contains("_") || cpf.replace(".", "").replace("-", "").isBlank()) {
            destacarBorda(campoCpf);
            JOptionPane.showMessageDialog(null, "CPF inválido ou incompleto!");
            return;
        } else { restaurarBorda(campoCpf); }

        if (!cpf.matches("\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}")) {
            destacarBorda(campoCpf);
            JOptionPane.showMessageDialog(null, "CPF deve estar no formato 000.000.000-00!");
            return;
        } else { restaurarBorda(campoCpf); }

        if (telefone.contains("_") || telefone.replace("(", "").replace(")", "").replace(" ", "").replace("-", "").isBlank()) {
            destacarBorda(campoTelefone);
            JOptionPane.showMessageDialog(null, "Telefone inválido ou incompleto!");
            return;
        } else { restaurarBorda(campoTelefone); }

        if (senha.length == 0) {
            destacarBorda(campoSenha);
            JOptionPane.showMessageDialog(null, "O campo Senha não pode estar vazio!");
            return;
        } else { restaurarBorda(campoSenha); }

        if (!requisitosSenha.equals("")) {
            destacarBorda(campoSenha);
            JOptionPane.showMessageDialog(null, requisitosSenha);
            return;
        } else { restaurarBorda(campoSenha); }

        if (!Arrays.equals(senha, confirmarSenha)) {
            destacarBorda(campoSenha);
            destacarBorda(campoConfirmarSenha);
            JOptionPane.showMessageDialog(null, "As senhas não coincidem!");
            return;
        } else {
            restaurarBorda(campoSenha);
            restaurarBorda(campoConfirmarSenha);
        }

        boolean administrador = (perfilSelecionado == Perfil.PROFESSOR || perfilSelecionado == Perfil.TECNICO)
                && checkBoxAdministrador.isSelected();

        String hashSenha = BCrypt.with(BCrypt.Version.VERSION_2A).hashToString(12, senha);
        Arrays.fill(senha, '\0');
        Arrays.fill(confirmarSenha, '\0');

        CadastroController controller = new CadastroController();
        int sucesso = controller.CadastrarUsuario(nome, email, matricula, cpf, telefone, hashSenha, perfilSelecionado, administrador);

        if (sucesso == 1) {
            JOptionPane.showMessageDialog(TelaCadastro.this, "Cadastro realizado com sucesso!");
            dispose();
        } else if (sucesso == 2) {
            JOptionPane.showMessageDialog(TelaCadastro.this, "O usuário já está cadastrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(TelaCadastro.this, "Erro ao realizar cadastro.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
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
        campo.setBorder(BorderFactory.createLineBorder(Color.RED, 3));
    }

    public void restaurarBorda(JTextField campo) {
        campo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
    }
}
