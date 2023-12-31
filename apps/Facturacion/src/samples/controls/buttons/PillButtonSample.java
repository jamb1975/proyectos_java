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
package samples.controls.buttons;

import menu.Sample;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

/**
 * This sample demonstrates styling toggle buttons with CSS.
 *
 * @see javafx.scene.control.ToggleButton
 * @related controls/buttons/ToggleButton
 * @resource PillButton.css
 * @resource center-btn.png
 * @resource center-btn-selected.png
 * @resource left-btn.png
 * @resource left-btn-selected.png
 * @resource right-btn.png
 * @resource right-btn-selected.png
 */
public class PillButtonSample extends Sample {

    public PillButtonSample() {

        String pillButtonCss = PillButtonSample.class.getResource("PillButton.css").toExternalForm();

        // create 3 toggle buttons and a toogle group for them
        ToggleButton tb1 = new ToggleButton("Left Button");
        tb1.setId("pill-left");
        ToggleButton tb2 = new ToggleButton("Center Button");
        tb2.setId("pill-center");
        ToggleButton tb3 = new ToggleButton("Right Button");
        tb3.setId("pill-right");

        ToggleGroup group = new ToggleGroup();
        tb1.setToggleGroup(group);
        tb2.setToggleGroup(group);
        tb3.setToggleGroup(group);
        // select the first button to start with
        group.selectToggle(tb1);

        HBox hBox = new HBox();
        hBox.getChildren().addAll(tb1, tb2, tb3);
        hBox.getStylesheets().add(pillButtonCss);
        getChildren().add(hBox);
    }
}

