package fxnomina;

import java.text.ParseException;
import java.util.ArrayList;
import control.Popover;
import control.SearchBox;
import search.IndexSearcher;
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
import modelo.GenEapb;
import modelo.GenMunicipios;
import modelo.GenPersonas;
import modelo.GenSexo;
import modelo.GenTiposDocumentos;


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
     private List<GenEapb> li_geneapb = new ArrayList<>();
     private List<GenMunicipios> li_genmunicipios = new ArrayList<>();
   private List<ActividadesEconomicas> li_ActividadesEconomicases = new ArrayList<>();
    private List<GenTiposDocumentos> li_tipoDocumentoses = new ArrayList<>();
    private List<GenSexo> li_GenSexos = new ArrayList<>();
      private List<String>li_rh=new ArrayList<>();
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
                        
                            if (GenEapb.class == OClass.getClass()) {
                                if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                    if(filterservicios)
                                    {     
                                    FXNomina.s_genEapb = (GenEapb) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                    searchResultPopoverList.itemClicked(FXNomina.s_genEapb);
                                    this.SearchBox.setText(FXNomina.s_genEapb.getNombre()+" Codigo:"+FXNomina.s_genEapb.getCodigo());
                                    hide();
                                    }
                                    else
                                    {
                                    FXNomina.aseguradora = (GenEapb) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                   // searchResultPopoverList.itemClicked(SihicApp.aseguradora);
                                    this.SearchBox.setText(FXNomina.aseguradora.getCodigo() + " " + FXNomina.aseguradora.getNombre());
                                    hide(); 
                                    }

                                }
                            }
                                 if (OClass.getClass() == GenMunicipios.class) {
                                                    if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                    FXNomina.s_genMunicipios = (GenMunicipios) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                    searchResultPopoverList.itemClicked(FXNomina.s_genMunicipios);
                                                    this.SearchBox.setText(FXNomina.s_genMunicipios.getNombre() + "-" + FXNomina.s_genMunicipios.getGenDepartamentos().getNombre());
                                                    hide();
                                                }
                                            }       if (OClass.getClass() == ActividadesEconomicas.class) {
                                                                    if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                        FXNomina.s_ActividadesEconomicas = (ActividadesEconomicas) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                        searchResultPopoverList.itemClicked(FXNomina.s_ActividadesEconomicas);
                                                                        this.SearchBox.setText(FXNomina.s_ActividadesEconomicas.getCodigo_ciiu_0079() + " || " + FXNomina.s_ActividadesEconomicas.getDescripcion());
                                                                        hide();
                                                                    }

                                                                } 
                                                                           if (OClass.getClass() == GenTiposDocumentos.class)
                                                                         {
                                                                           if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                            FXNomina.genTiposDocumentos = (GenTiposDocumentos) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                            searchResultPopoverList.itemClicked(FXNomina.genTiposDocumentos);
                                                                            this.SearchBox.setText(FXNomina.genTiposDocumentos.getNombre());
                                                                            hide();
                                                                          }
                                                                        }
                                                                        
                                                                         if (OClass.getClass() == GenSexo.class)
                                                                         {
                                                                           if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                                                                            FXNomina.genSexo = (GenSexo) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                            searchResultPopoverList.itemClicked(FXNomina.genSexo);
                                                                            this.SearchBox.setText(FXNomina.genSexo.getDescripcion());
                                                                            hide();
                                                                          }
                                                                        }
                                                                         
                                                                                   if (OClass.getClass() == String.class)
                                                                                            {
                                                                                               if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) 
                                                                                               {
                                                                                                   FXNomina.rh= (String) searchResultPopoverList.getSelectionModel().getSelectedItem();
                                                                                                   searchResultPopoverList.itemClicked(FXNomina.rh);
                                                                                                   this.SearchBox.setText(FXNomina.rh);
                                                                                                   hide();
                                                                                                }
                                                                                            }
                                                                                       }
                                                                                     }
                    
                
             

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
                     
                    if (GenEapb.class == OClass.getClass()) 
                    {
                        populateMenu(li_geneapb);
                    } 
                    if (GenMunicipios.class == OClass.getClass())
                    {
                        populateMenu(li_genmunicipios);
                     } 
                                                       
                    if (ActividadesEconomicas.class == OClass.getClass()) 
                    {
                        populateMenu(li_ActividadesEconomicases);
                    } 
                    if (GenTiposDocumentos.class == OClass.getClass()) 
                    {
                        populateMenu(li_tipoDocumentoses);
                     }
                                                                
                      if (GenSexo.class == OClass.getClass()) 
                      {
                          populateMenu(li_GenSexos);
                      } 
                                                                    
            
            return;
        }
    
        boolean haveResults = false;
        try {
            if (indexSearcher == null) 
            {
                indexSearcher = new IndexSearcher();
            }
            if (GenEapb.class == OClass.getClass())
            {
             li_geneapb = indexSearcher.searchEapb(SearchBox.getText());
            } 
            if (GenMunicipios.class == OClass.getClass())
            {
                 li_genmunicipios = indexSearcher.searchGenMunicipios(SearchBox.getText());
                                    
            } 
             if (GenTiposDocumentos.class == OClass.getClass()) 
            {
             li_tipoDocumentoses = indexSearcher.searchgentiposdocumentos(SearchBox.getText());
           } 
                                                                        
            if (GenSexo.class == OClass.getClass()) 
            {
                li_GenSexos = indexSearcher.searchgensexo(SearchBox.getText());
            } 
                                                                       
            
            // check if we have any results

        } catch (Exception e) {
            e.printStackTrace();
        }

        
           
                if (li_geneapb.size() > 0) 
                {
                    showError(null);
                    populateMenu(li_geneapb);
                    show();
                } 
                if (li_genmunicipios.size() > 0) 
                {
                     showError(null);
                     populateMenu(li_genmunicipios);
                     show();
                } 
                if (li_ActividadesEconomicases.size() > 0) 
                {
                   showError(null);
                   populateMenu(li_ActividadesEconomicases);
                   show();
                } 
                 if (li_tipoDocumentoses.size() > 0) 
                 {
                   showError(null);
                   populateMenu(li_tipoDocumentoses);
                   show();
                 }
                if (li_GenSexos.size() > 0) 
               {
                showError(null);
                populateMenu(li_GenSexos);
                show();
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
