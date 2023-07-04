package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.HclFormulacionesMedicas;
import modelo.MedPresentacionMedicamentos;
import modelo.MedUnidadDosis;
import modelo.MedUnidadFrecuencia;
import modelo.MedUnidadTratamiento;
import modelo.MedViasAdministracion;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(HclFormulacionMedicamentos.class)
public class HclFormulacionMedicamentos_ { 

    public static volatile SingularAttribute<HclFormulacionMedicamentos, Integer> frecuencia;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, String> justificacion;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, MedViasAdministracion> medViasAdministracion;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, String> usuarioModificador;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, Date> fechaModificacion;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, MedPresentacionMedicamentos> medPresentacionMedicamentos;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, MedUnidadFrecuencia> medUnidadFrecuencia;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, String> cantidadletras;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, Float> dosis;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, MedUnidadTratamiento> medUnidadTratamiento;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, String> usuarioCreador;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, String> recomendacion;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, Integer> tiempotratamiento;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, MedUnidadDosis> medUnidadDosis;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, HclFormulacionesMedicas> hclFormulacionesMedicas;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, String> codigomedicamento;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, String> medicamento;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, Date> fechaCreacion;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, Long> id;
    public static volatile SingularAttribute<HclFormulacionMedicamentos, Integer> cantidad;

}