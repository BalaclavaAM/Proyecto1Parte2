package uniandes.dpoo.proyecto1.procesamiento;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import uniandes.dpoo.proyecto1.modelo.*;

public class ProcesadorBanner implements Serializable {
	
	private static final long serialVersionUID = -4746963895960324815L;

	private List<Curso> cursos;

	private List<Carrera> carreras;

	private Map<String,Estudiante> estudiantes;

	private Map<String,Coordinador> coordinadores;

	public ProcesadorBanner(List<Curso> cursos, List<Carrera> carreras, Map<String, Estudiante> estudiantes,
			Map<String, Coordinador> coordinadores) {
		super();
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

	public void setEstudiantes(Map<String, Estudiante> estudiantes) {
		this.estudiantes = estudiantes;
	}

	public Map<String, Coordinador> getCoordinadores() {
		return coordinadores;
	}

	public void setCoordinadores(Map<String, Coordinador> coordinadores) {
		this.coordinadores = coordinadores;
	}

	public Collection<String> darNombresCarreras()
	{
		Collection<String> nombres = new HashSet<String>();
		for (Carrera carrera : carreras)
		{
			nombres.add(carrera.getNombre());
		}

		return nombres;
	}
	
	
}
