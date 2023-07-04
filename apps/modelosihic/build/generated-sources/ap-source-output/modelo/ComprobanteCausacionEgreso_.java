package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Proveedores;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(ComprobanteCausacionEgreso.class)
public class ComprobanteCausacionEgreso_ { 

    public static volatile SingularAttribute<ComprobanteCausacionEgreso, Proveedores> proveedores;
    public static volatile SingularAttribute<ComprobanteCausacionEgreso, String> usuarioModificador;
    public static volatile SingularAttribute<ComprobanteCausacionEgreso, Date> fechaModificacion;
    public static volatile SingularAttribute<ComprobanteCausacionEgreso, BigDecimal> valor;
    public static volatile SingularAttribute<ComprobanteCausacionEgreso, String> numerodocumentosoporte;
    public static volatile SingularAttribute<ComprobanteCausacionEgreso, String> concepto;
    public static volatile SingularAttribute<ComprobanteCausacionEgreso, Date> fechaelaboracion;
    public static volatile SingularAttribute<ComprobanteCausacionEgreso, Date> fechaCreacion;
    public static volatile SingularAttribute<ComprobanteCausacionEgreso, Long> id;
    public static volatile SingularAttribute<ComprobanteCausacionEgreso, Long> no_consecutivo;
    public static volatile SingularAttribute<ComprobanteCausacionEgreso, String> usuarioCreador;

}