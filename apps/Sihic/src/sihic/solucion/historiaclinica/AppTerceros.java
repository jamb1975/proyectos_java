package sihic.solucion.historiaclinica;

import java.io.IOException;
import java.util.List;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;
import jenum.EstadoRegistroEnum;
import javafx.geometry.HPos;
import javafx.geometry.VPos;

import sihic.SihicApp;
import sihic.control.SearchBoxCustomer;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class AppTerceros extends Application {

    private TableView tableView;

    private int m_IEstRec;
    private Label m_LNombre;
    private TextField m_TFNombre;
    private Label m_LNo_Ident;
    private TextField m_TFNo_Ident;
    private Label m_LDir1;
    private TextField m_TFDir1;
    private Label m_LCelular;
    private TextField m_TFCelular;
    private Label m_LEmail;
    private TextField m_TFEmail;
    private Label m_LSexo;
    private RadioButton m_TFMasc;
    private RadioButton m_TFFem;
    private Button save;
    private Button nuevo;
    private Label success;

    private Pane myPane;
    private GridPane gridpane;
    private GridPane gridpaneRoot;
    private GridPane gridpaneTable;
    private HBox hbox;
    private HBox hboxButton;
    private ChoiceBox choiceBox;
    private SearchBoxCustomer m_SearchBox;
    private Label m_LTipoUsua;
    private Boolean m_BCanSave;

    public Parent createContent() throws IOException {
        m_BCanSave = true;
        tableView = new TableView();
        m_IEstRec = 0;
        choiceBox = new ChoiceBox();
        m_LTipoUsua = new Label("Tipo de Tercero:");

        myPane = new Pane();
        gridpane = new GridPane();
        gridpaneRoot = new GridPane();
        gridpaneTable = new GridPane();
        hbox = new HBox();
        hboxButton = new HBox(10);
        //getChildren().add(myPane);
        m_LNombre = new Label("Nombre: ");
        m_LNombre.setUnderline(true);
        m_TFNombre = new TextField();
        m_LNo_Ident = new Label("No Identificación: ");
        m_LNo_Ident.setUnderline(true);
        m_TFNo_Ident = new TextField();
        m_LDir1 = new Label("Dirección: ");
        m_TFDir1 = new TextField();
        m_TFDir1.setPrefSize(250, USE_PREF_SIZE);
        m_LCelular = new Label("Celular: ");
        m_TFCelular = new TextField();
        m_LCelular.setUnderline(true);
        m_LSexo = new Label("Sexo: ");
        m_TFMasc = new RadioButton("Masculino");
        m_TFMasc.getStyleClass().add("label");
        ToggleGroup toggleGroup = new ToggleGroup();
        ImageView imageView = new ImageView("/images/bs_save.gif");
        save = new Button("Guardar", imageView);
        imageView = null;
        imageView = new ImageView("/images/bs_newRecord.gif");
        nuevo = new Button("Nuevo", imageView);
        success = new Label();
        hboxButton.getChildren().addAll(save, nuevo);
        m_TFMasc.setToggleGroup(toggleGroup);
        m_TFFem = new RadioButton("Femenino");
        m_TFFem.getStyleClass().add("label");
        m_TFFem.setToggleGroup(toggleGroup);
        m_LEmail = new Label("Email: ");
        m_TFEmail = new TextField();
        m_SearchBox = new SearchBoxCustomer("SearchBox", "Buscar Por: Nombre o No Ident");
        m_SearchBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

            }
        });
        hbox.setSpacing(5);
        hbox.getChildren().addAll(m_TFMasc, m_TFFem);
        // m_TFNombre.getStyleClass().add("text-input");
        m_TFNombre.setPrefWidth(120);
        Text _tTexthebreo = new Text("Datos Personales Tercero");
        _tTexthebreo.setFill(Color.BLACK);
        _tTexthebreo.textAlignmentProperty().setValue(TextAlignment.CENTER);
        _tTexthebreo.setFont(Font.font("WP Hebrew David", FontWeight.SEMI_BOLD, 32));

        GridPane.setHalignment(gridpane, HPos.CENTER);
        GridPane.setValignment(gridpane, VPos.CENTER);
        gridpane.setHgap(2);
        gridpane.setVgap(2);

        //gridpane.add(_tTexthebreo, 0, 0,2,1);
        gridpane.add(m_LNo_Ident, 0, 0);
        gridpane.add(m_TFNo_Ident, 1, 0);
        gridpane.add(m_LNombre, 0, 1);
        gridpane.add(m_TFNombre, 1, 1);
        gridpane.add(m_LDir1, 0, 2);
        gridpane.add(m_TFDir1, 1, 2, 2, 1);
        gridpane.add(m_LCelular, 0, 3);
        gridpane.add(m_TFCelular, 1, 3);
        gridpane.add(m_LSexo, 0, 4);
        gridpane.add(hbox, 1, 4);
        gridpane.add(m_LEmail, 0, 5);
        gridpane.add(m_TFEmail, 1, 5);
        gridpane.add(m_LTipoUsua, 0, 6);
        gridpane.add(choiceBox, 1, 6);
        gridpane.add(success, 0, 7, 2, 1);
        gridpane.add(hboxButton, 1, 9, 3, 1);

        gridpane.setMinSize(1000, 350);

        nuevo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                nuevoTercero();
            }
        });
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                saveTercero();
            }
        });

        //Crear Tabla
        LoadTable();
        TableColumn firstNameCol = new TableColumn();
        firstNameCol.setPrefWidth(150);
        firstNameCol.setText("Nombre");
        firstNameCol.setCellValueFactory(new PropertyValueFactory("nombre"));
        TableColumn lastNameCol = new TableColumn();
        lastNameCol.setPrefWidth(150);
        //lastNameCol.getStyleClass().add("table-header");
        Text _TNoIden = new Text("No. Identificación");
        lastNameCol.setText(_TNoIden.getText());
        lastNameCol.setCellValueFactory(new PropertyValueFactory("no_ident"));

        tableView.setMinWidth(300);

        tableView.setEditable(true);
        tableView.getColumns().addAll(firstNameCol, lastNameCol);
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                fLoadRecord();
            }

        });
        tableView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.UP) {
                    fLoadRecord();
                }
            }
        });
        //agregamos el formulario y la tabla
        gridpane.add(m_SearchBox, 3, 0);

        gridpane.add(tableView, 3, 1, 1, 10);
        // gridpaneRoot.add(gridpane, 0, 0);
        //  gridpaneRoot.setGridLinesVisible(true);
        // gridpane.setGridLinesVisible(true);
        ObservableList _observTipoTercero = FXCollections.observableArrayList();
        _observTipoTercero.add("Empleado");
        _observTipoTercero.add("Cliente");
        _observTipoTercero.add("Usuario");
        choiceBox.setItems(_observTipoTercero);
        choiceBox.setValue("Empleado");
        gridpane.getStyleClass().add("background");
        return gridpane;
    }

    public static Node createIconContent() throws IOException {
        ImageView imageView = new ImageView(new Image("/modulos/parametrizacion/Terceros.png"));
        imageView.setFitHeight(80);
        imageView.setFitWidth(90);
        javafx.scene.Group g
                = new javafx.scene.Group(imageView);

        return g;
    }

    public void saveTercero() {
        boolean _IsGuardado = true;

        if (m_TFCelular.getText().equals("")) {
            m_TFCelular.setStyle("-fx-background-color:#ff1600");
            m_BCanSave = false;
        }
        if (m_TFNo_Ident.getText().equals("")) {
            m_TFNo_Ident.setStyle("-fx-background-color:#ff1600");
            m_BCanSave = false;
        }
        if (m_TFNombre.getText().equals("")) {
            m_TFNombre.setStyle("-fx-background-color:#ff1600");
            m_BCanSave = false;
        }
        if (m_BCanSave) {

            save.setDisable(true);
            nuevo.setDisable(false);
            try {
                switch (m_IEstRec) {
                    case 0:
                        break;
                    case 1:
                        break;

                }
                LoadTable();

            } catch (Exception e) {
                _IsGuardado = false;

            }
            success.setOpacity(0);
            if (_IsGuardado) {
                success.setText(m_IEstRec == EstadoRegistroEnum.NUEVO.ordinal() ? "Registro Guardado" : "Registro Modificado");
            } else {
                success.setText("Error al Almacenar");

            }
        } else {
            m_BCanSave = true;
            success.setText("Los Campos de Texto en Rojo son Obligatorios ");
        }
        animateMessage();

    }

    public void nuevoTercero() {
        m_IEstRec = 0;
        save.setDisable(false);
        nuevo.setDisable(true);

        m_TFNombre.setText("");
        m_TFNo_Ident.setText("");
        m_TFCelular.setText("");
        m_TFDir1.setText("");
        m_TFEmail.setText("");
        m_TFMasc.setSelected(false);
        m_TFFem.setSelected(false);

        success.setOpacity(0);;
        success.setText("Registro Nuevo");
        animateMessage();
    }

    private void animateMessage() {
        success.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(2000), success);
        ft.setFromValue(0.0);
        ft.setToValue(1);

        ft.play();

        //success.setOpacity(0);
        ft.setOnFinished(new EventHandler<ActionEvent>() {

            public void handle(ActionEvent event) {
                success.setText("");
                m_TFCelular.setStyle(null);
                m_TFCelular.getStyleClass().add("text-field");
                m_TFNo_Ident.setStyle(null);

                m_TFNo_Ident.getStyleClass().add("text-field");
                m_TFNombre.setStyle(null);

                m_TFNombre.getStyleClass().add("text-field");
            }
        });

    }

    public void fLoadRecord() {
        m_IEstRec = 1;
        if (save.isDisable()) {
            save.setDisable(false);
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    /**
     * Java main for when running without JavaFX launcher
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public void LoadTable() {
        try {
            ///System.out.println("SearchBox.handle DOWN");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
