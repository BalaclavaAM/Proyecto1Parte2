package uniandes.dpoo.proyecto1.GUI.HistAndPlan;

import uniandes.dpoo.proyecto1.GUI.InterfazBannerPrincipal;
import uniandes.dpoo.proyecto1.GUI.PanelAux;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.EstadoCurso;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PanelPlan extends PanelAux {
    private final JTextField Jnombre;
    private final JButton Jvalidar;
    private JTable tablaPlan;
    private JScrollPane scrollTabla;
    private final JPanel panelBotones;
    private boolean agregando;
    private final ArrayList<ArrayList<CursoRegistrado>> posicionCursos;
    private final ArrayList<ArrayList<String>> posicionEstado;
    private final AuxCambios aux;



    public PanelPlan(InterfazBannerPrincipal principal, Plan plan) {
        super(principal);
        setLayout(new GridBagLayout());
        setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(getHeight()/8,getWidth()/8, getHeight()/8,getWidth()/8);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        });
        Jnombre = new JTextField(plan.getNombre());
        Jnombre.setEditable(false);
        agregando = false;
        Jnombre.setHorizontalAlignment(0);
        tablaPlan = new JTable();
        panelBotones = new JPanel(new GridLayout(1,4));
        Map<String, CursoRegistrado> cursosAgregar = new HashMap<>();
        Map<String, ArrayList<CursoRegistrado>> cursosQuitar = new HashMap<>();
        posicionCursos = new ArrayList<>();
        posicionEstado = new  ArrayList<>();
        aux = new AuxCambios(plan, this, posicionCursos, posicionEstado, cursosAgregar, cursosQuitar);
        JButton mostrarIn = new JButton("Mostra Info"); mostrarIn.addActionListener(e-> aux.mostrarInfo(false));
        JButton agregar = new JButton("Agregar Curso"); agregar.addActionListener(e->{
            AgregarAux au = new AgregarAux(this, principal.getBanner(), posicionCursos, posicionEstado, cursosAgregar, tablaPlan,false);
            au.setVisible(true);
        });
        JButton quitar = new JButton("Quitar Seleccionado"); quitar.addActionListener(e -> aux.quitar());

        JButton deshacer = new JButton("Deshacer accion");deshacer.addActionListener(e -> aux.deshacer());

        panelBotones.add(mostrarIn);panelBotones.add(agregar);panelBotones.add(quitar);panelBotones.add(deshacer);
         Jvalidar = new JButton("Validar");Jvalidar.addActionListener(e-> aux.validarMalla());
        añadirElementos();
    }



    public void añadirElementos(){
        GridBagConstraints gb = new GridBagConstraints();
        JPanel conNombre = AuxCambios.centrar(Jnombre,1,1,2,2);
        JPanel conValidar = AuxCambios.centrar(Jvalidar,1,1,2,1);
        gb.gridx = 1; gb.gridwidth = 4; gb.gridheight = 3;  gb.weightx = 4; gb.weighty = 3;gb.fill = 1;
        add(conNombre,gb);
        gb.gridy = 8;
        add(conValidar,gb);
        gb.gridx = 1; gb.gridy=7; gb.gridwidth = 4; gb.gridheight = 1;  gb.weightx = 4; gb.weighty = 0;
        add(panelBotones,gb);
        agregando = true;
        actualizarPanel();
    }

    public void actualizarPanel(){
        GridBagConstraints gb = new GridBagConstraints();
        try {        remove(scrollTabla);
        }catch(Exception ignored) { }
        if(agregando) {
            tablaPlan = aux.crearTabla();
        }
        scrollTabla = new JScrollPane(tablaPlan);
        gb.gridx = 1;gb.gridy = 3;gb.gridwidth = 4;gb.gridheight = 4;gb.weightx = 4;gb.weighty = 4;gb.fill = 1;
        add(scrollTabla, gb);
    }

    class MiRender extends DefaultTableCellRenderer
    {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            this.setOpaque(false);
            String estado = posicionEstado.get(row).get(column);
            if (estado != null) {
                if (estado.equals("agregar")) {
                    this.setOpaque(true);
                    this.setBackground(Color.GREEN);
                    this.setForeground(Color.BLUE);
                } else if (estado.equals("quitar")) {
                    this.setOpaque(true);
                    this.setBackground(Color.RED);
                    this.setForeground(Color.YELLOW);
                } else {
                    this.setBackground(Color.WHITE);
                    this.setForeground(Color.BLACK);
                }
            }
            else {
                this.setBackground(Color.WHITE);
                this.setForeground(Color.BLACK);
            }
            return this;
        }
    }



}
