package uniandes.dpoo.proyecto1.modelo.ErrorAgregar;

import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;

public class ErrorLimite implements ErrorAgregar{
    private CursoRegistrado cursoRegistrado;

    public ErrorLimite(CursoRegistrado cursoRegistrado){
        this.cursoRegistrado = cursoRegistrado;
    }

    @Override
    public String getTipe() {
        return "Limite";
    }

    @Override
    public String getMesage() {
        return String.format("No se pudo inscribir el curso %s exede el limite de creditos para el semestre %s",
                cursoRegistrado.getCurso().getCodigo(),cursoRegistrado.getPeriodo().periodoS());
    }
}
