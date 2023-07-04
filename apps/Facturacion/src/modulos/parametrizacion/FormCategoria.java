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


import controls.ButtonInsertProduct;
import controls.SearchBox;
import java.io.IOException;
import javafx.animation.FadeTransition;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
import model.Categoria;
import modulos.facturas.FormFacturarMesas;
import persistence.InitialLoadEntityManagerProxy;
import rest.CategoriaJerseyClient;

/**
 * Demonstrates a TextField control that allows you to enter text.
 *
 * @see javafx.scene.control.TextField
 */
public class FormCategoria extends Sample {
    private TableView tableView ;
    private Categoria categoria;
    private int m_IEstRec;
    private  Label m_LNombre;
    private TextField m_TFNombre;
    
    private Button save;
    private Button nuevo;
    private Label success;
    private InitialLoadEntityManagerProxy iEm;
    private CategoriaJerseyClient m_sJerseyClient;    
    private Pane myPane;
    private GridPane gridpane;
    private GridPane gridpaneRoot;
    private GridPane gridpaneTable; 
    private HBox hbox;
    private HBox hboxButton;
    private SearchBox m_SearchBox;
    private Boolean m_BCanSave;
    private ObservableList _oTempDatosCAtegoria=FXCollections.observableArrayList();
    public FormCategoria() throws IOException {
        m_BCanSave=true;
        tableView = new TableView();
        m_IEstRec=0;
        categoria = new Categoria();
        m_sJerseyClient=new CategoriaJerseyClient();
        myPane=new Pane();
        gridpane=new GridPane();
        gridpaneRoot=new GridPane();
        gridpaneTable=new GridPane();
        hbox=new HBox();
        hboxButton=new HBox(10);
        gridpane.getStyleClass().add("category-page");
        //getChildren().add(myPane);
        m_LNombre=new Label("Nombre: ");
        m_LNombre.setUnderline(true);
        m_TFNombre=new TextField();
       
        ImageView imageView=new ImageView("/images/bs_save.gif");
        save =new Button("Guardar",imageView);
        imageView=null;
        imageView=new ImageView("/images/bs_newRecord.gif");
        nuevo =new Button("Nuevo",imageView);
        success=new Label();
        hboxButton.getChildren().addAll(save,nuevo);
        m_SearchBox=new SearchBox("SearchBox", "Buscar Por: Nombre");
        m_SearchBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                
                    ///System.out.println("SearchBox.handle DOWN");
                  Categoria[] _Listterceros=  m_sJerseyClient.findCriteria_XML(Categoria[].class, "0", "21", m_SearchBox.getTextBox().getText());
                _oTempDatosCAtegoria.clear();
                _oTempDatosCAtegoria.addAll(_Listterceros);
            }
        });
        // m_TFNombre.getStyleClass().add("text-input");
        m_TFNombre.setPrefWidth(150);
          Text _tTexthebreo=new Text("  Categoria  ");
       _tTexthebreo.setFill(Color.WHITESMOKE);
       _tTexthebreo.textAlignmentProperty().setValue(TextAlignment.CENTER);
       _tTexthebreo.setFont(Font.font("WP Hebrew David", FontWeight.BOLD,32));
       GridPane.setHalignment(_tTexthebreo, HPos.CENTER);
       gridpane.setHgap(5);
       gridpane.setVgap(5);
       gridpaneRoot.alignmentProperty().setValue(Pos.TOP_CENTER);
       GridPane.setValignment(gridpaneRoot, VPos.TOP);
       GridPane.setHalignment(success, HPos.CENTER);
      gridpane.getStyleClass().add("background");
       gridpane.add(m_LNombre, 0, 3);
       gridpane.add(m_TFNombre, 1, 3);
       gridpane.add(success, 0, 12,4,1);
       gridpane.add(hboxButton, 1, 14,4,1);
       gridpaneRoot.setMaxSize(500, 300);
       gridpane.setMaxSize(300, 300);
       
     nuevo.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                nuevoCategoria();
            }});
      save.setOnAction(new  EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent ke) {
                saveCategoria();
            }});
      
      //Crear Tabla
        TableColumn firstNameCol = new TableColumn();
        firstNameCol.setPrefWidth(150);
        firstNameCol.setText("Nombre");
        firstNameCol.setCellValueFactory(new PropertyValueFactory("m_NombreCat"));
       
        TableColumn lastNameCol = new TableColumn();
        lastNameCol.setPrefWidth(100);
       //lastNameCol.getStyleClass().add("table-header");
        Text _TNoIden=new Text("Id");
        lastNameCol.setText(_TNoIden.getText());
        lastNameCol.setCellValueFactory(new PropertyValueFactory("id"));
        
 
        if(MenuMain.getM_oDatosCategoria().size()>0)
                               {
                                    for(Categoria cat:(Categoria[])MenuMain.getM_oDatosCategoria().get(0))
                                   {
                                      _oTempDatosCAtegoria.add(cat);
                                   }
                               }
                       
       
        tableView.setItems(_oTempDatosCAtegoria);
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
         ImageView imageView = new ImageView(new Image("/modulos/parametrizacion/Categoria.png"));
            imageView.setFitHeight(80);
            imageView.setFitWidth(90);
          javafx.scene.Group g =
                new javafx.scene.Group(imageView);
   
        return g;
    }
    
     public void saveCategoria() {
       boolean _IsGuardado=true;
       
       
     
        categoria.setM_NombreCat(m_TFNombre.getText());
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
                      m_sJerseyClient.create_XML(categoria);
                      break;
                  case 1:
                      m_sJerseyClient.edit_XML(categoria,categoria.getId().toString());
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
      public void nuevoCategoria() {
       categoria = new Categoria();
       m_IEstRec=0;
       save.setDisable(false);
       nuevo.setDisable(true);
     
       m_TFNombre.setText("");
      
        
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
                   m_TFNombre.setStyle(null);
                m_TFNombre.getStyleClass().add("text-field");
            }
        });
        
           
       
    }
    public void fLoadRecord(){
        m_IEstRec=1;
        if(save.isDisable())
         save.setDisable(false);
        categoria= (Categoria) tableView.getFocusModel().getFocusedItem();
                m_TFNombre.setText(categoria.getM_NombreCat());
               
              
                
    }
}