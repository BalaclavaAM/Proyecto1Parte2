package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;
import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;
import uniandes.dpoo.proyecto1.procesamiento.Banner;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class PanelPlanes extends PanelAux implements ActionListener {
    private Banner banner;
    private Map<String,Plan> planes;


    public PanelPlanes(InterfazBannerPrincipal principal, Estudiante estudiante) {
        super(principal);
        setSize(principal.getPrincipalUsuario().getSize());

        banner = principal.getBanner();
        setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        planes = estudiante.getPlanes();
        int n = planes.size();
        JPanel data = new JPanel(new GridLayout(n,5));
        for(Plan plan: planes.values()){
            data.add(new JLabel(plan.getNombre())); data.add(new JLabel(plan.getPeriodoInicio().periodoS()));
            data.add(new JLabel(plan.getUltimoPeriodo().periodoS()));
            JButton b1 = new JButton("ver");
            b1.setActionCommand("ver-"+plan.getNombre());
            b1.addActionListener(this);
            data.add(b1);
            JButton b2 = new JButton("editar");
            b2.setActionCommand("editar-"+plan.getNombre());
            b2.addActionListener(this);
            data.add(b2);
        }
        JPanel encabezado = new JPanel( new GridLayout(1,4));
        encabezado.add(new JLabel("Nombre")); encabezado.add(new JLabel("Periodo Inicio")); encabezado.add(new JLabel("Periodo final"));
        encabezado.add(new JLabel("  "));
        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 7; gb.gridheight = 2; gb.weightx = 7; gb.weighty = 3;
        add(new JLabel(),gb);
        gb.gridx = 0; gb.gridy = 2; gb.gridwidth = 1; gb.gridheight = n; gb.weightx = 1; gb.weighty = n;
        add(new JLabel(),gb);
        gb.gridx = 1; gb.gridy = 2; gb.gridwidth = 4; gb.gridheight = 1; gb.weightx = 4; gb.weighty = 1; gb.fill = 1;
        add(encabezado,gb);
        gb.gridx = 1; gb.gridy = 3; gb.gridwidth = 4; gb.gridheight = n; gb.weightx = 2; gb.weighty =n ; gb.fill = 1;
        add(new JScrollPane(data), gb);
        gb.gridx = 5; gb.gridy = 3; gb.gridwidth = 2; gb.gridheight = n; gb.weightx = 2; gb.weighty = n;
        add(new JLabel(),gb);
        gb.gridx = 0; gb.gridy = n + 4; gb.gridwidth = 7; gb.gridheight = 1; gb.weightx = 7; gb.weighty = 2;
        add(new JLabel(),gb);
    }

    @Override
    public void mostrar(PanelImagenFondo fondo) {
        setOpaque(false);
        fondo.add(this);
    }

    public void actionPerformed(ActionEvent e) {
        String[] com = e.getActionCommand().split("-");
        String accion = com[0];
        Plan plan = planes.get(com[1]);
        if(accion.equals("ver")){
            PanelAux panelvista = new InVerPlan(principal,plan);
            principal.ocultarYmostrar(panelvista);
        }else{
            PanelAux panelEdicion = new InEdPlan(principal,plan);
            principal.ocultarYmostrar(panelEdicion);
        }
        

    }
}
