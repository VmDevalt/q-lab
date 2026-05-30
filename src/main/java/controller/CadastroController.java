package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.UsuarioDao;
import model.Perfil;
import model.Usuario;
import util.ConnectionFactory;

public class CadastroController {

	public boolean CadastrarUsuario(String nome, String email, String cpf,  String telefone, String hashSenha, Perfil perfil)
	{
		Usuario usuario = new Usuario(nome, email, hashSenha, cpf, telefone, perfil);
		try {
			UsuarioDao dao = new UsuarioDao();
			return dao.cadastrarUsuario(usuario); 
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
