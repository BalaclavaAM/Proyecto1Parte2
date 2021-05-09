package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;
import uniandes.dpoo.proyecto1.modelo.usuario.Usuario;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;

public class PrincipalEstudiante extends PanelAux{
    private Estudiante estudiante;
    public String RUTA = "./data/imagenes/usuarioGenerico.png";

    public PrincipalEstudiante(InterfazBannerPrincipal principal, Estudiante estudiante) {
        super(principal);

        JPanel superior = new JPanel();
        JPanel superiorDatos = new JPanel();
        setLayout(new GridLayout(2, 1));
        superior.setLayout(new GridLayout(1, 5));
        superiorDatos.setLayout(new GridLayout(3, 1));

        JTextField nombre = new JTextField(" " + estudiante.getNombre());nombre.setEditable(false);
        JTextField codigo = new JTextField(" " + estudiante.getCodigo());codigo.setEditable(false);
        JTextField carrera = new JTextField(" " + estudiante.getCarrera()); carrera.setEditable(false);

        superiorDatos.add(nombre);
        superiorDatos.add(codigo);
        superiorDatos.add(carrera);
        superiorDatos.setOpaque(false);

        superior.add(superiorDatos);
        superior.add(new JLabel());
        superior.add(new JLabel());
        superior.add(new JLabel());
        superior.add(new JLabel());
        superior.setOpaque(false);
        Map<String, CursoRegistrado> cursosInscritos = estudiante.getHistoriaAcademica().getCursosInscritos();

        JPanel inferior = new JPanel();
            //prueba
            String[][] data = new String[][]{{"DPOO","SISTEMAS","3"},{"ESCRITURA","LENGUAS","2"},{"FISICA","FISICA","3"}, {"CBUG","CBUG","2"}, {"INGLES","LENGUAS","0"}, {"INTEGRAL","MATEMATICAS","3"}};
            JPanel tablaCursosIns = new  JPanel();
            tablaCursosIns.setLayout(new GridLayout(cursosInscritos.size()+1,3));
            tablaCursosIns.add(new JLabel("Nombre"));tablaCursosIns.add(new JLabel("Materia"));tablaCursosIns.add(new JLabel("Creditos"));
            /*
            for (String[] datum : data) {
                tablaCursosIns.add(new JLabel(datum[0]));
                tablaCursosIns.add(new JLabel(datum[1]));
                tablaCursosIns.add(new JLabel(datum[2]));
            }
            */

        //Real
        for (CursoRegistrado cR: cursosInscritos.values()) {
            tablaCursosIns.add(new JLabel(cR.getCurso().getNombre()));
            tablaCursosIns.add(new JLabel(cR.getCurso().getMateria()));
            tablaCursosIns.add(new JLabel(String.valueOf(cR.getCurso().getCreditos())));
        }
        JScrollPane sp=new JScrollPane(tablaCursosIns);
        inferior.setLayout(new GridBagLayout());
        this.add(superior);
        this.add(inferior);
        GridBagConstraints gb = new GridBagConstraints();
        int alIns = 5;
        int anIns = 4;

        gb.gridx = 0; gb.gridy = 0;gb.gridheight=1;gb.gridwidth = 7;  gb.weightx = 7; gb.fill = 1; gb.weighty = 1; //rU
        inferior.add(new JLabel(),gb);
        gb.gridx = 0; gb.gridy = 1; gb.gridheight=alIns;gb.gridwidth = 1;   gb.weightx = 1; gb.fill = 1; gb.weighty = alIns; //
        inferior.add(new JLabel(),gb);
        gb.gridx = 1; gb.gridy = 1;  gb.gridwidth = anIns; gb.fill = 1; gb.weightx = anIns; gb.weighty = alIns;
        inferior.add(sp,gb);
        gb.gridx = alIns+1; gb.gridy = 1;gb.gridwidth = 2; gb.gridheight=alIns; gb.weightx = 2; gb.fill = 1; gb.weighty = alIns;
        inferior.add(new JLabel(),gb);
        tablaCursosIns.setVisible(true);
        inferior.setOpaque(false);
        inferior.setVisible(true);


        setOpaque(false);

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
            int d = (int) (0.3*Math.min(getWidth(), getHeight()));
            g.drawImage(image, (int) (getWidth()*0.7), 10,d,d,this);
        }

    }

    @Override
    public void ocultar() {

    }

    @Override
    public void mostrar(PanelImagenFondo fondo) {
        setOpaque(false);
        fondo.add(this);
    }


    @Override
    public void reset() {

    }
}
