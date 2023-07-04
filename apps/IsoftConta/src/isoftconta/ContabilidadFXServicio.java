package isoftconta;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import isoftconta.model.WelcomePage;

public class ContabilidadFXServicio implements FXServicioArea {

    /** {@inheritDoc} */
    @Override public String getAreaNombre() {
        return "IsoftConta";
    }
    
    /** {@inheritDoc} */
    @Override public String getServicioBasePackage() {
        return "isoftconta.servicios";
    }
    
    /** {@inheritDoc} */
    @Override public WelcomePage getWelcomePage() {
        VBox vBox = new VBox();
        ImageView imgView = new ImageView();
        imgView.setStyle("-fx-image: url('isoftconta/servicios/logo2.png');");
        StackPane pane = new StackPane();
        pane.setPrefHeight(407);
        pane.setStyle("-fx-background-image: url('isoftconta/servicios/bar.png');"
                + "-fx-background-repeat: repeat-x;");
        pane.getChildren().add(imgView);
        Label label = new Label();
        label.setWrapText(true);
        StringBuilder desc = new StringBuilder();
      //  desc.append("**IsoftContaFX Herramienta de Contabilidad\n");
       // desc.append("**Provee una Interfaz de facil Manejo\n");
      //  desc.append("**Manejo del Catálogo de Cuentas Homologado con la Nif");
      //  desc.append("\n");
    //    desc.append("**Explore los Diferentes Servicios con un solo Click.");
        label.setText(desc.toString());
        label.setStyle("-fx-font-size: 1.5em; -fx-padding: 20 0 0 5;");
        vBox.getChildren().addAll(pane, label);
        WelcomePage wPage = new WelcomePage("Bienevenido to IsoftContaFX!", vBox);
        return wPage;
    }
    
    public static void main(String[] args) {
        IsoftConta.main(args);
    }
}
