package uniandes.dpoo.proyecto1.modelo.Registro;


import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
<<<<<<< HEAD
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.NotaCual;
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.calCual;

public class CursoRegistrado {

	private final Curso curso;
	private final Periodo periodo;
	private Nota nota = new NotaCual(calCual.pendiente);
	private Estado estado = Estado.Pendiente;
=======
import uniandes.dpoo.proyecto1.modelo.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Nota.NotaCual;
import uniandes.dpoo.proyecto1.modelo.Nota.calCual;

public class CursoRegistrado {
	private final Curso curso;
	private final Periodo periodo;
	private Nota nota = new NotaCual(calCual.pendiente);
	private EstadoCurso estado = EstadoCurso.Pendiente;
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
	private boolean epsilon = false;
	private boolean numerica = false;

	public CursoRegistrado(Curso curso, Periodo periodo, Nota nota){
		this.curso = curso;
		this.periodo = periodo;
		this.nota = nota;
<<<<<<< HEAD
		this.estado = Estado.Finalizado;
=======
		this.estado = EstadoCurso.Finalizado;
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
		this.numerica = nota.isNumeric();
	}


	public CursoRegistrado(Curso curso, Periodo periodo, Nota nota, boolean epsilon){
		this.curso = curso;
		this.nota = nota;
		this.periodo = periodo;
		this.epsilon = epsilon;
<<<<<<< HEAD
		this.estado = Estado.Finalizado;
=======
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
		this.numerica = nota.isNumeric();
	}

	public CursoRegistrado(Curso curso, Periodo periodo){
		this.curso = curso;
		this.periodo = periodo;
	}
<<<<<<< HEAD
	public CursoRegistrado(Curso curso, Periodo periodo, Estado estado){
		this.curso = curso;
		this.periodo = periodo;
		this.estado = estado;
=======
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
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
	}

	public CursoRegistrado(Curso curso, Periodo periodo, boolean epsilon){
		this.curso = curso;
		this.periodo = periodo;
		this.epsilon = epsilon;
	}


	public Curso getCurso() {
		return curso;
	}

<<<<<<< HEAD
	public Estado getEstado() {
		return estado;
	}

=======
	public EstadoCurso getEstado() {
		return estado;
	}

	public void setEstado(EstadoCurso estado){
		this.estado = estado;
	}

>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
	public Nota getNota() {
		return nota;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
<<<<<<< HEAD
=======
		this.estado = EstadoCurso.Finalizado;
	}

	public void cambiarRegistro(Nota nota, boolean epsilon){
		this.nota = nota;
		this.epsilon = epsilon;
		this.estado = EstadoCurso.Finalizado;
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
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
