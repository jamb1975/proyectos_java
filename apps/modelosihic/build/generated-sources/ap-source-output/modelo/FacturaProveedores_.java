package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.ComprobanteEgreso;
import modelo.FacturaItemProveedores;
import modelo.Proveedores;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(FacturaProveedores.class)
public class FacturaProveedores_ { 

    public static volatile SingularAttribute<FacturaProveedores, Float> quantity;
    public static volatile SingularAttribute<FacturaProveedores, Date> fechaModificacion;
    public static volatile SingularAttribute<FacturaProveedores, BigDecimal> devolver;
    public static volatile ListAttribute<FacturaProveedores, FacturaItemProveedores> facturaItemsProveedores;
    public static volatile SingularAttribute<FacturaProveedores, BigDecimal> valor;
    public static volatile SingularAttribute<FacturaProveedores, String> banco;
    public static volatile SingularAttribute<FacturaProveedores, String> forma_pago;
    public static volatile ListAttribute<FacturaProveedores, ComprobanteEgreso> li_conComprobanteEgresos;
    public static volatile SingularAttribute<FacturaProveedores, BigDecimal> valor_real_venta;
    public static volatile SingularAttribute<FacturaProveedores, Float> retefuente;
    public static volatile SingularAttribute<FacturaProveedores, Date> facturaDate;
    public static volatile SingularAttribute<FacturaProveedores, String> usuarioCreador;
    public static volatile SingularAttribute<FacturaProveedores, Long> no_factura;
    public static volatile SingularAttribute<FacturaProveedores, String> nocheque;
    public static volatile SingularAttribute<FacturaProveedores, Long> id_fi_temp;
    public static volatile SingularAttribute<FacturaProveedores, Proveedores> proveedores;
    public static volatile SingularAttribute<FacturaProveedores, BigDecimal> totalAmount;
    public static volatile SingularAttribute<FacturaProveedores, Float> iva;
    public static volatile SingularAttribute<FacturaProveedores, String> notargeta;
    public static volatile SingularAttribute<FacturaProveedores, BigDecimal> efectivo;
    public static volatile SingularAttribute<FacturaProveedores, BigDecimal> valor_egresos;
    public static volatile SingularAttribute<FacturaProveedores, Boolean> credito;
    public static volatile SingularAttribute<FacturaProveedores, Date> fechaCreacion;
    public static volatile SingularAttribute<FacturaProveedores, Long> id;

}