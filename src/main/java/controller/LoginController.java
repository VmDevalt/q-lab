package controller;

import dao.UsuarioDao;
import model.Usuario;

public class LoginController {
	
	public Usuario  logarUsuario(String cpf, String hashSenha) throws Exception{
		
			UsuarioDao dao = new UsuarioDao();
            Usuario usuario = dao.login(cpf, hashSenha);
            
           if (usuario == null) {
                 throw new Exception("CPF ou senha incorretos.");
           }
		return usuario;
	}
}
