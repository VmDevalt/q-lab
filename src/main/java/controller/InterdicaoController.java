package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import model.Interdicao;
import repository.InterdicaoRepository;

public class InterdicaoController {

    private final InterdicaoRepository repository = new InterdicaoRepository();

    public String interditar(int laboratorioId, Date dataInicio, Date dataFim, String motivo) {
        try {
            repository.interditar(laboratorioId, dataInicio, dataFim, motivo);
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao interditar laboratório: " + e.getMessage();
        }
    }

    public boolean isInterditado(int laboratorioId, Date data) {
        try {
            return repository.isInterditado(laboratorioId, data);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Interdicao> listarInterdicoesAtivas(int laboratorioId) {
        try {
            return repository.listarAtivasPorLaboratorio(laboratorioId);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public String reativar(int laboratorioId, int idInterdicao) {
        try {
            repository.reativar(laboratorioId, idInterdicao);
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao reativar laboratório: " + e.getMessage();
        }
    }
}
