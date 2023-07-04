/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios;

import java.util.List;
import java.util.stream.Collectors;
import modelo.EntidadesStatic;
import modelo.GenMunicipios;

/**
 *
 * @author adminlinux
 */
public class CiudadesController {
  private static  int i=0; 
    public static List<GenMunicipios> getAllRecords()
    {
        return EntityManagerGeneric.em.createQuery("select c from GenMunicipios c")
                                      .getResultList();
    }
public static void load_asciudades()
{
   EntidadesStatic.as_ciudades=new String[EntidadesStatic.li_ciudades.size()];
   int i=0;
   for(GenMunicipios m:EntidadesStatic.li_ciudades)
   {
       EntidadesStatic.as_ciudades[i]=m.getCodigo()+"-"+m.getNombre();
       i++;
   }
} 
public static void load_asproductos()
{
   EntidadesStatic.as_productos=new String[EntidadesStatic.li_productos.size()];
   i=0;
   EntidadesStatic.li_productos.stream().forEach(p->{
        EntidadesStatic.as_productos[i]=p.getCodigobarras()+"-"+p.getNombre();
        i++;
   });
  
}
public static void load_asservicios()
{
   EntidadesStatic.as_servicios=new String[EntidadesStatic.li_servicios.size()];
  i=0; 
   EntidadesStatic.li_servicios.stream().forEach(p->{
   EntidadesStatic.as_servicios[i]=p.getCodigobarras()+"-"+p.getNombre();
   i++;
   });
  
}
  public  static void findciudadporcodigo(String codigo)
{
   String[] _asciudad=codigo.split("-");
   List<GenMunicipios> _li_GenMunicipioses=EntidadesStatic.li_ciudades.stream().filter(e-> (e.getCodigo().equals(_asciudad[0]))).collect(Collectors.toList());
   if(_li_GenMunicipioses.size()>0)
   {
       EntidadesStatic.es_ciudades=_li_GenMunicipioses.get(0);
    }
}
}
