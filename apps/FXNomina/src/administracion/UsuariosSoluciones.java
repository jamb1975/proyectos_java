/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administracion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TreeItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import modelo.GenPersonas;
import modelo.Solucion;
import modelo.UsuarioSoluciones;
import modelo.Usuarios;
import fxnomina.FXNomina;
import controlador.GenPersonasControllerClient;
import controlador.UsuarioSolucionesControllerClient;

/**
 *
 * @author karolyani
 */
public class UsuariosSoluciones extends Application {

    private GridPane Gp_UsuariosSoluci;
    private TableView Tv_Usuarios;
    private Label L_Buscar;
    private Label L_Nombre;
    private Label L_NoIdent;
    private Label L_Password;
    private Label L_Rpassword;
    private Label la_usuario;
    private Label la_celular;
    private Label la_direccion;
    private Label la_email;
    private Label la_apellido;
    private TextField Tf_Buscar;
    private TextField Tf_Nombre;
    private TextField apellido;
    private TextField Tf_NoIdent;
    private TextField usuario;
    private TextField celular;
    private TextField direccion;
    private TextField email;
    private PasswordField Tf_Password;
    private PasswordField Tf_Rpassword;
    private TableColumn Tc_Nombre;
    private TableColumn Tc_NoIdent;
    private List<RadioButton> Rb_Soluciones;
    private Map<String, CheckBox> Cb_UsuarioSoluciones = new HashMap<String, CheckBox>();
    private VBox Vb_Soluciones;
    private VBox Vb_UsuarioSoluciones;
    private VBox Vb_UsuarioSoluciones2;
     private VBox Vb_UsuarioSoluciones3;
    private HBox botones;
    private ToggleGroup Tg_Soluciones;
    private Usuarios usuarios;
    private GenPersonas genPersonas;
    CheckBox rb = new CheckBox();
    int index;
    //Datos
    private ObservableList Ol_Usuarios = FXCollections.observableArrayList();
    private UsuarioSolucionesControllerClient usuariosSolucionesControllerClient;
    private GenPersonasControllerClient genPersonasControllerClient;
    private Map<String, Solucion> Ma_UsuarioSoluciones;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
    private StackPane stack;
    private ImageView imageView;
    private Button save;
    private Button nuevo;
    private TitledPane tp_generic;

