package model;

public class Usuario {
	
	private String nome;
	private String email;
	private String senha;
	private String cpf;
	private String telefone;
	private Perfil perfil;
	private boolean administrador;
	private String matricula;

	public Usuario(String nome, String email, String senha, String matricula, String cpf, 
		String telefone, Perfil perfil, boolean administrador) {
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.matricula = matricula;
		this.cpf = cpf;
		this.telefone = telefone;
		this.perfil = perfil;
		this.administrador = administrador;
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
	public String getTelefone() {
		
		return telefone;
	}
	public void setTelefone(String telefone) {
		
		this.telefone = telefone;
	}
	public Perfil getPerfil() {

		return perfil;
	}
	public void setPerfil (Perfil perfil) {

		this.perfil = perfil;
	}

	public boolean isAdministrador() {

		return administrador;
	}
	public void setAdministrador(boolean administrador) {

		this.administrador = administrador;
	}
	
	public String getMatricula() {
		return matricula;
	}
	
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

}
