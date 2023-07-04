package sihic.contabilidad;

import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.EstadosMensaje;
import modelo.Puc;

import modelo.GenCategoriasProductos;
import sihic.SearchPopover;
import sihic.SihicApp;
import static sihic.SihicApp.pageBrowser;
import sihic.control.SearchBox;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FConfiguracionesCompra extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;

    private GridPane gp_generic;

    private SearchBox mercanciasnofab_natd = new SearchBox();
    private SearchBox impuestoventas_natd = new SearchBox();
    private SearchBox retefuente_natc = new SearchBox();
    private SearchBox proveedores_natc = new SearchBox();
    private SearchBox reteiva_natc = new SearchBox();
    private SearchBox reteica_natc = new SearchBox();
    private SearchBox caja_natc = new SearchBox();
    private SearchPopover sp_mercanciasnofab_natd;
    private SearchPopover sp_impuestoventas_natd;
    private SearchPopover sp_retefuente_natc;
    private SearchPopover sp_proveedores_natc;
    private SearchPopover sp_reteiva_natc;
    private SearchPopover sp_reteica_natc;
    private SearchPopover sp_caja_natc;
    private TextField porc_retefuente = new TextField();
    private TextField porc_reteica = new TextField();
    private Button save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    private boolean permitirproceso = false;
    Thread backgroundThread;
    private ChoiceBox compraentreregimenes;
    private Puc conPuc;

    public Parent createContent() throws IOException {
        sp_mercanciasnofab_natd = new SearchPopover(mercanciasnofab_natd, pageBrowser, SihicApp.conPuc, permitirproceso);
        sp_mercanciasnofab_natd.setMaxSize(300, 70);
        mercanciasnofab_natd.setMaxWidth(300);
        mercanciasnofab_natd.setOnAction(e -> {
            conPuc = null;
            conPuc = new Puc();
            conPuc = SihicApp.conPuc;
            SihicApp.s_configuraciones.setMercanciasnofab_natd(conPuc);
        });
        sp_impuestoventas_natd = new SearchPopover(impuestoventas_natd, pageBrowser, SihicApp.conPuc, permitirproceso);
        sp_impuestoventas_natd.setMaxSize(300, 70);
        impuestoventas_natd.setMaxWidth(300);
        impuestoventas_natd.setOnAction(e -> {
            conPuc = null;
            conPuc = new Puc();
            conPuc = SihicApp.conPuc;
            SihicApp.s_configuraciones.setImpuestoventascompras_natd(conPuc);
        });
        sp_proveedores_natc = new SearchPopover(proveedores_natc, pageBrowser, SihicApp.conPuc, permitirproceso);
        sp_proveedores_natc.setMaxSize(300, 70);
        proveedores_natc.setMaxWidth(300);
        proveedores_natc.setOnAction(e -> {
            conPuc = null;
            conPuc = new Puc();
            conPuc = SihicApp.conPuc;
            SihicApp.s_configuraciones.setProveedores_natc(conPuc);
        });
        sp_retefuente_natc = new SearchPopover(retefuente_natc, pageBrowser, SihicApp.conPuc, permitirproceso);
        sp_retefuente_natc.setMaxSize(300, 70);
        retefuente_natc.setMaxWidth(300);
        retefuente_natc.setOnAction(e -> {
            conPuc = null;
            conPuc = new Puc();
            conPuc = SihicApp.conPuc;
            SihicApp.s_configuraciones.setRtefuente_natc(conPuc);
        });
        sp_caja_natc = new SearchPopover(caja_natc, pageBrowser, SihicApp.conPuc, permitirproceso);
        sp_caja_natc.setMaxSize(300, 70);
        caja_natc.setMaxWidth(300);
        caja_natc.setOnAction(e -> {
            conPuc = null;
            conPuc = new Puc();
            conPuc = SihicApp.conPuc;
            SihicApp.s_configuraciones.setCajageneral_natc(conPuc);
        });
        sp_reteica_natc = new SearchPopover(reteica_natc, pageBrowser, SihicApp.conPuc, permitirproceso);
        sp_reteica_natc.setMaxSize(300, 70);
        reteica_natc.setMaxWidth(300);
        reteica_natc.setOnAction(e -> {
            conPuc = null;
            conPuc = new Puc();
            conPuc = SihicApp.conPuc;
            //   SihicApp.s_configuraciones.set(conPuc);
        });
        sp_reteiva_natc = new SearchPopover(reteiva_natc, pageBrowser, SihicApp.conPuc, permitirproceso);
        sp_reteiva_natc.setMaxSize(300, 70);
        reteiva_natc.setMaxWidth(300);
        porc_retefuente = new TextField();
        porc_reteica = new TextField();
//***********************************************  
        compraentreregimenes = new ChoiceBox();
        compraentreregimenes.getItems().addAll("Grande contribuyente compra a grande contribuyente", "Grande contribuyente compra a régimen común", "Grande contribuyente compra a régimen simplificado", "Régimen común compra a grande contribuyente", "Régimen común compra a régimen común", "Régimen común compra a régimen simplificado", "Régimen simplificado compra a grande contribuyente", "Régimen simplificado compra a régimen común", "Régimen simplificado compra a régimen simplificado");
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();

        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        save = new Button("", imageView);
        save.setTooltip(new Tooltip("Guardar Configuración"));
        hb_botones = new HBox(2);

        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        nuevo = new Button("", imageView);
        nuevo.setTooltip(new Tooltip("Nueva Categoría"));
        nuevo.setOnAction(e -> {

            nuevo();

        });
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo);

