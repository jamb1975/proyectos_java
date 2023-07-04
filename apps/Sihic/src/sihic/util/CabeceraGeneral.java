/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.util;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Phrase;
import java.io.IOException;
import java.text.DateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Factura;
import sihic.SihicApp;

/**
 * Clase que maneja los eventos de pagina necesarios para agregar un encabezado
 * y conteo de paginas a un documento. El encabezado, definido en onEndPage,
 * consiste en una tabla con 3 celdas que contienen: Frase del encabezado |
 * pagina de | total de paginas, con una linea horizontal separando el
 * encabezado del texto
 *
 * Referencia: http://itextpdf.com/examples/iia.php?id=104
 *
 * @author David
 */
public class CabeceraGeneral extends PdfPageEventHelper {

    private String encabezado;
    PdfTemplate total;
    Factura factura;

    /**
     * Crea el objecto PdfTemplate el cual contiene el numero total de paginas
     * en el documento
     */
    public void onOpenDocument(PdfWriter writer, Document document) {
        total = writer.getDirectContent().createTemplate(30, 16);

    }

    public CabeceraGeneral(Factura factura) {
        this.factura = factura;
    }

    /**
     * Esta es el metodo a llamar cuando ocurra el evento onEndPage, es en este
     * evento donde crearemos el encabeazado de la pagina con los elementos
     * indicados.
     */
    public void onStartPage(PdfWriter writer, Document document) {
        Rectangle rect = writer.getBoxSize("art");
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Left"), rect.getLeft(), rect.getTop(), 0);
        ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER, new Phrase("Top Right"), rect.getRight(), rect.getTop(), 0);
    }

    public void onEndPage(PdfWriter writer, Document document) {
        PdfPTable tableHeader = new PdfPTable(3);
        try {
            // Se determina el ancho y altura de la tabla
            PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
            PdfPTable tableCliente = new PdfPTable(new float[]{6, 7});
            PdfPTable tableFacturaItems = new PdfPTable(new float[]{2, 5, 3, 3});
            Image imagen;
            tableImagenTexto.getDefaultCell().setBorder(0);
            imagen = Image.getInstance(SihicApp.genUnidadesOrganizacion.getLogo());
            tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableImagenTexto.getDefaultCell().setColspan(2);
            tableImagenTexto.getDefaultCell().setFixedHeight(40f);
            tableImagenTexto.addCell(imagen);
            tableImagenTexto.getDefaultCell().setFixedHeight(0f);
            tableImagenTexto.getDefaultCell().setFixedHeight(0f);
            //tableImagenTexto.getDefaultCell().setColspan(2);
            Font fuente = FontFactory.getFont("Times-Roman", 10, Font.BOLD);

            tableImagenTexto.addCell(new Paragraph("CENTRO MÉDICO DEL GUAVIARE SAS", fuente));
            fuente = FontFactory.getFont("Times-Roman", 9, Font.BOLD);
            tableImagenTexto.addCell(new Paragraph("Nit.: 900.767.863 ", fuente));
            tableImagenTexto.addCell(new Paragraph("CRA 22 N° 11-18 B. LA ESPERANZA TEL. 3134212162 ", fuente));
            tableImagenTexto.addCell(new Paragraph("SAN JOSE DEL GUAVIARE", fuente));
            fuente = FontFactory.getFont("Times-Roman", 8, Font.BOLD);
            tableImagenTexto.addCell(new Paragraph("Resolución DIAN N° 780000001575 Fecha 07/12/2015", fuente));
            tableImagenTexto.addCell(new Paragraph("Facturación por Computador dede N° 1 al 3000", fuente));

            tableHeader.setWidths(new int[]{24, 24, 2});
            tableHeader.setTotalWidth(500f);
            //table.setLockedWidth(true);

            //  tableHeader.getDefaultCell().setFixedHeight(40f);
            // Borde de la celda
            tableHeader.getDefaultCell().setBorder(0);
            tableHeader.setWidthPercentage(100f);
            tableCliente.setWidthPercentage(100f);
            tableHeader.getDefaultCell().setColspan(3);
            fuente = FontFactory.getFont("Times-Roman", 8, Font.BOLD);
            tableCliente.getDefaultCell().setBorder(0);
            tableCliente.addCell(new Paragraph("Fecha Elab: " + DateFormat.getDateInstance().format(factura.getFacturaDate()) + " Fecha Venc: " + DateFormat.getDateInstance().format(factura.getFechavencimiento()), fuente));
            tableCliente.addCell(new Paragraph("Factura de venta" + " N° " + factura.getNofacturacerosizquierda() + "\nNit: 17266911 Tel: 3102030435 Ciudad: Cumaral-Meta", fuente));
            try {
                tableCliente.addCell(new Paragraph("Entidad: " + factura.getHclConsultas().getAgnCitas().getGenPacientes().getGenEapb().getNombre() + " Código: " + factura.getHclConsultas().getAgnCitas().getGenPacientes().getGenEapb().getCodigo() + " Nit: " + factura.getHclConsultas().getAgnCitas().getGenPacientes().getGenEapb().getNit() + " Servicios: Imagenes de Imagenologia", fuente));
            } catch (Exception e) {
                tableCliente.addCell(new Paragraph("Entidad: N  Código: N", fuente));

            }
            tableCliente.addCell(new Paragraph("Tipo Acuerdo:", fuente));

            tableHeader.addCell(tableImagenTexto);
            tableHeader.addCell(tableCliente);
            tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);

            //  tableHeader.addCell(String.format("Pagina %010d de", writer.getPageNumber()));
            tableFacturaItems.getDefaultCell().setColspan(0);

            tableFacturaItems.getDefaultCell().setBorder(0);
            tableFacturaItems.addCell(new Paragraph("Comp", fuente));
            tableFacturaItems.addCell(new Paragraph("Fecha", fuente));
            tableFacturaItems.addCell(new Paragraph("Descripción", fuente));
            tableFacturaItems.addCell(new Paragraph("Edad", fuente));
            tableFacturaItems.addCell(new Paragraph("Sex", fuente));
            tableFacturaItems.addCell(new Paragraph("Concepto", fuente));
            tableFacturaItems.addCell(new Paragraph("Cant", fuente));
            tableFacturaItems.addCell(new Paragraph("Valor", fuente));
            tableFacturaItems.addCell(new Paragraph("Copago", fuente));
            tableFacturaItems.addCell(new Paragraph("Autoriz", fuente));
            tableFacturaItems.addCell(new Paragraph("Código", fuente));
            tableFacturaItems.addCell(new Paragraph("Und", fuente));
            tableHeader.addCell(tableFacturaItems);

            //  tableHeader.addCell(cell);
            // Esta linea escribe la tabla como encabezado
            tableHeader.writeSelectedRows(0, -1, 23, 803, writer.getDirectContent());

        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (IOException ex) {
            Logger.getLogger(Cabecera.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Realiza el conteo de paginas al momento de cerrar el documento
     */
    public void onCloseDocument(PdfWriter writer, Document document) {
        ColumnText.showTextAligned(total, Element.ALIGN_LEFT, new Phrase(String.valueOf(writer.getPageNumber() - 1)), 2, 2, 0);
    }

    // Getter and Setters
    public String getEncabezado() {
        return encabezado;
    }

    public void setEncabezado(String encabezado) {
        this.encabezado = encabezado;
    }
}
