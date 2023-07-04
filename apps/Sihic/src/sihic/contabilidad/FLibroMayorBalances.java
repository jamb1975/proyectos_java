/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.contabilidad;

/**
 *
 * @author adminlinux
 */
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import modelo.BalancePrueba;
import modelo.LibroMayorBalances;
import modelo.Nomina;
import sihic.SihicApp;

public class FLibroMayorBalances extends Application {

    private GridPane gp_generico;
    private static TableView tv_generic;
    private static ObservableList ol_libromayorbalances = FXCollections.observableArrayList();
    private static List<LibroMayorBalances> li_libromayorbalances;
    private Button bu_buscar;
    private Button bu_nuevo;
    private Button bu_calcular;
    private Button bu_balanceprueba;
     private Button bu_esf;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    Thread backgroundThread;
    private StackPane stack;
    private ImpresionFactura impresionFactura;
    private SimpleDateFormat df = new SimpleDateFormat("MMM-dd");
    private String appClass;
    private Class clz;
    private Object app;
    private Parent parent;
    private Stage stage = new Stage(StageStyle.DECORATED);
    Scene scene = null;
    private ContextMenu contextMenu;
    private MenuItem itemnuevo;
    private MenuItem itemeditar;
    private MenuItem itemdelete;
    private ImageView img;
    private static DatePicker datefrom;
    private static DatePicker dateto;
    private TitledPane tp_generic;
    private int mes=0;
    private int año=0;
    public Parent createContent() throws ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException {
        tp_generic = new TitledPane();
        tp_generic.setText("Libro Mayor y de Balances");
        tp_generic.setCollapsible(false);
        datefrom=new DatePicker(sihic.util.UtilDate.primerdiaprimermes(new Date()));
        dateto=new DatePicker(sihic.util.UtilDate.ultimodiaultimomes(new Date()));
        stage.setTitle("Nomina Empleados");
        img = new ImageView("/images/new2.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemnuevo = new MenuItem("Nuevo", img);
        itemnuevo.setOnAction(e -> {
            try {
                SihicApp.empleados = null;
                show();
            } catch (Exception x) {

            }
        });
        img = null;
        img = new ImageView("/images/editar.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemeditar = new MenuItem("Editar", img);
        itemeditar.setOnAction(e -> {

            try {
               if(checkregistroexistente())
               {
                   show();
               }
            } catch (Exception x) {
                x.printStackTrace();
            }
        });
        img = null;
        img = new ImageView("/images/delete.png");
        img.setFitHeight(20);
        img.setFitWidth(20);
        itemdelete = new MenuItem("Eliminar", img);
        itemdelete.setOnAction(e -> {

            try {
              if( checkregistroexistente())
                eliminar();
            } catch (Exception x) {

            }
        });

        contextMenu = new ContextMenu(itemnuevo, itemeditar, itemdelete);
        stack = new StackPane();
       
        
        ImageView imageView;

        imageView = new ImageView("/images/find.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        bu_buscar = new Button("", imageView);
        bu_buscar.setTooltip(new Tooltip("Ultimos Movimientos Mensual al Libro"));
        bu_buscar.setDisable(false);
        bu_buscar.setOnAction(e
                -> {
            try {
                getLastMovimiento();

            } catch (Exception ex) {

            }
        });
        imageView = new ImageView("/images/addlibromayor2.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        bu_nuevo = new Button("", imageView);
        bu_nuevo.setTooltip(new Tooltip("Agregar nuevos Movimientos Mensual al Libro"));
        bu_nuevo.setDisable(false);
        bu_nuevo.setOnAction(e
                -> {
            try {
                
               addNewMovimiento();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        imageView = new ImageView("/images/calcular.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        bu_calcular = new Button("", imageView);
        bu_calcular.setTooltip(new Tooltip("Calcular de Nuevo Movimientos Mensual al Libro"));
        bu_calcular.setDisable(false);
        bu_calcular.setOnAction(e
                -> {
            try {
              
                    volveracalcularlibromayorbalances();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
        imageView = new ImageView("/images/balanceprueba.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        bu_balanceprueba = new Button("", imageView);
        bu_balanceprueba.setTooltip(new Tooltip("Balance de Prueba"));
        bu_balanceprueba.setDisable(false);
        bu_balanceprueba.setOnAction(e
                -> {
            try {
              
                    crearbalancedeprueba();
                    show();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
          imageView = new ImageView("/images/esf.png");
        imageView.setFitHeight(40);
        imageView.setFitWidth(40);

        bu_esf = new Button("", imageView);
        bu_esf.setTooltip(new Tooltip("Estado de Siuación Financiera"));
        bu_esf.setDisable(false);
        bu_esf.setOnAction(e-> {
           
              
            try {
                fpdf_estadosituacionfinanciera();
            } catch (Exception ex) {
                Logger.getLogger(FLibroMayorBalances.class.getName()).log(Level.SEVERE, null, ex);
            } 
          
        });
        gp_generico = new GridPane();
        tv_generic = new TableView();
        
        TableColumn fecha = new TableColumn();
        fecha.setMinWidth(100);
        fecha.setText("Fecha");
        fecha.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libro) {

                return new SimpleStringProperty(sihic.util.UtilDate.s_formatoyearmesdia(libro.getValue().getFechaelaboracion()));

            }
        });
        TableColumn cmes = new TableColumn();
        cmes.setMinWidth(65);
        cmes.setText("Mes");
        cmes.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libro) {

                return new SimpleStringProperty(String.valueOf(libro.getValue().getMes()));

            }
        });
       TableColumn caño = new TableColumn();
        caño.setMinWidth(65);
        caño.setText("Año");
        caño.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libro) {

                return new SimpleStringProperty(String.valueOf(libro.getValue().getAño()));

            }
        });
        TableColumn detalle = new TableColumn();
        detalle.setMinWidth(200);
        detalle.setText("Cuenta");
        detalle.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libro) {

                return new SimpleStringProperty(libro.getValue().getConPuc().getNombre());

            }
        });
        TableColumn ccodigo = new TableColumn();
        ccodigo.setMinWidth(100);
        ccodigo.setText("Código");
        ccodigo.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libro) {

                return new SimpleStringProperty(libro.getValue().getConPuc().getCodigo());

            }
        });
        TableColumn csaldoanteriordebito = new TableColumn();
        csaldoanteriordebito.setMinWidth(150);
        csaldoanteriordebito.setText("Saldo Anterior Débito");
        csaldoanteriordebito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libro) {

                return new SimpleStringProperty(libro.getValue().getSaldoanteriordebito().toString());

            }
        });
        TableColumn csaldoanteriorcredito = new TableColumn();
        csaldoanteriorcredito.setMinWidth(150);
        csaldoanteriorcredito.setText("Saldo Anterior Crédito");
        csaldoanteriorcredito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libro) {

                return new SimpleStringProperty(libro.getValue().getSaldoanteriorcredito().toString());

            }
        });
        TableColumn movimientodebito = new TableColumn();
        movimientodebito.setMinWidth(150);
        movimientodebito.setText("Movimiento Débito");
        movimientodebito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libro) {

                return new SimpleStringProperty(libro.getValue().getMovimientosdebito().toString());

            }
        });
         TableColumn movimientocredito = new TableColumn();
        movimientocredito.setMinWidth(150);
        movimientocredito.setText("Movimiento Crédito");
        movimientocredito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libro) {

                return new SimpleStringProperty(libro.getValue().getMovimientoscredito().toString());

            }
        });
          TableColumn saldodebito = new TableColumn();
          saldodebito.setMinWidth(150);
          saldodebito.setText("Saldo Débito");
          saldodebito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libro) {

                return new SimpleStringProperty(libro.getValue().getSaldodebito().toString());

            }
        });
           TableColumn saldocredito = new TableColumn();
          saldocredito.setMinWidth(150);
          saldocredito.setText("Saldo Crédito");
          saldocredito.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<LibroMayorBalances, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<LibroMayorBalances, String> libro) {

                return new SimpleStringProperty(libro.getValue().getSaldocredito().toString());

            }
        });
        Gp_Message = new GridPane();

        Gp_Message.setMaxSize(40, gp_generico.getLayoutBounds().getWidth());
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        gp_generico.getStylesheets().add(SihicApp.hojaestilos);
        gp_generico.getStyleClass().add("background");
        gp_generico.addRow(0,bu_nuevo,bu_calcular,bu_balanceprueba,bu_esf);

        gp_generico.add(tv_generic, 0, 3, 7, 1);

        gp_generico.setVgap(5);
        gp_generico.setHgap(5);
        gp_generico.setAlignment(Pos.TOP_CENTER);
        stack.setAlignment(Pos.TOP_CENTER);
        tp_generic.setContent(gp_generico);
        stack.getChildren().addAll(tp_generic, Gp_Message);
        gp_generico.setMinSize(1340, 630);
        gp_generico.setMaxSize(1340, 630);
        tv_generic.getColumns().addAll(fecha, caño, cmes,ccodigo,detalle,csaldoanteriordebito,csaldoanteriorcredito,movimientodebito,movimientocredito,saldodebito,saldocredito);
        tv_generic.setMinHeight(570);
        tv_generic.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tv_generic.setContextMenu(contextMenu);

        getLastMovimiento();

        return stack;
    }
       
    public static void getRecords() throws ParseException {
        try {
           
            ol_libromayorbalances.clear();
            ol_libromayorbalances.addAll(SihicApp.li_libromayorbalances.toArray());
            tv_generic.setItems(ol_libromayorbalances);
        } catch (Exception e) {

        }
    }

    private void show() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        appClass = "sihic.contabilidad.FBalancePrueba";
        clz=null;
        clz = Class.forName(appClass);
        app = clz.newInstance();
        parent = null;
        parent = (Parent) clz.getMethod("createContent").invoke(app);
        scene = null;
        scene = new Scene(parent, Color.TRANSPARENT);

        if (stage.isShowing()) {
            stage.close();
        }

