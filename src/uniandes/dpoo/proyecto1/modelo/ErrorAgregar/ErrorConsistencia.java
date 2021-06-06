package uniandes.dpoo.proyecto1.modelo.ErrorAgregar;

import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.EstadoRegistro;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;

public class ErrorConsistencia implements ErrorAgregar{
    private final EstadoRegistro er;
    private final CursoRegistrado cr;
    private final Periodo p;


    public ErrorConsistencia(EstadoRegistro er, CursoRegistrado cr, Periodo p){
        this.er = er;
        this.cr = cr;
        this.p = p;
    }

    @Override
    public String getTipe() {
        return "Incosistencia";
    }

    @Override
    public String getMesage() {
        if( er==EstadoRegistro.Inconsistente) {
            return String.format("Iconsistencia en el curso %s aprovado en %s , conflicto con registros posteriores en %s",
                    cr.getCurso().getCodigo(), cr.getPeriodo().toString(), p.toString());
        }
        else{
            return String.format("Repeticion en el curso %s en %s ya se encuentro registrado y/o aprovado en %s",
                    cr.getCurso().getCodigo(), cr.getPeriodo().toString(), p.toString());
        }
    }
}
