package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AgnEspecialidades;
import modelo.HclCupsMedicamentos;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(HclCups.class)
public class HclCups_ { 

    public static volatile SingularAttribute<HclCups, String> descripcion;
    public static volatile SingularAttribute<HclCups, String> codigo;
    public static volatile SingularAttribute<HclCups, String> usuarioModificador;
    public static volatile SingularAttribute<HclCups, Date> fechaModificacion;
    public static volatile SingularAttribute<HclCups, Long> gen_procedimientos_apoyo_id;
    public static volatile SingularAttribute<HclCups, Long> gen_recomendacion_especiali_id;
    public static volatile SingularAttribute<HclCups, String> recomendaciones_especificas;
    public static volatile SingularAttribute<HclCups, String> usuarioCreador;
    public static volatile SingularAttribute<HclCups, Date> fechaCreacion;
    public static volatile SingularAttribute<HclCups, Long> atencion;
    public static volatile SingularAttribute<HclCups, AgnEspecialidades> agnEspecialidades;
    public static volatile SingularAttribute<HclCups, Long> id;
    public static volatile ListAttribute<HclCups, HclCupsMedicamentos> medicamentosItems;

}