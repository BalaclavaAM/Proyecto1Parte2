package uniandes.dpoo.proyecto1.modelo;

import java.util.ArrayList;

public class RequerimientoHistoria {
    private Requerimiento req;
    private int creditosCumplidos;
    private boolean cumplido;
    private double porcentaje;
    private ArrayList<CursoVisto> cursos;

    public RequerimientoHistoria(Requerimiento req){
        this.req = req;
        this.creditosCumplidos = 0;
        this.cumplido = false;
        this.porcentaje = 0.0;
        this.cursos = new ArrayList<>();
    }
    public boolean agregarCurso(CursoVisto curso){
        if(req.validar(curso.getCurso()) & curso.getNota()>3){
            cursos.add(curso);
            this.creditosCumplidos +=  curso.getCurso().getCreditos();
            this.porcentaje = (double)creditosCumplidos/req.getCreditos();
            if(creditosCumplidos >= req.getCreditos()){
                this.cumplido = true;
            }
            return true;
        }
        return false;
    }
    
    public void quitarCurso(CursoVisto curso){
        cursos.remove(curso);
        this.creditosCumplidos -=  curso.getCurso().getCreditos();
        this.porcentaje = (double)creditosCumplidos/req.getCreditos();
        if(creditosCumplidos < req.getCreditos()){
            this.cumplido = false;
        }
    }

    public boolean getCumplido(){
        return cumplido;
    }

    public double getPorcentaje(){
        return porcentaje;
    }
    
    

}
