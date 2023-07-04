package sihic.contabilidad;

import sihic.PageBrowser;
import sihic.SearchPopover;
import sihic.control.SearchBox;
import sihic.general.LoadChoiceBoxGeneral;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.BarcodeEAN;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import message.EstadosMensaje;
import modelo.GenCategoriasProductos;
import modelo.Producto;
import sihic.SihicApp;
import static sihic.SihicApp.s_productoControllerClient;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FProducto extends Application {

    ObservableList<String> ov = FXCollections.<String>observableArrayList();
    StackPane stack;
    private static String title = "";
    private static String titleprecio = "";
    private GridPane gp_generic;
    private TextField codigo;
    private TextField codigobarras;
    private SearchBox sb_proveedor = new SearchBox();
    private SearchPopover sp_proveedor;
    private SearchBox sb_categoria = new SearchBox();
    private SearchPopover sp_categoria;
    private TextField nombre;
    private TextArea descripcion;
    private TextField precio2015;
    private TextField precio2016;
    private TextField precio2017;
    private TextField precio2018;
    private TextField topminimo;
    private TextField topmaximo;
    private TextField iva;
    private Label la_presentacioncomercial;
    private TextField presentaciocomercial;
    private Label la_medformasfarmaceuticas;
    private TextField medformasfarmaceuticas;
    private Label la_laboratorio;
    private TextField laboratorio;
    private Label la_unidadmedida;
    private TextField unidamedida;
    private Label la_reginvima;
    private TextField reginvima;
    private Button save;
    private Button nuevo;
    EstadosMensaje estadosMensaje;
    private Stage stage;
    private HBox hb_botones;
    private ImageView logo;
    private ImageView codbarras;
    private Button bu_logo;
    private Button bu_codebar;
    private Button bu_gencodebar;
    private Image loadlogo;
    private GridPane Gp_Message;
    private Label L_Message;
    private Runnable Task_Message;
    private boolean permitirproceso = false;
    Thread backgroundThread;
    private File fileLogo;
    private FileChooser fileChooser = new FileChooser();
    private WritableImage wi;
    private BufferedImage bi;
    private DecimalFormat format = new DecimalFormat("#.0");

    public Parent createContent() throws IOException {
        findByCodigo();
        sp_categoria = new SearchPopover(sb_categoria, new PageBrowser(), SihicApp.genCategoriasProductos, false);
        sp_categoria.setMaxWidth(300);
        sb_categoria.setMinWidth(300);
        sb_proveedor.setMinWidth(300);
        logo = new ImageView("/images/foto.png");
        logo.setFitHeight(100);
        logo.setFitWidth(100);
        codbarras = new ImageView();
        codbarras.setFitHeight(20);
        codbarras.setFitWidth(100);
        //***********************************************  
        estadosMensaje = new EstadosMensaje();
        stack = new StackPane();

        codigo = new TextField("");
        codigo.setMaxWidth(350);

        topminimo = new TextField("");
        topmaximo = new TextField("");
        codigo.getProperties().put("requerido", true);
        iva = new TextField("");
        iva.getProperties().put("requerido", true);
        codigobarras = new TextField("");
        codigobarras.getProperties().put("requerido", false);
        codigobarras.setMinWidth(130);
        precio2015 = new TextField("");
        precio2016 = new TextField("");
        precio2016.setMaxWidth(350);
        precio2017 = new TextField("");
        precio2018 = new TextField("");
        precio2015.setTextFormatter(new TextFormatter<>(c
                -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));
        precio2016.setTextFormatter(new TextFormatter<>(c
                -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));
        precio2017.setTextFormatter(new TextFormatter<>(c
                -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));
        precio2018.setTextFormatter(new TextFormatter<>(c
                -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));

        codigobarras.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue) {
                if (codigobarras.getText() != null) {
                    if (newPropertyValue) {
                        System.out.println("new value" + codigobarras.getText());
                    } else {

                        System.out.println("old value" + codigobarras.getText());
                        try {
                            if (codigobarras.getText().length() >= 13) {
                                fcodigobarras(codigobarras.getText());
                            }
                        } catch (DocumentException ex) {
                            Logger.getLogger(FMedicamento.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(FMedicamento.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }
            }
        });
        nombre = new TextField("");
        nombre.getProperties().put("requerido", true);
        nombre.setMinWidth(350);

        descripcion = new TextArea("");
        descripcion.setMaxWidth(350);

        la_laboratorio = new Label("Laboratorio:");
        la_laboratorio.setMinWidth(130);
        laboratorio = new TextField();
        laboratorio.setMaxWidth(350);
        la_reginvima = new Label("Registro Invima:");
        reginvima = new TextField();
        la_medformasfarmaceuticas = new Label("F. Farmaceutica:");
        medformasfarmaceuticas = new TextField();
        medformasfarmaceuticas.setMaxWidth(350);

        la_presentacioncomercial = new Label("P. Comercial:");
        presentaciocomercial = new TextField();
        presentaciocomercial.setMaxWidth(350);
        la_unidadmedida = new Label("Unidad Medida:");
        unidamedida = new TextField();
        ImageView imageView = new ImageView("/images/save.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);

        save = new Button("", imageView);
        save.setTooltip(new Tooltip("Guardar Producto"));
        hb_botones = new HBox(2);

        save.setOnAction(e -> {
            try {
                save();
            } catch (InterruptedException ex) {

            }
        });
        imageView = null;
        imageView = new ImageView("/images/new2.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);

        nuevo = new Button("", imageView);
        nuevo.setTooltip(new Tooltip("Nuevo Producto"));

        nuevo.setOnAction(e -> {
            nuevo();

        });
        imageView = null;
        imageView = new ImageView("/images/img.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);

        bu_logo = new Button("", imageView);

        bu_logo.setTooltip(new Tooltip("Cargas Imagen de Producto"));
        bu_logo.setOnAction(e -> {
            LoadImageLogo();

        });
        imageView = null;
        imageView = new ImageView("/images/pdf.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        bu_codebar = new Button("", imageView);
        bu_codebar.setTooltip(new Tooltip("Imprimir Código de Barras Ean13"));
        bu_codebar.setOnAction(e -> {
            try {
                printcodebar();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FMedicamento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(FMedicamento.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        imageView = null;
        imageView = new ImageView("/images/codebar.png");
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        bu_gencodebar = new Button("", imageView);
        bu_gencodebar.setTooltip(new Tooltip("Generar Código de Barras Ean13"));
        bu_gencodebar.setOnAction(e -> {

            try {
                generarcodigobarras();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(FMedicamento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DocumentException ex) {
                Logger.getLogger(FMedicamento.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(FMedicamento.class.getName()).log(Level.SEVERE, null, ex);
            }

        });
        hb_botones = new HBox(10);
        hb_botones.getChildren().addAll(save, nuevo);
        gp_generic = new GridPane();

        gp_generic.add(new Label(title), 0, 0, 1, 1);
        gp_generic.add(nombre, 1, 0, 1, 1);
        gp_generic.add(new Label("Codigo: "), 0, 1, 1, 1);
        gp_generic.add(codigo, 1, 1, 1, 1);
        gp_generic.add(new Label(titleprecio), 0, 2, 1, 1);
        gp_generic.add(precio2015, 1, 2, 1, 1);
        gp_generic.add(new Label("Descripción:"), 0, 3, 1, 1);
        gp_generic.add(descripcion, 1, 3, 1, 1);
        gp_generic.add(hb_botones, 0, 4, 4, 1);
        GridPane.setHalignment(hb_botones, HPos.CENTER);
        gp_generic.getStylesheets().add(SihicApp.hojaestilos);
        gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(2);
        gp_generic.setVgap(2);
        hb_botones.setAlignment(Pos.CENTER);
        gp_generic.setAlignment(Pos.TOP_LEFT);
        // gp_generic.getStylesheets().add("/abaco/SofackarStylesCommon.css");
        // gp_generic.getStyleClass().add("background");
        gp_generic.setHgap(5);
        gp_generic.setVgap(5);
        gp_generic.autosize();
        hb_botones.setAlignment(Pos.CENTER);
        Gp_Message = new GridPane();
        Gp_Message = new GridPane();
        Gp_Message.setMinWidth(gp_generic.getLayoutBounds().getWidth());
        Gp_Message.setMaxHeight(40);
        L_Message = new Label();
        L_Message.getStylesheets().add(SihicApp.hojaestilos);
        L_Message.getStyleClass().add("labelMessage");
        Gp_Message.add(L_Message, 0, 1);
        Gp_Message.setAlignment(Pos.TOP_RIGHT);
        L_Message.alignmentProperty().setValue(Pos.TOP_RIGHT);
        GridPane.setValignment(L_Message, VPos.TOP);
        GridPane.setValignment(Gp_Message, VPos.TOP);
        Gp_Message.setVisible(false);
        stack.setAlignment(Pos.TOP_CENTER);
        //stack.setMinWidth(700);
        stack.getChildren().addAll(gp_generic, Gp_Message);
        crearoeditar();
        return stack;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    private void nuevo() {
        SihicApp.s_producto = null;
        SihicApp.s_producto = new Producto();
        empty_field();
    }

    public void save() throws InterruptedException {
        validar_formulario();
        if (permitirproceso) {
            set_datos();
            try {

                if (SihicApp.s_producto.getId() == null) {
                    SihicApp.productoControllerClient.create();
                    L_Message.setText("Registro Almacenado");
                } else {
                    SihicApp.productoControllerClient.update();
                    L_Message.setText("Registro modificado");
                }
                s_productoControllerClient.findproductosall(null);
                if (SihicApp.nombresolucion.equals("Admin. Planta y Equipo") || SihicApp.nombresolucion.equals("Admin Honorarios y Serv") || SihicApp.nombresolucion.equals("Admin Gastos")) {
                    AdminProductos.getRecords();
                } else {
                    SihicApp.li_producto = SihicApp.productoControllerClient.getRecords(null);
                }
                Task_Message = () -> {
                    try {
                        SetMessage();
                    } catch (InterruptedException ex) {

                    }
                };
                backgroundThread = new Thread(Task_Message);
                // Terminate the running thread if the application exits
                backgroundThread.setDaemon(true);

                // Start the thread
                backgroundThread.start();
            } catch (Exception e) {
                L_Message.setText("Error Almacenando");
                Task_Message = () -> {
                    try {
                        SetMessage();
                    } catch (InterruptedException ex) {

                    }
                };
                backgroundThread = new Thread(Task_Message);
                // Terminate the running thread if the application exits
                backgroundThread.setDaemon(true);

                // Start the thread
                backgroundThread.start();
            }
        }
    }

    private void set_datos() {
        GenCategoriasProductos genCategoriasProductos = new GenCategoriasProductos();
        genCategoriasProductos.setId(Long.valueOf(3));
        SihicApp.s_producto.setCodigo(codigo.getText().trim());
        SihicApp.s_producto.setNombre(nombre.getText());
        SihicApp.s_producto.setPrecio2015(new BigDecimal(precio2015.getText().trim().replace(",", ".")));
        SihicApp.s_producto.setDescrip(descripcion.getText());
        SihicApp.s_producto.setGenCategoriasProductos(SihicApp.genCategoriasProductos);
    }

    public void validar_formulario() throws InterruptedException {
        permitirproceso = true;
        for (Object n : gp_generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                if (general.getProperties().get("requerido") != null) {
                    if ((general.getLength() <= 0) && ((boolean) general.getProperties().get("requerido") == true)) {
                        permitirproceso = false;
                        general.setStyle("-fx-background-color:#ff1600");

                    }
                }

            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;

                    if (general.getProperties().get("requerido") != null) {
                        if (general.getSelectionModel().getSelectedIndex() < 0 && (boolean) general.getProperties().get("requerido") == true) {
                            permitirproceso = false;
                            general.getStylesheets().add(0, "/sihic/personal.css");
                            general.getStylesheets().set(0, "/sihic/personal.css");
                        }
                    }
                }
            }
        }
        FadeTransition ft = new FadeTransition(Duration.millis(2000), save);
        //ft.setFromValue(0.0);
        // ft.setToValue(1);

        ft.play();

        //success.setOpacity(0);
        ft.setOnFinished(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                for (Object n : gp_generic.getChildren().toArray()) {
                    if (n.getClass() == TextField.class) {
                        TextField general = (TextField) n;
                        if (general.getProperties().get("requerido") != null) {
                            System.out.println("propiedad-->" + general.getProperties().get("requerido"));
                            if (((boolean) general.getProperties().get("requerido") == true)) {

                                general.setStyle(null);
                                general.getStyleClass().add("text-field");
                            }
                        }

                    } else {
                        if (n.getClass() == ChoiceBox.class) {
                            ChoiceBox general = (ChoiceBox) n;
                            if (general.getProperties().get("requerido") != null) {
                                if (general.getSelectionModel().getSelectedIndex() < 0 && (boolean) general.getProperties().get("requerido") == true) {
                                    general.getStylesheets().set(0, SihicApp.hojaestilos);

                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private void SetMessage() throws InterruptedException {

        Gp_Message.setVisible(true);
        //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
        Thread.sleep(3000);
        Gp_Message.setVisible(false);
//         backgroundThread.setDaemon(false);
        Task_Message = null;

    }
// A change listener to track the change in selected index

    public void indexChanged(ObservableValue<? extends Number> observable,
            Number oldValue,
            Number newValue) {

    }

    public void LoadImageLogo() {

        fileLogo = fileChooser.showOpenDialog(stage);

        if (fileLogo != null) {
            byte[] img2;
            try {
                bi = ImageIO.read(fileLogo);

                wi = SwingFXUtils.toFXImage(bi, wi);
                loadlogo = wi;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        }

        logo.setImage(loadlogo);
    }

    public void save_img() {
        byte[] data;
        InputStream in;
        try {
            in = new FileInputStream(fileLogo);
            data = new byte[in.available()];
            in.read(data);
            SihicApp.s_producto.setImg(data);
        } catch (Exception e) {

        }
    }

    public void getDatos() {

        codigo.setText(SihicApp.s_producto.getCodigo());
        nombre.setText(SihicApp.s_producto.getNombre());
        descripcion.setText(SihicApp.s_producto.getDescrip());
        precio2015.setText(SihicApp.s_producto.getPrecio2015().toString().replace(".", ","));

    }

    public void crearoeditar() {

        if (SihicApp.s_producto != null) {
            getDatos();

        } else {
            SihicApp.s_producto = new Producto();
            nuevo();
        }

    }

    private void fcodigobarras(String codigo) throws FileNotFoundException, DocumentException, IOException {
        if (codigo == null) {
            codigo = "9781935182610";
        }
        System.out.println("codigo->" + codigo);
        //Es el tipo de clase 
        /*  BarcodeEAN codeEAN = new BarcodeEAN();
   //Seteo el tipo de codigo
   codeEAN.setCodeType(Barcode.EAN13);
   //Setep el codigo
   codeEAN.setCode("9781935182610");
   //Paso el codigo a imagen
   img = codeEAN.createImageWithBarcode( pdfw.getDirectContent(), BaseColor.GREEN, BaseColor.RED);
       bi = ImageIO.read(in);
       wi=new WritableImage(1,1);
       wi=SwingFXUtils.toFXImage(bi, wi);
       //img=new ImageView();
      Image loadcodbarras=wi;
       codbarras.setImage(loadcodbarras);
         */
        BarcodeEAN codeEAN = new BarcodeEAN();
        //Seteo el tipo de codigo
        codeEAN.setCodeType(Barcode.EAN13);
        //Setep el codigo
        codeEAN.setCode(codigo);
        codeEAN.setGuardBars(true);
        // codeEAN.setAltText("9781935182610");
        Barcode39 code39ext = new Barcode39();
        code39ext.setCode("1234567");
        code39ext.setStartStopText(false);
        code39ext.setExtended(true);
        Barcode128 code128 = new Barcode128();
        code128.setCode("9781935182610");
        java.awt.Image rawImage = codeEAN.createAwtImage(Color.blue, Color.WHITE);
        BufferedImage outImage = new BufferedImage(rawImage.getWidth(null), rawImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
        outImage.getGraphics().drawImage(rawImage, 0, 0, null);
        ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        ImageIO.write(outImage, "png", bytesOut);
        bytesOut.flush();
        byte[] pngImageData = bytesOut.toByteArray();
        InputStream in = new ByteArrayInputStream(pngImageData);
        bi = ImageIO.read(in);
        wi = new WritableImage(1, 1);
        wi = SwingFXUtils.toFXImage(bi, wi);
        //img=new ImageView();
        loadlogo = wi;
        codbarras.setImage(loadlogo);

    }

    private void empty_field() {
        for (Object n : gp_generic.getChildren().toArray()) {
            if (n.getClass() == TextField.class) {
                TextField general = (TextField) n;
                general.setText("");

            } else {
                if (n.getClass() == ChoiceBox.class) {
                    ChoiceBox general = (ChoiceBox) n;

                    general.getSelectionModel().select(-1);

                } else {
                    if (n.getClass() == TextArea.class) {
                        TextArea general = (TextArea) n;

                        general.setText("");

                    } else {
                        if (n.getClass() == RadioButton.class) {
                            RadioButton general = (RadioButton) n;

                            general.setSelected(false);

                        } else {
                            if (n.getClass() == CheckBox.class) {
                                CheckBox general = (CheckBox) n;

                                general.setSelected(false);

                            }
                        }
                    }
                }
            }
        }

    }

    private void printcodebar() throws FileNotFoundException, DocumentException {
        /* Tamamaño en Cm de la pagina
    Ejemplo: 216x720 points
    216pt/72 points por inch=3inch
    720pt/72 points por inch=10inch
    El Tamaños de la pagina es 3 inch x 10 inch
    3 inch x 2.54=7.62 cm
    10 inch x 2.54=25.5 cm
         */
        if (codigobarras.getText().length() >= 13) {

            Rectangle one = new Rectangle(84, 40);
            Document document = new Document(one, 2, 2, 2, 2);
            PdfWriter pw = PdfWriter.getInstance(document, new FileOutputStream("/home/adminlinux/sihic/codigobarras.pdf"));
            document.open();
            BarcodeEAN codeEAN = new BarcodeEAN();
            //Seteo el tipo de codigo
            codeEAN.setCodeType(Barcode.EAN13);
            //Setep el codigo
            //codeEAN.setSize(16);
            codeEAN.setCode(codigobarras.getText());
            //Paso el codigo a imagen
            com.itextpdf.text.Image img = codeEAN.createImageWithBarcode(pw.getDirectContent(), BaseColor.GREEN, BaseColor.RED);
            document.add(img);
            document.close();
        }

    }

    private void generarcodigobarras() throws FileNotFoundException, DocumentException, IOException {
        String codigopais = "770";
        String codempresa = "00000";
        for (int i = 1; i <= 9999; i++) {
            String codebar = codigopais + codempresa + getcerosizquierda(String.valueOf(i)) + "7";
            List<Producto> li_prod = SihicApp.li_producto.stream().filter(line -> line.getCodigo().toUpperCase().contains(codebar)) //filters the line, equals to "mkyong"
                    .collect(Collectors.toList());
            if (li_prod.size() <= 0) {
                codigobarras.setText(codebar);
                break;
            }
        }
        fcodigobarras(codigobarras.getText());
    }

    public String getcerosizquierda(String codigo) {

        String cerosizq = "";

        for (int i = 4; i > codigo.length(); i--) {
            cerosizq = cerosizq + "0";
        }
        cerosizq = cerosizq + codigo;
        return cerosizq;
    }

    /**
     * Java main for when running without JavaFX launcher
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static void findByCodigo() {
        switch (SihicApp.nombresolucion) {
            case "Admin. Planta y Equipo":

                title = "Propiedad Planta y Equipo:";
                titleprecio = "Valor Planta y Equipo:";
                break;
            case "Compra. Planta Equipo":

                title = "Propiedad Planta y Equipo:";
                titleprecio = "Valor Planta y Equipo:";
                break;
            case "Admin Gastos":

                title = "Gasto Fijo:";
                titleprecio = "Valor Gasto Fijo:";
                break;
            case "Gastos Fijos":

                title = "Gasto Fijo:";
                titleprecio = "Valor Gasto Fijo:";
                break;
            case "Admin Honorarios y Serv":

                title = "Honorario Servicio:";
                titleprecio = " Valor Honorario Servicio:";

                break;
            case "Honorarios y Serv.":

                title = "Honorario Servicio:";
                titleprecio = " Valor Honorario Servicio:";

                break;
        }

    }
}
