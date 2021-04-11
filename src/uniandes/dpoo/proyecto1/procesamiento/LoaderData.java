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
			
			ObjectInputStream in4=new ObjectInputStream(new FileInputStream("cursos.txt"));  
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
	
	
	public void guardarEstudiantes(List<Estudiante> estudiantes) {
		try {
			FileOutputStream fout=new FileOutputStream("estudiantes.txt");  
			ObjectOutputStream out=new ObjectOutputStream(fout);  
			int guardados= 0;
			
			for (Estudiante e : estudiantes){
				out.writeObject(e);  
				guardados+=1;
			}
			out.flush();    
			out.close();  
			System.out.println("Se han guardado "+guardados+" estudiantes");  
		}catch(Exception a){System.out.println(a);}  
	}  

}
