package repository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dao.ReservaDao;
import model.Reserva;

public class ReservaRepository {

    private final ReservaDao dao = new ReservaDao();

    public int criar(int horarioId, String matricula, String disciplina) throws SQLException {
        return dao.save(horarioId, matricula, disciplina);
    }

    public Reserva buscarAgendadaPorHorario(int horarioId) throws SQLException {
        return dao.findAgendadaByHorario(horarioId);
    }

    public List<Reserva> buscarPorMatricula(String matricula) throws SQLException {
        return dao.findByMatricula(matricula);
    }

    public List<Reserva> buscarPorLaboratorioEData(int laboratorioId, Date data) throws SQLException {
        return dao.findByLaboratorioEData(laboratorioId, data);
    }

    public List<Reserva> historicoPorMatricula(String matricula) throws SQLException {
        return dao.findHistoricoByMatricula(matricula);
    }

    public void cancelar(int idReserva) throws SQLException {
        dao.cancelar(idReserva);
    }
}
