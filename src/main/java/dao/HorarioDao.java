package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Horario;
import model.HorariosEnum;
import model.StatusLab;
import util.ConnectionFactory;

public class HorarioDao {
	public List<Horario> recuperarHorarios(Date data) throws SQLException {
		String sql = "SELECT * FROM horarios WHERE dia = ?";
		Connection conn = ConnectionFactory.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setDate(1, data);
		ArrayList <Horario> horarios = new ArrayList<>();
		
		
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Horario h = new Horario(
				rs.getInt("id_horario"),
				rs.getDate("dia"),
				HorariosEnum.valueOf(rs.getString("horario")),
				StatusLab.valueOf(rs.getString("status_lab")),
				rs.getInt("laboratorio_id"));
			
			horarios.add(h);
		}
		ConnectionFactory.closeConnection(conn);
		ps.close();
		return (horarios);
	}

}
