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
public class FooterPlantillas extends PdfPageEventHelper {

    private String encabezado;
    PdfTemplate total;

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

        try {
            // Se determina el ancho y altura de la tabla
            PdfPTable tablefooter = new PdfPTable(new float[]{10});
            Image imagen;
            tablefooter.getDefaultCell().setBorder(0);
            Font fuente = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
            tablefooter.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
            tablefooter.addCell(new Paragraph("NOTA: SE ADVIERTE AL USUARIO QUE ESTA OBLIGADO POR DISPOSICIONES LEGALES Y PARA EL BENEFICIO DE GUARDAR LAS PLACAS, IMPRESOS ECOGRAFICOS  E INFORMES POR PERIODOS  NO MENOR A TRES AÑOS.", fuente));
            imagen = Image.getInstance("/home/adminlinux/sihic/firma.png");
            tablefooter.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            tablefooter.getDefaultCell().setFixedHeight(70f);

            tablefooter.addCell(imagen);
            tablefooter.getDefaultCell().setFixedHeight(0f);
            tablefooter.addCell(new Paragraph("GUSTAVO DE JESUS MONSALVE TAMAYO\n"
                    + "MEDICO RADIOLOGO\n"
                    + "RM. 5463-9", fuente));
            tablefooter.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            fuente = FontFactory.getFont("arial", 9, Font.BOLD);

            tablefooter.addCell(new Paragraph("Dirección: Carrera 22 #11-18 Barrio la Esperanza\n"
                    + "Email:centromedicoguaviare.07@gmail.com  ", fuente));

            tablefooter.setWidths(new int[]{50});
            tablefooter.setTotalWidth(562.64f);
            tablefooter.setWidthPercentage(100f);

            tablefooter.writeSelectedRows(0, -1, 25, 190, writer.getDirectContent());

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
