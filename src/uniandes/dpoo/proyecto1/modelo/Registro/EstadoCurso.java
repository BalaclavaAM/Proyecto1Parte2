package uniandes.dpoo.proyecto1.modelo.Registro;

<<<<<<< Updated upstream
<<<<<<< HEAD
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.NotaNum;

public class EstadoCurso{
    private boolean numerica = false;
    private Nota nota;
    private final Periodo periodo;
    private  boolean epsilon = false;
    private Estado estado;

    public EstadoCurso(Nota nota, Periodo periodo, Estado estado, boolean epsilon){
        this.nota = nota;
        this.periodo = periodo;
        this.epsilon = epsilon;
        this.estado = Estado.Finalizado;
        this.numerica = nota.isNumeric();
    }

    public EstadoCurso(Nota nota, Periodo periodo, Estado estado){
        this.nota = nota;
        this.periodo = periodo;
        this.estado = estado;
        this.estado = Estado.Finalizado;
        this.numerica = nota.isNumeric();
    }

    public EstadoCurso(Periodo periodo,Estado estado, boolean epsilon){
        this.periodo = periodo;
        this.epsilon = epsilon;
        this.estado = estado;

    }

    public EstadoCurso(Periodo periodo, Estado estado){
        this.periodo = periodo;
        this.estado = estado;
    }

    public Nota getNota(){
        return this.nota;
    }

    public boolean isNumerica() {
        return numerica;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public boolean isEpsilon() {
        return epsilon;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public static void main(String[] args) {
        Nota n = new NotaNum(3);
    }

=======
public enum EstadoCurso {
    Finalizado, Pendiente ,Inscrito, Retirado, Planeado;
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
=======
public enum EstadoCurso {
    Finalizado, Pendiente ,Inscrito, Retirado, Planeado;
>>>>>>> Stashed changes
}
