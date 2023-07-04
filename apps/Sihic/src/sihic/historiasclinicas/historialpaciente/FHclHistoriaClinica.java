package sihic.historiasclinicas.historialpaciente;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sihic.SihicApp;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FHclHistoriaClinica extends Application {

    //**********************************************************************************
    //**********************************************************************************
    //***************Iniciamos Tab
    ImageView antecedentesIcon = getImage("antecedentes.png");
    ImageView consultasIcon = getImage("consultasmedico.png");
    ImageView formulasmedicasIcon = getImage("fmedicas.png");
    ImageView exmenfisicoIcon = getImage("examenfisico.png");
    private AntecedentesTab generalTab;
    private ConsultasTab consultasTab;
    private ExamenFisicoTab examenFisicoTab;
    private FormulacionesMedicasTab formulacionesMedicasTab;
    private TabPane tabHlcHistoriaClinica;

    public Parent createContent() throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, ParseException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        //   appPathHclAntecedentesGenerales="sihic.historiasclinicas.historialpaciente.FHclHistoriaClinica";
        // ClzHzclAntecedentesGenerales = Class.forName(appPathHclAntecedentesGenerales);
        //appClassHclAntecedentesGenerales=ClzHzclAntecedentesGenerales.newInstance();
        tabHlcHistoriaClinica = null;
        tabHlcHistoriaClinica = new TabPane();
        tabHlcHistoriaClinica.getStylesheets().add(SihicApp.hojaestilos);
        tabHlcHistoriaClinica.getStyleClass().add("background");
        this.generalTab = new AntecedentesTab("Antecedentes", antecedentesIcon);
        this.consultasTab = new ConsultasTab("Consulta", consultasIcon);
        this.examenFisicoTab = new ExamenFisicoTab("Examen Físico", exmenfisicoIcon);
        this.formulacionesMedicasTab = new FormulacionesMedicasTab("Formulas Médicas", formulasmedicasIcon);
        generalTab.setClosable(false);
        formulacionesMedicasTab.setClosable(false);
        consultasTab.setClosable(false);
        tabHlcHistoriaClinica.getTabs().addAll(generalTab, consultasTab, formulacionesMedicasTab, examenFisicoTab);

        return tabHlcHistoriaClinica;
    }

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.show();
    }

    public ImageView getImage(String fileName) {
        ImageView imgView = null;
        try {
            String imagePath = "/images/" + fileName;
            Image img = new Image(imagePath);
            imgView = new ImageView(img);
            imgView.setFitHeight(25);
            imgView.setFitWidth(25);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return imgView;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
