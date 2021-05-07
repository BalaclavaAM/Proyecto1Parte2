package uniandes.dpoo.proyecto1.modelo.usuario;

import java.io.Serializable;

public abstract class Usuario implements Serializable {
	// ************************************************************************
	// Atributos
	// ************************************************************************
	protected static final long serialVersionUID = -1050671037655716152L;
	protected String username;
	protected String contrasenha;
	protected String nombre;
	
	// ************************************************************************
	// Elcons tructor
	// ************************************************************************
	public Usuario(String username, String contrasenha, String nombre) {
		this.username = username;
		this.contrasenha = contrasenha;
		this.nombre = nombre;
	}

	public abstract String getPermission();

	public boolean igualContrasenha(String intent){
		return contrasenha.equals(intent);
	}

	public boolean cambiarContrasenha(String intent, String newPassword){
		if(intent.equals(contrasenha)){
			contrasenha = newPassword;
			return true;
		}
		return false;
	}

	public String getUsername() {
		return username;
	}

	public String getNombre() {
		return nombre;
	}

}
