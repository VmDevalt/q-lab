package repository;

import java.sql.SQLException;
import java.util.List;

import dao.LaboratorioDao;
import model.Laboratorio;

public class LaboratorioRepository {

    private final LaboratorioDao dao = new LaboratorioDao();

    public List<Laboratorio> listarTodos() throws SQLException {
        return dao.findAll();
    }

    public Laboratorio buscarPorId(int id) throws SQLException {
        return dao.findById(id);
    }
}
