package abaco.solucionpage;

import abaco.Page;
import abaco.PageBrowser;
import abaco.SolucionInfo;
import static abaco.SolucionInfo.SolucionRuntimeInfo;

import javafx.beans.binding.ObjectBinding;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.util.Callback;

/**
 * Page for showing a sample
 */
public class SolucionPage extends StackPane implements Page {
    static final double INDENT = 8;
    final ObjectProperty<SolucionInfo> solucionInfoProperty = new SimpleObjectProperty<>();
    private final StringProperty titleProperty = new SimpleStringProperty();
    PageBrowser pageBrowser;
    final ObjectProperty<SolucionRuntimeInfo> solucionRuntimeInfoProperty = new SimpleObjectProperty<>();

    public SolucionPage(SolucionInfo sampleInfo, String url, final PageBrowser pageBrowser) {
        solucionInfoProperty.set(sampleInfo);
        this.pageBrowser = pageBrowser;
       
        titleProperty.bind(new StringBinding() {
            { bind(solucionInfoProperty); }
            @Override protected String computeValue() {
                SolucionInfo sample = SolucionPage.this.solucionInfoProperty.get();
                if (sample != null) {
                    return sample.nameSolucion;
                } else {
                    return null;
                }
            }
        });
        solucionRuntimeInfoProperty.bind(new ObjectBinding<SolucionRuntimeInfo>() {
            { bind(solucionInfoProperty); }
            @Override protected SolucionRuntimeInfo computeValue() {
                return solucionInfoProperty.get().buildSolucionNode();
            }
        });

        SolucionPageContent frontPage = new SolucionPageContent(this);
        getChildren().setAll(frontPage);
    }

    public void update(SolucionInfo sampleInfo,String url) {
        solucionInfoProperty.set(sampleInfo);
    }

    @Override public ReadOnlyStringProperty titleProperty() {
        return titleProperty;
    }

    @Override public String getTitle() {
        return titleProperty.get();
    }

    @Override public String getUrl() {
        return "solucion://" + solucionInfoProperty.get().sofackarPath;
    }

    @Override public Node getNode() {
        return this;
    }
    
    String apiClassToUrl(String classname) {
        String urlEnd = classname.replaceAll("\\.([a-z])", "/$1").replaceFirst("\\.([A-Z])", "/$1");
        if (classname.startsWith("javafx")) {
            return "http://download.java.net/jdk8/jfxdocs/"+urlEnd+".html";
        } else {
            return "http://download.java.net/jdk8/docs/api/"+urlEnd+".html";
        }
    }

    /**
     * This method is equivalent to bind(ObjectBinding) as it would invoke
     * updater immediately as well as on any change to SampleInfo
     * @param updater a method that updates content for a given SampleInfo
     */
    void registerSolucionInfoUpdater(final Callback<SolucionInfo, Void> updater) {
        solucionInfoProperty.addListener((ObservableValue<? extends SolucionInfo> ov, SolucionInfo t, SolucionInfo sampleInfo) -> {
            updater.call(sampleInfo);
        });
        updater.call(solucionInfoProperty.get());
    }
}
