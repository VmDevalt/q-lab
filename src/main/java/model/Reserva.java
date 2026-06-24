package model;

import java.sql.Date;

public class Reserva {
	int idReserva;
	int horarioId;
	int usuarioId;
	Date dataReserva; 
	String disciplina;
	
	public Reserva (int idReserva, int horarioId, int usuarioId, Date dataReserva, String disciplina) {
		this.idReserva = idReserva;
		this.horarioId = horarioId;
		this.usuarioId = usuarioId;
		this.dataReserva = dataReserva;
		this.disciplina = disciplina;
	}
	
	public int getIdReserva() {
		return idReserva;
	}
	
	public void setIdReserva(int idReserva) {
		this.idReserva = idReserva;
	}

	public int getHorarioId() {
		return horarioId;
	}

	public void setHorarioId(int horarioId) {
		this.horarioId = horarioId;
	}

	public int getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(int usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Date getDataReserva() {
		return dataReserva;
	}

	public void setDataReserva(Date dataReserva) {
		this.dataReserva = dataReserva;
	}

	public String getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(String disciplina) {
		this.disciplina = disciplina;
	}
	

}
