package uniandes.dpoo.proyecto1.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PanelSuperior extends JPanel implements ActionListener {

	private static final long serialVersionUID = -6494690918335180464L;
	public final static String RUTA = "./data/imagenes/andes.jpg";
	private JLabel logouniandes;
	
	public PanelSuperior (InterfazBannerPrincipal principal) {
		setSize(principal.getWidth(),50);
		setLayout(new BorderLayout());
		setBackground(Color.decode("0xf1c132"));
		JButton home = new JButton("Home");
		JButton regresar = new JButton();
		home.setBackground(Color.decode("0xf1c132"));
		home.setActionCommand("Home");home.addActionListener(e->principal.home());
		home.addActionListener(principal);
		logouniandes = new JLabel( );
		logouniandes.setBackground(Color.WHITE);
		logouniandes.setIcon(new ImageIcon(RUTA));
		add(logouniandes, BorderLayout.WEST);
		add(home,BorderLayout.EAST);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(3);
	}
}
