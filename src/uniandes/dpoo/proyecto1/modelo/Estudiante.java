package uniandes.dpoo.proyecto1.modelo;

public class Estudiante extends Usuario {
	private String name;
	private String codigo;
	private PlandeEstudios plan;
	
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
	public PlandeEstudios getPlan() {
		return plan;
	}
	public void setPlan(PlandeEstudios plan) {
		this.plan = plan;
	}
	
}
