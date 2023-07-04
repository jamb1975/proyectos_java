/*
 * Copyright (c) 2008, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
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
package modulos.parametrizacion;


import controls.SearchBox;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import jenum.EstadoRegistroEnum;
import menu.MenuMain;
import menu.Sample;
import model.DatosEmpresa;
import persistence.InitialLoadEntityManagerProxy;
import rest.DatosEmpresaJerseyClient;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FormDatosEmpresa extends Sample {
    private TableView tableView ;
    private DatosEmpresa datosEmpresa;
    private int m_IEstRec;
    private  Label m_LNombre;
    private TextField m_TFNombre;
    private Label m_LNo_Ident;
    private TextField m_TFNo_Ident;
    private Label m_LDir1;
    private TextField m_TFDir1;
    private Label m_LEmail;
    private TextField m_TFEmail;
    private Label m_LNoMesas;
    private TextField m_TFNoMesas;
    private Button save;
    private Button nuevo;
    private Label success;
    private InitialLoadEntityManagerProxy iEm;
    private DatosEmpresaJerseyClient m_sJerseyClient;    
    private Pane myPane;
    private GridPane gridpane;
    private GridPane gridpaneRoot;
    private GridPane gridpaneTable; 
    private HBox hbox;
    private HBox hboxButton;
    private SearchBox m_SearchBox;
    private Boolean m_BCanSave;
    public FormDatosEmpresa() throws IOException {
        m_BCanSave=true;
        tableView = new TableView();
        m_IEstRec=0;
        datosEmpresa = new DatosEmpresa();
        m_sJerseyClient=new DatosEmpresaJerseyClient();
        myPane=new Pane();
        gridpane=new GridPane();
        gridpane.getStyleClass().add("background");
        gridpaneRoot=new GridPane();
        gridpaneTable=new GridPane();
        hbox=new HBox();
        hboxButton=new HBox(10);
        gridpane.getStyleClass().add("category-page");
        //getChildren().add(myPane);
        m_LNombre=new Label("Nombre: ");
        m_LNombre.setUnderline(true);
        m_TFNombre=new TextField();
        m_LNo_Ident=new Label("No Identificación: ");
        m_LNo_Ident.setUnderline(true);
        m_TFNo_Ident=new TextField();
        m_LDir1=new Label("Dirección: ");
        m_TFDir1=new TextField();
        m_TFDir1.setPrefSize(450, USE_PREF_SIZE);
        m_LNoMesas=new Label("No Mesas:");
        m_TFNoMesas=new TextField();
        ImageView imageView=new ImageView("/images/bs_save.gif");
        save =new Button("Guardar",imageView);
        imageView=null;
        imageView=new ImageView("/images/bs_newRecord.gif");
        nuevo =new Button("Nuevo",imageView);
        success=new Label();
        hboxButton.getChildren().addAll(save,nuevo);
        m_LEmail=new Label("Email: ");
        m_TFEmail=new TextField();
        m_SearchBox=new SearchBox("SearchBox", "Buscar Por: Razón Social o Nit");
        m_SearchBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                
                    ///System.out.println("SearchBox.handle DOWN");
                  DatosEmpresa[] _Listterceros=  m_sJerseyClient.findCriteria_XML(DatosEmpresa[].class, "0", "21", m_SearchBox.getTextBox().getText());
                MenuMain.getM_oDatosEmpresa().clear();
                MenuMain.getM_oDatosEmpresa().addAll(_Listterceros);
            }
        });
        // m_TFNombre.getStyleClass().add("text-input");
        m_TFNombre.setPrefWidth(150);
          Text _tTexthebreo=new Text("         Datos Empresa            ");
       _tTexthebreo.setFill(Color.WHITESMOKE);
       _tTexthebreo.textAlignmentProperty().setValue(TextAlignment.CENTER);
       _tTexthebreo.setFont(Font.font("WP Hebrew David", FontWeight.BOLD,32));
       GridPane.setHalignment(_tTexthebreo, HPos.CENTER);
       gridpane.setHgap(5);
       gridpane.setVgap(5);
       gridpaneRoot.alignmentProperty().setValue(Pos.TOP_CENTER);
       GridPane.setValignment(gridpaneRoot, VPos.TOP);
       GridPane.setHalignment(success, HPos.CENTER);
      
    
       gridpane.add(m_LNo_Ident, 0, 2);
       gridpane.add(m_TFNo_Ident, 1, 2);
       gridpane.add(m_LNombre, 0, 3);
       gridpane.add(m_TFNombre, 1, 3);
       gridpane.add(m_LDir1, 0, 4);
       gridpane.add(m_TFDir1, 1, 4,3,1);
       gridpane.add(m_LEmail, 0, 5);
       gridpane.add(m_TFEmail, 1, 5);
       gridpane.add(m_LNoMesas, 0, 6);
       gridpane.add(m_TFNoMesas, 1, 6);
       gridpane.add(success, 0, 12,4,1);
       gridpane.add(hboxButton, 1, 14,4,1);
       gridpaneRoot.setMaxSize(800, 350);
       gridpane.setMaxSize(400, 400);
       
     nuevo.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                nuevoDatosEmpresa();
            }});
      save.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                saveDatosEmpresa();
            }});
      
      //Crear Tabla
        TableColumn firstNameCol = new TableColumn();
        firstNameCol.setPrefWidth(150);
        firstNameCol.setText("Nombre");
        firstNameCol.setCellValueFactory(new PropertyValueFactory("m_sNombre"));
       
        TableColumn lastNameCol = new TableColumn();
        lastNameCol.setPrefWidth(150);
       //lastNameCol.getStyleClass().add("table-header");
        Text _TNoIden=new Text("No. Identificación");
        lastNameCol.setText(_TNoIden.getText());
        lastNameCol.setCellValueFactory(new PropertyValueFactory("nit"));
        
 
       
        tableView.setItems(MenuMain.getM_oDatosEmpresa());
        tableView.setEditable(true);
        tableView.getColumns().addAll(firstNameCol, lastNameCol);
        tableView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                public void handle(MouseEvent me) {
                    fLoadRecord();
                }
                
                });
        tableView.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                 if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.UP) 
                 {
                    fLoadRecord();
                 }
            }
        });
        //agregamos el formulario y la tabla
        gridpaneTable.add(m_SearchBox, 0, 2);
        gridpaneTable.add(tableView, 0, 4);
        gridpaneRoot.add(gridpane, 0, 0);
        gridpaneRoot.add(gridpaneTable, 1, 0);
        getChildren().add(gridpaneRoot);
       }
    public  static Node createIconContent() throws IOException {
         ImageView imageView = new ImageView(new Image("/modulos/parametrizacion/DatosEmpresa.png"));
            imageView.setFitHeight(80);
            imageView.setFitWidth(90);
          javafx.scene.Group g =
                new javafx.scene.Group(imageView);
   
        return g;
    }
    
     public void saveDatosEmpresa() {
       boolean _IsGuardado=true;
       
       
     
        datosEmpresa.setM_sNombre(m_TFNombre.getText());
        datosEmpresa.setNit(m_TFNo_Ident.getText());
        datosEmpresa.setM_sDir(m_TFDir1.getText());
        datosEmpresa.setM_sEmail(m_TFEmail.getText());
        datosEmpresa.setM_iNoMesas(Integer.parseInt(m_TFNoMesas.getText()));
        if(m_TFNoMesas.getText().equals(""))
        {
            m_TFNoMesas.setStyle("-fx-background-color:#ff1600");
            m_BCanSave=false;
        }
        if(m_TFNo_Ident.getText().equals(""))
        {
            m_TFNo_Ident.setStyle("-fx-background-color:#ff1600");
            m_BCanSave=false;
        }
        if(m_TFNombre.getText().equals(""))
        {
            m_TFNombre.setStyle("-fx-background-color:#ff1600");
            m_BCanSave=false;
        }
         if(m_BCanSave)
         {
             
             save.setDisable(true);
             nuevo.setDisable(false);
          try{
              switch(m_IEstRec){
                  case 0:
                      m_sJerseyClient.create_XML(datosEmpresa);
                      break;
                  case 1:
                      m_sJerseyClient.edit_XML(datosEmpresa,datosEmpresa.getId().toString());
                      break;
                      
              }
              
              
       
       }catch(Exception e){
           _IsGuardado=false;
           
       }
     success.setOpacity(0);
        if(_IsGuardado)
        {
        success.setText(m_IEstRec==EstadoRegistroEnum.NUEVO.ordinal()?"Registro Guardado":"Registro Modificado");
        }
        else
        {
            success.setText("Error al Almacenar");
            
        }
         }
         else
         {
             m_BCanSave=true;
             success.setText("Los Campos de Texto en Rojo son Obligatorios ");
         }
        animateMessage();
        
   MenuMain.LoadDataInit();
    }
      public void nuevoDatosEmpresa() {
       datosEmpresa = new DatosEmpresa();
       m_IEstRec=0;
       save.setDisable(false);
       nuevo.setDisable(true);
     
       m_TFNombre.setText("");
       m_TFNo_Ident.setText("");
       m_TFDir1.setText("");
       m_TFEmail.setText("");
        
        success.setOpacity(0);;
        success.setText("Registro Nuevo");
        animateMessage();
       }
    private void animateMessage() {
         success.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(2000), success);
        ft.setFromValue(0.0);
        ft.setToValue(1);
      
        ft.play();
       
        //success.setOpacity(0);
      
       ft.setOnFinished(new EventHandler<ActionEvent>() {
 
            public void handle(ActionEvent event) {
                success.setText("");
                m_TFNoMesas.setStyle(null);
                m_TFNoMesas.getStyleClass().add("text-field");
                m_TFNo_Ident.setStyle(null);
                m_TFNo_Ident.getStyleClass().add("text-field");
                m_TFNombre.setStyle(null);
                m_TFNombre.getStyleClass().add("text-field");
            }
        });
        
           
       
    }
    public void fLoadRecord(){
        m_IEstRec=1;
        if(save.isDisable())
         save.setDisable(false);
        datosEmpresa= (DatosEmpresa) tableView.getFocusModel().getFocusedItem();
                m_TFNombre.setText(datosEmpresa.getM_sNombre());
                m_TFNo_Ident.setText(datosEmpresa.getNit());
                m_TFNoMesas.setText(Integer.toString(datosEmpresa.getM_iNoMesas()));
                m_TFDir1.setText(datosEmpresa.getM_sDir());
                m_TFEmail.setText(datosEmpresa.getM_sEmail());
              
                
    }
}