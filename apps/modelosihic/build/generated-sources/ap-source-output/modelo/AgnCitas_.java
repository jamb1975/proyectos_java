package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AgnConsultorios;
import modelo.AgnEstadosCita;
import modelo.AgnMedicos;
import modelo.GenHorasCita;
import modelo.GenPacientes;
import modelo.Producto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(AgnCitas.class)
public class AgnCitas_ { 

    public static volatile SingularAttribute<AgnCitas, GenPacientes> genPacientes;
    public static volatile SingularAttribute<AgnCitas, AgnEstadosCita> agnEstadosCita;
    public static volatile SingularAttribute<AgnCitas, String> usuarioModificador;
    public static volatile SingularAttribute<AgnCitas, Date> fechaModificacion;
    public static volatile SingularAttribute<AgnCitas, Producto> servicio;
    public static volatile SingularAttribute<AgnCitas, String> noConsultorio;
    public static volatile SingularAttribute<AgnCitas, GenHorasCita> genHorasCita;
    public static volatile SingularAttribute<AgnCitas, Date> fechaHora;
    public static volatile SingularAttribute<AgnCitas, AgnConsultorios> agnConsultorios;
    public static volatile SingularAttribute<AgnCitas, AgnMedicos> agnMedicos;
    public static volatile SingularAttribute<AgnCitas, Date> horaInicio;
    public static volatile SingularAttribute<AgnCitas, String> usuarioCreador;
    public static volatile SingularAttribute<AgnCitas, Date> horaFin;
    public static volatile SingularAttribute<AgnCitas, Date> fechaIngreso;
    public static volatile SingularAttribute<AgnCitas, Date> fechaCreacion;
    public static volatile SingularAttribute<AgnCitas, Long> id;

}