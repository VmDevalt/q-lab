package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import model.Horario;
import model.HorariosEnum;
import repository.HorarioRepository;

public class HorarioController {

    private final HorarioRepository repository = new HorarioRepository();

    public List<Horario> recuperarHorariosPorLabEData(Date data, int laboratorioId) {
        try {
            return repository.buscarPorDataELab(data, laboratorioId);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public int encontrarOuCriarHorario(Date dia, HorariosEnum slot, int laboratorioId) {
        try {
            return repository.encontrarOuCriar(dia, slot, laboratorioId);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
