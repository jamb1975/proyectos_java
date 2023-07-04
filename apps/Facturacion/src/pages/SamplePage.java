
/*
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle Corporation nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package pages;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javafx.application.ConditionalFeature;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javax.swing.filechooser.FileSystemView;
import menu.*;
import model.SampleInfo;
import modulos.facturas.FormFacturarMesas;
import sampleproject.SampleProjectBuilder;
import util.Utils;

/**
 * SamplePage
 */
public class SamplePage extends Page {
    private static WebEngine engine = null;
    private static WebView webView = null;
    private SampleInfo sampleInfo;
    private Class moduloClass;
    private String rawCode;
    private String htmlCode;
    private static   ScrollPane scrollPane = new ScrollPane();
    private static  final VBox main = new VBox(0);
    public SamplePage(String name, String sourceFileUrl) throws IllegalArgumentException {
        super(name);
        String unqualifiedClassName = sourceFileUrl.substring(sourceFileUrl.lastIndexOf('/')+1,
                sourceFileUrl.length());
        String unqualifiedClassName2 = sourceFileUrl.substring(sourceFileUrl.lastIndexOf("/modulos/")
                +9,
                sourceFileUrl.length()-(unqualifiedClassName.length()+1));
        try {
            // load class
            moduloClass = getClass().getClassLoader().loadClass("modulos."+unqualifiedClassName2+"."+unqualifiedClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } /*catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        // Add API back references
        MenuMain menumain = MenuMain.getMenuMain();
        

    }

    public SamplePage(SamplePage pageToClone) {
       super(pageToClone.getName());
        this.sampleInfo = pageToClone.sampleInfo;
        this.moduloClass = pageToClone.moduloClass;
    }

    public SampleInfo getSampleInfo() {
        return sampleInfo;
    }

    @Override public Node createView() {
        Long _mlIdCat=new Long(0);
        SplitPane splitPane = new SplitPane();
    
        // check if 3d sample and on supported platform
        //System.out.println("sampleClass.getSuperclass() == Sample3D.class = " + (sampleClass.getSuperclass() == Sample3D.class));
        //System.out.println("Platform.isSupported(ConditionalFeature.SCENE3D) = " + Platform.isSupported(ConditionalFeature.SCENE3D));
        if (moduloClass.getSuperclass() == Sample3D.class && !Platform.isSupported(ConditionalFeature.SCENE3D)) {
            Label error =  new Label("JavaFX 3D is currently not supported on your configuration.");
            error.setStyle("-fx-text-fill: orangered; -fx-font-size: 1.4em;");
            error.setWrapText(true);
            error.setAlignment(Pos.CENTER);
            error.setTextAlignment(TextAlignment.CENTER);
            return error;
        }
        try {
            // create main grid
            //final FlowSafeVBox main = new FlowSafeVBox();
           
            
            // create header
            Label header = new Label(getName());
            header.getStyleClass().add("page-header");
           // main.getChildren().add(header);
            // create sample area
            final StackPane sampleArea = new StackPane();
            VBox.setVgrow(sampleArea, Priority.NEVER);
            sampleArea.getChildren().clear();
            main.getChildren().clear();
            main.getChildren().add(sampleArea);
            // create sample
            final Sample sample;
            final FormFacturarMesas sample2;
           
           
      
         
            // create border pane for main content and sidebar
            BorderPane borderPane = new BorderPane();
            
           // borderPane.setStyle("overflow-x: scroll;");
            scrollPane = new ScrollPane();
           // scrollPane.getStyleClass().add("noborder-scroll-pane");
            
            FormFacturarMesas fm;
           
            if(moduloClass.getSimpleName().equals("FormFacturarMesas"))
            {
                  fm=new FormFacturarMesas();
                 sample=fm;
                 _mlIdCat=fm.getM_lInitIdCat();
                 sampleArea.getChildren().add(sample);
                
            }
            else
            {
         
           sample = (Sample)moduloClass.newInstance();
           sampleArea.getChildren().add(sample);
            }
            
            // scrollPane.setFitToWidth(true);
            if(!moduloClass.getSimpleName().equals("FormFacturarMesas"))
            {  //scrollPane.setMinWidth(725);
                borderPane.setCenter(main);
               // borderPane.setRight(createSideBar(sample));
              // scrollPane.setFitToHeight(true);
               //scrollPane.setContent(borderPane);
              
            }
            else
            {
                  BorderPane rightSplitPane = new BorderPane();
            BorderPane leftSplitPane = new BorderPane();
         
                // create split pane
             sample2 =new FormFacturarMesas(_mlIdCat);
            // MenuMain.modalDimmer.
             //calculamos el ancho de acuerdo  de la window main
             //leftSplitPane.setMinWidth(870);
            leftSplitPane.setStyle("-fx-background-color: #ffffff;");
            rightSplitPane.setStyle("-fx-background-color: #ffffff;");
            scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
            scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
           
            //main.getStyleClass().add("sample-page");
            splitPane.setStyle("-fx-background-color: #ffffff;");
            sampleArea.setStyle("-fx-background-color: #ffffff;");
            sample.setStyle("-fx-background-color: #ffffff;");
            splitPane.setId("page-splitpane");
            splitPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            //splitPane.setCenterShape(true);
            
            sampleArea.alignmentProperty().setValue(Pos.CENTER);
             GridPane.setHalignment(leftSplitPane, HPos.CENTER);
            GridPane.setHalignment(scrollPane, HPos.CENTER);
           main.alignmentProperty().setValue(Pos.BOTTOM_CENTER);
           
           // leftSplitPane.requestLayout();
            leftSplitPane.setCenter(main);
            scrollPane.setContent(leftSplitPane);
            scrollPane.setStyle("-fx-background: rgb(255,255,255);");
           
            rightSplitPane.setMinWidth(200);
            rightSplitPane.setRight(createSideBar(sample2)); 
            splitPane.getItems().clear();
            splitPane.getItems().addAll(scrollPane, rightSplitPane);
            splitPane.setDividerPosition(0, 0);
            resizeWidthScrollPane(MenuMain.modalDimmer.getWidth());
              }
        
        //scrollPane.setMinHeight(555);
            // create tab pane
             
           if(moduloClass.getSimpleName().equals("FormFacturarMesas"))
            {   
                 //GridPane.setHalignment(splitPane, HPos.CENTER);
         
                 return splitPane;
          
            }
           else
           {
               sample.setMinWidth(550);
               sample.setMinHeight(550);
                return sample;
           }
        } catch (Exception e) {
            e.printStackTrace();
            return new Text("Failed to create sample because of ["+e.getMessage()+"]");
        }
    }

    private Node createSideBar(Sample sample) {
        GridPane sidebar = new GridPane();
        sidebar.getStyleClass().add("right-sidebar");
        sidebar.setMaxWidth(Double.MAX_VALUE);
        sidebar.setMaxHeight(Double.MAX_VALUE);
        sidebar.getChildren().add(sample);
        return sidebar;
    }

    private Node getIcon() {
        URL url = moduloClass.getResource(moduloClass.getSimpleName()+".png");
        if (url != null) {
            ImageView imageView = new ImageView(new Image(url.toString()));
            imageView.setFitHeight(70);
            imageView.setFitWidth(70);
            return imageView;
        } else {
            ImageView imageView = new ImageView(new Image(MenuMain.class.getResource("/images/icon-overlay.png").toString()));
            imageView.setMouseTransparent(true);
            Rectangle overlayHighlight = new Rectangle(-8,-8,130,130);
            overlayHighlight.setFill(new LinearGradient(0,0.5,0,1,true, CycleMethod.NO_CYCLE, new Stop[]{ new Stop(0,Color.BLACK), new Stop(1,Color.web("#444444"))}));
            overlayHighlight.setOpacity(0.8);
            overlayHighlight.setMouseTransparent(true);
            overlayHighlight.setBlendMode(BlendMode.ADD);
            Rectangle background = new Rectangle(-8,-8,130,130);
            background.setFill(Color.web("#b9c0c5"));
            Group group = new Group(background);
            Rectangle clipRect = new Rectangle(114,114);
            clipRect.setArcWidth(38);
            clipRect.setArcHeight(38);
            group.setClip(clipRect);
            Node content = createIconContent();
            double x=(content.getBoundsInParent().getWidth());
            double x1=(content.getBoundsInParent().getMinX());
            double y=(content.getBoundsInParent().getHeight());
            double y1=(content.getBoundsInParent().getMinY());
            if(x==0.0 || x1==0.0)
            {
                x=92;
                y=80;
                
            }
            
            if (content != null) {
                content.setTranslateX((int)((114-x)/2)-(int)x1);
                content.setTranslateY((int)((114-y)/2)-(int)y1);
                group.getChildren().add(content);
            }
            group.getChildren().addAll(overlayHighlight,imageView);
            // Wrap in extra group as clip dosn't effect layout without it
            return new Group(group);
        }
    }

    public Node createIconContent() {
        try {
            Method createIconContent = moduloClass.getDeclaredMethod("createIconContent");
            return (Node)createIconContent.invoke(moduloClass);
        } catch (NoSuchMethodException e) {
            System.err.println("Sample ["+getName()+"] is missing a icon.");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Node createTile() {
        Button tile = new Button(getName().trim(),getIcon());
        tile.setMinSize(140,145);
        tile.setPrefSize(140,145);
        tile.setMaxSize(140,145);
        tile.setContentDisplay(ContentDisplay.TOP);
        tile.getStyleClass().clear();
        tile.getStyleClass().add("sample-tile");
        tile.setOnAction(new EventHandler() {
            public void handle(Event event) {
                MenuMain.getMenuMain().goToPage(SamplePage.this);
            }
        });
        return tile;
    }

  


    
    public static class SamplePageView extends TabPane {
        private Sample sample;

        public SamplePageView(Sample sample) {
            super();
            this.sample = sample;
        }

        public void stop() {
            sample.stop();
        }
    }
    public  static void  resizeWidthScrollPane(Double _DWidthScene)
    {
        
        Double _DWidthScrollPane=(650*_DWidthScene)/1020;
        if(_DWidthScrollPane>650)
        {
          _DWidthScrollPane=_DWidthScrollPane-50;  
          main.setTranslateX(90);
        }
        else
        {
            main.setTranslateX(0);
        }
        scrollPane.setMinWidth(_DWidthScrollPane);
        
        System.out.println("Width min:"+_DWidthScrollPane);
    }
}
