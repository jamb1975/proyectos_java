package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.HclTiposAntecedentesPatologicos;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(HclAntecedentesPatologicos.class)
public class HclAntecedentesPatologicos_ { 

    public static volatile SingularAttribute<HclAntecedentesPatologicos, String> usuarioModificador;
    public static volatile SingularAttribute<HclAntecedentesPatologicos, Date> fechaModificacion;
    public static volatile SingularAttribute<HclAntecedentesPatologicos, Date> fechaCreacion;
    public static volatile SingularAttribute<HclAntecedentesPatologicos, HclTiposAntecedentesPatologicos> hclTiposAntecedentesPatologicos;
    public static volatile SingularAttribute<HclAntecedentesPatologicos, Long> id;
    public static volatile SingularAttribute<HclAntecedentesPatologicos, String> observacion;
    public static volatile SingularAttribute<HclAntecedentesPatologicos, String> usuarioCreador;

}