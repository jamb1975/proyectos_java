/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.digitos;

/**
 *
 * @author karolyani
 */
import java.util.Calendar;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class NumeroDigital extends Parent {

    private Calendar calendar = Calendar.getInstance();
    private Digit[] digits;
    private Timeline delayTimeline, secondTimeline;
    private Group dots;
    private GridPane Gp_digits;

    public NumeroDigital(Color onColor, Color offColor, String numero) {
        // create effect for on LEDs

        Gp_digits = new GridPane();
        getChildren().clear();
        Glow onEffect = new Glow(1.7f);
        onEffect.setInput(new InnerShadow());
        // create effect for on dot LEDs
        Glow onDotEffect = new Glow(1.7f);
        onDotEffect.setInput(new InnerShadow(5, Color.BLACK));
        // create effect for off LEDs
        InnerShadow offEffect = new InnerShadow();
        // create digits
        digits = new Digit[numero.length()];
        int j = 1;
        int k = 0;
        int posx = 0;
        for (int i = numero.length() - 1; i >= 0; i--) {
            if (j % 3 == 0) {
                k++;
            }
            k++;
            j++;
        }
        k--;
        j = 1;
        for (int i = numero.length() - 1; i >= 0; i--) {
            Digit digit = new Digit(onColor, offColor, onEffect, offEffect);

            if (numero.charAt(i) != ',' && numero.charAt(i) != '.') {
                Gp_digits.add(digit, k, 0);
                GridPane.setValignment(digit, VPos.CENTER);

                digit.setLayoutX(k * 60 + ((k) % 1) * 20);
                //getChildren().add(digit);
                if (j % 3 == 0 && i > 0) { //create dots

                    dots = new Group(new Circle(0, 0, 6, onColor));
                    //Circle punto=new Circle(6, onColor);
                    Text punto = new Text(".");

                    punto.setFont(Font.font("ARIAL", FontWeight.BOLD, 77));
                    punto.setFill(onColor);
                    GridPane.setValignment(punto, VPos.BOTTOM);
                    GridPane.setHalignment(punto, HPos.LEFT);
                    Gp_digits.add(punto, k - 1, 0);

                    punto.setEffect(onDotEffect);
                    GridPane.setValignment(punto, VPos.BOTTOM);
                    GridPane.setValignment(Gp_digits, VPos.BOTTOM);
                    GridPane.setHalignment(punto, HPos.CENTER);
                    //getChildren().add(punto);
                    k--;
                }

            } else {
                Text coma = new Text(",");

                coma.setFont(Font.font("ARIAL", FontWeight.BOLD, 77));
                coma.setFill(onColor);
                GridPane.setValignment(coma, VPos.BOTTOM);
                GridPane.setHalignment(coma, HPos.CENTER);
                coma.setEffect(onDotEffect);
                Gp_digits.add(coma, k, 0);
            }
            k--;

            digits[i] = digit;
            int num = numero.charAt(i);

            digits[i].showNumber(num - 48);

            j++;
        }
        Gp_digits.setHgap(14);

        getChildren().add(Gp_digits);

    }

}
