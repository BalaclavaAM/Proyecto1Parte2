package uniandes.dpoo.proyecto1.modelo.usuario;


public class Coordinador extends Usuario {
	private static final long serialVersionUID = 8736824648617471317L;
	private String nombre;
	private Carrera carrera;

	public Coordinador(String nombredeusuario, String contrasenha, String nombre, Carrera race) {
		super(nombredeusuario, contrasenha,nombre);
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

	@Override
	public String getPermission() {
		return "Cordinador";

	}

}
