/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
/**
 *
 * @author karol
 */
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "PRODUCTO")
public class Producto implements Serializable {
     @Column(name = "ID", table = "PRODUCTO", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_PRODUCTO_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_PRODUCTO_ID", sequenceName = "SEQ_PRODUCTO_ID", allocationSize = 1, initialValue = 1)
    private Long id;
    @ManyToOne(optional = true, targetEntity = HclCups.class)
    @JoinColumn(name="HCLCUPS_ID")
    private HclCups hclCups;
    @ManyToOne(optional = true, targetEntity = HclCups.class)
    @JoinColumn(name="HCLCUPSMED_ID")
    private HclCups hclCupsMed;
    @ManyToOne(optional = true, targetEntity = GenCategoriasProductos.class)
    @JoinColumn(name="GENCATEGORIASPRODUCTOS_ID")
    private GenCategoriasProductos genCategoriasProductos;
    @ManyToOne(optional = true, targetEntity = MedPresentacionMedicamentos.class)
    @JoinColumn(name="MEDPRESENTACIONMEDICAMENTOS_ID")
    private MedPresentacionMedicamentos medFormasFarmaceuticas;
     @ManyToOne(optional = true, targetEntity = MedUnidadDosis.class)
    @JoinColumn(name="MEDUNIDADDOSIS_ID")
    private MedUnidadDosis unidadmedida;
     @ManyToOne(cascade = CascadeType.ALL)
     AgnEspecialidades agnEspecialidades;
    @Column(length = 150)
    private String presentacioncomercial;
    @Column(length = 50)
    private String principioactivo;
    private int medida;
    private int concentracion;
    @Column(length = 50)
    private String laboratorio;
    @Column(length = 50)
    private String reginvima;
    @Column(length = 50)
    private String marca;
    private String codigo;
    private int codigo2 = 0;
    @Column(precision=12,scale=2)
    private BigDecimal costo = BigDecimal.ZERO;
    @Column(precision=12,scale=2)
    private BigDecimal precio = BigDecimal.ZERO;
    private String nombre;
    private String descrip;
    private String tipo;
    private String estado;
    private boolean esmateriaprima;
    private String codigosrelacionados; 
    private int iva;
    private int ude;
    private float monto;
    private String tipo_radiografia;
    private String forma_liquidar;
    private float incremento;
    private String codequivalente;
    private int tipo_medicamentoinsumos;
    private int stock_minimo;
    private int uso;
    private int tipoimagen;
    @Column(precision=12,scale=2)
    private BigDecimal precio2015 = BigDecimal.ZERO;
    @Column(precision=12,scale=2)
    private BigDecimal precio2016 = BigDecimal.ZERO;
    @Column(precision=12,scale=2)
    private BigDecimal precio2017 = BigDecimal.ZERO;
     @Column(precision=12,scale=2)
    private BigDecimal precio2018 = BigDecimal.ZERO;
     @Column(precision=12,scale=2)
    private BigDecimal precio2001 = BigDecimal.ZERO;
     @Column(precision=12,scale=2)
    private BigDecimal precio2015_soat = BigDecimal.ZERO;
    @Column(precision=12,scale=2)
    private BigDecimal precio2016_soat = BigDecimal.ZERO;
    @Column(precision=12,scale=2)
    private BigDecimal precio2017_soat = BigDecimal.ZERO;
     @Column(precision=12,scale=2)
    private BigDecimal precio2018_soat = BigDecimal.ZERO;
    @Column(precision=12,scale=2)
    private BigDecimal precio2001_soat = BigDecimal.ZERO;
    @Column(precision=12,scale=2)
    private BigDecimal precio2020 = BigDecimal.ZERO;
     @Column(precision=12,scale=2)
    private BigDecimal precio2020_soat = BigDecimal.ZERO;
    private String codigo_barras;
@Transient
    private int m_iCantidad;
@Lob
    private byte[]img;
  @Column(name = "FECHA_MODIFICACION")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaModificacion;
   @Column(name = "FECHA_CREACION")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechaCreacion;
     @Column(name = "USUARIO_CREADOR",length = 50)
    @Basic
    private String usuarioCreador;  
    
    @OneToMany(fetch = FetchType.EAGER,mappedBy="hclCupsProducto",orphanRemoval = true)
    private List<ProductoMedicamentos> medicamentosItems = new ArrayList<ProductoMedicamentos>();
   
     private int tipo_medicamento;
     private  String formafarmaceutica;
     private  String concentracionmedicamento;
     private String unidadmedidamedicamento;
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
@Column(precision=12,scale=2)
    public BigDecimal getCosto() {
        return costo;
    }

    public void setCosto(BigDecimal costo) {
        this.costo = costo;
    }

    public String getDescrip() {
        return descrip;
    }

    public void setDescrip(String descrip) {
        this.descrip = descrip;
    }

    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
@Column(precision=12,scale=2)
    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    /**
     * @return the tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * @return the no_hab
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }

    /**
     * @return the usuario
     */
    public int getCodigo2() {
        return codigo2;
    }

    public void setCodigo2(int codigo2) {
        this.codigo2 = codigo2;
    }

 
    public int getM_iCantidad() {
        return m_iCantidad;
    }

    public void setM_iCantidad(int m_iCantidad) {
        this.m_iCantidad = m_iCantidad;
    }
    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public boolean isEsmateriaprima() {
        return esmateriaprima;
    }

    public void setEsmateriaprima(boolean esmateriaprima) {
        this.esmateriaprima = esmateriaprima;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public String getCodigosrelacionados() {
        return codigosrelacionados;
    }

    public void setCodigosrelacionados(String codigosrelacionados) {
        this.codigosrelacionados = codigosrelacionados;
    }

    public int getUde() {
        return ude;
    }

    public void setUde(int ude) {
        this.ude = ude;
    }

    public Date getFechaModificacion() {
        return fechaModificacion;
    }

    public void setFechaModificacion(Date fechaModificacion) {
        this.fechaModificacion = fechaModificacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(String usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    public HclCups getHclCups() {
        return hclCups;
    }

    public void setHclCups(HclCups hclCups) {
        this.hclCups = hclCups;
    }

    public GenCategoriasProductos getGenCategoriasProductos() {
        return genCategoriasProductos;
    }

    public void setGenCategoriasProductos(GenCategoriasProductos genCategoriasProductos) {
        this.genCategoriasProductos = genCategoriasProductos;
    }

    public MedPresentacionMedicamentos getMedFormasFarmaceuticas() {
        return medFormasFarmaceuticas;
    }

    public void setMedFormasFarmaceuticas(MedPresentacionMedicamentos medFormasFarmaceuticas) {
        this.medFormasFarmaceuticas = medFormasFarmaceuticas;
    }

    public MedUnidadDosis getUnidadmedida() {
        return unidadmedida;
    }

    public void setUnidadmedida(MedUnidadDosis unidadmedida) {
        this.unidadmedida = unidadmedida;
    }

    public String getPresentacioncomercial() {
        return presentacioncomercial;
    }

    public void setPresentacioncomercial(String presentacioncomercial) {
        this.presentacioncomercial = presentacioncomercial;
    }

    public String getPrincipioactivo() {
        return principioactivo;
    }

    public void setPrincipioactivo(String principioactivo) {
        this.principioactivo = principioactivo;
    }

    public int getMedida() {
        return medida;
    }

    public void setMedida(int medida) {
        this.medida = medida;
    }

    public int getConcentracion() {
        return concentracion;
    }

    public void setConcentracion(int concentracion) {
        this.concentracion = concentracion;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getReginvima() {
        return reginvima;
    }

    public void setReginvima(String reginvima) {
        this.reginvima = reginvima;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public String getTipo_radiografia() {
        return tipo_radiografia;
    }

    public void setTipo_radiografia(String tipo_radiografia) {
        this.tipo_radiografia = tipo_radiografia;
    }

    public String getForma_liquidar() {
        return forma_liquidar;
    }

    public void setForma_liquidar(String forma_liquidar) {
        this.forma_liquidar = forma_liquidar;
    }

    public float getIncremento() {
        return incremento;
    }

    public void setIncremento(float incremento) {
        this.incremento = incremento;
    }

    public String getCodequivalente() {
        return codequivalente;
    }

    public void setCodequivalente(String codequivalente) {
        this.codequivalente = codequivalente;
    }

    public int getTipo_medicamentoinsumos() {
        return tipo_medicamentoinsumos;
    }

    public void setTipo_medicamentoinsumos(int tipo_medicamentoinsumos) {
        this.tipo_medicamentoinsumos = tipo_medicamentoinsumos;
    }

    public int getStock_minimo() {
        return stock_minimo;
    }

    public void setStock_minimo(int stock_minimo) {
        this.stock_minimo = stock_minimo;
    }

    public int getUso() {
        return uso;
    }

    public void setUso(int uso) {
        this.uso = uso;
    }

    public String getCodigo_barras() {
        return codigo_barras;
    }

    public void setCodigo_barras(String codigo_barras) {
        this.codigo_barras = codigo_barras;
    }

    public BigDecimal getPrecio2015() {
        return precio2015;
    }

    public void setPrecio2015(BigDecimal precio2015) {
        this.precio2015 = precio2015;
    }

    public BigDecimal getPrecio2016() {
        return precio2016;
    }

    public void setPrecio2016(BigDecimal precio2016) {
        this.precio2016 = precio2016;
    }

    public BigDecimal getPrecio2017() {
        return precio2017;
    }

    public void setPrecio2017(BigDecimal precio2017) {
        this.precio2017 = precio2017;
    }

    public HclCups getHclCupsMed() {
        return hclCupsMed;
    }

    public void setHclCupsMed(HclCups hclCupsMed) {
        this.hclCupsMed = hclCupsMed;
    }

    public List<ProductoMedicamentos> getMedicamentosItems() {
        return medicamentosItems;
    }

    public void setMedicamentosItems(List<ProductoMedicamentos> medicamentosItems) {
        this.medicamentosItems = medicamentosItems;
    }

    public int getTipo_medicamento() {
        return tipo_medicamento;
    }

    public void setTipo_medicamento(int tipo_medicamento) {
        this.tipo_medicamento = tipo_medicamento;
    }

    public String getFormafarmaceutica() {
        return formafarmaceutica;
    }

    public void setFormafarmaceutica(String formafarmaceutica) {
        this.formafarmaceutica = formafarmaceutica;
    }

    public String getConcentracionmedicamento() {
        return concentracionmedicamento;
    }

    public void setConcentracionmedicamento(String concentracionmedicamento) {
        this.concentracionmedicamento = concentracionmedicamento;
    }

    public String getUnidadmedidamedicamento() {
        return unidadmedidamedicamento;
    }

    public void setUnidadmedidamedicamento(String unidadmedidamedicamento) {
        this.unidadmedidamedicamento = unidadmedidamedicamento;
    }

   
    public void AddMedicamentos(Producto producto,float cantidad)
    {
        ProductoMedicamentos productoMedicamentos=new ProductoMedicamentos();
        for(ProductoMedicamentos pm :medicamentosItems)
        {
            if(pm.equals(producto))
            {
                return;
            }
            
        }
        productoMedicamentos.setCantidad(cantidad);
        productoMedicamentos.setHclCupsProducto(this);
        productoMedicamentos.setMedicamento(producto);
        medicamentosItems.add(productoMedicamentos);
    }
     public void removeMedicamentos(Producto producto)
    {
       for(ProductoMedicamentos pm :medicamentosItems)
        {
            if(pm.getMedicamento().equals(producto))
            { 
                medicamentosItems.remove(pm);
                return;
            }
            
        }
    }

    public BigDecimal getPrecio2018() {
        return precio2018;
    }

    public void setPrecio2018(BigDecimal precio2018) {
        this.precio2018 = precio2018;
    }

    public AgnEspecialidades getAgnEspecialidades() {
        return agnEspecialidades;
    }

    public void setAgnEspecialidades(AgnEspecialidades agnEspecialidades) {
        this.agnEspecialidades = agnEspecialidades;
    }

    public int getTipoimagen() {
        return tipoimagen;
    }

    public void setTipoimagen(int tipoimagen) {
        this.tipoimagen = tipoimagen;
    }

    public BigDecimal getPrecio2001() {
        return precio2001;
    }

    public void setPrecio2001(BigDecimal precio2001) {
        this.precio2001 = precio2001;
    }

    public BigDecimal getPrecio2015_soat() {
        return precio2015_soat;
    }

    public void setPrecio2015_soat(BigDecimal precio2015_soat) {
        this.precio2015_soat = precio2015_soat;
    }

    public BigDecimal getPrecio2016_soat() {
        return precio2016_soat;
    }

    public void setPrecio2016_soat(BigDecimal precio2016_soat) {
        this.precio2016_soat = precio2016_soat;
    }

    public BigDecimal getPrecio2017_soat() {
        return precio2017_soat;
    }

    public void setPrecio2017_soat(BigDecimal precio2017_soat) {
        this.precio2017_soat = precio2017_soat;
    }

    public BigDecimal getPrecio2018_soat() {
        return precio2018_soat;
    }

    public void setPrecio2018_soat(BigDecimal precio2018_soat) {
        this.precio2018_soat = precio2018_soat;
    }

    public BigDecimal getPrecio2001_soat() {
        return precio2001_soat;
    }

    public void setPrecio2001_soat(BigDecimal precio2001_soat) {
        this.precio2001_soat = precio2001_soat;
    }

    public BigDecimal getPrecio2020() {
        return precio2020;
    }

    public void setPrecio2020(BigDecimal precio2020) {
        this.precio2020 = precio2020;
    }

    public BigDecimal getPrecio2020_soat() {
        return precio2020_soat;
    }

    public void setPrecio2020_soat(BigDecimal precio2020_soat) {
        this.precio2020_soat = precio2020_soat;
    }

   
  
  
}
