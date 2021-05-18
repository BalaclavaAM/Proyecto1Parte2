package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;
import uniandes.dpoo.proyecto1.procesamiento.Banner;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Map;

public class InterfazInscripcionCursos extends PanelAux implements ActionListener {
    private Banner banner;
    private JButton buscar;
    private JComboBox departamento;
    private JTextField codigomateria;
    private JButton inscribir;
    private GridLayout materias;
    private TablaSecciones tablamaterias;
    private String[][] data;

    public InterfazInscripcionCursos(InterfazBannerPrincipal principal, Estudiante estudiante) {
        super(principal);
        setSize(principal.getPrincipalUsuario().getSize());
        banner = principal.getBanner();
        setLayout(new BorderLayout());

        buscar = new JButton("Buscar");
        buscar.addActionListener(this);
        buscar.setActionCommand("buscarMateria");
        departamento = new JComboBox();

        codigomateria = new JTextField("");
        inscribir = new JButton("Inscribir");
        materias = new GridLayout(6,5);
        Map<String, Map<String, Curso>> mapacursos = banner.getCursosDepartamento();
        alimentarComboBox(mapacursos);
        setBorder(new EmptyBorder(20,20,20,20));
        JPanel panelarriba = new JPanel();
        panelarriba.setLayout(new GridLayout(1,5));
        panelarriba.setOpaque(false);
        JPanel panelchikitoarriba = new JPanel();
        panelchikitoarriba.setOpaque(false);
        panelchikitoarriba.setLayout(new GridLayout(5,1));
        panelchikitoarriba.add(departamento);
        panelchikitoarriba.add(crearVacio());
        panelchikitoarriba.add(codigomateria);
        panelchikitoarriba.add(crearVacio());

        panelchikitoarriba.add(buscar);

        panelarriba.add(panelchikitoarriba);
        panelarriba.add(crearVacio());
        panelarriba.add(crearVacio());
        panelarriba.add(crearVacio());
        panelarriba.add(crearVacio());
        add(panelarriba,BorderLayout.NORTH);
        tablamaterias = new TablaSecciones(banner);
        add(tablamaterias,BorderLayout.CENTER);


    }
    private void armarTabla(Map<String, Map<String, Curso>> mapCursos){
        String[] columnas = {"Código","NRC","Profesor","Horario","Materia","Créditos","Ciclo","Epsilon","Tipo E"};
        ArrayList<Curso> cursos = banner.filtrarCursos("",codigomateria.getText());
    }

    private void actualizarTabla(){
        tablamaterias.removeAll();
        ArrayList<Curso> cursos = banner.filtrarCursos(departamento.getSelectedItem().toString(),codigomateria.getText());
        data = new String[cursos.size()][5];
        Integer x = 0;
        for (Curso curso:cursos){
            data[x][0]=curso.getCodigo();
            data[x][1]=curso.getNombre();
            data[x][2]=Integer.toString(curso.getCreditos());
            data[x][3]="e";
            data[x][4]="a";
            x++;
        }
        tablamaterias.repaint();
    }
    private void alimentarComboBox(Map<String, Map<String, Curso>> mapCursos){
        for (Map.Entry<String, Map<String,Curso>> entry1 : mapCursos.entrySet()){
            departamento.addItem(entry1.getKey());
        }
    }


    private JPanel crearVacio(){
        JPanel momos = new JPanel();
        momos.setOpaque(false);
        return momos;
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("buscarMateria")){
            actualizarTabla();
        }
    }

    @Override
    public void actualizarPanel() {

    }
}
