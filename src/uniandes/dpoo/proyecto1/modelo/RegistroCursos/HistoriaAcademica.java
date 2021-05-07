package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Nota.*;
import uniandes.dpoo.proyecto1.modelo.Registro.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;


public class HistoriaAcademica extends MallaCursos implements Serializable {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -491840464239633611L;
    private Map<String, CursoRegistrado> cursosInscritos;


    public HistoriaAcademica(Pensum pensum, Periodo periodo) {
        super(periodo,periodo);
		this.pensum = pensum;
		this.cursosRegistrados = new Hashtable<>();
		this.reqsRegistrados = new Hashtable<>();
		this.cursosValidados = new Hashtable<>();
		this.cursosInscritos = new Hashtable<>();
		this.infoSemestre = new Hashtable<>();
	}


    public boolean actualizarNota(Curso curso, String periodoS, Nota nota) {
        Map<String,CursoRegistrado> Mcr = infoSemestre.get(periodoS);
        if(Mcr!=null){
            CursoRegistrado cr = Mcr.get(curso.getCodigo());
            if(cr !=null){
                cr.setNota(nota);
                String codigo = cr.getCurso().getCodigo();
                String reqAsociado = pensum.getCursosValidacionAuto().get(codigo);
                if (reqAsociado != null) {
                    validarRequerimiento(codigo, reqAsociado);
                    }
                }
                return true;
            }
        return false;
    }

    public void vaciarInscritos(){
        for(CursoRegistrado cursoR: cursosInscritos.values()) {
            cursoR.setEstado(EstadoCurso.Finalizado);
            cursosInscritos.remove(cursoR.getCurso().getCodigo());
        }
    }


	public ArrayList<EstadoAgregar> inscripcionCursos(Map<String, CursoRegistrado> cursosP) {
        ArrayList<EstadoAgregar> estado = new ArrayList<>();
        if(!ultimoPeriodo.periodoS().equals(peridoSistema.periodoS()) && !cursosInscritos.isEmpty()){
            vaciarInscritos();
            infoSemestre.putIfAbsent(Periodo.periodoS(peridoSistema.getAnio(),peridoSistema.getSemestre()),new Hashtable<>());
        }
        agregarCursosPeriodo(cursosP,estado);
        return estado;
    }


    @Override
    public CursoRegistrado getCurReg(String codigo) {
         return cursosRegistrados.get(codigo);
    }


    public EstadoRegistro cambiarRequerimiento(String codigoCurso, String req1N, String req2N){
        RequerimientoRegistrado reqR1 =  reqsRegistrados.get(req1N);
        Curso curso = cursosRegistrados.get(codigoCurso).getCurso();
        if(req1N == null  || curso == null){
            return EstadoRegistro.Inexistente;
        }
        EstadoRegistro val = validarRequerimiento(codigoCurso, req2N);
        if(val != EstadoRegistro.Ok){
            return val;
        }
        if(reqR1.quitarCurso(codigoCurso)){
            return EstadoRegistro.Ok;
        }
        return EstadoRegistro.Inexistente;
    }



    public double calcularPromedioSemestre(String periodoS) {
		Map<String,CursoRegistrado> cursosPs = infoSemestre.get(periodoS);
		int creditosSemestre = 0;
		double puntosSemestre = 0;

        for (CursoRegistrado cr: cursosPs.values()) {
            if (cr.isNumerica()) {
                int creditosCurso = cr.getCurso().getCreditos();
                creditosSemestre += creditosCurso;
                puntosSemestre += creditosCurso * cr.getNota().notaNum();
            }
        }

		if(creditosSemestre >0){
			return puntosSemestre/creditosSemestre;
		}
		return 0;
	}


	public double calcularPromedioAcademico() {
        double puntosSemestre = 0;
        double creditosSemestre = 0;
        for(Map<String, CursoRegistrado> mp: infoSemestre.values()) {
            for (CursoRegistrado cr : mp.values()) {
                if (cr.isNumerica()) {
                    int creditosCurso = cr.getCurso().getCreditos();
                    creditosSemestre += creditosCurso;
                    puntosSemestre += creditosCurso * cr.getNota().notaNum();
                }
            }
        }
		if(creditosSemestre >0){
			return puntosSemestre/creditosSemestre;
		}

		return 0;
	}

    public float getCreditos() {
        return creditos;
    }

    public Map<String, CursoRegistrado> getCursosInscritos() {
        return cursosInscritos;
    }

    @Override
    public boolean dentroPeriodo(Periodo p) {
        return peridoSistema.compare(p) == 1 && periodoInicio.compare(p) <= 0;
    }

    @Override
    public Periodo getPHis() {
        return peridoSistema;
    }


    @Override
    public boolean aprovado(CursoRegistrado cursoR) {
        return cursoR.getNota().aprobo();
    }

    @Override
    public int itemsCumplidos(String reqN) {
        RequerimientoRegistrado rR = reqsRegistrados.get(reqN);
        if(rR != null && peridoSistema.compare(rR.ultimoPeriodo()) == 1){
            return rR.getItemsCumplidos();
        }
        return 0;
    }

    @Override
    public int itemsCumplidos(String reqN, Periodo periodo) {
        RequerimientoRegistrado rR = reqsRegistrados.get(reqN);
        if(rR != null && periodo.compare(rR.ultimoPeriodo()) == 1){
            return rR.getItemsCumplidos();
        }
        return 0;
    }


    /*public static void main(String[] args) {
        Pensum psm = new Pensum("Sistemas");
        ArrayList<String> opc = new ArrayList<>();
        opc.add("de");
        opc.add("ss");
        Restriccion r1 = new RestriccionNivel(Nivel.uno);
        Restriccion r2 = new Correquisito(opc);
        Periodo p = new Periodo(2,2,1);
        HistoriaAcademica h = new HistoriaAcademica(psm,p);
        ArrayList<Restriccion> res = new ArrayList<>();
        res.add(r2);
        Curso cur1 = new Curso("ss","321","ISIS",3,8,true,"dd",res);
        System.out.println(cur1.getRestricciones().get(0));
        CursoRegistrado cr = new CursoRegistrado(cur1, new NotaCual(calCual.pendiente),EstadoCurso.Inscrito,false,p);
        Map<String,CursoRegistrado> lcr = new Hashtable<>();
        lcr.put(cr.getCurso().getCodigo(),cr);
        ArrayList<EstadoAgregar> ea = h.inscripcionCursos(lcr,p);
        System.out.println(cr.getEstadoAgregar().getError());
        System.out.println(cr.getEstadoAgregar().getCurso());
        System.out.println(cr.getEstadoAgregar().getRest());
    }*/
}


