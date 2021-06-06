package uniandes.dpoo.proyecto1.GUI.Validaciones;

import uniandes.dpoo.proyecto1.GUI.InterfazBannerPrincipal;
import uniandes.dpoo.proyecto1.GUI.PanelAux;
import uniandes.dpoo.proyecto1.GUI.Utilidades;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;

import javax.swing.*;
import java.awt.*;

public class PanelValidaciones extends PanelAux {
    private TablaCursosReq tablaCursos;
    private JScrollPane scrollTabla;
    private final JButton vR;
    private final JButton cR;
    private final JButton qR;
    private final HistoriaAcademica historia;
    private final JPanel panelBotones;

    public PanelValidaciones(InterfazBannerPrincipal principal, HistoriaAcademica historia) {
        super(principal);
        this.historia = historia;
        Utilidades.agregarBorder(this, 0.1, 0.1, 0.1, 0.1);
        setLayout(new GridBagLayout());
        tablaCursos = new TablaCursosReq(this, historia);
        vR = new JButton("Validar");
        vR.addActionListener(e -> tablaCursos.validar());
        vR.setEnabled(false);
        cR = new JButton("Cambiar");
        cR.addActionListener(e -> tablaCursos.cambiar());
        cR.setEnabled(false);
        qR = new JButton("Quitar"); qR.addActionListener(e-> tablaCursos.quitar());
        qR.setEnabled(false);
        panelBotones = Utilidades.centrarH(new JComponent[]{vR,cR,qR},3,2,true,false);
        Utilidades.agregarBorder(panelBotones,0.1,0,0.2,0.2);
        añadirElementos();
    }

    public void añadirElementos(){
        nuevaTabla();
        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = 0; gb.gridy = 7; gb.gridwidth = 1; gb.gridheight = 1; gb.weightx = 1; gb.weighty = 1; gb.fill = 1;
        add(panelBotones,gb);
    }

    public void botonesA(){
        vR.setEnabled(false); cR.setEnabled(true); qR.setEnabled(true);
    }

    public void botonesB(){
        vR.setEnabled(true); cR.setEnabled(false); qR.setEnabled(false);
    }

    private void nuevaTabla() {
        GridBagConstraints gb = new GridBagConstraints();
        tablaCursos = new TablaCursosReq(this,historia);
        scrollTabla = new JScrollPane(tablaCursos);
        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 1; gb.gridheight = 7; gb.weightx = 1; gb.weighty = 7; gb.fill = 1;
        add(scrollTabla, gb);
    }


    public void actualizarPanel(){
        reiniciarPanel();
    }

    public void reiniciarPanel(){
        try {
            remove(scrollTabla);
            nuevaTabla();
            SwingUtilities.updateComponentTreeUI(this);
        }catch(Exception ignored) { }
    }
}
