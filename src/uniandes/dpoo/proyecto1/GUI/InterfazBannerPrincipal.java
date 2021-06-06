package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.GUI.EstructuraVentana.PanelImagenFondo;
import uniandes.dpoo.proyecto1.GUI.EstructuraVentana.PanelOptions;
import uniandes.dpoo.proyecto1.GUI.EstructuraVentana.PanelSuperior;
import uniandes.dpoo.proyecto1.GUI.PrincipalUsurio.PrincipalAdmin;
import uniandes.dpoo.proyecto1.GUI.PrincipalUsurio.PrincipalCoordinador;
import uniandes.dpoo.proyecto1.GUI.PrincipalUsurio.PrincipalEstudiante;
import uniandes.dpoo.proyecto1.GUI.PrincipalUsurio.PrincipalUsusario;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.usuario.Admin;
import uniandes.dpoo.proyecto1.modelo.usuario.Coordinador;
import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;
import uniandes.dpoo.proyecto1.modelo.usuario.Usuario;
import uniandes.dpoo.proyecto1.procesamiento.Banner;
import uniandes.dpoo.proyecto1.procesamiento.LoaderData;
import uniandes.dpoo.proyecto1.procesamiento.Prueba;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.Serial;

import javax.swing.*;

public class InterfazBannerPrincipal extends JFrame implements ActionListener {
    @Serial
    private static final long serialVersionUID = 7466153715672542511L;
	private final Banner banner;
	private final PanelOptions opciones;
    private final PanelImagenFondo fondo;
	private final PanelLogin panelLogin;
	private PanelAux vistaAct;
	private PrincipalUsusario principalUsuario;


	public InterfazBannerPrincipal () {
		setTitle( "Banner" );
        getContentPane( ).setLayout( new BorderLayout( ) );
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize( 1000, 650 );
        opciones = new PanelOptions(this);
        fondo = new PanelImagenFondo();
        PanelSuperior superior = new PanelSuperior(this);
        panelLogin = new PanelLogin(this);
        banner = new Banner(new Periodo(2021,10,1));
        Prueba.cargarPrueba(banner);
        try {
            LoaderData.CargaCursos(banner);
            LoaderData.CargaSecciones(banner);
            LoaderData.cargarSeccionesNativas(banner);
        } catch (Exception e) {
            e.printStackTrace();
        }
        fondo.setLayout(new BorderLayout());
        add(fondo,BorderLayout.CENTER);
        add(opciones,BorderLayout.WEST);
        add(superior,BorderLayout.NORTH);
        mostrar(panelLogin);
	}


    public static void main( String[] args )
    {
        InterfazBannerPrincipal interfaz = new InterfazBannerPrincipal( );
        interfaz.setVisible(true) ;
    }


    public void ocultarYmostrar(PanelAux pa){
	    vistaAct.setVistaSiguiente(pa);
        pa.setVistaAnterior(vistaAct);
        vistaAct.setVisible(false);
        mostrar(pa);
    }

    public void mostrar(PanelAux pa){
        vistaAct = pa;
        pa.setOpaque(false);
        fondo.add(pa);
        pa.setVisible(true);
    }

    public void siguiente(){
        PanelAux pa = vistaAct.getVistaSiguiente();
        if(pa != null){
            ocultarYmostrar(pa);
        }
    }

    public void anterior(){
        PanelAux pa = vistaAct.getVistaAnterior();
        if(pa != null){
            vistaAct.setVisible(false);
            mostrar(pa);
        }
    }

    public void actualizarPanel() {
	    panelLogin.actualizarPanel();
	    mostrar(panelLogin);
    }

    public void cerrarSeccion(){
        if(principalUsuario != null){
            opciones.quitarBotones();
            vistaAct.setVisible(false);
            principalUsuario = null;
            vistaAct = null;
            actualizarPanel();
        }
    }

    public PrincipalUsusario getPrincipalUsuario() {
        return principalUsuario;
    }

    public void setVistaAct(PanelAux vistaAct) {
        this.vistaAct = vistaAct;
    }

    public void mostrarPrincipal(Usuario user){
        if(user!=null){
            String permission = user.getPermission();
            switch (permission) {
                case "Estudiante" -> {
                    principalUsuario = new PrincipalEstudiante(this, (Estudiante) user);
                    opciones.botonesEstudiant(principalUsuario);
                }
                case "Coordinador" -> {
                    principalUsuario = new PrincipalCoordinador(this, (Coordinador) user);
                    opciones.botonesAdmi(principalUsuario);
                }
                case "Admin" -> {
                    principalUsuario = new PrincipalAdmin(this, (Admin) user);
                    opciones.botonesAdmi(principalUsuario);
                }
                default -> System.out.println("wrong permision");
            }
            if(principalUsuario != null) {
                panelLogin.setVisible(false);
                mostrar(principalUsuario);
                setVistaAct(principalUsuario);
            }

        }else {
            actualizarPanel();
            System.out.println("error de inicio");
        }
    }

    public void home(){
	    if(principalUsuario != null) {
            ocultarYmostrar(principalUsuario);
        }else{
	        ocultarYmostrar(panelLogin);
	        panelLogin.actualizarPanel();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) { //solo para mostrar y
    }


    public Banner getBanner() {
        return banner;
    }

    public PanelImagenFondo getFondo() {
        return fondo;
    }

}
