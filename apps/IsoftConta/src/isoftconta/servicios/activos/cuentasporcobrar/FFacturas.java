/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios.activos.cuentasporcobrar;

import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import isoftconta.IsoftConta;
import isoftconta.glyphfont.FontAwesome;
import isoftconta.glyphfont.Glyph;
import isoftconta.servicios.ConexionPosgresql;
import isoftconta.servicios.DocumentoSoporteController;
import isoftconta.util.UtilDate;
import modelo.DTOFactura;
import modelo.EntidadesStatic;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

/**
 *
 * @author adminlinux
 */
public class FFacturas extends Application{
   private GridPane gp_generic=new GridPane();
    private TableView tv_facturas=new TableView();
    private TableColumn tc_fechaelaboracion = new TableColumn();
    private TableColumn tc_nofactura = new TableColumn();
    private TableColumn tc_nombres = new TableColumn();
    private TableColumn tc_totalamount = new TableColumn();
    private TableColumn tc_cuotamoderadora = new TableColumn();
    private TableColumn tc_copago = new TableColumn();
    private TableColumn tc_bucausarfactura = new TableColumn<>("");
    private Label la_fechadesde=new Label("Desde:");
    private DatePicker fechadesde=new DatePicker(LocalDate.now());
    private Label la_fechahasta=new Label("Hasta:");
    private DatePicker fechahasta=new DatePicker(LocalDate.now());
    private Label la_prefijo=new Label("Prefijo");
    private ChoiceBox cb_prefijo=new ChoiceBox();
    private ObservableList Ol_Factura = FXCollections.observableArrayList();
    private Button bu_getfacturasporprefijo=new Button();
    private Button bu_importartodasfacturas=new Button();
    private TextField puccausaciondebito=new TextField();
    private TextField puccausacioncredito=new TextField();
    private TextField tf_nocomprobante=new TextField();
    private Notifications notificationBuilder = Notifications.create();
     public Parent createContent()
     {  
         tf_nocomprobante.setText("-1");
         TextFields.bindAutoCompletion(puccausaciondebito, EntidadesStatic.as_puc);
         puccausaciondebito.setMaxWidth(300);
         puccausaciondebito.setMinWidth(300);
         puccausaciondebito.setPromptText("Cuenta Puc Causación Débito");
         puccausaciondebito.focusedProperty().addListener(new ChangeListener<Boolean>()
      {
    @Override
    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
    {
     DocumentoSoporteController.findpuccausaciondebitoporcodigo(puccausaciondebito.getText());
    }
    });
         TextFields.bindAutoCompletion(puccausacioncredito, EntidadesStatic.as_puc);
         puccausacioncredito.setMaxWidth(300);
         puccausacioncredito.setMinWidth(300);
         puccausacioncredito.setPromptText("Cuenta Puc Causación Crédito");
         puccausacioncredito.focusedProperty().addListener(new ChangeListener<Boolean>()
      {
    @Override
    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
    {
     DocumentoSoporteController.findpuccausacioncreditoporcodigo(puccausacioncredito.getText());
    }
    });
         gp_generic.setPadding(new Insets(5, 5, 5, 5));
         gp_generic.setVgap(5);
         gp_generic.setHgap(5);
         cb_prefijo.getItems().addAll("A","P","T","E");
         char IM_DATABASE='\uf1c0';
         Color randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
         Glyph glyph_database=  IsoftConta.icoMoon.create(FontAwesome.Glyph.ARCHIVE.getChar()).sizeFactor(2).color(randomColor).size(16).useGradientEffect();
         Glyph glyph_importartodasfacturas=  IsoftConta.fontAwesome.create(FontAwesome.Glyph.BOLT.getChar()).sizeFactor(2).color(randomColor).size(16).useGradientEffect();
       
         bu_getfacturasporprefijo.setGraphic(glyph_database);
         bu_getfacturasporprefijo.setOnAction(eh->{
             ConexionPosgresql.connectDatabasePosgresql("localhost", "7777", "sihic", "postgres", "JAmbg172*");
             
             try {
                 ConexionPosgresql.getfacturasporprefijoyfecha(fechadesde.getValue().toString(), fechahasta.getValue().toString(), "TODOS");
                  getRecords();
             } catch (SQLException ex) {
                 Logger.getLogger(FFacturas.class.getName()).log(Level.SEVERE, null, ex);
             } catch (ParseException ex) {
                 Logger.getLogger(FFacturas.class.getName()).log(Level.SEVERE, null, ex);
             }
         });
         bu_importartodasfacturas.setGraphic(glyph_importartodasfacturas);
         bu_importartodasfacturas.setOnAction(eh->{
             Long nocomprobante=Long.valueOf(-1);
                 try
                 {
                     nocomprobante=Long.valueOf(tf_nocomprobante.getText());
                 }
                 catch(Exception e)
                 {
                    nocomprobante=Long.valueOf(-1);  
                 }
             try {
                 FICI_CausacionIngreso.procesarcausarfacturas(nocomprobante);
             } catch (ParseException ex) {
                 Logger.getLogger(FFacturas.class.getName()).log(Level.SEVERE, null, ex);
             } catch (InterruptedException ex) {
                 Logger.getLogger(FFacturas.class.getName()).log(Level.SEVERE, null, ex);
             }
         });
         tc_fechaelaboracion.setSortable(false);
        tc_fechaelaboracion.setMinWidth(80);
        tc_fechaelaboracion.setText("Fecha de Elaboración");
        tc_fechaelaboracion.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DTOFactura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DTOFactura, String> factura) {

                return new SimpleStringProperty(UtilDate.s_formatoyearmesdia(factura.getValue().getFechaelaboracion()));

            }
        });
        tc_nofactura.setSortable(false);
        tc_nofactura.setMinWidth(80);
        tc_nofactura.setText("No Factura");
        tc_nofactura.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DTOFactura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DTOFactura, String> factura) {

                return new SimpleStringProperty(factura.getValue().getPrefijo()+factura.getValue().getNo_factura());

            }
        });
        tc_nombres.setSortable(false);
        tc_nombres.setMinWidth(150);
        tc_nombres.setText("Cliente");
        tc_nombres.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DTOFactura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DTOFactura, String> factura) {

                return new SimpleStringProperty(factura.getValue().getNombre()+" "+factura.getValue().getPrimer_apellido()+" "+factura.getValue().getSegundo_apellido());

            }
        });
         tc_totalamount.setSortable(false);
         tc_totalamount.setMinWidth(80);
         tc_totalamount.setText("Valor");
         tc_totalamount.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DTOFactura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DTOFactura, String> factura) {

                return new SimpleStringProperty(factura.getValue().getTotalamount().toString());

            }
        });
         tc_cuotamoderadora.setSortable(false);
         tc_cuotamoderadora.setMinWidth(80);
         tc_cuotamoderadora.setText("Cuota Mod");
         tc_cuotamoderadora.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DTOFactura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DTOFactura, String> factura) {
                try
                 {
                return new SimpleStringProperty(factura.getValue().getCuotamoderadora().toString());
                 }
                catch(Exception e)
                 {
                     return new SimpleStringProperty("0");  
                 }

            }
        });
         tc_copago.setSortable(false);
         tc_copago.setMinWidth(80);
         tc_copago.setText("Copago");
         tc_copago.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<DTOFactura, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<DTOFactura, String> factura) {
                 try{
                return new SimpleStringProperty(factura.getValue().getCopago().toString());
                }
                catch(Exception e)
                 {
                     return new SimpleStringProperty("0");  
                 }

            }
        });
         tc_bucausarfactura.setMinWidth(50);
        tc_bucausarfactura.setSortable(false);
        tc_bucausarfactura.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<DTOFactura, Boolean>, ObservableValue<Boolean>>() {

            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<DTOFactura, Boolean> fi) {
                return new SimpleBooleanProperty(fi.getValue() != null);
            }
        });
        //Indicamos que muestre el ButtonCell creado mas abajo.
        tc_bucausarfactura.setCellFactory(
                new Callback<TableColumn<DTOFactura, Boolean>, TableCell<DTOFactura, Boolean>>() {

            @Override
            public TableCell<DTOFactura, Boolean> call(TableColumn<DTOFactura, Boolean> p) {
                return new ButtonCellCausarFactura(tv_facturas);
            }

        });
         tv_facturas.getColumns().addAll(tc_fechaelaboracion,tc_nofactura, tc_nombres, tc_totalamount,tc_cuotamoderadora,tc_copago,tc_bucausarfactura);
        //ta_librodiario.setMinWidth(650);

        tv_facturas.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        gp_generic.addRow(0,fechadesde,fechahasta,IsoftConta.cb_sucursales,IsoftConta.cb_centrocostos,tf_nocomprobante,bu_getfacturasporprefijo,bu_importartodasfacturas);
        gp_generic.add(tv_facturas,0,1,6,1);
        return gp_generic;
     }

    @Override
    public void start(Stage stage) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
     
