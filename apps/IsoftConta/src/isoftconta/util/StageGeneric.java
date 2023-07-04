/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.util;

import java.lang.reflect.InvocationTargetException;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import modelo.EntidadesStatic;

/**
 *
 * @author adminlinux
 */
public class StageGeneric {
    private String appClass;
    private Class clz;
    private Object app;
    private Parent parent;
    private Stage stage=new Stage(StageStyle.DECORATED);
    private String title;
    private Scene scene=null;
    private boolean validar=false;
      public StageGeneric(String appPath,String title,boolean validar)
    {
        this.appClass=appPath;
        this.title=title;
        this.validar=validar;
    }
    public void show() throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
    {
          
        stage.setTitle(title);
    
       clz = Class.forName(appClass);
         app = clz.getDeclaredConstructor().newInstance();
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
    public void showmodal() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, InstantiationException {
        stage=null;
        stage=new Stage(StageStyle.DECORATED);
        stage.setTitle(title);
        clz = Class.forName(appClass);
        app = clz.getDeclaredConstructor().newInstance();
        parent = (Parent) clz.getMethod("createContent").invoke(app);
        scene = null;
        scene = new Scene(parent, Color.TRANSPARENT);
        stage.setOnCloseRequest(
                e->{
           if(EntidadesStatic.totaldebe.compareTo(EntidadesStatic.totalhaber)!=0 && validar)
           {
             Alert alert=new Alert(Alert.AlertType.ERROR, "Los totales Debe y Haber deben ser iguales");
             alert.show();
             e.consume();
           }
                
        });
      
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
       
         stage.initModality(Modality.APPLICATION_MODAL);
       //  stage.initOwner(this.stage);

       // stage.setAlwaysOnTop(true);
		stage.showAndWait();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

}
