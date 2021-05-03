package uniandes.dpoo.proyecto1.GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class InterfazBannerPrincipal extends JFrame implements ActionListener {
	private static final long serialVersionUID = 7466153715672542511L;
	
	private JTextField usuario;
	private JPasswordField password;
	private PanelOptions opciones;
	private PanelSuperior superior;
	private PanelImagenFondo fondo;
	private JButton botonLogin;
	private JPanel panelLogin;
	
	
	public InterfazBannerPrincipal () {
		setTitle( "Banner" );
        getContentPane( ).setLayout( new BorderLayout( ) );
        setSize( 1000, 680 );
        setResizable( false );
        
        usuario = new JTextField( );
        password = new JPasswordField( "Contraseña" );
        opciones = new PanelOptions("Guest",this);
        botonLogin = new JButton("Login");
        
        usuario.setText("Usuario");
        
        superior = new PanelSuperior();
        fondo = new PanelImagenFondo();
        
        fondo.setLayout(new BorderLayout());
        
        panelLogin = new JPanel();
        panelLogin.setLayout(new GridLayout(3,1));
        
        panelLogin.add(usuario);
        panelLogin.add(password);
        panelLogin.add(botonLogin);
        
        fondo.add(panelLogin,BorderLayout.SOUTH);
        
        add(fondo,BorderLayout.CENTER);
        
        add(opciones,BorderLayout.WEST);
        add(superior,BorderLayout.NORTH);
        
        botonLogin.addActionListener(this);
        botonLogin.setActionCommand("main>LoginTry");
        
	}
	
	
    public static void main( String[] args )
    {

        InterfazBannerPrincipal interfaz = new InterfazBannerPrincipal( );
        interfaz.setVisible(true) ;
       
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if (comando=="main>LoginTry") {
			opciones.setPermissions("Momero");
			panelLogin.setVisible(false);
		}
		
	}

	
	

}
