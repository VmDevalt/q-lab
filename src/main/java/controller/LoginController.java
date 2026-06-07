package controller;

import dao.UsuarioDao;
import model.Usuario;

public class LoginController {
	
	public Usuario  logarUsuario(String matricula, String hashSenha) throws Exception{
		
			UsuarioDao dao = new UsuarioDao();
            Usuario usuario = dao.login(matricula, hashSenha);
            
           if (usuario == null) {
                 throw new Exception("Matrícula ou senha incorretos.");
           }
		return usuario;
	}
}
