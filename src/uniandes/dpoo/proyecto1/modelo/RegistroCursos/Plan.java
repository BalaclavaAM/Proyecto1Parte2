package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Nota.NotaCual;
import uniandes.dpoo.proyecto1.modelo.Nota.calCual;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.EstadoCurso;
import uniandes.dpoo.proyecto1.modelo.Registro.RequerimientoRegistrado;

import java.util.ArrayList;

public class Plan extends MallaCursos {
    private final HistoriaAcademica historia;
    private final String nombre;
    private Periodo primerPeriodo;
    private Periodo ultimoPeriodo;


    public Plan(String nombre, HistoriaAcademica historia) {
        super(historia.getPeridoSistema());
        this.pensum = historia.pensum;
        this.historia = historia;
        this.nombre = nombre;
        validarInscritos();
    }

    public void validarInscritos(){ //los cursos incritos se tomarian como aprovados
        infoSemestres.putIfAbsent(Periodo.copy(peridoSistema).periodoS(),new ArrayList<>());
        for(CursoRegistrado ci: historia.getCursosInscritos()){
            validarInscrito(ci);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void validarInscrito(CursoRegistrado ci){
        CursoRegistrado cp = new CursoRegistrado(ci.getCurso(),new NotaCual(calCual.A),ci.getEpsilon(),ci.getPeriodo());
        cp.setEstado(EstadoCurso.Inscrito);
        agregarCurso(cp,EstadoRegistro.Ok);
    }


    @Override
    public void agregarPeriodo(Periodo periodo) {
        infoSemestres.putIfAbsent(periodo.periodoS(),new ArrayList<>());
        if(primerPeriodo == null){
            primerPeriodo = periodo;
            ultimoPeriodo = periodo;
        }else{
            if(primerPeriodo.compare(periodo) == 1){
                primerPeriodo = periodo;
            }else if (periodo.compare(ultimoPeriodo) == 1) {
                    ultimoPeriodo = periodo;
            }
        }
    }

    @Override
    public CursoRegistrado getCurReg(String codigo) {
        CursoRegistrado c2 = cursosRegistrados.get(codigo);
        if (c2 != null) {
            return c2;
        }
        return historia.cursosRegistrados.get(codigo);
    }



    @Override
    public boolean dentroPeriodo(Periodo p) {
        return historia.getPeridoSistema().compare(p) == -1;
    }

    @Override
    public Periodo getPHis() {
        return historia.peridoSistema;
    }

    @Override
    public boolean aprovado(CursoRegistrado cursoR) {
        return cursoR.getEstado() == EstadoCurso.Planeado || cursoR.getNota().aprobo();
    }
    @Override
    public int itemsCumplidos(String reqN) {
        int sum = 0;
        RequerimientoRegistrado rR1 = historia.reqsRegistrados.get(reqN);
        if(rR1 != null){
            sum += rR1.getItemsCumplidos();
        }
        RequerimientoRegistrado rR2 = reqsRegistrados.get(reqN);
        if(rR2 != null){
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
        if(rR2 != null && periodo.compare(rR2.ultimoPeriodo()) == 1){
            sum += rR2.getItemsCumplidos();
        }
        return sum;
    }


    public HistoriaAcademica getHistoria() {
        return historia;
    }

    public Periodo getUltimoPeriodo() {
        return ultimoPeriodo;
    }

    public Periodo getPrimerPeriodo() {
        return primerPeriodo;
    }

}


