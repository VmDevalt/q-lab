package controller;

import model.Usuario;
import repository.UsuarioRepository;

public class LoginController {

    private final UsuarioRepository repository = new UsuarioRepository();

    public Usuario logarUsuario(String matricula, String senha) throws Exception {
        return repository.autenticar(matricula, senha);
    }
}
