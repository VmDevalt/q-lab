package model;

public class Laboratorio {

    private int id;
    private String nome;
    private String descricao;
    private String andar;
    private int capacidade;
    private boolean ativo;

    public Laboratorio(int id, String nome, String descricao, String andar, int capacidade, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.andar = andar;
        this.capacidade = capacidade;
        this.ativo = ativo;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getAndar() { return andar; }
    public void setAndar(String andar) { this.andar = andar; }

    public int getCapacidade() { return capacidade; }
    public void setCapacidade(int capacidade) { this.capacidade = capacidade; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    @Override
    public String toString() { return nome; }
}
