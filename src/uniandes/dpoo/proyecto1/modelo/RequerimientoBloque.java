package uniandes.dpoo.proyecto1.modelo;

import java.util.ArrayList;

public class RequerimientoBloque extends Requerimiento {
    private String tipologia;
    private int creditos;
    private Curso[] cursos;

    public RequerimientoBloque(String nombre, int semestresug, ArrayList<Curso> cursos) {
        super(nombre, semestresug, cursos);
    }

    public boolean validar(Curso[] cursosOp) {
        int sum = 0;
        for(Curso c: cursosOp){
            boolean isN = false;
            for(Curso c2: cursos){
                if (c2.equals(c)) {
                    isN = true;
                    break;
                }
            }
            if(!isN){
                return false;
            }
            sum += c.getCreditos();
        }
        return (sum >= this.creditos);
    }

  
}
