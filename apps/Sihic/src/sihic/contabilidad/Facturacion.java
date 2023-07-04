package sihic.contabilidad;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class Facturacion extends Application {

    private String appPathFacturar;
    private Class clzFacturar;
    private Object appClassFacturar;
    private Stage stageFacturar = new Stage(StageStyle.DECORATED);
    private Scene scene = null;
    Parent P_NuevaCita;
    private Label la_title;

    public Parent createContent() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        stageFacturar.setTitle("Factura");
        appPathFacturar = "sihic.contabilidad.Facturar";
        clzFacturar = Class.forName(appPathFacturar);
        appClassFacturar = clzFacturar.newInstance();
        la_title = new Label("Facturar");
        la_title.setStyle("color: white;font-size:12px;");
        Region label = new Region();
        Region label2 = new Region();
        String url = getClass().getResource("/sihic/images/facturacion.png").toExternalForm();
        label.setBackground(
                new Background(
                        new BackgroundFill[]{
                            new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)
                        },
                        new BackgroundImage[]{
                            new BackgroundImage(
                                    getImage(url),
                                    BackgroundRepeat.NO_REPEAT,
                                    BackgroundRepeat.NO_REPEAT,
                                    new BackgroundPosition(Side.LEFT, 5, false, Side.TOP, 5, false),
                                    new BackgroundSize(106, 96, false, false, false, false)
                            )
                        }
                ));

        DropShadow dsEffect = new DropShadow();
        dsEffect.setOffsetX(0);
        dsEffect.setOffsetY(0);
        dsEffect.setColor(Color.GOLD);
        label.getStyleClass().add("sample-medium-preview");
        label.setMinSize(116, 106);
        label.setPrefSize(116, 106);
        label.setMaxSize(116, 106);
        label.setOnMouseEntered(e -> {
            label.setEffect(dsEffect);
        });
        label.setOnMouseExited(e -> {
            label.setEffect(null);
        });
        label.setOnMouseClicked(e -> {
            try {
                shownuevacita();
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        });
        GridPane gp = new GridPane();
        gp.getStyleClass().add("background");
        url = getClass().getResource("/sihic/images/administracion.png").toExternalForm();
        label2.setBackground(
                new Background(
                        new BackgroundFill[]{
                            new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)
                        },
                        new BackgroundImage[]{
                            new BackgroundImage(
                                    getImage(url),
                                    BackgroundRepeat.NO_REPEAT,
                                    BackgroundRepeat.NO_REPEAT,
                                    new BackgroundPosition(Side.LEFT, 5, false, Side.TOP, 5, false),
                                    new BackgroundSize(106, 96, false, false, false, false)
                            )
                        }
                ));

        dsEffect.setOffsetX(0);
        dsEffect.setOffsetY(0);
        dsEffect.setColor(Color.GOLD);
        label2.getStyleClass().add("sample-medium-preview");
        label2.setMinSize(116, 106);
        label2.setPrefSize(116, 106);
        label2.setMaxSize(116, 106);
        label2.setOnMouseEntered(e -> {
            label2.setEffect(dsEffect);
        });
        label2.setOnMouseExited(e -> {
            label2.setEffect(null);
        });
        label2.setOnMouseClicked(e -> {
            try {
                shownuevacita();
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        });

        GridPane.setValignment(label, VPos.TOP);
        GridPane.setValignment(label2, VPos.TOP);
        GridPane.setHalignment(la_title, HPos.CENTER);
        gp.setAlignment(Pos.CENTER);
        gp.add(label, 0, 0);
        gp.add(label2, 1, 0);
        gp.add(la_title, 0, 1);
        gp.add(new Label("Administración"), 1, 1);
        gp.setVgap(5);
        gp.setHgap(5);
        return gp;
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
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

    private void shownuevacita() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        P_NuevaCita = null;
        P_NuevaCita = (Parent) clzFacturar.getMethod("createContent").invoke(appClassFacturar);
        scene = null;
        scene = new Scene(P_NuevaCita, Color.TRANSPARENT);

        if (stageFacturar.isShowing()) {
            stageFacturar.close();
        }

//set scene to stage
        stageFacturar.sizeToScene();

        //center stage on screen
        stageFacturar.centerOnScreen();
        stageFacturar.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageFacturar.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
