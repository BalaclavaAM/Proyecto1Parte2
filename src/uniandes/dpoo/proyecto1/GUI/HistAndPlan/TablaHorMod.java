package uniandes.dpoo.proyecto1.GUI.HistAndPlan;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.EstadoCurso;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Vector;

public class TablaHorMod extends JTable{
    private final ArrayList<CursoRegistrado> posicionCursos;
    private final DefaultTableModel model;
    private final ArrayList<String> posicionEstado;
    private final  ArrayList<CursoRegistrado> cursosAgregar;
    private final   ArrayList<CursoRegistrado> cursosQuitar;
    private final boolean nota;
    private final int colSemestre;

    public TablaHorMod(Map<String,ArrayList<CursoRegistrado>> infoSemestres, boolean nota){
        this.posicionCursos = new ArrayList<>();
        this.posicionEstado = new ArrayList<>();
        this.cursosAgregar = new ArrayList<>();
        this.cursosQuitar = new ArrayList<>();
        this.nota = nota;
        Vector<String> colnames;
        if (nota){
            colnames = new Vector<>(Arrays.asList("Codigo","Nombre","Materia","Creditos","Nota","Semestre"));
            colSemestre = 5;
        }else{
            colnames = new Vector<>(Arrays.asList("Codigo","Nombre","Materia","Creditos","Semestre"));
            colSemestre = 4;
        }
        model = new DefaultTableModel(crearDataTablaHor(infoSemestres),colnames){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        setModel(model);
        getTableHeader().setReorderingAllowed(false);
        setCellSelectionEnabled(true);
        setDefaultRenderer(Object.class,new MiRender());
    }


    public void agregar(CursoRegistrado cursoR){
        Vector<String> fila = nuevaFila(cursoR, nota);
        int pos = calcularPosicion(cursoR.getPeriodo().periodoS());
        model.insertRow(pos,fila);
        cursosAgregar.add(cursoR);
        posicionEstado.add(pos,"Agregar");
        posicionCursos.add(pos,cursoR);
    }

    private Vector<String> nuevaFila(CursoRegistrado cursoR, boolean nota) {
        Curso curso = cursoR.getCurso();
        Vector<String> fila = new Vector<>(Arrays.asList(curso.getCodigo(),curso.getNombre(),curso.getMateria(),
                 String.valueOf(curso.getCreditos()), cursoR.getPeriodo().periodoS()));
        if (nota){
            fila.insertElementAt(cursoR.getNota().toString(),4);
        }
        return fila;
    }

    public void quitar(){
        int fila = getSelectedRow();
        if(fila != -1) {
            CursoRegistrado cursoR = posicionCursos.get(fila);
            cursosQuitar.add(cursoR);
            posicionEstado.set(fila,"Quitar");
        }
    }


    public void deshacer(){
        int fila = getSelectedRow();
        if(fila != -1) {
            CursoRegistrado cursoR = posicionCursos.get(fila);
            String estado = posicionEstado.get(fila);
            if (estado.equals("Quitar")){
                cursosQuitar.remove(cursoR);
                posicionEstado.set(fila,cursoR.getEstado().toString());
            }
            else if(estado.equals("Agregar")){
                cursosAgregar.remove(cursoR);
                model.removeRow(getSelectedRow());
                posicionEstado.remove(fila);
            }
        }
    }

    private int calcularPosicion(String semestre){
        for (int i = 0; i < getRowCount(); i++) {
            String sc = (String) getValueAt(i,colSemestre);
            if(sc.compareTo(semestre) > 0){
                return i;
            }
        }
        return getRowCount();
    }


    private Vector<Vector<String>> crearDataTablaHor(Map<String,ArrayList<CursoRegistrado>> infoSemestres){
        Vector<Vector<String>> data = new Vector<>();
        ArrayList<String> semestres = new ArrayList<>(infoSemestres.keySet());
        semestres.sort(String::compareTo);
        for(String semestre: semestres){
            for(CursoRegistrado cursoR: infoSemestres.get(semestre)){
                Vector<String> fila =  nuevaFila(cursoR, nota);
                posicionCursos.add(cursoR);
                posicionEstado.add(cursoR.getEstado().toString());
                data.add(fila);
            }
        }
        return data;
    }

    public ArrayList<CursoRegistrado> getCursosAgregar() {
        return cursosAgregar;
    }

    public ArrayList<CursoRegistrado> getCursosQuitar() {
        return cursosQuitar;
    }


    public void mostrarInfo(boolean nota){
        int fila = getSelectedRow();
        CursoRegistrado cursoR = posicionCursos.get(fila);
        if(fila != -1 && cursoR != null) {
            String estado = posicionEstado.get(fila);
            new InfoAux(cursoR,estado,nota);
        }
    }

    /**
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
     */

    public CursoRegistrado getSelecteCursoR(){
        int fila = getSelectedRow();
        if(fila != -1) {
            return posicionCursos.get(fila);
        }
        return null;
    }

    class MiRender extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table,
                                                       Object value,
                                                       boolean isSelected,
                                                       boolean hasFocus,
                                                       int row,
                                                       int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            this.setOpaque(false);
            String estado = posicionEstado.get(row);
            CursoRegistrado cr = posicionCursos.get(row);
            if (estado.equals("Agregar")) {
                this.setOpaque(true);
                this.setBackground(Color.GREEN);
                this.setForeground(Color.BLUE);
            } else if (estado.equals("Quitar")) {
                this.setOpaque(true);
                this.setBackground(Color.RED);
                this.setForeground(Color.YELLOW);
            } else if (cr.getEstado().equals(EstadoCurso.Inscrito)) {
                this.setOpaque(true);
                this.setBackground(Color.BLUE);
                this.setForeground(Color.BLACK);
            } else if(row%2 == 0){
                this.setOpaque(true);
                this.setBackground(Color.WHITE);
                this.setForeground(Color.BLACK);
            }else{
                this.setOpaque(true);
                this.setBackground(Color.GRAY);
                this.setForeground(Color.WHITE);
            }

            if(getSelectedRow() == row){
                this.setBackground(getBackground().darker());
            }
            return this;
        }
    }
}
