package model;

public class Reserva {

    private int idReserva;
    private Horario horario;
    private String matricula;
    private String disciplina;
    private StatusReserva status;

    public Reserva(int idReserva, Horario horario, String matricula, String disciplina, StatusReserva status) {
        this.idReserva = idReserva;
        this.horario = horario;
        this.matricula = matricula;
        this.disciplina = disciplina;
        this.status = status;
    }

    public int getIdReserva() { return idReserva; }
    public void setIdReserva(int idReserva) { this.idReserva = idReserva; }

    public Horario getHorario() { return horario; }
    public void setHorario(Horario horario) { this.horario = horario; }

    public String getMatricula() { return matricula; }
    public void setMatricula(String matricula) { this.matricula = matricula; }

    public String getDisciplina() { return disciplina; }
    public void setDisciplina(String disciplina) { this.disciplina = disciplina; }

    public StatusReserva getStatus() { return status; }
    public void setStatus(StatusReserva status) { this.status = status; }
}
