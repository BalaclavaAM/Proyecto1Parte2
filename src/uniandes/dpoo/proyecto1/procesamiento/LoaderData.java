package uniandes.dpoo.proyecto1.procesamiento;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Restricciones.*;

import java.io.*;
import java.lang.reflect.Array;
import java.nio.Buffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LoaderData {
	private final static String rutacursos = "./data/information/Cursinhos.csv";
	private final static String rutasecciones = "./data/information/Secciones.csv";

	public static Banner cargarData() {
		try {
			ObjectInputStream in=new ObjectInputStream(new FileInputStream("info.txt"));  
			Banner s=(Banner)in.readObject();
			in.close();
			return s;
			
		}catch(Exception a){System.out.println(a);}
		return null;  
	}

	public static void CargaPensum(String  filename) throws IOException {
		ArrayList<Requerimiento> listaRequerimientos = new ArrayList<>();

		BufferedReader br = new BufferedReader(new FileReader(filename));
		String titulos = br.readLine();
		String linea = br.readLine();

		while (linea != null)
		{

			String[] partes = linea.trim().split(",");
			System.out.println(partes[0]);

			linea = br.readLine();
		}
	}

	public static void CargaSecciones(Banner banner) throws  IOException {
		BufferedReader br  = new BufferedReader(new FileReader(rutasecciones));
		br.readLine();
		String linea = br.readLine();
		while (linea != null){
			String[] partes = linea.trim().split(",");
			String codigo = partes[0];
			Boolean epsilon = Boolean.parseBoolean(partes[1]);
			String nrc = partes[2];
			ArrayList<String> horario = parseList(partes[3]);
			String horas = horario.get(0);
			ArrayList<String> dias = parseList2(horario.get(1));
			String profesor = partes[4];
			Boolean tipoe = Boolean.parseBoolean(partes[5]);
			Integer seccion = Integer.parseInt(partes[6]);
			String ciclo = partes[7];

		}
	}

	public static void CargaCursos(Banner banner) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(rutacursos));
		br. readLine();
		String linea = br.readLine();
		while (linea != null)
		{
			String[] partes = linea.trim().split(",");
			String nombre = partes[0];
			String codigo = partes[1];
			Integer creditos = Integer.parseInt(partes[2]);
			String dpto = partes[3];
			Boolean numerica = Boolean.parseBoolean(partes[4]);
			ArrayList<Restriccion> restricciones = armarRequerimientos(partes[5],partes[6],partes[7],partes[8]);
			Boolean completo = Boolean.parseBoolean(partes[9]);
			String desc = partes[10];
			Curso curso = new Curso(nombre,codigo,dpto,creditos,completo,numerica,desc,restricciones);
			if (banner.getCursos().containsKey(dpto)){
				banner.getCursos().get(dpto).put(codigo,curso);
			} else {
				Map<String,Curso> mapadpto = new HashMap<>();
				mapadpto.put(codigo,curso);
				banner.getCursos().put(dpto,mapadpto);
			}

			linea = br.readLine();
		}
	}

	private static ArrayList<Restriccion> armarRequerimientos(String correquisitos, String prerequisitos,
																String restricciones, String nivel)
	{
		ArrayList<Restriccion> retorno = new ArrayList<>();
		ArrayList<String> cCorrequisitos = parseList2(correquisitos);
		for (String correq:cCorrequisitos)
		{
			retorno.add(new Correquisito(parseList(correq)));
		}
		for (String prereq:parseList2(prerequisitos))
		{
			retorno.add(new Prerrequisito(parseList(prereq)));
		}
		for (String restr:parseList(restricciones))
		{
			retorno.add(new RestriccionReq(restr));
		}
		if (!(nivel.equals("null"))){
		Nivel nNivel;
		switch (nivel){
			case "1":
				nNivel=Nivel.UNO;
			break;
			case "2":
				nNivel=Nivel.DOS;
				break;
			case "3":
				nNivel=Nivel.TRES;
				break;
			case "4":
				nNivel=Nivel.CUATRO;
				break;
			default:
				nNivel=Nivel.CERO;
		}
		retorno.add(new RestriccionNivel(nNivel));
		}
		return retorno;
	}

	private static ArrayList<String> parseList2(String string){
		ArrayList<String> retorno = new ArrayList<>();
		if (!(string.equals("null"))) {
			String[] lista = string.split("/");
			for (String e : lista) {
				retorno.add(e);
			}
		}
		return retorno;
	}

	private static ArrayList<String> parseList(String string){
		ArrayList<String> retorno = new ArrayList<>();
		if (!(string.equals("null"))) {
			String[] lista = string.split(";");
			for (String e : lista) {
				retorno.add(e);
			}
		}
		return retorno;
	}
	
	public static void guardarData(Banner procesador) {
		try {
			FileOutputStream fout=new FileOutputStream("info.txt");  
			ObjectOutputStream out=new ObjectOutputStream(fout);  
			out.writeObject(procesador);
			out.flush();    
			out.close();  

		}catch(Exception a){System.out.println(a);}  
	}

	public static void main(String[] args) throws IOException {
	}
}
