package modelo;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.FacturaItem;
import modelo.HclCie10;
import modelo.HclConsultas;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(NotasEstudio.class)
public class NotasEstudio_ { 

    public static volatile SingularAttribute<NotasEstudio, String> elaboro;
    public static volatile SingularAttribute<NotasEstudio, Integer> minutosegreso;
    public static volatile SingularAttribute<NotasEstudio, Integer> tipo;
    public static volatile SingularAttribute<NotasEstudio, String> svingresota;
    public static volatile SingularAttribute<NotasEstudio, FacturaItem> facturaItem;
    public static volatile SingularAttribute<NotasEstudio, String> svegresota;
    public static volatile SingularAttribute<NotasEstudio, String> usuarioCreador;
    public static volatile SingularAttribute<NotasEstudio, String> conclusion;
    public static volatile SingularAttribute<NotasEstudio, Integer> horaegreso;
    public static volatile SingularAttribute<NotasEstudio, Integer> minutosingreso;
    public static volatile SingularAttribute<NotasEstudio, Long> id;
    public static volatile SingularAttribute<NotasEstudio, String> svegresofc;
    public static volatile SingularAttribute<NotasEstudio, Boolean> embarazada;
    public static volatile SingularAttribute<NotasEstudio, HclCie10> hclCie10;
    public static volatile SingularAttribute<NotasEstudio, String> svingresospo2;
    public static volatile SingularAttribute<NotasEstudio, Date> fechaModificacion;
    public static volatile SingularAttribute<NotasEstudio, Float> peso;
    public static volatile SingularAttribute<NotasEstudio, Integer> horaatencion;
    public static volatile SingularAttribute<NotasEstudio, Integer> minutosatencion;
    public static volatile SingularAttribute<NotasEstudio, String> miembrosuperior;
    public static volatile SingularAttribute<NotasEstudio, String> af;
    public static volatile SingularAttribute<NotasEstudio, Float> cantiopramida;
    public static volatile SingularAttribute<NotasEstudio, Integer> horaingreso;
    public static volatile SingularAttribute<NotasEstudio, String> acompanante;
    public static volatile SingularAttribute<NotasEstudio, String> detalle;
    public static volatile SingularAttribute<NotasEstudio, String> svegresospo2;
    public static volatile SingularAttribute<NotasEstudio, String> svingresofc;
    public static volatile SingularAttribute<NotasEstudio, Integer> pacientellego;
    public static volatile SingularAttribute<NotasEstudio, String> jelco;
    public static volatile SingularAttribute<NotasEstudio, Integer> estadosalida;
    public static volatile SingularAttribute<NotasEstudio, Date> fechaCreacion;
    public static volatile SingularAttribute<NotasEstudio, HclConsultas> hclConsultas;
    public static volatile SingularAttribute<NotasEstudio, String> notaenfermeria;

}