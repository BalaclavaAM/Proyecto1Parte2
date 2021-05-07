package uniandes.dpoo.proyecto1.modelo.usuario;

import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Pensum;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Estudiante extends Usuario implements Serializable {
	private static final long serialVersionUID = -232931508719866061L;
	private String codigo;
	private HistoriaAcademica HistoriaAcademica;
	private Map<String, Plan> planes;
	private String carrera;

	public Estudiante(String nombredeusuario, String contrasenha, String name, String codigo, Pensum pensum,String carrera,
					  Periodo periodo) {
		super(nombredeusuario, contrasenha,name);
		this.codigo = codigo;
		this.HistoriaAcademica = new HistoriaAcademica(pensum, periodo);
		this.carrera = carrera;
		ArrayList<Integer> d = new ArrayList<>();
	}


	public String getCodigo() {
		return codigo;
	}

	public void nuevoPlan(String nombre, Periodo periodo) {
		Plan np = new Plan(getHistoriaAcademica(), periodo);
		planes.put(nombre, np);
	}

	public HistoriaAcademica getHistoriaAcademica() {
		return HistoriaAcademica;
	}

	public Map<String, Plan> getPlan() {
		return planes;
	}

	@Override
	public String getPermission() {
		return "Estudiante";
	}

}
