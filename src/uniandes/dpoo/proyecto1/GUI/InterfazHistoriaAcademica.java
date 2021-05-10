package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;

import javax.swing.*;

public class InterfazHistoriaAcademica extends PanelAux {
    private JButton addCurso;
    private JButton validateRequirement;
    private JButton getValidateRequirement2;
    private JButton addPeriodo;
    private JButton updateCursoInformation;
    private JPanel addCursopanel;
    private JPanel addRequeriment;

    public InterfazHistoriaAcademica(InterfazBannerPrincipal principal, Estudiante e) {
        super(principal);
    }

    @Override
    public void mostrar(PanelImagenFondo fondo) {

    }
}
