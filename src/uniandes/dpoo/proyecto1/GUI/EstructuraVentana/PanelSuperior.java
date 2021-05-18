package uniandes.dpoo.proyecto1.GUI.EstructuraVentana;

import uniandes.dpoo.proyecto1.GUI.InterfazBannerPrincipal;

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
		setLayout(new GridBagLayout());
		setBackground(Color.decode("0xf1c132"));
		JButton home = new JButton("Home");
		JButton anterior = new JButton("anterior");
		JButton siguiente = new JButton("siguiente");
		JButton cerrar = new JButton("Cerrar seccion");
		cerrar.setBackground(Color.decode("0xf1c132")); cerrar.addActionListener(e->principal.cerrarSeccion());

		siguiente.setBackground(Color.decode("0xf1c132")); siguiente.addActionListener(e->principal.siguiente());
		anterior.setBackground(Color.decode("0xf1c132")); anterior.addActionListener(e->principal.anterior());
		home.setBackground(Color.decode("0xf1c132"));home.addActionListener(e->principal.home());
		JPanel panelmenu = new JPanel(new GridLayout(1,5)); panelmenu.setBackground(Color.decode("0xf1c132"));
		panelmenu.setOpaque(false);
		JLabel relleno = new JLabel();
		panelmenu.add(home);panelmenu.add(anterior);panelmenu.add(siguiente);panelmenu.add(relleno); panelmenu.add(cerrar);
		logouniandes = new JLabel( );
		logouniandes.setBackground(Color.WHITE);
		logouniandes.setIcon(new ImageIcon(RUTA));
		GridBagConstraints gb = new GridBagConstraints();
		gb.gridx = 0; gb.gridy = 0;  gb.gridwidth = 1; gb.gridheight = 1; gb.weightx = 0; gb.weighty = 0; gb.fill = 1;
		add(logouniandes, gb);
		gb.gridx = 1; gb.gridy = 0;  gb.gridwidth = 6; gb.weightx = 6; gb.weighty = 0; gb.fill = 1;
		add(panelmenu,gb);


	}


	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(3);
	}
}
