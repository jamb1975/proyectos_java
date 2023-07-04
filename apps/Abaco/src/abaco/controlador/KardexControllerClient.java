/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.controlador;

import abaco.AbacoApp;
import static abaco.AbacoApp.kardexControllerClient;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import model.Kardex;
import service.KardexController;



/**
 * Jersey REST client generated for REST resource:KardexController
 [model.kardex]<br>
 * USAGE:
 * <pre>
        KardexControllerClient client = new KardexControllerClient();
        Object response = client.XXX(...);
        // do whatever with response
        client.close();
 </pre>
 *
 * @author karolyani
 */
public class KardexControllerClient {
     static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
} 
    private KardexController kardexController;
    

    public KardexControllerClient() {
       kardexController=new KardexController();
    }

    public String countREST() {
        
        return kardexController.countREST();
    }

    public void   edit_XML(Object requestEntity,  String id)  {
         kardexController.edit(Long.valueOf(id),(Kardex)requestEntity);
    }
    

    


  

    public List<Kardex>  findRange_XML(String datefrom, String dateto,String idprod,String otro) throws ParseException {
          return kardexController.findRange(datefrom, dateto, Long.valueOf(idprod));
    }

    public void create_XML(Object requestEntity)  {
      kardexController.create((Kardex)requestEntity);
    }

   

    public List<Kardex>  findAll_XML()   {
         return kardexController.findAll();
    }

    public Kardex find_XML(String id_product,String tipo,String no_fac)  {
     
        
        return kardexController.find(Long.valueOf(id_product), tipo);
    }

    public void remove(String id)  {
         kardexController.remove(Long.valueOf(id));
    }

    public void close() {
        kardexController=null;
    }
    public List<Kardex> getRecords()
    {
        AbacoApp.li_kardex =kardexController.getRecords();
       List<Kardex> l_kardex= AbacoApp.li_kardex.stream().filter(distinctByKey(b ->b.getProducto())).collect(Collectors.toList());//.distinct().collect(Collectors.toList());
       AbacoApp.li_kardex=l_kardex;
        return AbacoApp.li_kardex;
    }  
     public List<Kardex> getRecords(String search,String datefrom,String dateto) throws ParseException
    {
        return kardexController.getRecords( search, datefrom, dateto);
    }  
     public Kardex update()
     {
         return kardexController.update(AbacoApp.s_kardex);
     }
     public void delete()
     {
          kardexController.delete(AbacoApp.s_kardex);
     }
    
}
