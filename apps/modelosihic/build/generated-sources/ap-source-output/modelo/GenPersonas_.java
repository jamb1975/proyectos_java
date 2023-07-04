package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.GenEstadosCiviles;
import modelo.GenEtnias;
import modelo.GenMunicipios;
import modelo.GenNivelesEducativos;
import modelo.GenProfesiones;
import modelo.GenSexo;
import modelo.GenTiposDocumentos;
import modelo.GenZonaResidencia;
import modelo.InformacionTributaria;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(GenPersonas.class)
public class GenPersonas_ { 

    public static volatile SingularAttribute<GenPersonas, GenTiposDocumentos> genTiposDocumentos;
    public static volatile SingularAttribute<GenPersonas, String> segundoNombre;
    public static volatile SingularAttribute<GenPersonas, Date> fechaNacimiento;
    public static volatile SingularAttribute<GenPersonas, String> segundoApellido;
    public static volatile SingularAttribute<GenPersonas, String> documento;
    public static volatile SingularAttribute<GenPersonas, GenSexo> genSexo;
    public static volatile SingularAttribute<GenPersonas, String> usuarioCreador;
    public static volatile SingularAttribute<GenPersonas, String> eMail;
    public static volatile SingularAttribute<GenPersonas, GenProfesiones> gen_profesiones;
    public static volatile SingularAttribute<GenPersonas, InformacionTributaria> informacionTributaria;
    public static volatile SingularAttribute<GenPersonas, Long> id;
    public static volatile SingularAttribute<GenPersonas, String> telefono;
    public static volatile SingularAttribute<GenPersonas, GenEstadosCiviles> genEstadosCiviles;
    public static volatile SingularAttribute<GenPersonas, GenMunicipios> genMunicipios;
    public static volatile SingularAttribute<GenPersonas, String> barrio;
    public static volatile SingularAttribute<GenPersonas, String> usuarioModificador;
    public static volatile SingularAttribute<GenPersonas, Date> fechaModificacion;
    public static volatile SingularAttribute<GenPersonas, String> primerNombre;
    public static volatile SingularAttribute<GenPersonas, String> primerApellido;
    public static volatile SingularAttribute<GenPersonas, String> direccion;
    public static volatile SingularAttribute<GenPersonas, GenEtnias> genEtnias;
    public static volatile SingularAttribute<GenPersonas, String> foto;
    public static volatile SingularAttribute<GenPersonas, String> observaciones;
    public static volatile SingularAttribute<GenPersonas, Date> fechaCreacion;
    public static volatile SingularAttribute<GenPersonas, GenNivelesEducativos> genNivelesEducativos;
    public static volatile SingularAttribute<GenPersonas, GenZonaResidencia> gen_zona_residencia;

}