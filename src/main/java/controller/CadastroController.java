package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.UsuarioDao;
import model.Perfil;
import model.Usuario;
import util.ConnectionFactory;

public class CadastroController {

	public int CadastrarUsuario(String nome, String email, String matricula, String cpf, String telefone, String hashSenha, Perfil perfil, boolean administrador)
	{
		Usuario usuario = new Usuario(nome, email,  hashSenha, matricula, cpf, telefone, perfil, administrador);
		try {
			UsuarioDao dao = new UsuarioDao();
			return dao.cadastrarUsuario(usuario) ? 1 : 0; 
			
		} catch (SQLException e) {
		    if (e.getErrorCode() == 1062) {
		    	return 2;
		    } else {
		    	return 3;
		    }
		} catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
