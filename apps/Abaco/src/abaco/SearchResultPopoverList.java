package abaco;
import abaco.control.Popover;
import abaco.control.PopoverTreeList;
import abaco.search.DocumentType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Skin;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Categoria;
import model.Factura;
import model.GenMunicipios;
import model.Kardex;
import model.Personas;
import model.Producto;
import model.Proveedores;


/**
 * Popover page that displays a list of search results.
 */
public class SearchResultPopoverList extends PopoverTreeList<Object> implements Popover.Page {
    private Popover popover;
    private PageBrowser pageBrowser;
    private Rectangle leftLine = new Rectangle(0,0,1,1);
    private IconPane iconPane = new IconPane();
    private final Pane backgroundRectangle = new Pane();

    public SearchResultPopoverList(PageBrowser pageBrowser) {
        this.pageBrowser = pageBrowser;
        leftLine.setFill(Color.web("#dfdfdf"));
        iconPane.setManaged(false);
        setFocusTraversable(false);
       // backgroundRectangle.setStyle("-fx-background-color: white;");
        setPlaceholder(backgroundRectangle);
    }

    @Override protected void layoutChildren() {
        super.layoutChildren();
      //  if (leftLine.getParent() != this) getChildren().add(leftLine);
      //  if (iconPane.getParent() != this) getChildren().add(iconPane);
        leftLine.setLayoutX(40);
        leftLine.setLayoutY(0);
        leftLine.setHeight(getHeight());
        iconPane.setLayoutX(0);
        iconPane.setLayoutY(0);
        iconPane.resize(getWidth(), getHeight());
        backgroundRectangle.resize(getWidth(), getHeight());
    }

    @Override public void itemClicked(Object item) 
    {
       
        
             if(Producto.class==item.getClass())
            {
              AbacoApp.s_producto=(Producto)item;
              
            }
                else
            {
             if(Kardex.class==item.getClass())
            {
              AbacoApp.s_kardex=(Kardex)item;
            }
            else  {
             if(GenMunicipios.class==item.getClass())
            {
              AbacoApp.s_genmunicipios=(GenMunicipios)item;
            }
             else
             {
                  if(Categoria.class==item.getClass())
            {
              AbacoApp.s_categoria=(Categoria)item;
            }
                  else
             {
                  if(Factura.class==item.getClass())
            {
              AbacoApp.s_factura=(Factura)item;
            }
             }
             }
             
        }
            }
    
         
       popover.hide();
       // pageBrowser.goToPage(item.getEnsemblePath());
    }

    @Override public void setPopover(Popover popover) {
        this.popover = popover;
    }

    @Override public Popover getPopover() {
        return popover;
    }

    @Override public Node getPageNode() {
        return this;
    }

    @Override public String getPageTitle() {
        return "";
    }

    @Override public String leftButtonText() {
        return null;
    }

    @Override public void handleLeftButton() {}

    @Override public String rightButtonText() {
        return null;
    }

    @Override public void handleRightButton() {
    }

    @Override public void handleShown() { }
    @Override public void handleHidden() { }
    
    
    @Override public ListCell<Object> call(ListView<Object> p) {
        return new SearchResultListCell();
    }
    
    private class IconPane extends Pane {
        private Region samplesIcon = new Region();
        private Label classesIcon = new Label("C");
        private Label propertiesIcon = new Label("P");
        private Label methodsIcon = new Label("M");
        private Label fieldsIcon = new Label("F");
        private Label enumsIcon = new Label("E");
        private Region documentationIcon = new Region();
        private List<SearchResultListCell> allCells = new ArrayList<>();
        private Rectangle classesLine = new Rectangle(0,0,40,1);
        private Rectangle propertiesLine = new Rectangle(0,0,40,1);
        private Rectangle methodsLine = new Rectangle(0,0,40,1);
        private Rectangle fieldsLine = new Rectangle(0,0,40,1);
        private Rectangle enumsLine = new Rectangle(0,0,40,1);
        private Rectangle documentationLine = new Rectangle(0,0,40,1);
        