    public Parent createContent() {
        tp_generic = new TitledPane();
        tp_generic.setText("Administrador Usuarios y Asignación de Soluciones");
        tp_generic.setCollapsible(false);
        botones = new HBox(2);
        imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        save = new Button();
        save.setGraphic(imageView);
        save.setOnAction(e -> {
            saveusuario();
        });
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        nuevo = new Button("", imageView);
        nuevo.setOnAction(e -> {
            nuevousuario();
        });
        botones.getChildren().addAll(save, nuevo);
        stack = new StackPane();
        stack.setAlignment(Pos.TOP_CENTER);
        stack.autosize();

        genPersonas = new GenPersonas();
        Ma_UsuarioSoluciones = new HashMap<String, Solucion>();
        genPersonasControllerClient = new GenPersonasControllerClient();
        usuarios = new Usuarios();
        Gp_UsuariosSoluci = new GridPane();
        Tv_Usuarios = new TableView();
        L_Buscar = new Label("Buscar: ");
        L_Nombre = new Label("Nombre: ");
        L_NoIdent = new Label("Nº Ident: ");
        L_Password = new Label("Password: ");
        L_Rpassword = new Label("R_Password: ");
        la_usuario = new Label("Usuario: ");
        la_celular = new Label("Celular: ");
        la_direccion = new Label("Dirección: ");
        la_email = new Label("Email: ");
        la_apellido = new Label("Apellido: ");
        Tf_Buscar = new TextField();
        Tf_Nombre = new TextField();
        Tf_NoIdent = new TextField();
        usuario = new TextField();
        celular = new TextField();
        direccion = new TextField();
        email = new TextField();
        apellido = new TextField();
        Tf_Password = new PasswordField();
        Tf_Rpassword = new PasswordField();
        Tf_Rpassword.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

            }
        });
        Tf_Buscar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {

                getUsuarios();
                HAbDisRb(false);
            }
        });
        Tv_Usuarios.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                LoadRecord();
            }

        });
        Tc_NoIdent = new TableColumn("No Ident");
        Tc_NoIdent.setMinWidth(200);
        Tc_NoIdent.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuarios, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuarios, String> usua) {

                return new SimpleStringProperty(usua.getValue().getGenPersonas().getDocumento());

            }
        });
        Tc_Nombre = new TableColumn("Nombre");
        Tc_Nombre.setMinWidth(1130);
        Tc_Nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Usuarios, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Usuarios, String> usua) {

                return new SimpleStringProperty(usua.getValue().getGenPersonas().getNombres());

            }
        });
        Tv_Usuarios.setMinHeight(250);
        Tv_Usuarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        GridPane.setHalignment(Tv_Usuarios, HPos.CENTER);
        Tv_Usuarios.getColumns().addAll(Tc_NoIdent, Tc_Nombre);
        Gp_UsuariosSoluci.add(L_Buscar, 0, 0);
        Gp_UsuariosSoluci.add(Tf_Buscar, 1, 0, 3, 1);
        Gp_UsuariosSoluci.add(Tv_Usuarios, 0, 7, 7, 1);
        Gp_UsuariosSoluci.add(L_Nombre, 0, 1);
        Gp_UsuariosSoluci.add(Tf_Nombre, 1, 1);
        Gp_UsuariosSoluci.add(la_apellido, 2, 1);
        Gp_UsuariosSoluci.add(apellido, 3, 1);
        Gp_UsuariosSoluci.add(L_NoIdent, 0, 2);
        Gp_UsuariosSoluci.add(Tf_NoIdent, 1, 2);
        Gp_UsuariosSoluci.add(la_usuario, 2, 2);
        Gp_UsuariosSoluci.add(usuario, 3, 2);
        Gp_UsuariosSoluci.add(L_Password, 0, 3);
        Gp_UsuariosSoluci.add(Tf_Password, 1, 3);
        Gp_UsuariosSoluci.add(L_Rpassword, 2, 3);
        Gp_UsuariosSoluci.add(Tf_Rpassword, 3, 3);
        Gp_UsuariosSoluci.add(la_direccion, 0, 4);
        Gp_UsuariosSoluci.add(direccion, 1, 4);
        Gp_UsuariosSoluci.add(la_email, 2, 4);
        Gp_UsuariosSoluci.add(email, 3, 4);
        Gp_UsuariosSoluci.add(la_celular, 0, 5);
        Gp_UsuariosSoluci.add(celular, 1, 5);
        Gp_UsuariosSoluci.add(botones, 0, 6, 4, 1);
        botones.setAlignment(Pos.CENTER);
        Gp_UsuariosSoluci.getStyleClass().add("background");
        Gp_UsuariosSoluci.setVgap(2);
        Gp_UsuariosSoluci.setMinWidth(700);
        usuariosSolucionesControllerClient = new UsuarioSolucionesControllerClient();
        Rb_Soluciones = new ArrayList<RadioButton>();

        Vb_Soluciones = new VBox();
        Vb_UsuarioSoluciones = new VBox();
        Vb_UsuarioSoluciones.setSpacing(4);
        Vb_UsuarioSoluciones2 = new VBox();
        Vb_UsuarioSoluciones2.setSpacing(4);
         Vb_UsuarioSoluciones3 = new VBox();
        Vb_UsuarioSoluciones3.setSpacing(4);
        Tg_Soluciones = new ToggleGroup();

        Gp_UsuariosSoluci.add(Vb_UsuarioSoluciones, 2, 8, 1, 1);
        Gp_UsuariosSoluci.add(Vb_UsuarioSoluciones2, 4, 8, 1, 1);
          Gp_UsuariosSoluci.add(Vb_UsuarioSoluciones3, 6, 8, 1, 1);
        getSoluciones();
        Gp_Message = new GridPane();
        Gp_Message.setMinSize(Gp_UsuariosSoluci.getLayoutBounds().getHeight(), Gp_UsuariosSoluci.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(FXNomina.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        tp_generic.setContent(Gp_UsuariosSoluci);
        stack.getChildren().addAll(tp_generic, Gp_Message);
        Gp_UsuariosSoluci.setMinWidth(1340);
        return stack;

    }

    private void getSoluciones() {
        RadioButton rb = new RadioButton();

        for (Solucion s : usuariosSolucionesControllerClient.getSoluciones()) {
            rb.setText(s.getSolucion());

            rb.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ke) {

                    getUsuarioSoluciones(s);

                }
            });
            rb.setOnMouseClicked(e -> {

                getUsuarioSoluciones(s);

            });
            Rb_Soluciones.add(rb);
            rb = null;
            rb = new RadioButton();
        }
        for (RadioButton rb2 : Rb_Soluciones) {
            rb2.setDisable(true);
            rb2.setToggleGroup(Tg_Soluciones);
            Vb_Soluciones.getChildren().add(rb2);

        }
        Gp_UsuariosSoluci.add(Vb_Soluciones, 0, 8, 2, 1);
    }

    private void getUsuarioSoluciones(Solucion solucion) {
        Vb_UsuarioSoluciones.getChildren().clear();
        Vb_UsuarioSoluciones2.getChildren().clear();
        Vb_UsuarioSoluciones3.getChildren().clear();
        Cb_UsuarioSoluciones.clear();
        Vb_UsuarioSoluciones.setSpacing(4);
        Vb_UsuarioSoluciones2.setSpacing(4);
         Vb_UsuarioSoluciones3.setSpacing(4);
        index = 0;
        List<Solucion> li_usuariossoluciones = usuariosSolucionesControllerClient.getSoluciones(solucion);
        System.out.println("solucion->" + solucion.getSolucion());
        System.out.println("No items->" + li_usuariossoluciones.size());
        for (final Solucion sol : li_usuariossoluciones) {

            rb.setText(sol.getSolucion());
            rb.setStyle("-fx-text-fill: white;");
            if (Ma_UsuarioSoluciones.get(sol.getSolucion()) != null) {
                rb.setSelected(true);
                Cb_UsuarioSoluciones.put(sol.getSolucion(), rb);
            } else {
                rb.setSelected(false);
                Cb_UsuarioSoluciones.put(sol.getSolucion(), rb);
            }

            Cb_UsuarioSoluciones.get(sol.getSolucion()).setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent ke) {

                    boolean B_rb = Cb_UsuarioSoluciones.get(sol.getSolucion()).isSelected();
                    SaveOrDelete(sol, B_rb);

                }
            });
            if (index<7) {
                Vb_UsuarioSoluciones.getChildren().add(Cb_UsuarioSoluciones.get(sol.getSolucion()));
            } else {
                 if (index>=7 && index <14) {
                Vb_UsuarioSoluciones2.getChildren().add(Cb_UsuarioSoluciones.get(sol.getSolucion()));
                 }
                 else {
                 if (index>=14) {
                Vb_UsuarioSoluciones3.getChildren().add(Cb_UsuarioSoluciones.get(sol.getSolucion()));
                 }
            }
            }
            index++;

            rb = null;
            rb = new CheckBox();
        }

    }

    private void SaveOrDelete(Solucion s, boolean Bo_sol) {

        if (Bo_sol) {
            UsuarioSoluciones usuasolu = null;
            usuasolu = new UsuarioSoluciones();
            usuasolu.setSolucion(s);
            usuasolu.setUsuario(usuarios);
            usuariosSolucionesControllerClient.create(usuasolu);
        } else {
            usuariosSolucionesControllerClient.remove(s);
        }
        getUsuarioSoluciones();
    }

    private void getUsuarios() {
        Ol_Usuarios.clear();
        Ol_Usuarios.setAll(usuariosSolucionesControllerClient.getUsuarios(Tf_Buscar.getText().trim()));
        Tv_Usuarios.setItems(Ol_Usuarios);
    }

    private void LoadRecord() {
        Ma_UsuarioSoluciones.clear();
        Cb_UsuarioSoluciones.clear();
        Vb_UsuarioSoluciones.getChildren().clear();
        Vb_UsuarioSoluciones2.getChildren().clear();
        Vb_UsuarioSoluciones3.getChildren().clear();
        try {
            Tf_NoIdent.setText(((Usuarios) Tv_Usuarios.getSelectionModel().getSelectedItem()).getGenPersonas().getDocumento());
            Tf_Nombre.setText(((Usuarios) Tv_Usuarios.getSelectionModel().getSelectedItem()).getGenPersonas().getPrimerNombre());
            apellido.setText(((Usuarios) Tv_Usuarios.getSelectionModel().getSelectedItem()).getGenPersonas().getPrimerApellido());
            celular.setText(((Usuarios) Tv_Usuarios.getSelectionModel().getSelectedItem()).getGenPersonas().getTelefono());
            direccion.setText(((Usuarios) Tv_Usuarios.getSelectionModel().getSelectedItem()).getGenPersonas().getDireccion());
            email.setText(((Usuarios) Tv_Usuarios.getSelectionModel().getSelectedItem()).getGenPersonas().getEMail());
            usuario.setText(((Usuarios) Tv_Usuarios.getSelectionModel().getSelectedItem()).getUsuario());
            Tf_Password.setText(((Usuarios) Tv_Usuarios.getSelectionModel().getSelectedItem()).getPassword());
            Tf_Rpassword.setText(((Usuarios) Tv_Usuarios.getSelectionModel().getSelectedItem()).getPassword());
            usuarios = (Usuarios) Tv_Usuarios.getSelectionModel().getSelectedItem();
            genPersonas = ((Usuarios) Tv_Usuarios.getSelectionModel().getSelectedItem()).getGenPersonas();
            HAbDisRb(true);
            getUsuarioSoluciones();
        } catch (Exception e) {

        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void getUsuarioSoluciones() {
        Ma_UsuarioSoluciones.clear();
        for (UsuarioSoluciones us : usuariosSolucionesControllerClient.getUsuarioSoluciones(usuarios)) {
            Ma_UsuarioSoluciones.put(us.getSolucion().getSolucion(), us.getSolucion());
        }
    }

    private void HAbDisRb(boolean hab) {
        for (RadioButton rb2 : Rb_Soluciones) {
            if (hab) {
                rb2.setDisable(false);
            } else {
                rb2.setDisable(true);
            }
            rb2.setSelected(false);
        }
    }

    private void saveusuario() {
        if (Tf_Password.getText().trim().equals(Tf_Rpassword.getText().trim())) {
            System.out.println("gen personas->" + genPersonas);
            usuarios.setPassword(Tf_Password.getText().trim());
            usuarios.setUsuario(usuario.getText().trim());
            genPersonas.setPrimerNombre(Tf_Nombre.getText());
            genPersonas.setPrimerApellido(apellido.getText());
            genPersonas.setDocumento(Tf_NoIdent.getText());
            genPersonas.setEMail(email.getText());
            genPersonas.setDireccion(direccion.getText());
            genPersonas.setTelefono(celular.getText());
            try {
                genPersonasControllerClient.saveusuario(genPersonas, usuarios);
                L_Message.setText("Registro almacenado");
                Task_Message = () -> {
                    try {

                        SetMessage();
                    } catch (InterruptedException ex) {

                    }
                };
                backgroundThread = new Thread(Task_Message);
                backgroundThread.setDaemon(true);
                backgroundThread.start();
            } catch (Exception e) {
                L_Message.setText("Error en el proceso");
                Task_Message = () -> {
                    try {
                        SetMessage();
                    } catch (InterruptedException ex) {

                    }
                };
                backgroundThread = new Thread(Task_Message);
                backgroundThread.setDaemon(true);
                backgroundThread.start();

            }
        }
    }

    private void SetMessage() throws InterruptedException {

        Gp_Message.setVisible(true);
        //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
        Thread.sleep(3000);
        Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
        Task_Message = null;

    }

    private void nuevousuario() {
        usuarios = null;
        usuarios = new Usuarios();
        genPersonas = null;
        genPersonas = new GenPersonas();
        Tf_NoIdent.setText("");
        Tf_Nombre.setText("");
        apellido.setText("");
        Tf_Password.setText("");
        Tf_Rpassword.setText("");
        celular.setText("");
        direccion.setText("");
        email.setText("");

    }

}
