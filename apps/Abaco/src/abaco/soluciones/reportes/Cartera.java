/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.soluciones.reportes;
import abaco.util.UtilDate;
import abaco.AbacoApp;
import static abaco.AbacoApp.cb_temas;
import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import abaco.controlador.ProductoControllerClient;
import com.aquafx_project.AquaFx;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.print.PrintException;
import jfxtras.styles.jmetro8.JMetro;
import jxl.Workbook;
import jxl.write.WritableCellFeatures;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import model.ConComprobanteIngreso;
import model.ConPuc;
import model.Factura;
import org.aerofx.AeroFX;


/**
 *
 * @author karolyani
 */
public class Cartera extends Application {
     
    private TableView tv_generic ;
    private ObservableList ol_generic;
    
   
    private GridPane gridpane;
    private GridPane gridpaneRoot;
    
    ProductoControllerClient productoControllerClient;
    private Text T_Totales;
    private TextField T_Valor_total_todo;
    private TextField T_Cant_total_todo;
    
    private Label L_Cliente;
    private TextField Tf_cliente;
    private TextField Tf_total=new TextField();
    private Button B_Exportar;
    private Button bu_abonogeneral;
    private TitledPane tp_generic;
    private ContextMenu contextMenu;
    private MenuItem  itemabonos;
    private ImageView img;
    private  String appClassAbonos;
    private  Class clzAbonos ;
    private  Object appAbonos ;
    private  Parent parentAbonos;
    private  Stage stageAbonos= new Stage(StageStyle.DECORATED);
    private  Stage stageabonosgeneral= new Stage(StageStyle.DECORATED);
    private Label la_valorabonogenral=new Label("Valor");
    private TextField valorabonogeneral=new TextField("0");
    Scene sceneFactura =null;
    private List<ConPuc> li_conPucs;
    private  Task<Void>   task_abonogeneral;
    private  Thread  thimport_abonogeneral;
    private Label [] labels;
    private ProgressBar[] pbs;
    private ProgressIndicator[] pins;
    private HBox hbs [];
    private ProgressBar pb ;
    private ProgressIndicator pi;
     GridPane gp=new GridPane();
    
    Parent p_abonogenral = new Group(la_valorabonogenral,valorabonogeneral);
    StackPane stackPane;
    public Parent createContent() throws IOException {
      
        gp.setVisible(false);
      
        stageabonosgeneral.setTitle("Abono General Facturas");
        contextMenu=null;
         contextMenu=new ContextMenu(); 
         img=null;
         img=new ImageView ("/images/abonos.png");
         img.setFitHeight(20);
         img.setFitWidth(20);
         itemabonos=new MenuItem("Abonos a factura",img);
         itemabonos.setOnAction(e->{
         
           try{
         checkregistroexistente();
         showAbonosFactura();
           }catch(Exception x)
           {
               x.printStackTrace();
           }
       });
         contextMenu.getItems().addAll(itemabonos);
          productoControllerClient=new ProductoControllerClient();
          L_Cliente=new Label("Cliente: ");
          Tf_cliente=new TextField();
          Tf_cliente.setMinWidth(200);
          Tf_cliente.setPromptText("Encontrar por código o  nombre ");
          valorabonogeneral.setMinWidth(300);
          Tf_cliente.textProperty().addListener((observable, oldValue, newValue) -> {
           getRecords();
});
          ImageView imageView;
         tp_generic=new TitledPane();
           tp_generic.setText("Cartera");
        imageView=null;
        imageView=new ImageView("/images/excel.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        B_Exportar=new Button("Exportar",imageView);
         B_Exportar.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                try {
                    exportar_excel();
                } catch (IOException ex) {
                    Logger.getLogger(VentasPorProducto.class.getName()).log(Level.SEVERE, null, ex);
                } catch (WriteException ex) {
                    Logger.getLogger(VentasPorProducto.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ParseException ex) {
                    Logger.getLogger(Cartera.class.getName()).log(Level.SEVERE, null, ex);
                }
            }});
       
