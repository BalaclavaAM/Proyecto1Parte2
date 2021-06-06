package uniandes.dpoo.proyecto1.modelo.Restricciones;


import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.MallaCursos;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;

public interface PreRestriccion extends Restriccion {
	//cursos es una lista de cursos que se agregan el el mismo periodo en el que se busca revisar la restriccion;
	boolean cumple(MallaCursos malla);

	boolean cumple(CursoRegistrado cursoR, MallaCursos malla, Periodo periodo);

	String tipo();

	String nombre();
}
