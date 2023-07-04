/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.reportespdf;

import com.itextpdf.text.BadElementException;
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
import javax.swing.JOptionPane;
import sihic.SihicApp;
import sihic.util.Cabecera;
import sihic.util.FooterPlantillas;

/**
 *
 * @author karolyani
 */
public class PlantillasPdf {

    private static final String BASE_FONT_BOLD = "Trebuchet MS B";

    public void fplantillas() throws IOException, BadElementException, DocumentException {

        Document document = new Document();
        Font fuente = new Font();
        Font fuente2 = new Font();
        document = new Document(PageSize.LETTER, 20f, 20f, 20f, 180f);
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/resultadoplantilla.pdf"));
        Rectangle rect = new Rectangle(PageSize.LETTER);
        writer.setBoxSize("art", rect);
        FooterPlantillas footer = new FooterPlantillas();
        writer.setPageEvent(footer);
        document.open();

        //PdfPTable table = new PdfPTable(new float[] { 5, 5});
        PdfPTable table = new PdfPTable(new float[]{10});
        PdfPTable tableImagenTexto = new PdfPTable(new float[]{10});
        PdfPTable tableGeneric = new PdfPTable(new float[]{5, 5, 5, 5});
        Image imagen;

        imagen = Image.getInstance(SihicApp.genUnidadesOrganizacion.getLogo());

        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setFixedHeight(40f);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableImagenTexto.addCell(imagen);

        tableImagenTexto.getDefaultCell().setFixedHeight(0f);
        table.setWidthPercentage(100f);

        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        fuente = FontFactory.getFont("arial", 14, Font.BOLD);
        tableImagenTexto.addCell(new Paragraph("CENTRO MÉDICO DEL GUAVIARE SAS", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);

        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableImagenTexto.addCell(new Paragraph("Nit.: 900.767.863 ", fuente));
        tableImagenTexto.addCell(new Paragraph("CRA 22 N° 11-18 B. LA ESPERANZA TEL. 3134212162 ", fuente));
        tableImagenTexto.addCell(new Paragraph("SAN JOSE DEL GUAVIARE", fuente));
        table.getDefaultCell().setPadding(10);
        table.setWidthPercentage(100f);

        document.add(tableImagenTexto);

        //tabla datos de cliente
        table.getDefaultCell().setPadding(2);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
        table.getDefaultCell().setBorder(0);
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        fuente = FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
        document.add(new Paragraph("Hora: " + sihic.util.UtilDate.gethorafecha(SihicApp.resultados.getFechaCreacion()) + ":" + sihic.util.UtilDate.getminutofecha(SihicApp.resultados.getFechaCreacion()), fuente));
        document.add(new Paragraph("Fecha: " + sihic.util.UtilDate.formatonombremesdiayear(SihicApp.resultados.getFechaCreacion()), fuente));
        document.add(new Paragraph("Nombre: " + SihicApp.resultados.getFacturaItem().getGenPacientes().getGenPersonas().getNombres(), fuente));
        document.add(new Paragraph("N° Ident: " + SihicApp.resultados.getFacturaItem().getGenPacientes().getGenPersonas().getDocumento(), fuente));
        document.add(new Paragraph("Edad: " + SihicApp.resultados.getFacturaItem().getGenPacientes().getGenPersonas().year(), fuente));
        document.add(new Paragraph("Entidad: " + SihicApp.resultados.getFacturaItem().getGenPacientes().getGenEapb().getNombre(), fuente));
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        fuente = FontFactory.getFont(BASE_FONT_BOLD, 12, Font.UNDERLINE);
        Paragraph titulo = new Paragraph("EXAMEN SOLICITADO: " + SihicApp.resultados.getExamensolicitado(), fuente);
        titulo.setAlignment(Element.ALIGN_CENTER);
        document.add(titulo);
        document.add(new Paragraph("\n"));
        switch (SihicApp.resultados.getPlantilla()) {
            case 0:
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);

                table.addCell(new Paragraph(SihicApp.resultados.getDescripcion(), fuente));

                fuente2 = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
                table.addCell("\n");
                table.addCell("\n");

                table.addCell(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));
                document.add(table);
                break;
            case 1:
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                table.addCell(new Paragraph("Utero: " + SihicApp.resultados.getUtero(), fuente));
                table.addCell(new Paragraph(SihicApp.resultados.getDescripcion(), fuente));
                table.addCell("\n");
                fuente = FontFactory.getFont("arial", 12, Font.BOLD);
                table.addCell(new Paragraph("Biometria Fetal", fuente));
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                table.addCell(new Paragraph(SihicApp.resultados.getDescripcion(), fuente));
                table.addCell(new Paragraph("DBP: " + SihicApp.resultados.getDbp() + "   mm", fuente));
                table.addCell(new Paragraph("CC:  " + SihicApp.resultados.getCc() + "    mm", fuente));
                table.addCell(new Paragraph("CA:  " + SihicApp.resultados.getCa() + "    mm", fuente));
                table.addCell(new Paragraph("LF:  " + SihicApp.resultados.getLf() + "    mm", fuente));
                table.addCell(new Paragraph("Peso: " + SihicApp.resultados.getLf() + "   gr", fuente));
                table.addCell(new Paragraph("Placenta:          " + SihicApp.resultados.getPlacenta(), fuente));
                table.addCell(new Paragraph("Liquido Amniotico: " + SihicApp.resultados.getLiquidoamniotico(), fuente));
                table.addCell(new Paragraph("Cervix:            " + SihicApp.resultados.getCervix(), fuente));
                table.addCell("\n");
                table.addCell("\n");
                table.addCell(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));
                document.add(table);
                break;
            case 2:
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                document.add(new Paragraph(SihicApp.resultados.getDescripcion_corta(), fuente));
                document.add(new Paragraph("\n"));
                tableGeneric.addCell(new Paragraph("", fuente));
                tableGeneric.addCell(new Paragraph("VEL. MÁXIMA", fuente));
                tableGeneric.addCell(new Paragraph("VEL. MÍNIMA", fuente));
                tableGeneric.addCell(new Paragraph("IR", fuente));
                tableGeneric.addCell(new Paragraph("ARTERIA HEPATICA", fuente));
                tableGeneric.addCell(new Paragraph(SihicApp.resultados.getVelmax(), fuente));
                tableGeneric.addCell(new Paragraph(SihicApp.resultados.getVelmin(), fuente));
                tableGeneric.addCell(new Paragraph(SihicApp.resultados.getIr(), fuente));
                document.add(tableGeneric);