        imageView=null;
        imageView=new ImageView("/images/capitado.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);
        bu_abonogeneral=new Button("",imageView);
      
       bu_abonogeneral.setOnAction(e->{
            try {
               showAbonogeneral();
            } catch(Exception e2)
            {
                e2.printStackTrace();
            }
       });
       valorabonogeneral.setOnAction(e->{
            try {
                calcularabonogeneral();
            } catch (ParseException ex) {
                Logger.getLogger(Cartera.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Cartera.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Cartera.class.getName()).log(Level.SEVERE, null, ex);
            } catch (PrintException ex) {
                Logger.getLogger(Cartera.class.getName()).log(Level.SEVERE, null, ex);
            }
       });
        T_Totales=new Text("Total: ");
         T_Totales.setFill(Color.BLACK);
          T_Totales.textAlignmentProperty().setValue(TextAlignment.CENTER);
          T_Totales.setFont(Font.font("ARIAL", FontWeight.BOLD,20));
        
          T_Valor_total_todo=new TextField();
          T_Valor_total_todo.setMaxWidth(130);
          T_Cant_total_todo=new TextField();
          T_Cant_total_todo.setMaxWidth(130);
         Tf_total.setMaxWidth(150);
        ol_generic= FXCollections.observableArrayList();
        gridpane=new GridPane();
        gridpaneRoot=new GridPane();
      //  gridpane.getStyleClass().add("category-page");
        gridpane.setHgap(5);
        gridpane.setVgap(5);
        
        gridpaneRoot.alignmentProperty().setValue(Pos.TOP_CENTER);
        GridPane.setValignment(gridpaneRoot, VPos.TOP);
        //Creamos la tabla para ver existencias
       stageAbonos.setOnHiding(e->{
           getRecords();
       });
        tv_generic=new TableView();
        tv_generic.setContextMenu(contextMenu);
        TableColumn nofactura = new TableColumn();
        nofactura.setMinWidth(130);
        nofactura.setText("N° Factura");
        nofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getNofacturacerosizquierda());
                            
            }});
        
        TableColumn noident = new TableColumn();
        noident.setMinWidth(110);
        noident.setText("N° Ident");
        noident.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getCustomer().getNo_ident());
                            
            }});
       
       TableColumn nombre = new TableColumn();
        nombre.setMinWidth(150);
        nombre.setText("Nombre");
        nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getCustomer().getNombre());
                            
            }});
         TableColumn celular = new TableColumn();
        celular.setMinWidth(100);
        celular.setText("Celular");
        celular.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getCustomer().getCelular());
                            
            }});
        
         TableColumn valor_factura = new TableColumn();
         valor_factura.setMinWidth(120);
         valor_factura.setText("V/Factura");
         valor_factura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getTotalAmount().toString());
                            
            }});
         TableColumn abonos_factura = new TableColumn();
         abonos_factura.setMinWidth(120);
         abonos_factura.setText("T/Abonos");
         abonos_factura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getValor_abonos().toString());
                            
            }});
         TableColumn saldo = new TableColumn();
         saldo.setMinWidth(90);
         saldo.setText("Saldo");
         saldo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty((factura.getValue().getTotalAmount().toBigInteger().subtract(factura.getValue().getValor_abonos().toBigInteger())).toString());
                            
            }});
         TableColumn fecha = new TableColumn();
         fecha.setMinWidth(100);
         fecha.setText("Fecha");
         fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(abaco.util.UtilDate.formatodiamesyear(factura.getValue().getFacturaDate()));
                            
            }});
          TableColumn tiempoendias = new TableColumn();
         tiempoendias.setMinWidth(110);
         tiempoendias.setText("D/Mora");
         tiempoendias.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(String.valueOf((new Date().getTime()-factura.getValue().getFacturaDate().getTime())/86400000));
                            
            }});
 
      
        tv_generic.setMinHeight(500);
        tv_generic.setMaxHeight(500);
        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_generic.getColumns().addAll(nofactura,noident,nombre,celular,valor_factura,abonos_factura,saldo,fecha,tiempoendias );
        
        gridpane.add(L_Cliente, 0, 0);
        gridpane.add(Tf_cliente, 1, 0);
        gridpane.add(B_Exportar, 2, 0);
        gridpane.add(bu_abonogeneral, 3, 0);
       
        Tf_cliente.setAlignment(Pos.CENTER_LEFT);
        gridpane.add(tv_generic, 0, 1,4,1);
        gridpane.add(new Label("Total a Pagar:"), 2, 2,1,1);
        gridpane.add(Tf_total, 3, 2,1,1);
        GridPane.setHalignment(Tf_cliente, HPos.LEFT);
        GridPane.setHalignment(L_Cliente, HPos.CENTER);
     
        
       
       //Traer   datos desde la BD
        getRecords();
       
        gridpane.setMinSize(1050, 610);
       
             //agregamos el formulario y la tabla
     //   gridpane.getStyleClass().add("background");
        GridPane.setValignment(gridpaneRoot, VPos.TOP);
    //   gridpane.getStylesheets().add("/abaco/SofackarStylesCommon.css");
      StackPane stack=new StackPane();
        
          tp_generic.setContent(gridpane);
        stack.setAlignment(Pos.TOP_CENTER);
       
           stack.getChildren().addAll(tp_generic);
          switch(cb_temas.getSelectionModel().getSelectedIndex())
         {
             case 0: new JMetro(JMetro.Style.DARK).applyTheme(stack);
                      break;
             case 1: new JMetro(JMetro.Style.LIGHT).applyTheme(stack);
                      break;
             case 2: AeroFX.style();
                        AeroFX.styleAllAsGroupBox(gridpane);
                         break;  
             case 3: AquaFx.style();
                     AquaFx.setGroupBox(gridpane);
                     break;              
         }
        return stack;
       }
    public  static Node createIconContent() throws IOException {
         ImageView imageView = new ImageView(new Image("/modulos/inventarios/Inventario.png"));
            imageView.setFitHeight(80);
            imageView.setFitWidth(90);
          javafx.scene.Group g =
                new javafx.scene.Group(imageView);
   
        return g;
    }
 
 private void getRecords()
 {
        //colocamos los datos
     AbacoApp.li_facturas=AbacoApp.facturaControllerClient.cartera(Tf_cliente.getText());
       ol_generic.clear();
       ol_generic.addAll( AbacoApp.li_facturas.toArray());
       tv_generic.setItems(ol_generic);
       float cant_total=0;
       BigDecimal total=BigDecimal.ZERO;
      for(Factura f:  AbacoApp.li_facturas)
      {
          total=total.add(f.getTotalAmount().subtract(f.getValor_abonos()));
      }
      
      Tf_total.setText(String.valueOf(total.toBigInteger()));
      
 }
   @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }
public  void exportar_excel() throws IOException, WriteException, ParseException
  {
     
        String rutaArchivo = System.getProperty("user.home")+"/Cartera.xls"; 
        jxl.write.Label label;
        jxl.write.Number number;
        File archivoXLS = new File(rutaArchivo);
        if(archivoXLS.exists()) 
        {
            archivoXLS.delete(); 
            archivoXLS.createNewFile();
        }
        WritableWorkbook libro;
         FileOutputStream archivo = new FileOutputStream(archivoXLS);
        libro = Workbook.createWorkbook(archivo);
        WritableSheet hoja = libro.createSheet("Mi hoja de trabajo 1",0);
         int f=0;
         
                
         for(Factura factura: AbacoApp.facturaControllerClient.cartera(Tf_cliente.getText()))
         {
           
           
            
             
            
              if(f==0)
             {
                  
                
                 
                // CellType.LABEL;
                 WritableCellFeatures writableCellFeatures=new WritableCellFeatures();
                 writableCellFeatures.removeDataValidation();
                 label=null;
                 label=new jxl.write.Label(f, f, "N° Factura");
                 hoja.addCell(label);
              
               
               
                 
                 label=null;
                 label=new jxl.write.Label(1, f, "No Ident");
                 hoja.addCell(label);
                 
                 label=null;
                 label=new jxl.write.Label(2, f, "Nombre");
                 hoja.addCell(label);
               
              
                 label=null;
                 label=new jxl.write.Label(3, f, "Valor Factura");
                 hoja.addCell(label);
                 label=null;
                 label=new jxl.write.Label(4, f, "Valor Abonos");
                 hoja.addCell(label);
                 label=null;
                 label=new jxl.write.Label(5, f, "Valor Saldo");
                 hoja.addCell(label);
                 label=null;
                 label=new jxl.write.Label(6, f, "Fecha Elab Factura");
                 hoja.addCell(label);
                 label=null;
                 label=new jxl.write.Label(7, f, "Días de Mora");
                 hoja.addCell(label);
                  label=null;
                 label=new jxl.write.Label(8, f, "Celular");
                 hoja.addCell(label);
             }
               f++;
                 hoja.insertRow(f);
                 label=null;
                 label=new jxl.write.Label(0, f, factura.getNofacturacerosizquierda());
                 hoja.addCell(label);
              
                 
                 label=null;
                 label=new jxl.write.Label(1, f, factura.getCustomer().getNo_ident());
                 hoja.addCell(label);
                 
                 label=null;
                 label=new jxl.write.Label(2, f, factura.getCustomer().getNombre());
                 hoja.addCell(label);
                 
                 
                number=null;
                 number=new jxl.write.Number(3, f, factura.getTotalAmount().floatValue());
                 hoja.addCell(number);
               number=null;
                 number=new jxl.write.Number(4, f, factura.getValor_abonos().floatValue());
                 hoja.addCell(number);
             
                number=null;
                 number=new jxl.write.Number(5, f, (factura.getTotalAmount().subtract(factura.getValor_abonos())).floatValue());
                 hoja.addCell(number);
              
                 label=null;
                 label=new jxl.write.Label(6, f,UtilDate.formatodiamesyear2(factura.getFacturaDate()));
                 hoja.addCell(label);
                 
                 label=null;
                 label=new jxl.write.Label(7, f, String.valueOf(((new Date().getTime()-factura.getFacturaDate().getTime())/86400000)));
                 hoja.addCell(label);
                 label=null;
                 label=new jxl.write.Label(8, f, factura.getCustomer().getCelular());
                 hoja.addCell(label);
            
         }
         libro.write();
         libro.close();
         archivo.close();
         Desktop.getDesktop().open(archivoXLS);
    } 
