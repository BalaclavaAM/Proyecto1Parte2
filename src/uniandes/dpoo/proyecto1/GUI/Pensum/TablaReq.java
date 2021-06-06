package uniandes.dpoo.proyecto1.GUI.Pensum;

import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

public class TablaReq extends JTable {
    private final ArrayList<String> listReq;

    public TablaReq(Map<Requerimiento,Double> reqMap){
        listReq = new ArrayList<>();
        Vector<Vector<String>> data = crearData(reqMap);
        Vector<String> colnames = new Vector<>(Arrays.asList("Nombre","Tipologia","Nivel","Mains","Creditos","Semestre", "Avance"));
        DefaultTableModel model = new DefaultTableModel(data, colnames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(model);
        getTableHeader().setReorderingAllowed(false);
        setCellSelectionEnabled(true);
        setDefaultRenderer(Object.class, new MiRender());
    }

    public String getSelectedReq(){
        int fila = getSelectedRow();
        if(fila != -1){
            return listReq.get(fila);
        }
        return null;
    }

    public Vector<Vector<String>> crearData(Map<Requerimiento,Double> reqFaltantes){
        Vector<Vector<String>> data = new Vector<>();
        ArrayList<Requerimiento> listReqOrd = new ArrayList<>(reqFaltantes.keySet());
        listReqOrd.sort(Requerimiento::compareS);
        for(Requerimiento rq: listReqOrd){
            String avance = String.valueOf(reqFaltantes.get(rq));
            data.add(new Vector<>(Arrays.asList(rq.getNombre(),rq.getTipologia().toString(),rq.getNivel().toString(),
                    Arrays.toString(rq.getMains()),String.valueOf(rq.getCreditos()),String.valueOf(rq.getSemestresugerido()),
                    avance)));
            listReq.add(rq.getNombre());
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
            if (row == table.getSelectedRow()) {
                this.setBackground(Color.cyan);
            }
            return this;
        }
    }

}
