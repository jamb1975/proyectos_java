package sihic;

import sihic.playground.PlaygroundProperty;
import sihic.solucionpage.SolucionPage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.ConditionalFeature;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Descriptor for a ensemble sample. Everything the ui needs is determined at
 * compile time from the sample sources and stored in these SampleInfo objects
 * so we don't have to calculate anything at runtime.
 */
public class SolucionInfo {

    // =============== BASICS ==================================================
    public  String name;
    public  String nameSolucion;
    public  String description;
    public  String sofackarPath;

    // =============== SOURCES & RESOURCES =====================================
    /**
     * The base URI for all the source files and resources for this sample.
     */
    public  String baseUri;
    /**
     * All the files needed by this sample. Relative to the sample base URI.
     */
    /**
     * The URL for the source of the sample main file. Relative to the sample
     * base URI
     */

    /**
     * Full classpath for sample's application class
     */
    public  String appClass;
    /**
     * ClassPath Url for preview image of size 206x152
     */
    public  String previewUrl;
    /**
     * List of properties in the sample that can be played with
     */
    public final PlaygroundProperty[] playgroundProperties;
    /**
     * List of features that require specific platform support
     */
    public final ConditionalFeature[] conditionalFeatures;
    /**
     * If true, then the sample runs on embedded platform
     */
    public final boolean runsOnEmbedded;

    // =============== RELATED =================================================
    /**
     * Array of classpaths to related docs.
     */
    /**
     * Array of urls to related (non-api) docs given as pairs (url, name).
     */
    public SolucionInfo(String name, String description, String ensemblePath, String baseUri, String appClass,
            String previewUrl, PlaygroundProperty[] playgroundProperties, ConditionalFeature[] conditionalFeatures,
            boolean runsOnEmbedded, String nameSolucion) {
        this.name = name;
        this.description = description;
        this.sofackarPath = ensemblePath;
        this.baseUri = baseUri;
        this.appClass = appClass;
        this.playgroundProperties = playgroundProperties;
        this.conditionalFeatures = conditionalFeatures;
        this.runsOnEmbedded = runsOnEmbedded;
        this.nameSolucion = nameSolucion;
        if (SihicApp.PRELOAD_PREVIEW_IMAGES) {
            // Note: there may be missing classes/resources due to some filtering
            if (PlatformFeatures.USE_EMBEDDED_FILTER && !runsOnEmbedded) {
                // we should skip loading this image which will not ever be shown
            } else {
                java.net.URL url = getClass().getResource(previewUrl);
                if (url != null) {
                    getImage(url.toExternalForm());
                } else {
                    // mark this previewURL as missing
                    System.out.println("Note: Sample preview " + ensemblePath + " not found");
                    previewUrl = null;
                }
            }
        }

        this.previewUrl = previewUrl;
    }

    @Override
    public String toString() {
        return name;
    }

    // 460 x 345 - 5x5px border = 450x335
    public Node getLargePreview() {
//        if (previewUrl != null) {
//            String url = getClass().getResource(previewUrl).toExternalForm();
//            label.setBackground(new Background(
//                new BackgroundImage(
//                    new Image(url), 
//                    BackgroundRepeat.NO_REPEAT, 
//                    BackgroundRepeat.NO_REPEAT, 
//                    new BackgroundPosition(Side.LEFT,5,false, Side.TOP,5,false), 
//                    new BackgroundSize(450, 335, false, false, false, false)
//                )));
//        }

        return new LargePreviewRegion();
    }

