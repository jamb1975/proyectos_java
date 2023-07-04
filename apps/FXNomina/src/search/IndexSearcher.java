package search;

import fxnomina.FXNomina;
import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import modelo.ActividadesEconomicas;
import modelo.GenEapb;
import modelo.GenMunicipios;
import modelo.GenSexo;
import modelo.GenTiposDocumentos;

import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.grouping.SearchGroup;
import general.LoadChoiceBoxGeneral;


/**
 * Class for searching the index
 */
public class IndexSearcher {

    private final static List<SearchGroup> searchGroups = new ArrayList<>();
     List<String> results_files = new ArrayList<String>();

    static {
        for (DocumentType dt : DocumentType.values()) {
            SearchGroup searchGroup = new SearchGroup();
            searchGroup.groupValue = dt.toString();
            searchGroup.sortValues = new Comparable[]{5f};
            searchGroups.add(searchGroup);
        }
    }
    private org.apache.lucene.search.IndexSearcher searcher;

    public IndexSearcher() {
     }

   

    public List<GenEapb> searchEapb(String searchString) throws ParseException {

        return FXNomina.l_eapb.stream().filter(line -> line.getNombre().toUpperCase().contains(searchString.toUpperCase()) || line.getCodigo().toUpperCase().contains(searchString.toUpperCase())) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());

    }

    
    public List<GenMunicipios> searchGenMunicipios(String searchString) throws ParseException {

        return FXNomina.getL_genmunicipios().stream().filter(line -> line.getCodigo().toUpperCase().contains(searchString.toUpperCase()) || line.getNombre().toUpperCase().contains(searchString.toUpperCase())) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());
    }

    
    public List<ActividadesEconomicas> searchActividadesEconomicas(String searchString) throws ParseException {

        return FXNomina.li_actividadeseconomicas.stream().filter(line -> line.getCodigo_ciiu_0079().toUpperCase().contains(searchString.toUpperCase()) || line.getDescripcion().toUpperCase().contains(searchString.toUpperCase())) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());

    }

    
    public List<GenTiposDocumentos> searchgentiposdocumentos(String searchString) throws ParseException {

        return FXNomina.l_gentiposdocumentos.stream().filter(e->e.getNombre().toUpperCase().contains(searchString.toUpperCase())||e.getSigla().toUpperCase().contains(searchString.toUpperCase())).collect(Collectors.toList());

    }
    public List<GenSexo> searchgensexo(String searchString) throws ParseException {

        return FXNomina.getL_gensexo().stream().filter(e->e.getDescripcion().toUpperCase().contains(searchString.toUpperCase())||e.getSigla().toUpperCase().contains(searchString.toUpperCase())).collect(Collectors.toList());

    }
    
    public List<String> searchrh(String searchString) throws ParseException {

        FXNomina.li_rh.clear();
        FXNomina.li_rh.add("O+");
        FXNomina.li_rh.add("O-");
        FXNomina.li_rh.add("A+");
        FXNomina.li_rh.add("A-");
        FXNomina.li_rh.add("B+");
        FXNomina.li_rh.add("B-");
        FXNomina.li_rh.add("NO SABE");
        FXNomina.li_rh.add("NO RESPONDE");
        return FXNomina.li_rh.stream().filter(e->e.toUpperCase().contains(searchString.toUpperCase())).collect(Collectors.toList());

    }
    
    /**
     * Simple command line test application
     *
     * @param args command line arguments
     * @throws Exception for maps errors
     */
    public static void main(String[] args) throws Exception {

    }

    public void doPagingSearch(BufferedReader in, org.apache.lucene.search.IndexSearcher searcher, Query query,
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
        results_files = null;
        results_files = new ArrayList<String>();
        for (int i = start; i < end; i++) {
            if (raw) {                              // output raw format
                System.out.println("doc=" + hits[i].doc + " score=" + hits[i].score);
                continue;
            }

            Document doc = searcher.doc(hits[i].doc);
            String path = doc.get("path");
            if (path != null) {
                System.out.println((i + 1) + ". " + path);
                results_files.add((i + 1) + ". " + path);
                String title = doc.get("title");
                if (title != null) {
                    System.out.println("   Title: " + doc.get("title"));
                }
            } else {
                System.out.println((i + 1) + ". " + "No path for this document");
            }

        }

    }
}