// gridpane
        gp_generic = new GridPane();
        gp_generic.add(new Label("Cuenta Inv. Medicamentos D(141501): "), 0, 0);
        gp_generic.add(mercanciasnofab_natd, 1, 0);
        gp_generic.add(new Label("Cuenta IVA Compras D(240802):"), 0, 1);
        //gp_generic.add(new Label(" Por tanto a partir del primero de enero de 2017,\n los responsables del régimen común no deberán calcular ese IVA teórico cuando realicen compras de bienes o servicios a los responsables del régimen simplificado del IVA.\n" +"\n" +"  : "), 2, 0);        gp_generic.add(new Label("Iva Nat. D: "), 0, 1);
        gp_generic.add(impuestoventas_natd, 1, 1);
        gp_generic.add(new Label("Cuenta Proveedores  C(22050500005): "), 0, 2);
        gp_generic.add(proveedores_natc, 1, 2);
        gp_generic.add(new Label("Cuenta Rte Fuente C(23654000001): "), 0, 3);
        gp_generic.add(retefuente_natc, 1, 3);
        gp_generic.add(new Label("Cuenta Retención IVA C(236740): "), 0, 4);
        gp_generic.add(reteiva_natc, 1, 4);
        gp_generic.add(new Label("Cuenta Rte Ica C(236840): "), 0, 5);
        gp_generic.add(reteica_natc, 1, 5);
        gp_generic.add(new Label("% ReteFuente: "), 0, 6);
        gp_generic.add(porc_retefuente, 1, 6);
        gp_generic.add(new Label("% ReteICA: "), 0, 7);
        gp_generic.add(porc_reteica, 1, 7);

        gp_generic.add(hb_botones, 0, 8, 2, 1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_generic.getStylesheets().add(SihicApp.hojaestilos);
        gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.setAlignment(Pos.TOP_CENTER);
        // gp_generic.getStylesheets().add(SihicApp.hojaestilos);
        // gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinWidth(gp_generic.getLayoutBounds().getWidth());
        Gp_Message.setMaxHeight(40);
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generic, Gp_Message);
        crearoeditar();
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    private void nuevo() {
        SihicApp.conPuc = null;
        SihicApp.conPuc = new Puc();
        mercanciasnofab_natd.setText("");
        impuestoventas_natd.setText("");
        proveedores_natc.setText("");
        caja_natc.setText("");
        retefuente_natc.setText("");
        reteica_natc.setText("");
        reteiva_natc.setText("");
        porc_retefuente.setText("");
        porc_reteica.setText("");
        empty_field();
    }

    public void save() throws InterruptedException {
        validar_formulario();
        if (permitirproceso) {
            set_datos();
            try {

                if (SihicApp.s_configuraciones.getId() == null) {
                    SihicApp.s_ConfiguracionesControllerClient.create();
                    L_Message.setText("Registro Almacenado");
                } else {
                    SihicApp.s_ConfiguracionesControllerClient.update();
                    L_Message.setText("Registro modificado");
                }

                Task_Message = () -> {
                    try {
                        SetMessage();
                    } catch (InterruptedException ex) {

                    }
                };
                backgroundThread = new Thread(Task_Message);
                // Terminate the running thread if the application exits
                backgroundThread.setDaemon(true);

                // Start the thread
                backgroundThread.start();
            } catch (Exception e) {
                L_Message.setText("Error Almacenando");
                Task_Message = () -> {
                    try {
                        SetMessage();
                    } catch (InterruptedException ex) {

                    }
                };
                backgroundThread = new Thread(Task_Message);
                // Terminate the running thread if the application exits
                backgroundThread.setDaemon(true);

                // Start the thread
                backgroundThread.start();
            }
        }
    }

    private void set_datos() {
        if (SihicApp.s_configuraciones.getMercanciasnofab_natd() == null) {
            SihicApp.s_configuraciones.setMercanciasnofab_natd(new Puc());
        }
        SihicApp.s_configuraciones.getMercanciasnofab_natd().setCodigo(mercanciasnofab_natd.getText());

        if (SihicApp.s_configuraciones.getImpuestoventascompras_natd() == null) {
            SihicApp.s_configuraciones.setImpuestoventascompras_natd(new Puc());
        }
        SihicApp.s_configuraciones.getImpuestoventascompras_natd().setCodigo(impuestoventas_natd.getText());
        if (SihicApp.s_configuraciones.getProveedores_natc() == null) {
            SihicApp.s_configuraciones.setProveedores_natc(new Puc());
        }
        SihicApp.s_configuraciones.getProveedores_natc().setCodigo(proveedores_natc.getText());

        if (SihicApp.s_configuraciones.getRtefuente_natc() == null) {
            SihicApp.s_configuraciones.setRtefuente_natc(new Puc());
        }
        SihicApp.s_configuraciones.getRtefuente_natc().setCodigo(retefuente_natc.getText());
        if (SihicApp.s_configuraciones.getIvaretenidocompras_natc() == null) {
            SihicApp.s_configuraciones.setIvaretenidocompras_natc(new Puc());
        }
        SihicApp.s_configuraciones.getIvaretenidocompras_natc().setCodigo(reteiva_natc.getText());
        if (SihicApp.s_configuraciones.getIcaretenidocompras_natc() == null) {
            SihicApp.s_configuraciones.setIcaretenidocompras_natc(new Puc());
        }
        SihicApp.s_configuraciones.getIcaretenidocompras_natc().setCodigo(reteica_natc.getText());
        if (SihicApp.s_configuraciones.getCajageneral_natc() == null) {
            SihicApp.s_configuraciones.setCajageneral_natc(new Puc());
        }
        SihicApp.s_configuraciones.getCajageneral_natc().setCodigo(caja_natc.getText());

        //cargamos la imagen
    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                if (general.getProperties().get("requerido") != null) {
                    if ((general.getLength() <= 0) && ((boolean) general.getProperties().get("requerido") == true)) {
                        permitirproceso = false;
                        general.setStyle("-fx-background-color:#ff1600");

                    }
                }

            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;

                    if (general.getSelectionModel().getSelectedIndex() < 0 && (boolean) general.getProperties().get("requerido") == true) {
                        permitirproceso = false;
                        general.getStylesheets().add(0, "/sihic/personal.css");
                        general.getStylesheets().set(0, "/sihic/personal.css");
                    }

                }
            }
        }
        FadeTransition ft = new FadeTransition(Duration.millis(2000), save);
        //ft.setFromValue(0.0);
        // ft.setToValue(1);

        ft.play();

        //success.setOpacity(0);
        ft.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (Object n : gp_generic.getChildren().toArray()) {
                    if (n.getClass() == TextField.class) {
                        TextField general = (TextField) n;
                        if (general.getProperties().get("requerido") != null) {
                            if (((boolean) general.getProperties().get("requerido") == true)) {

                                general.setStyle(null);
                                general.getStyleClass().add("text-field");
                            }
                        }

                    } else {
                        if (n.getClass() == ChoiceBox.class) {
                            ChoiceBox general = (ChoiceBox) n;

                            if (general.getSelectionModel().getSelectedIndex() < 0 && (boolean) general.getProperties().get("requerido") == true) {
                                general.getStylesheets().set(0, SihicApp.hojaestilos);

                            }

                        }
                    }
                }
            }
        });

    }

    private void SetMessage() throws InterruptedException {

        Gp_Message.setVisible(true);
        //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
        Thread.sleep(3000);
        Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
        Task_Message = null;

    }
