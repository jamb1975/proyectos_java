package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AgnCitas;
import modelo.ConComprobanteProcedimiento;
import modelo.Factura;
import modelo.GenEapb;
import modelo.GenPacientes;
import modelo.HclCie10;
import modelo.HclConsultas;
import modelo.Producto;
import modelo.Sucursales;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(FacturaItem.class)
public class FacturaItem_ { 

    public static volatile SingularAttribute<FacturaItem, String> tipoatencion;
    public static volatile SingularAttribute<FacturaItem, GenPacientes> genPacientes;
    public static volatile SingularAttribute<FacturaItem, String> talla;
    public static volatile SingularAttribute<FacturaItem, BigDecimal> copago;
    public static volatile SingularAttribute<FacturaItem, Producto> producto;
    public static volatile SingularAttribute<FacturaItem, String> clasificacionorigen;
    public static volatile SingularAttribute<FacturaItem, BigDecimal> cuotamoderadora;
    public static volatile SingularAttribute<FacturaItem, HclCie10> diagnosticosecundario;
    public static volatile SingularAttribute<FacturaItem, Date> fechadesecho;
    public static volatile SingularAttribute<FacturaItem, String> usuarioCreador;
    public static volatile SingularAttribute<FacturaItem, String> estadopaciente;
    public static volatile SingularAttribute<FacturaItem, AgnCitas> agnCitas;
    public static volatile SingularAttribute<FacturaItem, Factura> factura;
    public static volatile SingularAttribute<FacturaItem, GenEapb> genEapb;
    public static volatile SingularAttribute<FacturaItem, Long> id;
    public static volatile SingularAttribute<FacturaItem, String> noautorizacion;
    public static volatile SingularAttribute<FacturaItem, Float> quantity;
    public static volatile SingularAttribute<FacturaItem, Date> fechaModificacion;
    public static volatile SingularAttribute<FacturaItem, BigDecimal> precio_compra;
    public static volatile SingularAttribute<FacturaItem, BigDecimal> valor_total2;
    public static volatile SingularAttribute<FacturaItem, BigDecimal> descuento;
    public static volatile SingularAttribute<FacturaItem, HclCie10> diagnosticoprincipal;
    public static volatile SingularAttribute<FacturaItem, Date> fechaapertura;
    public static volatile SingularAttribute<FacturaItem, String> tipodiagnostico;
    public static volatile SingularAttribute<FacturaItem, BigDecimal> valor_total;
    public static volatile SingularAttribute<FacturaItem, BigDecimal> valor_u;
    public static volatile SingularAttribute<FacturaItem, Sucursales> sucursales;
    public static volatile SingularAttribute<FacturaItem, HclCie10> diagnosticoingreso;
    public static volatile SingularAttribute<FacturaItem, ConComprobanteProcedimiento> conComprobanteProcedimiento;
    public static volatile SingularAttribute<FacturaItem, String> discapacidad;
    public static volatile SingularAttribute<FacturaItem, Date> fechaCreacion;
    public static volatile SingularAttribute<FacturaItem, Integer> position;
    public static volatile SingularAttribute<FacturaItem, HclConsultas> hclConsultas;
    public static volatile SingularAttribute<FacturaItem, String> tipocuenta;

}