package uniandes.dpoo.proyecto1.procesamiento;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.usuario.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;


public class Banner implements Serializable {

	@Serial
	private static final long serialVersionUID = -4746963895960324815L;

	private Map<String,Map<String,Curso>> catalogoDepartamentos; //catalogo por materia

	private Map<String,Curso> catalogo;

	private Map<String,Carrera> carreras; //<nombreUsuario,Usuario>

	private Map<String, Usuario> usuarios; //<nombreUsuario,Usuario>

	private Periodo periodo;

	private int nEstSem = 0; //numero de estudiantes durante el semestre

	public Banner(Periodo periodo) {
		this.catalogoDepartamentos = new HashMap<>();
		this.carreras = new HashMap<>();
		this.usuarios = new HashMap<>();
		this.periodo = periodo;
	}

	public void cargarData(String filename){

	}

	public void avanzarPeriodo(){
		periodo.avanzarPeriodo();
		nEstSem = 0;
	}

	public Map<String, Map<String, Curso>> getCursos() {
		return catalogoDepartamentos;
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

	public Periodo getPeriodo() {
		return periodo;
	}

	public Map<String, Curso> getCatalogo() {
		return catalogo;
	}

	public Map<String, Map<String, Curso>> getCatalogoDepartamentos() {
		return catalogoDepartamentos;
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

	public Usuario authenticate(String user, String password) {
		Usuario e = usuarios.get(user);
		if (e != null) {
			if (e.igualContrasenha(password)) {
				return e;
			}
		}
		return null;
	}

	public String crearCordinador(String username, String nombre, String carreraN){
		Carrera carrera = carreras.get(carreraN);
		if(usuarios.containsKey(username)) {
			if (carreraN != null) {
				String password = randomPassword();
				Coordinador coordinador = new Coordinador(username, password, nombre,carrera);
				usuarios.put(username, coordinador);
				nEstSem++;
				return password;
			}
		}
		return "";
	}

	public ArrayList<Curso> filtrarCursos(String dpto, String codigo){
		ArrayList<Curso> retornar = new ArrayList<>();
		if (!(dpto.isEmpty()))
		{
			if (catalogo.containsKey(dpto))
			{
				Map<String, Curso> colecciondpto = catalogo.get(dpto);
				if (!(codigo.isEmpty())){
					if (colecciondpto.containsKey(codigo)){
						retornar.add(colecciondpto.get(codigo));
					}
				} else {
					for (Map.Entry<String, Curso> entry1 : colecciondpto.entrySet()){
						retornar.add(entry1.getValue());
					}
				}
			} else if (!(codigo.isEmpty())){
				buscarAux(codigo, retornar);
			}
		}
		else{
			if (!(codigo.isEmpty())) {
				buscarAux(codigo, retornar);
			} else {
				for (Map.Entry<String, Map<String,Curso>> entry1 : catalogo.entrySet()){
					for (Map.Entry<String,Curso> entry : entry1.getValue().entrySet()){
							retornar.add(entry.getValue());
					}
				}
			}
		}
		return retornar;
	}

	private void buscarAux(String codigo, ArrayList<Curso> retornar) {
		for (Map.Entry<String, Map<String,Curso>> entry1 : catalogo.entrySet()){
			for (Map.Entry<String,Curso> entry : entry1.getValue().entrySet()){
				if (entry.getKey().equals(codigo)){
					retornar.add(entry.getValue());
				}
			}
		}
	}

	public String crearEstudiante(String username, String nombre, String carreraN) {
		Carrera carrera = carreras.get(carreraN);
		if(usuarios.containsKey(nombre)) {
			if (carreraN != null) {
				String password = randomPassword();
				String codigo = periodo.getAnio() + periodo.getSemestre() / 10 + agregarCeros(nEstSem);
				Estudiante estudiante = new Estudiante(username, password, password, codigo, carrera.getPensumActual()
						,carreraN,periodo);
				usuarios.put(username, estudiante);
				nEstSem++;
				return password;
			}
		}
		return "";
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

