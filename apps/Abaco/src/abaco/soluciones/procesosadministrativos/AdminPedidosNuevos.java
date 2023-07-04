/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package abaco.soluciones.procesosadministrativos;

import abaco.AbacoApp;
import static abaco.AbacoApp.cb_temas;
import com.aquafx_project.AquaFx;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import jfxtras.styles.jmetro8.JMetro;
import model.Pedidos;
import org.aerofx.AeroFX;

/**
 *
 * @author isoft
 */
public class AdminPedidosNuevos extends Application{
    
      private static TableView tv_pedidosnuevos=new TableView();
      private ObservableList ol_pedisonuevos = FXCollections.observableArrayList();
     
      private GridPane gp_generic=new GridPane();
      private TableColumn numeropedido = new TableColumn();
      private TableColumn numeroident = new TableColumn(); 
      private TableColumn nombre = new TableColumn();
      private TableColumn fecha = new TableColumn();
      private TableColumn dir = new TableColumn();
      private TableColumn numerocelular = new TableColumn();
      private TableColumn buttonCol = new TableColumn<>("");
      private  String appclassdetallepedido;
      private  Class clzdetallespedido ;
      private  Object appdetallespedido ;
      private  Parent parentdetallespedido;
      private  Stage stagedetallespedido= new Stage(StageStyle.DECORATED);
      private Scene sc_detallepedido=null;
      public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
          tv_pedidosnuevos.getColumns().clear();
          iniciarcolumnastablas();
           tv_pedidosnuevos.getColumns().addAll(numeropedido, fecha, numeroident, nombre, buttonCol);
           tv_pedidosnuevos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

          gp_generic.addRow(0, tv_pedidosnuevos);
           switch(cb_temas.getSelectionModel().getSelectedIndex())
         {
             case 0: new JMetro(JMetro.Style.DARK).applyTheme(gp_generic);
                      break;
             case 1: new JMetro(JMetro.Style.LIGHT).applyTheme(gp_generic);
                      break;
             case 2: AeroFX.style();
                        AeroFX.styleAllAsGroupBox(gp_generic);
                         break;  
             case 3: AquaFx.style();
                     AquaFx.setGroupBox(gp_generic);
                     break;              
         }
         if(AbacoApp.cb_temas.getSelectionModel().getSelectedIndex()!=0)
        {
          buttonCol.setStyle("-fx-background-color:#007fff");
        
        }
         getRecords();
          return gp_generic;
       }
   
      private void iniciarcolumnastablas()
      {
        fecha.setMinWidth(150);
        fecha.setText("Fecha");
        fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedidos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedidos, String> pedidos) {
               
                    return new SimpleStringProperty(abaco.util.UtilDate.formatodiamesyear(pedidos.getValue().getFecha()));
                            
            }
        });  
        numeropedido.setMinWidth(150);
        numeropedido.setText("N° pedido");
        numeropedido.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedidos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedidos, String> pedidos) {
               
                    return new SimpleStringProperty(pedidos.getValue().getNo_pedido());
                            
            }
        });
        numeroident.setMinWidth(150);
        numeroident.setText("N° identificación");
        numeroident.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedidos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedidos, String> pedidos) {
               
                    return new SimpleStringProperty(pedidos.getValue().getCliente().getNo_ident());
                            
            }
        });
         nombre.setMinWidth(200);
         nombre.setText("Nombre");
         nombre.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedidos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedidos, String> pedidos) {
               
                    return new SimpleStringProperty(pedidos.getValue().getCliente().getNombre());
                            
            }
        });
         dir.setMinWidth(150);
         dir.setText("Dirección");
         dir.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedidos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedidos, String> pedidos) {
               
                    return new SimpleStringProperty(pedidos.getValue().getCliente().getDir1());
                            
            }
        });
         numerocelular.setMinWidth(150);
         numerocelular.setText("N° celular");
         numerocelular.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Pedidos, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Pedidos, String> pedidos) {
               
                    return new SimpleStringProperty(pedidos.getValue().getCliente().getDir1());
                            
            }
        });
        buttonCol.setText("Ver detalles de pedido");
        buttonCol.setSortable(false);
        buttonCol.setMinWidth(200);
        buttonCol.setSortable(false);
         buttonCol.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Pedidos, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Pedidos, Boolean> fi) {
                return new SimpleBooleanProperty(fi.getValue() != null);
            }
        });
        //Indicamos que muestre el ButtonCell creado mas abajo.
        buttonCol.setCellFactory(
                new Callback<TableColumn<Pedidos, Boolean>, TableCell<Pedidos, Boolean>>() {

            @Override
            public TableCell<Pedidos, Boolean> call(TableColumn<Pedidos, Boolean> p) {
                return new ButtonCell(tv_pedidosnuevos);
            }

        }); 
      }

    @Override
    public void start(Stage primaryStage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
       private class ButtonCell extends TableCell<Pedidos, Boolean> {
      final Button cellButton = new Button();
      ButtonCell(final TableView tblView) {
     /* Image imageOk = null;
      imageOk = new Image(getClass().getResourceAsStream("/images/pdf.png"));
      ImageView iv = null;
      iv = new ImageView(imageOk);
      iv.setFitHeight(16);
      iv.setFitWidth(16);*/
      cellButton.setText("Ver items");
      cellButton.setCursor(Cursor.HAND);
     // cellButton.setTooltip(new Tooltip("Confirmar y preparar pedido"));
      cellButton.setOnAction(e -> {
      int selectdIndex = getTableRow().getIndex();
      AbacoApp.pedidos = ((Pedidos) ol_pedisonuevos.get(selectdIndex));
          try {
              showdetallespedido();
          } catch (Exception ex) {
              Logger.getLogger(AdminPedidosNuevos.class.getName()).log(Level.SEVERE, null, ex);
          } 
       });
        }
        //Muestra un boton si la fila no es nula

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                try {
                    int selectdIndex = getTableRow().getIndex();

                    setGraphic(cellButton);
                } catch (Exception e) {

                }

            } else {
                setGraphic(new Label());

            }
        }

    }
  public  void   getRecords() throws ParseException
{
         //colocamos los datos
         try{
           AbacoApp.pedidosControllerclient.getrecordsporestado(AbacoApp.estadopedido);
       
       ol_pedisonuevos.clear();
       
        ol_pedisonuevos.addAll(AbacoApp.li_pedidos.toArray());
        tv_pedidosnuevos.setItems(ol_pedisonuevos);
      } catch(Exception e)
       {
           e.printStackTrace();
       }
    }     
  private void showdetallespedido() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException
  {  
    appclassdetallepedido="abaco.soluciones.procesosadministrativos.FDetallesPedido";
    clzdetallespedido = Class.forName(appclassdetallepedido);
    appdetallespedido= clzdetallespedido.newInstance();
    parentdetallespedido=null;
    parentdetallespedido= (Parent) clzdetallespedido.getMethod("createContent").invoke(appdetallespedido);
    sc_detallepedido=null;
    sc_detallepedido=new Scene(parentdetallespedido, Color.TRANSPARENT);
      
        if (stagedetallespedido.isShowing())
       {
           stagedetallespedido.close();
       }      
        
        
//set scene to stage
                stagedetallespedido.sizeToScene();
                
              
                //center stage on screen
                stagedetallespedido.centerOnScreen();
                stagedetallespedido.setScene(sc_detallepedido);
                 
                //stage.setMaxWidth(550);
                //stage.setX(x);
               // stage.setY(y);
                //show the stage
                stagedetallespedido.show();
  }        
}
