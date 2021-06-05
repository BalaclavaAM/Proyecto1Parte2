package uniandes.dpoo.proyecto1.GUI.Validaciones;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.HistoriaAcademica;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

public class TablaCursosReq extends JTable {
    private HistoriaAcademica historia;
    private DefaultTableModel model;
    private ArrayList<CursoRegistrado> posicionCursos;
    private JDialog auxAc;
    private PanelValidaciones exter;

    TablaCursosReq(PanelValidaciones exter, HistoriaAcademica historia){
        this.historia = historia;
        this.exter = exter;
        posicionCursos = new ArrayList<>();
        models(crearDataTablaReq(historia.getCursosRegistrados(),historia.getCursosValidados()),
                new Vector<>(Arrays.asList("Codigo","Nombre","Periodo","Nota","Requerimiento")));
        getTableHeader().setReorderingAllowed(false);
        setCellSelectionEnabled(true);
        setDefaultRenderer(Object.class,new MiRender());
    }

    public void quitar() {
        int fila = getSelectedRow();
        if(fila != -1) {
            CursoRegistrado cr = posicionCursos.get(fila);
            historia.quitarCursoReq(cr.getCurso().getCodigo());
        }
    }

    public void cambiar() {
        int fila = getSelectedRow();
        if(fila != -1) {
            if(auxAc != null) {
                auxAc.dispose();
            }
            CursoRegistrado cr = posicionCursos.get(fila);
            auxAc = new auxCambiarReq(exter, cr,historia.getCursosValidados().get(cr.getCurso().getCodigo()) ,historia);
        }
    }

    public void validar() {
        int fila = getSelectedRow();
        if(fila != -1) {
            if(auxAc != null) {
                auxAc.dispose();
            }            CursoRegistrado cr = posicionCursos.get(fila);
            auxAc = new auxValidarReq(exter, cr, historia);
        }
    }


    public Vector<Vector<String>> crearDataTablaReq(Map<String, CursoRegistrado> cursosRegistrados,
                                                    Map<String,String> cursosValidados){
        Vector<Vector<String>> data = new Vector<>();
        ArrayList<CursoRegistrado> crL = new ArrayList<>(cursosRegistrados.values());
        crL.sort(CursoRegistrado::compareT);
        for(CursoRegistrado cr: crL){
            Curso c = cr.getCurso();
            Vector<String> fila = new Vector<>(Arrays.asList(c.getCodigo(), c.getNombre(), cr.getPeriodo().toString(),
                    cr.getNota().toString(), cursosValidados.get(c.getCodigo())));
            data.add(fila);
            posicionCursos.add(cr);
        }
        return data;
    }



    private void models(Vector<Vector<String>> data, Vector<String> columnames){
        ListSelectionListener listener = e -> {
            int fila = getSelectedRow();
            CursoRegistrado cr = posicionCursos.get(fila);
            if(historia.getCursosValidados().containsKey(cr.getCurso().getCodigo())){
                exter.botonesA();
            }else{
                exter.botonesB();
            }
        };

        model = new DefaultTableModel(data,columnames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(model);
        ListSelectionModel cellSelectionModel = getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(listener);
    }

    class MiRender extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            CursoRegistrado cr = posicionCursos.get(row);
            if (historia.getCursosValidados().containsKey(cr.getCurso().getCodigo())) {
                this.setBackground(Color.cyan);
                this.setForeground(Color.BLACK);
            } else if (row % 2 == 0) {
                this.setBackground(Color.WHITE);
                this.setForeground(Color.BLACK);
            } else {
                this.setBackground(Color.BLACK);
                this.setForeground(Color.WHITE);
            }
            if (row == getSelectedRow()) {
                this.setBackground(Color.lightGray);
            }
            return this;
        }
    }

}
