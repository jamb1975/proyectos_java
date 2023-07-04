package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.HclCie10;
import modelo.HclDiagnostico;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(HclDiagnosticosRelacionados.class)
public class HclDiagnosticosRelacionados_ { 

    public static volatile SingularAttribute<HclDiagnosticosRelacionados, String> usuarioModificador;
    public static volatile SingularAttribute<HclDiagnosticosRelacionados, Date> fechaModificacion;
    public static volatile SingularAttribute<HclDiagnosticosRelacionados, HclCie10> hclcie10_id;
    public static volatile SingularAttribute<HclDiagnosticosRelacionados, Date> fechaCreacion;
    public static volatile SingularAttribute<HclDiagnosticosRelacionados, Long> id;
    public static volatile SingularAttribute<HclDiagnosticosRelacionados, HclDiagnostico> hcldiagnostico_id;
    public static volatile SingularAttribute<HclDiagnosticosRelacionados, String> usuarioCreador;

}