private class ButtonCellCausarFactura extends TableCell<DTOFactura, Boolean> {

        //boton a mostrar
        final Button cellButton = new Button();
       

        ButtonCellCausarFactura(final TableView tblView) {
        char IM_CAUSARINGRESO='\uf058';
        Color  randomColor = new Color( Math.random(), Math.random(), Math.random(), 1);
        Glyph glyph_causaringreso=  IsoftConta.icoMoon.create(IM_CAUSARINGRESO).sizeFactor(2).color(randomColor).size(16).useGradientEffect();
      cellButton.setMaxSize(18, 18);
            cellButton.setGraphic(glyph_causaringreso);
            cellButton.setCursor(Cursor.HAND);
            cellButton.setTooltip(new Tooltip("Causar Ingreso"));
            cellButton.setOnAction(e -> {
                int selectdIndex = getTableRow().getIndex();
                EntidadesStatic.dtofactura= ((DTOFactura) Ol_Factura.get(selectdIndex));
                try {
                  ///  FICI_CausacionIngreso.procesarcausarfacturas();

                  
                } catch(Exception e2)
                {
                }

            });
        }
       
 @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            try {
                int selectdIndex = getTableRow().getIndex();
              if(!empty)
              {
                setGraphic(cellButton);
              
               } else {
                    setGraphic(new Label());

                }
                  
                
            } catch (Exception e) {

            }
        }
       

    }
 public  void getRecords() throws ParseException {
        //colocamos los datos
        try {
          System.out.println("size factura->"+EntidadesStatic.li_dtoFacturas.size());
            Ol_Factura.clear();
            Ol_Factura.addAll(EntidadesStatic.li_dtoFacturas.toArray());
            tv_facturas.setItems(Ol_Factura);
        } catch (Exception e) {
           
        }
    }

}
