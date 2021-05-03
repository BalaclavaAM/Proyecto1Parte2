package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Restricciones.Restriccion;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.EstadoCurso;
import uniandes.dpoo.proyecto1.modelo.Nota.NotaCual;
import uniandes.dpoo.proyecto1.modelo.Nota.calCual;
import uniandes.dpoo.proyecto1.modelo.Registro.Periodo;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Plan extends MallaCursos {
    public final EstadoCurso estadoPl = EstadoCurso.Planeado;
    public final NotaCual  notaPlan = new NotaCual(calCual.planeado);
    private final HistoriaAcademica historia;
    private final ArrayList<Nota> auxnotas = new ArrayList<>();
    private final ArrayList<Boolean> auxbolean = new ArrayList<>();

    public Plan(HistoriaAcademica historia) {
        this.pensum = historia.pensum;
        this.periodo = historia.periodo;
        this.historia = historia;
    }

    public void validarInscritos(){
        for(Curso ci: historia.getCursosInscritos().values()){
            CursoRegistrado regist = new CursoRegistrado(ci,notaPlan,estadoPl, false, periodo);
            cursosRegistrados.put(ci.getCodigo(), regist);
            modificarHistoria(regist, periodo);
        }
    }

    public ArrayList<EstadoAgregar> agregarCursos(ArrayList<Curso> cursos, ArrayList<Periodo> periodos){
        return super.agregarCursos(cursos,periodos,auxnotas,auxbolean);
    }

    @Override
    public CursoRegistrado getCurReg(String codigo) {
        CursoRegistrado c1 = historia.cursosRegistrados.get(codigo);
        if (c1 != null){
            CursoRegistrado c2 = cursosRegistrados.get(codigo);
            if(c2 != null){
                return c2;
            }
            return c1;
        }
        return null;
    }

    @Override
    public int revisarRestriciones(Curso curso, ArrayList<Curso> cursosP, Periodo periodo) {
        ArrayList<Restriccion> restriccions = curso.getRestricciones();
        for(Restriccion rst: restriccions){
            if(!rst.cumple(this, cursosP, periodo)){
                return 0;
            }
        }
        return 1;
    }


    @Override
    public boolean dentroPeriodo(Periodo p) {
        return historia.getPeriodo().compare(p) == -1;
    }

    @Override
    public Periodo getPHis() {
        return historia.periodo;
    }

    @Override
    public Nota getauxNota(int i, ArrayList<Nota> notas) {
        return notaPlan;
    }

    @Override
    public boolean getauxEps(int i, ArrayList<Boolean> epsilons) {
        return false;
    }

    @Override
    public EstadoCurso getauxEsC(Nota nota) {
        return estadoPl;
    }

    public Map<String, CursoRegistrado> getCursosRegistrados() {
        return cursosRegistrados;
    }

    public HistoriaAcademica getHistoria() {
        return historia;
    }
}


