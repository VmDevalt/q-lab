package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.Usuario;
import util.ConnectionFactory;

public class UsuarioDao {
	public boolean cadastrarUsuario(Usuario usuario) throws SQLException {
		String sql = "insert into usuario (nome,email,cpf,telefone,senha,perfil) values(?,?,?,?,?,?)";
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		
		ps.setString(1, usuario.getNome());
		ps.setString(2, usuario.getEmail());
		ps.setString(3, usuario.getCpf());
		ps.setString(4, usuario.getTelefone());
		ps.setString(5,usuario.getSenha());
		ps.setString(6, usuario.getPerfil().name());
		
		ps.executeUpdate();
		
		ConnectionFactory.closeConnection(conn);
		ps.close();
		return true;
	}
}
