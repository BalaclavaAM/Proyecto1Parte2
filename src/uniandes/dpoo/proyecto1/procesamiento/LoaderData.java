package uniandes.dpoo.proyecto1.procesamiento;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LoaderData {
	
	public static ProcesadorBanner cargarData() {
		try {
			ObjectInputStream in=new ObjectInputStream(new FileInputStream("info.txt"));  
			ProcesadorBanner s=(ProcesadorBanner)in.readObject(); 
			in.close();
			return s;
			
		}catch(Exception a){System.out.println(a);}
		return null;  
	}
	
	
	public static void guardarData(ProcesadorBanner procesador) {
		try {
			FileOutputStream fout=new FileOutputStream("info.txt");  
			ObjectOutputStream out=new ObjectOutputStream(fout);  
			out.writeObject(procesador);
			out.flush();    
			out.close();  

		}catch(Exception a){System.out.println(a);}  
	}  

}
