package uniandes.dpoo.proyecto1.modelo;

import java.io.Serializable;

public class Usuario implements Serializable {
	// ************************************************************************
	// Atributos
	// ************************************************************************
	private static final long serialVersionUID = -1050671037655716152L;
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
