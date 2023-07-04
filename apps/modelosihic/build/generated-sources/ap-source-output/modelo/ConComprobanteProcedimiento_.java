package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.ConsecutivoComprobanteProcedimiento;
import modelo.FacturaItem;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(ConComprobanteProcedimiento.class)
public class ConComprobanteProcedimiento_ { 

    public static volatile ListAttribute<ConComprobanteProcedimiento, FacturaItem> facturaItems;
    public static volatile SingularAttribute<ConComprobanteProcedimiento, String> usuarioModificador;
    public static volatile SingularAttribute<ConComprobanteProcedimiento, Date> fechaModificacion;
    public static volatile SingularAttribute<ConComprobanteProcedimiento, ConsecutivoComprobanteProcedimiento> consecutivoComprobanteProcedimiento;
    public static volatile SingularAttribute<ConComprobanteProcedimiento, Date> fechaelaboracion;
    public static volatile SingularAttribute<ConComprobanteProcedimiento, Date> fechaCreacion;
    public static volatile SingularAttribute<ConComprobanteProcedimiento, Long> id;
    public static volatile SingularAttribute<ConComprobanteProcedimiento, String> usuarioCreador;

}