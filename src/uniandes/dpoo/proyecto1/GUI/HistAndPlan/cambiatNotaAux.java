package uniandes.dpoo.proyecto1.GUI.HistAndPlan;

import uniandes.dpoo.proyecto1.modelo.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Nota.NotaCual;
import uniandes.dpoo.proyecto1.modelo.Nota.NotaNum;
import uniandes.dpoo.proyecto1.modelo.Nota.calCual;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.MallaCursos;

import javax.swing.*;
import java.awt.*;

public class cambiatNotaAux extends JDialog {

    public cambiatNotaAux(PanelHistoria panel, CursoRegistrado cursoR, HistoriaAcademica historia){
        setSize(150,100);
        setLayout(new GridLayout(3,1));
        JLabel notaAct = new JLabel("Nota actual");
        JTextField notaActJT = new JTextField(cursoR.getNota().toString()); notaActJT.setEditable(false);
        JPanel na = new JPanel(new GridLayout(1,2));
        na.add(notaAct);na.add(notaActJT);
        add(na);
        JLabel notaNuv = new JLabel("Nota nueva");
        JTextField notaNuvJT = new JTextField("0.0");
        JPanel nn = new JPanel(new GridLayout(1,2));
        nn.add(notaNuv);nn.add(notaNuvJT);
        add(nn);
        JButton cambiatNota = new JButton("cambiar nota");cambiatNota.addActionListener(e->{
            Nota notaR;
            String notaIn = notaNuvJT.getText();
            if(notaIn.equals("A") || notaIn.equals("R")){
                calCual calC = calCual.valueOf(notaIn);
                notaR = new NotaCual(calC);
            }else{
                float calN = Float.parseFloat(notaIn);
                notaR = new NotaNum(calN);
            }
            historia.actualizarNota(cursoR,notaR);
            panel.reiniciarPanel();
        });
        add(cambiatNota);
    }
}
