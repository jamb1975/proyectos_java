/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package message;

import java.util.Map;
import java.util.WeakHashMap;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;

/**
 *
 * @author adminlinux
 */
public class EstadosMensaje {
       public   Boolean checkproceso=new Boolean(false);
       public  GridPane Gp_MessageSuccesfull;
       public  GridPane Gp_MessageError;
       public  Runnable Task_Message;
       public  Thread backgroundThread;
       
       public EstadosMensaje()
       {
           
           Gp_MessageSuccesfull=new GridPane();
          // Gp_MessageSuccesfull.setMaxSize(1070, 40);
           //Gp_MessageSuccesfull.setMinSize(1070, 40);
           Gp_MessageSuccesfull.setAlignment(Pos.TOP_RIGHT);
           Gp_MessageSuccesfull.add(agregarLabel("/images/update.jpg"), 0, 0);
           
           Gp_MessageError=new GridPane();
          // Gp_MessageError.setMaxSize(1070, 40);
           //Gp_MessageError.setMinSize(1070, 40);
           Gp_MessageError.setAlignment(Pos.TOP_RIGHT);
           Gp_MessageError.add(agregarLabel("/images/advertencia.jpg"), 0, 0);
    
          
       }
       
        private  Map<String, Image> imageCache;
     private  Image getImage(String url) {
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
     
     public   Region agregarLabel(String path)
     {
         Region label = new Region();
    
            String url = getClass().getResource(path).toExternalForm();
            label.setBackground(
                    new Background(
                            new BackgroundFill[]{
                                new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)
                            },
                            new BackgroundImage[]{
                                new BackgroundImage(
                                    getImage(url),
                                    BackgroundRepeat.NO_REPEAT,
                                    BackgroundRepeat.NO_REPEAT,
                                    new BackgroundPosition(Side.LEFT,5,false, Side.TOP,5,false),
                                    new BackgroundSize(96, 86, false, false, false, false)
                                )
                            }
                    ));
        
        
        DropShadow dsEffect = new DropShadow();
dsEffect.setOffsetX(0);
dsEffect.setOffsetY(0);
dsEffect.setColor(Color.GOLD);
        label.getStyleClass().add("sample-medium-preview");
        label.setMinSize(116, 106);
        label.setPrefSize(116, 106);
        label.setMaxSize(116, 106);
        return label;
     }
     
     public   GridPane tipomensaje()
     {
         
           
         if(!checkproceso)
         {
           return Gp_MessageError;  
         }
         else{
             return Gp_MessageSuccesfull;
         }
     }
     public  void   SetMessage() throws InterruptedException
    {
       
       tipomensaje().setVisible(true);
       //FadeTransition ft = new FadeTransition(Duration.millis(4000),Gp_Message);
         Thread.sleep(3000);
         tipomensaje().setVisible(false);
//         backgroundThread.setDaemon(false);
         Task_Message=null;
        //ft.setFromValue(0.0);
       // ft.setToValue(1);
      
       // ft.play();
       
        //success.setOpacity(0);
      
       /*ft.setOnFinished(new EventHandler<ActionEvent>() {
           @Override
           public void handle(ActionEvent event) {
                      Gp_Message.setVisible(false);
                      backgroundThread.setDaemon(false);
 
       // Start the thread

      Task_Message=null;
            }
           
       });*/
    }
   public  void   runThread()
   {
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
