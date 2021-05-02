package uniandes.dpoo.proyecto1.modelo.Registro;


import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.NotaCual;
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.calCual;

public class CursoRegistrado {
	private final Curso curso;
	private final Periodo periodo;
	private Nota nota = new NotaCual(calCual.pendiente);
	private EstadoCurso estado = EstadoCurso.Pendiente;
	private boolean epsilon = false;
	private boolean numerica = false;

	public CursoRegistrado(Curso curso, Periodo periodo, Nota nota){
		this.curso = curso;
		this.periodo = periodo;
		this.nota = nota;
		this.estado = EstadoCurso.Finalizado;
		this.numerica = nota.isNumeric();
	}


	public CursoRegistrado(Curso curso, Periodo periodo, Nota nota, boolean epsilon){
		this.curso = curso;
		this.nota = nota;
		this.periodo = periodo;
		this.epsilon = epsilon;
		this.numerica = nota.isNumeric();
	}

	public CursoRegistrado(Curso curso, Periodo periodo){
		this.curso = curso;
		this.periodo = periodo;
	}
	public CursoRegistrado(Curso curso, Periodo periodo, EstadoCurso estado){
		this.curso = curso;
		this.periodo = periodo;
		this.estado = estado;
		if(estado == EstadoCurso.Inscrito){
			this.nota = new NotaCual(calCual.pendiente);
		}
		if(estado == EstadoCurso.Planeado){
			this.nota = new NotaCual(calCual.planeado);
		}
	}

	public CursoRegistrado(Curso curso, Periodo periodo, boolean epsilon){
		this.curso = curso;
		this.periodo = periodo;
		this.epsilon = epsilon;
	}


	public Curso getCurso() {
		return curso;
	}

	public EstadoCurso getEstado() {
		return estado;
	}

	public void setEstado(EstadoCurso estado){
		this.estado = estado;
	}

	public Nota getNota() {
		return nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
		this.estado = EstadoCurso.Finalizado;
	}

	public void cambiarRegistro(Nota nota, boolean epsilon){
		this.nota = nota;
		this.epsilon = epsilon;
		this.estado = EstadoCurso.Finalizado;
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
