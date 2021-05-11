package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;
import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;
import uniandes.dpoo.proyecto1.procesamiento.Banner;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class PanelPlanes extends PanelAux implements ActionListener {
    private Banner banner;
    private Map<String,Plan> planes;
    private JTable tablaPlanes;
    private Estudiante estudiante;
    private JPanel panelBotones;


    public PanelPlanes(InterfazBannerPrincipal principal, Estudiante estudiante) {
        super(principal);
        setLayout(new GridBagLayout());
        tablaPlanes = new JTable();
        planes = estudiante.getPlanes();
        this.estudiante = estudiante;
        panelBotones = new JPanel(new GridLayout(1,3));
        JButton nuevo = new JButton("Nuevo Plan");nuevo.setActionCommand("Nuevo");nuevo.addActionListener(this);
        JButton editar = new JButton("Editar Selecionado");editar.setActionCommand("Editar");editar.addActionListener(this);
        JButton ver = new JButton("Ver seleccionado");ver.setActionCommand("Ver");ver.addActionListener(this);
        panelBotones.add(nuevo);
        panelBotones.add(ver);
        panelBotones.add(editar);
        actualizarPanel();
    }

    private void actualizarPanel(){
        DefaultTableModel tableModel = new DefaultTableModel(crearData(planes),
                new String[]{ "Nombre", "Periodo Inicio", "Periodo Fin" }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPlanes.setModel(tableModel);
        int alto = Math.min(3,planes.size() + 1);
        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 1; gb.gridheight = 9; gb.weightx = 1; gb.weighty = 9;gb.fill = 1;
        add(new JLabel(),gb); // relleno 1
        gb.gridx = 1; gb.gridy = 0; gb.gridwidth = 4; gb.gridheight = 3; gb.weightx = 4; gb.weighty = 3;gb.fill = 1;
        add(new JLabel(),gb);
        gb.gridx = 1; gb.gridy = 4; gb.gridwidth = 4; gb.gridheight = alto; gb.weightx = 4; gb.weighty = 3;gb.fill = 1;
        add(new JScrollPane(tablaPlanes),gb);
        gb.gridx = 1; gb.gridy = alto+1; gb.gridwidth = 4; gb.gridheight = 1; gb.weightx = 4; gb.weighty = 1;gb.fill = 1;
        add(new JLabel(),gb);
        gb.gridx = 1; gb.gridy = alto+3; gb.gridwidth = 4; gb.gridheight = 2;
        add(panelBotones,gb);
        gb.gridx = 1; gb.gridy = alto+5; gb.gridwidth = 4; gb.gridheight = 4-alto;
        add(new JLabel(),gb);
    }

    private String[][] crearData(Map<String, Plan> planes) {
        String[][] data = new String[planes.size()][3];
        int i = 0;
        for(Plan plan : planes.values()){
            Periodo p1 = plan.getPrimerPeriodo(); String sp1;
            Periodo p2 = plan.getUltimoPeriodo(); String sp2;
            if(p1 == null){
                 sp1 = "Sin definir";sp2 = "Sin definir";
            }else{
                sp1 = p1.periodoS();sp2 = p2.periodoS();
            }
            data[i] = new String[]{plan.getNombre(),sp1,sp2};
            i++;
        }
        return data;
    }

    @Override
    public void mostrar(PanelImagenFondo fondo) {
        setOpaque(false);
        fondo.add(this);
    }

    class auxDlg extends JDialog implements ActionListener{
        public JTextField nombre;
        auxDlg(){
            setSize(20,20);
            setLayout(new BorderLayout());
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            setVisible(true);
            JLabel instrucc = new JLabel("Ingrese el nombre del plan");
            nombre = new JTextField();
            JButton agregar = new JButton("Agregar");
            agregar.addActionListener(this);
            add(instrucc, BorderLayout.NORTH);add(nombre, BorderLayout.CENTER);add(agregar, BorderLayout.EAST);
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            estudiante.nuevoPlan(nombre.getText());
            setVisible(false);
            actualizarPanel();
        }
    }

    public void actionPerformed(ActionEvent e) {
        String accion = e.getActionCommand();
        switch (accion) {
            case "Ver" -> {
                String planN = (String) tablaPlanes.getValueAt(tablaPlanes.getSelectedRow(), 0);
                Plan plan = planes.get(planN);
                PanelAux panelvista = new InVerPlan(principal, plan);
                principal.ocultarYmostrar(panelvista);
            }
            case "Editar" -> {
                String planN = (String) tablaPlanes.getValueAt(tablaPlanes.getSelectedRow(), 0);
                Plan plan = planes.get(planN);
                PanelAux panelEdicion = new InEdPlan(principal, plan);
                principal.ocultarYmostrar(panelEdicion);
            }
            case "Nuevo" -> {
                System.out.println("hh");
                auxDlg nu = new auxDlg();
                nu.setVisible(true);
            }
        }


    }
}
