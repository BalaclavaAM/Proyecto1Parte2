package uniandes.dpoo.proyecto1.GUI.HistAndPlan;

import uniandes.dpoo.proyecto1.GUI.InterfazBannerPrincipal;
import uniandes.dpoo.proyecto1.GUI.PanelAux;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;
import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class PanelPlanes extends PanelAux {
    private Map<String,Plan> planes;
    private JTable tablaPlanes;
    private JScrollPane scrollTabla;
    private Estudiante estudiante;
    private JPanel panelBotones;


    public PanelPlanes(InterfazBannerPrincipal principal, Estudiante estudiante) {
        super(principal);
        setLayout(new GridBagLayout());
        this.estudiante = estudiante;
        planes = estudiante.getPlanes();
        panelBotones = new JPanel(new GridLayout(1,3));
        JButton nuevo = new JButton("Nuevo Plan");nuevo.setActionCommand("Nuevo");nuevo.addActionListener((event)->{
            auxDlg nu = new auxDlg();
            nu.setVisible(true);
        });
        JButton ver = new JButton("Ver seleccionado");ver.setActionCommand("Ver");ver.addActionListener((event) ->{
            String planN = (String) tablaPlanes.getValueAt(tablaPlanes.getSelectedRow(), 0);
            Plan plan = planes.get(planN);
            PanelAux panelvista = new PanelPlan(principal, plan);
            principal.ocultarYmostrar(panelvista);
        });
        panelBotones.add(nuevo);
        panelBotones.add(ver);
        añadirElementos();
    }

    private void añadirElementos(){
        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 1; gb.gridheight = 15; gb.weightx = 1; gb.weighty = 15;gb.fill = 1;
        add(new JLabel(),gb); // relleno 1
        gb.gridx = 5; gb.gridy = 0; gb.gridwidth = 1; gb.gridheight = 15; gb.weightx = 1; gb.weighty = 15;gb.fill = 1;
        add(new JLabel(),gb); // relleno 1
        gb.gridx = 1; gb.gridy = 0; gb.gridwidth = 4; gb.gridheight = 3; gb.weightx = 4; gb.weighty = 3;gb.fill = 1;
        add(new JLabel(),gb);
        gb.gridx = 1; gb.gridy = 12; gb.gridwidth = 4; gb.gridheight = 1; gb.weightx = 4; gb.weighty = 1;gb.fill = 1;
        add(new JLabel(),gb);
        gb.gridx = 1; gb.gridy = 13; gb.gridwidth = 4; gb.gridheight = 1;  gb.weightx = 4; gb.weighty = 0;gb.fill = 1;
        add(panelBotones,gb);
        gb.gridx = 1; gb.gridy = 14; gb.gridwidth = 4; gb.gridheight = 1; gb.weightx = 4; gb.weighty =  3;
        add(new JLabel(),gb);
        actualizarPanel();
    }

    public void actualizarPanel(){
        GridBagConstraints gb = new GridBagConstraints();
        try {
            remove(scrollTabla);
            tablaPlanes = new JTable();
            scrollTabla = new JScrollPane(tablaPlanes);
        }catch (Exception ignored){
            tablaPlanes = new JTable();
            scrollTabla = new JScrollPane(tablaPlanes);
        }

        DefaultTableModel tableModel = new DefaultTableModel(crearData(planes),
                new String[]{ "Nombre", "Periodo Inicio", "Periodo Fin" }) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPlanes.setModel(tableModel);
        gb.gridx = 1; gb.gridy = 3; gb.gridwidth = 4; gb.gridheight = 6; gb.weightx = 4; gb.weighty = 3;gb.fill = 1;
        add(scrollTabla,gb);
        SwingUtilities.updateComponentTreeUI(this);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
    }

    @Override
    public void repaint() {
        super.repaint();
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


    class auxDlg extends JDialog{
        public JTextField nombre;
        auxDlg(){
            this.setSize(200,100);
            setLayout(new BorderLayout());
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
            setVisible(true);
            JLabel instrucc = new JLabel("Ingrese el nombre del plan");
            nombre = new JTextField();
            JButton agregar = new JButton("Agregar");
            agregar.addActionListener((event)->{
                estudiante.nuevoPlan(nombre.getText());
                setVisible(false);
                actualizarPanel();
            });
            add(instrucc, BorderLayout.NORTH);add(nombre, BorderLayout.CENTER);add(agregar, BorderLayout.EAST);
        }
    }

}
