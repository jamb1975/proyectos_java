/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javax.persistence.EntityTransaction;
import javax.persistence.TemporalType;
import static isoftconta.servicios.EntityManagerGeneric.em;
import modelo.DocumentoSoporte;
import modelo.EntidadesStatic;
import static modelo.EntidadesStatic.as_puc;
import static modelo.EntidadesStatic.es_puc;
import static modelo.EntidadesStatic.li_puc;
import modelo.LibroAuxiliar;
import modelo.Puc;

/**
 *
 * @author adminlinux
 */
public class DocumentoSoporteController {
         private static   EntityTransaction et=null;
      
   public static int servicio_crear()
   {   try
      {
            
             et=em.getTransaction();
          
       verificarestadotransacion();
       et.begin();
      if(EntidadesStatic.es_documentosoporte.getClientes()!=null)
      {  
      if(EntidadesStatic.es_documentosoporte.getClientes().getTipopersona()!=null)
      {
       if(EntidadesStatic.es_documentosoporte.getClientes().getTipopersona().length()>1)
       {
           EntidadesStatic.es_documentosoporte.getClientes().setTipopersona("0");
       }
      }
      if(EntidadesStatic.es_documentosoporte.getClientes().getTipoidentificacion()!=null)
      {
        if(EntidadesStatic.es_documentosoporte.getClientes().getTipoidentificacion().length()>1)
       {
           EntidadesStatic.es_documentosoporte.getClientes().setTipoidentificacion("0");
       }
      }
      }
       EntidadesStatic.es_documentosoporte.setNo_consecutivo(ConsecutivosContabilidadController.getNoConsecutivo(EntidadesStatic.es_documentosoporte.getTipodocsoporte()));
       EntidadesStatic.es_documentosoporte=em.merge(EntidadesStatic.es_documentosoporte);
       et.commit();
       
        return 1;
      }catch(Exception e)
      { e.printStackTrace();
        {
       if(EntityManagerGeneric.et.isActive())
       {
           EntityManagerGeneric.et.rollback();
       }
       EntityManagerGeneric.et.begin();
          EntidadesStatic.es_documentosoporte.setNo_consecutivo(ConsecutivosContabilidadController.getNoConsecutivo2(EntidadesStatic.es_documentosoporte.getTipodocsoporte()));
       em.persist(EntidadesStatic.es_documentosoporte);
       et.commit();
      
         return 1; 
      }
      }
   }
   public static int servicio_actualizar()
   {   try
      {
          
             et=em.getTransaction();
          
       verificarestadotransacion();
       et.begin();
      if(EntidadesStatic.es_documentosoporte.getClientes()!=null)
      {  
      if(EntidadesStatic.es_documentosoporte.getClientes().getTipopersona()!=null)
      {
       if(EntidadesStatic.es_documentosoporte.getClientes().getTipopersona().length()>1)
       {
           EntidadesStatic.es_documentosoporte.getClientes().setTipopersona("0");
       }
      }
      if(EntidadesStatic.es_documentosoporte.getClientes().getTipoidentificacion()!=null)
      {
        if(EntidadesStatic.es_documentosoporte.getClientes().getTipoidentificacion().length()>1)
       {
           EntidadesStatic.es_documentosoporte.getClientes().setTipoidentificacion("0");
       }
      }
      }
     EntidadesStatic.es_documentosoporte= em.merge(EntidadesStatic.es_documentosoporte);
       et.commit();
       
        return 1;
      }catch(Exception e)
      {
         return -1; 
      }
   }
   private static void verificarestadotransacion()
   {
       if(et.isActive())
       {
           et.rollback();
       }
   }
   
