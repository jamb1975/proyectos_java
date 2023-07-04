package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.HclCie10;
import modelo.HclConsultas;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(HclDiagnostico.class)
public class HclDiagnostico_ { 

    public static volatile SingularAttribute<HclDiagnostico, HclConsultas> hclConsultas_id;
    public static volatile SingularAttribute<HclDiagnostico, Long> tipo;
    public static volatile SingularAttribute<HclDiagnostico, Date> fechaModificacion;
    public static volatile SingularAttribute<HclDiagnostico, HclCie10> hclcie10_id;
    public static volatile SingularAttribute<HclDiagnostico, Date> fechaCreacion;
    public static volatile SingularAttribute<HclDiagnostico, Long> id;
    public static volatile SingularAttribute<HclDiagnostico, String> usuarioCreador;

}