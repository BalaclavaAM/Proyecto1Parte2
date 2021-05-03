package uniandes.dpoo.proyecto1.procesamiento;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import uniandes.dpoo.proyecto1.modelo.usuario.Carrera;
import uniandes.dpoo.proyecto1.modelo.usuario.Coordinador;
import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;


public class ProcesadorBanner implements Serializable {

	private static final long serialVersionUID = -4746963895960324815L;

	private List<Curso> cursos;

	private List<Carrera> carreras;

	private Map<String, Estudiante> estudiantes;

	private Map<String, Coordinador> coordinadores;

	public ProcesadorBanner(List<Curso> cursos, List<Carrera> carreras, Map<String, Estudiante> estudiantes,
							Map<String, Coordinador> coordinadores) {
		this.cursos = cursos;
		this.carreras = carreras;
		this.estudiantes = estudiantes;
		this.coordinadores = coordinadores;
	}

	public List<Curso> getCursos() {
		return cursos;
	}

	public void setCursos(List<Curso> cursos) {
		this.cursos = cursos;
	}

	public List<Carrera> getCarreras() {
		return carreras;
	}

	public void setCarreras(List<Carrera> carreras) {
		this.carreras = carreras;
	}

	public Map<String, Estudiante> getEstudiantes() {
		return estudiantes;
	}


	public Map<String, Coordinador> getCoordinadores() {
		return coordinadores;
	}

	public void setCoordinadores(Map<String, Coordinador> coordinadores) {
		this.coordinadores = coordinadores;
	}

	public Collection<String> darNombresCarreras() {
		Collection<String> nombres = new HashSet<String>();
		for (Carrera carrera : carreras) {
			nombres.add(carrera.getNombre());
		}

		return nombres;
	}

	public int authEstudiante(String user, String password) {
		if (estudiantes.containsKey(user)) {
			if (estudiantes.get(user).getContrasenha().equals(password)) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return -1;
		}
	}

	public int authCoordinador(String user, String password) {
		if (coordinadores.containsKey(user)) {
			if (coordinadores.get(user).getContrasenha() == password) {
				return 1;
			} else {
				return 0;
			}
		} else {
			return -1;
		}
	}

	public boolean crearEstudiante(String username, String contrasenha, String nombre, String codigo) {
		return true;
	}



}