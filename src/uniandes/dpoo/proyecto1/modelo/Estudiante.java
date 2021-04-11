package uniandes.dpoo.proyecto1.modelo;

public class Estudiante extends Usuario {
	private String name;
	private String codigo;
	private Pensum plan;
	private HistoriaAcademica historia;

	public Estudiante(String name, String codigo, Pensum plan) {
		this.name = name;
		this.codigo = codigo;
		this.plan = plan;
		this.historia = new HistoriaAcademica(plan);
	}

	public String getName() {
		return name;
	}
	public String getCodigo() {
		return codigo;
	}
	public Pensum getPlan() {
		return plan;
	}


	
}
