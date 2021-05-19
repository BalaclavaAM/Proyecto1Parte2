package uniandes.dpoo.proyecto1.GUI;

import javax.swing.*;

public abstract class PanelAux extends JPanel{
    private PanelAux vistaAnterior;
    private PanelAux vistaSiguiente;

    protected final InterfazBannerPrincipal principal;

    public PanelAux(InterfazBannerPrincipal principal) {
        this.principal = principal;
        setSize(principal.getFondo().getWidth(), principal.getFondo().getHeight());
    }

    public abstract void actualizarPanel();

    public void setVistaAnterior(PanelAux vistaAnterior) {
        this.vistaAnterior = vistaAnterior;
    }

    public PanelAux getVistaAnterior(){
        return vistaAnterior;
    }

    public PanelAux getVistaSiguiente() {
        return vistaSiguiente;
    }

    public void setVistaSiguiente(PanelAux vistaSiguiente) {
        this.vistaSiguiente = vistaSiguiente;
    }

    public void reset() {

    }
}
