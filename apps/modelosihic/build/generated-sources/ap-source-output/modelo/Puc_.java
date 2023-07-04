package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Puc;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(Puc.class)
public class Puc_ { 

    public static volatile SingularAttribute<Puc, String> descripcion;
    public static volatile SingularAttribute<Puc, String> codigo;
    public static volatile SingularAttribute<Puc, Integer> tipo;
    public static volatile SingularAttribute<Puc, String> usuarioModificador;
    public static volatile SingularAttribute<Puc, Date> fechaModificacion;
    public static volatile SingularAttribute<Puc, String> nombre;
    public static volatile SingularAttribute<Puc, String> usuarioCreador;
    public static volatile SingularAttribute<Puc, BigDecimal> porc_base;
    public static volatile SingularAttribute<Puc, String> natcuenta;
    public static volatile SingularAttribute<Puc, Boolean> deshabilitar;
    public static volatile SingularAttribute<Puc, Date> fechaCreacion;
    public static volatile SingularAttribute<Puc, Puc> conpuc_id;
    public static volatile SingularAttribute<Puc, Long> id;
    public static volatile SingularAttribute<Puc, String> tipocuenta;

}