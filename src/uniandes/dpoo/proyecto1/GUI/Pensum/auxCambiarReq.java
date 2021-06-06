package uniandes.dpoo.proyecto1.GUI.Pensum;

import uniandes.dpoo.proyecto1.GUI.Utilidades;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;

import javax.swing.*;
import java.awt.*;

public class auxCambiarReq extends JDialog{
    private final JLabel nombreC;
    private final TablaReq table;
    private final JLabel reqNJ;
    private final JButton bc;


    public auxCambiarReq(PanelValidaciones exter, CursoRegistrado cursoR, String reqN, HistoriaAcademica historia){
        setLayout(new GridBagLayout());
        setVisible(true);
        setSize(500,400);
        nombreC = new JLabel(cursoR.getCurso().getNombre());
        reqNJ = new JLabel(reqN);
        table = new TablaReq(historia.reqFaltantes());

        bc = new JButton("Cambiar"); bc.addActionListener(e -> {
            String req2N = table.getSelectedReq();
            if(req2N != null) {
                historia.cambiarRequerimiento(cursoR, req2N);
                dispose();
                exter.reiniciarPanel();
            }
        });
        añadirElementos();
    }

    private void añadirElementos() {
        GridBagConstraints gb = new GridBagConstraints();
        JPanel pNombre = Utilidades.centrarH( new JComponent[]{new JLabel("Nombre Curso: ") ,nombreC,
        new JLabel("Requerimiento Actual:"), reqNJ},2,1,true,false);
        Utilidades.agregarBorder(pNombre,0,0,0,0.4);
        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 1; gb.gridheight = 1; gb.weightx = 1; gb.weighty = 1; gb.fill = 1;
        add(pNombre,gb);
        gb.gridy = 1; gb.gridheight = 6; gb.weighty = 6;
        add(new JScrollPane(table),gb);
        gb.gridy = 7; gb.gridheight = 1; gb.weighty = 0;
        add(bc,gb);
    }



}
