package model;

import java.sql.Date;

public class Horario {

    private int id;
    private Date dia;
    private HorariosEnum horario;
    private Laboratorio laboratorio;

    public Horario(int id, Date dia, HorariosEnum horario, Laboratorio laboratorio) {
        this.id = id;
        this.dia = dia;
        this.horario = horario;
        this.laboratorio = laboratorio;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Date getDia() { return dia; }
    public void setDia(Date dia) { this.dia = dia; }

    public HorariosEnum getHorario() { return horario; }
    public void setHorario(HorariosEnum horario) { this.horario = horario; }

    public Laboratorio getLaboratorio() { return laboratorio; }
    public void setLaboratorio(Laboratorio laboratorio) { this.laboratorio = laboratorio; }
}
