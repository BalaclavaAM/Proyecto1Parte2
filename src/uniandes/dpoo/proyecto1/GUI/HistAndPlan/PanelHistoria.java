package uniandes.dpoo.proyecto1.GUI.HistAndPlan;

import uniandes.dpoo.proyecto1.GUI.InterfazBannerPrincipal;
import uniandes.dpoo.proyecto1.GUI.PanelAux;
import uniandes.dpoo.proyecto1.GUI.Utilidades;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PanelHistoria extends PanelAux {
    public String RUTA = "./data/imagenes/usuarioGenerico.png";
    private final JLabel creditosIIn;
    private final JLabel creditosAIn;
    private final JLabel promedioIn;
    private final JPanel imagen;
    private TablaHorMod tablaHistoria;
    private JScrollPane scrollTabla;
    private final JButton Bvalidar;
    private final JPanel panelBotones;
    private final JPanel panelDatos;
    private final JPanel panelInfo;
    private final HistoriaAcademica historia;


    public PanelHistoria(InterfazBannerPrincipal principal, HistoriaAcademica historiaAcademica, String codigo,
                         String name) {
        super(principal);
        setLayout(new GridBagLayout());
        Utilidades.agregarBorder(this,0.05,0.05,0.03,0.03);
        JTextField nombreJT = new JTextField(name);
        nombreJT.setEditable(false);
        JTextField codigoJT = new JTextField(codigo);
        codigoJT.setEditable(false);
        nombreJT.setEditable(false);
        nombreJT.setHorizontalAlignment(0);
        historia = historiaAcademica;
        this.imagen = imagen();

        JButton mostrarIn = new JButton("Mostra Info");
        mostrarIn.addActionListener(e -> tablaHistoria.mostrarInfo(true));
        JButton agregar = new JButton("Agregar Curso");
        agregar.addActionListener(e -> {
            AgregarAux au = new AgregarAux(tablaHistoria,principal.getBanner(),true);
            au.setVisible(true);
        });
        JButton quitar = new JButton("Quitar");
        quitar.addActionListener(e -> tablaHistoria.quitar());
        JButton Bnota = new JButton("Actualizar Nota");
        Bnota.addActionListener(e -> {
            CursoRegistrado cursoR = tablaHistoria.getSelecteCursoR();
            if(cursoR != null) {
                cambiatNotaAux cna = new cambiatNotaAux(this,cursoR, historia);
                cna.setVisible(true);
                reiniciarPanel();
            }
        });
        JButton deshacer = new JButton("Deshacer accion");
        deshacer.addActionListener(e ->tablaHistoria.deshacer());

        JLabel semestreIn = new JLabel();
        creditosIIn = new JLabel();
        creditosAIn = new JLabel();
        promedioIn = new JLabel();

        panelInfo = new JPanel(new GridLayout(1,8));
        panelInfo.add(new JLabel("Semestre: "));
        panelInfo.add(semestreIn);
        panelInfo.add(new JLabel("Creditos"));
        panelInfo.add(creditosIIn);
        panelInfo.add(new JLabel("Creditos Aproavdos"));
        panelInfo.add(creditosAIn);
        panelInfo.add(new JLabel("Promedio"));
        panelInfo.add(promedioIn);

        panelBotones = new JPanel(new GridLayout(1, 4));
        panelBotones.add(mostrarIn);
        panelBotones.add(agregar);
        panelBotones.add(quitar);
        panelBotones.add(deshacer);
        panelBotones.add(Bnota);
        JTextField Jnombre = new JTextField("Nombre: " + name); Jnombre.setEditable(false);
        JTextField Jcodigo = new JTextField("Codigo: " + codigo); Jcodigo.setEditable(false);
        JTextField pi = new JTextField("Periodo Ingreso: " + historiaAcademica.getPrimerPeriodo()); pi.setEditable(false);
        panelDatos = Utilidades.centrarV(new JComponent[]{Jnombre,Jcodigo,pi}, 2,1,false,false);

        Bvalidar = new JButton("Validar");
        Bvalidar.addActionListener(e -> {
            historia.actulizarMalla(tablaHistoria.getCursosAgregar(),tablaHistoria.getCursosQuitar());
            reiniciarPanel();
        });
        aniadirElementos();
    }

    private JPanel imagen(){
        JPanel it = new JPanel() {
            @Override
            public void paint(Graphics g) {
                Image image = null;
                super.paint(g);
                try {
                    image = ImageIO.read(new File(RUTA));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (image != null) {
                    int d = (int) (0.8 * Math.min(getWidth(), getHeight()));
                    g.drawImage(image, (getWidth() - d), 0, d, d, this);
                }

            }
        };
        it.setOpaque(false);
        return it;
    }

    public void aniadirElementos() {
        Utilidades.agregarBorder(panelDatos,0,0.1,0,0);
        JPanel Jvalidar = Utilidades.centrarV(new JComponent[]{Bvalidar},1,0,true,false);
        Utilidades.agregarBorder(Jvalidar,0.25,0.25,0.1,0.1);
        Utilidades.agregarBorder(panelBotones,0,0.2,0,0);
        panelBotones.setOpaque(false);
        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 2; gb.gridheight = 3; gb.weightx = 2; gb.weighty = 3;gb.fill= 1;
        add(panelDatos,gb);
        gb.gridx = 2; gb.gridwidth = 4; gb.weightx = 4;
        add(imagen,gb);
        gb.gridx = 0; gb.gridy = 14; gb.gridwidth = 6; gb.gridheight = 1; gb.weightx = 6; gb.weighty = 1;
        add(panelBotones,gb);
        gb.gridy = 17;
        add(panelInfo,gb);
        gb.gridy = 15; gb.gridwidth = 6; gb.gridheight = 2; gb.weightx = 6; gb.weighty = 3;
        add(Jvalidar,gb);
        nuevaTabla(gb);
        actualizarInfo();
    }
    private void nuevaTabla(GridBagConstraints gb) {
        tablaHistoria = new TablaHorMod(historia.getInfoSemestres(),true);
        scrollTabla = new JScrollPane(tablaHistoria);
        gb.gridx = 0; gb.gridy = 3; gb.gridwidth = 6; gb.gridheight = 11; gb.weightx = 6; gb.weighty = 11; gb.fill = 1;
        add(scrollTabla, gb);
    }


    public void reiniciarPanel(){
        GridBagConstraints gb = new GridBagConstraints();
        try {
            remove(scrollTabla);
            nuevaTabla(gb);
            actualizarInfo();
            SwingUtilities.updateComponentTreeUI(this);
        }catch(Exception ignored) { }
    }


    public void actualizarPanel() {
        //no hacer nada
    }

    public void actualizarInfo(){
        promedioIn.setText(String.valueOf(historia.calcularPromedioAcademico()));
        creditosAIn.setText(String.valueOf(historia.getCreditosAprovados()));
        creditosIIn.setText(String.valueOf(historia.getCreditos()));
    }

}
