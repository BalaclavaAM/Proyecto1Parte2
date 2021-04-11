package uniandes.dpoo.proyecto1.modelo;

public class Periodo {
    private final int anio;
    private final int periodo;
    private final int ciclo;

    public Periodo(int anio, int periodo, int ciclo) {
        this.anio = anio;
        this.periodo = periodo;
        this.ciclo = ciclo;
    }

    public int compare(Periodo oPeriodo){
        if (this.anio + this.periodo + this.ciclo > oPeriodo.anio + oPeriodo.periodo + oPeriodo.ciclo){
            return 1;
        }else if(this.anio + this.periodo + this.ciclo == oPeriodo.anio + oPeriodo.periodo + oPeriodo.ciclo){
            return -1;
        }else {
            return 0;
        }
    }



}

