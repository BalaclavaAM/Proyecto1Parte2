package uniandes.dpoo.proyecto1.GUI.HistAndPlan;

import uniandes.dpoo.proyecto1.GUI.InterfazBannerPrincipal;
import uniandes.dpoo.proyecto1.GUI.PanelAux;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PanelHistoria extends PanelAux {
    public String RUTA = "./data/imagenes/usuarioGenerico.png";
    private final Estudiante estudiante;
    private final JLabel creditosIIn;
    private final JLabel creditosAIn;
    private final JLabel promedioIn;
    private final JPanel imagen;
    private JTable tablaHistoria;
    private JScrollPane scrollTabla;
    private final JButton Bvalidar;
    private final JPanel panelDatos;
    private final JPanel panelBotones;
    private final JPanel panelInfo;
    private boolean agregando;
    private final ArrayList<ArrayList<CursoRegistrado>> posicionCursos;
    private final ArrayList<ArrayList<String>> posicionEstado;
    private final AuxCambios aux;


    public PanelHistoria(InterfazBannerPrincipal principal, Estudiante estudiante) {
        super(principal);
        this.estudiante = estudiante;
        setLayout(new GridBagLayout());
        JTextField nombreJT = new JTextField(estudiante.getNombre());
        nombreJT.setEditable(false);
        JTextField codigoJT = new JTextField(estudiante.getCodigo());
        codigoJT.setEditable(false);
        nombreJT.setEditable(false);
        agregando = false;
        nombreJT.setHorizontalAlignment(0);
        tablaHistoria = new JTable();
        Map<String, CursoRegistrado> cursosAgregar = new HashMap<>();
        Map<String, ArrayList<CursoRegistrado>> cursosQuitar = new HashMap<>();
        posicionCursos = new ArrayList<>();
        posicionEstado = new ArrayList<>();
        this.imagen = new JPanel() {
            @Override
            public void paint(Graphics g) {
                Image image = null;
                super.paint(g);
                try {
                    image = ImageIO.read(new File(RUTA));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (image != null) {
                    int d = (int) (0.65 * Math.min(getWidth(), getHeight()));
                    g.drawImage(image, (getWidth() - d), 0, d, d, this);
                }

            }
        };
        imagen.setOpaque(false);
        aux = new AuxCambios(estudiante.getHistoriaAcademica(), this, posicionCursos, posicionEstado, cursosAgregar,
                cursosQuitar, new MiRender());
        JButton mostrarIn = new JButton("Mostra Info");
        mostrarIn.addActionListener(e -> aux.mostrarInfo(false));
        JButton agregar = new JButton("Agregar Curso");
        agregar.addActionListener(e -> {
            AgregarAux au = new AgregarAux(this, principal.getBanner(), posicionCursos, posicionEstado, cursosAgregar,
                    tablaHistoria, true);
            au.setVisible(true);
        });
        JButton quitar = new JButton("Quitar");
        quitar.addActionListener(e -> aux.quitar());
        JButton Bnota = new JButton("Actualizar Nota");
        Bnota.addActionListener(e -> {
            int fila = tablaHistoria.getSelectedRow();
            int col = tablaHistoria.getSelectedColumn();
            CursoRegistrado cursoR = posicionCursos.get(fila).get(col);
            if (fila != -1 && cursoR!= null) {
                cambiatNotaAux cn = new cambiatNotaAux(cursoR, estudiante.getHistoriaAcademica());
                cn.setVisible(true);
            }
        });
        JButton deshacer = new JButton("Deshacer accion");
        deshacer.addActionListener(e -> aux.deshacer());
        JLabel semestreIn = new JLabel();
        creditosIIn = new JLabel();
        creditosAIn = new JLabel();
        promedioIn = new JLabel();

        panelInfo = new JPanel(new GridLayout(1,8));
        panelInfo.add(new JLabel("Semestre: "));
        panelInfo.add(semestreIn);
        panelInfo.add(new JLabel("Creditos"));
        panelInfo.add(creditosIIn);
        panelInfo.add(new JLabel("Creditos Aproavdos"));
        panelInfo.add(creditosAIn);
        panelInfo.add(new JLabel("Promedio"));
        panelInfo.add(promedioIn);
        
        panelDatos = new JPanel(new GridLayout(3,1));
        panelDatos.add(new JLabel("Nombre: " + estudiante.getNombre()));
        panelDatos.add(new JLabel("Codigo: " + estudiante.getCodigo()));
        panelDatos.add(new JLabel("Periodo Ingreso: " + estudiante.getHistoriaAcademica().getPrimerPeriodo()));


        panelBotones = new JPanel(new GridLayout(1, 4));
        panelBotones.add(mostrarIn);
        panelBotones.add(agregar);
        panelBotones.add(quitar);
        panelBotones.add(deshacer);
        panelBotones.add(Bnota);
        Bvalidar = new JButton("Validar");
        Bvalidar.addActionListener(e -> aux.validarMalla());
        añadirElementos();
    }


    public void añadirElementos() {
        GridBagConstraints gb = new GridBagConstraints();
        gb. gridx = 0; gb.gridy = 0;  gb.gridwidth = 1; gb.gridheight = 19; gb.weightx = 1; gb.weighty = 19; gb.fill =1;
        add(new JLabel(),gb);
        gb.gridx = 7;
        add(new JLabel(),gb);
        gb.gridx = 1; gb.gridwidth = 6; gb.gridheight = 1; gb.weightx = 6; gb.weighty = 1;
        add(new JLabel(),gb);
        gb.gridy = 1; gb.gridwidth = 2; gb.gridheight = 3; gb.weightx = 2; gb.weighty = 3;
        add(panelDatos,gb);
        gb.gridy = 4;
        add(new JLabel(),gb);
        gb.gridx = 3; gb.gridy = 1; gb.gridwidth = 4; gb.gridheight = 6; gb.weightx = 4; gb.weighty = 6;
        add(imagen,gb);
        gb.gridy = 14; gb.gridheight = 3; gb.weighty = 3;
        add(AuxCambios.centrar(Bvalidar,1,2,2,1),gb);
        gb.gridx = 1; gb.gridy = 13; gb.gridwidth = 6; gb.gridheight = 1; gb.weightx = 6; gb.weighty = 1;
        add(panelBotones,gb);
        gb.gridy = 17;
        add(panelInfo,gb);
        gb.gridy = 18;
        add(new JLabel(),gb);
        agregando = true;
        actualizarPanel();
    }

    public void actualizarPanel() {
        GridBagConstraints gb = new GridBagConstraints();
        try {
            remove(scrollTabla);
        } catch (Exception ignored) {
        }
        if(agregando){
            tablaHistoria = aux.crearTabla();
            actualizarInfo();
        }
        gb.gridx = 1; gb.gridy = 7; gb.gridwidth = 6; gb.gridheight = 6; gb.weightx = 6; gb.weighty = 6; gb.fill = 1;
        scrollTabla = new JScrollPane(tablaHistoria);
        add(scrollTabla, gb);

    }

    public void actualizarInfo(){
        promedioIn.setText(String.valueOf(estudiante.getHistoriaAcademica().calcularPromedioAcademico()));
        creditosAIn.setText(String.valueOf(estudiante.getHistoriaAcademica().getCreditosAprovados()));
        creditosIIn.setText(String.valueOf(estudiante.getHistoriaAcademica().getCreditos()));
    }

    class MiRender extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column) {
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
            } else {
                this.setBackground(Color.WHITE);
                this.setForeground(Color.BLACK);
            }
            return this;
        }
    }
}
