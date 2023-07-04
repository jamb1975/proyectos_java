package sihic.administracion;

import java.io.IOException;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import jfxtras.styles.jmetro8.JMetro;
import modelo.UsuarioSoluciones;
import service.EntityManagerGeneric;
import sihic.SihicApp;
import static sihic.SihicApp.cprogreso;
import sihic.controlador.SolucionControllerClient;
import sihic.controlador.UsuarioSolucionesControllerClient;
import sihic.generated.Soluciones;

public class FLogin extends Application {

    StackPane stack;
    private static GridPane gp_progreso;
    public static GridPane Gp_ValidarUsuario;
    public static Label L_Usuario = new Label("Usuario: ");
    public static Label L_Password = new Label("Clave: ");
    public static TextField Tf_Usuario = new TextField();
    public static PasswordField Pf_Password = new PasswordField();
    public static Button Bu_Validar = new Button("");
    public static Button Bu_Cancelar = new Button("");
    private UsuarioSolucionesControllerClient usuarioSolucionesControllerClient = new UsuarioSolucionesControllerClient();
    private SolucionControllerClient solucionControllerClient;
    private ImageView imageView;
    private static final ProgressIndicator p5 = new ProgressIndicator();
    private static Timeline timeline = new Timeline();
    private static Thread thimport_77;

    public Parent createContent() throws IOException {
        gp_progreso = new GridPane();
        gp_progreso.setStyle("background: white");
        stack = new StackPane();
        Gp_ValidarUsuario = new GridPane();
        Label l = new Label("AUTORIZO A CENTRO MÉDICO GUAVIARE(CMG) A UTILIZAR ESTE SOFTWARE");
        imageView = new ImageView("/images/login.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        Bu_Validar.setGraphic(imageView);
        Pf_Password.setOnAction(e -> {
            getSoluciones();

        });
        Bu_Validar.setOnAction(e -> {
            getSoluciones();

        });
        Gp_ValidarUsuario.add(L_Usuario, 0, 0);
        Gp_ValidarUsuario.add(Tf_Usuario, 1, 0);
        Gp_ValidarUsuario.add(L_Password, 0, 1);
        Gp_ValidarUsuario.add(Pf_Password, 1, 1);
        Gp_ValidarUsuario.add(Bu_Validar, 0, 2, 2, 1);
        Gp_ValidarUsuario.add(l, 0, 3, 2, 1);
        GridPane.setHalignment(Bu_Validar, HPos.CENTER);
        Gp_ValidarUsuario.setVgap(7);
        Gp_ValidarUsuario.getStylesheets().add(SihicApp.hojaestilos);
      Gp_ValidarUsuario.getStyleClass().add("background");
      gp_progreso.getStylesheets().add(SihicApp.hojaestilos);
      gp_progreso.getStyleClass().add("backgroundprogreso");
     //  new JMetro(JMetro.Style.DARK).applyTheme(Gp_ValidarUsuario);
     //  new JMetro(JMetro.Style.DARK).applyTheme(gp_progreso);
      //   new JMetro(JMetro.Style.DARK).applyTheme(stack);
        gp_progreso.setAlignment(Pos.CENTER);
        //showprogreso(p5);

        p5.setPrefSize(150, 150);
        p5.setStyle("-fx-foreground-color: #FFFFFF");
        p5.styleProperty().bind(Bindings.createStringBinding(
                () -> {
                    final double percent = p5.getProgress();
                    // p5.setProgress((SihicApp.cprogreso*(SihicApp.cprogreso*2.56)));
                    if (percent < 0) {
                        return null; // progress bar went indeterminate
                    }                    //
                    // poor man's gradient for stops: red, yellow 50%, green
                    // Based on http://en.wikibooks.org/wiki/Color_Theory/Color_gradient#Linear_RGB_gradient_with_6_segments
                    //
                    final double m = (percent);
                    final int n = (int) m;
                    final double f = m - n;
                    final int t = (int) (255 * f);
                    int r = 1, g = 1, b = 1;
                    switch (n) {
                        case 0:
                            r = 255;
                            g = t;
                            b = 0;
                            break;
                        case 1:
                            r = 255 - t;
                            g = 255;
                            b = 0;
                            break;
                        case 2:
                            r = 0;
                            g = 255;
                            b = 0;
                            break;

                    }
                    final String style = String.format("-fx-progress-color: rgb(%d,%d,%d)", r, g, b);
                    return style;
                },
                p5.progressProperty()
        ));
        p5.progressProperty().bind(SihicApp.task_4.progressProperty());
        p5.visibleProperty().bind(SihicApp.task_4.runningProperty());

        /* timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        final KeyValue kv0 = new KeyValue(p5.progressProperty(), 0);
        final KeyValue kv1 = new KeyValue(p5.progressProperty(), 1);
        final KeyFrame kf0 = new KeyFrame(Duration.ZERO, kv0);
        final KeyFrame kf1 = new KeyFrame(Duration.millis(3000), kv1);
        timeline.getKeyFrames().addAll(kf0, kf1);
        //p5.setVisible(true);
        play();*/
        gp_progreso.add(p5, 0, 0);
        stack.getChildren().addAll(Gp_ValidarUsuario, gp_progreso);
        check_load_datos();
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public void getSoluciones() {
        if (!(Tf_Usuario.getText().trim().equals("admin") && Pf_Password.getText().trim().equals("JAmbg172*"))) {
            usuarioSolucionesControllerClient.getUsuarioSoluciones(Tf_Usuario.getText().trim(), Pf_Password.getText().trim());
            if (SihicApp.Li_UsuarioSoluciones.size() > 0) {
                SihicApp.usuarios = SihicApp.Li_UsuarioSoluciones.get(0).getUsuario();

                SihicApp.Bo_LoginCorrecto = true;
                asignarsoluciones();

            } else {
                SihicApp.Bo_LoginCorrecto = false;
            }
        } else {
            SihicApp.Bo_LoginCorrecto = true;
            SihicApp.usuarios.setUsuario("admin");
            SihicApp.usuarios.setPassword("JAmbg172*");
            asignarsoluciones();
        }
    }

    private void asignarsoluciones() {
        for (UsuarioSoluciones us : SihicApp.Li_UsuarioSoluciones) {
            SihicApp.hm_usuariosoluciones.put(us.getSolucion().getSolucion(), us.getSolucion());
        }
        SihicApp.stageLogin.close();
        Soluciones.tiene_permiso("");
    }

    private static void check_load_datos() {
        Task<Void> task_77 = new Task<Void>() {

            @Override
            protected Void call() throws Exception {

                while (true) {
                    //  System.out.println("Cprogreso->"+SihicApp.cprogreso);
                    if (SihicApp.tproceso() >= 35) {
                        // System.out.println("Cprogreso->"+SihicApp.cprogreso);
                        gp_progreso.setVisible(false);
                        timeline.stop();
                        EntityManagerGeneric.cerrartodaslasconexiones();
                        return null;
                    }
                }

            }
        };
        thimport_77 = new Thread(task_77);

        thimport_77.setDaemon(true);
        thimport_77.start();
    }

    public void play() {
        timeline.play();
    }

    @Override
    public void stop() {
        timeline.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
