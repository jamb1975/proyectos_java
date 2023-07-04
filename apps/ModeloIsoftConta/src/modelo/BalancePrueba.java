/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.collections.transformation.TransformationList;

/**
 *
 * @author adminlinux
 */
public  class BalancePrueba {
 
    
    
    private   StringProperty codigo = new SimpleStringProperty();
    private   StringProperty cuenta = new SimpleStringProperty();
    private   StringProperty s_debe = new SimpleStringProperty();
    private   StringProperty s_haber = new SimpleStringProperty();
    private   ObjectProperty<Double> debe = new SimpleObjectProperty<>();
    private   ObjectProperty<Double> haber = new SimpleObjectProperty<>();
    
     public BalancePrueba()
    {
        
    }
    public BalancePrueba(String codigo,String cuenta, BigDecimal totaldebe, BigDecimal totalhaber)
    {
        
        setCodigo(codigo);
        setCuenta(cuenta);
        if(totaldebe==null || totalhaber==null)
        {
           totaldebe=BigDecimal.ZERO;
           totalhaber=BigDecimal.ZERO;
        }
        setDebe(totaldebe.doubleValue());
        setHaber(totalhaber.doubleValue());
        
    }
    
    public ObjectProperty<Double> debeProperty() {
       
            return this.debe;
        }

        public final Double getDebe() {
             //System.out.println("debe property->"+this.debe);
            return this.debe.get();
        }

        public final void setDebe(final Double _debe) {
          BigDecimal bd=BigDecimal.valueOf(_debe);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            this.debe.set(bd.doubleValue());
        }

        public ObjectProperty<Double> haberProperty() {
            return this.haber;
        }

        public final Double getHaber() {
            return this.haber.get();
        }

        public final void setHaber(final Double haber) {
            BigDecimal bd=BigDecimal.valueOf(haber);
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            this.haber.set(bd.doubleValue());
        }

        public final StringProperty codigoProperty() {
            return this.codigo;
        }

        public final String getCodigo() {
            return this.codigoProperty().get();
        }

        public final void setCodigo(final String codigo) {
            this.codigoProperty().set(codigo);
         }
          public final StringProperty cuentaProperty() {
            return this.cuenta;
        }

        public final String getCuenta() {
            return this.cuentaProperty().get();
        }

        public final void setCuenta(final String cuenta) {
            this.cuentaProperty().set(cuenta);
         }

public static class LineItemListWithTotal extends
            TransformationList<BalancePrueba, BalancePrueba> {

        private final TotalLine totalLine;

        public LineItemListWithTotal(
                ObservableList<? extends BalancePrueba> source) {
            super(source);
            totalLine = new TotalLine(source);
        }

        @Override
        public void sourceChanged(Change<? extends BalancePrueba> c) {

            // no need to modify change:
            // indexes generated by the source list will match indexes in this
            // list

            fireChange(c);
        }

        // if index is in range for source list, just return that index
        // otherwise return -1, indicating index is not represented in source
        @Override
        public int getSourceIndex(int index) {
            if (index < getSource().size()) {
                return index;
            }
            return -1;
        }

        // if index is in range for source list, return corresponding
        // item from source list.
        // if index is one after the last element in the source list,
        // return total line.
        @Override
        public BalancePrueba get(int index) {
            if (index < getSource().size()) {
                return getSource().get(index);
            } else if (index == getSource().size()) {
                return totalLine;
            } else
                throw new ArrayIndexOutOfBoundsException(index);
        }

        // size of transformation list is one bigger than size of source list:
        @Override
        public int size() {
            return getSource().size() + 1;
        }

        @Override
        public int getViewIndex(int i) {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

}  
  public static class TotalLine extends BalancePrueba {

        private final ReadOnlyObjectWrapper<Double> debe = new ReadOnlyObjectWrapper<>();
        private final ReadOnlyObjectWrapper<Double> haber = new ReadOnlyObjectWrapper<>();
        private   StringProperty s_debe = new SimpleStringProperty();
        private   StringProperty s_haber = new SimpleStringProperty();
        public TotalLine(ObservableList<? extends BalancePrueba> items) {
            super("","Total", null, null);

            // Bind total to the sum of the totals of all the other line items:
            debe.bind(Bindings.createObjectBinding(() -> items.stream()
                    .collect(Collectors.summingDouble(BalancePrueba::getDebe)),
                    items));
            haber.bind(Bindings.createObjectBinding(() -> items.stream()
                    .collect(Collectors.summingDouble(BalancePrueba::getHaber)),
                    items));
        }

        @Override
        public ObjectProperty<Double> debeProperty() {
            return debe;
        }
        @Override
        public ObjectProperty<Double> haberProperty() {
            return haber;
        }
          @Override
        public StringProperty getS_debe() {
             BigDecimal bd=BigDecimal.valueOf(debe.get());
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            s_debe.set(bd.toString());
            return s_debe;
        }
        @Override
        public StringProperty getS_haber() {
             BigDecimal bd=BigDecimal.valueOf(haber.get());
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            s_haber.set(bd.toString());
            return s_haber;
        }
    } 

    public StringProperty getS_debe() {
          BigDecimal bd=BigDecimal.valueOf(debe.get());
            bd = bd.setScale(2, RoundingMode.HALF_UP);
            s_debe.set(bd.toString());
        return s_debe;
    }

    public void setS_debe(StringProperty s_debe) {
        
        this.s_debe = s_debe;
    }

    public StringProperty getS_haber() {
         BigDecimal bd=BigDecimal.valueOf(haber.get());
         bd = bd.setScale(2, RoundingMode.HALF_UP);
         s_haber.set(bd.toString());
        return s_haber;
    }

    public void setS_haber(StringProperty s_haber) {
        this.s_haber = s_haber;
    }

  
}
