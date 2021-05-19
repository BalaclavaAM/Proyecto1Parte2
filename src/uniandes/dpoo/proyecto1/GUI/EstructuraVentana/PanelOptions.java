package uniandes.dpoo.proyecto1.GUI.EstructuraVentana;


import uniandes.dpoo.proyecto1.GUI.InterfazBannerPrincipal;
import uniandes.dpoo.proyecto1.GUI.PrincipalUsurio.PrincipalUsusario;

import java.awt.*;
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
		botoneGeneral();
		for (JButton jb: botones){
			add(jb);
		}
		principal = pPrincipal;
	}
	public void botonesAdmi(PrincipalUsusario pa){

		JButton bCP = new JButton("Cargar Pensum");
		bCP.setActionCommand("PO>CargaPensum");
		botones.add(bCP);
		diseño(pa, 3, botones.size());
	}


	public void botoneGeneral(){
		JButton oCS = new JButton("Oferta Cursos"); oCS.setActionCommand("PO>OCursos");
		botones.add(oCS);
		JButton pGR = new JButton("Programas"); pGR.setActionCommand("PO>Programas");
		botones.add(pGR);
		JButton pCL = new JButton("Calendario"); pCL.setActionCommand("PO>Calendario");
		botones.add(pCL);
		for (int i = 0; i < 3; i++) {
			JButton b = botones.get(i);
			b.setBackground(Color.LIGHT_GRAY);
			b.addActionListener(principal);
		}
		diseño(principal,0,botones.size());
	}

	public void botonesEstudiant(PrincipalUsusario pe){
		JButton bHA= new JButton("Historia Académica"); bHA.setActionCommand("PO>HAcademica");
		botones.add(bHA);

		JButton bIM = new JButton("Inscribir Materias"); bIM.setActionCommand("PO>IMaterias");
		botones.add(bIM);

		JButton bAH = new JButton("Validar Requerimientos"); bAH.setActionCommand("PO>Validar");
		botones.add(bAH);

		JButton pSM = new JButton("Planeacion"); pSM.setActionCommand("PO>Planear");
		botones.add(pSM);
		diseño(pe,3,botones.size());

	}


	public void diseño(ActionListener al, int s, int f){
		for (int i = s; i < f; i++) {
			JButton b = botones.get(i);
			b.setBackground(Color.LIGHT_GRAY);
			b.addActionListener(al);
			add(b);
		}
	}

	public void quitarBotones(){
		int size = botones.size();
		for (int i = size - 1; i > 2; i--) {
			JButton b = botones.get(i);
			remove(b);
			botones.remove(i);
		}
	}

/*
	public void setPermissions(String permission){
		for (JButton jButton : botones) {
			String permiso = jButton.getActionCommand();
			switch (permiso) {
				case "PO>CargaPensum":
					jButton.setEnabled(!permission.equals("Guest"));
					break;
				case "PO>HAcademica":

				case "PO>PSemestre":

				case "PO>IMaterias":
					jButton.setEnabled(permission.equals("Estudiante"));
					break;
				case "PO>AHistoria":
					jButton.setEnabled(permission.equals("Guest"));
					break;

				case "PO>OCursos":

				case "PO>Programas":
					jButton.setEnabled(true);
					break;

				case "PO>Calendario":
					jButton.setEnabled(true);
					break;
				default:
					throw new IllegalArgumentException("Unexpected value: " + permiso);
			}
		}
	}
*/

	@Override
	public void actionPerformed(ActionEvent e) {

	}
}
