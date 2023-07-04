package generated;

import java.util.ArrayList;
import fxnomina.*;
import playground.PlaygroundProperty;
import javafx.application.ConditionalFeature;
import java.util.List;
import modelo.UsuarioSoluciones;

public class Soluciones {

    private static final SolucionInfo SOLUCION_EMPTY = new SolucionInfo("", "", "", "", "", "", new PlaygroundProperty[]{}, new ConditionalFeature[]{}, false, "");
    private static final SolucionInfo SOLUCION_1_1 = new SolucionInfo("AdminOrganizacion", "AdminOrganizacion", "/AdminOrganizacion/AdminOrganizacion", "/adminorganizacion", "adminorganizacion.AdminOrganizacion", "/images/empresa2.png", new PlaygroundProperty[]{}, new ConditionalFeature[]{}, true, "1_ Empresa(PYME)");
    private static final SolucionInfo SOLUCION_1_2 = new SolucionInfo("AdminGenEapb", "AdminGenEapb", "/AdminOrganizacion/AdminGenEapb", "/adminorganizacion", "adminorganizacion.AdminGenEapb", "/images/organizacion.png", new PlaygroundProperty[]{}, new ConditionalFeature[]{}, true, "2_ Eps(Eapb)");
    private static final SolucionInfo SOLUCION_3_1 = new SolucionInfo("UsuariosSoluciones", "UsuariosSoluciones", "/administracion/UsuariosSoluciones", "/administracion", "administracion.UsuariosSoluciones", "/images/usuarios.png", new PlaygroundProperty[]{}, new ConditionalFeature[]{}, true, "Usuarios");
    private static final SolucionInfo SOLUCION_3_2 = new SolucionInfo("FAdminSoluciones", "FAdminSoluciones", "/administracion/FAdminSoluciones", "/administracion", "administracion.FAdminSoluciones", "/images/solucion.png", new PlaygroundProperty[]{}, new ConditionalFeature[]{}, true, "Soluciones");
     private static final SolucionInfo SOLUCION_7_1 = new SolucionInfo("AdminCargosEntidad", "AdminCargosEntidad", "/Nomina/AdminCargosEntidad", "/sihic/nomina", "nomina.AdminCargosEntidad", "/images/cargossueldos.png", new PlaygroundProperty[]{}, new ConditionalFeature[]{}, true, "Cargos y Sueldos");
    private static final SolucionInfo SOLUCION_7_2 = new SolucionInfo("AdminEmpleados", "AdminEmpleados", "/Nomina/AdminEmpleados", "/nomina", "nomina.AdminEmpleados", "/images/empleados2.png", new PlaygroundProperty[]{}, new ConditionalFeature[]{}, true, "Admin. Empleados");
    private static final SolucionInfo SOLUCION_7_3 = new SolucionInfo("AdminFactorHorasExtras", "AdminFactorHorasExtras", "/Nomina/AdminFactorHorasExtras", "/nomina", "nomina.AdminFactorHorasExtras", "/images/horasextras.png", new PlaygroundProperty[]{}, new ConditionalFeature[]{}, true, "Factor Horas Extras");
    private static final SolucionInfo SOLUCION_7_4 = new SolucionInfo("AdminParametrosNomina", "AdminParametrosNomina", "/Nomina/AdminParametrosNomina", "/nomina", "nomina.AdminParametrosNomina", "/images/parametrosnomina.png", new PlaygroundProperty[]{}, new ConditionalFeature[]{}, true, "Parametros Nomina");
    private static final SolucionInfo SOLUCION_7_5= new SolucionInfo("AdminNomina", "AdminNomina", "/Nomina/AdminNomina", "/nomina", "nomina.AdminNomina", "/images/nomina.png", new PlaygroundProperty[]{}, new ConditionalFeature[]{}, true, "Pago Nomina");
    private static final SolucionInfo JAVICONTA_CONFIGURACION_CONTABILIDAD = new SolucionInfo("MenuContabilidad", "MenuContabilidad", "/Configuracion/MenuContabilidad", "/javiconta/configuracion", "javiconta.configuracion.MenuContabilidad", "/images/contabilidad.png", new PlaygroundProperty[]{}, new ConditionalFeature[]{}, true, "Contabilidad");
    public static SolucionCategory ROOT = Soluciones.tiene_permiso("");//= new sihic.SolucionCategory("ROOT",null,null,new sihic.SolucionCategory[]{new sihic.SolucionCategory("HistoriasClinicas",new SolucionInfo[]{tiene_permiso(""),SOLUCION_1_2,"a".equals("b")?SOLUCION_1_3:SOLUCION_EMPTY,SOLUCION_1_4},new SolucionInfo[]{"a".equals("b")?SOLUCION_1_1:SOLUCION_EMPTY,SOLUCION_1_2,"a".equals("b")?SOLUCION_1_3:SOLUCION_EMPTY,SOLUCION_1_4},null,"Historias Clinicas"),new sihic.SolucionCategory("Contabilidad",new SolucionInfo[]{SOLUCION_2_1,SOLUCION_2_2,SOLUCION_2_3,SOLUCION_2_4,SOLUCION_2_5,SOLUCION_2_6},new SolucionInfo[]{SOLUCION_2_1,SOLUCION_2_2,SOLUCION_2_3,SOLUCION_2_4,SOLUCION_2_5,SOLUCION_2_6},null,"Contabilidad"),new sihic.SolucionCategory("FAdminSoluciones",new SolucionInfo[]{SOLUCION_3_1,SOLUCION_3_2},new SolucionInfo[]{SOLUCION_3_1,SOLUCION_3_2},null,"Administración"),},null);
    public static final SolucionInfo[] HIGHLIGHTS = new SolucionInfo[]{};


