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
import java.math.BigDecimal;
import java.text.DateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Factura;
import sihic.SihicApp;
import sihic.contabilidad.documentos.FacturacionMasiva;

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
public class Cabecera2 extends PdfPageEventHelper {

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

    public Cabecera2(Factura factura) {
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
            PdfPTable tableFacturaItems = new PdfPTable(new float[]{2, 3, 5, 2, 5, 2, 2, 2, 2, 2});
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
            tableImagenTexto.addCell(new Paragraph("Nit.: 900.767.863-6", fuente));
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
           fuente = FontFactory.getFont("Times-Roman", 8, Font.BOLD);
            switch(factura.getPrefijo().substring(0, 1))
            {
                case "A":
                    switch(SihicApp.usuarios.getSucursales().getCodigo())
                    {
                        case "1": tableImagenTexto.addCell(new Paragraph("Autorización DIAN N° 18764006388690 Fecha 27/10/2020 Facturación de Venta Electrónica  desde la N° 1 a la 5000", fuente));
                                  break;
                        case "2": tableImagenTexto.addCell(new Paragraph("Autorización DIAN N° 18764006382795 Fecha 27/10/2020 Facturación de Venta Electrónica  desde la N° 1 a la 5000", fuente));
                                  break;
                        case "3": tableImagenTexto.addCell(new Paragraph("Autorización DIAN N° 18764006387361 Fecha 27/10/2020 Facturación de Venta Electrónica  desde la N° 1 a la 5000", fuente));
                                  break;
                        case "4": tableImagenTexto.addCell(new Paragraph("Autorización DIAN N° 18764006385861 Fecha 27/10/2020 Facturación de Venta Electrónica  desde la N° 1 a la 5000", fuente));
                                  break;
                            
                    }
                    break;
                case "P":
                    switch(SihicApp.usuarios.getSucursales().getCodigo())
                    {
                        case "1":   tableImagenTexto.addCell(new Paragraph("Autorización DIAN N° 18764013340124 Fecha 18/05/2021 Facturación de Venta Electrónica  desde la N° 5001 al 20000", fuente));
                                   break;
                        case "2": tableImagenTexto.addCell(new Paragraph("Autorización DIAN N° 18764006384222 Fecha 27/10/2020 Facturación de Venta Electrónica  desde la N° 1 a la 5000", fuente));
                                   break;
                        case "3":  tableImagenTexto.addCell(new Paragraph("Autorización DIAN N° 18764006387875 Fecha 27/10/2020 Facturación de Venta Electrónica  desde la N° 1 a la 5000", fuente));
                                   break;
                        case "4":  tableImagenTexto.addCell(new Paragraph("Autorización DIAN N° 18764006386092 Fecha 27/10/2020 Facturación de Venta Electrónica  desde la N° 1 a la 5000", fuente));
                                   break;
                            
                    }    
                break;    
                case "T":
                    switch(SihicApp.usuarios.getSucursales().getCodigo())
                    {
                        case "1":     tableImagenTexto.addCell(new Paragraph("Autorización DIAN N° 18764006389667 Fecha 27/10/2020 Facturación de Venta Electrónica  desde la N° 1 a la 5000", fuente));
                                      break;
                        case "2": tableImagenTexto.addCell(new Paragraph("Autorización DIAN N° 18762010496165 Fecha 07/05/2018 Facturación de Venta Electrónica  desde la N° 1 a la 5000", fuente));
                                  break;
                        case "3": tableImagenTexto.addCell(new Paragraph("Autorización DIAN N° 18764006388192 Fecha 27/10/2020 Facturación de Venta Electrónica  desde la N° 1 a la 5000", fuente));
                                  break;
                        case "4": tableImagenTexto.addCell(new Paragraph("Autorización DIAN N° 18762010496165 Fecha 07/05/2018 Facturación de Venta Electrónica  desde la N° 1 a la 5000", fuente));
                                  break;
                            
                    }    
                break; 
               case "E":
                    switch(SihicApp.usuarios.getSucursales().getCodigo())
                    {
                        case "1":     tableImagenTexto.addCell(new Paragraph("Autorización DIAN N° 18764006389380 Fecha 27/10/2020 Facturación de Venta Electrónica  desde la N° 1 a la 5000", fuente));
                                      break;
                        case "2": tableImagenTexto.addCell(new Paragraph("Autorización DIAN N° 18764006383872 Fecha 27/10/2020 Facturación de Venta Electrónica  desde la N° 1 a la 5000", fuente));
                                  break;
                        case "3": tableImagenTexto.addCell(new Paragraph("Autorización DIAN N° 18764006388192 Fecha 27/10/2020 Facturación de Venta Electrónica  desde la N° 1 a la 5000", fuente));
                                  break;
                        case "4": tableImagenTexto.addCell(new Paragraph("Autorización DIAN N° 18764006386315 Fecha 27/10/2020 Facturación de Venta Electrónica  desde la N° 1 a la 5000", fuente));
                                  break;
                    }    
                break; 
                case "S":
                    switch(SihicApp.usuarios.getSucursales().getCodigo())
                    {
                        case "1": tableImagenTexto.addCell(new Paragraph("Autorización DIAN N° 18764014549494 Fecha 25/06/2021 Facturación de Venta Electrónica  desde la N° 1 al 10000", fuente));
                                 break;
                    }    
                break;
            }
            
            tableHeader.setWidths(new int[]{24, 24, 2});
            tableHeader.setTotalWidth(562.64f);
            tableHeader.getDefaultCell().setBorder(0);
            tableHeader.setWidthPercentage(100f);
            tableCliente.setWidthPercentage(100f);
            tableFacturaItems.setWidthPercentage(100f);
            tableHeader.getDefaultCell().setColspan(3);
            fuente = FontFactory.getFont("Times-Roman", 8, Font.BOLD);
            tableCliente.getDefaultCell().setBorder(0);
            tableCliente.addCell(new Paragraph("Fecha Elab: " + DateFormat.getDateInstance().format(factura.getFacturaDate()), fuente));//+" Fecha Venc: "+DateFormat.getDateInstance().format(factura.getFechavencimiento()),fuente));
            tableCliente.addCell(new Paragraph("Factura" +     " N° " + factura.getNofacturacerosizquierda() , fuente));

            try {
                if (factura.getPrefijo().substring(0, 1).equals("A") || factura.getPrefijo().substring(0, 1).equals("P")) {
                   tableCliente.getDefaultCell().setColspan(2);
                   tableCliente.addCell(new Paragraph("Entidad: " + factura.getGenEapb().getNombre() + " Código: " + factura.getGenEapb().getCodigo() +  " Nit:"+ factura.getGenEapb().getNit()+" Ciudad: "+ SihicApp.sucursales.getNombre()+" \nObservación: Por Concepto de Prestación de Servicios de Radiología e Imágene Diagnósticas Reg. "+factura.getGenTiposUsuarios().getNombre()+" en el Mes de "+ sihic.util.UtilDate.obtenerMes(factura.getFacturaItems().get(0).getAgnCitas().getFechaHora())+" de "+sihic.util.UtilDate.getyearfecha(factura.getFacturaItems().get(0).getAgnCitas().getFechaHora()), fuente));
                    //   tableCliente.addCell(new Paragraph("Cliente: " + factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getNombres() + "Nit o Cc: " + factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() + " \nServicios: Imagenes de Imagenologia", fuente));
                  
               tableCliente.addCell(new Paragraph("Anexos: RIPS + SOPORTES ORIGINALES                 " +FacturacionMasiva.tf_fechaespecifica.getText()+ String.format("                                 Pagina %d ", writer.getPageNumber()), fuente));

                } else {
                    if (factura.getPrefijo().equals("T")) {
                        try{
                        tableCliente.addCell(new Paragraph("Entidad: " + factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getAseguradora().getNombre() + " Código: " + factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getAseguradora().getCodigo() + " \nServicios: Imagenes de Imagenologia", fuente));
                        tableCliente.addCell(new Paragraph("Nit: " + factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getAseguradora().getNit() + " Anexos: RIPS + SOPORTES ORIGINALES \n" +FacturacionMasiva.tf_fechaespecifica.getText()+ String.format("                                 Pagina %d ", writer.getPageNumber()), fuente));
                        }catch(Exception e)
                        {
                        tableCliente.addCell(new Paragraph("Entidad: " + factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenEapb().getNombre() + " Código: " + factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenEapb().getCodigo() + " \nServicios: Imagenes de Imagenologia", fuente));
                        tableCliente.addCell(new Paragraph("Nit: " + factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenEapb().getNit() + " Anexos: RIPS + SOPORTES ORIGINALES \n" +FacturacionMasiva.tf_fechaespecifica.getText()+ String.format("                                 Pagina %d ", writer.getPageNumber()), fuente));
                    
                        }
                    } else {
                          
                          tableCliente.addCell(new Paragraph("Cliente: " + factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getNombres() + " \nServicios: Imagenes de Imagenologia", fuente));
                          tableCliente.addCell(new Paragraph("Nit o CC: " +  factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getDocumento() , fuente));
                             
                    }
                }
            } catch (Exception e) {
                        System.out.println("Cliente->"+factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getNombres());
                        System.out.println("Clieccnte->"+factura.getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getDocumento());
              
                e.printStackTrace();
                if (factura.getPrefijo().substring(0, 1).equals("A") || factura.getPrefijo().substring(0, 1).equals("P") || factura.getPrefijo().substring(0, 1).equals("T")) {
                    tableCliente.addCell(new Paragraph("Entidad: " + factura.getGenEapb().getNombre() + "  Código: " + factura.getGenEapb().getCodigo() + " \nServicios: Imagenes de Imagenología", fuente));
                    tableCliente.addCell(new Paragraph("Nit: " + factura.getGenEapb().getNit() + " Anexos: RIPS + SOPORTES ORIGINALES " +FacturacionMasiva.tf_fechaespecifica.getText()+ String.format("                                 Pagina %d ", writer.getPageNumber()), fuente));

                } else 
                {
                    tableCliente.addCell(new Paragraph("Cliente: N  Nit o CC: N \nServicios: Imagenes de Imagenología", fuente));
                    tableCliente.addCell(new Paragraph("Nit o Cc: N Anexos: RIPS + SOPORTES ORIGINALES " + FacturacionMasiva.tf_fechaespecifica.getText()+ String.format("                                 Pagina %d ", writer.getPageNumber()), fuente));

                }

            }

            tableHeader.addCell(tableImagenTexto);
            tableHeader.addCell(tableCliente);
            tableHeader.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

            //  tableHeader.addCell(String.format("Pagina %010d de", writer.getPageNumber()));
            tableFacturaItems.getDefaultCell().setColspan(0);
            fuente = FontFactory.getFont("arial", 8, Font.NORMAL);
            tableHeader.getDefaultCell().setVerticalAlignment(Element.ALIGN_BOTTOM);
            tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableFacturaItems.getDefaultCell().setBorderWidth((float) 0.25);
            tableFacturaItems.addCell(new Paragraph("Comp", fuente));
            tableFacturaItems.addCell(new Paragraph("Tipo Doc", fuente));
            tableFacturaItems.addCell(new Paragraph("Descripción", fuente));
            //tableFacturaItems.addCell(new Paragraph ("Fecha Ent",fuente));
            //tableFacturaItems.addCell(new Paragraph ("Fecha Sal",fuente));
            tableFacturaItems.addCell(new Paragraph("Código", fuente));
            tableFacturaItems.addCell(new Paragraph("Concepto", fuente));
            tableFacturaItems.addCell(new Paragraph("Cant", fuente));
            tableFacturaItems.addCell(new Paragraph("V/Uni", fuente));
            tableFacturaItems.addCell(new Paragraph("Cuota. Mod", fuente));
            tableFacturaItems.addCell(new Paragraph("Copago", fuente));
            tableFacturaItems.addCell(new Paragraph("Valor Total", fuente));
            tableHeader.addCell(tableFacturaItems);

            //  tableHeader.addCell(cell);
            // Esta linea escribe la tabla como encabezado
            tableHeader.writeSelectedRows(0, -1, 25, 791, writer.getDirectContent());

        } catch (DocumentException de) {
            throw new ExceptionConverter(de);
        } catch (IOException ex) {
            Logger.getLogger(Cabecera2.class.getName()).log(Level.SEVERE, null, ex);
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
