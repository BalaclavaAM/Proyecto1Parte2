package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Registro.RequerimientoRegistrado;
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
        for(CursoRegistrado ci: historia.getCursosInscritos().values()){
            boolean ep = ci.getEpsilon();
            modificarHistoria(
                    new CursoRegistrado(ci.getCurso(),notaPlan,EstadoCurso.Planeado,ep,ci.getPeriodo()), EstadoRegistro.Ok);
        }
    }

    public ArrayList<EstadoAgregar> agregarCursos(ArrayList<CursoRegistrado> cursosR){
        return super.agregarCursos(cursosR);
    }

    @Override
    public CursoRegistrado getCurReg(String codigo) {
        CursoRegistrado c2 = cursosRegistrados.get(codigo);
        if (c2 != null) {
            return c2;
        }
        return cursosRegistrados.get(codigo);
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

    @Override
    public boolean aprovado(CursoRegistrado cursoR) {
        return cursoR.getEstado() == EstadoCurso.Planeado || cursoR.getNota().aprobo() || cursoR.getEstado() == EstadoCurso.Inscrito;
    }
    @Override
    public int itemsCumplidos(String reqN) {
        int sum = 0;
        RequerimientoRegistrado rR1 = historia.reqsRegistrados.get(reqN);
        if(rR1 != null){
            sum += rR1.getItemsCumplidos();
        }
        RequerimientoRegistrado rR2 = reqsRegistrados.get(reqN);
        if(rR1 != null){
            sum += rR2.getItemsCumplidos();
        }
        return sum;
    }


    @Override
    public int itemsCumplidos(String reqN, Periodo periodo) {
        int sum = 0;
        RequerimientoRegistrado rR1 = historia.reqsRegistrados.get(reqN);
        if(rR1 != null && periodo.compare(rR1.ultimoPeriodo()) == 1){
            sum += rR1.getItemsCumplidos();
        }
        RequerimientoRegistrado rR2 = reqsRegistrados.get(reqN);
        if(rR1 != null && periodo.compare(rR1.ultimoPeriodo()) == 1){
            sum += rR2.getItemsCumplidos();
        }
        return sum;
    }

    public Map<String, CursoRegistrado> getCursosRegistrados() {
        return cursosRegistrados;
    }

    public HistoriaAcademica getHistoria() {
        return historia;
    }
}


