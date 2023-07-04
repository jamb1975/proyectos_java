package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.GenEstadoFormula;
import modelo.HclConsultas;
import modelo.HclTiposFormulas;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(HclFormulacionesMedicas.class)
public class HclFormulacionesMedicas_ { 

    public static volatile SingularAttribute<HclFormulacionesMedicas, String> descripcion;
    public static volatile SingularAttribute<HclFormulacionesMedicas, String> usuarioModificador;
    public static volatile SingularAttribute<HclFormulacionesMedicas, Date> fechaModificacion;
    public static volatile SingularAttribute<HclFormulacionesMedicas, GenEstadoFormula> genEstadoFormula;
    public static volatile SingularAttribute<HclFormulacionesMedicas, Date> fechaformulacion;
    public static volatile SingularAttribute<HclFormulacionesMedicas, String> ordenterapeutica;
    public static volatile SingularAttribute<HclFormulacionesMedicas, Date> fechaCreacion;
    public static volatile SingularAttribute<HclFormulacionesMedicas, HclTiposFormulas> hclTiposFormulas;
    public static volatile SingularAttribute<HclFormulacionesMedicas, Long> id;
    public static volatile SingularAttribute<HclFormulacionesMedicas, HclConsultas> hclConsultas;
    public static volatile SingularAttribute<HclFormulacionesMedicas, String> usuarioCreador;
    public static volatile SingularAttribute<HclFormulacionesMedicas, String> ordenmedica;

}