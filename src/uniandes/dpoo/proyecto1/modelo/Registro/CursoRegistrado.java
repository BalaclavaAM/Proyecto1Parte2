package uniandes.dpoo.proyecto1.modelo.Registro;


import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Nota.NotaCual;
import uniandes.dpoo.proyecto1.modelo.Nota.calCual;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;


public class CursoRegistrado {
	private final Curso curso;
	private final Periodo periodo;
	private Nota nota = new NotaCual(calCual.P);
	private EstadoCurso estado;
	private CursoRegistrado anterior = null;
	private boolean agregado = false;
	private final boolean epsilon;
	private boolean numerica = false;



	public CursoRegistrado(Curso curso, Nota nota, boolean epsilon,Periodo periodo){
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

	public void setAnterior(CursoRegistrado anterior) {
		this.anterior = anterior;
	}

	public CursoRegistrado getAnterior() {
		return anterior;
	}

	public void setNota(Nota nota) {
		this.nota = nota;
	}

	public int compareT(CursoRegistrado cr){
		return getPeriodo().compare(cr.getPeriodo());
	}

	public boolean isNumerica() {
		return numerica;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public boolean isRepetido() {
		return anterior != null;
	}

}
