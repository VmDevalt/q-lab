package repository;

import java.sql.SQLException;

import at.favre.lib.crypto.bcrypt.BCrypt;
import dao.UsuarioDao;
import model.Usuario;

public class UsuarioRepository {

    private final UsuarioDao dao = new UsuarioDao();

    public Usuario autenticar(String matricula, String senha) throws Exception {
        Usuario usuario = dao.findByMatricula(matricula);
        if (usuario == null || !verificarSenha(senha, usuario.getSenha())) {
            throw new Exception("Matrícula ou senha incorretos.");
        }
        if (!usuario.isAtivo()) {
            throw new Exception("Usuário desativado. Contate o administrador.");
        }
        return usuario;
    }

    public Usuario buscar(String matricula) throws SQLException {
        return dao.findByMatricula(matricula);
    }

    public boolean cadastrar(Usuario usuario) throws SQLException {
        return dao.save(usuario);
    }

    public boolean atualizar(Usuario usuario) throws SQLException {
        return dao.update(usuario);
    }

    public boolean desativar(String matricula) throws SQLException {
        return dao.updateStatus(matricula, false);
    }

    public boolean reativar(String matricula) throws SQLException {
        return dao.updateStatus(matricula, true);
    }

    private boolean verificarSenha(String senhaDigitada, String senhaBanco) {
        if (senhaBanco != null && senhaBanco.startsWith("$2")) {
            return BCrypt.verifyer().verify(senhaDigitada.toCharArray(), senhaBanco).verified;
        }
        return senhaDigitada.equals(senhaBanco);
    }
}
