package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Interdicao;
import model.Laboratorio;
import util.ConnectionFactory;

public class InterdicaoDao {

    public int interditar(int laboratorioId, Date dataInicio, Date dataFim, String motivo) throws SQLException {
        String sql = "INSERT INTO interdicoes (laboratorio_id, data_inicio, data_fim, motivo, ativo) VALUES (?, ?, ?, ?, TRUE)";
        String sqlLaboratorio = "UPDATE laboratorios SET ativo = FALSE WHERE id_laboratorio = ?";
        int idGerado;


        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, laboratorioId);
            ps.setDate(2, dataInicio);
            ps.setDate(3, dataFim);
            ps.setString(4, motivo);
            ps.executeUpdate();
	        try (ResultSet rs = ps.getGeneratedKeys()) {
	            if (rs.next()) {
	                idGerado = rs.getInt(1);
	            } else {
	                throw new SQLException("Falha ao salvar interdição");
	            }
	        }

	        try (PreparedStatement psLab = conn.prepareStatement(sqlLaboratorio)) {
	            psLab.setInt(1, laboratorioId);
	            psLab.executeUpdate();
	        }

	        return idGerado;
	    }
    }

    public boolean isInterditado(int laboratorioId, Date data) throws SQLException {
        String sql = """
            SELECT 1 FROM interdicoes
            WHERE laboratorio_id = ? AND ativo = TRUE
              AND data_inicio <= ? AND data_fim >= ?
            LIMIT 1
            """;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, laboratorioId);
            ps.setDate(2, data);
            ps.setDate(3, data);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public List<Interdicao> findAtivasByLaboratorio(int laboratorioId) throws SQLException {
        String sql = """
            SELECT i.*, l.id_laboratorio, l.nome, l.descricao, l.andar, l.capacidade, l.ativo AS lab_ativo
            FROM interdicoes i
            JOIN laboratorios l ON i.laboratorio_id = l.id_laboratorio
            WHERE i.laboratorio_id = ? AND i.ativo = TRUE
            ORDER BY i.data_inicio
            """;
        List<Interdicao> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, laboratorioId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Laboratorio lab = new Laboratorio(
                        rs.getInt("id_laboratorio"),
                        rs.getString("nome"),
                        rs.getString("descricao"),
                        rs.getString("andar"),
                        rs.getInt("capacidade"),
                        rs.getBoolean("lab_ativo")
                    );
                    lista.add(new Interdicao(
                        rs.getInt("id_interdicao"),
                        lab,
                        rs.getDate("data_inicio"),
                        rs.getDate("data_fim"),
                        rs.getString("motivo"),
                        rs.getBoolean("ativo")
                    ));
                }
            }
        }
        return lista;
    }

    public void reativar(int laboratorioId, int idInterdicao) throws SQLException {
        String sqlInterdicao = "UPDATE interdicoes SET ativo = FALSE WHERE id_interdicao = ?";
        String sqlLaboratorio = "UPDATE laboratorios SET ativo = TRUE WHERE id_laboratorio = ?";

        try (Connection conn = ConnectionFactory.getConnection()) {

            try (PreparedStatement psInterdicao = conn.prepareStatement(sqlInterdicao)) {
                psInterdicao.setInt(1, idInterdicao);
                psInterdicao.executeUpdate();
            }

            try (PreparedStatement psLab = conn.prepareStatement(sqlLaboratorio)) {
                psLab.setInt(1, laboratorioId);
                psLab.executeUpdate();
            }
        }
    }
}