private void checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
   {
       if ((tv_generic.getSelectionModel())!=null)
       {
           AbacoApp.s_factura=(Factura)tv_generic.getSelectionModel().getSelectedItem();
         
       }
   }
 private void showAbonosFactura() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
  {  
    appClassAbonos="abaco.soluciones.procesosadministrativos.AdminAbonosFacturaCliente";
    clzAbonos = Class.forName(appClassAbonos);
    appAbonos = clzAbonos.newInstance();
    parentAbonos=null;
    parentAbonos = (Parent) clzAbonos.getMethod("createContent").invoke(appAbonos);
    sceneFactura=null;
    sceneFactura=new Scene(parentAbonos, Color.TRANSPARENT);
      
        if (stageAbonos.isShowing())
       {
           stageAbonos.close();
       }      
        
        
//set scene to stage
                stageAbonos.sizeToScene();
                
              
                //center stage on screen
                stageAbonos.centerOnScreen();
                stageAbonos.setScene(sceneFactura);
                 
                //stage.setMaxWidth(550);
                //stage.setX(x);
               // stage.setY(y);
                //show the stage
                stageAbonos.show();
  }   
 private void showAbonogeneral() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
  {  
     
      gp.getChildren().clear();
      pb=null;
      pi=null;
  pb=new ProgressBar(0);
  pi=new ProgressIndicator(1);
  
  gp.addRow(0, pb,pi);
  gp.setVisible(false);
   p_abonogenral.setVisible(true); 
    stackPane=null;
      stackPane=new StackPane(p_abonogenral,gp);
    Scene sceneabonosgeneral=null;
    sceneabonosgeneral=new Scene(stackPane, Color.TRANSPARENT);
      
        if (stageabonosgeneral.isShowing())
       {
           stageabonosgeneral.close();
       }      

                stageabonosgeneral.sizeToScene();
          
                stageabonosgeneral.centerOnScreen();
                stageabonosgeneral.setScene(sceneabonosgeneral);
                 
             p_abonogenral.getStylesheets().add("/abaco/SofackarStylesCommon.css"); 
        p_abonogenral.getStyleClass().add("background");   
                stageabonosgeneral.show();
  }   
    /**
     * Java main for when running without JavaFX launcher
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    private void calcularabonogeneral() throws ParseException, InterruptedException, IOException, PrintException
    {
       p_abonogenral.setVisible(false);
       gp.setAlignment(Pos.CENTER);
       gp.setVisible(true);
       labels=null;
       pbs=null;
       pins=null;
       hbs=null;
       labels=new Label[AbacoApp.li_facturas.size()];
       pbs=new ProgressBar[AbacoApp.li_facturas.size()];
       pins=new ProgressIndicator[AbacoApp.li_facturas.size()];
       hbs=new HBox[AbacoApp.li_facturas.size()];
       
       task_abonogeneral=new Task<Void>() {
        
            @Override
            protected Void call() throws Exception {
        BigDecimal saldoabonos=BigDecimal.ZERO;
        BigDecimal valor=new BigDecimal(valorabonogeneral.getText().trim());
        double i=0;
     
        for(Factura f: AbacoApp.li_facturas)
        {
            
            pb.setProgress(i);
            pi.setProgress(i);
            i=i+(1.0/AbacoApp.li_facturas.size());
           saldoabonos=f.getTotalAmount().subtract(f.getValor_abonos());
              System.out.println("Progress->"+(1.0/AbacoApp.li_facturas.size()));
           if(saldoabonos.compareTo(BigDecimal.ZERO)==1)
           {
               if(valor.compareTo(BigDecimal.ZERO)==1)
               {
                   if(valor.compareTo(saldoabonos)==0 || valor.compareTo(saldoabonos)==1)
               {
                   AbacoApp.conComprobanteContabilidad=AbacoApp.conPucControllerClient.findConComprobanteContabilidad(Long.MIN_VALUE,1);
                   
                   AbacoApp.s_conComprobanteIngreso=new ConComprobanteIngreso();
                   AbacoApp.s_conComprobanteIngreso.setConComprobanteContabilidad(AbacoApp.conComprobanteContabilidad);
                   AbacoApp.s_conComprobanteIngreso.setConcepto("Abono a Factura N° "+f.getNofacturacerosizquierda());
                   AbacoApp.s_conComprobanteIngreso.setValor(saldoabonos);
                   AbacoApp.s_conComprobanteIngreso.setFechaelaboracion(new Date());
                   AbacoApp.s_conComprobanteIngreso=AbacoApp.conComprobanteIngresoControllerClient.edit(AbacoApp.s_conComprobanteIngreso,AbacoApp.s_factura);
                   AddDetalle(); 
                   AbacoApp.conComprobanteContabilidad=AbacoApp.conPucControllerClient.save(AbacoApp.conComprobanteContabilidad);
                   f.addAbono(AbacoApp.s_conComprobanteIngreso);
                   AbacoApp.facturaControllerClient.guardarFactura(f);
                   valor=valor.subtract(saldoabonos);
               }
               else
               {
                   AbacoApp.conComprobanteContabilidad=AbacoApp.conPucControllerClient.findConComprobanteContabilidad(Long.MIN_VALUE,1);
                   AbacoApp.s_conComprobanteIngreso=new ConComprobanteIngreso();
                   AbacoApp.s_conComprobanteIngreso.setConComprobanteContabilidad(AbacoApp.conComprobanteContabilidad);
                   AbacoApp.s_conComprobanteIngreso.setConcepto("Abono a Factura N° "+f.getNofacturacerosizquierda());
                   AbacoApp.s_conComprobanteIngreso.setValor(valor);
                   AbacoApp.s_conComprobanteIngreso.setFechaelaboracion(new Date());
                   AbacoApp.s_conComprobanteIngreso=AbacoApp.conComprobanteIngresoControllerClient.edit(AbacoApp.s_conComprobanteIngreso,AbacoApp.s_factura);
                   AddDetalle(); 
                   AbacoApp.conComprobanteContabilidad=AbacoApp.conPucControllerClient.save(AbacoApp.conComprobanteContabilidad);
                   f.addAbono(AbacoApp.s_conComprobanteIngreso);
                   AbacoApp.facturaControllerClient.guardarFactura(f); 
                   valor=BigDecimal.ZERO;
               }
               
                   
               }
           }
            
        }   
           ImpresoraTermica it=new ImpresoraTermica();
           it.imprimirabonogeneral();
           getRecords();
           pb.setProgress(1);
           pi.setProgress(1);
          // stageabonosgeneral.close();
           return null;
           
             }
         };
            
             thimport_abonogeneral = new Thread(task_abonogeneral);
                
  
        thimport_abonogeneral.setDaemon(true);
        thimport_abonogeneral.start();
    }
     public void AddDetalle() throws ParseException, InterruptedException
  {
      AbacoApp.conComprobanteContabilidad.getDetallesComprobanteContabilidads().clear();
      // AbacoApp.conComprobanteContabilidad.getDetallesComprobanteContabilidads().clear();
       searchPuc("110505");
       AbacoApp.s_conPuc=li_conPucs.size()>0?li_conPucs.get(0):null;
      if(AbacoApp.s_conPuc!=null)
      {
       try{
        AbacoApp.conComprobanteContabilidad.addPuc(AbacoApp.s_conPuc,"Abona a N° Factura:"+AbacoApp.s_factura.getNofacturacerosizquierda(),new BigDecimal(valorabonogeneral.getText().trim()),true);
       }catch(Exception e)
                {
                 AbacoApp.conComprobanteContabilidad.addPuc(AbacoApp.s_conPuc,"Abona a N° Factura Antigua que no registra en el sistemas",new BigDecimal(valorabonogeneral.getText().trim()),true);
       
                }
     
      }
         searchPuc("130505");
       AbacoApp.s_conPuc=li_conPucs.size()>0?li_conPucs.get(0):null;
      if(AbacoApp.s_conPuc!=null)
      {
          try{  
            AbacoApp.conComprobanteContabilidad.addPuc(AbacoApp.s_conPuc,"Abona a N° Factura:"+AbacoApp.s_factura.getNofacturacerosizquierda(),new BigDecimal(valorabonogeneral.getText().trim()),false);
 }catch(Exception e)
                {
                 AbacoApp.conComprobanteContabilidad.addPuc(AbacoApp.s_conPuc,"Abona a N° Factura Antigua que no registra en el sistemas",new BigDecimal(valorabonogeneral.getText().trim()),true);
       
                }
      }
     
      
  }
    public void searchPuc(String searchString) throws ParseException {
     
      li_conPucs= AbacoApp.li_conpuc.stream().filter(line -> line.getCodigo().toUpperCase().equals(searchString))	//filters the line, equals to "mkyong"
	       .collect(Collectors.toList());	
      
   }
}   