                table.addCell(new Paragraph(SihicApp.resultados.getDescripcion(), fuente));
                table.addCell("\n");
                table.addCell("\n");
                table.addCell(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));
                document.add(table);
                break;
            case 3:
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                table.addCell(new Paragraph("Testiculo Derecho:   " + SihicApp.resultados.getTesticuloder(), fuente));
                table.addCell(new Paragraph("Testiculo Izquierdo: " + SihicApp.resultados.getTesticuloizq(), fuente));
                table.addCell(new Paragraph(SihicApp.resultados.getDescripcion(), fuente));
                table.addCell("\n");
                table.addCell("\n");
                table.addCell(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));
                document.add(table);

                break;
            case 4:

                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                table.addCell(new Paragraph(SihicApp.resultados.getDescripcion_corta(), fuente));
                table.addCell(new Paragraph("Riñon Derecho:   " + SihicApp.resultados.getRinonder(), fuente));
                table.addCell(new Paragraph("Riñon Izquierdo: " + SihicApp.resultados.getRinosizq(), fuente));
                table.addCell(new Paragraph(SihicApp.resultados.getDescripcion(), fuente));
                table.addCell("\n");
                table.addCell("\n");
                table.addCell(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));
                document.add(table);
                break;
            case 5:

                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                document.add(new Paragraph(SihicApp.resultados.getDescripcion(), fuente));
                document.add(new Paragraph("\n"));
                tableGeneric.addCell(new Paragraph("", fuente));
                tableGeneric.addCell(new Paragraph("VEL. MÁXIMA\nDer      Izq", fuente));
                tableGeneric.addCell(new Paragraph("VEL. MÍNIMA\nDer      Izq", fuente));
                tableGeneric.addCell(new Paragraph("IR\nDer      Izq", fuente));
                tableGeneric.addCell(new Paragraph("ARTERIA ARCUATA", fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getArcuatavelmaxima(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getArcuatavelminima(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getArcuatair(), fuente));
                tableGeneric.addCell(new Paragraph("ARTERIA SEGMENTARIA", fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getSegmentariaelmaxima(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getSegmentariavelminima(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getSegmentariair(), fuente));
                tableGeneric.addCell(new Paragraph("RENAL", fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getRenalvelmaxima(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getRenalvelminima(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getRenalir(), fuente));
                tableGeneric.addCell(new Paragraph("INTERLOBULILLAR", fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getBulillarvelmaxima(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getBulillarvelminima(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getBulillarir(), fuente));
                tableGeneric.addCell(new Paragraph("AORTA ABDOMINAL", fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getAortavelmaxima(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getAortavelminima(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getArcuatair(), fuente));

                document.add(tableGeneric);
                table.addCell(new Paragraph(SihicApp.resultados.getDescripcion_corta(), fuente));
                table.addCell("\n");
                table.addCell("\n");
                table.addCell(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));
                document.add(table);

                break;
            case 6:

                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                table.addCell(new Paragraph("Utero: " + SihicApp.resultados.getUtero(), fuente));
                table.addCell(new Paragraph("Endometrio:   " + SihicApp.resultados.getPelv_endometrio(), fuente));
                table.addCell(new Paragraph("Ovario Derecho: " + SihicApp.resultados.getPelv_ovaderecho(), fuente));
                table.addCell(new Paragraph("Ovario Izquierdo: " + SihicApp.resultados.getPelv_ovaizquierdo(), fuente));
                document.add(new Paragraph("\n"));
                table.addCell(new Paragraph(SihicApp.resultados.getDescripcion(), fuente));
                table.addCell("\n");
                table.addCell("\n");
                table.addCell(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));
                document.add(table);
                break;
            case 7:

                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                table.addCell(new Paragraph("Fur: " + SihicApp.resultados.getUtero(), fuente));
                table.addCell(new Paragraph("Fpp: " + SihicApp.resultados.getUtero(), fuente));
                table.addCell(new Paragraph("Utero: " + SihicApp.resultados.getUtero(), fuente));
                table.addCell(new Paragraph(SihicApp.resultados.getDescripcion_corta(), fuente));
                document.add(new Paragraph("\n"));
                fuente = FontFactory.getFont("arial", 12, Font.BOLD);
                table.addCell(new Paragraph("Biometria Fetal", fuente));
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                table.addCell(new Paragraph("DBP: " + SihicApp.resultados.getDbp() + "   mm", fuente));
                table.addCell(new Paragraph("CC:  " + SihicApp.resultados.getCc() + "    mm", fuente));
                table.addCell(new Paragraph("CA:  " + SihicApp.resultados.getCa() + "    mm", fuente));
                table.addCell(new Paragraph("LF:  " + SihicApp.resultados.getLf() + "    mm", fuente));
                table.addCell(new Paragraph("Peso: " + SihicApp.resultados.getLf() + "   gr", fuente));
                table.addCell(new Paragraph("Placenta:          " + SihicApp.resultados.getPlacenta(), fuente));
                table.addCell(new Paragraph("Liquido Amniotico: " + SihicApp.resultados.getLiquidoamniotico(), fuente));
                table.addCell(new Paragraph("Cervix:            " + SihicApp.resultados.getCervix(), fuente));
                table.addCell("\n");
                table.addCell("\n");
                table.addCell(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));
                document.add(table);

                break;
            case 8:

                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                document.add(new Paragraph(SihicApp.resultados.getDescripcion_corta(), fuente));
                document.add(new Paragraph("\n"));
                tableGeneric.getDefaultCell().setColspan(2);
                tableGeneric.addCell(new Paragraph("", fuente));
                tableGeneric.getDefaultCell().setColspan(1);
                tableGeneric.addCell(new Paragraph("ÍNDICE DE RESISTENCIA\nDer      Izq", fuente));
                tableGeneric.addCell(new Paragraph("ÍNDICE DE PULSATILIDAD\nDer      Izq", fuente));
                tableGeneric.getDefaultCell().setColspan(2);
                tableGeneric.addCell(new Paragraph("ARTERIA UTERINA", fuente));
                tableGeneric.getDefaultCell().setColspan(1);
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getFet_artuterinaindresist(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getFet_artuterinaindpulsati(), fuente));
                tableGeneric.getDefaultCell().setColspan(2);
                tableGeneric.addCell(new Paragraph("ARTERIA UMBILICAL", fuente));
                tableGeneric.getDefaultCell().setColspan(1);
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getFet_artumbilicalindresist(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getFet_artumbilicalindpulsati(), fuente));
                tableGeneric.getDefaultCell().setColspan(2);
                tableGeneric.addCell(new Paragraph("ARTERIA CEREBRAL MEDIA", fuente));
                tableGeneric.getDefaultCell().setColspan(1);
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getFet_artcerebralindresist(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getFet_artcerebralindpulsati(), fuente));
                tableGeneric.getDefaultCell().setColspan(2);
                tableGeneric.addCell(new Paragraph("AORTA TORACICA FETAL", fuente));
                tableGeneric.getDefaultCell().setColspan(1);
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getFet_aortoracicaindresist(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getFet_aortoracicaindpulsati(), fuente));

                document.add(tableGeneric);
                table.addCell(new Paragraph(SihicApp.resultados.getDescripcion(), fuente));
                table.addCell("\n");
                table.addCell("\n");
                table.addCell(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));
                document.add(table);
                break;
            case 9:

                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                document.add(new Paragraph(SihicApp.resultados.getDescripcion(), fuente));
                document.add(new Paragraph("\n"));
                tableGeneric.addCell(new Paragraph("", fuente));
                tableGeneric.addCell(new Paragraph("VEL.MAXIMA\nDer       Izq", fuente));
                tableGeneric.addCell(new Paragraph("VEL. MININA\nDer      Izq", fuente));
                tableGeneric.addCell(new Paragraph("INDI RESIST\nDer      Izq", fuente));
                tableGeneric.addCell(new Paragraph("FEMORAL COMUN", fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_femoralcomun_velmax(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_femoralcomun_velmin(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_femoralcomun_indresist(), fuente));
                tableGeneric.addCell(new Paragraph("FEMORAL SUPERFICIAL", fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_femoralsuperf_velmax(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_femoralsuperf_velmin(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_femoralsuperf_indresist(), fuente));
                tableGeneric.addCell(new Paragraph("POPLITEA", fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_poplitea_velmax(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_poplitea_velmin(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_poplitea_indresist(), fuente));
                tableGeneric.addCell(new Paragraph("TIBIAL ANTERIOR", fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_tibialanterior_velmax(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_tibialanterior_velmin(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_tibialanterior_indresist(), fuente));
                tableGeneric.addCell(new Paragraph("TIBIAL POSTERIOR", fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_tibialposterior_velmax(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_tibialposterior_velmin(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_tibialposterior_indresist(), fuente));
                tableGeneric.addCell(new Paragraph("PEDIA", fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_pedia_velmax(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_pedia_velmin(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopmieminf_pedia_indresist(), fuente));

                document.add(tableGeneric);
                table.addCell("\n");
                table.addCell("\n");
                table.addCell(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));
                document.add(table);
                break;
            case 10:

                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(new Paragraph("Lado Derecho:", fuente));
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                table.addCell(new Paragraph("Sistema Venoso Profundo:" + SihicApp.resultados.getDopvemieminf_ldersvp(), fuente));
                table.addCell(new Paragraph("Sistema Venoso Superficial:" + SihicApp.resultados.getDopvemieminf_ldersvs(), fuente));
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(new Paragraph("Lado Izquierdo:", fuente));
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);

                table.addCell(new Paragraph("Sistema Venoso Profundo:" + SihicApp.resultados.getDopvemieminf_lizqsvp(), fuente));
                table.addCell(new Paragraph("Sistema Venoso Superficial:" + SihicApp.resultados.getDopvemieminf_lizqsvs(), fuente));

                fuente2 = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
                table.addCell("\n");
                table.addCell("\n");

                table.addCell(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));
                document.add(table);
                break;
            case 11:

                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(new Paragraph("Lado Derecho:", fuente));
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                table.addCell(new Paragraph("ARTERIA FEMORAL COMUN:      " + SihicApp.resultados.getDopartmieminf_lderafc(), fuente));
                table.addCell(new Paragraph("ARTERIA FEMORAL SUPERFICIAL:" + SihicApp.resultados.getDopartmieminf_lderafs(), fuente));
                table.addCell(new Paragraph("ARTERIA POPLITEA:           " + SihicApp.resultados.getDopartmieminf_lderapoplitea(), fuente));
                table.addCell(new Paragraph("ARTERIA TIBIAL ANTERIOR    :" + SihicApp.resultados.getDopartmieminf_lderata(), fuente));
                table.addCell(new Paragraph("ARTERIA TIABIAL POSTERIOR:  " + SihicApp.resultados.getDopartmieminf_lderatp(), fuente));

                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
                table.addCell(new Paragraph("Lado Izquierdo:", fuente));
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);

                table.addCell(new Paragraph("ARTERIA FEMORAL COMUN:" + SihicApp.resultados.getDopartmieminf_lizqafc(), fuente));
                table.addCell(new Paragraph("ARTERIA FEMORAL SUPERFICIAL::" + SihicApp.resultados.getDopartmieminf_lizqafs(), fuente));
                table.addCell(new Paragraph("ARTERIA POPLITEA:" + SihicApp.resultados.getDopartmieminf_lizqapoplitea(), fuente));
                table.addCell(new Paragraph("ARTERIA TIBIAL ANTERIOR:" + SihicApp.resultados.getDopartmieminf_lizqata(), fuente));
                table.addCell(new Paragraph("ARTERIA TIABIAL POSTERIOR:" + SihicApp.resultados.getDopartmieminf_lizqatp(), fuente));

                fuente2 = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
                table.addCell("\n");
                table.addCell("\n");

                table.addCell(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));
                document.add(table);

                break;
            case 12:

                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                document.add(new Paragraph(SihicApp.resultados.getDescripcion(), fuente));
                document.add(new Paragraph("\n"));
                tableGeneric.getDefaultCell().setColspan(2);
                tableGeneric.addCell(new Paragraph("MIEMBRO INFERIOR DERECHO", fuente));
                tableGeneric.addCell(new Paragraph("MIEMBRO INFERIOR IZQUIERDO", fuente));

                tableGeneric.getDefaultCell().setColspan(4);
                tableGeneric.addCell(new Paragraph("VENA FEMORAL COMUN:", fuente));
                tableGeneric.getDefaultCell().setColspan(2);
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopvemieminf_ldervenafemoralc(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopvemieminf_lizqvenafemoralc(), fuente));
                tableGeneric.getDefaultCell().setColspan(4);
                tableGeneric.addCell(new Paragraph("VENA FEMORAL SUPERFICIAL Y PROFUNDA:", fuente));
                tableGeneric.getDefaultCell().setColspan(2);
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopvemieminf_ldervenafemoralsp(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopvemieminf_lizqvenafemoralsp(), fuente));
                tableGeneric.getDefaultCell().setColspan(4);
                tableGeneric.addCell(new Paragraph("CAYADO DE LA SAFENA INTERNA:", fuente));
                tableGeneric.getDefaultCell().setColspan(2);
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopvemieminf_ldercayadosafenainterna(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopvemieminf_lizqcayadosafenainterna(), fuente));
                tableGeneric.getDefaultCell().setColspan(4);
                tableGeneric.addCell(new Paragraph("SAFENA INTERNA:", fuente));
                tableGeneric.getDefaultCell().setColspan(2);
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopvemieminf_ldersafena(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopvemieminf_lizqsafena(), fuente));
                tableGeneric.getDefaultCell().setColspan(4);
                tableGeneric.addCell(new Paragraph("VENA POPLITEA:", fuente));
                tableGeneric.getDefaultCell().setColspan(2);
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopvemieminf_ldervenapoplitea(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopvemieminf_lizqvenapoplitea(), fuente));
                tableGeneric.getDefaultCell().setColspan(4);
                tableGeneric.addCell(new Paragraph("CAYADO DE LA SAFENA EXTERNA:", fuente));
                tableGeneric.getDefaultCell().setColspan(2);
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopvemieminf_ldercayadosafenaexterna(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopvemieminf_lizqcayadosafenaexterna(), fuente));
                tableGeneric.getDefaultCell().setColspan(4);
                tableGeneric.addCell(new Paragraph("SAFENA EXTERNA Y PERFORANTES:", fuente));
                tableGeneric.getDefaultCell().setColspan(2);
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopvemieminf_ldercayadosafenaexternaperforantes(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopvemieminf_lizqcayadosafenaexternaperforantes(), fuente));
                tableGeneric.getDefaultCell().setColspan(4);
                tableGeneric.addCell(new Paragraph("TIBIAL POSTERIOR:", fuente));
                tableGeneric.getDefaultCell().setColspan(2);
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopvemieminf_ldertibialposterior(), fuente));
                tableGeneric.addCell(new Paragraph(sihic.SihicApp.resultados.getDopvemieminf_lizqtibialposterior(), fuente));

                document.add(tableGeneric);

                table.addCell(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));
                document.add(table);
                break;
            case 13:
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                table.addCell(new Paragraph("Procedimiento: " + SihicApp.resultados.getProcedimiento(), fuente));
                fuente2 = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
                table.addCell("\n");
                table.addCell("\n");
                table.addCell(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));
                document.add(table);

                break;
            case 14:
                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                table.addCell(new Paragraph(SihicApp.resultados.getDescripcion(), fuente));
                table.addCell(new Paragraph("Procedimiento: " + SihicApp.resultados.getProcedimiento(), fuente));
                fuente2 = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
                table.addCell("\n");
                table.addCell("\n");
                table.addCell(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));
                document.add(table);

                break;
            case 15:

                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                table.addCell(new Paragraph(SihicApp.resultados.getDescripcion(), fuente));
                table.addCell(new Paragraph("Procedimiento: " + SihicApp.resultados.getProcedimiento(), fuente));
                fuente2 = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
                table.addCell("\n");
                table.addCell("\n");
                table.addCell(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));
                document.add(table);
                break;
            case 16:

                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                table.addCell(new Paragraph(SihicApp.resultados.getDescripcion(), fuente));
                table.addCell(new Paragraph("Procedimiento: " + SihicApp.resultados.getProcedimiento(), fuente));
                fuente2 = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
                table.addCell("\n");
                table.addCell("\n");
                table.addCell(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));
                document.add(table);

                break;
            case 17:

                table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                table.addCell(new Paragraph("Utero: " + SihicApp.resultados.getUtero(), fuente));
                table.addCell(new Paragraph(SihicApp.resultados.getDescripcion(), fuente));
                fuente = FontFactory.getFont("arial", 12, Font.BOLD);
                table.addCell(new Paragraph("Biometria Fetal", fuente));
                fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
                table.addCell(new Paragraph(SihicApp.resultados.getDescripcion(), fuente));
                table.addCell(new Paragraph("DBP: " + SihicApp.resultados.getDbp() + "   mm", fuente));
                table.addCell(new Paragraph("CC:  " + SihicApp.resultados.getCc() + "    mm", fuente));
                table.addCell(new Paragraph("CA:  " + SihicApp.resultados.getCa() + "    mm", fuente));
                table.addCell(new Paragraph("LF:  " + SihicApp.resultados.getLf() + "    mm", fuente));
                table.addCell(new Paragraph("Peso: " + SihicApp.resultados.getLf() + "   gr", fuente));
                table.addCell(new Paragraph("Placenta:          " + SihicApp.resultados.getPlacenta(), fuente));
                table.addCell(new Paragraph("Liquido Amniotico: " + SihicApp.resultados.getLiquidoamniotico(), fuente));
                table.addCell(new Paragraph("Cervix:            " + SihicApp.resultados.getCervix(), fuente));
                document.add(table);
                document.add(new Paragraph("\n"));
                tableGeneric.addCell(new Paragraph("TONO FETAL", fuente));
                tableGeneric.addCell(new Paragraph("MOVIMIENTO FETAL", fuente));
                tableGeneric.addCell(new Paragraph("MOVIMIENTO RESPIRAT", fuente));
                tableGeneric.addCell(new Paragraph("LIQUIDO AMNIOTICO", fuente));
                tableGeneric.addCell(new Paragraph(SihicApp.resultados.getTonofetal(), fuente));
                tableGeneric.addCell(new Paragraph(SihicApp.resultados.getMovfetal(), fuente));
                tableGeneric.addCell(new Paragraph(SihicApp.resultados.getMovrespiratorio(), fuente));
                tableGeneric.addCell(new Paragraph(SihicApp.resultados.getLiquidoamniotico2(), fuente));
                document.add(tableGeneric);
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("\n"));
                document.add(new Paragraph("Opinión: " + SihicApp.resultados.getOpinion(), fuente));

                break;
        }

        // step 5
        document.close();

        File file = new File("/home/adminlinux/sihic/resultadoplantilla.pdf");

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

}
