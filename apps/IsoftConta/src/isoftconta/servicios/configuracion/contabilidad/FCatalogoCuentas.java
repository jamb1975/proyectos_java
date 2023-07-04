
package isoftconta.servicios.configuracion.contabilidad;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.TreeTableView.TreeTableViewSelectionModel;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import isoftconta.ContabilidadFXServicios;
import isoftconta.IsoftConta;
import isoftconta.glyphfont.Glyph;
import isoftconta.servicios.CatalogoCuentasController;
import modelo.EntidadesStatic;
import modelo.Puc;
import org.jopendocument.dom.spreadsheet.Sheet;





import org.jopendocument.dom.spreadsheet.SpreadSheet;

public class FCatalogoCuentas extends ContabilidadFXServicios{
    private static GridPane  gpconpuc;
    private Label l_buscar;
    private static TextField  buscar;
    private ObservableList ol_conpuc = FXCollections.observableArrayList();
    private String appPathConPuc;
    private Class clzConPuc;
    private Object appClassConPuc;
    private static Stage stageConPuc = new Stage(StageStyle.DECORATED);
    private Scene scene = null;
    private Parent P_GenConPuc;
    private static TreeTableView tv_conpuc;
   

    private static TreeTableViewSelectionModel<Puc> tsm;
    private static TreeItem<Puc> rootNode;
    private Button nuevo;
    private static ContextMenu contextMenu;
    private MenuItem itemnuevo;
    private MenuItem itemeditar;
    private MenuItem itemeliminar;
    private TitledPane tp_generic;
    private File file = new File("/home/adminlinux/CONTABILIDAD_2019/puc_2019_salud.ods");
    private Sheet catalogo;
    private Button bu_importar;
    private final TextField cuenta=new TextField("0");
    private Label vercuentas=new Label("Ver Solamente Cuentas:");
    private static ChoiceBox cb_vercuentas=new ChoiceBox();
    private static TreeTableColumn<Puc, Boolean> deshabilitar = new TreeTableColumn<Puc, Boolean>("Deshabilitar");

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override public String getServicioNombre() {
        return "Catálogo de Cuentas";
    }
    
    
    
    @Override public boolean isVisible() {
        return true;
    }
    
