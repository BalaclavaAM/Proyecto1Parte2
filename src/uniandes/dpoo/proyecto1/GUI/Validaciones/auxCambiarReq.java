package uniandes.dpoo.proyecto1.GUI.Validaciones;

import uniandes.dpoo.proyecto1.GUI.Utilidades;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class auxCambiarReq extends JDialog{
    private final ArrayList<Requerimiento> reqFaltantes;
    private final JLabel nombreC;
    private final JTable table;
    private final JLabel reqNJ;
    private final JButton bc;


    public auxCambiarReq(PanelRequerimientos exter, CursoRegistrado cursoR, String reqN, HistoriaAcademica historia){
        setLayout(new GridBagLayout());
        setVisible(true);
        setSize(500,400);
        nombreC = new JLabel(cursoR.getCurso().getNombre());
        reqNJ = new JLabel(reqN);
        table = new JTable();
        Vector<String> colnames = new Vector<>(Arrays.asList("Nombre","Tipologia","Nivel","Mains","Creditos","Semestre"));
        reqFaltantes = historia.reqFaltantes();
        table.setModel(new DefaultTableModel(auxValidarReq.crearData(reqFaltantes),colnames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return super.isCellEditable(row, column);
            }
        });
        bc = new JButton("Cambiar"); bc.addActionListener(e -> {
            int fila = table.getSelectedRow();
            if(fila != -1) {
                historia.cambiarRequerimiento(cursoR, reqFaltantes.get(fila).getNombre());
                dispose();
            }
        });
        table.getTableHeader().setReorderingAllowed(false);
        table.setCellSelectionEnabled(true);
        table.setDefaultRenderer(Object.class, new auxValidarReq.MiRender());
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
        add(table,gb);
        gb.gridy = 7; gb.gridheight = 1; gb.weighty = 1;
        add(bc,gb);
    }



}
