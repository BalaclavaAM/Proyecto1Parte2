package uniandes.dpoo.proyecto1.modelo.Restricciones;

import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.EstadoAgregar;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.MallaCursos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Correquisito implements Restriccion{
    private final ArrayList<String> opciones;

    public Correquisito(ArrayList<String> opciones) {
        this.opciones = opciones;
    }


    private CursoRegistrado cumple(CursoRegistrado cursoR, MallaCursos malla,Map<String,Nodo> nodoMap,Periodo periodo){
        for (String codigo : opciones) {
            CursoRegistrado registro = malla.getCurReg(codigo);
            if (registro != null){
                if((malla.aprovado(registro) && registro.getPeriodo().compare(periodo) < 0) ||
                        (registro.getPeriodo().compare(periodo) == 0)) {
                    return registro;
                }
            }
            return cumpleEnInscripcion(cursoR,malla,codigo,nodoMap,periodo);
        }
        return null;
    }

    private CursoRegistrado cumpleEnInscripcion(CursoRegistrado cursoR,MallaCursos malla ,String opcion,
                                                Map<String,Nodo> nodoMap,Periodo periodo){
        Nodo dependencia = nodoMap.get(opcion);
        if(dependencia != null){
            Estado e = dependencia.estado;
            if(e == Estado.validado){
                return dependencia.cursoR;
            }else if(e == Estado.rechazado){
                return null;
            }
            else{
                if (cumpletodos(cursoR, malla, nodoMap, periodo)) {
                    return dependencia.cursoR;
                }
            }
        }
        return null;
    }

    private static boolean cumpletodos(CursoRegistrado cursoR, MallaCursos malla, Map<String,Nodo> nodoMap,Periodo periodo){
        Nodo nac = nodoMap.get(cursoR.getCurso().getCodigo());
        if(nac.recorrido && nac.estado == Estado.pendiente){
            //excepcion correquisitos ciclicos
            return false;
        }

        nac.recorrido();
        for(Correquisito cr: cursoR.getCurso().getCorrequisitos()){
            nac.temp = cr;
            if(cr.cumple(cursoR,malla,nodoMap,periodo) == null){
                nac.setRechazado();
                return false;
            }
        }
        nac.setValidado();
        return true;
    }



    public static ArrayList<CursoRegistrado> cursosCumple(ArrayList<CursoRegistrado> cursosP, MallaCursos malla,
                                                   Periodo periodo,ArrayList<EstadoAgregar> estado) {
        Map<String,Nodo> nodoMap = new HashMap<>();
        for(CursoRegistrado cr: cursosP){
            nodoMap.put(cr.getCurso().getCodigo(),new Nodo(cr));
        }

        for (int i = cursosP.size() - 1; i > -1; i--) {
            CursoRegistrado cursoR = cursosP.get(i);
            Nodo nac = nodoMap.get(cursoR.getCurso().getCodigo());
            Estado e =  nac.estado;
            if(e != Estado.validado){
                if (e == Estado.rechazado ||  !cumpletodos(cursoR, malla, nodoMap, periodo)) {
                    cursosP.remove(i);
                    nodoMap.remove(cursoR.getCurso().getCodigo());
                    estado.add(new EstadoAgregar(cursoR, nac.temp.nombre()));
                }
            }
        }
        return cursosP;
    }


    private enum Estado {
        pendiente, validado,rechazado
    }

    private static class Nodo {
        public Correquisito temp = null;
        public Estado estado;
        public CursoRegistrado cursoR;
        public boolean recorrido;
        public Nodo(CursoRegistrado cursoR){
            recorrido = false;
            this.cursoR = cursoR;
            estado = Estado.pendiente;
        }
        public void setValidado() {
            estado = Estado.validado;
        }
        public void setRechazado(){
            estado = Estado.rechazado;
        }

        public void recorrido(){
            recorrido = true;
        }

    }


    public String tipo () {
        return "Correquisito";
    }

    @Override
    public String nombre() {
        return opciones.toString();
    }
}