        public IconPane() {
            getStyleClass().add("search-icon-pane");
            samplesIcon.getStyleClass().add("soluciones-icon");
            documentationIcon.getStyleClass().add("documentation-icon");
            classesLine.setFill(Color.web("#dfdfdf"));
            propertiesLine.setFill(Color.web("#dfdfdf"));
            methodsLine.setFill(Color.web("#dfdfdf"));
            fieldsLine.setFill(Color.web("#dfdfdf"));
            enumsLine.setFill(Color.web("#dfdfdf"));
            documentationLine.setFill(Color.web("#dfdfdf"));
           // getChildren().addAll(samplesIcon,classesIcon,propertiesIcon,methodsIcon,fieldsIcon,enumsIcon,documentationIcon,
             //                   classesLine,propertiesLine,methodsLine,fieldsLine,enumsLine,documentationLine);
            setMouseTransparent(true);
        }
        @Override protected void layoutChildren() {
            List<SearchResultListCell> visibleCells = new ArrayList<>(20);
            for (SearchResultListCell cell: allCells) {
                if (cell.isVisible()) visibleCells.add(cell);
            }
            Collections.sort(visibleCells, (Node o1, Node o2) -> Double.compare(o1.getLayoutY(), o2.getLayoutY()));
            
            samplesIcon.setLayoutX(8);
            samplesIcon.resize(24, 24);
            classesIcon.setLayoutX(8);
            classesIcon.resize(24, 24);
            propertiesIcon.setLayoutX(8);
            propertiesIcon.resize(24, 24);
            methodsIcon.setLayoutX(8);
            methodsIcon.resize(24, 24);
            fieldsIcon.setLayoutX(8);
            fieldsIcon.resize(24, 24);
            enumsIcon.setLayoutX(8);
            enumsIcon.resize(24, 24);
            documentationIcon.setLayoutX(8);
            documentationIcon.resize(24, 24);
            
            
            samplesIcon.setVisible(false);
            classesIcon.setVisible(false);
            propertiesIcon.setVisible(false);
            methodsIcon.setVisible(false);
            fieldsIcon.setVisible(false);
            enumsIcon.setVisible(false);
            documentationIcon.setVisible(false);
            classesLine.setVisible(false);
            propertiesLine.setVisible(false);
            methodsLine.setVisible(false);
            fieldsLine.setVisible(false);
            enumsLine.setVisible(false);
            documentationLine.setVisible(false);

            final int last = visibleCells.size()-1;
            DocumentType lastDocType = null;
           /* for(int index = 0; index <= last; index ++) {
                SearchResultListCell cell = visibleCells.get(index);
                DocumentType docType = getDocumentTypeForCell(cell);
                if (lastDocType != docType && docType != null) {
                    // this is first of this doc type
                    Node icon = getIconForDocType(docType);
                    icon.setVisible(true);
                    // calculate cell position relative to iconPane
                    Point2D cell00 = cell.localToScene(0, 0);
                    cell00 = sceneToLocal(cell00);
                    // check if next is differnt
                    if (index != last && getDocumentTypeForCell(visibleCells.get(index+1)) != docType) {
                        icon.setLayoutY(cell00.getY()+8);
                    } else {
                        icon.setLayoutY(Math.max(8,cell00.getY()+8));
                    }
                    // update line
                    Rectangle line = getLineForDocType(docType);
                    if (line != null) {
                        line.setVisible(true);
                        line.setLayoutY(cell00.getY());
                    }
                }
                lastDocType = docType;
            }*/

        }
        
      
    }
    
    
    private class SearchResultListCell extends ListCell<Object> implements Skin<SearchResultListCell>, EventHandler {
        private static final int TEXT_GAP = 6;
        private ImageView arrow = new ImageView(RIGHT_ARROW);
        private Label title = new Label();
        private Label details = new Label();
        private int cellIndex;
        private Rectangle topLine = new Rectangle(0,0,1,1);
        
