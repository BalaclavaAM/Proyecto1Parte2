package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.EstadoCurso;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class InEdPlan extends PanelAux implements ActionListener {
    private Plan plan;
    private Map<String, Curso> catalogo;
    private Map<String,CursoRegistrado> agregados;
    private Map<String,CursoRegistrado> nuevos;
    private JTextField codigo;
    private JTextField periodo;
    private JPanel data;

    public InEdPlan(InterfazBannerPrincipal principal, Plan plan) {
        super(principal);
        this.plan = plan;
        this.catalogo = new HashMap<>();
        agregados = new HashMap<>(plan.getCursosRegistrados());
        setLayout(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 1; gb.gridheight = 3; gb.weightx = 1; gb.weighty = 3;
        add(new JTextField(plan.getNombre()), gb);
        gb.gridx = 3; gb.gridy = 0; gb.gridwidth = 3; gb.gridheight = 3; gb.weightx = 3; gb.weighty = 3;
        add(new JLabel(),gb);
        gb.gridx = 0; gb.gridy = 3; gb.gridwidth = 1; gb.gridheight = 1; gb.weightx = 1; gb.weighty = 1;gb.fill = 1;
        add(codigo = new JTextField("Codigo"),gb);
        gb.gridx = 1; gb.gridy = 3; gb.gridwidth = 1; gb.gridheight = 1; gb.weightx = 1; gb.weighty = 1;gb.fill = 1;
        add(periodo = new JTextField("Periodo"),gb);
        JButton agregar = new JButton("agregar");
        gb.gridx = 2; gb.gridy = 3; gb.gridwidth = 1; gb.gridheight = 1; gb.weightx = 1; gb.weighty = 1;gb.fill = 1;
        add(agregar,gb);
        actualizarPanel();
        gb.gridx = 0; gb.gridy = 5; gb.gridwidth = 1; gb.gridheight = 5; gb.weightx = 1; gb.weighty = 5; gb.fill = 1;
        add(new JLabel(),gb);
        gb.gridx = 0; gb.gridy = 5; gb.gridwidth = 5; gb.gridheight = 5; gb.weightx = 5; gb.weighty = 5; gb.fill = 1;
        add(new JScrollPane(data),gb);
    }



    @Override
    public void mostrar(PanelImagenFondo fondo) {

    }
    public void actualizarPanel(){
        data = new JPanel(new GridBagLayout());
        GridBagConstraints gb2 = new GridBagConstraints();
        gb2.gridx = 0; gb2.gridy = 0; gb2.gridwidth = 1; gb2.gridheight = 1; gb2.weightx = 1; gb2.weighty = 1;gb2.fill = 1;
        data.add(new JLabel("Codigo"),gb2);
        gb2.gridx = 1; gb2.gridy = 0; gb2.gridwidth = 1; gb2.gridheight = 1; gb2.weightx = 1; gb2.weighty = 1;gb2.fill = 1;
        data.add(new JLabel("Materia"));
        gb2.gridx = 2; gb2.gridy = 0; gb2.gridwidth = 1; gb2.gridheight = 1; gb2.weightx = 1; gb2.weighty = 1;gb2.fill = 1;
        data.add(new JLabel(""));
        int county = 1;
        for(CursoRegistrado cr: agregados.values()){
            gb2.gridx = 0; gb2.gridy = county; gb2.gridwidth = 1; gb2.gridheight = 1; gb2.weightx = 1; gb2.weighty = 1;
            data.add(new JLabel(cr.getCurso().getCodigo()),gb2);
            gb2.gridx = 1; gb2.gridy = county; gb2.gridwidth = 1; gb2.gridheight = 1; gb2.weightx = 1; gb2.weighty = 1;
            data.add(new JLabel(cr.getCurso().getMateria()),gb2);
            gb2.gridx = 2; gb2.gridy = county; gb2.gridwidth = 1; gb2.gridheight = 1; gb2.weightx = 1; gb2.weighty = 1;
            JButton b = new JButton("quitar");
            b.setActionCommand(cr.getCurso().getCodigo());
            b.addActionListener(this);
            data.add(b,gb2);
            county++;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("agregar")){
            String codigo = this.codigo.getText();
            String periodoText = this.periodo.getText();
            Periodo periodo = Periodo.stringToPeriodo(periodoText);
            Curso curso = catalogo.get(codigo);
            if(periodo != null && curso != null){
                agregados.put(codigo,new CursoRegistrado(curso, EstadoCurso.Planeado,false,periodo));
                nuevos.put(codigo,new CursoRegistrado(curso, EstadoCurso.Planeado,false,periodo));
                actualizarPanel();

            }
        }
    }
}
