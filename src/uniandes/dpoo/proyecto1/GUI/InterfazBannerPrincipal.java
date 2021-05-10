package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.usuario.Admin;
import uniandes.dpoo.proyecto1.modelo.usuario.Coordinador;
import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;
import uniandes.dpoo.proyecto1.modelo.usuario.Usuario;
import uniandes.dpoo.proyecto1.procesamiento.Banner;
import uniandes.dpoo.proyecto1.procesamiento.Prueba;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

import javax.swing.*;

public class InterfazBannerPrincipal extends JFrame implements ActionListener {
	@Serial
    private static final long serialVersionUID = 7466153715672542511L;
	
	private Banner banner;
	private PanelOptions opciones;
	private PanelSuperior superior;
	private PanelImagenFondo fondo;
	private PanelLogin panelLogin;
	private PanelAux vistaAct;
	private PrincipalUsusario principalUsuario;
	private Usuario usuario;


	
	public InterfazBannerPrincipal () {
		setTitle( "Banner" );
        getContentPane( ).setLayout( new BorderLayout( ) );
        setSize( 1000, 680 );
        opciones = new PanelOptions("Guest",this);
        fondo = new PanelImagenFondo();
        superior = new PanelSuperior(this);
        panelLogin = new PanelLogin(this);
        banner = new Banner(new Periodo(2021,10));
        Prueba.cargarPrueba(banner);
        fondo.setLayout(new BorderLayout());
        panelLogin.mostrar(fondo);
        vistaAct = panelLogin;
        add(fondo,BorderLayout.CENTER);
        add(opciones,BorderLayout.WEST);
        add(superior,BorderLayout.NORTH);
	}


    public static void main( String[] args )
    {
        InterfazBannerPrincipal interfaz = new InterfazBannerPrincipal( );
        interfaz.setVisible(true) ;

    }
    public void ocultarYmostrar(PanelAux pa){
        pa.mostrar(fondo);
        vistaAct.ocultar();
        vistaAct = pa;
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
                vistaAct.ocultar();
                setVistaAct(principalUsuario);
                principalUsuario.mostrar(fondo);
            }

        }else {
            System.out.println("error de inicio");
        }
    }

    public PanelOptions getOpciones() {
        return opciones;
    }



    @Override
    public void actionPerformed(ActionEvent e) { //solo para mostrar y
    }


    public PanelAux getVistaAct() {
        return vistaAct;
    }

    public Banner getBanner() {
        return banner;
    }

    public PanelImagenFondo getFondo() {
        return fondo;
    }

}
