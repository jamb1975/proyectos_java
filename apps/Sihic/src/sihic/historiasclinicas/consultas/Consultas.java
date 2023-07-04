package sihic.historiasclinicas.consultas;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.WeakHashMap;
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
public class Consultas extends Application {

    private String appPathConsultas;
    private Class clzConsultas;
    private Object appClassConsultas;
    private final Stage stageConsultas = new Stage(StageStyle.DECORATED);
    private Scene scene = null;
    Parent P_Consultas;
    private Label la_title;

    public Parent createContent() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        appPathConsultas = "sihic.historiasclinicas.consultas.VerConsultas";
        clzConsultas = Class.forName(appPathConsultas);
        appClassConsultas = clzConsultas.newInstance();
        la_title = new Label("Ver Consultas");
        la_title.setStyle("color: white;font-size:12px;");
        Region label = new Region();

        String url = getClass().getResource("/sihic/images/verconsultas.png").toExternalForm();
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
                showconsultas();
            } catch (Exception e2) {
                e2.printStackTrace();
            }

        });
        GridPane gp = new GridPane();
        gp.getStyleClass().add("background");

        GridPane.setValignment(label, VPos.TOP);
        GridPane.setHalignment(la_title, HPos.CENTER);
        gp.setAlignment(Pos.CENTER);
        gp.add(label, 0, 0);
        gp.add(la_title, 0, 1);
        gp.setVgap(5);
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

    private void showconsultas() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        P_Consultas = null;
        P_Consultas = (Parent) clzConsultas.getMethod("createContent").invoke(appClassConsultas);
        scene = null;
        scene = new Scene(P_Consultas, Color.TRANSPARENT);

        if (stageConsultas.isShowing()) {
            stageConsultas.close();
        }

//set scene to stage
        stageConsultas.sizeToScene();

        //center stage on screen
        stageConsultas.centerOnScreen();
        stageConsultas.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageConsultas.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
