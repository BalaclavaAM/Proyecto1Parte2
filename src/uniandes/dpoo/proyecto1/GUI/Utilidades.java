package uniandes.dpoo.proyecto1.GUI;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class Utilidades {

    public static JPanel centrarH(JComponent[] comps, int ancho, int marcomp, boolean Hresize, boolean Vresize){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        panel.setOpaque(false);
        int wc = Hresize?ancho:0;
        int wm = Hresize?marcomp:0;
        gb.fill = 1;
        gb.weighty = Vresize?1:0;
        gb.weightx = wc;
        gb.gridheight = 1;
        gb.gridx = 0;
        gb.gridy = 0;
        panel.add(comps[0], gb);
        for (int j = 1; j < comps.length; j++) {
            gb.gridx += ancho; gb.gridwidth = marcomp; gb.weightx = wm;
            panel.add(new JLabel(""),gb);
            gb.gridx += marcomp ; gb.gridwidth = ancho; gb.weightx = wc;
            panel.add(comps[j], gb);
        }
        return panel;
    }

    public static JPanel centrarV(JComponent[] comps, int alto, int marcomp,boolean Hresize, boolean Vresize){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        panel.setOpaque(false);
        int wc = Vresize?alto:0;
        int wm = Vresize?marcomp:0;
        gb.fill = 1;
        gb.weightx = 1;
        gb.weighty = wc;
        gb.gridwidth = 1;
        gb.gridx = 0;
        gb.gridy = 0;
        panel.add(comps[0], gb);
        for (int j = 1; j < comps.length; j++) {
            gb.gridy += alto; gb.gridheight = marcomp; gb.weighty = wm;
            panel.add(new JLabel(),gb);
            gb.gridy += marcomp ; gb.gridheight = alto; gb.weighty = wc;
            panel.add(comps[j], gb);
        }
        return panel;
    }

    public static Vector vectorNull(int n){
        Vector vectornull = new Vector<>();
        for (int i = 0; i < n; i++) {
            vectornull.add(null);
        }
        return vectornull;
    }
    public static ArrayList arrayNull(int n){
        ArrayList arraynull = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            arraynull.add(null);
        }
        return arraynull;
    }

    public static JPanel agregarBorder(JPanel panel, double top, double bottom, double left, double right) {
         panel.setBorder(new Border() {
            @Override
            public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

            }

            @Override
            public Insets getBorderInsets(Component c) {
                return new Insets((int)(panel.getHeight()*top), (int)(panel.getWidth() *left),
                        (int)(panel.getHeight()*bottom),(int)(panel.getWidth()*right));
            }
            @Override
            public boolean isBorderOpaque() {
                return false;
            }
        });
        return panel;
    }

}
