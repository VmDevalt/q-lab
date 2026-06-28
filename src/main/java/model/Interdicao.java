package model;

import java.sql.Date;

public class Interdicao {

    private int id;
    private Laboratorio laboratorio;
    private Date dataInicio;
    private Date dataFim;
    private String motivo;
    private boolean ativo;

    public Interdicao(int id, Laboratorio laboratorio, Date dataInicio, Date dataFim, String motivo, boolean ativo) {
        this.id = id;
        this.laboratorio = laboratorio;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.motivo = motivo;
        this.ativo = ativo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Laboratorio getLaboratorio() { return laboratorio; }
    public void setLaboratorio(Laboratorio laboratorio) { this.laboratorio = laboratorio; }

    public Date getDataInicio() { return dataInicio; }
    public void setDataInicio(Date dataInicio) { this.dataInicio = dataInicio; }

    public Date getDataFim() { return dataFim; }
    public void setDataFim(Date dataFim) { this.dataFim = dataFim; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }
}
