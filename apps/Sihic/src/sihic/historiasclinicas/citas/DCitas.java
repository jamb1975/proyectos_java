package sihic.historiasclinicas.citas;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
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
import jenum.EnumEstadosCita;
import modelo.AgnCitas;
import modelo.AgnEstadosCita;
import sihic.SihicApp;
import sihic.controlador.AgnCitasControllerClient;
import sihic.util.UtilDate;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class DCitas extends Application {

    private String appPathNuevaCita;
    private Class clzNuevaCita;
    private Object appClassNuevaCita;
    private Stage stageNuevaCita = new Stage(StageStyle.DECORATED);
    private static AgnCitasControllerClient agnCitasControllerClient;
    private Scene scene = null;
    Parent P_NuevaCita;
    private Label la_title;

    public Parent createContent() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        agnCitasControllerClient = new AgnCitasControllerClient();
        appPathNuevaCita = "sihic.historiasclinicas.citas.NuevaCita";
        clzNuevaCita = Class.forName(appPathNuevaCita);
        appClassNuevaCita = clzNuevaCita.newInstance();
        P_NuevaCita = null;
        P_NuevaCita = (Parent) clzNuevaCita.getMethod("createContent").invoke(appClassNuevaCita);
        scene = null;
        scene = new Scene(P_NuevaCita, Color.TRANSPARENT);
        la_title = new Label("Nueva Cita");
        la_title.setStyle("color: white;font-size:12px;");
        Region label = new Region();

        String url = getClass().getResource("/sihic/images/nuevacita.png").toExternalForm();
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

    private void shownuevacita() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {

        if (stageNuevaCita.isShowing()) {
            stageNuevaCita.close();
        }

//set scene to stage
        stageNuevaCita.sizeToScene();

        //center stage on screen
        stageNuevaCita.centerOnScreen();
        stageNuevaCita.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageNuevaCita.show();
    }

    //FUNCION STATIC QUE CAMBIA ESTADO Y FECHASHORA SEGUN ESTADO
    public static void cambiarestadocitayfechahora(int estado) throws ParseException {
        AgnCitas ac = new AgnCitas();
        ac = SihicApp.agnCitasTemp;
        AgnEstadosCita agnEstadosCita = new AgnEstadosCita();
        agnEstadosCita.setId(Long.valueOf(estado));
        ac.setAgnEstadosCita(agnEstadosCita);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date datecurrent = UtilDate.colocarfechahoraespecifica(ac.getFechaHora(), String.valueOf(LocalTime.now().getHour()), String.valueOf(LocalTime.now().getMinute()));
        Calendar c = Calendar.getInstance();
        c.setTime(datecurrent);
        switch (estado) {
            case 1:
                ac.setFechaCreacion(datecurrent);

                break;
            case 2:
                ac.setFechaIngreso(datecurrent);
                break;
            case 3:
                ac.setHoraInicio(datecurrent);
                break;

            case 4:
                ac.setHoraFin(datecurrent);
                break;
            case 5:
                ac.setAgnEstadosCita(null);
                ac.setAgnConsultorios(null);
                break;

        }
        if (agnCitasControllerClient == null) {
            agnCitasControllerClient = new AgnCitasControllerClient();
        }
      
    }

    public static void main(String[] args) {
        launch(args);
    }
}
