package uniandes.dpoo.proyecto1.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Estudiante extends Usuario implements Serializable {
	private static final long serialVersionUID = -232931508719866061L;
	private String name;
	private String codigo;
	private Pensum pensum;
	private HistoriaAcademica HistoriaAcademica;
	private Map<String,List<Curso>> plan;
	
	public Estudiante(String nombredeusuario, String contrasenha, String name, String codigo, Pensum pensum, Periodo periodo) {
		super(nombredeusuario, contrasenha);
		this.name = name;
		this.codigo = codigo;
		this.pensum = pensum;
		this.HistoriaAcademica = new HistoriaAcademica(pensum, periodo);
		this.plan = Collections.emptyMap();
		ArrayList<Integer> d = new ArrayList<>();
	}

	public String getName() {
		return name;
	}
	public String getCodigo() {
		return codigo;
	}
	public Pensum getPensum() {
		return this.pensum;
	}

	public HistoriaAcademica getHistoriaAcademica() {
		return HistoriaAcademica;
	}
	public Map<String,List<Curso>> getPlan() {
		return plan;
	}
}
