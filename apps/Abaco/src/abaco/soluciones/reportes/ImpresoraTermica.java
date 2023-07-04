/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.soluciones.reportes;

import abaco.AbacoApp;
import abaco.util.NumberToLetterConverter;
import abaco.util.UtilDate;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.InetAddress;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import model.Factura;
import model.FacturaItem;


/**
 *
 * @author adminlinux
 */
public class ImpresoraTermica {
 private void Dibuja_Linea(PrintWriter ps) {
        try {
            ps.println("------------------------------------------------");
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
public void abrircajonmodedero() throws IOException
{
    //27,112,0,100,250 secuencia apertura cajon monedero 0x07
    try
    {
    InetAddress addr = InetAddress.getLocalHost();
	        String hostname = addr.getHostName();
                System.out.println("nombre host->"+hostname);
                FileWriter file = new FileWriter("//"+hostname+"/POS80");
     file.write(27);
     file.write(112);
     file.write(0);
     file.write(100);
     file.write(250);
    // file.write(0);
     file.close();
    }catch(Exception e)
    {
        
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
public  void imprimir() throws IOException, PrintException
{
          
  
 
 /*PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
 
                 
		      FileInputStream inputStream = null;
        try {
            inputStream = new FileInputStream("/home/adminlinux/abaco/enterprise.png");
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        if (inputStream == null) {
            return;
        }
        
            DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
            
		DocPrintJob docPrintJob = printService.createPrintJob();
		Doc doc = new SimpleDoc(inputStream , flavor, null);
		try {
			docPrintJob.print(doc, null);
                     printService=null;
                     docPrintJob=null;
                     flavor=null;
		} catch (PrintException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
             //  FileWriter file = new FileWriter("//home/adminlinux/factura7.txt");
    try
    {
    	         InetAddress addr = InetAddress.getLocalHost();
	        String hostname = addr.getHostName();
                System.out.println("nombre host->"+hostname);
                FileWriter file = new FileWriter("//"+hostname+"/POS80");
                BufferedWriter buffer = new BufferedWriter(file);
                PrintWriter ps = new PrintWriter(buffer);
                 
      
                setNegro(ps);
                setFormato(2, ps);
                ps.println("                 "+AbacoApp.s_organizacion.getNombre());
                ps.println("           NO RESPONSABLES DEL IVA");
                ps.println("                   Nit: "+AbacoApp.s_organizacion.getNit());
                ps.println("                "+AbacoApp.s_organizacion.getDir());
                 ps.println("               "+AbacoApp.s_organizacion.getGenMunicipios()!=null?AbacoApp.s_organizacion.getGenMunicipios().getNombre()+"-"+AbacoApp.s_organizacion.getGenMunicipios().getGenDepartamentos().getNombre():"");
                Dibuja_Linea(ps);
                setFormato(2, ps);
                String fp="";
                if(AbacoApp.s_factura.getCredito()==null)
                {
                 fp="CONTADO";
                }  
                else
                {
                  if(AbacoApp.s_factura.getCredito()==true)
                {
                 fp="CREDITO";
                }  
                  else
                  {
                       fp="CONTADO";
                  }
                }
                ps.println("No. Doc Equivalente: " +AbacoApp.s_factura.getNofacturacerosizquierda()+" Forma de Pago: "+ fp);
                ps.println("Fecha     :" + UtilDate.formatodiamesyear(AbacoApp.s_factura.getFacturaDate()) + "  Hora : " + UtilDate.gethorafecha(AbacoApp.s_factura.getFacturaDate())+ ":" + UtilDate.getminutofecha(AbacoApp.s_factura.getFacturaDate()));
                ps.println("Caj: 1" + " Ven :"+AbacoApp.s_usuarios.getGenPersonas().getNombre());
                Dibuja_Linea(ps);
                ps.println("Nit o CC:"+(AbacoApp.s_factura.getCustomer()==null?"Ventas Mostrador":AbacoApp.s_factura.getCustomer().getNo_ident())+" Cliente: " + (AbacoApp.s_factura.getCustomer()==null?"Ventas Mostrador":AbacoApp.s_factura.getCustomer().getNombre()));
                Dibuja_Linea(ps);
                ps.println("Descripcion            " +    "Cant" + "   " + "   V/Total");
                Dibuja_Linea(ps);
                
                       
                // aqui recorro mis productos y los imprimo
                for(FacturaItem fi: AbacoApp.s_factura.getFacturaItems())
                {
                         String nombre=fi.getProduct().getNombre();
                         if(nombre.length()>=19)
                         {
                             nombre=nombre.substring(0, 19);
                         }
                         else
                         {
                             int espsblanco=19-nombre.length();
                             for(int i=0;i<espsblanco;i++)
                             {
                               nombre=nombre+" ";  
                             }
                             
                         }
               
                   ps.println(nombre + "    "+fi.getQuantity()+"       $"+Double.toString(fi.getValor_total().doubleValue()));  
                }
                Dibuja_Linea(ps);
                ps.println("TOTAL                          : $" + AbacoApp.s_factura.getTotalAmount() );
                ps.println();
                ps.println("EFECTIVO: "+AbacoApp.s_factura.getEfectivo().toString() );
                ps.println("DEVOLUCION: "+AbacoApp.s_factura.getTotalAmount().subtract(AbacoApp.s_factura.getEfectivo()).toString());
                ps.println("Este Documento se asemeja a una Letra de Cambio");
                ps.println(AbacoApp.s_organizacion.getNombre());
                ps.println("     CELULAR "+AbacoApp.s_organizacion.getCelular());
                ps.println("     EMAIL "+AbacoApp.s_organizacion.getEmail());
                 ps.println("                IMPRESO POR INSSEND");
                ps.println("     CELULAR 3102030435-EMAIL VMGJAMB@GMAIL.COM");
              
                correr(5, ps);
                cortar(ps);
                
        System.out.println("Vueltas ->"+AbacoApp.s_factura.getTotalAmount().subtract(AbacoApp.s_factura.getEfectivo()).toString()+AbacoApp.s_usuarios.getGenPersonas().getNombre());
      
                ps.close();  
    }catch(Exception e)
    {
       e.printStackTrace();
    }
}
public  void imprimirarqueo() throws IOException, PrintException
{
    
                String texto = "Esto es lo que va a la impresora";
 		PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
 		DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
		DocPrintJob docPrintJob = printService.createPrintJob();
		Doc doc = new SimpleDoc(texto.getBytes(), flavor, null);
		
               InetAddress addr = InetAddress.getLocalHost();
	        String hostname = addr.getHostName();
                FileWriter file = new FileWriter("//"+hostname+"/POS80");
                BufferedWriter buffer = new BufferedWriter(file);
                PrintWriter ps = new PrintWriter(buffer);
                setNegro(ps);
                setFormato(2, ps);
                ps.println("            "+AbacoApp.s_organizacion.getNombre());
                ps.println("                   Nit: "+AbacoApp.s_organizacion.getNit());
                ps.println("             Dir:"+AbacoApp.s_organizacion.getDir());
                Dibuja_Linea(ps);
                setFormato(2, ps);
                ps.println("N° Arqueo    :" +AbacoApp.s_arqueo.getId());
                ps.println("Fecha     :" + UtilDate.formatodiamesyear(AbacoApp.s_arqueo.getFecha()) + "  Hora : " + UtilDate.gethorafecha(AbacoApp.s_arqueo.getFecha())+ ":" + UtilDate.getminutofecha(AbacoApp.s_arqueo.getFecha()));
                 Dibuja_Linea(ps);
                ps.println("Total Contado: $" + AbacoApp.s_arqueo.getValor_contado().toString());
                ps.println("Total Crédito: $" + AbacoApp.s_arqueo.getValor_credito().toString());
                ps.println("Inicial Caja : $" + AbacoApp.s_arqueo.getValor_caja().toString());
                Dibuja_Linea(ps);
                ps.println("Total:__________$" + AbacoApp.s_arqueo.getValor_contado().add(AbacoApp.s_arqueo.getValor_caja()));
                Dibuja_Linea(ps);
                ps.println("Entrega: " +AbacoApp.s_arqueo.getEntrega()+" Recibe: "+AbacoApp.s_arqueo.getRecibe());
                Dibuja_Linea(ps);
                ps.println("Esta factura se asemeja a una letra de cambio");
               ps.println("     CELULAR "+AbacoApp.s_organizacion.getCelular());
                ps.println("     EMAIL "+AbacoApp.s_organizacion.getEmail());
             
                ps.println("                IMPRESO POR INSSEND");
                ps.println("     CELULAR 3102030435-EMAIL VMGJAMB@GMAIL.COM");
                
               correr(5, ps);
                cortar(ps);
                ps.close();  
                   
}

public  void imprimirreciboingreso() throws IOException, PrintException
{
    
                
              InetAddress addr = InetAddress.getLocalHost();
	        String hostname = addr.getHostName();
                FileWriter file = new FileWriter("//"+hostname+"/POS80");
                BufferedWriter buffer = new BufferedWriter(file);
                PrintWriter ps = new PrintWriter(buffer);
                setFormato(1, ps);
                setFormato(2, ps);
                ps.println("                   "+AbacoApp.s_organizacion.getNombre());
                ps.println("                   Nit: "+AbacoApp.s_organizacion.getNit().replace("ó", "&oacute;"));
                ps.println("                   Dir:"+AbacoApp.s_organizacion.getDir());
               Dibuja_Linea(ps);
                ps.println("N° Recibo Ingreso   :" +AbacoApp.s_conComprobanteIngreso.getNocomprobantecerosizquierda());
                ps.println("Fecha     :" + UtilDate.formatodiamesyear(AbacoApp.s_conComprobanteIngreso.getFechaelaboracion()) + "  Hora : " + UtilDate.gethorafecha(AbacoApp.s_conComprobanteIngreso.getFechaelaboracion())+ ":" + UtilDate.getminutofecha(AbacoApp.s_conComprobanteIngreso.getFechaelaboracion()));
                 Dibuja_Linea(ps);
                try{ 
                ps.println("Recibimos De: CC " + AbacoApp.s_conComprobanteIngreso.getFactura().getCustomer().getNo_ident()+" Nombre: "+AbacoApp.s_conComprobanteIngreso.getFactura().getCustomer().getNombre());
                }catch(Exception e)
                {
                   ps.println("Recibimos De CC: " + AbacoApp.s_conComprobanteIngreso.getUsuarioModificador()+" Nombre: " +AbacoApp.s_conComprobanteIngreso.getObservaciones());
                 
                }
                ps.println("La Suma de: " + NumberToLetterConverter.convertNumberToLetter(AbacoApp.s_conComprobanteIngreso.getValor().doubleValue())+"M/c: "+AbacoApp.s_conComprobanteIngreso.getValor());
                 ps.println("Por Concepto De: " + AbacoApp.s_conComprobanteIngreso.getConcepto());
                
                Dibuja_Linea(ps);
                ps.println("Elabora:__________________     Aprueba:__________________" );
                Dibuja_Linea(ps);
                 ps.println();
                ps.println("                IMPRESO POR INSSEND");
                ps.println("     CELULAR 3102030435-EMAIL VMGJAMB@GMAIL.COM");
                correr(5, ps);
                cortar(ps);
                ps.close();  
             
}
public  void imprimirreciboegreso() throws IOException, PrintException
{
    
                
              InetAddress addr = InetAddress.getLocalHost();
	        String hostname = addr.getHostName();
                FileWriter file = new FileWriter("//"+hostname+"/POS80");
                BufferedWriter buffer = new BufferedWriter(file);
                PrintWriter ps = new PrintWriter(buffer);
                setFormato(1, ps);
                setFormato(2, ps);
                ps.println("                   "+AbacoApp.s_organizacion.getNombre());
                ps.println("                   Nit: "+AbacoApp.s_organizacion.getNit().replace("ó", "&oacute;"));
                ps.println("                   Dir:"+AbacoApp.s_organizacion.getDir());
               Dibuja_Linea(ps);
                ps.println("N° Recibo Egreso   :" +AbacoApp.conComprobanteEgreso.getNocomprobantecerosizquierda());
                ps.println("Fecha     :" + UtilDate.formatodiamesyear(AbacoApp.conComprobanteEgreso.getFechaelaboracion()) + "  Hora : " + UtilDate.gethorafecha(AbacoApp.conComprobanteEgreso.getFechaelaboracion())+ ":" + UtilDate.getminutofecha(AbacoApp.conComprobanteEgreso.getFechaelaboracion()));
                 Dibuja_Linea(ps);
                try{ 
                ps.println("Pagamos A: CC " + AbacoApp.conComprobanteEgreso.getFacturaproveedores().getProveedores().getNo_ident()+" Nombre: "+AbacoApp.conComprobanteEgreso.getFacturaproveedores().getProveedores().getNombre());
                }catch(Exception e)
                {
                   ps.println("Pagamos A CC: " + AbacoApp.conComprobanteEgreso.getUsuarioModificador()+" Nombre: " +AbacoApp.conComprobanteEgreso.getConcepto());
                 
                }
                ps.println("La Suma de: " + NumberToLetterConverter.convertNumberToLetter(AbacoApp.s_conComprobanteIngreso.getValor().doubleValue())+"M/c: "+AbacoApp.s_conComprobanteIngreso.getValor());
                 ps.println("Por Concepto De: " + AbacoApp.s_conComprobanteIngreso.getConcepto());
                
                Dibuja_Linea(ps);
                ps.println("Elabora:__________________     Proveedor:__________________" );
                Dibuja_Linea(ps);
                 ps.println();
                ps.println("     CELULAR "+AbacoApp.s_organizacion.getCelular());
                ps.println("     EMAIL "+AbacoApp.s_organizacion.getEmail());
             
                ps.println("                IMPRESO POR INSSEND");
                ps.println("     CELULAR 3102030435-EMAIL INGENIEROJAVIERMURCIA@GMAIL.COM");
                correr(5, ps);
                cortar(ps);
                ps.close();  
             
}
public  void imprimirabonogeneral() throws IOException, PrintException
{
           try
           {
                
                  InetAddress addr = InetAddress.getLocalHost();
	        String hostname = addr.getHostName();
                FileWriter file = new FileWriter("//"+hostname+"/POS80");
                BufferedWriter buffer = new BufferedWriter(file);
                PrintWriter ps = new PrintWriter(buffer);
               
                setFormato(1, ps);
                setFormato(2, ps);
                ps.println("                   "+AbacoApp.s_organizacion.getNombre());
                ps.println("                   Nit: "+AbacoApp.s_organizacion.getNit().replace("ó", "&oacute;"));
                ps.println("                   Dir:"+AbacoApp.s_organizacion.getDir());
                ps.println("Fecha     :" + UtilDate.formatodiamesyear(AbacoApp.s_conComprobanteIngreso.getFechaelaboracion()) + "  Hora : " + UtilDate.gethorafecha(AbacoApp.s_conComprobanteIngreso.getFechaelaboracion())+ ":" + UtilDate.getminutofecha(AbacoApp.s_conComprobanteIngreso.getFechaelaboracion()));
              
                Dibuja_Linea(ps);
                BigDecimal totalabono=BigDecimal.ZERO;
                BigDecimal totalsaldoadeber=BigDecimal.ZERO;
                for(Factura f: AbacoApp.li_facturas)
                {
                 totalabono=totalabono.add(f.getComprobanteingresos().get(f.getComprobanteingresos().size()-1).getValor());
                 totalsaldoadeber=totalsaldoadeber.add(f.getTotalAmount().subtract(f.getValor_abonos()));
                 ps.println("Factura No:" +f.getNofacturacerosizquierda()+"Recibo Ingreso:" +f.getComprobanteingresos().get(f.getComprobanteingresos().size()-1).getNocomprobantecerosizquierda()+" Saldo deber:"+ (f.getTotalAmount().compareTo(f.getValor_abonos())==0?"$0":"$"+(f.getTotalAmount().subtract(f.getValor_abonos()).toString())));
                }
                Dibuja_Linea(ps);
                try{ 
                ps.println("Recibimos De: CC " + AbacoApp.s_conComprobanteIngreso.getFactura().getCustomer().getNo_ident()+" Nombre: "+AbacoApp.s_conComprobanteIngreso.getFactura().getCustomer().getNombre());
                }catch(Exception e)
                {
                   ps.println("Recibimos De CC: " + AbacoApp.s_conComprobanteIngreso.getUsuarioModificador()+" Nombre: " +AbacoApp.s_conComprobanteIngreso.getObservaciones());
                 
                }
                ps.println("La Suma de: " + NumberToLetterConverter.convertNumberToLetter(totalabono.doubleValue())+"M/c: "+totalabono);
                ps.println("Por Concepto De: Abono General a Facturas");
                ps.println("Queda Pendiente un saldo de: " + totalsaldoadeber.toString());
               
                Dibuja_Linea(ps);
                ps.println("Elabora:__________________Aprueba:________________" );
                Dibuja_Linea(ps);
                 ps.println();
                  ps.println("     CELULAR "+AbacoApp.s_organizacion.getCelular());
                ps.println("     EMAIL "+AbacoApp.s_organizacion.getEmail());
               
                ps.println("                IMPRESO POR INSSEND");
                ps.println("     CELULAR 3102030435-EMAIL VMGJAMB@GMAIL.COM");
                correr(5, ps);
                cortar(ps);
                ps.close();  
           }catch(Exception e)
               
           {
               
           }
           //1   File file2=new File("C:\\home\\adminlinux\\abaco\\ticket_ingreso.txt");
                   
            //   Desktop.getDesktop().print(file2);
}
public  void prueba() throws IOException, PrintException
{
    
                
              
                BigDecimal totalabono=BigDecimal.ZERO;
                BigDecimal totalsaldoadeber=BigDecimal.ZERO;
                for(Factura f: AbacoApp.li_facturas)
                {
                 totalabono=totalabono.add(f.getComprobanteingresos().get(f.getComprobanteingresos().size()-1).getValor());
                 totalsaldoadeber=totalsaldoadeber.add(f.getTotalAmount().subtract(f.getValor_abonos()));
                }
               
              
             System.out.println("total abono-->"+totalabono);
             System.out.println("total saldo a deber-->"+totalsaldoadeber);
             //1   File file2=new File("C:\\home\\adminlinux\\abaco\\ticket_ingreso.txt");
                   
            //   Desktop.getDesktop().print(file2);
}
}

