package uniandes.dpoo.proyecto1.modelo.Registro;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.EstadoRegistro;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

public class RequerimientoRegistrado {
    private Requerimiento req;
    private int creditosCumplidos = 0;
    private int itemsCumplidos = 0;
    private boolean validado;
    private ArrayList<Object> validaciones;
    private Periodo ultimoPeriodo;
    private Map<String, CursoRegistrado> cursosR;


    public RequerimientoRegistrado(Requerimiento req){
        this.req = req;
        this.creditosCumplidos = 0;
        Map<String, Curso> cursos = new Hashtable<>();
        this.cursosR = new Hashtable<>();
        this.itemsCumplidos = 0;
    }




    public EstadoRegistro agregarCurso(CursoRegistrado cursoR){
        Periodo periodo = cursoR.getPeriodo();
        Curso curso = cursoR.getCurso();
        String codigo = curso.getCodigo();
        int val = req.validar(curso);
        if( val == 0 ){
            return EstadoRegistro.Inexistente;
        }
        CursoRegistrado registroPrevio = cursosR.get(codigo);
        if(registroPrevio != null) {
            if (cursoR.getEstado() == EstadoCurso.Planeado) {
                if(cursoR.getPeriodo().compare(registroPrevio.getPeriodo())== 1){
                    quitarCurso(codigo);
                }else{
                    return EstadoRegistro.Repetido;
                }
            }else{
                return EstadoRegistro.Repetido;
            }
        }
        if(cumplio()){
            return EstadoRegistro.SobreInscripcion;
        }
        cursosR.put(codigo,cursoR);
        this.itemsCumplidos += val;
        this.creditosCumplidos += curso.getCreditos();
        if(periodo.compare(ultimoPeriodo) == 1){
            ultimoPeriodo = periodo;
        }
        if(cumplio()){
            validado = true;
        }
        return EstadoRegistro.Ok;
    }

    public boolean cumplio(){
        return itemsCumplidos>req.getItems();
    }

    public EstadoRegistro quitarCurso(String codigo){
        CursoRegistrado cursoR = cursosR.get(codigo);
        if(cursoR == null){
            return EstadoRegistro.Inexistente;
        }
        cursosR.remove(codigo);
        Curso curso = cursoR.getCurso();
        int val = req.validar(curso);
        itemsCumplidos -= val;
        creditosCumplidos -= curso.getCreditos();
        if(cursoR.getPeriodo().compare(ultimoPeriodo) == 0){
            this.ultimoPeriodo = ultimoPeriodo();
        }
        if(cumplio() != validado){
            validado = !validado;
        }
        return EstadoRegistro.Ok;
    }

    public Periodo ultimoPeriodo(){
        Periodo mayor = null;
        for (CursoRegistrado cR: cursosR.values()) {
            if(mayor == null ||  cR.getPeriodo().compare(mayor) == 1){
                mayor = cR.getPeriodo();
            }
        }
        return mayor;
    }

    public int getCreditosCumplidos() {
        return creditosCumplidos;
    }




    public int getItemsCumplidos() {
        return itemsCumplidos;
    }



    public Requerimiento getReq() {
        return req;
    }


    public Map<String, CursoRegistrado> getCursosR() {
        return cursosR;
    }





}
