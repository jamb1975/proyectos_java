package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AgnMedicosEspecialidades;
import modelo.GenPersonas;
import modelo.GenUnidadesOrganizacion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(AgnMedicos.class)
public class AgnMedicos_ { 

    public static volatile SingularAttribute<AgnMedicos, String> usuarioModificador;
    public static volatile SingularAttribute<AgnMedicos, Date> fechaModificacion;
    public static volatile SingularAttribute<AgnMedicos, String> noConsultorio;
    public static volatile SingularAttribute<AgnMedicos, String> documento;
    public static volatile SingularAttribute<AgnMedicos, Date> fechaCreacion;
    public static volatile SingularAttribute<AgnMedicos, Long> id;
    public static volatile SingularAttribute<AgnMedicos, GenUnidadesOrganizacion> genUnidadesOrganizacion;
    public static volatile SingularAttribute<AgnMedicos, String> numeroRegistro;
    public static volatile SingularAttribute<AgnMedicos, String> nombre;
    public static volatile ListAttribute<AgnMedicos, AgnMedicosEspecialidades> medicosEspecialidadeses;
    public static volatile SingularAttribute<AgnMedicos, String> usuarioCreador;
    public static volatile SingularAttribute<AgnMedicos, GenPersonas> genPersonas;

}