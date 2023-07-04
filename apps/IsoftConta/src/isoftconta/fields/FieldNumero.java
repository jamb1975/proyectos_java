/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package isoftconta.fields;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 *
 * @author adminlinux
 */
public class FieldNumero extends TextField{
   
    
    public FieldNumero(int limit) {
        
        
        UnaryOperator<Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            System.out.println("newText " + newText);
            if (newText.matches("-?([0-9]*)?")) {
                System.out.println("cumple regla");
                 if (change.isContentChange()) {
                int newLength = change.getControlNewText().length();
                if (newLength > limit) {
                    String trimmedText = change.getControlNewText().substring(0, limit);
                    change.setText(trimmedText);
                    int oldLength = change.getControlText().length();
                    change.setRange(0, oldLength);
                }
                 }
                return change;
            }
            System.out.println("No cumple regla");
            return null;
        };
        setTextFormatter(
                new TextFormatter<Integer>(
                        new IntegerStringConverter(), 0, integerFilter));
        
       
     /*   setTextFormatter(new TextFormatter<>(c
                -> {
            if (c.getControlNewText().isEmpty()) {
                return c;
            }

            ParsePosition parsePosition = new ParsePosition(0);
            Object object = format.parse(c.getControlNewText(), parsePosition);

            if (object == null || parsePosition.getIndex() < c.getControlNewText().length()) {
                return null;
            } else {
                return c;
            }
        }));
        
     
      UnaryOperator<Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) { 
                return change;
            } else if ("-".equals(change.getText()) ) {
                if (change.getControlText().startsWith("-")) {
                    change.setText("");
                    change.setRange(0, 1);
                    change.setCaretPosition(change.getCaretPosition()-2);
                    change.setAnchor(change.getAnchor()-2);
                    return change ;
                } else {
                    change.setRange(0, 0);
                    return change ;
                }
            }
            return null;
        };

        // modified version of standard converter that evaluates an empty string 
        // as zero instead of null:
        StringConverter<Integer> converter = new IntegerStringConverter() {
            @Override
            public Integer fromString(String s) {
                if (s.isEmpty()) return 0 ;
                return super.fromString(s);
            }
        };
        TextFormatter<Integer> textFormatter = 
                new TextFormatter<Integer>(converter, 0, integerFilter);
        this.setTextFormatter(textFormatter);

        // demo listener:
        textFormatter.valueProperty().addListener((obs, oldValue, newValue) -> System.out.println(newValue));
*/

    }
 public FieldNumero() {
        
        
        UnaryOperator<Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            System.out.println("newText " + newText);
            if (newText.matches("-?([0-9]*)?")) {
                System.out.println("cumple regla");
                return change;
            }
            System.out.println("No cumple regla");
            return null;
        };
        setTextFormatter(
                new TextFormatter<Integer>(
                        new IntegerStringConverter(), 0, integerFilter));        
        
 }
}
