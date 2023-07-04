
package modulos.parametrizacion;


import controls.SearchBox;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import jenum.EstadoRegistroEnum;
import menu.MenuMain;
import menu.Sample;
import model.Categoria;
import model.FacturaItem;
import model.Producto;
import model.ProductoMateriaPrima;
import modulos.FindBy.FindProduct;
import rest.ProductoJerseyClient;
import rest.ProductoMateriaPrimaJerseyClient;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FormProductos extends Sample {
    private TableView tableView ;
    private TableView tableMateriaPrima ;
    private int m_IEstRec;
    private ProductoJerseyClient m_sProdJerseyClient;
    private ProductoMateriaPrimaJerseyClient productoMateriaPrimaJerseyClient;
    private Vector<Long>m_vlIdCat=new Vector<Long>();
    private FileChooser fileChooser = new FileChooser();
    private Stage stage;
    private ChoiceBox m_CBCategoria;
    private Label m_LCategoria;
    private Label m_LNombre;
    private TextField m_TFNombre;
    private Label m_LCodigo;
    private TextField m_TFCodigo;
    private Label m_LCosto;
    private TextField m_TFCosto;
    private Label m_LPrecio;
    private TextField m_TFPrecio;
    private Label m_LDescrip;
    private TextArea m_TFDescrip;
    private Label L_MateriaPrima;
    private TextField Tf_MateriaPrima;
    private Label L_CantidadGastada;
    private TextField Tf_CantidaGastada;
    private Label L_UnidadMedida;
    private ChoiceBox Cb_UnidadMedida;
    private Label L_EsMateriaPrima;
    private CheckBox Ch_EsMateriaPrima;
    private Button bLoadImage;
    private ImageView img;
    private Button save;
    private Button nuevo;
    private Label success;
    private final Desktop desktop = Desktop.getDesktop();
    private Image Loadimg;
    private File file;
    private WritableImage wi;
    private BufferedImage bi;
    private ObservableList oList;
    private Producto productos;
    private Producto materiaprima;
    private Pane myPane;
    private GridPane gridpane;
    private GridPane gridpaneRoot;
    private GridPane gridpaneTable; 
    private HBox hbox;
    private HBox hboxButton;
    private SearchBox m_SearchBox;
    private Boolean m_BCanSave;
    private ObservableList m_ODatosProducto = FXCollections.observableArrayList();
    private FindProduct findProduct;
     private  ObservableList  Ol_MateriaPrimaItems = FXCollections.observableArrayList();
    public FormProductos() throws IOException {
        productos=new Producto();
        materiaprima=new Producto();
        findProduct=new FindProduct(true);
        Thread th = new Thread(task);
            th.setDaemon(false);
            th.start();
        img=new ImageView();
        HBox hbox=new HBox();
        img.setFitHeight(100);
        img.setFitWidth(100);
        hbox.setStyle(  "    -fx-border-color: -fx-focus-color;\n" +
                     "    -fx-border-insets: -1.4, 0;\n" +
                     "    -fx-border-radius: 2.4, 2;\n" +
                     "    -fx-border-width: 1.4;");
        hbox.getChildren().clear();
        hbox.getChildren().add(img);
        m_BCanSave=true;
        tableView = new TableView();
        tableMateriaPrima = new TableView();
        m_IEstRec=0;
        m_sProdJerseyClient=new ProductoJerseyClient();
        productoMateriaPrimaJerseyClient=new ProductoMateriaPrimaJerseyClient();
        myPane=new Pane();
        gridpane=new GridPane();
        gridpane.getStyleClass().add("background");
        gridpaneRoot=new GridPane();
        gridpaneRoot.getStyleClass().add("background");
        gridpaneRoot.setVgap(3);
        gridpaneTable=new GridPane();
       
        hboxButton=new HBox(10);
        //getChildren().add(myPane);
        L_MateriaPrima=new Label("Materia Prima");
        Tf_MateriaPrima=new TextField();
        Tf_MateriaPrima.setMaxWidth(150);
        L_CantidadGastada=new Label("Cantidad Necesaria");
        Tf_CantidaGastada=new TextField();
        Tf_CantidaGastada.setMaxWidth(150);
        L_UnidadMedida=new Label("Unidad de Medida");
        Cb_UnidadMedida=new ChoiceBox();
        Cb_UnidadMedida.getItems().addAll("Gramos","Cm3");
        Cb_UnidadMedida.setMaxWidth(150);
        m_CBCategoria=new ChoiceBox();
        m_CBCategoria.setMaxHeight(10);
        m_LCategoria=new Label("Categoria: ");
        m_LCategoria.setUnderline(true);
        m_LCategoria.setMinWidth(80);
        m_LNombre=new Label("Nombre: ");
        m_LNombre.setUnderline(true);
        m_TFNombre=new TextField();
        m_LCodigo=new Label("Codigo: ");
        m_LCodigo.setUnderline(true);
        m_TFCodigo=new TextField();
        m_LCosto=new Label("Costo: ");
        m_TFCosto=new TextField();
        m_LPrecio=new Label("Precio: ");
        m_TFPrecio=new TextField();
        m_LPrecio.setUnderline(true);
        m_LDescrip =new Label("Descripción: ");
        m_LDescrip.setMinWidth(120);
        m_TFDescrip=new TextArea();
        L_EsMateriaPrima= new Label("Es Materia Prima: ");
        Ch_EsMateriaPrima=new CheckBox();
        ImageView imageView=new ImageView("/images/bs_save.gif");
        save =new Button("Guardar",imageView);
        imageView=null;
        imageView=new ImageView("/images/bs_newRecord.gif");
        nuevo =new Button("Nuevo",imageView);
        imageView=null;
        imageView=new ImageView("/images/bs_image.gif");
        bLoadImage =new Button("Cargar",imageView);
        success=new Label();
        hboxButton.getChildren().addAll(save,nuevo);
        
        m_SearchBox=new SearchBox("SearchBox", "Buscar Por: Nombre o No Ident");
        m_SearchBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                
                    ///System.out.println("SearchBox.handle DOWN");
                LoadTable();
                
            }
        });
        // m_TFNombre.getStyleClass().add("text-input");
       // m_TFNombre.setMaxWidth(80);
          Text _tTexthebreo=new Text("       Productos      ");
       _tTexthebreo.setFill(Color.WHITESMOKE);
       _tTexthebreo.textAlignmentProperty().setValue(TextAlignment.CENTER);
       _tTexthebreo.setFont(Font.font("WP Hebrew David", FontWeight.BOLD,32));
       GridPane.setHalignment(_tTexthebreo, HPos.CENTER);
        gridpane.setHgap(5);
       gridpane.setVgap(5);
      
 
       // GridPane.setValignment(gridpaneRoot, VPos.TOP);
       //GridPane.setHalignment(success, HPos.CENTER);
       GridPane.setHalignment(bLoadImage, HPos.CENTER);
    
       gridpane.add(m_LCategoria, 0, 1);
       gridpane.add(m_CBCategoria, 1, 1);
       gridpane.add(hbox, 3, 1,1,3);
       gridpane.add(bLoadImage, 3, 4);
       gridpane.add(m_LNombre, 0, 2);
       gridpane.add(m_TFNombre, 1, 2);
       gridpane.add(m_LCodigo, 0, 3);
       gridpane.add(m_TFCodigo, 1, 3);
       gridpane.add(m_LCosto, 0, 4);
       gridpane.add(m_TFCosto, 1, 4);
       gridpane.add(m_LPrecio, 0, 5);
       gridpane.add(m_TFPrecio, 1, 5);
       gridpane.add(m_LDescrip, 0, 6);
       gridpane.add(m_TFDescrip, 1, 6);
       gridpane.add(L_EsMateriaPrima, 0, 7);
       gridpane.add(Ch_EsMateriaPrima, 1, 7);
       gridpane.add(success, 0, 8,4,1);
       gridpane.add(hboxButton, 1, 9,4,1);
       gridpaneRoot.setMaxSize(800, 550);
       gridpane.setMaxSize(800, 550);
       
     nuevo.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                nuevoProducto();
            }});
      save.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                try {
                    saveProducto();
                } catch (IOException ex) {
                    Logger.getLogger(FormProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }});
      bLoadImage.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                LoadImage();
            }});
       Tf_MateriaPrima.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                
                    findProduct.show();
                    findProduct.getTf_Search().requestFocus();
               
            }});
        findProduct.getStage().setOnHidden(new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent windowEvent) {
                if (windowEvent.getEventType() == WindowEvent.WINDOW_HIDDEN ) {
                    if(findProduct.getProducto()!=null)
                    {
                  Tf_MateriaPrima.setText(findProduct.getProducto().getNombre());
                  Tf_CantidaGastada.requestFocus();
                    }
                }
                }
        });
        
        //validacion solo numeros
        Tf_CantidaGastada.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                  
                try{
                    Float.valueOf(Tf_CantidaGastada.getText());
                     if (keyEvent.getCode() == KeyCode.ENTER) {
                 addMateriaPrima();
                 Tf_MateriaPrima.requestFocus();
                }  
            if (keyEvent.getCode() == KeyCode.SPACE) {
                 Tf_MateriaPrima.requestFocus();
                }
            
               
                }  catch(Exception e)
                {
                   
                    keyEvent.consume(); 
                    Tf_CantidaGastada.backward();
                    Tf_CantidaGastada.deleteNextChar();
                    
                }
                }
        });
        m_TFCosto.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                  
                try{
                    Float.valueOf(m_TFCosto.getText());
                    
               
                }  catch(Exception e)
                {
                    keyEvent.consume(); 
                    m_TFCosto.backward();
                    m_TFCosto.deleteNextChar();
                    
                }
                }
        });
         m_TFPrecio.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                  
                try{
                    Float.valueOf(m_TFPrecio.getText());
                    
               
                }  catch(Exception e)
                {
                    keyEvent.consume(); 
                    m_TFPrecio.backward();
                    m_TFPrecio.deleteNextChar();
                    
                }
                }
        });
      //Crear Tabla
       crearTableView();
        //agregamos el formulario y la tabla
       GridPane.setColumnSpan(tableMateriaPrima, 3);
       GridPane.setColumnSpan(gridpane, 3);
       GridPane.setColumnSpan(gridpane, 3);
        gridpaneTable.add(m_SearchBox, 0, 2);
        gridpaneTable.add(tableView, 0, 4);
        gridpaneRoot.add(gridpane, 0, 0);
        gridpaneRoot.add(gridpaneTable, 3, 0);
        gridpaneRoot.add(L_MateriaPrima,0,1);
        
        gridpaneRoot.add(L_UnidadMedida,1,1);
        gridpaneRoot.add(L_CantidadGastada,2,1);
        gridpaneRoot.add(Tf_MateriaPrima,0,2);
        gridpaneRoot.add(Cb_UnidadMedida,1,2);
        gridpaneRoot.add(Tf_CantidaGastada,2,2);
        gridpaneRoot.add(tableMateriaPrima, 0, 3);
        gridpaneRoot.setHgap(7);
        fileChooser.setTitle("Abrir Recursos de Imagen");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
            );
         
            m_CBCategoria.getItems().clear();
         //   m_CBCategoria.getStyleClass().add("choice-box");
                int i=0;
                if(MenuMain.getM_oDatosCategoria().size()>0)
                {
                   for(Categoria categoria:(Categoria[])MenuMain.getM_oDatosCategoria().get(0))
                   {   
                       m_vlIdCat.add(i,categoria.getId());
                       m_CBCategoria.getItems().add(i,categoria.getM_NombreCat());
                      
       
                   }
                }
         
        getChildren().add(gridpaneRoot);
       
    }
    public  static Node createIconContent() throws IOException {
         ImageView imageView = new ImageView(new Image("/modulos/parametrizacion/Producto.png"));
            imageView.setFitHeight(80);
            imageView.setFitWidth(90);
          javafx.scene.Group g =
                new javafx.scene.Group(imageView);
   
        return g;
    }
    
     public void saveProducto() throws FileNotFoundException, IOException {
      boolean _IsGuardado=true;
       
       
     
        productos.setNombre(m_TFNombre.getText());
        productos.setCodigo(m_TFCodigo.getText());
        productos.setEsmateriaprima(Ch_EsMateriaPrima.isSelected());
        try{
         productos.setCosto(BigDecimal.valueOf(Double.valueOf(m_TFCosto.getText())));
         productos.setPrecio(BigDecimal.valueOf(Double.valueOf(m_TFPrecio.getText())));
        }catch(Exception e)
        {
            productos.setCosto(BigDecimal.ZERO); 
            productos.setPrecio(BigDecimal.ZERO);
            
        }
        productos.setDescrip(m_TFDescrip.getText());
       int index=m_CBCategoria.getSelectionModel().getSelectedIndex();
       if(index>=0)
       {
        productos.setCategoria(new Categoria(m_vlIdCat.get(index)));    
       }
       
        //productos.setTipo(String.valueOf(index));
        //guardando imagen png o jpg en bases de datos
        byte[]data;
        InputStream in;
        try{
        in = new FileInputStream(file);
        data=new byte[in.available()];
	in.read(data);
             productos.setImg(data);
        }catch(Exception e)
        {
           
        }
        
           
        if(m_CBCategoria.getSelectionModel().getSelectedIndex()<0)
        {
            m_CBCategoria.setStyle("-fx-background-color:#ff1600");
            m_BCanSave=false;
        }
        if(m_TFPrecio.getText().equals(""))
        {
            m_TFPrecio.setStyle("-fx-background-color:#ff1600");
            m_BCanSave=false;
        }
        if(m_TFNombre.getText().equals(""))
        {
            m_TFNombre.setStyle("-fx-background-color:#ff1600");
            m_BCanSave=false;
        }
        if(m_TFCodigo.getText().equals(""))
        {
            m_TFCodigo.setStyle("-fx-background-color:#ff1600");
            m_BCanSave=false;
        }
         if(m_BCanSave)
         {
             
             save.setDisable(true);
             nuevo.setDisable(false);
          try{
              switch(m_IEstRec){
                  case 0:
                      m_sProdJerseyClient.create_XML(productos);
                      break;
                  case 1:
                      m_sProdJerseyClient.edit_XML(productos,productos.getId().toString());
                      break;
                      
              }
              
              
       
       }catch(Exception e){
           _IsGuardado=false;
           
       }
     success.setOpacity(0);
        if(_IsGuardado)
        {
        success.setText(m_IEstRec==EstadoRegistroEnum.NUEVO.ordinal()?"Registro Guardado":"Registro Modificado");
        }
        else
        {
            success.setText("Error al Almacenar");
            
        }
         }
         else
         {
             m_BCanSave=true;
             success.setText("Los Campos de Texto en Rojo son Obligatorios ");
         }
        animateMessage();
        
   MenuMain.LoadDataInit();
    }
      public void nuevoProducto() {
       productos = new Producto();
       m_IEstRec=0;
       save.setDisable(false);
       nuevo.setDisable(true);
       m_TFNombre.setText("");
       m_TFCodigo.setText("");
       m_TFCosto.setText("");
       m_TFPrecio.setText("");
       m_TFDescrip.setText("");
       Ch_EsMateriaPrima.setSelected(false);
       img.setImage(null);
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
 
            @Override
            public void handle(ActionEvent event) {
                success.setText("");
                m_TFNombre.setStyle(null);
                m_TFNombre.getStyleClass().add("text-field");
                m_TFCodigo.setStyle(null);
                m_TFCodigo.getStyleClass().add("text-field");
                m_TFPrecio.setStyle(null);
                m_TFPrecio.getStyleClass().add("text-field");
            }
        });
        
           
       
    }
    public void fLoadRecord() throws IOException{
        m_IEstRec=1;
        if(save.isDisable())
         save.setDisable(false);
         productos= (Producto) tableView.getFocusModel().getFocusedItem();
         m_TFNombre.setText(productos.getNombre());
         m_TFCodigo.setText(productos.getCodigo());
         m_TFCosto.setText(productos.getCosto().toString());
         m_TFPrecio.setText(productos.getPrecio().toString());
         m_TFDescrip.setText(productos.getDescrip());
         Ch_EsMateriaPrima.setSelected(productos.isEsmateriaprima());
         try
         {
         InputStream in = new ByteArrayInputStream(productos.getImg());
          bi = ImageIO.read(in);
          wi=new WritableImage(1,1);
          wi=SwingFXUtils.toFXImage(bi, wi);
          //img=new ImageView();
          Loadimg=wi;
          img.setImage(Loadimg);
          System.out.println("size img:"+productos.getImg().length);
         }catch(Exception e)
         {
             
         }
          int index=m_vlIdCat.indexOf(productos.getCategoria().getId());
          m_CBCategoria.getSelectionModel().select(index);
         itemsmateriaprima();      
    }
    
   public void LoadImage() {
        
       
        file=fileChooser.showOpenDialog(stage);
         
        if(file!=null)
        {
            byte[]img2;
            try {
                   bi = ImageIO.read(file);
                  
                  wi=SwingFXUtils.toFXImage(bi, wi);
                  Loadimg=wi;
                } catch (IOException ex) {
                    System.out.println(ex.getMessage());
                }
        }
    
        img.setImage(Loadimg);
    }
     
   private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                FXMLProductosController.class.getName()).log(
                    Level.SEVERE, null, ex
                );
        }
    }  
 
   public void LoadTable()
   {            try{
                Producto[] _ListProductos=  m_sProdJerseyClient.findCriteria_XML(Producto[].class, "0", "21", m_SearchBox.getTextBox().getText());
                m_ODatosProducto.clear();
                m_ODatosProducto.addAll(_ListProductos);
   }catch(Exception e)
   {
       
   }
   }
   private void crearTableView()
   {
       
       //creamos tabla de prodcutos
        TableColumn firstNameCol = new TableColumn();
        firstNameCol.setPrefWidth(150);
        firstNameCol.setText("Nombre");
        firstNameCol.setCellValueFactory(new PropertyValueFactory("nombre"));
       
        TableColumn lastNameCol = new TableColumn();
        lastNameCol.setPrefWidth(150);
       //lastNameCol.getStyleClass().add("table-header");
        Text _TNoIden=new Text("Codigo");
        lastNameCol.setText(_TNoIden.getText());
        lastNameCol.setCellValueFactory(new PropertyValueFactory("codigo"));
        
 
     
        tableView.setItems(m_ODatosProducto);
        tableView.setEditable(true);
        tableView.getColumns().addAll(firstNameCol, lastNameCol);
        tableView.setMaxHeight(250);
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    try {
                        fLoadRecord();
                    } catch (IOException ex) {
                        Logger.getLogger(FormProductos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                });
        tableView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                 if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.UP) 
                 {
                     try {
                         fLoadRecord();
                     } catch (IOException ex) {
                         Logger.getLogger(FormProductos.class.getName()).log(Level.SEVERE, null, ex);
                     }
                 }
            }
        });
        LoadTable();
        //creamos tabla de materia prima por prodcuto
        TableColumn MateriaPrima = new TableColumn();
        MateriaPrima.setMinWidth(150);
        MateriaPrima.setText("Materia Prima");
        MateriaPrima.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductoMateriaPrima, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ProductoMateriaPrima, String> productoMateriaPrima) {
                return new SimpleStringProperty(productoMateriaPrima.getValue().getMateriaprima().getNombre());
            }
            });
       
        TableColumn CantidadNecesaria = new TableColumn();
        CantidadNecesaria.setMinWidth(130);
        CantidadNecesaria.setText("Cantidad Necesaria");
        CantidadNecesaria.setCellValueFactory(new PropertyValueFactory("cantidad_necesaria"));
        
        TableColumn UnidadMedida = new TableColumn();
        UnidadMedida.setMinWidth(130);
        UnidadMedida.setText("Unidad de Medida");
        UnidadMedida.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ProductoMateriaPrima, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ProductoMateriaPrima, String> productoMateriaPrima) {
                return new SimpleStringProperty(productoMateriaPrima.getValue().getUnidad_medida()==0?"Gramos":"Cm3");
            }
            });
       
 
        
      
      tableMateriaPrima.setMaxHeight(300);
      tableMateriaPrima.setMaxWidth(450);
      tableMateriaPrima.setEditable(true);
      tableMateriaPrima.getColumns().addAll(MateriaPrima, CantidadNecesaria,UnidadMedida );
      
       
   }
   Task<Void> task = new Task<Void>() {
         @Override protected Void call() throws Exception {
            
    //stage finproduct
      findProduct.loadData();
             // Return null at the end of a Task of type Void
             return null;
         }
     };
   
  public void addMateriaPrima()
{ 
    //cambiamos las columnas a otro origen de datos
    //verificamos que solo se ejecute solo una vez
    if(productos.isEsmateriaprima())
    {
        success.setText("Una materia prima no puede contener materia prima");
        animateMessage();
    }
   ProductoMateriaPrima productoMateriaPrima=new ProductoMateriaPrima();
   productoMateriaPrima.setProducto(productos);
   productoMateriaPrima.setMateriaprima(findProduct.getProducto());
   productoMateriaPrima.setCantidad_necesaria(Float.valueOf(Tf_CantidaGastada.getText()));
   productoMateriaPrima.setUnidad_medida(Cb_UnidadMedida.getSelectionModel().getSelectedIndex());//0--gramos 1-- cm3
  //  productos.addMateriaPrima(findProduct.getProducto(), Float.valueOf(Tf_CantidaGastada.getText()),Cb_UnidadMedida.getSelectionModel().getSelectedIndex());
  productoMateriaPrimaJerseyClient.create_JSON(productoMateriaPrima);
    itemsmateriaprima();
    //Cargamos la tabla con el nuevo item
   // 
  
}
  private void itemsmateriaprima()
  {
        Ol_MateriaPrimaItems.clear();
        Ol_MateriaPrimaItems.addAll(productoMateriaPrimaJerseyClient.findMateriasPrimas_XML(ProductoMateriaPrima[].class, productos.getId().toString()));
        tableMateriaPrima.setItems(Ol_MateriaPrimaItems);
  }
}