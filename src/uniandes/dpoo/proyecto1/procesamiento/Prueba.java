package uniandes.dpoo.proyecto1.procesamiento;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Pensum;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.ReqTipologia;
import uniandes.dpoo.proyecto1.modelo.Nota.Nota;
import uniandes.dpoo.proyecto1.modelo.Nota.NotaCual;
import uniandes.dpoo.proyecto1.modelo.Nota.calCual;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.EstadoCurso;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.RequerimientoCreditos;
import uniandes.dpoo.proyecto1.modelo.Restricciones.Correquisito;
import uniandes.dpoo.proyecto1.modelo.Restricciones.Prerrequisito;
import uniandes.dpoo.proyecto1.modelo.Restricciones.Restriccion;
import uniandes.dpoo.proyecto1.modelo.usuario.Admin;
import uniandes.dpoo.proyecto1.modelo.usuario.Carrera;
import uniandes.dpoo.proyecto1.modelo.usuario.Coordinador;
import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Prueba {

    public static void cargarPrueba(Banner banner){
        Pensum pensumPru = new Pensum("prueba");
        Requerimiento req1 = new RequerimientoCreditos("IP", Nivel.uno,1, ReqTipologia.Obligatorio,3,
                new ArrayList<>(Arrays.asList("ISIS-1221", "1121")),new String[]{"ISIS-1221"});
        Requerimiento req2 = new RequerimientoCreditos("FISICA 1", Nivel.uno,2, ReqTipologia.Obligatorio,3,
                new ArrayList<>(Arrays.asList("FISI-1018")),new String[]{"FISI-1018"});
        pensumPru.agregarRequerimiento(req1);
        pensumPru.agregarRequerimiento(req2);
        Correquisito r21 = new Correquisito(new ArrayList<>(Arrays.asList("FISI-1018")));

        Curso c1 = new Curso("IP","ISIS-1221","ISIS",3,true,true,"soloPythonlks",
                new ArrayList<>());
        Curso c2 = new Curso("FISICA 1","FISI-1018","ISIS",3,true,true,"soloPythonlks",
                new ArrayList<>());
        Curso c3 = new Curso("FISICA EXP 1", "FISI-1019","ISIS",1,true,true,"laExprimentacion",
                new ArrayList<>(Arrays.asList(r21)));
        Periodo psis = banner.getPeriodo();

        Carrera carreraPru = new Carrera("CarrraPrueba","p");
        carreraPru.setPensumActual(pensumPru);
        Estudiante e = new Estudiante("E","1","pedrito","10230", pensumPru, "CarrraPrueba", psis);
        banner.getUsuarios().put("E",e);
        Map<String,CursoRegistrado> cursosRegistrados = new HashMap<>();
        Periodo p = Periodo.copy(psis);

        cursosRegistrados.put("ISIS-1221",new CursoRegistrado(c1, EstadoCurso.Inscrito, false, p));
        cursosRegistrados.put("FISI-1019", new CursoRegistrado(c3, EstadoCurso.Inscrito,false,p));
        cursosRegistrados.put("FISI-1018",new CursoRegistrado(c2, EstadoCurso.Inscrito,false,p));
        Curso c4 = new Curso("Estructura de Datos","ISIS-1206","ISIS",3,true,true,"EdamosfelicesYnolosabiamos",
                new ArrayList<>(Arrays.asList(
                        new Prerrequisito(new ArrayList<>(Arrays.asList("ISIS-1221", "ISIS-1021"))))));
        Curso c5 = new Curso("FISICA 1","FISI-1018","ISIS",3,true,true,"soloPythonlks",
                new ArrayList<>());

        e.nuevoPlan("miprimerplan :)",new Periodo(2022,10));
        e.nuevoPlan("misecond :)",new Periodo(2022,20));

        Plan plan1 = e.getPlan("miprimerplan :)");
        e.getHistoriaAcademica().inscripcionCursos(cursosRegistrados);
        plan1.validarInscritos();
        plan1.agregarCursos(new ArrayList<>(Arrays.asList(
                new CursoRegistrado(c4,EstadoCurso.Planeado,false,new Periodo(2023,19)),
                new CursoRegistrado(c5,EstadoCurso.Planeado, false, new Periodo(2024,20))
        )));
        banner.getUsuarios().put("Admin", new Admin("Admin","soyAdmin", "eladmin"));
        banner.getUsuarios().put("C",new Coordinador("C","2","Elcordi",carreraPru));
    }
}
