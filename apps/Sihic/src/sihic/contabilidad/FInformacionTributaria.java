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
import javafx.scene.control.DatePicker;
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
import jenum.EnumEntidad;
import message.EstadosMensaje;
import modelo.InformacionTributaria;
import modelo.Proveedores;
import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.SihicApp;
import sihic.control.SearchBox;
import sihic.util.UtilDate;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FInformacionTributaria extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private GridPane gp_generic = new GridPane();
    private final CheckBox regimenespecial = new CheckBox();
    private final CheckBox contribuyente = new CheckBox();
    private final CheckBox autoretenedor = new CheckBox();
    private final CheckBox declarante = new CheckBox();
    private final CheckBox exento = new CheckBox();
    private final ChoiceBox entidad = new ChoiceBox();
    private SearchBox sb_actividadeconomica_renta = new SearchBox();
    private SearchPopover sp_actividadeconomica_renta;
    private SearchBox sb_actividadeconomica_ciiu = new SearchBox();
    private SearchPopover sp_actividadeconomica_ciiu;
    private final ChoiceBox tipoactoadministrativo_regimenespecial = new ChoiceBox();
    private final ChoiceBox tipoactoadministrativo_contribuyente = new ChoiceBox();
    private final ChoiceBox tipoactoadministrativo_autoretenedor = new ChoiceBox();
    private final ChoiceBox tipoactoadministrativo_declarante = new ChoiceBox();
    private final ChoiceBox tipoactoadministrativo_exento = new ChoiceBox();
    private final TextField tipoactoadministrativo_regimenespecial_numero = new TextField();
    private final TextField tipoactoadministrativo_contribuyente_numero = new TextField();
    private final TextField tipoactoadministrativo_autoretenedor_numero = new TextField();
    private final TextField tipoactoadministrativo_declarante_numero = new TextField();
    private final TextField tipoactoadministrativo_exento_numero = new TextField();
    private final DatePicker tipoactoadministrativo_regimenespecial_fecha = new DatePicker();
    private final DatePicker tipoactoadministrativo_contribuyente_fecha = new DatePicker();
    private final DatePicker tipoactoadministrativo_autoretenedor_fecha = new DatePicker();
    private final DatePicker tipoactoadministrativo_declarante_fecha = new DatePicker();
    private final DatePicker tipoactoadministrativo_exento_fecha = new DatePicker();
    private final CheckBox responsableiva = new CheckBox();
    private final CheckBox grancontribuyente = new CheckBox();
    private final ChoiceBox tipoactoadministrativo_grancontribuyente = new ChoiceBox();
    private final TextField tipoactoadministrativo_grancontribuyente_numero = new TextField();
    private final DatePicker tipoactoadministrativo_grancontribuyente_fecha = new DatePicker();
    private final CheckBox serviciocomprabogota = new CheckBox();
    private final CheckBox domiciliobogota = new CheckBox();
    private final ChoiceBox tiporegimen = new ChoiceBox();
    private final CheckBox factura = new CheckBox();
    private final TextField resolucionfacturacion_numero = new TextField();
    private final DatePicker resolucionfacturacion_fecha = new DatePicker();
    private final TextField tarrifapormil = new TextField();
    private CheckBox impuestotimbre = new CheckBox();
    private final ChoiceBox tipoactoadministrativoimpuestotimbre_exento = new ChoiceBox();
    private final TextField tipoactoadministrativoimpuestotimbre_exento_numero = new TextField();
    private final DatePicker tipoactoadministrativoimpuestotimbre_exento_fecha = new DatePicker();
    private Button save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private HBox hb_checkbox;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    private boolean permitirproceso = false;
    Thread backgroundThread;

    public Parent createContent() throws IOException {
        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();

        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        save = new Button("", imageView);
        save.setTooltip(new Tooltip("Guardar "));
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
        nuevo.setTooltip(new Tooltip("Nuevo"));
        nuevo.setOnAction(e -> {
            nuevo();
        });
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo);
        entidad.getItems().add(EnumEntidad.NATURAL.ordinal(), "Natural");
        entidad.getItems().add(EnumEntidad.JURIDICA.ordinal(), "Jurídica");

        entidad.getItems().add(EnumEntidad.PRIVADA.ordinal(), "Privada");
        entidad.getItems().add(EnumEntidad.PUBLICA.ordinal(), "Pública");
        entidad.getItems().add(EnumEntidad.MIXTA.ordinal(), "Mixta");
        sp_actividadeconomica_renta = new SearchPopover(sb_actividadeconomica_renta, new PageBrowser(), SihicApp.s_ActividadesEconomicas, permitirproceso);
        sb_actividadeconomica_renta.setOnAction(e -> {
            SihicApp.s_informacionTributaria.setActividadesEconomicas_renta(SihicApp.s_ActividadesEconomicas);
        });
        sp_actividadeconomica_ciiu = new SearchPopover(sb_actividadeconomica_ciiu, new PageBrowser(), SihicApp.s_ActividadesEconomicas, permitirproceso);
        sb_actividadeconomica_ciiu.setOnAction(e -> {
            SihicApp.s_informacionTributaria.setActividadesEconomicas(SihicApp.s_ActividadesEconomicas);
        });
        tipoactoadministrativo_autoretenedor.getItems().add(jenum.EnumTipoActoAdministrativo.RESOLUCION.ordinal(), "Resolución");
        tipoactoadministrativo_autoretenedor.getItems().add(jenum.EnumTipoActoAdministrativo.DECRETO.ordinal(), "Decreto");
        tipoactoadministrativo_autoretenedor.getItems().add(jenum.EnumTipoActoAdministrativo.LEY.ordinal(), "Ley");
        tipoactoadministrativo_contribuyente.getItems().add(jenum.EnumTipoActoAdministrativo.RESOLUCION.ordinal(), "Resolución");
        tipoactoadministrativo_contribuyente.getItems().add(jenum.EnumTipoActoAdministrativo.DECRETO.ordinal(), "Decreto");
        tipoactoadministrativo_contribuyente.getItems().add(jenum.EnumTipoActoAdministrativo.LEY.ordinal(), "Ley");
        tipoactoadministrativo_declarante.getItems().add(jenum.EnumTipoActoAdministrativo.RESOLUCION.ordinal(), "Resolución");
        tipoactoadministrativo_declarante.getItems().add(jenum.EnumTipoActoAdministrativo.DECRETO.ordinal(), "Decreto");
        tipoactoadministrativo_declarante.getItems().add(jenum.EnumTipoActoAdministrativo.LEY.ordinal(), "Ley");
        tipoactoadministrativo_exento.getItems().add(jenum.EnumTipoActoAdministrativo.RESOLUCION.ordinal(), "Resolución");
        tipoactoadministrativo_exento.getItems().add(jenum.EnumTipoActoAdministrativo.DECRETO.ordinal(), "Decreto");
        tipoactoadministrativo_exento.getItems().add(jenum.EnumTipoActoAdministrativo.LEY.ordinal(), "Ley");
        tipoactoadministrativo_regimenespecial.getItems().add(jenum.EnumTipoActoAdministrativo.RESOLUCION.ordinal(), "Resolución");
        tipoactoadministrativo_regimenespecial.getItems().add(jenum.EnumTipoActoAdministrativo.DECRETO.ordinal(), "Decreto");
        tipoactoadministrativo_regimenespecial.getItems().add(jenum.EnumTipoActoAdministrativo.LEY.ordinal(), "Ley");
        tipoactoadministrativo_grancontribuyente.getItems().add(jenum.EnumTipoActoAdministrativo.RESOLUCION.ordinal(), "Resolución");
        tipoactoadministrativo_grancontribuyente.getItems().add(jenum.EnumTipoActoAdministrativo.DECRETO.ordinal(), "Decreto");
        tipoactoadministrativo_grancontribuyente.getItems().add(jenum.EnumTipoActoAdministrativo.LEY.ordinal(), "Ley");

        tiporegimen.getItems().add(jenum.EnumTipoRegimen.COMUN.ordinal(), "Común");
        tiporegimen.getItems().add(jenum.EnumTipoRegimen.SIMPLIFICADO.ordinal(), "Simplificado");
