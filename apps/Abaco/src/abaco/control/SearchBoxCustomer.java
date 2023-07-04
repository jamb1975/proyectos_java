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
package abaco.control;



import java.util.List;
import java.util.Map;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.WindowEvent;
import javafx.util.Duration;




/**
 * A mac style search box with drop down with results
 */
public class SearchBoxCustomer extends Region {
    private TextField textBox;
    private Button clearButton;
   
    private ContextMenu contextMenu = new ContextMenu();
    private Popup extraInfoPopup = new Popup();
    private Label infoName;
    private Label infoDescription;
    private VBox infoBox;
    private Tooltip searchErrorTooltip = new Tooltip();
    private Timeline searchErrorTooltipHidder = null;
    
    public SearchBoxCustomer(String id)
    {
        setId(id);
        setMinHeight(24);
        setPrefSize(100, 150);
        setMaxHeight(24);
        textBox = new TextField();
        textBox.setPrefWidth(100);
        textBox.setPromptText("Cant");
        clearButton = new Button();
        clearButton.setVisible(false);
        getChildren().addAll(textBox, clearButton);
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                textBox.setText("");
                textBox.requestFocus();
            }
        });
        textBox.textProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                clearButton.setVisible(textBox.getText().length() != 0);
            }
        });
        
     
    }
    
    public SearchBoxCustomer(String id,String _SPropText)
    {
        setId(id);
        setMinHeight(24);
        setPrefSize(100, 150);
        setMaxHeight(24);
        textBox = new TextField();
        textBox.setPrefWidth(100);
        textBox.setPromptText(_SPropText);
        clearButton = new Button();
        clearButton.setVisible(false);
        getChildren().addAll(textBox, clearButton);
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent actionEvent) {
                textBox.setText("");
                textBox.requestFocus();
            }
        });
        textBox.textProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                clearButton.setVisible(textBox.getText().length() != 0);
            }
        });
        
     
    }
    
    public SearchBoxCustomer() {
        setId("SearchBox");
        setMinHeight(24);
        setPrefSize(100, 150);
        setMaxHeight(24);
        textBox = new TextField();
        textBox.setPrefWidth(100);
        textBox.setPromptText("Cod Prod.");
        clearButton = new Button();
        clearButton.setVisible(false);
        getChildren().addAll(textBox, clearButton);
        clearButton.setOnAction(new EventHandler<ActionEvent>() {
        @Override public void handle(ActionEvent actionEvent) {
        textBox.setText("");
        textBox.requestFocus();
            }
        });
        textBox.setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.RIGHT) {
                    ///System.out.println("SearchBox.handle DOWN");
                    
                }
            }
        });
       
        // create info popup
        infoBox = new VBox();
        infoBox.setId("search-info-box");
        infoBox.setFillWidth(true);
        infoBox.setMinWidth(USE_PREF_SIZE);
        infoBox.setPrefWidth(350);
        infoName = new Label();
        infoName.setId("search-info-name");
        infoName.setMinHeight(USE_PREF_SIZE);
        infoName.setPrefHeight(28);
        infoDescription = new Label();
        infoDescription.setId("search-info-description");
        infoDescription.setWrapText(true);
        infoDescription.setPrefWidth(infoBox.getPrefWidth()-24);
        infoBox.getChildren().addAll(infoName,infoDescription);
        extraInfoPopup.getContent().add(infoBox);
        // hide info popup when context menu is hidden
        contextMenu.setOnHidden(new EventHandler<WindowEvent>() {
            @Override public void handle(WindowEvent windowEvent) {
                extraInfoPopup.hide();
            }
        });
    }
    
    private void showError(String message) {
        searchErrorTooltip.setText(message);
        if (searchErrorTooltipHidder != null) searchErrorTooltipHidder.stop();
        if (message != null) {
            Point2D toolTipPos = textBox.localToScene(0, textBox.getLayoutBounds().getHeight());
            double x = toolTipPos.getX() + textBox.getScene().getX() + textBox.getScene().getWindow().getX();
            double y = toolTipPos.getY() + textBox.getScene().getY() + textBox.getScene().getWindow().getY();
            searchErrorTooltip.show( textBox.getScene().getWindow(),x, y);
            searchErrorTooltipHidder = new Timeline();
            searchErrorTooltipHidder.getKeyFrames().add( 
                new KeyFrame(Duration.seconds(3), 
                    new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent t) {
                            searchErrorTooltip.hide();
                            searchErrorTooltip.setText(null);
                        }
                    }
                )
            );
            searchErrorTooltipHidder.play();
        } else {
            searchErrorTooltip.hide();
        }
    }

   

    public TextField getTextBox() {
        return textBox;
    }

    public void setTextBox(TextField textBox) {
        this.textBox = textBox;
    }

    @Override protected void layoutChildren() {
        textBox.resize(getWidth(),getHeight());
        clearButton.resizeRelocate(getWidth()-18,6,12,13);
    }
}
