package uniandes.dpoo.proyecto1.modelo.Registro;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Requerimientos.Requerimiento;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class RequerimientoRegistrado {
    private Requerimiento req;
    private int creditosCumplidos;
    private int itemsCumplidos;
    private boolean cumplido;
    private double porcentaje;
    private boolean validado;
    private ArrayList<Object> validaciones;
    private Map<String, Curso> cursos;

    public RequerimientoRegistrado(Requerimiento req){
        this.req = req;
        this.creditosCumplidos = 0;
        this.cumplido = false;
        this.porcentaje = 0.0;
        this.cursos = new Hashtable<>();
        this.itemsCumplidos = 0;

    }


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
    }

    public int getCreditosCumplidos() {
        return creditosCumplidos;
    }

    public Requerimiento getReq() {
        return req;
    }

    public boolean getCumplido(){
        return cumplido;
    }

    public double getPorcentaje(){
        return porcentaje;
    }
    
    

}
