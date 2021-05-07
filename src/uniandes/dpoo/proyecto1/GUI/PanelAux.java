package uniandes.dpoo.proyecto1.GUI;

import javax.swing.*;

public abstract class PanelAux extends JPanel{

    protected final InterfazBannerPrincipal principal;

    public PanelAux(InterfazBannerPrincipal principal) {
        this.principal = principal;
    }

    public void ocultar() {setVisible(false);
    }

    public abstract void mostrar(PanelImagenFondo fondo);

    public void reset() {

    }
}
