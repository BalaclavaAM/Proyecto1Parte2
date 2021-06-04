package uniandes.dpoo.proyecto1.GUI.HistAndPlan;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Nota.NotaCual;
import uniandes.dpoo.proyecto1.modelo.Nota.NotaNum;
import uniandes.dpoo.proyecto1.modelo.Nota.calCual;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.EstadoCurso;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.procesamiento.Banner;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class AgregarAux extends JDialog {
    private Banner banner;
    private Map<String, CursoRegistrado> cursosAgregar;
    private JTextField Jcodigo;
    private JTextField cursoN;
    private JTextField Janio;
    private JTextField Jnota;
    private JComboBox<Integer> semestre;
    private JComboBox<Integer> ciclo;
    private Checkbox epsilon;
    private JButton agregar;
    private Curso curso;
    private boolean nota;
    private TablaHorMod table;


    public AgregarAux(TablaHorMod table, Banner banner, boolean nota) {

        setDefaultCloseOperation(1);
        this.table = table;
        this.nota = nota;
        this.banner = banner;
        setSize(600, 100);
        setLayout(new BorderLayout());
        JLabel codlab = new JLabel("Codigo:");
        Jcodigo = new JTextField();
        JButton buscar = new JButton("Buscar");
        buscar.addActionListener((event) -> {
            curso = banner.getCatalogo().get(Jcodigo.getText());
            if (curso != null) {
                agregar.setEnabled(true);
                cursoN.setText(curso.getNombre());
                Janio.setEnabled(true);
                semestre.setEnabled(true);
                if (nota) {
                    Jnota.setEnabled(true);
                }
            }
        });
        agregar = new JButton("agregar");
        agregar.addActionListener((event) -> {
            agregar();
            actualizarParcialPanel();

        });
        ciclo = new JComboBox<>(new Integer[]{1, 2});
        cursoN = new JTextField();
        Janio = new JTextField("año");
        semestre = new JComboBox<>(new Integer[]{10, 19, 20});
        semestre.addItemListener((event) -> {
            ciclo.setEnabled((int) semestre.getSelectedItem() != 19);
        });
        epsilon = new Checkbox("epsilon.");
        if (nota) {
            Jnota = new JTextField();
            Jnota.setText("0.0");
        }
        GridBagConstraints gb = new GridBagConstraints();
        JPanel superior = new JPanel(new GridBagLayout());
        gb.gridx = 0;
        gb.gridy = 0;
        gb.gridwidth = 4;
        gb.gridheight = 1;
        gb.weightx = 4;
        gb.fill = 1;
        superior.add(codlab, gb);
        gb.gridx = 4;
        gb.gridwidth = 5;
        gb.weightx = 5;
        superior.add(Jcodigo, gb);
        gb.gridx = 9;
        gb.gridwidth = 3;
        gb.weightx = 3;
        superior.add(buscar, gb);
        gb.gridx = 12;
        gb.gridwidth = 2;
        gb.weightx = 1;
        superior.add(new JLabel(), gb);
        JPanel inferior = new JPanel(new GridBagLayout());
        gb = new GridBagConstraints();
        gb.gridx = 0;
        gb.gridy = 0;
        gb.gridwidth = 3;
        gb.weightx = 3;
        gb.fill = 1;
        inferior.add(cursoN, gb);
        gb.gridx = 3;
        gb.gridwidth = 2;
        gb.weightx = 2;
        inferior.add(Janio, gb);
        gb.gridx += gb.weightx;
        inferior.add(semestre, gb);
        gb.gridx += gb.weightx;
        inferior.add(ciclo, gb);
        gb.gridx += gb.weightx;
        inferior.add(epsilon, gb);
        if (nota) {
            gb.gridx += gb.weightx;
            inferior.add(Jnota, gb);
        }
        gb.gridx += gb.weightx;
        inferior.add(agregar, gb);
        add(superior, BorderLayout.NORTH);
        add(inferior, BorderLayout.SOUTH);
        actualizarParcialPanel();
    }

    void actualizarParcialPanel() {
        Jcodigo.setText("");
        cursoN.setText("Curso a agregar");
        Janio.setEnabled(false);
        semestre.setEnabled(false);
        ciclo.setEnabled(false);
        agregar.setEnabled(false);
        if (nota) {
            Jnota.setEnabled(false);
        }
    }

    void agregar() {
        Periodo p;

        try {
            int anio = Integer.parseInt(Janio.getText());
            if (ciclo.isEnabled()) {
                p = new Periodo(anio, (Integer) semestre.getSelectedItem(), (Integer) ciclo.getSelectedItem());
            } else {
                p = new Periodo(anio, (Integer) semestre.getSelectedItem());
            }
            CursoRegistrado cursoR;
            if (nota) {
                Nota notaR;
                String notaIn = Jnota.getText();
                if (notaIn.equals("A") || notaIn.equals("R")) {
                    calCual calC = calCual.valueOf(notaIn);
                    notaR = new NotaCual(calC);
                } else {
                    float calN = Float.parseFloat(notaIn);
                    notaR = new NotaNum(calN);
                }
                cursoR = new CursoRegistrado(curso, notaR, epsilon.getState(), p);
            } else {
                cursoR = new CursoRegistrado(curso, EstadoCurso.Planeado, epsilon.getState(), p);
            }
            table.agregar(cursoR);

        } catch (NumberFormatException numberFormatException) {
            numberFormatException.printStackTrace();
        }

    }

}
