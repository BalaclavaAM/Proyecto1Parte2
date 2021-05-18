package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.modelo.usuario.Carrera;
import uniandes.dpoo.proyecto1.modelo.usuario.Coordinador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrincipalCoordinador extends PrincipalUsusario{
    private Carrera carrera;
    public PrincipalCoordinador(InterfazBannerPrincipal principal, Coordinador coordinador) {
        super(principal);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void actualizarPanel() {

    }
}
