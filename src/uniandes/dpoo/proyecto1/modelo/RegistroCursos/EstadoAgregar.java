package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;

public class EstadoAgregar {
    private EstadoRegistro error;
    private Periodo periodo;
    private Curso curso;
    private String rest;


    public EstadoAgregar(CursoRegistrado cursoR, String rest) {
        this.error = EstadoRegistro.Restriccion;
        this.periodo = cursoR.getPeriodo();
        this.curso = cursoR.getCurso();
        this.rest = rest;
    }

    public EstadoAgregar(Periodo periodo, EstadoRegistro er) {
        this.error = er;
        this.periodo = periodo;
    }

    public EstadoAgregar(CursoRegistrado cursoR, EstadoRegistro er) {
        this.error = er;
        this.periodo = cursoR.getPeriodo();
        this.curso = cursoR.getCurso();
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
