package uniandes.dpoo.proyecto1.modelo;


public class Restriccion {
	private String[] opciones;
	private String[] tipo;
	
	public Restriccion(String[] opciones, String[] tipo){
		this.setOpciones(opciones);
		this.tipo = tipo;
	}

	public String[] getOpciones() {
		return opciones;
	}

	public void setOpciones(String[] opciones) {
		this.opciones = opciones;
	}
}
