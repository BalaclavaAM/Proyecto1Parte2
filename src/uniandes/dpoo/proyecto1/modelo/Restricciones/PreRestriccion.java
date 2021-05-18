package uniandes.dpoo.proyecto1.modelo.Restricciones;


import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.MallaCursos;

import java.util.Map;

public interface PreRestriccion {

	//cursos es una lista de cursos que se agregan el el mismo periodo en el que se busca revisar la restriccion;

	public boolean cumple(MallaCursos malla);
	public boolean cumple(MallaCursos malla, Periodo periodo);
	public String tipo();
	public String nombre();
}