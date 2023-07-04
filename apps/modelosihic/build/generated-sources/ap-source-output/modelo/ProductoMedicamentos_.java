package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.Producto;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(ProductoMedicamentos.class)
public class ProductoMedicamentos_ { 

    public static volatile SingularAttribute<ProductoMedicamentos, String> usuarioModificador;
    public static volatile SingularAttribute<ProductoMedicamentos, Date> fechaModificacion;
    public static volatile SingularAttribute<ProductoMedicamentos, String> observaciones;
    public static volatile SingularAttribute<ProductoMedicamentos, Producto> medicamento;
    public static volatile SingularAttribute<ProductoMedicamentos, Date> fechaCreacion;
    public static volatile SingularAttribute<ProductoMedicamentos, Long> id;
    public static volatile SingularAttribute<ProductoMedicamentos, Float> cantidad;
    public static volatile SingularAttribute<ProductoMedicamentos, Producto> hclCupsProducto;
    public static volatile SingularAttribute<ProductoMedicamentos, String> usuarioCreador;

}