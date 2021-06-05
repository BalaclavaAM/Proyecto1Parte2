package uniandes.dpoo.proyecto1.GUI.Pensum;

import uniandes.dpoo.proyecto1.GUI.InterfazBannerPrincipal;
import uniandes.dpoo.proyecto1.GUI.PanelAux;
import uniandes.dpoo.proyecto1.GUI.Utilidades;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;

import javax.swing.*;
import java.awt.*;

public class PanelPensum extends PanelAux {
    private TablaReq tablaReq;
    private JScrollPane scrollTabla;
    private final HistoriaAcademica historia;


    public PanelPensum(InterfazBannerPrincipal principal, HistoriaAcademica historia) {
        super(principal);
        this.historia = historia;
        setLayout(new GridBagLayout());
        Utilidades.agregarBorder(this,0.05,0.05,0.05, 0.05);
        tablaReq = new TablaReq(historia.avanceReq());
        añadirElementos();
    }

    private void añadirElementos(){
        nuevaTabla();
    }


    private void nuevaTabla() {
        GridBagConstraints gb = new GridBagConstraints();
        tablaReq = new TablaReq(historia.avanceReq());
        scrollTabla = new JScrollPane(tablaReq);
        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 1; gb.gridheight = 7; gb.weightx = 1; gb.weighty = 1; gb.fill = 1;
        add(scrollTabla, gb);
    }


    public void actualizarPanel(){
        //no hacer nada
    }

    public void reiniciarPanel(){
        try {
            remove(scrollTabla);
            nuevaTabla();
            SwingUtilities.updateComponentTreeUI(this);
        }catch(Exception ignored) { }
    }
}
