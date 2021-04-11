package uniandes.dpoo.proyecto1.modelo;

public class Estudiante extends Usuario {
	private String name;
	private String codigo;
	private Pensum pensum;
	
	public Estudiante(String nombredeusuario, String contrasenha, String name, String codigo, Pensum pensum) {
		super(nombredeusuario, contrasenha);
		this.name = name;
		this.codigo = codigo;
		this.pensum = pensum;
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	
	public Pensum getPensum() {
		return pensum;
	}
	public void setPensum(Pensum pensum) {
		this.pensum = pensum;
	}
}
