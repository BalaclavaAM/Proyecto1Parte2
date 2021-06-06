package uniandes.dpoo.proyecto1.modelo.Cursos_Req;

public class Seccion {
    private final Curso curso;
    private final Boolean epsilon;
    private final String nrc;
    private final String[][] horarios;
    private final String profesor;
    private final Boolean tipoE;
    private final Integer NSeccion;
    private final String ciclo;
    private String nombre;
    private String codigo;
    private float creditos;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public float getCreditos() {
        return creditos;
    }

    public void setCreditos(float creditos) {
        this.creditos = creditos;
    }

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
