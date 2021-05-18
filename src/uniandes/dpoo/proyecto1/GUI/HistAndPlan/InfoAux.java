package uniandes.dpoo.proyecto1.GUI.HistAndPlan;

import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;

import javax.swing.*;
import java.util.Arrays;
import java.util.Vector;

public class InfoAux extends JDialog {

    public InfoAux(CursoRegistrado cursoR, String estado, boolean nota){
        setDefaultCloseOperation(2);
        setSize(170,150);
        Vector<String> info;

        info = new Vector<>(Arrays.asList("Nombre: " + cursoR.getCurso().getNombre(),
                "Codigo: " + cursoR.getCurso().getCodigo(),
                "Materia: " + cursoR.getCurso().getMateria(),
                "Periodo: " + cursoR.getPeriodo().toString(),
                "Creditos: " + cursoR.getCurso().getCreditos(),
                "Epsilon: " + cursoR.getEpsilon(),
                "Estado: " + estado));
        if(nota){
            if(cursoR.getNota().isNumeric()){
                info.add("Nota: "+ cursoR.getNota().notaNum());
            }else{
                info.add("Nota: " + cursoR.getNota().notaCual());
            }
        }

        JList<String> infoList = new JList<>(info);

        add(infoList);
    }
}
