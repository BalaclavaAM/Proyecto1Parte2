package uniandes.dpoo.proyecto1.procesamiento;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Seccion;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Restricciones.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoaderData {
	private final static String rutacursos = "./data/information/Cursinhos.csv";
	private final static String rutasecciones = "./data/information/Seccioninhas.csv";
	private final static String rutacartelera = "./data/information/cartelerautf8.csv";

	public static Banner cargarData() {
		try {
			ObjectInputStream in=new ObjectInputStream(new FileInputStream("info.txt"));  
			Banner s=(Banner)in.readObject();
			in.close();
			return s;
			
		}catch(Exception a){System.out.println(a);}
		return null;  
	}


	public static List<String[]> cargarSeccionesNativas(Banner banner) throws Exception {
		Reader reader = new FileReader(rutacartelera, StandardCharsets.UTF_8);
		List<String[]> list = new ArrayList<>();
		CSVParser parsero = new CSVParserBuilder().withSeparator(';').build();
		CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parsero).build();
		String[] line;
		csvReader.readNext();
		csvReader.readNext();
		csvReader.readNext();
		while ((line = csvReader.readNext()) != null) {
			if (!(line[0].equals("**** Fin datos ****")))
			{
				int Numero = Integer.parseInt(line[0]);
				int NRC = Integer.parseInt(line[1]);
				String Parte = line[2];
				String Codigo = line[4].strip();
				int Seccion = Integer.parseInt(line[5]);
				float Creditos = Float.parseFloat(line[6]);
				String nombreMateria = line[7];
				int Cupo = Integer.parseInt(line[8]);
				int Inscritos = Integer.parseInt(line[9]);
				int Disponibles = Integer.parseInt(line[10]);
				String[][] infosemanas = new String[28][11];
				for (int i = 11; i <= 131; i++)
				{
					double numero = i/11; double parteDecimal = numero % 1; int semana = (int) (numero - parteDecimal);
					infosemanas[semana][i-(11*semana)]=line[i];
				}
				infosemanas[12][0]="";
				for (int i = 132; i <= 141 ; i++) {
					infosemanas[12][i-131]=line[i];
				}
				for (int i = 142; i<=306; i++)
				{
					double numero = (i+1)/11; double parteDecimal = numero % 1; int semana = (int) (numero - parteDecimal);
					infosemanas[semana][(i+1)-(11*semana)]=line[i];
				}
				Curso curso = banner.buscarCursoByCode(Codigo);
				Seccion seccionactual = new Seccion(curso,false,NRC,);

			}
		}
		reader.close();
		csvReader.close();
		return list;
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
			String profesor = partes[3];
			Boolean tipoe = Boolean.parseBoolean(partes[4]);
			Integer nseccion = Integer.parseInt(partes[5]);
			String ciclo = partes[6];
			Curso curso = banner.buscarCursoByCode(codigo);
			Seccion seccion = new Seccion(curso,epsilon,nrc,profesor,tipoe,nseccion,ciclo);
			banner.getSecciones().add(seccion);

			linea=br.readLine();
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
			int creditos = Integer.parseInt(partes[2]);
			String dpto = partes[3];
			boolean numerica = Boolean.parseBoolean(partes[4]);
			ArrayList<PreRestriccion> restricciones = armarRestricciones(partes[6],partes[7],partes[8]);
			ArrayList<Correquisito> correquisitos = armarCorrequisitos(partes[5]);
			Curso curso = new Curso(nombre,codigo,dpto,creditos,numerica,restricciones,correquisitos);
			if (banner.getCursosDepartamento().containsKey(dpto)){
				banner.getCursosDepartamento().get(dpto).put(codigo,curso);
			} else {
				Map<String,Curso> mapadpto = new HashMap<>();
				mapadpto.put(codigo,curso);
				banner.getCursosDepartamento().put(dpto,mapadpto);
			}
			banner.getCatalogo().put(codigo,curso);

			linea = br.readLine();
		}
	}
	private static ArrayList<Correquisito> armarCorrequisitos(String correquisitos){
		ArrayList<Correquisito> retorno = new ArrayList<>();
		ArrayList<String> cCorrequisitos = parseList2(correquisitos);
		for (String correq:cCorrequisitos)
		{
			retorno.add(new Correquisito(parseList(correq)));
		}
		return retorno;
	}


	private static ArrayList<PreRestriccion> armarRestricciones(String prerequisitos,
																String restricciones, String nivel)
	{
		ArrayList<PreRestriccion> retorno = new ArrayList<>();

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