// A change listener to track the change in selected index

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {

    }

    public void getdatos() {
        mercanciasnofab_natd.setText(SihicApp.s_configuraciones.getMercanciasnofab_natd().getCodigo());
        impuestoventas_natd.setText(SihicApp.s_configuraciones.getImpuestoventascompras_natd().getCodigo());
        retefuente_natc.setText(SihicApp.s_configuraciones.getRtefuente_natc().getCodigo());
        reteiva_natc.setText(SihicApp.s_configuraciones.getIvaretenidocompras_natc().getCodigo());
        reteica_natc.setText(SihicApp.s_configuraciones.getIcaretenidocompras_natc().getCodigo());
        proveedores_natc.setText(SihicApp.s_configuraciones.getProveedores_natc().getCodigo());
        caja_natc.setText(SihicApp.s_configuraciones.getCajageneral_natc().getCodigo());
    }

    public void crearoeditar() {
        SihicApp.s_configuraciones = SihicApp.s_ConfiguracionesControllerClient.getRecord();

        if (SihicApp.s_configuraciones.getId() != null) {
            getdatos();

        } else {

            nuevo();
        }

    }

    private void empty_field() {
        for (Object n : gp_generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                general.setText("");

            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;

                    general.getSelectionModel().select(-1);

                } else {
                    if (n.getClass() == TextArea.class) {
                        TextArea general = (TextArea) n;

                        general.setText("");

                    } else {
                        if (n.getClass() == RadioButton.class) {
                            RadioButton general = (RadioButton) n;

                            general.setSelected(false);

                        } else {
                            if (n.getClass() == CheckBox.class) {
                                CheckBox general = (CheckBox) n;

                                general.setSelected(false);

                            }
                        }
                    }
                }
            }
        }

    }

    /**
     * Java main for when running without JavaFX launcher
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
