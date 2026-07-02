package repository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dao.InterdicaoDao;
import model.Interdicao;

public class InterdicaoRepository {

    private final InterdicaoDao dao = new InterdicaoDao();

    public int interditar(int laboratorioId, Date dataInicio, Date dataFim, String motivo) throws SQLException {
        return dao.interditar(laboratorioId, dataInicio, dataFim, motivo);
    }

    public boolean isInterditado(int laboratorioId, Date data) throws SQLException {
        return dao.isInterditado(laboratorioId, data);
    }

    public List<Interdicao> listarAtivasPorLaboratorio(int laboratorioId) throws SQLException {
        return dao.findAtivasByLaboratorio(laboratorioId);
    }

    public void reativar(int laboratorioId, int idInterdicao) throws SQLException {
        dao.reativar(laboratorioId, idInterdicao);
    }
}
