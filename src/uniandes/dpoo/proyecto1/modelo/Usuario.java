package uniandes.dpoo.proyecto1.modelo;

public class Usuario {
	// ************************************************************************
	// Atributos
	// ************************************************************************
	private String nombredeusuario;
	private String contrasenha;
	
	
	
	// ************************************************************************
	// Elcons tructor
	// ************************************************************************
	public Usuario(String nombredeusuario, String contrasenha) {
		this.nombredeusuario = nombredeusuario;
		this.contrasenha = contrasenha;
	}

	public String getContrasenha() {
		return contrasenha;
	}
	
	public void setContrasenha(String contrasenha) {
		this.contrasenha = contrasenha;
	}
	
	public String getNombredeusuario() {
		return nombredeusuario;
	}
	
	public void setNombredeusuario(String nombredeusuario) {
		this.nombredeusuario = nombredeusuario;
	}
}
