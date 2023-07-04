/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.persistence.TemporalType;
import modelo.ConComprobanteProcedimiento;
import modelo.FacturaItem;
import modelo.Producto;
import modelo.RptCitaEstadisticaDTO;

/**
 *
 * @author adminlinux
 */
public class FacturaItemController {
    
    public List<FacturaItem> getLi_facturaitems(ConComprobanteProcedimiento conComprobanteProcedimiento)
    {
        return EntityManagerGeneric.em.createQuery("select fi from FacturaItem fi where fi.conComprobanteProcedimiento=:cp order by fi.id")
                                      .setParameter("cp",conComprobanteProcedimiento)
                                      .getResultList();
                                      
    }
    public List<FacturaItem> getLi_facturaitemsProcedimiento(Long id,int tipo)
    {
        if(tipo==0)
        {
        return EntityManagerGeneric.em.createQuery("select fi from FacturaItem fi where fi.factura.no_factura=:pid order by fi.conComprobanteProcedimiento.consecutivoComprobanteProcedimiento.id")
                                      .setParameter("pid",id)
                                             .getResultList();
        }
        else
        {
             return EntityManagerGeneric.em.createQuery("select fi from FacturaItem fi where fi.conComprobanteProcedimiento.consecutivoComprobanteProcedimiento.id=:pid  order by fi.conComprobanteProcedimiento.consecutivoComprobanteProcedimiento.id")
                                      .setParameter("pid",id)
                                             .getResultList();
        }
                                      
    }
     public List<FacturaItem> getLi_Procedimientos(Long id,int tipo)
    {
        if(tipo==0)
        {
        return EntityManagerGeneric.em.createQuery("select fi from FacturaItem fi where fi.producto.genCategoriasProductos.id=1 and fi.factura.no_factura=:pid order by fi.conComprobanteProcedimiento.consecutivoComprobanteProcedimiento.id")
                                      .setParameter("pid",id)
                                             .getResultList();
        }
        else
        {
             return EntityManagerGeneric.em.createQuery("select fi from FacturaItem fi where fi.producto.genCategoriasProductos.id=1 and fi.conComprobanteProcedimiento.consecutivoComprobanteProcedimiento.id=:pid  order by fi.conComprobanteProcedimiento.consecutivoComprobanteProcedimiento.id")
                                      .setParameter("pid",id)
                                             .getResultList();
        }
                                      
    }
   public void create(FacturaItem facturaItem)
      {
          EntityManagerGeneric.et.begin();
          EntityManagerGeneric.em.persist(facturaItem);
          EntityManagerGeneric.et.commit();
      }
       public FacturaItem edit(FacturaItem facturaItem)
      {
          EntityManagerGeneric.et.begin();
          facturaItem=EntityManagerGeneric.em.merge(facturaItem);
          EntityManagerGeneric.et.commit();
          return facturaItem;
      }
   
public List<FacturaItem> rptestadisticas(String datefrom,  String dateto, Producto producto,String search) throws ParseException
{
String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date fromDate =df.parse(datefrom+" 00:00:00");
Date toDate = df.parse(dateto+" 23:59:59");

    return EntityManagerGeneric.em.createQuery("select fi from FacturaItem fi where fi.factura.facturaDate BETWEEN :m1 and :m2 and (lower(fi.producto.codigo) like :search or lower(fi.producto.nombre) like :search or fi.producto.codigo like :search  ) order by fi.factura.prefijo,fi.conComprobanteProcedimiento.consecutivoComprobanteProcedimiento.id")
                                                                                   .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("search", searchpattern)
                                                                                   .getResultList();
   
}
public List<RptCitaEstadisticaDTO> rptestadisticasDTO(String datefrom,  String dateto, Producto producto,String search) throws ParseException
{
String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date fromDate =df.parse(datefrom+" 00:00:00");
Date toDate = df.parse(dateto+" 23:59:59");
 long ti=System.currentTimeMillis();
   List<RptCitaEstadisticaDTO> li_CitaEstadisticaDTOs=EntityManagerGeneric.em.createQuery("select  new modelo.RptCitaEstadisticaDTO(fi.agnCitas.fechaHora,fi.agnCitas.fechaHora,fi.agnCitas.genHorasCita.hora,fi.agnCitas.genHorasCita.minutos,fi.agnCitas.genPacientes.genPersonas.primerNombre,fi.agnCitas.genPacientes.genPersonas.segundoNombre,fi.agnCitas.genPacientes.genPersonas.primerApellido,fi.agnCitas.genPacientes.genPersonas.segundoApellido,fi.agnCitas.genPacientes.genPersonas.genTiposDocumentos.nombre,fi.agnCitas.genPacientes.genPersonas.documento,fi.agnCitas.genPacientes.genPersonas.fechaNacimiento,fi.agnCitas.genPacientes.genPersonas.telefono,fi.agnCitas.genPacientes.genPersonas.genSexo.sigla,fi.agnCitas.genPacientes.genPersonas.direccion,ea.nombre,fi.agnCitas.agnMedicos.genPersonas.primerApellido,fi.agnCitas.genPacientes.genTiposUsuarios.codigo,fi.producto.codigo,fi.producto.nombre,fi.valor_total,fi.producto.nombre,fi.factura.prefijo,fi.conComprobanteProcedimiento.consecutivoComprobanteProcedimiento.id,fi.producto.genCategoriasProductos.codigo,fi.talla,fi.agnCitas.genPacientes.genTiposUsuarios.nombre,f.no_factura, fi.noautorizacion) from Factura f JOIN FETCH f.facturaItems fi LEFT OUTER JOIN fi.agnCitas.genPacientes.genEapb ea where f.facturaDate BETWEEN :m1 and :m2 and (lower(fi.producto.codigo) like :search or lower(fi.producto.nombre) like :search or fi.producto.codigo like :search  ) order by f.prefijo,fi.conComprobanteProcedimiento.consecutivoComprobanteProcedimiento.id",RptCitaEstadisticaDTO.class)
                                                                                   .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("search", searchpattern)
                                                                                   .getResultList();
      float tf=ti-System.currentTimeMillis();
   return li_CitaEstadisticaDTOs;
}
public List<FacturaItem> rptprocedimientos(String datefrom,  String dateto, Producto producto,String search) throws ParseException
{
String searchpattern = search==null ? "%" :  '%'+ search.toLowerCase().replace('*', '%') + '%';
SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date fromDate =df.parse(datefrom+" 00:00:00");
Date toDate = df.parse(dateto+" 23:59:59");

    return EntityManagerGeneric.em.createQuery("select fi from FacturaItem fi where fi.hclConsultas.agnCitas.fechaHora BETWEEN :m1 and :m2 and fi.producto=:prod and (lower(fi.genPacientes.genEapb.nit) like :search or lower(fi.genPacientes.genEapb.nombre) like :search or lower(fi.genPacientes.genTiposUsuarios.nombre) like :search or fi.genPacientes.genEapb is null) order by fi.genPacientes.genEapb.nombre,fi.genPacientes.genTiposUsuarios.nombre" )
                                                                                   .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("prod", producto)
                                                                                   .setParameter("search", searchpattern)
                                                                                   .getResultList();
   
}

public List<FacturaItem> rptfacturaitemscopago(String datefrom,  String dateto) throws ParseException
{
SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
Date fromDate =df.parse(datefrom+" 00:00:00");
Date toDate = df.parse(dateto+" 23:59:59");

    return EntityManagerGeneric.em.createQuery("select fi from FacturaItem fi where fi.factura.facturaDate BETWEEN :m1 and :m2  order by fi.factura.facturaDate")
                                                                                   .setParameter("m1", fromDate,TemporalType.TIMESTAMP)
                                                                                   .setParameter("m2", toDate,TemporalType.TIMESTAMP)
                                                                                    .getResultList();
   
}
public boolean check_ifexistsnoautorizacion(String noautirizacion)
{
  if(EntityManagerGeneric.em.createQuery("select fi from FacturaItem fi where fi.noautorizacion=:pnoautorizacion")
          .setParameter("pnoautorizacion",noautirizacion )
          .getResultList().size()>0)
  {
      return false;
  }
  else
  {
      return true;
  }
 }
}
