package uniandes.dpoo.proyecto1.modelo;

public class Coordinador extends Usuario {
	private String nombre;
	private Carrera carrera;

	public Coordinador(String nombredeusuario, String contrasenha, String nombre, Carrera race) {
		super(nombredeusuario, contrasenha);
		this.nombre = nombre;
		this.setCarrera(race);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Carrera getCarrera() {
		return carrera;
	}

	public void setCarrera(Carrera carrera) {
		this.carrera = carrera;
	}
	
}
