/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.util;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.util.ArrayList;
import isoftconta.servicios.DocumentoSoporteController;
import modelo.BalancePrueba;
import modelo.EntidadesStatic;
import modelo.LibroAuxiliar;
import modelo.LibroMayorBalances;

/**
 *
 * @author adminlinux
 */
public class ImpresionDocumentoSoporte {
     public static void fpdfdocumentosoporte() throws IOException, BadElementException, DocumentException {
        Document document = new Document();
        Font fuente = new Font();
        document = new Document(PageSize.LETTER, 20f, 20f, 20f, 20f);
        String nombrearchivo=(DocumentoSoporteController.getNombredocumentosoporte(EntidadesStatic.es_documentosoporte.getTipodocsoporte())[0]);
        PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/isoftconta/"+nombrearchivo.replace(" ","").trim()+".pdf"));
        document.open();
        //PdfPTable table = new PdfPTable(new float[] { 5, 5});

        
        PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
        PdfPTable tableCliente = new PdfPTable(new float[]{10});
        PdfPTable tabledetalles = new PdfPTable(new float[]{4,2,8,3,3});
        tabledetalles.setWidthPercentage(100f);
        Image imagen;
       
        imagen = Image.getInstance("resources/logo.png");

        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setFixedHeight(40f);

        tableImagenTexto.addCell(imagen);

        tableImagenTexto.getDefaultCell().setFixedHeight(0f);
        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        fuente = FontFactory.getFont("Times-Roman", 14, Font.BOLD);
        tableImagenTexto.addCell(new Paragraph("CENTRO MÉDICO DEL GUAVIARE SAS", fuente));
        fuente = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
        tableImagenTexto.getDefaultCell().setColspan(2);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableImagenTexto.addCell(new Paragraph("Nit.: 900.767.863 ", fuente));
        tableImagenTexto.addCell(new Paragraph("CRA 22 N° 11-18 B. LA ESPERANZA TEL. 3134212162 ", fuente));
        tableImagenTexto.addCell(new Paragraph("SAN JOSE DEL GUAVIARE", fuente));
        
        document.add(tableImagenTexto);

        
        tabledetalles.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        tabledetalles.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tabledetalles.getDefaultCell().setColspan(1);
      
        fuente = FontFactory.getFont("Times-Roman", 9, Font.BOLD);
        tabledetalles.getDefaultCell().setBorder(0);
        tabledetalles.addCell(new Paragraph(nombrearchivo+" No. " + EntidadesStatic.es_documentosoporte.getNocomprobantecerosizquierda(), fuente));
        tabledetalles.getDefaultCell().setColspan(2);
        tabledetalles.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        fuente = FontFactory.getFont("Times-Roman", 7, Font.NORMAL);
        tabledetalles.addCell(new Paragraph("Fecha: " + DateFormat.getDateInstance().format(EntidadesStatic.es_documentosoporte.getFechaelaboracion()), fuente));
        tabledetalles.getDefaultCell().setColspan(2);
      
        
        tabledetalles.addCell(new Paragraph("Concepto: " + EntidadesStatic.es_documentosoporte.getConcepto(), fuente));
        tabledetalles.getDefaultCell().setColspan(1);
       
        
        
        tabledetalles.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tabledetalles.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        tabledetalles.addCell(new Paragraph("Tercero", fuente));
        tabledetalles.addCell(new Paragraph("Codigo", fuente));
        tabledetalles.addCell(new Paragraph("Cuenta", fuente));
        tabledetalles.addCell(new Paragraph("Debe", fuente));
        tabledetalles.addCell(new Paragraph("Haber", fuente));
       
       // tabledetalles.getDefaultCell().setBorder(0);
        //tableFacturaItems.getDefaultCell().setBorderWidthTop(1);
       
        for(LibroAuxiliar dc: EntidadesStatic.es_documentosoporte.getLi_libroauxiliar())
        {
        tabledetalles.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        //tabledetalles.getDefaultCell().setBorderWidthLeft((float) 0.25);
       // tabledetalles.getDefaultCell().setBorderWidthRight((float) 0.25);
       // tabledetalles.getDefaultCell().setBorderWidthBottom((float) 0.25);
        try
        {
        tabledetalles.addCell(new Paragraph(EntidadesStatic.es_documentosoporte.getClientes().getNo_ident()+"||"+EntidadesStatic.es_documentosoporte.getClientes().getNombres(), fuente));
        }
        catch(Exception e)
        {
             tabledetalles.addCell(new Paragraph(EntidadesStatic.es_documentosoporte.getDocumentoSoporte().getClientes().getNo_ident()+"||"+EntidadesStatic.es_documentosoporte.getDocumentoSoporte().getClientes().getNombres(), fuente));
      
        }
        tabledetalles.addCell(new Paragraph(dc.getConPuc().getCodigo(), fuente));
        tabledetalles.addCell(new Paragraph(dc.getConPuc().getNombre(), fuente));
        tabledetalles.addCell(new Paragraph(dc.getDebe().toString(), fuente));
        tabledetalles.addCell(new Paragraph(dc.getHaber().toString(), fuente));
        tabledetalles.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        }
        document.add(tabledetalles);
        document.add(new Paragraph("\n\n", fuente));
        document.add(new Paragraph("\n\n", fuente));
        document.add(new Paragraph("                                   Elaborado por: _____________________  Aprobado por: ____________________\"", fuente));
      
        
        // step 5
        document.close();
File file = new File("/home/adminlinux/isoftconta/"+nombrearchivo.replace(" ","").trim()+".pdf");
String os = System.getProperty("os.name"); 
      if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
 { 
     String rutaarchivo="/usr/bin/evince /home/adminlinux/isoftconta/"+nombrearchivo.replace(" ","").trim()+".pdf";
     System.out.println(rutaarchivo);
  Runtime r = Runtime.getRuntime(); 
  Process p = r.exec(rutaarchivo); 
  }
else
 {
  
    Desktop.getDesktop().open(file);
 
 }

    }
     public static void fpdfdocumentosoportecontercero() throws IOException, BadElementException, DocumentException {
        Document document = new Document();
        Font fuente = new Font();
        document = new Document(PageSize.LETTER, 20f, 20f, 20f, 20f);
        String nombrearchivo=(DocumentoSoporteController.getNombredocumentosoporte(EntidadesStatic.es_documentosoporte.getTipodocsoporte())[0]);
        PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/isoftconta/"+nombrearchivo.replace(" ","").trim()+".pdf"));
        document.open();
        PdfPTable tableMaster = new PdfPTable(1);
        //PdfPTable table = new PdfPTable(new float[] { 5, 5});

        PdfPTable table = new PdfPTable(new float[]{10});
        PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
        PdfPTable tableCliente = new PdfPTable(new float[]{10});
        PdfPTable tabledetalles = new PdfPTable(new float[]{4,2,8,3,3});
        tabledetalles.setWidthPercentage(100f);
        Image imagen;
        tableMaster.getDefaultCell().setBorderWidth(0);

        imagen = Image.getInstance("resources/logo.png");
       
        
        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setFixedHeight(40f);

        tableImagenTexto.addCell(imagen);

        tableImagenTexto.getDefaultCell().setFixedHeight(0f);
        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
 
        fuente = FontFactory.getFont("Times-Roman", 14, Font.BOLD);
        tableImagenTexto.addCell(new Paragraph("CENTRO MÉDICO DEL GUAVIARE SAS", fuente));
        fuente = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
        tableImagenTexto.getDefaultCell().setColspan(2);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableImagenTexto.addCell(new Paragraph("Nit.: 900.767.863 ", fuente));
        tableImagenTexto.addCell(new Paragraph("CRA 22 N° 11-18 B. LA ESPERANZA TEL. 3134212162 ", fuente));
        tableImagenTexto.addCell(new Paragraph("SAN JOSE DEL GUAVIARE", fuente));
        fuente = FontFactory.getFont("Times-Roman", 9, Font.BOLD);
        tableImagenTexto.addCell(new Paragraph(nombrearchivo+" No. " + EntidadesStatic.es_documentosoporte.getNocomprobantecerosizquierda(), fuente));
        
        tableCliente.getDefaultCell().setBorderWidthBottom(0);
        tableCliente.getDefaultCell().setBorderWidthTop(0);
        tableCliente.getDefaultCell().setBorderWidthLeft(0);
        tableCliente.getDefaultCell().setBorderWidthRight(0);
       
        tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        fuente = FontFactory.getFont("Times-Roman", 9, Font.NORMAL);
        tableCliente.addCell(new Paragraph("Fecha: " + DateFormat.getDateInstance().format(EntidadesStatic.es_documentosoporte.getFechaelaboracion()), fuente));
        tableCliente.addCell(new Paragraph("Concepto: " + EntidadesStatic.es_documentosoporte.getConcepto(), fuente));
        
        tabledetalles.getDefaultCell().setColspan(5);
        tabledetalles.addCell(tableImagenTexto);
        tabledetalles.addCell(tableCliente);
          tabledetalles.getDefaultCell().setColspan(1);
        tabledetalles.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tabledetalles.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        tabledetalles.setWidthPercentage(100f);
         Paragraph paragraphTable1 = new Paragraph(); 
        tabledetalles.addCell(new Paragraph("Tercero", fuente));
        tabledetalles.addCell(new Paragraph("Codigo", fuente));
        tabledetalles.addCell(new Paragraph("Cuenta", fuente));
        tabledetalles.addCell(new Paragraph("Debe", fuente));
        tabledetalles.addCell(new Paragraph("Haber", fuente));
       
       
        for(LibroAuxiliar dc: EntidadesStatic.es_documentosoporte.getLi_libroauxiliar())
        {
         try
         {
        tabledetalles.addCell(new Paragraph(dc.getTerceros().getNo_ident()+"||"+dc.getTerceros().getNombres(), fuente));
         }catch(Exception e)
         {
               tabledetalles.addCell(new Paragraph(dc.getDocumentoSoporte().getClientes().getNo_ident()+"||"+dc.getDocumentoSoporte().getClientes().getNombres(), fuente));
      
         }
        tabledetalles.addCell(new Paragraph(dc.getConPuc().getCodigo(), fuente));
        tabledetalles.addCell(new Paragraph(dc.getConPuc().getNombre(), fuente));
        tabledetalles.addCell(new Paragraph(dc.getDebe().toString(), fuente));
        tabledetalles.addCell(new Paragraph(dc.getHaber().toString(), fuente));
        }
        
        tabledetalles.getDefaultCell().setColspan(5);
        tabledetalles.getDefaultCell().setBorder(0);
        tabledetalles.addCell(new Paragraph("\n\n", fuente));
        tabledetalles.addCell(new Paragraph("\n\n", fuente));
        tabledetalles.addCell(new Paragraph("                                    Elaborado por: _____________________  Aprobado por: ____________________", fuente));
       
        paragraphTable1.add(tabledetalles);
        
       
        document.add(paragraphTable1);
        // step 5
        document.close();
File file = new File("/home/adminlinux/isoftconta/"+nombrearchivo.replace(" ","").trim()+".pdf");
String os = System.getProperty("os.name"); 
      if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
 { 
     String rutaarchivo="/usr/bin/evince /home/adminlinux/isoftconta/"+nombrearchivo.replace(" ","").trim()+".pdf";
     System.out.println(rutaarchivo);
  Runtime r = Runtime.getRuntime(); 
  Process p = r.exec(rutaarchivo); 
  }
else
 {
  
    Desktop.getDesktop().open(file);
 
 }

    }
     public static void crearbalancedeprueba()
     {
         if( EntidadesStatic.li_balaBalancePruebas==null)
         {
              EntidadesStatic.li_balaBalancePruebas=new ArrayList<>();
         }
       EntidadesStatic.li_balaBalancePruebas.clear();
       for(LibroMayorBalances lb: EntidadesStatic.li_libromayorbalances)
       {
            if(lb.getConPuc().getCodigo().substring(0,1).equals("1") || lb.getConPuc().getCodigo().substring(0,1).equals("5")||lb.getConPuc().getCodigo().substring(0,1).equals("6"))
           {
               EntidadesStatic.balancePrueba=null;
                EntidadesStatic.balancePrueba=new BalancePrueba(lb.getConPuc().getCodigo(), lb.getConPuc().getNombre(), lb.getSaldodebito().compareTo(BigDecimal.ZERO)==1?lb.getSaldodebito():lb.getSaldocredito(),BigDecimal.ZERO);
          
            } 
           else
           {
               if(lb.getConPuc().getCodigo().substring(0,1).equals("2") || lb.getConPuc().getCodigo().substring(0,1).equals("3")||lb.getConPuc().getCodigo().substring(0,1).equals("4"))
           {
                EntidadesStatic.balancePrueba=null;
                EntidadesStatic.balancePrueba=new BalancePrueba(lb.getConPuc().getCodigo(), lb.getConPuc().getNombre(), BigDecimal.ZERO, lb.getSaldodebito().compareTo(BigDecimal.ZERO)==1?lb.getSaldodebito():lb.getSaldocredito());
          
           } 
               
           }
           EntidadesStatic.li_balaBalancePruebas.add( EntidadesStatic.balancePrueba);
       }
 }        
   public static void fpdf_estadosituacionfinanciera() throws IOException, BadElementException, DocumentException, URISyntaxException {
        String especialidad = "";
        BigDecimal total = BigDecimal.ZERO;
        
        Document document = new Document();
        Font fuente = new Font();
       
        document = new Document(PageSize.LETTER, 2, 2, 2, 2);
        PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/isoftconta/esf.pdf"));
        document.open();

        PdfPTable tableMaster = new PdfPTable(1);
        //PdfPTable table = new PdfPTable(new float[] { 5, 5});

        PdfPTable table = new PdfPTable(new float[]{10});
        PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
        PdfPTable tableesf = new PdfPTable(new float[]{7, 3});
        Image imagen;
        tableMaster.getDefaultCell().setBorderWidth(0);
       imagen = Image.getInstance("resources/logo.png");
        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setFixedHeight(40f);
        tableImagenTexto.addCell(imagen);
        tableImagenTexto.getDefaultCell().setFixedHeight(0f);
        table.setWidthPercentage(100f);
        tableesf.setWidthPercentage(100f);
        tableMaster.setWidthPercentage(100f);
        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        fuente = FontFactory.getFont("arial", 14, Font.BOLD);
         tableImagenTexto.addCell(new Paragraph("CENTRO MÉDICO DEL GUAVIARE SAS", fuente));
        fuente = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
        tableImagenTexto.getDefaultCell().setColspan(2);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableImagenTexto.addCell(new Paragraph("Nit.: 900.767.863 ", fuente));
        tableImagenTexto.addCell(new Paragraph("CRA 22 N° 11-18 B. LA ESPERANZA TEL. 3134212162 ", fuente));
        tableImagenTexto.addCell(new Paragraph("SAN JOSE DEL GUAVIARE", fuente));
         table.getDefaultCell().setPadding(10);
        table.setWidthPercentage(100f);
        table.addCell(tableImagenTexto);
        //tabla datos de cliente
        table.getDefaultCell().setPadding(7);
        tableesf.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        tableesf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableesf.getDefaultCell().setColspan(2);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        tableesf.getDefaultCell().setColspan(2);
        fuente = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
        tableesf.getDefaultCell().setBorder(0);
        tableesf.addCell(new Paragraph("Estado de Situación Financiera al "+isoftconta.util.UtilDate.calculardiasdelmes(EntidadesStatic.li_libromayorbalances.get(0).getAño(), EntidadesStatic.li_libromayorbalances.get(0).getMes()-1)+" de "+isoftconta.util.UtilDate.obtenerMes(EntidadesStatic.li_libromayorbalances.get(0).getAño(), EntidadesStatic.li_libromayorbalances.get(0).getMes())+" de "+EntidadesStatic.li_libromayorbalances.get(0).getAño(), fuente));
       fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
         tableesf.addCell(new Paragraph("Bajo NIIF para Pymes", fuente));
       tableesf.addCell(new Paragraph("(En Pesos Colombianos $COP)", fuente));
        tableesf.getDefaultCell().setColspan(1);
        tableesf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
       
        tableesf.addCell(new Paragraph("Activos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph(String.valueOf(EntidadesStatic.li_libromayorbalances.get(0).getAño()),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Activos Corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("",fuente));
        tableesf.addCell(new Paragraph("Efectivo y equivalentes al efectivo", fuente));
      
        Double efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("11") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        Double efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("11") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        BigDecimal tot_activoscorrientes=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot_activoscorrientes = tot_activoscorrientes.setScale(2, RoundingMode.HALF_UP);
        System.out.println("ed->"+efectivodebito);
        System.out.println("ec->"+efectivocredito);
        System.out.println("tot->"+tot_activoscorrientes);
        tableesf.addCell(new Paragraph(String.valueOf((tot_activoscorrientes)),fuente));
        
        tableesf.addCell(new Paragraph("Cuentas comerciales por cobrar y otras cuentas por cobrar corrientes", fuente));
       
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("13") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("13") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot_activoscorrientes=tot_activoscorrientes.add(BigDecimal.valueOf(efectivodebito+efectivocredito));
        tot_activoscorrientes = tot_activoscorrientes.setScale(2, RoundingMode.HALF_UP);
        System.out.println("ed->"+efectivodebito);
        System.out.println("ec->"+efectivocredito);
        System.out.println("tot->"+tot_activoscorrientes);
        tableesf.addCell(new Paragraph(String.valueOf((BigDecimal.valueOf(efectivodebito+efectivocredito).setScale(2, RoundingMode.HALF_UP))),fuente));
        tableesf.addCell(new Paragraph("Otros activos financieros corrientes", fuente));
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("14") || lb.getConPuc().getCodigo().substring(0,2).equals("12") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("14")|| lb.getConPuc().getCodigo().substring(0,2).equals("12") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot_activoscorrientes=tot_activoscorrientes.add(BigDecimal.valueOf(efectivodebito+efectivocredito));
        tot_activoscorrientes = tot_activoscorrientes.setScale(2, RoundingMode.HALF_UP);
         System.out.println("ed->"+efectivodebito);
        System.out.println("ec->"+efectivocredito);
        System.out.println("tot->"+tot_activoscorrientes);
        tableesf.addCell(new Paragraph(String.valueOf(BigDecimal.valueOf(efectivodebito+efectivocredito).setScale(2, RoundingMode.HALF_UP)),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total Activos Corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        //efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("14") || lb.getConPuc().getCodigo().substring(0,2).equals("12") || lb.getConPuc().getCodigo().substring(0,2).equals("11") || lb.getConPuc().getCodigo().substring(0,2).equals("13") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        //efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("14")|| lb.getConPuc().getCodigo().substring(0,2).equals("12") || lb.getConPuc().getCodigo().substring(0,2).equals("11") || lb.getConPuc().getCodigo().substring(0,2).equals("13") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
       // tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        //tot = tot.setScale(2, RoundingMode.HALF_UP);
        System.out.println("ed->"+efectivodebito);
        System.out.println("ec->"+efectivocredito);
        System.out.println("tot->"+tot_activoscorrientes);
        tableesf.addCell(new Paragraph(String.valueOf(tot_activoscorrientes),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Activos no corrientes", fuente));
        tableesf.addCell(new Paragraph("", fuente));
         fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
         tableesf.addCell(new Paragraph("Propiedades, planta y equipo", fuente));
         efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        BigDecimal tot_activosnocorrientes=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot_activosnocorrientes = tot_activosnocorrientes.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(BigDecimal.valueOf(efectivodebito+efectivocredito).setScale(2, RoundingMode.HALF_UP)),fuente));
         tableesf.addCell(new Paragraph("Activos intangibles distintos de la plusvalía", fuente));
         efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("17") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("17") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot_activosnocorrientes=tot_activosnocorrientes.add(BigDecimal.valueOf(efectivodebito+efectivocredito));
        tot_activosnocorrientes = tot_activosnocorrientes.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(BigDecimal.valueOf(efectivodebito+efectivocredito).setScale(2, RoundingMode.HALF_UP)),fuente));
         tableesf.addCell(new Paragraph("Otros activos financieros no corrientes", fuente));
         efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("18") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("18") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot_activosnocorrientes=tot_activosnocorrientes.add(BigDecimal.valueOf(efectivodebito+efectivocredito));
        tot_activosnocorrientes = tot_activosnocorrientes.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(BigDecimal.valueOf(efectivodebito+efectivocredito).setScale(2, RoundingMode.HALF_UP)),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total Activos No Corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
      //  efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15") || lb.getConPuc().getCodigo().substring(0,2).equals("17") || lb.getConPuc().getCodigo().substring(0,2).equals("18") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
      //  efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15")|| lb.getConPuc().getCodigo().substring(0,2).equals("17") || lb.getConPuc().getCodigo().substring(0,2).equals("18") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot_activosnocorrientes=tot_activosnocorrientes.add(BigDecimal.valueOf(efectivodebito+efectivocredito));
        tot_activosnocorrientes = tot_activosnocorrientes.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot_activosnocorrientes),fuente));
        tableesf.addCell(new Paragraph("Total Activos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
       // efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15") || lb.getConPuc().getCodigo().substring(0,2).equals("17") || lb.getConPuc().getCodigo().substring(0,2).equals("18") || lb.getConPuc().getCodigo().substring(0,2).equals("11") || lb.getConPuc().getCodigo().substring(0,2).equals("12") || lb.getConPuc().getCodigo().substring(0,2).equals("13") || lb.getConPuc().getCodigo().substring(0,2).equals("14") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
       // efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15")|| lb.getConPuc().getCodigo().substring(0,2).equals("17") || lb.getConPuc().getCodigo().substring(0,2).equals("18") || lb.getConPuc().getCodigo().substring(0,2).equals("12") || lb.getConPuc().getCodigo().substring(0,2).equals("13") || lb.getConPuc().getCodigo().substring(0,2).equals("14") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
       // tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
       // tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot_activosnocorrientes.add(tot_activoscorrientes)),fuente));
        tableesf.getDefaultCell().setColspan(2);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0.25);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0.25);
        tableesf.addCell(new Paragraph("",fuente));
       
