package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.ComprobanteIngreso;
import modelo.Factura.Status;
import modelo.FacturaItem;
import modelo.GenConvenios;
import modelo.GenEapb;
import modelo.GenPersonas;
import modelo.GenTiposUsuarios;
import modelo.HclConsultas;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(Factura.class)
public class Factura_ { 

    public static volatile SingularAttribute<Factura, BigDecimal> valor_iva;
    public static volatile SingularAttribute<Factura, Date> fechaaceptacion;
    public static volatile SingularAttribute<Factura, GenTiposUsuarios> genTiposUsuarios;
    public static volatile SingularAttribute<Factura, Boolean> causadaacontabilidad;
    public static volatile SingularAttribute<Factura, String> forma_pago;
    public static volatile SingularAttribute<Factura, BigDecimal> valor_descuento;
    public static volatile SingularAttribute<Factura, String> usuarioCreador;
    public static volatile SingularAttribute<Factura, Long> no_factura;
    public static volatile SingularAttribute<Factura, GenEapb> genEapb;
    public static volatile SingularAttribute<Factura, BigDecimal> iva;
    public static volatile SingularAttribute<Factura, Boolean> facturacerrada;
    public static volatile ListAttribute<Factura, ComprobanteIngreso> comprobantesingresos;
    public static volatile SingularAttribute<Factura, Boolean> credito;
    public static volatile SingularAttribute<Factura, Long> id;
    public static volatile SingularAttribute<Factura, String> trackingNumber;
    public static volatile SingularAttribute<Factura, BigDecimal> diferenciacapitado;
    public static volatile SingularAttribute<Factura, GenPersonas> genPersonas;
    public static volatile ListAttribute<Factura, FacturaItem> facturaItems;
    public static volatile SingularAttribute<Factura, Integer> tarifa;
    public static volatile SingularAttribute<Factura, Date> fechaModificacion;
    public static volatile SingularAttribute<Factura, Boolean> aceptada;
    public static volatile SingularAttribute<Factura, BigDecimal> netAmount;
    public static volatile SingularAttribute<Factura, BigDecimal> descuento;
    public static volatile SingularAttribute<Factura, Float> porcentajedescuento;
    public static volatile SingularAttribute<Factura, BigDecimal> tax;
    public static volatile SingularAttribute<Factura, BigDecimal> valor_real_venta;
    public static volatile SingularAttribute<Factura, String> prefijo;
    public static volatile SingularAttribute<Factura, Date> fechavencimiento;
    public static volatile SingularAttribute<Factura, Date> facturaDate;
    public static volatile SingularAttribute<Factura, BigDecimal> totalAmount;
    public static volatile SingularAttribute<Factura, Date> fecharadicacion;
    public static volatile SingularAttribute<Factura, Boolean> have_iva;
    public static volatile SingularAttribute<Factura, Boolean> deleted;
    public static volatile SingularAttribute<Factura, String> numeroradicado;
    public static volatile SingularAttribute<Factura, BigDecimal> efectivo;
    public static volatile SingularAttribute<Factura, BigDecimal> valor_abonos;
    public static volatile SingularAttribute<Factura, Date> fechaCreacion;
    public static volatile SingularAttribute<Factura, Boolean> have_descuento;
    public static volatile SingularAttribute<Factura, GenConvenios> genConvenios;
    public static volatile SingularAttribute<Factura, HclConsultas> hclConsultas;
    public static volatile SingularAttribute<Factura, Status> status;

}