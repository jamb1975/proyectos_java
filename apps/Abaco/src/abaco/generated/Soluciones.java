package abaco.generated;
import abaco.AbacoApp;
import abaco.SolucionCategory;
import abaco.SolucionInfo;
import abaco.playground.PlaygroundProperty;
import java.util.ArrayList;
import javafx.application.ConditionalFeature;
import java.util.HashMap;
import java.util.List;
import jfxtras.styles.jmetro8.JMetro;
import model.UsuarioSoluciones;
public class Soluciones{
    private static final SolucionInfo SOLUCION_EMPTY=new SolucionInfo("", "", "", "", "", "",new PlaygroundProperty[]{},new ConditionalFeature[]{}, false, "");
   
     
   //private static final SolucionInfo SOLUCION_3 = new SolucionInfo("Administrador de Categorias","Administrador de Categorias","/Parametrizacion/Crear Categoria","/abaco/soluciones/parametrizacion","abaco.soluciones.parametrizacion.FormmCategoria","/abaco/soluciones/parametrizacion/Categoria.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true);
   private static final SolucionInfo SOLUCION_21 = new SolucionInfo("InformacionAdministrativa","AdminOrganizacion","/InformacionAdministrativa/AdminOrganizacion","/abaco/soluciones/contabilidad","abaco.soluciones.informacionadministrativa.AdminOrganizacion","/images/empresa.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Organización");
   private static final SolucionInfo SOLUCION_22 = new SolucionInfo("InformacionAdministrativa","AdminCategorias","/InformacionAdministrativa/AdminCategorias","/abaco/soluciones/contabilidad","abaco.soluciones.informacionadministrativa.AdminCategorias","/images/categorias.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Categorias");
   private static final SolucionInfo SOLUCION_23 = new SolucionInfo("InformacionAdministrativa","AdminProductos","/InformacionAdministrativa/AdminProductos","/abaco/soluciones/contabilidad","abaco.soluciones.informacionadministrativa.AdminProductos","/images/producto.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Productos");
   private static final SolucionInfo SOLUCION_24 = new SolucionInfo("InformacionAdministrativa","AdminServicios","/InformacionAdministrativa/AdminServicios","/abaco/soluciones/contabilidad","abaco.soluciones.informacionadministrativa.AdminServicios","/images/servicios.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Servicios");
   private static final SolucionInfo SOLUCION_25 = new SolucionInfo("InformacionAdministrativa","AdminProveedores","/InformacionAdministrativa/AdminProveedores","/abaco/soluciones/contabilidad","abaco.soluciones.informacionadministrativa.AdminProveedores","/images/proveedores.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Proveedores");
   private static final SolucionInfo SOLUCION_26 = new SolucionInfo("InformacionAdministrativa","AdminPersonas","/InformacionAdministrativa/AdminPersonas","/abaco/soluciones/contabilidad","abaco.soluciones.informacionadministrativa.AdminPersonas","/images/personas.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Terceros");
  
   private static final SolucionInfo SOLUCION_11= new SolucionInfo("ProcesosAdministrativos","AdminFacturaProveedores","/ProcesosAdministrativos/Inventario","/abaco/soluciones/documentos","abaco.soluciones.procesosadministrativos.AdminFacturaProveedores","/images/compras.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Compra a Proveedor");
   private static final SolucionInfo SOLUCION_12 = new SolucionInfo("ProcesosAdministrativos","Factura","/ProcesosAdministrativos/FFactura","/abaco/soluciones/procesosadministrativos","abaco.soluciones.procesosadministrativos.FFactura","/images/caja.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Factura");
   private static final SolucionInfo SOLUCION_13 = new SolucionInfo("ProcesosAdministrativos","AdminFacturas","/ProcesosAdministrativos/AdminFacturas","/abaco/soluciones/procesosadministrativos","abaco.soluciones.procesosadministrativos.AdminFacturas","/images/adminfacturas.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Admin. Factura");
   private static final SolucionInfo SOLUCION_14 = new SolucionInfo("ProcesosAdministrativos","AdminComprobanteEgreso","/ProcesosAdministrativos/FFactura","/abaco/soluciones/procesosadministrativos","abaco.soluciones.procesosadministrativos.AdminComprobanteEgreso","/images/egreso.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Comp. Egreso");
   private static final SolucionInfo SOLUCION_15 = new SolucionInfo("ProcesosAdministrativos","AdminComprobanteIngreso","/ProcesosAdministrativos/AdminComprobanteIngreso","/abaco/soluciones/procesosadministrativos","abaco.soluciones.procesosadministrativos.AdminComprobanteIngreso","/images/recibocaja.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Recibo Ingreso");
     