    // 216x162 - 5+5px border = 206x152
    public Node getMediumPreview() {
        Region label = new Region();
        CornerRadii cornerRadii = new CornerRadii(8);
        Color color = Color.valueOf("#000000");

        if (previewUrl != null) {

            if (nameSolucion.equals("Compte. Apertura") || nameSolucion.equals("Compra. Planta Equipo") || nameSolucion.equals("Compra. Medtos") || nameSolucion.equals("Compra. Insumos")) {
                color = Color.valueOf("#0066CC");
            } else {
                if (nameSolucion.equals("Gastos Fijos") || nameSolucion.equals("Honorarios y Serv.")) {
                    color = Color.valueOf("#ffcc00");
                } else {
                    if (nameSolucion.equals("Facturar") || nameSolucion.equals("Compte. Ingresos")) {
                        color = Color.valueOf("#009900");
                    } else {
                        if (nameSolucion.equals("Recibo de Caja") || nameSolucion.equals("Compte. Egresos")) {
                            color = Color.valueOf("#ff0000");
                        } else {
                            if (nameSolucion.equals("Compte. Contabilidad")) {
                                color = Color.valueOf("#606060");
                            } else {
                                if (nameSolucion.equals("Admin. Planta y Equipo") || nameSolucion.equals("Admin. Medicamentos") || nameSolucion.equals("Admin. Insumos")) {
                                    color = Color.valueOf("darkblue");
                                } else {
                                    if (nameSolucion.equals("Admin Gastos") || nameSolucion.equals("Admin Honorarios y Serv")) {
                                        color = Color.valueOf("darkgoldenrod");
                                    } else {
                                        if (nameSolucion.equals("Admin. Consignaciones")) {
                                            color = Color.valueOf("#006600");
                                        } else {
                                            if (nameSolucion.equals("Admin. Nota Débito")) {
                                                color = Color.valueOf("#66CC33");
                                            } else {
                                                if (nameSolucion.equals("Admin. Nota Crédito")) {
                                                    color = Color.valueOf("#990000");
                                                } else {
                                                    if (nameSolucion.equals("Admin. Nota Contabilidad")) {
                                                        color = Color.valueOf("#606060");
                                                    } else {
                                                        if (nameSolucion.equals("Admin. Cargos Sueldos")) {
                                                            color = Color.valueOf("#333333");
                                                        } else {
                                                            if (nameSolucion.equals("Admin. Empleados")) {
                                                                color = Color.valueOf("#333333");
                                                            }
                                                           else
                                                            {
                                                                if(nameSolucion.equals("Cargos y Sueldos"))
                                                                {
                                                                    color = Color.valueOf("#ff1b00");
                                                                }
                                                                 else
                                                            {
                                                                if(nameSolucion.equals("Admin. Empleados"))
                                                                {
                                                                    color = Color.valueOf("#FFBF00");
                                                                }
                                                                else
                                                            {
                                                                if(nameSolucion.equals("Factor Horas Extras"))
                                                                {
                                                                    color = Color.valueOf("#4169e1");
                                                                }
                                                                else
                                                            {
                                                                if(nameSolucion.equals("Parametros Nomina"))
                                                                {
                                                                    color = Color.valueOf("#CD7F32");
                                                                }
                                                                else
                                                            {
                                                                if(nameSolucion.equals("Pago Nomina"))
                                                                {
                                                                    color = Color.valueOf("#7d2181");
                                                                }
                                                                else
                                                                {
                                                                   if(nameSolucion.equals("Libro Mayor y Balances"))
                                                                {
                                                                    color = Color.valueOf("#7d2181");
                                                                } 
                                                                    if(nameSolucion.equals("Radicar y Aceptar Facturas"))
                                                                {
                                                                    color = Color.valueOf("#009900");
                                                                }
                                                                    else
                                                                    {
                                                                   if(nameSolucion.equals("Conciliaciones Bancarias"))
                                                                   {
                                                                      color = Color.valueOf("#FF6600");
                                                                    } 
                                                                   if(nameSolucion.equals("Facturación Masiva"))
                                                                   {
                                                                      color = Color.valueOf("#009900");
                                                                    } 
                                                                   else
                                                                   {
                                                                     if(nameSolucion.equals("Causar Facturas"))
                                                                   {
                                                                      color = Color.valueOf("#009900");
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
            }
            String url = getClass().getResource(previewUrl).toExternalForm();
            label.setBackground(
                    new Background(
                            new BackgroundFill[]{
                                new BackgroundFill(color, cornerRadii, Insets.EMPTY)
                            },
                            new BackgroundImage[]{
                                new BackgroundImage(
                                        getImage(url),
                                        BackgroundRepeat.NO_REPEAT,
                                        BackgroundRepeat.NO_REPEAT,
                                        new BackgroundPosition(Side.LEFT, 12, false, Side.TOP, 10, false),
                                        new BackgroundSize(90, 90, false, false, false, false)
                                )
                            }
                    ));

        }
        DropShadow dsEffect = new DropShadow();
        dsEffect.setOffsetX(0);
        dsEffect.setOffsetY(0);
        dsEffect.setColor(Color.BLACK);
        dsEffect.setRadius(30);
      //  label.getStyleClass().add("sample-medium-preview");
        label.setMinSize(116, 106);
        label.setPrefSize(116, 106);
        label.setMaxSize(116, 106);
        label.setOnMouseEntered(e -> {
            label.setEffect(dsEffect);
        });
        label.setOnMouseExited(e -> {
            label.setEffect(null);
        });
        if (nameSolucion.equals("")) {
            label.visibleProperty().setValue(false);
            return null;
        }
        return label;
    }

    public SolucionRuntimeInfo buildSolucionNode() {
        try {
            Method play = null;
            Method stop = null;
            Class clz = Class.forName(appClass);
            final Object app = clz.newInstance();
            Parent root = (Parent) clz.getMethod("createContent").invoke(app);

            for (Method m : clz.getMethods()) {
                switch (m.getName()) {
                    case "play":
                        play = m;
                        break;
                    case "stop":
                        stop = m;
                        break;
                }
            }
            final Method fPlay = play;
            final Method fStop = stop;

            root.sceneProperty().addListener((ObservableValue<? extends Scene> ov, Scene oldScene, Scene newScene) -> {
                try {
                    if (oldScene != null && fStop != null) {
                        fStop.invoke(app);
                    }
                    if (newScene != null && fPlay != null) {
                        fPlay.invoke(app);
                    }
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                    Logger.getLogger(SolucionPage.class.getName()).log(Level.SEVERE, null, ex);
                }
            });

            return new SolucionRuntimeInfo(root, app, clz);
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
            Logger.getLogger(SolucionPage.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new SolucionRuntimeInfo(new Pane(), new Object(), Object.class);
    }
    private static final Image SOLUCION_BACKGROUND = getImage(SolucionInfo.class.getResource("images/sample-background.png").toExternalForm());

    private class LargePreviewRegion extends Region {

        private final Node sampleNode = buildSolucionNode().getSolucionNode();
        private final Label label = new Label();
        private final ImageView background = new ImageView(SOLUCION_BACKGROUND);

        public LargePreviewRegion() {
            getStyleClass().add("solucion-large-preview");
            //label.setText(name);
            label.getStyleClass().add("solucion-large-preview-label");
            label.setAlignment(Pos.BOTTOM_CENTER);
            label.setWrapText(true);
            getChildren().addAll(background, sampleNode, label);
        }

        @Override
        protected double computeMinWidth(double height) {
            return 460;
        }

        @Override
        protected double computeMinHeight(double width) {
            return 345;
        }

        @Override
        protected double computePrefWidth(double height) {
            return 460;
        }

        @Override
        protected double computePrefHeight(double width) {
            return 345;
        }

        @Override
        protected void layoutChildren() {
            double labelHeight = label.prefHeight(440);
            background.setLayoutX(5);
            background.setLayoutY(5);
            background.setFitWidth(450);
            background.setFitHeight(335);
            sampleNode.setLayoutX(10);
            sampleNode.setLayoutY(10);
            sampleNode.resize(440, 315 - labelHeight);
            label.setLayoutX(10);
            label.setLayoutY(345 - 15 - labelHeight);
            label.resize(440, labelHeight);
        }
    }

    public boolean needsPlayground() {
        return playgroundProperties.length > 0;
    }

    public static interface URL {

        String getURL();

        String getName();
    }

    private static Map<String, Image> imageCache;

    private static Image getImage(String url) {
        if (imageCache == null) {
            imageCache = new WeakHashMap<>();
        }
        Image image = imageCache.get(url);
        if (image == null) {
            image = new Image(url);
            imageCache.put(url, image);
        }
        return image;
    }

    public static class SolucionRuntimeInfo {

        private final Parent sampleNode;
        private final Object app;
        private final Class clz;

        public SolucionRuntimeInfo(Parent sampleNode, Object app, Class clz) {
            this.sampleNode = sampleNode;
            this.app = app;
            this.clz = clz;
        }

        public Object getApp() {
            return app;
        }

        public Class getClz() {
            return clz;
        }

        public Parent getSolucionNode() {
            return sampleNode;
        }
    }
}
