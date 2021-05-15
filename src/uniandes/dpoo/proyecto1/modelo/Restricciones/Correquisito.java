package uniandes.dpoo.proyecto1.modelo.Restricciones;

import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.EstadoAgregar;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.MallaCursos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Correquisito{
    private final ArrayList<String> opciones;

    public Correquisito(ArrayList<String> opciones) {
        this.opciones = opciones;
    }


    public boolean cumpleRegistro(MallaCursos malla, Periodo periodo){
        for (String codigo : opciones) {
            CursoRegistrado cursoR = malla.getCurReg(codigo);
            if (cursoR != null){
                if((malla.aprovado(cursoR) && cursoR.getPeriodo().compare(periodo) < 0) ||
                        (cursoR.getPeriodo().compare(periodo) == 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static ArrayList<Correquisito> CorrequisitosEnRegistro(MallaCursos malla, CursoRegistrado cr, Periodo periodo){
        //retorna los correquisitos faltantes
        ArrayList<Correquisito> correquisitosFaltantes = new ArrayList<>();
        for(Correquisito correquisito: cr.getCurso().getCorrequisitos()){
            if(!correquisito.cumpleRegistro(malla,periodo)){
                correquisitosFaltantes.add(correquisito);
            }
        }
        return correquisitosFaltantes;
    }


    public static ArrayList<CursoRegistrado> cumpleEnInscripcion(Map<CursoRegistrado, ArrayList<Correquisito>> mapCorrequisitos,
                                                                 ArrayList<EstadoAgregar> estado){
        ArrayList<CursoRegistrado> cumplidos = new ArrayList<>();
        Map<String, Nodo> mapNodos = new HashMap<>();
        for (CursoRegistrado cr: mapCorrequisitos.keySet()){
            mapNodos.put(cr.getCurso().getCodigo(), new Nodo(cr, mapCorrequisitos.get(cr)));
        }
        Map<String, Nodo> mapNodosCop = new HashMap<>(mapNodos);
        for(Nodo nodo: mapNodos.values()){
            Correquisito corrIncumplido = extenderbrazos(nodo,mapNodosCop);
            if(corrIncumplido == null){
                cumplidos.add(nodo.cursoR);
            }else{
                estado.add(new EstadoAgregar(nodo.cursoR,"Correquisito"));
                mapNodosCop.remove(nodo.codigo);
            }
        }
        return cumplidos;
    }

    public static Correquisito extenderbrazos(Nodo nodo, Map<String, Nodo> mapNodos){
        if(nodo.estado == Estado.validado){
            return null;
        }
        for(Correquisito corr: nodo.faltantes){
            boolean estado = false;
            for(String codigo:corr.opciones){
                if(mapNodos.containsKey(codigo)){
                   if(extenderbrazos(mapNodos.get(codigo), mapNodos) == null){
                       estado = true;
                       break;
                   }
                }
            }
            if(!estado) {
                nodo.setEstado(Estado.rechazado);
                return corr;
            }
        }
        nodo.setEstado(Estado.validado);
        return null;
    }


    enum Estado {
        pendiente, validado,rechazado;
    }

    static class Nodo {
        public Estado estado;
        public String codigo;
        public CursoRegistrado cursoR;
        public ArrayList<Correquisito> faltantes;
        public Nodo(CursoRegistrado cursoR, ArrayList<Correquisito> correquisitos){
            this.codigo = cursoR.getCurso().getCodigo();
            this.cursoR = cursoR;
            if(correquisitos.size() == 0){
                estado = Estado.validado;
            }else{
                estado = Estado.pendiente;
            }
            this.faltantes = correquisitos;
        }
        public void setEstado(Estado estado) {
            this.estado = estado;
        }
    }


    public String tipo () {
        return "Correquisito";
    }

}