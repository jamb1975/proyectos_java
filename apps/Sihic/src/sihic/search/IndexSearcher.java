package sihic.search;

import java.io.BufferedReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;
import modelo.ActividadesEconomicas;
import modelo.AgnCitas;
import modelo.AgnConsultorios;
import modelo.AgnMedicos;
import modelo.ComprobanteCausacionEgreso;
import modelo.Puc;
import modelo.Factura;
import modelo.FacturaItem;
import modelo.FacturaProveedores;
import modelo.GenConvenios;
import modelo.GenEapb;
import modelo.GenEstadosCiviles;
import modelo.GenEtnias;
import modelo.GenHorasCita;
import modelo.GenMunicipios;
import modelo.GenNivelesEducativos;
import modelo.GenNivelesUsuarios;
import modelo.GenPacientes;
import modelo.GenProfesiones;
import modelo.GenSexo;
import modelo.GenTiposAfiliacion;
import modelo.GenTiposDocumentos;
import modelo.GenTiposUsuarios;
import modelo.GenZonaResidencia;
import modelo.HclCie10;
import modelo.HclCups;
import modelo.Kardex;
import modelo.Producto;
import modelo.Proveedores;
import modelo.RetencionFuente;
import modelo.Sucursales;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.grouping.SearchGroup;
import service.HclHistoriasClinicasController;
import sihic.SihicApp;
import sihic.controlador.ConComprobanteGastosControllerClient;
import sihic.controlador.ConComprobanteIngresoControllerClient;
import sihic.controlador.HclHistoriasClinicasControllerClient;
import sihic.controlador.ProductoControllerClient;
import sihic.general.LoadChoiceBoxGeneral;

/**
 * Class for searching the index
 */
public class IndexSearcher {

