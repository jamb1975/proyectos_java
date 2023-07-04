
package abaco;

import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Pagination;
import javafx.scene.control.Skin;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.Callback;
import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import javafx.scene.layout.Pane;
import abaco.generated.Soluciones;
import static abaco.AbacoApp.SHOW_HIGHLIGHTS;
import jfxtras.styles.jmetro8.JMetro;

/**
 * The home page for ensemble.
 */
public class HomePage extends ListView<HomePage.HomePageRow> implements Callback<ListView<HomePage.HomePageRow>, ListCell<HomePage.HomePageRow>>, ChangeListener<Number>, Page{
    private static final int ITEM_WIDTH = 216;
    private static final int ITEM_HEIGHT = 162;
    private static final int ITEM_GAP = 8;
    private static final int MIN_MARGIN = 20;
    private static enum RowType{Highlights,Title,Soluciones};
    private int numberOfColumns = -1;
    private final HomePageRow HIGHLIGHTS_ROW = new HomePageRow(RowType.Highlights,null,null,null);
    private final PageBrowser pageBrowser;
    private final ReadOnlyStringProperty titleProperty = new ReadOnlyStringWrapper();

    public HomePage(PageBrowser pageBrowser) {
        this.pageBrowser = pageBrowser;
        setId("HomePage");
        // don't use any of the standard ListView CSS
        getStyleClass().clear();
        // listen for when the list views width changes and recalculate number of columns
         setStyle("-fx-text-fill: white;");
        new JMetro(JMetro.Style.DARK).applyTheme(this);
        widthProperty().addListener(this);
        // set our custom cell factory
        setCellFactory(this);
    }

    @Override public ReadOnlyStringProperty titleProperty() {
        return titleProperty;
    }

    @Override public String getTitle() {
        return titleProperty.get();
    }

    @Override public String getUrl() {
        return PageBrowser.HOME_URL;
    }

    @Override public Node getNode() {
        return this;
    }

    /* Called when the ListView's width changes */
    @Override public void changed(ObservableValue<? extends Number> observableValue, Number number, Number newWidth) {
        // calculate new number of columns that will fit
        double width = newWidth.doubleValue();
        width -= 60;
        final int newNumOfColumns = Math.max(1, (int) ((width+450) / (ITEM_WIDTH + ITEM_GAP)));
        // our size may have changed, so see if we need to rebuild items list
        if (numberOfColumns != newNumOfColumns) {
            numberOfColumns = newNumOfColumns;
            rebuild();
        }
    }

    @Override public ListCell<HomePageRow> call(ListView<HomePageRow> homePageRowListView) {
        return new HomeListCell();
    }

    // Called to rebuild the list's items based on the current number of columns
    private void rebuild() {
        // build new list of titles and samples
        List<HomePageRow> newItems = new ArrayList<>();
        
        // add Highlights to top
        if (SHOW_HIGHLIGHTS) {
           // newItems.add(HIGHLIGHTS_ROW);
        }
        // add any samples directly in root category
        addSolucionRows(newItems,Soluciones.ROOT.soluciones);
        // add samples for all sub categories
        for(SolucionCategory category: Soluciones.ROOT.subCategories) {
            // add title row
           newItems.add(new HomePageRow(RowType.Title,category.name,null,category.title));
            // add samples
            addSolucionRows(newItems,category.solucionesAll);
        }
        // replace the lists items
        getItems().setAll(newItems);
    }

    /**
     * Add samples rows to the items list for all SampleInfo's in samples array
     *
     * @param items The list of rows to add too
     * @param samples The SampleInfo's to create rows for
     */
    private void addSolucionRows(List<HomePageRow> items, SolucionInfo[] samples) {
        if(samples == null) return;
        for(int row = 0; row < Math.ceil((double) samples.length / numberOfColumns); row++) {
            int sampleIndex = row*numberOfColumns;
            SolucionInfo[] sampleInfos = Arrays.copyOfRange(samples,sampleIndex, Math.min(sampleIndex+numberOfColumns,samples.length));
            items.add(new HomePageRow(RowType.Soluciones, null, sampleInfos,null));
        }
    }

