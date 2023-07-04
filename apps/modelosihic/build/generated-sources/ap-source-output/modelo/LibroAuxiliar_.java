package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.ComprobanteCausacionEgreso;
import modelo.ComprobanteEgreso;
import modelo.Factura;
import modelo.NotaContabilidad;
import modelo.Proveedores;
import modelo.Puc;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(LibroAuxiliar.class)
public class LibroAuxiliar_ { 

    public static volatile SingularAttribute<LibroAuxiliar, String> descripcion;
    public static volatile SingularAttribute<LibroAuxiliar, ComprobanteCausacionEgreso> comprobanteCausacionEgreso;
    public static volatile SingularAttribute<LibroAuxiliar, Puc> conPuc;
    public static volatile SingularAttribute<LibroAuxiliar, String> usuarioModificador;
    public static volatile SingularAttribute<LibroAuxiliar, Date> fechaModificacion;
    public static volatile SingularAttribute<LibroAuxiliar, Date> fechaelaboracion;
    public static volatile SingularAttribute<LibroAuxiliar, BigDecimal> debe;
    public static volatile SingularAttribute<LibroAuxiliar, ComprobanteEgreso> comprobanteEgreso;
    public static volatile SingularAttribute<LibroAuxiliar, String> usuarioCreador;
    public static volatile SingularAttribute<LibroAuxiliar, NotaContabilidad> notaContabilidad;
    public static volatile SingularAttribute<LibroAuxiliar, ComprobanteEgreso> comprobanteIngreso;
    public static volatile SingularAttribute<LibroAuxiliar, Proveedores> proveedores;
    public static volatile SingularAttribute<LibroAuxiliar, BigDecimal> haber;
    public static volatile SingularAttribute<LibroAuxiliar, Factura> factura;
    public static volatile SingularAttribute<LibroAuxiliar, Date> fechaCreacion;
    public static volatile SingularAttribute<LibroAuxiliar, Long> id;

}