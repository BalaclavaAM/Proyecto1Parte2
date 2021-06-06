package uniandes.dpoo.proyecto1.modelo.usuario;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Pensum;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Estudiante extends Usuario implements Serializable {
	private static final long serialVersionUID = -232931508719866061L;
	private final String codigo;
	private final HistoriaAcademica HistoriaAcademica;
	private final Map<String, Plan> planes;
	private final String carrera;


	public Estudiante(String nombredeusuario, String contrasenha, String name, String codigo, Pensum pensum, String carrera,
					  Periodo periodo) {
		super(nombredeusuario, contrasenha, name);
		this.codigo = codigo;
		this.HistoriaAcademica = new HistoriaAcademica(pensum, periodo);
		this.carrera = carrera;
		planes = new HashMap<>();
		ArrayList<Integer> d = new ArrayList<>();
	}


	public String getCodigo() {
		return codigo;
	}

	public void nuevoPlan(String nombre) {
		Plan np = new Plan(nombre,getHistoriaAcademica());
		planes.put(nombre, np);
	}

	public HistoriaAcademica getHistoriaAcademica() {
		return HistoriaAcademica;
	}

	public Plan getPlan(String nombre){
		return  planes.get(nombre);
	}


	public Map<String, Plan> getPlanes() {
		return planes;
	}

	public String getCarrera() {
		return carrera;
	}

	@Override
	public String getPermission() {
		return "Estudiante";
	}

}
