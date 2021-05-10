package uniandes.dpoo.proyecto1.procesamiento;

import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;

import java.io.*;
import java.util.ArrayList;

public class LoaderData {
	
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
		CargaPensum("C:/Users/Netie/Documents/DPOO/Proyecto1Parte2/data/Libro1.csv");
	}
}
