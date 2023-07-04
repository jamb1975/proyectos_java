/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nomina;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Rectangle;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

import modelo.NominaEmpleados;
import fxnomina.FXNomina;

import util.NumberToLetterConverter;

/**
 *
 * @author adminlinux
 */
public class ImprimirPdf {
     private static String cerosizq = "";
  private static String cerosizqcomprobante = "";
  private static  Long nofactura = Long.MIN_VALUE;
  private static  Long nocomprobante = Long.MIN_VALUE;
   
 private static BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
    } 
 private static String setCeros(String numero) {
        cerosizqcomprobante = "";
        for (int i = 4; i > numero.toString().length(); i--) {
            cerosizqcomprobante = cerosizqcomprobante + "0";
        }
        return cerosizqcomprobante;
    } 
   
 public static void fpdfcomprobantenomina() throws IOException, BadElementException, DocumentException {
       
        Document document = new Document();
        Font fuente = new Font();
        document = new Document(PageSize.LETTER, 20, 20, 20, 20 );
        PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/nomina/comprobantenomina.pdf"));
         document.setPageSize(PageSize.LETTER.rotate());
        document.open();

        PdfPTable table = new PdfPTable(new float[]{10});
        PdfPTable tableImagenTexto = new PdfPTable(new float[]{10});
        PdfPTable tabletitulo = new PdfPTable(new float[]{10});
        PdfPTable tablepadre = new PdfPTable(new float[]{4,4,4});
        PdfPTable tabless = new PdfPTable(new float[]{5,5});
        PdfPTable tablepf = new PdfPTable(new float[]{5,5});
        PdfPTable tableps = new PdfPTable(new float[]{5,5});
        PdfPTable pagonomina = new PdfPTable(new float[]{3, 4, 2, 2, 2, 2,2,2,2,2,2,3});
        Image imagen;
       

      

        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setFixedHeight(40f);
        if(FXNomina.genUnidadesOrganizacion.getLogo()!=null)
        {   
         imagen = Image.getInstance(FXNomina.genUnidadesOrganizacion.getLogo());  
         tableImagenTexto.addCell(imagen);
        }
        else
        {
          tableImagenTexto.addCell("");  
        }
        tableImagenTexto.getDefaultCell().setFixedHeight(0f);
        table.setWidthPercentage(100f);
        tabletitulo.setWidthPercentage(100f);
        pagonomina.setWidthPercentage(100f);
        tablepadre.setWidthPercentage(100f);
        tabless.setWidthPercentage(100f);
        tablepf.setWidthPercentage(100f);
        tableps.setWidthPercentage(100f);
        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tablepadre.getDefaultCell().setBorderWidthBottom(0);
        tablepadre.getDefaultCell().setBorderWidthTop(0);
        tablepadre.getDefaultCell().setBorderWidthLeft(0);
        tablepadre.getDefaultCell().setBorderWidthRight(0);
       

        tableImagenTexto.getDefaultCell().setColspan(2);
         
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableImagenTexto.addCell(new Paragraph(FXNomina.genUnidadesOrganizacion!=null?FXNomina.genUnidadesOrganizacion.getNombre():"", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableImagenTexto.addCell(new Paragraph("Nit: "+FXNomina.genUnidadesOrganizacion!=null?FXNomina.genUnidadesOrganizacion.getNit():"", fuente));
        
        document.add(tableImagenTexto);

        //tabla datos de cliente
        table.getDefaultCell().setPadding(7);
        tabletitulo.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        tabletitulo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
       
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);

        fuente = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
        tabletitulo.getDefaultCell().setBorder(0);
        tabletitulo.addCell(new Paragraph("Comprobante de Nomina " + " No. " + FXNomina.nomina.getNocomprobantecerosizquierda(), fuente));
        tabletitulo.addCell(new Paragraph("Periodo Inicio: "+util.UtilDate.s_formatoyearmesdia(FXNomina.nomina.getPeriodoinicio())+" Periodo Fin: "+ util.UtilDate.s_formatoyearmesdia(FXNomina.nomina.getPeriodofin()), fuente));
        tabletitulo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        fuente = FontFactory.getFont("arial", 10, Font.NORMAL);
             
         document.add(tabletitulo);
        
        pagonomina.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        pagonomina.addCell(new Paragraph("N° Ident", fuente));
        pagonomina.addCell(new Paragraph("Nombre", fuente));
        pagonomina.addCell(new Paragraph("Sueldo Básico", fuente));
        pagonomina.addCell(new Paragraph("Días Trabajados", fuente));
        pagonomina.addCell(new Paragraph("Sueldo Bas. Total", fuente));
        pagonomina.addCell(new Paragraph("Aux Transporte", fuente));
        pagonomina.addCell(new Paragraph("Tot. Devengado", fuente));
        pagonomina.addCell(new Paragraph("Salud", fuente));
        pagonomina.addCell(new Paragraph("Pensión", fuente));
        pagonomina.addCell(new Paragraph("Total Deducciones", fuente));
        pagonomina.addCell(new Paragraph("Sueldo Neto", fuente));
        pagonomina.addCell(new Paragraph("Firma", fuente));
        for (NominaEmpleados ne : FXNomina.nomina.getNominaempleadositems()) {
           
            pagonomina.addCell(new Paragraph(ne.getEmpleados().getGenPersonas().getDocumento(), fuente));
            pagonomina.addCell(new Paragraph(ne.getEmpleados().getGenPersonas().getPrimerNombre()+" "+ne.getEmpleados().getGenPersonas().getPrimerApellido(), fuente));
            pagonomina.addCell(new Paragraph(String.valueOf(ne.getEmpleados().getCargosEntidad().getSueldo().toBigInteger()), fuente));
            pagonomina.addCell(new Paragraph(String.valueOf(ne.getCantdiastrabajados()), fuente));
            pagonomina.addCell(new Paragraph(String.valueOf(ne.getTotalbasico().toBigInteger()), fuente));
            pagonomina.addCell(new Paragraph(String.valueOf(ne.getAuxiliotransporte().toBigInteger()), fuente));
            pagonomina.addCell(new Paragraph(String.valueOf(ne.getTotaldevengado().toBigInteger()), fuente));
            pagonomina.addCell(new Paragraph(String.valueOf(round(ne.getValorsalud()).toBigInteger()), fuente));
            pagonomina.addCell(new Paragraph(String.valueOf(round(ne.getValorpension()).toBigInteger()), fuente));
            pagonomina.addCell(new Paragraph(String.valueOf(round(ne.getTotaldeducciones()).toBigInteger()), fuente));
            pagonomina.addCell(new Paragraph(String.valueOf(round(ne.getNetopagado()).toBigInteger()), fuente));
            pagonomina.addCell(new Paragraph("", fuente));
        }

          
            pagonomina.getDefaultCell().setColspan(4);
            pagonomina.addCell(new Paragraph("Totales:", fuente));
            
            pagonomina.getDefaultCell().setColspan(1);
            pagonomina.addCell(new Paragraph(FXNomina.nomina.getTotalbasico().toBigInteger().toString(), fuente));
            pagonomina.addCell(new Paragraph(FXNomina.nomina.getTotalauxtransporte().toBigInteger().toString(), fuente));
            pagonomina.addCell(new Paragraph(FXNomina.nomina.getTotaldevengado().toBigInteger().toString(), fuente));
            pagonomina.addCell(new Paragraph(FXNomina.nomina.getTotalsalud().toBigInteger().toString(), fuente));
            pagonomina.addCell(new Paragraph(FXNomina.nomina.getTotalpension().toBigInteger().toString(), fuente));
            pagonomina.addCell(new Paragraph(FXNomina.nomina.getTotaldeducciones().toBigInteger().toString(), fuente));
            pagonomina.addCell(new Paragraph(FXNomina.nomina.getTotalsueldoneto().toBigInteger().toString(), fuente));
          
             pagonomina.addCell(new Paragraph("", fuente));
            document.add(pagonomina);
           fuente = FontFactory.getFont("Times-Roman", 10, Font.BOLD);     
        tabless.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tabless.getDefaultCell().setColspan(2);
        tabless.addCell(new Paragraph("Seguridad Social", fuente));
        fuente = FontFactory.getFont("Times-Roman", 10, Font.NORMAL);     
        tabless.getDefaultCell().setColspan(1);
        tabless.addCell(new Paragraph("Salud Empleador:", fuente));
        tabless.addCell(new Paragraph(FXNomina.nomina.getSs_salud().toBigInteger().toString(), fuente));
        tabless.addCell(new Paragraph("Fondo Pensiones:", fuente));
        tabless.addCell(new Paragraph(FXNomina.nomina.getSs_fondopensiones().toBigInteger().toString(), fuente));
        tabless.addCell(new Paragraph("ARL:", fuente));
        tabless.addCell(new Paragraph(FXNomina.nomina.getSs_arl().toBigInteger().toString(), fuente));
        tabless.addCell(new Paragraph("Total Seguridad Social", fuente));
        tabless.addCell(new Paragraph(FXNomina.nomina.getSs_salud().add(FXNomina.nomina.getSs_fondopensiones().add(FXNomina.nomina.getSs_arl())).toBigInteger().toString(), fuente));
        tablepadre.addCell(tabless);
        
        fuente = FontFactory.getFont("Times-Roman", 10, Font.BOLD);     
        tablepf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tablepf.getDefaultCell().setColspan(2);
        tablepf.addCell(new Paragraph("Parafiscales", fuente));
        fuente = FontFactory.getFont("Times-Roman", 10, Font.NORMAL);     
        tablepf.getDefaultCell().setColspan(1);
        tablepf.addCell(new Paragraph("Caja Comp. Fam::", fuente));
        tablepf.addCell(new Paragraph(FXNomina.nomina.getPf_cajacf().toBigInteger().toString(), fuente));
        tablepf.addCell(new Paragraph("ICBF:", fuente));
        tablepf.addCell(new Paragraph(FXNomina.nomina.getPf_icbf().toBigInteger().toString(), fuente));
        tablepf.addCell(new Paragraph("SENA:", fuente));
        tablepf.addCell(new Paragraph(FXNomina.nomina.getPf_sena().toBigInteger().toString(), fuente));
        tablepf.addCell(new Paragraph("Total Parafiscales", fuente));
        tablepf.addCell(new Paragraph(FXNomina.nomina.getPf_icbf().add(FXNomina.nomina.getPf_cajacf().add(FXNomina.nomina.getPf_sena())).toBigInteger().toString(), fuente));
        tablepadre.addCell(tablepf);
        
              fuente = FontFactory.getFont("Times-Roman", 10, Font.BOLD);     
        tableps.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableps.getDefaultCell().setColspan(2);
        tableps.addCell(new Paragraph("Prestaciones Sociales", fuente));
        fuente = FontFactory.getFont("Times-Roman", 10, Font.NORMAL);     
        tableps.getDefaultCell().setColspan(1);
        tableps.addCell(new Paragraph("Cesantías:", fuente));
        tableps.addCell(new Paragraph(FXNomina.nomina.getPp_cesantias().toBigInteger().toString(), fuente));
        tableps.addCell(new Paragraph("Intereses Cesantías:", fuente));
        tableps.addCell(new Paragraph(FXNomina.nomina.getPp_intcesantias().toBigInteger().toString(), fuente));
        tableps.addCell(new Paragraph("Prima:", fuente));
        tableps.addCell(new Paragraph(FXNomina.nomina.getPp_prima().toBigInteger().toString(), fuente));
        tableps.addCell(new Paragraph("Vacaciones:", fuente));
        tableps.addCell(new Paragraph(FXNomina.nomina.getPp_vacaciones().toBigInteger().toString(), fuente));
        tableps.addCell(new Paragraph("Total Prestaciones Sociales:", fuente));
        
        tableps.addCell(new Paragraph(FXNomina.nomina.getPp_cesantias().add(FXNomina.nomina.getPp_intcesantias().add(FXNomina.nomina.getPp_prima()).add(FXNomina.nomina.getPp_vacaciones())).toBigInteger().toString(), fuente));
        tablepadre.addCell(tableps);
        document.add(tablepadre);
        fuente = FontFactory.getFont("Times-Roman", 8, Font.NORMAL);
        Paragraph para3 = new Paragraph("\n\n\n\nElaboro: ________________________   Autoriza:____________________________", fuente);
            para3.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(para3);
        document.close();
        File file = new File("/home/adminlinux/nomina/comprobantenomina.pdf");
        try {
      String os = System.getProperty("os.name"); 
      if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
 { 
  Runtime r = Runtime.getRuntime(); 
  Process p = r.exec("/usr/bin/evince /home/adminlinux/nomina/comprobantenomina.pdf"); 
  }
else
 {
      
    Desktop.getDesktop().open(file);
 
 }
//Process p = Runtime.getRuntime().exec ("//home//adminlinux//sihic//factura.pdf");

        } catch (Exception evvv) {

            JOptionPane.showMessageDialog(null, "No se puede abrir el archivo de ayuda, probablemente fue borrado", "ERROR", JOptionPane.ERROR_MESSAGE);

        }
        /*   ProcessBuilder pb = new ProcessBuilder("java","-jar","/home/adminlinux/sihic/pdfviewercomprobante/PdfViewer.jar");

	        System.out.println("Run echo command");

	        Process process = pb.start();*/

    }




}
