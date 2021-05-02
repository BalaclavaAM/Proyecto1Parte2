package uniandes.dpoo.proyecto1.modelo.Cursos_Req.Restricciones;


import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;

import java.util.ArrayList;

public interface Restriccion {

	//cursos es una lista de cursos que se agregan el el mismo periodo en el que se busca revisar la restriccion;

	public boolean cumple(Plan plan);
	// para agreagar un solo curso al final del plan (util para nuevo plan)
	public boolean cumple(Plan plan,ArrayList<Curso> cursos );
	// para agreagar cursos al final del plan (util para nuevo plan)
	public boolean cumple(Plan plan, Periodo periodo);
	// para agreagar un solo curso en un periodo(util para editar plan)
	public boolean cumple(Plan plan, ArrayList<Curso> cursos, Periodo periodo);
	//para agreagar cursos en un periodo(util para editar plan)
	public boolean cumple(HistoriaAcademica historia);
	// para agreagar un solo curso al final de la historia (util para inscripcion)
	public boolean cumple(HistoriaAcademica historia,ArrayList<Curso> cursos );
	// para agreagar un solo curso al final de la historia (util para inscripcion)
	public boolean cumple(HistoriaAcademica historia, Periodo periodo);
	// para agreagar un solo curso en un periodo(util para editar historia)
	public boolean cumple(HistoriaAcademica historiaAcademica, ArrayList<Curso> cursos, Periodo periodo);
	// para agreagar cursos en un periodo(util para editar historia)

	public String tipo();
}
