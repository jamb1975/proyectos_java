package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.FacturaItemProveedores;
import modelo.FacturaProveedores;
import modelo.Producto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(FacturaItemProveedores.class)
public class FacturaItemProveedores_ { 

    public static volatile SingularAttribute<FacturaItemProveedores, FacturaItemProveedores> facturaItemProveedoresPadre;
    public static volatile SingularAttribute<FacturaItemProveedores, BigDecimal> valor_total;
    public static volatile SingularAttribute<FacturaItemProveedores, BigDecimal> valor_u;
    public static volatile SingularAttribute<FacturaItemProveedores, Float> quantity;
    public static volatile SingularAttribute<FacturaItemProveedores, Date> fechaModificacion;
    public static volatile SingularAttribute<FacturaItemProveedores, Date> fechaCreacion;
    public static volatile SingularAttribute<FacturaItemProveedores, Producto> producto;
    public static volatile SingularAttribute<FacturaItemProveedores, FacturaProveedores> facturaProveedores;
    public static volatile SingularAttribute<FacturaItemProveedores, Long> id;
    public static volatile SingularAttribute<FacturaItemProveedores, Integer> position;
    public static volatile SingularAttribute<FacturaItemProveedores, Float> cantidad_unidades;
    public static volatile SingularAttribute<FacturaItemProveedores, String> usuarioCreador;

}