    @Override public Node getPanel(Stage stage) {
        char IM_IMPORTAR= '\uf1c3';
        char IM_NUEVO = '\uf0fe';
        Color randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
       Glyph glyph_importar=  IsoftConta.icoMoon.create(IM_IMPORTAR).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
       randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
       Glyph glyph_nuevo=  IsoftConta.icoMoon.create(IM_NUEVO).sizeFactor(2).color(randomColor).size(32).useGradientEffect();
     
        
         try {
            catalogo = SpreadSheet.createFromFile(file).getSheet(0);
        } catch (Exception e) {

        }
        cb_vercuentas.getItems().addAll("Activas","Desactivas","Todas");
        cb_vercuentas.getSelectionModel().select(0);
        tp_generic = new TitledPane();
        tp_generic.setText("Administrador Catálogo de Cuentas");
        tp_generic.setCollapsible(false);
       
        itemnuevo = new MenuItem("Nuevo");
        itemnuevo.setOnAction(e -> {
            EntidadesStatic.es_puc = null;

            try {
                if (checkregistroexistentePadre()) {
                    show();
                }
            } catch (Exception x) {

            }
        });
       
        itemeditar = new MenuItem("Editar");
        itemeditar.setOnAction(e -> {

            try {
                if (checkregistroexistente()) {
                    show();
                }

            } catch (Exception x) {
                    x.printStackTrace();
            }
        });
        
        itemeliminar = new MenuItem("Eliminar");
        itemeliminar.setOnAction(e -> {

            try {

                if (checkregistroexistente()) {
                    delete();
                }

            } catch (Exception x) {

            }
        });
        contextMenu = new ContextMenu(itemnuevo, itemeditar, itemeliminar);
        
           cb_vercuentas.getSelectionModel().selectedIndexProperty().addListener(this::indexChanged);
     
        stageConPuc.setTitle("Catálogo de Cuentas");
        nuevo = new Button("",glyph_nuevo);
        
        nuevo.setDisable(false);
        nuevo.setOnAction(e
                -> {
            try {
                EntidadesStatic.es_puc = null;
                if (checkregistroexistentePadre()) {
                    show();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
             }
        });
        bu_importar = new Button("",glyph_importar);
       // bu_importar.setDisable(true);
        bu_importar.setOnAction(e -> {
            try {
                ImportarCatalogo();
            } catch (IOException ex) {
             } catch (ParseException ex) {
            }
        });
       
       
        //**********Ventana Factura

        l_buscar = new Label("Buscar:");
        l_buscar.setMinWidth(100);
        buscar = new TextField("0");
        buscar.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                // System.out.println("textfield changed from " + oldValue + " to " + newValue);
                getPuc();
            } catch (ParseException ex) {
            }
        });

        gpconpuc = new GridPane();

        try {
            getPuc();
        } catch (ParseException ex) {
            Logger.getLogger(FCatalogoCuentas.class.getName()).log(Level.SEVERE, null, ex);
        }
        gpconpuc.add(l_buscar, 0, 0);
        gpconpuc.add(buscar, 1, 0);
        gpconpuc.add(nuevo, 2, 0);
     //   gpconpuc.add(bu_importar, 3, 0);
        gpconpuc.add(vercuentas, 4, 0);
        gpconpuc.add(cb_vercuentas, 5, 0);
        gpconpuc.setVgap(5);
        gpconpuc.setHgap(5);
        tp_generic.setContent(gpconpuc);
        StackPane stack = new StackPane();
        gpconpuc.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        stack.getChildren().add(tp_generic);
        return stack;
        
       
    }
    
    
    
  
     public static void getPuc() throws ParseException {
        //colocamos los datos

        init_tretable();
        rootNode = null;
        EntidadesStatic.es_puc = null;
        EntidadesStatic.es_puc = new Puc();
         try {
            if(buscar.getText().equals(""))
            {
                buscar.setText("");
            }
            EntidadesStatic.es_puc = CatalogoCuentasController.findConPuc(buscar.getText(),cb_vercuentas.getSelectionModel().getSelectedIndex());

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (EntidadesStatic.es_puc != null) {
            rootNode = new TreeItem<>(EntidadesStatic.es_puc);
            createTree(EntidadesStatic.es_puc, rootNode);
            tv_conpuc.setRoot(rootNode);
          

        }
        try {
            tv_conpuc.getTreeItem(tsm.getSelectedIndex()).setExpanded(true);
        } catch (Exception e) {

        }
        expandTreeView(rootNode);
    }

    private boolean checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tsm.getSelectedItem()) != null) {
           EntidadesStatic.es_puc = (Puc) tsm.getSelectedItem().getValue();
           EntidadesStatic.es_puc_padre = (Puc) tsm.getSelectedItem().getValue().getConpuc_id();
            return true;
        } else {
            return false;
        }
    }

    private boolean checkregistroexistentePadre() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tsm.getSelectedItem()) != null) {
            EntidadesStatic.es_puc_padre = (Puc) tsm.getSelectedItem().getValue();
            return true;
        } else {
            return false;
        }
    }

    private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
         appPathConPuc = "isoftconta.servicios.configuracion.contabilidad.FICI_CatalogoCuentas";
        try {
            clzConPuc = Class.forName(appPathConPuc);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        appClassConPuc = clzConPuc.getDeclaredConstructor().newInstance();
        P_GenConPuc = null;
        P_GenConPuc = (Parent) clzConPuc.getMethod("createContent").invoke(appClassConPuc);
        scene = null;
        scene = new Scene(P_GenConPuc, Color.TRANSPARENT);

        if (stageConPuc.isShowing()) {
            stageConPuc.close();
        }

//set scene to stage
        stageConPuc.sizeToScene();

        //center stage on screen
        stageConPuc.centerOnScreen();
        stageConPuc.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stageConPuc.show();
    }

    // A change listener to track the change in selected index
    private static void createTree(Puc conpuc, TreeItem<Puc> root) {

        for (Puc cp : CatalogoCuentasController.getConPucHijos(conpuc)) {
            TreeItem<Puc> node = new TreeItem<>(cp);
            if(cb_vercuentas.getSelectionModel().getSelectedIndex()==0)
            {
                if(!cp.isDeshabilitar())
                {
            root.getChildren().add(node);
            if (CatalogoCuentasController.getConPucHijos(cp).size() > 0)
            {
                createTree(cp, node);
            }
                }
            }
            else
            {
                 if(cb_vercuentas.getSelectionModel().getSelectedIndex()==1)
            {
                if(cp.isDeshabilitar()|| cp.getCodigo().equals(buscar.getText()))
                {
            root.getChildren().add(node);
            if (CatalogoCuentasController.getConPucHijos(cp).size() > 0)
            {
                createTree(cp, node);
            }
                }
            }
                 else
                 {
             root.getChildren().add(node);
            if (CatalogoCuentasController.getConPucHijos(cp).size() > 0)
            {
                createTree(cp, node);
            }
                 }
            }
            }
        

    }

    public static Puc getConPuc() {
        return EntidadesStatic.es_puc;
    }

    public static void setConPuc(Puc conPuc) {
        EntidadesStatic.es_puc = conPuc;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    private static void init_tretable() {
        gpconpuc.getChildren().remove(tv_conpuc);
        tv_conpuc = null;
        tsm = null;
        tv_conpuc = new TreeTableView();
          tv_conpuc.setMinHeight(577);
        tsm = tv_conpuc.getSelectionModel();

        stageConPuc.setOnHidden(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                if (windowEvent.getEventType() == WindowEvent.WINDOW_HIDDEN) {

                    // getPuc();
                }
            }
        });
        TreeTableColumn codigo = new TreeTableColumn();
        codigo.setMinWidth(200);
        codigo.setText("Código");
        codigo.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Puc, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Puc, String> conpuc) {

                return new SimpleStringProperty((conpuc.getValue().valueProperty().getValue().getCodigo()));

            }
        });
        TreeTableColumn nombre = new TreeTableColumn();
        nombre.setMinWidth(400);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Puc, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Puc, String> conpuc) {

                return new SimpleStringProperty((conpuc.getValue().valueProperty().getValue().getNombre()));

            }
        });
        deshabilitar.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Puc, Boolean>, ObservableValue<Boolean>>() {
        
            @Override
            public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<Puc, Boolean> param) {
                Puc deshab = param.getValue().getValue();
 
                SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(deshab.isDeshabilitar());
 
                // Note: singleCol.setOnEditCommit(): Not work for
                // CheckBoxTableCell.
 
                // When "Single?" column change.
                booleanProp.addListener(new ChangeListener<Boolean>() {
 
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                            Boolean newValue) {
                        deshab.setDeshabilitar(newValue);
                        EntidadesStatic.es_puc=deshab;
                        try {
                            CatalogoCuentasController.servicio_actualizar();
                            getPuc();
                        } catch (ParseException ex) {
                         }
                    }
                });
                return booleanProp;
            }
        });
         deshabilitar.setCellFactory(new Callback<TreeTableColumn<Puc, Boolean>,TreeTableCell<Puc, Boolean>>() {
            @Override
            public TreeTableCell<Puc, Boolean> call(TreeTableColumn<Puc, Boolean> p) {
                CheckBoxTreeTableCell<Puc, Boolean> cell = new CheckBoxTreeTableCell<Puc, Boolean>();
                cell.setAlignment(Pos.CENTER);
                return cell;
            }
        });
         deshabilitar.setMinWidth(200);
        gpconpuc.autosize();
        tv_conpuc.getColumns().addAll(codigo, nombre,deshabilitar);
        tv_conpuc.setEditable(true);
        tv_conpuc.autosize();
        tv_conpuc.setContextMenu(contextMenu);
        tv_conpuc.setColumnResizePolicy(TreeTableView.CONSTRAINED_RESIZE_POLICY);
        gpconpuc.add(tv_conpuc, 0, 1, 6, 1);
    }

    public void ImportarCatalogo() throws IOException, ParseException {
        String codigo = "";
        String codigopadre = "";
        String concepto = "";
        Puc padre = null;
        Puc CuentapadreAll = null;
        CuentapadreAll = new Puc();
        EntidadesStatic.es_puc=null;
        EntidadesStatic.es_puc=new Puc();
        EntidadesStatic.es_puc.setCodigo("0");
        EntidadesStatic.es_puc.setNombre("Catálogo de Cuentas");
        EntidadesStatic.es_puc.setTipo(0);
        CatalogoCuentasController.servicio_crear();
        CuentapadreAll = EntidadesStatic.es_puc;
        for (int noreg = 1; noreg < 420; noreg++) {
            codigo = catalogo.getCellAt(0, noreg).getTextValue().trim();
            concepto = catalogo.getCellAt(1, noreg).getTextValue();
            EntidadesStatic.es_puc = null;
            EntidadesStatic.es_puc = new Puc();
            EntidadesStatic.es_puc.setCodigo(codigo);
            EntidadesStatic.es_puc.setNombre(concepto);
            EntidadesStatic.es_puc.setTipo(Integer.valueOf(codigo.substring(0, 1)));

            if (codigo.length() == 2) {
                codigopadre = codigo.substring(0, 1);
            } else {
                if (codigo.length() == 4) {
                    codigopadre = codigo.substring(0, 2);
                } else {
                    if (codigo.length() == 6) {
                        codigopadre = codigo.substring(0, 4);
                    }
                    else {
                    if (codigo.length() == 8) {
                        codigopadre = codigo.substring(0, 6);
                    }
                }
                }

            }
            if (codigo.length() > 1) {
                padre = CatalogoCuentasController.findConPuc(codigopadre,cb_vercuentas.getSelectionModel().getSelectedIndex());
               EntidadesStatic.es_puc.setConpuc_id(padre);
            } else {
                EntidadesStatic.es_puc.setConpuc_id(CuentapadreAll);
            }
            CatalogoCuentasController.servicio_crear();
        }

    }

    private void delete() throws ParseException {
       CatalogoCuentasController.servicio_eliminar();
        getPuc();
    }

    private static void expandTreeView(TreeItem<?> item) {
        if (item != null && !item.isLeaf()) {
            item.setExpanded(true);
            for (TreeItem<?> child : item.getChildren()) {
                expandTreeView(child);
            }
        }
    }
  public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {
        try {
            cb_vercuentas.getSelectionModel().select(newValue.intValue());
           getPuc();
        } catch (ParseException ex) {
          }
    }  
}