package uniandes.dpoo.proyecto1.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Estudiante extends Usuario implements Serializable {
	private static final long serialVersionUID = -232931508719866061L;
	private String name;
	private String codigo;
	private HistoriaAcademica HistoriaAcademica;
	
	public Estudiante(String nombredeusuario, String contrasenha, String name, String codigo) {
		super(nombredeusuario, contrasenha);
		this.name = name;
		this.codigo = codigo;
		this.HistoriaAcademica = new HistoriaAcademica();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public HistoriaAcademica getHistoriaAcademica() {
		return HistoriaAcademica;
	}

	public void setHistoriaAcademica(HistoriaAcademica historiaAcademica) {
		HistoriaAcademica = historiaAcademica;
	}
	

}
