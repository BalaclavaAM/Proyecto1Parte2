package uniandes.dpoo.proyecto1.GUI;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PanelOptions extends JPanel implements ActionListener {

	private static final long serialVersionUID = 5506885411509118415L;
	
	private InterfazBannerPrincipal principal;
	private ArrayList<JButton> botones;
	
	
	public PanelOptions ( String rol, InterfazBannerPrincipal pPrincipal ) {
		setLayout(new GridLayout(10,0));
		this.setBackground(Color.GRAY);
		botones = new ArrayList<>();
		
		JButton bCP = new JButton("Cargar Pensum");
		bCP.setActionCommand("PO>CargaPensum");
		botones.add(bCP);
		
		JButton bHA= new JButton("Historia Académica");
		bHA.setActionCommand("PO>HAcademica");
		botones.add(bHA);
		
		JButton bIM = new JButton("Inscribir Materias");
		bIM.setActionCommand("PO>IMaterias");
		botones.add(bIM);
		
		JButton bAH = new JButton("Actualizar Historia");
		bAH.setActionCommand("PO>AHistoria");
		botones.add(bAH);
		
		JButton pSM = new JButton("Planear Semestres");
		pSM.setActionCommand("PO>PSemestre");
		botones.add(pSM);
		
		JButton oCS = new JButton("Oferta Cursos");
		oCS.setActionCommand("PO>OCursos");
		botones.add(oCS);
		
		JButton pGR = new JButton("Programas");
		pGR.setActionCommand("PO>Programas");
		botones.add(pGR);
		
		JButton pCL = new JButton("Calendario");
		pCL.setActionCommand("PO>Calendario");
		botones.add(pCL);
		
		
		for (JButton jButton : botones) {
			jButton.setBackground(Color.LIGHT_GRAY);
			add(jButton);
		}
		setPermissions(rol);
		principal = pPrincipal;
	}

	
	public void setPermissions(String permission){
		for (JButton jButton : botones) {
			String permiso = jButton.getActionCommand();
			switch (permiso) {
				case "PO>CargaPensum", "PO>IMaterias", "PO>HAcademica", "PO>PSemestre", "PO>AHistoria" -> jButton.setEnabled(!permission.equals("Guest"));
				case "PO>OCursos", "PO>Programas", "PO>Calendario" -> jButton.setEnabled(true);
				default -> throw new IllegalArgumentException("Unexpected value: " + permiso);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String comando = e.getActionCommand();
		if (comando.equals("PO>CargaPensum")) {
			
		}
		
	}
}
