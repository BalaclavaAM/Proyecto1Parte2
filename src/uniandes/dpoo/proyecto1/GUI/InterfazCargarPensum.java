package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;
import uniandes.dpoo.proyecto1.procesamiento.Banner;

import javax.swing.*;

public class InterfazCargarPensum extends PanelAux {
    private Banner banner;
    private JTextField nombrearchivo;
    private JButton cambiopensum;
    private JButton actualizarpensum;

    public InterfazCargarPensum(InterfazBannerPrincipal principal, Estudiante estudiante) {
        super(principal);
    }


    @Override
    public void actualizarPanel() {

    }
}
