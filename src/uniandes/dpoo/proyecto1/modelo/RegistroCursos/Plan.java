package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Restricciones.Restriccion;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.EstadoCurso;
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.NotaCual;
import uniandes.dpoo.proyecto1.modelo.Registro.Nota.calCual;
import uniandes.dpoo.proyecto1.modelo.Registro.Periodo;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class Plan extends MallaCursos {
    public final EstadoCurso estadoPl = EstadoCurso.Planeado;
    public final NotaCual  notaPlan = new NotaCual(calCual.planeado);
    private HistoriaAcademica historia;

    public Plan(HistoriaAcademica historia) {
        this.pensum = historia.pensum;
        this.periodo = historia.periodo;
        this.historia = historia;
    }



    public Map<String, CursoRegistrado> getCursosRegistrados() {
        return cursosRegistrados;
    }



    public ArrayList<EstadoAgregar> agregarCursos(ArrayList<Curso> cursos, ArrayList<Periodo> periodos) {
        ArrayList<EstadoAgregar> estado = new ArrayList<>();
        Map<Periodo, ArrayList<Curso>> cursosPeriodos = new Hashtable<>();
        ArrayList<Periodo> Lperiodos = new ArrayList<>();
        Map<Curso, Integer> infoCursos = new Hashtable<>();
        formatoAgregar(cursos, periodos, cursosPeriodos, infoCursos, Lperiodos, estado);
        agregarCursosAux(Lperiodos, cursosPeriodos,estado);

        return estado;
    }

    public void agregarCursosAux(ArrayList<Periodo> Lperiodos, Map<Periodo, ArrayList<Curso>> cursosPeriodos,
                             ArrayList<EstadoAgregar> estado){
        for (Periodo p : Lperiodos) {
            ArrayList<Curso> cursosP = cursosPeriodos.get(p);
            if(historia.getPeriodo().compare(p) == -1){
                agregarPeriodo(p);
                for (Curso c : cursosP) {
                    int val = agregarCursoxPeriodo(c,estadoPl,cursosP, p);
                    if (val != 1) {
                        estado.add(new EstadoAgregar(val,p,c));
                    }
                }
            }else{
                estado.add(new EstadoAgregar(5,p));
                break;
            }
        }
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



    public HistoriaAcademica getHistoria() {
        return historia;
    }
}


