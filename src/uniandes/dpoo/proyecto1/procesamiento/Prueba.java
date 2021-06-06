package uniandes.dpoo.proyecto1.procesamiento;

import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Curso;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Nivel;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.Pensum;
import uniandes.dpoo.proyecto1.modelo.Cursos_Req.ReqTipologia;
import uniandes.dpoo.proyecto1.modelo.Nota.NotaCual;
import uniandes.dpoo.proyecto1.modelo.Nota.NotaNum;
import uniandes.dpoo.proyecto1.modelo.Registro.CursoRegistrado;
import uniandes.dpoo.proyecto1.modelo.Registro.EstadoCurso;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Periodo;
import uniandes.dpoo.proyecto1.modelo.RegistroCursos.Plan;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.Requerimiento;
import uniandes.dpoo.proyecto1.modelo.Requerimientos.RequerimientoCreditos;
import uniandes.dpoo.proyecto1.modelo.Restricciones.Correquisito;
import uniandes.dpoo.proyecto1.modelo.Restricciones.Prerrequisito;
import uniandes.dpoo.proyecto1.modelo.Restricciones.RestriccionNivel;
import uniandes.dpoo.proyecto1.modelo.usuario.Admin;
import uniandes.dpoo.proyecto1.modelo.usuario.Carrera;
import uniandes.dpoo.proyecto1.modelo.usuario.Coordinador;
import uniandes.dpoo.proyecto1.modelo.usuario.Estudiante;

import java.util.ArrayList;
import java.util.Arrays;

public class Prueba {

    public static void cargarPrueba(Banner banner){
        Pensum pensumPru = new Pensum("prueba");
        Requerimiento req1 = new RequerimientoCreditos("IP", Nivel.UNO,1, ReqTipologia.Obligatorio,3,
                new ArrayList<>(Arrays.asList("ISIS-1221", "1121")),new String[]{"ISIS-1221"});
        Requerimiento req2 = new RequerimientoCreditos("FISICA 1", Nivel.UNO,2, ReqTipologia.Obligatorio,3,
                new ArrayList<>(Arrays.asList("FISI-1018")),new String[]{"FISI-1018"});
        pensumPru.agregarRequerimiento(req1);
        pensumPru.agregarRequerimiento(req2);
        Correquisito r21 = new Correquisito(new ArrayList<>(Arrays.asList("FISI-1018")));

        Curso c1 = new Curso("IP","ISIS-1221","ISIS",3,true,
                new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new RestriccionNivel(Nivel.CERO), "descripcion");
        Curso c2 = new Curso("FISICA 1","FISI-1018","ISIS",3,true,
                new ArrayList<>(),new ArrayList<>(),new ArrayList<>(),new RestriccionNivel(Nivel.CERO), "descripcion");
        Curso c3 = new Curso("FISICA EXP 1", "FISI-1019","ISIS",1,true,
                new ArrayList<>(),new ArrayList<>(Arrays.asList(r21)),new ArrayList<>(),new RestriccionNivel(Nivel.CERO), "descripcion");
        Periodo psis = banner.getPeriodo();

        Carrera carreraPru = new Carrera("CarrraPrueba","p");
        carreraPru.setPensumActual(pensumPru);
        Estudiante e = new Estudiante("E","1","pedrito","10230", pensumPru, "CarrraPrueba", psis);
        banner.getUsuarios().put("E",e);
        ArrayList<CursoRegistrado> cursosRegistrados = new ArrayList<>();
        Periodo p = Periodo.copy(psis);

        Curso c4 = new Curso("Estructura de Datos","ISIS-1206","ISIS",3,true,
                new ArrayList<>(Arrays.asList(new Prerrequisito(new ArrayList<>(Arrays.asList("ISIS-1221", "ISIS-1021"))))), new ArrayList<>(),
                new ArrayList<>(),new RestriccionNivel(Nivel.CERO),"descripcion");
        Curso c5 = new Curso("FISICA 1","FISI-1018","ISIS",3,true,
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new RestriccionNivel(Nivel.CERO), "descripcion");

        Curso c6 = new Curso("CBCC-1177","CBCC-1177","ISIS",3,true,
                new ArrayList<>(), new ArrayList<>(),new ArrayList<>(),new RestriccionNivel(Nivel.CERO), "descripcion");

        banner.avanzarPeriodo();
        e.getHistoriaAcademica().agregarCursos(new ArrayList<>(Arrays.asList(new CursoRegistrado(c1, new NotaNum(4.0f), false, p))));
        cursosRegistrados.add(new CursoRegistrado(c3, EstadoCurso.Inscrito,false,psis));
        cursosRegistrados.add(new CursoRegistrado(c2, EstadoCurso.Inscrito,false,psis));
        banner.avanzarPeriodo();
        banner.avanzarPeriodo();

        e.getHistoriaAcademica().inscripcionCursos(cursosRegistrados);
        e.nuevoPlan("miprimerplan :)");
        e.nuevoPlan("misecond :)");
        banner.avanzarPeriodo();
        Plan plan1 = e.getPlan("miprimerplan :)");
        Periodo p2 = Periodo.copy(banner.getPeriodo());
        p2.avanzarPeriodo();
        p2.avanzarPeriodo();
        banner.avanzarPeriodo();
        plan1.agregarCursos(new ArrayList<>(Arrays.asList(
                new CursoRegistrado(c6,EstadoCurso.Planeado,false, p2),
                new CursoRegistrado(c4,EstadoCurso.Planeado,false,p2),
                new CursoRegistrado(c5,EstadoCurso.Planeado, false, p2)
        )));

        banner.getUsuarios().put("Admin", new Admin("Admin","soyAdmin", "eladmin"));
        banner.getUsuarios().put("C",new Coordinador("C","2","Elcordi",carreraPru));
    }
}