//set scene to stage
        stage.sizeToScene();

        //center stage on screen
        stage.centerOnScreen();
        stage.setScene(scene);

        //stage.setMaxWidth(550);
        //stage.setX(x);
        // stage.setY(y);
        //show the stage
        stage.show();
    }

    private boolean checkregistroexistente() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        if ((tv_generic.getSelectionModel()) != null) {
            SihicApp.nomina = (Nomina) tv_generic.getSelectionModel().getSelectedItem();
            return true;
        }
        else
        {
            return false;
        }
    }

    private void eliminar() throws ParseException {
        //genPersonasControllerClient.delete(genPacientes);
        //getListGenPersonas();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

    }
    
    private void getLastMovimiento() throws ParseException
    {
        SihicApp.libroMayorBalancesControllerClient.getLastMovimiento();
        getRecords();
    }
    private void addNewMovimiento() throws ParseException
    {
      SihicApp.libroMayorBalancesControllerClient.addNewMovimiento();
      getRecords();
    }
    private void volveracalcularlibromayorbalances() throws ParseException
    {
        if(extraermesaño())
        {
          SihicApp.libroMayorBalancesControllerClient.volveracalcularlibromayorbalances(mes,año);
          getRecords();
        }
    }
   private boolean extraermesaño()
    {
       if (SihicApp.li_libromayorbalances.size()>0)
       {
            mes=SihicApp.li_libromayorbalances.get(0).getMes();
            año=SihicApp.li_libromayorbalances.get(0).getAño();
            return true;
        }
        else
        {
            return false;
        }  
    }
   
   private static void crearbalancedeprueba()
   {
       SihicApp.li_balaBalancePruebas.clear();
       for(LibroMayorBalances lb: SihicApp.li_libromayorbalances)
       {
            if(lb.getConPuc().getCodigo().substring(0,1).equals("1") || lb.getConPuc().getCodigo().substring(0,1).equals("5")||lb.getConPuc().getCodigo().substring(0,1).equals("6"))
           {
               SihicApp.balancePrueba=null;
                SihicApp.balancePrueba=new BalancePrueba(lb.getConPuc().getCodigo(), lb.getConPuc().getNombre(), lb.getSaldodebito().compareTo(BigDecimal.ZERO)==1?lb.getSaldodebito():lb.getSaldocredito(),BigDecimal.ZERO);
          
            } 
           else
           {
               if(lb.getConPuc().getCodigo().substring(0,1).equals("2") || lb.getConPuc().getCodigo().substring(0,1).equals("3")||lb.getConPuc().getCodigo().substring(0,1).equals("4"))
           {
                SihicApp.balancePrueba=null;
                SihicApp.balancePrueba=new BalancePrueba(lb.getConPuc().getCodigo(), lb.getConPuc().getNombre(), BigDecimal.ZERO, lb.getSaldodebito().compareTo(BigDecimal.ZERO)==1?lb.getSaldodebito():lb.getSaldocredito());
          
           } 
               
           }
           SihicApp.li_balaBalancePruebas.add( SihicApp.balancePrueba);
       }
   }        
  public void fpdf_estadosituacionfinanciera() throws IOException, BadElementException, DocumentException, URISyntaxException {
        String especialidad = "";
        BigDecimal total = BigDecimal.ZERO;
        
        Document document = new Document();
        Font fuente = new Font();
       
        document = new Document(PageSize.LETTER, 2, 2, 2, 2);
        PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/esf.pdf"));
        document.open();

        PdfPTable tableMaster = new PdfPTable(1);
        //PdfPTable table = new PdfPTable(new float[] { 5, 5});

        PdfPTable table = new PdfPTable(new float[]{10});
        PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
        PdfPTable tableesf = new PdfPTable(new float[]{7, 3});
        Image imagen;
        tableMaster.getDefaultCell().setBorderWidth(0);
        imagen = Image.getInstance(SihicApp.genUnidadesOrganizacion.getLogo());
        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setFixedHeight(40f);
        tableImagenTexto.addCell(imagen);
        tableImagenTexto.getDefaultCell().setFixedHeight(0f);
        table.setWidthPercentage(100f);
        tableesf.setWidthPercentage(100f);
        tableMaster.setWidthPercentage(100f);
        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        fuente = FontFactory.getFont("arial", 14, Font.BOLD);
        tableImagenTexto.addCell(new Paragraph(SihicApp.genUnidadesOrganizacion.getNombre(), fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableImagenTexto.getDefaultCell().setColspan(2);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableImagenTexto.addCell(new Paragraph("Nit.: "+SihicApp.genUnidadesOrganizacion.getNit(), fuente));
        tableImagenTexto.addCell(new Paragraph(SihicApp.genUnidadesOrganizacion.getDireccion()+" TEL.  "+SihicApp.genUnidadesOrganizacion.getTelefono(), fuente));
        table.getDefaultCell().setPadding(10);
        table.setWidthPercentage(100f);
        table.addCell(tableImagenTexto);
        //tabla datos de cliente
        table.getDefaultCell().setPadding(7);
        tableesf.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        tableesf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableesf.getDefaultCell().setColspan(2);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
 tableesf.getDefaultCell().setColspan(2);
        fuente = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
        tableesf.getDefaultCell().setBorder(0);
        tableesf.addCell(new Paragraph("Estado de Situación Financiera al "+sihic.util.UtilDate.calculardiasdelmes(SihicApp.li_libromayorbalances.get(0).getAño(), SihicApp.li_libromayorbalances.get(0).getMes()-1)+" de "+sihic.util.UtilDate.obtenerMes(SihicApp.li_libromayorbalances.get(0).getAño(), SihicApp.li_libromayorbalances.get(0).getMes())+" de "+SihicApp.li_libromayorbalances.get(0).getAño(), fuente));
       fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
         tableesf.addCell(new Paragraph("Bajo NIIF para Pymes", fuente));
       tableesf.addCell(new Paragraph("(En Pesos Colombianos $COP)", fuente));
        tableesf.getDefaultCell().setColspan(1);
        tableesf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
       
        tableesf.addCell(new Paragraph("Activos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph(String.valueOf(SihicApp.li_libromayorbalances.get(0).getAño()),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Activos Corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("",fuente));
        tableesf.addCell(new Paragraph("Efectivo y equivalentes al efectivo", fuente));
      
        Double efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("11")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        Double efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("11")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        BigDecimal tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf((tot)),fuente));
        
        tableesf.addCell(new Paragraph("Cuentas comerciales por cobrar y otras cuentas por cobrar corrientes", fuente));
       
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("13")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("13")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
          tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
           tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf((tot)),fuente));
        
        tableesf.addCell(new Paragraph("Otros activos financieros corrientes", fuente));
       
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("14") || lb.getConPuc().getCodigo().substring(0,2).equals("12")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("14")|| lb.getConPuc().getCodigo().substring(0,2).equals("12")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
           tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
           tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
      fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total Activos Corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("14") || lb.getConPuc().getCodigo().substring(0,2).equals("12") || lb.getConPuc().getCodigo().substring(0,2).equals("11") || lb.getConPuc().getCodigo().substring(0,2).equals("13")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("14")|| lb.getConPuc().getCodigo().substring(0,2).equals("12") || lb.getConPuc().getCodigo().substring(0,2).equals("11") || lb.getConPuc().getCodigo().substring(0,2).equals("13")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Activos no corrientes", fuente));
        tableesf.addCell(new Paragraph("", fuente));
         fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
         tableesf.addCell(new Paragraph("Propiedades, planta y equipo", fuente));
         efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
         tableesf.addCell(new Paragraph("Activos intangibles distintos de la plusvalía", fuente));
         efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("17")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("17")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
         tableesf.addCell(new Paragraph("Otros activos financieros no corrientes", fuente));
         efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("18")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("18")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total Activos No Corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15") || lb.getConPuc().getCodigo().substring(0,2).equals("17") || lb.getConPuc().getCodigo().substring(0,2).equals("18")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15")|| lb.getConPuc().getCodigo().substring(0,2).equals("17") || lb.getConPuc().getCodigo().substring(0,2).equals("18")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.addCell(new Paragraph("Total Activos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15") || lb.getConPuc().getCodigo().substring(0,2).equals("17") || lb.getConPuc().getCodigo().substring(0,2).equals("18") || lb.getConPuc().getCodigo().substring(0,2).equals("11") || lb.getConPuc().getCodigo().substring(0,2).equals("12") || lb.getConPuc().getCodigo().substring(0,2).equals("13") || lb.getConPuc().getCodigo().substring(0,2).equals("14")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15")|| lb.getConPuc().getCodigo().substring(0,2).equals("17") || lb.getConPuc().getCodigo().substring(0,2).equals("18") || lb.getConPuc().getCodigo().substring(0,2).equals("12") || lb.getConPuc().getCodigo().substring(0,2).equals("13") || lb.getConPuc().getCodigo().substring(0,2).equals("14")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.getDefaultCell().setColspan(2);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0.25);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0.25);
        tableesf.addCell(new Paragraph("",fuente));
       
        tableesf.getDefaultCell().setBorderWidth((float) 0);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0);
        //*************************pasivos******************************
        tableesf.getDefaultCell().setColspan(1);
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
         tableesf.addCell(new Paragraph("Patrimonio y pasivos ", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("",fuente));
        tableesf.addCell(new Paragraph("Pasivos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("",fuente));
        tableesf.addCell(new Paragraph("Pasivos corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("",fuente));
        tableesf.addCell(new Paragraph("Provisiones corrientes por beneficios a los empleados", fuente));
      
         efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("23")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
         efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("23")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
         tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf((tot)),fuente));
        
        tableesf.addCell(new Paragraph("Cuentas por pagar comerciales y otras cuentas por pagar", fuente));
       
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf((tot)),fuente));
        
        tableesf.addCell(new Paragraph("Pasivos por impuestos corrientes, corriente", fuente));
       
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("22") ).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("22")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
         tableesf.addCell(new Paragraph("Otros pasivos financieros corrientes", fuente));
       
        efectivodebito=0.0;//SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("22") ).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=0.0;//=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("22")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total pasivos Corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21") || lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21")|| lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Pasivos no corrientes", fuente));
         tableesf.addCell(new Paragraph("", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("Otros pasivos financieros no corrientes", fuente));
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("24")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("24")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
         tableesf.addCell(new Paragraph("Otros pasivos no financieros no corrientes", fuente));
         efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("25")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("25")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total de pasivos no corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("24")|| lb.getConPuc().getCodigo().substring(0,2).equals("25")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.addCell(new Paragraph("Total Pasivos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21") || lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") || lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") || lb.getConPuc().getCodigo().substring(0,2).equals("26")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21")|| lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") || lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") || lb.getConPuc().getCodigo().substring(0,2).equals("26")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        
        
        tableesf.getDefaultCell().setColspan(2);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0.25);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0.25);
        tableesf.addCell(new Paragraph("",fuente));
        
        //*************************************************************************************************************************
     
        tableesf.getDefaultCell().setBorderWidth((float) 0);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0);
        
        
        //***************************************************************
          tableesf.getDefaultCell().setColspan(1);
             fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Patrimonio", fuente));
         tableesf.addCell(new Paragraph("", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("Capital emitido", fuente));
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.addCell(new Paragraph("Otras reservas", fuente));
         efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("33")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("33")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.addCell(new Paragraph("Superavit por revaluación", fuente));
         efectivodebito=0.0;//SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("25")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=0.0;//SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("25")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.addCell(new Paragraph("Ganancias acumuladas", fuente));
         efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("35")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("35")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total patrimonio", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31") || lb.getConPuc().getCodigo().substring(0,2).equals("33") || lb.getConPuc().getCodigo().substring(0,2).equals("35")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31")|| lb.getConPuc().getCodigo().substring(0,2).equals("33")|| lb.getConPuc().getCodigo().substring(0,2).equals("35")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
          tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
     
          //*************************************************************
            //*************************************************************************************************************************
          tableesf.getDefaultCell().setColspan(2);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0.25);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0.25);
        tableesf.addCell(new Paragraph("",fuente));
          
        tableesf.getDefaultCell().setBorderWidth((float) 0);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0);
          tableesf.getDefaultCell().setColspan(1);
        tableesf.addCell(new Paragraph("Total patrimonio y pasivos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31") || lb.getConPuc().getCodigo().substring(0,2).equals("33") || lb.getConPuc().getCodigo().substring(0,2).equals("35")|| lb.getConPuc().getCodigo().substring(0,2).equals("21") || lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") || lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") || lb.getConPuc().getCodigo().substring(0,2).equals("26")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31")|| lb.getConPuc().getCodigo().substring(0,2).equals("33")|| lb.getConPuc().getCodigo().substring(0,2).equals("35")|| lb.getConPuc().getCodigo().substring(0,2).equals("21") || lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") || lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") || lb.getConPuc().getCodigo().substring(0,2).equals("26")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
   
        fuente = FontFactory.getFont("Times-Roman", 8, Font.NORMAL);
        tableesf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        tableesf.getDefaultCell().setPadding(5);
        tableesf.getDefaultCell().setPadding(10);
        tableesf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        fuente = FontFactory.getFont("Times-Roman", 9, Font.BOLD);
        tableesf.addCell(new Paragraph("Firma quien Aprueba: ________________________", fuente));
        tableesf.addCell(new Paragraph("Validez de este documento 30 días", fuente));
        table.addCell(tableesf);
        tableMaster.addCell(table);
        document.add(tableMaster);

        // step 5
        document.close();
         File file = new File("///home/adminlinux/sihic/esf.pdf");
