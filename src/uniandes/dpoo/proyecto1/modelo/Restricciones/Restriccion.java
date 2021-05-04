package uniandes.dpoo.proyecto1.modelo.Restricciones;


import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.MallaCursos;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;

import java.util.ArrayList;
import java.util.Map;

public interface Restriccion {

	//cursos es una lista de cursos que se agregan el el mismo periodo en el que se busca revisar la restriccion;

	public boolean cumple(MallaCursos malla);
	public boolean cumple(MallaCursos malla, Map<String,CursoRegistrado> cursosP);

	public boolean cumple(MallaCursos malla, Periodo periodo);
	public boolean cumple(MallaCursos malla, Map<String,CursoRegistrado> cursosP, Periodo periodo);

	public String tipo();
}
