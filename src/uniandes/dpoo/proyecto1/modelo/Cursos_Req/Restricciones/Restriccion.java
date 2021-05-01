package uniandes.dpoo.proyecto1.modelo.Cursos_Req.Restricciones;


import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;

import java.util.ArrayList;

public interface Restriccion {

	public boolean cumple(Plan plan);
	public boolean cumple(Plan plan, Periodo periodo);
	public boolean cumple(Plan plan, ArrayList<Curso> cursos, Periodo periodo);
	public boolean cumple(HistoriaAcademica historia);
	public boolean cumple(HistoriaAcademica historia, Periodo periodo);
	public boolean cumple(HistoriaAcademica historiaAcademica, ArrayList<Curso> cursos, Periodo periodo);

	public String tipo();
}
