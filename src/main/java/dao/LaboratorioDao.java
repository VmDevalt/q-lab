package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Laboratorio;
import util.ConnectionFactory;

public class LaboratorioDao {

    public List<Laboratorio> findAll() throws SQLException {
        String sql = "SELECT * FROM laboratorios WHERE ativo = TRUE ORDER BY nome";
        List<Laboratorio> labs = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                labs.add(mapRow(rs));
            }
        }
        return labs;
    }

    public Laboratorio findById(int id) throws SQLException {
        String sql = "SELECT * FROM laboratorios WHERE id_laboratorio = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    private Laboratorio mapRow(ResultSet rs) throws SQLException {
        return new Laboratorio(
            rs.getInt("id_laboratorio"),
            rs.getString("nome"),
            rs.getString("descricao"),
            rs.getString("andar"),
            rs.getInt("capacidade"),
            rs.getBoolean("ativo")
        );
    }
}
