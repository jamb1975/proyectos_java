package modelo;

import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import modelo.AgnEspecialidades;
import modelo.GenCategoriasProductos;
import modelo.HclCups;
import modelo.MedPresentacionMedicamentos;
import modelo.MedUnidadDosis;
import modelo.ProductoMedicamentos;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2022-02-09T12:37:22")
@StaticMetamodel(Producto.class)
public class Producto_ { 

    public static volatile SingularAttribute<Producto, String> presentacioncomercial;
    public static volatile SingularAttribute<Producto, String> tipo;
    public static volatile SingularAttribute<Producto, String> estado;
    public static volatile SingularAttribute<Producto, String> forma_liquidar;
    public static volatile SingularAttribute<Producto, String> usuarioCreador;
    public static volatile SingularAttribute<Producto, Integer> uso;
    public static volatile SingularAttribute<Producto, String> marca;
    public static volatile SingularAttribute<Producto, HclCups> hclCupsMed;
    public static volatile SingularAttribute<Producto, BigDecimal> precio2001_soat;
    public static volatile SingularAttribute<Producto, BigDecimal> precio2015_soat;
    public static volatile SingularAttribute<Producto, BigDecimal> precio2020;
    public static volatile SingularAttribute<Producto, Long> id;
    public static volatile SingularAttribute<Producto, Integer> codigo2;
    public static volatile ListAttribute<Producto, ProductoMedicamentos> medicamentosItems;
    public static volatile SingularAttribute<Producto, String> codigo_barras;
    public static volatile SingularAttribute<Producto, String> codigo;
    public static volatile SingularAttribute<Producto, String> descrip;
    public static volatile SingularAttribute<Producto, Date> fechaModificacion;
    public static volatile SingularAttribute<Producto, String> reginvima;
    public static volatile SingularAttribute<Producto, Integer> tipoimagen;
    public static volatile SingularAttribute<Producto, String> tipo_radiografia;
    public static volatile SingularAttribute<Producto, Float> monto;
    public static volatile SingularAttribute<Producto, Integer> ude;
    public static volatile SingularAttribute<Producto, AgnEspecialidades> agnEspecialidades;
    public static volatile SingularAttribute<Producto, BigDecimal> precio2017_soat;
    public static volatile SingularAttribute<Producto, GenCategoriasProductos> genCategoriasProductos;
    public static volatile SingularAttribute<Producto, byte[]> img;
    public static volatile SingularAttribute<Producto, String> codequivalente;
    public static volatile SingularAttribute<Producto, Integer> stock_minimo;
    public static volatile SingularAttribute<Producto, MedPresentacionMedicamentos> medFormasFarmaceuticas;
    public static volatile SingularAttribute<Producto, Float> incremento;
    public static volatile SingularAttribute<Producto, String> principioactivo;
    public static volatile SingularAttribute<Producto, String> nombre;
    public static volatile SingularAttribute<Producto, BigDecimal> precio2018_soat;
    public static volatile SingularAttribute<Producto, BigDecimal> precio;
    public static volatile SingularAttribute<Producto, Integer> iva;
    public static volatile SingularAttribute<Producto, BigDecimal> precio2001;
    public static volatile SingularAttribute<Producto, BigDecimal> precio2020_soat;
    public static volatile SingularAttribute<Producto, Integer> concentracion;
    public static volatile SingularAttribute<Producto, Integer> tipo_medicamento;
    public static volatile SingularAttribute<Producto, String> formafarmaceutica;
    public static volatile SingularAttribute<Producto, Integer> medida;
    public static volatile SingularAttribute<Producto, BigDecimal> costo;
    public static volatile SingularAttribute<Producto, String> codigosrelacionados;
    public static volatile SingularAttribute<Producto, String> concentracionmedicamento;
    public static volatile SingularAttribute<Producto, String> laboratorio;
    public static volatile SingularAttribute<Producto, Boolean> esmateriaprima;
    public static volatile SingularAttribute<Producto, BigDecimal> precio2016_soat;
    public static volatile SingularAttribute<Producto, HclCups> hclCups;
    public static volatile SingularAttribute<Producto, BigDecimal> precio2018;
    public static volatile SingularAttribute<Producto, BigDecimal> precio2017;
    public static volatile SingularAttribute<Producto, BigDecimal> precio2016;
    public static volatile SingularAttribute<Producto, BigDecimal> precio2015;
    public static volatile SingularAttribute<Producto, Integer> tipo_medicamentoinsumos;
    public static volatile SingularAttribute<Producto, Date> fechaCreacion;
    public static volatile SingularAttribute<Producto, MedUnidadDosis> unidadmedida;
    public static volatile SingularAttribute<Producto, String> unidadmedidamedicamento;

}