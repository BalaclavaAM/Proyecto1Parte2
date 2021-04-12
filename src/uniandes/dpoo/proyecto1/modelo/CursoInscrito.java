package uniandes.dpoo.proyecto1.modelo;

public class CursoInscrito {
    private Curso curso;
    private Periodo periodo;

    public CursoInscrito(Curso curso, Periodo periodo) {
        this.setCurso(curso);
        this.setPeriodo(periodo);
    }

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
	}

}
