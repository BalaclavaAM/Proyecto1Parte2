package uniandes.dpoo.proyecto1.modelo;

import java.util.Date;

public class CursoVisto {
	private Curso curso;
	private float nota;
	private Date fecha;
	

	public CursoVisto(Curso curso, Periodo periodo, float nota) {
		this.curso = curso;
		this.periodo = periodo;
		this.nota = nota;
	}

	public Curso getCurso() {
		return curso;
	}
	
	public float getNota() {
		return nota;
	}
	

}
