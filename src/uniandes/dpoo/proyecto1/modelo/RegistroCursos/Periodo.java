package uniandes.dpoo.proyecto1.modelo.RegistroCursos;


public class Periodo {
    private int anio;
    private int semestre; // los formatos son 10 19 20, tal cual aparece en baner.
    private int ciclo;
    private  boolean completo;


    public Periodo(int anio, int semestre, int ciclo) {
        this.anio = anio;
        this.semestre = semestre;
        this.ciclo = ciclo;
        this.completo = false;
    }

    public Periodo(int anio, int semestre){
        this.anio = anio;
        this.semestre = semestre;
        this.ciclo = 1;
        this.completo = true;
   }
    public boolean avanzarPeriodo(){
        if(semestre == 19){
            semestre = 20;
            ciclo = 1;
            return true;
        }
        if(ciclo == 1){
            ciclo++;
            return true;
        }
        if(ciclo == 2) {
            if (semestre == 10) {
                ciclo--;
                semestre = 19;
                return true;
            }
            if (semestre == 20) {
                anio++;
                semestre = 10;
                ciclo = 1;
                return true;
            }
        }
        System.out.println("pusieron mal los periodos");
        return false;
    }



    public String periodoS(){
        return anio + "-" + semestre;
    }

    public static String periodoS(int anio, int semestre){
        return anio + "-" + semestre;
    }

    public int compare(Periodo p1){
        return compareL(p1,this);
    }



    public static int compareL(Periodo p1, Periodo p2) {
        int c1 = Integer.compare(p1.anio, p2.anio);
        if (c1 == 0) {
            int c2 = Integer.compare(p1.semestre, p2.semestre);
            if (c2 == 0) {
                int c3 = Integer.compare(p1.ciclo, p2.ciclo);
                if (c3 == -1) {
                    if (p1.completo) {
                        return 0;
                    }
                }
                return c3;
            }
            return c2;
        }
        return c1;
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
