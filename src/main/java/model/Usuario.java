package model;

public class Usuario {
	
	private String nome;
	private String email;
	private String senha;
	private String cpf;
	private String numero;
	private Perfil perfil;
	
	public Usuario(String nome, String email, String senha, String cpf, String numero, Perfil perfil) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.cpf = cpf;
		this.numero = numero;
		this.perfil = perfil;
	}
	
	public String getNome() {
		
		return nome;
	}
	public void setNome(String nome) {
		
		this.nome = nome;
	}
	
	public String getEmail() {
		
		return email;
	}
	public void setEmail(String email) {
		
		this.email = email;
	}
	
	public String getSenha() {
		
		return senha;
	}
	public void setSenha(String senha) {
		
		this.senha = senha;
	}
	public String getCpf() {
		
		return cpf;
	}
	public void setCpf(String cpf) {
		
		this.cpf = cpf;
	}
	public String getNumero() {
		
		return numero;
	}
	public void setNumero(String numero) {
		
		this.numero = numero;
	}
	public Perfil getPerfil() {
		
		return perfil;
	}
	public void setPerfil (Perfil perfil) {
		
		this.perfil = perfil;
	}
	
}
