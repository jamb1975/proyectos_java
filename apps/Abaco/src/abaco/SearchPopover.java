package abaco;


import java.text.ParseException;
import java.util.ArrayList;
import abaco.control.Popover;
import abaco.control.SearchBox;
import abaco.search.IndexSearcher;
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
import model.Categoria;
import model.Factura;
import model.GenMunicipios;
import model.Kardex;
import model.Personas;
import model.Producto;
import model.Proveedores;


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
    private List<Producto> li_producto=new ArrayList<>();
    private List<Kardex> li_kardex=new ArrayList<>();
    private List<GenMunicipios> li_genmunicipios=new ArrayList<>();
    private List<Categoria> li_categorias=new ArrayList<>();
    private List<Personas> li_genpersonas=new ArrayList<>();
    private List<Proveedores> li_proveedores=new ArrayList<>();
    private List<Factura> li_factura=new ArrayList<>();
    private boolean filterservicios;
    public SearchPopover(final SearchBox SearchBox, PageBrowser pageBrowser,Object OClass,boolean filterservicios) {
        super();
        this.SearchBox = SearchBox;
        this.pageBrowser = pageBrowser;
        this.OClass=OClass;
        this.filterservicios=filterservicios;
        setStyle(" -fx-border-image-slice: 78 120 60 50 fill;\n" +
                 "    -fx-border-image-width: 78 120 60 50;");
        setPrefWidth(600);
        SearchBox.textProperty().addListener((ObservableValue<? extends String> ov, String t, String t1) -> {
            try {
                updateResults();
            } catch (ParseException ex)
            {
                Logger.getLogger(SearchPopover.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
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
                    
                    if(Producto.class==OClass.getClass())
                    {
                     if(searchResultPopoverList.getSelectionModel().getSelectedItem()!=null)
                    {
                        AbacoApp.s_producto =(Producto) searchResultPopoverList.getSelectionModel().getSelectedItem();
                        searchResultPopoverList.itemClicked(AbacoApp.s_producto);
                        if(filterservicios)
                        {
                        this.SearchBox.setText(AbacoApp.s_producto.getNombre());
                        }
                        else
                        {
                           AbacoApp.s_producto.getCodigo(); 
                        }
                        searchResultPopoverList.getSelectionModel().select(-1);
                        hide();
                    }
                    }
                     else
                    {
                    if(Kardex.class==OClass.getClass())
                    {
                    if(searchResultPopoverList.getSelectionModel().getSelectedItem()!=null)
                    {
                       AbacoApp.s_kardex =(Kardex) searchResultPopoverList.getSelectionModel().getSelectedItem();
                       searchResultPopoverList.itemClicked(AbacoApp.s_kardex);
                      
                            this.SearchBox.setText(AbacoApp.s_kardex.getProducto().getNombre());
                        
                        hide();
                     }
                    }
                    else
                    {
                        if(OClass.getClass()==GenMunicipios.class)
                        {
                     
                    if(searchResultPopoverList.getSelectionModel().getSelectedItem()!=null)
                   { 
                            AbacoApp.s_genmunicipios =(GenMunicipios) searchResultPopoverList.getSelectionModel().getSelectedItem();
                            searchResultPopoverList.itemClicked(AbacoApp.s_genmunicipios);
                            this.SearchBox.setText(AbacoApp.s_genmunicipios.getNombre()+"-"+AbacoApp.s_genmunicipios.getGenDepartamentos().getNombre());
                            hide();
                    }
                        }
                    else
                    {
                        if(OClass.getClass()==Categoria.class)
                        {
                     
                    if(searchResultPopoverList.getSelectionModel().getSelectedItem()!=null)
                   { 
                            AbacoApp.s_categoria =(Categoria) searchResultPopoverList.getSelectionModel().getSelectedItem();
                            searchResultPopoverList.itemClicked(AbacoApp.s_categoria);
                            this.SearchBox.setText(AbacoApp.s_categoria.getM_NombreCat());
                            hide();
                    }
                        }
                            else
                          {
                        if(OClass.getClass()==Personas.class)
                        {
                     
                         if(searchResultPopoverList.getSelectionModel().getSelectedItem()!=null)
                        { 
                            AbacoApp.s_genpersonas =(Personas) searchResultPopoverList.getSelectionModel().getSelectedItem();
                            searchResultPopoverList.itemClicked(AbacoApp.s_genpersonas);
                            this.SearchBox.setText(AbacoApp.s_genpersonas.getNombre());
                            hide();
                         }
                        }
                        
                          else
                          {
                           if(OClass.getClass()==Proveedores.class)
                           {
                     
                            if(searchResultPopoverList.getSelectionModel().getSelectedItem()!=null)
                           { 
                            AbacoApp.s_proveedores =(Proveedores) searchResultPopoverList.getSelectionModel().getSelectedItem();
                            searchResultPopoverList.itemClicked(AbacoApp.s_proveedores);
                            this.SearchBox.setText(AbacoApp.s_proveedores.getNombre());
                            hide();
                            }
                           }
                           else
                           {
                           if(OClass.getClass()==Factura.class)
                           {
                     
                            if(searchResultPopoverList.getSelectionModel().getSelectedItem()!=null)
                           { 
                            AbacoApp.s_factura =(Factura) searchResultPopoverList.getSelectionModel().getSelectedItem();
                            searchResultPopoverList.itemClicked(AbacoApp.s_factura);
                            this.SearchBox.setText(AbacoApp.s_factura.getNofacturacerosizquierda()+"||"+AbacoApp.s_factura.getCustomer().getNombre());
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
              
            else
            {
                 if (t.getCode() == KeyCode.ESCAPE) {
                         this.SearchBox.setText("");
                         
                         if(Producto.class==OClass.getClass())
                         {
                          AbacoApp.s_producto=null;
                          AbacoApp.s_producto=new Producto();
                         }
                          else
                         {
                         if(Kardex.class==OClass.getClass())
                         {
                          AbacoApp.s_kardex=null;
                          AbacoApp.s_kardex=new Kardex();
                         }
                         else
                         {
                            if(GenMunicipios.class==OClass.getClass())
                            {
                                AbacoApp.s_genmunicipios=null;
                                AbacoApp.s_genmunicipios=new GenMunicipios();
                            }
                            else
                            {
                               if(Categoria.class==OClass.getClass())
                            {
                                AbacoApp.s_categoria=null;
                                AbacoApp.s_categoria=new Categoria();
                            } 
                                else
                            {
                               if(Personas.class==OClass.getClass())
                            {
                                AbacoApp.s_genpersonas=null;
                                AbacoApp.s_genpersonas=new Personas();
                            }
                                else
                            {
                               if(Proveedores.class==OClass.getClass())
                            {
                                AbacoApp.s_proveedores=null;
                                AbacoApp.s_proveedores=new Proveedores();
                            } 
                               else
                            {
                               if(Factura.class==OClass.getClass())
                            {
                                AbacoApp.s_factura=null;
                                AbacoApp.s_factura=new Factura();
                            }  
                            }   
                            }
                            }
                            }
                                 }
                                }
                          hide();
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
        if(SearchBox.getText() == null || SearchBox.getText().isEmpty()) 
        {
            
                      if(Producto.class==OClass.getClass())
                     {    
                       populateMenu(li_producto);
                     }
                      else
                   {
                      if(Kardex.class==OClass.getClass())
                     {    
                       populateMenu(li_kardex);
                     }
                      else{
                          if(GenMunicipios.class==OClass.getClass())
                          {
                              populateMenu(li_genmunicipios);
                          }
                          else{
                          if(Categoria.class==OClass.getClass())
                          {
                              populateMenu(AbacoApp.li_categoria);
                          }
                          else{
                          if(Personas.class==OClass.getClass())
                          {
                              populateMenu(AbacoApp.li_genpersonas);
                          }
                          else{
                          if(Proveedores.class==OClass.getClass())
                          {
                              populateMenu(AbacoApp.li_proveedores);
                          }
                            else{
                          if(Proveedores.class==OClass.getClass())
                          {
                              populateMenu(AbacoApp.li_proveedores);
                          }
                          else
                          {
                           if(Factura.class==OClass.getClass())
                          {
                              populateMenu(AbacoApp.li_facturas);
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
            if (indexSearcher == null) indexSearcher = new IndexSearcher();
            if(Producto.class==OClass.getClass())
            {
            li_producto = indexSearcher.searchProductos(SearchBox.getText());
            }
            else
            {
            
            if(Kardex.class==OClass.getClass())
            {
                if(!filterservicios)
                {
                    li_kardex = indexSearcher.searchProductosKardex(SearchBox.getText());
                }
                else
                {
                    li_kardex = indexSearcher.searchServiciosKardex(SearchBox.getText());
                }
            
            }
            else
            {
                if(GenMunicipios.class==OClass.getClass())
                {
                    li_genmunicipios=indexSearcher.searchGenMunicipios(SearchBox.getText());
                }
                else
                {
                    if(Categoria.class==OClass.getClass())
                {
                    li_categorias=indexSearcher.searchCategoria(SearchBox.getText());
                }
                 else
                {
                    if(Personas.class==OClass.getClass())
                {
                    li_genpersonas=indexSearcher.searchPersonas(SearchBox.getText());
                }
                 else
                {
                    if(Proveedores.class==OClass.getClass())
                {
                    li_proveedores=indexSearcher.searchProveedores(SearchBox.getText());
                }
                 else
                {
                    if(Factura.class==OClass.getClass())
                {
                    li_factura=indexSearcher.searchFacturas(SearchBox.getText());
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
                if (li_producto.size()>0) {
            showError(null);
            populateMenu(li_producto);
            show();
        }
           
            else
            {
            if (li_kardex.size()>0) {
            showError(null);
            populateMenu(li_kardex);
            show();
        }
            else
            {     
             if(li_genmunicipios.size()>0)
             {
                 showError(null);
                 populateMenu(li_genmunicipios);
                 show(); 
             }
             else
             {
                 if(li_categorias.size()>0)
             {
                 showError(null);
                 populateMenu(li_categorias);
                 show(); 
             }
             else{
                  if(li_genpersonas.size()>0)
             {
                 showError(null);
                 populateMenu(li_genpersonas);
                 show(); 
             }  
              else
                  {
             if(li_proveedores.size()>0)
             {
                 showError(null);
                 populateMenu(li_proveedores);
                 show(); 
             }
             else
             {
                 if(li_factura.size()>0)
             {
                 showError(null);
                 populateMenu(li_factura);
                 show(); 
             }
                 else
                 {
                  hide();
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
        if (searchErrorTooltipHidder != null) searchErrorTooltipHidder.stop();
        if (message != null) {
            Point2D toolTipPos = SearchBox.localToScene(0, SearchBox.getLayoutBounds().getHeight());
            System.out.println("scene sb->"+SearchBox.getScene());
            double x = toolTipPos.getX() + SearchBox.getScene().getX() + SearchBox.getScene().getWindow().getX();
            double y = toolTipPos.getY() + SearchBox.getScene().getY() + SearchBox.getScene().getWindow().getY();
            searchErrorTooltip.show( SearchBox.getScene().getWindow(),x, y);
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
       System.out.println("Tamaño busquedas->"+results.size());
        for(Object cp: results) {
            
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