        private SearchResultListCell() {
           super();
            //System.out.println("CREATED TimeSlot CELL " + (cellIndex));
            // we don't need any of the labeled functionality of the default cell skin, so we replace skin with our own
            // in this case using this same class as it saves memory. This skin is very simple its just a HBox container
            setSkin(this);
            setStyle("-fx-text-fill: #f4f4f4; \n" +
            "    -fx-font-size: 12;\n" +
            "    -fx-padding: 0 25 0 0;");
            if(AbacoApp.cb_temas.getSelectionModel().getSelectedIndex()==0)
            {    
            title.setStyle("fx-background-color: linear-gradient(\n" +
                                  "        #A6A6A6 20%,\n" +
                                  "        #A0A0A0 40%,\n" +
                                  "        #9D9D9D 60%,\n" +
                                  "        #A0A0A0 80%,\n" +
                                  "        #A3A3A3 100%\n" +
                                  "        );\n" +
                                  "    -fx-text-fill: white;");
            }
            else
            {
                title.setStyle("fx-background-color: linear-gradient(\n" +
                                  "        #A6A6A6 20%,\n" +
                                  "        #A0A0A0 40%,\n" +
                                  "        #9D9D9D 60%,\n" +
                                  "        #A0A0A0 80%,\n" +
                                  "        #A3A3A3 100%\n" +
                                  "        );\n" +
                                  "    -fx-text-fill: #007fff;"); 
            }
            
             getChildren().addAll(title);
            setOnMouseClicked(this);

            // listen to changes of this cell being added and removed from list 
            // and when it or its parent is moved. If any of those things change
            // then update the iconPane's layout. requestLayout() will be called
            // many times for any change of cell layout in the list but that 
            // dosn't matter as they will all be batched up by layout machanisim
            // and iconPane.layoutChildren() will only be called once per frame.
            final ChangeListener<Bounds> boundsChangeListener = (ObservableValue<? extends Bounds> ov, Bounds t, Bounds t1) -> {
                iconPane.requestLayout();
            };
            parentProperty().addListener((ObservableValue<? extends Parent> ov, Parent oldParent, Parent newParent) -> {
                if(oldParent != null) {
                    oldParent.layoutBoundsProperty().removeListener(boundsChangeListener);
                }
                if (newParent != null && newParent.isVisible()) {
                    iconPane.allCells.add(SearchResultListCell.this);
                    newParent.layoutBoundsProperty().addListener(boundsChangeListener);
                } else {
                    iconPane.allCells.remove(SearchResultListCell.this);
                }
                iconPane.requestLayout();
            });
        }

        @Override protected double computeMinWidth(double height) {
            final Insets insets = getInsets();
            final double h = height = insets.getBottom() - insets.getTop();
            return (int)((insets.getLeft() + title.minWidth(h) + TEXT_GAP + details.minWidth(h) + insets.getRight())+ 0.5d);
        }

        @Override protected double computePrefWidth(double height) {
            final Insets insets = getInsets();
            final double h = height = insets.getBottom() - insets.getTop();
            return (int)((insets.getLeft() + title.prefWidth(h) + TEXT_GAP + details.prefWidth(h) + insets.getRight())+ 0.5d);
        }

        @Override protected double computeMaxWidth(double height) {
            final Insets insets = getInsets();
            final double h = height = insets.getBottom() - insets.getTop();
            return (int)((insets.getLeft() + title.maxWidth(h) + TEXT_GAP + details.maxWidth(h) + insets.getRight())+ 0.5d);
        }

        @Override protected double computeMinHeight(double width) {
            final Insets insets = getInsets();
            final double w = width - insets.getLeft() - insets.getRight();
            return (int)((insets.getTop() + title.minHeight(w) + TEXT_GAP + details.minHeight(w) + insets.getBottom())+ 0.5d);
        }

