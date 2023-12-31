package solucionpage;

import fxnomina.SolucionInfo;
import playground.PlaygroundProperty;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.Property;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.Slider;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Callback;
import javafx.util.StringConverter;

/**
 *
 */
class PlaygroundTabs extends TabPane {

    private final SolucionPage solucionPage;
    //private final GridPane grid;
    // private final Tab propertiesTab;
    //  private final Tab dataTab;

    PlaygroundTabs(final SolucionPage solucionPage) {
        this.solucionPage = solucionPage;
        /*grid = new GridPane();
        grid.setHgap(SolucionPage.INDENT);
        grid.setVgap(SolucionPage.INDENT);
        //grid.setPadding(new Insets(SolucionPage.INDENT));
        getStyleClass().add("floating");
        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.getStyleClass().clear();
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        propertiesTab = new Tab("Properties");
        propertiesTab.setContent(scrollPane);
        dataTab = new Tab("Data");
        //getTabs().addAll(propertiesTab, dataTab);
        setMinSize(100, 100);
        solucionPage.registerSolucionInfoUpdater(new Callback<SolucionInfo, Void>() {

            @Override
            public Void call(SolucionInfo solucionInfo) {
                update(solucionInfo);
                return null;
            }
        });*/
    }

    private PropertyController newPropertyController(PlaygroundProperty playgroundProperty, Object object, Object property) {
        if (playgroundProperty.propertyName.equals("getStrokeDashArray")) {
            return new StrokeDashArrayPropertyController(playgroundProperty, object, (ObservableList<Double>) property);
        }
        if (property instanceof DoubleProperty) {
            DoubleProperty prop = (DoubleProperty) property;
            return new DoublePropertyController(playgroundProperty, object, prop);
        } else if (property instanceof IntegerProperty) {
            IntegerProperty prop = (IntegerProperty) property;
            return new IntegerPropertyController(playgroundProperty, object, prop);
        } else if (property instanceof BooleanProperty) {
            BooleanProperty prop = (BooleanProperty) property;
            return new BooleanPropertyController(playgroundProperty, object, prop);
        } else if (property instanceof StringProperty) {
            StringProperty prop = (StringProperty) property;
            return new StringPropertyController(playgroundProperty, object, prop);
        } else if (property instanceof ObjectProperty) {
            final ObjectProperty prop = (ObjectProperty) property;
            if (prop.get() instanceof Color) {
                return new ColorPropertyController(playgroundProperty, object, prop);
            }
            if (prop.get() instanceof String) {
                return new StringPropertyController(playgroundProperty, object, prop);
            }
            if (prop.get() != null && prop.get().getClass().isEnum()) {
                return new EnumPropertyController(playgroundProperty, object, prop, (Enum) prop.get());
            }
        }
        return null;
    }

    private void update(SolucionInfo solucionInfo) {

    }

    private class PropertyController {

        private PlaygroundProperty playgroundProperty;
        private String name;
        private Label label;
        private Region controller;
        private Node preview;

        public PropertyController(PlaygroundProperty playgroundProperty) {
            this(playgroundProperty, playgroundProperty.propertyName);
        }

        public PropertyController(PlaygroundProperty playgroundProperty, String name) {
            if (playgroundProperty.properties.containsKey("name")) {
                this.name = playgroundProperty.properties.get("name");
            } else {
                this.name = name;
            }
            this.playgroundProperty = playgroundProperty;
        }

        public Region getLabel() {
            if (label == null) {
                label = new Label(name);
                label.setAlignment(Pos.BASELINE_RIGHT);
                label.setLabelFor(getController());
                label.setTextOverrun(OverrunStyle.ELLIPSIS);
                label.setMaxWidth(200);
            }
            return label;
        }

        protected void setController(Region controller) {
            this.controller = controller;
        }

        protected void setPreview(Node preview) {
            this.preview = preview;
        }

        public Region getController() {
            if (controller == null) {
                controller = new Region();
            }
            return controller;
        }

        public Node getPreview() {
            if (preview == null) {
                preview = new Region();
            }
            return preview;
        }

        protected double getProperty(PlaygroundProperty playgroundProperty, String name, double defaultValue) throws NumberFormatException {
            String value = playgroundProperty.properties.get(name);
            if (value == null) {
                return defaultValue;
            } else {
                return Double.parseDouble(value);
            }
        }
    }

    private class DoublePropertyController extends PropertyController {

        public DoublePropertyController(PlaygroundProperty playgroundProperty, Object object, Property<Number> prop) {
            super(playgroundProperty);
            Slider slider = new Slider();
            slider.setMin(getProperty(playgroundProperty, "min", 0));
            slider.setMax(getProperty(playgroundProperty, "max", 100));
            double step = getProperty(playgroundProperty, "step", 0);
            if (step > 0) {
                slider.setMajorTickUnit(step);
                slider.setMinorTickCount(0);
                slider.setSnapToTicks(true);
            }
            slider.valueProperty().bindBidirectional(prop);
            setController(slider);

            TextField preview = new TextField();
            preview.setPrefColumnCount(4);
            preview.textProperty().bindBidirectional(prop, new StringConverter<Number>() {

                @Override
                public String toString(Number number) {
                    return DecimalFormat.getInstance().format((Double) number);
                }

                @Override
                public Number fromString(String string) {
                    try {
                        Number number = DecimalFormat.getInstance().parse(string);
                        return number;
                    } catch (Exception e) {
                        return 0;
                    }
                }
            });
            setPreview(preview);
        }
    }

