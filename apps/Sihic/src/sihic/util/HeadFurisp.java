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
public class HeadFurisp extends PdfPageEventHelper {

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
            PdfPTable tableImagenTexto = new PdfPTable(new float[]{1, 9});
            Image imagen;
            tableImagenTexto.setWidths(new int[]{2, 48});
            tableImagenTexto.setTotalWidth(562.64f);
            imagen = Image.getInstance("/home/adminlinux/sihic/escudo.jpeg");
            tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
            tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
            tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
            tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
            tableImagenTexto.getDefaultCell().setFixedHeight(40f);
            tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            tableImagenTexto.getDefaultCell().setRowspan(5);
            tableImagenTexto.addCell(imagen);

            tableImagenTexto.getDefaultCell().setFixedHeight(0f);

            tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
            tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
            tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
            tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
            tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableImagenTexto.setWidthPercentage(100f);
            Font fuente = FontFactory.getFont("arial", 9, Font.NORMAL);
            tableImagenTexto.addCell(new Paragraph("REPUBLICA DE COLOMBIA\nMINISTERIO DE SALUD Y PROTECCIÓN SOCIAL\nFORMULARIO ÚNICO DE RECLAMACIÓN DE LAS INSTITUCIONES PRESTADORAS DE SALUD POR SERVICIOS PRESTADOS A VICTIMAS\nPRESTADORES DE SERVICIOS DE SALUD - FURISP", fuente));

            //  tableHeader.addCell(cell);
            // Esta linea escribe la tabla como encabezado
            tableImagenTexto.writeSelectedRows(0, -1, 25, 780, writer.getDirectContent());

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
