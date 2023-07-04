package solucionpage;

import fxnomina.Page;
import fxnomina.SolucionInfo;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.scene.Node;
import javafx.scene.control.TabPane;

/**
 * Page showing tabs with all the source code and resources for a sample
 */
public class SourcePage extends TabPane implements Page {

    private final ObjectProperty<SolucionInfo> solucionInfoProperty = new SimpleObjectProperty<>();
    private final StringProperty titleProperty = new SimpleStringProperty();

    public SourcePage() {
        getStyleClass().add("source-page");
        titleProperty.bind(new StringBinding() {
            {
                bind(solucionInfoProperty);
            }

            @Override
            protected String computeValue() {
                SolucionInfo solucion = solucionInfoProperty.get();
                if (solucion != null) {
                    return solucion.name + " :: Source";
                } else {
                    return null;
                }
            }
        });
        setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
    }

    @Override
    public ReadOnlyStringProperty titleProperty() {
        return titleProperty;
    }

    @Override
    public String getTitle() {
        return titleProperty.get();
    }

    @Override
    public String getUrl() {
        return "solucion-src://" + solucionInfoProperty.get().sofackarPath;
    }

    @Override
    public Node getNode() {
        return this;
    }

    public void setSolucionInfo(SolucionInfo solucionInfo) {
        solucionInfoProperty.set(solucionInfo);
        getTabs().clear();

    }

}