// gridpane
        gp_generic = new GridPane();
        gp_generic.add(new Label("Entidad: "), 0, 0);
        gp_generic.add(entidad, 1, 0);
        gp_generic.add(new Label("Actividad Ecónomica Renta: "), 2, 0);
        gp_generic.add(sb_actividadeconomica_renta, 3, 0);
        gp_generic.add(new Label("RETENCION EN LA FUENTE"), 2, 1);
        gp_generic.add(new Label("Tipo Documento"), 2, 2);
        gp_generic.add(new Label("Número"), 3, 2);
        gp_generic.add(new Label("Fecha"), 4, 2);
        gp_generic.add(new Label("Régimen Especial:"), 0, 3);
        gp_generic.add(regimenespecial, 1, 3);
        gp_generic.add(tipoactoadministrativo_regimenespecial, 2, 3);
        gp_generic.add(tipoactoadministrativo_regimenespecial_numero, 3, 3);
        gp_generic.add(tipoactoadministrativo_regimenespecial_fecha, 4, 3);
        gp_generic.add(new Label("Contribuyente:"), 0, 4);
        gp_generic.add(contribuyente, 1, 4);
        gp_generic.add(tipoactoadministrativo_contribuyente, 2, 4);
        gp_generic.add(tipoactoadministrativo_contribuyente_numero, 3, 4);
        gp_generic.add(tipoactoadministrativo_contribuyente_fecha, 4, 4);
        gp_generic.add(new Label("Autoretenedor:"), 0, 5);
        gp_generic.add(autoretenedor, 1, 5);
        gp_generic.add(tipoactoadministrativo_autoretenedor, 2, 5);
        gp_generic.add(tipoactoadministrativo_autoretenedor_numero, 3, 5);
        gp_generic.add(tipoactoadministrativo_autoretenedor_fecha, 4, 5);
        gp_generic.add(new Label("Exento:"), 0, 6);
        gp_generic.add(exento, 1, 6);
        gp_generic.add(tipoactoadministrativo_exento, 2, 6);
        gp_generic.add(tipoactoadministrativo_exento_numero, 3, 6);
        gp_generic.add(tipoactoadministrativo_exento_fecha, 4, 6);
        gp_generic.add(new Label("Declarante:"), 0, 7);
        gp_generic.add(declarante, 1, 7);
        gp_generic.add(new Label("IMPUESTO DE VALOR AGREGADO -IVA"), 0, 8, 5, 1);
        gp_generic.add(new Label("Responsable IVA"), 0, 9);
        gp_generic.add(new Label("Régimen"), 1, 9);
        gp_generic.add(new Label("Factura"), 2, 9);
        gp_generic.add(new Label("Resolución Facturación"), 3, 9);
        gp_generic.add(new Label("Fecha"), 4, 9);
        gp_generic.add(responsableiva, 0, 10);
        gp_generic.add(tiporegimen, 1, 10);
        gp_generic.add(factura, 2, 10);
        gp_generic.add(resolucionfacturacion_numero, 3, 10);
        gp_generic.add(resolucionfacturacion_fecha, 4, 10);
        gp_generic.add(new Label("Tipo Documento"), 2, 11);
        gp_generic.add(new Label("Número"), 3, 11);
        gp_generic.add(new Label("Fecha"), 4, 11);
        gp_generic.add(new Label("Gran Contribuyente:"), 0, 12);
        gp_generic.add(grancontribuyente, 1, 12);
        gp_generic.add(tipoactoadministrativo_grancontribuyente, 2, 12);
        gp_generic.add(tipoactoadministrativo_grancontribuyente_numero, 3, 12);
        gp_generic.add(tipoactoadministrativo_grancontribuyente_fecha, 4, 12);
        gp_generic.add(new Label("IMPUESTO DE INDUSTRIA COMERCIO AVISOS Y TABLEROS  -ICA"), 0, 13, 5, 1);
        gp_generic.add(new Label("Domicilio Bogotá"), 2, 14);
        gp_generic.add(new Label("Actividad Ecónomica/Cod Ciuu"), 3, 14);
        gp_generic.add(new Label("Tarifa por Mil"), 4, 14);
        gp_generic.add(new Label("Servicio o Compra Bogotá:"), 0, 15);
        gp_generic.add(serviciocomprabogota, 1, 15);
        gp_generic.add(domiciliobogota, 2, 15);
        gp_generic.add(sb_actividadeconomica_ciiu, 3, 15);
        gp_generic.add(tarrifapormil, 4, 15);
        gp_generic.add(new Label("IMPUESTO DE TIMBRE"), 0, 16);
        gp_generic.add(new Label("Exento:"), 0, 17);
        gp_generic.add(impuestotimbre, 1, 17);
        gp_generic.add(tipoactoadministrativoimpuestotimbre_exento, 2, 17);
        gp_generic.add(tipoactoadministrativoimpuestotimbre_exento_numero, 3, 17);
        gp_generic.add(tipoactoadministrativoimpuestotimbre_exento_fecha, 4, 17);
        gp_generic.add(sp_actividadeconomica_renta, 2, 1, 5, 10);
        gp_generic.add(sp_actividadeconomica_ciiu, 3, 16, 5, 3);
        gp_generic.add(hb_botones, 0, 18, 5, 1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_generic.getStylesheets().add(SihicApp.hojaestilos);
        gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.setAlignment(Pos.TOP_CENTER);
        // gp_generic.getStylesheets().add("/abaco/SofackarStylesCommon.css");
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
        SihicApp.s_proveedores = null;
        SihicApp.s_proveedores = new Proveedores();
        empty_field();

    }

    public void save() throws InterruptedException {
        validar_formulario();
        if (permitirproceso) {
            set_datos();
            try {

                if (SihicApp.s_informacionTributaria.getId() == null) {
                    SihicApp.informacionTributariaControllerClient.save();
                    L_Message.setText("Registro Almacenado");
                } else {
                    SihicApp.informacionTributariaControllerClient.modificar();
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

        SihicApp.s_informacionTributaria.setActividadesEconomicas_renta(SihicApp.s_ActividadesEconomicas);
        SihicApp.s_informacionTributaria.setRegimenespecial(regimenespecial.isSelected());
        SihicApp.s_informacionTributaria.setTipoactoadministrativo_regimenespecial(tipoactoadministrativo_regimenespecial.getSelectionModel().getSelectedIndex());
        SihicApp.s_informacionTributaria.setTipoactoadministrativo_regimenespecial_numero(tipoactoadministrativo_regimenespecial_numero.getText());
        SihicApp.s_informacionTributaria.setTipoactoadministrativo_regimenespecial_fecha(UtilDate.localdatetodate(tipoactoadministrativo_regimenespecial_fecha.getValue()));
        SihicApp.s_informacionTributaria.setContribuyente(contribuyente.isSelected());
        SihicApp.s_informacionTributaria.setTipoactoadministrativo_contribuyente(tipoactoadministrativo_contribuyente.getSelectionModel().getSelectedIndex());
        SihicApp.s_informacionTributaria.setTipoactoadministrativo_contribuyente_numero(tipoactoadministrativo_contribuyente_numero.getText());
        SihicApp.s_informacionTributaria.setTipoactoadministrativo_contribuyente_fecha(UtilDate.localdatetodate(tipoactoadministrativo_contribuyente_fecha.getValue()));
        SihicApp.s_informacionTributaria.setAutoretenedor(autoretenedor.isSelected());
        SihicApp.s_informacionTributaria.setTipoactoadministrativo_autoretenedor(tipoactoadministrativo_autoretenedor.getSelectionModel().getSelectedIndex());
        SihicApp.s_informacionTributaria.setTipoactoadministrativo_autoretenedor_numero(tipoactoadministrativo_autoretenedor_numero.getText());
        SihicApp.s_informacionTributaria.setTipoactoadministrativo_autoretenedor_fecha(UtilDate.localdatetodate(tipoactoadministrativo_autoretenedor_fecha.getValue()));
        SihicApp.s_informacionTributaria.setExento(exento.isSelected());
        SihicApp.s_informacionTributaria.setTipoactoadministrativo_exento(tipoactoadministrativo_exento.getSelectionModel().getSelectedIndex());
        SihicApp.s_informacionTributaria.setTipoactoadministrativo_exento_numero(tipoactoadministrativo_exento_numero.getText());
        SihicApp.s_informacionTributaria.setTipoactoadministrativo_exento_fecha(UtilDate.localdatetodate(tipoactoadministrativo_exento_fecha.getValue()));
        SihicApp.s_informacionTributaria.setDeclarante(declarante.isSelected());
        SihicApp.s_informacionTributaria.setResponsableiva(responsableiva.isSelected());
        SihicApp.s_informacionTributaria.setTiporegimen(tiporegimen.getSelectionModel().getSelectedIndex());
        SihicApp.s_informacionTributaria.setFactura(factura.isSelected());
        SihicApp.s_informacionTributaria.setResolucionfacturacion_numero(resolucionfacturacion_numero.getText());

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

    public void crearoeditar() {

        if (SihicApp.s_proveedores != null) {

            getDatos();
        } else {
            SihicApp.s_proveedores = new Proveedores();
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

    private void getDatos() {
        if (SihicApp.s_informacionTributaria != null) {

            if (SihicApp.s_informacionTributaria.getActividadesEconomicas_renta() != null) {
                sb_actividadeconomica_renta.setText(SihicApp.s_informacionTributaria.getActividadesEconomicas_renta().getCodigo_ciiu_0079() + "||" + SihicApp.s_informacionTributaria.getActividadesEconomicas_renta().getDescripcion());
            }

            regimenespecial.setSelected(SihicApp.s_informacionTributaria.isRegimenespecial());
            tipoactoadministrativo_regimenespecial.getSelectionModel().select(SihicApp.s_informacionTributaria.getTipoactoadministrativo_regimenespecial());
            tipoactoadministrativo_regimenespecial_numero.setText(SihicApp.s_informacionTributaria.getTipoactoadministrativo_regimenespecial_numero());
            if (SihicApp.s_informacionTributaria.getTipoactoadministrativo_regimenespecial_fecha() == null) {
                tipoactoadministrativo_regimenespecial_fecha.setValue(UtilDate.formatoyearmesdia(SihicApp.s_informacionTributaria.getTipoactoadministrativo_regimenespecial_fecha()));
            }
            contribuyente.setSelected(SihicApp.s_informacionTributaria.isContribuyente());
            tipoactoadministrativo_contribuyente.getSelectionModel().select(SihicApp.s_informacionTributaria.getTipoactoadministrativo_contribuyente());
            tipoactoadministrativo_contribuyente_numero.setText(SihicApp.s_informacionTributaria.getTipoactoadministrativo_contribuyente_numero());
            if (SihicApp.s_informacionTributaria.getTipoactoadministrativo_contribuyente_fecha() != null) {
                tipoactoadministrativo_contribuyente_fecha.setValue(UtilDate.formatoyearmesdia(SihicApp.s_informacionTributaria.getTipoactoadministrativo_contribuyente_fecha()));
            }
            autoretenedor.setSelected(SihicApp.s_informacionTributaria.isAutoretenedor());
            tipoactoadministrativo_autoretenedor.getSelectionModel().select(SihicApp.s_informacionTributaria.getTipoactoadministrativo_autoretenedor());
            tipoactoadministrativo_autoretenedor_numero.setText(SihicApp.s_informacionTributaria.getTipoactoadministrativo_autoretenedor_numero());
            if (SihicApp.s_informacionTributaria.getTipoactoadministrativo_autoretenedor_fecha() != null) {
                tipoactoadministrativo_autoretenedor_fecha.setValue(UtilDate.formatoyearmesdia(SihicApp.s_informacionTributaria.getTipoactoadministrativo_autoretenedor_fecha()));
            }
            exento.setSelected(SihicApp.s_informacionTributaria.isExento());
            tipoactoadministrativo_exento.getSelectionModel().select(SihicApp.s_informacionTributaria.getTipoactoadministrativo_exento());
            tipoactoadministrativo_exento_numero.setText(SihicApp.s_informacionTributaria.getTipoactoadministrativo_exento_numero());
            if (SihicApp.s_informacionTributaria.getTipoactoadministrativo_exento_fecha() != null) {
                tipoactoadministrativo_exento_fecha.setValue(UtilDate.formatoyearmesdia(SihicApp.s_informacionTributaria.getTipoactoadministrativo_exento_fecha()));
            }
            declarante.setSelected(SihicApp.s_informacionTributaria.isDeclarante());
            responsableiva.setSelected(SihicApp.s_informacionTributaria.isResponsableiva());
            tiporegimen.getSelectionModel().select(SihicApp.s_informacionTributaria.getTiporegimen());
            factura.setSelected(SihicApp.s_informacionTributaria.isFactura());
            resolucionfacturacion_numero.setText(SihicApp.s_informacionTributaria.getResolucionfacturacion_numero());
        } else {
            SihicApp.s_informacionTributaria = new InformacionTributaria();
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
