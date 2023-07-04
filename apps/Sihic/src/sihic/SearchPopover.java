package sihic;

import java.text.ParseException;
import java.util.ArrayList;
import sihic.control.Popover;
import sihic.control.SearchBox;
import sihic.search.IndexSearcher;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
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
import modelo.GenPersonas;
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

/**
 * Implementation of popover to show search results
 */
public class SearchPopover extends Popover {

    private final SearchBox SearchBox;
    private final PageBrowser pageBrowser;
    private IndexSearcher indexSearcher;
    private Tooltip searchErrorTooltip = null;
    private Timeline searchErrorTooltipHidder = null;
    private SearchResultPopoverList searchResultPopoverList;
    private Object OClass;
    private List<Puc> results = new ArrayList<Puc>();
    private List<ComprobanteCausacionEgreso> li_conComprobanteGastos = new ArrayList<ComprobanteCausacionEgreso>();
    private List<GenEapb> li_geneapb = new ArrayList<>();
    private List<HclCups> li_hclcups = new ArrayList<>();
    private List<Producto> li_producto = new ArrayList<>();
    private List<Kardex> li_kardex = new ArrayList<>();
    private List<GenMunicipios> li_genmunicipios = new ArrayList<>();
    private List<GenProfesiones> li_genprofesiones = new ArrayList<>();
    private List<Factura> li_factura = new ArrayList<>();
    private List<FacturaItem> li_facturaitem = new ArrayList<>();
    private List<HclCie10> li_hclcie10 = new ArrayList<>();
    private List<ActividadesEconomicas> li_ActividadesEconomicases = new ArrayList<>();
    private List<RetencionFuente> li_retencionfuente = new ArrayList<>();
    private List<FacturaProveedores> li_facFacturaProveedoreses = new ArrayList<>();
    private List<GenTiposDocumentos> li_tipoDocumentoses = new ArrayList<>();
    private List<GenSexo> li_GenSexos = new ArrayList<>();
    private List<GenEtnias> li_geEtniases = new ArrayList<>();
    private List<GenNivelesEducativos> li_GenNivelesEducativoses = new ArrayList<>();
    private List<GenZonaResidencia> li_GenZonaResidencias = new ArrayList<>();
    private List<GenEstadosCiviles> li_genEstadosCivileses = new ArrayList<>();
    private List<GenTiposAfiliacion> li_GenTiposAfiliacions = new ArrayList<>();
    private List<GenTiposUsuarios> li_GenTiposUsuarioses = new ArrayList<>();
    private List<GenNivelesUsuarios> li_GenNivelesUsuarioses = new ArrayList<>();
    private List<GenPacientes>li_GenPacientes=new ArrayList<>();
    private List<GenConvenios>li_Convenios=new ArrayList<>();
    private List<GenHorasCita>li_GenHorasCitas=new ArrayList<>();
    private List<AgnMedicos>li_Medicos=new ArrayList<>();
    private List<AgnConsultorios>li_Consultorios=new ArrayList<>();
    private List<Sucursales>li_Sucursales=new ArrayList<>(); 
    private List<String>li_rh=new ArrayList<>();
    private List<Proveedores>li_proveedores=new ArrayList<>();
    private List<AgnCitas>li_agncitas=new ArrayList<>();
    private boolean filterservicios;

