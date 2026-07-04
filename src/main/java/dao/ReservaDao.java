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
import model.Reserva;
import model.StatusReserva;
import util.ConnectionFactory;

public class ReservaDao {

    public int save(int horarioId, String matricula, String disciplina) throws SQLException {
        String sql = "INSERT INTO reservas (horario_id, matricula, disciplina, status) VALUES (?, ?, ?, 'AGENDADA')";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, horarioId);
            ps.setString(2, matricula);
            ps.setString(3, disciplina);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new SQLException("Falha ao salvar reserva");
    }
    
    public int saveSolicitacao(int horarioId, String matricula, String disciplina, String responsavelMatricula) throws SQLException {
        String sql = "INSERT INTO reservas (horario_id, matricula, disciplina, status, responsavel_matricula) VALUES (?, ?, ?, 'SOLICITADA', ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, horarioId);
            ps.setString(2, matricula);
            ps.setString(3, disciplina);
            ps.setString(4, responsavelMatricula);
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) return rs.getInt(1);
            }
        }
        throw new SQLException("Falha ao salvar reserva");
    }

    public Reserva findAgendadaByHorario(int horarioId) throws SQLException {
        String sql = buildJoinQuery("r.horario_id = ? AND r.status = 'AGENDADA'");
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, horarioId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapRow(rs);
            }
        }
        return null;
    }
    
    public List<Reserva> findSolicitacoesByResponsavel(String responsavelMatricula) throws SQLException {
        String sql = buildJoinQuery("r.responsavel_matricula = ? AND r.status = 'SOLICITADA'");
        List<Reserva> listaSolicitacoes = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, responsavelMatricula);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) listaSolicitacoes.add(mapRow(rs));
            }
        }
        return listaSolicitacoes;
    }

    public List<Reserva> findByMatricula(String matricula) throws SQLException {
        String sql = buildJoinQuery("r.matricula = ? AND r.status = 'AGENDADA' ORDER BY h.dia, h.horario");
        List<Reserva> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapRow(rs));
            }
        }
        return lista;
    }

    public List<Reserva> findByLaboratorioEData(int laboratorioId, Date data) throws SQLException {
        String sql = buildJoinQuery("h.laboratorio_id = ? AND h.dia = ? AND r.status = 'AGENDADA'");
        List<Reserva> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, laboratorioId);
            ps.setDate(2, data);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapRow(rs));
            }
        }
        return lista;
    }

    public List<Reserva> findHistoricoByMatricula(String matricula) throws SQLException {
        String sql = buildJoinQuery("r.matricula = ? ORDER BY h.dia DESC, h.horario");
        List<Reserva> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapRow(rs));
            }
        }
        return lista;
    }

    public void atualizar(int idReserva, String novaDisciplina) throws SQLException {
        String sql = "UPDATE reservas SET disciplina = ? WHERE id_reserva = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, novaDisciplina);
            ps.setInt(2, idReserva);
            ps.executeUpdate();
        }
    }

    public void cancelar(int idReserva) throws SQLException {
        String sql = "UPDATE reservas SET status = 'CANCELADA' WHERE id_reserva = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            ps.executeUpdate();
        }
    }

    public void aprovarReserva(int idReserva) throws SQLException {
        String sql = "UPDATE reservas SET status = 'AGENDADA' WHERE id_reserva = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idReserva);
            ps.executeUpdate();
        }
    }
    
    public List<Reserva> findAllSolicitacoes() throws SQLException {
        String sql = buildJoinQuery("r.status = 'SOLICITADA'");
        List<Reserva> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapRow(rs));
        }
        return lista;
    }

    private String buildJoinQuery(String whereClause) {
        return """
            SELECT r.id_reserva, r.matricula, r.disciplina, r.status, r.responsavel_matricula,
                   h.id_horario, h.dia, h.horario,
                   l.id_laboratorio, l.nome, l.descricao, l.andar, l.capacidade, l.ativo
            FROM reservas r
            JOIN horarios h ON r.horario_id = h.id_horario
            JOIN laboratorios l ON h.laboratorio_id = l.id_laboratorio
            WHERE \s""" + whereClause;
    }

    private Reserva mapRow(ResultSet rs) throws SQLException {
        Laboratorio lab = new Laboratorio(
            rs.getInt("id_laboratorio"),
            rs.getString("nome"),
            rs.getString("descricao"),
            rs.getString("andar"),
            rs.getInt("capacidade"),
            rs.getBoolean("ativo")
        );
        Horario horario = new Horario(
            rs.getInt("id_horario"),
            rs.getDate("dia"),
            HorariosEnum.valueOf(rs.getString("horario")),
            lab
        );
        return new Reserva(
            rs.getInt("id_reserva"),
            horario,
            rs.getString("matricula"),
            rs.getString("disciplina"),
            StatusReserva.valueOf(rs.getString("status")),
            rs.getString("responsavel_matricula")
        );
    }
}
