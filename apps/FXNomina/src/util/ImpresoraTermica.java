/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

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

import fxnomina.FXNomina;

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
        ps.println(FXNomina.genUnidadesOrganizacion.getNombre());
        ps.println(FXNomina.genUnidadesOrganizacion.getDireccion());
        ps.println(FXNomina.genUnidadesOrganizacion.getNit());
        Dibuja_Linea(ps);
        ps.println("N° Factura    :" );
        ps.println("Fecha     : Hora : " );
        ps.println("Caj   : 1" + " Ven : Jeni");
        Dibuja_Linea(ps);
        ps.println("Cliente     :" );
        Dibuja_Linea(ps);
        ps.println("Descripcion        " + "Cant" + "   " + "V/Total");
        Dibuja_Linea(ps);
        int lineas = 7;

        // aqui recorro mis productos y los imprimo
     /*   for (FacturaItem fi : SihicApp.s_factura.getFacturaItems()) {
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
        File file2 = new File("C:\\home\\adminlinux\\abaco\\impresora_ticket.txt");*/

        //   Desktop.getDesktop().print(file2);
    }

    
}
