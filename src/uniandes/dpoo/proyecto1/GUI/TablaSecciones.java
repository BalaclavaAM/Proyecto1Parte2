package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.procesamiento.Banner;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TablaSecciones extends JPanel {
    private final Banner banner;
    private final boolean DEBUG = true;

    public TablaSecciones(Banner bBanner) {
        super(new GridLayout(1, 0));
        banner = bBanner;
        String[] columnNames = {"Código",
                "NRC",
                "Profesor",
                "Horario",
                "Materia",
                "Créditos",
                "Ciclo",
                "Epsilon",
                "Tipo E"};

        Object[][] data = banner.getAllSeccionesForJTable();

        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        if (DEBUG) {
            table.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    printDebugData(table);
                }
            });
        }

        //Create the scroll pane and add the table to it.
        JScrollPane scrollPane = new JScrollPane(table);

        //Add the scroll pane to this panel.
        add(scrollPane);
    }

    private void printDebugData(JTable table) {
        int numRows = table.getRowCount();
        int numCols = table.getColumnCount();
        javax.swing.table.TableModel model = table.getModel();

        System.out.println("Value of data: ");
        for (int i=0; i < numRows; i++) {
            System.out.print("    row " + i + ":");
            for (int j=0; j < numCols; j++) {
                System.out.print("  " + model.getValueAt(i, j));
            }
            System.out.println();
        }
        System.out.println("--------------------------");
    }
}