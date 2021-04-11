package uniandes.dpoo.proyecto1.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Estudiante extends Usuario implements Serializable {
	private static final long serialVersionUID = -232931508719866061L;
	private String name;
	private String codigo;
<<<<<<< HEAD
	private ArrayList<Pensum> pensum;
	private HistoriaAcademica HistoriaAcademica;
	
	public Estudiante(String nombredeusuario, String contrasenha, String name, String codigo, ArrayList<Pensum> pensum,
			uniandes.dpoo.proyecto1.modelo.HistoriaAcademica historiaAcademica) {
		super(nombredeusuario, contrasenha);
		this.name = name;
		this.codigo = codigo;
		this.setPensum(pensum);
		setHistoriaAcademica(historiaAcademica);
	}
=======
	private Pensum plan;
	private HistoriaAcademica historia;

	public Estudiante(String name, String codigo, Pensum plan) {
		this.name = name;
		this.codigo = codigo;
		this.plan = plan;
		this.historia = new HistoriaAcademica(plan);
	}

>>>>>>> 65bc98c5aac87b21397d961916857c963fa63c69
	public String getName() {
		return name;
	}
	public String getCodigo() {
		return codigo;
	}
	public Pensum getPlan() {
		return plan;
	}
<<<<<<< HEAD
	public ArrayList<Pensum> getPensum() {
		return pensum;
	}
	public void setPensum(ArrayList<Pensum> pensum) {
		this.pensum = pensum;
	}
	public HistoriaAcademica getHistoriaAcademica() {
		return HistoriaAcademica;
	}
	public void setHistoriaAcademica(HistoriaAcademica historiaAcademica) {
		HistoriaAcademica = historiaAcademica;
	}
	

=======


	
>>>>>>> 65bc98c5aac87b21397d961916857c963fa63c69
}