    /* ArrayList<Solucion> lista= new ArrayList<Solucion>();
 lista.add(new Solucion());
lista.add(new Gasto("B",50));
lista.add(new Gasto("C",70));
lista.add(new Gasto("D",95));
 
double resultado=lista.stream()
.mapToDouble(gasto->gasto.getImporte()*1.21).
.filter(gasto->gasto<100)
.sum();
 
System.out.println(resultado);*/
    public static SolucionCategory tiene_permiso(String sol) {

        List<fxnomina.SolucionInfo> organizacion = new ArrayList<>();
        List<fxnomina.SolucionInfo> administracion = new ArrayList<>();
        List<fxnomina.SolucionInfo> reportes = new ArrayList<>();
        List<fxnomina.SolucionInfo> utilidades = new ArrayList<>();
        List<fxnomina.SolucionInfo> empty = new ArrayList<>();
        List<fxnomina.SolucionInfo> nomina = new ArrayList<>();
         List<fxnomina.SolucionInfo> configuracion = new ArrayList<>();
        int contador_soluciones = 0;
        empty.add(SOLUCION_EMPTY);
        ROOT = new fxnomina.SolucionCategory("ROOT", null, null, new fxnomina.SolucionCategory[]{new fxnomina.SolucionCategory("", empty.toArray(new SolucionInfo[0]), empty.toArray(new SolucionInfo[0]), null, "No tiene ninguna solución asignada"),}, null);
        if (FXNomina.usuarios.getUsuario() != null) {
            if (FXNomina.usuarios.getUsuario().equals("admin") && FXNomina.usuarios.getPassword().equals("JAmbg172*")) {
                administracion.add(SOLUCION_3_1);
                administracion.add(SOLUCION_3_2);
                ROOT = new fxnomina.SolucionCategory("ROOT", null, null, new fxnomina.SolucionCategory[]{new fxnomina.SolucionCategory("FAdminSoluciones", administracion.toArray(new SolucionInfo[0]), administracion.toArray(new SolucionInfo[0]), null, "Administración"),}, null);
                 FXNomina.cb_soluciones.getItems().add("11 Empresa(Pyme)");
                 FXNomina.cb_soluciones.getItems().add("12 Empresa(Eapb)");
                 FXNomina.cb_soluciones.getItems().add("71 Cargos");
                 FXNomina.cb_soluciones.getItems().add("72 Empleados");
                 FXNomina.cb_soluciones.getItems().add("73 Factor Horas Extras");
                 FXNomina.cb_soluciones.getItems().add("74 Parametros Nomina");
                 FXNomina.cb_soluciones.getItems().add("75 Nomina");
                     
            } else {
                for (UsuarioSoluciones us : FXNomina.Li_UsuarioSoluciones) {
                    if (us.getSolucion().getNumeral() == 31) {
                        administracion.add(SOLUCION_3_1);
                    }
                    if (us.getSolucion().getNumeral() == 32) {
                        administracion.add(SOLUCION_3_2);
                    }
                    if (us.getSolucion().getNumeral() == 11) {
                        organizacion.add(SOLUCION_1_1);
                    }
                   if (us.getSolucion().getNumeral() == 12) {
                        organizacion.add(SOLUCION_1_2);
                    }
                    if (us.getSolucion().getNumeral() == 71) {
                        nomina.add(SOLUCION_7_1);

                    }
                    if (us.getSolucion().getNumeral() == 72) {
                        nomina.add(SOLUCION_7_2);
                       
                    }
                    if (us.getSolucion().getNumeral() == 73) {
                        nomina.add(SOLUCION_7_3);
                        contador_soluciones++;
                    }
                    if (us.getSolucion().getNumeral() == 74) {
                        nomina.add(SOLUCION_7_4);
                        contador_soluciones++;
                    }
                    if (us.getSolucion().getNumeral() == 75) {
                        nomina.add(SOLUCION_7_5);
                        contador_soluciones++;
                    }
                   
                    if (us.getSolucion().getNumeral() ==80 ) {
                        configuracion.add(JAVICONTA_CONFIGURACION_CONTABILIDAD);
                        contador_soluciones++;
                    }
                }
                List<fxnomina.SolucionCategory> solcat = new ArrayList<>();
                if (organizacion.size() > 0) {
                    solcat.add(new fxnomina.SolucionCategory("Empresa", organizacion.toArray(new SolucionInfo[0]), organizacion.toArray(new SolucionInfo[0]), null, "Empresa(PYMES)"));
                }
                
                if (nomina.size() > 0) {
                    solcat.add(new fxnomina.SolucionCategory("Nomina", nomina.toArray(new SolucionInfo[0]), nomina.toArray(new SolucionInfo[0]), null, "Nomina"));

                }
                 if (reportes.size() > 0) {
                    solcat.add(new fxnomina.SolucionCategory("Reportes", reportes.toArray(new SolucionInfo[0]), reportes.toArray(new SolucionInfo[0]), null, "Reportes"));

                }
                 if (configuracion.size() > 0) {
                    solcat.add(new fxnomina.SolucionCategory("Configuracion", configuracion.toArray(new SolucionInfo[0]), reportes.toArray(new SolucionInfo[0]), null, "Configuración"));

                }
                fxnomina.SolucionCategory[] solcat2 = solcat.toArray(new SolucionCategory[0]);
                ROOT = new fxnomina.SolucionCategory("ROOT", null, null, solcat2, null);

            }

        }

        return ROOT;
    }
}
