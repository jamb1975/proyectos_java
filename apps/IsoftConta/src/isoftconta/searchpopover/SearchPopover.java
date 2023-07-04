package isoftconta.searchpopover;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.util.Duration;

import modelo.EntidadesStatic;
import modelo.GenMunicipios;
import modelo.Puc;
import modelo.Terceros;

/**
 * Implementation of popover to show search results
 */
public class SearchPopover extends Popover {

    private  final  SearchBox SearchBox;
    private final PageBrowser pageBrowser;
    private IndexSearcher indexSearcher;
    private Tooltip searchErrorTooltip = null;
    private Timeline searchErrorTooltipHidder = null;
    private  SearchResultPopoverList searchResultPopoverList;
    private  Object OClass;
    private List<Puc> results_puc = new ArrayList<Puc>();
    private List<Terceros> results_terceros = new ArrayList<Terceros>();
    private List<GenMunicipios> results_ciudades = new ArrayList<GenMunicipios>();
    private boolean filterservicios;

    public SearchPopover( final SearchBox SearchBox, PageBrowser pageBrowser, Object OClass, boolean filterservicios) {
       // super();
        this.SearchBox = SearchBox;
        this.pageBrowser = pageBrowser;
        this.OClass = OClass;
        
        this.filterservicios = filterservicios;
        getStyleClass().add("right-tooth");
        setPrefWidth(600);
        this.SearchBox.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
            try {
                updateResults();
            } catch (ParseException ex) {
                Logger.getLogger(SearchPopover.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
      
        this.SearchBox.focusedProperty().addListener(new ChangeListener<Boolean>()
{
    @Override
    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
    {
     // hide();
    }
});
        this.SearchBox.addEventFilter(KeyEvent.ANY, (KeyEvent t) -> {
           
            if (t.getCode() == KeyCode.DOWN
                    || t.getCode() == KeyCode.UP
                    || t.getCode() == KeyCode.PAGE_DOWN
                    || (t.getCode() == KeyCode.HOME && (t.isControlDown() || t.isMetaDown()))
                    || (t.getCode() == KeyCode.END && (t.isControlDown() || t.isMetaDown()))
                    || t.getCode() == KeyCode.PAGE_UP) {
                System.out.println("Evento consumado ");
                searchResultPopoverList.fireEvent(t);
                t.consume();
            } else if (t.getCode() == KeyCode.ENTER) {
                //  t.consume();
                if (t.getEventType() == KeyEvent.KEY_PRESSED) {
                    if (Puc.class == OClass.getClass()) {
                        if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                            Puc selectedItem = (Puc) searchResultPopoverList.getSelectionModel().getSelectedItem();
                            this.SearchBox.setText(selectedItem.getCodigo() + " " + selectedItem.getNombre());
                            hide();

                        }

                    }
                    else
                if (Terceros.class == OClass.getClass()) {
                if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                    Terceros selectedItem = (Terceros) searchResultPopoverList.getSelectionModel().getSelectedItem();
                     this.SearchBox.setText(selectedItem.getNo_ident() + " " + selectedItem.getNombres());
                    hide();

                }
              
                 }
                  else
                if (GenMunicipios.class == OClass.getClass()) {
                if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                    GenMunicipios selectedItem = (GenMunicipios) searchResultPopoverList.getSelectionModel().getSelectedItem();
                    this.SearchBox.setText(selectedItem.getCodigo() + " " + selectedItem.getNombre());
                    hide();

                }
              
                 }   
                    
                            } 
            } else {
                if (t.getCode() == KeyCode.DELETE) {
                     this.SearchBox.setText("");
                     if (Puc.class == OClass.getClass()) 
                     {
                        EntidadesStatic.es_puc= null;
                        EntidadesStatic.es_puc = new Puc();
                     }
                else
                if (Terceros.class == OClass.getClass()) 
                {
                   EntidadesStatic.es_terceros=null;
                   EntidadesStatic.es_terceros=new Terceros();
                }
                 else
                if (GenMunicipios.class == OClass.getClass()) 
                {
                   EntidadesStatic.es_ciudades=null;
                   EntidadesStatic.es_ciudades=new GenMunicipios();
                }                                              
                        
                    
                    hide();
                }

            }

        });

        searchResultPopoverList = new SearchResultPopoverList(pageBrowser);
        searchResultPopoverList.setMouseTransparent(false);
        searchResultPopoverList.setFocusTraversable(true);
        searchResultPopoverList.addEventFilter(MouseEvent.MOUSE_CLICKED, (MouseEvent t) -> {
               
            if (Puc.class == OClass.getClass()) {
                
                if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                    Puc selectedItem = (Puc) searchResultPopoverList.getSelectionModel().getSelectedItem();
                    this.SearchBox.setText(selectedItem.getCodigo() + " " + selectedItem.getNombre());
                    hide();

                }
              
            }
            else
                if (Terceros.class == OClass.getClass()) {
                if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                    Terceros selectedItem = (Terceros) searchResultPopoverList.getSelectionModel().getSelectedItem();
                    this.SearchBox.setText(selectedItem.getNo_ident() + " " + selectedItem.getNombres());
                    hide();

                }
              
            }
            else
                if (GenMunicipios.class == OClass.getClass()) {
                if (searchResultPopoverList.getSelectionModel().getSelectedItem() != null) {
                    GenMunicipios selectedItem = (GenMunicipios) searchResultPopoverList.getSelectionModel().getSelectedItem();
                    this.SearchBox.setText(selectedItem.getCodigo() + " " + selectedItem.getNombre());
                    hide();

                }
              
            }

        });
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
            if (Puc.class == OClass.getClass()) {
                populateMenu(results_puc);
            }
            else
            if (Terceros.class == OClass.getClass()) {
                populateMenu(results_terceros);
            }
            else
                if (GenMunicipios.class == OClass.getClass()) {
                populateMenu(results_ciudades);
            }
            return;
        }
        boolean haveResults = false;
        try {
            if (indexSearcher == null) {
                indexSearcher = new IndexSearcher();
            }
            if (Puc.class == OClass.getClass()) {
                results_puc = indexSearcher.searchencatalogocuentas(SearchBox.getText());
                System.out.println("size->"+results_puc.size());
            } 
            else
               if (Terceros.class == OClass.getClass()) {
                results_terceros = indexSearcher.searchenterceros(SearchBox.getText()
                );
            }
            else
               if (GenMunicipios.class == OClass.getClass()) {
                results_ciudades = indexSearcher.searchenciudades();
            }
            // check if we have any results

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (results_puc.size() > 0) {
            showError(null);
            populateMenu(results_puc);
            show();
        } 
        else
          if (results_terceros.size() > 0) {
            showError(null);
            populateMenu(results_terceros);
            show();
        }
        else
         if (results_ciudades.size() > 0) {
            showError(null);
            populateMenu(results_ciudades);
            show();
        }
        else
             hide();
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
