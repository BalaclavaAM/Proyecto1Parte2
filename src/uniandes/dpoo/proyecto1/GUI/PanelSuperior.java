package uniandes.dpoo.proyecto1.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class PanelSuperior extends JPanel implements ActionListener {

	private static final long serialVersionUID = -6494690918335180464L;
	public final static String RUTA = "./data/imagenes/andes.jpg";
	private JLabel startbutton;
	
	public PanelSuperior (InterfazBannerPrincipal principal) {
		setSize(principal.getWidth(),50);
		setLayout(new BorderLayout());
		setBackground(Color.decode("0xf1c132"));
		JButton op = new JButton("Home");
		op.setBackground(Color.decode("0xf1c132"));
		op.setActionCommand("Home");
		op.addActionListener(principal);
		startbutton = new JLabel( );
		startbutton.setIcon(new ImageIcon( new ImageIcon(RUTA).getImage().getScaledInstance(getWidth()/3,(int)(getHeight()), Image.SCALE_DEFAULT)));
		add(startbutton, BorderLayout.WEST);
		add(op,BorderLayout.EAST);
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(3);
	}
}
