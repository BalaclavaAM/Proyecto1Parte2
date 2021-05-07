package uniandes.dpoo.proyecto1.procesamiento;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.usuario.Carrera;
import uniandes.dpoo.proyecto1.modelo.usuario.Coordinador;
import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;
import uniandes.dpoo.proyecto1.modelo.usuario.Usuario;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;


public class ProcesadorBanner implements Serializable {

	@Serial
	private static final long serialVersionUID = -4746963895960324815L;

	private Map<String,Map<String,Curso>> catalogo; //catalogo por materia

	private Map<String,Carrera> carreras; //<nombreUsuario,Usuario>

	private Map<String, Usuario> usuarios; //<nombreUsuario,Usuario>

	private Periodo periodo;

	private int nEstSem; //numero de estudiantes durante el semestre

	public ProcesadorBanner() {
		this.catalogo = new HashMap<>();
		this.carreras = new HashMap<>();
		this.usuarios = new HashMap<>();
	}

	public Map<String, Map<String, Curso>> getCursos() {
		return catalogo;
	}

	public Map<String,Carrera> getCarreras() {
		return carreras;
	}

	public Map<String, Usuario> getUsuarios() {
		return usuarios;
	}


	public Collection<String> darNombresCarreras() {
		return carreras.keySet();
	}

	public void avanzarPeriodo(){
		periodo.avanzarPeriodo();
	}

	public Usuario authenticate(String user, String password) {
		Usuario e = usuarios.get(user);
		if (e != null) {
			if (e.igualContrasenha(password)) {
				return e;
			}
		}
		return null;
	}

	private static String agregarCeros(int n){
		String ns = String.valueOf(n);
		int con = 4 - ns.length();
		StringBuilder nb = new StringBuilder(ns);
		for (int i = 0; i <con; i++) {
			nb.insert(0, "0");
		}
		return nb.toString();
	}

	public boolean crearCordinador(String username, String contrasenha, String nombre, String carreraN){
		Carrera carrera = carreras.get(carreraN);
		if(usuarios.containsKey(username)) {
			if (carreraN != null) {
				String password = randomPassword();
				Coordinador coordinador = new Coordinador(username, contrasenha, nombre,carrera);
				usuarios.put(username, coordinador);
				nEstSem++;
				return true;
			}
		}
		return false;
	}

	public boolean crearEstudiante(String username, String nombre, String carreraN) {
		Carrera carrera = carreras.get(carreraN);
		if(usuarios.containsKey(nombre)) {
			if (carreraN != null) {
				String password = randomPassword();
				String codigo = periodo.getAnio() + periodo.getSemestre() % 10 + agregarCeros(nEstSem);
				Estudiante estudiante = new Estudiante(username, password, password, codigo, carrera.getPensumActual()
						,carreraN,periodo);
				usuarios.put(username, estudiante);
				nEstSem++;
				return true;
			}
		}
		return false;
	}


	private static String randomPassword() {
		StringBuilder contrasenha = new StringBuilder();
		for (int i = 0; i < 9; i++) {
			contrasenha.append((char) (Math.random() * (127 - 49) + 49));
		}
		return contrasenha.toString();
	}

	public static void main(String[] args) {
		int r = 9;
		System.out.println(agregarCeros(r));
		System.out.println(randomPassword());
		System.out.println((char) (126));
	}
}

