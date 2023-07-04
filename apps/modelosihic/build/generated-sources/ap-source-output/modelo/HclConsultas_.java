package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AgnCitas;
import modelo.HclCausaExterna;
import modelo.HclCodigosConsultas;
import modelo.HclFinalidad;
import modelo.HclTiposAtenciones;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(HclConsultas.class)
public class HclConsultas_ { 

    public static volatile SingularAttribute<HclConsultas, String> descripcion;
    public static volatile SingularAttribute<HclConsultas, String> motivo;
    public static volatile SingularAttribute<HclConsultas, HclCodigosConsultas> codigosConsultas;
    public static volatile SingularAttribute<HclConsultas, HclFinalidad> hclFinalidad;
    public static volatile SingularAttribute<HclConsultas, String> usuarioModificador;
    public static volatile SingularAttribute<HclConsultas, Date> fechaModificacion;
    public static volatile SingularAttribute<HclConsultas, String> anamnesis;
    public static volatile SingularAttribute<HclConsultas, String> examensolicitado;
    public static volatile SingularAttribute<HclConsultas, Date> fechaevolucion;
    public static volatile SingularAttribute<HclConsultas, String> medicoqueordeno;
    public static volatile SingularAttribute<HclConsultas, String> numeroautorizacion;
    public static volatile SingularAttribute<HclConsultas, String> procedimiento;
    public static volatile SingularAttribute<HclConsultas, String> usuarioCreador;
    public static volatile SingularAttribute<HclConsultas, String> opinion;
    public static volatile SingularAttribute<HclConsultas, AgnCitas> agnCitas;
    public static volatile SingularAttribute<HclConsultas, String> concepto;
    public static volatile SingularAttribute<HclConsultas, Date> fechaCreacion;
    public static volatile SingularAttribute<HclConsultas, Long> id;
    public static volatile SingularAttribute<HclConsultas, HclTiposAtenciones> HclTiposAtenciones;
    public static volatile SingularAttribute<HclConsultas, HclCausaExterna> causaExterna;

}