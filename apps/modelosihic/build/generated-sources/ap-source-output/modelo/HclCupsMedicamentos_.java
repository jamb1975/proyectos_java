package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.HclCups;
import modelo.Producto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(HclCupsMedicamentos.class)
public class HclCupsMedicamentos_ { 

    public static volatile SingularAttribute<HclCupsMedicamentos, String> usuarioModificador;
    public static volatile SingularAttribute<HclCupsMedicamentos, Date> fechaModificacion;
    public static volatile SingularAttribute<HclCupsMedicamentos, String> observaciones;
    public static volatile SingularAttribute<HclCupsMedicamentos, Date> fechaCreacion;
    public static volatile SingularAttribute<HclCupsMedicamentos, Long> id;
    public static volatile SingularAttribute<HclCupsMedicamentos, Producto> producto;
    public static volatile SingularAttribute<HclCupsMedicamentos, String> usuarioCreador;
    public static volatile SingularAttribute<HclCupsMedicamentos, HclCups> hclCups;

}