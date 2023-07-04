/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.soluciones.administrador;
import abaco.AbacoApp;
import static abaco.AbacoApp.cb_temas;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import model.Factura;
import abaco.controlador.FacturaControllerClient;
import com.aquafx_project.AquaFx;
import jfxtras.styles.jmetro8.JMetro;
import org.aerofx.AeroFX;
/**
 *
 * @author SIMboxDEV8
 */
public class AdminFacturas extends Application{
     private TableView Ta_Factura ;
     private ObservableList Ol_Factura;
     private GridPane gridpaneDatos;
     private GridPane gridpaneRoot;
     private GridPane gridpaneFechas;
     private GridPane gridpane;
     private DatePicker Dp_Date_from;
     private DatePicker Dp_Date_to;
     private List<Factura>  ar_OrdenGuia;
     private FacturaControllerClient facturaControllerClient;
     Factura factura;
     private Label L_Buscar;
     private TextField Tf_Buscar;
     private Label L_Date_from;
     private Label L_Date_to;
     private Button B_Eliminar;
     private Label success;
     private Button B_DetallesFacturas;
     private Button B_Pdf;
     
     final ObservableList Ol_FormaDePago=FXCollections.observableArrayList("Contado", "Credito", "Al Cobro");
     final ObservableList Ol_Ciudades=FXCollections.observableArrayList();
     
   
      //graficos barras
      public Parent createContent() throws ParseException
     {
          
        
           factura=new Factura();
           L_Buscar=new Label("Buscar ");
           success=new Label();
           success.getStyleClass().add("message");
           Tf_Buscar= new TextField();
           Dp_Date_from=new DatePicker();
           Dp_Date_to=new DatePicker();
           L_Date_from=new Label("Fecha de Vuelo Desde: ");
           L_Date_to=new Label("A: ");
           ImageView imageView;
           imageView=new ImageView("/images/pdf.jpg");
           imageView.setFitHeight(16);
           imageView.setFitWidth(16);
            B_Pdf=new Button("Imprimir Pdf",imageView);
            B_Pdf.setMaxWidth(130);
            imageView=new ImageView("/images/items.gif");
            imageView.setFitHeight(16);
            imageView.setFitWidth(16);
            B_DetallesFacturas=new Button("Detalles de Guia ",imageView);
            B_DetallesFacturas.setMaxWidth(130);
           Ol_Factura= FXCollections.observableArrayList();
           ar_OrdenGuia=null;
          
          facturaControllerClient=null;
          facturaControllerClient=new FacturaControllerClient();
          Dp_Date_from.setValue(LocalDate.now());
          Dp_Date_to.setValue(LocalDate.now());
          
          Thread th = new Thread(task);
          th.setDaemon(false);
          th.start();
         //Creamos la tabla
          imageView=null; 
          imageView=new ImageView("/images/delete.png");
          imageView.setFitHeight(16);
          imageView.setFitWidth(16);
          B_Eliminar=new Button("Eliminar",imageView);
          Dp_Date_from.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                
               
                try {
                    findOrdenesGuia();
                } catch (ParseException ex) {
                    Logger.getLogger(AdminFacturas.class.getName()).log(Level.SEVERE, null, ex);
                }
               
               
            }});
          Dp_Date_to.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                
               
                try {
                    findOrdenesGuia();
                } catch (ParseException ex) {
                    Logger.getLogger(AdminFacturas.class.getName()).log(Level.SEVERE, null, ex);
                }
               
               
            }});
       B_Pdf.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                
                try {
                    printFactura(1);
                } catch (IOException ex) {
                  
                }
               
            }}); 
       B_DetallesFacturas.setOnMouseClicked((MouseEvent ke) -> {
         
        });
        B_Eliminar.setOnMouseClicked((MouseEvent ke) -> {
               try {
                   deleteFactura();
               } catch (ParseException ex) {
                   Logger.getLogger(AdminFacturas.class.getName()).log(Level.SEVERE, null, ex);
               }
        });
        B_Eliminar.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
              
            }});
        
        Tf_Buscar.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                
                try {
                    findOrdenesGuia();
                } catch (ParseException ex) {
                    Logger.getLogger(AdminFacturas.class.getName()).log(Level.SEVERE, null, ex);
                }
                    
               
            }});
       
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Ta_Factura=new TableView();
        TableColumn Fecha = new TableColumn();
        Fecha.setMinWidth(70);
        Fecha.setText("Fecha");
        Fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(DateFormat.getDateInstance().format(factura.getValue().getFacturaDate()));
                            
            }
        });
   
        
 TableColumn NoIdent = new TableColumn();
        NoIdent.setMinWidth(100);
        NoIdent.setText("No. Ident");
        NoIdent.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getCustomer()==null?"":factura.getValue().getCustomer().getNo_ident());
                            
            }
        });
        NoIdent.setCellFactory(TextFieldTableCell.forTableColumn());
        NoIdent.setOnEditCommit(new EventHandler<CellEditEvent<Factura, String>>() {
                    @Override
                    public void handle(CellEditEvent<Factura, String> t) {
                      
                        ((Factura) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).getCustomer().setNo_ident(t.getNewValue());
                        String noident=(((Factura) t.getTableView().getItems().get(t.getTablePosition().getRow())).getCustomer().getNo_ident());
                        factura=((Factura) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                       
                       
                        ModifyFactura();
                    }
                });
        TableColumn Cliente = new TableColumn();
        Cliente.setMinWidth(200);
        Cliente.setText("Cliente");
        Cliente.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getCustomer()==null?"":factura.getValue().getCustomer().getNombre());
                            
            }
        });
        
       Cliente.setCellFactory(TextFieldTableCell.forTableColumn());
       Cliente.setOnEditCommit(new EventHandler<CellEditEvent<Factura, String>>() {
                    @Override
                    public void handle(CellEditEvent<Factura, String> t) {
                      
                        ((Factura) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).getCustomer().setNombre(t.getNewValue());
                        String nombre=(((Factura) t.getTableView().getItems().get(t.getTablePosition().getRow())).getCustomer().getNombre());
                        factura=((Factura) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                       
                       
                        ModifyFactura();
                    }
                });
       TableColumn NoGuia = new TableColumn();
        NoGuia.setMinWidth(100);
        NoGuia.setText("No. Factura");
        NoGuia.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getLastNumberNoFactura().getId().toString());
                            
            }
        });
        TableColumn Dir = new TableColumn();
        Dir.setMinWidth(100);
        Dir.setText("Dirección");
        Dir.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getCustomer()==null?"":factura.getValue().getCustomer().getDir1());
                            
            }
        });
        Dir.setCellFactory(TextFieldTableCell.forTableColumn());
        Dir.setOnEditCommit(new EventHandler<CellEditEvent<Factura, String>>() {
                    @Override
                    public void handle(CellEditEvent<Factura, String> t) {
                      
                        ((Factura) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).getCustomer().setDir1(t.getNewValue());
                        String dir=(((Factura) t.getTableView().getItems().get(t.getTablePosition().getRow())).getCustomer().getDir1());
                        factura=((Factura) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                       
                       
                        ModifyFactura();
                    }
                });
        
        TableColumn Tel = new TableColumn();
        Tel.setMinWidth(100);
        Tel.setText("Telefono");
        Tel.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getCustomer()==null?"":factura.getValue().getCustomer().getCelular());
                            
            }
        });
        Tel.setCellFactory(TextFieldTableCell.forTableColumn());
        Tel.setOnEditCommit(new EventHandler<CellEditEvent<Factura, String>>() {
                    @Override
                    public void handle(CellEditEvent<Factura, String> t) {
                      
                        ((Factura) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).getCustomer().setCelular(t.getNewValue());
                        String dir=(((Factura) t.getTableView().getItems().get(t.getTablePosition().getRow())).getCustomer().getDir1());
                        factura=((Factura) t.getTableView().getItems().get(t.getTablePosition().getRow()));
                       
                       
                        ModifyFactura();
                    }
                });
       
       TableColumn FormaPago = new TableColumn();
        FormaPago.setMinWidth(100);
        FormaPago.setText("Forma de Pago");
        FormaPago.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getCredito()?"Credito":"Contado");
                            
            }
        });
       
