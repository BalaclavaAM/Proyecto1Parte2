package uniandes.dpoo.proyecto1.modelo.Cursos_Req;

import java.util.ArrayList;

public class Seccion {
    private Curso curso;
    private Boolean epsilon;
    private String nrc;
    private String[][] horarios;
    private String profesor;
    private Boolean tipoE;
    private Integer NSeccion;
    private String ciclo;

    public Seccion(Curso curso, Boolean epsilon, String nrc,String[][] horario, String profesor, Boolean tipoE, Integer NSeccion, String ciclo) {
        this.curso = curso;
        this.epsilon = epsilon;
        this.nrc = nrc;
        this.horarios = horario;
        this.profesor = profesor;
        this.tipoE = tipoE;
        this.NSeccion = NSeccion;
        this.ciclo = ciclo;
    }
    public Curso getCurso() {
        return curso;
    }

    public Boolean getEpsilon() {
        return epsilon;
    }

    public String getNrc() {
        return nrc;
    }

    public String[][] getHorario() {
        return horarios;
    }


    public String getProfesor() {
        return profesor;
    }

    public Boolean getTipoE() {
        return tipoE;
    }

    public Integer getNSeccion() {
        return NSeccion;
    }

    public String getCiclo() {
        return ciclo;
    }

}
