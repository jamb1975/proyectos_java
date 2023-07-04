/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
/**
 *
 * @author adminlinux
 */
public class EntidadesStatic {
    //********Entidades y List de entidades globales
    public static DocumentoSoporte es_documentosoporte=null;
    public static DocumentoSoporte es_documentosoporte_root=null;
    public static DatosPyme es_datospyme=null;
    public static ParametrosContabilidad es_parametroscontabilidad=null;
    public static Producto es_producto=null;
    public static Factura  es_factura=null;
    public static FacturaItem es_facturaitem=null;
    public static FacturaProveedores es_facturaproveedores=null;
    public static FacturaItemProveedores es_facturaitemproveedores=null;
    public static Kardex es_kardex=null;
    public static Proveedores es_proveedores=null;
    public static Puc es_puc=null;
    public static Puc es_puc_padre=null;
    public static Puc es_puc_causaciondebito=null;
    public static Puc es_puc_causacioncredito=null;
    public static List<Puc> li_puc=null;
    public static List<DocumentoSoporte> li_documentosoporte=null;
    public static Terceros es_terceros=null;
    public static List<Terceros> li_terceros=null;
    public static List<GenMunicipios> li_ciudades=null;
    public static GenMunicipios es_ciudades=null;
    public static LibroAuxiliar es_libroauxiliar=null;
    public static List<LibroAuxiliar> li_libroauxiliar=null;
    public static LibroMayorBalances es_libromayorbalances=null;
    public static List<LibroMayorBalances> li_libromayorbalances=null;
    public static BalancePrueba balancePrueba=null;
    public static List<BalancePrueba> li_balaBalancePruebas=null;
    public static List<Producto> li_productos=null;
    public static List<Producto> li_servicios=null;
    public static List<FacturaProveedores> li_facturaproveedores=null;
    public static List<Factura> li_facturas=null;
    public static List<Kardex> li_kardex=null;
    //****recursos objetos globales
    public static String[] as_puc;
    public static String[] as_productos;
    public static String[] as_servicios;
    public static String[] as_documentosoporte=new String[7];
    public static String[] as_terceros=new String[7];
    public static Set<String> possibleSuggestions = new HashSet<>(Arrays.asList(as_terceros));
    public static Set<String> possibleSuggestions_docsoporte = new HashSet<>(Arrays.asList(as_documentosoporte));
    public static String[] as_ciudades=new String[2];
    public static List<DTOFactura> li_dtoFacturas=null;
    public static DTOFactura dtofactura=null;
    public static BigDecimal totaldebe=BigDecimal.ZERO;
    public static BigDecimal totalhaber=BigDecimal.ZERO;
    public static Long id_source_mov;

}