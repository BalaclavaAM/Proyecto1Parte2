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
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class AgregarAux extends JDialog{
    private Banner banner;
    private JPanel exter;
    private Map<String,CursoRegistrado> cursosAgregar;
    private JTable tablaMalla;
    private Vector<Vector> data;
    private JTextField Jcodigo;
    private JTextField cursoN;
    private JButton buscar;
    private JTextField Janio;
    private JTextField Jnota;
    private JComboBox<Integer> semestre;
    private JComboBox<Integer> ciclo;
    private Checkbox epsilon;
    private JButton agregar;
    private Curso curso;
    private CursoRegistrado cursoR;
    private boolean nota;

    public AgregarAux(JPanel exter, Banner banner, ArrayList<ArrayList<CursoRegistrado>> posicionCursos,
                      ArrayList<ArrayList<String>> posicionEstado,
                      Map<String, CursoRegistrado> cursosAgregar, JTable tablaMalla, boolean nota){

        setDefaultCloseOperation(1);
        this.nota = nota;
        this.exter = exter;
        this.tablaMalla = tablaMalla;
        this.banner = banner;
        this.cursosAgregar = cursosAgregar;
        setSize(600,100);
        setLayout(new BorderLayout());
        JLabel codlab = new JLabel("Codigo:");
        Jcodigo = new JTextField();
        buscar = new JButton("Buscar"); buscar.addActionListener((event)->{
            curso = this.banner.getCatalogo().get(Jcodigo.getText());
            if(curso != null){                agregar.setEnabled(true);
                cursoN.setText(curso.getNombre());
                Janio.setEnabled(true);
                semestre.setEnabled(true);
                if(nota){ Jnota.setEnabled(true); }
            }});
        agregar = new JButton("agregar");agregar.addActionListener((event)->{
            agregar(posicionEstado,posicionCursos);
            actualizarParcialPanel();

        });
        ciclo = new JComboBox<>(new Integer[]{1,2});
        cursoN = new JTextField();
        Janio = new JTextField("año");
        semestre = new JComboBox<>(new Integer[]{10,19,20});semestre.addItemListener((event)->{
            ciclo.setEnabled((int) semestre.getSelectedItem() != 19 && !curso.isCompleto());});
        epsilon = new Checkbox("epsilon.");
        if(nota){ Jnota = new JTextField(); Jnota.setText("0.0");}
        GridBagConstraints gb = new GridBagConstraints();
        JPanel superior = new JPanel(new GridBagLayout());
        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 4; gb.gridheight =1;gb.weightx = 4; gb.fill = 1;
        superior.add(codlab,gb);
        gb.gridx = 4; gb.gridwidth = 5;gb.weightx = 5;
        superior.add(Jcodigo,gb);
        gb.gridx = 9; gb.gridwidth = 3;gb.weightx = 3;
        superior.add(buscar,gb);
        gb.gridx = 12; gb.gridwidth = 2; gb.weightx = 1;
        superior.add(new JLabel(),gb);
        JPanel inferior = new JPanel(new GridBagLayout());
        gb = new GridBagConstraints();
        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 3; gb.weightx = 3;gb.fill = 1;
        inferior.add(cursoN,gb);
        gb.gridx = 3; gb.gridwidth = 2;gb.weightx = 2;
        inferior.add(Janio,gb);
        gb.gridx += gb.weightx;
        inferior.add(semestre,gb);
        gb.gridx += gb.weightx;
        inferior.add(ciclo,gb);
        gb.gridx += gb.weightx;
        inferior.add(epsilon,gb);
        if(nota){
            gb.gridx += gb.weightx;
            inferior.add(Jnota,gb);
        }
        gb.gridx += gb.weightx;
        inferior.add(agregar,gb);
        add(superior, BorderLayout.NORTH);
        add(inferior,BorderLayout.SOUTH);
        actualizarParcialPanel();
    }

    void actualizarParcialPanel(){
        Jcodigo.setText("");
        cursoN.setText("Curso a agregar");
        Janio.setEnabled(false);
        semestre.setEnabled(false);
        ciclo.setEnabled(false);
        agregar.setEnabled(false);
        if(nota){
            Jnota.setEnabled(false);
        }
    }

    void agregar(ArrayList<ArrayList<String>> posicionEstado, ArrayList<ArrayList<CursoRegistrado>> posicionCursos){
        Periodo p;
        if(!cursosAgregar.containsKey(curso.getCodigo())) {
            try {
                int anio = Integer.parseInt(Janio.getText());
                if (ciclo.isEnabled()) {
                    p = new Periodo(anio, (Integer) semestre.getSelectedItem(), (Integer) ciclo.getSelectedItem());
                } else {
                    p = new Periodo(anio, (Integer) semestre.getSelectedItem());
                }
                if(nota){
                    Nota notaR;
                    String notaIn = Jnota.getText();
                    if(notaIn.equals("A") || notaIn.equals("R")){
                        calCual calC = calCual.valueOf(notaIn);
                        notaR = new NotaCual(calC);
                    }else{
                        float calN = Float.parseFloat(notaIn);
                        notaR = new NotaNum(calN);
                    }
                    cursoR = new CursoRegistrado(curso,notaR, epsilon.getState(), p);
                }else{
                    cursoR = new CursoRegistrado(curso, EstadoCurso.Planeado, epsilon.getState(), p);
                }

                var ref = new Object() {
                    DefaultTableModel tableModel = (DefaultTableModel) tablaMalla.getModel();
                };
                data = ref.tableModel.getDataVector();
                String semestre = p.periodoS();
                int col = 0;
                int fila;
                int filas = tablaMalla.getRowCount();
                int np = tablaMalla.getColumnCount();
                Vector<String> columnNames = new Vector<>(np);
                while (col < np) {
                    String header = tablaMalla.getColumnName(col);
                    columnNames.add(header);
                    if (header.equals(semestre)) {
                        break;
                    }
                    col++;
                }
                if (col == np) {
                    columnNames.add(semestre);
                    columnNames.sort(String::compareTo);
                    col = columnNames.indexOf(semestre);
                    for (int i = 0; i < filas; i++) {
                        data.get(i).add(col,null);
                        posicionCursos.get(i).add(col,null);
                        posicionEstado.get(i).add(col,null);
                    }

                    data.get(0).setElementAt(curso.getCodigo(), col);
                    posicionCursos.get(0).set(col,cursoR);
                    posicionEstado.get(0).set(col,"agregar");

                } else {
                    int ultimoOcupado = filas - 1;
                    while (ultimoOcupado > -1) {
                        if (data.get(ultimoOcupado).get(col) != null) {
                            break;
                        }
                        ultimoOcupado--;
                    }
                    fila = ultimoOcupado + 1;
                    if (fila == filas) {
                        data.add(AuxCambios.vectorNull(np));
                        posicionCursos.add(AuxCambios.arrayNull(np));
                        posicionEstado.add(AuxCambios.arrayNull(np));
                    }
                    data.get(fila).setElementAt(curso.getCodigo(), col);
                    posicionCursos.get(fila).set(col,cursoR);
                    posicionEstado.get(fila).set(col,"agregar");
                }

                ref.tableModel = new DefaultTableModel(data, columnNames) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                tablaMalla.setModel(ref.tableModel);
                cursosAgregar.put(curso.getCodigo(), cursoR);
            } catch (NumberFormatException numberFormatException) {
                numberFormatException.printStackTrace();
            }


            SwingUtilities.updateComponentTreeUI(exter);
        }else{
            System.out.println("Curso ya planeado");
        }
    }
}
