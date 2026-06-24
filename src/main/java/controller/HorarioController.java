package controller;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import dao.HorarioDao;
import model.Horario;

public class HorarioController {
	public List<Horario> recuperarHorarios(Date data) {
		try {
			HorarioDao dao = new HorarioDao();
			return dao.recuperarHorarios(data);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
