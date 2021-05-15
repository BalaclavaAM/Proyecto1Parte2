package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PrincipalEstudiante extends PrincipalUsusario{
    private Estudiante estudiante;
    public String RUTA = "./data/imagenes/usuarioGenerico.png";
    private final JTextField nombre;
    private final JTextField codigo;
    private final JTextField carrera;
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
        }; imagen.setOpaque(false);
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

        int alto = Math.min(5,inscritos.size());
        tablaInscritos.setAutoResizeMode(4);
        tablaInscritos.setMaximumSize(new Dimension(4*getWidth()/6,alto*getHeight()/14));
        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 1; gb.gridheight = 15; gb.weightx = 1; gb.weighty = 14;
        add(new JLabel(),gb); // rellenos laterales
        gb.gridx = 5;
        add(new JLabel(),gb);

        gb.gridx = 1; gb.gridy = 0; gb.gridwidth = 4; gb.gridheight = 1; gb.weightx = 4; gb.weighty = 1;
        add(new JLabel(),gb);
        gb.gridy = 2; gb.gridwidth = 2; gb.gridheight = 1; gb.weightx = 2; gb.weighty = 1;
        add(new JLabel(),gb);
        gb.gridy = 4;
        add(new JLabel(),gb);
        gb.gridy = 6;
        add(new JLabel(),gb);
        gb.gridx = 3; gb.gridy = 1; gb.gridwidth = 2; gb.gridheight = 6; gb.weightx = 2; gb.weighty = 6;gb.fill = 1;
        add(imagen,gb);
        gb.gridx = 1;gb.gridheight = 1; gb.weighty = 1;
        add(nombre,gb);
        gb.gridy = 3;
        add(codigo,gb);
        gb.gridy = 5;
        add(carrera,gb);
        gb.gridy = 7; gb.gridwidth = 4; gb.gridheight = 5;gb.weightx=4; gb.weighty = 5;
        add(new JScrollPane(tablaInscritos),gb);
        gb.gridy = 13; gb.gridwidth = 4; gb.gridheight =2; gb.weightx = 4; gb.weighty = 2;
        add(new JLabel(),gb);
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
    public void ocultar() {
        setVisible(false);
    }

    @Override
    public void mostrar(PanelImagenFondo fondo) {
        setOpaque(false);
        fondo.add(this);
    }


    @Override
    public void reset() {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Planear")){
             PanelPlanes pp = new PanelPlanes(principal,estudiante);
             principal.ocultarYmostrar(pp);
        } else if (e.getActionCommand().equals("PO>IMaterias")){
            InterfazInscripcionCursos iic = new InterfazInscripcionCursos(principal,estudiante);
            principal.ocultarYmostrar(iic);
        }

    }
}
