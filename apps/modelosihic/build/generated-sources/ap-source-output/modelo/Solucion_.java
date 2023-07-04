package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Solucion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(Solucion.class)
public class Solucion_ { 

    public static volatile SingularAttribute<Solucion, String> descripcion;
    public static volatile SingularAttribute<Solucion, Solucion> solucionPadre;
    public static volatile SingularAttribute<Solucion, String> ruta;
    public static volatile SingularAttribute<Solucion, Integer> numeral;
    public static volatile SingularAttribute<Solucion, Long> id;
    public static volatile SingularAttribute<Solucion, String> clase;
    public static volatile SingularAttribute<Solucion, String> solucion;

}