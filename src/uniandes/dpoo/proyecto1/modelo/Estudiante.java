package uniandes.dpoo.proyecto1.modelo;

public class Estudiante extends Usuario{
	private static final long serialVersionUID = 7413490025559091836L;
	private String name;
	private String codigo;
	private HistoriaAcademica HistoriaAcademica;
	


	public Estudiante(String nombredeusuario, String contrasenha, String name, String codigo,
			HistoriaAcademica historiaAcademica) {
		super(nombredeusuario, contrasenha);
		this.name = name;
		this.codigo = codigo;
		HistoriaAcademica = historiaAcademica;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public HistoriaAcademica getHistoriaAcademica() {
		return HistoriaAcademica;
	}

	public void setHistoriaAcademica(HistoriaAcademica historiaAcademica) {
		HistoriaAcademica = historiaAcademica;
	}
	

}
