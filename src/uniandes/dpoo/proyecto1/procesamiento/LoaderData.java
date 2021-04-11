package uniandes.dpoo.proyecto1.procesamiento;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uniandes.dpoo.proyecto1.modelo.*;

public class LoaderData {
	
	public static ProcesadorBanner cargarData() {
		try {
			Map<String, Estudiante> estudiantes = new HashMap<>();
			Map<String, Coordinador> coordinadores = new HashMap<>();
			List<Curso> cursos = new ArrayList<>();
			List<Carrera> carreras = new ArrayList<>();
			
			ObjectInputStream in=new ObjectInputStream(new FileInputStream("estudiantes.txt"));  
			Estudiante s=(Estudiante)in.readObject();  
			
			while (s!=null) {
				estudiantes.put(s.getCodigo(), s);
			}
			in.close();
			
			ObjectInputStream in2=new ObjectInputStream(new FileInputStream("coordinadores.txt"));  
			Coordinador c=(Coordinador)in2.readObject();  
			
			while (c!=null) {
				coordinadores.put(c.getNombredeusuario(), c);
			}
			in2.close();  
			
			ObjectInputStream in3=new ObjectInputStream(new FileInputStream("cursos.txt"));  
			Curso curso=(Curso)in3.readObject();  
			
			while (curso!=null) {
				cursos.add(curso);
			}
			in3.close();  
			
			ObjectInputStream in4=new ObjectInputStream(new FileInputStream("carreras.txt"));
			Carrera carrera=(Carrera)in4.readObject();  
			
			while (carrera!=null) {
				carreras.add(carrera);
			}
			in4.close();  
			
			ProcesadorBanner procesador = new ProcesadorBanner(cursos, carreras, estudiantes, coordinadores);
			return procesador;
			
		}catch(Exception a){System.out.println(a);}
		return null;  
	}
	
	
	public static void guardarData(ProcesadorBanner procesador) {
		try {
			FileOutputStream fout=new FileOutputStream("estudiantes.txt");  
			ObjectOutputStream out=new ObjectOutputStream(fout);  
			int guardados= 0;
			
			for (Map.Entry<String,Estudiante> entry : procesador.getEstudiantes().entrySet()){
				out.writeObject(entry.getValue());
				guardados+=1;
			}
			out.flush();    
			out.close();  
			System.out.println("Se han guardado "+guardados+" estudiantes");

			FileOutputStream fout2=new FileOutputStream("coordinadores.txt");
			ObjectOutputStream out2=new ObjectOutputStream(fout2);
			int guardados2= 0;

			for (Map.Entry<String,Coordinador> entry : procesador.getCoordinadores().entrySet()){
				out.writeObject(entry.getValue());
				guardados2+=1;
			}
			out2.flush();
			out2.close();
			System.out.println("Se han guardado "+guardados2+" coordinadores");


			FileOutputStream fout3=new FileOutputStream("cursos.txt");
			ObjectOutputStream out3=new ObjectOutputStream(fout3);
			int guardados3= 0;

			for (Curso curso : procesador.getCursos()){
				out.writeObject(curso);
				guardados3+=1;
			}
			out3.flush();
			out3.close();
			System.out.println("Se han guardado "+guardados3+" cursos");

			FileOutputStream fout4=new FileOutputStream("carreras.txt");
			ObjectOutputStream out4=new ObjectOutputStream(fout4);
			int guardados4= 0;

			for (Carrera carrera : procesador.getCarreras()){
				out.writeObject(carrera);
				guardados4+=1;
			}
			out4.flush();
			out4.close();
			System.out.println("Se han guardado "+guardados4+" carreras");


		}catch(Exception a){System.out.println(a);}  
	}  

}
