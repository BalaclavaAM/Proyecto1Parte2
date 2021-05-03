package uniandes.dpoo.proyecto1.modelo.Registro;


import java.util.ArrayList;

public class Periodo {
    private final int anio;
    private final int semestre; // los formatos son 10 19 20, tal cual aparece en baner.
    private final int ciclo;
    private final boolean semestral;


    public Periodo(int anio, int semestre, int ciclo, boolean semestral) {
        this.anio = anio;
        this.semestre = semestre;
        this.ciclo = ciclo;
        this.semestral = semestral;
    }
    public Periodo(int anio, int semestre, boolean semestral){
        this.anio = anio;
        this.semestre = semestre;
        this.ciclo = 1;
        this.semestral = semestral;
    }

    public Periodo(int anio, int semestre){
        this.anio = anio;
        this.semestre = semestre;
        this.ciclo = 1;
        this.semestral = true;
    }


    public boolean isSemestral() {
        return semestral;
    }

    public int compare(Periodo oPeriodo) {
        return compareL(this, oPeriodo);
    }


    public static int compareL(Periodo p1, Periodo p2) {
        int c1 = Integer.compare(p1.anio, p2.anio);
        if (c1 != 0) {
            return c1;
        }
        int c2 = Integer.compare(p1.semestre, p2.semestre);
        if (c2 != 0) {
            return c2;
        }
        return Integer.compare(p1.ciclo, p2.ciclo);
    }



    public int getSemestre() {
        return semestre;
    }

    public int getAnio() {
        return anio;
    }

    public int getCiclo() {
        return ciclo;
    }
}
