/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javiconta.configuracion.contabilidad;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author adminlinux
 */
public class IconoGenerico extends Button{
    private String colormodulo="";
    private String nombresolucion="";
    private String rutasolucion="";
    private  String appclass;
    private  Class clz ;
    private  Object app ;
    private  Parent parent;
    private  Stage stage= new Stage(StageStyle.DECORATED);
    private Scene scene =null;
    private String rutaimagen="";
    private static Map<String, Image> imageCache;
    public IconoGenerico(String colormodulo,String nombresolucion,String appclass,String rutaimagen) 
    {
        
     this.colormodulo=colormodulo;
     this.nombresolucion=nombresolucion;
     this.appclass=appclass;
           this.getStyleClass().setAll("solucion-button");
                                this.setContentDisplay(ContentDisplay.TOP);
                                this.setText(nombresolucion);
                                
                                this.setGraphic(getMediumPreview());
                               
                                this.setOnAction((ActionEvent actionEvent) -> {
         try {
             show();
         } catch (Exception ex) {
             Logger.getLogger(IconoGenerico.class.getName()).log(Level.SEVERE, null, ex);
         } 
                                });
     this.rutaimagen=rutaimagen;                
    }
     public Node getMediumPreview() {
        Region label = new Region();
        CornerRadii cornerRadii = new CornerRadii(8);
        Color color = Color.valueOf(colormodulo);
            label.setBackground(
                    new Background(
                            new BackgroundFill[]{
                                new BackgroundFill(color, cornerRadii, Insets.EMPTY)
                            },
                            new BackgroundImage[]{
                                new BackgroundImage(
                                        getImage(rutaimagen),
                                        BackgroundRepeat.NO_REPEAT,
                                        BackgroundRepeat.NO_REPEAT,
                                        new BackgroundPosition(Side.LEFT, 12, false, Side.TOP, 10, false),
                                        new BackgroundSize(90, 90, false, false, false, false)
                                )
                            }
                    ));

        
        DropShadow dsEffect = new DropShadow();
        dsEffect.setOffsetX(0);
        dsEffect.setOffsetY(0);
        dsEffect.setColor(Color.BLACK);
        dsEffect.setRadius(30);
      //  label.getStyleClass().add("sample-medium-preview");
        label.setMinSize(116, 106);
        label.setPrefSize(116, 106);
        label.setMaxSize(116, 106);
        label.setOnMouseEntered(e -> {
            label.setEffect(dsEffect);
        });
        label.setOnMouseExited(e -> {
            label.setEffect(null);
        });
        if (nombresolucion.equals("")) {
            label.visibleProperty().setValue(false);
            return null;
        }
        return label;
    
     }
 private static Image getImage(String url) {
        if (imageCache == null) {
            imageCache = new WeakHashMap<>();
        }
        Image image = imageCache.get(url);
        if (image == null) {
            image = new Image(url);
            imageCache.put(url, image);
        }
        return image;
    }    
private void show() throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException
{
 clz = Class.forName(appclass);
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
}
