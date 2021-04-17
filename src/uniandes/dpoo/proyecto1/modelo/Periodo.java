package uniandes.dpoo.proyecto1.modelo;

public class Periodo {
    private final int anio;
    private final int periodo;
    private final int ciclo;
    private final boolean semestral;

    public Periodo(int anio, int periodo, int ciclo, boolean semestral) {
        this.anio = anio;
        this.periodo = periodo;
        this.ciclo = ciclo;
        this.semestral = semestral;
    }

    public boolean isSemestral() {
        return semestral;
    }

    public int compare(Periodo oPeriodo){
        return Integer.compare(this.anio + this.periodo + this.ciclo, oPeriodo.anio + oPeriodo.periodo + oPeriodo.ciclo);
    }
}

