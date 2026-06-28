package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Perfil;
import model.Usuario;
import util.ConnectionFactory;

public class UsuarioDao {

    public Usuario findByMatricula(String matricula) throws SQLException {
        String sql = "SELECT * FROM usuarios WHERE matricula = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, matricula);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Usuario(
                    rs.getString("nome"),
                    rs.getString("email"),
                    rs.getString("senha"),
                    rs.getString("matricula"),
                    rs.getString("cpf"),
                    rs.getString("telefone"),
                    Perfil.valueOf(rs.getString("perfil")),
                    rs.getBoolean("administrador"),
                    rs.getBoolean("ativo")
                );
            }
        }
        return null;
    }

    public boolean save(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (nome, email, cpf, telefone, senha, perfil, administrador, matricula) VALUES (?,?,?,?,?,?,?,?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getCpf());
            ps.setString(4, usuario.getTelefone());
            ps.setString(5, usuario.getSenha());
            ps.setString(6, usuario.getPerfil().name());
            ps.setBoolean(7, usuario.isAdministrador());
            ps.setString(8, usuario.getMatricula());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean update(Usuario usuario) throws SQLException {
        String sql = "UPDATE usuarios SET nome=?, email=?, telefone=?, perfil=?, administrador=? WHERE matricula=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, usuario.getNome());
            ps.setString(2, usuario.getEmail());
            ps.setString(3, usuario.getTelefone());
            ps.setString(4, usuario.getPerfil().name());
            ps.setBoolean(5, usuario.isAdministrador());
            ps.setString(6, usuario.getMatricula());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean updateStatus(String matricula, boolean ativo) throws SQLException {
        String sql = "UPDATE usuarios SET ativo=? WHERE matricula=?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setBoolean(1, ativo);
            ps.setString(2, matricula);
            ps.executeUpdate();
        }
        return true;
    }
}