try { 
 
 String os = System.getProperty("os.name"); 
 System.out.println("os->"+os);
 if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
 { 
  Runtime r = Runtime.getRuntime(); 
  Process p = r.exec("/usr/bin/evince /home/adminlinux/sihic/esf.pdf"); 
  }
else
 {
      if (!Desktop.isDesktopSupported() 
 && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) { 
    Desktop.getDesktop().open(file);
 }
 }
 
 
} catch (Exception t) { 
   
}

       

       
       

    }
   public void fpdf_estadoresultadosintegrales() throws IOException, BadElementException, DocumentException, URISyntaxException {
        String especialidad = "";
        BigDecimal total = BigDecimal.ZERO;
        
        Document document = new Document();
        Font fuente = new Font();
       
        document = new Document(PageSize.LETTER, 2, 2, 2, 2);
        PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/eri.pdf"));
        document.open();

        PdfPTable tableMaster = new PdfPTable(1);
        //PdfPTable table = new PdfPTable(new float[] { 5, 5});

        PdfPTable table = new PdfPTable(new float[]{10});
        PdfPTable tableImagenTexto = new PdfPTable(new float[]{3, 7});
        PdfPTable tableesf = new PdfPTable(new float[]{7, 3});
        Image imagen;
        tableMaster.getDefaultCell().setBorderWidth(0);
        imagen = Image.getInstance(SihicApp.genUnidadesOrganizacion.getLogo());
        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setFixedHeight(40f);
        tableImagenTexto.addCell(imagen);
        tableImagenTexto.getDefaultCell().setFixedHeight(0f);
        table.setWidthPercentage(100f);
        tableesf.setWidthPercentage(100f);
        tableMaster.setWidthPercentage(100f);
        tableImagenTexto.getDefaultCell().setBorderWidthBottom(0);
        tableImagenTexto.getDefaultCell().setBorderWidthTop(0);
        tableImagenTexto.getDefaultCell().setBorderWidthLeft(0);
        tableImagenTexto.getDefaultCell().setBorderWidthRight(0);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);

        fuente = FontFactory.getFont("arial", 14, Font.BOLD);
        tableImagenTexto.addCell(new Paragraph(SihicApp.genUnidadesOrganizacion.getNombre(), fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableImagenTexto.getDefaultCell().setColspan(2);
        tableImagenTexto.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableImagenTexto.addCell(new Paragraph("Nit.: "+SihicApp.genUnidadesOrganizacion.getNit(), fuente));
        tableImagenTexto.addCell(new Paragraph(SihicApp.genUnidadesOrganizacion.getDireccion()+" TEL.  "+SihicApp.genUnidadesOrganizacion.getTelefono(), fuente));
        table.getDefaultCell().setPadding(10);
        table.setWidthPercentage(100f);
        table.addCell(tableImagenTexto);
        //tabla datos de cliente
        table.getDefaultCell().setPadding(7);
        tableesf.getDefaultCell().setBackgroundColor(BaseColor.WHITE);
        tableesf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        tableesf.getDefaultCell().setColspan(2);
        table.getDefaultCell().setVerticalAlignment(Element.ALIGN_TOP);
 tableesf.getDefaultCell().setColspan(2);
        fuente = FontFactory.getFont("Times-Roman", 12, Font.BOLD);
        tableesf.getDefaultCell().setBorder(0);
        tableesf.addCell(new Paragraph("Estado de Resultados Integrales del año terminado el "+sihic.util.UtilDate.calculardiasdelmes(SihicApp.li_libromayorbalances.get(0).getAño(), 11)+" de "+sihic.util.UtilDate.obtenerMes(SihicApp.li_libromayorbalances.get(0).getAño(), SihicApp.li_libromayorbalances.get(0).getMes())+" de "+SihicApp.li_libromayorbalances.get(0).getAño(), fuente));
       fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
         tableesf.addCell(new Paragraph("Bajo NIIF para Pymes", fuente));
       tableesf.addCell(new Paragraph("(En Pesos Colombianos $COP)", fuente));
        tableesf.getDefaultCell().setColspan(1);
        tableesf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
       
        tableesf.addCell(new Paragraph("Resultado", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph(String.valueOf(SihicApp.li_libromayorbalances.get(0).getAño()),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Activos Corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("",fuente));
        tableesf.addCell(new Paragraph("Efectivo y equivalentes al efectivo", fuente));
      
        Double efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("11")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        Double efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("11")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        BigDecimal tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf((tot)),fuente));
        
        tableesf.addCell(new Paragraph("Cuentas comerciales por cobrar y otras cuentas por cobrar corrientes", fuente));
       
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("13")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("13")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
          tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
           tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf((tot)),fuente));
        
        tableesf.addCell(new Paragraph("Otros activos financieros corrientes", fuente));
       
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("14") || lb.getConPuc().getCodigo().substring(0,2).equals("12")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("14")|| lb.getConPuc().getCodigo().substring(0,2).equals("12")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
           tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
           tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
      fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total Activos Corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("14") || lb.getConPuc().getCodigo().substring(0,2).equals("12") || lb.getConPuc().getCodigo().substring(0,2).equals("11") || lb.getConPuc().getCodigo().substring(0,2).equals("13")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("14")|| lb.getConPuc().getCodigo().substring(0,2).equals("12") || lb.getConPuc().getCodigo().substring(0,2).equals("11") || lb.getConPuc().getCodigo().substring(0,2).equals("13")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Activos no corrientes", fuente));
        tableesf.addCell(new Paragraph("", fuente));
         fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
         tableesf.addCell(new Paragraph("Propiedades, planta y equipo", fuente));
         efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
         tableesf.addCell(new Paragraph("Activos intangibles distintos de la plusvalía", fuente));
         efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("17")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("17")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
         tableesf.addCell(new Paragraph("Otros activos financieros no corrientes", fuente));
         efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("18")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("18")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total Activos No Corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15") || lb.getConPuc().getCodigo().substring(0,2).equals("17") || lb.getConPuc().getCodigo().substring(0,2).equals("18")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15")|| lb.getConPuc().getCodigo().substring(0,2).equals("17") || lb.getConPuc().getCodigo().substring(0,2).equals("18")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.addCell(new Paragraph("Total Activos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15") || lb.getConPuc().getCodigo().substring(0,2).equals("17") || lb.getConPuc().getCodigo().substring(0,2).equals("18") || lb.getConPuc().getCodigo().substring(0,2).equals("11") || lb.getConPuc().getCodigo().substring(0,2).equals("12") || lb.getConPuc().getCodigo().substring(0,2).equals("13") || lb.getConPuc().getCodigo().substring(0,2).equals("14")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("15")|| lb.getConPuc().getCodigo().substring(0,2).equals("17") || lb.getConPuc().getCodigo().substring(0,2).equals("18") || lb.getConPuc().getCodigo().substring(0,2).equals("12") || lb.getConPuc().getCodigo().substring(0,2).equals("13") || lb.getConPuc().getCodigo().substring(0,2).equals("14")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.getDefaultCell().setColspan(2);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0.25);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0.25);
        tableesf.addCell(new Paragraph("",fuente));
       
        tableesf.getDefaultCell().setBorderWidth((float) 0);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0);
        //*************************pasivos******************************
        tableesf.getDefaultCell().setColspan(1);
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
         tableesf.addCell(new Paragraph("Patrimonio y pasivos ", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("",fuente));
        tableesf.addCell(new Paragraph("Pasivos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("",fuente));
        tableesf.addCell(new Paragraph("Pasivos corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("",fuente));
        tableesf.addCell(new Paragraph("Provisiones corrientes por beneficios a los empleados", fuente));
      
         efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("23")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
         efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("23")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
         tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf((tot)),fuente));
        
        tableesf.addCell(new Paragraph("Cuentas por pagar comerciales y otras cuentas por pagar", fuente));
       
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf((tot)),fuente));
        
        tableesf.addCell(new Paragraph("Pasivos por impuestos corrientes, corriente", fuente));
       
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("22") ).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("22")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
         tableesf.addCell(new Paragraph("Otros pasivos financieros corrientes", fuente));
       
        efectivodebito=0.0;//SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("22") ).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=0.0;//=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("22")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total pasivos Corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21") || lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21")|| lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Pasivos no corrientes", fuente));
         tableesf.addCell(new Paragraph("", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("Otros pasivos financieros no corrientes", fuente));
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("24")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("24")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
         tableesf.addCell(new Paragraph("Otros pasivos no financieros no corrientes", fuente));
         efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("25")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("25")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total de pasivos no corrientes", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("24")|| lb.getConPuc().getCodigo().substring(0,2).equals("25")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.addCell(new Paragraph("Total Pasivos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21") || lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") || lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") || lb.getConPuc().getCodigo().substring(0,2).equals("26")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("21")|| lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") || lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") || lb.getConPuc().getCodigo().substring(0,2).equals("26")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        
        
        tableesf.getDefaultCell().setColspan(2);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0.25);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0.25);
        tableesf.addCell(new Paragraph("",fuente));
        
        //*************************************************************************************************************************
     
        tableesf.getDefaultCell().setBorderWidth((float) 0);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0);
        
        
        //***************************************************************
          tableesf.getDefaultCell().setColspan(1);
             fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Patrimonio", fuente));
         tableesf.addCell(new Paragraph("", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        tableesf.addCell(new Paragraph("Capital emitido", fuente));
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.addCell(new Paragraph("Otras reservas", fuente));
         efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("33")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("33")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.addCell(new Paragraph("Superavit por revaluación", fuente));
         efectivodebito=0.0;//SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("25")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=0.0;//SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("25")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        tableesf.addCell(new Paragraph("Ganancias acumuladas", fuente));
         efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("35")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("35")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
        fuente = FontFactory.getFont("arial", 12, Font.BOLD);
        tableesf.addCell(new Paragraph("Total patrimonio", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31") || lb.getConPuc().getCodigo().substring(0,2).equals("33") || lb.getConPuc().getCodigo().substring(0,2).equals("35")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31")|| lb.getConPuc().getCodigo().substring(0,2).equals("33")|| lb.getConPuc().getCodigo().substring(0,2).equals("35")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
          tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
     
          //*************************************************************
            //*************************************************************************************************************************
          tableesf.getDefaultCell().setColspan(2);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0.25);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0.25);
        tableesf.addCell(new Paragraph("",fuente));
          
        tableesf.getDefaultCell().setBorderWidth((float) 0);
        tableesf.getDefaultCell().setBorderWidthBottom((float) 0);
        tableesf.getDefaultCell().setBorderWidthTop((float) 0);
          tableesf.getDefaultCell().setColspan(1);
        tableesf.addCell(new Paragraph("Total patrimonio y pasivos", fuente));
        fuente = FontFactory.getFont("arial", 12, Font.NORMAL);
        efectivodebito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31") || lb.getConPuc().getCodigo().substring(0,2).equals("33") || lb.getConPuc().getCodigo().substring(0,2).equals("35")|| lb.getConPuc().getCodigo().substring(0,2).equals("21") || lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") || lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") || lb.getConPuc().getCodigo().substring(0,2).equals("26")).mapToDouble(o -> o.getSaldodebito().doubleValue()).sum();
        efectivocredito=SihicApp.li_libromayorbalances.stream().filter(lb->lb.getConPuc().getCodigo().substring(0,2).equals("31")|| lb.getConPuc().getCodigo().substring(0,2).equals("33")|| lb.getConPuc().getCodigo().substring(0,2).equals("35")|| lb.getConPuc().getCodigo().substring(0,2).equals("21") || lb.getConPuc().getCodigo().substring(0,2).equals("22") || lb.getConPuc().getCodigo().substring(0,2).equals("23") || lb.getConPuc().getCodigo().substring(0,2).equals("24") || lb.getConPuc().getCodigo().substring(0,2).equals("25") || lb.getConPuc().getCodigo().substring(0,2).equals("26")).mapToDouble(o -> o.getSaldocredito().doubleValue()).sum();
        tot=BigDecimal.valueOf(efectivodebito+efectivocredito);
        tot = tot.setScale(2, RoundingMode.HALF_UP);
        tableesf.addCell(new Paragraph(String.valueOf(tot),fuente));
   
        fuente = FontFactory.getFont("Times-Roman", 8, Font.NORMAL);
        tableesf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
        tableesf.getDefaultCell().setPadding(5);
        tableesf.getDefaultCell().setPadding(10);
        tableesf.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        fuente = FontFactory.getFont("Times-Roman", 9, Font.BOLD);
        tableesf.addCell(new Paragraph("Firma quien Aprueba: ________________________", fuente));
        tableesf.addCell(new Paragraph("Validez de este documento 30 días", fuente));
        table.addCell(tableesf);
        tableMaster.addCell(table);
        document.add(tableMaster);

        // step 5
        document.close();
         File file = new File("///home/adminlinux/sihic/esf.pdf");
try { 
 
 String os = System.getProperty("os.name"); 
 System.out.println("os->"+os);
 if (!(os.startsWith("Mac OS")) && !(os.startsWith("Windows"))) 
 { 
  Runtime r = Runtime.getRuntime(); 
  Process p = r.exec("/usr/bin/evince /home/adminlinux/sihic/esf.pdf"); 
  }
else
 {
      if (!Desktop.isDesktopSupported() 
 && Desktop.getDesktop().isSupported(Desktop.Action.OPEN)) { 
    Desktop.getDesktop().open(file);
 }
 }
 
 
} catch (Exception t) { 
   
}

       

       
       

    }
  
}
