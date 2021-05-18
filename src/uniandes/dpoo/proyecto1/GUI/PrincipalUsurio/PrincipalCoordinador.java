package uniandes.dpoo.proyecto1.GUI.PrincipalUsurio;

import uniandes.dpoo.proyecto1.GUI.InterfazBannerPrincipal;
import uniandes.dpoo.proyecto1.modelo.usuario.Carrera;
import uniandes.dpoo.proyecto1.modelo.usuario.Coordinador;

import java.awt.event.ActionEvent;

public class PrincipalCoordinador extends PrincipalUsusario {
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
