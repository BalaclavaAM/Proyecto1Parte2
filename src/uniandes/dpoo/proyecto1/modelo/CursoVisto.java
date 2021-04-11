package uniandes.dpoo.proyecto1.modelo;

import java.util.Date;

public class CursoVisto extends Curso {
	private boolean notanumerica;
	private boolean aprobado;
	private float nota;
	private Date fecha;
	

	public CursoVisto(String nombre, String codigo, String programa, String duracion, String tipologia,
			boolean notanumerica, boolean aprobado, float nota, Date fecha) {
		super(nombre, codigo, programa, duracion, tipologia);
		this.notanumerica = notanumerica;
		this.aprobado = aprobado;
		this.nota = nota;
		this.fecha = fecha;
	}
	
	
	public boolean isNotanumerica() {
		return notanumerica;
	}
	public void setNotanumerica(boolean notanumerica) {
		this.notanumerica = notanumerica;
	}
	
	public boolean isAprobado() {
		return aprobado;
	}
	public void setAprobado(boolean aprobado) {
		this.aprobado = aprobado;
	}
	
	public float getNota() {
		return nota;
	}
	public void setNota(float nota) {
		this.nota = nota;
	}
	
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
}