        @Override protected double computePrefHeight(double width) {
            final Insets insets = getInsets();
            final double w = width - insets.getLeft() - insets.getRight();
            return (int)((insets.getTop() + title.prefHeight(w) + TEXT_GAP + details.prefHeight(w) + insets.getBottom())+ 0.5d);
        }

        @Override protected double computeMaxHeight(double width) {
            final Insets insets = getInsets();
            final double w = width - insets.getLeft() - insets.getRight();
            return (int)((insets.getTop() + title.maxHeight(w) + TEXT_GAP + details.maxHeight(w) + insets.getBottom())+ 0.5d);
        }

        @Override protected void layoutChildren() {
            final Insets insets = getInsets();
            final double left = insets.getLeft();
            final double top = insets.getTop();
            final double w = getWidth() - left - insets.getRight();
            final double h = getHeight() - top - insets.getBottom();
            final double titleHeight = title.prefHeight(w);
            title.setLayoutX(left);
            title.setLayoutY(top);
            title.resize(w, titleHeight);
            final double detailsHeight = details.prefHeight(w);
            details.setLayoutX(left);
            details.setLayoutY(top + titleHeight + TEXT_GAP);
            details.resize(w, detailsHeight);
            final Bounds arrowBounds = arrow.getLayoutBounds();
            arrow.setLayoutX(getWidth() - arrowBounds.getWidth() - 12);
            arrow.setLayoutY((int)((getHeight() - arrowBounds.getHeight())/2d));
            topLine.setLayoutX(left-5);
            topLine.setWidth(getWidth()-left+5);
        }

 
        // CELL METHODS
  @Override      
protected void updateItem(Object result, boolean empty) {
            super.updateItem(result,empty);
            if (result == null) { // empty item
                arrow.setVisible(false);
                title.setVisible(false);
                details.setVisible(false);
            } else {
                arrow.setVisible(true);
                title.setVisible(true);
                details.setVisible(true);
                
                             if(Producto.class==result.getClass())
                            {
                               title.setText(((Producto)result).getNombre());
                               details.setText(((Producto)result).getCodigo());
                            }
                              else
                          {
                             if(Kardex.class==result.getClass())
                            {
                               title.setText(((Kardex)result).getProducto().getCodigobarras()+"||"+((Kardex)result).getProducto().getNombre());
                               details.setText(((Kardex)result).getProducto().getCodigobarras());
                            }
                             else
                             {
                                 if(GenMunicipios.class==result.getClass())
                                 {
                                    title.setText(((GenMunicipios)result).getNombre()+"-"+((GenMunicipios)result).getGenDepartamentos().getNombre());
                                    details.setText(((GenMunicipios)result).getNombre());
                                 }
                                 else
                                 {
                                      if(Categoria.class==result.getClass())
                                 {
                                    title.setText(((Categoria)result).getM_NombreCat());
                                    details.setText(((Categoria)result).getM_NombreCat());
                                 }
                                      else
                                 {
                                      if(Personas.class==result.getClass())
                                 {
                                    title.setText(((Personas)result).getNombre());
                                    details.setText(((Personas)result).getNombre());
                                 }
                                            else
                                 {
                                      if(Proveedores.class==result.getClass())
                                 {
                                    title.setText(((Proveedores)result).getNombre());
                                    details.setText(((Proveedores)result).getNombre());
                                 }
                                                else
                                 {
                                      if(Factura.class==result.getClass())
                                 {
                                    title.setText(((Factura)result).getNofacturacerosizquierda());
                                    details.setText(((Factura)result).getNofacturacerosizquierda());
                                 }
                                 }
                                 }
                                 }
                                 }
                                 
                                   }
                                 }
                             }
                           
                           
                     
                     
                    
                
            
        }

        // SKIN METHODS
        @Override public SearchResultListCell getSkinnable() { return this; }
        @Override public Node getNode() { return null; }
        @Override public void dispose() {}
        @Override public void handle(Event t) {
            itemClicked(getItem());
            
        }
    }
}