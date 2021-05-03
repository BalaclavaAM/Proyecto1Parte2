package uniandes.dpoo.proyecto1.GUI;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelSuperior extends JPanel {

	private static final long serialVersionUID = -6494690918335180464L;
	public final static String RUTA = "./data/imagenes/andes.jpg";
	private JLabel startbutton;
	
	public PanelSuperior ( ) {
		setLayout(new GridLayout(0,3));
		setBackground(Color.decode("0xf1c132"));
		startbutton = new JLabel( );
		startbutton.setIcon(new ImageIcon(RUTA));
		add(startbutton);
	}
	

}
