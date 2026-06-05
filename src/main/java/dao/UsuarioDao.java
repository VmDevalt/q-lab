package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Usuario;
import util.ConnectionFactory;

public class UsuarioDao {
	public boolean cadastrarUsuario(Usuario usuario) throws SQLException {
		String sql = "insert into usuarios (nome,email,cpf,telefone,senha,perfil,administrador) values(?,?,?,?,?,?,?)";
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);

		ps.setString(1, usuario.getNome());
		ps.setString(2, usuario.getEmail());
		ps.setString(3, usuario.getCpf());
		ps.setString(4, usuario.getTelefone());
		ps.setString(5, usuario.getSenha());
		ps.setString(6, usuario.getPerfil().name());
		ps.setBoolean(7, usuario.isAdministrador());
		
		ps.executeUpdate();
		
		ConnectionFactory.closeConnection(conn);
		ps.close();
		return true;
	}

    public Usuario login(String cpf, String senha) {
        String sql = "SELECT * FROM usuarios WHERE cpf = ? AND senha = ?";
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, cpf);
			ps.setString(2, senha);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return new Usuario(
					rs.getString("nome"),
					rs.getString("email"),
					rs.getString("senha"),
					rs.getString("cpf"),
					rs.getString("telefone"),
					model.Perfil.valueOf(rs.getString("perfil")),
					rs.getBoolean("administrador")
				);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
}
