package uniandes.dpoo.proyecto1.consola;

import uniandes.dpoo.proyecto1.procesamiento.*;
import uniandes.dpoo.proyecto1.modelo.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.Collections;

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
				else if (opcion_seleccionada == 2 && procesador != null)
					ejecutarLogearse();
				else if (opcion_seleccionada == 3)
					genemptyProc();
				else if (opcion_seleccionada == 4)
					createUser();
				else if (opcion_seleccionada == 0)
					ejecutar = false;
				else if (procesador==null) {
					System.out.println("Para usar esta opción primero debes cargar los datos almacenados.");
				}
			}
			catch (NumberFormatException e)
			{
				System.out.println("Debe seleccionar uno de los números de las opciones.");
			}
		}
	}

	public void mostrarMenu()
	{
		if (usuario!=null) {
			System.out.println("Estás logeado como "+usuario.getNombredeusuario());
		}
		
		System.out.println("\nOpciones de la aplicación\n");
		System.out.println("1. Cargar la información");
		System.out.println("2. Logearse");
		System.out.println("3. Generar un archivo de información vacío");
		System.out.println("4. Crear un usuario");
		System.out.println("0. Salir");
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
		int opcion = Integer.parseInt(input("Por favor ingrese 1 si es estudiante, de lo contrario ingrese 2"));
		try
		{
			String user = input("Nombre de usuario: ");
			String password = input("Contraseña: ");
			if (opcion == 1){
				int validacion = procesador.authEstudiante(user, password);
				if (validacion==1) {
					usuario = procesador.getEstudiantes().get(user);
				} else if (validacion == -1) {
					System.out.println("Error, el usuario "+user+" no existe en el sistema.");
				} else {
					System.out.println("Error, la contraseña no coincide.");
				}
			} else if (opcion ==2) {
				int validacion = procesador.authCoordinador(user, password);
				if (validacion==1) {
					usuario = procesador.getCoordinadores().get(user);
				} else if (validacion == -1) {
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

	private void genemptyProc()
	{
		System.out.println("\n" + "Logeo de Usuario" + "\n");
		int opcion = Integer.parseInt(input("Presione 1 si está de acuerdo con borrar todos los datos existentes y generar unos nuevos.\nDe lo contrario presione otra tecla"));
		try
		{
			if (opcion==1) {
				procesador = new ProcesadorBanner(Collections.emptyList(),Collections.emptyList(),Collections.emptyMap(),Collections.emptyMap());
				LoaderData.guardarData(procesador);
				System.out.println("La información ha sido generada correctamente.");
			}
		}
		catch (Exception e)
		{
			System.out.println("ERROR: "+e);
		}

	}


	private void createUser()
	{
		System.out.println("\n" + "Creación de Usuario" + "\n");
		int opcion = Integer.parseInt(input("Presione 1 si desea crear un usuario estudiante. Si desea crear un usuario coordinador presione 2. \nDe lo contrario presione cualquier otra tecla."));
		try
		{
			if (opcion==1) {
				procesador = new ProcesadorBanner(Collections.emptyList(),Collections.emptyList(),Collections.emptyMap(),Collections.emptyMap());
				LoaderData.guardarData(procesador);
				System.out.println("La información ha sido generada correctamente.");
			}
		}
		catch (Exception e)
		{
			System.out.println("ERROR: "+e);
		}

	}
}