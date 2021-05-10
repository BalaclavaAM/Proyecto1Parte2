package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;
import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;
import uniandes.dpoo.proyecto1.procesamiento.Banner;

import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
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
        String data[][] = new String[planes.size()][3];
        int i = 0;
        for(Plan plan: planes.values()){
            JButton jb = new JButton("agregar");

            jb.addActionListener(this);

            data[i][0] = plan.getNombre();
            data[i][1] =  plan.getPeriodoInicio().periodoS();
            data[i][2] =  plan.getUltimoPeriodo().periodoS();
            i++;
        }


        JTable tablaPlanes = new JTable(data, new String[]{"Nombre", "Periodo Inicio", "Periodo Final"});
        for (int c = 0; c < tablaPlanes.getColumnCount(); c++)
        {
            Class<?> col_class = tablaPlanes.getColumnClass(c);
            tablaPlanes.setDefaultEditor(col_class, null); // remove editor
        }


        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 7; gb.gridheight = 3; gb.weightx = 6; gb.weighty = 3;
        add(new JLabel(),gb);
        gb.gridx = 0; gb.gridy = 3; gb.gridwidth = 1; gb.gridheight = 4; gb.weightx = 1; gb.weighty = 4; gb.fill = 1;
        add(new JLabel(),gb);
        gb.gridx = 1; gb.gridy = 3; gb.gridwidth = 4; gb.gridheight = 4; gb.weightx = 4; gb.weighty = 4;
        add(new JScrollPane(tablaPlanes), gb);
        gb.gridx = 5; gb.gridy = 3; gb.gridwidth = 2; gb.gridheight = 4; gb.weightx = 2; gb.weighty = 4;
        add(new JLabel(),gb);
        gb.gridx = 0; gb.gridy = 7; gb.gridwidth = 7; gb.gridheight = 2; gb.weightx = 7; gb.weighty = 2;
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
