package uniandes.dpoo.proyecto1.consola;

import uniandes.dpoo.proyecto1.procesamiento.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

public class ConsolaBanner {

	private ProcesadorBanner procesador;

	public void ejecutarapp() {

		System.out.println("Sistema de gesti�n de cursos y calificaciones\n");

		boolean ejecutar = true;

		while (ejecutar) {
			try {
				mostrarMenu();
				int opcion_seleccionada = Integer.parseInt(input("Por favor seleccione una opci�n"));
				if (opcion_seleccionada == 1)
					ejecutarCargarInfo();
			}
			catch (NumberFormatException e)
			{
				System.out.println("Debe seleccionar uno de los n�meros de las opciones.");
			}
		}
	}


	public void mostrarMenu()
	{
		System.out.println("\nOpciones de la aplicaci�n\n");
		System.out.println("1. Cargar la informaci�n");
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
			System.out.println("Se carg� el archivo con informaci�n del sistema.");
			Collection<String> eventos = procesador.darNombresCarreras();
			System.out.println("Las carreras para las que se tiene informaci�n son:");
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
	public static void main(String[] args)
	{
		ConsolaBanner consola = new ConsolaBanner();
		consola.ejecutarapp();
	}
}