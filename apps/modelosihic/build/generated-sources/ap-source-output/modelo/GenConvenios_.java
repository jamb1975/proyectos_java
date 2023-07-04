package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.GenEapb;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(GenConvenios.class)
public class GenConvenios_ { 

    public static volatile SingularAttribute<GenConvenios, String> descripcion;
    public static volatile SingularAttribute<GenConvenios, String> tipoconvenio;
    public static volatile SingularAttribute<GenConvenios, String> usuarioModificador;
    public static volatile SingularAttribute<GenConvenios, Date> fechaModificacion;
    public static volatile SingularAttribute<GenConvenios, GenEapb> genEapb;
    public static volatile SingularAttribute<GenConvenios, Float> porcentajedescuento;
    public static volatile SingularAttribute<GenConvenios, Date> fechaCreacion;
    public static volatile SingularAttribute<GenConvenios, String> numerocontrato;
    public static volatile SingularAttribute<GenConvenios, Long> id;
    public static volatile SingularAttribute<GenConvenios, Date> fechacontrato;
    public static volatile SingularAttribute<GenConvenios, String> usuarioCreador;

}