FormaPago.setCellFactory(ChoiceBoxTableCell.forTableColumn(Ol_FormaDePago));
 FormaPago.setOnEditCommit(  new EventHandler<CellEditEvent<Factura, String>>() {
                    @Override
                    public void handle(CellEditEvent<Factura, String> t) {
                       
                        ((Factura) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                                ).setForma_pago(t.getNewValue());
                        factura=((Factura) t.getTableView().getItems().get(
                                t.getTablePosition().getRow()));
                        if(t.getNewValue().equals("Contado"))
                        {
                            factura.setCredito(false);
                        }
                        else
                            {
                        if(t.getNewValue().equals("Credito"))
                        {
                            factura.setCredito(true);
                        }
                               
                                    
                          }
                        ModifyFactura();
                    }
                });

        TableColumn ValorTotal= new TableColumn();
        ValorTotal.setMinWidth(100);
        ValorTotal.setText("Valor Total");
        ValorTotal.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Factura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Factura, String> factura) {
               
                    return new SimpleStringProperty(factura.getValue().getTotalAmount().toString());
                            
            }
        });
       
      Ta_Factura.setMinHeight(600);
      Ta_Factura.setMinWidth(870);
      Ta_Factura.setEditable(true);
      Ta_Factura.getColumns().addAll(NoGuia,Fecha,FormaPago,NoIdent,Cliente,Dir,Tel,ValorTotal);
     
         gridpaneDatos=new GridPane();
         gridpaneRoot=new GridPane();
         gridpaneFechas=new GridPane();
         gridpane=new GridPane();
        // gridpane.getStyleClass().add("category-page");
         
        gridpaneFechas.add(L_Date_from, 0, 0);
        gridpaneFechas.add(Dp_Date_from, 1, 0);
        gridpaneFechas.add(L_Date_to, 2, 0);
        gridpaneFechas.add(Dp_Date_to, 3, 0);
        gridpaneFechas.add(L_Buscar, 4, 0);
        gridpaneFechas.add(Tf_Buscar, 5, 0);
        gridpaneFechas.add(B_Eliminar, 6, 0);
        gridpaneFechas.add(B_DetallesFacturas, 7, 0);
        gridpaneFechas.add(B_Pdf, 8, 0);
        gridpaneFechas.setMinWidth(430);
        gridpaneFechas.setHgap(5);
        
     
       gridpane.setVgap(5);
        gridpaneRoot.alignmentProperty().setValue(Pos.TOP_CENTER);
       GridPane.setValignment(gridpaneRoot, VPos.TOP);
      
       
        
      
         gridpane.add(gridpaneFechas, 0, 0);
         gridpane.add(Ta_Factura, 0, 1);
        
         gridpane.setMinSize(1270, 610);
         gridpaneRoot.setMinSize(1000, 610);
         GridPane.setColumnSpan(Ta_Factura,5);
         GridPane.setColumnSpan(gridpaneFechas,5);
         GridPane.setHalignment(Ta_Factura, HPos.CENTER);
         GridPane.setHalignment(gridpaneFechas, HPos.CENTER);
         GridPane.setHalignment(gridpaneDatos, HPos.CENTER);
         gridpaneFechas.setAlignment(Pos.CENTER);
         gridpaneDatos.setAlignment(Pos.CENTER);    
         gridpaneRoot.add(gridpane, 0, 0);
         //gridpane.getStyleClass().add("background");
         switch(cb_temas.getSelectionModel().getSelectedIndex())
         {
             case 0: new JMetro(JMetro.Style.DARK).applyTheme(gridpaneRoot);
                      break;
             case 1: new JMetro(JMetro.Style.LIGHT).applyTheme(gridpaneRoot);
                      break;
             case 2: AeroFX.style();
                        AeroFX.styleAllAsGroupBox(gridpaneRoot);
                         break;  
             case 3: AquaFx.style();
                     AquaFx.setGroupBox(gridpaneRoot);
                     break;              
         }
         findOrdenesGuia();
         return gridpaneRoot;
    }
    public  static Node createIconContent() throws IOException {
         ImageView imageView = new ImageView(new Image("/modulos/inventarios/Inventario.png"));
         imageView.setFitHeight(80);
         imageView.setFitWidth(90);
         javafx.scene.Group g =
               new javafx.scene.Group(imageView);
   
        return g;
    }
  private void  findOrdenesGuia() throws ParseException
    {
         //colocamos los datos
       
      
       
         ar_OrdenGuia=facturaControllerClient.getOrdenesGuia(Dp_Date_from.getValue().toString(),Dp_Date_to.getValue().toString(), Tf_Buscar.getText());
      
       Ol_Factura.clear();
       
        Ol_Factura.addAll(ar_OrdenGuia);
       // Ta_KardexProducto.getItems().clear();
       Ta_Factura.setItems(Ol_Factura);
      
           
            
            
            
         
        
        
      
    }
   Task<Void> task = new Task<Void>() {
         @Override protected Void call() throws Exception {
            
    //stage finproduct
   
             // Return null at the end of a Task of type Void
             return null;
         }
     };
   public void deleteFactura() throws ParseException
   {
       Factura factura=(Factura)Ta_Factura.getSelectionModel().getSelectedItem();
       if(factura!=null)
       {
           if (factura.getFacturaItems().size()<=0)
           {
               factura.setDeleted(true);
               facturaControllerClient.guardarFactura(factura);
               findOrdenesGuia();
           }
           else
           {
               gridpaneFechas.add(success,7,0 );
               success.setText("La factura tiene items de venta");
                animateMessage();
               
           }
       }
       else
           {
               gridpaneFechas.add(success,7,0 );
               success.setText("No ha señalado ninguna orden de Guia");
                animateMessage();
           }
   }
   
   private void animateMessage() {
         success.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(3000), success);
        ft.setFromValue(0.0);
        ft.setToValue(1);
      
        ft.play();
       
        //success.setOpacity(0); 
        
      
       ft.setOnFinished(new EventHandler<ActionEvent>() {
 
            @Override
            public void handle(ActionEvent event) {
               gridpaneFechas.getChildren().remove(success);
                success.setText("");
                
            }
        });
        
           
       
    }
   public void printFactura(int opc) throws IOException
 {
     if(Ta_Factura.getSelectionModel().getSelectedItem()!=null)
     {
         
     ImpresionFactura impresionFactura=null;
     impresionFactura=new ImpresionFactura((Factura)Ta_Factura.getSelectionModel().getSelectedItem());
     impresionFactura.fprintpdf(opc);
     }
     
     
 }public void ModifyFactura()
 {
     facturaControllerClient.guardarFactura(factura);
 }
   @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    /**
     * Java main for when running without JavaFX launcher
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

 
     

