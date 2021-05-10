package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.EstadoCurso;
import uniandes.dpoo.proyecto1.modelo.Registro.RequerimientoRegistrado;

import java.util.Map;

public class Plan extends MallaCursos {
    public final EstadoCurso estadoPl = EstadoCurso.Planeado;
    private final HistoriaAcademica historia;
    private final String nombre;

    public Plan(HistoriaAcademica historia, String nombre, Periodo periodoInicio) {
        super(historia.peridoSistema, periodoInicio);
        this.pensum = historia.pensum;
        this.historia = historia;
        this.nombre = nombre;
    }

    public void validarInscritos(){ //los cursos incritos se tomarian como aprovados
        for(CursoRegistrado ci: historia.getCursosInscritos().values()){
            validarInscrito(ci);
        }
    }

    public String getNombre() {
        return nombre;
    }

    public void validarInscrito(CursoRegistrado ci){
        boolean ep = ci.getEpsilon();
        Periodo pi = ci.getPeriodo();
        modificarHistoria(new CursoRegistrado(ci.getCurso(),estadoPl,ep,pi), EstadoRegistro.Ok);
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


