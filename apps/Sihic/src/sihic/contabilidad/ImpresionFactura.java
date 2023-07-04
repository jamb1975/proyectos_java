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
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
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

import modelo.ComprobanteIngreso;
import modelo.LibroAuxiliar;
import modelo.Factura;
import modelo.FacturaItem;
import modelo.NotasEstudio;
import sihic.SihicApp;
import sihic.controlador.FacturaItemControllerClient;
import sihic.util.Cabecera;
import sihic.util.HeadReporteFactura;
import sihic.util.NumberToLetterConverter;

/**
 *
 * @author karolyani
 */
public class ImpresionFactura {

    private Factura factura;
    String cerosizq = "";
    String cerosizqcomprobante = "";
    Long nofactura = Long.MIN_VALUE;
    Long nocomprobante = Long.MIN_VALUE;
    private FacturaItemControllerClient facturaItemControllerClient = new FacturaItemControllerClient();

    public ImpresionFactura(Factura factura) {
        this.factura = factura;

    }

   

    public ImpresionFactura() {

    }

    public void fprintpdffactura(int opc) throws IOException {
        Document document = new Document();

        Font fuente = new Font();
        try {
            document = new Document(PageSize.LETTER, 20f, 20f, 180f, 20f);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/factura.pdf"));
            Rectangle rect = new Rectangle(PageSize.LETTER);
            writer.setBoxSize("art", rect);
            Cabecera encabezado = new Cabecera(factura);
            Paragraph parrafo;
            int i;

            encabezado.setEncabezado("Prueba de encabezado en iText");

            // indicamos que objecto manejara los eventos al escribir el Pdf
            writer.setPageEvent(encabezado);
            document.open();

            PdfPTable tableMaster = new PdfPTable(1);
            //PdfPTable table = new PdfPTable(new float[] { 5, 5});

            PdfPTable table = new PdfPTable(new float[]{10});
            PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
            PdfPTable tableCliente = new PdfPTable(new float[]{6, 7});
            PdfPTable tableFacturaItems = new PdfPTable(new float[]{2, 2, 5, 2, 2, 7, 2, 2, 2, 2, 2, 2});
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
            tableFacturaItems.getDefaultCell().setColspan(12);
            //tableFacturaItems.addCell(tableEntidad);

            tableFacturaItems.getDefaultCell().setColspan(0);
            table.getDefaultCell().setColspan(2);
            tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setPadding(0);
            //tableFacturaItems.getDefaultCell().setBorderWidthTop(1);

            fuente = new Font();
            fuente.setFamily("arial");
            fuente.setSize(9);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table.getDefaultCell().setPadding(5);
            int regs = 0;
            // table.addCell(tableFacturaItems);
            for (FacturaItem fi : factura.getFacturaItems()) {
                if (regs == 14) {
                    /*  table.addCell(tableFacturaItems);
              tableFacturaItems.flushContent();
              document.newPage();*/

                }

                tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                tableFacturaItems.addCell(new Paragraph(String.valueOf(setCeros(fi.getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId().toString()) + fi.getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(DateFormat.getDateInstance().format(fi.getConComprobanteProcedimiento().getFechaelaboracion())).replace("/", ""), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getGenPacientes().getGenPersonas().getNombres() + " Id. " + fi.getGenPacientes().getGenPersonas().getDocumento()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf("A" + fi.getGenPacientes().getGenPersonas().year()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getGenPacientes().getGenPersonas().getGenSexo().getSigla()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getNombre().length() <= 30 ? fi.getProducto().getNombre() : fi.getProducto().getNombre().substring(0, 30)), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getQuantity()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getValor_total()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf("")));
                tableFacturaItems.addCell(new Paragraph(String.valueOf("")));
                if (fi.getProducto().getHclCups() != null) {
                    try {

                        tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getHclCups().getCodigo().replace(".", "")), fuente));
                    } catch (Exception e) {
                        tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getCodigo().replace(".", "")), fuente));

                    }
                } else {
                    tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getCodigo().replace(".", "")), fuente));

                }
                tableFacturaItems.addCell(new Paragraph(String.valueOf("2")));

                regs++;
            }
            tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

            tableFacturaItems.getDefaultCell().setColspan(10);
            tableFacturaItems.addCell(new Paragraph("SubTotal: ", fuente));
            tableFacturaItems.addCell(new Paragraph(factura.getNetAmount().toString(), fuente));
            tableFacturaItems.addCell(new Paragraph("Iva: ", fuente));
            tableFacturaItems.addCell(new Paragraph(factura.getIva().toString(), fuente));
            tableFacturaItems.addCell(new Paragraph("Abonos/Copagos: ", fuente));
            tableFacturaItems.addCell(new Paragraph(factura.getDescuento().toString(), fuente));
            tableFacturaItems.addCell(new Paragraph("Valor Total: ", fuente));
            tableFacturaItems.addCell(new Paragraph(factura.getTotalAmount().toString(), fuente));
            tableFacturaItems.getDefaultCell().setColspan(12);
            tableFacturaItems.addCell(new Paragraph("Valor Total en Letras: " + sihic.util.NumberToLetterConverter.convertNumberToLetter(String.valueOf(factura.getTotalAmount())), fuente));
            table.addCell(tableFacturaItems);
            table.getDefaultCell().setColspan(2);

            fuente = FontFactory.getFont("arial", 5, Font.NORMAL);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            table.getDefaultCell().setPadding(5);
            table.addCell(new Paragraph("ESTA FACTURA CUMPLE CON TODOS LOS LOS REQUISITOS DE LA LEY SEGUN EL ART. 621 Y 774 DEL CODIGO DE COMERCIO Y 617 DEL ESTATUS TRIBUTARIO ESTA FACTURA ES UN TITULO Y SE ASIMILA EN TODOS SUS EFECTOS A UNA LETRA DE CAMBIO SEGUN ARTICULO 779 C.CIO. LEY 1231 DEL 2008 LEY 1231 DE JULIO DE 2008", fuente));

            table.getDefaultCell().setPadding(10);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            fuente = FontFactory.getFont("Times-Roman", 9, Font.BOLD);
            table.addCell(new Paragraph("Aceptada: ________________________  Vendedor: ________________________", fuente));
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
        if (opc == 1) {
            try {

//Desktop.getDesktop().open(file);
//Process p = Runtime.getRuntime().exec ("//home//adminlinux//sihic//factura.pdf");
            } catch (Exception evvv) {
                JOptionPane.showMessageDialog(null, "No se puede abrir el archivo de ayuda, probablemente fue borrado", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            //ProcessBuilder pb = new ProcessBuilder("java","-jar","/home/adminlinux/sihic/pdfviewercomprobante/PdfViewerFactura.jar");
            //ProcessBuilder pb = new ProcessBuilder("/home/adminlinux/sihic/factura.pdf");

//	        System.out.println("Run echo command");
//	        Process process = pb.start();
        }

    }

    public void fpdfcomprobanteprocedimiento(FacturaItem fi) throws IOException, BadElementException, DocumentException {
        List<FacturaItem> li_facturaitem = itemsfacturaprocedimiento(fi);//facturaItemControllerClient.getLi_facturaitems(fi.getConComprobanteProcedimiento());
        //Capturar especialidad
        String especialidad = "";
        BigDecimal total = BigDecimal.ZERO;
        for (FacturaItem fi2 : li_facturaitem) {
            if (fi2.getProducto().getHclCups() != null) {
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
        nocomprobante = fi.getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId();

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
        tableCliente.addCell(new Paragraph("Comprobante" + "\nNo. " + setCeros(cerosizq) + fi.getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId(), fuente));
        tableCliente.getDefaultCell().setColspan(1);
        tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableCliente.addCell(new Paragraph("Fecha: ", fuente));
        tableCliente.addCell(new Paragraph(DateFormat.getDateInstance().format(fi.getHclConsultas().getAgnCitas().getFechaHora()) + " Cargo a Factura: " + factura.getNofacturacerosizquierda(), fuente));
        try {
            if (factura.getPrefijo().substring(0,1).equals("A") || factura.getPrefijo().substring(0,1).equals("P")) {
                tableCliente.addCell(new Paragraph("Entidad: " + factura.getGenEapb().getNombre() + " Código: " + factura.getGenEapb().getCodigo(), fuente));

            } else {
                  if (factura.getPrefijo().substring(0,1).equals("T")) {
                tableCliente.addCell(new Paragraph("Entidad: " + fi.getGenPacientes().getAseguradora().getNombre() + " Código: " + fi.getGenPacientes().getAseguradora().getCodigo(), fuente));

            }
               else
                  {
                tableCliente.addCell(new Paragraph("Entidad: " + fi.getGenPacientes().getGenEapb().getNombre() + " Código: " + fi.getHclConsultas().getAgnCitas().getGenPacientes().getGenEapb().getCodigo(), fuente));
                  }
            }
        } catch (Exception e) {
            tableCliente.addCell(new Paragraph("Entidad: N  Código: N", fuente));

        }
        tableCliente.addCell(new Paragraph("Tipo Atención: " + "Ambulatoria", fuente));
        tableCliente.addCell(new Paragraph("Médico: " + fi.getHclConsultas().getAgnCitas().getAgnMedicos().getGenPersonas().getNombres(), fuente));
        tableCliente.addCell(new Paragraph(" Especialidad: " + especialidad, fuente));
        //table.getDefaultCell().setBorderWidthRight((float)0.25);
        tableCliente.addCell(new Paragraph("Paciente: " + fi.getGenPacientes().getGenPersonas().getNombres(), fuente));
        tableCliente.addCell(new Paragraph("No Documento: " + fi.getGenPacientes().getGenPersonas().getDocumento(), fuente));
        tableCliente.getDefaultCell().setColspan(2);
        String profesion;
        if (fi.getGenPacientes().getGenPersonas().getGen_profesiones() != null) {
            profesion = "Profesión: " + fi.getGenPacientes().getGenPersonas().getGen_profesiones().getCodigo();
        } else {
            profesion = "Profesión: No";
        }
        tableCliente.addCell(new Paragraph(profesion + " Tipo Usuario: " + fi.getGenPacientes().getGenTiposUsuarios().getNombre() + " Edad: A " + fi.getGenPacientes().getGenPersonas().year() + " Sexo: " + fi.getGenPacientes().getGenPersonas().getGenSexo().getSigla() + " Ciudad: " + fi.getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo() + " Zona: " + fi.getGenPacientes().getGenPersonas().getGen_zona_residencia().getCodigo(), fuente));
        tableCliente.addCell(new Paragraph("Fecha de Nacimiento: " + sihic.util.UtilDate.formatodiamesyear(fi.getGenPacientes().getGenPersonas().getFechaNacimiento()), fuente));

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

            Desktop.getDesktop().open(file);
//Process p = Runtime.getRuntime().exec ("//home//adminlinux//sihic//factura.pdf");

        } catch (Exception evvv) {

            JOptionPane.showMessageDialog(null, "No se puede abrir el archivo de ayuda, probablemente fue borrado", "ERROR", JOptionPane.ERROR_MESSAGE);

        }
        /*   ProcessBuilder pb = new ProcessBuilder("java","-jar","/home/adminlinux/sihic/pdfviewercomprobante/PdfViewer.jar");

	        System.out.println("Run echo command");

	        Process process = pb.start();*/

    }

    public static void fpdfcomprobanteingreso(ComprobanteIngreso conComprobanteIngreso) throws IOException, BadElementException, DocumentException {
        Document document = new Document();
        Font fuente = new Font();
         document = new Document(PageSize.LETTER, 2, 2, 2, 2);
        PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/comprobanteingreso.pdf"));
        document.open();
        PdfPTable tableMaster = new PdfPTable(1);
        //PdfPTable table = new PdfPTable(new float[] { 5, 5});

        PdfPTable table = new PdfPTable(new float[]{10});
        PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
        PdfPTable tableCliente = new PdfPTable(new float[]{6});
        PdfPTable tableFacturaItems = new PdfPTable(new float[]{2, 5, 1, 3});
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
        table.getDefaultCell().setPadding(10);
        table.setWidthPercentage(100f);

        table.addCell(tableImagenTexto);

        //tabla datos de cliente
        table.getDefaultCell().setPadding(15);
        tableCliente.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableCliente.getDefaultCell().setColspan(1);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);

        fuente = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
        tableCliente.getDefaultCell().setBorder(0);
        tableCliente.addCell(new Paragraph("Comprobante de ingreso" + "\nNo. " + conComprobanteIngreso.getNocomprobantecerosizquierda(), fuente));
        tableCliente.getDefaultCell().setColspan(2);
        tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        fuente = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
        tableCliente.addCell(new Paragraph("Fecha: " + DateFormat.getDateInstance().format(conComprobanteIngreso.getFechaelaboracion()), fuente));
        tableCliente.getDefaultCell().setColspan(2);
        String dir, tel, nombre, nit;
        if (conComprobanteIngreso.getFactura().getPrefijo().substring(0,1).equals("A") || conComprobanteIngreso.getFactura().getPrefijo().substring(0,1).equals("P")) {
            nit = conComprobanteIngreso.getFactura().getGenEapb().getNit();
            dir = conComprobanteIngreso.getFactura().getGenEapb().getDireccion();
            tel = conComprobanteIngreso.getFactura().getGenEapb().getTelefono();
            nombre = conComprobanteIngreso.getFactura().getGenEapb().getNombre();
        } else {
            nit = conComprobanteIngreso.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getDocumento();
            nombre = conComprobanteIngreso.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getNombres();
            dir = conComprobanteIngreso.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getDireccion();
            tel = conComprobanteIngreso.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getTelefono();
        }
        tableCliente.addCell(new Paragraph("Nit o CC: " + nit, fuente));
        tableCliente.getDefaultCell().setColspan(2);
        tableCliente.addCell(new Paragraph("Recibido: " + nombre, fuente));
        tableCliente.getDefaultCell().setColspan(2);
        tableCliente.addCell(new Paragraph("Dirección: " + dir, fuente));
        tableCliente.getDefaultCell().setColspan(2);
        tableCliente.addCell(new Paragraph("Telefono: " + tel, fuente));
        table.getDefaultCell().setBorderWidthRight((float) 0.25);
        tableCliente.addCell(new Paragraph("Concepto: " + SihicApp.notaContabilidad.getConcepto(), fuente));
        
        table.addCell(tableCliente);
        table.getDefaultCell().setColspan(2);
        tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setPadding(0);
        tableFacturaItems.addCell(new Paragraph("Cheque", fuente));
        tableFacturaItems.addCell(new Paragraph("Banco", fuente));
        tableFacturaItems.addCell(new Paragraph("Targeta", fuente));
        tableFacturaItems.addCell(new Paragraph("Valor", fuente));
        tableFacturaItems.getDefaultCell().setBorder(0);
        //tableFacturaItems.getDefaultCell().setBorderWidthTop(1);
        fuente = new Font();

        tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableFacturaItems.getDefaultCell().setBorderWidthLeft((float) 0.25);
        tableFacturaItems.getDefaultCell().setBorderWidthRight((float) 0.25);
        tableFacturaItems.getDefaultCell().setBorderWidthBottom((float) 0.25);
        tableFacturaItems.addCell(new Paragraph(conComprobanteIngreso.getNocheque(), fuente));
        tableFacturaItems.getDefaultCell().setBorderWidthLeft(0);
        tableFacturaItems.getDefaultCell().setBorderWidthRight((float) 0.25);
        tableFacturaItems.addCell(new Paragraph(conComprobanteIngreso.getBanco(), fuente));
        tableFacturaItems.addCell(new Paragraph(conComprobanteIngreso.getNotargeta(), fuente));
        tableFacturaItems.addCell(new Paragraph(conComprobanteIngreso.getValor().toString(), fuente));
        tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(tableFacturaItems);

        table.getDefaultCell().setBorder(0);
        table.addCell(new Paragraph("\n\n", fuente));
        table.addCell(new Paragraph("\n\n", fuente));
        table.addCell(new Paragraph("Firma de Recibido: ________________________\n", fuente));
        tableMaster.addCell(table);
        document.add(tableMaster);
        // step 5
        document.close();
File file = new File("/home/adminlinux/sihic/comprobanteingreso.pdf");
String os = System.getProperty("os.name"); 
      if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
 { 
  Runtime r = Runtime.getRuntime(); 
  Process p = r.exec("/usr/bin/evince /home/adminlinux/sihic/comprobanteingreso.pdf"); 
  }
else
 {
      if (!Desktop.isDesktopSupported() 
 && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) { 
    Desktop.getDesktop().open(file);
 }
 }

    }

    public void fpdfrecibocaja(ComprobanteIngreso conComprobanteIngreso) throws IOException, BadElementException, DocumentException {
        Document document = new Document();
        Font fuente = new Font();
        nocomprobante = conComprobanteIngreso.getNo_consecutivo();
        document = new Document(PageSize.LETTER, 2, 2, 2, 2);
        PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/comprobante.pdf"));
        document.open();
        PdfPTable tableMaster = new PdfPTable(1);
        //PdfPTable table = new PdfPTable(new float[] { 5, 5});

        PdfPTable table = new PdfPTable(new float[]{10});
        PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
        PdfPTable tableCliente = new PdfPTable(new float[]{6});
        PdfPTable tableFacturaItems = new PdfPTable(new float[]{2, 5, 1, 3});
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
        table.getDefaultCell().setPadding(10);
        table.setWidthPercentage(100f);

        table.addCell(tableImagenTexto);

        //tabla datos de cliente
        table.getDefaultCell().setPadding(15);
        tableCliente.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableCliente.getDefaultCell().setColspan(1);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);

        fuente = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
        tableCliente.getDefaultCell().setBorder(0);
        tableCliente.addCell(new Paragraph("Recibo de Caja" + "\nNo. " + SihicApp.notaContabilidad.getNocomprobantecerosizquierda(), fuente));
        tableCliente.getDefaultCell().setColspan(2);
        tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        fuente = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
        tableCliente.addCell(new Paragraph("Fecha: " + DateFormat.getDateInstance().format(SihicApp.notaContabilidad.getFechaelaboracion()), fuente));
        tableCliente.getDefaultCell().setColspan(2);
        String dir, tel, nombre, nit;

        nit = conComprobanteIngreso.getFacturaItem().getGenPacientes().getGenPersonas().getDocumento();
        dir = conComprobanteIngreso.getFacturaItem().getGenPacientes().getGenPersonas().getDireccion();
        tel = conComprobanteIngreso.getFacturaItem().getGenPacientes().getGenPersonas().getTelefono();
        nombre = conComprobanteIngreso.getFacturaItem().getGenPacientes().getGenPersonas().getNombres();

        tableCliente.addCell(new Paragraph("Nit o CC: " + nit, fuente));
        tableCliente.getDefaultCell().setColspan(2);
        tableCliente.addCell(new Paragraph("Recibido: " + nombre, fuente));
        tableCliente.getDefaultCell().setColspan(2);
        tableCliente.addCell(new Paragraph("Dirección: " + dir, fuente));
        tableCliente.getDefaultCell().setColspan(2);
        tableCliente.addCell(new Paragraph("Telefono: " + tel, fuente));
        table.getDefaultCell().setBorderWidthRight((float) 0.25);
        tableCliente.addCell(new Paragraph("Concepto: " + SihicApp.notaContabilidad.getConcepto(), fuente));

        table.addCell(tableCliente);
        table.getDefaultCell().setColspan(2);
        tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setPadding(0);
        tableFacturaItems.addCell(new Paragraph("Cheque", fuente));
        tableFacturaItems.addCell(new Paragraph("Banco", fuente));
        tableFacturaItems.addCell(new Paragraph("Targeta", fuente));
        tableFacturaItems.addCell(new Paragraph("Valor", fuente));
        tableFacturaItems.getDefaultCell().setBorder(0);
        //tableFacturaItems.getDefaultCell().setBorderWidthTop(1);
        fuente = new Font();

        tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableFacturaItems.getDefaultCell().setBorderWidthLeft((float) 0.25);
        tableFacturaItems.getDefaultCell().setBorderWidthRight((float) 0.25);
        tableFacturaItems.getDefaultCell().setBorderWidthBottom((float) 0.25);
        tableFacturaItems.addCell(new Paragraph(conComprobanteIngreso.getNocheque(), fuente));
        tableFacturaItems.getDefaultCell().setBorderWidthLeft(0);
        tableFacturaItems.getDefaultCell().setBorderWidthRight((float) 0.25);
        tableFacturaItems.addCell(new Paragraph(conComprobanteIngreso.getBanco(), fuente));
        tableFacturaItems.addCell(new Paragraph(conComprobanteIngreso.getNotargeta(), fuente));
        tableFacturaItems.addCell(new Paragraph(conComprobanteIngreso.getValor().toString(), fuente));
        tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table.addCell(tableFacturaItems);

        table.getDefaultCell().setBorder(0);
        table.addCell(new Paragraph("", fuente));
        table.addCell(new Paragraph("", fuente));
        table.addCell(new Paragraph("Firma de Recibido: ________________________\n", fuente));
        tableMaster.addCell(table);
        document.add(tableMaster);
        // step 5
        document.close();

        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "/home/adminlinux/sihic/pdfviewercomprobante/PdfViewer.jar");

        System.out.println("Run echo command");

        Process process = pb.start();

    }

    private String setCeros(String numero) {
        cerosizqcomprobante = "";
        for (int i = 4; i > numero.toString().length(); i--) {
            cerosizqcomprobante = cerosizqcomprobante + "0";
        }
        return cerosizqcomprobante;
    }

  /*  public void pdfviewercomprobante() throws IOException {
        Document document = new Document();
        Font fuente = new Font();
        try {
            document = new Document(PageSize.LETTER.rotate(), 10, 10, 10, 10);

            PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/comprobantediario.pdf"));

            document.open();

            PdfPTable tableMaster = new PdfPTable(1);
            //PdfPTable table = new PdfPTable(new float[] { 5, 5});
            for (int i = 0; i < 2; i++) {

                PdfPTable table = new PdfPTable(new float[]{10});
                PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
                PdfPTable tableCliente = new PdfPTable(new float[]{6, 7});
                PdfPTable tableFacturaItems = new PdfPTable(new float[]{2, 4, 5, 3, 3, 3});
                Image imagen;
                tableMaster.getDefaultCell().setBorderWidth(0);
                tableImagenTexto.getDefaultCell().setBorder(0);
                imagen = Image.getInstance("/home/adminlinux/sihic/logo.png");
                tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

                tableImagenTexto.getDefaultCell().setColspan(2);
                tableImagenTexto.getDefaultCell().setFixedHeight(50f);

                tableImagenTexto.addCell(imagen);

                tableImagenTexto.getDefaultCell().setFixedHeight(0f);

                //  tableImagenTexto.getDefaultCell().setFixedHeight(0f);
                //tableImagenTexto.getDefaultCell().setColspan(2);
                fuente = FontFactory.getFont("Times-Roman", 11, Font.BOLD);

                tableImagenTexto.addCell(new Paragraph("CENTRO MÉDICO DEL GUAVIARE SAS", fuente));
                fuente = FontFactory.getFont("Times-Roman", 8, Font.BOLD);
                tableImagenTexto.addCell(new Paragraph("Nit.: 900.767.863 ", fuente));
                tableImagenTexto.addCell(new Paragraph("CRA 22 N° 11-18 B. LA ESPERANZA TEL. 3134212162 ", fuente));
                tableImagenTexto.addCell(new Paragraph("SAN JOSE DEL GUAVIARE", fuente));

                table.getDefaultCell().setPadding(10);
                table.setWidthPercentage(100f);
                table.addCell(tableImagenTexto);

                //tabla datos de cliente
                table.getDefaultCell().setPadding(7);

                tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                // tableEntidad.getDefaultCell().setColspan(4);
                table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);

                fuente = FontFactory.getFont("Times-Roman", 10, Font.BOLD);
                tableCliente.getDefaultCell().setBorder(0);
                tableCliente.addCell(new Paragraph("Fecha Elab: " + DateFormat.getDateInstance().format(conComprobanteContabilidad.getFechaelaboracion()), fuente));
                tableCliente.addCell(new Paragraph("Comprobante Diario" + "\nNo. " + setCeros(conComprobanteContabilidad.getConsecutivoComprobanteContabilidad().getId().toString()) + conComprobanteContabilidad.getConsecutivoComprobanteContabilidad().getId().toString() + "\nNit: 17266911 Tel: 3102030435 Ciudad: Cumaral-Meta", fuente));

                fuente = FontFactory.getFont("Times-Roman", 9, Font.NORMAL);
                table.addCell(tableCliente);
                table.getDefaultCell().setColspan(2);
                tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                table.getDefaultCell().setPadding(0);
                tableFacturaItems.getDefaultCell().setBorder(0);
                tableFacturaItems.addCell(new Paragraph("Código", fuente));
                tableFacturaItems.addCell(new Paragraph("Cuenta", fuente));
                tableFacturaItems.addCell(new Paragraph("Detalle", fuente));
                tableFacturaItems.addCell(new Paragraph("Parciales", fuente));
                tableFacturaItems.addCell(new Paragraph("Debe", fuente));
                tableFacturaItems.addCell(new Paragraph("Haber", fuente));
                tableFacturaItems.getDefaultCell().setBorder(0);
                //tableFacturaItems.getDefaultCell().setBorderWidthTop(1);
                fuente = new Font();

                fuente.setSize(9);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                table.getDefaultCell().setPadding(5);
                tableFacturaItems.getDefaultCell().setBorderWidthTop(1);
                tableFacturaItems.getDefaultCell().setBorderWidthLeft(1);
                tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                for (LibroAuxiliar conDetallesComprobanteContabilidad : conComprobanteContabilidad.getDetallesComprobanteContabilidads()) {
                    tableFacturaItems.getDefaultCell().setBorderWidthBottom(0);
                    tableFacturaItems.getDefaultCell().setBorderWidthRight(0);
                    tableFacturaItems.addCell(new Paragraph(conDetallesComprobanteContabilidad.getConPuc().getCodigo(), fuente));
                    tableFacturaItems.getDefaultCell().setBorderWidthLeft(0);
                    tableFacturaItems.addCell(new Paragraph(conDetallesComprobanteContabilidad.getConPuc().getNombre(), fuente));
                    tableFacturaItems.addCell(new Paragraph(conDetallesComprobanteContabilidad.getDescripcion(), fuente));
                    tableFacturaItems.addCell(new Paragraph(conDetallesComprobanteContabilidad.getParcialdebe().toString(), fuente));
                    tableFacturaItems.addCell(new Paragraph(conDetallesComprobanteContabilidad.getParcialdebe().toString(), fuente));
                    tableFacturaItems.addCell(new Paragraph(conDetallesComprobanteContabilidad.getParcialhaber().toString(), fuente));
                    tableFacturaItems.getDefaultCell().setBorderWidthRight(1);
                    tableFacturaItems.addCell(new Paragraph(conDetallesComprobanteContabilidad.getHaber().toString(), fuente));
                    tableFacturaItems.getDefaultCell().setBorderWidthLeft(1);
                    tableFacturaItems.getDefaultCell().setBorderWidthTop(0);

                }
                tableFacturaItems.getDefaultCell().setBorderWidthTop(1);
                tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
                tableFacturaItems.getDefaultCell().setBorder(1);
                tableFacturaItems.getDefaultCell().setColspan(6);
                tableFacturaItems.addCell(new Paragraph("", fuente));
                table.addCell(tableFacturaItems);
                table.getDefaultCell().setColspan(2);

                fuente = FontFactory.getFont("Times-Roman", 5, Font.NORMAL);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                table.getDefaultCell().setPadding(5);
                table.getDefaultCell().setPadding(10);
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                fuente = FontFactory.getFont("Times-Roman", 9, Font.BOLD);
                table.addCell(new Paragraph("Reviso: ________________________  Autorizo: ________________________", fuente));
                tableMaster.addCell(table);

            }
            document.add(tableMaster);

            // step 5
            document.close();
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        ProcessBuilder pb = new ProcessBuilder("java", "-jar", "/home/adminlinux/sihic/pdfviewercomprobante/PdfViewerComprobanteDiario.jar");

        System.out.println("Run echo command");

        Process process = pb.start();

    }*/

    public void fpdfnotasestudio(NotasEstudio ne, int tipo) throws IOException, BadElementException, DocumentException {

        Document document = new Document();
        Font fuente = new Font();
        Font fuenteN = new Font();
        String numerofactura = ne.getFacturaItem().getFactura().getNofacturacerosizquierda();
        String especialidad;
        try {
            try {

                especialidad = ne.getFacturaItem().getProducto().getAgnEspecialidades().getNombre() + " Código: " + ne.getFacturaItem().getProducto().getAgnEspecialidades().getCodigo();
            } catch (Exception e) {
                especialidad = "No";
            }

            document = new Document(PageSize.LETTER, 20, 20, 20, 20);
            PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/notasestudio.pdf"));
            document.open();

            //PdfPTable table = new PdfPTable(new float[] { 5, 5});
            PdfPTable tableImagenTexto = new PdfPTable(new float[]{10});
            PdfPTable tableEntidad = new PdfPTable(new float[]{6, 7});
            PdfPTable tableGeneral = new PdfPTable(new float[]{7, 5});
            PdfPTable tableTiempo = new PdfPTable(new float[]{3, 3, 3});
            PdfPTable tablePaciente = new PdfPTable(new float[]{2, 2, 2, 2, 2, 2, 2, 1});
            PdfPTable tableFacturaItems = new PdfPTable(new float[]{2, 4, 2, 2, 2});
            PdfPTable tabledx = new PdfPTable(new float[]{2f, 8});
            Image imagen;
            tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            imagen = Image.getInstance(SihicApp.genUnidadesOrganizacion.getLogo());
            tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
            tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
            tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
            tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
            tableImagenTexto.getDefaultCell().setFixedHeight(40f);
            tableImagenTexto.addCell(imagen);

            tableImagenTexto.getDefaultCell().setFixedHeight(0f);
            tableEntidad.setWidthPercentage(100f);
            tableFacturaItems.setWidthPercentage(100f);
            tableGeneral.setWidthPercentage(100f);
            tableTiempo.setWidthPercentage(100f);
            tablePaciente.setWidthPercentage(100f);
            tabledx.setWidthPercentage(100f);
            tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
            tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
            tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
            tableImagenTexto.getDefaultCell().setBorderWidthRight(0);

            fuente = FontFactory.getFont("arial", 14, Font.BOLD);
            fuenteN = FontFactory.getFont("arial", 12, Font.BOLD);
            tableImagenTexto.addCell(new Paragraph("CENTRO MÉDICO DEL GUAVIARE SAS", fuente));
            fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
            tableImagenTexto.getDefaultCell().setColspan(2);
            tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableImagenTexto.addCell(new Paragraph("Nit.: 900.767.863 ", fuente));
            tableImagenTexto.addCell(new Paragraph("CRA 22 N° 11-18 B. LA ESPERANZA TEL. 3134212162 ", fuente));
            tableImagenTexto.addCell(new Paragraph("SAN JOSE DEL GUAVIARE", fuente));
            document.add(tableImagenTexto);

            tableEntidad.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
            tableEntidad.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            fuente = FontFactory.getFont("arial", 9, Font.NORMAL);
            tableEntidad.getDefaultCell().setBorder(0);
            try {
                tableEntidad.addCell(new Paragraph("Entidad: " + ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenEapb().getNombre() + " Código: " + ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenEapb().getCodigo(), fuente));
            } catch (Exception e) {
                tableEntidad.addCell(new Paragraph("Entidad: N  Código: N", fuente));

            }
            tableEntidad.addCell(new Paragraph("Factura" + "No. " + numerofactura, fuente));
            try
            {
            tableEntidad.addCell(new Paragraph("Médico: " + ne.getFacturaItem().getAgnCitas().getAgnMedicos()!=null?ne.getFacturaItem().getAgnCitas().getAgnMedicos().getGenPersonas().getNombres():"N", fuente));
            tableEntidad.addCell(new Paragraph("Especialidad: " + especialidad, fuente));
            tableEntidad.addCell(new Paragraph("F. Pago: Ambulatorio ", fuente));
            tableEntidad.addCell(new Paragraph("Autorización:  " + ne.getFacturaItem().getNoautorizacion() + " Afiliación: " + ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenTiposAfiliacion()!=null?ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenTiposAfiliacion().getDescripcion():"N", fuente));
            }catch(Exception e)
            {
                e.printStackTrace();
               tableEntidad.addCell(new Paragraph("Médico: " + ne.getFacturaItem().getHclConsultas().getAgnCitas().getAgnMedicos().getGenPersonas().getNombres(), fuente));
               tableEntidad.addCell(new Paragraph("Especialidad: " + especialidad, fuente));
              tableEntidad.addCell(new Paragraph("F. Pago: Ambulatorio ", fuente));
               tableEntidad.addCell(new Paragraph("Autorización:  " + ne.getFacturaItem().getHclConsultas().getNumeroautorizacion() + " Afiliación: " + ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenTiposAfiliacion().getDescripcion(), fuente));
   
            }
            document.add(tableEntidad);
            //table paciente items
            tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableFacturaItems.addCell(new Paragraph("CODIGO", fuente));
            tableFacturaItems.addCell(new Paragraph("DESCRIPCIÓN", fuente));
            tableFacturaItems.addCell(new Paragraph("CANT", fuente));
            tableFacturaItems.addCell(new Paragraph("VALOR / U", fuente));
            tableFacturaItems.addCell(new Paragraph("VALOR TOTAL", fuente));
            BigDecimal totalfactura = BigDecimal.ZERO;
            for (FacturaItem fi2 : ne.getFacturaItem().getFactura().getFacturaItems()) {
                if (fi2.getConComprobanteProcedimiento().equals(ne.getFacturaItem().getConComprobanteProcedimiento())) {
                    tableFacturaItems.addCell(new Paragraph(ne.getFacturaItem().getProducto().getCodigo(), fuente));
                    tableFacturaItems.addCell(new Paragraph(ne.getFacturaItem().getProducto().getNombre(), fuente));
                    tableFacturaItems.addCell(new Paragraph(String.valueOf(ne.getFacturaItem().getQuantity()), fuente));
                    tableFacturaItems.addCell(new Paragraph(ne.getFacturaItem().getValor_u().toString(), fuente));
                    tableFacturaItems.addCell(new Paragraph(ne.getFacturaItem().getValor_total().toString(), fuente));
                    totalfactura = totalfactura.add(fi2.getValor_total());
                }
            }
            tableFacturaItems.getDefaultCell().setColspan(4);
            tableFacturaItems.addCell(new Paragraph("Total Factura:", fuente));
            tableFacturaItems.addCell(new Paragraph(totalfactura.toString(), fuente));
            tableFacturaItems.getDefaultCell().setColspan(5);
            tableFacturaItems.addCell(new Paragraph("Detalle:", fuente));
            document.add(tableFacturaItems);
            document.add(new Paragraph("\n"));
            tableGeneral.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableGeneral.getDefaultCell().setBorder(0);
            tableGeneral.addCell(new Paragraph("Recibí el servicio a satisfacción: ____________________________", fuente));
            tableGeneral.addCell(new Paragraph("Elaboro: " + ne.getElaboro(), fuente));

            document.add(tableGeneral);
            document.add(new Paragraph("\n"));
            //TableTiempo
            switch (ne.getTipo()) {

                case 0:
                    tableTiempo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    tableTiempo.getDefaultCell().setBorderWidth(0);
                    tableTiempo.addCell(new Paragraph("Hora de Ingreso", fuente));
                    tableTiempo.addCell(new Paragraph("Hora de Atención", fuente));
                    tableTiempo.addCell(new Paragraph("Fecha", fuente));
                    tableTiempo.addCell(new Paragraph(String.valueOf(ne.getHoraingreso()) + ":" + ne.getMinutosingreso(), fuente));
                    tableTiempo.addCell(new Paragraph(String.valueOf(ne.getHoraatencion()) + ":" + ne.getMinutosatencion(), fuente));
                    try
                    {
                    tableTiempo.addCell(new Paragraph(sihic.util.UtilDate.s_formatoyearmesdia(ne.getFacturaItem().getHclConsultas().getAgnCitas().getFechaIngreso()), fuente));
                    }
                    catch(Exception e)
                    {
                        try
                        {
                        tableTiempo.addCell(new Paragraph(sihic.util.UtilDate.s_formatoyearmesdia(ne.getFacturaItem().getAgnCitas().getFechaIngreso()), fuente));
                        }
                        catch(Exception e2)
                        {
                              tableTiempo.addCell(new Paragraph(sihic.util.UtilDate.s_formatoyearmesdia(ne.getFacturaItem().getAgnCitas().getFechaHora()), fuente));
                      
                        }
                    }
                    document.add(tableTiempo);
                    document.add(new Paragraph("\n"));
                    //tablepaciente;
                    tablePaciente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("NOMBRE DEL PACIENTE", fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph("DOCUMENTO", fuente));
                    tablePaciente.addCell(new Paragraph("EMB", fuente));
                    tablePaciente.addCell(new Paragraph("EDAD", fuente));
                    tablePaciente.addCell(new Paragraph("SEXO", fuente));
                    tablePaciente.addCell(new Paragraph("CIUDAD", fuente));
                    tablePaciente.addCell(new Paragraph("ZONA", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    try
                    {
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getNombres(), fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento(), fuente));
                    tablePaciente.addCell(new Paragraph(ne.isEmbarazada() == false ? "No" : "Si", fuente));
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().year(), fuente));
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla(), fuente));
                    try{
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getNombre(), fuente));
                    }catch(Exception e)
                    {
                    } 
                        tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_zona_residencia().getDescripcion(), fuente));
                    
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Dirección", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Cod Ocupación", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph("Tipo de Usuario", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getDireccion(), fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones() != null ? ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones().getCodigo() : "No", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenTiposUsuarios().getNombre(), fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Ocupación", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Telefono", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph("Hora de Egreso", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones() != null ? ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones().getDescripcion() : "No", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getTelefono(), fuente));
                    }catch(Exception e)
                    {
                        tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getNombres(), fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento(), fuente));
                    tablePaciente.addCell(new Paragraph(ne.isEmbarazada() == false ? "No" : "Si", fuente));
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().year(), fuente));
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla(), fuente));
                   
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getNombre(), fuente));
                    
                        tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGen_zona_residencia().getDescripcion(), fuente));
                    
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Dirección", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Cod Ocupación", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph("Tipo de Usuario", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDireccion(), fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones() != null ? ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones().getCodigo() : "No", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenTiposUsuarios().getNombre(), fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Ocupación", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Telefono", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph("Hora de Egreso", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones() != null ? ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones().getDescripcion() : "No", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getTelefono(), fuente));
                    
                    }
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph(ne.getHoraegreso() + ":" + ne.getMinutosegreso(), fuente));
                    tablePaciente.getDefaultCell().setColspan(8);
                    tablePaciente.addCell(new Paragraph("Acompañante: " + ne.getAcompanante(), fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph("Paciente Llego", fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph("Estado de Salida", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Signos Vitales al Ingreso", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Signos Vitales al Egreso", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Peso: " + ne.getPeso(), fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph("Consciente: " + (ne.getEstadosalida() == jenum.EnumEstadoSalida.VIVO.ordinal() ? "X" : ""), fuente));
                    tablePaciente.addCell(new Paragraph("Vivo: " + (ne.getEstadosalida() == jenum.EnumEstadoSalida.VIVO.ordinal() ? "X" : ""), fuente));
                    tablePaciente.addCell(new Paragraph("T/A:" + ne.getSvingresota(), fuente));
                    tablePaciente.addCell(new Paragraph("MMHG", fuente));
                    tablePaciente.addCell(new Paragraph("T/A:" + ne.getSvegresota(), fuente));
                    tablePaciente.addCell(new Paragraph("MMHG/DL", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.getDefaultCell().setRowspan(3);
                    tablePaciente.addCell(new Paragraph("Kg", fuente));
                    tablePaciente.getDefaultCell().setRowspan(1);
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph("Inconsciente: " + (ne.getPacientellego() == jenum.EnumPacienteLlega.INCONCIENTE.ordinal() ? "X" : ""), fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph("Muerto: " + (ne.getEstadosalida() == jenum.EnumEstadoSalida.MUERTO.ordinal() ? "X" : ""), fuente));
                    tablePaciente.addCell(new Paragraph("FC: " + ne.getSvingresofc(), fuente));
                    tablePaciente.addCell(new Paragraph("LPM", fuente));
                    tablePaciente.addCell(new Paragraph("FC: " + ne.getSvegresofc(), fuente));
                    tablePaciente.addCell(new Paragraph("LPM", fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph("Muerto: " + (ne.getPacientellego() == jenum.EnumPacienteLlega.INCONCIENTE.ordinal() ? "X" : ""), fuente));
                    tablePaciente.addCell(new Paragraph("Remitido: " + (ne.getEstadosalida() == jenum.EnumEstadoSalida.MUERTO.ordinal() ? "X" : ""), fuente));
                    tablePaciente.addCell(new Paragraph("SpO2: " + ne.getSvingresospo2(), fuente));
                    tablePaciente.addCell(new Paragraph("%", fuente));
                    tablePaciente.addCell(new Paragraph("SpO2: " + ne.getSvegresospo2(), fuente));
                    tablePaciente.addCell(new Paragraph("%", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("", fuente));
                    document.add(tablePaciente);
                    tabledx.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    tabledx.getDefaultCell().setBorder(0);
                    tabledx.addCell(new Paragraph("Diagnóstico: ", fuenteN));
                    tabledx.addCell(new Paragraph(ne.getHclCie10() != null ? ne.getHclCie10().getDescripcion() : "", fuente));
                    tabledx.addCell((new Paragraph("Estudio: ", fuenteN)));
                    tabledx.addCell(new Paragraph(ne.getFacturaItem().getProducto().getNombre() + " Tipo  Contrastada", fuente));
                    tabledx.addCell(new Paragraph("Nota Enfermería: ", fuenteN));
                    tabledx.addCell(new Paragraph(ne.getNotaenfermeria(), fuente));
                    //tabledx.addCell(new Paragraph("Ingresa paciente para realización de " + ne.getFacturaItem().getProducto().getNombre() + " Contrastada ordenado por médico tratante, paciente consciente, orientado, colaborador, en compañía de familiar, Medico Radiólogo brinda toda la información sobre el procedimiento, despeja dudas paciente manifiesta entender y acepta, firma consentimiento informado. En área de preparación del paciente  se toman signos vitales, previa asepsia y antisepsia de canaliza vena periférica en miembro superior " + ne.getMiembrosuperior() + " con catether de seguridad Jelco # " + ne.getJelco() + ", queda con catether heparinizado, se permeabiliza vena con SSN 0.9 % 10 ml IV, se traslada paciente al área de tomografía para toma del estudio, se administra medio de contraste IOPRAMIDA " + ne.getCantiopramida() + " ml IV según protocolo sin presentar ninguna complicación y/o evento adverso. Finaliza procedimiento sin complicaciones, se toman signos vitales encontrándose dentro de los parámetros normales, paciente en reposo por 20 minutos en la camilla continua con signo vitales parámetros normales, se retira venopunción. Se hace entrega de boleta para solicitar resultado del estudio, pendiente lectura. Se entrega y explica al paciente y a su acompañante, el instructivo cuidado pos toma de estudios con medio de contraste, se despejan dudas.  Egresa paciente en buen estado general en compañía del familiar. ", fuente));
                    tabledx.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    tabledx.getDefaultCell().setColspan(2);
                    tabledx.addCell(new Paragraph("\n"));
                    tabledx.addCell(new Paragraph("\n"));
                    tabledx.addCell(new Paragraph("\n"));
                    tabledx.addCell(new Paragraph("\n"));

                    tabledx.addCell(new Paragraph("__________________________   _____________________    ______________________________", fuente));
                    tabledx.addCell(new Paragraph("FIRMA AUXILIAR ENFERMERÍA     FIRMA MÉDICO GENERAL     FIRMA TECNÓLOGO RADIOLOGÍA", fuente));
                    document.add(tabledx);
                    break;
                case 1:
                    tableTiempo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    tableTiempo.getDefaultCell().setBorderWidth(0);
                    tableTiempo.addCell(new Paragraph("Hora de Ingreso", fuente));
                    tableTiempo.addCell(new Paragraph("Hora de Atención", fuente));
                    tableTiempo.addCell(new Paragraph("Fecha", fuente));
                    tableTiempo.addCell(new Paragraph(String.valueOf(ne.getHoraingreso()) + ":" + ne.getMinutosingreso(), fuente));
                    tableTiempo.addCell(new Paragraph(String.valueOf(ne.getHoraatencion()) + ":" + ne.getMinutosatencion(), fuente));
                    try
                    {
                    tableTiempo.addCell(new Paragraph(sihic.util.UtilDate.s_formatoyearmesdia(ne.getFacturaItem().getAgnCitas().getFechaHora()), fuente));
                    }catch(Exception e)
                    {
                      // e.printStackTrace();
                       tableTiempo.addCell(new Paragraph(sihic.util.UtilDate.s_formatoyearmesdia(ne.getFacturaItem().getHclConsultas().getAgnCitas().getFechaHora()), fuente));
                 
                    }
                    document.add(tableTiempo);
                    document.add(new Paragraph("\n"));
                    //tablepaciente;
                    tablePaciente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("NOMBRE DEL PACIENTE", fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph("DOCUMENTO", fuente));
                    tablePaciente.addCell(new Paragraph("EMB", fuente));
                    tablePaciente.addCell(new Paragraph("EDAD", fuente));
                    tablePaciente.addCell(new Paragraph("SEXO", fuente));
                    tablePaciente.addCell(new Paragraph("CIUDAD", fuente));
                    tablePaciente.addCell(new Paragraph("ZONA", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                   try
                   {
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getNombres(), fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getGenPacientes().getGenPersonas().getDocumento(), fuente));
                    tablePaciente.addCell(new Paragraph(ne.isEmbarazada() == false ? "No" : "Si", fuente));
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().year(), fuente));
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla(), fuente));
                    try{
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getNombre(), fuente));
                    
                    }catch(Exception e)
                    {
                        
                    } 
                    try
                    {
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_zona_residencia().getDescripcion(), fuente));
                    }catch(Exception e)
                    {
                        
                    } 
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Dirección", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Cod Ocupación", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph("Tipo de Usuario", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getDireccion(), fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones() != null ? ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones().getCodigo() : "No", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenTiposUsuarios().getNombre(), fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Ocupación", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Telefono", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph("Hora de Egreso", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones() != null ? ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones().getDescripcion() : "No", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getTelefono(), fuente));
                   }catch(Exception e)
                   {
                       tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getNombres(), fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getGenPacientes().getGenPersonas().getDocumento(), fuente));
                    tablePaciente.addCell(new Paragraph(ne.isEmbarazada() == false ? "No" : "Si", fuente));
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().year(), fuente));
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla(), fuente));
                   
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getNombre(), fuente));
                    
                   
                   
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGen_zona_residencia().getDescripcion(), fuente));
                   
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Dirección", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Cod Ocupación", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph("Tipo de Usuario", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDireccion(), fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones() != null ? ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones().getCodigo() : "No", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenTiposUsuarios().getNombre(), fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Ocupación", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Telefono", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph("Hora de Egreso", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones() != null ? ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones().getDescripcion() : "No", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getTelefono(), fuente));
                    
                   }
                    
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph(ne.getHoraegreso() + ":" + ne.getMinutosegreso(), fuente));
                    tablePaciente.getDefaultCell().setColspan(8);
                    tablePaciente.addCell(new Paragraph("Acompañante: " + ne.getAcompanante(), fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph("Paciente Llego", fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph("Estado de Salida", fuente));
                    tablePaciente.getDefaultCell().setColspan(6);
                    tablePaciente.addCell(new Paragraph("Signos Vitales al Ingreso", fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph("Consciente: " + (ne.getEstadosalida() == jenum.EnumEstadoSalida.VIVO.ordinal() ? "X" : ""), fuente));
                    tablePaciente.addCell(new Paragraph("Vivo: " + (ne.getEstadosalida() == jenum.EnumEstadoSalida.VIVO.ordinal() ? "X" : ""), fuente));
                    tablePaciente.getDefaultCell().setColspan(3);
                    tablePaciente.addCell(new Paragraph("T/A:" + ne.getSvingresota(), fuente));
                    tablePaciente.addCell(new Paragraph("MM/HG", fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph("Inconsciente: " + (ne.getPacientellego() == jenum.EnumPacienteLlega.INCONCIENTE.ordinal() ? "X" : ""), fuente));
                    tablePaciente.addCell(new Paragraph("Muerto: " + (ne.getEstadosalida() == jenum.EnumEstadoSalida.MUERTO.ordinal() ? "X" : ""), fuente));
                    tablePaciente.getDefaultCell().setColspan(3);
                    tablePaciente.addCell(new Paragraph("FC: " + ne.getSvingresofc(), fuente));
                    tablePaciente.addCell(new Paragraph("LPM", fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph("Muerto: " + (ne.getPacientellego() == jenum.EnumPacienteLlega.INCONCIENTE.ordinal() ? "X" : ""), fuente));
                    tablePaciente.addCell(new Paragraph("Remitido: " + (ne.getEstadosalida() == jenum.EnumEstadoSalida.MUERTO.ordinal() ? "X" : ""), fuente));
                    tablePaciente.getDefaultCell().setColspan(3);
                    tablePaciente.addCell(new Paragraph("SpO2: " + ne.getSvingresospo2(), fuente));
                    tablePaciente.addCell(new Paragraph("%", fuente));

                    document.add(tablePaciente);
                    tabledx.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    tabledx.getDefaultCell().setBorder(0);
                    tabledx.addCell(new Paragraph("Diagnóstico: ", fuenteN));
                    tabledx.addCell(new Paragraph(ne.getHclCie10() != null ? ne.getHclCie10().getDescripcion() : "", fuente));
                    tabledx.addCell((new Paragraph("Estudio: ", fuenteN)));
                    tabledx.addCell(new Paragraph(ne.getFacturaItem().getProducto().getHclCups().getDescripcion() + " Tipo Simple", fuente));
                    tabledx.addCell(new Paragraph("Conclusión: ", fuenteN));
                    tabledx.addCell(new Paragraph("Ingresa paciente para realización de " + ne.getFacturaItem().getProducto().getHclCups().getDescripcion() + " simple, ordenada por médico tratante, paciente consciente, orientado, colaborador, medico radiólogo brinda información sobre procedimiento, paciente manifiesta entender y acepta firma Consentimiento Informado, en área de preparación del paciente  se toman signos vitales encontrándose dentro de los parámetros normales, se traslada paciente al área de tomografía, se efectúa  procedimiento sin complicación alguna. Egresa paciente en buen estado general, se hace entrega de boleta para solicitar resultado del estudio, pendiente lectura.", fuente));
                    tabledx.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    tabledx.getDefaultCell().setColspan(2);
                    tabledx.addCell(new Paragraph("\n"));
                    tabledx.addCell(new Paragraph("\n"));
                    tabledx.addCell(new Paragraph("\n"));
                    tabledx.addCell(new Paragraph("\n"));

                    tabledx.addCell(new Paragraph("__________________________                          ______________________________", fuente));
                    tabledx.addCell(new Paragraph("FIRMA AUXILIAR ENFERMERÍA                          FIRMA TECNÓLOGO RADIOLOGÍA", fuente));
                    document.add(tabledx);
                    break;
                case 2:
                    tableTiempo.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    tableTiempo.getDefaultCell().setBorderWidth(0);
                    tableTiempo.addCell(new Paragraph("Hora de Ingreso", fuente));
                    tableTiempo.addCell(new Paragraph("Hora de Atención", fuente));
                    tableTiempo.addCell(new Paragraph("Fecha", fuente));
                    tableTiempo.addCell(new Paragraph(String.valueOf(ne.getHoraingreso()) + ":" + ne.getMinutosingreso(), fuente));
                    tableTiempo.addCell(new Paragraph(String.valueOf(ne.getHoraatencion()) + ":" + ne.getMinutosatencion(), fuente));
                    try
                    {
                    tableTiempo.addCell(new Paragraph(sihic.util.UtilDate.s_formatoyearmesdia(ne.getFacturaItem().getAgnCitas().getFechaIngreso()), fuente));
                    }catch(Exception e)
                    {
                        try
                        {
                         tableTiempo.addCell(new Paragraph(sihic.util.UtilDate.s_formatoyearmesdia(ne.getFacturaItem().getHclConsultas().getAgnCitas().getFechaIngreso()), fuente));
                        }catch(Exception e2)
                        { try
                        {
                            tableTiempo.addCell(new Paragraph(sihic.util.UtilDate.s_formatoyearmesdia(ne.getFacturaItem().getAgnCitas().getFechaHora()), fuente));
                        }catch(Exception e3)
                        {
                                 tableTiempo.addCell(new Paragraph(sihic.util.UtilDate.s_formatoyearmesdia(ne.getFacturaItem().getHclConsultas().getAgnCitas().getFechaHora()), fuente));
                      
                        }
                        }
                  
                    }
                    document.add(tableTiempo);
                    document.add(new Paragraph("\n"));
                    //tablepaciente;
                    tablePaciente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("NOMBRE DEL PACIENTE", fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph("DOCUMENTO", fuente));
                    tablePaciente.addCell(new Paragraph("EMB", fuente));
                    tablePaciente.addCell(new Paragraph("EDAD", fuente));
                    tablePaciente.addCell(new Paragraph("SEXO", fuente));
                    tablePaciente.addCell(new Paragraph("CIUDAD", fuente));
                    tablePaciente.addCell(new Paragraph("ZONA", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    try
                    {
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getNombres(), fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento(), fuente));
                    tablePaciente.addCell(new Paragraph(ne.isEmbarazada() == false ? "No" : "Si", fuente));
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().year(), fuente));
                    
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo()!=null?ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla():"", fuente));
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios()!=null?ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getNombre():"", fuente));
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_zona_residencia()!=null?ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_zona_residencia().getDescripcion():"", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Dirección", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Cod Ocupación", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph("Tipo de Usuario", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getDireccion(), fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones() != null ? ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones().getCodigo() : "No", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes()!=null?ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenTiposUsuarios().getNombre():"", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Ocupación", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Telefono", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph("Hora de Egreso", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones() != null ? ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones().getDescripcion() : "No", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getTelefono(), fuente));
                    }catch(Exception e)
                    {
                     
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getNombres(), fuente));
                    tablePaciente.getDefaultCell().setColspan(1);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDocumento(), fuente));
                    tablePaciente.addCell(new Paragraph(ne.isEmbarazada() == false ? "No" : "Si", fuente));
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().year(), fuente));
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla(), fuente));
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getNombre(), fuente));
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGen_zona_residencia().getDescripcion(), fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Dirección", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Cod Ocupación", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph("Tipo de Usuario", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getDireccion(), fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones() != null ? ne.getFacturaItem().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones().getCodigo() : "No", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenTiposUsuarios().getNombre(), fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Ocupación", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph("Telefono", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph("Hora de Egreso", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones() != null ? ne.getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getGen_profesiones().getDescripcion() : "No", fuente));
                    tablePaciente.getDefaultCell().setColspan(2);
                    tablePaciente.addCell(new Paragraph(ne.getFacturaItem().getHclConsultas().getAgnCitas().getGenPacientes().getGenPersonas().getTelefono(), fuente));

                    }
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph(ne.getHoraegreso() + ":" + ne.getMinutosegreso(), fuente));
                    tablePaciente.getDefaultCell().setColspan(8);
                    tablePaciente.addCell(new Paragraph("Acompañante: " + ne.getAcompanante(), fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph("Paciente Llego", fuente));
                    tablePaciente.getDefaultCell().setColspan(4);
                    tablePaciente.addCell(new Paragraph("Estado de Salida", fuente));

                    tablePaciente.addCell(new Paragraph("Consciente: " + (ne.getEstadosalida() == jenum.EnumEstadoSalida.VIVO.ordinal() ? "X" : ""), fuente));
                    tablePaciente.addCell(new Paragraph("Vivo: " + (ne.getEstadosalida() == jenum.EnumEstadoSalida.VIVO.ordinal() ? "X" : ""), fuente));
                    tablePaciente.addCell(new Paragraph("Inconsciente: " + (ne.getPacientellego() == jenum.EnumPacienteLlega.INCONCIENTE.ordinal() ? "X" : ""), fuente));
                    tablePaciente.addCell(new Paragraph("Muerto: " + (ne.getEstadosalida() == jenum.EnumEstadoSalida.MUERTO.ordinal() ? "X" : ""), fuente));
                    tablePaciente.addCell(new Paragraph("Muerto: " + (ne.getPacientellego() == jenum.EnumPacienteLlega.INCONCIENTE.ordinal() ? "X" : ""), fuente));
                    tablePaciente.addCell(new Paragraph("Remitido: " + (ne.getEstadosalida() == jenum.EnumEstadoSalida.MUERTO.ordinal() ? "X" : ""), fuente));

                    document.add(tablePaciente);
                    tabledx.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                    tabledx.getDefaultCell().setBorder(0);
                    tabledx.addCell(new Paragraph("Diagnóstico: ", fuenteN));
                    tabledx.addCell(new Paragraph(ne.getHclCie10() != null ? ne.getHclCie10().getDescripcion() : "", fuente));
                    tabledx.addCell((new Paragraph("Estudio: ", fuenteN)));
                    tabledx.addCell(new Paragraph(ne.getFacturaItem().getProducto().getHclCups().getDescripcion() + " MAMOGRAFIA AF(" + ne.getAf() + ")", fuente));
                    tabledx.addCell(new Paragraph("Conclusión: ", fuenteN));
                    tabledx.addCell(new Paragraph("Ingresa paciente para realización de Mamografía ordenada por médico tratante, paciente consciente, orientada, colaboradora, medico radiólogo brinda información sobre procedimiento, paciente manifiesta entender y acepta se firma consentimiento informado, se traslada paciente al área de mamografía, se efectúa  procedimiento sin complicación alguna. Egresa paciente en buen estado general. Se hace entrega de boleta para solicitar resultado del estudio, pendiente lectura.", fuente));
                    tabledx.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                    tabledx.getDefaultCell().setColspan(2);
                    tabledx.addCell(new Paragraph("\n"));
                    tabledx.addCell(new Paragraph("\n"));
                    tabledx.addCell(new Paragraph("\n"));
                    tabledx.addCell(new Paragraph("\n"));

                    tabledx.addCell(new Paragraph("                          ______________________________", fuente));
                    tabledx.addCell(new Paragraph("                          FIRMA TECNÓLOGO RADIOLOGÍA", fuente));
                    document.add(tabledx);
                    break;
            }

            // step 5
            document.close();

            File file = new File("/home/adminlinux/sihic/notasestudio.pdf");

            try {

                Desktop.getDesktop().open(file);
//Process p = Runtime.getRuntime().exec ("//home//adminlinux//sihic//factura.pdf");

            } catch (Exception evvv) {

                JOptionPane.showMessageDialog(null, "No se puede abrir el archivo de ayuda, probablemente fue borrado", "ERROR", JOptionPane.ERROR_MESSAGE);

            }
        } catch (Exception ex2) {
            ex2.printStackTrace();
        }
        /*   ProcessBuilder pb = new ProcessBuilder("java","-jar","/home/adminlinux/sihic/pdfviewercomprobante/PdfViewer.jar");

	        System.out.println("Run echo command");

	        Process process = pb.start();*/

    }
    private BigDecimal round(BigDecimal amount) {
        return new BigDecimal(amount.movePointRight(2).add(new BigDecimal(".5")).toBigInteger()).movePointLeft(2);
    }
    public void fprintpdffacturageneral(int opc) throws IOException {
        Document document = new Document();

        Font fuente = new Font();
        try {
            document = new Document(PageSize.LETTER, 20f, 20f, 160f, 20f);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/factura.pdf"));
            Rectangle rect = new Rectangle(PageSize.LETTER);
            writer.setBoxSize("art", rect);
            Cabecera encabezado = new Cabecera(factura);
            Paragraph parrafo;
            int i;

            encabezado.setEncabezado("Prueba de encabezado en iText");

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
            for (FacturaItem fi : factura.getFacturaItems()) {

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
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getNombre().length() <= 30 ? fi.getProducto().getNombre() : fi.getProducto().getNombre().substring(0, 30)), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getQuantity()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getValor_u()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getCuotamoderadora()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getCopago()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getValor_total()), fuente));

                regs++;
            }
            tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

            factura.calculartotalescopagocuotamoeras();
            tableFacturaItems.getDefaultCell().setColspan(10);
            tableFacturaItems.addCell(new Paragraph("SubTotal: ", fuente));
            tableFacturaItems.addCell(new Paragraph(factura.getNetAmount().add(factura.getTotalcuotasmoderadoras()).add(factura.getTotalcopagos()).toString(), fuente));
            tableFacturaItems.addCell(new Paragraph("Total C. Moderadoras: ", fuente));
            tableFacturaItems.addCell(new Paragraph(factura.getTotalcuotasmoderadoras().toString(), fuente));
            tableFacturaItems.addCell(new Paragraph("Total Copagos: ", fuente));
            tableFacturaItems.addCell(new Paragraph(factura.getTotalcopagos().toString(), fuente));
            tableFacturaItems.addCell(new Paragraph("Valor Total: ", fuente));
            tableFacturaItems.addCell(new Paragraph(factura.getTotalAmount().toString(), fuente));
            tableFacturaItems.getDefaultCell().setColspan(12);
            tableFacturaItems.addCell(new Paragraph("Valor Total en Letras: " + sihic.util.NumberToLetterConverter.convertNumberToLetter(String.valueOf(factura.getTotalAmount())), fuente));
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
        if (opc == 1) {
            try {

                Desktop.getDesktop().open(file);
//Process p = Runtime.getRuntime().exec ("//home//adminlinux//sihic//factura.pdf");

            } catch (Exception evvv) {
                JOptionPane.showMessageDialog(null, "No se puede abrir el archivo de ayuda, probablemente fue borrado", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            //ProcessBuilder pb = new ProcessBuilder("java","-jar","/home/adminlinux/sihic/pdfviewercomprobante/PdfViewerFactura.jar");
            //ProcessBuilder pb = new ProcessBuilder("/home/adminlinux/sihic/factura.pdf");

//	        System.out.println("Run echo command");
//	        Process process = pb.start();
        }

    }
    public void freporteffacturageneral(int opc) throws IOException {
        Document document = new Document();

        Font fuente = new Font();
        try {
            document = new Document(PageSize.LETTER, 20f, 20f, 160f, 20f);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/rptfactura.pdf"));
            Rectangle rect = new Rectangle(PageSize.LETTER);
            writer.setBoxSize("art", rect);
            HeadReporteFactura headReporteFactura = new HeadReporteFactura(factura);
            Paragraph parrafo;
            int i;

            // indicamos que objecto manejara los eventos al escribir el Pdf
            writer.setPageEvent(headReporteFactura);
            document.open();

            PdfPTable tableMaster = new PdfPTable(1);
            //PdfPTable table = new PdfPTable(new float[] { 5, 5});

            PdfPTable table = new PdfPTable(new float[]{10});
            PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
            PdfPTable tableCliente = new PdfPTable(new float[]{6, 7});
            PdfPTable tableFacturaItems = new PdfPTable(new float[]{2, 3, 5, 3, 3, 2, 5, 2, 2, 3, 2});
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
            for (FacturaItem fi : factura.getFacturaItems()) {

                tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
                tableFacturaItems.addCell(new Paragraph(String.valueOf(setCeros(fi.getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId().toString()) + fi.getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId()), fuente));
                tableFacturaItems.addCell(new Paragraph(fi.getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla() + " " + fi.getGenPacientes().getGenPersonas().getDocumento(), fuente));
                tableFacturaItems.addCell(new Paragraph(fi.getGenPacientes().getGenPersonas().getNombres(), fuente));
                tableFacturaItems.addCell(new Paragraph(sihic.util.UtilDate.formatodiamesyear(fi.getHclConsultas().getAgnCitas().getFechaCreacion()), fuente));
                tableFacturaItems.addCell(new Paragraph(sihic.util.UtilDate.formatodiamesyear(fi.getHclConsultas().getAgnCitas().getFechaCreacion()), fuente));
                if (fi.getProducto().getHclCups() != null) {
                    try {

                        tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getHclCups().getCodigo().replace(".", "")), fuente));
                    } catch (Exception e) {
                        tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getCodigo().replace(".", "")), fuente));

                    }
                } else {
                    tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getCodigo().replace(".", "")), fuente));

                }
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getProducto().getNombre().length() <= 30 ? fi.getProducto().getNombre() : fi.getProducto().getNombre().substring(0, 30)), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getQuantity()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getValor_u()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getHclConsultas().getNumeroautorizacion()), fuente));
                tableFacturaItems.addCell(new Paragraph(String.valueOf(fi.getValor_total()), fuente));

                regs++;
            }
            tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

            factura.calculartotalescopagocuotamoeras();
            tableFacturaItems.getDefaultCell().setColspan(10);
            tableFacturaItems.addCell(new Paragraph("SubTotal: ", fuente));
            tableFacturaItems.addCell(new Paragraph(factura.getNetAmount().add(factura.getTotalcuotasmoderadoras()).add(factura.getTotalcopagos()).toString(), fuente));
            tableFacturaItems.addCell(new Paragraph("Total C. Moderadoras: ", fuente));
            tableFacturaItems.addCell(new Paragraph(factura.getTotalcuotasmoderadoras().toString(), fuente));
            tableFacturaItems.addCell(new Paragraph("Total Copagos: ", fuente));
            tableFacturaItems.addCell(new Paragraph(factura.getTotalcopagos().toString(), fuente));
            tableFacturaItems.addCell(new Paragraph("Valor Total: ", fuente));
            tableFacturaItems.addCell(new Paragraph(factura.getTotalAmount().toString(), fuente));
            tableFacturaItems.getDefaultCell().setColspan(12);
            tableFacturaItems.addCell(new Paragraph("Valor Total en Letras: " + sihic.util.NumberToLetterConverter.convertNumberToLetter(String.valueOf(factura.getTotalAmount())), fuente));
            table.addCell(tableFacturaItems);
            table.getDefaultCell().setColspan(2);

            fuente = FontFactory.getFont("arial", 5, Font.NORMAL);

            table.getDefaultCell().setPadding(10);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            fuente = FontFactory.getFont("Times-Roman", 9, Font.BOLD);
            table.addCell(new Paragraph("Aceptada: ________________________  Vendedor: ________________________", fuente));
            tableMaster.addCell(table);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            table.getDefaultCell().setPadding(5);
            table.addCell(new Paragraph(" RESOLUCION DIAN 780000001443 DE 2015-09-23", fuente));
            table.addCell(new Paragraph(" No somos responsables de IVA", fuente));
            table.addCell(new Paragraph(" LA PRESENTE FACTURA SE ASIMILA EN SUS EFECTOS A UNA LETRA DE CAMBIO ART 779  CODIGO DE COMERCIO", fuente));

            document.add(tableMaster);

            // step 5
            document.close();
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }
        File file = new File("/home/adminlinux/sihic/rptfactura.pdf");
        if (opc == 1) {
            try {

                Desktop.getDesktop().open(file);
//Process p = Runtime.getRuntime().exec ("//home//adminlinux//sihic//factura.pdf");

            } catch (Exception evvv) {
                JOptionPane.showMessageDialog(null, "No se puede abrir el archivo de ayuda, probablemente fue borrado", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            //ProcessBuilder pb = new ProcessBuilder("java","-jar","/home/adminlinux/sihic/pdfviewercomprobante/PdfViewerFactura.jar");
            //ProcessBuilder pb = new ProcessBuilder("/home/adminlinux/sihic/factura.pdf");

//	        System.out.println("Run echo command");
//	        Process process = pb.start();
        }

    }
    private List<FacturaItem> itemsfacturaprocedimiento(FacturaItem fi) {
        List<FacturaItem> li_fi = new ArrayList<>();
        li_fi.addAll(fi.getFactura().getFacturaItems());
        //  System.out.println("size 7->"+li_fi.stream().filter(line ->line.getConComprobanteProcedimiento().getId().equals(fi.getConComprobanteProcedimiento().getId())).collect(Collectors.toList()).size());
        return li_fi.stream().filter(line -> line.getConComprobanteProcedimiento().getId().equals(fi.getConComprobanteProcedimiento().getId())).collect(Collectors.toList());
    }
     public void fprintpdffacturaglobal() throws IOException {
        Document document = new Document();

        Font fuente = new Font();
        try {
            document = new Document(PageSize.LETTER, 20f, 20f, 160f, 20f);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/facturaglobal.pdf"));
            Rectangle rect = new Rectangle(PageSize.LETTER);
            writer.setBoxSize("art", rect);
            Cabecera encabezado = new Cabecera(factura);
            Paragraph parrafo;
            int i;

            encabezado.setEncabezado("Prueba de encabezado en iText");

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

            factura.calculartotalescopagocuotamoeras();
            tableFacturaItems.addCell(new Paragraph("Concepto", fuente));
            tableFacturaItems.addCell(new Paragraph("Cantidad", fuente));
            tableFacturaItems.addCell(new Paragraph("Valor", fuente));
          
            tableFacturaItems.addCell(new Paragraph("Procedimientos", fuente));
            tableFacturaItems.addCell(new Paragraph(String.valueOf(factura.getFacturaItems().size()), fuente));
            tableFacturaItems.addCell(new Paragraph(factura.getTotalAmount().toString(), fuente));
            tableFacturaItems.addCell(new Paragraph("Valor Neto:", fuente));
            tableFacturaItems.addCell(new Paragraph("", fuente));
            tableFacturaItems.addCell(new Paragraph(factura.getTotalAmount().toString(), fuente));
            tableFacturaItems.getDefaultCell().setColspan(3);
             tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableFacturaItems.addCell(new Paragraph("Valor Total en Letras:                  " + sihic.util.NumberToLetterConverter.convertNumberToLetter(String.valueOf(factura.getTotalAmount())), fuente));
            String regimen="";
            
            if(factura.getPrefijo().substring(0,1).equals("A") || factura.getPrefijo().substring(0,1).equals("P"))
            {
                regimen=factura.getGenTiposUsuarios().getNombre();
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
     public static void fpdfnotacontable() throws IOException, BadElementException, DocumentException {
        Document document = new Document();
        Font fuente = new Font();
         document = new Document(PageSize.LETTER, 2, 2, 2, 2);
        PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/notacontable.pdf"));
        document.open();
        PdfPTable tableMaster = new PdfPTable(1);
        //PdfPTable table = new PdfPTable(new float[] { 5, 5});

        PdfPTable table = new PdfPTable(new float[]{10});
        PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
        PdfPTable tableCliente = new PdfPTable(new float[]{10});
        PdfPTable tabledetalles = new PdfPTable(new float[]{4,2,8,3,3});
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
        table.getDefaultCell().setPadding(10);
        table.setWidthPercentage(100f);
        table.addCell(tableImagenTexto);

        
        table.getDefaultCell().setPadding(15);
        tableCliente.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableCliente.getDefaultCell().setColspan(1);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);

        fuente = FontFactory.getFont("Times-Roman", 9, Font.BOLD);
        tableCliente.getDefaultCell().setBorder(0);
        tableCliente.addCell(new Paragraph("Nota Contabilidad No. " + SihicApp.notaContabilidad.getNocomprobantecerosizquierda(), fuente));
        tableCliente.getDefaultCell().setColspan(2);
        tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        fuente = FontFactory.getFont("Times-Roman", 7, Font.NORMAL);
        tableCliente.addCell(new Paragraph("Fecha: " + DateFormat.getDateInstance().format(SihicApp.notaContabilidad.getFechaelaboracion()), fuente));
        tableCliente.getDefaultCell().setColspan(2);
      
        
        tableCliente.addCell(new Paragraph("Concepto: " + SihicApp.notaContabilidad.getConcepto(), fuente));
        tableCliente.getDefaultCell().setColspan(2);
       
        
        table.addCell(tableCliente);
        table.getDefaultCell().setColspan(2);
        tabledetalles.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.getDefaultCell().setPadding(0);
        tabledetalles.addCell(new Paragraph("Tercero", fuente));
        tabledetalles.addCell(new Paragraph("Codigo", fuente));
        tabledetalles.addCell(new Paragraph("Cuenta", fuente));
        tabledetalles.addCell(new Paragraph("Debe", fuente));
        tabledetalles.addCell(new Paragraph("Haber", fuente));
       
        tabledetalles.getDefaultCell().setBorder(0);
        //tableFacturaItems.getDefaultCell().setBorderWidthTop(1);
       
        for(LibroAuxiliar dc: SihicApp.notaContabilidad.getLi_liLibroAuxiliars())
        {
        tabledetalles.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tabledetalles.getDefaultCell().setBorderWidthLeft((float) 0.25);
        tabledetalles.getDefaultCell().setBorderWidthRight((float) 0.25);
        tabledetalles.getDefaultCell().setBorderWidthBottom((float) 0.25);
        tabledetalles.addCell(new Paragraph(dc.getProveedores().getNo_ident()+"||"+dc.getProveedores().getNombre(), fuente));
      
        tabledetalles.addCell(new Paragraph(dc.getConPuc().getCodigo(), fuente));
        tabledetalles.getDefaultCell().setBorderWidthLeft(0);
        tabledetalles.getDefaultCell().setBorderWidthRight((float) 0.25);
        tabledetalles.addCell(new Paragraph(dc.getConPuc().getNombre(), fuente));
        tabledetalles.addCell(new Paragraph(dc.getDebe().toString(), fuente));
        tabledetalles.addCell(new Paragraph(dc.getHaber().toString(), fuente));
        tabledetalles.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        }
        table.addCell(tabledetalles);

        table.getDefaultCell().setBorder(0);
        table.addCell(new Paragraph("\n\n", fuente));
        table.addCell(new Paragraph("\n\n", fuente));
        table.addCell(new Paragraph("                                    Elaborado por: _____________________  Aprobado por: ____________________", fuente));
        tableMaster.addCell(table);
        document.add(tableMaster);
        // step 5
        document.close();
File file = new File("/home/adminlinux/sihic/notacontable.pdf");
String os = System.getProperty("os.name"); 
      if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
 { 
  Runtime r = Runtime.getRuntime(); 
  Process p = r.exec("/usr/bin/evince /home/adminlinux/sihic/notacontable.pdf"); 
  }
else
 {
  
    Desktop.getDesktop().open(file);
 
 }

    }

     
}
