package uniandes.dpoo.proyecto1.GUI.HistAndPlan;

import uniandes.dpoo.proyecto1.GUI.InterfazBannerPrincipal;
import uniandes.dpoo.proyecto1.GUI.PanelAux;
import uniandes.dpoo.proyecto1.GUI.Utilidades;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class PanelPlan extends PanelAux {
    private final JTextField Jnombre;
    private final JButton Jvalidar;
    private TablaHorMod tablaPlan;
    private JScrollPane scrollTabla;
    private final JPanel panelBotones;
    private final Map<String, ArrayList<CursoRegistrado>> infoSemestres;



    public PanelPlan(InterfazBannerPrincipal principal, Plan plan) {
        super(principal);
        setLayout(new GridBagLayout());
        Utilidades.agregarBorder(this,0.05,0.05,0.05,0.05);
        Jnombre = new JTextField(plan.getNombre());
        Jnombre.setEditable(false);
        Jnombre.setHorizontalAlignment(0);
        panelBotones = new JPanel(new GridLayout(1,4));
        infoSemestres = plan.getInfoSemestres();

        JButton mostrarIn = new JButton("Mostra Info"); mostrarIn.addActionListener(e-> tablaPlan.mostrarInfo(false));
        JButton agregar = new JButton("Agregar Curso"); agregar.addActionListener(e->{
                AgregarAux au = new AgregarAux(tablaPlan,principal.getBanner(),false);
                au.setVisible(true);
            });
        JButton quitar = new JButton("Quitar Seleccionado"); quitar.addActionListener(e -> tablaPlan.quitar());
        JButton deshacer = new JButton("Deshacer accion");deshacer.addActionListener(e -> tablaPlan.deshacer());
        panelBotones.add(mostrarIn);panelBotones.add(agregar);panelBotones.add(quitar);panelBotones.add(deshacer);
        Jvalidar = new JButton("Validar");Jvalidar.addActionListener(e-> {
                    plan.actulizarMalla(tablaPlan.getCursosAgregar(),tablaPlan.getCursosQuitar());
                    reiniciarPanel(); });
        aniadirElementos();
    }



    private void aniadirElementos(){
        GridBagConstraints gb = new GridBagConstraints();
        JPanel conNombre = Utilidades.centrarV(new JComponent[]{Jnombre},1,1, false,false);
        JPanel conValidar = Utilidades.centrarV(new JComponent[]{Jvalidar},1,0, true,false);
        Utilidades.agregarBorder(conValidar,0.25,0,0.1,0.1);
        gb.gridy = 0; gb.gridx = 1; gb.gridwidth = 4; gb.gridheight = 3;  gb.weightx = 4; gb.weighty = 3;gb.fill = 1;
        add(conNombre,gb);
        Utilidades.agregarBorder(conNombre,0.1,0.1,0.2,0.2);
        gb.gridy = 11; gb.gridheight = 2; gb.weighty = 2;
        add(conValidar,gb);
        gb.gridx = 1; gb.gridy=10; gb.gridwidth = 4; gb.gridheight = 1;  gb.weightx = 4; gb.weighty = 0;
        add(panelBotones,gb);
        nuevaTabla(gb);
    }

    private void nuevaTabla(GridBagConstraints gb) {
        tablaPlan = new TablaHorMod(infoSemestres,false);
        scrollTabla = new JScrollPane(tablaPlan);
        gb.gridx = 1; gb.gridy = 3; gb.gridwidth = 4; gb.gridheight = 7; gb.weightx = 4; gb.weighty = 7; gb.fill = 1;
        add(scrollTabla, gb);
    }

    public void actualizarPanel(){
        //no hacer nada
    }

    public void reiniciarPanel(){
        GridBagConstraints gb = new GridBagConstraints();
        try {
            remove(scrollTabla);
            nuevaTabla(gb);
            SwingUtilities.updateComponentTreeUI(this);
        }catch(Exception ignored) { }
    }
}
