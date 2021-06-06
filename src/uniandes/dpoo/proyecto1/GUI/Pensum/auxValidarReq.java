package uniandes.dpoo.proyecto1.GUI.Pensum;

import uniandes.dpoo.proyecto1.GUI.Utilidades;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Vector;

public class auxValidarReq extends JDialog {
    private final JLabel nombreC;
    private final TablaReq table;
    private final JButton bv;


    public auxValidarReq(PanelValidaciones exter, CursoRegistrado cursoR, HistoriaAcademica historia){
        setLayout(new GridBagLayout());
        setVisible(true);
        setSize(500,400);
        nombreC = new JLabel(cursoR.getCurso().getNombre());
        table = new TablaReq(historia.reqFaltantes());
        Vector<String> colnames = new Vector<>(Arrays.asList("Nombre","Tipologia","Nivel","Mains","Creditos","Semestre", "Avance"));
        bv = new JButton("Validar"); bv.addActionListener(e -> {
            String reqN = table.getSelectedReq();
            if(reqN != null) {
                historia.validarRequerimiento(cursoR, reqN);
                dispose();
                exter.reiniciarPanel();
            }
        });
        añadirElementos();
    }

    private void añadirElementos(){
        GridBagConstraints gb = new GridBagConstraints();
        JPanel pNombre = Utilidades.centrarH( new JComponent[]{new JLabel("Nombre Curso: ") ,nombreC,},2,1,
                true,false);
        Utilidades.agregarBorder(pNombre,0,0,0,0.7);
        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 1; gb.gridheight = 1; gb.weightx = 1; gb.weighty = 1; gb.fill = 1;
        add(pNombre,gb);
        gb.gridy = 1; gb.gridheight = 6; gb.weighty = 6;
        add(new JScrollPane(table),gb);
        gb.gridy = 7; gb.gridheight = 1; gb.weighty = 0;
        add(bv,gb);
    }


}
