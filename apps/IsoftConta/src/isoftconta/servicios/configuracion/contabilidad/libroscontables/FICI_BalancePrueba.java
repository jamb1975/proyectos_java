/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.servicios.configuracion.contabilidad.libroscontables;

/**
 *
 * @author adminlinux
 */
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.Function;
import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter.Change;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;
import modelo.BalancePrueba;
import modelo.BalancePrueba.LineItemListWithTotal;
import modelo.EntidadesStatic;

/**
 *
 * @author adminlinux
 */
public class FICI_BalancePrueba extends Application {

    private GridPane gp_generico;
    private static TableView tv_generic;
  
    private static List<BalancePrueba> li_balanceprueba;
    private Button bu_buscar;
    private Button bu_nuevo;
    private Button bu_calcular;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
    private StackPane stack;
   
    private SimpleDateFormat df = new SimpleDateFormat("MMM-dd");
    private String appClass;
    private Class clz;
    private Object app;
    private Parent parent;
    private Stage stage = new Stage(StageStyle.DECORATED);
    Scene scene = null;
    private ContextMenu contextMenu;
    
    private TitledPane tp_generic;
    private int mes=0;
    private int año=0;
    private TextField totaldebito=new TextField("0");
    private TextField totalcredito=new TextField("0");
    private static ObservableList<BalancePrueba> ol_balanceprueba;
      private static <S, T> TableColumn<S, T> column(String title,
            Function<S, ObservableValue<T>> property, boolean editable,
            StringConverter<T> converter, int _width) {
        
        TableColumn<S, T> col = new TableColumn<>(title);
        
        col.setCellValueFactory(cellData -> property.apply(cellData.getValue()));
        
        col.setEditable(editable);
        col.setMinWidth(_width);
        
        if (editable) {
            col.setCellFactory(TextFieldTableCell.forTableColumn(converter));
        }
        
        return col;
}
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
       
        
       
        gp_generico = new GridPane();
         tp_generic = new TitledPane();
        tp_generic.setText("BALANCE DE PRUEBA "+isoftconta.util.UtilDate.obtenerMes(EntidadesStatic.li_libromayorbalances.get(0).getAño(),EntidadesStatic.li_libromayorbalances.get(0).getMes()).toUpperCase()+" DE "+EntidadesStatic.li_libromayorbalances.get(0).getAño());
        tp_generic.setCollapsible(false);
    
        stack=new StackPane();
        tv_generic = new TableView();
        
       
         tv_generic.getColumns().add(
                column("Código", BalancePrueba::codigoProperty, false, null,150));
        
        tv_generic.getColumns().add(
                column("Cuenta", BalancePrueba::cuentaProperty, false, null,250));

       

        tv_generic.getColumns().add(
                column("Debe", BalancePrueba::getS_debe, false,
                        null,150));

        tv_generic.getColumns().add(column("Haber", BalancePrueba::getS_haber, false, null,150));
         ol_balanceprueba = FXCollections.observableArrayList(item -> 
         {
            return new Observable[] {item.debeProperty(),item.haberProperty()};
        });
        tv_generic.setRowFactory(tv -> {
            PseudoClass lastLinePC = PseudoClass.getPseudoClass("last-line");
            TableRow<BalancePrueba> row = new TableRow<>();
            row.indexProperty().addListener((obs, oldIndex, newIndex) -> {
                row.pseudoClassStateChanged(lastLinePC, newIndex.intValue() == ol_balanceprueba.size());
            });
           ol_balanceprueba.addListener((ListChangeListener.Change<? extends BalancePrueba> change) -> {
                row.pseudoClassStateChanged(lastLinePC, row.getIndex() == ol_balanceprueba.size());                
            });
            return row ;
});
      // tv_generic.getColumns().addAll(codigo,cuenta,debito,credito);
        Gp_Message = new GridPane();

        Gp_Message.setMaxSize(40, gp_generico.getLayoutBounds().getWidth());
        L_Message = new Label();
      
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
           
        gp_generico.add(tv_generic, 0, 0, 1, 1);
        
        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic, Gp_Message);
        gp_generico.setMinSize(710, 550);
        gp_generico.setMaxSize(710, 550);
        tv_generic.getColumns().addAll();
        tv_generic.setMinHeight(540);
        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       

        getRecords();

        return stack;
    }
       
    public static void getRecords() throws ParseException {
        try {
           
            ol_balanceprueba.clear();
            for(BalancePrueba bp:EntidadesStatic.li_balaBalancePruebas){
                
                addItem(ol_balanceprueba,bp.getCodigo(), bp.getCuenta(),BigDecimal.valueOf(bp.getDebe()),BigDecimal.valueOf(bp.getHaber()));
            }
            tv_generic.setItems(new LineItemListWithTotal(ol_balanceprueba));
        } catch (Exception e) {
e.printStackTrace();
        }
    }

   
    @Override
    public void start(Stage primaryStage) throws Exception {

    }
    
 private GridPane createEditor(ObservableList<BalancePrueba> items,
            ReadOnlyIntegerProperty selectedIndex) {

        

     

       

        Button deleteButton = new Button("Delete");
        deleteButton.setDisable(true);
        selectedIndex.addListener((obs, oldIndex, newIndex) -> {
            deleteButton.setDisable(newIndex.intValue() < 0
                    || newIndex.intValue() >= items.size());
        });
        items.addListener((ListChangeListener.Change<? extends BalancePrueba> change) -> {
            deleteButton.setDisable(selectedIndex.get() < 0
                    || selectedIndex.get() >= items.size());
        });

        deleteButton.setOnAction(e -> items.remove(selectedIndex.get()));

        GridPane editor = new GridPane();
        editor.setHgap(5);
        editor.setVgap(5);

       

      
     

        ColumnConstraints leftCol = new ColumnConstraints();
        leftCol.setHalignment(HPos.RIGHT);
        ColumnConstraints rightCol = new ColumnConstraints();
        rightCol.setHalignment(HPos.LEFT);
        rightCol.setHgrow(Priority.ALWAYS);

        editor.getColumnConstraints().addAll(leftCol, rightCol);
        editor.setAlignment(Pos.CENTER);
        editor.setPadding(new Insets(10));
        return editor;
    }

    private static void addItem(ObservableList<BalancePrueba> items, String codigo,String cuenta,BigDecimal debe,BigDecimal haber)
             {
       
      
        items.add(new BalancePrueba(codigo,cuenta,debe,haber));
       
}  
    
}
