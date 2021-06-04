package uniandes.dpoo.proyecto1.GUI.Validaciones;

import uniandes.dpoo.proyecto1.GUI.Utilidades;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Vector;

public class auxValidarReq extends JDialog {
    private final ArrayList<Requerimiento> reqFaltantes;
    private final JLabel nombreC;
    private final JTable table;
    private final JButton bv;


    public auxValidarReq(PanelRequerimientos exter, CursoRegistrado cursoR, HistoriaAcademica historia){
        setLayout(new GridBagLayout());
        setVisible(true);
        setSize(500,400);
        nombreC = new JLabel(cursoR.getCurso().getNombre());
        table = new JTable();
        Vector<String> colnames = new Vector<>(Arrays.asList("Nombre","Tipologia","Nivel","Mains","Creditos","Semestre"));
        reqFaltantes = historia.reqFaltantes();
        table.setModel(new DefaultTableModel(crearData(reqFaltantes),colnames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return super.isCellEditable(row, column);
            }
        });

        bv = new JButton("Validar"); bv.addActionListener(e -> {
            int fila = table.getSelectedRow();
            if(fila != -1) {
                historia.validarRequerimiento(cursoR, reqFaltantes.get(fila).getNombre());
                dispose();
            }
        });
        table.getTableHeader().setReorderingAllowed(false);
        table.setCellSelectionEnabled(true);
        table.setDefaultRenderer(Object.class, new MiRender());
        añadirElementos();
    }

    private void añadirElementos() {
        GridBagConstraints gb = new GridBagConstraints();
        JPanel pNombre = Utilidades.centrarH( new JComponent[]{new JLabel("Nombre Curso: ") ,nombreC,},2,1,
                true,false);
        Utilidades.agregarBorder(pNombre,0,0,0,0.7);
        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 1; gb.gridheight = 1; gb.weightx = 1; gb.weighty = 1; gb.fill = 1;
        add(pNombre,gb);
        gb.gridy = 1; gb.gridheight = 6; gb.weighty = 6;
        add(table,gb);
        gb.gridy = 7; gb.gridheight = 1; gb.weighty = 1;
        add(bv,gb);
    }


    public static Vector<Vector<String>> crearData(ArrayList<Requerimiento> reqFaltantes){
        Vector<Vector<String>> data = new Vector<>();
        for(Requerimiento rq: reqFaltantes){
            data.add(new Vector<>(Arrays.asList(rq.getNombre(),rq.getTipologia().toString(),rq.getNivel().toString(),
                    Arrays.toString(rq.getMains()),String.valueOf(rq.getCreditos()),String.valueOf(rq.getSemestresugerido()))));
        }
        return data;
    }

    static class MiRender extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (row % 2 == 0) {
                this.setBackground(Color.WHITE);
                this.setForeground(Color.BLACK);
            } else {
                this.setBackground(Color.BLACK);
                this.setForeground(Color.WHITE);
            }
            if (isSelected) {
                this.setBackground(getBackground().darker());
            }
            return this;
        }
    }
}
