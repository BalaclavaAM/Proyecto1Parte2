package uniandes.dpoo.proyecto1.modelo;


public class CursoVisto {
	private Curso curso;
	private float nota;
	private Periodo periodo;
	

	public CursoVisto(Curso curso, Periodo periodo, float nota) {
		this.setCurso(curso);
		this.setPeriodo(periodo);
		this.setNota(nota);
	}


	public Curso getCurso() {
		return curso;
	}


	public void setCurso(Curso curso) {
		this.curso = curso;
	}


	public float getNota() {
		return nota;
	}


	public void setNota(float nota) {
		this.nota = nota;
	}


	public Periodo getPeriodo() {
		return periodo;
	}


	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}



	
}
