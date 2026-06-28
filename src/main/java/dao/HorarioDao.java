package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Horario;
import model.HorariosEnum;
import model.Laboratorio;
import util.ConnectionFactory;

public class HorarioDao {

    public List<Horario> findByDataELab(Date data, int laboratorioId) throws SQLException {
        String sql = """
            SELECT h.id_horario, h.dia, h.horario,
                   l.id_laboratorio, l.nome, l.descricao, l.andar, l.capacidade, l.ativo
            FROM horarios h
            JOIN laboratorios l ON h.laboratorio_id = l.id_laboratorio
            WHERE h.dia = ? AND h.laboratorio_id = ?
            ORDER BY h.horario
            """;
        List<Horario> horarios = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, data);
            ps.setInt(2, laboratorioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    horarios.add(mapRow(rs));
                }
            }
        }
        return horarios;
    }

    public int findOrCreate(Date dia, HorariosEnum horario, int laboratorioId) throws SQLException {
        String upsert = """
            INSERT INTO horarios (dia, horario, laboratorio_id) VALUES (?, ?, ?)
            ON DUPLICATE KEY UPDATE id_horario = LAST_INSERT_ID(id_horario)
            """;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(upsert, Statement.RETURN_GENERATED_KEYS)) {
            ps.setDate(1, dia);
            ps.setString(2, horario.name());
            ps.setInt(3, laboratorioId);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new SQLException("Falha ao criar/recuperar horario");
    }

    public Horario findById(int id) throws SQLException {
        String sql = """
            SELECT h.id_horario, h.dia, h.horario,
                   l.id_laboratorio, l.nome, l.descricao, l.andar, l.capacidade, l.ativo
            FROM horarios h
            JOIN laboratorios l ON h.laboratorio_id = l.id_laboratorio
            WHERE h.id_horario = ?
            """;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }

    private Horario mapRow(ResultSet rs) throws SQLException {
        Laboratorio lab = new Laboratorio(
            rs.getInt("id_laboratorio"),
            rs.getString("nome"),
            rs.getString("descricao"),
            rs.getString("andar"),
            rs.getInt("capacidade"),
            rs.getBoolean("ativo")
        );
        return new Horario(
            rs.getInt("id_horario"),
            rs.getDate("dia"),
            HorariosEnum.valueOf(rs.getString("horario")),
            lab
        );
    }
}
