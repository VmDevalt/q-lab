package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import model.HorariosEnum;
import model.Reserva;
import repository.HorarioRepository;
import repository.InterdicaoRepository;
import repository.ReservaRepository;

public class ReservaController {

    private final ReservaRepository reservaRepo = new ReservaRepository();
    private final HorarioRepository horarioRepo = new HorarioRepository();
    private final InterdicaoRepository interdicaoRepo = new InterdicaoRepository();

    public String realizarReserva(Date dia, HorariosEnum slot, int laboratorioId,
                                   String matricula, String disciplina) {
        try {
            if (interdicaoRepo.isInterditado(laboratorioId, dia)) {
                return "O laboratório está interditado nesta data.";
            }
            int horarioId = horarioRepo.encontrarOuCriar(dia, slot, laboratorioId);
            if (reservaRepo.buscarAgendadaPorHorario(horarioId) != null) {
                return "Este horário já está reservado.";
            }
            reservaRepo.criar(horarioId, matricula, disciplina);
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao realizar reserva: " + e.getMessage();
        }
    }

    public String cancelarReserva(int idReserva) {
        try {
            reservaRepo.cancelar(idReserva);
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cancelar reserva: " + e.getMessage();
        }
    }

    public String editarReserva(int idReserva, String novaDisciplina) {
        try {
            reservaRepo.atualizar(idReserva, novaDisciplina);
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao editar reserva: " + e.getMessage();
        }
    }

    public List<Reserva> listarReservasAtivas(String matricula) {
        try {
            return reservaRepo.buscarPorMatricula(matricula);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Reserva> historico(String matricula) {
        try {
            return reservaRepo.historicoPorMatricula(matricula);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<Reserva> reservasPorLabEData(int laboratorioId, Date data) {
        try {
            return reservaRepo.buscarPorLaboratorioEData(laboratorioId, data);
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public Reserva buscarAgendadaPorHorario(int horarioId) {
        try {
            return reservaRepo.buscarAgendadaPorHorario(horarioId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
