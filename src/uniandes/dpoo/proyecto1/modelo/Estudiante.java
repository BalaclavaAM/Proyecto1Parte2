package uniandes.dpoo.proyecto1.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Estudiante extends Usuario implements Serializable {
	private static final long serialVersionUID = -232931508719866061L;
	private String name;
	private String codigo;
	private ArrayList<Pensum> pensum;
	private HistoriaAcademica HistoriaAcademica;
	
	public Estudiante(String nombredeusuario, String contrasenha, String name, String codigo, ArrayList<Pensum> pensum,
			uniandes.dpoo.proyecto1.modelo.HistoriaAcademica historiaAcademica) {
		super(nombredeusuario, contrasenha);
		this.name = name;
		this.codigo = codigo;
		this.setPensum(pensum);
		setHistoriaAcademica(historiaAcademica);
	}
	public String getName() {
		return name;
	}
	public String getCodigo() {
		return codigo;
	}
	public ArrayList<Pensum> getPensum() {
		return pensum;
	}
	public void setPensum(ArrayList<Pensum> pensum) {
		this.pensum = pensum;
	}
	public HistoriaAcademica getHistoriaAcademica() {
		return HistoriaAcademica;
	}
	public void setHistoriaAcademica(HistoriaAcademica historiaAcademica) {
		HistoriaAcademica = historiaAcademica;
	}
	

}
