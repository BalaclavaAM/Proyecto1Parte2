package uniandes.dpoo.proyecto1.GUI.HistAndPlan;

import uniandes.dpoo.proyecto1.GUI.PanelAux;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.MallaCursos;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

public class AuxCambios {
    private MallaCursos malla;
    private JTable tablaMalla;
    private DefaultTableModel tableModel;
    private PanelAux exter;
    private ArrayList<ArrayList<CursoRegistrado>> posicionCursos;
    private ArrayList<ArrayList<String>> posicionEstado;
    private Map<String, CursoRegistrado> cursosAgregar;
    private Map<String, ArrayList<CursoRegistrado>> cursosQuitar;
    private DefaultTableCellRenderer render;
    private boolean agregando;

    public AuxCambios(MallaCursos malla, PanelAux exter, ArrayList<ArrayList<CursoRegistrado>> posicionCursos,
                      ArrayList<ArrayList<String>> posicionEstado, Map<String, CursoRegistrado> cursosAgregar,
                      Map<String, ArrayList<CursoRegistrado>> cursosQuitar, DefaultTableCellRenderer render) {
        this.malla = malla;
        this.exter = exter;
        this.posicionCursos = posicionCursos;
        this.posicionEstado = posicionEstado;
        this.cursosAgregar = cursosAgregar;
        this.cursosQuitar = cursosQuitar;
        this.render = render;
    }


    public void mostrarInfo(boolean nota){
        int fila = tablaMalla.getSelectedRow();
        int col = tablaMalla.getSelectedColumn();
        CursoRegistrado cursoR = posicionCursos.get(fila).get(col);
        if(fila != -1 && cursoR != null) {
            String estado = posicionEstado.get(fila).get(col);
            InfoAux ia = new InfoAux(cursoR,estado,nota);
            ia.setVisible(true);
        }
    }


    public JTable crearTabla(){
            tablaMalla = new JTable();
            Map<String, ArrayList<CursoRegistrado>> infoPeriodos = malla.getInfoPeriodos();
            Vector<String> semestres = new Vector<>(infoPeriodos.keySet());
            semestres.sort(String::compareTo);

            tableModel = new DefaultTableModel(crearData(infoPeriodos, semestres), semestres) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tablaMalla.setModel(tableModel);
            tablaMalla.setCellSelectionEnabled(true);
            tablaMalla.setDefaultRenderer(Object.class, render);
            SwingUtilities.updateComponentTreeUI(exter);
        return tablaMalla;
    }

    public void validarMalla(){
        malla.actulizarMalla(cursosAgregar,cursosQuitar);
        exter.actualizarPanel();
    }

    public void quitar() {
        int fila = tablaMalla.getSelectedRow();
        int col = tablaMalla.getSelectedColumn();
        CursoRegistrado cursoR = posicionCursos.get(fila).get(col);
        if (fila != -1 && cursoR != null) {
            String estado = posicionEstado.get(fila).get(col);
            if (!estado.equals("agregar")) {
                String codigo = cursoR.getCurso().getCodigo();
                ArrayList<CursoRegistrado> listP =  cursosQuitar.computeIfAbsent(cursoR.getPeriodo().toString(),k->new ArrayList<>());
                listP.add(cursoR);
                posicionEstado.get(fila).set(col,"quitar");
                SwingUtilities.updateComponentTreeUI(exter);
            }
        }
    }



    public void deshacer(){
        int fila = tablaMalla.getSelectedRow();
        int col = tablaMalla.getSelectedColumn();
        CursoRegistrado cursoR = posicionCursos.get(fila).get(col);
        if (fila != -1 && cursoR!= null){
            String codigo = cursoR.getCurso().getCodigo();
            String estado = posicionEstado.get(fila).get(col);
            if (estado.equals("agregar")) {
                cursosAgregar.remove(codigo);
                Vector<String> colnamesTabla = columnnames();
                Vector<Vector> data = tableModel.getDataVector();
                if (fila == 0 && (tablaMalla.getRowCount() == 1 || tablaMalla.getValueAt(fila + 1, col) == null)) {
                    oneElementColumn(data,col);
                    colnamesTabla.remove(col);
                } else {
                    moreElementsColum(data,fila,col);
                }
                finalVacia(data);
                tableModel = new DefaultTableModel(data, colnamesTabla) {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                tablaMalla.setModel(tableModel);
            } else if (estado.equals("quitar")) {
                posicionEstado.get(fila).set(col, cursoR.getEstado().toString());
                cursosQuitar.remove(codigo);
            }
            SwingUtilities.updateComponentTreeUI(exter);
        }
    }



    public  void finalVacia(Vector<Vector> data) {
        boolean finalvacia = true;
        for (Object celda: data.get(data.size()-1)){
            if (celda != null) {
                finalvacia = false; break;
            }
        }
        if(finalvacia){
            data.remove(data.size()-1);
        }
    }

    private Vector<String> columnnames() {
        Vector<String> colnamesTabla = new Vector<>();
        for (int j = 0; j < tablaMalla.getColumnCount(); j++) {
            colnamesTabla.add(tablaMalla.getColumnName(j));
        }
        return colnamesTabla;
    }

    private void oneElementColumn(Vector<Vector> data, int col) {
        for (int i = 0; i < tablaMalla.getRowCount(); i++) {
            data.get(i).remove(col);
            posicionCursos.get(i).remove(col);
            posicionEstado.get(i).remove(col);
        }
    }

    private void moreElementsColum(Vector<Vector> data, int fila, int col){
        for (int i = tablaMalla.getRowCount()-1; i > fila ; i--) {
            data.get(i-1).set(col,data.get(i).get(col));
            posicionCursos.get(i-1).set(col, posicionCursos.get(i).get(col));
            posicionEstado.get(i-1).set(col, posicionEstado.get(i).get(col));
        }
        data.get(tablaMalla.getRowCount()-1).set(col,null);
        posicionCursos.get(tablaMalla.getRowCount()-1).set(col,null);
        posicionEstado.get(tablaMalla.getRowCount()-1).set(col,null);
    }

    private  Vector<Vector<String>> crearData(Map<String, ArrayList<CursoRegistrado>> infoPeriodos, Vector<String> semestres){
        int np = malla.getInfoPeriodos().size();
        Vector<Vector<String>> data = new Vector<>();
        int j = 0;
        for(String semestre: semestres){
            int i = 0;
            ArrayList<CursoRegistrado> infoPeriodo = infoPeriodos.get(semestre);
            for(CursoRegistrado cr: infoPeriodo) {
                if (data.size() == i) {
                    data.add(vectorNull(np));
                    posicionCursos.add(arrayNull(np));
                    posicionEstado.add(arrayNull(np));
                }
                data.get(i).set(j, cr.getCurso().getCodigo());
                posicionCursos.get(i).set(j, cr);
                posicionEstado.get(i).set(j, cr.getEstado().toString());
                i++;
            }
            j++;
        }
        return data;
    }

    public static JPanel centrar(JComponent comp, int margx, int margy, int ancho, int alto){
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

    public static Vector vectorNull(int n){
        Vector vectornull = new Vector<>();
        for (int i = 0; i < n; i++) {
            vectornull.add(null);
        }
        return vectornull;
    }
    public static <t> ArrayList arrayNull(int n){
        ArrayList<t> arraynull = new ArrayList();
        for (int i = 0; i < n; i++) {
            arraynull.add(null);
        }
        return arraynull;
    }
}
