/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.reportespdf;

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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import javax.swing.JOptionPane;
import modelo.GenDepartamentos;
import modelo.GenMunicipios;
import modelo.GenPersonas;
import modelo.GenTiposDocumentos;
import modelo.GenZonaResidencia;
import modelo.HclCie10;
import sihic.SihicApp;
import sihic.util.Cabecera;
import sihic.util.FooterPlantillas;
import sihic.util.HeadFurisp;
import sihic.util.UtilDate;

/**
 *
 * @author karolyani
 */
public class PdfFurisp {

    private static final String BASE_FONT_BOLD = "Trebuchet MS B";

    public void furisp() throws IOException, BadElementException, DocumentException {

        Document document = new Document();
        Font fuente = new Font();
        Font fuente2 = new Font();
        Font fuente4 = FontFactory.getFont("arial", 1.5F, Font.NORMAL);
        document = new Document(PageSize.LETTER, 20f, 20f, 65f, 20f);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/furisp.pdf"));
        Rectangle rect = new Rectangle(PageSize.LETTER);
        writer.setBoxSize("art", rect);
        HeadFurisp headFurisp = new HeadFurisp();
        writer.setPageEvent(headFurisp);
        document.open();

        //PdfPTable III_table = new PdfPTable(new float[] { 5, 5});
        PdfPTable INIT_table = new PdfPTable(new float[]{5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 3});
        PdfPTable I_table = new PdfPTable(new float[]{6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        PdfPTable II_table = new PdfPTable(new float[]{6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        PdfPTable III_table = new PdfPTable(new float[]{6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        PdfPTable IV_table = new PdfPTable(new float[]{6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        PdfPTable V_table = new PdfPTable(new float[]{6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        PdfPTable VI_table = new PdfPTable(new float[]{6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        PdfPTable VI_vehiculosinvolucrados_table = new PdfPTable(new float[]{6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        PdfPTable VII_table = new PdfPTable(new float[]{6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        PdfPTable VIII_table = new PdfPTable(new float[]{6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        PdfPTable IX_table = new PdfPTable(new float[]{6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        PdfPTable X_table = new PdfPTable(new float[]{6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        PdfPTable XI_table = new PdfPTable(new float[]{6, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
        PdfPTable tableImagenTexto = new PdfPTable(new float[]{1, 9});
        Image imagen;
try{
            imagen = Image.getInstance("/home/adminlinux/sihic/escudo.jpeg");
}catch(Exception e)
{
    imagen=null;
    
    
}
        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setFixedHeight(40f);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        tableImagenTexto.getDefaultCell().setRowspan(5);
        tableImagenTexto.addCell(imagen);

        tableImagenTexto.getDefaultCell().setFixedHeight(0f);
        INIT_table.setWidthPercentage(100f);

        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableImagenTexto.setWidthPercentage(100f);
        fuente = FontFactory.getFont("arial", 9, Font.NORMAL);
        tableImagenTexto.addCell(new Paragraph("REPUBLICA DE COLOMBIA\nMINISTERIO DE SALUD Y PROTECCIÓN SOCIAL\nFORMULARIO ÚNICO DE RECLAMACIÓN DE LAS INSTITUCIONES PRESTADORAS DE SALUD POR SERVICIOS PRESTADOS A VICTIMAS\nPRESTADORES DE SERVICIOS DE SALUD - FURISP", fuente));

        //tabla datos de cliente
        Paragraph paragraph = new Paragraph(" ", fuente4);
        INIT_table.setWidthPercentage(100f);

        INIT_table.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
        INIT_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);

        INIT_table.getDefaultCell().setBorderWidthBottom(1);
        INIT_table.getDefaultCell().setBorderWidthTop(1);
        INIT_table.getDefaultCell().setBorderWidthLeft(1);
        INIT_table.getDefaultCell().setBorderWidthRight(1);
        fuente = FontFactory.getFont("arial", 9, Font.NORMAL);
        INIT_table.getDefaultCell().setBorder(0);
        INIT_table.getDefaultCell().setColspan(13);

        //paragraph.setPaddingTop(1);
        INIT_table.getDefaultCell().setColspan(1);
        INIT_table.addCell(new Paragraph("Fecha Radicación: ", fuente));
        INIT_table.getDefaultCell().setBorderWidthBottom(0.1F);
        INIT_table.getDefaultCell().setBorderWidthTop(0.1F);
        INIT_table.getDefaultCell().setBorderWidthLeft(0.1F);
        INIT_table.getDefaultCell().setBorderWidthRight(0.1F);
        if (SihicApp.s_hclfurisp.getFecharadicacion() == null) {
            SihicApp.s_hclfurisp.setFecharadicacion(new Date());
        }

        INIT_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharadicacion())).length() < 2 ? "0" : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharadicacion())).substring(0, 1), fuente));
        INIT_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharadicacion())).length() < 2 ? String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharadicacion())).substring(0, 1) : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharadicacion())).substring(1, 2), fuente));
        INIT_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharadicacion())).length() < 2 ? "0" : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharadicacion())).substring(0, 1), fuente));
        INIT_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharadicacion())).length() < 2 ? String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharadicacion())).substring(0, 1) : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharadicacion())).substring(1, 2), fuente));
        INIT_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFecharadicacion())).substring(0, 1), fuente));
        INIT_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFecharadicacion())).substring(1, 2), fuente));
        INIT_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFecharadicacion())).substring(2, 3), fuente));
        INIT_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFecharadicacion())).substring(3, 4), fuente));
        INIT_table.getDefaultCell().setBorder(0);
        INIT_table.getDefaultCell().setIndent(1);
        INIT_table.addCell(new Paragraph("RG", fuente));
        INIT_table.getDefaultCell().setBorderWidthBottom(0.1F);
        INIT_table.getDefaultCell().setBorderWidthTop(0.1F);
        INIT_table.getDefaultCell().setBorderWidthLeft(0.1F);
        INIT_table.getDefaultCell().setBorderWidthRight(0.1F);
        INIT_table.addCell(new Paragraph(SihicApp.s_hclfurisp.isRg() ? "X" : "", fuente));
        INIT_table.getDefaultCell().setBorder(0);
        INIT_table.addCell(new Paragraph("No. Radicado", fuente));
        INIT_table.getDefaultCell().setBorderWidthBottom(0.1F);
        INIT_table.getDefaultCell().setBorderWidthTop(0.1F);
        INIT_table.getDefaultCell().setBorderWidthLeft(0.1F);
        INIT_table.getDefaultCell().setBorderWidthRight(0.1F);
        INIT_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNoradicado(), fuente));
        INIT_table.getDefaultCell().setBorder(0);
        INIT_table.getDefaultCell().setColspan(13);
        INIT_table.addCell(paragraph);
        INIT_table.getDefaultCell().setColspan(2);
        INIT_table.addCell(new Paragraph("No. Radicado Anterior(Respuesta Glosa Anterior, marcar x en RG)", fuente));
        INIT_table.getDefaultCell().setBorderWidthBottom(0.1F);
        INIT_table.getDefaultCell().setBorderWidthTop(0.1F);
        INIT_table.getDefaultCell().setBorderWidthLeft(0.1F);
        INIT_table.getDefaultCell().setBorderWidthRight(0.1F);
        INIT_table.getDefaultCell().setColspan(4);
        INIT_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNoradicadoanterior(), fuente));
        INIT_table.getDefaultCell().setBorder(0);
        INIT_table.getDefaultCell().setColspan(4);
        INIT_table.addCell(new Paragraph("Nro. Factura/Cuenta de Cobro", fuente));
        INIT_table.getDefaultCell().setBorderWidthBottom(0.1F);
        INIT_table.getDefaultCell().setBorderWidthTop(0.1F);
        INIT_table.getDefaultCell().setBorderWidthLeft(0.1F);
        INIT_table.getDefaultCell().setBorderWidthRight(0.1F);
        INIT_table.getDefaultCell().setColspan(3);
        INIT_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNoradicadoanterior(), fuente));
        document.add(INIT_table);
        I_table.setWidthPercentage(100f);
        I_table.getDefaultCell().setBorderWidth(0);
        I_table.getDefaultCell().setColspan(34);
        I_table.addCell(paragraph);
        I_table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        I_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        I_table.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
        I_table.addCell(new Paragraph("I. DATOS DE LA INSTITUCIÓN PRESTADORA DE SALUD", fuente));
        I_table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);

        I_table.addCell(paragraph);

        I_table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        I_table.getDefaultCell().setColspan(1);
        I_table.getDefaultCell().setBorder(0);
        I_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        I_table.addCell(new Paragraph("Razón Social", fuente));
        I_table.getDefaultCell().setColspan(33);
        I_table.getDefaultCell().setBorderWidthBottom(0.1F);
        I_table.getDefaultCell().setBorderWidthLeft(0.1F);
        I_table.getDefaultCell().setBorderWidthTop(0.1F);
        I_table.getDefaultCell().setBorderWidthRight(0.1F);
        I_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        I_table.addCell(new Paragraph(SihicApp.genUnidadesOrganizacion.getNombre(), fuente));
        I_table.getDefaultCell().setBorder(0);
        I_table.getDefaultCell().setBorder(0);
        I_table.getDefaultCell().setColspan(34);
        I_table.addCell(paragraph);
        I_table.getDefaultCell().setColspan(1);
        I_table.addCell(new Paragraph("Código Habilitación", fuente));
        I_table.getDefaultCell().setBorderWidthBottom(0.1F);
        I_table.getDefaultCell().setBorderWidthLeft(0.1F);
        I_table.getDefaultCell().setBorderWidthTop(0.1F);
        I_table.getDefaultCell().setBorderWidthRight(0.1F);
        I_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 12; i++) {
            if (i < SihicApp.genUnidadesOrganizacion.getCodigo().length()) {
                I_table.addCell(new Paragraph(SihicApp.genUnidadesOrganizacion.getCodigo().substring(i, (i + 1)), fuente));
            } else {
                I_table.addCell(new Paragraph("  ", fuente));
            }
        }
        I_table.getDefaultCell().setBorder(0);
        I_table.getDefaultCell().setColspan(5);
        I_table.addCell(new Paragraph("Nit", fuente));
        I_table.getDefaultCell().setColspan(1);
        I_table.getDefaultCell().setBorderWidthBottom(0.1F);
        I_table.getDefaultCell().setBorderWidthLeft(0.1F);
        I_table.getDefaultCell().setBorderWidthTop(0.1F);
        I_table.getDefaultCell().setBorderWidthRight(0.1F);
        for (int i = 0; i < 16; i++) {
            if (i < SihicApp.genUnidadesOrganizacion.getNit().length()) {
                I_table.addCell(new Paragraph(SihicApp.genUnidadesOrganizacion.getNit().substring(i, (i + 1)), fuente));
            } else {
                I_table.addCell(new Paragraph("  ", fuente));
            }
        }
        I_table.getDefaultCell().setBorder(0);
        I_table.getDefaultCell().setColspan(34);
        I_table.addCell(paragraph);
        I_table.getDefaultCell().setColspan(1);
        I_table.addCell(new Paragraph("Dirección", fuente));
        I_table.getDefaultCell().setBorderWidthBottom(0.1F);
        I_table.getDefaultCell().setBorderWidthLeft(0.1F);
        I_table.getDefaultCell().setBorderWidthTop(0.1F);
        I_table.getDefaultCell().setBorderWidthRight(0.1F);
        I_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 33; i++) {
            if (i < SihicApp.genUnidadesOrganizacion.getDireccion().length()) {
                I_table.addCell(new Paragraph(SihicApp.genUnidadesOrganizacion.getDireccion().substring(i, (i + 1)), fuente));
            } else {
                I_table.addCell(new Paragraph("  ", fuente));
            }
        }
        I_table.getDefaultCell().setColspan(34);
        I_table.getDefaultCell().setBorder(0);
        I_table.addCell(paragraph);
        I_table.getDefaultCell().setColspan(1);
        I_table.addCell(new Paragraph("Departamento", fuente));
        I_table.getDefaultCell().setBorderWidthBottom(0.1F);
        I_table.getDefaultCell().setBorderWidthLeft(0.1F);
        I_table.getDefaultCell().setBorderWidthTop(0.1F);
        I_table.getDefaultCell().setBorderWidthRight(0.1F);
        I_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 16; i++) {
            if (i < SihicApp.genUnidadesOrganizacion.getGenMunicipios().getGenDepartamentos().getNombre().length()) {
                I_table.addCell(new Paragraph(SihicApp.genUnidadesOrganizacion.getGenMunicipios().getGenDepartamentos().getNombre().substring(i, (i + 1)), fuente));
            } else {
                I_table.addCell(new Paragraph("  ", fuente));
            }
        }
        I_table.getDefaultCell().setColspan(2);
        I_table.getDefaultCell().setBorder(0);
        I_table.addCell(new Paragraph("Cod", fuente));
        I_table.getDefaultCell().setColspan(1);
        I_table.getDefaultCell().setBorderWidthBottom(0.1F);
        I_table.getDefaultCell().setBorderWidthLeft(0.1F);
        I_table.getDefaultCell().setBorderWidthTop(0.1F);
        I_table.getDefaultCell().setBorderWidthRight(0.1F);
        I_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 2; i++) {
            if (i < SihicApp.genUnidadesOrganizacion.getGenMunicipios().getGenDepartamentos().getCodigo().length()) {
                I_table.addCell(new Paragraph(SihicApp.genUnidadesOrganizacion.getGenMunicipios().getGenDepartamentos().getCodigo().substring(i, (i + 1)), fuente));
            } else {
                I_table.addCell(new Paragraph("  ", fuente));
            }
        }
        I_table.getDefaultCell().setColspan(3);
        I_table.getDefaultCell().setBorder(0);
        I_table.addCell(new Paragraph("Teléfono", fuente));
        I_table.getDefaultCell().setColspan(1);
        I_table.getDefaultCell().setBorderWidthBottom(0.1F);
        I_table.getDefaultCell().setBorderWidthLeft(0.1F);
        I_table.getDefaultCell().setBorderWidthTop(0.1F);
        I_table.getDefaultCell().setBorderWidthRight(0.1F);
        I_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 10; i++) {
            if (i < SihicApp.genUnidadesOrganizacion.getTelefono().length()) {
                I_table.addCell(new Paragraph(SihicApp.genUnidadesOrganizacion.getTelefono().substring(i, (i + 1)), fuente));
            } else {
                I_table.addCell(new Paragraph("  ", fuente));
            }
        }
        I_table.getDefaultCell().setColspan(34);
        I_table.getDefaultCell().setBorder(0);
        I_table.addCell(paragraph);
        I_table.getDefaultCell().setColspan(1);
        I_table.addCell(new Paragraph("Municipio", fuente));
        I_table.getDefaultCell().setBorderWidthBottom(0.1F);
        I_table.getDefaultCell().setBorderWidthLeft(0.1F);
        I_table.getDefaultCell().setBorderWidthTop(0.1F);
        I_table.getDefaultCell().setBorderWidthRight(0.1F);
        I_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 21; i++) {
            if (i < SihicApp.genUnidadesOrganizacion.getGenMunicipios().getNombre().length()) {
                I_table.addCell(new Paragraph(SihicApp.genUnidadesOrganizacion.getGenMunicipios().getNombre().substring(i, (i + 1)), fuente));
            } else {
                I_table.addCell(new Paragraph("  ", fuente));
            }
        }
        I_table.getDefaultCell().setColspan(2);
        I_table.getDefaultCell().setBorder(0);
        I_table.addCell(new Paragraph("Cod", fuente));
        I_table.getDefaultCell().setColspan(1);
        I_table.getDefaultCell().setBorderWidthBottom(0.1F);
        I_table.getDefaultCell().setBorderWidthLeft(0.1F);
        I_table.getDefaultCell().setBorderWidthTop(0.1F);
        I_table.getDefaultCell().setBorderWidthRight(0.1F);
        I_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 10; i++) {
            if (i < SihicApp.genUnidadesOrganizacion.getGenMunicipios().getCodigo().substring(2, 5).length()) {
                I_table.addCell(new Paragraph(SihicApp.genUnidadesOrganizacion.getGenMunicipios().getCodigo().substring(i + 2, (i + 3)), fuente));
            } else {
                I_table.getDefaultCell().setBorder(0);
                I_table.addCell(new Paragraph("  ", fuente));
            }
        }
        document.add(I_table);
        //***************************************************************************II
        II_table.getDefaultCell().setBorderWidth(0);
        II_table.getDefaultCell().setColspan(34);
        II_table.setWidthPercentage(100f);
        II_table.addCell(paragraph);
        II_table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        II_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        II_table.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
        II_table.addCell(new Paragraph("II. DATOS DE LA VICTIMA DEL EVENTO CATASTRÓFICO O ACCIDENTE DE TRANSITO", fuente));
        II_table.getDefaultCell().setBorder(0);
        II_table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        II_table.addCell(paragraph);
        II_table.getDefaultCell().setColspan(16);
        II_table.getDefaultCell().setBorderWidthBottom(0.1F);
        II_table.getDefaultCell().setBorderWidthLeft(0.1F);
        II_table.getDefaultCell().setBorderWidthTop(0.1F);
        II_table.getDefaultCell().setBorderWidthRight(0.1F);

        II_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        try
        {
        II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getPrimerApellido(), fuente));
        }catch(Exception e)
        {
           II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getPrimerApellido(), fuente));
         
        }
        II_table.getDefaultCell().setBorder(0);
        II_table.getDefaultCell().setColspan(2);
        II_table.addCell(new Paragraph("", fuente));

        II_table.getDefaultCell().setColspan(16);
        II_table.getDefaultCell().setBorderWidthBottom(0.1F);
        II_table.getDefaultCell().setBorderWidthLeft(0.1F);
        II_table.getDefaultCell().setBorderWidthTop(0.1F);
        II_table.getDefaultCell().setBorderWidthRight(0.1F);
        II_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        try
        {
        II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getSegundoApellido(), fuente));
        }catch(Exception e)
        {
            II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getSegundoApellido(), fuente));
       
        }
        II_table.getDefaultCell().setBorder(0);
        II_table.getDefaultCell().setColspan(16);
        II_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        II_table.addCell(new Paragraph("1er Apellido", fuente));
        II_table.getDefaultCell().setColspan(2);
        II_table.addCell(new Paragraph(" ", fuente));
        II_table.getDefaultCell().setColspan(16);
        II_table.addCell(new Paragraph("2do Apellido", fuente));
        II_table.getDefaultCell().setBorder(0);
        II_table.getDefaultCell().setColspan(34);
        II_table.addCell(paragraph);
        II_table.getDefaultCell().setColspan(16);
        II_table.getDefaultCell().setBorderWidthBottom(0.1F);
        II_table.getDefaultCell().setBorderWidthLeft(0.1F);
        II_table.getDefaultCell().setBorderWidthTop(0.1F);
        II_table.getDefaultCell().setBorderWidthRight(0.1F);

        II_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        try
        {
        II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getPrimerNombre(), fuente));
        }catch(Exception e)
        {
           II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getPrimerNombre(), fuente));
        
        }
        II_table.getDefaultCell().setBorder(0);
        II_table.getDefaultCell().setColspan(2);
        II_table.addCell(new Paragraph("", fuente));

        II_table.getDefaultCell().setColspan(16);
        II_table.getDefaultCell().setBorderWidthBottom(0.1F);
        II_table.getDefaultCell().setBorderWidthLeft(0.1F);
        II_table.getDefaultCell().setBorderWidthTop(0.1F);
        II_table.getDefaultCell().setBorderWidthRight(0.1F);
        II_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        try
        {
        II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getSegundoNombre(), fuente));
        }catch(Exception e)
        {
             II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getSegundoNombre(), fuente));
      
        }
        II_table.getDefaultCell().setBorder(0);
        II_table.getDefaultCell().setColspan(16);
        II_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        II_table.addCell(new Paragraph("1er Nombre", fuente));
        II_table.getDefaultCell().setColspan(2);
        II_table.addCell(new Paragraph(" ", fuente));
        II_table.getDefaultCell().setColspan(16);
        II_table.addCell(new Paragraph("2do Nombre", fuente));
        II_table.getDefaultCell().setBorder(0);
        II_table.getDefaultCell().setColspan(34);
        II_table.addCell(paragraph);
        II_table.getDefaultCell().setColspan(1);
        II_table.addCell(new Paragraph("Tipo de Documento", fuente));
        II_table.getDefaultCell().setColspan(2);
        II_table.getDefaultCell().setBorderWidthBottom(0.1F);
        II_table.getDefaultCell().setBorderWidthLeft(0.1F);
        II_table.getDefaultCell().setBorderWidthTop(0.1F);
        II_table.getDefaultCell().setBorderWidthRight(0.1F);
        try {
            for (int i = 0; i < 8; i++) {
                switch (i) {
                    case 0:
                      try
                      {
                        II_table.addCell(new Paragraph("CC:" + (SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().equals("CC") ? "X" : ""), fuente));
                      }catch(Exception e)
                      {
                         II_table.addCell(new Paragraph("CC:" + (SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().equals("CC") ? "X" : ""), fuente));  
                      }
                        break;
                    case 1:
                        try
                        {
                        II_table.addCell(new Paragraph("CE:" + (SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().equals("CE") ? "X" : ""), fuente));
                        }catch(Exception e)
                        {
                             II_table.addCell(new Paragraph("CE:" + (SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().equals("CE") ? "X" : ""), fuente));
                        }
                        break;
                    case 2:
                        try
                        {
                        II_table.addCell(new Paragraph("PA:" + (SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().equals("PA") ? "X" : ""), fuente));
                        }catch(Exception e)
                        {
                              II_table.addCell(new Paragraph("PA:" + (SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().equals("PA") ? "X" : ""), fuente));
                      
                        }
                        break;
                    case 3:
                        try
                        {
                        II_table.addCell(new Paragraph("TI:" + (SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().equals("TI") ? "X" : ""), fuente));
                        }catch(Exception e)
                        {
                            II_table.addCell(new Paragraph("TI:" + (SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().equals("TI") ? "X" : ""), fuente));
                        
                        }
                        break;
                    case 4:
                        try
                        {
                        II_table.addCell(new Paragraph("RC:" + (SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().equals("RC") ? "X" : ""), fuente));
                        }catch(Exception e)
                        {
                             II_table.addCell(new Paragraph("RC:" + (SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().equals("RC") ? "X" : ""), fuente));
                       
                        }
                        break;
                    case 5:
                       try
                       {
                        II_table.addCell(new Paragraph("AS:" + (SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().equals("AS") ? "X" : ""), fuente));
                       }catch(Exception e)
                       {
                            II_table.addCell(new Paragraph("AS:" + (SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().equals("AS") ? "X" : ""), fuente));
                       
                       }
                        break;
                    case 6:
                        try
                        {
                        II_table.addCell(new Paragraph("MS:" + (SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().equals("MS") ? "X" : ""), fuente));
                        }catch(Exception e)
                        {
                             II_table.addCell(new Paragraph("MS:" + (SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().equals("MS") ? "X" : ""), fuente));
                       
                        }
                        break;
                    case 7:
                        try
                        {
                        II_table.addCell(new Paragraph("CD:" + (SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().equals("CD") ? "X" : ""), fuente));
                        }catch(Exception e)
                        {
                            II_table.addCell(new Paragraph("CD:" + (SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenTiposDocumentos().getSigla().equals("CD") ? "X" : ""), fuente));
                        
                        }
                        break;
                }
            }
        } catch (Exception e) {
            II_table.addCell(new Paragraph("CC:X"));
        }
        II_table.getDefaultCell().setColspan(5);
        II_table.getDefaultCell().setBorder(0);
        II_table.addCell(new Paragraph("N° Documento", fuente));
        II_table.getDefaultCell().setColspan(1);
        II_table.getDefaultCell().setBorderWidthBottom(0.1F);
        II_table.getDefaultCell().setBorderWidthLeft(0.1F);
        II_table.getDefaultCell().setBorderWidthTop(0.1F);
        II_table.getDefaultCell().setBorderWidthRight(0.1F);
        if(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes()!=null)
        {
        for (int i = 0; i < 12; i++) {
            if (i < SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getDocumento().length()) {
                II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getDocumento().substring(i, i + 1), fuente));
            } else {
                II_table.addCell(new Paragraph(" ", fuente));

            }
        }
        }
        else
        {
          for (int i = 0; i < 12; i++) {
            if (i < SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().length()) {
                II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getDocumento().substring(i, i + 1), fuente));
            } else {
                II_table.addCell(new Paragraph(" ", fuente));

            }
        }  
        }
        II_table.getDefaultCell().setColspan(34);
        II_table.getDefaultCell().setBorder(0);
        II_table.addCell(paragraph);
        II_table.getDefaultCell().setColspan(3);
        II_table.getDefaultCell().setBorder(0);
        II_table.addCell(new Paragraph("Fecha de Nacimiento", fuente));
        II_table.getDefaultCell().setColspan(1);
        II_table.getDefaultCell().setBorderWidthBottom(0.5F);
        II_table.getDefaultCell().setBorderWidthLeft(0.5F);
        II_table.getDefaultCell().setBorderWidthRight(0.5F);
        II_table.getDefaultCell().setBorderWidthTop(0.5F);
        try
        {
        II_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getFechaNacimiento())).length() < 2 ? "0" : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(0, 1), fuente));
        II_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getFechaNacimiento())).length() < 2 ? String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(0, 1) : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(1, 2), fuente));
        II_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getFechaNacimiento())).length() < 2 ? "0" : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(0, 1), fuente));
        II_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getFechaNacimiento())).length() < 2 ? String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(0, 1) : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(1, 2), fuente));
        II_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(0, 1), fuente));
        II_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(1, 2), fuente));
        II_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(2, 3), fuente));
        II_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(3, 4), fuente));
        }catch(Exception e)
        {
        II_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento())).length() < 2 ? "0" : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(0, 1), fuente));
        II_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento())).length() < 2 ? String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(0, 1) : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(1, 2), fuente));
        II_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento())).length() < 2 ? "0" : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(0, 1), fuente));
        II_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento())).length() < 2 ? String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(0, 1) : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(1, 2), fuente));
        II_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(0, 1), fuente));
        II_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(1, 2), fuente));
        II_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(2, 3), fuente));
        II_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getFechaNacimiento())).substring(3, 4), fuente));
         
        }
        II_table.getDefaultCell().setBorder(0);
        II_table.getDefaultCell().setColspan(2);
        II_table.addCell(new Paragraph("Sexo", fuente));
        II_table.getDefaultCell().setBorderWidthBottom(0.5F);
        II_table.getDefaultCell().setBorderWidthLeft(0.5F);
        II_table.getDefaultCell().setBorderWidthRight(0.5F);
        II_table.getDefaultCell().setBorderWidthTop(0.5F);
        try
        {
        II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenSexo().getSigla().equals("F") ? "F:X" : "", fuente));
        }catch(Exception e)
        {
            II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla().equals("F") ? "F:X" : "", fuente));
        
        }
        II_table.getDefaultCell().setBorder(0);
        II_table.getDefaultCell().setColspan(1);
        II_table.addCell(new Paragraph(" ", fuente));
        II_table.getDefaultCell().setColspan(2);
        II_table.getDefaultCell().setBorderWidthBottom(0.5F);
        II_table.getDefaultCell().setBorderWidthLeft(0.5F);
        II_table.getDefaultCell().setBorderWidthRight(0.5F);
        II_table.getDefaultCell().setBorderWidthTop(0.5F);
        try
        {
        II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenSexo().getSigla().equals("M") ? "M:X" : "", fuente));
        }catch(Exception e)
        {
           II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenSexo().getSigla().equals("M") ? "M:X" : "", fuente));
        
        }
        II_table.getDefaultCell().setBorder(0);
        II_table.getDefaultCell().setColspan(16);
        II_table.addCell(new Paragraph(" ", fuente));
        II_table.getDefaultCell().setColspan(34);
        II_table.getDefaultCell().setBorder(0);
        II_table.addCell(paragraph);
        II_table.getDefaultCell().setColspan(1);
        II_table.addCell(new Paragraph("Dirección", fuente));
        II_table.getDefaultCell().setBorderWidthBottom(0.1F);
        II_table.getDefaultCell().setBorderWidthLeft(0.1F);
        II_table.getDefaultCell().setBorderWidthTop(0.1F);
        II_table.getDefaultCell().setBorderWidthRight(0.1F);
        II_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        if(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes()!=null)
        {  
        for (int i = 0; i < 33; i++) {
            if (i < SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getDireccion().length()) {
                II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getDireccion().substring(i, (i + 1)), fuente));
            } else {
                II_table.addCell(new Paragraph("  ", fuente));
            }
        
        }
        }
        else
        {
          for (int i = 0; i < 33; i++) {
            if (i < SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getDireccion().length()) {
                II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getDireccion().substring(i, (i + 1)), fuente));
            } else {
                II_table.addCell(new Paragraph("  ", fuente));
            }
        
        }      
                }
        II_table.getDefaultCell().setColspan(34);
        II_table.getDefaultCell().setBorder(0);
        II_table.addCell(paragraph);
        II_table.getDefaultCell().setColspan(1);
        II_table.addCell(new Paragraph("Departamento", fuente));
        II_table.getDefaultCell().setBorderWidthBottom(0.1F);
        II_table.getDefaultCell().setBorderWidthLeft(0.1F);
        II_table.getDefaultCell().setBorderWidthTop(0.1F);
        II_table.getDefaultCell().setBorderWidthRight(0.1F);
        II_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
     
        for (int i = 0; i < 16; i++) {  
            try
            {
            if (i < SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getNombre().length()) {
                II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getNombre().substring(i, (i + 1)), fuente));
            } else {

                II_table.addCell(new Paragraph("  ", fuente));
            }
            }catch(Exception e)
            {
                try
                {
                 if (i < SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getNombre().length()) {
                II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getNombre().substring(i, (i + 1)), fuente));
            } else {

                II_table.addCell(new Paragraph("  ", fuente));
            }
                }catch(Exception e30)
                        {
                         II_table.addCell(new Paragraph("  ", fuente));
                        }
            }
        }
        II_table.getDefaultCell().setColspan(2);
        II_table.getDefaultCell().setBorder(0);
        II_table.addCell(new Paragraph("Cod", fuente));
        II_table.getDefaultCell().setColspan(1);
        II_table.getDefaultCell().setBorderWidthBottom(0.1F);
        II_table.getDefaultCell().setBorderWidthLeft(0.1F);
        II_table.getDefaultCell().setBorderWidthTop(0.1F);
        II_table.getDefaultCell().setBorderWidthRight(0.1F);
        II_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 15; i++) {
            try
            {
            if (i < SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo().length()) {
                II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo().substring(i, (i + 1)), fuente));
            } else {
                II_table.getDefaultCell().setBorder(0);
                II_table.addCell(new Paragraph("  ", fuente));
            }
            }catch( Exception e4)
            {
                try
                {
               if (i < SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo().length()) {
                II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getGenDepartamentos().getCodigo().substring(i, (i + 1)), fuente));
            } else {
                II_table.getDefaultCell().setBorder(0);
                II_table.addCell(new Paragraph("  ", fuente));
            } 
                }catch(Exception e5)
                {
                  II_table.getDefaultCell().setBorder(0);
                 II_table.addCell(new Paragraph("  ", fuente));  
                }
            }    
        }

        II_table.getDefaultCell().setColspan(34);
        II_table.getDefaultCell().setBorder(0);
        II_table.addCell(paragraph);
        II_table.getDefaultCell().setColspan(1);
        II_table.addCell(new Paragraph("Municipio", fuente));
        II_table.getDefaultCell().setBorderWidthBottom(0.1F);
        II_table.getDefaultCell().setBorderWidthLeft(0.1F);
        II_table.getDefaultCell().setBorderWidthTop(0.1F);
        II_table.getDefaultCell().setBorderWidthRight(0.1F);
        II_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 21; i++) {
            try{
            if (i < SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenMunicipios().getNombre().length()) {
                II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenMunicipios().getNombre().substring(i, (i + 1)), fuente));
            } else {
                II_table.addCell(new Paragraph("  ", fuente));
            }
            }catch(Exception e)
            {
                try
                {
                if (i < SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getNombre().length()) {
                II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getNombre().substring(i, (i + 1)), fuente));
            } else {
                II_table.addCell(new Paragraph("  ", fuente));
            } 
                }catch(Exception e2)
                {
                    II_table.addCell(new Paragraph("  ", fuente)); 
                }
            }
        }
        II_table.getDefaultCell().setColspan(2);
        II_table.getDefaultCell().setBorder(0);
        II_table.addCell(new Paragraph("Cod", fuente));
        II_table.getDefaultCell().setColspan(1);
        II_table.getDefaultCell().setBorderWidthBottom(0.1F);
        II_table.getDefaultCell().setBorderWidthLeft(0.1F);
        II_table.getDefaultCell().setBorderWidthTop(0.1F);
        II_table.getDefaultCell().setBorderWidthRight(0.1F);
        II_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 10; i++) {
            try
            {
            if (i < SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5).length()) {
                II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(i + 2, (i + 3)), fuente));
            } else {
                II_table.getDefaultCell().setBorder(0);
                II_table.addCell(new Paragraph("  ", fuente));
            }
            }catch(Exception e)
            {
                try
                {
               if (i < SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(2, 5).length()) {
                II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getFactura().getFacturaItems().get(0).getAgnCitas().getGenPacientes().getGenPersonas().getGenMunicipios().getCodigo().substring(i + 2, (i + 3)), fuente));
            } else {
                II_table.getDefaultCell().setBorder(0);
                II_table.addCell(new Paragraph("  ", fuente));
            } 
                }catch(Exception e2)
                {
                    II_table.getDefaultCell().setBorder(0);
                    II_table.addCell(new Paragraph("  ", fuente)); 
                }
            }
        }
        II_table.getDefaultCell().setColspan(34);
        II_table.getDefaultCell().setBorder(0);
        II_table.addCell(paragraph);
        II_table.getDefaultCell().setColspan(6);
        II_table.getDefaultCell().setBorder(0);
        II_table.addCell(new Paragraph("Condición del Accidentado", fuente));
        II_table.getDefaultCell().setColspan(1);
        II_table.getDefaultCell().setBorderWidthBottom(0.1F);
        II_table.getDefaultCell().setBorderWidthLeft(0.1F);
        II_table.getDefaultCell().setBorderWidthTop(0.1F);
        II_table.getDefaultCell().setBorderWidthRight(0.1F);
        II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getCondicionaccidentado() == 0 ? "X" : "", fuente));
        II_table.getDefaultCell().setColspan(6);
        II_table.getDefaultCell().setBorder(0);
        II_table.addCell(new Paragraph("Conductor", fuente));
        II_table.getDefaultCell().setColspan(1);
        II_table.getDefaultCell().setBorderWidthBottom(0.1F);
        II_table.getDefaultCell().setBorderWidthLeft(0.1F);
        II_table.getDefaultCell().setBorderWidthTop(0.1F);
        II_table.getDefaultCell().setBorderWidthRight(0.1F);
        II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getCondicionaccidentado() == 1 ? "X" : "", fuente));
        II_table.getDefaultCell().setColspan(6);
        II_table.getDefaultCell().setBorder(0);
        II_table.addCell(new Paragraph("Peatón", fuente));
        II_table.getDefaultCell().setColspan(1);
        II_table.getDefaultCell().setBorderWidthBottom(0.1F);
        II_table.getDefaultCell().setBorderWidthLeft(0.1F);
        II_table.getDefaultCell().setBorderWidthTop(0.1F);
        II_table.getDefaultCell().setBorderWidthRight(0.1F);
        II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getCondicionaccidentado() == 2 ? "X" : "", fuente));
        II_table.getDefaultCell().setColspan(6);
        II_table.getDefaultCell().setBorder(0);
        II_table.addCell(new Paragraph("Ocupante", fuente));
        II_table.getDefaultCell().setColspan(1);
        II_table.getDefaultCell().setBorderWidthBottom(0.1F);
        II_table.getDefaultCell().setBorderWidthLeft(0.1F);
        II_table.getDefaultCell().setBorderWidthTop(0.1F);
        II_table.getDefaultCell().setBorderWidthRight(0.1F);
        II_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getCondicionaccidentado() == 3 ? "X" : "", fuente));
        II_table.getDefaultCell().setColspan(6);
        II_table.getDefaultCell().setBorder(0);
        II_table.addCell(new Paragraph("Ciclista", fuente));
        document.add(II_table);
        //*********************************************************************************III
        III_table.setWidthPercentage(100f);
        III_table.getDefaultCell().setBorderWidth(0);
        III_table.getDefaultCell().setColspan(34);
        III_table.addCell(paragraph);
        III_table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        III_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        III_table.addCell(new Paragraph("III. DATOS DEL SITIO DONDE OCURRIO EL EVENTO CATASTROFICO O EL ACCIDENTE DE TRANSITO", fuente));
        III_table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        III_table.addCell(paragraph);
        III_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        Font fuente3 = FontFactory.getFont("arial", 9, Font.UNDERLINE);
        III_table.addCell(new Paragraph("Naturaleza del evento", fuente3));
        III_table.getDefaultCell().setColspan(2);
        III_table.addCell(new Paragraph(" ", fuente));
        III_table.getDefaultCell().setColspan(7);
        III_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        III_table.addCell(new Paragraph("Accidente de Transito", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.ACCIDENTETRANSITO.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setColspan(24);
        III_table.getDefaultCell().setBorder(0);
        III_table.addCell(new Paragraph(" ", fuente));
        III_table.getDefaultCell().setColspan(34);
        III_table.getDefaultCell().setBorder(0);
        III_table.addCell(paragraph);
        III_table.getDefaultCell().setColspan(2);
        III_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        III_table.addCell(new Paragraph("Naturales:", fuente));
        III_table.getDefaultCell().setColspan(7);
        III_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        III_table.addCell(new Paragraph("Sismo", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.SISMO.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(7);
        III_table.addCell(new Paragraph("Maremoto", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.MAREMOTO.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(7);
        III_table.addCell(new Paragraph("Erupciones Volcánicas", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.ERUCIONESVOLCANICAS.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(7);
        III_table.addCell(new Paragraph("Huracán", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.HURACAN.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setColspan(34);
        III_table.getDefaultCell().setBorder(0);
        III_table.addCell(paragraph);
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(2);
        III_table.addCell(new Paragraph(" ", fuente));
        III_table.getDefaultCell().setColspan(7);
        III_table.addCell(new Paragraph("Inundaciones", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.INUNDACIONES.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(7);
        III_table.addCell(new Paragraph("Avalancha", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.AVALANCHA.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(7);
        III_table.addCell(new Paragraph("Deslizamiento de Tierra", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.DELIZAMIENTOTIERRA.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(7);
        III_table.addCell(new Paragraph("Incencio Natural", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.INCENDIONATURAL.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setColspan(34);
        III_table.getDefaultCell().setBorder(0);
        III_table.addCell(paragraph);
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(2);
        III_table.addCell(new Paragraph(" ", fuente));
        III_table.getDefaultCell().setColspan(7);
        III_table.addCell(new Paragraph("Rayo", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.RAYO.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(7);
        III_table.addCell(new Paragraph("Vendaval", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.VENDAVAL.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(7);
        III_table.addCell(new Paragraph("Tornado", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.TORNADO.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(8);
        III_table.getDefaultCell().setBorder(0);
        III_table.addCell(new Paragraph(" ", fuente));
        III_table.getDefaultCell().setColspan(34);
        III_table.getDefaultCell().setBorder(0);
        III_table.addCell(paragraph);
        III_table.getDefaultCell().setColspan(2);
        III_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        III_table.addCell(new Paragraph("Terroristas:", fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(7);
        III_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        III_table.addCell(new Paragraph("Explosión", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.EXPLOSION.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(7);
        III_table.addCell(new Paragraph("Masacre", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.MASACRE.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(7);
        III_table.addCell(new Paragraph("Mina Antipersonal", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.MINAANTIPERSONAL.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(7);
        III_table.addCell(new Paragraph("Combate", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.COMBATE.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setColspan(34);
        III_table.getDefaultCell().setBorder(0);
        III_table.addCell(paragraph);
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(2);
        III_table.addCell(new Paragraph(" ", fuente));
        III_table.getDefaultCell().setColspan(7);
        III_table.addCell(new Paragraph("Incendio", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.INCENDIO.ordinal() ? "X" : "", fuente));

        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(7);
        III_table.addCell(new Paragraph("Ataques a Municipios", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.ATAQUEAMUNICIPIOS.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(16);
        III_table.addCell(new Paragraph(" ", fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(34);
        III_table.addCell(paragraph);
        III_table.getDefaultCell().setColspan(1);
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        III_table.addCell(new Paragraph("Otro", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.5F);
        III_table.getDefaultCell().setBorderWidthLeft(0.5F);
        III_table.getDefaultCell().setBorderWidthTop(0.5F);
        III_table.getDefaultCell().setBorderWidthRight(0.5F);
        III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturalezaevento() == jenum.EnumNaturalezaEvento.OTRO.ordinal() ? "X" : "", fuente));
        III_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(3);
        III_table.addCell(new Paragraph("Cual?", fuente));
        III_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        III_table.getDefaultCell().setColspan(1);
        III_table.getDefaultCell().setBorderWidthBottom(0.5F);
        III_table.getDefaultCell().setBorderWidthLeft(0.5F);
        III_table.getDefaultCell().setBorderWidthTop(0.5F);
        III_table.getDefaultCell().setBorderWidthRight(0.5F);
        if (SihicApp.s_hclfurisp.getNaturaleza() == null) {
            SihicApp.s_hclfurisp.setNaturaleza("");
        }
        for (int i = 0; i < 29; i++) {
            if (i < SihicApp.s_hclfurisp.getNaturaleza().length()) {
                III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNaturaleza().substring(i, i + 1), fuente));
            } else {
                if (i < 25) {
                    III_table.addCell(new Paragraph(" ", fuente));
                } else {
                    III_table.getDefaultCell().setBorder(0);
                    III_table.addCell(new Paragraph(" ", fuente));
                }

            }

        }
        III_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        III_table.getDefaultCell().setColspan(34);
        III_table.getDefaultCell().setBorder(0);
        III_table.addCell(paragraph);
        III_table.getDefaultCell().setColspan(3);
        III_table.addCell(new Paragraph("Dirección de la ocurrencia", fuente));
        III_table.getDefaultCell().setColspan(1);
        III_table.getDefaultCell().setBorderWidthBottom(0.5F);
        III_table.getDefaultCell().setBorderWidthLeft(0.5F);
        III_table.getDefaultCell().setBorderWidthRight(0.5F);
        III_table.getDefaultCell().setBorderWidthTop(0.5F);
        for (int i = 0; i < 31; i++) {
            if (SihicApp.s_hclfurisp.getDireccionocurrencia() == null) {
                SihicApp.s_hclfurisp.setDireccionocurrencia("");
            }
            if (i < SihicApp.s_hclfurisp.getDireccionocurrencia().length()) {
                III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getDireccionocurrencia().substring(i, i + 1), fuente));
            } else {
                III_table.addCell(new Paragraph(""));

            }
        }
        III_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        III_table.getDefaultCell().setColspan(34);
        III_table.getDefaultCell().setBorder(0);
        III_table.addCell(paragraph);
        III_table.getDefaultCell().setColspan(3);
        III_table.getDefaultCell().setBorder(0);
        III_table.addCell(new Paragraph("Fecha Evento/Accidente", fuente));
        III_table.getDefaultCell().setColspan(1);
        III_table.getDefaultCell().setBorderWidthBottom(0.5F);
        III_table.getDefaultCell().setBorderWidthLeft(0.5F);
        III_table.getDefaultCell().setBorderWidthRight(0.5F);
        III_table.getDefaultCell().setBorderWidthTop(0.5F);
        if (SihicApp.s_hclfurisp.getFecharevento() == null) {
            SihicApp.s_hclfurisp.setFecharevento(new Date());
        }
        III_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharevento())).length() < 2 ? "0" : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharevento())).substring(0, 1), fuente));
        III_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharevento())).length() < 2 ? String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharevento())).substring(0, 1) : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharevento())).substring(1, 2), fuente));
        III_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharevento())).length() < 2 ? "0" : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharevento())).substring(0, 1), fuente));
        III_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharevento())).length() < 2 ? String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharevento())).substring(0, 1) : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharevento())).substring(1, 2), fuente));
        III_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFecharevento())).substring(0, 1), fuente));
        III_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFecharevento())).substring(1, 2), fuente));
        III_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFecharevento())).substring(2, 3), fuente));
        III_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFecharevento())).substring(3, 4), fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(3);
        III_table.addCell(new Paragraph(" ", fuente));
        III_table.getDefaultCell().setColspan(3);
        III_table.addCell(new Paragraph("Hora", fuente));
        III_table.getDefaultCell().setColspan(1);
        III_table.getDefaultCell().setBorderWidthBottom(0.5F);
        III_table.getDefaultCell().setBorderWidthLeft(0.5F);
        III_table.getDefaultCell().setBorderWidthRight(0.5F);
        III_table.getDefaultCell().setBorderWidthTop(0.5F);
        III_table.addCell(new Paragraph(String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFecharevento())).length() < 2 ? "0" : String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFecharevento())).substring(0, 1), fuente));
        III_table.addCell(new Paragraph(String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFecharevento())).length() < 2 ? String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFecharevento())).substring(0, 1) : String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFecharevento())).substring(1, 2), fuente));
        III_table.addCell(new Paragraph(String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFecharevento())).length() < 2 ? "0" : String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFecharevento())).substring(0, 1), fuente));
        III_table.addCell(new Paragraph(String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFecharevento())).length() < 2 ? String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFecharevento())).substring(0, 1) : String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFecharevento())).substring(1, 2), fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(13);
        III_table.addCell(new Paragraph(" ", fuente));

        III_table.getDefaultCell().setColspan(34);
        III_table.getDefaultCell().setBorder(0);
        III_table.addCell(paragraph);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph("Departamento", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 16; i++) {
            if (SihicApp.s_hclfurisp.getIII_genMunicipios() != null) {
                if (i < SihicApp.s_hclfurisp.getIII_genMunicipios().getGenDepartamentos().getNombre().length()) {
                    III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getIII_genMunicipios().getGenDepartamentos().getNombre().substring(i, (i + 1)), fuente));
                } else {

                    III_table.addCell(new Paragraph("  ", fuente));
                }
            } else {
                III_table.addCell(new Paragraph("  ", fuente));
            }
        }
        III_table.getDefaultCell().setColspan(2);
        III_table.getDefaultCell().setBorder(0);
        III_table.addCell(new Paragraph("Cod", fuente));
        III_table.getDefaultCell().setColspan(1);
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 15; i++) {
            if (SihicApp.s_hclfurisp.getIII_genMunicipios() != null) {
                if (i < SihicApp.s_hclfurisp.getIII_genMunicipios().getGenDepartamentos().getCodigo().length()) {
                    III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getIII_genMunicipios().getGenDepartamentos().getCodigo().substring(i, (i + 1)), fuente));
                } else {
                    III_table.getDefaultCell().setBorder(0);
                    III_table.addCell(new Paragraph("  ", fuente));
                }
            } else {
                if (i < 2) {
                    III_table.addCell(new Paragraph("  ", fuente));
                } else {
                    III_table.getDefaultCell().setBorder(0);
                    III_table.addCell(new Paragraph("  ", fuente));
                }
            }
        }

        III_table.getDefaultCell().setColspan(34);
        III_table.getDefaultCell().setBorder(0);
        III_table.addCell(paragraph);
        III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph("Municipio", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 21; i++) {
            if (SihicApp.s_hclfurisp.getIII_genMunicipios() != null) {
                if (i < SihicApp.s_hclfurisp.getIII_genMunicipios().getNombre().length()) {
                    III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getIII_genMunicipios().getNombre().substring(i, (i + 1)), fuente));
                } else {
                    III_table.addCell(new Paragraph("  ", fuente));
                }
            } else {
                III_table.addCell(new Paragraph("  ", fuente));
            }
        }
        III_table.getDefaultCell().setColspan(2);
        III_table.getDefaultCell().setBorder(0);
        III_table.addCell(new Paragraph("Cod", fuente));
        III_table.getDefaultCell().setColspan(1);
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        III_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 3; i++) {
            if (SihicApp.s_hclfurisp.getIII_genMunicipios() != null) {
                if (i < SihicApp.s_hclfurisp.getIII_genMunicipios().getCodigo().substring(2, 5).length()) {
                    III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getIII_genMunicipios().getCodigo().substring(i + 2, (i + 3)), fuente));
                } else {
                    III_table.getDefaultCell().setBorder(0);
                    III_table.addCell(new Paragraph("  ", fuente));
                }
            } else {
                III_table.addCell(new Paragraph("  ", fuente));
            }
        }
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(3);
        III_table.addCell(new Paragraph("Zona", fuente));
        III_table.getDefaultCell().setColspan(2);
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        if (SihicApp.s_hclfurisp.getGenZonaResidencia() != null) {
            III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getGenZonaResidencia().getCodigo().equals("UR") ? "U:X" : "U:", fuente));
        } else {
            III_table.addCell(new Paragraph("U:", fuente));

        }
        III_table.getDefaultCell().setBorder(0);
        //III_table.getDefaultCell().setColspan(1);
        III_table.addCell(new Paragraph(" ", fuente));
        III_table.getDefaultCell().setColspan(2);
        III_table.getDefaultCell().setBorderWidthBottom(0.1F);
        III_table.getDefaultCell().setBorderWidthLeft(0.1F);
        III_table.getDefaultCell().setBorderWidthTop(0.1F);
        III_table.getDefaultCell().setBorderWidthRight(0.1F);
        if (SihicApp.s_hclfurisp.getGenZonaResidencia() != null) {

            III_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getGenZonaResidencia().getCodigo().equals("RU") ? "R:X" : "R:", fuente));
        } else {
            III_table.addCell(new Paragraph("R:", fuente));
        }
        III_table.getDefaultCell().setColspan(34);
        III_table.getDefaultCell().setBorder(0);
        III_table.addCell(paragraph);
        III_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        III_table.addCell(new Paragraph("Descripción Breve Evento Catastrófico o Accidente de Transito", fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.getDefaultCell().setColspan(34);
        III_table.addCell(new Paragraph("Enuncie las principales características del evento/accidente", fuente));
        III_table.getDefaultCell().setBorderWidthBottom(0.5F);
        III_table.getDefaultCell().setBorderWidthTop(0.5F);
        III_table.getDefaultCell().setBorderWidthLeft(0.5F);
        III_table.getDefaultCell().setBorderWidthRight(0.5F);
        III_table.getDefaultCell().setColspan(34);
        III_table.addCell(new Paragraph(" " + SihicApp.s_hclfurisp.getDescripciobreve(), fuente));
        III_table.getDefaultCell().setBorder(0);
        III_table.addCell(paragraph);
        document.add(III_table);
        IV_table.getDefaultCell().setColspan(34);
        IV_table.setWidthPercentage(100f);
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        IV_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        IV_table.addCell(new Paragraph("IV. DATOS DEL VEHICULO DE ACCIDENTE DE TRANSITO", fuente));
        IV_table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        IV_table.addCell(paragraph);
        IV_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        IV_table.getDefaultCell().setColspan(5);
        IV_table.getDefaultCell().setBorder(0);
        IV_table.addCell(new Paragraph("Asegurado", fuente));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5F);
        IV_table.getDefaultCell().setBorderWidthTop(0.5F);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5F);
        IV_table.getDefaultCell().setBorderWidthRight(0.5F);
        IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getEstadoaseguramiento() == 0 ? "X" : "", fuente));
        IV_table.getDefaultCell().setColspan(6);
        IV_table.getDefaultCell().setBorder(0);
        IV_table.addCell(new Paragraph("No Asegurado", fuente));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5F);
        IV_table.getDefaultCell().setBorderWidthTop(0.5F);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5F);
        IV_table.getDefaultCell().setBorderWidthRight(0.5F);
        IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getEstadoaseguramiento() == 1 ? "X" : "", fuente));
        IV_table.getDefaultCell().setColspan(6);
        IV_table.getDefaultCell().setBorder(0);
        IV_table.addCell(new Paragraph("Vehiculo fantasma", fuente));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5F);
        IV_table.getDefaultCell().setBorderWidthTop(0.5F);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5F);
        IV_table.getDefaultCell().setBorderWidthRight(0.5F);
        IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getEstadoaseguramiento() == 2 ? "X" : "", fuente));
        IV_table.getDefaultCell().setColspan(6);
        IV_table.getDefaultCell().setBorder(0);
        IV_table.addCell(new Paragraph("Poliza Falsa", fuente));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5F);
        IV_table.getDefaultCell().setBorderWidthTop(0.5F);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5F);
        IV_table.getDefaultCell().setBorderWidthRight(0.5F);
        IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getEstadoaseguramiento() == 3 ? "X" : "", fuente));
        IV_table.getDefaultCell().setColspan(6);
        IV_table.getDefaultCell().setBorder(0);
        IV_table.addCell(new Paragraph("Vehiculo en fuga", fuente));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5F);
        IV_table.getDefaultCell().setBorderWidthTop(0.5F);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5F);
        IV_table.getDefaultCell().setBorderWidthRight(0.5F);
        IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getEstadoaseguramiento() == 4 ? "X" : "", fuente));
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(34);
        IV_table.addCell(paragraph);

        IV_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        IV_table.getDefaultCell().setColspan(1);
        IV_table.addCell(new Paragraph("Marca", fuente));
        IV_table.getDefaultCell().setColspan(15);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5F);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5F);
        IV_table.getDefaultCell().setBorderWidthRight(0.5F);
        IV_table.getDefaultCell().setBorderWidthTop(0.5F);
        IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getMarca(), fuente));
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(2);
        IV_table.addCell(" ");
        IV_table.getDefaultCell().setColspan(5);
        IV_table.addCell(new Paragraph("Placa"));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5F);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5F);
        IV_table.getDefaultCell().setBorderWidthRight(0.5F);
        IV_table.getDefaultCell().setBorderWidthTop(0.5F);
        if (SihicApp.s_hclfurisp.getPlaca() == null) {
            SihicApp.s_hclfurisp.setPlaca("");
        }
        for (int i = 0; i < 6; i++) {
            if (i < SihicApp.s_hclfurisp.getPlaca().length()) {
                IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getPlaca().substring(i, i + 1), fuente));
            } else {

                IV_table.addCell(" ");
            }
        }
        IV_table.getDefaultCell().setColspan(5);
        IV_table.getDefaultCell().setBorder(0);
        IV_table.addCell(" ");
        IV_table.getDefaultCell().setColspan(34);
        IV_table.addCell(paragraph);
        IV_table.getDefaultCell().setColspan(1);
        IV_table.addCell(new Paragraph("Tipo de Servicio:", fuente));
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(3);
        IV_table.addCell(new Paragraph("Particular", fuente));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5F);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5F);
        IV_table.getDefaultCell().setBorderWidthRight(0.5F);
        IV_table.getDefaultCell().setBorderWidthTop(0.5F);
        IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getTiposervicio() == 0 ? "X" : "", fuente));
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(3);
        IV_table.addCell(new Paragraph("Público", fuente));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5F);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5F);
        IV_table.getDefaultCell().setBorderWidthRight(0.5F);
        IV_table.getDefaultCell().setBorderWidthTop(0.5F);
        IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getTiposervicio() == 1 ? "X" : "", fuente));
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(2);
        IV_table.addCell(new Paragraph("Oficial", fuente));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5F);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5F);
        IV_table.getDefaultCell().setBorderWidthRight(0.5F);
        IV_table.getDefaultCell().setBorderWidthTop(0.5F);
        IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getTiposervicio() == 2 ? "X" : "", fuente));
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(7);
        IV_table.addCell(new Paragraph("Vehiculo de emergencia", fuente));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5F);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5F);
        IV_table.getDefaultCell().setBorderWidthRight(0.5F);
        IV_table.getDefaultCell().setBorderWidthTop(0.5F);
        IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getTiposervicio() == 3 ? "X" : "", fuente));
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(13);
        IV_table.addCell(new Paragraph("Vehiculo de servicio diplomático o consular", fuente));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5F);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5F);
        IV_table.getDefaultCell().setBorderWidthRight(0.5F);
        IV_table.getDefaultCell().setBorderWidthTop(0.5F);
        IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getTiposervicio() == 4 ? "X" : "", fuente));
        IV_table.getDefaultCell().setColspan(34);
        IV_table.getDefaultCell().setBorder(0);
        IV_table.addCell(paragraph);
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorder(0);
        IV_table.addCell(new Paragraph(" "));
        IV_table.getDefaultCell().setColspan(10);
        IV_table.getDefaultCell().setBorder(0);
        IV_table.addCell(new Paragraph("Vehiculo de transporte masivo", fuente));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5f);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5f);
        IV_table.getDefaultCell().setBorderWidthRight(0.5f);
        IV_table.getDefaultCell().setBorderWidthTop(0.5f);
        IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getTiposervicio() == 5 ? "X" : "", fuente));
        IV_table.getDefaultCell().setColspan(5);
        IV_table.getDefaultCell().setBorder(0);
        IV_table.addCell(new Paragraph("Vehiculo escolar", fuente));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5f);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5f);
        IV_table.getDefaultCell().setBorderWidthRight(0.5f);
        IV_table.getDefaultCell().setBorderWidthTop(0.5f);
        IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getTiposervicio() == 5 ? "X" : ""));
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(16);
        IV_table.addCell(" ");
        IV_table.getDefaultCell().setColspan(34);
        IV_table.addCell(paragraph);
        IV_table.getDefaultCell().setColspan(5);
        IV_table.addCell(new Paragraph("Código de la aseguradora", fuente));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5f);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5f);
        IV_table.getDefaultCell().setBorderWidthRight(0.5f);
        IV_table.getDefaultCell().setBorderWidthTop(0.5f);
        if (SihicApp.s_hclfurisp.getCodigoaseguradora() == null) {
            SihicApp.s_hclfurisp.setCodigoaseguradora("");
        }
        for (int i = 0; i < 6; i++) {
            if (i < SihicApp.s_hclfurisp.getCodigoaseguradora().length()) {
                IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getCodigoaseguradora().substring(i, i + 1), fuente));
            } else {
                IV_table.addCell(new Paragraph(" ", fuente));
            }
        }
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(23);
        IV_table.addCell(new Paragraph(" ", fuente));
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(34);
        IV_table.addCell(paragraph);
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(1);
        IV_table.addCell(new Paragraph("N° de la Póliza", fuente));
        IV_table.getDefaultCell().setBorderWidthBottom(0.5f);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5f);
        IV_table.getDefaultCell().setBorderWidthRight(0.5f);
        IV_table.getDefaultCell().setBorderWidthTop(0.5f);
        IV_table.getDefaultCell().setColspan(1);
        if (SihicApp.s_hclfurisp.getNopoliza() == null) {
            SihicApp.s_hclfurisp.setNopoliza("");
        }
        for (int i = 0; i < 20; i++) {
            if (i < SihicApp.s_hclfurisp.getNopoliza().length()) {
                IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getNopoliza().substring(i, i + 1), fuente));
            } else {
                IV_table.addCell(new Paragraph(" ", fuente));
            }
        }
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(8);
        IV_table.addCell(new Paragraph("Intervención de autoridad", fuente));
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(1);
        IV_table.addCell(new Paragraph("Si", fuente));
        IV_table.getDefaultCell().setBorderWidthBottom(0.5f);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5f);
        IV_table.getDefaultCell().setBorderWidthRight(0.5f);
        IV_table.getDefaultCell().setBorderWidthTop(0.5f);
        IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.isIntervencionautoridad() ? "X" : "", fuente));

        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(2);
        IV_table.addCell(new Paragraph("No", fuente));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5f);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5f);
        IV_table.getDefaultCell().setBorderWidthRight(0.5f);
        IV_table.getDefaultCell().setBorderWidthTop(0.5f);
        IV_table.addCell(new Paragraph(!SihicApp.s_hclfurisp.isIntervencionautoridad() ? "X" : "", fuente));
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(34);
        IV_table.addCell(paragraph);
        IV_table.getDefaultCell().setColspan(1);
        IV_table.addCell(new Paragraph("Vigencia", fuente));
        IV_table.getDefaultCell().setColspan(3);
        IV_table.addCell(new Paragraph("Desde", fuente));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5f);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5f);
        IV_table.getDefaultCell().setBorderWidthRight(0.5f);
        IV_table.getDefaultCell().setBorderWidthTop(0.5f);
        if (SihicApp.s_hclfurisp.getFecharvigenciadesde() == null) {
            SihicApp.s_hclfurisp.setFecharvigenciadesde(new Date());
        }
        if (SihicApp.s_hclfurisp.getFechavigenciahasta() == null) {
            SihicApp.s_hclfurisp.setFechavigenciahasta(new Date());
        }
        IV_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharvigenciadesde())).length() < 2 ? "0" : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharvigenciadesde())).substring(0, 1), fuente));
        IV_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharvigenciadesde())).length() < 2 ? String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharvigenciadesde())).substring(0, 1) : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharvigenciadesde())).substring(1, 2), fuente));
        IV_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharvigenciadesde())).length() < 2 ? "0" : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharvigenciadesde())).substring(0, 1), fuente));
        IV_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharvigenciadesde())).length() < 2 ? String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharvigenciadesde())).substring(0, 1) : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharvigenciadesde())).substring(1, 2), fuente));
        IV_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFecharvigenciadesde())).substring(0, 1), fuente));
        IV_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFecharvigenciadesde())).substring(1, 2), fuente));
        IV_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFecharvigenciadesde())).substring(2, 3), fuente));
        IV_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFecharvigenciadesde())).substring(3, 4), fuente));
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(2);
        IV_table.addCell(new Paragraph("Hasta", fuente));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5f);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5f);
        IV_table.getDefaultCell().setBorderWidthRight(0.5f);
        IV_table.getDefaultCell().setBorderWidthTop(0.5f);

        IV_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechavigenciahasta())).length() < 2 ? "0" : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechavigenciahasta())).substring(0, 1), fuente));
        IV_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechavigenciahasta())).length() < 2 ? String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechavigenciahasta())).substring(0, 1) : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechavigenciahasta())).substring(1, 2), fuente));
        IV_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechavigenciahasta())).length() < 2 ? "0" : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechavigenciahasta())).substring(0, 1), fuente));
        IV_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechavigenciahasta())).length() < 2 ? String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechavigenciahasta())).substring(0, 1) : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechavigenciahasta())).substring(1, 2), fuente));
        IV_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFechavigenciahasta())).substring(0, 1), fuente));
        IV_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFechavigenciahasta())).substring(1, 2), fuente));
        IV_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFechavigenciahasta())).substring(2, 3), fuente));
        IV_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFechavigenciahasta())).substring(3, 4), fuente));
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(7);
        IV_table.addCell(new Paragraph("Cobro Excedente Póliza", fuente));
        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(1);
        IV_table.addCell(new Paragraph("Si", fuente));
        IV_table.getDefaultCell().setBorderWidthBottom(0.5f);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5f);
        IV_table.getDefaultCell().setBorderWidthRight(0.5f);
        IV_table.getDefaultCell().setBorderWidthTop(0.5f);
        IV_table.addCell(new Paragraph(SihicApp.s_hclfurisp.isCobroexcedentepoliza() ? "X" : "", fuente));

        IV_table.getDefaultCell().setBorder(0);
        IV_table.getDefaultCell().setColspan(2);
        IV_table.addCell(new Paragraph("No", fuente));
        IV_table.getDefaultCell().setColspan(1);
        IV_table.getDefaultCell().setBorderWidthBottom(0.5f);
        IV_table.getDefaultCell().setBorderWidthLeft(0.5f);
        IV_table.getDefaultCell().setBorderWidthRight(0.5f);
        IV_table.getDefaultCell().setBorderWidthTop(0.5f);
        IV_table.addCell(new Paragraph(!SihicApp.s_hclfurisp.isCobroexcedentepoliza() ? "X" : "", fuente));
        IV_table.getDefaultCell().setBorder(0);

        IV_table.getDefaultCell().setColspan(34);
        IV_table.addCell(paragraph);

        document.add(IV_table);

        //****************************************************************************************v
        V_table.getDefaultCell().setBorderWidth(0);
        V_table.getDefaultCell().setColspan(34);
        V_table.setWidthPercentage(100f);

        V_table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        V_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        V_table.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
        V_table.addCell(new Paragraph("V. DATOS DEL PROPIETARIO DEL VEHICULO", fuente));
        if (SihicApp.s_hclfurisp.getPropietariovehiculo() == null) {
            SihicApp.s_hclfurisp.setPropietariovehiculo(new GenPersonas());
        }
        V_table.getDefaultCell().setBorder(0);
        V_table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        V_table.addCell(paragraph);
        V_table.getDefaultCell().setColspan(16);
        V_table.getDefaultCell().setBorderWidthBottom(0.1F);
        V_table.getDefaultCell().setBorderWidthLeft(0.1F);
        V_table.getDefaultCell().setBorderWidthTop(0.1F);
        V_table.getDefaultCell().setBorderWidthRight(0.1F);

        V_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        V_table.addCell(new Paragraph(" " + SihicApp.s_hclfurisp.getPropietariovehiculo().getPrimerApellido(), fuente));
        V_table.getDefaultCell().setBorder(0);
        V_table.getDefaultCell().setColspan(2);
        V_table.addCell(new Paragraph(" ", fuente));

        V_table.getDefaultCell().setColspan(16);
        V_table.getDefaultCell().setBorderWidthBottom(0.1F);
        V_table.getDefaultCell().setBorderWidthLeft(0.1F);
        V_table.getDefaultCell().setBorderWidthTop(0.1F);
        V_table.getDefaultCell().setBorderWidthRight(0.1F);
        V_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        V_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getPropietariovehiculo().getSegundoApellido(), fuente));
        V_table.getDefaultCell().setBorder(0);
        V_table.getDefaultCell().setColspan(16);
        V_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        V_table.addCell(new Paragraph("1er Apellido", fuente));
        V_table.getDefaultCell().setColspan(2);
        V_table.addCell(new Paragraph(" ", fuente));
        V_table.getDefaultCell().setColspan(16);
        V_table.addCell(new Paragraph("2do Apellido", fuente));
        V_table.getDefaultCell().setBorder(0);
        V_table.getDefaultCell().setColspan(34);
        V_table.addCell(paragraph);
        V_table.getDefaultCell().setColspan(16);
        V_table.getDefaultCell().setBorderWidthBottom(0.1F);
        V_table.getDefaultCell().setBorderWidthLeft(0.1F);
        V_table.getDefaultCell().setBorderWidthTop(0.1F);
        V_table.getDefaultCell().setBorderWidthRight(0.1F);

        V_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        V_table.addCell(new Paragraph(" " + SihicApp.s_hclfurisp.getPropietariovehiculo().getPrimerNombre(), fuente));
        V_table.getDefaultCell().setBorder(0);
        V_table.getDefaultCell().setColspan(2);
        V_table.addCell(new Paragraph("", fuente));

        V_table.getDefaultCell().setColspan(16);
        V_table.getDefaultCell().setBorderWidthBottom(0.1F);
        V_table.getDefaultCell().setBorderWidthLeft(0.1F);
        V_table.getDefaultCell().setBorderWidthTop(0.1F);
        V_table.getDefaultCell().setBorderWidthRight(0.1F);
        V_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        V_table.addCell(new Paragraph(" " + SihicApp.s_hclfurisp.getPropietariovehiculo().getSegundoNombre(), fuente));
        V_table.getDefaultCell().setBorder(0);
        V_table.getDefaultCell().setColspan(16);
        V_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        V_table.addCell(new Paragraph("1er Nombre", fuente));
        V_table.getDefaultCell().setColspan(2);
        V_table.addCell(new Paragraph(" ", fuente));
        V_table.getDefaultCell().setColspan(16);
        V_table.addCell(new Paragraph("2do Nombre", fuente));
        V_table.getDefaultCell().setBorder(0);
        V_table.getDefaultCell().setColspan(34);
        V_table.addCell(paragraph);
        V_table.getDefaultCell().setColspan(1);
        V_table.addCell(new Paragraph("Tipo de Documento", fuente));
        V_table.getDefaultCell().setColspan(2);
        V_table.getDefaultCell().setBorderWidthBottom(0.1F);
        V_table.getDefaultCell().setBorderWidthLeft(0.1F);
        V_table.getDefaultCell().setBorderWidthTop(0.1F);
        V_table.getDefaultCell().setBorderWidthRight(0.1F);
        if (SihicApp.s_hclfurisp.getPropietariovehiculo().getGenTiposDocumentos() == null) {
            SihicApp.s_hclfurisp.getPropietariovehiculo().setGenTiposDocumentos(new GenTiposDocumentos());
            SihicApp.s_hclfurisp.getPropietariovehiculo().getGenTiposDocumentos().setSigla("");
        }
        for (int i = 0; i < 9; i++) {
            switch (i) {
                case 0:

                    V_table.addCell(new Paragraph("CC:" + (SihicApp.s_hclfurisp.getPropietariovehiculo().getGenTiposDocumentos().getSigla().equals("CC") ? "X" : ""), fuente));
                    break;
                case 1:
                    V_table.addCell(new Paragraph("CE:" + (SihicApp.s_hclfurisp.getPropietariovehiculo().getGenTiposDocumentos().getSigla().equals("CE") ? "X" : ""), fuente));
                    break;
                case 2:

                    V_table.addCell(new Paragraph("PA:" + (SihicApp.s_hclfurisp.getPropietariovehiculo().getGenTiposDocumentos().getSigla().equals("PA") ? "X" : ""), fuente));
                    break;
                case 3:

                    V_table.addCell(new Paragraph("TI:" + (SihicApp.s_hclfurisp.getPropietariovehiculo().getGenTiposDocumentos().getSigla().equals("TI") ? "X" : ""), fuente));
                    break;
                case 4:

                    V_table.addCell(new Paragraph("RC:" + (SihicApp.s_hclfurisp.getPropietariovehiculo().getGenTiposDocumentos().getSigla().equals("RC") ? "X" : ""), fuente));
                    break;
                case 5:

                    V_table.addCell(new Paragraph("AS:" + (SihicApp.s_hclfurisp.getPropietariovehiculo().getGenTiposDocumentos().getSigla().equals("AS") ? "X" : ""), fuente));
                    break;
                case 6:

                    V_table.addCell(new Paragraph("MS:" + (SihicApp.s_hclfurisp.getPropietariovehiculo().getGenTiposDocumentos().getSigla().equals("MS") ? "X" : ""), fuente));
                    break;
                case 7:

                    V_table.addCell(new Paragraph("CD:" + (SihicApp.s_hclfurisp.getPropietariovehiculo().getGenTiposDocumentos().getSigla().equals("CD") ? "X" : ""), fuente));
                    break;
                case 8:

                    V_table.addCell(new Paragraph("TR:" + (SihicApp.s_hclfurisp.getPropietariovehiculo().getGenTiposDocumentos().getSigla().equals("TR") ? "X" : ""), fuente));
                    break;

            }
        }

        V_table.getDefaultCell().setColspan(3);
        V_table.getDefaultCell().setBorder(0);
        V_table.addCell(new Paragraph("Documento", fuente));
        V_table.getDefaultCell().setColspan(1);
        V_table.getDefaultCell().setBorderWidthBottom(0.1F);
        V_table.getDefaultCell().setBorderWidthLeft(0.1F);
        V_table.getDefaultCell().setBorderWidthTop(0.1F);
        V_table.getDefaultCell().setBorderWidthRight(0.1F);
        if (SihicApp.s_hclfurisp.getPropietariovehiculo().getDocumento() == null) {
            SihicApp.s_hclfurisp.getPropietariovehiculo().setDocumento("");
        }
        for (int i = 0; i < 12; i++) {
            if (i < SihicApp.s_hclfurisp.getPropietariovehiculo().getDocumento().length()) {
                V_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getPropietariovehiculo().getDocumento().substring(i, i + 1), fuente));
            } else {
                V_table.addCell(new Paragraph(" ", fuente));

            }
        }
        V_table.getDefaultCell().setColspan(34);
        V_table.getDefaultCell().setBorder(0);
        V_table.addCell(paragraph);
        V_table.getDefaultCell().setColspan(1);
        V_table.addCell(new Paragraph("Dirección", fuente));
        V_table.getDefaultCell().setBorderWidthBottom(0.1F);
        V_table.getDefaultCell().setBorderWidthLeft(0.1F);
        V_table.getDefaultCell().setBorderWidthTop(0.1F);
        V_table.getDefaultCell().setBorderWidthRight(0.1F);
        V_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        if (SihicApp.s_hclfurisp.getPropietariovehiculo().getDireccion() == null) {
            SihicApp.s_hclfurisp.getPropietariovehiculo().setDireccion("");
        }
        for (int i = 0; i < 33; i++) {
            if (i < SihicApp.s_hclfurisp.getPropietariovehiculo().getDireccion().length()) {
                V_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getPropietariovehiculo().getDireccion().substring(i, (i + 1)), fuente));
            } else {
                V_table.addCell(new Paragraph("  ", fuente));
            }
        }
        V_table.getDefaultCell().setColspan(34);
        V_table.getDefaultCell().setBorder(0);
        V_table.addCell(paragraph);
        V_table.getDefaultCell().setColspan(1);
        V_table.addCell(new Paragraph("Departamento", fuente));
        V_table.getDefaultCell().setBorderWidthBottom(0.1F);
        V_table.getDefaultCell().setBorderWidthLeft(0.1F);
        V_table.getDefaultCell().setBorderWidthTop(0.1F);
        V_table.getDefaultCell().setBorderWidthRight(0.1F);
        V_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        if (SihicApp.s_hclfurisp.getV_genMunicipios() == null) {
            SihicApp.s_hclfurisp.setV_genMunicipios(new GenMunicipios());
            SihicApp.s_hclfurisp.getV_genMunicipios().setNombre("");
            SihicApp.s_hclfurisp.getV_genMunicipios().setCodigo("12345");
            SihicApp.s_hclfurisp.getV_genMunicipios().setGenDepartamentos(new GenDepartamentos());
            SihicApp.s_hclfurisp.getV_genMunicipios().getGenDepartamentos().setNombre("");
            SihicApp.s_hclfurisp.getV_genMunicipios().getGenDepartamentos().setCodigo("");

        }
        for (int i = 0; i < 16; i++) {
            if (i < SihicApp.s_hclfurisp.getV_genMunicipios().getGenDepartamentos().getNombre().length()) {
                V_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getV_genMunicipios().getGenDepartamentos().getNombre().substring(i, (i + 1)), fuente));
            } else {

                V_table.addCell(new Paragraph("  ", fuente));
            }
        }
        V_table.getDefaultCell().setColspan(2);
        V_table.getDefaultCell().setBorder(0);
        V_table.addCell(new Paragraph("Cod", fuente));
        V_table.getDefaultCell().setColspan(1);
        V_table.getDefaultCell().setBorderWidthBottom(0.1F);
        V_table.getDefaultCell().setBorderWidthLeft(0.1F);
        V_table.getDefaultCell().setBorderWidthTop(0.1F);
        V_table.getDefaultCell().setBorderWidthRight(0.1F);
        V_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 2; i++) {
            if (i < SihicApp.s_hclfurisp.getV_genMunicipios().getGenDepartamentos().getCodigo().length()) {
                V_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getV_genMunicipios().getGenDepartamentos().getCodigo().substring(i, (i + 1)), fuente));
            } else {

                V_table.addCell(new Paragraph("  ", fuente));
            }
        }
        V_table.getDefaultCell().setColspan(3);
        V_table.getDefaultCell().setBorder(0);
        V_table.addCell(new Paragraph("Teléfono", fuente));
        V_table.getDefaultCell().setColspan(1);
        V_table.getDefaultCell().setBorderWidthBottom(0.1F);
        V_table.getDefaultCell().setBorderWidthLeft(0.1F);
        V_table.getDefaultCell().setBorderWidthTop(0.1F);
        V_table.getDefaultCell().setBorderWidthRight(0.1F);
        V_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        if (SihicApp.s_hclfurisp.getPropietariovehiculo().getTelefono() == null) {
            SihicApp.s_hclfurisp.getPropietariovehiculo().setTelefono("");
        }
        for (int i = 0; i < 10; i++) {
            if (i < SihicApp.s_hclfurisp.getPropietariovehiculo().getTelefono().length()) {
                V_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getPropietariovehiculo().getTelefono().substring(i, (i + 1)), fuente));
            } else {
                V_table.addCell(new Paragraph("  ", fuente));
            }
        }
        V_table.getDefaultCell().setColspan(34);
        V_table.getDefaultCell().setBorder(0);
        V_table.addCell(paragraph);
        V_table.getDefaultCell().setColspan(1);
        V_table.addCell(new Paragraph("Municipio", fuente));
        V_table.getDefaultCell().setBorderWidthBottom(0.1F);
        V_table.getDefaultCell().setBorderWidthLeft(0.1F);
        V_table.getDefaultCell().setBorderWidthTop(0.1F);
        V_table.getDefaultCell().setBorderWidthRight(0.1F);
        V_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 21; i++) {
            if (i < SihicApp.s_hclfurisp.getV_genMunicipios().getNombre().length()) {
                V_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getV_genMunicipios().getNombre().substring(i, (i + 1)), fuente));
            } else {
                V_table.addCell(new Paragraph("  ", fuente));
            }
        }
        V_table.getDefaultCell().setColspan(2);
        V_table.getDefaultCell().setBorder(0);
        V_table.addCell(new Paragraph("Cod", fuente));
        V_table.getDefaultCell().setColspan(1);
        V_table.getDefaultCell().setBorderWidthBottom(0.1F);
        V_table.getDefaultCell().setBorderWidthLeft(0.1F);
        V_table.getDefaultCell().setBorderWidthTop(0.1F);
        V_table.getDefaultCell().setBorderWidthRight(0.1F);
        V_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 10; i++) {
            if (i < SihicApp.s_hclfurisp.getV_genMunicipios().getCodigo().substring(2, 5).length()) {
                V_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getV_genMunicipios().getCodigo().substring(i + 2, (i + 3)), fuente));
            } else {
                V_table.getDefaultCell().setBorder(0);
                V_table.addCell(new Paragraph("  ", fuente));
            }
        }
        V_table.getDefaultCell().setColspan(34);
        V_table.getDefaultCell().setBorder(0);
        V_table.addCell(paragraph);
        document.add(V_table);
        //****************************************************************************************VI
        VI_table.getDefaultCell().setBorderWidth(0);
        VI_table.getDefaultCell().setColspan(34);
        VI_table.setWidthPercentage(100f);
        VI_table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        VI_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        VI_table.getDefaultCell().setVerticalAlignment(Element.ALIGN_CENTER);
        VI_table.addCell(new Paragraph("VI. DATOS DEL CONDUCTOR DEL VEHÍCULO INVOLUCRADO EN EL ACCIDENTE DE TRANSITO", fuente));
        if (SihicApp.s_hclfurisp.getConsuctorvehiculo() == null) {
            SihicApp.s_hclfurisp.setConsuctorvehiculo(new GenPersonas());
        }
        VI_table.getDefaultCell().setBorder(0);
        VI_table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        VI_table.addCell(paragraph);
        VI_table.getDefaultCell().setColspan(16);
        VI_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VI_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VI_table.getDefaultCell().setBorderWidthTop(0.1F);
        VI_table.getDefaultCell().setBorderWidthRight(0.1F);

        VI_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        VI_table.addCell(new Paragraph(" " + SihicApp.s_hclfurisp.getConsuctorvehiculo().getPrimerApellido(), fuente));
        VI_table.getDefaultCell().setBorder(0);
        VI_table.getDefaultCell().setColspan(2);
        VI_table.addCell(new Paragraph(" ", fuente));

        VI_table.getDefaultCell().setColspan(16);
        VI_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VI_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VI_table.getDefaultCell().setBorderWidthTop(0.1F);
        VI_table.getDefaultCell().setBorderWidthRight(0.1F);
        VI_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        VI_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getConsuctorvehiculo().getSegundoApellido(), fuente));
        VI_table.getDefaultCell().setBorder(0);
        VI_table.getDefaultCell().setColspan(16);
        VI_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        VI_table.addCell(new Paragraph("1er Apellido", fuente));
        VI_table.getDefaultCell().setColspan(2);
        VI_table.addCell(new Paragraph(" ", fuente));
        VI_table.getDefaultCell().setColspan(16);
        VI_table.addCell(new Paragraph("2do Apellido", fuente));
        VI_table.getDefaultCell().setBorder(0);
        VI_table.getDefaultCell().setColspan(34);
        VI_table.addCell(paragraph);
        VI_table.getDefaultCell().setColspan(16);
        VI_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VI_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VI_table.getDefaultCell().setBorderWidthTop(0.1F);
        VI_table.getDefaultCell().setBorderWidthRight(0.1F);

        VI_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        VI_table.addCell(new Paragraph(" " + SihicApp.s_hclfurisp.getConsuctorvehiculo().getPrimerNombre(), fuente));
        VI_table.getDefaultCell().setBorder(0);
        VI_table.getDefaultCell().setColspan(2);
        VI_table.addCell(new Paragraph("", fuente));

        VI_table.getDefaultCell().setColspan(16);
        VI_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VI_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VI_table.getDefaultCell().setBorderWidthTop(0.1F);
        VI_table.getDefaultCell().setBorderWidthRight(0.1F);
        VI_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        VI_table.addCell(new Paragraph(" " + SihicApp.s_hclfurisp.getConsuctorvehiculo().getSegundoNombre(), fuente));
        VI_table.getDefaultCell().setBorder(0);
        VI_table.getDefaultCell().setColspan(16);
        VI_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        VI_table.addCell(new Paragraph("1er Nombre", fuente));
        VI_table.getDefaultCell().setColspan(2);
        VI_table.addCell(new Paragraph(" ", fuente));
        VI_table.getDefaultCell().setColspan(16);
        VI_table.addCell(new Paragraph("2do Nombre", fuente));
        VI_table.getDefaultCell().setBorder(0);
        VI_table.getDefaultCell().setColspan(34);
        VI_table.addCell(paragraph);
        VI_table.getDefaultCell().setColspan(1);
        VI_table.addCell(new Paragraph("Tipo de Documento", fuente));
        VI_table.getDefaultCell().setColspan(2);
        VI_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VI_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VI_table.getDefaultCell().setBorderWidthTop(0.1F);
        VI_table.getDefaultCell().setBorderWidthRight(0.1F);
        if (SihicApp.s_hclfurisp.getConsuctorvehiculo().getGenTiposDocumentos() == null) {
            SihicApp.s_hclfurisp.getConsuctorvehiculo().setGenTiposDocumentos(new GenTiposDocumentos());
            SihicApp.s_hclfurisp.getConsuctorvehiculo().getGenTiposDocumentos().setSigla("");

        }

        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0:

                    VI_table.addCell(new Paragraph("CC:" + (SihicApp.s_hclfurisp.getConsuctorvehiculo().getGenTiposDocumentos().getSigla().equals("CC") ? "X" : ""), fuente));
                    break;
                case 1:
                    VI_table.addCell(new Paragraph("CE:" + (SihicApp.s_hclfurisp.getConsuctorvehiculo().getGenTiposDocumentos().getSigla().equals("CE") ? "X" : ""), fuente));
                    break;
                case 2:

                    VI_table.addCell(new Paragraph("PA:" + (SihicApp.s_hclfurisp.getConsuctorvehiculo().getGenTiposDocumentos().getSigla().equals("PA") ? "X" : ""), fuente));
                    break;
                case 3:

                    VI_table.addCell(new Paragraph("TI:" + (SihicApp.s_hclfurisp.getConsuctorvehiculo().getGenTiposDocumentos().getSigla().equals("TI") ? "X" : ""), fuente));
                    break;
                case 4:

                    VI_table.addCell(new Paragraph("RC:" + (SihicApp.s_hclfurisp.getConsuctorvehiculo().getGenTiposDocumentos().getSigla().equals("RC") ? "X" : ""), fuente));
                    break;
                case 5:

                    VI_table.addCell(new Paragraph("AS:" + (SihicApp.s_hclfurisp.getConsuctorvehiculo().getGenTiposDocumentos().getSigla().equals("AS") ? "X" : ""), fuente));
                    break;
                case 6:

                    VI_table.addCell(new Paragraph("MS:" + (SihicApp.s_hclfurisp.getConsuctorvehiculo().getGenTiposDocumentos().getSigla().equals("MS") ? "X" : ""), fuente));
                    break;
                case 7:

                    VI_table.addCell(new Paragraph("CD:" + (SihicApp.s_hclfurisp.getConsuctorvehiculo().getGenTiposDocumentos().getSigla().equals("CD") ? "X" : ""), fuente));
                    break;
            }
        }

        VI_table.getDefaultCell().setColspan(5);
        VI_table.getDefaultCell().setBorder(0);
        VI_table.addCell(new Paragraph("Documento", fuente));
        VI_table.getDefaultCell().setColspan(1);
        VI_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VI_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VI_table.getDefaultCell().setBorderWidthTop(0.1F);
        VI_table.getDefaultCell().setBorderWidthRight(0.1F);
        if (SihicApp.s_hclfurisp.getConsuctorvehiculo().getDocumento() == null) {
            SihicApp.s_hclfurisp.getConsuctorvehiculo().setDocumento("");
        }
        for (int i = 0; i < 12; i++) {
            if (i < SihicApp.s_hclfurisp.getConsuctorvehiculo().getDocumento().length()) {
                VI_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getConsuctorvehiculo().getDocumento().substring(i, i + 1), fuente));
            } else {
                VI_table.addCell(new Paragraph(" ", fuente));

            }
        }
        VI_table.getDefaultCell().setColspan(34);
        VI_table.getDefaultCell().setBorder(0);
        VI_table.addCell(paragraph);
        VI_table.getDefaultCell().setColspan(1);
        VI_table.addCell(new Paragraph("Dirección", fuente));
        VI_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VI_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VI_table.getDefaultCell().setBorderWidthTop(0.1F);
        VI_table.getDefaultCell().setBorderWidthRight(0.1F);
        VI_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        if (SihicApp.s_hclfurisp.getConsuctorvehiculo().getDireccion() == null) {
            SihicApp.s_hclfurisp.getConsuctorvehiculo().setDireccion("");
        }
        for (int i = 0; i < 33; i++) {
            if (i < SihicApp.s_hclfurisp.getConsuctorvehiculo().getDireccion().length()) {
                VI_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getConsuctorvehiculo().getDireccion().substring(i, (i + 1)), fuente));
            } else {
                VI_table.addCell(new Paragraph("  ", fuente));
            }
        }
        VI_table.getDefaultCell().setColspan(34);
        VI_table.getDefaultCell().setBorder(0);
        VI_table.addCell(paragraph);
        VI_table.getDefaultCell().setColspan(1);
        VI_table.addCell(new Paragraph("Departamento", fuente));
        VI_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VI_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VI_table.getDefaultCell().setBorderWidthTop(0.1F);
        VI_table.getDefaultCell().setBorderWidthRight(0.1F);
        VI_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        if (SihicApp.s_hclfurisp.getVI_genMunicipios() == null) {
            SihicApp.s_hclfurisp.setVI_genMunicipios(new GenMunicipios());
            SihicApp.s_hclfurisp.getVI_genMunicipios().setNombre("");
            SihicApp.s_hclfurisp.getVI_genMunicipios().setCodigo("12345");
            SihicApp.s_hclfurisp.getVI_genMunicipios().setGenDepartamentos(new GenDepartamentos());
            SihicApp.s_hclfurisp.getVI_genMunicipios().getGenDepartamentos().setNombre("");
            SihicApp.s_hclfurisp.getVI_genMunicipios().getGenDepartamentos().setCodigo("");

        }
        for (int i = 0; i < 16; i++) {
            if (i < SihicApp.s_hclfurisp.getVI_genMunicipios().getGenDepartamentos().getNombre().length()) {
                VI_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_genMunicipios().getGenDepartamentos().getNombre().substring(i, (i + 1)), fuente));
            } else {

                VI_table.addCell(new Paragraph("  ", fuente));
            }
        }
        VI_table.getDefaultCell().setColspan(2);
        VI_table.getDefaultCell().setBorder(0);
        VI_table.addCell(new Paragraph("Cod", fuente));
        VI_table.getDefaultCell().setColspan(1);
        VI_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VI_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VI_table.getDefaultCell().setBorderWidthTop(0.1F);
        VI_table.getDefaultCell().setBorderWidthRight(0.1F);
        VI_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 2; i++) {
            if (i < SihicApp.s_hclfurisp.getVI_genMunicipios().getGenDepartamentos().getCodigo().length()) {
                VI_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_genMunicipios().getGenDepartamentos().getCodigo().substring(i, (i + 1)), fuente));
            } else {

                VI_table.addCell(new Paragraph("  ", fuente));
            }
        }
        VI_table.getDefaultCell().setColspan(3);
        VI_table.getDefaultCell().setBorder(0);
        VI_table.addCell(new Paragraph("Teléfono", fuente));
        VI_table.getDefaultCell().setColspan(1);
        VI_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VI_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VI_table.getDefaultCell().setBorderWidthTop(0.1F);
        VI_table.getDefaultCell().setBorderWidthRight(0.1F);
        VI_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        if (SihicApp.s_hclfurisp.getConsuctorvehiculo().getTelefono() == null) {
            SihicApp.s_hclfurisp.getConsuctorvehiculo().setTelefono("");
        }
        for (int i = 0; i < 10; i++) {
            if (i < SihicApp.s_hclfurisp.getConsuctorvehiculo().getTelefono().length()) {
                VI_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getConsuctorvehiculo().getTelefono().substring(i, (i + 1)), fuente));
            } else {
                VI_table.addCell(new Paragraph("  ", fuente));
            }
        }
        VI_table.getDefaultCell().setColspan(34);
        VI_table.getDefaultCell().setBorder(0);
        VI_table.addCell(paragraph);
        VI_table.getDefaultCell().setColspan(1);
        VI_table.addCell(new Paragraph("Municipio", fuente));
        VI_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VI_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VI_table.getDefaultCell().setBorderWidthTop(0.1F);
        VI_table.getDefaultCell().setBorderWidthRight(0.1F);
        VI_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 21; i++) {
            if (i < SihicApp.s_hclfurisp.getVI_genMunicipios().getNombre().length()) {
                VI_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_genMunicipios().getNombre().substring(i, (i + 1)), fuente));
            } else {
                VI_table.addCell(new Paragraph("  ", fuente));
            }
        }
        VI_table.getDefaultCell().setColspan(2);
        VI_table.getDefaultCell().setBorder(0);
        VI_table.addCell(new Paragraph("Cod", fuente));
        VI_table.getDefaultCell().setColspan(1);
        VI_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VI_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VI_table.getDefaultCell().setBorderWidthTop(0.1F);
        VI_table.getDefaultCell().setBorderWidthRight(0.1F);
        VI_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        for (int i = 0; i < 10; i++) {
            if (i < SihicApp.s_hclfurisp.getVI_genMunicipios().getCodigo().substring(2, 5).length()) {
                VI_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_genMunicipios().getCodigo().substring(i + 2, (i + 3)), fuente));
            } else {
                VI_table.getDefaultCell().setBorder(0);
                VI_table.addCell(new Paragraph("  ", fuente));
            }
        }
        VI_table.getDefaultCell().setColspan(34);
        VI_table.getDefaultCell().setBorder(0);
        VI_table.addCell(paragraph);

        document.add(VI_table);

        //****************************************************************DATOS VEHICULOS INVOLUCRADOS***************************************
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(34);
        VI_vehiculosinvolucrados_table.setWidthPercentage(100f);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        VI_vehiculosinvolucrados_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("DATOS DE VEHICULOS INVOLUCRADOS", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        VI_vehiculosinvolucrados_table.addCell(paragraph);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("Datos del Segundo Vehiculo:", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(5);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("Asegurado", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(1);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthTop(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthRight(0.5F);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_estadoaseguramiento_vehiculosinvolucrados1() == 0 ? "X" : "", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(6);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("No Asegurado", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(1);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthTop(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthRight(0.5F);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_estadoaseguramiento_vehiculosinvolucrados1() == 1 ? "X" : "", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(6);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("Vehiculo fantasma", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(1);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthTop(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthRight(0.5F);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_estadoaseguramiento_vehiculosinvolucrados1() == 2 ? "X" : "", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(6);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("Poliza Falsa", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(1);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthTop(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthRight(0.5F);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_estadoaseguramiento_vehiculosinvolucrados1() == 3 ? "X" : "", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(6);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("Vehiculo en fuga", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(1);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthTop(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthRight(0.5F);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_estadoaseguramiento_vehiculosinvolucrados1() == 4 ? "X" : "", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(34);
        VI_vehiculosinvolucrados_table.addCell(paragraph);
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(1);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("Placa", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(1);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthRight(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthTop(0.5F);
        if (SihicApp.s_hclfurisp.getVI_placa_vehiculosinvolucrados1() == null) {
            SihicApp.s_hclfurisp.setVI_placa_vehiculosinvolucrados1("");
        }
        for (int i = 0; i < 6; i++) {
            if (i < SihicApp.s_hclfurisp.getVI_placa_vehiculosinvolucrados1().length()) {
                VI_vehiculosinvolucrados_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_placa_vehiculosinvolucrados1().substring(i, i + 1)));
            } else {

                VI_vehiculosinvolucrados_table.addCell(" ");
            }
        }
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(27);
        VI_vehiculosinvolucrados_table.addCell(paragraph);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(34);
        VI_vehiculosinvolucrados_table.addCell(paragraph);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(2);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("Tipo de Documento", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(2);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthTop(0.1F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthRight(0.1F);
        if (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados1() == null) {
            SihicApp.s_hclfurisp.setVI_tipodocumentos_vehiculosinvolucrados1(new GenTiposDocumentos());
            SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados1().setSigla("");

        }

        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0:

                    VI_vehiculosinvolucrados_table.addCell(new Paragraph("CC:" + (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados1().getSigla().equals("CC") ? "X" : ""), fuente));
                    break;
                case 1:
                    VI_vehiculosinvolucrados_table.addCell(new Paragraph("CE:" + (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados1().getSigla().equals("CE") ? "X" : ""), fuente));
                    break;
                case 2:

                    VI_vehiculosinvolucrados_table.addCell(new Paragraph("PA:" + (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados1().getSigla().equals("PA") ? "X" : ""), fuente));
                    break;
                case 3:

                    VI_vehiculosinvolucrados_table.addCell(new Paragraph("TI:" + (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados1().getSigla().equals("TI") ? "X" : ""), fuente));
                    break;
                case 4:

                    VI_vehiculosinvolucrados_table.addCell(new Paragraph("RC:" + (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados1().getSigla().equals("RC") ? "X" : ""), fuente));
                    break;
                case 5:

                    VI_vehiculosinvolucrados_table.addCell(new Paragraph("AS:" + (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados1().getSigla().equals("AS") ? "X" : ""), fuente));
                    break;
                case 6:

                    VI_vehiculosinvolucrados_table.addCell(new Paragraph("MS:" + (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados1().getSigla().equals("MS") ? "X" : ""), fuente));
                    break;
                case 7:

                    VI_vehiculosinvolucrados_table.addCell(new Paragraph("CD:" + (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados1().getSigla().equals("CD") ? "X" : ""), fuente));
                    break;
            }
        }

        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(4);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("Documento", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(1);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthTop(0.1F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthRight(0.1F);
        if (SihicApp.s_hclfurisp.getVI_documento_vehiculosinvolucrados1() == null) {
            SihicApp.s_hclfurisp.setVI_documento_vehiculosinvolucrados1("");
        }
        for (int i = 0; i < 12; i++) {
            if (i < SihicApp.s_hclfurisp.getVI_documento_vehiculosinvolucrados1().length()) {
                VI_vehiculosinvolucrados_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_documento_vehiculosinvolucrados1().substring(i, i + 1), fuente));
            } else {
                VI_vehiculosinvolucrados_table.addCell(new Paragraph(" ", fuente));

            }
        }
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(34);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.addCell(paragraph);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("Datos del Tercer Vehiculo:", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(5);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("Asegurado", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(1);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthTop(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthRight(0.5F);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_estadoaseguramiento_vehiculosinvolucrados2() == 0 ? "X" : "", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(6);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("No Asegurado", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(1);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthTop(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthRight(0.5F);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_estadoaseguramiento_vehiculosinvolucrados2() == 1 ? "X" : "", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(6);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("Vehiculo fantasma", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(1);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthTop(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthRight(0.5F);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_estadoaseguramiento_vehiculosinvolucrados2() == 2 ? "X" : "", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(6);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("Poliza Falsa", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(1);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthTop(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthRight(0.5F);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_estadoaseguramiento_vehiculosinvolucrados2() == 3 ? "X" : "", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(6);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("Vehiculo en fuga", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(1);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthTop(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthRight(0.5F);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_estadoaseguramiento_vehiculosinvolucrados2() == 4 ? "X" : "", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(34);
        VI_vehiculosinvolucrados_table.addCell(paragraph);
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(1);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("Placa", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(1);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthRight(0.5F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthTop(0.5F);
        if (SihicApp.s_hclfurisp.getVI_placa_vehiculosinvolucrados2() == null) {
            SihicApp.s_hclfurisp.setVI_placa_vehiculosinvolucrados2("");
        }
        for (int i = 0; i < 6; i++) {
            if (i < SihicApp.s_hclfurisp.getVI_placa_vehiculosinvolucrados2().length()) {
                VI_vehiculosinvolucrados_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_placa_vehiculosinvolucrados2().substring(i, i + 1)));
            } else {

                VI_vehiculosinvolucrados_table.addCell(" ");
            }
        }
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(27);
        VI_vehiculosinvolucrados_table.addCell(paragraph);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(34);
        VI_vehiculosinvolucrados_table.addCell(paragraph);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(2);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("Tipo de Documento", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(2);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthTop(0.1F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthRight(0.1F);
        if (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados2() == null) {
            SihicApp.s_hclfurisp.setVI_tipodocumentos_vehiculosinvolucrados2(new GenTiposDocumentos());
            SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados2().setSigla("");

        }

        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0:

                    VI_vehiculosinvolucrados_table.addCell(new Paragraph("CC:" + (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados2().getSigla().equals("CC") ? "X" : ""), fuente));
                    break;
                case 1:
                    VI_vehiculosinvolucrados_table.addCell(new Paragraph("CE:" + (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados2().getSigla().equals("CE") ? "X" : ""), fuente));
                    break;
                case 2:

                    VI_vehiculosinvolucrados_table.addCell(new Paragraph("PA:" + (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados2().getSigla().equals("PA") ? "X" : ""), fuente));
                    break;
                case 3:

                    VI_vehiculosinvolucrados_table.addCell(new Paragraph("TI:" + (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados2().getSigla().equals("TI") ? "X" : ""), fuente));
                    break;
                case 4:

                    VI_vehiculosinvolucrados_table.addCell(new Paragraph("RC:" + (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados2().getSigla().equals("RC") ? "X" : ""), fuente));
                    break;
                case 5:

                    VI_vehiculosinvolucrados_table.addCell(new Paragraph("AS:" + (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados2().getSigla().equals("AS") ? "X" : ""), fuente));
                    break;
                case 6:

                    VI_vehiculosinvolucrados_table.addCell(new Paragraph("MS:" + (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados2().getSigla().equals("MS") ? "X" : ""), fuente));
                    break;
                case 7:

                    VI_vehiculosinvolucrados_table.addCell(new Paragraph("CD:" + (SihicApp.s_hclfurisp.getVI_tipodocumentos_vehiculosinvolucrados2().getSigla().equals("CD") ? "X" : ""), fuente));
                    break;
            }
        }

        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(4);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.addCell(new Paragraph("Documento", fuente));
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(1);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthTop(0.1F);
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorderWidthRight(0.1F);
        if (SihicApp.s_hclfurisp.getVI_documento_vehiculosinvolucrados2() == null) {
            SihicApp.s_hclfurisp.setVI_documento_vehiculosinvolucrados2("");
        }
        for (int i = 0; i < 12; i++) {
            if (i < SihicApp.s_hclfurisp.getVI_documento_vehiculosinvolucrados2().length()) {
                VI_vehiculosinvolucrados_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVI_documento_vehiculosinvolucrados2().substring(i, i + 1), fuente));
            } else {
                VI_vehiculosinvolucrados_table.addCell(new Paragraph(" ", fuente));

            }
        }
        VI_vehiculosinvolucrados_table.getDefaultCell().setBorder(0);
        VI_vehiculosinvolucrados_table.getDefaultCell().setColspan(34);
        VI_vehiculosinvolucrados_table.addCell(paragraph);
        document.add(VI_vehiculosinvolucrados_table);
        //***********************************************************************************************VII
        VII_table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        VII_table.setWidthPercentage(100F);
        VII_table.getDefaultCell().setBorder(0);
        VII_table.getDefaultCell().setColspan(34);
        VII_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        VII_table.addCell(new Paragraph("VII. DATOS DE REMISIÓN", fuente));
        VII_table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        VII_table.addCell(paragraph);
        VII_table.getDefaultCell().setColspan(1);
        VII_table.addCell(new Paragraph("Tipo Referencia:", fuente));
        VII_table.getDefaultCell().setColspan(3);
        VII_table.addCell(new Paragraph("Remisión", fuente));
        VII_table.getDefaultCell().setColspan(1);
        VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VII_table.getDefaultCell().setBorderWidthRight(0.5F);
        VII_table.getDefaultCell().setBorderWidthTop(0.5F);
        VII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getTiporeferencia() == 0 ? "X" : "", fuente));
        VII_table.getDefaultCell().setBorder(0);
        VII_table.getDefaultCell().setColspan(6);
        VII_table.addCell(new Paragraph("Órden de Servicio", fuente));
        VII_table.getDefaultCell().setColspan(1);
        VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VII_table.getDefaultCell().setBorderWidthRight(0.5F);
        VII_table.getDefaultCell().setBorderWidthTop(0.5F);
        VII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getTiporeferencia() == 1 ? "X" : "", fuente));
        VII_table.getDefaultCell().setBorder(0);
        VII_table.getDefaultCell().setColspan(22);
        VII_table.addCell(new Paragraph(" ", fuente));
        VII_table.getDefaultCell().setColspan(34);
        VII_table.getDefaultCell().setBorder(0);
        VII_table.addCell(paragraph);
        VII_table.getDefaultCell().setColspan(3);
        VII_table.getDefaultCell().setBorder(0);
        VII_table.addCell(new Paragraph("Fecha remisión", fuente));
        VII_table.getDefaultCell().setColspan(1);
        VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VII_table.getDefaultCell().setBorderWidthRight(0.5F);
        VII_table.getDefaultCell().setBorderWidthTop(0.5F);
        if (SihicApp.s_hclfurisp.getFecharemision() == null) {

            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
        } else {
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharemision())).length() < 2 ? "0" : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharemision())).substring(0, 1), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharemision())).length() < 2 ? String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharemision())).substring(0, 1) : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFecharemision())).substring(1, 2), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharemision())).length() < 2 ? "0" : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharemision())).substring(0, 1), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharemision())).length() < 2 ? String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharemision())).substring(0, 1) : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFecharemision())).substring(1, 2), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFecharemision())).substring(0, 1), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFecharemision())).substring(1, 2), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFecharemision())).substring(2, 3), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFecharemision())).substring(3, 4), fuente));
        }
        VII_table.getDefaultCell().setBorder(0);
        VII_table.getDefaultCell().setColspan(3);
        VII_table.addCell(new Paragraph(" ", fuente));
        VII_table.getDefaultCell().setColspan(3);
        VII_table.addCell(new Paragraph("Hora", fuente));
        VII_table.getDefaultCell().setColspan(1);
        VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VII_table.getDefaultCell().setBorderWidthRight(0.5F);
        VII_table.getDefaultCell().setBorderWidthTop(0.5F);
        if (SihicApp.s_hclfurisp.getFecharemision() != null) {
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFecharemision())).length() < 2 ? "0" : String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFecharemision())).substring(0, 1), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFecharemision())).length() < 2 ? String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFecharemision())).substring(0, 1) : String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFecharemision())).substring(1, 2), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFecharemision())).length() < 2 ? "0" : String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFecharemision())).substring(0, 1), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFecharemision())).length() < 2 ? String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFecharemision())).substring(0, 1) : String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFecharemision())).substring(1, 2), fuente));
            VII_table.getDefaultCell().setBorder(0);
        } else {
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.getDefaultCell().setBorder(0);
        }
        VII_table.getDefaultCell().setColspan(13);
        VII_table.addCell(new Paragraph(" ", fuente));

        VII_table.getDefaultCell().setColspan(34);
        VII_table.addCell(paragraph);
        VII_table.getDefaultCell().setColspan(3);
        VII_table.addCell(new Paragraph("Prestador que remite", fuente));
        VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VII_table.getDefaultCell().setBorderWidthRight(0.5F);
        VII_table.getDefaultCell().setBorderWidthTop(0.5F);
        VII_table.getDefaultCell().setColspan(31);
        VII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getPersonaqueremite(), fuente));
        VII_table.getDefaultCell().setBorder(0);
        VII_table.getDefaultCell().setColspan(34);
        VII_table.addCell(paragraph);
        VII_table.getDefaultCell().setColspan(3);
        VII_table.addCell(new Paragraph("Código de Inscripción:", fuente));
        VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VII_table.getDefaultCell().setBorderWidthRight(0.5F);
        VII_table.getDefaultCell().setBorderWidthTop(0.5F);
        VII_table.getDefaultCell().setColspan(1);
        if (SihicApp.s_hclfurisp.getVII_codigoinscripcionremite() == null) {
            SihicApp.s_hclfurisp.setVII_codigoinscripcionremite("");
        }
        for (int i = 0; i < 12; i++) {
            if (i < SihicApp.s_hclfurisp.getVII_codigoinscripcionremite().length()) {
                VII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVII_codigoinscripcionremite().substring(i, i + 1), fuente));
            } else {
                VII_table.addCell(new Paragraph(" ", fuente));
            }
        }
        VII_table.getDefaultCell().setBorder(0);
        VII_table.getDefaultCell().setColspan(19);
        VII_table.addCell(new Paragraph(" ", fuente));
        VII_table.getDefaultCell().setColspan(34);
        VII_table.addCell(paragraph);
        VII_table.getDefaultCell().setColspan(3);
        VII_table.addCell(new Paragraph("Profesional que remite:", fuente));
        VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VII_table.getDefaultCell().setBorderWidthRight(0.5F);
        VII_table.getDefaultCell().setBorderWidthTop(0.5F);
        VII_table.getDefaultCell().setColspan(17);
        VII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVII_profesionalremite(), fuente));
        VII_table.getDefaultCell().setColspan(3);
        VII_table.getDefaultCell().setBorder(0);
        VII_table.addCell(new Paragraph("Cargo:", fuente));
        VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VII_table.getDefaultCell().setBorderWidthRight(0.5F);
        VII_table.getDefaultCell().setBorderWidthTop(0.5F);
        VII_table.getDefaultCell().setColspan(11);
        VII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVII_profesionalremitecargo(), fuente));
        VII_table.getDefaultCell().setBorder(0);
        VII_table.getDefaultCell().setColspan(34);
        VII_table.addCell(paragraph);
        VII_table.getDefaultCell().setColspan(3);
        VII_table.addCell(new Paragraph("Dirección  que remite:", fuente));
        VII_table.getDefaultCell().setColspan(1);
        VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VII_table.getDefaultCell().setBorderWidthRight(0.5F);
        VII_table.getDefaultCell().setBorderWidthTop(0.5F);
        VII_table.getDefaultCell().setColspan(1);
        if (SihicApp.s_hclfurisp.getVII_direccionremite() == null) {
            SihicApp.s_hclfurisp.setVII_direccionremite("");
        }
        for (int i = 0; i < 31; i++) {
            if (i < SihicApp.s_hclfurisp.getVII_direccionremite().length()) {
                VII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVII_direccionremite().substring(i, i + 1), fuente));
            } else {
                VII_table.addCell(new Paragraph(" ", fuente));
            }
        }
        VII_table.getDefaultCell().setBorder(0);
        VII_table.getDefaultCell().setColspan(34);
        VII_table.addCell(paragraph);
        VII_table.getDefaultCell().setColspan(3);
        VII_table.addCell(new Paragraph("Municipio que remite:", fuente));
        VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VII_table.getDefaultCell().setBorderWidthRight(0.5F);
        VII_table.getDefaultCell().setBorderWidthTop(0.5F);
        VII_table.getDefaultCell().setColspan(1);
        if (SihicApp.s_hclfurisp.getVII_genMunicipiosRemite() == null) {
            SihicApp.s_hclfurisp.setVII_genMunicipiosRemite(new GenMunicipios());
            SihicApp.s_hclfurisp.getVII_genMunicipiosRemite().setNombre("");
        }
        for (int i = 0; i < 31; i++) {
            if (i < SihicApp.s_hclfurisp.getVII_genMunicipiosRemite().getNombre().length()) {
                VII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVII_genMunicipiosRemite().getNombre().substring(i, i + 1), fuente));

            } else {
                VII_table.addCell(new Paragraph(" ", fuente));

            }
        }

        VII_table.getDefaultCell().setColspan(34);
        VII_table.getDefaultCell().setBorder(0);
        VII_table.addCell(paragraph);
        VII_table.getDefaultCell().setColspan(3);
        VII_table.getDefaultCell().setBorder(0);
        VII_table.addCell(new Paragraph("Fecha aceptación", fuente));
        VII_table.getDefaultCell().setColspan(1);
        VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VII_table.getDefaultCell().setBorderWidthRight(0.5F);
        VII_table.getDefaultCell().setBorderWidthTop(0.5F);
        if (SihicApp.s_hclfurisp.getFechaaceptacion() != null) {
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechaaceptacion())).length() < 2 ? "0" : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechaaceptacion())).substring(0, 1), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechaaceptacion())).length() < 2 ? String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechaaceptacion())).substring(0, 1) : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechaaceptacion())).substring(1, 2), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechaaceptacion())).length() < 2 ? "0" : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechaaceptacion())).substring(0, 1), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechaaceptacion())).length() < 2 ? String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechaaceptacion())).substring(0, 1) : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechaaceptacion())).substring(1, 2), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFechaaceptacion())).substring(0, 1), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFechaaceptacion())).substring(1, 2), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFechaaceptacion())).substring(2, 3), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFechaaceptacion())).substring(3, 4), fuente));
            VII_table.getDefaultCell().setBorder(0);
            VII_table.getDefaultCell().setColspan(3);
            VII_table.addCell(new Paragraph(" ", fuente));
            VII_table.getDefaultCell().setColspan(3);
            VII_table.addCell(new Paragraph("Hora", fuente));
            VII_table.getDefaultCell().setColspan(1);
            VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
            VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
            VII_table.getDefaultCell().setBorderWidthRight(0.5F);
            VII_table.getDefaultCell().setBorderWidthTop(0.5F);
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaaceptacion())).length() < 2 ? "0" : String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaaceptacion())).substring(0, 1), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaaceptacion())).length() < 2 ? String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaaceptacion())).substring(0, 1) : String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaaceptacion())).substring(1, 2), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaaceptacion())).length() < 2 ? "0" : String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaaceptacion())).substring(0, 1), fuente));
            VII_table.addCell(new Paragraph(String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaaceptacion())).length() < 2 ? String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaaceptacion())).substring(0, 1) : String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaaceptacion())).substring(1, 2), fuente));
        } else {
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.getDefaultCell().setBorder(0);
            VII_table.getDefaultCell().setColspan(3);
            VII_table.addCell(new Paragraph(" ", fuente));
            VII_table.getDefaultCell().setColspan(3);
            VII_table.addCell(new Paragraph("Hora", fuente));
            VII_table.getDefaultCell().setColspan(1);
            VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
            VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
            VII_table.getDefaultCell().setBorderWidthRight(0.5F);
            VII_table.getDefaultCell().setBorderWidthTop(0.5F);
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));
            VII_table.addCell(new Paragraph("", fuente));

        }
        VII_table.getDefaultCell().setBorder(0);
        VII_table.getDefaultCell().setColspan(13);
        VII_table.addCell(new Paragraph(" ", fuente));

        VII_table.getDefaultCell().setColspan(34);
        VII_table.addCell(paragraph);
        VII_table.getDefaultCell().setColspan(3);
        VII_table.addCell(new Paragraph("Prestador que recibe", fuente));
        VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VII_table.getDefaultCell().setBorderWidthRight(0.5F);
        VII_table.getDefaultCell().setBorderWidthTop(0.5F);
        VII_table.getDefaultCell().setColspan(31);
        VII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getPersonaquerecibe(), fuente));
        VII_table.getDefaultCell().setBorder(0);
        VII_table.getDefaultCell().setColspan(34);
        VII_table.addCell(paragraph);
        VII_table.getDefaultCell().setColspan(3);
        VII_table.addCell(new Paragraph("Código de Inscripción:", fuente));
        VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VII_table.getDefaultCell().setBorderWidthRight(0.5F);
        VII_table.getDefaultCell().setBorderWidthTop(0.5F);
        VII_table.getDefaultCell().setColspan(1);
        if (SihicApp.s_hclfurisp.getVII_codigoinscripcionrecibe() == null) {
            SihicApp.s_hclfurisp.setVII_codigoinscripcionrecibe("");
        }
        for (int i = 0; i < 12; i++) {
            if (i < SihicApp.s_hclfurisp.getVII_codigoinscripcionrecibe().length()) {
                VII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVII_codigoinscripcionrecibe().substring(i, i + 1), fuente));
            } else {
                VII_table.addCell(new Paragraph(" ", fuente));
            }
        }
        VII_table.getDefaultCell().setBorder(0);
        VII_table.getDefaultCell().setColspan(19);
        VII_table.addCell(new Paragraph(" ", fuente));
        VII_table.getDefaultCell().setColspan(34);
        VII_table.addCell(paragraph);
        VII_table.getDefaultCell().setColspan(3);
        VII_table.addCell(new Paragraph("Profesional que recibe:", fuente));
        VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VII_table.getDefaultCell().setBorderWidthRight(0.5F);
        VII_table.getDefaultCell().setBorderWidthTop(0.5F);
        VII_table.getDefaultCell().setColspan(17);
        VII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVII_profesionalrecibe(), fuente));
        VII_table.getDefaultCell().setColspan(3);
        VII_table.getDefaultCell().setBorder(0);
        VII_table.addCell(new Paragraph("Cargo:", fuente));
        VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VII_table.getDefaultCell().setBorderWidthRight(0.5F);
        VII_table.getDefaultCell().setBorderWidthTop(0.5F);
        VII_table.getDefaultCell().setColspan(11);
        VII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVII_profesionalrecibecargo(), fuente));
        VII_table.getDefaultCell().setBorder(0);
        VII_table.getDefaultCell().setColspan(34);
        VII_table.addCell(paragraph);
        VII_table.getDefaultCell().setColspan(3);
        VII_table.addCell(new Paragraph("Dirección  que recibe:", fuente));
        VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VII_table.getDefaultCell().setBorderWidthRight(0.5F);
        VII_table.getDefaultCell().setBorderWidthTop(0.5F);
        VII_table.getDefaultCell().setColspan(1);
        if (SihicApp.s_hclfurisp.getVII_direccionrecibe() == null) {
            SihicApp.s_hclfurisp.setVII_direccionrecibe("");
        }
        for (int i = 0; i < 31; i++) {
            if (i < SihicApp.s_hclfurisp.getVII_direccionrecibe().length()) {
                VII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVII_direccionrecibe().substring(i, i + 1), fuente));
            } else {
                VII_table.addCell(new Paragraph(" ", fuente));
            }
        }
        VII_table.getDefaultCell().setBorder(0);
        VII_table.getDefaultCell().setColspan(34);
        VII_table.addCell(paragraph);
        VII_table.getDefaultCell().setColspan(3);
        VII_table.addCell(new Paragraph("Municipio que recibe:", fuente));
        VII_table.getDefaultCell().setBorderWidthBottom(0.5F);
        VII_table.getDefaultCell().setBorderWidthLeft(0.5F);
        VII_table.getDefaultCell().setBorderWidthRight(0.5F);
        VII_table.getDefaultCell().setBorderWidthTop(0.5F);
        VII_table.getDefaultCell().setColspan(1);
        if (SihicApp.s_hclfurisp.getVII_genMunicipiosRecibe() == null) {
            SihicApp.s_hclfurisp.setVII_genMunicipiosRecibe(new GenMunicipios());
            SihicApp.s_hclfurisp.getVII_genMunicipiosRecibe().setNombre("");
        }
        for (int i = 0; i < 31; i++) {
            if (i < SihicApp.s_hclfurisp.getVII_genMunicipiosRecibe().getNombre().length()) {
                VII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVII_genMunicipiosRecibe().getNombre().substring(i, i + 1), fuente));

            } else {
                VII_table.addCell(new Paragraph(" ", fuente));

            }
        }
        VII_table.getDefaultCell().setColspan(34);
        VII_table.getDefaultCell().setBorder(0);
        VII_table.addCell(paragraph);

        document.add(VII_table);

        VIII_table.getDefaultCell().setColspan(34);
        VIII_table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        VIII_table.getDefaultCell().setBorder(0);
        VIII_table.setWidthPercentage(100F);
        VIII_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        VIII_table.addCell(new Paragraph("VIII. AMPARO DE TRANSPORTE Y MOVILIZACIÓN DE LA VICTIMA", fuente));
        VIII_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        VIII_table.getDefaultCell().setColspan(34);
        VIII_table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        VIII_table.addCell(new Paragraph("Diligenciar únicamente para el transporte desde el sitio del evento hasta la primera IPS(Transporte Primario)", fuente));
        VIII_table.getDefaultCell().setColspan(1);
        VIII_table.addCell(new Paragraph("Datos de Vehiculo", fuente));
        VIII_table.getDefaultCell().setColspan(3);
        VIII_table.addCell(new Paragraph("Placa N°", fuente));
        setAllBordes(VIII_table);
        VIII_table.getDefaultCell().setColspan(1);
        if (SihicApp.s_hclfurisp.getPlacamovvictima() == null) {
            SihicApp.s_hclfurisp.setPlacamovvictima("");
        }

        for (int i = 0; i < 6; i++) {
            if (i < SihicApp.s_hclfurisp.getPlacamovvictima().length()) {
                VIII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getPlacamovvictima().substring(i, i + 1), fuente));
            } else {
                VIII_table.addCell(new Paragraph(" ", fuente));

            }
        }
        VIII_table.getDefaultCell().setBorder(0);
        VIII_table.getDefaultCell().setColspan(24);
        VIII_table.addCell(new Paragraph(" ", fuente));
        VIII_table.getDefaultCell().setColspan(34);
        VIII_table.getDefaultCell().setBorder(0);

        VIII_table.addCell(paragraph);
        VIII_table.getDefaultCell().setColspan(14);
        VIII_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VIII_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VIII_table.getDefaultCell().setBorderWidthTop(0.1F);
        VIII_table.getDefaultCell().setBorderWidthRight(0.1F);

        VIII_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        if (SihicApp.s_hclfurisp.getVIII_consuctorvehiculo() == null) {
            SihicApp.s_hclfurisp.setVIII_consuctorvehiculo(new GenPersonas());
            SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().setPrimerApellido("");
            SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().setSegundoApellido("");
            SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().setPrimerNombre("");
            SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().setSegundoNombre("");

        }
        VIII_table.addCell(new Paragraph(" " + SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().getPrimerApellido(), fuente));
        VIII_table.getDefaultCell().setBorder(0);
        VIII_table.getDefaultCell().setColspan(2);
        VIII_table.addCell(new Paragraph("", fuente));

        VIII_table.getDefaultCell().setColspan(18);
        VIII_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VIII_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VIII_table.getDefaultCell().setBorderWidthTop(0.1F);
        VIII_table.getDefaultCell().setBorderWidthRight(0.1F);
        VIII_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        VIII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().getSegundoApellido(), fuente));
        VIII_table.getDefaultCell().setBorder(0);
        VIII_table.getDefaultCell().setColspan(14);
        VIII_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        VIII_table.addCell(new Paragraph("1er Apellido", fuente));
        VIII_table.getDefaultCell().setColspan(2);
        VIII_table.addCell(new Paragraph(" ", fuente));
        VIII_table.getDefaultCell().setColspan(18);
        VIII_table.addCell(new Paragraph("2do Apellido", fuente));
        VIII_table.getDefaultCell().setBorder(0);
        VIII_table.getDefaultCell().setColspan(34);
        VIII_table.addCell(paragraph);
        VIII_table.getDefaultCell().setColspan(14);
        VIII_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VIII_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VIII_table.getDefaultCell().setBorderWidthTop(0.1F);
        VIII_table.getDefaultCell().setBorderWidthRight(0.1F);

        VIII_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        VIII_table.addCell(new Paragraph(" " + SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().getPrimerNombre(), fuente));
        VIII_table.getDefaultCell().setBorder(0);
        VIII_table.getDefaultCell().setColspan(2);
        VIII_table.addCell(new Paragraph("", fuente));

        VIII_table.getDefaultCell().setColspan(18);
        VIII_table.getDefaultCell().setBorderWidthBottom(0.1F);
        VIII_table.getDefaultCell().setBorderWidthLeft(0.1F);
        VIII_table.getDefaultCell().setBorderWidthTop(0.1F);
        VIII_table.getDefaultCell().setBorderWidthRight(0.1F);
        VIII_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        VIII_table.addCell(new Paragraph(" " + SihicApp.s_hclfurisp.getVIII_consuctorvehiculo().getSegundoNombre(), fuente));
        VIII_table.getDefaultCell().setBorder(0);
        VIII_table.getDefaultCell().setColspan(14);
        VIII_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        VIII_table.addCell(new Paragraph("1er Nombre", fuente));
        VIII_table.getDefaultCell().setColspan(2);
        VIII_table.addCell(new Paragraph(" ", fuente));
        VIII_table.getDefaultCell().setColspan(18);
        VIII_table.addCell(new Paragraph("2do Nombre", fuente));
        VIII_table.getDefaultCell().setBorder(0);
        VIII_table.getDefaultCell().setColspan(34);
        VIII_table.addCell(paragraph);
        VIII_table.getDefaultCell().setColspan(3);
        VIII_table.addCell(new Paragraph("Transporto la victima desde", fuente));
        setAllBordes(VIII_table);
        VIII_table.getDefaultCell().setColspan(14);
        VIII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getTrasnportodesde(), fuente));
        NoBordes(VIII_table);
        VIII_table.getDefaultCell().setColspan(3);
        VIII_table.addCell(new Paragraph("Hasta", fuente));
        setAllBordes(VIII_table);
        VIII_table.getDefaultCell().setColspan(14);
        VIII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getTransportehasta(), fuente));
        VIII_table.getDefaultCell().setBorder(0);
        VIII_table.getDefaultCell().setColspan(34);
        VIII_table.addCell(paragraph);
        VIII_table.getDefaultCell().setColspan(1);
        VIII_table.addCell(new Paragraph("Tipo de Transporte", fuente));
        NoBordes(VIII_table);
        VIII_table.getDefaultCell().setColspan(6);
        VIII_table.addCell(new Paragraph("Ambulancia Básica", fuente));
        setAllBordes(VIII_table);
        VIII_table.getDefaultCell().setColspan(1);
        VIII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getTipotransporte() == 0 ? "X" : "", fuente));
        NoBordes(VIII_table);
        VIII_table.getDefaultCell().setColspan(7);
        VIII_table.addCell(new Paragraph("Ambulancia Médicada", fuente));
        setAllBordes(VIII_table);
        VIII_table.getDefaultCell().setColspan(1);
        VIII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getTipotransporte() == 1 ? "X" : "", fuente));
        NoBordes(VIII_table);
        VIII_table.getDefaultCell().setColspan(10);
        VIII_table.addCell(new Paragraph("Lugar donde recoge la Victima", fuente));
        setAllBordes(VIII_table);
        VIII_table.getDefaultCell().setColspan(1);
        VIII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getTipotransporte() == 2 ? "X" : "", fuente));
        NoBordes(VIII_table);
        VIII_table.getDefaultCell().setColspan(2);
        VIII_table.addCell(new Paragraph("Zona", fuente));
        setAllBordes(VIII_table);
        VIII_table.getDefaultCell().setColspan(2);
        if (SihicApp.s_hclfurisp.getVIII_zona() == null) {
            SihicApp.s_hclfurisp.setVIII_zona(new GenZonaResidencia());
            SihicApp.s_hclfurisp.getVIII_zona().setCodigo("");
        }
        VIII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVIII_zona().getCodigo().equals("UR") ? "U:X" : "U:", fuente));
        NoBordes(VIII_table);
        VIII_table.getDefaultCell().setColspan(1);
        VIII_table.addCell(new Paragraph(" ", fuente));
        setAllBordes(VIII_table);
        VIII_table.getDefaultCell().setColspan(2);
        VIII_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getVIII_zona().getCodigo().equals("RU") ? "R:X" : "R:", fuente));
        NoBordes(VIII_table);
        VIII_table.getDefaultCell().setColspan(34);
        VIII_table.addCell(paragraph);
        document.add(VIII_table);

        //***************************************************************************************************IX
        IX_table.getDefaultCell().setColspan(34);
        IX_table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        IX_table.getDefaultCell().setBorder(0);
        IX_table.setWidthPercentage(100F);
        IX_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        IX_table.addCell(new Paragraph("IX. CERTIFICADO DE LA ATENCIÓN MEDICA DE LA VICTIMA COMO PRUEBA DEL ACCIDENTE O EVENTO ", fuente));
        IX_table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        IX_table.getDefaultCell().setColspan(34);
        IX_table.getDefaultCell().setBorder(0);
        IX_table.addCell(paragraph);
        IX_table.getDefaultCell().setColspan(1);
        IX_table.getDefaultCell().setBorder(0);
        IX_table.addCell(new Paragraph("Fecha ingreso", fuente));
        IX_table.getDefaultCell().setColspan(1);
        IX_table.getDefaultCell().setBorderWidthBottom(0.5F);
        IX_table.getDefaultCell().setBorderWidthLeft(0.5F);
        IX_table.getDefaultCell().setBorderWidthRight(0.5F);
        IX_table.getDefaultCell().setBorderWidthTop(0.5F);
        if (SihicApp.s_hclfurisp.getFechaingreso() == null) {
            SihicApp.s_hclfurisp.setFechaingreso(new Date());
        }
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechaingreso())).length() < 2 ? "0" : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechaingreso())).substring(0, 1), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechaingreso())).length() < 2 ? String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechaingreso())).substring(0, 1) : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechaingreso())).substring(1, 2), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechaingreso())).length() < 2 ? "0" : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechaingreso())).substring(0, 1), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechaingreso())).length() < 2 ? String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechaingreso())).substring(0, 1) : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechaingreso())).substring(1, 2), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFechaingreso())).substring(0, 1), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFechaingreso())).substring(1, 2), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFechaingreso())).substring(2, 3), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFechaingreso())).substring(3, 4), fuente));
        IX_table.getDefaultCell().setBorder(0);
        IX_table.getDefaultCell().setColspan(2);
        IX_table.addCell(new Paragraph("a las", fuente));
        IX_table.getDefaultCell().setColspan(1);
        IX_table.getDefaultCell().setBorderWidthBottom(0.5F);
        IX_table.getDefaultCell().setBorderWidthLeft(0.5F);
        IX_table.getDefaultCell().setBorderWidthRight(0.5F);
        IX_table.getDefaultCell().setBorderWidthTop(0.5F);
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaingreso())).length() < 2 ? "0" : String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaingreso())).substring(0, 1), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaingreso())).length() < 2 ? String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaingreso())).substring(0, 1) : String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaingreso())).substring(1, 2), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaingreso())).length() < 2 ? "0" : String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaingreso())).substring(0, 1), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaingreso())).length() < 2 ? String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaingreso())).substring(0, 1) : String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaingreso())).substring(1, 2), fuente));

        IX_table.getDefaultCell().setColspan(5);
        IX_table.getDefaultCell().setBorder(0);
        IX_table.addCell(new Paragraph("Fecha egreso", fuente));
        IX_table.getDefaultCell().setColspan(1);
        IX_table.getDefaultCell().setBorderWidthBottom(0.5F);
        IX_table.getDefaultCell().setBorderWidthLeft(0.5F);
        IX_table.getDefaultCell().setBorderWidthRight(0.5F);
        IX_table.getDefaultCell().setBorderWidthTop(0.5F);
        if (SihicApp.s_hclfurisp.getFechaegreso() == null) {
            SihicApp.s_hclfurisp.setFechaegreso(new Date());
        }
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechaegreso())).length() < 2 ? "0" : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechaegreso())).substring(0, 1), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechaegreso())).length() < 2 ? String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechaegreso())).substring(0, 1) : String.valueOf(UtilDate.getdiafecha(SihicApp.s_hclfurisp.getFechaegreso())).substring(1, 2), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechaegreso())).length() < 2 ? "0" : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechaegreso())).substring(0, 1), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechaegreso())).length() < 2 ? String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechaegreso())).substring(0, 1) : String.valueOf(UtilDate.getmesfecha(SihicApp.s_hclfurisp.getFechaegreso())).substring(1, 2), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFechaegreso())).substring(0, 1), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFechaegreso())).substring(1, 2), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFechaegreso())).substring(2, 3), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getyearfecha(SihicApp.s_hclfurisp.getFechaegreso())).substring(3, 4), fuente));
        IX_table.getDefaultCell().setBorder(0);
        IX_table.getDefaultCell().setColspan(2);
        IX_table.addCell(new Paragraph("a las", fuente));
        IX_table.getDefaultCell().setColspan(1);
        IX_table.getDefaultCell().setBorderWidthBottom(0.5F);
        IX_table.getDefaultCell().setBorderWidthLeft(0.5F);
        IX_table.getDefaultCell().setBorderWidthRight(0.5F);
        IX_table.getDefaultCell().setBorderWidthTop(0.5F);
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaegreso())).length() < 2 ? "0" : String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaegreso())).substring(0, 1), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaegreso())).length() < 2 ? String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaegreso())).substring(0, 1) : String.valueOf(UtilDate.gethorafecha(SihicApp.s_hclfurisp.getFechaegreso())).substring(1, 2), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaegreso())).length() < 2 ? "0" : String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaegreso())).substring(0, 1), fuente));
        IX_table.addCell(new Paragraph(String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaegreso())).length() < 2 ? String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaegreso())).substring(0, 1) : String.valueOf(UtilDate.getminutofecha(SihicApp.s_hclfurisp.getFechaegreso())).substring(1, 2), fuente));
        IX_table.getDefaultCell().setBorder(0);

        IX_table.getDefaultCell().setColspan(34);
        IX_table.addCell(paragraph);
        NoBordes(IX_table);
        IX_table.getDefaultCell().setColspan(10);
        IX_table.addCell(new Paragraph("Código de Diagnóstico principal Ingreso", fuente));
        IX_table.getDefaultCell().setColspan(1);
        setAllBordes(IX_table);
        if (SihicApp.s_hclfurisp.getDxmaininit() == null) {
            SihicApp.s_hclfurisp.setDxmaininit(new HclCie10());
            SihicApp.s_hclfurisp.getDxmaininit().setCodigo("");
        }
        for (int i = 0; i < 4; i++) {
            if (i < SihicApp.s_hclfurisp.getDxmaininit().getCodigo().length()) {
                IX_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getDxmaininit().getCodigo().substring(i, i + 1), fuente));
            } else {
                IX_table.addCell(new Paragraph(" ", fuente));

            }
        }
        NoBordes(IX_table);
        IX_table.getDefaultCell().setColspan(1);
        IX_table.addCell(new Paragraph(" ", fuente));
        NoBordes(IX_table);
        IX_table.getDefaultCell().setColspan(15);
        IX_table.addCell(new Paragraph("Código de Diagnóstico principal Egreso", fuente));
        IX_table.getDefaultCell().setColspan(1);
        setAllBordes(IX_table);
        if (SihicApp.s_hclfurisp.getDxmainend() == null) {
            SihicApp.s_hclfurisp.setDxmainend(new HclCie10());
            SihicApp.s_hclfurisp.getDxmainend().setCodigo("");
        }
        for (int i = 0; i < 4; i++) {
            if (i < SihicApp.s_hclfurisp.getDxmainend().getCodigo().length()) {
                IX_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getDxmainend().getCodigo().substring(i, i + 1), fuente));
            } else {
                IX_table.addCell(new Paragraph(" ", fuente));

            }
        }
        NoBordes(IX_table);

        IX_table.getDefaultCell().setColspan(34);
        IX_table.addCell(paragraph);
        NoBordes(IX_table);
        IX_table.getDefaultCell().setColspan(10);
        IX_table.addCell(new Paragraph(" Otro Código de Diagnóstico principal Ingreso", fuente));
        IX_table.getDefaultCell().setColspan(1);
        setAllBordes(IX_table);
        if (SihicApp.s_hclfurisp.getDxtherinit() == null) {
            SihicApp.s_hclfurisp.setDxtherinit(new HclCie10());
            SihicApp.s_hclfurisp.getDxtherinit().setCodigo("");
        }
        for (int i = 0; i < 4; i++) {
            if (i < SihicApp.s_hclfurisp.getDxtherinit().getCodigo().length()) {
                IX_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getDxtherinit().getCodigo().substring(i, i + 1), fuente));
            } else {
                IX_table.addCell(new Paragraph(" ", fuente));

            }
        }
        NoBordes(IX_table);
        IX_table.getDefaultCell().setColspan(1);
        IX_table.addCell(new Paragraph(" ", fuente));
        NoBordes(IX_table);
        IX_table.getDefaultCell().setColspan(15);
        IX_table.addCell(new Paragraph(" Otro Código de Diagnóstico principal Egreso", fuente));
        IX_table.getDefaultCell().setColspan(1);
        setAllBordes(IX_table);
        if (SihicApp.s_hclfurisp.getDxtherend() == null) {
            SihicApp.s_hclfurisp.setDxtherend(new HclCie10());
            SihicApp.s_hclfurisp.getDxtherend().setCodigo("");
        }
        for (int i = 0; i < 4; i++) {
            if (i < SihicApp.s_hclfurisp.getDxtherend().getCodigo().length()) {
                IX_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getDxtherend().getCodigo().substring(i, i + 1), fuente));
            } else {
                IX_table.addCell(new Paragraph(" ", fuente));

            }
        }
        NoBordes(IX_table);

        IX_table.getDefaultCell().setColspan(34);
        IX_table.addCell(paragraph);
        NoBordes(IX_table);
        IX_table.getDefaultCell().setColspan(10);
        IX_table.addCell(new Paragraph(" Otro Código de Diagnóstico principal Ingreso", fuente));
        IX_table.getDefaultCell().setColspan(1);
        setAllBordes(IX_table);
        if (SihicApp.s_hclfurisp.getDxther2init() == null) {
            SihicApp.s_hclfurisp.setDxther2init(new HclCie10());
            SihicApp.s_hclfurisp.getDxther2init().setCodigo("");
        }
        for (int i = 0; i < 4; i++) {
            if (i < SihicApp.s_hclfurisp.getDxther2init().getCodigo().length()) {
                IX_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getDxther2init().getCodigo().substring(i, i + 1), fuente));
            } else {
                IX_table.addCell(new Paragraph(" ", fuente));

            }
        }
        NoBordes(IX_table);
        IX_table.getDefaultCell().setColspan(1);
        IX_table.addCell(new Paragraph(" ", fuente));
        NoBordes(IX_table);
        IX_table.getDefaultCell().setColspan(15);
        IX_table.addCell(new Paragraph(" Otro Código de Diagnóstico principal Egreso", fuente));
        IX_table.getDefaultCell().setColspan(1);
        setAllBordes(IX_table);
        if (SihicApp.s_hclfurisp.getDxther2end() == null) {
            SihicApp.s_hclfurisp.setDxther2end(new HclCie10());
            SihicApp.s_hclfurisp.getDxther2end().setCodigo("");
        }
        for (int i = 0; i < 4; i++) {
            if (i < SihicApp.s_hclfurisp.getDxther2end().getCodigo().length()) {
                IX_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getDxther2end().getCodigo().substring(i, i + 1), fuente));
            } else {
                IX_table.addCell(new Paragraph(" ", fuente));

            }
        }
        NoBordes(IX_table);

        if (SihicApp.s_hclfurisp.getIX_profesionaltratante() == null) {
            SihicApp.s_hclfurisp.setIX_profesionaltratante(new GenPersonas());
        }
        IX_table.getDefaultCell().setBorder(0);
        IX_table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        IX_table.getDefaultCell().setColspan(34);
        IX_table.addCell(paragraph);
        IX_table.getDefaultCell().setColspan(14);
        IX_table.getDefaultCell().setBorderWidthBottom(0.1F);
        IX_table.getDefaultCell().setBorderWidthLeft(0.1F);
        IX_table.getDefaultCell().setBorderWidthTop(0.1F);
        IX_table.getDefaultCell().setBorderWidthRight(0.1F);

        IX_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        IX_table.addCell(new Paragraph(" " + SihicApp.s_hclfurisp.getIX_profesionaltratante().getPrimerApellido(), fuente));
        IX_table.getDefaultCell().setBorder(0);
        IX_table.getDefaultCell().setColspan(2);
        IX_table.addCell(new Paragraph("", fuente));

        IX_table.getDefaultCell().setColspan(18);
        IX_table.getDefaultCell().setBorderWidthBottom(0.1F);
        IX_table.getDefaultCell().setBorderWidthLeft(0.1F);
        IX_table.getDefaultCell().setBorderWidthTop(0.1F);
        IX_table.getDefaultCell().setBorderWidthRight(0.1F);
        IX_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        IX_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getIX_profesionaltratante().getSegundoApellido(), fuente));
        IX_table.getDefaultCell().setBorder(0);
        IX_table.getDefaultCell().setColspan(14);
        IX_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        IX_table.addCell(new Paragraph("1er Apellido", fuente));
        IX_table.getDefaultCell().setColspan(2);
        IX_table.addCell(new Paragraph(" ", fuente));
        IX_table.getDefaultCell().setColspan(18);
        IX_table.addCell(new Paragraph("2do Apellido", fuente));
        IX_table.getDefaultCell().setBorder(0);
        IX_table.getDefaultCell().setColspan(34);
        IX_table.addCell(paragraph);
        IX_table.getDefaultCell().setColspan(14);
        IX_table.getDefaultCell().setBorderWidthBottom(0.1F);
        IX_table.getDefaultCell().setBorderWidthLeft(0.1F);
        IX_table.getDefaultCell().setBorderWidthTop(0.1F);
        IX_table.getDefaultCell().setBorderWidthRight(0.1F);

        IX_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        IX_table.addCell(new Paragraph(" " + SihicApp.s_hclfurisp.getIX_profesionaltratante().getPrimerNombre(), fuente));
        IX_table.getDefaultCell().setBorder(0);
        IX_table.getDefaultCell().setColspan(2);
        IX_table.addCell(new Paragraph("", fuente));

        IX_table.getDefaultCell().setColspan(18);
        IX_table.getDefaultCell().setBorderWidthBottom(0.1F);
        IX_table.getDefaultCell().setBorderWidthLeft(0.1F);
        IX_table.getDefaultCell().setBorderWidthTop(0.1F);
        IX_table.getDefaultCell().setBorderWidthRight(0.1F);
        IX_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        IX_table.addCell(new Paragraph(" " + SihicApp.s_hclfurisp.getIX_profesionaltratante().getSegundoNombre(), fuente));
        IX_table.getDefaultCell().setBorder(0);
        IX_table.getDefaultCell().setColspan(14);
        IX_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        IX_table.addCell(new Paragraph("1er Nombre", fuente));
        IX_table.getDefaultCell().setColspan(2);
        IX_table.addCell(new Paragraph(" ", fuente));
        IX_table.getDefaultCell().setColspan(18);
        IX_table.addCell(new Paragraph("2do Nombre", fuente));
        IX_table.getDefaultCell().setBorder(0);
        IX_table.getDefaultCell().setColspan(34);
        IX_table.addCell(paragraph);
        IX_table.getDefaultCell().setColspan(1);
        IX_table.addCell(new Paragraph("Tipo de Documento", fuente));
        IX_table.getDefaultCell().setColspan(2);
        IX_table.getDefaultCell().setBorderWidthBottom(0.1F);
        IX_table.getDefaultCell().setBorderWidthLeft(0.1F);
        IX_table.getDefaultCell().setBorderWidthTop(0.1F);
        IX_table.getDefaultCell().setBorderWidthRight(0.1F);
        if (SihicApp.s_hclfurisp.getIX_profesionaltratante().getGenTiposDocumentos() == null) {
            SihicApp.s_hclfurisp.getIX_profesionaltratante().setGenTiposDocumentos(new GenTiposDocumentos());
            SihicApp.s_hclfurisp.getIX_profesionaltratante().getGenTiposDocumentos().setSigla("");

        }

        for (int i = 0; i < 8; i++) {
            switch (i) {
                case 0:

                    IX_table.addCell(new Paragraph("CC:" + (SihicApp.s_hclfurisp.getIX_profesionaltratante().getGenTiposDocumentos().getSigla().equals("CC") ? "X" : ""), fuente));
                    break;
                case 1:
                    IX_table.addCell(new Paragraph("CE:" + (SihicApp.s_hclfurisp.getIX_profesionaltratante().getGenTiposDocumentos().getSigla().equals("CE") ? "X" : ""), fuente));
                    break;
                case 2:

                    IX_table.addCell(new Paragraph("PA:" + (SihicApp.s_hclfurisp.getIX_profesionaltratante().getGenTiposDocumentos().getSigla().equals("PA") ? "X" : ""), fuente));
                    break;
                case 3:

                    IX_table.addCell(new Paragraph("TI:" + (SihicApp.s_hclfurisp.getIX_profesionaltratante().getGenTiposDocumentos().getSigla().equals("TI") ? "X" : ""), fuente));
                    break;
                case 4:

                    IX_table.addCell(new Paragraph("RC:" + (SihicApp.s_hclfurisp.getIX_profesionaltratante().getGenTiposDocumentos().getSigla().equals("RC") ? "X" : ""), fuente));
                    break;
                case 5:

                    IX_table.addCell(new Paragraph("AS:" + (SihicApp.s_hclfurisp.getIX_profesionaltratante().getGenTiposDocumentos().getSigla().equals("AS") ? "X" : ""), fuente));
                    break;
                case 6:

                    IX_table.addCell(new Paragraph("MS:" + (SihicApp.s_hclfurisp.getIX_profesionaltratante().getGenTiposDocumentos().getSigla().equals("MS") ? "X" : ""), fuente));
                    break;
                case 7:

                    IX_table.addCell(new Paragraph("CD:" + (SihicApp.s_hclfurisp.getIX_profesionaltratante().getGenTiposDocumentos().getSigla().equals("CD") ? "X" : ""), fuente));
                    break;
            }
        }

        IX_table.getDefaultCell().setColspan(5);
        IX_table.getDefaultCell().setBorder(0);
        IX_table.addCell(new Paragraph("N° Documento", fuente));
        IX_table.getDefaultCell().setColspan(1);
        IX_table.getDefaultCell().setBorderWidthBottom(0.1F);
        IX_table.getDefaultCell().setBorderWidthLeft(0.1F);
        IX_table.getDefaultCell().setBorderWidthTop(0.1F);
        IX_table.getDefaultCell().setBorderWidthRight(0.1F);
        if (SihicApp.s_hclfurisp.getIX_profesionaltratante().getDocumento() == null) {
            SihicApp.s_hclfurisp.getIX_profesionaltratante().setDocumento("");
        }
        for (int i = 0; i < 12; i++) {
            if (i < SihicApp.s_hclfurisp.getIX_profesionaltratante().getDocumento().length()) {
                IX_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getIX_profesionaltratante().getDocumento().substring(i, i + 1), fuente));
            } else {
                IX_table.addCell(new Paragraph(" ", fuente));

            }
        }
        IX_table.getDefaultCell().setColspan(34);
        IX_table.getDefaultCell().setBorder(0);
        IX_table.addCell(paragraph);
        IX_table.getDefaultCell().setColspan(14);
        IX_table.addCell(paragraph);
        IX_table.getDefaultCell().setColspan(8);
        IX_table.getDefaultCell().setBorder(0);
        IX_table.addCell(new Paragraph("N° de Registro Médico", fuente));
        IX_table.getDefaultCell().setColspan(1);
        IX_table.getDefaultCell().setBorderWidthBottom(0.1F);
        IX_table.getDefaultCell().setBorderWidthLeft(0.1F);
        IX_table.getDefaultCell().setBorderWidthTop(0.1F);
        IX_table.getDefaultCell().setBorderWidthRight(0.1F);
        if (SihicApp.s_hclfurisp.getIX_noregistromedico() == null) {
            SihicApp.s_hclfurisp.setIX_noregistromedico("");
        }
        for (int i = 0; i < 12; i++) {
            if (i < SihicApp.s_hclfurisp.getIX_noregistromedico().length()) {
                IX_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getIX_noregistromedico().substring(i, i + 1), fuente));
            } else {
                IX_table.addCell(new Paragraph(" ", fuente));

            }
        }
        NoBordes(IX_table);
        IX_table.getDefaultCell().setColspan(34);
        IX_table.addCell(paragraph);
        document.add(IX_table);
        //***************************************************************************************************X
        NoBordes(X_table);
        X_table.getDefaultCell().setColspan(34);
        X_table.setWidthPercentage(100f);
        X_table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        X_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        X_table.addCell(new Paragraph("X. AMPAROS QUE RECLAMA", fuente));
        X_table.getDefaultCell().setColspan(34);
        X_table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        X_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        X_table.addCell(paragraph);
        X_table.getDefaultCell().setColspan(9);
        X_table.addCell(new Paragraph(" "));
        setAllBordes(X_table);
        X_table.getDefaultCell().setColspan(11);
        X_table.addCell(new Paragraph("Valor total facturado", fuente));
        X_table.addCell(new Paragraph("Valor raclamado al FOSYGA", fuente));
        NoBordes(X_table);
        X_table.getDefaultCell().setColspan(3);
        X_table.addCell(new Paragraph(" "));
        X_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        setAllBordes(X_table);
        X_table.getDefaultCell().setColspan(9);
        X_table.addCell(new Paragraph("Gastos medicos quirurgicos", fuente));

        X_table.getDefaultCell().setColspan(11);
        X_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getX_gastosmedicostotal().toString(), fuente));
        X_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getX_gastosmedicosfosyga().toString(), fuente));
        NoBordes(X_table);
        X_table.getDefaultCell().setColspan(3);
        X_table.addCell(new Paragraph(" "));
        setAllBordes(X_table);
        X_table.getDefaultCell().setColspan(9);
        X_table.addCell(new Paragraph("Gastos de transporte y movilización de victima", fuente));

        X_table.getDefaultCell().setColspan(11);
        X_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getX_gastostransportetotal().toString(), fuente));
        X_table.addCell(new Paragraph(SihicApp.s_hclfurisp.getX_gastostransportefosyga().toString(), fuente));
        NoBordes(X_table);
        X_table.getDefaultCell().setColspan(3);
        X_table.addCell(new Paragraph(" "));
        X_table.getDefaultCell().setColspan(34);
        X_table.addCell(paragraph);
        X_table.addCell(paragraph);
        X_table.addCell(new Paragraph("El total facturado y reclamado descrito en este númeral se debe detallar y hacer descripción de las actividades, procedimientos, medicamentos, insumos, suministros, materiales, dentro del anexo técnico número 2", fuente));
        X_table.getDefaultCell().setColspan(34);
        X_table.addCell(paragraph);
        document.add(X_table);
        //**************************************************************************************************************XI
        NoBordes(XI_table);
        XI_table.getDefaultCell().setColspan(34);
        XI_table.setWidthPercentage(100f);
        XI_table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
        XI_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        XI_table.addCell(new Paragraph("XI. DECLARACIONES DE LA INSTITUCION PRESTADORA DE SERVICIOS DE SALUD", fuente));
        XI_table.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        XI_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        //XI_table.addCell(paragraph);
        XI_table.addCell(paragraph);
        XI_table.addCell(new Paragraph("Como representante legal o Gerente de la Institución Prestadora de Servicios de Salud, declaró bajo la gavedad de juramento que toda la información contenidad en este formulario es cierta y podrá se verificada por la Compañía de Seguros, por la Dirección de Administracion de Fondos de la Protección Social o quien haga sus veces, por el Administrador Fiduciario del Fondo de Solidaridad y Garantía Fosyga, por la Superintendencia Nacional de Salud o la Contraloria General de la República de no ser así, acepto todas las consecuencias legales que produzca esta situación. Adicionalmente, manifiesto que la reclamación no ha sido presentada con anterioridad ni se ha recibido pago alguno por las sumas reclamadas", fuente));
        XI_table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        // XI_table.addCell(paragraph);
        // XI_table.addCell(paragraph);
        XI_table.addCell(paragraph);
        XI_table.addCell(new Paragraph("_________________________________________________          _________________________________________________", fuente));
        XI_table.addCell(new Paragraph("                     NOMBRE                                                               FIRMA DEL REPRESENTATE LEGAL O GERENTE", fuente));
        document.add(XI_table);
        document.close();

        File file = new File("/home/adminlinux/sihic/furisp.pdf");

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

    private void setAllBordes(PdfPTable table) {
        table.getDefaultCell().setBorderWidthBottom(0.1F);
        table.getDefaultCell().setBorderWidthLeft(0.1F);
        table.getDefaultCell().setBorderWidthTop(0.1F);
        table.getDefaultCell().setBorderWidthRight(0.1F);
    }

    private void NoBordes(PdfPTable table) {
        table.getDefaultCell().setBorder(0);

    }

}
