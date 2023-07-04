package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.HclConsultas;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(HclAntecedentesFarmacologicos.class)
public class HclAntecedentesFarmacologicos_ { 

    public static volatile SingularAttribute<HclAntecedentesFarmacologicos, String> descripcion;
    public static volatile SingularAttribute<HclAntecedentesFarmacologicos, String> usuarioModificador;
    public static volatile SingularAttribute<HclAntecedentesFarmacologicos, Date> fechaModificacion;
    public static volatile SingularAttribute<HclAntecedentesFarmacologicos, String> medicamento;
    public static volatile SingularAttribute<HclAntecedentesFarmacologicos, Date> fechaCreacion;
    public static volatile SingularAttribute<HclAntecedentesFarmacologicos, Long> id;
    public static volatile SingularAttribute<HclAntecedentesFarmacologicos, HclConsultas> hclConsultas;
    public static volatile SingularAttribute<HclAntecedentesFarmacologicos, String> usuarioCreador;

}