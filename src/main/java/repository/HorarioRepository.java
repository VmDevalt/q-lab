package repository;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dao.HorarioDao;
import model.Horario;
import model.HorariosEnum;

public class HorarioRepository {

    private final HorarioDao dao = new HorarioDao();

    public List<Horario> buscarPorDataELab(Date data, int laboratorioId) throws SQLException {
        return dao.findByDataELab(data, laboratorioId);
    }

    public int encontrarOuCriar(Date dia, HorariosEnum horario, int laboratorioId) throws SQLException {
        return dao.findOrCreate(dia, horario, laboratorioId);
    }

    public Horario buscarPorId(int id) throws SQLException {
        return dao.findById(id);
    }
}
