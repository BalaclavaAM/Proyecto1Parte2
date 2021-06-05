package uniandes.dpoo.proyecto1.GUI.PrincipalUsurio;

import uniandes.dpoo.proyecto1.GUI.HistAndPlan.PanelHistoria;
import uniandes.dpoo.proyecto1.GUI.HistAndPlan.PanelPlanes;
import uniandes.dpoo.proyecto1.GUI.InterfazBannerPrincipal;
import uniandes.dpoo.proyecto1.GUI.InterfazInscripcionCursos;
import uniandes.dpoo.proyecto1.GUI.Pensum.PanelPensum;
import uniandes.dpoo.proyecto1.GUI.Validaciones.PanelValidaciones;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PrincipalEstudiante extends PrincipalUsusario {
    private final Estudiante estudiante;
    public String RUTA = "./data/imagenes/usuarioGenerico.png";
    private final JTextField nombre;
    private final JTextField codigo;
    private final JTextField carrera;
    private PanelPlanes panelPlanes;
    private final JPanel imagen;

    public PrincipalEstudiante(InterfazBannerPrincipal principal, Estudiante estudiante) {
        super(principal);
        this.estudiante = estudiante;
        this.imagen = new JPanel(){
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
                    int d = (int) (0.65*Math.min(getWidth(), getHeight()));
                    g.drawImage(image,(getWidth()-d),  0,d,d,this);
                }

            }
        };
        setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets(getHeight()/10,getWidth()/10, getHeight()/10,getWidth()/10);
            }

            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        });
        imagen.setOpaque(false);
        setLayout(new GridBagLayout());
        nombre = new JTextField(estudiante.getNombre());nombre.setEditable(false);
        codigo = new JTextField(estudiante.getCodigo());codigo.setEditable(false);
        carrera = new JTextField(estudiante.getCarrera()); carrera.setEditable(false);
        actualizarPanel();

    }

    public void actualizarPanel() {
        JTable tablaInscritos = new JTable();
        GridBagConstraints gb = new GridBagConstraints();
        Map<String,CursoRegistrado> inscritos = estudiante.getHistoriaAcademica().getCursosInscritos();

        DefaultTableModel tableModel = new DefaultTableModel(new String[][]{}, new String[]{"Nombre", "Codigo", "Creditos"}) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };
        for(CursoRegistrado cr: inscritos.values()){
            Curso c = cr.getCurso();
            tableModel.addRow(new String[]{c.getNombre(),c.getCodigo(), String.valueOf(c.getCreditos())});
        }

        tablaInscritos.setModel(tableModel);

        gb.gridx = 0; gb.gridy = 1; gb.gridwidth = 2; gb.gridheight = 1; gb.weightx = 2; gb.weighty = 1;gb.fill = 1;
        add(new JLabel(),gb);
        gb.gridy = 3;
        add(new JLabel(),gb);
        gb.gridy = 5;
        add(new JLabel(),gb);
        gb.gridy = 0;
        add(nombre,gb);
        gb.gridy = 2;
        add(codigo,gb);
        gb.gridy = 4;
        add(carrera,gb);
        gb.gridx = 2; gb.gridy = 0; gb.gridwidth = 2; gb.gridheight = 6; gb.weightx = 2; gb.weighty = 6;
        add(imagen,gb);
        gb.gridx = 0; gb.gridy = 6; gb.gridwidth = 4; gb.gridheight = 4;gb.weightx=4; gb.weighty = 4;
        add(new JScrollPane(tablaInscritos),gb);
    }


    public String[][] crearData(Map<String,CursoRegistrado> inscritos){
        String[][] data = new String[inscritos.size()][3];
        int i = 0;
        for(CursoRegistrado cursoR : inscritos.values()){
            Curso c = cursoR.getCurso();
            data[i] = new String[]{c.getNombre(),c.getCodigo(), String.valueOf(c.getCreditos())};
            i++;
        }
        return data;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "PO>Planear" -> {
                if (panelPlanes == null) {
                    panelPlanes = new PanelPlanes(principal, estudiante);
                } else {
                    panelPlanes.actualizarPanel();
                }
                principal.ocultarYmostrar(panelPlanes);
            }
            case "PO>IMaterias" -> {
                InterfazInscripcionCursos iic = new InterfazInscripcionCursos(principal, estudiante);
                principal.ocultarYmostrar(iic);
            }
            case "PO>HAcademica" -> {
                PanelHistoria ph = new PanelHistoria(principal, estudiante);
                principal.ocultarYmostrar(ph);
            }
            case  "PO>Pensum" ->{
                PanelPensum pp = new PanelPensum(principal,estudiante.getHistoriaAcademica());
                principal.ocultarYmostrar(pp);
            }

            case "PO>Validar" ->{
                PanelValidaciones pv = new PanelValidaciones(principal,estudiante.getHistoriaAcademica());
                principal.ocultarYmostrar(pv);
            }

        }

    }
}
