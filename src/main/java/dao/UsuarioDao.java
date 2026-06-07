package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import at.favre.lib.crypto.bcrypt.BCrypt;
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

	public Usuario login(String cpf, String senha) throws SQLException {
		String sql = "SELECT * FROM usuarios WHERE cpf = ?";
		try (Connection conn = ConnectionFactory.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setString(1, cpf);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				String senhaBanco = rs.getString("senha").trim();
				if (verificarSenha(senha, senhaBanco)) {
					return new Usuario(
						rs.getString("nome"),
						rs.getString("email"),
						senhaBanco,
						rs.getString("cpf"),
						rs.getString("telefone"),
						model.Perfil.valueOf(rs.getString("perfil")),
						rs.getBoolean("administrador")
					);
				}
			}
		}
		return null;
	}

	private boolean verificarSenha(String senhaDigitada, String senhaBanco) {
		if (senhaBanco != null && senhaBanco.startsWith("$2")) {
			return BCrypt.verifyer().verify(senhaDigitada.toCharArray(), senhaBanco).verified;
		}
		return senhaDigitada.equals(senhaBanco);
	}
}
