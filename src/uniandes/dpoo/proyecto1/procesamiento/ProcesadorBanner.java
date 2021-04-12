package uniandes.dpoo.proyecto1.procesamiento;

import java.io.Serializable;
import java.util.ArrayList;
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
	
	public int authEstudiante(String user, String password) {
		if (estudiantes.containsKey(user)) {
			if (estudiantes.get(user).getContrasenha()==password) {
				return 1;
			}
			else
			{
				return 0;
			}
		}
		else
		{
			return -1;
		}
	}
	
	public int authCoordinador(String user, String password) {
		if (coordinadores.containsKey(user)) {
			if (coordinadores.get(user).getContrasenha()==password) {
				return 1;
			}
			else
			{
				return 0;
			}
		}
		else
		{
			return -1;
		}
	}
	
	public boolean crearEstudiante(String username, String contrasenha, String nombre, String codigo) 
	{
		return true;
	}
	
	public boolean checkCorequisitos(Curso elcurso, List<Curso> cursosSemestre) {
		boolean retorno = true;
		if (elcurso.getRestricciones().containsKey("coreq")) {
			List<Curso> correquisitos = elcurso.getRestricciones().get("coreq");
			for (Curso correquisito : correquisitos) {
				if (!(cursosSemestre.contains(correquisito))){
					retorno = false;
					break;
				}
			}
		}
		return retorno;
	}
	
	public boolean checkPreRequisitos(Curso elcurso, HistoriaAcademica historia) {
		boolean retorno = true;
		if (elcurso.getRestricciones().containsKey("prereq")) {
			List<Curso> prerequisitos = elcurso.getRestricciones().get("prereq");
			for (Curso prereq : prerequisitos) {
				Map<Integer, ArrayList<CursoVisto>> cursosvistos = historia.getCursosvistosXsemestre();
				for (Map.Entry<Integer,ArrayList<CursoVisto>> entrada : cursosvistos.entrySet()) {
					ArrayList<CursoVisto> cursossemestre = entrada.getValue();
					for (CursoVisto cv : cursossemestre) {
						if (!(cv.getCurso().getCodigo()==prereq.getNombre())) {
							retorno=false;
							break;
						}
					}
				}
			}
		}
		return retorno;
	}
	
	public boolean checkPlan(Estudiante elestudiante) {
		boolean retorno = true;
		Map<String,List<Curso>> plan = elestudiante.getPlan();
		for (Map.Entry<String,List<Curso>> entrada : plan.entrySet()) {
			int creditos = 0;
			for (Curso cursoplaneado : entrada.getValue()) {
				creditos+=cursoplaneado.getCreditos();
				if (! (checkCorequisitos(cursoplaneado,entrada.getValue())) || !(checkPreRequisitos(cursoplaneado,elestudiante.getHistoriaAcademica()))) {
					retorno = false;
					break;
				}
				if (creditos>25) {
					retorno = false;
					break;
				}
			}
		}
		return retorno;
	}
}
