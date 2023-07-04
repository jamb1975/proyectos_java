/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import modelo.FacturaItem;
import sihic.SihicApp;

/**
 *
 * @author adminlinux
 */
public class ImpresoraTermica {

    private void Dibuja_Linea(PrintWriter ps) {
        try {
            ps.println("----------------------------------------");
        } catch (Exception e) {
            System.out.print(e);
        }
    }

//para cortar el papel de mi ticketera
    private void cortar(PrintWriter ps) {

        try {
            char[] ESC_CUT_PAPER = new char[]{0x1B, 'm'};
            ps.write(ESC_CUT_PAPER);

        } catch (Exception e) {
            System.out.print(e);
        }
    }

    private void correr(int fin, PrintWriter pw) {
        try {
            int i = 0;
            for (i = 1; i <= fin; i++) {
                pw.println("");
            }
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    private void setFormato(double formato, PrintWriter pw) {
        try {
            char[] ESC_CUT_PAPER = new char[]{0x1B, '!', (char) formato};
            pw.write(ESC_CUT_PAPER);

        } catch (Exception e) {
            System.out.print(e);
        }
    }

// para el color de mi ticketera
    private void setRojo(PrintWriter pw) {
        try {
            char[] ESC_CUT_PAPER = new char[]{0x1B, 'r', 1};
            pw.write(ESC_CUT_PAPER);
        } catch (Exception e) {
            System.out.print(e);
        }
    }

    private void setNegro(PrintWriter pw) {
        try {
            char[] ESC_CUT_PAPER = new char[]{0x1B, 'r', 0};
            pw.write(ESC_CUT_PAPER);

        } catch (Exception e) {
            System.out.print(e);
        }
    }

    public void imprimir() throws IOException, PrintException {

        String texto = "Esto es lo que va a la impresora";

        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        DocPrintJob docPrintJob = printService.createPrintJob();
        Doc doc = new SimpleDoc(texto.getBytes(), flavor, null);
        /*try {
			//docPrintJob.print(doc, null);
		} catch (PrintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        FileWriter file = new FileWriter("COM1:");
        BufferedWriter buffer = new BufferedWriter(file);
        PrintWriter ps = new PrintWriter(buffer);

        setFormato(1, ps);
        ps.println(SihicApp.genUnidadesOrganizacion.getNombre());
        ps.println(SihicApp.genUnidadesOrganizacion.getDireccion());
        ps.println(SihicApp.genUnidadesOrganizacion.getNit());
        Dibuja_Linea(ps);
        ps.println("N° Factura    :" + SihicApp.s_factura.getNofacturacerosizquierda());
        ps.println("Fecha     :" + UtilDate.formatodiamesyear(SihicApp.s_factura.getFacturaDate()) + "  Hora : " + UtilDate.gethorafecha(SihicApp.s_factura.getFacturaDate()) + ":" + UtilDate.getminutofecha(SihicApp.s_factura.getFacturaDate()));
        ps.println("Caj   : 1" + " Ven : Jeni");
        Dibuja_Linea(ps);
        ps.println("Cliente     :" + SihicApp.s_factura.getGenPersonas().getNombres());
        Dibuja_Linea(ps);
        ps.println("Descripcion        " + "Cant" + "   " + "V/Total");
        Dibuja_Linea(ps);
        int lineas = 7;

        // aqui recorro mis productos y los imprimo
        for (FacturaItem fi : SihicApp.s_factura.getFacturaItems()) {
            ps.println(fi.getProducto().getNombre().length() > 19 ? fi.getProducto().getNombre().substring(0, 19) : fi.getProducto().getNombre() + "       " + fi.getQuantity() + "  " + fi.getValor_total());
        }
        Dibuja_Linea(ps);
        ps.println("TOTAL              : $./ " + SihicApp.s_factura.getTotalAmount());
        ps.println();

        ps.println("  IMPRESO POR INSSEND");
        ps.println("    CELULAR 3102030435-EMAIL VMGJAMB@GMAIL.COM");
        correr(10, ps);
        cortar(ps);
        ps.close();
        File file2 = new File("C:\\home\\adminlinux\\abaco\\impresora_ticket.txt");

        //   Desktop.getDesktop().print(file2);
    }

    public void imprimirarqueo() throws IOException, PrintException {

        String texto = "Esto es lo que va a la impresora";
        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        DocPrintJob docPrintJob = printService.createPrintJob();
        Doc doc = new SimpleDoc(texto.getBytes(), flavor, null);
        /*try {
			//docPrintJob.print(doc, null);
		} catch (PrintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        FileWriter file = new FileWriter("COM1:");
        BufferedWriter buffer = new BufferedWriter(file);
        PrintWriter ps = new PrintWriter(buffer);

        setFormato(1, ps);
        ps.println(SihicApp.genUnidadesOrganizacion.getNombre());
        /*     ps.println(SihicApp.genUnidadesOrganizacion.getDir());
                ps.println(SihicApp.genUnidadesOrganizacion.getNit());
                Dibuja_Linea(ps);
                ps.println("N° Arqueo    :" +SihicApp.s_arqueo.getId());
                ps.println("Fecha     :" + UtilDate.formatodiamesyear(SihicApp.s_arqueo.getFecha()) + "  Hora : " + UtilDate.gethorafecha(SihicApp.s_arqueo.getFecha())+ ":" + UtilDate.getminutofecha(SihicApp.s_arqueo.getFecha()));
                 Dibuja_Linea(ps);
                ps.println("Total Contado: $" + SihicApp.s_arqueo.getValor_contado().toString());
                ps.println("Total Crédito: $" + SihicApp.s_arqueo.getValor_credito().toString());
                ps.println("Inicial Caja : $" + SihicApp.s_arqueo.getValor_caja().toString());
                Dibuja_Linea(ps);
                ps.println("Total:__________$" + SihicApp.s_arqueo.getValor_contado().add(SihicApp.s_arqueo.getValor_caja()));
                Dibuja_Linea(ps);
                ps.println("Entrega: " +SihicApp.s_arqueo.getEntrega()+" Recibe: "+SihicApp.s_arqueo.getRecibe());
                Dibuja_Linea(ps);
                ps.println();
                ps.println("  IMPRESO POR INSSEND");
                ps.println("    CELULAR 3102030435-EMAIL VMGJAMB@GMAIL.COM");
                correr(10, ps);
                cortar(ps);
                ps.close();  
                   File file2=new File("C:\\home\\adminlinux\\abaco\\ticket_arqueo.txt");
         */
        //   Desktop.getDesktop().print(file2);
    }

    public void imprimirreciboingreso() throws IOException, PrintException {

        FileWriter file = new FileWriter("COM1:");
        BufferedWriter buffer = new BufferedWriter(file);
        PrintWriter ps = new PrintWriter(buffer);

        setFormato(1, ps);
        ps.println(SihicApp.genUnidadesOrganizacion.getNombre());
        ps.println(SihicApp.genUnidadesOrganizacion.getDireccion());
        ps.println(SihicApp.genUnidadesOrganizacion.getNit());
        Dibuja_Linea(ps);
        ps.println("N° Recibo Ingreso   :" + SihicApp.conComprobanteIngreso.getNocomprobantecerosizquierda());
        ps.println("Fecha     :" + UtilDate.formatodiamesyear(SihicApp.conComprobanteIngreso.getFechaelaboracion()) + "  Hora : " + UtilDate.gethorafecha(SihicApp.conComprobanteIngreso.getFechaelaboracion()) + ":" + UtilDate.getminutofecha(SihicApp.conComprobanteIngreso.getFechaelaboracion()));
        Dibuja_Linea(ps);
        ps.println("Recibimos De: CC " + SihicApp.conComprobanteIngreso.getFactura().getGenPersonas().getDocumento() + " Nombre: " + SihicApp.conComprobanteIngreso.getFactura().getGenPersonas().getNombres());
        ps.println("La Suma de: " + NumberToLetterConverter.convertNumberToLetter(SihicApp.conComprobanteIngreso.getValor().doubleValue()) + "M/c: " + SihicApp.conComprobanteIngreso.getValor());
        ps.println("Por Concepto De: " + SihicApp.conComprobanteIngreso.getConcepto());

        Dibuja_Linea(ps);
        ps.println("Elabora:________________ Aprueba:__________________");
        Dibuja_Linea(ps);
        ps.println();
        ps.println("  IMPRESO POR INSSEND");
        ps.println("    CELULAR 3102030435-EMAIL VMGJAMB@GMAIL.COM");
        correr(10, ps);
        cortar(ps);
        ps.close();
        File file2 = new File("C:\\home\\adminlinux\\abaco\\ticket_ingreso.txt");

        //   Desktop.getDesktop().print(file2);
    }
}
