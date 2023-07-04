package solucionpage;

import fxnomina.PlatformFeatures;
import fxnomina.SolucionInfo;
import javafx.scene.Node;
import javafx.scene.layout.Region;
import javafx.scene.text.Text;
import javafx.util.Callback;

import static solucionpage.SolucionPage.INDENT;

/**
 * The content for Sample Page
 */
class SolucionPageContent extends Region {

    private Node playground;
    private boolean needsPlayground;
    final SolucionPage solucionPage;
    private SolucionContainer solucionContainer;

    SolucionPageContent(final SolucionPage solucionPage) {
        this.solucionPage = solucionPage;
        playground = new PlaygroundNode(solucionPage);
        solucionPage.registerSolucionInfoUpdater((SolucionInfo sampleInfo) -> {
            update(sampleInfo);
            return null;
        });
    }

    @Override
    protected void layoutChildren() {
        super.layoutChildren();

        double maxWidth = getWidth() - 2 * INDENT;
        double maxHeight = getHeight() - 2 * INDENT;

        boolean landscape = getWidth() >= getHeight();
        boolean wide = getWidth() >= getHeight() * 1.5;
        if (wide) {
            // Sample on right, everything else on left
            double x = Math.round(getWidth() / 2 + INDENT / 2);
            double w = getWidth() - INDENT - x;
            //solucionContainer.resizeRelocate(INDENT, INDENT, (getWidth() - 3 * INDENT) / 2, maxHeight);
            solucionContainer.resizeRelocate(INDENT, INDENT, maxWidth, (getHeight() - 3 * INDENT) / 2);
            if (needsPlayground) {
                double h = (getHeight() - INDENT * 3) / 2;
                playground.resizeRelocate(INDENT, Math.round(INDENT * 2 + h), w, h);
            } else {
            }
        } else {
            // Sample on top, everything else on bottom
            solucionContainer.resizeRelocate(INDENT, INDENT, maxWidth, (getHeight() - 3 * INDENT) / 2);
            double y = Math.round(getHeight() / 2 + INDENT / 2);
            if (landscape) {
                double h = getHeight() - INDENT - y;
                if (needsPlayground) {
                    double w = (getWidth() - INDENT * 3) / 2;
                    playground.resizeRelocate(INDENT, y, w, h);
                } else {

                }
            } else {
                double w = getWidth() - INDENT * 2;
                if (needsPlayground) {
                    double h = (getHeight() - INDENT * 2 - y) / 2;
                    playground.resizeRelocate(INDENT, y, w, h);

                } else {
                    double h = getHeight() - INDENT - y;
                }
            }
        }
    }

    static Text title(String text) {
        Text title = new Text(text);
        title.getStyleClass().add("solucion-page-box-title");
        return title;
    }

    private void update(SolucionInfo solucionInfo) {
        solucionContainer = new SolucionContainer(solucionPage.solucionRuntimeInfoProperty.get().getSolucionNode());
        solucionContainer.getStyleClass().add("solucion-page-sample-node");
        if (!PlatformFeatures.DISPLAY_PLAYGROUND) {
            needsPlayground = false;
        } else {
            needsPlayground = solucionInfo.needsPlayground();
        }
        if (needsPlayground) {
            getChildren().setAll(solucionContainer, playground);
        } else {
            getChildren().setAll(solucionContainer);
        }
    }
}
