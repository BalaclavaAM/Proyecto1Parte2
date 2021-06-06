package uniandes.dpoo.proyecto1.GUI.HistAndPlan;

import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class InfoAux extends JDialog {

    public InfoAux(CursoRegistrado cursoR, String estado, boolean nota){
        setLayout(new GridLayout(2,1));
        setDefaultCloseOperation(2);
        setVisible(true);
        setSize(200,330);
        Vector<String> info;

        info = new Vector<>(Arrays.asList("Nombre: " + cursoR.getCurso().getNombre(),
                "Codigo: " + cursoR.getCurso().getCodigo(),
                "Materia: " + cursoR.getCurso().getMateria(),
                "Periodo: " + cursoR.getPeriodo().toString(),
                "Creditos: " + cursoR.getCurso().getCreditos(),
                "Epsilon: " + cursoR.getEpsilon(),
                "Estado: " + estado));
        if(nota){
            info.add("Nota: "+ cursoR.getNota().toString());
        }

        JList<String> infoList = new JList<>(info);
        JTextArea ja = new JTextArea(cursoR.getCurso().getDescripcion());
        add(new JScrollPane(infoList));
        add(ja);
    }
}
