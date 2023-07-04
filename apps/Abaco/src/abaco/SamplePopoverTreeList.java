package abaco;

import abaco.control.Popover;
import abaco.control.PopoverTreeList;
import abaco.generated.Soluciones;
import java.util.Comparator;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import abaco.control.PopoverMenu;
import abaco.control.PopoverTreeListMenu;

/**
 * Popover page that displays a list of samples and sample categories for a given
 * SampleCategory.
 */
public class SamplePopoverTreeList extends PopoverTreeListMenu implements PopoverMenu.Page {
    private PopoverMenu popoverMenu;
    private SolucionCategory category;
    private PageBrowser pageBrowser;

    public SamplePopoverTreeList(SolucionCategory category, PageBrowser pageBrowser) {
        this.category = category;
        this.pageBrowser = pageBrowser;
        if (category.subCategories!=null ) {
            if(category.name!=null)
            getItems().addAll(category.subCategories);
        
        }
        if (category.soluciones!=null ) {
            if(category.title!=null)
            getItems().addAll(category.soluciones);
        
        }
        getItems().sort(new Comparator() {
            
            private String getName(Object o) {
                if (o instanceof SolucionCategory) {
                    return ((SolucionCategory) o).title;
                } else if (o instanceof SolucionInfo) {
                    return ((SolucionInfo) o).nameSolucion;
                } else {
                    return "";
                }
            }

            @Override
            public int compare(Object o1, Object o2) {
                return getName(o1).compareTo(getName(o2));
            }
        });
    }
    
    @Override public ListCell call(ListView p) {
        return new SolucionItemListCell();
    }

    @Override protected void itemClicked(Object item) {
        if (item instanceof SolucionCategory) {
            popoverMenu.pushPage(new SamplePopoverTreeList((SolucionCategory)item, pageBrowser));
        } else if (item instanceof SolucionInfo) {
            popoverMenu.hide();
            pageBrowser.goToSolucion((SolucionInfo)item);
        }
    }

    @Override public void setPopoverMenu(PopoverMenu popoverMenu) {
        this.popoverMenu = popoverMenu;
    }

    @Override public PopoverMenu getPopoverMenu() {
        return popoverMenu;
    }

    @Override public Node getPageNode() {
        return this;
    }

    @Override public String getPageTitle() {
        return "Soluciones";
    }

    @Override public String leftButtonText() {
        return category == Soluciones.ROOT ? null : "< Atras";
    }

    @Override public void handleLeftButton() {
        popoverMenu.popPage();
    }

    @Override public String rightButtonText() {
        return "Ninguno";
    }

    @Override public void handleRightButton() {
        popoverMenu.hide();
    }

    @Override public void handleShown() { }
    @Override public void handleHidden() { }
    
    
    private class SolucionItemListCell extends ListCell implements EventHandler<MouseEvent> {
        private ImageView arrow = new ImageView(RIGHT_ARROW);
        private Region icon = new Region();

        private SolucionItemListCell() {
            super();
           // getStyleClass().setAll("solucion-tree-list-cell");
            setOnMouseClicked(this);
            setGraphic(icon);
        }
        
        @Override public void handle(MouseEvent t) {
            itemClicked(getItem());
        }

        @Override protected double computePrefWidth(double height) {
            return 100;
        }

        @Override protected double computePrefHeight(double width) {
            return 44;
        }

        @Override protected void layoutChildren() {
            if (arrow.getParent() != this) getChildren().add(arrow);
            super.layoutChildren();
            final int w = (int)getWidth();
            final int h = (int)getHeight();
            final Bounds arrowBounds = arrow.getLayoutBounds();
            arrow.setLayoutX(w - arrowBounds.getWidth() - 12);
            arrow.setLayoutY((int)((h - arrowBounds.getHeight())/2d));
        }
        
        // CELL METHODS
        @Override protected void updateItem(Object item, boolean empty) {
            // let super do its work
            super.updateItem(item,empty);
            // update our state
            if (item == null) { // empty item
                setText(null);
                arrow.setVisible(false);
                icon.getStyleClass().clear();
            } else {
                
                arrow.setVisible(true);
                if (item instanceof SolucionCategory) {
                    setText(((SolucionCategory)item).title);
                    icon.getStyleClass().setAll("folder-icon");
                } else {
                    setText(((SolucionInfo)item).nameSolucion);
                    icon.getStyleClass().setAll("soluciones-icon");
                }
            }
        }
    }
}