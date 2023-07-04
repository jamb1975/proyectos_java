package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AgnConsultorios;
import modelo.AgnMedicos;
import modelo.Producto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(AgnConsultoriosProcedimientos.class)
public class AgnConsultoriosProcedimientos_ { 

    public static volatile SingularAttribute<AgnConsultoriosProcedimientos, String> usuarioModificador;
    public static volatile SingularAttribute<AgnConsultoriosProcedimientos, Date> fechaModificacion;
    public static volatile SingularAttribute<AgnConsultoriosProcedimientos, Producto> servicios_id;
    public static volatile SingularAttribute<AgnConsultoriosProcedimientos, AgnConsultorios> agnConsultorios;
    public static volatile SingularAttribute<AgnConsultoriosProcedimientos, Date> fechaCreacion;
    public static volatile SingularAttribute<AgnConsultoriosProcedimientos, Long> id;
    public static volatile SingularAttribute<AgnConsultoriosProcedimientos, AgnMedicos> agnMedicos;
    public static volatile SingularAttribute<AgnConsultoriosProcedimientos, String> usuarioCreador;

}