package uniandes.dpoo.proyecto1.modelo;


public class CursoVisto {
	private Curso curso;
	private float nota;
	private Periodo periodo;
	

	public CursoVisto(Curso curso, Periodo periodo, float nota) {
		this.curso = curso;
		this.periodo = periodo;
		this.nota = nota;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public Curso getCurso() {
		return curso;
	}
	
	public float getNota() {
		return nota;
	}
	

}
