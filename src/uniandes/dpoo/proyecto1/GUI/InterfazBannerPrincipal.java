package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.modelo.usuario.Usuario;
import uniandes.dpoo.proyecto1.procesamiento.Banner;

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
	private PanelAux principalUsuario;
	private Usuario usuario;

	
	
	public InterfazBannerPrincipal () {
		setTitle( "Banner" );
        getContentPane( ).setLayout( new BorderLayout( ) );
        setSize( 1000, 680 );
        opciones = new PanelOptions("Guest",this);
        fondo = new PanelImagenFondo();
        superior = new PanelSuperior(this);
        panelLogin = new PanelLogin(this);


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

    public void mostrarPrincipal(Usuario user){
        if(user!=null){
            if(user.getPermission().equals("Estudiante")){
                principalUsuario =  new PrincipalEstudiante(this,);
            }
        }else {
            System.out.println("error ");
        }
    }

    public PanelOptions getOpciones() {
        return opciones;
    }



    @Override
    public void actionPerformed(ActionEvent e) { //solo para mostrar y
	    vistaAct.ocultar();
    }

    public Banner getBanner() {
        return banner;
    }

    public PanelImagenFondo getFondo() {
        return fondo;
    }

}
