package abaco;

import static abaco.PlatformFeatures.WEB_SUPPORTED;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import java.util.LinkedList;
import abaco.generated.Soluciones;
import abaco.solucionpage.SolucionPage;
import abaco.solucionpage.SourcePage;

/**
 * Sample page navigation with history.
 * 
 * Also knows how to create Ensemble pages.
 */
public class PageBrowser extends Region {
    public static final String HOME_URL = "home";
    private HomePage homePage;
    private Page currentPage;
    private SolucionPage solucionPage;
    private String currentPageUrl;
    private SourcePage sourcePage;
    private DocsPage docsPage;
    private LinkedList<String> pastHistory = new LinkedList<>();
    private LinkedList<String> futureHistory = new LinkedList<>();
    private BooleanProperty forwardPossible = new SimpleBooleanProperty(false);
    public ReadOnlyBooleanProperty forwardPossibleProperty() { return forwardPossible; }
    public boolean isForwardPossible() { return forwardPossible.get(); }
    private BooleanProperty backPossible = new SimpleBooleanProperty(false);
    public ReadOnlyBooleanProperty backPossibleProperty() { return backPossible; }
    public boolean isBackPossible() { return backPossible.get(); }
    private BooleanProperty atHome = new SimpleBooleanProperty(false);
    public ReadOnlyBooleanProperty atHomeProperty() { return atHome; }
    public boolean isAtHome() { return atHome.get(); }
    private StringProperty currentPageTitle = new SimpleStringProperty(null);
    public ReadOnlyStringProperty currentPageTitleProperty() { return currentPageTitle; };
    public String getCurrentPageTitle() { return currentPageTitle.get(); }
    
    public void forward() {
        String newUrl = futureHistory.pop();
        if (newUrl != null) {
            pastHistory.push(getCurrentPageUrl());
            goToPage(newUrl, null, false);
        }
    }
    
    public void backward() {
        String newUrl = pastHistory.pop();
        if (newUrl != null) {
            futureHistory.push(getCurrentPageUrl());
            goToPage(newUrl, null, false);
        }
    }
    
    public void goToSolucion(SolucionInfo solucion) {
        AbacoApp.nombresolucion=solucion.nameSolucion;
        goToPage("solucion://"+solucion.sofackarPath, solucion, true);
         
    }
    
    public void goToPage(String url) {
        goToPage(url, null, true);
    }
    
    public void goHome() {
        goToPage(HOME_URL, null, true);
    }

    /**
     * This is called when a inner url has changed inside of a page and we want 
     * to update the history.
     * 
     * @param newUrl The new url that the currentPage node is displaying
     */
    public void externalPageChange(String newUrl) {
        if (currentPageUrl != null) {
            pastHistory.push(getCurrentPageUrl());
        }
        futureHistory.clear();
        currentPageUrl = newUrl;
    }
    
    private void goToPage(String url, SolucionInfo solucion, boolean updateHistory) {
        Page nextPage = null;
        // get node for page
        if (url.equals(HOME_URL)) {
            nextPage = getHomePage();
        } else if (url.startsWith("http://") || url.startsWith("https://")) {
            if (WEB_SUPPORTED) {
                nextPage = updateDocsPage(url);
            } else {
                System.err.println("Web pages are not supported and links to them should be disabled!");
            }
        } else if (solucion != null) {
            nextPage = updateSamplePage(solucion, url);
        } else if (url.startsWith("solucion://")) {
            String solucionPath = url.substring("solucion://".length());
            if (solucionPath.contains("?")) {
                solucionPath = solucionPath.substring(0, solucionPath.indexOf('?') - 1);
            }
            solucion = Soluciones.ROOT.solucionForPath(solucionPath);
            if (solucion != null) {
                nextPage = updateSamplePage(solucion, url);
            } else {
                throw new UnsupportedOperationException("Unknown sample url ["+url+"]");
            }
        } else if (url.startsWith("sample-src://")) {
            String samplePath = url.substring("sample-src://".length());
            if (samplePath.contains("?")) {
                samplePath = samplePath.substring(0, samplePath.indexOf('?') - 1);
            }
            solucion = Soluciones.ROOT.solucionForPath(samplePath);
            if (solucion != null) {
                nextPage = updateSourcePage(solucion);
            } else {
                System.err.println("Unknown sample url [" + url + "]");
            }
        } else {
            System.err.println("Unknown ensemble page url [" + url + "]");
        }
        if (nextPage != null) {
            // update history
            if (updateHistory) {
                if (currentPageUrl != null) {
                    pastHistory.push(getCurrentPageUrl());
                }
                futureHistory.clear();
            }
            currentPageUrl = url;
            // remove old page
            if (currentPage != null) {
                getChildren().remove(currentPage);
            }
            currentPage = nextPage;
            getChildren().add(currentPage.getNode());
            // update properties
            atHome.set(url.equals(HOME_URL));
            forwardPossible.set(!futureHistory.isEmpty());
            backPossible.set(!pastHistory.isEmpty());
            currentPageTitle.bind(currentPage.titleProperty());
        }
    }

    @Override protected void layoutChildren() {
        if (currentPage != null) {
            currentPage.getNode().resize(getWidth(), getHeight());
        }
    }

    public String getCurrentPageUrl() {
        return currentPageUrl;
    }

    private SolucionPage updateSamplePage(SolucionInfo solucion, String url) {
        if (solucionPage == null) {
            solucionPage = new SolucionPage(solucion, url, this);
        } else {
          solucionPage.update(solucion,url);
        }
        return solucionPage;
    }

    

    private Page getHomePage() {
        if (homePage == null) {
            homePage = new HomePage(this);
        }
        return homePage;
    }

    private DocsPage updateDocsPage(String url) {
        if (docsPage == null) {
            docsPage = new DocsPage(this);
        }
        docsPage.goToUrl(url);
        return docsPage;
    }
    private SourcePage updateSourcePage(SolucionInfo solucion) {
        if (sourcePage == null) {
            sourcePage = new SourcePage();
        }
        sourcePage.setSolucionInfo(solucion);
        return sourcePage;
    }
}
