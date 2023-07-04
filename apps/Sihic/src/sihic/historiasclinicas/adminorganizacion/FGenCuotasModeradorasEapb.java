/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.historiasclinicas.adminorganizacion;
import sihic.SihicApp;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import message.EstadosMensaje;
import modelo.GenCuotasModeradorasEapb;
import modelo.GenEapb;
import sihic.controlador.GenCuotasModeradorasControllerClient;
import sihic.general.LoadChoiceBoxGeneral;

/**
 *
 * @author adminlinux
 */
public class FGenCuotasModeradorasEapb extends Application {

    private TableView tv_cuotasmoderadoras;
    private ObservableList Ol_Generic = FXCollections.observableArrayList();
    StackPane stack;
    private GridPane gp_generic;
    private Label la_eps;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    private Thread backgroundThread;
    private GenEapb genEapb;
    private GenCuotasModeradorasEapb cuotasModeradorasEapb;
    EstadosMensaje estadosMensaje;
    private GenCuotasModeradorasControllerClient genCuotasModeradorasControllerClient;

    public Parent createContent() throws IOException {
        genEapb = new GenEapb();
        cuotasModeradorasEapb = new GenCuotasModeradorasEapb();
        genCuotasModeradorasControllerClient = new GenCuotasModeradorasControllerClient();
        Gp_Message = new GridPane();
        gp_generic = new GridPane();
        la_eps = new Label("Eps:");
        L_Message = new Label();
        stack = new StackPane();
        tv_cuotasmoderadoras = new TableView();
        tv_cuotasmoderadoras.setEditable(true);
        LoadChoiceBoxGeneral.getCb_eapb().setOnAction(e -> {
            try {

                getCuotasModeradorasEapb();
            } catch (Exception e2) {

            }
        });
        TableColumn nivel = new TableColumn();
        nivel.setMinWidth(200);
        nivel.setText("Nivel");
        nivel.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenCuotasModeradorasEapb, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenCuotasModeradorasEapb, String> genCuotasModeradorasEapb) {

                return new SimpleStringProperty(String.valueOf(genCuotasModeradorasEapb.getValue().getGenNivelesUsuarios().getNivel()));

            }
        });
        TableColumn valor = new TableColumn();
        valor.setMinWidth(200);
        valor.setText("Valor");
        valor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<GenCuotasModeradorasEapb, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<GenCuotasModeradorasEapb, String> genCuotasModeradorasEapb) {

                return new SimpleStringProperty(genCuotasModeradorasEapb.getValue().getValor().toString());

            }
        });
        valor.setCellFactory(TextFieldTableCell.forTableColumn());
        valor.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<GenCuotasModeradorasEapb, String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<GenCuotasModeradorasEapb, String> t) {

                ((GenCuotasModeradorasEapb) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())).setValor(BigDecimal.valueOf(Double.valueOf(t.getNewValue())));
                BigDecimal valor = (((GenCuotasModeradorasEapb) t.getTableView().getItems().get(t.getTablePosition().getRow())).getValor());
                cuotasModeradorasEapb = ((GenCuotasModeradorasEapb) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                genEapb.addCuotaModeradora(cuotasModeradorasEapb);
                try {
                    save();
                } catch (InterruptedException ex) {

                }
            }
        });
        tv_cuotasmoderadoras.autosize();
        tv_cuotasmoderadoras.getColumns().addAll(nivel, valor);
        tv_cuotasmoderadoras.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        gp_generic.getStylesheets().add(SihicApp.hojaestilos);
        gp_generic.getStyleClass().add("background");
        gp_generic.setAlignment(Pos.TOP_CENTER);
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        Gp_Message.setMinSize(gp_generic.getLayoutBounds().getHeight(), gp_generic.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        gp_generic.add(la_eps, 0, 0);
        gp_generic.add(LoadChoiceBoxGeneral.getCb_eapb(), 1, 0);
        gp_generic.add(tv_cuotasmoderadoras, 0, 1, 2, 1);
        gp_generic.setMinSize(700, 400);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().addAll(gp_generic, Gp_Message);

        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void SetMessage() throws InterruptedException {

        Gp_Message.setVisible(true);
        //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
        Thread.sleep(3000);
        Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
        Task_Message = null;

    }

    public void save() throws InterruptedException {

        genCuotasModeradorasControllerClient.save(genEapb);
        L_Message.setText("Registro Almacenado");
        try {
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
            e.printStackTrace();
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

    private void getCuotasModeradorasEapb() {
        genEapb = genCuotasModeradorasControllerClient.addCuotasModeradoras(LoadChoiceBoxGeneral.getCb_eapb().getValue().toString());

        Ol_Generic.clear();

        Ol_Generic.addAll(genEapb.getLi_genCuotasModeradorasEapbs().toArray());
        // Ta_KardexProducto.getItems().clear();
        tv_cuotasmoderadoras.setItems(Ol_Generic);

    }
}