    private final static List<SearchGroup> searchGroups = new ArrayList<>();
    private ConComprobanteGastosControllerClient conComprobanteGastosControllerClient;
    private ConComprobanteIngresoControllerClient conComprobanteIngresoControllerClient;
    List<String> results_files = new ArrayList<String>();
    private HclHistoriasClinicasControllerClient hclHistoriasClinicasControllerClient;
    private ProductoControllerClient productoControllerClient;
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
        conComprobanteGastosControllerClient = new ConComprobanteGastosControllerClient();
        conComprobanteIngresoControllerClient = new ConComprobanteIngresoControllerClient();
        hclHistoriasClinicasControllerClient=new HclHistoriasClinicasControllerClient();
        productoControllerClient=new ProductoControllerClient();
    }

    public List<Puc> search(String searchString) throws ParseException {

        return SihicApp.li_conpuc.stream().filter(line -> (line.getCodigo().toLowerCase().contains(searchString.toLowerCase()) || line.getNombre().toLowerCase().contains(searchString.toLowerCase())) && line.getCodigo().length()>=6) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());

    }

    public List<ComprobanteCausacionEgreso> searchCg(String searchString) throws ParseException {

        return conComprobanteGastosControllerClient.li_conConComprobanteGastos(searchString);

    }

    public List<GenEapb> searchEapb(String searchString) throws ParseException {

        return SihicApp.l_eapb.stream().filter(line -> line.getNombre().toUpperCase().contains(searchString.toUpperCase()) || line.getCodigo().toUpperCase().contains(searchString.toUpperCase())) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());

    }

    public List<HclCups> searchHclCups(String searchString) throws ParseException {

        return hclHistoriasClinicasControllerClient.getCups(searchString); //SihicApp.li_hclcups.stream().filter(line -> line.getCodigo().toUpperCase().contains(searchString.toUpperCase()) || line.getDescripcion().toUpperCase().contains(searchString.toUpperCase())) //filters the line, equals to "mkyong"
                //.collect(Collectors.toList());

    }
    public List<Producto> searchProductos2(String searchString) throws ParseException {
        System.out.println("sise prodcupots->" + SihicApp.li_producto.size());
        productoControllerClient.findproductosall(searchString);//SihicApp.li_producto.stream().filter(line -> (line.getCodigo().toUpperCase().contains(searchString.toUpperCase()) || line.getNombre().toUpperCase().contains(searchString.toUpperCase())) ) //filters the line, equals to "mkyong"
               // .collect(Collectors.toList());
               return SihicApp.li_producto;

    }
    public List<Producto> searchProductos(String searchString) throws ParseException {
       productoControllerClient.findproductosall(searchString);//SihicApp.li_producto.stream().filter(line -> (line.getCodigo().toUpperCase().contains(searchString.toUpperCase()) || line.getNombre().toUpperCase().contains(searchString.toUpperCase())) ) //filters the line, equals to "mkyong"
               // .collect(Collectors.toList());
               return SihicApp.li_producto;

    }

    public List<Kardex> searchProductosKardex(String searchString) throws ParseException {
         productoControllerClient.li_findproductosKardex(searchString);
        return SihicApp.li_productosKardex;
    }

    public List<Kardex> searchServiciosKardex(String searchString) throws ParseException {
         productoControllerClient.li_findserviciosKardex(searchString);
        return SihicApp.li_serviciosKardex;
    }

    public List<GenMunicipios> searchGenMunicipios(String searchString) throws ParseException {

        return SihicApp.getL_genmunicipios().stream().filter(line -> line.getCodigo().toUpperCase().contains(searchString.toUpperCase()) || line.getNombre().toUpperCase().contains(searchString.toUpperCase())) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());
    }

    public List<GenProfesiones> searchGenProfesiones(String searchString) throws ParseException {

        return SihicApp.getL_genprofesiones().stream().filter(line -> line.getCodigo().toUpperCase().contains(searchString.toUpperCase()) || line.getDescripcion().toUpperCase().contains(searchString.toUpperCase())) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());
    }

    public List<FacturaItem> searchFacturaItem(String searchString) throws ParseException {

        return conComprobanteIngresoControllerClient.findfacturaitem(searchString);
    }

    public List<Factura> searchFactura(String searchString) throws ParseException {

        return SihicApp.s_facturaControllerClient.getrecordscartera(searchString);
    }

    public List<Factura> searchFacturafind(String searchString) throws ParseException {

        return SihicApp.s_facturaControllerClient.getRecordsfind(searchString);
    }

    public List<HclCie10> searchHclCie10(String searchString) throws ParseException {

        return hclHistoriasClinicasControllerClient.getCie10(searchString);
//SihicApp.li_hclcie10.stream().filter(line -> line.getCodigo().toUpperCase().contains(searchString.toUpperCase()) || line.getDescripcion().toUpperCase().contains(searchString.toUpperCase())) //filters the line, equals to "mkyong"
               // .collect(Collectors.toList());

    }

    public static List<Producto> searchProductosServicios(String searchString) throws ParseException {

        return SihicApp.li_serviciosProductos.stream().filter(line -> line.getCodigo().toUpperCase().contains(searchString.toUpperCase())) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());

    }

    public List<ActividadesEconomicas> searchActividadesEconomicas(String searchString) throws ParseException {

        return SihicApp.li_actividadeseconomicas.stream().filter(line -> line.getCodigo_ciiu_0079().toUpperCase().contains(searchString.toUpperCase()) || line.getDescripcion().toUpperCase().contains(searchString.toUpperCase())) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());

    }

    public List<RetencionFuente> searchretencionfuente(String searchString) throws ParseException {

        return SihicApp.li_retencionfuente.stream().filter(line -> String.valueOf(line.getCodigo()).contains(searchString.toUpperCase()) || line.getConcepto().toUpperCase().contains(searchString.toUpperCase())) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());

    }

    public List<FacturaProveedores> searchfacturaproveedores(String searchString) throws ParseException {

        return SihicApp.facturaProveedoresControllerClient.getRecordsCartera(searchString);

    }
   public List<GenPacientes> searchgenpacientes(String searchString) throws ParseException {

        return SihicApp.genPersonasControllerClient.getGenpacientes(searchString);

    }
    public List<GenTiposDocumentos> searchgentiposdocumentos(String searchString) throws ParseException {

        return SihicApp.getL_gentiposdocumentos().stream().filter(e->e.getNombre().toUpperCase().contains(searchString.toUpperCase())||e.getSigla().toUpperCase().contains(searchString.toUpperCase())).collect(Collectors.toList());

    }
    public List<GenSexo> searchgensexo(String searchString) throws ParseException {

        return SihicApp.getL_gensexo().stream().filter(e->e.getDescripcion().toUpperCase().contains(searchString.toUpperCase())||e.getSigla().toUpperCase().contains(searchString.toUpperCase())).collect(Collectors.toList());

    }
    public List<GenEtnias> searchgenetnias(String searchString) throws ParseException {

        return SihicApp.getL_genetnias().stream().filter(e->e.getNombre().toUpperCase().contains(searchString.toUpperCase())).collect(Collectors.toList());

    }
    public List<GenNivelesEducativos> searchgenniveleseducativos(String searchString) throws ParseException {

        return SihicApp.getL_genniveleseducativos().stream().filter(e->e.getDescripcion().toUpperCase().contains(searchString.toUpperCase())).collect(Collectors.toList());

    }
    public List<GenZonaResidencia> searchgenzonaresidencia(String searchString) throws ParseException {

        return SihicApp.getL_genzonaresidencia().stream().filter(e->e.getDescripcion().toUpperCase().contains(searchString.toUpperCase())).collect(Collectors.toList());

    }
    public List<GenEstadosCiviles> searchgenestadosciviles(String searchString) throws ParseException {

        return SihicApp.getL_genestadosciviles().stream().filter(e->e.getNombre().toUpperCase().contains(searchString.toUpperCase())).collect(Collectors.toList());

    }
     public List<GenTiposAfiliacion> searchgentiposafiliacion(String searchString) throws ParseException {

        return SihicApp.l_tiposafiliacion.stream().filter(e->e.getDescripcion().toUpperCase().contains(searchString.toUpperCase())).collect(Collectors.toList());

    }
     public List<GenTiposUsuarios> searchgentiposusuarios(String searchString) throws ParseException {

        return SihicApp.l_tiposusuarios.stream().filter(e->e.getNombre().toUpperCase().contains(searchString.toUpperCase())).collect(Collectors.toList());

    }
     public List<GenNivelesUsuarios> searchgennivelesusuarios(String searchString) throws ParseException {

        return SihicApp.l_nivelesusuarios.stream().filter(e->e.getNivel()==Integer.valueOf(searchString)).collect(Collectors.toList());

    }
     public List<GenConvenios> searchgenconvenios(String searchString) throws ParseException {

           if(LoadChoiceBoxGeneral.cb_conseuctivosnofactutasporsucursal.getValue().toString().substring(0,1).equals("A")|| SihicApp.s_factura.getPrefijo().substring(0,1).equals("P")){
         return SihicApp.l_conveniosp.stream().filter(e->e.getGenEapb().getId().equals(SihicApp.s_genEapb.getId()) && (e.getNumerocontrato().toUpperCase().contains(searchString)|| e.getGenEapb().getNombre().toUpperCase().contains(searchString))).collect(Collectors.toList());
       } 
       else
       {
        return SihicApp.l_conveniosp.stream().filter(e->e.getTipoconvenio().equals("P")&& e.getNumerocontrato().toUpperCase().contains(searchString)).collect(Collectors.toList());
   
       }

    }
     public List<GenHorasCita> searchgenhorascitas(String searchString) throws ParseException {

            SihicApp.genHorasCitaControllerClient.getRecords(searchString,SihicApp.pfechacita,SihicApp.agnMedicos.getId());
            return SihicApp.li_horasdisponibles;
    }
    public List<AgnMedicos> searchagnmedicos(String searchString) throws ParseException {

        return SihicApp.l_medicos.stream().filter(e->e.getGenPersonas().getNombres().toUpperCase().contains(searchString.toUpperCase()) || e.getGenPersonas().getDocumento().contains(searchString)).collect(Collectors.toList());

    }
    public List<AgnConsultorios> searchagnconsultorios(String searchString) throws ParseException {

        return SihicApp.l_consultorios.stream().filter(e->String.valueOf(e.getNumero()).contains(searchString) || e.getDescripcion().toUpperCase().contains(searchString.toUpperCase())).collect(Collectors.toList());

    }
    public List<Sucursales> searchsucursales(String searchString) throws ParseException {

        return SihicApp.li_Sucursales.stream().filter(e->e.getCodigo().contains(searchString) || e.getNombre().toUpperCase().contains(searchString.toUpperCase())).collect(Collectors.toList());

    }
    public List<String> searchrh(String searchString) throws ParseException {

        SihicApp.li_rh.clear();
        SihicApp.li_rh.add("O+");
        SihicApp.li_rh.add("O-");
        SihicApp.li_rh.add("A+");
        SihicApp.li_rh.add("A-");
        SihicApp.li_rh.add("B+");
        SihicApp.li_rh.add("B-");
        SihicApp.li_rh.add("NO SABE");
        SihicApp.li_rh.add("NO RESPONDE");
        return SihicApp.li_rh.stream().filter(e->e.toUpperCase().contains(searchString.toUpperCase())).collect(Collectors.toList());

    }
    public List<Proveedores> searchproveedores(String searchString) throws ParseException {

        return SihicApp.li_proveedores.stream().filter(e->e.getNo_ident().toUpperCase().trim().startsWith(searchString.trim().toUpperCase()) || e.getNombre().toUpperCase().trim().contains(searchString.toUpperCase().trim())).collect(Collectors.toList());

    }
     public List<AgnCitas> searchcitas(String searchString) throws ParseException {

         String fechacurrent=sihic.util.UtilDate.s_formatoyearmesdia(new Date());
        return SihicApp.agnCitasControllerClient.agncitasagendada(searchString,fechacurrent);

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