        tableesf.getDefaultCell().setBorderWidth((float) 0);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0);
        //*************************pasivos******************************
        tableesf.getDefaultCell().setColspan(1);
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
         tableesf.addCell(new Paragraph("Patrimonio y pasivos ", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("",fuente));
        tableesf.addCell(new Paragraph("Pasivos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("",fuente));
        tableesf.addCell(new Paragraph("Pasivos corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("",fuente));
        tableesf.addCell(new Paragraph("Provisiones corrientes por beneficios a los empleados", fuente));
      
         efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("23") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
         efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("23") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        BigDecimal tot_pasivoscorrientes=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot_pasivoscorrientes = tot_pasivoscorrientes.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf((BigDecimal.valueOf(efectivodebito+efectivocredito).setScale(2, RoundingMode.HALF_UP))),fuente));
        
        tableesf.addCell(new Paragraph("Cuentas por pagar comerciales y otras cuentas por pagar", fuente));
       
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot_pasivoscorrientes=tot_pasivoscorrientes.add(BigDecimal.valueOf(efectivodebito+efectivocredito));
        tot_pasivoscorrientes = tot_pasivoscorrientes.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf((BigDecimal.valueOf(efectivodebito+efectivocredito).setScale(2, RoundingMode.HALF_UP))),fuente));
        
        tableesf.addCell(new Paragraph("Pasivos por impuestos corrientes, corriente", fuente));
       
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("22") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("22") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot_pasivoscorrientes=tot_pasivoscorrientes.add(BigDecimal.valueOf(efectivodebito+efectivocredito));
        tot_pasivoscorrientes = tot_pasivoscorrientes.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(BigDecimal.valueOf(efectivodebito+efectivocredito).setScale(2, RoundingMode.HALF_UP)),fuente));
         tableesf.addCell(new Paragraph("Otros pasivos financieros corrientes", fuente));
       
        efectivodebito=0.0;//EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("22") ).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=0.0;//=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("22")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot_pasivoscorrientes=tot_pasivoscorrientes.add(BigDecimal.valueOf(efectivodebito+efectivocredito));
        tot_pasivoscorrientes = tot_pasivoscorrientes.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(BigDecimal.valueOf(efectivodebito+efectivocredito).setScale(2, RoundingMode.HALF_UP)),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total pasivos Corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
       // efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21") || lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
       // efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21")|| lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        //tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
      //  tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot_pasivoscorrientes),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Pasivos no corrientes", fuente));
         tableesf.addCell(new Paragraph("", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("Otros pasivos financieros no corrientes", fuente));
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("24") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("24") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        BigDecimal tot_pasivosnocorrientes=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot_pasivosnocorrientes = tot_pasivosnocorrientes.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(BigDecimal.valueOf(efectivodebito+efectivocredito).setScale(2, RoundingMode.HALF_UP)),fuente));
         tableesf.addCell(new Paragraph("Otros pasivos no financieros no corrientes", fuente));
         efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("25") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("25") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot_pasivosnocorrientes=tot_pasivosnocorrientes.add(BigDecimal.valueOf(efectivodebito+efectivocredito));
        tot_pasivosnocorrientes = tot_pasivosnocorrientes.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(BigDecimal.valueOf(efectivodebito+efectivocredito).setScale(2, RoundingMode.HALF_UP)),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total de pasivos no corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
       // efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
      //  efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("24")|| lb.getConPuc().getCodigo().substring(0,2).equals("25") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
      //  tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
       // tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot_pasivosnocorrientes),fuente));
        tableesf.addCell(new Paragraph("Total Pasivos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
       // efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21") || lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") || lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") || lb.getConPuc().getCodigo().substring(0,2).equals("26") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
      //  efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21")|| lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") || lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") || lb.getConPuc().getCodigo().substring(0,2).equals("26") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
      //  tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
     //   tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot_pasivosnocorrientes.add(tot_pasivoscorrientes)),fuente));
        
        
        tableesf.getDefaultCell().setColspan(2);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0.25);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0.25);
        tableesf.addCell(new Paragraph("",fuente));
        
        //*************************************************************************************************************************
     
        tableesf.getDefaultCell().setBorderWidth((float) 0);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0);
        
        
        //***************************************************************
          tableesf.getDefaultCell().setColspan(1);
             fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Patrimonio", fuente));
         tableesf.addCell(new Paragraph("", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("Capital emitido", fuente));
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        BigDecimal tot_patrimonio=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot_patrimonio = tot_patrimonio.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(BigDecimal.valueOf(efectivodebito+efectivocredito).setScale(2, RoundingMode.HALF_UP)),fuente));
        tableesf.addCell(new Paragraph("Otras reservas", fuente));
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("33") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("33") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot_patrimonio=tot_patrimonio.add(BigDecimal.valueOf(efectivodebito+efectivocredito));
        tot_patrimonio = tot_patrimonio.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(BigDecimal.valueOf(efectivodebito+efectivocredito).setScale(2, RoundingMode.HALF_UP)),fuente));
        tableesf.addCell(new Paragraph("Superavit por revaluación", fuente));
         efectivodebito=0.0;//EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("25")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=0.0;//EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("25")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot_patrimonio=tot_patrimonio.add(BigDecimal.valueOf(efectivodebito+efectivocredito));
        tot_patrimonio = tot_patrimonio.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(BigDecimal.valueOf(efectivodebito+efectivocredito).setScale(2, RoundingMode.HALF_UP)),fuente));
        tableesf.addCell(new Paragraph("Ganancias acumuladas", fuente));
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("35") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("35") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot_patrimonio=tot_patrimonio.add(BigDecimal.valueOf(efectivodebito+efectivocredito));
        tot_patrimonio = tot_patrimonio.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(BigDecimal.valueOf(efectivodebito+efectivocredito).setScale(2, RoundingMode.HALF_UP)),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total patrimonio", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
       // efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31") || lb.getConPuc().getCodigo().substring(0,2).equals("33") || lb.getConPuc().getCodigo().substring(0,2).equals("35") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
      //  efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31")|| lb.getConPuc().getCodigo().substring(0,2).equals("33")|| lb.getConPuc().getCodigo().substring(0,2).equals("35") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
      //  tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
      //  tot = tot.setScale(2, RoundingMode.HALF_UP);
          tableesf.addCell(new Paragraph(String.valueOf(tot_patrimonio),fuente));
     
          //*************************************************************
            //*************************************************************************************************************************
          tableesf.getDefaultCell().setColspan(2);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0.25);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0.25);
        tableesf.addCell(new Paragraph("",fuente));
          
        tableesf.getDefaultCell().setBorderWidth((float) 0);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0);
          tableesf.getDefaultCell().setColspan(1);
        tableesf.addCell(new Paragraph("Total patrimonio y pasivos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
       // efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31") || lb.getConPuc().getCodigo().substring(0,2).equals("33") || lb.getConPuc().getCodigo().substring(0,2).equals("35")|| lb.getConPuc().getCodigo().substring(0,2).equals("21") || lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") || lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") || lb.getConPuc().getCodigo().substring(0,2).equals("26") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
      //  efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31")|| lb.getConPuc().getCodigo().substring(0,2).equals("33")|| lb.getConPuc().getCodigo().substring(0,2).equals("35")|| lb.getConPuc().getCodigo().substring(0,2).equals("21") || lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") || lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") || lb.getConPuc().getCodigo().substring(0,2).equals("26") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
      //  tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
       // tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot_patrimonio.add(tot_pasivoscorrientes).add(tot_pasivosnocorrientes)),fuente));
   
        fuente = FontFactory.getFont("Times-Roman", 8, Font.NORMAL);
        tableesf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        tableesf.getDefaultCell().setPadding(5);
        tableesf.getDefaultCell().setPadding(10);
        tableesf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        fuente = FontFactory.getFont("Times-Roman", 9, Font.BOLD);
        tableesf.addCell(new Paragraph("Firma quien Aprueba: ________________________", fuente));
        tableesf.addCell(new Paragraph("Validez de este documento 30 días", fuente));
        table.addCell(tableesf);
        tableMaster.addCell(table);
        document.add(tableMaster);

        // step 5
        document.close();
         File file = new File("/home/adminlinux/isoftconta/esf.pdf");
try { 
 
 String os = System.getProperty("os.name"); 
 System.out.println("os->"+os);
 if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
 { 
  Runtime r = Runtime.getRuntime(); 
  Process p = r.exec("/usr/bin/evince /home/adminlinux/isoftconta/esf.pdf"); 
  }
else
 {
    
    Desktop.getDesktop().open(file);
 
 }
 
 
} catch (Exception t) { 
   
}

       

       
       

    }
   public static void fpdf_estadoresultadosintegrales() throws IOException, BadElementException, DocumentException, URISyntaxException {
        String especialidad = "";
        BigDecimal total = BigDecimal.ZERO;
        
        Document document = new Document();
        Font fuente = new Font();
       
        document = new Document(PageSize.LETTER, 2, 2, 2, 2);
        PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/isoftconta/eri.pdf"));
        document.open();

        PdfPTable tableMaster = new PdfPTable(1);
        //PdfPTable table = new PdfPTable(new float[] { 5, 5});

        PdfPTable table = new PdfPTable(new float[]{10});
        PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
        PdfPTable tableesf = new PdfPTable(new float[]{7, 3});
        Image imagen;
        tableMaster.getDefaultCell().setBorderWidth(0);
        imagen = Image.getInstance("resources/logo.png");
        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setFixedHeight(40f);
        tableImagenTexto.addCell(imagen);
        tableImagenTexto.getDefaultCell().setFixedHeight(0f);
        table.setWidthPercentage(100f);
        tableesf.setWidthPercentage(100f);
        tableMaster.setWidthPercentage(100f);
        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        fuente = FontFactory.getFont("arial", 14, Font.BOLD);
         tableImagenTexto.addCell(new Paragraph("CENTRO MÉDICO DEL GUAVIARE SAS", fuente));
        fuente = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
        tableImagenTexto.getDefaultCell().setColspan(2);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableImagenTexto.addCell(new Paragraph("Nit.: 900.767.863 ", fuente));
        tableImagenTexto.addCell(new Paragraph("CRA 22 N° 11-18 B. LA ESPERANZA TEL. 3134212162 ", fuente));
        tableImagenTexto.addCell(new Paragraph("SAN JOSE DEL GUAVIARE", fuente));
        table.getDefaultCell().setPadding(10);
        table.setWidthPercentage(100f);
        table.addCell(tableImagenTexto);
        //tabla datos de cliente
        table.getDefaultCell().setPadding(7);
        tableesf.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        tableesf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableesf.getDefaultCell().setColspan(2);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        tableesf.getDefaultCell().setColspan(2);
        fuente = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
        tableesf.getDefaultCell().setBorder(0);
        tableesf.addCell(new Paragraph("Estado de Resultados Integrales del año terminado el "+isoftconta.util.UtilDate.calculardiasdelmes(EntidadesStatic.li_libromayorbalances.get(0).getAño(), 11)+" de "+isoftconta.util.UtilDate.obtenerMes(EntidadesStatic.li_libromayorbalances.get(0).getAño(), EntidadesStatic.li_libromayorbalances.get(0).getMes())+" de "+EntidadesStatic.li_libromayorbalances.get(0).getAño(), fuente));
       fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
         tableesf.addCell(new Paragraph("Bajo NIIF para Pymes", fuente));
       tableesf.addCell(new Paragraph("(En Pesos Colombianos $COP)", fuente));
        tableesf.getDefaultCell().setColspan(1);
        tableesf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
       
        tableesf.addCell(new Paragraph("Resultado", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph(String.valueOf(EntidadesStatic.li_libromayorbalances.get(0).getAño()),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Activos Corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("",fuente));
        tableesf.addCell(new Paragraph("Efectivo y equivalentes al efectivo", fuente));
      
        Double efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("11") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        Double efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("11") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        BigDecimal tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf((tot)),fuente));
        
        tableesf.addCell(new Paragraph("Cuentas comerciales por cobrar y otras cuentas por cobrar corrientes", fuente));
       
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("13") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("13") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
          tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
           tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf((tot)),fuente));
        
        tableesf.addCell(new Paragraph("Otros activos financieros corrientes", fuente));
       
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("14") || lb.getConPuc().getCodigo().substring(0,2).equals("12") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("14")|| lb.getConPuc().getCodigo().substring(0,2).equals("12") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
           tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
           tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
      fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total Activos Corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("14") || lb.getConPuc().getCodigo().substring(0,2).equals("12") || lb.getConPuc().getCodigo().substring(0,2).equals("11") || lb.getConPuc().getCodigo().substring(0,2).equals("13") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("14")|| lb.getConPuc().getCodigo().substring(0,2).equals("12") || lb.getConPuc().getCodigo().substring(0,2).equals("11") || lb.getConPuc().getCodigo().substring(0,2).equals("13") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Activos no corrientes", fuente));
        tableesf.addCell(new Paragraph("", fuente));
         fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
         tableesf.addCell(new Paragraph("Propiedades, planta y equipo", fuente));
         efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
         tableesf.addCell(new Paragraph("Activos intangibles distintos de la plusvalía", fuente));
         efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("17") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("17") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
         tableesf.addCell(new Paragraph("Otros activos financieros no corrientes", fuente));
         efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("18") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("18") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total Activos No Corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15") || lb.getConPuc().getCodigo().substring(0,2).equals("17") || lb.getConPuc().getCodigo().substring(0,2).equals("18") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15")|| lb.getConPuc().getCodigo().substring(0,2).equals("17") || lb.getConPuc().getCodigo().substring(0,2).equals("18") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.addCell(new Paragraph("Total Activos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15") || lb.getConPuc().getCodigo().substring(0,2).equals("17") || lb.getConPuc().getCodigo().substring(0,2).equals("18") || lb.getConPuc().getCodigo().substring(0,2).equals("11") || lb.getConPuc().getCodigo().substring(0,2).equals("12") || lb.getConPuc().getCodigo().substring(0,2).equals("13") || lb.getConPuc().getCodigo().substring(0,2).equals("14") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15")|| lb.getConPuc().getCodigo().substring(0,2).equals("17") || lb.getConPuc().getCodigo().substring(0,2).equals("18") || lb.getConPuc().getCodigo().substring(0,2).equals("12") || lb.getConPuc().getCodigo().substring(0,2).equals("13") || lb.getConPuc().getCodigo().substring(0,2).equals("14") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.getDefaultCell().setColspan(2);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0.25);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0.25);
        tableesf.addCell(new Paragraph("",fuente));
       
        tableesf.getDefaultCell().setBorderWidth((float) 0);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0);
        //*************************pasivos******************************
        tableesf.getDefaultCell().setColspan(1);
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
         tableesf.addCell(new Paragraph("Patrimonio y pasivos ", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("",fuente));
        tableesf.addCell(new Paragraph("Pasivos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("",fuente));
        tableesf.addCell(new Paragraph("Pasivos corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("",fuente));
        tableesf.addCell(new Paragraph("Provisiones corrientes por beneficios a los empleados", fuente));
      
         efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("23") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
         efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("23") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
         tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf((tot)),fuente));
        
        tableesf.addCell(new Paragraph("Cuentas por pagar comerciales y otras cuentas por pagar", fuente));
       
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue() ).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf((tot)),fuente));
        
        tableesf.addCell(new Paragraph("Pasivos por impuestos corrientes, corriente", fuente));
       
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("22") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("22") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
         tableesf.addCell(new Paragraph("Otros pasivos financieros corrientes", fuente));
       
        efectivodebito=0.0;//EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("22") ).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=0.0;//=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("22")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total pasivos Corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21") || lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21")|| lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Pasivos no corrientes", fuente));
         tableesf.addCell(new Paragraph("", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("Otros pasivos financieros no corrientes", fuente));
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("24") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("24") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
         tableesf.addCell(new Paragraph("Otros pasivos no financieros no corrientes", fuente));
         efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("25") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("25") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total de pasivos no corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("24")|| lb.getConPuc().getCodigo().substring(0,2).equals("25") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.addCell(new Paragraph("Total Pasivos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21") || lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") || lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") || lb.getConPuc().getCodigo().substring(0,2).equals("26") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21")|| lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") || lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") || lb.getConPuc().getCodigo().substring(0,2).equals("26") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        
        
        tableesf.getDefaultCell().setColspan(2);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0.25);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0.25);
        tableesf.addCell(new Paragraph("",fuente));
        
        //*************************************************************************************************************************
     
        tableesf.getDefaultCell().setBorderWidth((float) 0);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0);
        
        
        //***************************************************************
          tableesf.getDefaultCell().setColspan(1);
             fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Patrimonio", fuente));
         tableesf.addCell(new Paragraph("", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("Capital emitido", fuente));
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.addCell(new Paragraph("Otras reservas", fuente));
         efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("33") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("33") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.addCell(new Paragraph("Superavit por revaluación", fuente));
         efectivodebito=0.0;//EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("25")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=0.0;//EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("25")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.addCell(new Paragraph("Ganancias acumuladas", fuente));
         efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("35") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("35") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total patrimonio", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31") || lb.getConPuc().getCodigo().substring(0,2).equals("33") || lb.getConPuc().getCodigo().substring(0,2).equals("35") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31")|| lb.getConPuc().getCodigo().substring(0,2).equals("33")|| lb.getConPuc().getCodigo().substring(0,2).equals("35") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
          tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
     
          //*************************************************************
            //*************************************************************************************************************************
          tableesf.getDefaultCell().setColspan(2);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0.25);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0.25);
        tableesf.addCell(new Paragraph("",fuente));
          
        tableesf.getDefaultCell().setBorderWidth((float) 0);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0);
          tableesf.getDefaultCell().setColspan(1);
        tableesf.addCell(new Paragraph("Total patrimonio y pasivos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31") || lb.getConPuc().getCodigo().substring(0,2).equals("33") || lb.getConPuc().getCodigo().substring(0,2).equals("35")|| lb.getConPuc().getCodigo().substring(0,2).equals("21") || lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") || lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") || lb.getConPuc().getCodigo().substring(0,2).equals("26") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=EntidadesStatic.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31")|| lb.getConPuc().getCodigo().substring(0,2).equals("33")|| lb.getConPuc().getCodigo().substring(0,2).equals("35")|| lb.getConPuc().getCodigo().substring(0,2).equals("21") || lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") || lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") || lb.getConPuc().getCodigo().substring(0,2).equals("26") && !lb.getDetalle().equals("Comprobante Saldos Iniciales")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
   
        fuente = FontFactory.getFont("Times-Roman", 8, Font.NORMAL);
        tableesf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        tableesf.getDefaultCell().setPadding(5);
        tableesf.getDefaultCell().setPadding(10);
        tableesf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        fuente = FontFactory.getFont("Times-Roman", 9, Font.BOLD);
        tableesf.addCell(new Paragraph("Firma quien Aprueba: ________________________", fuente));
        tableesf.addCell(new Paragraph("Validez de este documento 30 días", fuente));
        table.addCell(tableesf);
        tableMaster.addCell(table);
        document.add(tableMaster);

        // step 5
        document.close();
         File file = new File("/home/adminlinux/isoftconta/eri.pdf");
try { 
 
 String os = System.getProperty("os.name"); 
 System.out.println("os->"+os);
 if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
 { 
  Runtime r = Runtime.getRuntime(); 
  Process p = r.exec("/usr/bin/evince /home/adminlinux/isoftconta/eri.pdf"); 
  }
else
 {
   
    Desktop.getDesktop().open(file);
 
 }
 
 
} catch (Exception t) { 
   
}

       

       
       

    }
 
}
