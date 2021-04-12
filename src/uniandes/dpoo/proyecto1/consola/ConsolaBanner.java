package uniandes.dpoo.proyecto1.consola;

import uniandes.dpoo.proyecto1.procesamiento.*;
import uniandes.dpoo.proyecto1.modelo.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

public class ConsolaBanner {

	private ProcesadorBanner procesador;
	private Usuario usuario;


	public static void main(String[] args)
	{	
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
				else if (opcion_seleccionada == 2)
					ejecutarLogearse();
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
		System.out.println("2. Logearse");
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
	private void ejecutarLogearse()
	{
		System.out.println("\n" + "Logeo de Usuario" + "\n");
		int opcion = Integer.parseInt(input("Por favor ingrese 1 si es estudiante, de lo contrario ingrese 2."));
		try
		{
			String user = input("Nombre de usuario: ");
			String password = input("Contraseña: ");
			if (opcion == 1){
				int validacion = procesador.authEstudiante(user, password);
				if (validacion==1) {
					usuario = procesador.getEstudiantes().get(user);
				} else if (validacion == 0) {
					System.out.println("Error, el usuario "+user+" no existe en el sistema.");
				} else {
					System.out.println("Error, la contraseña no coincide.");
				}
			} else if (opcion ==2) {
				int validacion = procesador.authCoordinador(user, password);
				if (validacion==1) {
					usuario = procesador.getCoordinadores().get(user);
				} else if (validacion == 0) {
					System.out.println("Error, el usuario "+user+" no existe en el sistema.");
				} else {
					System.out.println("Error, la contraseña no coincide.");
				}
			}
		}
		catch (Exception e)
		{
			System.out.println("ERROR: "+e);
		}

	}
}