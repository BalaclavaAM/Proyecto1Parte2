package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.modelo.usuario.Admin;
import uniandes.dpoo.proyecto1.procesamiento.Banner;

import java.awt.event.ActionEvent;

public class PrincipalAdmin extends PrincipalUsusario {
    private Banner banner;
    public PrincipalAdmin(InterfazBannerPrincipal principal, Admin admin) {
        super(principal);
    }

    @Override
    public void mostrar(PanelImagenFondo fondo) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
