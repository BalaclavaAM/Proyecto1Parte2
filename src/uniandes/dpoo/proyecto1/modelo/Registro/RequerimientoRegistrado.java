package uniandes.dpoo.proyecto1.modelo.Registro;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
<<<<<<< HEAD
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Requerimientos.Requerimiento;
=======
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class RequerimientoRegistrado {
    private Requerimiento req;
    private int creditosCumplidos;
    private int itemsCumplidos;
<<<<<<< HEAD
    private boolean cumplido;
    private double porcentaje;
    private boolean validado;
    private ArrayList<Object> validaciones;
    private Map<String, Curso> cursos;
=======
    private boolean validado = false;
    private ArrayList<Object> validaciones;
    private Periodo ultimoPeriodo;
    private Map<String, CursoRegistrado> cursosR;
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e

    public RequerimientoRegistrado(Requerimiento req){
        this.req = req;
        this.creditosCumplidos = 0;
<<<<<<< HEAD
        this.cumplido = false;
        this.porcentaje = 0.0;
        this.cursos = new Hashtable<>();
=======
        this.cursosR = new Hashtable<>();
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
        this.itemsCumplidos = 0;

    }


<<<<<<< HEAD
    public int agregarCurso(Curso curso){
        String codigo = curso.getCodigo();
        int val = req.validar(curso);
        if( val == 0){
            return 0;
        }
        if(cursos.containsKey(codigo)){
            return 2;
        }
        cursos.put(codigo,curso);
        this.itemsCumplidos += val;
        this.creditosCumplidos += curso.getCreditos();
        return 1;
    }


    public void quitarCurso(Curso curso){
        cursos.remove(curso.getCodigo());
        int val = req.validar(curso);
        itemsCumplidos -= val;
        creditosCumplidos -= curso.getCreditos();
=======
    public int agregarCurso(CursoRegistrado cursoR, Periodo periodo){
        Curso curso = cursoR.getCurso();
        String codigo = curso.getCodigo();
        int val = req.validar(curso);
        if( val == 0 ){
            return 0;
        }
        if(cursosR.containsKey(codigo) || cumplio()){
            return 2;
        }
        cursosR.put(codigo,cursoR);
        this.itemsCumplidos += val;
        this.creditosCumplidos += curso.getCreditos();
        if(periodo.compare(ultimoPeriodo) == 1){
            ultimoPeriodo = periodo;
        }
        return 1;
    }

    public boolean cumplio(){
        return itemsCumplidos>req.getItems();
    }

    public boolean quitarCurso(String codigo){
        CursoRegistrado cursoR = cursosR.get(codigo);
        if(cursoR == null){
            return false;
        }
        Curso curso = cursoR.getCurso();
        cursosR.remove(codigo);
        int val = req.validar(curso);
        itemsCumplidos -= val;
        creditosCumplidos -= curso.getCreditos();
        if(cursoR.getPeriodo().compare(ultimoPeriodo) == 0){
            this.ultimoPeriodo = ultimoPeriodo();
        }
        return true;
    }

    public Periodo ultimoPeriodo(){
        Periodo mayor = null;
        for (CursoRegistrado cR: cursosR.values()) {
            if(mayor == null ||  cR.getPeriodo().compare(mayor) == 1){
                mayor = cR.getPeriodo();
            }
        }
        return mayor;
>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
    }

    public int getCreditosCumplidos() {
        return creditosCumplidos;
    }

<<<<<<< HEAD
    public Requerimiento getReq() {
        return req;
    }

    public boolean getCumplido(){
        return cumplido;
    }

    public double getPorcentaje(){
        return porcentaje;
    }
    
=======
    public int getItemsCumplidos() {
        return itemsCumplidos;
    }

    public Map<String, CursoRegistrado> getCursosR() {
        return cursosR;
    }

    public Requerimiento getReq() {
        return req;
    }



>>>>>>> 6580ca4ff77c3ec51f62719aaf850025599dfa8e
    

}
