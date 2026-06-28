package controller;

import java.sql.SQLException;

import model.Perfil;
import model.Usuario;
import repository.UsuarioRepository;

public class CadastroController {

    private final UsuarioRepository repository = new UsuarioRepository();

    public int CadastrarUsuario(String nome, String email, String matricula, String cpf,
            String telefone, String hashSenha, Perfil perfil, boolean administrador) {
        Usuario usuario = new Usuario(nome, email, hashSenha, matricula, cpf, telefone, perfil, administrador);
        try {
            return repository.cadastrar(usuario) ? 1 : 0;
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                return 2;
            }
            return 3;
        }
    }

    public Usuario buscarUsuario(String matricula) {
        try {
            return repository.buscar(matricula);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int editarUsuario(String nome, String email, String matricula,
            String telefone, Perfil perfil, boolean administrador) {
        Usuario usuario = new Usuario(nome, email, "", matricula, "", telefone, perfil, administrador);
        try {
            return repository.atualizar(usuario) ? 1 : 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 3;
        }
    }

    public boolean desativarUsuario(String matricula) {
        try {
            return repository.desativar(matricula);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean reativarUsuario(String matricula) {
        try {
            return repository.reativar(matricula);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
