/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sihic.util;
import java.io.File;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import modelo.AgnEspecialidades;
import modelo.FacturaItemProveedores;
import modelo.FacturaProveedores;
import modelo.GenCategoriasProductos;
import modelo.HclCups;
import modelo.Kardex;
import modelo.Producto;
import modelo.Proveedores;
import modelo.TiposMedicamento;
import org.jopendocument.dom.spreadsheet.Sheet;
import service.EntityManagerGeneric;

/**
 *
 * @author adminlinux
 */
public class ImportFromExcel {

    private Sheet sheet_estratificacion_v1;
    File file = new File("/home/adminlinux/SUI/estrativicacion_v1/estratificacion_v1.ods");

    public void migrardatospostgresql() {
        if (EntityManagerGeneric.et.isActive()) {
            EntityManagerGeneric.et.getRollbackOnly();
        }
        FacturaProveedores facturaProveedores = new FacturaProveedores();
        Proveedores proveedores = new Proveedores();
        proveedores.setNo_ident("900767863-6");
        proveedores.setNombre("Centro Médico Guaviare");
        EntityManagerGeneric.et.begin();
        EntityManagerGeneric.em.persist(proveedores);
        facturaProveedores.setNo_factura(Long.MIN_VALUE);
        facturaProveedores.setProveedores(proveedores);
        EntityManagerGeneric.em.persist(facturaProveedores);
        EntityManagerGeneric.et.commit();
        GenCategoriasProductos categoriasProductos = new GenCategoriasProductos();
        AgnEspecialidades agnEspecialidades = EntityManagerGeneric.em.find(AgnEspecialidades.class, Long.valueOf(71));
        categoriasProductos = EntityManagerGeneric.em.find(GenCategoriasProductos.class, Long.valueOf(1));

        for (int noreg = 3; noreg < 70; noreg++) {
            // edir=  sheet_estratificacion_v1.getCellAt(2,noreg).getTextValue();
            try {
                System.out.println(sheet_estratificacion_v1.getCellAt(1, noreg));
                if (sheet_estratificacion_v1.getCellAt(0, noreg) != null) {

                    String codcups = sheet_estratificacion_v1.getCellAt(1, noreg).getTextValue().trim().replace("'", "");
                    String nombreprod = sheet_estratificacion_v1.getCellAt(2, noreg).getTextValue().trim().replace("'", "");
                    //String tiporadiografia= sheet_estratificacion_v1.getCellAt(3,noreg).getTextValue().trim().replace("'","");

                    //String monto= sheet_estratificacion_v1.getCellAt(4,noreg).getTextValue().trim().replace(",",".");
                    //String formaliquidar= sheet_estratificacion_v1.getCellAt(5,noreg).getTextValue().trim().replace("'","");
                    //String incremento= sheet_estratificacion_v1.getCellAt(6,noreg).getTextValue().trim().replace("'","");
                    String valor = sheet_estratificacion_v1.getCellAt(4, noreg).getTextValue().trim().replace("'", "");
                    //String codprod= sheet_estratificacion_v1.getCellAt(8,noreg).getTextValue().trim().replace("'","");

                    HclCups hclCups = null;
                    hclCups = new HclCups();
                    Producto producto = null;
                    producto = new Producto();
                    Kardex kardex = null;
                    kardex = new Kardex();
                    FacturaItemProveedores facturaItemProveedores = null;
                    facturaItemProveedores = new FacturaItemProveedores();
                    try {
                        hclCups = (HclCups) EntityManagerGeneric.em.createQuery("select c from HclCups c where c.codigo= :cod")
                                .setParameter("cod", codcups)
                                .setMaxResults(0)
                                .getSingleResult();
                        hclCups.setAgnEspecialidades(agnEspecialidades);
                    } catch (Exception e) {
                        hclCups = new HclCups();
                        hclCups.setCodigo(codcups);
                        hclCups.setDescripcion(nombreprod);
                        hclCups.setAgnEspecialidades(agnEspecialidades);
                        EntityManagerGeneric.et.begin();
                        EntityManagerGeneric.em.persist(hclCups);
                        EntityManagerGeneric.et.commit();
                    }
                    producto.setGenCategoriasProductos(categoriasProductos);
                    // producto.setCodequivalente(codprod);
                    producto.setCodigo(codcups);
                    producto.setNombre(nombreprod);
                    producto.setHclCups(hclCups);
                    // producto.setTipo_radiografia(tiporadiografia);
                    try {
                        producto.setPrecio(new BigDecimal(valor.trim()));
                    } catch (Exception e) {
                        producto.setPrecio(BigDecimal.ZERO);
                    }
                    EntityManagerGeneric.et.begin();
                    EntityManagerGeneric.em.merge(hclCups);
                    EntityManagerGeneric.em.persist(producto);
                    kardex.setProducto(producto);
                    kardex.setCantidad_entrada(1);
                    kardex.setValor_entrada(producto.getPrecio());
                    kardex.setFechaCreacion(new Date());
                    kardex.setValor_saldo(producto.getPrecio());
                    kardex.setValor_unidad(producto.getPrecio());
                    facturaItemProveedores.setCantidad_unidades(1);
                    facturaItemProveedores.setFacturaProveedores(facturaProveedores);
                    facturaItemProveedores.setFechaCreacion(new Date());
                    facturaItemProveedores.setProducto(producto);
                    facturaItemProveedores.setQuantity(1);
                    facturaItemProveedores.setValor_u(producto.getPrecio());
                    facturaItemProveedores.setValor_total(producto.getPrecio());
                    EntityManagerGeneric.em.persist(kardex);
                    EntityManagerGeneric.em.persist(facturaItemProveedores);
                    EntityManagerGeneric.et.commit();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void Migrarmedicamentospostgresql() throws ParseException {
        Producto producto = new Producto();
        if (EntityManagerGeneric.et.isActive()) {
            EntityManagerGeneric.et.getRollbackOnly();
        }
        FacturaProveedores facturaProveedores = EntityManagerGeneric.em.find(FacturaProveedores.class, Long.valueOf(179));
        Proveedores proveedores = EntityManagerGeneric.em.find(Proveedores.class, Long.valueOf(175));
        GenCategoriasProductos categoriasProductos = new GenCategoriasProductos();
        AgnEspecialidades agnEspecialidades = EntityManagerGeneric.em.find(AgnEspecialidades.class, Long.valueOf(103));
        categoriasProductos = EntityManagerGeneric.em.find(GenCategoriasProductos.class, Long.valueOf(2));
        Kardex kardex = null;
        kardex = new Kardex();
        FacturaItemProveedores facturaItemProveedores = null;
        facturaItemProveedores = new FacturaItemProveedores();
        for (int noreg = 1; noreg < 58; noreg++) {
            // edir=  sheet_estratificacion_v1.getCellAt(2,noreg).getTextValue();

            String nombreprod = sheet_estratificacion_v1.getCellAt(1, noreg).getTextValue().trim().replace("'", "");
            String concentracion = sheet_estratificacion_v1.getCellAt(3, noreg).getTextValue().trim().replace("'", "");
            String presentacion = sheet_estratificacion_v1.getCellAt(4, noreg).getTextValue().trim().replace("'", "");
            //String uso= sheet_estratificacion_v1.getCellAt(4,noreg).getTextValue().trim().replace("'","");
            String precio = sheet_estratificacion_v1.getCellAt(9, noreg).getTextValue().trim().replace("'", "");
            String marca = sheet_estratificacion_v1.getCellAt(7, noreg).getTextValue().trim().replace("'", "");
            String reginvima = sheet_estratificacion_v1.getCellAt(10, noreg).getTextValue().trim().replace("'", "");
            String unidad = sheet_estratificacion_v1.getCellAt(5, noreg).getTextValue().trim().replace("'", "");
            String lote = sheet_estratificacion_v1.getCellAt(6, noreg).getTextValue().trim().replace("'", "");
            String cantidad = sheet_estratificacion_v1.getCellAt(0, noreg).getTextValue().trim().replace("'", "");
            String fechav = sheet_estratificacion_v1.getCellAt(12, noreg).getTextValue().trim().replace("'", "");
//  String codbarras= sheet_estratificacion_v1.getCellAt(7,noreg).getTextValue().trim().replace("'","");
            // String stockminimo= sheet_estratificacion_v1.getCellAt(8,noreg).getTextValue().trim().replace("'","");
            producto.setGenCategoriasProductos(categoriasProductos);
            //producto.setCodequivalente(codcups);
            producto.setNombre(nombreprod);
            producto.setDescrip(concentracion);
            producto.setPresentacioncomercial(presentacion);
            producto.setReginvima(reginvima);
            producto.setLaboratorio(marca);
            producto.setPrincipioactivo(unidad);
            producto.setTipo_medicamentoinsumos(TiposMedicamento.MRLABORATORIO.ordinal());
            try {
                producto.setPrecio2017(new BigDecimal(precio));
            } catch (Exception e) {
                producto.setPrecio(BigDecimal.ZERO);
            }
            producto.setMarca(marca);
            producto.setCodigo(String.valueOf(noreg + 18));

            EntityManagerGeneric.et.begin();
            EntityManagerGeneric.em.persist(producto);
            kardex.setProducto(producto);
            kardex.setCantidad_entrada(Float.valueOf(cantidad));
            kardex.setCantidad_saldo(Float.valueOf(cantidad));
            kardex.setValor_entrada(producto.getPrecio2017().multiply(new BigDecimal(cantidad)));
            kardex.setFechaCreacion(new Date());
            kardex.setValor_saldo(producto.getPrecio2017().multiply(new BigDecimal(cantidad)));
            kardex.setValor_unidad(producto.getPrecio2017());
            kardex.setLote(lote);
            SimpleDateFormat df;
            //fecha de vencimiento
            try {
                df = new SimpleDateFormat("yyyy/MM/dd");
                Date fromDate = df.parse(fechav);
                kardex.setFechavencimiento(fromDate);
            } catch (Exception e) {
                try {
                    df = new SimpleDateFormat("dd-MM-yyyy");
                    Date fromDate = df.parse("01-" + fechav);
                    kardex.setFechavencimiento(fromDate);
                } catch (Exception e2) {
                    try {
                        df = new SimpleDateFormat("dd/MM/yyyy");
                        Date fromDate = df.parse("01/" + fechav);
                        kardex.setFechavencimiento(fromDate);
                    } catch (Exception e3) {
                        try {
                            df = new SimpleDateFormat("yyyy/MM/dd");
                            Date fromDate = df.parse(fechav + "/01");
                            kardex.setFechavencimiento(fromDate);
                        } catch (Exception e4) {
                            kardex.setFechavencimiento(null);
                        }
                    }
                }
            }
            facturaItemProveedores.setCantidad_unidades(Float.valueOf(cantidad));
            facturaItemProveedores.setFacturaProveedores(facturaProveedores);
            facturaItemProveedores.setFechaCreacion(new Date());
            facturaItemProveedores.setProducto(producto);
            facturaItemProveedores.setQuantity(Float.valueOf(cantidad));
            facturaItemProveedores.setValor_u(producto.getPrecio2017());
            facturaItemProveedores.setValor_total(producto.getPrecio2017().multiply(new BigDecimal(cantidad)));
            EntityManagerGeneric.em.persist(kardex);
            EntityManagerGeneric.em.persist(facturaItemProveedores);
            EntityManagerGeneric.et.commit();
            producto = null;
            producto = new Producto();
            kardex = null;
            kardex = new Kardex();
            facturaItemProveedores = null;
            facturaItemProveedores = new FacturaItemProveedores();
        }
    }

    public void tarifas2015() {
        if (EntityManagerGeneric.et.isActive()) {
            EntityManagerGeneric.et.getRollbackOnly();
        }
        FacturaProveedores facturaProveedores = EntityManagerGeneric.em.find(FacturaProveedores.class, Long.valueOf(179));
        Proveedores proveedores = EntityManagerGeneric.em.find(Proveedores.class, Long.valueOf(175));

        GenCategoriasProductos categoriasProductos = new GenCategoriasProductos();
        AgnEspecialidades agnEspecialidades = EntityManagerGeneric.em.find(AgnEspecialidades.class, Long.valueOf(103));
        categoriasProductos = EntityManagerGeneric.em.find(GenCategoriasProductos.class, Long.valueOf(1));

        for (int noreg = 3; noreg < 62; noreg++) {
            // edir=  sheet_estratificacion_v1.getCellAt(2,noreg).getTextValue();

            String codcups = sheet_estratificacion_v1.getCellAt(0, noreg).getTextValue().trim().replace("'", "");
            String nombreprod = sheet_estratificacion_v1.getCellAt(2, noreg).getTextValue().trim().replace("'", "");
            //String tiporadiografia= sheet_estratificacion_v1.getCellAt(3,noreg).getTextValue().trim().replace("'","");

            //String monto= sheet_estratificacion_v1.getCellAt(4,noreg).getTextValue().trim().replace(",",".");
            //String formaliquidar= sheet_estratificacion_v1.getCellAt(5,noreg).getTextValue().trim().replace("'","");
            //String incremento= sheet_estratificacion_v1.getCellAt(6,noreg).getTextValue().trim().replace("'","");
            String valor = sheet_estratificacion_v1.getCellAt(2, noreg).getTextValue().trim().replace("'", "");
            String valor2 = sheet_estratificacion_v1.getCellAt(3, noreg).getTextValue().trim().replace("'", "");
            //String valor2= sheet_estratificacion_v1.getCellAt(3,noreg).getTextValue().trim().replace("'","");
            //String codprod= sheet_estratificacion_v1.getCellAt(8,noreg).getTextValue().trim().replace("'","");

            HclCups hclCups = null;
            hclCups = new HclCups();
            Producto producto = null;
            producto = new Producto();
            Kardex kardex = null;
            kardex = new Kardex();
            FacturaItemProveedores facturaItemProveedores = null;
            facturaItemProveedores = new FacturaItemProveedores();
            try {
                hclCups = (HclCups) EntityManagerGeneric.em.createQuery("select c from HclCups c where c.codigo= :cod")
                        .setParameter("cod", codcups)
                        .setMaxResults(1)
                        .getSingleResult();
                hclCups.setAgnEspecialidades(agnEspecialidades);
            } catch (Exception e) {
                hclCups = new HclCups();
                hclCups.setCodigo(codcups);
                hclCups.setDescripcion(nombreprod);
                hclCups.setAgnEspecialidades(agnEspecialidades);
                EntityManagerGeneric.et.begin();
                EntityManagerGeneric.em.persist(hclCups);
                EntityManagerGeneric.et.commit();
            }
            producto.setGenCategoriasProductos(categoriasProductos);
            // producto.setCodequivalente(codprod);
            producto.setCodigo(codcups);
            producto.setNombre(nombreprod);
            producto.setHclCups(hclCups);
            try {
                producto.setPrecio2016(new BigDecimal(valor));
            } catch (Exception e) {
                producto.setPrecio2016(BigDecimal.ZERO);
            }
            try {
                producto.setPrecio2017(new BigDecimal(valor2));
            } catch (Exception e) {
                producto.setPrecio2017(BigDecimal.ZERO);
            }
            // producto.setTipo_radiografia(tiporadiografia);

            EntityManagerGeneric.et.begin();
            //   EntityManagerGeneric.em.merge(hclCups);
            try {
                Producto prod = (Producto) EntityManagerGeneric.em.createQuery("select p from Producto p where p.codigo= :cod order by p.id")
                        .setParameter("cod", codcups)
                        .setMaxResults(1)
                        .getSingleResult();
                prod.setPrecio2016(producto.getPrecio2016());
                prod.setPrecio2017(producto.getPrecio2017());
                EntityManagerGeneric.em.merge(prod);
                /* kardex.setProducto(producto);
         kardex.setCantidad_entrada(1);
         kardex.setCantidad_saldo(1);
         kardex.setValor_entrada(producto.getPrecio2015());
         kardex.setFechaCreacion(new Date());
         kardex.setValor_saldo(producto.getPrecio2015());
         kardex.setValor_unidad(producto.getPrecio2015());
         facturaItemProveedores.setCantidad_unidades(1);
         facturaItemProveedores.setFacturaProveedores(facturaProveedores);
         facturaItemProveedores.setFechaCreacion(new Date());
         facturaItemProveedores.setProducto(producto);
         facturaItemProveedores.setQuantity(1);
         facturaItemProveedores.setValor_u(producto.getPrecio());
         facturaItemProveedores.setValor_total(producto.getPrecio());
         EntityManagerGeneric.em.persist(kardex);
         EntityManagerGeneric.em.persist(facturaItemProveedores);*/
                EntityManagerGeneric.et.commit();

            } catch (Exception e) {
                EntityManagerGeneric.et.commit();
                e.printStackTrace();
            }

        }
    }
    public void importall()
    {
        
    }

}
