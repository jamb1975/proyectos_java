package modelo;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AsientosModelosTiposDocumentosContables;
import modelo.TiposDocumentosContables;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(ModeloTiposDocumentosContables.class)
public class ModeloTiposDocumentosContables_ { 

    public static volatile SingularAttribute<ModeloTiposDocumentosContables, String> descripcion;
    public static volatile SingularAttribute<ModeloTiposDocumentosContables, Long> id;
    public static volatile SingularAttribute<ModeloTiposDocumentosContables, TiposDocumentosContables> tiposDocumentosContables;
    public static volatile ListAttribute<ModeloTiposDocumentosContables, AsientosModelosTiposDocumentosContables> asientosModelosTiposDocumentosContableses;

}