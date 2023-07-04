package sihic.util;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.biff.CountryCode;
import jxl.read.biff.BiffException;
import jxl.write.WriteException;
import modelo.ActividadesEconomicas;
import modelo.AgnCitas;
import modelo.AgnEspecialidades;
import modelo.ConComprobanteProcedimiento;
import modelo.ConsecutivoComprobanteProcedimiento;
import modelo.Factura;
import modelo.FacturaItem;
import modelo.GenCategoriasProductos;
import modelo.GenPacientes;
import modelo.GenPersonas;
import modelo.Kardex;
import modelo.Producto;
import modelo.RetencionFuente;
import sihic.SihicApp;
import sihic.controlador.ActividadesEconomicasControllerClient;
import sihic.controlador.ImportExcelClient;
import sihic.controlador.RetencionFuenteControllerClient;
import sihic.controlador.UsuarioSolucionesControllerClient;

public class ImportarExcel extends Application {

    StackPane stack;
    private static GridPane gp_progreso;
    public static GridPane Gp_ValidarUsuario;
    public static Button Bu_importartarifas = new Button("Importar Tarifas");
    public static Button Bu_importarretefuente = new Button("Importar Retefuente");
    private UsuarioSolucionesControllerClient usuarioSolucionesControllerClient = new UsuarioSolucionesControllerClient();
    private static final ProgressIndicator p5 = new ProgressIndicator();
    private static Timeline timeline = new Timeline();
    private static Thread thimport_77;
    String fechacita;
    String horacita;
    String primernombre;
    String segundonombre;
    String primerapellido;
    String segundoapellido;
    String tipodoc;
    String documento;
    String fechanacimiento;
    String telefono;
    String sexof;
    String sexom;
    String direccion;
    String eps;
    String tipousuario;
    String codigocups;
    String especialidad;
    String prefijo;
    String no_comprobante;
    String nofactura;
    String valor;  
    String producto;
    String categoria;
    ImportExcelClient importExcelClient=new ImportExcelClient();
    public Parent createContent() throws IOException {
        Bu_importartarifas.setOnAction(e -> {
            try {
                importar_tarifas();
            } catch (Exception ex) {
                Logger.getLogger(ImportarExcel.class.getName()).log(Level.SEVERE, null, ex);
            } 
                      
        });
        Bu_importarretefuente.setOnAction(e -> {
            try {
                importar_excel_retefuente();
            } catch (IOException ex) {
                Logger.getLogger(ImportarExcel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (WriteException ex) {
                Logger.getLogger(ImportarExcel.class.getName()).log(Level.SEVERE, null, ex);
            } catch (BiffException ex) {
                Logger.getLogger(ImportarExcel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        stack = new StackPane();
        Gp_ValidarUsuario = new GridPane();
        Gp_ValidarUsuario.add(Bu_importarretefuente, 0, 0);
        Gp_ValidarUsuario.add(Bu_importartarifas, 1, 0);

        Gp_ValidarUsuario.setVgap(7);
        Gp_ValidarUsuario.getStylesheets().add(SihicApp.hojaestilos);
        Gp_ValidarUsuario.getStyleClass().add("background");

        //  showprogreso(p);
        stack.getChildren().addAll(Gp_ValidarUsuario);

        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void importar_excel_retefuente() throws IOException, WriteException, BiffException {
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setEncoding("ISO-8859-1");
        wbSettings.setLocale(new Locale("es", "ES"));
        wbSettings.setExcelDisplayLanguage("ES");
        wbSettings.setExcelRegionalSettings("ES");
        wbSettings.setCharacterSet(CountryCode.SPAIN.getValue());
        String rutaArchivo = "/home/adminlinux/contabilidad/Tablaretencion2018.xls";
        jxl.write.Label label;
        jxl.write.Number number;
        File archivoXLS = new File(rutaArchivo);
        Workbook libro;
        libro = Workbook.getWorkbook(archivoXLS, wbSettings);
        Sheet sheet = libro.getSheet(1);
        int f = 0;

        RetencionFuente retencionFuente;
        RetencionFuenteControllerClient retencionFuenteControllerClient = new RetencionFuenteControllerClient();
        for (int i = 6; i < 72; i++) {
            Cell codigo = sheet.getCell(1, i);
            Cell concepto = sheet.getCell(2, i);
            Cell condicionuvt = sheet.getCell(3, i);
            Cell porcentaje = sheet.getCell(5, i);
            //Cell tarifapormil = sheet.getCell(4, i);
            retencionFuente = null;
            retencionFuente = new RetencionFuente();
            //actividadesEconomicas.setCodigo_grupotarifa(tarifa_grupal.getContents());
            // if(codigo_ciuu_219!=null)
            //{
            //actividadesEconomicas.setCodigo_ciiu_219(codigo_ciuu_219.getContents());
            //}
            try {

                retencionFuente.setCodigo(Integer.valueOf(codigo.getContents()));
            } catch (Exception e) {

            }
            try {
                retencionFuente.setConcepto(concepto.getContents());
            } catch (Exception e) {

            }
            try {
                retencionFuente.setCondicionuvt(Integer.valueOf(condicionuvt.getContents()));
            } catch (Exception e) {

            }
            try {
                retencionFuente.setPorcentaje(new BigDecimal(porcentaje.getContents().replace(",", ".")));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (retencionFuente.getCodigo() > 0) {
                retencionFuenteControllerClient.create(retencionFuente);
            }

            /* if(tarifapormil!=null)
          {
           try{   
          actividadesEconomicas.setTarifa_pormil(Float.valueOf(tarifapormil.getContents().replaceAll(",", ".")));
           } catch(Exception e)
                  {
                  
                  }
             */
        }

        libro.close();

    }

    public void importar_excel_actividadeseconomicas() throws IOException, WriteException, BiffException {
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setEncoding("ISO-8859-1");
        wbSettings.setLocale(new Locale("es", "ES"));
        wbSettings.setExcelDisplayLanguage("ES");
        wbSettings.setExcelRegionalSettings("ES");
        wbSettings.setCharacterSet(CountryCode.SPAIN.getValue());
        String rutaArchivo = "/home/adminlinux/contabilidad/EstructuraDetalladaCIIU_4AC.xls";
        jxl.write.Label label;
        jxl.write.Number number;
        File archivoXLS = new File(rutaArchivo);
        Workbook libro;
        libro = Workbook.getWorkbook(archivoXLS, wbSettings);
        Sheet sheet = libro.getSheet(0);
        int f = 0;

        ActividadesEconomicas actividadesEconomicas;
        ActividadesEconomicasControllerClient actividadesEconomicasControllerClient = new ActividadesEconomicasControllerClient();
        for (int i = 6; i < 72; i++) {
            Cell division = sheet.getCell(0, i);
            Cell grupo = sheet.getCell(1, i);
            Cell clase = sheet.getCell(2, i);
            Cell descripcion = sheet.getCell(3, i);
            //Cell tarifapormil = sheet.getCell(4, i);
            actividadesEconomicas = null;
            actividadesEconomicas = new ActividadesEconomicas();
            //actividadesEconomicas.setCodigo_grupotarifa(tarifa_grupal.getContents());
            // if(codigo_ciuu_219!=null)
            //{
            //actividadesEconomicas.setCodigo_ciiu_219(codigo_ciuu_219.getContents());
            //}
            try {
                if (!division.getContents().equals("")) {
                    actividadesEconomicas.setCodigo_ciiu_0079(division.getContents());
                }
                if (!grupo.getContents().equals("")) {
                    actividadesEconomicas.setCodigo_ciiu_0079(grupo.getContents());
                }
                if (!clase.getContents().equals("")) {
                    actividadesEconomicas.setCodigo_ciiu_0079(clase.getContents());
                    if (Integer.valueOf(clase.getContents()) >= 111 && Integer.valueOf(clase.getContents()) <= 322) {
                        actividadesEconomicas.setTarifa_autoretencion(new BigDecimal(0.0040));
                    } else {
                        if (Integer.valueOf(clase.getContents()) >= 510 && Integer.valueOf(clase.getContents()) <= 990) {
                            actividadesEconomicas.setTarifa_autoretencion(new BigDecimal(0.0160));
                        } else {
                            if (Integer.valueOf(clase.getContents()) >= 1011 && Integer.valueOf(clase.getContents()) <= 3320) {
                                actividadesEconomicas.setTarifa_autoretencion(new BigDecimal(0.0040));
                            } else {
                                if (Integer.valueOf(clase.getContents()) >= 3511 && Integer.valueOf(clase.getContents()) <= 3900) {
                                    actividadesEconomicas.setTarifa_autoretencion(new BigDecimal(0.0160));
                                } else {
                                    if (Integer.valueOf(clase.getContents()) >= 4111 && Integer.valueOf(clase.getContents()) <= 4390) {
                                        actividadesEconomicas.setTarifa_autoretencion(new BigDecimal(0.0080));
                                    } else {
                                        if (Integer.valueOf(clase.getContents()) >= 4511 && Integer.valueOf(clase.getContents()) <= 4799) {
                                            actividadesEconomicas.setTarifa_autoretencion(new BigDecimal(0.0040));
                                        } else {
                                            if (Integer.valueOf(clase.getContents()) >= 4911 && Integer.valueOf(clase.getContents()) <= 9900) {
                                                actividadesEconomicas.setTarifa_autoretencion(new BigDecimal(0.0080));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                actividadesEconomicas.setDescripcion(descripcion.getContents());
                actividadesEconomicasControllerClient.create(actividadesEconomicas);

            } catch (Exception e) {

            }

        }

        libro.close();

    }

    public void importar_excel_productos() throws IOException, WriteException, BiffException {
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setEncoding("ISO-8859-1");
        wbSettings.setLocale(new Locale("es", "ES"));
        wbSettings.setExcelDisplayLanguage("ES");
        wbSettings.setExcelRegionalSettings("ES");
        wbSettings.setCharacterSet(CountryCode.SPAIN.getValue());
        String rutaArchivo = "/home/adminlinux/ECOOPSOS.xls";
        jxl.write.Label label;
        jxl.write.Number number;
        File archivoXLS = new File(rutaArchivo);
        Workbook libro;
        libro = Workbook.getWorkbook(archivoXLS, wbSettings);
        Sheet sheet = libro.getSheet(2);
        int f = 0;
        AgnEspecialidades agnEspecialidades = new AgnEspecialidades();

        GenCategoriasProductos genCategoriasProductos = new GenCategoriasProductos();

        for (AgnEspecialidades ae : SihicApp.l_especialidades) {
            if (ae.getId().equals(Long.valueOf(72))) {
                agnEspecialidades = ae;
            }
        }
        for (GenCategoriasProductos cp : SihicApp.l_categoriasproductos) {
            if (cp.getId().equals(Long.valueOf(1))) {
                genCategoriasProductos = cp;
            }
        }
        for (int i = 3; i < 74; i++) {
            String codigo = sheet.getCell(0, i).getContents();
            String nombre = sheet.getCell(1, i).getContents();
          //  String precio2015 = sheet.getCell(2, i).getContents();
           // String precio2016 = sheet.getCell(2, i).getContents();
           // String precio2017 = sheet.getCell(2, i).getContents();
            String precio2018 = sheet.getCell(2, i).getContents();

            SihicApp.s_producto = SihicApp.productoControllerClient.findByCodigoExactAllProductos(codigo);
            if (SihicApp.s_producto != null) {

                SihicApp.s_producto.setCodigo(codigo);
                SihicApp.s_producto.setNombre(nombre);
                SihicApp.s_producto.setAgnEspecialidades(agnEspecialidades);
                SihicApp.s_producto.setGenCategoriasProductos(genCategoriasProductos);
               
                try {
                    SihicApp.s_producto.setPrecio2018(new BigDecimal(precio2018));
                } catch (Exception e) {

                }

                SihicApp.productoControllerClient.update();
            } else {
                SihicApp.s_producto = new Producto();
                SihicApp.s_producto.setCodigo(codigo);
                SihicApp.s_producto.setNombre(nombre);
               // SihicApp.s_producto.setAgnEspecialidades(agnEspecialidades);
                SihicApp.s_producto.setGenCategoriasProductos(genCategoriasProductos);

                try {
                    SihicApp.s_producto.setPrecio2018(new BigDecimal(precio2018));
                } catch (Exception e) {

                }
                SihicApp.productoControllerClient.create();
            }
            Kardex kardex = null;
            kardex = new Kardex();
            kardex.setCantidad_entrada(1);
            kardex.setValor_entrada(SihicApp.s_producto.getPrecio2018());
            kardex.setValor_saldo(SihicApp.s_producto.getPrecio2018());
            kardex.setCantidad_saldo(1);
            kardex.setProducto(SihicApp.s_producto);
            SihicApp.kardexControllerClient.create(kardex);
            System.out.println("reg->" + i);
        }

        libro.close();

    }
public void importall() throws IOException, BiffException, ParseException
{
      WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setEncoding("ISO-8859-1");
        wbSettings.setLocale(new Locale("es", "ES"));
        wbSettings.setExcelDisplayLanguage("ES");
        wbSettings.setExcelRegionalSettings("ES");
        wbSettings.setCharacterSet(CountryCode.SPAIN.getValue());
        String rutaArchivo = "/home/adminlinux/estadisticas/rptestadisticasDICIEMBRE.xls";
        jxl.write.Label label;
        jxl.write.Number number;
        File archivoXLS = new File(rutaArchivo);
        Workbook libro;
        libro = Workbook.getWorkbook(archivoXLS, wbSettings);
        Sheet sheet = libro.getSheet(0);
        int f = 0;
       
     
        for (int i = 1; i < 1342; i++) {
           
            
            fechacita = sheet.getCell(2, i).getContents();
            if(fechacita==null)
            {
                fechacita="";
            }
            horacita = sheet.getCell(3, i).getContents();
            if(horacita==null)
            {
                horacita="";
            }
            primernombre = sheet.getCell(11, i).getContents();
            if(primernombre==null)
            {
                primernombre="";
            }
            segundonombre = sheet.getCell(12, i).getContents();
            if(segundonombre==null)
            {
                segundonombre="";
            }
            primerapellido = sheet.getCell(13, i).getContents();
            if(primerapellido==null)
            {
                primerapellido="";
            }
            segundoapellido = sheet.getCell(14, i).getContents();
            if(segundoapellido==null)
            {
                segundoapellido="";
            }
            tipodoc = sheet.getCell(15, i).getContents();
            if(tipodoc==null)
            {
                tipodoc="";
            }
            documento = sheet.getCell(16, i).getContents();
            if(documento==null)
            {
                documento="";
            }
            fechanacimiento = sheet.getCell(17, i).getContents();
            if(fechanacimiento==null)
            {
                fechanacimiento="";
            }
            telefono = sheet.getCell(21, i).getContents();
            if(telefono==null)
            {
                telefono="";
            }
            sexof = sheet.getCell(22, i).getContents();
            if(sexof==null)
            {
                sexof="";
            }
            sexom = sheet.getCell(23, i).getContents();
            if(sexom==null)
            {
                sexom="";
            }
            direccion = sheet.getCell(24, i).getContents();
            if(direccion==null)
            {
                direccion="";
            }
            eps = sheet.getCell(25, i).getContents();
            if(eps==null)
            {
                eps="";
            }
            codigocups = sheet.getCell(29, i).getContents();
            if(codigocups==null)
            {
                codigocups="";
            }
            especialidad = sheet.getCell(32, i).getContents();
            if(especialidad==null)
            {
                especialidad="";
            }
            prefijo = sheet.getCell(33, i).getContents();
            if(prefijo==null)
            {
                prefijo="";
            }
            no_comprobante = sheet.getCell(34, i).getContents();
            if(no_comprobante==null)
            {
                no_comprobante="";
            }
            tipousuario = sheet.getCell(36, i).getContents();
            if(tipousuario==null)
            {
                tipousuario="";
            }
            nofactura = sheet.getCell(37, i).getContents();
            if(nofactura==null)
            {
                nofactura="";
            }  
            valor = sheet.getCell(31, i).getContents();
            if(valor==null)
            {
                valor="0";
            }  
             producto = sheet.getCell(30, i).getContents();
            if(producto==null)
            {
                producto="";
            }
            categoria = sheet.getCell(35, i).getContents();
            if(categoria==null)
            {
                categoria="0";
            }
            if(categoria.trim().equals("Procedimientos"))
            {
                SihicApp.genCategoriasProductos=SihicApp.l_categoriasproductos.stream().filter(fi->fi.getCodigo()==1).collect(Collectors.toList()).get(0);
            }
             if(categoria.trim().equals("Insumos"))
            {
                SihicApp.genCategoriasProductos=SihicApp.l_categoriasproductos.stream().filter(fi->fi.getCodigo()==3).collect(Collectors.toList()).get(0);
            }
             if(categoria.trim().equals("Medicamentos"))
            {
                SihicApp.genCategoriasProductos=SihicApp.l_categoriasproductos.stream().filter(fi->fi.getCodigo()==2).collect(Collectors.toList()).get(0);
            }
             SihicApp.s_producto=null;
             SihicApp.s_producto=new Producto();
             SihicApp.s_producto.setGenCategoriasProductos(SihicApp.genCategoriasProductos);
             SihicApp.s_producto.setCodigo(codigocups);
             SihicApp.s_producto.setNombre(producto);
             SihicApp.s_producto.setPrecio2015(new BigDecimal(valor.trim()));
             SihicApp.s_producto.setPrecio2016(new BigDecimal(valor.trim()));
             SihicApp.s_producto.setPrecio2017(new BigDecimal(valor.trim()));
             SihicApp.s_producto.setPrecio2018(new BigDecimal(valor.trim()));
             
             importExcelClient.verificarproducto();
            importExcelClient.verificarpersona(documento.trim());
            if(SihicApp.genPacientesTemp==null)
            {
             SihicApp.genPersonas=null;
             SihicApp.genPersonas=new GenPersonas();
             SihicApp.genPersonas.setDocumento(documento.trim());
             SihicApp.genPersonas.setGenTiposDocumentos(SihicApp.getL_gentiposdocumentos().stream().filter(fi->fi.getDescripcion().trim().equals(tipodoc.trim())).collect(Collectors.toList()).get(0));
             SihicApp.genPersonas.setFechaNacimiento(sihic.util.UtilDate.stringtodate(fechanacimiento));
             if(sexom.trim().toUpperCase().equals("X"))
             {
                 sexom="M";
             }
             else
             {
                 sexom="F";
             }
             SihicApp.genPersonas.setGenSexo(SihicApp.getL_gensexo().stream().filter(fi->fi.getSigla().trim().equals(sexom)).collect(Collectors.toList()).get(0));
             SihicApp.genPersonas.setTelefono(telefono);
             SihicApp.genPersonas.setDireccion(direccion);
             SihicApp.genPersonas.setPrimerNombre(primernombre);
             SihicApp.genPersonas.setSegundoNombre(segundonombre);
             SihicApp.genPersonas.setPrimerApellido(primerapellido);
             SihicApp.genPersonas.setSegundoApellido(segundoapellido);
             SihicApp.genPersonas.setGen_zona_residencia(SihicApp.getL_genzonaresidencia().stream().filter(fi->fi.getCodigo().trim().equals("UR")).collect(Collectors.toList()).get(0));
             SihicApp.genPacientesTemp=null;
             SihicApp.genPacientesTemp=new GenPacientes();
             SihicApp.genPacientesTemp.setGenPersonas(SihicApp.genPersonas);
             try
             {
             SihicApp.genPacientesTemp.setGenEapb(SihicApp.l_eapb.stream().filter(fi->fi.getNombre().trim().equals(eps.trim())).collect(Collectors.toList()).get(0));
             }catch(Exception e)
             {
                SihicApp.genPacientesTemp.setGenEapb(null);  
             }
              try
             {
             SihicApp.genPacientesTemp.setGenTiposUsuarios(SihicApp.l_tiposusuarios.stream().filter(fi->fi.getNombre().trim().equals(tipousuario.trim())).collect(Collectors.toList()).get(0));
             }catch(Exception e)
             {
                SihicApp.genPacientesTemp.setGenEapb(null);  
             }
              SihicApp.genPacientesTemp.setGenTiposAfiliacion(SihicApp.l_tiposafiliacion.stream().filter(fi->fi.getCodigo().trim().equals("1")).collect(Collectors.toList()).get(0));
        }
            else
            {
                SihicApp.genPersonas=SihicApp.genPacientesTemp.getGenPersonas();
            }

        
        importExcelClient.verificarcomprobante(Long.valueOf(no_comprobante.trim()));
        if(SihicApp.conComprobanteProcedimiento==null)
        {
        SihicApp.agnCitasTemp=null;
        SihicApp.agnCitasTemp=new AgnCitas();
        SihicApp.agnCitasTemp.setFechaHora(sihic.util.UtilDate.stringtodate(fechacita));
        System.out.println("size medicos->"+SihicApp.l_medicos.size());
        SihicApp.agnCitasTemp.setAgnMedicos(SihicApp.l_medicos.stream().filter(fi->fi.getId()==74).collect(Collectors.toList()).get(0));
        if(especialidad.startsWith("RADIOGRA"))
        {
            especialidad="1";
        }
        else
        {
           if(especialidad.startsWith("XEROMAMOGRA"))
        {
            especialidad="3";
        }
           else
           {
            if(especialidad.startsWith("ECOGRA"))
           {
            especialidad="2";
           }
            else
            {
               especialidad="4";  
            }
           }
        }
        
        SihicApp.agnCitasTemp.setAgnConsultorios(SihicApp.getL_consultorios().stream().filter(fi->fi.getNumero()==Integer.valueOf(especialidad)).collect(Collectors.toList()).get(0));
      
          
        try
        {
        SihicApp.agnCitasTemp.setServicio(SihicApp.li_producto.stream().filter(fi->fi.getCodigo().trim().equals(codigocups.trim())).collect(Collectors.toList()).get(0));
        }catch(Exception e)
        {
                SihicApp.agnCitasTemp.setServicio(SihicApp.s_producto);
    
        }
        SihicApp.agnCitasTemp.setGenPacientes(SihicApp.genPacientesTemp);
        System.out.println("hora->"+horacita.substring(0, 2));
        System.out.println("minuto->"+horacita.substring(3, 5));
       
        System.out.println("horacita->"+horacita);
          System.out.println("reg->"+i);
        try
        {
        SihicApp.agnCitasTemp.setGenHorasCita(SihicApp.getGenhorascita().stream().filter(fi->fi.getHora()==Integer.valueOf(horacita.substring(0, 2)) && fi.getMinutos()==Integer.valueOf(horacita.substring(3, 4))).collect(Collectors.toList()).get(0));
        }catch(Exception e)
        {
          SihicApp.agnCitasTemp.setGenHorasCita(SihicApp.getGenhorascita().stream().filter(fi->fi.getHora()==Integer.valueOf(horacita.substring(0, 2))).collect(Collectors.toList()).get(0));
  
        }
         SihicApp.facturaItem=null;
        }
        else
        {
         importExcelClient.verificarfacturaitem(SihicApp.conComprobanteProcedimiento.getId(),codigocups);
         SihicApp.agnCitasTemp=SihicApp.facturaItem.getHclConsultas().getAgnCitas();
        }
        importExcelClient.verificarfactura(Long.valueOf(nofactura),prefijo.trim());
         System.out.println("facturaprefijo->"+prefijo+nofactura);
        if(SihicApp.s_factura==null)
        {
        SihicApp.s_factura=null;
        SihicApp.s_factura=new Factura();
        SihicApp.s_factura.setFacturaDate(sihic.util.UtilDate.stringtodate("01-12-2018"));
        SihicApp.s_factura.setFechavencimiento(sihic.util.UtilDate.stringtodate("30-12-2018"));
        SihicApp.s_factura.setPrefijo(prefijo.trim());
        SihicApp.s_factura.setNo_factura(Long.valueOf(nofactura));
          try
             {
             SihicApp.s_factura.setGenEapb(SihicApp.l_eapb.stream().filter(fi->fi.getNombre().trim().equals(eps.trim())).collect(Collectors.toList()).get(0));
             }catch(Exception e)
             {
                SihicApp.s_factura.setGenEapb(null);  
             }
              try
             {
             SihicApp.s_factura.setGenTiposUsuarios(SihicApp.l_tiposusuarios.stream().filter(fi->fi.getNombre().trim().equals(tipousuario.trim())).collect(Collectors.toList()).get(0));
             }catch(Exception e)
             {
                SihicApp.s_factura.setGenEapb(null);  
             }
               System.out.println("factura nueva guardada->"+nofactura);
        }      
        if(SihicApp.conComprobanteProcedimiento==null)
       {
        SihicApp.conComprobanteProcedimiento=null;
        SihicApp.conComprobanteProcedimiento=new ConComprobanteProcedimiento();
        ConsecutivoComprobanteProcedimiento cc=new ConsecutivoComprobanteProcedimiento();
        cc.setId(Long.valueOf(no_comprobante));
        SihicApp.conComprobanteProcedimiento.setFechaelaboracion(sihic.util.UtilDate.stringtodate(fechacita));
        SihicApp.conComprobanteProcedimiento.setConsecutivoComprobanteProcedimiento(cc);
       } 
        if(SihicApp.facturaItem==null)
        {    
             System.out.println("codigocups->"+codigocups);
             System.out.println("valor->"+valor);
             
        SihicApp.facturaItem=null;
        SihicApp.facturaItem=new FacturaItem();
        SihicApp.facturaItem.setFactura(SihicApp.s_factura);
        SihicApp.facturaItem.setConComprobanteProcedimiento(SihicApp.conComprobanteProcedimiento);
        SihicApp.facturaItem.setAgnCitas(SihicApp.agnCitasTemp);
        try
        {
        SihicApp.facturaItem.setProducto(SihicApp.li_producto.stream().filter(fi->fi.getCodigo().equals(codigocups)).collect(Collectors.toList()).get(0));
         }catch(Exception e)
        {
                SihicApp.facturaItem.setProducto(SihicApp.s_producto);
    
        }

        SihicApp.facturaItem.setValor_total(new BigDecimal(valor));
        try
        {
        SihicApp.facturaItem.setGenEapb(SihicApp.l_eapb.stream().filter(fi->fi.getNombre().trim().equals(eps.trim())).collect(Collectors.toList()).get(0));
        }catch(Exception e)
        {
          SihicApp.facturaItem.setGenEapb(null);  
        }
        SihicApp.facturaItem.setQuantity(1);
        SihicApp.facturaItem.setSucursales(SihicApp.li_Sucursales.stream().filter(fi->fi.getCodigo().trim().equals("1")).collect(Collectors.toList()).get(0));
        }
        else
        {
            SihicApp.facturaItem.setFactura(SihicApp.s_factura);
        SihicApp.facturaItem.setConComprobanteProcedimiento(SihicApp.conComprobanteProcedimiento);
        SihicApp.facturaItem.setAgnCitas(SihicApp.agnCitasTemp);
       try
        {
        SihicApp.facturaItem.setProducto(SihicApp.li_producto.stream().filter(fi->fi.getCodigo().equals(codigocups)).collect(Collectors.toList()).get(0));
         }catch(Exception e)
        {
                SihicApp.facturaItem.setProducto(SihicApp.s_producto);
    
        }
 SihicApp.facturaItem.setValor_total(new BigDecimal(valor));
        try
        {
        SihicApp.facturaItem.setGenEapb(SihicApp.l_eapb.stream().filter(fi->fi.getNombre().trim().equals(eps.trim())).collect(Collectors.toList()).get(0));
        }catch(Exception e)
        {
          SihicApp.facturaItem.setGenEapb(null);  
        }
        SihicApp.facturaItem.setQuantity(1);
        SihicApp.facturaItem.setSucursales(SihicApp.li_Sucursales.stream().filter(fi->fi.getCodigo().trim().equals("1")).collect(Collectors.toList()).get(0));

        }
        importExcelClient.savefactura();
        
        
}
        libro.close();
} 
public void importar_tarifas() throws IOException, WriteException, BiffException {
        WorkbookSettings wbSettings = new WorkbookSettings();
        wbSettings.setEncoding("ISO-8859-1");
        wbSettings.setLocale(new Locale("es", "ES"));
        wbSettings.setExcelDisplayLanguage("ES");
        wbSettings.setExcelRegionalSettings("ES");
        wbSettings.setCharacterSet(CountryCode.SPAIN.getValue());
        String rutaArchivo = "\\home\\adminlinux\\TARIFAS2020_SOAT.xls";
        jxl.write.Label label;
        jxl.write.Number number;
        File archivoXLS = new File(rutaArchivo);
        Workbook libro;
        libro = Workbook.getWorkbook(archivoXLS, wbSettings);
       
        AgnEspecialidades agnEspecialidades = new AgnEspecialidades();

        GenCategoriasProductos genCategoriasProductos = new GenCategoriasProductos();
//103 tomografias
//127 ecografías
//128 radiografías

//****villavo***********
// 71 radiología
//104 biopsias
//72 ecografias
//103 tomografias

//radiologia
 Sheet sheet = libro.getSheet(0);
        int f = 0;
        for (AgnEspecialidades ae : SihicApp.l_especialidades) {
            if (ae.getId().equals(Long.valueOf(71))) {
                agnEspecialidades = ae;
                System.out.println("Especialidad->"+ae.getNombre());
            }
        }
        for (GenCategoriasProductos cp : SihicApp.l_categoriasproductos) {
            if (cp.getId().equals(Long.valueOf(1))) {
                genCategoriasProductos = cp;
            }
        }
        for (int i = 2; i < 72; i++) {
           String codigo = sheet.getCell(0, i).getContents();
           String nombre = sheet.getCell(1, i).getContents();
           String precio2015 = sheet.getCell(2, i).getContents();
           String precio2016 = sheet.getCell(3, i).getContents();
           String precio2017 = sheet.getCell(4, i).getContents();
           String precio2018 = sheet.getCell(5, i).getContents();
           String precio2020 = sheet.getCell(6, i).getContents();

            SihicApp.s_producto = SihicApp.productoControllerClient.findByCodigoExactAllProductos(codigo);
            if (SihicApp.s_producto != null) {

                SihicApp.s_producto.setCodigo(codigo);
                SihicApp.s_producto.setNombre(nombre);
                SihicApp.s_producto.setAgnEspecialidades(agnEspecialidades);
                SihicApp.s_producto.setGenCategoriasProductos(genCategoriasProductos);
               
                try {
                    SihicApp.s_producto.setPrecio2015_soat(new BigDecimal(precio2015));
                    SihicApp.s_producto.setPrecio2016_soat(new BigDecimal(precio2016));
                    SihicApp.s_producto.setPrecio2017_soat(new BigDecimal(precio2017));
                    SihicApp.s_producto.setPrecio2018_soat(new BigDecimal(precio2018));
                    SihicApp.s_producto.setPrecio2020_soat(new BigDecimal(precio2020));
                } catch (Exception e) {

                }

                SihicApp.productoControllerClient.update();
            } else {
                SihicApp.s_producto = new Producto();
                SihicApp.s_producto.setCodigo(codigo);
                SihicApp.s_producto.setNombre(nombre);
               // SihicApp.s_producto.setAgnEspecialidades(agnEspecialidades);
                SihicApp.s_producto.setGenCategoriasProductos(genCategoriasProductos);

                try {
                  SihicApp.s_producto.setPrecio2015_soat(new BigDecimal(precio2015));
                    SihicApp.s_producto.setPrecio2016_soat(new BigDecimal(precio2016));
                    SihicApp.s_producto.setPrecio2017_soat(new BigDecimal(precio2017));
                    SihicApp.s_producto.setPrecio2018_soat(new BigDecimal(precio2018));
                    SihicApp.s_producto.setPrecio2020_soat(new BigDecimal(precio2020));
                } catch (Exception e) {

                }
                SihicApp.productoControllerClient.update();
            }
            Kardex kardex = null;
            kardex = new Kardex();
            kardex.setCantidad_entrada(1);
            kardex.setValor_entrada(SihicApp.s_producto.getPrecio2020());
            kardex.setValor_saldo(SihicApp.s_producto.getPrecio2020());
            kardex.setCantidad_saldo(1);
            kardex.setProducto(SihicApp.s_producto);
            SihicApp.s_kardex=kardex;
            SihicApp.kardexControllerClient.update();
            System.out.println("reg radio->" + i);
        }
        
        //radiografias
  /*sheet = libro.getSheet(1);
         f = 0;
        for (AgnEspecialidades ae : SihicApp.l_especialidades) {
            if (ae.getId().equals(Long.valueOf(128))) {
                agnEspecialidades = ae;
                System.out.println("Especialidad->"+ae.getNombre());
            }
        }
        for (GenCategoriasProductos cp : SihicApp.l_categoriasproductos) {
            if (cp.getId().equals(Long.valueOf(1))) {
                genCategoriasProductos = cp;
            }
        }
        for (int i = 2; i < 74; i++) {
            String codigo = sheet.getCell(0 , i).getContents();
            String nombre = sheet.getCell(1, i).getContents();
          //  String precio2015 = sheet.getCell(2, i).getContents();
           // String precio2016 = sheet.getCell(2, i).getContents();
           // String precio2017 = sheet.getCell(2, i).getContents();
            String precio2020 = sheet.getCell(2, i).getContents();

            SihicApp.s_producto = SihicApp.productoControllerClient.findByCodigoExactAllProductos(codigo);
            if (SihicApp.s_producto != null) {

                SihicApp.s_producto.setCodigo(codigo);
                SihicApp.s_producto.setNombre(nombre);
                SihicApp.s_producto.setAgnEspecialidades(agnEspecialidades);
                SihicApp.s_producto.setGenCategoriasProductos(genCategoriasProductos);
               
                try {
                    SihicApp.s_producto.setPrecio2020(new BigDecimal(precio2020));
                } catch (Exception e) {

                }

                SihicApp.productoControllerClient.update();
            } else {
                SihicApp.s_producto = new Producto();
                SihicApp.s_producto.setCodigo(codigo);
                SihicApp.s_producto.setNombre(nombre);
               // SihicApp.s_producto.setAgnEspecialidades(agnEspecialidades);
                SihicApp.s_producto.setGenCategoriasProductos(genCategoriasProductos);

                try {
                    SihicApp.s_producto.setPrecio2020(new BigDecimal(precio2020));
                } catch (Exception e) {

                }
                SihicApp.productoControllerClient.update();
            }
            Kardex kardex = null;
            kardex = new Kardex();
            kardex.setCantidad_entrada(1);
            kardex.setValor_entrada(SihicApp.s_producto.getPrecio2020());
            kardex.setValor_saldo(SihicApp.s_producto.getPrecio2020());
            kardex.setCantidad_saldo(1);
            kardex.setProducto(SihicApp.s_producto);
            SihicApp.s_kardex=kardex;
            SihicApp.kardexControllerClient.update();
            System.out.println("reg->" + i);
            
            
        }*/
 //ecografias
  sheet = libro.getSheet(1);
         f = 0;
        for (AgnEspecialidades ae : SihicApp.l_especialidades) {
            if (ae.getId().equals(Long.valueOf(72))) {
                agnEspecialidades = ae;
                System.out.println("Especialidad->"+ae.getNombre());
            }
        }
        for (GenCategoriasProductos cp : SihicApp.l_categoriasproductos) {
            if (cp.getId().equals(Long.valueOf(1))) {
                genCategoriasProductos = cp;
            }
        }
        for (int i = 3; i < 73; i++) {
            String codigo = sheet.getCell(0 , i).getContents();
            String nombre = sheet.getCell(1, i).getContents();
            String precio2015 = sheet.getCell(2, i).getContents();
            String precio2016 = sheet.getCell(3, i).getContents();
            String precio2017 = sheet.getCell(4, i).getContents();
            String precio2018 = sheet.getCell(5, i).getContents();
            String precio2020 = sheet.getCell(6, i).getContents();
            SihicApp.s_producto.setPrecio2015_soat(new BigDecimal(precio2015));
            SihicApp.s_producto.setPrecio2016_soat(new BigDecimal(precio2016));
            SihicApp.s_producto.setPrecio2017_soat(new BigDecimal(precio2017));
            SihicApp.s_producto.setPrecio2018_soat(new BigDecimal(precio2018));
            SihicApp.s_producto.setPrecio2020_soat(new BigDecimal(precio2020));

            SihicApp.s_producto = SihicApp.productoControllerClient.findByCodigoExactAllProductos(codigo);
            if (SihicApp.s_producto != null) {

                SihicApp.s_producto.setCodigo(codigo);
                SihicApp.s_producto.setNombre(nombre);
                SihicApp.s_producto.setAgnEspecialidades(agnEspecialidades);
                SihicApp.s_producto.setGenCategoriasProductos(genCategoriasProductos);
               
                try {
                      SihicApp.s_producto.setPrecio2015_soat(new BigDecimal(precio2015));
            SihicApp.s_producto.setPrecio2016_soat(new BigDecimal(precio2016));
            SihicApp.s_producto.setPrecio2017_soat(new BigDecimal(precio2017));
            SihicApp.s_producto.setPrecio2018_soat(new BigDecimal(precio2018));
            SihicApp.s_producto.setPrecio2020_soat(new BigDecimal(precio2020));

                } catch (Exception e) {

                }

                SihicApp.productoControllerClient.update();
            } else {
                SihicApp.s_producto = new Producto();
                SihicApp.s_producto.setCodigo(codigo);
                SihicApp.s_producto.setNombre(nombre);
               // SihicApp.s_producto.setAgnEspecialidades(agnEspecialidades);
                SihicApp.s_producto.setGenCategoriasProductos(genCategoriasProductos);

                try {
                    SihicApp.s_producto.setPrecio2020(new BigDecimal(precio2020));
                } catch (Exception e) {

                }
                SihicApp.productoControllerClient.update();
            }
            Kardex kardex = null;
            kardex = new Kardex();
            kardex.setCantidad_entrada(1);
            kardex.setValor_entrada(SihicApp.s_producto.getPrecio2020());
            kardex.setValor_saldo(SihicApp.s_producto.getPrecio2020());
            kardex.setCantidad_saldo(1);
            kardex.setProducto(SihicApp.s_producto);
            SihicApp.s_kardex=kardex;
            SihicApp.kardexControllerClient.update();
            System.out.println("reg eco->" + i);
            
            
        }

        libro.close();

    }
}
