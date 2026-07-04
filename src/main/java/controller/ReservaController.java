package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import model.HorariosEnum;
import model.Perfil;
import model.Reserva;
import model.Usuario;
import repository.HorarioRepository;
import repository.InterdicaoRepository;
import repository.ReservaRepository;
import repository.UsuarioRepository;

public class ReservaController {

    private final ReservaRepository reservaRepo = new ReservaRepository();
    private final HorarioRepository horarioRepo = new HorarioRepository();
    private final InterdicaoRepository interdicaoRepo = new InterdicaoRepository();
    private final UsuarioRepository usuarioRepo = new UsuarioRepository();

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
    
    public String solicitarReserva(Date dia, HorariosEnum slot, int laboratorioId,
		            			String matricula, String disciplina, String responsavelMatricula) {
		try {
			if (interdicaoRepo.isInterditado(laboratorioId, dia)) {
					return "O laboratório está interditado nesta data.";
			}
			int horarioId = horarioRepo.encontrarOuCriar(dia, slot, laboratorioId);
			if (reservaRepo.buscarAgendadaPorHorario(horarioId) != null) {
				return "Este horário já está reservado.";
			}
			Usuario professor = usuarioRepo.buscar(responsavelMatricula);
			if (professor == null) {
			    return "Professor não encontrado. Verifique a matrícula informada.";
			}
			reservaRepo.criarSolicitacao(horarioId, matricula, disciplina, responsavelMatricula);
			return null;
		} catch (SQLException e) {
		    if (e.getErrorCode() == 1452) { 
		        return "Professor não encontrado. Verifique a matrícula informada.";
		    }
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
    
    public String aprovarReserva(int idReserva) {
        try {
            reservaRepo.aprovarReserva(idReserva);
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao aprovar reserva: " + e.getMessage();
        }
    }
    
    public String rejeitarSolicitacao(int idReserva) {
        try {
            reservaRepo.cancelar(idReserva);
            return null;
        } catch (SQLException e) {
            e.printStackTrace();
            return "Erro ao cancelar solicitacao: " + e.getMessage();
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

    public List<Reserva> buscarSolicitacoesByResponsavel(String responsavelMatricula) {
        try {
            return reservaRepo.buscarSolicitacoesByResponsavel(responsavelMatricula);
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
