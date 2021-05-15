package uniandes.dpoo.proyecto1.GUI;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.EstadoCurso;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class PanelPlan extends PanelAux {
    private Plan plan;
    private JTextField nombre;
    private JTable tablaPlan;
    private JScrollPane scrollTabla;
    private JButton validar;
    private JPanel panelBotones;
    private DefaultTableModel tableModel;
    private Map<String, CursoRegistrado> celdasCursos;
    private Map<String,String> celdasQuitadas;
    private Map<String, CursoRegistrado> cursosAgregar;
    private Map<String, CursoRegistrado> cursosQuitar;



    public PanelPlan(InterfazBannerPrincipal principal, Plan plan) {
        super(principal);
        this.plan = plan;
        setLayout(new GridBagLayout());
        nombre = new JTextField(plan.getNombre());
        nombre.setEditable(false);
        nombre.setHorizontalAlignment(0);
        tablaPlan = new JTable();
        panelBotones = new JPanel(new GridLayout(1,4));
        cursosAgregar = new HashMap<>();
        cursosQuitar = new HashMap<>();
        celdasQuitadas = new HashMap<>();
        JButton mostrarIn = new JButton("Mostra Info"); mostrarIn.addActionListener(e->{

        });
        JButton agregar = new JButton("Agregar Curso"); agregar.addActionListener(e->{
            AgregarAux au = new AgregarAux(this);
            au.setVisible(true);


        });
        JButton quitar = new JButton("Quitar Seleccionado"); quitar.addActionListener(e ->{
            try {
                System.out.println("dd");
                int fila = tablaPlan.getSelectedRow();
                int col = tablaPlan.getSelectedColumn();
                String periodoS = tablaPlan.getColumnName(tablaPlan.getSelectedColumn());
                String cursoN = (String) tablaPlan.getValueAt(fila, col);
                CursoRegistrado cr = plan.getInfoPeriodos().get(periodoS).get(cursoN);
                celdasQuitadas.put(fila + "-" + col, "");
                cursosQuitar.put(cr.getCurso().getCodigo(),cr);
                SwingUtilities.updateComponentTreeUI(this);
            }catch(Exception ignored){ }
        });
        JButton mostrarC = new JButton("Mostrar Cambios"); mostrarC.addActionListener(e->{

        });
        panelBotones.add(mostrarIn);panelBotones.add(agregar);panelBotones.add(quitar);panelBotones.add(mostrarC);
        validar = new JButton("Validar");validar.addActionListener(e->{

        });
        añadirElementos();
    }

    public void añadirElementos(){
        GridBagConstraints gb = new GridBagConstraints();
        JPanel conNombre = centrar(nombre,1,1,2,2);
        JPanel conValidar = centrar(validar,1,1,2,1);
        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 1; gb.gridheight = 11; gb.weightx = 1; gb.weighty = 11;gb.fill = 1;
        add(new JLabel(""),gb);
        gb.gridx = 5;
        add(new JLabel(""),gb);
        gb.gridx = 1; gb.gridwidth = 4; gb.gridheight = 3;  gb.weightx = 4; gb.weighty = 3;
        add(conNombre,gb);
        gb.gridy = 8;
        add(conValidar,gb);
        gb.gridx = 1; gb.gridy=7; gb.gridwidth = 4; gb.gridheight = 1;  gb.weightx = 4; gb.weighty = 0;
        add(panelBotones,gb);
        actualizarPanel();
    }

    public void actualizarPanel(){
        tablaPlan = new JTable();
        scrollTabla = new JScrollPane(tablaPlan);
        try {
            remove(scrollTabla);
        }catch (Exception ignored){

        }
        Map<String, Map<String, CursoRegistrado>> infoPeriodos = plan.getInfoPeriodos();
        ArrayList<String> semestres = new ArrayList<>(infoPeriodos.keySet());
        semestres.sort(String::compareTo);

        tableModel = new DefaultTableModel(crearData(infoPeriodos,semestres), semestres.toArray()) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaPlan.setModel(tableModel);
        tablaPlan.setCellSelectionEnabled(true);
        tablaPlan.setDefaultRenderer (Object.class, new MiRender());
        scrollTabla = new JScrollPane(tablaPlan);
        GridBagConstraints gb = new GridBagConstraints();
        gb.gridx = 1; gb.gridy = 3; gb.gridwidth = 4; gb.gridheight = 4; gb.weightx = 4; gb.weighty = 4;gb.fill = 1;
        add(scrollTabla,gb);
        SwingUtilities.updateComponentTreeUI(this);
    }

    private String[][] crearData(Map<String, Map<String, CursoRegistrado>> infoPeriodos,ArrayList<String> semestres){
        int np = plan.getInfoPeriodos().size();
        Map<Integer,String[]> dataMap = new Hashtable<>();
        int i = 0;

        for(String semestre: semestres){
            int j= 0;
            Map<String, CursoRegistrado> infoPeriodo = infoPeriodos.get(semestre);
            for(CursoRegistrado cr: infoPeriodo.values()){
                dataMap.putIfAbsent(j,new String[np]);
                String[] linea =dataMap.get(j);
                linea[i] = cr.getCurso().getCodigo();
                j++;
            }
            i++;
        }
        String[][] data = new String[dataMap.size()][np];
        for (int j = 0; j < dataMap.size(); j++) {
            data[j] = dataMap.get(j);
        }
        return data;
    }

    private JPanel centrar(JComponent comp, int margx, int margy, int ancho, int alto){
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gb = new GridBagConstraints();
        panel.setOpaque(false);
        gb.gridx = 0; gb.gridy = 0; gb.gridwidth = margx; gb.gridheight = 2*margy + alto; gb.weightx = margx; gb.weighty = 2*margy + alto;
        panel.add(new JLabel(),gb);
        gb.gridx = margx + ancho;
        panel.add(new JLabel(),gb);
        gb.gridx = margx; gb.gridwidth = ancho; gb.gridheight = margy;gb.weightx = ancho; gb.weighty = margy;
        panel.add(new JLabel(),gb);
        gb.gridy = margy + alto;
        panel.add(new JLabel(),gb);
        gb.gridy = margy; gb.gridwidth = ancho; gb.gridheight = alto;gb.weightx = ancho; gb.weighty = alto; gb.fill = 1;
        panel.add(comp, gb);
        return panel;
    }

    class CambiosAux extends JDialog{

    }
    class MiRender extends DefaultTableCellRenderer
    {
        private int Row,Columns;
        public void setRow(int Row){
            this.Row=Row;
        }
        public void setColumns(int Columns){
            this.Columns=Columns;
        }

        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column)
        {
            super.getTableCellRendererComponent (table, value, isSelected, hasFocus, row, column);
            if(isSelected){
                this.setOpaque(true);
                this.setBackground(Color.RED);
                this.setForeground(Color.YELLOW);
            }

            if (celdasQuitadas.containsKey(row+"-"+column))
            {
                this.setOpaque(true);
                this.setBackground(Color.RED);
                this.setForeground(Color.YELLOW);
            }else{
                this.setOpaque(true);
                this.setBackground(Color.WHITE);
                this.setForeground(Color.BLACK);
            }
            super.getTableCellRendererComponent (table, value,isSelected,hasFocus,row, column);
            return this;
        }
    }

    class AgregarAux extends JDialog{
        private JTextField Jcodigo;
        private JTextField cursoN;
        private JButton buscar;
        private JTextField Janio;
        private JComboBox<Integer> semestre;
        private JComboBox<Integer> ciclo;
        private Checkbox epsilon;
        private JButton agregar;
        public Curso curso;
        public CursoRegistrado cursoR;
        private PanelAux exter;
        public AgregarAux(PanelAux exter){
            this.exter = exter;
            setSize(600,100);
            setLayout(new GridBagLayout());
            JLabel codlab = new JLabel("Codigo:");
            Jcodigo = new JTextField();
            buscar = new JButton("Buscar"); buscar.addActionListener((event)->{
                curso = principal.getBanner().getCatalogo().get(Jcodigo.getText());
                if(curso != null){
                    agregar.setEnabled(true);
                    cursoN.setText(curso.getNombre());
                    Janio.setEnabled(true);
                    semestre.setEnabled(true);
                    if(!curso.isCompleto()){
                        ciclo.setEnabled(true);
                    }

            }});
            agregar = new JButton("agregar");agregar.addActionListener((event)->{
                Periodo p;
                try {
                    int anio = Integer.parseInt(Janio.getText());
                    if(ciclo.isVisible()){
                        p = new Periodo(anio, (Integer) semestre.getSelectedItem(), (Integer) ciclo.getSelectedItem());
                    }else{
                        p = new Periodo(anio, (Integer) semestre.getSelectedItem());
                    }
                    String semestre = p.periodoS();
                    int indice = 0;
                    int np = tableModel.getColumnCount();
                    ArrayList<String> columnNames = new ArrayList<>(np);
                    while(indice < np){
                        String header = tablaPlan.getColumnName(indice);
                        columnNames.add(header);
                        if(header.equals(semestre)){
                            break;
                        }
                        indice++;
                    }
                    if(indice == np){
                        columnNames.add(semestre);
                        tableModel.setColumnIdentifiers(columnNames.toArray());
                        columnNames.sort(String::compareTo); //me dio pereza hacer un insert en orden
                        int nuevoIn =  columnNames.indexOf(semestre);
                        tableModel.setValueAt(curso.getCodigo(),0,indice);
                        tablaPlan.moveColumn(indice ,nuevoIn);
                    }else{
                        int ultimoOcupado = tableModel.getRowCount() -1;
                        while(ultimoOcupado >-1){
                            if(tableModel.getValueAt(ultimoOcupado, indice) != null){
                                break;
                            }
                            ultimoOcupado--;
                        }
                        if(ultimoOcupado == tableModel.getRowCount()-1){
                            String[] nuevaLinea = new String[np]; nuevaLinea[indice] = curso.getCodigo();
                            tableModel.addRow(nuevaLinea);
                        }else{
                            tableModel.setValueAt(curso.getCodigo(),ultimoOcupado-1,indice);
                        }
                    }
                    cursoR = new CursoRegistrado(curso, EstadoCurso.Planeado,epsilon.getState(),p);
                } catch (NumberFormatException numberFormatException) {
                    numberFormatException.printStackTrace();
                }
                SwingUtilities.updateComponentTreeUI(exter);
                actualizarParcialPanel();
            });
            ciclo = new JComboBox<>(new Integer[]{1,2});


            cursoN = new JTextField();
            Janio = new JTextField("año");
            semestre = new JComboBox<>(new Integer[]{10,19,20});semestre.addItemListener((event)->{
                ciclo.setEnabled((int) semestre.getSelectedItem() != 19);});
            epsilon = new Checkbox("epsilon.");
            GridBagConstraints gb = new GridBagConstraints();
            gb.gridx = 0; gb.gridy = 0; gb.gridwidth = 4; gb.gridheight =1;gb.weightx = 4; gb.fill = 1;
            add(codlab,gb);
            gb.gridx = 4; gb.gridwidth = 5;gb.weightx = 5;
            add(Jcodigo,gb);
            gb.gridx = 9; gb.gridwidth = 3;gb.weightx = 3;
            add(buscar,gb);
            gb.gridx = 0; gb.gridy = 1; gb.gridwidth = 3; gb.weightx = 3;
            add(cursoN,gb);
            gb.gridx = 3; gb.gridwidth = 2;gb.weightx = 2;
            add(Janio,gb);
            gb.gridx = 5;
            add(semestre,gb);
            gb.gridx = 7;
            add(ciclo,gb);
            gb.gridx = 9;
            add(epsilon,gb);
            gb.gridx = 11;
            add(agregar,gb);
            actualizarParcialPanel();
        }

        void actualizarParcialPanel(){
            Jcodigo.setText("");
            cursoN.setText("Curso a agregar");
            Janio.setEnabled(false);
            semestre.setEnabled(false);
            ciclo.setEnabled(false);
            agregar.setEnabled(false);
        }

    }


    @Override
    public void mostrar(PanelImagenFondo fondo) {
        setOpaque(false);
        fondo.add(this);
    }

}
