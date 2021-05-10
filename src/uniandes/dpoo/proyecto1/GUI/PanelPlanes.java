package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;
import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;
import uniandes.dpoo.proyecto1.procesamiento.Banner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class PanelPlanes extends PanelAux implements ActionListener {
    private Banner banner;


    public PanelPlanes(InterfazBannerPrincipal principal, Estudiante estudiante) {
        super(principal);
        setSize(principal.getPrincipalUsuario().getSize());

        banner = principal.getBanner();
        setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();

        Map<String, Plan> planes = estudiante.getPlanes();
        JPanel tablaPlanes = new JPanel(new GridLayout(planes.size()+1,3));
        tablaPlanes.add(new JLabel("Nombre"));tablaPlanes.add(new JLabel("Periodo Inicio"));tablaPlanes.add(new JLabel("Periodo Fin"));
        for(Plan plan: planes.values()){
            tablaPlanes.add(new JLabel(plan.getNombre()));
            tablaPlanes.add(new JLabel(plan.getPeriodoInicio().periodoS()));
            tablaPlanes.add(new JLabel(plan.getUltimoPeriodo().periodoS()));
        }

        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 6; gb.gridheight = 2; gb.weightx = 7; gb.weighty = 3;
        add(new JLabel(),gb);
        gb.gridx = 0; gb.gridy = 3; gb.gridwidth = 1; gb.gridheight = 4; gb.weightx = 1; gb.weighty = 4;
        add(new JLabel(),gb);
        gb.gridx = 1; gb.gridy = 3; gb.gridwidth = 4; gb.gridheight = 4; gb.weightx = 4; gb.weighty = 4;
        add(new JScrollPane(tablaPlanes), gb);
        gb.gridx = 4; gb.gridy = 3; gb.gridwidth = 1; gb.gridheight = 4; gb.weightx = 1; gb.weighty = 4;
        add(new JLabel(),gb);
    }

    @Override
    public void mostrar(PanelImagenFondo fondo) {
        setOpaque(false);
        fondo.add(this);
    }

    public void actionPerformed(ActionEvent e) {

    }
}