    private Reference<Pagination> paginationCache;
    private ImageView highlightRibbon;
    private Map<String, SectionRibbon> ribbonsCache = new WeakHashMap<>();
    private Map<SolucionInfo, Button> buttonCache = new WeakHashMap<>();

    private static int cellCount = 0;
    private static final PseudoClass TITLE_PSEUDO_CLASS = PseudoClass.getPseudoClass(RowType.Title.toString());

    private class HomeListCell extends ListCell<HomePageRow> implements Callback<Integer,Node>,  Skin<HomeListCell> {
        private static final double HIGHLIGHTS_HEIGHT = 400;
        private static final double RIBBON_HEIGHT = 60;
        private static final double DEFAULT_HEIGHT = 150;
        private static final double DEFAULT_WIDTH = 150;
        private double height = DEFAULT_HEIGHT;
        int cellIndex;
        private RowType oldRowType = null;
        private HBox box = new HBox(ITEM_GAP);
        private HomeListCell() {
            super();
            getStyleClass().clear();
            cellIndex = cellCount++;
           
            
            // we don't need any of the labeled functionality of the default cell skin, so we replace skin with our own
            // in this case using this same class as it saves memory. This skin is very simple its just a HBox container
            setSkin(this);
        }

        @Override protected double computeMaxHeight(double d) {
            return height;
        }

        @Override protected double computePrefHeight(double d) {
            return height;
        }

        @Override protected double computeMinHeight(double d) {
            return height;
        }

        @Override protected double computeMaxWidth(double height) {
            return Double.MAX_VALUE;
        }

        @Override protected double computePrefWidth(double height) {
            return DEFAULT_WIDTH;
        }

        @Override protected double computeMinWidth(double height) {
            return DEFAULT_WIDTH;
        }

        // CELL METHODS
        @Override protected void updateItem(HomePageRow item, boolean empty) {
            super.updateItem(item, empty);
            box.pseudoClassStateChanged(TITLE_PSEUDO_CLASS,item !=null && item.rowType == RowType.Title);
            if (item == null) {
                oldRowType = null;
                box.getChildren().clear();
                height = DEFAULT_HEIGHT;
            } else {
                switch (item.rowType) {
                    case Highlights:
                       /* if (oldRowType != RowType.Highlights) {
                            height = HIGHLIGHTS_HEIGHT;
                            Pagination pagination = paginationCache == null ? null 
                                    : paginationCache.get();
                            if (pagination == null) {
                                pagination = new Pagination(Soluciones.HIGHLIGHTS.length);
                                pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
                                pagination.setMaxWidth(USE_PREF_SIZE);
                                pagination.setMaxHeight(USE_PREF_SIZE);
                                pagination.setPageFactory(this);
                                paginationCache = new WeakReference<>(pagination);
                            }
                            if (highlightRibbon == null) {
                                highlightRibbon = new ImageView(new Image(getClass().getResource("images/highlights-ribbon.png").toExternalForm()));
                                highlightRibbon.setManaged(false);
                                highlightRibbon.layoutXProperty().bind(pagination.layoutXProperty().add(5));
                                highlightRibbon.layoutYProperty().bind(pagination.layoutYProperty().add(5));
                            }
                            box.setAlignment(Pos.CENTER);
                            box.getChildren().setAll(pagination, highlightRibbon);
                        }*/
                        break;
                    case Title:
                        height = RIBBON_HEIGHT;
                        SectionRibbon ribbon = ribbonsCache.get(item.title);
                       
                        if (ribbon == null) {
                            ribbon = new SectionRibbon(item.title.toUpperCase());
                            ribbonsCache.put(item.title, ribbon);
                        }
                        box.getChildren().setAll(ribbon);
                        box.setAlignment(Pos.CENTER);
                        break;
                    case Soluciones:
                        height = DEFAULT_HEIGHT;
                        box.setAlignment(Pos.CENTER);
                        box.getChildren().clear();
                        for (int i = 0; i < item.soluciones.length; i++) {
                            final SolucionInfo solucion = item.soluciones[i];
                            Button solucionButton = buttonCache.get(solucion);
                            if (solucionButton == null) {
                                solucionButton = new Button();
                               // solucionButton.getStyleClass().setAll("solucion-button");
                                solucionButton.setContentDisplay(ContentDisplay.TOP);
                                solucionButton.setText(solucion.nameSolucion);
                                solucionButton.setGraphic(solucion.getMediumPreview());
                                solucionButton.setOnAction((ActionEvent actionEvent) -> {
                                pageBrowser.goToSolucion(solucion);
                                });
                                buttonCache.put(solucion, solucionButton);
                            }
                            if (solucionButton.getParent() != null) {
                                ((Pane) solucionButton.getParent()).getChildren().remove(solucionButton);
                            }
                            box.getChildren().add(solucionButton);
                        }
                        break;
                }
                oldRowType = item.rowType;
            }
        }

