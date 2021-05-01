package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.Periodo;

public class EstadoAgregar {
    private int error;
    private Periodo periodo;
    private Curso curso;

    public EstadoAgregar(int error, Periodo periodo, Curso curso) {
        this.error = error;
        this.periodo = periodo;
        this.curso = curso;
    }

    public EstadoAgregar cambiarEstado(int error, Periodo periodo, Curso curso){
        this.error = error;
        this.periodo = periodo;
        this.curso = curso;
        return this;
    }

    public EstadoAgregar cambiarEstado(int error, Periodo periodo){
        this.error = error;
        this.periodo = periodo;
        return this;
    }

    public EstadoAgregar(int error, Periodo periodo) {
        this.error = error;
        this.periodo = periodo;
    }

    public int getError() {
        return error;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public Curso getCurso() {
        return curso;
    }
}
