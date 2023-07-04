package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.HclConsultas;
import modelo.HclTiposAntecedentesGenerales;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(HclAntecedentesGenerales.class)
public class HclAntecedentesGenerales_ { 

    public static volatile SingularAttribute<HclAntecedentesGenerales, String> descripcion;
    public static volatile SingularAttribute<HclAntecedentesGenerales, String> usuarioModificador;
    public static volatile SingularAttribute<HclAntecedentesGenerales, Date> fechaModificacion;
    public static volatile SingularAttribute<HclAntecedentesGenerales, HclTiposAntecedentesGenerales> hclTiposAntecedentesGenerales;
    public static volatile SingularAttribute<HclAntecedentesGenerales, Date> fechaCreacion;
    public static volatile SingularAttribute<HclAntecedentesGenerales, Long> id;
    public static volatile SingularAttribute<HclAntecedentesGenerales, HclConsultas> hclConsultas;
    public static volatile SingularAttribute<HclAntecedentesGenerales, String> usuarioCreador;

}