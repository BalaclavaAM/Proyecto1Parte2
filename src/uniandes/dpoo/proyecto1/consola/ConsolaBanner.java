package uniandes.dpoo.proyecto1.consola;

import uniandes.dpoo.proyecto1.procesamiento.*;
import uniandes.dpoo.proyecto1.modelo.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

public class ConsolaBanner {

	private ProcesadorBanner procesador;


	public static void main(String[] args)
	{
//		Curso cursoejemplo = new Curso("Introducción a la Programación","ISIS1221","ISIS","16","Fundamentación", false);
//
//		ArrayList <Curso> arreglocursos = new ArrayList<Curso>();
//		arreglocursos.add(cursoejemplo);
//
//		Requerimiento requerimiento1 = new Requerimiento("Programación1",1,arreglocursos);
//
//		ArrayList <Requerimiento> arreglorequerimiento = new ArrayList<Requerimiento>();
//		arreglorequerimiento.add(requerimiento1);
//
//		Pensum pensumejemplo = new Pensum(160,"ISIS2020",arreglorequerimiento);
//
//		ArrayList <Pensum> arreglopensum = new ArrayList<Pensum>();
//		arreglopensum.add(pensumejemplo);
//
//		Carrera carreraejemplo = new Carrera("Ingeniería de Sistemas y Computación","ISIS",arreglopensum);
//
//		List<Curso> cursos = new ArrayList<>();
//		cursos.add(cursoejemplo);
//
//		List<Carrera> carreras = new ArrayList<>();
//		carreras.add(carreraejemplo);
//		
//		HistoriaAcademica historia = new HistoriaAcademica();
//
//		Estudiante estudiantejemplo = new Estudiante("pe.yerson","vivamillonarios123","Pablo Esteban Yerso Nando","202014198",historia);
//
//		Coordinador coordinadorejemplo = new Coordinador("elcor.dinador","perritosdulces123","Elcor Dinador Martinez",carreraejemplo);
//
//		Map<String, Estudiante> estudiantes = new HashMap<>();
//		Map<String, Coordinador> coordinadores = new HashMap<>();
//		estudiantes.put(estudiantejemplo.getCodigo(),estudiantejemplo);
//		coordinadores.put(coordinadorejemplo.getNombre(),coordinadorejemplo);
//
//		ProcesadorBanner procesador = new ProcesadorBanner(cursos,carreras,estudiantes,coordinadores);
//
//		LoaderData.guardarData(procesador);
		
		ConsolaBanner consola = new ConsolaBanner();
		consola.ejecutarapp();
	}

	public void ejecutarapp() {

		System.out.println("Sistema de gestión de cursos y calificaciones\n");

		boolean ejecutar = true;

		while (ejecutar) {
			try {
				mostrarMenu();
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opción"));
				if (opcion_seleccionada == 1)
					ejecutarCargarInfo();
			}
			catch (NumberFormatException e)
			{
				System.out.println("Debe seleccionar uno de los números de las opciones.");
			}
		}
	}


	public void mostrarMenu()
	{
		System.out.println("\nOpciones de la aplicación\n");
		System.out.println("1. Cargar la información");
	}
	
	public String input(String mensaje)
	{
		try
		{
			System.out.print(mensaje + ": ");
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			return reader.readLine();
		}
		catch (IOException e)
		{
			System.out.println("Error leyendo de la consola");
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	private void ejecutarCargarInfo()
	{
		System.out.println("\n" + "Cargar los datos de la app" + "\n");
		try
		{
			procesador = LoaderData.cargarData();
			System.out.println("Se cargó el archivo con información del sistema.");
			Collection<String> eventos = procesador.darNombresCarreras();
			System.out.println("Las carreras para las que se tiene información son:");
			for (String dep : eventos)
			{
				System.out.println(" - " + dep);
			}
		}
		catch (Exception e)
		{
			System.out.println("ERROR: "+e);
		}

	}
}