package uniandes.dpoo.proyecto1.procesamiento;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Seccion;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Restricciones.Correquisito;
import uniandes.dpoo.proyecto1.modelo.Restricciones.Prerrequisito;
import uniandes.dpoo.proyecto1.modelo.Restricciones.RestriccionNivel;
import uniandes.dpoo.proyecto1.modelo.Restricciones.RestriccionReq;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

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
				String NRC = (line[1]);
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
				Seccion seccionactual = new Seccion(curso,false,NRC,infosemanas,"NO HAY INFO",false,Seccion,Parte);
				seccionactual.setNombre(nombreMateria);
				seccionactual.setCodigo(Codigo);
				seccionactual.setCreditos(Creditos);
				banner.getSecciones().add(seccionactual);

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

			linea = br.readLine();
		}
	}

	public static void CargaSecciones(Banner banner) throws IOException, CsvValidationException {
		Reader reader  = new FileReader(rutasecciones);
		CSVParser parsero = new CSVParserBuilder().withSeparator(',').build();
		CSVReader csvReader = new CSVReaderBuilder(reader).withCSVParser(parsero).build();
		String[] linea;
		csvReader.readNext();
		while ((linea = csvReader.readNext()) != null){
			String codigo = linea[0];
			Boolean epsilon = Boolean.parseBoolean(linea[1]);
			String nrc = linea[2];
			String profesor = linea[3];
			Boolean tipoe = Boolean.parseBoolean(linea[4]);
			Integer nseccion = Integer.parseInt(linea[5]);
			String ciclo = linea[6];
			Curso curso = banner.buscarCursoByCode(codigo);
			String[][] infosemanas = new String[28][11];
			for (int i = 1; i <= 297; i++)
			{
				double numero = i/11; double parteDecimal = numero % 1; int semana = (int) (numero - parteDecimal);
				infosemanas[semana][i-(11*semana)]=linea[i+6];
			}

			Seccion seccion = new Seccion(curso,epsilon,nrc,infosemanas,profesor,tipoe,nseccion,ciclo);
			banner.getSecciones().add(seccion);

		}
		reader.close();
		csvReader.close();
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
			Double creditos = Double.parseDouble(partes[2]);
			String dpto = partes[3];
			boolean numerica = Boolean.parseBoolean(partes[4]);
			Curso curso = new Curso(nombre,codigo,dpto,creditos,numerica,armarPerrequisitos(partes[6])
					,armarCorrequisitos(partes[5]),armarRestriccionesReq(partes[7]), convRestriccionNivel(partes[8]),
					"descripcion");
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

	private static ArrayList<Prerrequisito> armarPerrequisitos(String prerrequisitos){
		ArrayList<Prerrequisito> retorno = new ArrayList<>();
		ArrayList<String> cCorrequisitos = parseList2(prerrequisitos);
		for (String correq:cCorrequisitos)
		{
			retorno.add(new Prerrequisito(parseList(correq)));
		}
		return retorno;
	}


	public static ArrayList<RestriccionReq> armarRestriccionesReq(String restriccionesreq){
		ArrayList<RestriccionReq> retorno = new ArrayList<>();
		ArrayList<String> cCorrequisitos = parseList2(restriccionesreq);
		for (String correq:cCorrequisitos)
		{
			retorno.add(new RestriccionReq(correq));
		}
		return retorno;
	}



	public static RestriccionNivel convRestriccionNivel(String nivel){

		Nivel nNivel = switch (nivel) {
			case "1" -> Nivel.UNO;
			case "2" -> Nivel.DOS;
			case "3" -> Nivel.TRES;
			case "4" -> Nivel.CUATRO;
			default -> Nivel.CERO;
		};
		return new RestriccionNivel(nNivel);
	}


	private static ArrayList<String> parseList2(String string){
		ArrayList<String> retorno = new ArrayList<>();
		if (!(string.equals("null"))) {
			String[] lista = string.split("/");
			retorno.addAll(Arrays.asList(lista));
		}
		return retorno;
	}

	private static ArrayList<String> parseList(String string){
		ArrayList<String> retorno = new ArrayList<>();
		if (!(string.equals("null"))) {
			String[] lista = string.split(";");
			Collections.addAll(retorno, lista);
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
