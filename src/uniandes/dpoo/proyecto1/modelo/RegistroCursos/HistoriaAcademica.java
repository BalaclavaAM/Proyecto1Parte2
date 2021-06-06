package uniandes.dpoo.proyecto1.modelo.RegistroCursos;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Pensum;
import uniandes.dpoo.proyecto1.modelo.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.EstadoCurso;
import uniandes.dpoo.proyecto1.modelo.Registro.RequerimientoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;


public class HistoriaAcademica extends MallaCursos implements Serializable {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = -491840464239633611L;
    private final Periodo primerPeriodo;
    private Periodo ultimoPeriodo;


    public HistoriaAcademica(Pensum pensum, Periodo periodo) {
        super(periodo);
        Periodo p = Periodo.copy(periodo);
        this.primerPeriodo = p;
        this.ultimoPeriodo = p;
        this.pensum = pensum;
    }


    public void actualizarNota(CursoRegistrado cursoR, Nota nota) {
        String codigo = cursoR.getCurso().getCodigo();
        String reqN = cursosValidados.get(codigo);
        Requerimiento reqAsociado = pensum.getCursosValidacionAuto().get(codigo);
        cursoR.setNota(nota);
        if (reqN != null) {
            if(!nota.aprobo()) {
                // se podria considerar actualizar la historia desde el periodo en adelante;
                reqsRegistrados.get(reqN).quitarCurso(codigo);
            }
        }else{
            if(reqAsociado != null){
                validarRequerimiento(cursoR,reqAsociado.getNombre());
            }
        }
    }





	public ArrayList<EstadoAgregar> inscripcionCursos(ArrayList<CursoRegistrado> cursosP) {
        ultimoPeriodo = Periodo.copy(peridoSistema);
        return agregarCursos(cursosP);
    }

    public void actualizarPeriodo(){
        if(peridoSistema.compare(ultimoPeriodo) == 1){
            for(CursoRegistrado cr: infoSemestres.get(ultimoPeriodo.periodoS())){
                if(peridoSistema.compare(cr.getPeriodo()) == 1){ //solo finalizar los del ciclo 1 si estamos en
                    cr.setEstado(EstadoCurso.Finalizado);
                }
            }
        }
    }

    @Override
    public void agregarPeriodo(Periodo periodo) {
        infoSemestres.putIfAbsent(periodo.periodoS(),new ArrayList<>());
    }

    @Override
    public CursoRegistrado getCurReg(String codigo) {
         return cursosRegistrados.get(codigo);
    }


    public EstadoRegistro cambiarRequerimiento(CursoRegistrado cursoR, String req2N){
        String reqN = cursosValidados.get(cursoR.getCurso().getCodigo());
        if(reqN == null){
            return EstadoRegistro.Inexistente;
        }
        RequerimientoRegistrado reqR =  reqsRegistrados.get(reqN);
        reqR.quitarCurso(cursoR.getCurso().getCodigo());
        EstadoRegistro val = validarRequerimiento(cursoR, req2N);
        if(val != EstadoRegistro.Ok){
            reqR.agregarCurso(cursoR); //devuelve el curso a su req original
            return val;
        }
        return val;
    }

    public EstadoRegistro quitarCursoReq(String codigo){
        String reqN = cursosValidados.get(codigo);
        if(reqN == null){
            return EstadoRegistro.Inexistente;
        }
        cursosValidados.remove(codigo);
        reqsRegistrados.get(reqN).quitarCurso(codigo);
        return EstadoRegistro.Ok;
    }

    public double calcularPromedioSemestre(String periodoS) {
		ArrayList<CursoRegistrado> cursosPs = infoSemestres.get(periodoS);
		int creditosSemestre = 0;
		double puntosSemestre = 0;

        for (CursoRegistrado cr: cursosPs) {
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
        for(ArrayList<CursoRegistrado> lp: infoSemestres.values()) {
            for (CursoRegistrado cr : lp) {
                if (cr.getNota().isNumeric()) {
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

	public double calcularPromedioAcademicoAntesdeP(String semestre){
        ArrayList<String> semestres = new ArrayList<>(infoSemestres.keySet());
        semestres.sort(String::compareTo);
        int indice = semestres.indexOf(semestre);
        double puntosSemestre = 0;
        double creditosSemestre = 0;
        for (int i = 0; i < indice; i++) {
            ArrayList<CursoRegistrado> lp = infoSemestres.get(semestres.get(i));
            for (CursoRegistrado cr : lp) {
                if (cr.getNota().isNumeric()) {
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

    public Periodo getPrimerPeriodo() {
        return primerPeriodo;
    }


    public Periodo getUltimoPeriodo() {
        return ultimoPeriodo;
    }

    public ArrayList<CursoRegistrado> getCursosInscritos() {
        return infoSemestres.get(peridoSistema.periodoS());
    }

    @Override
    public boolean dentroPeriodo(Periodo p) {
        if(p.periodoS().equals(peridoSistema.periodoS())){
            return p.getCiclo() >= peridoSistema.getCiclo();
        }
        return peridoSistema.compare(p) == 1 && primerPeriodo.compare(p) <= 0;
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

    public boolean limiteValido(String periodo){
        return true;
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