   public static List<DocumentoSoporte> getRecords(String datefrom,  String dateto,int tipodocumentosoporte) throws ParseException
    {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate =df.parse(datefrom+" 00:00:00");
        Date toDate = df.parse(dateto+" 23:59:59");
        System.out.println("fechas->"+fromDate+"/"+toDate);
       return EntityManagerGeneric.em.createQuery("select  ds from DocumentoSoporte ds where ds.fechaelaboracion BETWEEN :m1 and :m2 and ds.tipodocsoporte=:tipodoc order by ds.no_consecutivo")
                                                                                   .setParameter("m1",fromDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("tipodoc", tipodocumentosoporte)
                                                                                   .getResultList();
    }
   
   public static List<DocumentoSoporte> getrecordsdocumentossoporte(String buscar, int tipodcosoporte)
    {
        String search=buscar==null?"%":'%'+buscar.toLowerCase().replace('*', '%')+'%';
       
        System.out.println("buscar->"+search);
       return EntityManagerGeneric.em.createQuery("select  ds from DocumentoSoporte ds where ds.tipodocsoporte=:tipodoc and (ds.clientes.no_ident like :search or lower(CONCAT(ds.clientes.nombre,ds.clientes.primerapellido,ds.clientes.segundoapellido)) like :search ) and  (ds.valorneto >(select SUM(dscompi.valorneto)as UnBilledHrs from DocumentoSoporte dscompi where dscompi.tipodocsoporte in(3,4) and dscompi.documentoSoporte.tipodocsoporte=:tipodoc and dscompi.documentoSoporte.id=ds.id  and dscompi.documentoSoporte.id is not null)or((select SUM(dscompi.valorneto)as UnBilledHrs from DocumentoSoporte dscompi where dscompi.tipodocsoporte in(3,4) and dscompi.documentoSoporte.tipodocsoporte=:tipodoc and dscompi.documentoSoporte.id=ds.id)is null)) order by ds.fechaelaboracion asc")
                                                                                    .setParameter("search", search)
                                                                                    .setParameter("tipodoc", tipodcosoporte)
                                                                                    .setMaxResults(120)
                                                                                    .getResultList();
    }
   public static List<LibroAuxiliar> getrecordsdocumentossoporte2(String datefrom,  String dateto,String buscar, int tipodcosoporte) throws ParseException
    {
        String search=buscar==null?"%":'%'+buscar.toLowerCase().replace('*', '%')+'%';
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate =df.parse(datefrom+" 00:00:00");
        Date toDate = df.parse(dateto+" 23:59:59");
        System.out.println("fecha->"+fromDate+toDate);
       /*return EntityManagerGeneric.em.createQuery("select  ds from DocumentoSoporte ds where ds.tipodocsoporte=:tipodoc and (ds.clientes.no_ident like :search or lower(CONCAT(ds.clientes.nombre,ds.clientes.primerapellido,ds.clientes.segundoapellido)) like :search ) and  (ds.valorneto >(select SUM(dscompi.valorneto)as UnBilledHrs from DocumentoSoporte dscompi where dscompi.tipodocsoporte in(3,4) and dscompi.documentoSoporte.tipodocsoporte=:tipodoc and dscompi.documentoSoporte.id=ds.id  and dscompi.documentoSoporte.id is not null)or((select SUM(dscompi.valorneto)as UnBilledHrs from DocumentoSoporte dscompi where dscompi.tipodocsoporte in(3,4) and dscompi.documentoSoporte.tipodocsoporte=:tipodoc and dscompi.documentoSoporte.id=ds.id)is null)) order by ds.fechaelaboracion,ds.prefijo asc")
                                                                                    .setParameter("search", search)
                                                                                    .setParameter("tipodoc", tipodcosoporte)
                                                                                    .setMaxResults(1000)
                                                                                     .getResultList();*/
            String sb="";
            String sb2="";
            String sb3="";
            String cp="";
            String la="";
            String la2="";
            String la3="";
            sb="SUBSTRING(la.conPuc.codigo, 1, 2)";
            sb2="SUBSTRING(transacciones.conPuc.codigo, 1, 2)";
            sb3="SUBSTRING(transacciones2.conPuc.codigo, 1, 2)";
            cp="13";
            la="la.debe";
            la2="transacciones.haber";
            la3="transacciones2.haber";
            
           return EntityManagerGeneric.em.createQuery("select  la from LibroAuxiliar la where la.debe>0 and " +sb+"=:cp and la.documentoSoporte.tipodocsoporte=:tipodoc and (la.terceros.no_ident like :search or lower(CONCAT(la.terceros.nombre,la.terceros.primerapellido,la.terceros.segundoapellido)) like :search ) and la.fechaelaboracion BETWEEN :m1 and :m2 and ("+la+" >(select SUM("+la2+")as UnBilledHrs from LibroAuxiliar  transacciones where transacciones.id_source_mov=la.id and transacciones.id_source_mov is not null and "+sb2+"='13' and transacciones.haber>0 and transacciones.documentoSoporte.tipodocsoporte=3  and   transacciones.terceros.id=la.documentoSoporte.clientes.id)or((select SUM("+la3+")as UnBilledHrs from LibroAuxiliar transacciones2 where " +sb3+"='13' and transacciones2.haber>0 and transacciones2.documentoSoporte.tipodocsoporte=3 and transacciones2.id_source_mov=la.id and transacciones2.id_source_mov is not null and transacciones2.terceros.id=la.documentoSoporte.clientes.id) is null)) order by la.documentoSoporte.prefijo,la.documentoSoporte.no_factura asc")
                                                                                    .setParameter("search", search)
                                                                                    .setParameter("tipodoc", 15)
                                                                                    .setParameter("cp", cp)
                                                                                    .setParameter("m1",fromDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                                   .getResultList();
    }
   public static List<LibroAuxiliar> getrecordsegresosmasivos(String datefrom,  String dateto,String buscar, int tipodcosoporte) throws ParseException
    {
        String search=buscar==null?"%":'%'+buscar.toLowerCase().replace('*', '%')+'%';
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date fromDate =df.parse(datefrom+" 00:00:00");
        Date toDate = df.parse(dateto+" 23:59:59");
        
        String sb="";
        String sb2="";
        String sb3="";
        String cp="";
        String la="";
        String la2="";
        String la3="";
        sb="SUBSTRING(la.conPuc.codigo, 1, 1)";
        sb2="SUBSTRING(transacciones.conPuc.codigo, 1, 1)";
        sb3="SUBSTRING(transacciones2.conPuc.codigo, 1, 1)";
        cp="2";
        la="la.haber";
        la2="transacciones.debe";
        la3="transacciones2.debe";
            
           return EntityManagerGeneric.em.createQuery("select  la from LibroAuxiliar la  where la.haber>0 and " +sb+"=:cp  and (la.terceros.no_ident like :search or lower(CONCAT(la.terceros.nombre,la.terceros.primerapellido,la.terceros.segundoapellido)) like :search) and la.fechaelaboracion BETWEEN :m1 and :m2 and ("+la+" >(select SUM("+la2+")as UnBilledHrs from LibroAuxiliar  transacciones where (transacciones.id_source_mov=la.id and transacciones.terceros.id=la.terceros.id) and "+sb2+"='2' and transacciones.debe>0 and transacciones.documentoSoporte.tipodocsoporte=4  )or((select SUM("+la3+")as UnBilledHrs from LibroAuxiliar transacciones2 where " +sb3+"='2' and transacciones2.debe>0 and transacciones2.documentoSoporte.tipodocsoporte=4 and (transacciones2.id_source_mov=la.id and transacciones2.terceros.id=la.terceros.id)  ) is null)) order by la.fechaelaboracion asc")
                                                                                    .setParameter("search", search)
                                                                                    //.setParameter("tipodoc", 14)
                                                                                    .setParameter("cp", cp)
                                                                                    .setParameter("m1",fromDate,TemporalType.TIMESTAMP)
                                                                                    .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                                   .getResultList();
    }
   public static List<LibroAuxiliar> getrecordsegresosmasivos2(String buscar) throws ParseException
    {
        String search=buscar==null?"%":'%'+buscar.toLowerCase().replace('*', '%')+'%';
        String sb="";
        String sb2="";
        String sb3="";
        String cp="";
        String la="";
        String la2="";
        String la3="";
        sb="SUBSTRING(la.conPuc.codigo, 1, 1)";
        sb2="SUBSTRING(transacciones.conPuc.codigo, 1, 1)";
        sb3="SUBSTRING(transacciones2.conPuc.codigo, 1, 1)";
        cp="2";
        la="la.haber";
        la2="transacciones.debe";
        la3="transacciones2.debe";
            
           return EntityManagerGeneric.em.createQuery("select  la from LibroAuxiliar la where (la.documentoSoporte.tipodocsoporte=14 or la.documentoSoporte.tipodocsoporte=6 or la.documentoSoporte.tipodocsoporte=9 or la.documentoSoporte.tipodocsoporte=10 or la.documentoSoporte.tipodocsoporte=17 or la.documentoSoporte.tipodocsoporte=19) and la.haber>0 and " +sb+"=:cp  and (la.terceros.no_ident like :search or lower(CONCAT(la.terceros.nombre,la.terceros.primerapellido,la.terceros.segundoapellido)) like :search ) and  ("+la+" >(select SUM("+la2+")as UnBilledHrs from LibroAuxiliar  transacciones where (transacciones.id_source_mov=la.id and transacciones.terceros.id=la.terceros.id) and "+sb2+"='2' and transacciones.debe>0 and transacciones.documentoSoporte.tipodocsoporte=4  )or((select SUM("+la3+")as UnBilledHrs from LibroAuxiliar transacciones2 where " +sb3+"='2' and transacciones2.debe>0 and transacciones2.documentoSoporte.tipodocsoporte=4 and (transacciones2.id_source_mov=la.id and transacciones2.terceros.id=la.terceros.id)  ) is null)) order by la.fechaelaboracion asc")
                                                                                    .setParameter("search", search)
                                                                                    //.setParameter("tipodoc", 14)
                                                                                    .setParameter("cp", cp)
                                                                                    .setMaxResults(77)
                                                                                   .getResultList();
    }
     public static List<LibroAuxiliar> getrecordsingresosmasivos2(String buscar) throws ParseException
    {
        String search=buscar==null?"%":'%'+buscar.toLowerCase().replace('*', '%')+'%';
        String sb="";
        String sb2="";
        String sb3="";
        String cp="";
        String la="";
        String la2="";
        String la3="";
        sb="SUBSTRING(la.conPuc.codigo, 1, 1)";
        sb2="SUBSTRING(transacciones.conPuc.codigo, 1, 1)";
        sb3="SUBSTRING(transacciones2.conPuc.codigo, 1, 1)";
        cp="2";
        la="la.debe";
        la2="transacciones.haber";
        la3="transacciones2.haber";
            
           return EntityManagerGeneric.em.createQuery("select  la from LibroAuxiliar la where (la.documentoSoporte.tipodocsoporte=14 or la.documentoSoporte.tipodocsoporte=6 or la.documentoSoporte.tipodocsoporte=9 or la.documentoSoporte.tipodocsoporte=10 or la.documentoSoporte.tipodocsoporte=17 or la.documentoSoporte.tipodocsoporte=19) and la.haber>0 and " +sb+"=:cp  and (la.terceros.no_ident like :search or lower(CONCAT(la.terceros.nombre,la.terceros.primerapellido,la.terceros.segundoapellido)) like :search ) and  ("+la+" >(select SUM("+la2+")as UnBilledHrs from LibroAuxiliar  transacciones where (transacciones.id_source_mov=la.id and transacciones.terceros.id=la.terceros.id) and "+sb2+"='1' and transacciones.debe>0 and transacciones.documentoSoporte.tipodocsoporte=3  )or((select SUM("+la3+")as UnBilledHrs from LibroAuxiliar transacciones2 where " +sb3+"='1' and transacciones2.debe>0 and transacciones2.documentoSoporte.tipodocsoporte=3 and (transacciones2.id_source_mov=la.id and transacciones2.terceros.id=la.terceros.id)  ) is null)) order by la.fechaelaboracion asc")
                                                                                    .setParameter("search", search)
                                                                                    //.setParameter("tipodoc", 14)
                                                                                    .setParameter("cp", cp)
                                                                                    .setMaxResults(77)
                                                                                   .getResultList();
    }
    public static List<LibroAuxiliar> getrecordslibroauxiliar(String buscar, int tipodcosoporte,int tipodocsoporte2) 
    {
        String search=buscar==null?"%":'%'+buscar.toLowerCase().replace('*', '%')+'%';
        if(EntityManagerGeneric.et.isActive())
        {
            EntityManagerGeneric.et.rollback();
        }
        try
        {
            String sb="";
            String cp="";
            String la="";
            String la2="";
            if(tipodocsoporte2==EnumDocumentoSoporte.COMPROBANTEEGRESO4.ordinal())
            {
                sb="SUBSTRING(la.conPuc.codigo, 1, 1)";
                cp="2";
                la="la.haber";
                la2="transacciones.haber";
            }
            else
             if(tipodocsoporte2==EnumDocumentoSoporte.COMPROBANTEINGRESO3.ordinal())
            {
                sb="SUBSTRING(la.conPuc.codigo, 1, 2)";
                cp="13";
                la="la.debe";
                la2="transacciones.debe";
            }
           return EntityManagerGeneric.em.createQuery("select  la from LibroAuxiliar la where "+sb+"=:cp and la.documentoSoporte.tipodocsoporte=:tipodoc and (la.terceros.no_ident like :search or lower(CONCAT(la.terceros.nombre,la.terceros.primerapellido,la.terceros.segundoapellido)) like :search ) and  ("+la+" >(select SUM("+la2+")as UnBilledHrs from LibroAuxiliar transacciones where transacciones.id_source_mov=la.id and transacciones.id_source_mov is not null and transacciones.terceros.id=la.terceros.id)or((select SUM(transacciones2.haber)as UnBilledHrs from LibroAuxiliar transacciones2 where transacciones2.id_source_mov=la.id and transacciones2.id_source_mov is not null and transacciones2.terceros.id=la.terceros.id) is null)) order by la.fechaelaboracion asc")
                                                                                    .setParameter("search", search)
                                                                                    .setParameter("tipodoc", tipodcosoporte)
                                                                                    .setParameter("cp", cp)
                                                                                    .setMaxResults(120)
                                                                                    .getResultList();
        }catch(Exception e)
                 {
                 e.printStackTrace();

       return new ArrayList<>();                 
             }
    }
public static String[] getdocumentodoporte(EnumDocumentoSoporte enumDocumentoSoporte)
{
    String[] nombreysigla=new String[2];
    switch(enumDocumentoSoporte)
    {
        case FACTURACOMPRA0:
            nombreysigla[0]="Factura de Compra";
            nombreysigla[1]="fc";
            return nombreysigla;
        case FACTURAVENTA1:
            nombreysigla[0]="Factura de Venta";
            nombreysigla[1]="fv";
            return nombreysigla;
        case RECIBOCAJAMENOR2:
            nombreysigla[0]="Recibo de Caja Menor";
            nombreysigla[1]="rcm";
            return nombreysigla;    
        case COMPROBANTEINGRESO3:
            nombreysigla[0]="Comprobante de Ingreso";
            nombreysigla[1]="ci";
            return nombreysigla; 
        case COMPROBANTEEGRESO4:
            nombreysigla[0]="Comprobante de Egreso";
            nombreysigla[1]="ce";
            return nombreysigla;
        case COMPROBANTEAJUSTE5:
            nombreysigla[0]="Comprobante de Ajuste";
            nombreysigla[1]="ca";
            return nombreysigla;
        case NOTACONTABILIDAD6:
            nombreysigla[0]="Nota de Contabilidad";
            nombreysigla[1]="nc";
            return nombreysigla; 
        case CIERREFINMES7:
            nombreysigla[0]="Cierre de Fin de Mes";
            nombreysigla[1]="cfm";
            return nombreysigla;  
        case CIERREFINANO8:
            nombreysigla[0]="Cierre de Fin de Año";
            nombreysigla[1]="cfa";
            return nombreysigla;
        case NOTADEBITO9:
            nombreysigla[0]="Nota Débito";
            nombreysigla[1]="nd";
            return nombreysigla;
        case NOTACREDITO10:
            nombreysigla[0]="Nota Crédito";
            nombreysigla[1]="nc";
            return nombreysigla;
        case COMPROBANTECONSIGNACION11:
            nombreysigla[0]="Comprobante de Consignación";
            nombreysigla[1]="cc";
            return nombreysigla; 
        case TRANSACCIONCAJEROELECTRONICO12:
            nombreysigla[0]="Transacción Cajero Eletrónico";
            nombreysigla[1]="tce";
            return nombreysigla; 
        case CONCILIACIONBANCARIA13:
            nombreysigla[0]="Conciliación Bancaria";
            nombreysigla[1]="cb";
            return nombreysigla;
        case COMPROBANTECAUSACIONEGRESO14:
            nombreysigla[0]="Comprobante Causación de Gastos";
            nombreysigla[1]="cce";
            return nombreysigla; 
        case COMPROBANTECAUSACIONINGRESO15:
            nombreysigla[0]="Comprobante Causación de Ingreso";
            nombreysigla[1]="cci";
            return nombreysigla;
        case CARGUEINICIALSALDOS16:
            nombreysigla[0]="Cargue Inicial Saldos";
            nombreysigla[1]="cis";
            return nombreysigla; 
        case COMPROBANTECAUSACIONNOMINA17:
            nombreysigla[0]="Comprobante de Causación de Nómina";
            nombreysigla[1]="cpn";
            return nombreysigla;    
        case NOVEDADNOMINA18:
            nombreysigla[0]="Novedad Nómina";
            nombreysigla[1]="nn";
            return nombreysigla; 
        case SALDOINICIAL19:
            nombreysigla[0]="Comprobante Saldos Iniciales";
            nombreysigla[1]="csi";
            return nombreysigla;    
        case NOTIFICACIONDEVOLUCIONVENTA20:
            nombreysigla[0]="Notificación Devolución Venta";
            nombreysigla[1]="ndv";
            return nombreysigla;         
              
        default:
            nombreysigla[0]="Sin Identificar";
            nombreysigla[1]="si";
            return nombreysigla;  
    }
} 
public static EnumDocumentoSoporte valorequivalenteenenum(int i)
{
    EnumDocumentoSoporte edocso=EnumDocumentoSoporte.FACTURACOMPRA0;
    for(EnumDocumentoSoporte eds:EnumDocumentoSoporte.values())
    {
        if(eds.ordinal()==i)
        {
            edocso=eds;
            break;
        }
    }
    return edocso;
}
public static String[] getNombredocumentosoporte(int i)
{
    return getdocumentodoporte(valorequivalenteenenum(i));
}
public static void load_aspuc()
{
   as_puc=new String[li_puc.size()];
   int i=0;
   for(Puc puc:li_puc)
   {
       as_puc[i]=puc.getCodigo()+"-"+puc.getNombre();
       i++;
   }
}
public  static void findpucporcodigo(String codigo)
{
   String[] _aspuc=codigo.split("-");
   List<Puc> _li_puc=li_puc.stream().filter(e-> (e.getCodigo().equals(_aspuc[0]))).collect(Collectors.toList());
   if(_li_puc.size()>0)
   {
     es_puc=_li_puc.get(0);
   }
}
public  static void findpuccausaciondebitoporcodigo(String codigo)
{
   String[] _aspuc=codigo.split("-");
   List<Puc> _li_puc=li_puc.stream().filter(e-> (e.getCodigo().equals(_aspuc[0]))).collect(Collectors.toList());
   if(_li_puc.size()>0)
   {
     EntidadesStatic.es_puc_causaciondebito=_li_puc.get(0);
    }
}
public  static void findpuccausacioncreditoporcodigo(String codigo)
{
   String[] _aspuc=codigo.split("-");
   List<Puc> _li_puc=li_puc.stream().filter(e-> (e.getCodigo().equals(_aspuc[0]))).collect(Collectors.toList());
   if(_li_puc.size()>0)
   {
     EntidadesStatic.es_puc_causacioncredito=_li_puc.get(0);
    }
}
public static void load_asdocumentosoporte(String buscar, int tipodocumentosoporte,int tipodocsoporte2) throws ParseException
{
    if(!buscar.contains("-"))
    {
      if( tipodocumentosoporte==EnumDocumentoSoporte.COMPROBANTECAUSACIONINGRESO15.ordinal())
      {      
       EntidadesStatic.li_documentosoporte=getrecordsdocumentossoporte(buscar,tipodocumentosoporte);
       if(EntidadesStatic.li_documentosoporte!=null)
   {
    if(EntidadesStatic.li_documentosoporte.size()>0)
   {
        EntidadesStatic.as_documentosoporte=null;
   EntidadesStatic.as_documentosoporte=new String[EntidadesStatic.li_documentosoporte.size()];
   int i=0;
       Collections.sort(EntidadesStatic.li_documentosoporte, (o1, o2) -> o1.getFechaelaboracion().compareTo(o2.getFechaelaboracion()));

   for(DocumentoSoporte ds:EntidadesStatic.li_documentosoporte)
   {
       EntidadesStatic.as_documentosoporte[i]= ds.getNo_consecutivo()+"-Mes: "+obtenerMes(ds.getFechaelaboracion())+"- "+(tipodocumentosoporte==15?ds.getNofacturacerosizquierda():ds.getNocomprobantecerosizquierda())+" "+ds.getClientes().getNo_ident()+" "+ds.getClientes().getNombres()+" $"+ds.getValorneto().toString();
       i++;
   }
    EntidadesStatic.possibleSuggestions_docsoporte=null;
    EntidadesStatic.possibleSuggestions_docsoporte = new HashSet<>(Arrays.asList(EntidadesStatic.as_documentosoporte));
    }
    } 
      }
      else
      if(tipodocumentosoporte==EnumDocumentoSoporte.COMPROBANTECAUSACIONEGRESO14.ordinal()||tipodocumentosoporte==EnumDocumentoSoporte.COMPROBANTECAUSACIONNOMINA17.ordinal()|| tipodocumentosoporte==EnumDocumentoSoporte.SALDOINICIAL19.ordinal())
      {      
        System.out.println("size libro auxiliar.....");   
       EntidadesStatic.li_libroauxiliar=getrecordslibroauxiliar(buscar,tipodocumentosoporte,tipodocsoporte2);
       System.out.println("size libro auxiliar"+EntidadesStatic.li_libroauxiliar.size());
       if(EntidadesStatic.li_libroauxiliar!=null)
   {
    if(EntidadesStatic.li_libroauxiliar.size()>0)
   {
        EntidadesStatic.as_documentosoporte=null;
   EntidadesStatic.as_documentosoporte=new String[EntidadesStatic.li_libroauxiliar.size()];
   int i=0;
   for(LibroAuxiliar ds:EntidadesStatic.li_libroauxiliar)
   {
       if(tipodocsoporte2==EnumDocumentoSoporte.COMPROBANTEEGRESO4.ordinal())
       {     
        EntidadesStatic.as_documentosoporte[i]=ds.getId()+"- "+ds.getTerceros().getNo_ident()+" "+ds.getTerceros().getNombres()+" "+ds.getDescripcion()+" $"+ds.getHaber();
       }
       else
       {
         if(tipodocsoporte2==EnumDocumentoSoporte.COMPROBANTEINGRESO3.ordinal())
       {     
        EntidadesStatic.as_documentosoporte[i]=ds.getId()+"- "+ds.getTerceros().getNo_ident()+" "+ds.getTerceros().getNombres()+" "+ds.getDescripcion()+" $"+ds.getDebe();
       }  
       }
       i++;
   }
    EntidadesStatic.possibleSuggestions_docsoporte=null;
    EntidadesStatic.possibleSuggestions_docsoporte = new HashSet<>(Arrays.asList(EntidadesStatic.as_documentosoporte));
    }
    } 
      } 
   
    }
}
public static void reiniciaras_documentosoporte()
{
    EntidadesStatic.as_documentosoporte=new String[2];
}
public  static void finddocumentosoportecausacioningreso(String no_consecutivo)
{
   String[] _as_documentosoportecausacioningresos=no_consecutivo.split("-");
  
   
  try
  {   
      if(!_as_documentosoportecausacioningresos[0].equals(""))
      {
   List<DocumentoSoporte> _li_documentosoporte=EntidadesStatic.li_documentosoporte.stream().filter(e-> (e.getNo_consecutivo().equals(Long.valueOf(_as_documentosoportecausacioningresos[0])))).collect(Collectors.toList());
   if(_li_documentosoporte.size()>0)
   {
     System.out.println("codigo->"+no_consecutivo+ ""+_as_documentosoportecausacioningresos[0]);  
     EntidadesStatic.es_documentosoporte_root=_li_documentosoporte.get(0);
     EntidadesStatic.li_documentosoporte.clear();
     EntidadesStatic.as_documentosoporte=null;
     EntidadesStatic.as_documentosoporte=new String[EntidadesStatic.li_documentosoporte.size()];
     EntidadesStatic.possibleSuggestions_docsoporte=null;
     EntidadesStatic.possibleSuggestions_docsoporte = new HashSet<>(Arrays.asList(EntidadesStatic.as_documentosoporte));
 
   }
      }
  }catch(Exception e)
  {
      
  }
}
public  static void findlibroauxiliarportecausacionnomina(String no_consecutivo)
{
   String[] _as_documentosoportecausacioningresos=no_consecutivo.split("-");
  
   
  try
  {   
      if(!_as_documentosoportecausacioningresos[0].equals(""))
      {
   List<LibroAuxiliar> _li_libroauxliar=EntidadesStatic.li_libroauxiliar.stream().filter(e-> (e.getId().equals(Long.valueOf(_as_documentosoportecausacioningresos[0])))).collect(Collectors.toList());
   if(_li_libroauxliar.size()>0)
   {
     System.out.println("codigo->"+no_consecutivo+ ""+_as_documentosoportecausacioningresos[0]);  
     EntidadesStatic.id_source_mov=_li_libroauxliar.get(0).getId();
     EntidadesStatic.es_libroauxiliar=_li_libroauxliar.get(0);
     EntidadesStatic.li_libroauxiliar.clear();
      EntidadesStatic.as_documentosoporte=null;
     EntidadesStatic.as_documentosoporte=new String[EntidadesStatic.li_libroauxiliar.size()];
     EntidadesStatic.possibleSuggestions_docsoporte=null;
     EntidadesStatic.possibleSuggestions_docsoporte = new HashSet<>(Arrays.asList(EntidadesStatic.as_documentosoporte));
 
   }
      }
  }catch(Exception e)
  {
      
  }
}
  public static DocumentoSoporte findfacturaporprefijonumero(String prefijo,Long numero)
  {   try
  {
      return(DocumentoSoporte) EntityManagerGeneric.em.createQuery("select ds from DocumentoSoporte ds where ds.prefijo=:pref and ds.no_factura=:nofact and ds.tipodocsoporte=15")
                                     .setParameter("pref", prefijo)
                                     .setParameter("nofact", numero)
                                     .getSingleResult();
  }catch(Exception e)
  {
      return null;
  }
  }
 public  static boolean verificarquenoexistecodigopuc(String codigo)
{
   
   List<Puc> _li_puc=li_puc.stream().filter(e-> (e.getCodigo().equals(codigo))).collect(Collectors.toList());
   if(_li_puc.size()<=0)
   {
    return true;
    }
   else
     return false;
}
 public  static boolean verificarquenoexistecodigopuc2(String codigo)
{
  
   List<Puc> _li_puc=li_puc.stream().filter(e-> (e.getCodigo().equals(codigo))).collect(Collectors.toList());
   if(_li_puc.size()<=0)
   {
     return true;
   }
   else
   {
     int i=0;  
     if(_li_puc.size()>1)
     {
         i=1;
     }
      if(EntidadesStatic.es_puc.getId().equals(_li_puc.get(i).getId()))
      {
         return true;
      }
       else
      {
      return false;
      }
   }
}
  public  static boolean verificarcodigoigualaroot(String codigo)
{
   System.out.println("Código root->"+codigo.substring(0,EntidadesStatic.es_puc_padre.getCodigo().length())+" código actual->"+EntidadesStatic.es_puc_padre.getCodigo());
   if(codigo.substring(0,EntidadesStatic.es_puc_padre.getCodigo().length()).equals(EntidadesStatic.es_puc_padre.getCodigo()))
   {
    return true;
   }
   else
       return false;
}
 public static DocumentoSoporte findbynoconsecutivo(Long noconsecutivo)
 {
     try
     {
     return (DocumentoSoporte)EntityManagerGeneric.em.createQuery("select ds from DocumentoSoporte ds where ds.no_consecutivo=:pno and ds.tipodocsoporte=3")
                                    .setParameter("pno", noconsecutivo)
                                    .setMaxResults(1)
                                    .getSingleResult();
     }catch(Exception e)
     {
         return null;
     }
 }    
  public static DocumentoSoporte findbynoconsecutivoegreso(Long noconsecutivo)
 {
     try
     {
     return (DocumentoSoporte)EntityManagerGeneric.em.createQuery("select ds from DocumentoSoporte ds where ds.no_consecutivo=:pno and ds.tipodocsoporte=4")
                                    .setParameter("pno", noconsecutivo)
                                    .setMaxResults(1)
                                    .getSingleResult();
     }catch(Exception e)
     {
         return null;
     }
 }
  public static String obtenerMes(Date d_fecha) {
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    LocalDate ld;
        df = new SimpleDateFormat("yyyy-MM-dd");
        String sfecha =  df.format(d_fecha);
        System.out.println("sfecha ini->" + sfecha);
        ld = LocalDate.parse(sfecha);
        Month mes = ld.getMonth();


String nombre = mes.getDisplayName(TextStyle.FULL, new Locale("es", "ES"));
return nombre;
}
}
