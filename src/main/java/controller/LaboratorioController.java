package controller;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import model.Laboratorio;
import repository.LaboratorioRepository;

public class LaboratorioController {

    private final LaboratorioRepository repository = new LaboratorioRepository();

    public List<Laboratorio> listarLaboratorios() {
        try {
            return repository.listarTodos();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Laboratorio buscarPorId(int id) {
        try {
            return repository.buscarPorId(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
