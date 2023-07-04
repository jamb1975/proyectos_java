package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.LibroAuxiliar;
import modelo.Nomina;
import modelo.Proveedores;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(NotaContabilidad.class)
public class NotaContabilidad_ { 

    public static volatile SingularAttribute<NotaContabilidad, String> usuarioModificador;
    public static volatile SingularAttribute<NotaContabilidad, Date> fechaModificacion;
    public static volatile SingularAttribute<NotaContabilidad, String> tiponota;
    public static volatile SingularAttribute<NotaContabilidad, Date> fechaelaboracion;
    public static volatile ListAttribute<NotaContabilidad, LibroAuxiliar> li_liLibroAuxiliars;
    public static volatile SingularAttribute<NotaContabilidad, String> usuarioCreador;
    public static volatile SingularAttribute<NotaContabilidad, String> detalle;
    public static volatile SingularAttribute<NotaContabilidad, Proveedores> proveedores;
    public static volatile SingularAttribute<NotaContabilidad, Nomina> nomina;
    public static volatile SingularAttribute<NotaContabilidad, String> observaciones;
    public static volatile SingularAttribute<NotaContabilidad, Date> fechaCreacion;
    public static volatile SingularAttribute<NotaContabilidad, Long> id;
    public static volatile SingularAttribute<NotaContabilidad, Long> no_consecutivo;

}