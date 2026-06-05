package controller;

import dao.UsuarioDao;
import model.Usuario;

public class LoginController {
	
	public Usuario  logarUsuario(String cpf, String hashSenha) throws Exception{
		
			UsuarioDao dao = new UsuarioDao();
            Usuario usuario = dao.buscarPorCpf(cpf);
            
           if (usuario == null) {
                 throw new Exception("User not found.");
           }
		 if (!usuario.getSenha().equals(hashSenha)) {
			throw new Exception("Senha incorreta.");
        
		}
        
		
		return usuario;
		
	}
}
