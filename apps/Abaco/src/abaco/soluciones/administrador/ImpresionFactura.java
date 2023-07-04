/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.soluciones.administrador;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import model.Factura;
import model.FacturaItem;
import abaco.AbacoApp;
import com.itextpdf.text.BadElementException;

/**
 *
 * @author karolyani
 */
public class ImpresionFactura {
    private Factura factura;
    public static final String DEFAULT_URL = "<html><body><object data=\"pdfFiles/interfaces.pdf\" type=\"application/pdf\">\n" +
                                             "<embed src='file:///home/adminlinux/abaco/factura.pdf' type='application/pdf\'>&nbsp; </embed>\n" +
                                             "alt :<a href='file:///home/adminlinux/abaco/factura.pdf'>Ver</a>\n" +
                                             "</object></body></html>";
                                             //"<body><h1>Hello from WebView</h1></body></html>";
      
    public  ImpresionFactura(Factura factura)
    {
        this.factura=factura;
    }
    
  public void fprintpdf(int opc) 
   {
        
        Document document = new Document();
         Font fuente= new Font();
      try {
           document=new Document(PageSize.A4,20,20,20,20);
           PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/abaco/factura.pdf"));
           document.open();
           
           PdfPTable tableMaster = new PdfPTable(1);
           //PdfPTable table = new PdfPTable(new float[] { 5, 5});
           for(int i=0;i<2;i++)
            { 
           
           PdfPTable table = new PdfPTable(new float[] {4,7});
           PdfPTable tableImagenTexto = new PdfPTable(new float[] {3,7});
           PdfPTable tableCliente = new PdfPTable(new float[] {6,7});
           PdfPTable tableFacturaItems = new PdfPTable(new float[] { 2,5,2,2,2});
           Image imagen;
            tableMaster.getDefaultCell().setBorderWidth(0);
           if(AbacoApp.getDatosempresa().getLogo()!=null)
           {
             imagen = Image.getInstance(AbacoApp.getDatosempresa().getLogo());
            
            tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
             tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
             tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
             tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
             tableImagenTexto.getDefaultCell().setFixedHeight(40f);
              
             tableImagenTexto.addCell(imagen);      
           }
          tableImagenTexto.getDefaultCell().setFixedHeight(0f);
             tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
             tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
             tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
             tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
             tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            
              fuente=FontFactory.getFont("Times-Roman", 11, Font.BOLD);
             tableImagenTexto.addCell(new Paragraph(AbacoApp.getDatosempresa().getNombre(),fuente));
               fuente=FontFactory.getFont("Times-Roman", 8, Font.NORMAL);
             tableImagenTexto.getDefaultCell().setColspan(2);
             tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
             tableImagenTexto.addCell(new Paragraph("Nit.: "+AbacoApp.getDatosempresa().getNit()+" Regimen Simplificado",fuente));
             table.getDefaultCell().setPadding(10);   
             table.setWidthPercentage(100f);
             table.getDefaultCell().setBorderWidthRight(0);
             table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
             table.addCell(tableImagenTexto);
                                                             
           
            //tabla datos de cliente
            table.getDefaultCell().setPadding(7);   
            tableCliente.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
           tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
           tableCliente.getDefaultCell().setColspan(4);
           table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
         
             fuente=FontFactory.getFont("Times-Roman", 10, Font.BOLD);
           tableCliente.getDefaultCell().setBorder(0);
           tableCliente.addCell(new Paragraph("Factura de Venta"+"\nNo. "+factura.getLastNumberNoFactura().getId(),fuente));
           tableCliente.getDefaultCell().setColspan(1);
           tableCliente.getDefaultCell().setBorderWidthTop((float)0.25);
           tableCliente.getDefaultCell().setBorderWidthLeft((float)0.25);
           tableCliente.getDefaultCell().setBorderWidthRight(0);
           tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
           fuente=FontFactory.getFont("Times-Roman", 9, Font.NORMAL);
           tableCliente.addCell(new Paragraph("Fecha: ",fuente));
           tableCliente.getDefaultCell().setBorderWidthTop((float)0.25);
           tableCliente.getDefaultCell().setBorderWidthLeft(0);
           tableCliente.getDefaultCell().setBorderWidthRight((float)0.25);
           tableCliente.addCell(new Paragraph (DateFormat.getDateInstance().format(factura.getFacturaDate()),fuente));
          
           
           tableCliente.getDefaultCell().setBorderWidthTop(0);
           tableCliente.getDefaultCell().setBorderWidthLeft((float)0.25);
           tableCliente.getDefaultCell().setBorderWidthRight(0);
           tableCliente.addCell(new Paragraph ("Cliente: ",fuente));
           tableCliente.getDefaultCell().setBorderWidthTop(0);
           tableCliente.getDefaultCell().setBorderWidthLeft(0);
           tableCliente.getDefaultCell().setBorderWidthRight((float)0.25);
           tableCliente.addCell(new Paragraph (factura.getCustomer().getNombre(),fuente));
             
            tableCliente.getDefaultCell().setBorderWidthTop(0);
           tableCliente.getDefaultCell().setBorderWidthLeft((float)0.25);
           tableCliente.getDefaultCell().setBorderWidthRight(0);
           tableCliente.addCell(new Paragraph ("Nit o Cc: ",fuente));
           tableCliente.getDefaultCell().setBorderWidthTop(0);
           tableCliente.getDefaultCell().setBorderWidthLeft(0);
           tableCliente.getDefaultCell().setBorderWidthRight((float)0.25);
           tableCliente.addCell(new Paragraph (factura.getCustomer().getNo_ident(),fuente));
            
           
           table.getDefaultCell().setBorderWidthRight((float)0.25);
          
            tableCliente.getDefaultCell().setBorderWidthBottom((float)0.25);
           tableCliente.getDefaultCell().setBorderWidthLeft((float)0.25);
           tableCliente.getDefaultCell().setBorderWidthRight(0);
           
           tableCliente.addCell(new Paragraph ("Forma de pago: ",fuente));
           tableCliente.getDefaultCell().setBorderWidthBottom((float)0.25);
           tableCliente.getDefaultCell().setBorderWidthLeft(0);
           tableCliente.getDefaultCell().setBorderWidthRight((float)0.25);
           tableCliente.addCell(new Paragraph (factura.getCredito()?"Crédito":"Contado",fuente));
         
           table.getDefaultCell().setBorderWidthRight((float)0.25);
           table.addCell(tableCliente);
           table.getDefaultCell().setColspan(2);
           tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
           table.getDefaultCell().setPadding(0);   
           tableFacturaItems.addCell(new Paragraph ("No. Item",fuente));
           tableFacturaItems.addCell(new Paragraph ("Producto",fuente));
           tableFacturaItems.addCell(new Paragraph ("Cantidad",fuente));
           tableFacturaItems.addCell(new Paragraph ("Valor/U",fuente));
           tableFacturaItems.addCell(new Paragraph ("Valor Total",fuente));
            tableFacturaItems.getDefaultCell().setBorder(0);
            //tableFacturaItems.getDefaultCell().setBorderWidthTop(1);
             fuente= new Font();
             
           fuente.setSize(9);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
           table.getDefaultCell().setPadding(5);
           table.addCell(new Paragraph("Calle 13 No. 20-70 Barrio Centro Tel.:311 221 9615 - 312 346 1779  Cumaral - Meta",fuente));

           for(FacturaItem fi:factura.getFacturaItems())
           {   tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
               tableFacturaItems.getDefaultCell().setBorderWidthLeft((float)0.25);
               tableFacturaItems.getDefaultCell().setBorderWidthRight((float)0.25);
               tableFacturaItems.getDefaultCell().setBorderWidthBottom((float)0.25);
               tableFacturaItems.addCell(new Paragraph (String.valueOf(fi.getPosition()),fuente));
               tableFacturaItems.getDefaultCell().setBorderWidthLeft(0);
               tableFacturaItems.getDefaultCell().setBorderWidthRight((float)0.25);
               tableFacturaItems.addCell(new Paragraph (String.valueOf(fi.getProduct().getNombre()),fuente));
               tableFacturaItems.addCell(new Paragraph (String.valueOf(fi.getQuantity()),fuente));
               tableFacturaItems.addCell(new Paragraph (String.valueOf(fi.getProduct().getPrecio()),fuente));
               tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
               tableFacturaItems.addCell(new Paragraph (String.valueOf(fi.getValor_total()),fuente));
               
               
           }
            tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
           if(!factura.isShowiva())
          {    
           tableFacturaItems.getDefaultCell().setBorderWidthTop(0);
           tableFacturaItems.getDefaultCell().setBorderWidthTop(0);
           tableFacturaItems.getDefaultCell().setBorderWidthLeft((float)0.25);
           tableFacturaItems.getDefaultCell().setBorderWidthRight(0);
           tableFacturaItems.addCell("");
           tableFacturaItems.getDefaultCell().setBorderWidthLeft(0);
           tableFacturaItems.addCell("");
           tableFacturaItems.addCell("");
           tableFacturaItems.addCell("Subtotal: ");
           tableFacturaItems.getDefaultCell().setBorderWidthLeft((float)0.25);
           tableFacturaItems.getDefaultCell().setBorderWidthRight((float)0.25);
           tableFacturaItems.addCell(factura.getNetAmount().toString());
        
           tableFacturaItems.getDefaultCell().setBorderWidthTop(0);
           tableFacturaItems.getDefaultCell().setBorderWidthTop(0);
          tableFacturaItems.getDefaultCell().setBorderWidthLeft((float)0.25);
           tableFacturaItems.getDefaultCell().setBorderWidthRight(0);
           tableFacturaItems.addCell("");
           tableFacturaItems.getDefaultCell().setBorderWidthLeft(0);
           tableFacturaItems.addCell("");
           tableFacturaItems.addCell("");
           tableFacturaItems.addCell("Iva: ");
           tableFacturaItems.getDefaultCell().setBorderWidthLeft((float)0.25);
           tableFacturaItems.getDefaultCell().setBorderWidthRight((float)0.25);
           tableFacturaItems.addCell(factura.getIva().toString());
          tableFacturaItems.getDefaultCell().setBorderWidthLeft((float)0.25);
           tableFacturaItems.getDefaultCell().setBorderWidthRight(0);
           tableFacturaItems.addCell("");
           tableFacturaItems.getDefaultCell().setBorderWidthLeft(0);
           tableFacturaItems.addCell("");
           tableFacturaItems.addCell("");
           tableFacturaItems.addCell("Descuento: ");
          
           tableFacturaItems.getDefaultCell().setBorderWidthLeft((float)0.25);
           tableFacturaItems.getDefaultCell().setBorderWidthRight((float)0.25);
           tableFacturaItems.addCell(factura.getDescuento().toString());
          }
           tableFacturaItems.getDefaultCell().setBorderWidthBottom((float)0.25);
           tableFacturaItems.getDefaultCell().setBorderWidthLeft((float)0.25);
           tableFacturaItems.getDefaultCell().setBorderWidthRight(0);
           tableFacturaItems.addCell("");
           tableFacturaItems.getDefaultCell().setBorderWidthLeft(0);
           tableFacturaItems.addCell("");
           tableFacturaItems.addCell("");
           
           tableFacturaItems.addCell(new Paragraph ("Total: ",fuente));
           tableFacturaItems.getDefaultCell().setBorderWidthLeft((float)0.25);
           tableFacturaItems.getDefaultCell().setBorderWidthRight((float)0.25);
           tableFacturaItems.getDefaultCell().setBorderWidthTop((float)0.25);
           tableFacturaItems.addCell(new Paragraph (factura.getTotalAmount().toString(),fuente));
           table.addCell(tableFacturaItems);
           table.getDefaultCell().setColspan(2);
           table.getDefaultCell().setBorderWidthBottom((float)0.25);
           table.getDefaultCell().setBorderWidthLeft((float)0.25);
           table.getDefaultCell().setBorderWidthRight((float)0.25);
           
           
           fuente=FontFactory.getFont("Times-Roman", 5, Font.NORMAL);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
           table.getDefaultCell().setPadding(5);
           table.addCell(new Paragraph("ESTA FACTURA CUMPLE CON TODOS LOS LOS REQUISITOS DE LA LEY SEGUN EL ART. 621 Y 774 DEL CODIGO DE COMERCIO Y 617 DEL ESTATUS TRIBUTARIO ESTA FACTURA ES UN TITULO Y SE ASIMILA EN TODOS SUS EFECTOS A UNA LETRA DE CAMBIO SEGUN ARTICULO 779 C.CIO. LEY 1231 DEL 2008 LEY 1231 DE JULIO DE 2008",fuente));

           table.getDefaultCell().setBorderWidthBottom((float)0.25);
           table.getDefaultCell().setBorderWidthLeft((float)0.25);
           table.getDefaultCell().setBorderWidthRight((float)0.25);
           table.getDefaultCell().setPadding(10);
            table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
              fuente=FontFactory.getFont("Times-Roman", 9, Font.BOLD);
           table.addCell(new Paragraph ("Aceptada: ________________________  Vendedor: ________________________",fuente));
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
      File file=new File("//home//adminlinux//abaco//factura.pdf");
      if(opc==1)
      {
          if(Desktop.isDesktopSupported())
              {
          try{
              embebido();
              
       // Desktop.getDesktop().open(file);
             
          }catch(Exception e)
          {
              e.printStackTrace();
          }
          }
      }
      else
      {
          // Desktop.getDesktop().print(file);
      }
   }   
  public static  void embebido() throws IOException
  {
       ProcessBuilder pb2 = new ProcessBuilder("java","-jar","/home/adminlinux/abaco/AbacoReportes.jar");
        System.out.println("Run echo command");
        Process process=pb2.start();
  }
  public   void fpdfrecibocaja() throws IOException, BadElementException, DocumentException
  {
         Document document = new Document();
         Font fuente= new Font();
         document=new Document(PageSize.LETTER,2,2,2,2);
         PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/comprobante.pdf"));
         document.open();
          PdfPTable tableMaster = new PdfPTable(1);
           //PdfPTable table = new PdfPTable(new float[] { 5, 5});
           
           PdfPTable table = new PdfPTable(new float[] {10});
           PdfPTable tableImagenTexto = new PdfPTable(new float[] {3,7});
           PdfPTable tableCliente = new PdfPTable(new float[] {6});
           PdfPTable tableFacturaItems = new PdfPTable(new float[] { 2,5,1,3});
           Image imagen;
           tableMaster.getDefaultCell().setBorderWidth(0);
           imagen = Image.getInstance(AbacoApp.getDatosempresa().getLogo());
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
            
             fuente=FontFactory.getFont("Times-Roman", 14, Font.BOLD);
             tableImagenTexto.addCell(new Paragraph("CENTRO MÉDICO DEL GUAVIARE SAS",fuente));
             fuente=FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
             tableImagenTexto.getDefaultCell().setColspan(2);
             tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
             tableImagenTexto.addCell(new Paragraph("Nit.: 900.767.863 ",fuente));
             tableImagenTexto.addCell(new Paragraph("CRA 22 N° 11-18 B. LA ESPERANZA TEL. 3134212162 ",fuente));
             tableImagenTexto.addCell(new Paragraph("SAN JOSE DEL GUAVIARE",fuente));
             table.getDefaultCell().setPadding(10);   
             table.setWidthPercentage(100f);
            
             table.addCell(tableImagenTexto);
                                                             
           
            //tabla datos de cliente
            table.getDefaultCell().setPadding(15);   
           tableCliente.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
           tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
           tableCliente.getDefaultCell().setColspan(1);
           table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
         
           fuente=FontFactory.getFont("Times-Roman", 12, Font.BOLD);
           tableCliente.getDefaultCell().setBorder(0);
           tableCliente.addCell(new Paragraph("Recibo de Caja"+"\nNo. "+ AbacoApp.s_conComprobanteIngreso.getNocomprobantecerosizquierda(),fuente));
           tableCliente.getDefaultCell().setColspan(2);
           tableCliente.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
           fuente=FontFactory.getFont("Times-Roman", 12, Font.NORMAL);
           tableCliente.addCell(new Paragraph("Fecha: "+DateFormat.getDateInstance().format(AbacoApp.s_conComprobanteIngreso.getFechaelaboracion()),fuente));
           tableCliente.getDefaultCell().setColspan(2);
           String dir,tel,nombre,nit;  
           nit=AbacoApp.s_conComprobanteIngreso.getFactura().getCustomer().getNo_ident();
           dir=AbacoApp.s_conComprobanteIngreso.getFactura().getCustomer().getDir1();
           tel=AbacoApp.s_conComprobanteIngreso.getFactura().getCustomer().getTel_fijo();
           nombre=AbacoApp.s_conComprobanteIngreso.getFactura().getCustomer().getNombre();
            
            tableCliente.addCell(new Paragraph ("Nit o CC: "+nit,fuente));
            tableCliente.getDefaultCell().setColspan(2);
            tableCliente.addCell(new Paragraph ("Recibido: "+ nombre,fuente));
            tableCliente.getDefaultCell().setColspan(2);
            tableCliente.addCell(new Paragraph ("Dirección: "+ dir,fuente));
            tableCliente.getDefaultCell().setColspan(2);
            tableCliente.addCell(new Paragraph ("Telefono: "+tel,fuente));
            table.getDefaultCell().setBorderWidthRight((float)0.25);
            tableCliente.addCell(new Paragraph ("Concepto: "+AbacoApp.s_conComprobanteIngreso.getConcepto(),fuente));
          
         
           table.addCell(tableCliente);
           table.getDefaultCell().setColspan(2);
           tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
           table.getDefaultCell().setPadding(0);   
           tableFacturaItems.addCell(new Paragraph ("Cheque",fuente));
           tableFacturaItems.addCell(new Paragraph ("Banco",fuente));
           tableFacturaItems.addCell(new Paragraph ("Targeta",fuente));
           tableFacturaItems.addCell(new Paragraph ("Valor",fuente));
            tableFacturaItems.getDefaultCell().setBorder(0);
            //tableFacturaItems.getDefaultCell().setBorderWidthTop(1);
             fuente= new Font();
             
          
            tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            tableFacturaItems.getDefaultCell().setBorderWidthLeft((float)0.25);
            tableFacturaItems.getDefaultCell().setBorderWidthRight((float)0.25);
            tableFacturaItems.getDefaultCell().setBorderWidthBottom((float)0.25);
            tableFacturaItems.addCell(new Paragraph (AbacoApp.s_conComprobanteIngreso.getNocheque(),fuente));
            tableFacturaItems.getDefaultCell().setBorderWidthLeft(0);
            tableFacturaItems.getDefaultCell().setBorderWidthRight((float)0.25);
            tableFacturaItems.addCell(new Paragraph (AbacoApp.s_conComprobanteIngreso.getBanco(),fuente));
            tableFacturaItems.addCell(new Paragraph (AbacoApp.s_conComprobanteIngreso.getNotargeta(),fuente));
            tableFacturaItems.addCell(new Paragraph (AbacoApp.s_conComprobanteIngreso.getValor().toString(),fuente));
            tableFacturaItems.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
            table.addCell(tableFacturaItems);
              
           
              table.getDefaultCell().setBorder(0);
              table.addCell(new Paragraph ("",fuente));
              table.addCell(new Paragraph ("",fuente));
              table.addCell(new Paragraph ("Firma de Recibido: ________________________\n",fuente));
              tableMaster.addCell(table);
           document.add(tableMaster);
           // step 5
          document.close();
      
    
       ProcessBuilder pb = new ProcessBuilder("java","-jar","/home/adminlinux/sihic/pdfviewercomprobante/PdfViewer.jar");

	        System.out.println("Run echo command");

	        Process process = pb.start();
            
  } 
}
