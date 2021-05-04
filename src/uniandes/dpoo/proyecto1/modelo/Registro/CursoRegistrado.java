package uniandes.dpoo.proyecto1.modelo.Registro;


import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Nota.NotaCual;
import uniandes.dpoo.proyecto1.modelo.Nota.calCual;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.EstadoAgregar;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.EstadoRegistro;


public class CursoRegistrado {
	private final Curso curso;
	private final Periodo periodo;
	private EstadoAgregar estadoAgregar;
	private Nota nota = new NotaCual(calCual.pendiente);
	private EstadoCurso estado = EstadoCurso.Pendiente;
	private boolean agregado = false;
	private boolean epsilon = false;
	private boolean numerica = false;

	public CursoRegistrado(Curso curso, Periodo periodo, Nota nota){
		this.curso = curso;
		this.periodo = periodo;
		this.nota = nota;
		this.numerica = nota.isNumeric();
	}


	public CursoRegistrado(Curso curso, Nota nota, EstadoCurso estadoC, boolean epsilon,Periodo periodo){
		this.curso = curso;
		this.nota = nota;
		this.estado = estadoC;
		this.periodo = periodo;
		this.epsilon = epsilon;
		this.numerica = nota.isNumeric();
		this.estadoAgregar = new EstadoAgregar(periodo);
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

	public CursoRegistrado(Curso curso, Periodo periodo, EstadoCurso estadoC, Nota nota, boolean epsilon) {
		this.curso = curso;
		this.periodo = periodo;
		this.estado = estadoC;
		this.nota = nota;
		this.epsilon = epsilon;
	}


	public Curso getCurso() {
		return curso;
	}


	public EstadoCurso getEstado() {
		return estado;
	}

	public EstadoAgregar getEstadoAgregar() {
		return estadoAgregar;
	}


	public void setEstado(EstadoCurso estado){
		this.estado = estado;
	}


	public Nota getNota() {
		return nota;
	}

	public boolean isAgregado() {
		return agregado;
	}

	public void Agregado() {
		this.agregado = true;
	}

	public boolean getEpsilon(){
		return epsilon;
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