        // SKIN METHODS
        @Override public HomeListCell getSkinnable() { return this; }
        @Override public Node getNode() { return box; }
        @Override public void dispose() {}
        // CALLBACK METHODS
        @Override public Node call(final Integer highlightIndex) {
            Button sampleButton = new Button();
            //sampleButton.getStyleClass().setAll("solucion-button");
            sampleButton.setGraphic(Soluciones.HIGHLIGHTS[highlightIndex].getLargePreview());
            sampleButton.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            sampleButton.setOnAction((ActionEvent actionEvent) -> {
            pageBrowser.goToSolucion(Soluciones.HIGHLIGHTS[highlightIndex]);
           
            });
            return sampleButton;
        }
    }

    public static class HomePageRow {
        public final RowType rowType;
        public final String title;
        public final SolucionInfo[] soluciones;

        private HomePageRow(RowType rowType, String title, SolucionInfo[] soluciones,String title2) 
        {
            this.rowType = rowType;
            this.title = title2;
            this.soluciones = soluciones;
        }

        @Override
        public String toString() {
            return "HomePageRow{" + "rowType=" + rowType + ", title=" + title + ", soluciones=" + soluciones + '}';
        }
    }

    private static class SectionRibbon extends Text {
        public SectionRibbon(String text) {
            super(text);
            if(AbacoApp.cb_temas.getSelectionModel().getSelectedIndex()==0)
            {
            setStyle(" -fx-font-family: \"Source Sans Pro ExtraLight\";\n" +
                                "    -fx-font-size: 30;\n" +
                                "    -fx-fill: white;");
            }else
            {
               setStyle(" -fx-font-family: \"Source Sans Pro ExtraLight\";\n" +
                                "    -fx-font-size: 30;\n" +
                                "    -fx-fill: #007fff;"); 
            }
        }
    }
//    private static class SectionRibbon extends Region {
//        private Text textNode = new Text();
//        public SectionRibbon(String text) {
//            textNode.setText(text);
//            textNode.getStyleClass().add("section-ribbon-text");
//            getStyleClass().add("section-ribbon");
//            setPrefHeight(50);
//            setMaxWidth(USE_PREF_SIZE);
//    //        textNode.setEffect(RIBBON_EFFECT);
//            getChildren().add(textNode);
//        }
//
//        public void setText(String text) {
//            textNode.setText(text);
//        }
//
//        @Override protected void layoutChildren() {
//            final Bounds textBounds = textNode.getBoundsInParent();
//            System.out.println("textBounds = " + textBounds);
//            System.out.println("getWidth() = " + getWidth());
//            textNode.relocate(0,
//                    snapPosition((getHeight() - textBounds.getHeight()) / 2) - 3);
//        }
//    }
}
