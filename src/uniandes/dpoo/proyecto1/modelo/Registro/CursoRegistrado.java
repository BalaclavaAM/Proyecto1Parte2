package uniandes.dpoo.proyecto1.modelo.Registro;


import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.NotaCual;
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.calCual;

public class CursoRegistrado {

	private final Curso curso;
	private final Periodo periodo;
	private Nota nota = new NotaCual(calCual.pendiente);
	private Estado estado = Estado.Pendiente;
	private boolean epsilon = false;
	private boolean numerica = false;

	public CursoRegistrado(Curso curso, Periodo periodo, Nota nota){
		this.curso = curso;
		this.periodo = periodo;
		this.nota = nota;
		this.estado = Estado.Finalizado;
		this.numerica = nota.isNumeric();
	}


	public CursoRegistrado(Curso curso, Periodo periodo, Nota nota, boolean epsilon){
		this.curso = curso;
		this.nota = nota;
		this.periodo = periodo;
		this.epsilon = epsilon;
		this.estado = Estado.Finalizado;
		this.numerica = nota.isNumeric();
	}

	public CursoRegistrado(Curso curso, Periodo periodo){
		this.curso = curso;
		this.periodo = periodo;
	}
	public CursoRegistrado(Curso curso, Periodo periodo, Estado estado){
		this.curso = curso;
		this.periodo = periodo;
		this.estado = estado;
	}

	public CursoRegistrado(Curso curso, Periodo periodo, boolean epsilon){
		this.curso = curso;
		this.periodo = periodo;
		this.epsilon = epsilon;
	}


	public Curso getCurso() {
		return curso;
	}

	public Estado getEstado() {
		return estado;
	}

	public Nota getNota() {
		return nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}

	public void setEpsilon(boolean epsilon) {
		this.epsilon = epsilon;
	}

	public boolean isNumerica() {
		return numerica;
	}

	public Periodo getPeriodo() {
		return periodo;
	}
}
