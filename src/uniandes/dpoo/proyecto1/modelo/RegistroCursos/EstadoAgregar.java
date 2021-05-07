package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Restricciones.Restriccion;

public class EstadoAgregar {
    private EstadoRegistro error;
    private Periodo periodo;
    private Curso curso;
    private Restriccion rest;


    public EstadoAgregar(CursoRegistrado curso, Restriccion rest) {
        this.error = curso.getEstadoAgregar().getError();
        this.periodo = curso.getPeriodo();
        this.curso = curso.getCurso();
        this.rest = rest;
    }

    public EstadoAgregar(Periodo periodo) {
        this.error = EstadoRegistro.Pendiente;
        this.periodo = periodo;
    }

    public EstadoAgregar(Periodo periodo, Curso curso){
        this.periodo = periodo;
        this.curso = curso;
        this.error = EstadoRegistro.Pendiente;
    }

    public Restriccion getRest() {
        return rest;
    }

    public void setRest(Restriccion rest) {
        this.rest = rest;
    }

    public void setError(EstadoRegistro error) {
        this.error = error;
    }



    public EstadoAgregar(EstadoRegistro error, Periodo periodo) {
        this.error = error;
        this.periodo = periodo;
    }

    public EstadoRegistro getError() {
        return error;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public Curso getCurso() {
        return curso;
    }
}
