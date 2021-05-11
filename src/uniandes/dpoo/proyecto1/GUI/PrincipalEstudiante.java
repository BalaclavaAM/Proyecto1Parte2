package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;
import uniandes.dpoo.proyecto1.modelo.usuario.Usuario;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PrincipalEstudiante extends PrincipalUsusario{
    private Estudiante estudiante;
    public String RUTA = "./data/imagenes/usuarioGenerico.png";

    public PrincipalEstudiante(InterfazBannerPrincipal principal, Estudiante estudiante) {
        super(principal);
        this.estudiante = estudiante;
        JPanel superior = new JPanel();
        JPanel superiorDatos = new JPanel();
        setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        JTextField nombre = new JTextField(" " + estudiante.getNombre());nombre.setEditable(false);
        JTextField codigo = new JTextField(" " + estudiante.getCodigo());codigo.setEditable(false);
        JTextField carrera = new JTextField(" " + estudiante.getCarrera()); carrera.setEditable(false);
        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 1; gb.gridheight = 15; gb.weightx = 1; gb.weighty = 15;gb.fill = 1;
        add(new JLabel(),gb); // relleno 1
        gb.gridx = 1; gb.gridy = 0; gb.gridwidth = 4; gb.gridheight = 1; gb.weightx = 4; gb.weighty = 1;gb.fill = 1;
        add(new JLabel(),gb);
        gb.gridx = 1; gb.gridy = 1; gb.gridwidth = 2; gb.gridheight = 1; gb.weightx = 2; gb.weighty = 1;gb.fill = 1;
        add(nombre,gb);
        gb.gridx = 1; gb.gridy = 2; gb.gridwidth = 2; gb.gridheight = 1; gb.weightx = 2; gb.weighty = 1;gb.fill = 1;
        add(new JLabel(),gb);
        gb.gridx = 1; gb.gridy = 3; gb.gridwidth = 2; gb.gridheight = 1; gb.weightx = 2; gb.weighty = 1;gb.fill = 1;
        add(codigo,gb);
        gb.gridx = 1; gb.gridy = 4; gb.gridwidth = 2; gb.gridheight = 1; gb.weightx = 2; gb.weighty = 1;gb.fill = 1;
        add(new JLabel(),gb);
        gb.gridx = 1; gb.gridy = 5; gb.gridwidth = 2; gb.gridheight = 1; gb.weightx = 2; gb.weighty = 1;gb.fill = 1;
        add(carrera,gb);
        gb.gridx = 1; gb.gridy = 6; gb.gridwidth = 2; gb.gridheight = 1; gb.weightx = 2; gb.weighty = 1;gb.fill = 1;
        add(new JLabel(),gb);
        gb.gridx = 3; gb.gridy = 1; gb.gridwidth = 2; gb.gridheight = 6; gb.weightx = 2; gb.weighty = 6;gb.fill = 1;
        add(new JLabel(),gb);
        gb.gridx = 5; gb.gridy = 0; gb.gridwidth = 1; gb.gridheight = 15; gb.weightx = 1; gb.weighty = 15;gb.fill = 1;
        add(new JLabel(),gb);
        String[] columnNames = { "Nombre", "Codigo", "Creditos" };
        JTable tablaInscritos = new JTable();
        Map<String,CursoRegistrado> inscritos = estudiante.getHistoriaAcademica().getCursosInscritos();

        DefaultTableModel tableModel = new DefaultTableModel(crearData(inscritos),columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaInscritos.setModel(tableModel);
        gb.gridx = 1; gb.gridy = 7; gb.gridwidth = 4; gb.gridheight = inscritos.size() + 1;
        add(new JScrollPane(tablaInscritos),gb);
        gb.gridx = 1; gb.gridy = 14; gb.gridwidth = 4; gb.gridheight =7- inscritos.size(); gb.weightx = 4; gb.weighty = 7- inscritos.size();gb.fill = 1;
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
    public void paint(Graphics g) {
        Image image = null;
        super.paint(g);
        try {
            image = ImageIO.read(new File(RUTA));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (image != null) {
            int d = (int) (0.2*Math.min(getWidth(), getHeight()));
            g.drawImage(image, (int) (getWidth()*0.7), 10,d,d,this);
        }

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
