package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.HclCups;
import modelo.HclFormulacionesMedicas;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(HclFormulacionProcedimientos.class)
public class HclFormulacionProcedimientos_ { 

    public static volatile SingularAttribute<HclFormulacionProcedimientos, String> recomendacion;
    public static volatile SingularAttribute<HclFormulacionProcedimientos, Boolean> estado;
    public static volatile SingularAttribute<HclFormulacionProcedimientos, String> usuarioModificador;
    public static volatile SingularAttribute<HclFormulacionProcedimientos, Date> fechaModificacion;
    public static volatile SingularAttribute<HclFormulacionProcedimientos, HclFormulacionesMedicas> hclFormulacionesMedicas;
    public static volatile SingularAttribute<HclFormulacionProcedimientos, Date> fechaCreacion;
    public static volatile SingularAttribute<HclFormulacionProcedimientos, Long> id;
    public static volatile SingularAttribute<HclFormulacionProcedimientos, String> usuarioCreador;
    public static volatile SingularAttribute<HclFormulacionProcedimientos, HclCups> hclCups;

}