package isoftconta.searchpopover;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import isoftconta.servicios.CiudadesController;
import isoftconta.servicios.TercerosController;
import modelo.ActividadesEconomicas;
import modelo.EntidadesStatic;
import modelo.GenMunicipios;
import modelo.Puc;
import modelo.Terceros;



/**
 * Class for searching the index
 */
public class IndexSearcher {
  List<String> results_files = new ArrayList<String>();

   

    public IndexSearcher() {
     
    
    }

    public List<Puc> searchencatalogocuentas(String searchString) throws ParseException {

        return EntidadesStatic.li_puc.stream().filter(line -> (line.getCodigo().toLowerCase().startsWith(searchString.toLowerCase()) || line.getNombre().toLowerCase().startsWith(searchString.toLowerCase())) && line.getCodigo().length()>=6) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());

    }
    public List<Terceros> searchenterceros(String searchString) throws ParseException {
          
        return TercerosController.getGenpersonas(searchString);

    }
     public List<GenMunicipios> searchenciudades() throws ParseException {
          
        return CiudadesController.getAllRecords();

    }

    
    /**
     * Simple command line test application
     *
     * @param args command line arguments
     * @throws Exception for maps errors
     */
    public static void main(String[] args) throws Exception {

    }

     }

