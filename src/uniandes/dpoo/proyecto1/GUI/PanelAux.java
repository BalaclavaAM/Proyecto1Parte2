package uniandes.dpoo.proyecto1.GUI;

import javax.swing.*;

public abstract class PanelAux extends JPanel{

    protected final InterfazBannerPrincipal principal;

    public PanelAux(InterfazBannerPrincipal principal) {
        this.principal = principal;
        setSize((int)(principal.getWidth()*0.9),(int)(principal.getHeight()*0.9));
    }

    public void ocultar() {setVisible(false);
    }

    public abstract void mostrar(PanelImagenFondo fondo);

    public void reset() {

    }
}
