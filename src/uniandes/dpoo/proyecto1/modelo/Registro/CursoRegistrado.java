package uniandes.dpoo.proyecto1.modelo.Registro;


import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Nota.*;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.EstadoAgregar;


public class CursoRegistrado {
	private final Curso curso;
	private final Periodo periodo;
	private Nota nota = new NotaCual(calCual.pendiente);
	private EstadoCurso estado = EstadoCurso.Pendiente;
	private boolean agregado = false;
	private boolean epsilon = false;
	private boolean numerica = false;



	public CursoRegistrado(Curso curso, Nota nota, EstadoCurso estadoC, boolean epsilon,Periodo periodo){
		this.curso = curso;
		this.nota = nota;
		this.estado = EstadoCurso.Finalizado;
		this.periodo = periodo;
		this.epsilon = epsilon;
		this.numerica = nota.isNumeric();
	}




	public CursoRegistrado(Curso curso, EstadoCurso estadoC, boolean epsilon,Periodo periodo){
		this.curso = curso;
		this.periodo = periodo;
		this.estado = estadoC;
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
		if(nota.notaCual() != calCual.pendiente) {
			this.estado = EstadoCurso.Finalizado;
		}
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