    private class IntegerPropertyController extends PropertyController {

        public IntegerPropertyController(PlaygroundProperty playgroundProperty, Object object, Property<Number> prop) {
            super(playgroundProperty);
            Slider slider = new Slider();
            slider.setMin(getProperty(playgroundProperty, "min", 0));
            slider.setMax(getProperty(playgroundProperty, "max", 100));
            slider.setSnapToTicks(true);
            slider.setMajorTickUnit(1);
            slider.valueProperty().bindBidirectional(prop);
            setController(slider);

            TextField preview = new TextField();
            preview.setPrefWidth(30);
            preview.textProperty().bindBidirectional(prop, new StringConverter<Number>() {

                @Override
                public String toString(Number number) {
                    return DecimalFormat.getInstance().format((Integer) number);
                }

                @Override
                public Number fromString(String string) {
                    try {
                        Number number = DecimalFormat.getInstance().parse(string);
                        return number;
                    } catch (Exception e) {
                        return 0;
                    }
                }
            });
            setPreview(preview);
        }
    }

    private class BooleanPropertyController extends PropertyController {

        public BooleanPropertyController(PlaygroundProperty playgroundProperty, Object object, Property<Boolean> prop) {
            super(playgroundProperty);
            CheckBox checkbox = new CheckBox();
            checkbox.selectedProperty().bindBidirectional(prop);
            setController(checkbox);
        }
    }

    private class StringPropertyController extends PropertyController {

        public StringPropertyController(PlaygroundProperty playgroundProperty, Object object, Property<String> prop) {
            super(playgroundProperty);
            TextField textField = new TextField();
            textField.textProperty().bindBidirectional(prop);
            setController(textField);
        }
    }

    private class ColorPropertyController extends PropertyController {

        public ColorPropertyController(PlaygroundProperty playgroundProperty, Object object, final Property<Paint> prop) {
            super(playgroundProperty);

            final Rectangle colorRect = new Rectangle(20, 20, (Color) prop.getValue());
            colorRect.setStroke(Color.GRAY);
            final Label valueLabel = new Label(formatWebColor((Color) prop.getValue()));
            valueLabel.setGraphic(colorRect);
            valueLabel.setContentDisplay(ContentDisplay.LEFT);
            setPreview(valueLabel);

        }

        private String formatWebColor(Color c) {
            String r = Integer.toHexString((int) (c.getRed() * 255));
            if (r.length() == 1) {
                r = "0" + r;
            }
            String g = Integer.toHexString((int) (c.getGreen() * 255));
            if (g.length() == 1) {
                g = "0" + g;
            }
            String b = Integer.toHexString((int) (c.getBlue() * 255));
            if (b.length() == 1) {
                b = "0" + b;
            }
            return "#" + r + g + b;
        }
    }

    private class EnumPropertyController extends PropertyController {

        public EnumPropertyController(PlaygroundProperty playgroundProperty, Object object, Property prop, final Enum enumeration) {
            super(playgroundProperty);

            final ChoiceBox choiceBox = new ChoiceBox();
            choiceBox.setItems(FXCollections.observableArrayList(enumeration.getClass().getEnumConstants()));
            choiceBox.getSelectionModel().select(prop.getValue());
            prop.bind(choiceBox.getSelectionModel().selectedItemProperty());
            setController(choiceBox);
        }
    }

    private class StrokeDashArrayPropertyController extends PropertyController {

        public StrokeDashArrayPropertyController(final PlaygroundProperty playgroundProperty, Object object, final ObservableList<Double> list) {
            super(playgroundProperty, "strokeDashArray");

            final ComboBox<ObservableList<Double>> comboBox = new ComboBox<>();
            comboBox.setEditable(true);
            comboBox.setItems(FXCollections.observableArrayList(
                    FXCollections.<Double>observableArrayList(100d, 50d),
                    FXCollections.<Double>observableArrayList(0d, 20d),
                    FXCollections.<Double>observableArrayList(20d, 20d),
                    FXCollections.<Double>observableArrayList(30d, 15d, 0d, 15d)
            ));
            comboBox.setConverter(new StringConverter<ObservableList<Double>>() {
                @Override
                public String toString(ObservableList<Double> t) {
                    if (t == null || t.isEmpty()) {
                        return null;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (Double d : t) {
                        String str = String.valueOf(d);
                        if (str.endsWith(".0")) {
                            str = str.substring(0, str.length() - 2);
                        }
                        sb.append(str).append(' ');
                    }
                    return sb.substring(0, sb.length() - 1);
                }

                @Override
                public ObservableList<Double> fromString(String string) {
                    String[] values = string.trim().split(" +");
                    ObservableList<Double> res = FXCollections.observableArrayList();
                    double sum = 0;
                    for (String value : values) {
                        try {
                            double val = Math.min(Math.max(Double.parseDouble(value), 0), 1000);
                            res.add(val);
                            sum += val;
                            if (sum > 5000) {
                                break;
                            }
                        } catch (Exception ignored) {
                        }
                    }
                    if (sum == 0) {
                        res.clear();
                    }
                    return res;
                }
            });
            comboBox.valueProperty().addListener(new ChangeListener() {
                @Override
                public void changed(ObservableValue ov, Object t, Object key) {
                    ObservableList<Double> value = comboBox.getValue();
                    list.setAll(value);

                    if (value != null && !value.isEmpty() && comboBox.getItems().indexOf(value) == -1) {
                        comboBox.getItems().add(value);
                    }
                }
            });
            setController(comboBox);
        }
    }
}
