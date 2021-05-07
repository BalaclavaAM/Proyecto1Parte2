package uniandes.dpoo.proyecto1.GUI;

import javax.swing.*;
import java.awt.*;

public class PrincipalEstudiante extends PanelAux{

    public PrincipalEstudiante(InterfazBannerPrincipal principal) {
        super(principal);
        setLayout(new GridLayout(3, 1));
        JPanel superior = new JPanel();
        superior.setLayout(new GridLayout(1,2));

    }

    @Override
    public void ocultar() {

    }

    @Override
    public void mostrar(PanelImagenFondo fondo) {

    }


    @Override
    public void reset() {

    }
}
