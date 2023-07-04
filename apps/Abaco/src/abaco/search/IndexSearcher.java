package abaco.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import model.GenMunicipios;
import model.Kardex;
import model.Producto;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.grouping.SearchGroup;
import abaco.AbacoApp;
import model.Categoria;
import model.Factura;
import model.Personas;
import model.Proveedores;

/**
 * Class for searching the index
 */
public class IndexSearcher {
    private final static List<SearchGroup> searchGroups = new ArrayList<>();
     List<String> results_files = new ArrayList<String>();
    static {
        for (DocumentType dt: DocumentType.values()){
            SearchGroup searchGroup = new SearchGroup();
            searchGroup.groupValue = dt.toString();
            searchGroup.sortValues = new Comparable[]{5f};
            searchGroups.add(searchGroup);
        }
    }
    private org.apache.lucene.search.IndexSearcher searcher;
  
   
    public IndexSearcher()
    {
    }

   public List<Producto> searchProductos(String searchString) throws ParseException {
       
       return  AbacoApp.li_producto.stream().filter(line ->line.getCodigobarras().toUpperCase().contains(searchString.toUpperCase()) || line.getNombre().toUpperCase().contains(searchString.toUpperCase()) )	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList());	
      
     
   }
   public List<Kardex> searchProductosKardex(String searchString) throws ParseException {
     
      return  AbacoApp.li_kardex.stream().filter(line -> line.getProducto().getCodigobarras().toUpperCase().contains(searchString.toUpperCase()) ||  line.getProducto().getNombre().toUpperCase().contains(searchString.toUpperCase()) )	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList());	
   }
   public List<Kardex> searchServiciosKardex(String searchString) throws ParseException {
     
      return  AbacoApp.li_kardexservicios.stream().filter(line -> line.getProducto().getCodigobarras().toUpperCase().contains(searchString.toUpperCase()) ||  line.getProducto().getNombre().toUpperCase().contains(searchString.toUpperCase()) )	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList());	
   }
   public List<GenMunicipios> searchGenMunicipios(String searchString) throws ParseException {
     
      return  AbacoApp.li_genmunicipios.stream().filter(line -> line.getCodigo().toUpperCase().contains(searchString.toUpperCase()) ||  line.getNombre().toUpperCase().contains(searchString.toUpperCase()))	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList());	
   }
   public List<Categoria> searchCategoria(String searchString) throws ParseException {
     
      return  AbacoApp.li_categoria.stream().filter(line -> line.getCodigo().toUpperCase().contains(searchString.toUpperCase()) ||  line.getM_NombreCat().toUpperCase().contains(searchString.toUpperCase()))	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList());	
   }
   public List<Personas> searchPersonas(String searchString) throws ParseException {
     
      return  AbacoApp.li_genpersonas.stream().filter(line -> line.getNo_ident().toUpperCase().contains(searchString.toUpperCase()) ||  line.getNombre().toUpperCase().contains(searchString.toUpperCase()))	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList());	
   }
   public List<Proveedores> searchProveedores(String searchString) throws ParseException {
     
      return  AbacoApp.li_proveedores.stream().filter(line -> line.getNo_ident().toUpperCase().contains(searchString.toUpperCase()) ||  line.getNombre().toUpperCase().contains(searchString.toUpperCase()))	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList());	
   }
   public List<Factura> searchFacturas(String searchString) throws ParseException {
     
      return  AbacoApp.facturaControllerClient.getFacturasACredito(searchString);
   }
    /**
     * Simple command line test application
     * @param args command line arguments
     * @throws Exception for maps errors
     */
    public static void main(String[] args) throws Exception {
        
    }
 public  void doPagingSearch(BufferedReader in, org.apache.lucene.search.IndexSearcher searcher, Query query, 
                                     int hitsPerPage, boolean raw, boolean interactive) throws IOException {
 
    // Collect enough docs to show  pages
    TopDocs results = searcher.search(query, 1 * hitsPerPage);
    ScoreDoc[] hits = results.scoreDocs;
    
    int numTotalHits = results.totalHits;
    System.out.println(numTotalHits + " total matching documents");

    int start = 0;
    int end = Math.min(numTotalHits, hitsPerPage);
        
     

        hits = searcher.search(query, numTotalHits).scoreDocs;
      
      
      end = Math.min(hits.length, start + hitsPerPage);
      results_files=null;
      results_files=new ArrayList<String>();
      for (int i = start; i < end; i++) {
        if (raw) {                              // output raw format
          System.out.println("doc="+hits[i].doc+" score="+hits[i].score);
          continue;
        }

        Document doc = searcher.doc(hits[i].doc);
        String path = doc.get("path");
        if (path != null) {
          System.out.println((i+1) + ". " + path);
          results_files.add((i+1) + ". " + path);
          String title = doc.get("title");
          if (title != null) {
            System.out.println("   Title: " + doc.get("title"));
          }
        } else {
          System.out.println((i+1) + ". " + "No path for this document");
        }
                  
      }

      
      
    
  }   
}
