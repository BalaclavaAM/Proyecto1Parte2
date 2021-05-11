package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class InVerPlan extends PanelAux{
    private Plan plan;

    public InVerPlan(InterfazBannerPrincipal principal, Plan plan) {
        super(principal);
        this.plan = plan;
        setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        Map<String, Map<String, CursoRegistrado>> infoPeridos = plan.getInfoPeriodos();
        int np = infoPeridos.size();
        int ancho = Math.max(np, 2);
        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = ancho + 2; gb.gridheight = 2; gb.weightx = ancho + 2; gb.weighty = 2; gb.fill = 1;
        add(new JLabel(),gb);
        JPanel data = new JPanel(new GridBagLayout());
        GridBagConstraints gb2 = new GridBagConstraints();
        JPanel encabezado = new JPanel(new GridLayout(1,np));
        int contx = 0;
        ArrayList<String> semestres = new ArrayList<>(infoPeridos.keySet());
        semestres.sort(CharSequence::compare);

        for(String semestre: semestres){
            int conty = 1;
            encabezado.add(new JLabel(semestre));
            Map<String,CursoRegistrado> infoP = infoPeridos.get(semestre);
            for(CursoRegistrado cr: infoP.values()){
                JPanel cursoP = new JPanel(new GridLayout(2,2));
                cursoP.add(new JLabel(cr.getCurso().getCodigo()));
                cursoP.add(new JLabel(String.valueOf(cr.getCurso().getCreditos())));
                cursoP.add(new JLabel(cr.getCurso().getNombre()));
                gb2.gridx = contx; gb2.gridy = conty; gb2.gridwidth = 1; gb2.gridheight = 1; gb2.weightx = 1; gb2.weighty = 1; gb2.fill = 1;
                data.add(cursoP,gb2);
                conty++;
                cursoP.setBackground(Color.BLUE);
            }
            contx++;
        }
        gb2.gridx = 0; gb2.gridy = 0; gb2.gridwidth = np; gb2.gridheight = 1; gb2.weightx = np; gb2.weighty = 1;
        data.add(encabezado,gb2);
        data.setBackground(Color.gray);
        gb.gridx = 1; gb.gridy = 1; gb.gridwidth = ancho; gb.gridheight = 3; gb.weightx = ancho; gb.weighty = 3; gb.fill = 1;
        add(new JScrollPane(data),gb);
        gb.gridx = 0; gb.gridy = 1; gb.gridwidth = 1; gb.gridheight = 3; gb.weightx = 1; gb.weighty = 3; gb.fill = 1;
        add(new JLabel(),gb);
    }


    @Override
    public void mostrar(PanelImagenFondo fondo) {
        setOpaque(false);
        fondo.add(this);
    }
}