   // private static final SolucionInfo SOLUCION_14 = new SolucionInfo("Atencion a Clientes","Atencion a Clientes","/Reportes/Atencion a Clientes","/abaco/soluciones/reportes","abaco.soluciones.reportes.AtencionAClientes","/abaco/soluciones/reportes/AtencionAClientes.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true);
   private static final SolucionInfo SOLUCION_31 = new SolucionInfo("Reportes","Ventas por Producto","/Reportes/Ventas por Producto","/abaco/soluciones/reportes","abaco.soluciones.reportes.VentasPorProducto","/images/reporteventas.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Ventas producto");
   private static final SolucionInfo SOLUCION_32 = new SolucionInfo("Reportes","Existencias","/Reportes/Existencias","/abaco/soluciones/reportes","abaco.soluciones.reportes.Existencias","/images/existencia.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Existencias");
   private static final SolucionInfo SOLUCION_33 = new SolucionInfo("Reportes","Cartera","/Reportes/Cartera","/abaco/soluciones/reportes","abaco.soluciones.reportes.Cartera","/images/cartera.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Cartera");
   private static final SolucionInfo SOLUCION_34 = new SolucionInfo("Reportes","FArqueo","/Reportes/FArqueo","/abaco/soluciones/reportes","abaco.soluciones.reportes.FArqueo","/images/arqueo.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Arqueo Caja");
   private static final SolucionInfo SOLUCION_35 = new SolucionInfo("Reportes","ImportarExcel","/Reportes/ImportarExcel","/abaco/soluciones/reportes","abaco.soluciones.reportes.ImportarExcel","/images/arqueo.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"ImportarExcel");
   private static final SolucionInfo SOLUCION_36 = new SolucionInfo("Reportes","DeudasProveedores","/Reportes/DeudasProveedores","/abaco/soluciones/DeudasProveedores","abaco.soluciones.reportes.DeudasProveedores","/images/deudas.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Deudas a Proveedores");
   private static final SolucionInfo SOLUCION_37 = new SolucionInfo("Reportes","PedidosUrgentes","/Reportes/PedidosUrgentes","/abaco/soluciones/PedidosUrgentes","abaco.soluciones.reportes.PedidosUrgentes","/images/pedidosurgentes.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Pedidos Urgentes");
  
   private static final SolucionInfo SOLUCION_41 = new SolucionInfo("UsuariosSoluciones","UsuariosSoluciones","/abaco/admon/UsuariosSoluciones","/abaco/admon","abaco.admon.UsuariosSoluciones","/images/usuarios.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Usuarios");
   private static final SolucionInfo SOLUCION_42 = new SolucionInfo("FAdminSoluciones","FAdminSoluciones","/abaco/admon/FAdminSoluciones","/abaco/admon","abaco.admon.FAdminSoluciones","/images/solucion.png",new PlaygroundProperty[]{},new ConditionalFeature[]{},true,"Soluciones");
   private static final HashMap<String,SolucionInfo[]> DOCS_URL_TO_SAMPLE = new HashMap<String,SolucionInfo[]>(1);
   public static  abaco.SolucionCategory ROOT = Soluciones.tiene_permiso("");
   public static final SolucionInfo[] HIGHLIGHTS = new SolucionInfo[]{};
  
   
    public static SolucionInfo[] getSamplesForDoc(String docUrl) {
        return DOCS_URL_TO_SAMPLE.get(docUrl);
    }
public static SolucionCategory tiene_permiso(String sol)
   {
        abaco.SolucionCategory []solgen=new SolucionCategory[1];
        List<abaco.SolucionInfo>informacionadministrativa=new ArrayList<>();
        List<abaco.SolucionInfo>procesosadministrativos=new ArrayList<>();
        List<abaco.SolucionInfo>administracion=new ArrayList<>();
        List<abaco.SolucionInfo>reportes=new ArrayList<>();
        List<abaco.SolucionInfo>empty=new ArrayList<>();
        int contador_soluciones = 0;
        empty.add(SOLUCION_EMPTY);
        ROOT = new abaco.SolucionCategory("ROOT", null, null, new abaco.SolucionCategory[]{new abaco.SolucionCategory("", empty.toArray(new SolucionInfo[0]), empty.toArray(new SolucionInfo[0]), null, "No tiene ninguna solución asignada"),}, null);
        if (AbacoApp.s_usuarios.getUsuario() != null) {
            if (AbacoApp.s_usuarios.getUsuario().equals("admin") && AbacoApp.s_usuarios.getPassword().equals("JAmbg172*")) {
                administracion.add(SOLUCION_41);
                administracion.add(SOLUCION_42);
                  
                  AbacoApp.cb_soluciones.getItems().add("11 Compra a Proveedor");
                  AbacoApp.cb_soluciones.getItems().add("12 Factura");
                  AbacoApp.cb_soluciones.getItems().add("13 Admin. Facturas");
                  AbacoApp.cb_soluciones.getItems().add("14 Recibo de Caja");
                  AbacoApp.cb_soluciones.getItems().add("15 Recibo de Ingreso");
                  AbacoApp.cb_soluciones.getItems().add("21 Organización");
                  AbacoApp.cb_soluciones.getItems().add("22 Categorías");
                  AbacoApp.cb_soluciones.getItems().add("23 Productos");
                  AbacoApp.cb_soluciones.getItems().add("24 Servicios");
                  AbacoApp.cb_soluciones.getItems().add("25 Proveedores"); 
                  AbacoApp.cb_soluciones.getItems().add("26 Terceros");
                  AbacoApp.cb_soluciones.getItems().add("31 Ventas Por Producto");
                  AbacoApp.cb_soluciones.getItems().add("32 Existencias");
                  AbacoApp.cb_soluciones.getItems().add("33 Cartera");
                  AbacoApp.cb_soluciones.getItems().add("34 Arqueo");
                  AbacoApp.cb_soluciones.getItems().add("36 Deudas a Proveedores");
                  AbacoApp.cb_soluciones.getItems().add("37 Pedidos Urgentes");
                ROOT = new abaco.SolucionCategory("ROOT", null, null, new abaco.SolucionCategory[]{new abaco.SolucionCategory("FAdminSoluciones", administracion.toArray(new SolucionInfo[0]), administracion.toArray(new SolucionInfo[0]), null, "Administración"),}, null);

            } else {
                for (UsuarioSoluciones us : AbacoApp.Li_UsuarioSoluciones) {
                    if (us.getSolucion().getNumeral() == 41) {
                        administracion.add(SOLUCION_41);
                    }
                    if (us.getSolucion().getNumeral() == 42) {
                        administracion.add(SOLUCION_42);
                    }
                    if (us.getSolucion().getNumeral() == 11) {
                        procesosadministrativos.add(SOLUCION_11);
                      
                    }
                    if (us.getSolucion().getNumeral() == 12) {
                        procesosadministrativos.add(SOLUCION_12);
                       
                    }
                    if (us.getSolucion().getNumeral() == 13) {
                        procesosadministrativos.add(SOLUCION_13);
                        
                    }
                    if (us.getSolucion().getNumeral() == 14) {
                        procesosadministrativos.add(SOLUCION_14);
                        
                    }
                    if (us.getSolucion().getNumeral() == 15) {
                        procesosadministrativos.add(SOLUCION_15);
                       
                        contador_soluciones++;
                    }
                  
                    if (us.getSolucion().getNumeral() == 21) {
                        informacionadministrativa.add(SOLUCION_21);
                      
                        
                    }
                   // if (us.getSolucion().getNumeral() == 22) {
                      //  informacionadministrativa.add(SOLUCION_22);
                         
                 //   }
                    if (us.getSolucion().getNumeral() == 23) {
                        informacionadministrativa.add(SOLUCION_23);
                        
                    }
                    if (us.getSolucion().getNumeral() == 24) {
                        informacionadministrativa.add(SOLUCION_24);
                        
                    }
                    if (us.getSolucion().getNumeral() == 25) {
                        informacionadministrativa.add(SOLUCION_25);
                         
                    }
                   
                     if (us.getSolucion().getNumeral() == 26) {
                        informacionadministrativa.add(SOLUCION_26);
                         
                        contador_soluciones++;
                    }
                    if (us.getSolucion().getNumeral() == 31) {
                        reportes.add(SOLUCION_31);
                        
                        
                    }
                    if (us.getSolucion().getNumeral() == 32) {
                        reportes.add(SOLUCION_32);
                        
                    }
                    if (us.getSolucion().getNumeral() == 33) {
                        reportes.add(SOLUCION_33);
                       
                       
                    }
                    if (us.getSolucion().getNumeral() == 34) {
                        reportes.add(SOLUCION_34);
                       
                       
                    }
                    if (us.getSolucion().getNumeral() == 35) {
                       // reportes.add(SOLUCION_35);
                       
                       
                    }
                     if (us.getSolucion().getNumeral() == 36) {
                       reportes.add(SOLUCION_36);
                       
                        
                       
                    }
                      if (us.getSolucion().getNumeral() == 37) {
                       reportes.add(SOLUCION_37);
                      
                        contador_soluciones++;
                       
                    }
                }
                List<abaco.SolucionCategory> solcat = new ArrayList<>();
                if (procesosadministrativos.size() > 0) {
                    solcat.add(new abaco.SolucionCategory("InformacionAdministrativa", informacionadministrativa.toArray(new SolucionInfo[0]), informacionadministrativa.toArray(new SolucionInfo[0]), null, "Información Administrativa"));
                }
                if (informacionadministrativa.size() > 0) {

                    solcat.add(new abaco.SolucionCategory("ProcesosAdministrativos", procesosadministrativos.toArray(new SolucionInfo[0]), procesosadministrativos.toArray(new SolucionInfo[0]), null, "Procesos Administrativos"));
                }
                if (reportes.size() > 0) {
                    solcat.add(new abaco.SolucionCategory("Reportes", reportes.toArray(new SolucionInfo[0]), reportes.toArray(new SolucionInfo[0]), null, "Reportes"));

                }
               
                abaco.SolucionCategory[] solcat2 = solcat.toArray(new SolucionCategory[0]);
                ROOT = new abaco.SolucionCategory("ROOT", null, null, solcat2, null);

            }

        }
        
        return ROOT;
   }
       
}
