/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad;

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
import modelo.FacturaItem;
import modelo.NominaEmpleados;
import sihic.SihicApp;
import sihic.contabilidad.documentos.FacturacionMasiva;
import sihic.util.Cabecera2;
import sihic.util.NumberToLetterConverter;

/**
 *
 * @author adminlinux
 */
public class ImprimirPdf {
     private static String cerosizq = "";
  private static String cerosizqcomprobante = "";
  private static  Long nofactura = Long.MIN_VALUE;
  private static  Long nocomprobante = Long.MIN_VALUE;
       public static void fpdfcomprobanteprocedimiento() throws IOException, BadElementException, DocumentException {
        List<FacturaItem> li_facturaitem = itemsfacturaprocedimiento();//facturaItemControllerClient.getLi_facturaitems(fi.getConComprobanteProcedimiento());
        //Capturar especialidad
        String especialidad = "";
        BigDecimal total = BigDecimal.ZERO;
        for (FacturaItem fi2 : li_facturaitem) {
            if (fi2.getProducto().getGenCategoriasProductos().getCodigo()==1) {
                try {

                    especialidad = fi2.getProducto().getAgnEspecialidades().getNombre() + " Código: " + fi2.getProducto().getAgnEspecialidades().getCodigo();
                } catch (Exception e) {
                   try{
                            especialidad = fi2.getProducto().getHclCups().getAgnEspecialidades().getNombre() + " Código: " + fi2.getProducto().getHclCups().getAgnEspecialidades().getCodigo();
                   }catch (Exception e2) {
                  
                            especialidad = "N/A";
           
                }
                }

            }
            total = total.add(fi2.getValor_total());
        }
        Document document = new Document();
        Font fuente = new Font();
        Long nocomprobante;
        try{
         nocomprobante = SihicApp.facturaItem.getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId();
        }catch(Exception e)
        {
           nocomprobante=Long.valueOf(0);
        }
        document = new Document(PageSize.LETTER, 2, 2, 2, 2);
        PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/comprobante.pdf"));
        document.open();

        PdfPTable tableMaster = new PdfPTable(1);
        //PdfPTable table = new PdfPTable(new float[] { 5, 5});

        PdfPTable table = new PdfPTable(new float[]{10});
        PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
        PdfPTable tableCliente = new PdfPTable(new float[]{6, 7});
        PdfPTable tableFacturaItems = new PdfPTable(new float[]{2, 4, 1, 3, 3, 3});
        Image imagen;
        tableMaster.getDefaultCell().setBorderWidth(0);

        imagen = Image.getInstance(SihicApp.genUnidadesOrganizacion.getLogo());

        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setFixedHeight(40f);

        tableImagenTexto.addCell(imagen);

        tableImagenTexto.getDefaultCell().setFixedHeight(0f);
        table.setWidthPercentage(100f);
        tableCliente.setWidthPercentage(100f);
        tableFacturaItems.setWidthPercentage(100f);
        tableMaster.setWidthPercentage(100f);
        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        fuente = FontFactory.getFont("arial", 14, Font.BOLD);
        tableImagenTexto.addCell(new Paragraph("CENTRO MÉDICO DEL GUAVIARE SAS", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableImagenTexto.getDefaultCell().setColspan(2);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableImagenTexto.addCell(new Paragraph("Nit.: 900.767.863 ", fuente));
         switch (SihicApp.sucursales.getCodigo().trim()) {
                case "1":
                    tableImagenTexto.addCell(new Paragraph("CRA 22 N° 11-18 B. LA ESPERANZA TEL. 3134212162 SAN JOSE DEL GUAVIARE-GUAVIARE", fuente));
                    break;
                case "2":
                    tableImagenTexto.addCell(new Paragraph("CRA 48 N° 10-74 B. LA ESPERANZA VILLAVICENCIO-META", fuente));
                    break;
                case "3":
                    tableImagenTexto.addCell(new Paragraph("DIAGONAL 15  N° 24-34 B.PABLO SEXTO ACACIAS-META", fuente));
                    break;
                case "4":
                    tableImagenTexto.addCell(new Paragraph("CL 14 N° 3-80 B. GRANADA-META", fuente));
                    break;
            }
        table.getDefaultCell().setPadding(10);
        table.setWidthPercentage(100f);

        table.addCell(tableImagenTexto);

        //tabla datos de cliente
        table.getDefaultCell().setPadding(7);
        tableCliente.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableCliente.getDefaultCell().setColspan(4);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);

        fuente = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
        tableCliente.getDefaultCell().setBorder(0);
        tableCliente.addCell(new Paragraph("Comprobante" + "\nNo. " + setCeros(cerosizq) + SihicApp.facturaItem.getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId(), fuente));
        tableCliente.getDefaultCell().setColspan(1);
        tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableCliente.addCell(new Paragraph("Fecha: ", fuente));
        tableCliente.addCell(new Paragraph(DateFormat.getDateInstance().format(SihicApp.facturaItem.getAgnCitas().getFechaHora()) + " Cargo a Factura: " + SihicApp.facturaItem.getFactura().getNofacturacerosizquierda(), fuente));
        try {
            if (SihicApp.facturaItem.getFactura().getPrefijo().substring(0,1).equals("A") || SihicApp.facturaItem.getFactura().getPrefijo().substring(0,1).equals("P") || SihicApp.s_factura.getPrefijo().substring(0, 1).equals("S")) {
                tableCliente.addCell(new Paragraph("Entidad: " + SihicApp.facturaItem.getFactura().getGenEapb().getNombre() + " Código: " + SihicApp.facturaItem.getFactura().getGenEapb().getCodigo(), fuente));

            } else {
                  if (SihicApp.facturaItem.getFactura().getPrefijo().substring(0,1).equals("T")) {
                tableCliente.addCell(new Paragraph("Entidad: " + SihicApp.facturaItem.getAgnCitas().getGenPacientes().getAseguradora().getNombre() + " Código: " + SihicApp.facturaItem.getAgnCitas().getGenPacientes().getAseguradora().getCodigo(), fuente));

            }
               else
                  {
                tableCliente.addCell(new Paragraph("Entidad: " + SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenEapb().getNombre() + " Código: " + SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenEapb().getCodigo(), fuente));
                  }
            }
        } catch (Exception e) {
            tableCliente.addCell(new Paragraph("Entidad: N  Código: N", fuente));

        }
        tableCliente.addCell(new Paragraph("Tipo Atención: " + "Ambulatoria", fuente));
        tableCliente.addCell(new Paragraph("Médico: " + SihicApp.facturaItem.getAgnCitas().getAgnMedicos().getGenPersonas().getNombres(), fuente));
        tableCliente.addCell(new Paragraph(" Especialidad: " + especialidad, fuente));
        //table.getDefaultCell().setBorderWidthRight((float)0.25);
        tableCliente.addCell(new Paragraph("Paciente: " + SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenPersonas().getNombres(), fuente));
        tableCliente.addCell(new Paragraph("No Documento: " + SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento(), fuente));
        tableCliente.getDefaultCell().setColspan(2);
        String profesion;
        if (SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones() != null) {
            profesion = "Profesión: " + SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones().getCodigo();
        } else {
            profesion = "Profesión: No";
        }
        tableCliente.addCell(new Paragraph(profesion + " Tipo Usuario: " + (SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenTiposUsuarios()!=null?SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenTiposUsuarios().getNombre():"N") + " Edad: A " + SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenPersonas().year() + " Sexo: " + SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla() + " Ciudad: " + (SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios()!=null?SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo():"N")+ " Zona: " + (SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenPersonas().getGen_zona_residencia()!=null?SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenPersonas().getGen_zona_residencia().getCodigo():"N"), fuente));
        tableCliente.addCell(new Paragraph("Fecha de Nacimiento: " + sihic.util.UtilDate.formatodiamesyear(SihicApp.facturaItem.getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento()), fuente));

        table.addCell(tableCliente);
        table.getDefaultCell().setColspan(2);
        tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setPadding(0);
        tableFacturaItems.addCell(new Paragraph("Código", fuente));
        tableFacturaItems.addCell(new Paragraph("Descripción", fuente));
        tableFacturaItems.addCell(new Paragraph("Cant", fuente));
        tableFacturaItems.addCell(new Paragraph("Valor/U", fuente));
        tableFacturaItems.addCell(new Paragraph("Copago o Cuota Mod", fuente));
        tableFacturaItems.addCell(new Paragraph("Valor Total", fuente));

        for (FacturaItem fi2 : li_facturaitem) {
            tableFacturaItems.getDefaultCell().setBorder(0);
            //tableFacturaItems.getDefaultCell().setBorderWidthTop(1);
            fuente = new Font();

            fuente.setFamily("arial");
            tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableFacturaItems.getDefaultCell().setBorderWidthLeft((float) 0.25);
            tableFacturaItems.getDefaultCell().setBorderWidthRight((float) 0.25);
            tableFacturaItems.getDefaultCell().setBorderWidthBottom((float) 0.25);
            String codigo = "";
           
                codigo = fi2.getProducto().getCodigo();
            
            tableFacturaItems.addCell(new Paragraph(codigo, fuente));
            tableFacturaItems.getDefaultCell().setBorderWidthLeft(0);
            tableFacturaItems.getDefaultCell().setBorderWidthRight((float) 0.25);
            tableFacturaItems.addCell(new Paragraph(fi2.getProducto().getNombre(), fuente));
            tableFacturaItems.addCell(new Paragraph(String.valueOf(fi2.getQuantity()), fuente));
            tableFacturaItems.addCell(new Paragraph(String.valueOf(round(fi2.getValor_u())), fuente));
            tableFacturaItems.addCell(new Paragraph("-" + String.valueOf(round(fi2.getCopago().add(fi2.getCuotamoderadora()))), fuente));
            tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            tableFacturaItems.addCell(new Paragraph(String.valueOf(round(fi2.getValor_total())), fuente));

        }

        tableFacturaItems.getDefaultCell().setColspan(5);
        tableFacturaItems.addCell(new Paragraph("Valor Total: ", fuente));
        tableFacturaItems.addCell(new Paragraph(round(total).toString(), fuente));
        tableFacturaItems.getDefaultCell().setColspan(6);
        tableFacturaItems.addCell(new Paragraph("Valor Total en Letras: " + NumberToLetterConverter.convertNumberToLetter(String.valueOf(round(total))), fuente));

        table.addCell(tableFacturaItems);
        table.getDefaultCell().setColspan(2);
        // table.getDefaultCell().setBorderWidthBottom((float)0.25);
        // table.getDefaultCell().setBorderWidthLeft((float)0.25);
        // table.getDefaultCell().setBorderWidthRight((float)0.25);

        fuente = FontFactory.getFont("Times-Roman", 8, Font.NORMAL);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        table.getDefaultCell().setPadding(5);
        table.addCell(new Paragraph("RES. N° 780000001443 23/09/2015", fuente));

        table.getDefaultCell().setBorderWidthBottom((float) 0.25);
        table.getDefaultCell().setBorderWidthLeft((float) 0.25);
        table.getDefaultCell().setBorderWidthRight((float) 0.25);
        table.getDefaultCell().setPadding(10);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        fuente = FontFactory.getFont("Times-Roman", 9, Font.BOLD);
        table.addCell(new Paragraph("Firma Paciente: ________________________", fuente));
        table.addCell(new Paragraph("Validez de este documento 30 días", fuente));
        tableMaster.addCell(table);
        document.add(tableMaster);

        // step 5
        document.close();

        File file = new File("/home/adminlinux/sihic/comprobante.pdf");

        try {

      String os = System.getProperty("os.name"); 
      if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
 { 
  Runtime r = Runtime.getRuntime(); 
  Process p = r.exec("/usr/bin/evince /home/adminlinux/sihic/comprobante.pdf"); 
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
public static void fprintpdffacturageneral() throws IOException {
        Document document = new Document();

        Font fuente = new Font();
        try {
            document = new Document(PageSize.LETTER, 20f, 20f, 160f, 20f);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/factura.pdf"));
            Rectangle rect = new Rectangle(PageSize.LETTER);
            writer.setBoxSize("art", rect);
            Cabecera2 encabezado = new Cabecera2(SihicApp.s_factura);
            Paragraph parrafo;
            int i;

         

            // indicamos que objecto manejara los eventos al escribir el Pdf
            writer.setPageEvent(encabezado);
            document.open();

            PdfPTable tableMaster = new PdfPTable(1);
            //PdfPTable table = new PdfPTable(new float[] { 5, 5});

            PdfPTable table = new PdfPTable(new float[]{10});
            PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
            PdfPTable tableCliente = new PdfPTable(new float[]{6, 7});
            PdfPTable tableFacturaItems = new PdfPTable(new float[]{2, 3, 5, 2, 5, 2, 2, 2, 2, 2});
            Image imagen;
            tableMaster.getDefaultCell().setBorderWidth(0);
            tableImagenTexto.getDefaultCell().setBorder(0);
            imagen = Image.getInstance(SihicApp.genUnidadesOrganizacion.getLogo());
            tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableImagenTexto.getDefaultCell().setColspan(2);
            tableImagenTexto.getDefaultCell().setFixedHeight(40f);
            tableImagenTexto.addCell(imagen);

            tableImagenTexto.getDefaultCell().setFixedHeight(0f);
            //tableImagenTexto.getDefaultCell().setColspan(2);
            fuente = FontFactory.getFont("arial", 8, Font.BOLD);

            table.getDefaultCell().setPadding(10);
            table.setWidthPercentage(100f);
            table.getDefaultCell().setBorder(0);
            tableCliente.setWidthPercentage(100f);
            tableFacturaItems.setWidthPercentage(100f);
            tableMaster.setWidthPercentage(100f);
            tableFacturaItems.getDefaultCell().setColspan(12);
            tableMaster.getDefaultCell().setBorder(0);

            tableFacturaItems.getDefaultCell().setColspan(0);
            //tabla datos de cliente
            table.getDefaultCell().setPadding(7);

            tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            // tableEntidad.getDefaultCell().setColspan(4);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
            table.setWidthPercentage(100f);
            fuente = FontFactory.getFont("arial", 8, Font.BOLD);
            tableCliente.getDefaultCell().setBorder(0);

            fuente = FontFactory.getFont("arial", 8, Font.NORMAL);
            tableFacturaItems.getDefaultCell().setColspan(9);
            //tableFacturaItems.addCell(tableEntidad);

            tableFacturaItems.getDefaultCell().setColspan(0);
            table.getDefaultCell().setColspan(2);
            tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setPadding(0);
            //tableFacturaItems.getDefaultCell().setBorderWidthTop(1);

            fuente = new Font();
            fuente.setFamily("arial");
            fuente.setSize(8);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setPadding(5);
            int regs = 0;
            // table.addCell(tableFacturaItems);
            for (FacturaItem fi : SihicApp.s_factura.getFacturaItems()) {

                tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                tableFacturaItems.addCell(new Paragraph(String.valueOf(setCeros(fi.getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId().toString()) + fi.getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId()), fuente));
                 try
                 {
                tableFacturaItems.addCell(new Paragraph(fi.getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla() + " " + fi.getAgnCitas().getGenPacientes().getGenPersonas().getDocumento(), fuente));
                tableFacturaItems.addCell(new Paragraph(fi.getAgnCitas().getGenPacientes().getGenPersonas().getNombres(), fuente));
                 }catch(Exception e)
                 {
                   tableFacturaItems.addCell(new Paragraph(fi.getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla() + " " + fi.getGenPacientes().getGenPersonas().getDocumento(), fuente));
                   tableFacturaItems.addCell(new Paragraph(fi.getGenPacientes().getGenPersonas().getNombres(), fuente));
                 
                 }
                 
// tableFacturaItems.addCell(new Paragraph (sihic.util.UtilDate.formatodiamesyear(fi.getHclConsultas().getAgnCitas().getFechaCreacion()),fuente));
                // tableFacturaItems.addCell(new Paragraph (sihic.util.UtilDate.formatodiamesyear(fi.getHclConsultas().getAgnCitas().getFechaCreacion()),fuente));
              /*  if (fi.getProducto().getHclCups() != null) {
                      if (!fi.getProducto().getHclCups().equals("")) {
                    try {

                        tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getHclCups().getCodigo().replace(".", "")), fuente));
                        System.out.println("OK");
                    } catch (Exception e) {
                        tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getCodigo().replace(".", "")), fuente));
                    }
                     } else {
                    tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getCodigo().replace(".", "")), fuente));

                }
                    }
                 else {*/
                    tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getCodigo().replace(".", "")), fuente));

               // }
               if(!FacturacionMasiva.vervompletoprocedimientp.isSelected())
               {
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getNombre().length() <= 30 ? fi.getProducto().getNombre() : fi.getProducto().getNombre().substring(0, 30)), fuente));
               }
               else
               {
                     tableFacturaItems.addCell(new Paragraph(fi.getProducto().getNombre(), fuente));
              
               }
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getQuantity()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getValor_u()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getCuotamoderadora()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getCopago()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getValor_total()), fuente));

                regs++;
            }
            tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

            SihicApp.s_factura.calculartotalescopagocuotamoeras();
            tableFacturaItems.getDefaultCell().setColspan(9);
            tableFacturaItems.addCell(new Paragraph("SubTotal: ", fuente));
            tableFacturaItems.addCell(new Paragraph(SihicApp.s_factura.getNetAmount().add(SihicApp.s_factura.getTotalcuotasmoderadoras()).add(SihicApp.s_factura.getTotalcopagos()).toBigInteger().toString(), fuente));
            tableFacturaItems.addCell(new Paragraph("Total C. Moderadoras: ", fuente));
            tableFacturaItems.addCell(new Paragraph(SihicApp.s_factura.getTotalcuotasmoderadoras().toBigInteger().toString(), fuente));
            tableFacturaItems.addCell(new Paragraph("Total Copagos: ", fuente));
            tableFacturaItems.addCell(new Paragraph(SihicApp.s_factura.getTotalcopagos().toBigInteger().toString(), fuente));
            tableFacturaItems.addCell(new Paragraph("Valor Total: ", fuente));
            tableFacturaItems.addCell(new Paragraph(SihicApp.s_factura.getTotalAmount().toBigInteger().toString(), fuente));
            tableFacturaItems.getDefaultCell().setColspan(12);
            tableFacturaItems.addCell(new Paragraph("Valor Total en Letras: " + sihic.util.NumberToLetterConverter.convertNumberToLetter(String.valueOf(SihicApp.s_factura.getTotalAmount().toBigInteger())), fuente));
            table.addCell(tableFacturaItems);

            table.getDefaultCell().setColspan(2);

            table.getDefaultCell().setPadding(10);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            fuente = FontFactory.getFont("Times-Roman", 9, Font.BOLD);
            table.addCell(new Paragraph("Aceptada: ________________________  Vendedor: ________________________", fuente));

            fuente = FontFactory.getFont("arial", 8, Font.NORMAL);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            table.getDefaultCell().setPadding(5);
            table.addCell(new Paragraph(" RESOLUCION DIAN 780000001443 DE 2015-09-23", fuente));
            table.addCell(new Paragraph(" No somos responsables de IVA", fuente));
            table.addCell(new Paragraph(" LA PRESENTE FACTURA SE ASIMILA EN SUS EFECTOS A UNA LETRA DE CAMBIO ART 779  CODIGO DE COMERCIO", fuente));

            tableMaster.addCell(table);
            document.add(tableMaster);

            // step 5
            document.close();
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        File file = new File("/home/adminlinux/sihic/factura.pdf");
       
            try {

             String os = System.getProperty("os.name"); 
      if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
 { 
  Runtime r = Runtime.getRuntime(); 
  Process p = r.exec("/usr/bin/evince /home/adminlinux/sihic/factura.pdf"); 
  }
else
 {
      
    Desktop.getDesktop().open(file);
 
 }
//Process p = Runtime.getRuntime().exec ("//home//adminlinux//sihic//factura.pdf");

            } catch (Exception evvv) {
                JOptionPane.showMessageDialog(null, "No se puede abrir el archivo de ayuda, probablemente fue borrado", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            //ProcessBuilder pb = new ProcessBuilder("java","-jar","/home/adminlinux/sihic/pdfviewercomprobante/PdfViewerFactura.jar");
            //ProcessBuilder pb = new ProcessBuilder("/home/adminlinux/sihic/factura.pdf");

//	        System.out.println("Run echo command");
//	        Process process = pb.start();
        

    }
   private static List<FacturaItem> itemsfacturaprocedimiento() {
        List<FacturaItem> li_fi = new ArrayList<>();
        li_fi.addAll(SihicApp.facturaItem.getFactura().getFacturaItems());
        return li_fi.stream().filter(line -> line.getConComprobanteProcedimiento().getId().equals(SihicApp.facturaItem.getConComprobanteProcedimiento().getId())).collect(Collectors.toList());
    }
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
   public static void fprintpdffacturaglobal() throws IOException {
        Document document = new Document();

        Font fuente = new Font();
        try {
            document = new Document(PageSize.LETTER, 20f, 20f, 160f, 20f);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/facturaglobal.pdf"));
            Rectangle rect = new Rectangle(PageSize.LETTER);
            writer.setBoxSize("art", rect);
            Cabecera2 encabezado = new Cabecera2(SihicApp.s_factura);
            Paragraph parrafo;
            int i;

           

            // indicamos que objecto manejara los eventos al escribir el Pdf
            writer.setPageEvent(encabezado);
            document.open();

            PdfPTable tableMaster = new PdfPTable(1);
            //PdfPTable table = new PdfPTable(new float[] { 5, 5});

            PdfPTable table = new PdfPTable(new float[]{10});
            PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
            PdfPTable tableCliente = new PdfPTable(new float[]{6, 7});
            PdfPTable tableFacturaItems = new PdfPTable(new float[]{3,3,3});
            Image imagen;
            tableMaster.getDefaultCell().setBorderWidth(0);
            tableImagenTexto.getDefaultCell().setBorder(0);
            imagen = Image.getInstance(SihicApp.genUnidadesOrganizacion.getLogo());
            tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableImagenTexto.getDefaultCell().setColspan(2);
            tableImagenTexto.getDefaultCell().setFixedHeight(40f);
            tableImagenTexto.addCell(imagen);

            tableImagenTexto.getDefaultCell().setFixedHeight(0f);
            //tableImagenTexto.getDefaultCell().setColspan(2);
            fuente = FontFactory.getFont("arial", 8, Font.BOLD);

            table.getDefaultCell().setPadding(10);
            table.setWidthPercentage(100f);
            table.getDefaultCell().setBorder(0);
            tableCliente.setWidthPercentage(100f);
            tableFacturaItems.setWidthPercentage(100f);
            tableMaster.setWidthPercentage(100f);
            
            tableMaster.getDefaultCell().setBorder(0);

            tableFacturaItems.getDefaultCell().setColspan(0);
            //tabla datos de cliente
            table.getDefaultCell().setPadding(7);

            tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            // tableEntidad.getDefaultCell().setColspan(4);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
            table.setWidthPercentage(100f);
            fuente = FontFactory.getFont("arial", 8, Font.BOLD);
            tableCliente.getDefaultCell().setBorder(0);

            fuente = FontFactory.getFont("arial", 8, Font.NORMAL);
            
            //tableFacturaItems.addCell(tableEntidad);

            tableFacturaItems.getDefaultCell().setColspan(0);
            table.getDefaultCell().setColspan(2);
            tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setPadding(0);
            //tableFacturaItems.getDefaultCell().setBorderWidthTop(1);

            fuente = new Font();
            fuente.setFamily("arial");
            fuente.setSize(8);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setPadding(5);
            int regs = 0;
            // table.addCell(tableFacturaItems);
            
            tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

            SihicApp.s_factura.calculartotalescopagocuotamoeras();
            tableFacturaItems.addCell(new Paragraph("Concepto", fuente));
            tableFacturaItems.addCell(new Paragraph("Cantidad", fuente));
            tableFacturaItems.addCell(new Paragraph("Valor", fuente));
          
            tableFacturaItems.addCell(new Paragraph("Procedimientos", fuente));
            tableFacturaItems.addCell(new Paragraph(String.valueOf(SihicApp.s_factura.getFacturaItems().size()), fuente));
            tableFacturaItems.addCell(new Paragraph(SihicApp.s_factura.getTotalAmount().toString(), fuente));
            tableFacturaItems.addCell(new Paragraph("Valor Neto:", fuente));
            tableFacturaItems.addCell(new Paragraph("", fuente));
            tableFacturaItems.addCell(new Paragraph(SihicApp.s_factura.getTotalAmount().toString(), fuente));
            tableFacturaItems.getDefaultCell().setColspan(3);
             tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableFacturaItems.addCell(new Paragraph("Valor Total en Letras:                  " + sihic.util.NumberToLetterConverter.convertNumberToLetter(String.valueOf(SihicApp.s_factura.getTotalAmount())), fuente));
            String regimen="";
            
            if(SihicApp.s_factura.getPrefijo().substring(0,1).equals("A") || SihicApp.s_factura.getPrefijo().substring(0,1).equals("P"))
            {
                regimen=SihicApp.s_factura.getGenTiposUsuarios().getNombre();
            }
            tableFacturaItems.addCell(new Paragraph("Observación: RÉGIMEN: "+ regimen.toUpperCase()+" DETALLE DE FACTURA", fuente));
          
            table.addCell(tableFacturaItems);

            table.getDefaultCell().setColspan(2);

            table.getDefaultCell().setPadding(10);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            fuente = FontFactory.getFont("Times-Roman", 9, Font.BOLD);
            table.addCell(new Paragraph("Aceptada: ________________________             Recibido: ________________________", fuente));

            fuente = FontFactory.getFont("arial", 8, Font.NORMAL);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setPadding(5);
            table.addCell(new Paragraph(" RESOLUCION DIAN 780000001443 DE 2015-09-23", fuente));
            table.addCell(new Paragraph(" No somos responsables de IVA", fuente));
            table.addCell(new Paragraph(" LA PRESENTE FACTURA SE ASIMILA EN SUS EFECTOS A UNA LETRA DE CAMBIO ART 779  CODIGO DE COMERCIO", fuente));

            tableMaster.addCell(table);
            document.add(tableMaster);

            // step 5
            document.close();
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
       File file = new File("/home/adminlinux/sihic/facturaglobal.pdf");
       String os = System.getProperty("os.name"); 
       if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
     { 
       Runtime r = Runtime.getRuntime(); 
        Process p = r.exec("/usr/bin/evince /home/adminlinux/sihic/facturaglobal.pdf"); 
     }
else
 {
      Desktop.getDesktop().open(file);
 }
 
}
 public static void fprintpdffacturacapitado() throws IOException {
        Document document = new Document();

        Font fuente = new Font();
        try {
            document = new Document(PageSize.LETTER, 20f, 20f, 160f, 20f);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/facturacapitado.pdf"));
            Rectangle rect = new Rectangle(PageSize.LETTER);
            writer.setBoxSize("art", rect);
            Cabecera2 encabezado = new Cabecera2(SihicApp.s_factura);
            Paragraph parrafo;
            int i;

         

            // indicamos que objecto manejara los eventos al escribir el Pdf
            writer.setPageEvent(encabezado);
            document.open();

            PdfPTable tableMaster = new PdfPTable(1);
            //PdfPTable table = new PdfPTable(new float[] { 5, 5});

            PdfPTable table = new PdfPTable(new float[]{10});
            PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
            PdfPTable tableCliente = new PdfPTable(new float[]{6, 7});
            PdfPTable tableFacturaItems = new PdfPTable(new float[]{2, 3, 5, 2, 5, 2, 2, 2, 2, 2});
            Image imagen;
            tableMaster.getDefaultCell().setBorderWidth(0);
            tableImagenTexto.getDefaultCell().setBorder(0);
            imagen = Image.getInstance(SihicApp.genUnidadesOrganizacion.getLogo());
            tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableImagenTexto.getDefaultCell().setColspan(2);
            tableImagenTexto.getDefaultCell().setFixedHeight(40f);
            tableImagenTexto.addCell(imagen);

            tableImagenTexto.getDefaultCell().setFixedHeight(0f);
            //tableImagenTexto.getDefaultCell().setColspan(2);
            fuente = FontFactory.getFont("arial", 8, Font.BOLD);

            table.getDefaultCell().setPadding(10);
            table.setWidthPercentage(100f);
            table.getDefaultCell().setBorder(0);
            tableCliente.setWidthPercentage(100f);
            tableFacturaItems.setWidthPercentage(100f);
            tableMaster.setWidthPercentage(100f);
            tableFacturaItems.getDefaultCell().setColspan(12);
            tableMaster.getDefaultCell().setBorder(0);

            tableFacturaItems.getDefaultCell().setColspan(0);
            //tabla datos de cliente
            table.getDefaultCell().setPadding(7);

            tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            // tableEntidad.getDefaultCell().setColspan(4);
            table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
            table.setWidthPercentage(100f);
            fuente = FontFactory.getFont("arial", 8, Font.BOLD);
            tableCliente.getDefaultCell().setBorder(0);

            fuente = FontFactory.getFont("arial", 8, Font.NORMAL);
            tableFacturaItems.getDefaultCell().setColspan(9);
            //tableFacturaItems.addCell(tableEntidad);

            tableFacturaItems.getDefaultCell().setColspan(0);
            table.getDefaultCell().setColspan(2);
            tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setPadding(0);
            //tableFacturaItems.getDefaultCell().setBorderWidthTop(1);

            fuente = new Font();
            fuente.setFamily("arial");
            fuente.setSize(8);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setPadding(5);
            int regs = 0;
            // table.addCell(tableFacturaItems);
            for (FacturaItem fi : SihicApp.s_factura.getFacturaItems()) {

                tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                tableFacturaItems.addCell(new Paragraph(String.valueOf(setCeros(fi.getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId().toString()) + fi.getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId()), fuente));
                tableFacturaItems.addCell(new Paragraph(fi.getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla() + " " + fi.getGenPacientes().getGenPersonas().getDocumento(), fuente));
                tableFacturaItems.addCell(new Paragraph(fi.getGenPacientes().getGenPersonas().getNombres(), fuente));
                // tableFacturaItems.addCell(new Paragraph (sihic.util.UtilDate.formatodiamesyear(fi.getHclConsultas().getAgnCitas().getFechaCreacion()),fuente));
                // tableFacturaItems.addCell(new Paragraph (sihic.util.UtilDate.formatodiamesyear(fi.getHclConsultas().getAgnCitas().getFechaCreacion()),fuente));
                if (fi.getProducto().getHclCups() != null) {
                    try {

                        tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getHclCups().getCodigo().replace(".", "")), fuente));
                    } catch (Exception e) {
                        tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getCodigo().replace(".", "")), fuente));

                    }
                } else {
                    tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getCodigo().replace(".", "")), fuente));

                }
                if(!FacturacionMasiva.vervompletoprocedimientp.isSelected())
                {
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getNombre().length() <= 30 ? fi.getProducto().getNombre() : fi.getProducto().getNombre().substring(0, 30)), fuente));
                }
                else
                {
                   tableFacturaItems.addCell(new Paragraph(fi.getProducto().getNombre(), fuente));
                 
                }
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getQuantity()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getValor_u()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getCuotamoderadora()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getCopago()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getValor_total()), fuente));

                regs++;
            }
            tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

            SihicApp.s_factura.calculartotalescopagocuotamoeras();
            tableFacturaItems.getDefaultCell().setColspan(9);
            tableFacturaItems.addCell(new Paragraph("SubTotal: ", fuente));
            tableFacturaItems.addCell(new Paragraph(SihicApp.s_factura.getNetAmount().add(SihicApp.s_factura.getTotalcuotasmoderadoras()).add(SihicApp.s_factura.getTotalcopagos()).toBigInteger().toString(), fuente));
            tableFacturaItems.addCell(new Paragraph("Total C. Moderadoras: ", fuente));
            tableFacturaItems.addCell(new Paragraph(SihicApp.s_factura.getTotalcuotasmoderadoras().toBigInteger().toString(), fuente));
            tableFacturaItems.addCell(new Paragraph("Diferencia en Capitación: ", fuente));
            tableFacturaItems.addCell(new Paragraph(SihicApp.s_factura.getDiferenciacapitado()!=null?SihicApp.s_factura.getDiferenciacapitado().toBigInteger().toString():"0", fuente));
            tableFacturaItems.addCell(new Paragraph("Valor Total: ", fuente));
            tableFacturaItems.addCell(new Paragraph(SihicApp.s_factura.getTotalAmount().toBigInteger().toString(), fuente));
            tableFacturaItems.getDefaultCell().setColspan(12);
            tableFacturaItems.addCell(new Paragraph("Valor Total en Letras: " + sihic.util.NumberToLetterConverter.convertNumberToLetter(String.valueOf(SihicApp.s_factura.getTotalAmount().toBigInteger())), fuente));
            table.addCell(tableFacturaItems);

            table.getDefaultCell().setColspan(2);

            table.getDefaultCell().setPadding(10);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            fuente = FontFactory.getFont("Times-Roman", 9, Font.BOLD);
            table.addCell(new Paragraph("Aceptada: ________________________  Vendedor: ________________________", fuente));

            fuente = FontFactory.getFont("arial", 8, Font.NORMAL);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            table.getDefaultCell().setPadding(5);
            table.addCell(new Paragraph(" RESOLUCION DIAN 780000001443 DE 2015-09-23", fuente));
            table.addCell(new Paragraph(" No somos responsables de IVA", fuente));
            table.addCell(new Paragraph(" LA PRESENTE FACTURA SE ASIMILA EN SUS EFECTOS A UNA LETRA DE CAMBIO ART 779  CODIGO DE COMERCIO", fuente));

            tableMaster.addCell(table);
            document.add(tableMaster);

            // step 5
            document.close();
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        File file = new File("/home/adminlinux/sihic/facturacapitado.pdf");
       
            try {

             String os = System.getProperty("os.name"); 
      if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
 { 
  Runtime r = Runtime.getRuntime(); 
  Process p = r.exec("/usr/bin/evince /home/adminlinux/sihic/facturacapitado.pdf"); 
  }
else
 {
      
    Desktop.getDesktop().open(file);
 
 }
//Process p = Runtime.getRuntime().exec ("//home//adminlinux//sihic//factura.pdf");

            } catch (Exception evvv) {
                JOptionPane.showMessageDialog(null, "No se puede abrir el archivo de ayuda, probablemente fue borrado", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            //ProcessBuilder pb = new ProcessBuilder("java","-jar","/home/adminlinux/sihic/pdfviewercomprobante/PdfViewerFactura.jar");
            //ProcessBuilder pb = new ProcessBuilder("/home/adminlinux/sihic/factura.pdf");

//	        System.out.println("Run echo command");
//	        Process process = pb.start();
        

    }  
 public static void fpdfcomprobantenomina() throws IOException, BadElementException, DocumentException {
       
        Document document = new Document();
        Font fuente = new Font();
        document = new Document(PageSize.LETTER, 20, 20, 20, 20 );
        PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/comprobantenomina.pdf"));
         document.setPageSize(PageSize.LETTER.rotate());
        document.open();

        PdfPTable table = new PdfPTable(new float[]{10});
        PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
        PdfPTable tabletitulo = new PdfPTable(new float[]{10});
        PdfPTable tablepadre = new PdfPTable(new float[]{4,4,4});
        PdfPTable tabless = new PdfPTable(new float[]{5,5});
        PdfPTable tablepf = new PdfPTable(new float[]{5,5});
        PdfPTable tableps = new PdfPTable(new float[]{5,5});
        PdfPTable pagonomina = new PdfPTable(new float[]{3, 4, 2, 2, 2, 2,2,2,2,2,2,3});
        Image imagen;
       

        imagen = Image.getInstance(SihicApp.genUnidadesOrganizacion.getLogo());

        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setFixedHeight(40f);

        tableImagenTexto.addCell(imagen);

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
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        fuente = FontFactory.getFont("arial", 14, Font.BOLD);
        tableImagenTexto.addCell(new Paragraph("CENTRO MÉDICO DEL GUAVIARE SAS", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableImagenTexto.getDefaultCell().setColspan(2);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableImagenTexto.addCell(new Paragraph("Nit.: 900.767.863 ", fuente));
        
        document.add(tableImagenTexto);

        //tabla datos de cliente
        table.getDefaultCell().setPadding(7);
        tabletitulo.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        tabletitulo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
       
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);

        fuente = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
        tabletitulo.getDefaultCell().setBorder(0);
        tabletitulo.addCell(new Paragraph("Comprobante de Nomina " + " No. " + SihicApp.nomina.getNocomprobantecerosizquierda(), fuente));
        tabletitulo.addCell(new Paragraph("Periodo Inicio: "+sihic.util.UtilDate.s_formatoyearmesdia(SihicApp.nomina.getPeriodoinicio())+" Periodo Fin: "+ sihic.util.UtilDate.s_formatoyearmesdia(SihicApp.nomina.getPeriodofin()), fuente));
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
        for (NominaEmpleados ne : SihicApp.nomina.getNominaempleadositems()) {
            pagonomina.getDefaultCell().setBorder(0);
            pagonomina.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            pagonomina.getDefaultCell().setBorderWidthLeft((float) 0.25);
            pagonomina.getDefaultCell().setBorderWidthRight((float) 0.25);
            pagonomina.getDefaultCell().setBorderWidthBottom((float) 0.25);
          
            
            pagonomina.addCell(new Paragraph(ne.getEmpleados().getGenPersonas().getDocumento(), fuente));
            pagonomina.getDefaultCell().setBorderWidthLeft(0);
            pagonomina.getDefaultCell().setBorderWidthRight((float) 0.25);
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

            pagonomina.getDefaultCell().setBorder(0);
            pagonomina.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            pagonomina.getDefaultCell().setBorderWidthLeft((float) 0.25);
            pagonomina.getDefaultCell().setBorderWidthRight((float) 0.25);
            pagonomina.getDefaultCell().setBorderWidthBottom((float) 0.25);
            pagonomina.getDefaultCell().setColspan(4);
            pagonomina.addCell(new Paragraph("Totales:", fuente));
            pagonomina.getDefaultCell().setBorderWidthLeft(0);
            pagonomina.getDefaultCell().setBorderWidthRight((float) 0.25);
            pagonomina.getDefaultCell().setColspan(1);
            pagonomina.addCell(new Paragraph(SihicApp.nomina.getTotalbasico().toBigInteger().toString(), fuente));
            pagonomina.addCell(new Paragraph(SihicApp.nomina.getTotalauxtransporte().toBigInteger().toString(), fuente));
            pagonomina.addCell(new Paragraph(SihicApp.nomina.getTotaldevengado().toBigInteger().toString(), fuente));
            pagonomina.addCell(new Paragraph(SihicApp.nomina.getTotalsalud().toBigInteger().toString(), fuente));
            pagonomina.addCell(new Paragraph(SihicApp.nomina.getTotalpension().toBigInteger().toString(), fuente));
            pagonomina.addCell(new Paragraph(SihicApp.nomina.getTotaldeducciones().toBigInteger().toString(), fuente));
            pagonomina.addCell(new Paragraph(SihicApp.nomina.getTotalsueldoneto().toBigInteger().toString(), fuente));
            pagonomina.getDefaultCell().setBorderWidthBottom(0);
            pagonomina.getDefaultCell().setBorderWidthTop(0);
            pagonomina.getDefaultCell().setBorderWidthLeft(0);
            pagonomina.getDefaultCell().setBorderWidthRight(0);
             pagonomina.addCell(new Paragraph("", fuente));
            document.add(pagonomina);
           fuente = FontFactory.getFont("Times-Roman", 10, Font.BOLD);     
        tabless.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tabless.getDefaultCell().setColspan(2);
        tabless.addCell(new Paragraph("Seguridad Social", fuente));
        fuente = FontFactory.getFont("Times-Roman", 10, Font.NORMAL);     
        tabless.getDefaultCell().setColspan(1);
        tabless.addCell(new Paragraph("Salud Empleador:", fuente));
        tabless.addCell(new Paragraph(SihicApp.nomina.getSs_salud().toBigInteger().toString(), fuente));
        tabless.addCell(new Paragraph("Fondo Pensiones:", fuente));
        tabless.addCell(new Paragraph(SihicApp.nomina.getSs_fondopensiones().toBigInteger().toString(), fuente));
        tabless.addCell(new Paragraph("ARL:", fuente));
        tabless.addCell(new Paragraph(SihicApp.nomina.getSs_arl().toBigInteger().toString(), fuente));
        tabless.addCell(new Paragraph("Total Seguridad Social", fuente));
        tabless.addCell(new Paragraph(SihicApp.nomina.getSs_salud().add(SihicApp.nomina.getSs_fondopensiones().add(SihicApp.nomina.getSs_arl())).toBigInteger().toString(), fuente));
        tablepadre.addCell(tabless);
        
        fuente = FontFactory.getFont("Times-Roman", 10, Font.BOLD);     
        tablepf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tablepf.getDefaultCell().setColspan(2);
        tablepf.addCell(new Paragraph("Parafiscales", fuente));
        fuente = FontFactory.getFont("Times-Roman", 10, Font.NORMAL);     
        tablepf.getDefaultCell().setColspan(1);
        tablepf.addCell(new Paragraph("Caja Comp. Fam::", fuente));
        tablepf.addCell(new Paragraph(SihicApp.nomina.getPf_cajacf().toBigInteger().toString(), fuente));
        tablepf.addCell(new Paragraph("ICBF:", fuente));
        tablepf.addCell(new Paragraph(SihicApp.nomina.getPf_icbf().toBigInteger().toString(), fuente));
        tablepf.addCell(new Paragraph("SENA:", fuente));
        tablepf.addCell(new Paragraph(SihicApp.nomina.getPf_sena().toBigInteger().toString(), fuente));
        tablepf.addCell(new Paragraph("Total Parafiscales", fuente));
        tablepf.addCell(new Paragraph(SihicApp.nomina.getPf_icbf().add(SihicApp.nomina.getPf_cajacf().add(SihicApp.nomina.getPf_sena())).toBigInteger().toString(), fuente));
        tablepadre.addCell(tablepf);
        
              fuente = FontFactory.getFont("Times-Roman", 10, Font.BOLD);     
        tableps.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableps.getDefaultCell().setColspan(2);
        tableps.addCell(new Paragraph("Prestaciones Sociales", fuente));
        fuente = FontFactory.getFont("Times-Roman", 10, Font.NORMAL);     
        tableps.getDefaultCell().setColspan(1);
        tableps.addCell(new Paragraph("Cesantías:", fuente));
        tableps.addCell(new Paragraph(SihicApp.nomina.getPp_cesantias().toBigInteger().toString(), fuente));
        tableps.addCell(new Paragraph("Intereses Cesantías:", fuente));
        tableps.addCell(new Paragraph(SihicApp.nomina.getPp_intcesantias().toBigInteger().toString(), fuente));
        tableps.addCell(new Paragraph("Prima:", fuente));
        tableps.addCell(new Paragraph(SihicApp.nomina.getPp_prima().toBigInteger().toString(), fuente));
        tableps.addCell(new Paragraph("Vacaciones:", fuente));
        tableps.addCell(new Paragraph(SihicApp.nomina.getPp_vacaciones().toBigInteger().toString(), fuente));
        tableps.addCell(new Paragraph("Total Prestaciones Sociales:", fuente));
        
        tableps.addCell(new Paragraph(SihicApp.nomina.getPp_cesantias().add(SihicApp.nomina.getPp_intcesantias().add(SihicApp.nomina.getPp_prima()).add(SihicApp.nomina.getPp_vacaciones())).toBigInteger().toString(), fuente));
        tablepadre.addCell(tableps);
        document.add(tablepadre);
        fuente = FontFactory.getFont("Times-Roman", 8, Font.NORMAL);
        Paragraph para3 = new Paragraph("\n\n\n\nElaboro: ________________________   Autoriza:____________________________", fuente);
            para3.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(para3);
        document.close();
        File file = new File("/home/adminlinux/sihic/comprobantenomina.pdf");
        try {
      String os = System.getProperty("os.name"); 
      if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
 { 
  Runtime r = Runtime.getRuntime(); 
  Process p = r.exec("/usr/bin/evince /home/adminlinux/sihic/comprobantenomina.pdf"); 
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