    public SearchPopover(final SearchBox SearchBox, PageBrowser pageBrowser, Object OClass, boolean filterservicios) {
        super();
        this.SearchBox = SearchBox;
        this.pageBrowser = pageBrowser;
        this.OClass = OClass;
        
        this.filterservicios = filterservicios;
        getStyleClass().add("right-tooth");
        setPrefWidth(600);
        SearchBox.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
            try {
                updateResults();
            } catch (ParseException ex) {
                Logger.getLogger(SearchPopover.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
       /* SearchBox.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
               
            if (ConPuc.class == OClass.getClass()) {
                if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                    ConPuc selectedItem = (ConPuc) searchResultPopoverList.getSelectionModel().getSelectedItem();

                    searchResultPopoverList.itemClicked(selectedItem);
                    this.SearchBox.setText(selectedItem.getCodigo() + " " + selectedItem.getNombre());
                    hide();

                }

            }

        });*/
        SearchBox.focusedProperty().addListener(new ChangeListener<Boolean>()
{
    @Override
    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
    {
      hide();
    }
});
        SearchBox.addEventFilter(KeyEvent.ANY, (KeyEvent t) -> {
            if (t.getCode() == KeyCode.DOWN
                    || t.getCode() == KeyCode.UP
                    || t.getCode() == KeyCode.PAGE_DOWN
                    || (t.getCode() == KeyCode.HOME && (t.isControlDown() || t.isMetaDown()))
                    || (t.getCode() == KeyCode.END && (t.isControlDown() || t.isMetaDown()))
                    || t.getCode() == KeyCode.PAGE_UP) {
                searchResultPopoverList.fireEvent(t);
                t.consume();
            } else if (t.getCode() == KeyCode.ENTER) {
                //  t.consume();
                if (t.getEventType() == KeyEvent.KEY_PRESSED) {
                        if (ComprobanteCausacionEgreso.class == OClass.getClass()) {
                            if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                ComprobanteCausacionEgreso selectedItem = (ComprobanteCausacionEgreso) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                searchResultPopoverList.itemClicked(selectedItem);
                                this.SearchBox.setText(selectedItem.getProveedores().getNo_ident());
                                hide();

                            }
                        } else {
                            if (GenEapb.class == OClass.getClass()) {
                                if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                    if(filterservicios)
                                    {     
                                    SihicApp.s_genEapb = (GenEapb) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                    searchResultPopoverList.itemClicked(SihicApp.s_genEapb);
                                    this.SearchBox.setText(SihicApp.s_genEapb.getNombre()+" Codigo:"+SihicApp.s_genEapb.getCodigo());
                                    hide();
                                    }
                                    else
                                    {
                                    SihicApp.aseguradora = (GenEapb) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                   // searchResultPopoverList.itemClicked(SihicApp.aseguradora);
                                    this.SearchBox.setText(SihicApp.aseguradora.getCodigo() + " " + SihicApp.aseguradora.getNombre());
                                    hide(); 
                                    }

                                }
                            } else {
                                if (HclCups.class == OClass.getClass()) {
                                    if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                        SihicApp.s_hclCups = (HclCups) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                        searchResultPopoverList.itemClicked(SihicApp.s_hclCups);
                                        this.SearchBox.setText(SihicApp.s_hclCups.getCodigo() + " " + SihicApp.s_hclCups.getDescripcion());
                                        hide();

                                    }
                                } else {
                                    if (Producto.class == OClass.getClass()) {
                                        if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                            SihicApp.s_producto = (Producto) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                            searchResultPopoverList.itemClicked(SihicApp.s_producto);
                                            if (filterservicios) {
                                                this.SearchBox.setText(SihicApp.s_producto.getCodigo() + " || " + SihicApp.s_producto.getNombre());
                                            } else {
                                                SihicApp.s_producto.getCodigo();
                                            }
                                            searchResultPopoverList.getSelectionModel().select(-1);
                                            hide();
                                        }
                                    } else {
                                        if (Kardex.class == OClass.getClass()) {
                                            if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                SihicApp.s_kardex = (Kardex) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                searchResultPopoverList.itemClicked(SihicApp.s_kardex);

                                                this.SearchBox.setText(SihicApp.s_kardex.getProducto().getCodigo() + " " + SihicApp.s_kardex.getProducto().getNombre());

                                                hide();
                                            }
                                        } else {
                                            if (OClass.getClass() == GenMunicipios.class) {

                                                if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                    SihicApp.s_genMunicipios = (GenMunicipios) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                    searchResultPopoverList.itemClicked(SihicApp.s_genMunicipios);
                                                    this.SearchBox.setText(SihicApp.s_genMunicipios.getNombre() + "-" + SihicApp.s_genMunicipios.getGenDepartamentos().getNombre());
                                                    hide();
                                                }
                                            } else {
                                                if (OClass.getClass() == GenProfesiones.class) {
                                                    if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                        SihicApp.s_GenProfesiones = (GenProfesiones) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                        searchResultPopoverList.itemClicked(SihicApp.s_GenProfesiones);
                                                        this.SearchBox.setText(SihicApp.s_GenProfesiones.getCodigo() + "-" + SihicApp.s_GenProfesiones.getDescripcion());
                                                        hide();
                                                    }
                                                } else {
                                                    if (OClass.getClass() == Factura.class) {
                                                        if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                            SihicApp.s_factura = (Factura) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                            searchResultPopoverList.itemClicked(SihicApp.s_factura);
                                                            try {
                                                                if (SihicApp.s_factura.getPrefijo().equals("A") || SihicApp.s_factura.getPrefijo().equals("P")) {
                                                                    if (filterservicios == false) {
                                                                        this.SearchBox.setText(SihicApp.s_factura.getNofacturacerosizquierda() + " || " + SihicApp.s_factura.getGenEapb().getNombre() + " || " + SihicApp.s_factura.getGenTiposUsuarios().getNombre());
                                                                    } else {
                                                                        this.SearchBox.setText(SihicApp.s_factura.getNofacturacerosizquierda() + " || " + SihicApp.s_factura.getGenEapb().getNombre() + " || " + SihicApp.s_factura.getGenTiposUsuarios().getNombre() + " || Saldo: " + SihicApp.s_factura.getTotalAmount().subtract(SihicApp.s_factura.getValor_abonos()));

                                                                    }
                                                                } else {
                                                                    if (filterservicios == true) {
                                                                        this.SearchBox.setText(SihicApp.s_factura.getNofacturacerosizquierda() + " || " + SihicApp.s_factura.getGenPersonas().getNombres() + " || Saldo: " + SihicApp.s_factura.getTotalAmount().subtract(SihicApp.s_factura.getValor_abonos()));
                                                                    } else {
                                                                        this.SearchBox.setText(SihicApp.s_factura.getNofacturacerosizquierda() + " || " + SihicApp.s_factura.getGenPersonas().getNombres());

                                                                    }
                                                                }
                                                            } catch (Exception e) {
                                                                this.SearchBox.setText(SihicApp.s_factura.getNofacturacerosizquierda());
                                                            }
                                                            hide();
                                                        }
                                                    } else {
                                                        if (OClass.getClass() == FacturaItem.class) {
                                                            if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                SihicApp.s_facturaitem = (FacturaItem) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                searchResultPopoverList.itemClicked(SihicApp.s_facturaitem);
                                                                this.SearchBox.setText(SihicApp.s_facturaitem.getConComprobanteProcedimiento().getConsecutivoComprobanteProcedimiento().getId().toString());
                                                                hide();
                                                            }

                                                        } else {
                                                            if (OClass.getClass() == HclCie10.class) {
                                                                if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                    SihicApp.hclCie10 = (HclCie10) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                    searchResultPopoverList.itemClicked(SihicApp.hclCie10);
                                                                    this.SearchBox.setText(SihicApp.hclCie10.getCodigo() + " || " + SihicApp.hclCie10.getDescripcion());
                                                                    hide();
                                                                }

                                                            } else {
                                                                if (OClass.getClass() == ActividadesEconomicas.class) {
                                                                    if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                        SihicApp.s_ActividadesEconomicas = (ActividadesEconomicas) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                        searchResultPopoverList.itemClicked(SihicApp.s_ActividadesEconomicas);
                                                                        this.SearchBox.setText(SihicApp.s_ActividadesEconomicas.getCodigo_ciiu_0079() + " || " + SihicApp.s_ActividadesEconomicas.getDescripcion());
                                                                        hide();
                                                                    }

                                                                } else {
                                                                    if (OClass.getClass() == FacturaProveedores.class) {
                                                                        if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                            SihicApp.s_FacturaProveedores = (FacturaProveedores) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                            searchResultPopoverList.itemClicked(SihicApp.s_FacturaProveedores);
                                                                            this.SearchBox.setText("N° Doc: " + SihicApp.s_FacturaProveedores.getNo_factura() + " || N° Ident: " + SihicApp.s_FacturaProveedores.getProveedores().getNo_ident() + " Nombre: " + SihicApp.s_FacturaProveedores.getProveedores().getNombre() + " Saldo: " + SihicApp.s_FacturaProveedores.getTotalAmount().subtract(SihicApp.s_FacturaProveedores.getValor_egresos()));
                                                                            hide();
                                                                        }
                                                                    }
                                                                    else
                                                                    {
                                                                         if (OClass.getClass() == GenTiposDocumentos.class)
                                                                         {
                                                                           if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                            SihicApp.genTiposDocumentos = (GenTiposDocumentos) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                            searchResultPopoverList.itemClicked(SihicApp.genTiposDocumentos);
                                                                            this.SearchBox.setText(SihicApp.genTiposDocumentos.getNombre());
                                                                            hide();
                                                                          }
                                                                        }
                                                                         else
                                                                         {
                                                                         if (OClass.getClass() == GenSexo.class)
                                                                         {
                                                                           if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                            SihicApp.genSexo = (GenSexo) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                            searchResultPopoverList.itemClicked(SihicApp.genSexo);
                                                                            this.SearchBox.setText(SihicApp.genSexo.getDescripcion());
                                                                            hide();
                                                                          }
                                                                        }
                                                                         else
                                                                         {
                                                                         if (OClass.getClass() == GenEtnias.class)
                                                                         {
                                                                           if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                            SihicApp.genEtnias = (GenEtnias) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                            searchResultPopoverList.itemClicked(SihicApp.genEtnias);
                                                                            this.SearchBox.setText(SihicApp.genEtnias.getNombre());
                                                                            hide();
                                                                          }
                                                                        }
                                                                       else
                                                                         {
                                                                          if (OClass.getClass() == GenNivelesEducativos.class)
                                                                          {
                                                                            if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                            SihicApp.genNivelesEducativos = (GenNivelesEducativos) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                            searchResultPopoverList.itemClicked(SihicApp.genNivelesEducativos);
                                                                            this.SearchBox.setText(SihicApp.genNivelesEducativos.getDescripcion());
                                                                            hide();
                                                                           }
                                                                          }
                                                                          else
                                                                          {
                                                                            if (OClass.getClass() == GenZonaResidencia.class)
                                                                            {
                                                                              if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                              SihicApp.genZonaResidencia = (GenZonaResidencia) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                              searchResultPopoverList.itemClicked(SihicApp.genZonaResidencia);
                                                                              this.SearchBox.setText(SihicApp.genZonaResidencia.getDescripcion());
                                                                               hide();
                                                                             }
                                                                            }
                                                                            else
                                                                            {
                                                                             if (OClass.getClass() == GenEstadosCiviles.class)
                                                                            {
                                                                              if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                              SihicApp.genEstadosCiviles = (GenEstadosCiviles) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                              searchResultPopoverList.itemClicked(SihicApp.genEstadosCiviles);
                                                                              this.SearchBox.setText(SihicApp.genEstadosCiviles.getNombre());
                                                                               hide();
                                                                             }
                                                                            }
                                                                             else
                                                                             {
                                                                               if (OClass.getClass() == GenTiposAfiliacion.class)
                                                                               {
                                                                              if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                              SihicApp.genTiposAfiliacion = (GenTiposAfiliacion) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                              searchResultPopoverList.itemClicked(SihicApp.genTiposAfiliacion);
                                                                              this.SearchBox.setText(SihicApp.genTiposAfiliacion.getDescripcion());
                                                                               hide();
                                                                              }
                                                                            }
                                                                             else
                                                                               {
                                                                               if (OClass.getClass() == GenTiposUsuarios.class)
                                                                               {
                                                                              if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                              SihicApp.genTiposUsuarios= (GenTiposUsuarios) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                              searchResultPopoverList.itemClicked(SihicApp.genTiposUsuarios);
                                                                              this.SearchBox.setText(SihicApp.genTiposUsuarios.getNombre());
                                                                               hide();
                                                                              }
                                                                            }
                                                                               else
                                                                               {
                                                                                   if (OClass.getClass() == GenNivelesUsuarios.class)
                                                                               {
                                                                              if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                              SihicApp.genNivelesUsuarios= (GenNivelesUsuarios) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                              searchResultPopoverList.itemClicked(SihicApp.genNivelesUsuarios);
                                                                              this.SearchBox.setText(String.valueOf(SihicApp.genNivelesUsuarios.getNivel()));
                                                                               hide();
                                                                              }
                                                                            }
                                                                             else
                                                                               {
                                                                                   if (OClass.getClass() == GenPacientes.class)
                                                                               {
                                                                              if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                              SihicApp.genPacientesTemp= (GenPacientes) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                              searchResultPopoverList.itemClicked(SihicApp.genPacientesTemp);
                                                                              this.SearchBox.setText(String.valueOf(SihicApp.genPacientesTemp.getGenPersonas().getDocumento()));
                                                                               hide();
                                                                              }
                                                                            }
                                                                                   else
                                                                                   {
                                                                               if (OClass.getClass() == GenConvenios.class)
                                                                               {
                                                                              if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                              SihicApp.genConvenios= (GenConvenios) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                              searchResultPopoverList.itemClicked(SihicApp.genConvenios);
                                                                              this.SearchBox.setText(String.valueOf(SihicApp.genConvenios.getNumerocontrato()+" %: "+SihicApp.genConvenios.getPorcentajedescuento()));
                                                                               hide();
                                                                              }
                                                                            }
                                                                               else
                                                                               {
                                                                               if (OClass.getClass() == GenHorasCita.class)
                                                                               {
                                                                                if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) 
                                                                                {
                                                                                  SihicApp.genHorasCita= (GenHorasCita) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                                  searchResultPopoverList.itemClicked(SihicApp.genHorasCita);
                                                                                 this.SearchBox.setText(SihicApp.genHorasCita.getHora()+":"+SihicApp.genHorasCita.getMinutos()+" "+ (SihicApp.genHorasCita.getZona()==0?"AM":"PM"));
                                                                                 hide();
                                                                               }
                                                                                
                                                                               }
                                                                               else
                                                                               {
                                                                               if (OClass.getClass() == AgnMedicos.class)
                                                                               {
                                                                                if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) 
                                                                                {
                                                                                  SihicApp.agnMedicos= (AgnMedicos) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                                  searchResultPopoverList.itemClicked(SihicApp.agnMedicos);
                                                                                 this.SearchBox.setText(SihicApp.agnMedicos.getGenPersonas().getNombres());
                                                                                 hide();
                                                                               }
                                                                                
                                                                               }
                                                                               else
                                                                               {
                                                                                 if (OClass.getClass() == AgnConsultorios.class)
                                                                               {
                                                                                if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) 
                                                                                {
                                                                                  SihicApp.agnConsultorios= (AgnConsultorios) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                                  searchResultPopoverList.itemClicked(SihicApp.agnConsultorios);
                                                                                 this.SearchBox.setText(SihicApp.agnConsultorios.getDescripcion());
                                                                                 hide();
                                                                               }
                                                                                
                                                                               }
                                                                               else
                                                                                 {
                                                                                    if (OClass.getClass() == Sucursales.class)
                                                                               {
                                                                                if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) 
                                                                                {
                                                                                  
                                                                                //  SihicApp.sucursales= (Sucursales) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                                  SihicApp.centrocostos= (Sucursales) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                                  searchResultPopoverList.itemClicked(SihicApp.sucursales);
                                                                                  this.SearchBox.setText(SihicApp.centrocostos.getNombre());
                                                                                 hide();
                                                                               }
                                                                                
                                                                                
                                                                               } 
                                                                                    else
                                                                                    {
                                                                                            if (OClass.getClass() == String.class)
                                                                                            {
                                                                                               if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) 
                                                                                               {
                                                                                                   SihicApp.rh= (String) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                                                   searchResultPopoverList.itemClicked(SihicApp.rh);
                                                                                                   this.SearchBox.setText(SihicApp.rh);
                                                                                                   hide();
                                                                                                }
                                                                                            }
                                                                                            else
                                                                                            {
                                                                                               if (OClass.getClass() == Proveedores.class)
                                                                                            {
                                                                                               if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) 
                                                                                               {
                                                                                                   SihicApp.s_proveedores= (Proveedores) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                                                   searchResultPopoverList.itemClicked(SihicApp.s_proveedores);
                                                                                                   this.SearchBox.setText(SihicApp.s_proveedores.getNo_ident()+" || "+ SihicApp.s_proveedores.getNombre());
                                                                                                   hide();
                                                                                                }
                                                                                            }
                                                                                             else
                                                                                               {
                                                                                                   if (OClass.getClass() == AgnCitas.class)
                                                                                            {
                                                                                               if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) 
                                                                                               {
                                                                                                   SihicApp.agnCitasTemp= (AgnCitas) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                                                   searchResultPopoverList.itemClicked(SihicApp.agnCitasTemp);
                                                                                                   this.SearchBox.setText(sihic.util.UtilDate.s_formatoyearmesdia(SihicApp.agnCitasTemp.getFechaHora())+" || "+ SihicApp.agnCitasTemp.getGenHorasCita().getHora()+":"+SihicApp.agnCitasTemp.getGenHorasCita().getMinutos()+"||"+SihicApp.agnCitasTemp.getGenPacientes().getGenPersonas().getNombres());
                                                                                                   hide();
                                                                                                }
                                                                                            }
                                                                                               }
                                                                                            }
                                                                                    }
                                                                                 }
                                                                               }
                                                                               }
                                                                               }
                                                                                   }
                                                                                  
                                                                               }      
                                                                               }
                                                                               }
                                                                             }
                                                                            }
                                                                          }
                                                                         }
                                                                         }
                                                                         }
                                                                    }

                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    
                }
            } /*else {
                if (t.getCode() == KeyCode.DELETE) {
                     this.SearchBox.setText("");
                   
                    if (GenEapb.class == OClass.getClass()) {
                        if(filterservicios)
                        {    
                        SihicApp.s_genEapb = null;
                        SihicApp.s_genEapb = new GenEapb();
                        }
                        else
                        {
                          SihicApp.aseguradora = null;
                          SihicApp.aseguradora = new GenEapb();
                          
                        }
                    } else {
                        if (HclCups.class == OClass.getClass()) {
                            SihicApp.s_hclCups = null;
                            SihicApp.s_hclCups = new HclCups();
                        } else {
                            if (Producto.class == OClass.getClass()) {
                                SihicApp.s_producto = null;
                                SihicApp.s_producto = new Producto();
                            } else {
                                if (Kardex.class == OClass.getClass()) {
                                    SihicApp.s_kardex = null;
                                    SihicApp.s_kardex = new Kardex();
                                } else {
                                    if (GenMunicipios.class == OClass.getClass()) {
                                        SihicApp.s_genMunicipios = null;
                                        SihicApp.s_genMunicipios = new GenMunicipios();
                                    } else {
                                        if (GenProfesiones.class == OClass.getClass()) {
                                            SihicApp.s_GenProfesiones = null;
                                            SihicApp.s_GenProfesiones = new GenProfesiones();
                                        } else {
                                            if (Factura.class == OClass.getClass()) {
                                                SihicApp.s_factura = null;
                                                SihicApp.s_factura = new Factura();
                                            } else {
                                                if (FacturaItem.class == OClass.getClass()) {
                                                    SihicApp.s_facturaitem = null;
                                                    SihicApp.s_facturaitem = new FacturaItem();
                                                } else {
                                                    if (HclCie10.class == OClass.getClass()) {
                                                        SihicApp.hclCie10 = null;
                                                        SihicApp.hclCie10 = new HclCie10();
                                                    } else {
                                                        if (ActividadesEconomicas.class == OClass.getClass()) {
                                                            SihicApp.s_ActividadesEconomicas = null;
                                                            SihicApp.s_ActividadesEconomicas = new ActividadesEconomicas();
                                                        } else {
                                                            if (FacturaProveedores.class == OClass.getClass()) {
                                                                SihicApp.s_FacturaProveedores = null;
                                                                SihicApp.s_FacturaProveedores = new FacturaProveedores();
                                                            } else {
                                                                if (ConPuc.class == OClass.getClass()) {
                                                                    SihicApp.conPuc = null;
                                                                    SihicApp.conPuc = new ConPuc();
                                                                }
                                                                else
                                                                {
                                                                    if (GenTiposDocumentos.class == OClass.getClass()) {
                                                                    SihicApp.genTiposDocumentos = null;
                                                                    SihicApp.genTiposDocumentos = new GenTiposDocumentos();
                                                                }
                                                                    else
                                                                    {
                                                                   if (GenSexo.class == OClass.getClass()) 
                                                                   {
                                                                        SihicApp.genSexo = null;
                                                                        SihicApp.genSexo = new GenSexo();
                                                                     }
                                                                   else
                                                                   {
                                                                       if (GenEtnias.class == OClass.getClass()) 
                                                                       {
                                                                           SihicApp.genEtnias = null;
                                                                           SihicApp.genEtnias = new GenEtnias();
                                                                       }
                                                                       else
                                                                       {
                                                                           if (GenNivelesEducativos.class == OClass.getClass()) {
                                                                            SihicApp.genNivelesEducativos = null;
                                                                            SihicApp.genNivelesEducativos = new GenNivelesEducativos();
                                                                          }
                                                                           else
                                                                           {
                                                                               if (GenZonaResidencia.class == OClass.getClass()) {
                                                                                SihicApp.genZonaResidencia = null;
                                                                                SihicApp.genZonaResidencia = new GenZonaResidencia();
                                                                                }
                                                                               else
                                                                               {
                                                                                   if (GenEstadosCiviles.class == OClass.getClass()) {
                                                                                      SihicApp.genEstadosCiviles = null;
                                                                                      SihicApp.genEstadosCiviles = new GenEstadosCiviles();
                                                                                    }
                                                                                   else
                                                                                   {
                                                                                       if (GenTiposAfiliacion.class == OClass.getClass()) {
                                                                                            SihicApp.genTiposAfiliacion = null;
                                                                                            SihicApp.genTiposAfiliacion = new GenTiposAfiliacion();
                                                                                        }
                                                                                       else
                                                                                       {
                                                                                           if (GenTiposUsuarios.class == OClass.getClass()) 
                                                                                           {
                                                                                              SihicApp.genTiposUsuarios = null;
                                                                                             SihicApp.genTiposUsuarios = new GenTiposUsuarios();
                                                                                           }
                                                                                           else
                                                                                           {
                                                                                               if (GenNivelesUsuarios.class == OClass.getClass()) {
                                                                                                SihicApp.genNivelesUsuarios = null;
                                                                                                SihicApp.genNivelesUsuarios = new GenNivelesUsuarios();
                                                                                              }
                                                                                               else
                                                                                               {
                                                                                               if (GenPacientes.class == OClass.getClass()) 
                                                                                               {
                                                                                                SihicApp.genPacientesTemp= null;
                                                                                                SihicApp.genPacientesTemp= new GenPacientes();
                                                                                               }
                                                                                               else
                                                                                               {
                                                                                                   if (GenConvenios.class == OClass.getClass()) 
                                                                                                   {
                                                                                                     SihicApp.genConvenios = null;
                                                                                                     SihicApp.genConvenios = new GenConvenios();
                                                                                                   }
                                                                                                   else
                                                                                                   {
                                                                                                       if (GenHorasCita.class == OClass.getClass()) 
                                                                                                       {
                                                                                                         SihicApp.genHorasCita = null;
                                                                                                         SihicApp.genHorasCita = new GenHorasCita();
                                                                                                       }
                                                                                                       else
                                                                                                       {
                                                                                                           if (AgnMedicos.class == OClass.getClass()) 
                                                                                                           {
                                                                                                              SihicApp.agnMedicos = null;
                                                                                                              SihicApp.agnMedicos = new AgnMedicos();
                                                                                                           }
                                                                                                           else
                                                                                                           {
                                                                                                             if (AgnConsultorios.class == OClass.getClass()) 
                                                                                                           {
                                                                                                              SihicApp.agnConsultorios = null;
                                                                                                              SihicApp.agnConsultorios = new AgnConsultorios();
                                                                                                           }
                                                                                                           else
                                                                                                             {
                                                                                                                 if (Sucursales.class == OClass.getClass()) 
                                                                                                                 {
                                                                                                                    SihicApp.centrocostos = null;
                                                                                                                    SihicApp.centrocostos = new Sucursales();
                                                                                                                 }
                                                                                                             }
                                                                                                           }
                                                                                                       }
                                                                                                   }
                                                                                               }
                                                                                               
                                                                                               }
                                                                                           }
                                                                                       }
                                                                                   }
                                                                               }
                                                                           }
                                                                       }
                                                                   }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    hide();
                }

            }*/

        });

        searchResultPopoverList = new SearchResultPopoverList(pageBrowser);
        // if list gets focus then send back to search box
        searchResultPopoverList.focusedProperty().addListener((ObservableValue<? extends Boolean> ov, Boolean t, Boolean hasFocus) -> {

            if (hasFocus) {
                SearchBox.requestFocus();
                SearchBox.selectPositionCaret(SearchBox.getText().length());
            }
        });

    }

    private void updateResults() throws ParseException {
        if (SearchBox.getText() == null || SearchBox.getText().isEmpty()) {
           
                
           
               
                    if (GenEapb.class == OClass.getClass()) {
                        populateMenu(li_geneapb);
                    } else {
                        if (HclCups.class == OClass.getClass()) {
                            populateMenu(li_hclcups);
                        } else {
                            if (Producto.class == OClass.getClass()) {
                                populateMenu(li_producto);
                            } else {
                                if (Kardex.class == OClass.getClass()) {
                                    populateMenu(li_kardex);
                                } else {
                                    if (GenMunicipios.class == OClass.getClass()) {
                                        populateMenu(li_genmunicipios);
                                    } else {
                                        if (GenProfesiones.class == OClass.getClass()) {
                                            populateMenu(li_genprofesiones);
                                        } else {
                                            if (Factura.class == OClass.getClass()) {
                                                populateMenu(li_factura);
                                            } else {
                                                if (FacturaItem.class == OClass.getClass()) {
                                                    populateMenu(li_facturaitem);
                                                } else {
                                                    if (HclCie10.class == OClass.getClass()) {
                                                        populateMenu(li_hclcie10);
                                                    } else {
                                                        if (ActividadesEconomicas.class == OClass.getClass()) {
                                                            populateMenu(li_ActividadesEconomicases);
                                                        } else {
                                                            if (FacturaProveedores.class == OClass.getClass()) {
                                                                populateMenu(li_facFacturaProveedoreses);
                                                            }
                                                            else
                                                            {
                                                                if (GenTiposDocumentos.class == OClass.getClass()) 
                                                                {
                                                                  populateMenu(li_tipoDocumentoses);
                                                                }
                                                                else
                                                                {
                                                                   if (GenPacientes.class == OClass.getClass()) 
                                                                {
                                                                  populateMenu(li_GenPacientes);
                                                                }
                                                                else
                                                                   {
                                                                     if (GenSexo.class == OClass.getClass()) 
                                                                    {
                                                                      populateMenu(li_GenSexos);
                                                                    } 
                                                                    else
                                                                     {
                                                                       if (GenEtnias.class == OClass.getClass()) 
                                                                    {
                                                                      populateMenu(li_geEtniases);
                                                                    }
                                                                       else
                                                                       {
                                                                           if (GenNivelesEducativos.class == OClass.getClass()) 
                                                                           {
                                                                              populateMenu(li_GenNivelesEducativoses);
                                                                           }
                                                                           else
                                                                           {
                                                                                if (GenZonaResidencia.class == OClass.getClass()) 
                                                                                {
                                                                                   populateMenu(li_GenZonaResidencias);
                                                                                }
                                                                                else
                                                                                {
                                                                                    if (GenEstadosCiviles.class == OClass.getClass()) 
                                                                                    {
                                                                                      populateMenu(li_genEstadosCivileses);
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                    if (GenTiposAfiliacion.class == OClass.getClass()) 
                                                                                    {
                                                                                      populateMenu(li_GenTiposAfiliacions);
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                    if (GenTiposUsuarios.class == OClass.getClass()) 
                                                                                    {
                                                                                      populateMenu(li_GenTiposUsuarioses);
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                     if (GenNivelesUsuarios.class == OClass.getClass()) 
                                                                                     {
                                                                                       populateMenu(li_GenNivelesUsuarioses);
                                                                                     }
                                                                                     else
                                                                                     {
                                                                                          if (GenConvenios.class == OClass.getClass()) 
                                                                                          {
                                                                                            populateMenu(li_Convenios);
                                                                                          }
                                                                                          else
                                                                                          {
                                                                                              if (GenHorasCita.class == OClass.getClass()) 
                                                                                             {
                                                                                               populateMenu(li_GenHorasCitas);
                                                                                             }
                                                                                              else
                                                                                              {
                                                                                                   if (AgnMedicos.class == OClass.getClass()) 
                                                                                                   {
                                                                                                      populateMenu(li_Medicos);
                                                                                                  }
                                                                                                   else
                                                                                                   {
                                                                                                        if (AgnConsultorios.class == OClass.getClass()) 
                                                                                                       {
                                                                                                          populateMenu(li_Consultorios);
                                                                                                       }
                                                                                                        else
                                                                                                        {
                                                                                                             if (Sucursales.class == OClass.getClass()) 
                                                                                                            {
                                                                                                               populateMenu(li_Sucursales);
                                                                                                             }
                                                                                                             else
                                                                                                             {
                                                                                                               if (String.class == OClass.getClass()) 
                                                                                                            {
                                                                                                               populateMenu(li_rh);
                                                                                                             }  
                                                                                                               else
                                                                                                               {
                                                                                                            if (Proveedores.class == OClass.getClass()) 
                                                                                                            {
                                                                                                               populateMenu(li_proveedores);
                                                                                                             }
                                                                                                            else
                                                                                                             {
                                                                                                              
                                                                                                                 if (AgnCitas.class == OClass.getClass()) 
                                                                                                            {
                                                                                                               populateMenu(li_proveedores);
                                                                                                             }
                                                                                                              }
                                                                                                               }
                                                                                                             }
                                                                                                        }
                                                                                                   }
                                                                                              }
                                                                                          }
                                                                                     }
                                                                                    }
                                                                                    }
                                                                                    }
                                                                                }
                                                                           }
                                                                       }
                                                                     }
                                                                   }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                
            
            return;
        }
        boolean haveResults = false;
        try {
            if (indexSearcher == null) {
                indexSearcher = new IndexSearcher();
            }
           
                
                    if (GenEapb.class == OClass.getClass()) {
                        li_geneapb = indexSearcher.searchEapb(SearchBox.getText());
                    } else {
                        if (HclCups.class == OClass.getClass()) {
                            li_hclcups = indexSearcher.searchHclCups(SearchBox.getText());
                        } else {
                            if (Producto.class == OClass.getClass()) {
                                if(filterservicios)
                                {   
                                li_producto = indexSearcher.searchProductos2    (SearchBox.getText());
                                }
                                else
                                {
                                        
                                     li_producto = indexSearcher.searchProductos(SearchBox.getText());     }
                                
                            } else {

                                if (Kardex.class == OClass.getClass()) {
                                    if (!filterservicios) {
                                        li_kardex = indexSearcher.searchProductosKardex(SearchBox.getText());
                                    } else {
                                        li_kardex = indexSearcher.searchServiciosKardex(SearchBox.getText());
                                    }

                                } else {
                                    if (GenMunicipios.class == OClass.getClass()) {
                                        li_genmunicipios = indexSearcher.searchGenMunicipios(SearchBox.getText());
                                    } else {
                                        if (GenProfesiones.class == OClass.getClass()) {
                                            li_genprofesiones = indexSearcher.searchGenProfesiones(SearchBox.getText());
                                        } else {
                                            if (Factura.class == OClass.getClass()) {
                                                if (filterservicios) {
                                                    li_factura = indexSearcher.searchFactura(SearchBox.getText());
                                                } else {
                                                    li_factura = indexSearcher.searchFacturafind(SearchBox.getText());
                                                }
                                            } else {
                                                if (FacturaItem.class == OClass.getClass()) {
                                                    li_facturaitem = indexSearcher.searchFacturaItem(SearchBox.getText());
                                                } else {
                                                    if (HclCie10.class == OClass.getClass()) {
                                                        li_hclcie10 = indexSearcher.searchHclCie10(SearchBox.getText());
                                                    } else {
                                                        if (ActividadesEconomicas.class == OClass.getClass()) {
                                                            li_ActividadesEconomicases = indexSearcher.searchActividadesEconomicas(SearchBox.getText());
                                                        } else {
                                                            if (RetencionFuente.class == OClass.getClass()) {
                                                                li_retencionfuente = indexSearcher.searchretencionfuente(SearchBox.getText());
                                                            } else {
                                                                if (FacturaProveedores.class == OClass.getClass()) {
                                                                    li_facFacturaProveedoreses = indexSearcher.searchfacturaproveedores(SearchBox.getText());
                                                                }
                                                                else
                                                                {
                                                                    if (GenPacientes.class == OClass.getClass()) {
                                                                    li_GenPacientes= indexSearcher.searchgenpacientes(SearchBox.getText());
                                                                } 
                                                                    else
                                                                    {
                                                                         if (GenTiposDocumentos.class == OClass.getClass()) 
                                                                         {
                                                                         li_tipoDocumentoses = indexSearcher.searchgentiposdocumentos(SearchBox.getText());
                                                                         } 
                                                                         else
                                                                         {
                                                                         if (GenSexo.class == OClass.getClass()) 
                                                                         {
                                                                              li_GenSexos = indexSearcher.searchgensexo(SearchBox.getText());
                                                                         } 
                                                                         else
                                                                         {
                                                                             if (GenEtnias.class == OClass.getClass()) 
                                                                         {
                                                                              li_geEtniases = indexSearcher.searchgenetnias(SearchBox.getText());
                                                                         } 
                                                                             else
                                                                             {
                                                                                   if (GenNivelesEducativos.class == OClass.getClass()) 
                                                                                   {
                                                                                      li_GenNivelesEducativoses = indexSearcher.searchgenniveleseducativos(SearchBox.getText());
                                                                                   } 
                                                                                   else
                                                                                   {
                                                                                     if (GenZonaResidencia.class == OClass.getClass()) 
                                                                                     {
                                                                                       li_GenZonaResidencias = indexSearcher.searchgenzonaresidencia(SearchBox.getText());
                                                                                     } 
                                                                                     else
                                                                                     {
                                                                                      if (GenEstadosCiviles.class == OClass.getClass()) 
                                                                                     {
                                                                                       li_genEstadosCivileses = indexSearcher.searchgenestadosciviles(SearchBox.getText());
                                                                                     }
                                                                                      else
                                                                                      {
                                                                                      if (GenTiposAfiliacion.class == OClass.getClass()) 
                                                                                     {
                                                                                       li_GenTiposAfiliacions = indexSearcher.searchgentiposafiliacion(SearchBox.getText());
                                                                                     }
                                                                                      else
                                                                                      {
                                                                                          if (GenTiposUsuarios.class == OClass.getClass()) 
                                                                                         {
                                                                                           li_GenTiposUsuarioses= indexSearcher.searchgentiposusuarios(SearchBox.getText());
                                                                                          }
                                                                                          else
                                                                                          {
                                                                                             if (GenNivelesUsuarios.class == OClass.getClass()) 
                                                                                            {
                                                                                               li_GenNivelesUsuarioses= indexSearcher.searchgennivelesusuarios(SearchBox.getText());
                                                                                             } 
                                                                                             else
                                                                                             {
                                                                                                  if (GenConvenios.class == OClass.getClass()) 
                                                                                                 {
                                                                                                   li_Convenios= indexSearcher.searchgenconvenios(SearchBox.getText());
                                                                                               
                                                                                                 }
                                                                                                  else
                                                                                                  {
                                                                                                      if (GenHorasCita.class == OClass.getClass()) 
                                                                                                     {
                                                                                                       li_GenHorasCitas= indexSearcher.searchgenhorascitas(SearchBox.getText());
                                                                                               
                                                                                                     }
                                                                                                      else
                                                                                                      {
                                                                                                          if (AgnMedicos.class == OClass.getClass()) 
                                                                                                          {
                                                                                                            li_Medicos= indexSearcher.searchagnmedicos(SearchBox.getText());
                                                                                               
                                                                                                           }
                                                                                                          else
                                                                                                          {
                                                                                                             if (AgnMedicos.class == OClass.getClass()) 
                                                                                                            {
                                                                                                              li_Medicos= indexSearcher.searchagnmedicos(SearchBox.getText());
                                                                                               
                                                                                                             } 
                                                                                                             else
                                                                                                             {
                                                                                                                 if (AgnConsultorios.class == OClass.getClass()) 
                                                                                                               {
                                                                                                                 li_Consultorios= indexSearcher.searchagnconsultorios(SearchBox.getText());
                                                                                               
                                                                                                               }
                                                                                                                 else
                                                                                                                 {
                                                                                                                     if (Sucursales.class == OClass.getClass()) 
                                                                                                                     {
                                                                                                                       li_Sucursales= indexSearcher.searchsucursales(SearchBox.getText());
                                                                                               
                                                                                                                      }
                                                                                                                     else
                                                                                                                     {
                                                                                                                          if (String.class == OClass.getClass()) 
                                                                                                                     {
                                                                                                                       li_rh= indexSearcher.searchrh(SearchBox.getText());
                                                                                               
                                                                                                                      } 
                                                                                                                          else
                                                                                                                          {
                                                                                                                             
                                                                                                                          if (Proveedores.class == OClass.getClass()) 
                                                                                                                          {
                                                                                                                            li_proveedores= indexSearcher.searchproveedores(SearchBox.getText());
                                                                                               
                                                                                                                          }
                                                                                                                          else
                                                                                                                          {
                                                                                                                           if (AgnCitas.class == OClass.getClass()) 
                                                                                                                          {
                                                                                                                            li_agncitas= indexSearcher.searchcitas(SearchBox.getText());
                                                                                                                            System.out.println("size citas->"+li_agncitas.size());
                                                                                               
                                                                                                                          }   
                                                                                                                          }
                                                                                                                          }
                                                                                                                     }
                                                                                                                 }
                                                                                                             }
                                                                                                          }
                                                                                                      }
                                                                                                  }
                                                                                             }
                                                                                          }
                                                                                      }
                                                                                      }
                                                                                     }
                                                                                   }
                                                                             }
                                                                         }
                                                                         }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }
                
            
            // check if we have any results

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (results.size() > 0) {
            showError(null);
            populateMenu(results);
            show();
        } else {
            if (li_conComprobanteGastos.size() > 0) {
                showError(null);
                populateMenu(li_conComprobanteGastos);
                show();
            } else {
                if (li_geneapb.size() > 0) {
                    showError(null);
                    populateMenu(li_geneapb);
                    show();
                } else {
                    if (li_producto.size() > 0) {
                        showError(null);
                        populateMenu(li_producto);
                        show();
                    } else {
                        if (li_hclcups.size() > 0) {
                            showError(null);
                            populateMenu(li_hclcups);
                            show();
                        } else {
                            if (li_kardex.size() > 0) {
                                showError(null);
                                populateMenu(li_kardex);
                                show();
                            } else {
                                if (li_genmunicipios.size() > 0) {
                                    showError(null);
                                    populateMenu(li_genmunicipios);
                                    show();
                                } else {
                                    if (li_genprofesiones.size() > 0) {
                                        showError(null);
                                        populateMenu(li_genprofesiones);
                                        show();
                                    } else {
                                        if (li_factura.size() > 0) {
                                            showError(null);
                                            populateMenu(li_factura);
                                            show();
                                        } else {
                                            if (li_facturaitem.size() > 0) {
                                                showError(null);
                                                populateMenu(li_facturaitem);
                                                show();
                                            } else {
                                                if (li_hclcie10.size() > 0) {
                                                    showError(null);
                                                    populateMenu(li_hclcie10);
                                                    show();
                                                } else {
                                                    if (li_ActividadesEconomicases.size() > 0) {
                                                        showError(null);
                                                        populateMenu(li_ActividadesEconomicases);
                                                        show();
                                                    } else {
                                                        if (li_retencionfuente.size() > 0) {
                                                            showError(null);
                                                            populateMenu(li_retencionfuente);
                                                            show();
                                                        } else {
                                                            if (li_facFacturaProveedoreses.size() > 0) {
                                                                showError(null);
                                                                populateMenu(li_facFacturaProveedoreses);
                                                                show();
                                                            } else {
                                                               if (li_GenPacientes.size() > 0) {
                                                                showError(null);
                                                                populateMenu(li_GenPacientes);
                                                                show();
                                                            }
                                                               else
                                                               {
                                                                   if (li_tipoDocumentoses.size() > 0) {
                                                                     showError(null);
                                                                     populateMenu(li_tipoDocumentoses);
                                                                     show();
                                                            }
                                                                   else
                                                                   {
                                                                      if (li_GenSexos.size() > 0) 
                                                                      {
                                                                        showError(null);
                                                                        populateMenu(li_GenSexos);
                                                                        show();
                                                                       }
                                                                      else
                                                                      {
                                                                          if (li_geEtniases.size() > 0) 
                                                                          {
                                                                            showError(null);
                                                                            populateMenu(li_geEtniases);
                                                                            show();
                                                                           }
                                                                       else
                                                                          {
                                                                          if (li_GenNivelesEducativoses.size() > 0) 
                                                                          {
                                                                            showError(null);
                                                                            populateMenu(li_GenNivelesEducativoses);
                                                                            show();
                                                                           }
                                                                          else
                                                                          {
                                                                              if (li_genEstadosCivileses.size() > 0) 
                                                                              {
                                                                                showError(null);
                                                                                populateMenu(li_genEstadosCivileses);
                                                                                show();
                                                                           }
                                                                              else
                                                                              {
                                                                                  if (li_GenTiposAfiliacions.size() > 0) 
                                                                                  {
                                                                                    showError(null);
                                                                                    populateMenu(li_GenTiposAfiliacions);
                                                                                    show();
                                                                                  }
                                                                                  else
                                                                                  {
                                                                                   if (li_GenTiposUsuarioses.size() > 0) 
                                                                                  {
                                                                                    showError(null);
                                                                                    populateMenu(li_GenTiposUsuarioses);
                                                                                    show();
                                                                                  }
                                                                                   else
                                                                                   {
                                                                                       if (li_GenNivelesUsuarioses.size() > 0) 
                                                                                  {
                                                                                    showError(null);
                                                                                    populateMenu(li_GenNivelesUsuarioses);
                                                                                    show();
                                                                                  }
                                                                                  else
                                                                                       {
                                                                                           if (li_Convenios.size() > 0) 
                                                                                           {
                                                                                              showError(null);
                                                                                               populateMenu(li_Convenios);
                                                                                            show();
                                                                                            }
                                                                                           else
                                                                                           {
                                                                                               if (li_GenHorasCitas.size() > 0) 
                                                                                               {
                                                                                                   showError(null);
                                                                                                   populateMenu(li_GenHorasCitas);
                                                                                                   show();
                                                                                                }
                                                                                               else
                                                                                               {
                                                                                                  if (li_Medicos.size() > 0) 
                                                                                               {
                                                                                                   showError(null);
                                                                                                   populateMenu(li_Medicos);
                                                                                                   show();
                                                                                                } 
                                                                                                else
                                                                                                  {
                                                                                                      if (li_Consultorios.size() > 0) 
                                                                                                     {
                                                                                                       showError(null);
                                                                                                       populateMenu(li_Consultorios);
                                                                                                       show();
                                                                                                     }
                                                                                                     else
                                                                                                      {
                                                                                                          if (li_Sucursales.size() > 0) 
                                                                                                          {
                                                                                                            showError(null);
                                                                                                            populateMenu(li_Sucursales);
                                                                                                            show();
                                                                                                          }
                                                                                                          else
                                                                                                          {
                                                                                                              if (li_GenZonaResidencias.size() > 0) 
                                                                                                          {
                                                                                                            showError(null);
                                                                                                            populateMenu(li_GenZonaResidencias);
                                                                                                            show();
                                                                                                          }
                                                                                                              else
                                                                                                              {
                                                                                                           if (li_rh.size() > 0) 
                                                                                                          {
                                                                                                            showError(null);
                                                                                                            populateMenu(li_rh);
                                                                                                            show();
                                                                                                          }
                                                                                                           else
                                                                                                           {
                                                                                                              if (li_proveedores.size() > 0) 
                                                                                                          {
                                                                                                            showError(null);
                                                                                                            populateMenu(li_proveedores);
                                                                                                            show();
                                                                                                          } 
                                                                                                              else
                                                                                                              {
                                                                                                                if (li_agncitas.size() > 0) 
                                                                                                          {
                                                                                                            showError(null);
                                                                                                            populateMenu(li_agncitas);
                                                                                                            show();
                                                                                                          }   
                                                                                                              }
                                                                                                           }
                                                                                                              }
                                                                                                          }
                                                                                                      }
                                                                                                  }
                                                                                               }
                                                                                           }
                                                                                       }
                                                                                   }
                                                                                  }
                                                                              }
                                                                          }
                                                                          }
                                                                      }
                                                                   }
                                                               }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }

                }
            }

        }
    }

    private void showError(String message) {
        if (searchErrorTooltip == null) {
            searchErrorTooltip = new Tooltip();
        }
        searchErrorTooltip.setText(message);
        if (searchErrorTooltipHidder != null) {
            searchErrorTooltipHidder.stop();
        }
        if (message != null) {
            Point2D toolTipPos = SearchBox.localToScene(0, SearchBox.getLayoutBounds().getHeight());
            double x = toolTipPos.getX() + SearchBox.getScene().getX() + SearchBox.getScene().getWindow().getX();
            double y = toolTipPos.getY() + SearchBox.getScene().getY() + SearchBox.getScene().getWindow().getY();
            searchErrorTooltip.show(SearchBox.getScene().getWindow(), x, y);
            searchErrorTooltipHidder = new Timeline();
            searchErrorTooltipHidder.getKeyFrames().add(
                    new KeyFrame(Duration.seconds(3), (ActionEvent t) -> {
                        searchErrorTooltip.hide();
                        searchErrorTooltip.setText(null);
                    })
            );
            searchErrorTooltipHidder.play();
        } else {
            searchErrorTooltip.hide();
        }
    }

    private void populateMenu(List<?> results) {
        searchResultPopoverList.getItems().clear();
        for (Object cp : results) {

            searchResultPopoverList.getItems().add(cp);

        }
        clearPages();
        pushPage(searchResultPopoverList);
    }

    public SearchBox getSearchBox() {
        return SearchBox;
    }

    public SearchResultPopoverList getSearchResultPopoverList() {
        return searchResultPopoverList;
    }

    public void setSearchResultPopoverList(SearchResultPopoverList searchResultPopoverList) {
        this.searchResultPopoverList = searchResultPopoverList;
    }

}
