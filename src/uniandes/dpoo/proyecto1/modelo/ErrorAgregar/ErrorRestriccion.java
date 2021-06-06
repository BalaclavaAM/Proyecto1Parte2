package uniandes.dpoo.proyecto1.modelo.ErrorAgregar;

import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Restricciones.Restriccion;

public class ErrorRestriccion implements ErrorAgregar {
    private Restriccion restriccion;
    private CursoRegistrado cursoRegistrado;

    public ErrorRestriccion(Restriccion restriccion, CursoRegistrado cursoRegistrado){
        this.cursoRegistrado = cursoRegistrado;
        this.restriccion = restriccion;
    }

    @Override
    public String getTipe() {
        return "Restriccion";
    }

    @Override
    public String getMesage() {
        return String.format("El curso %s (%s) en el periodo incumple %s %s en el periodo %s",
                cursoRegistrado.getCurso().getCodigo(), cursoRegistrado.getCurso().getNombre(),
                restriccion.tipo(), restriccion.nombre(),cursoRegistrado.getPeriodo().toString());
    }
}
