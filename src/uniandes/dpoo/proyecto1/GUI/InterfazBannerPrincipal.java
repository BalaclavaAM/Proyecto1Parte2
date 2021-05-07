package uniandes.dpoo.proyecto1.GUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;

import javax.swing.*;

public class InterfazBannerPrincipal extends JFrame implements ActionListener {
	@Serial
    private static final long serialVersionUID = 7466153715672542511L;
	
	private JTextField usuario;
	private JPasswordField password;
	private PanelOptions opciones;
	private PanelSuperior superior;
	private PanelImagenFondo fondo;
	private JButton botonLogin;
	private PanelLogin panelLogin;
	private PanelAux vistaAct;

	
	
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

    public PanelOptions getOpciones() {
        return opciones;
    }



    @Override
    public void actionPerformed(ActionEvent e) { //solo para mostrar y
	    vistaAct.ocultar();
    }

	
	

}
