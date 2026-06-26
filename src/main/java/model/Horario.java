package model;

import java.sql.Date;

public class Horario {
	int id;
	Date dia;
	HorariosEnum horario;
	StatusLab status; 
	int laboratorio_id;
	
	public Horario(int id, Date dia, HorariosEnum horario, StatusLab status, int laboratorio_id) {
		super();
		this.id = id;
		this.dia = dia;
		this.horario = horario;
		this.status = status;
		this.laboratorio_id = laboratorio_id;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDia() {
		return dia;
	}
	public void setDia(Date dia) {
		this.dia = dia;
	}
	public HorariosEnum getHorario() {
		return horario;
	}
	public void setHorario(HorariosEnum horario) {
		this.horario = horario;
	}
	public StatusLab getStatus() {
		return status;
	}
	public void setStatus(StatusLab status) {
		this.status = status;
	}
	public int getLaboratorio_id() {
		return laboratorio_id;
	}
	public void setLaboratorio_id(int laboratorio_id) {
		this.laboratorio_id = laboratorio_id;
	}
	
	
	
}
