/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author adminlinux
 */
@Entity
@Table(name = "HclFurisp")
public class HclFurisp implements Serializable {
    @Column(name = "ID", table = "HclFurisp", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_HCLFURISP_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_HCLFURISP_ID", sequenceName = "SEQ_HCLFURISP_ID", allocationSize = 1, initialValue = 1)
    private  Long id; 
    @Column(name = "FECHARADICACION")
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fecharadicacion;
    private String noradicado;
    private boolean rg;
    @ManyToOne(optional = true, targetEntity = Factura.class)
    @JoinColumn(name="FACTURA_ID")
    private Factura factura;
    private String noradicadoanterior;
    @ManyToOne(optional = true, targetEntity = GenPacientes.class)
    @JoinColumn(name="GENPACIENTES_ID")
    //datos victima
    private GenPacientes victima;
    private int condicionaccidentado;
    //datos evento catastrofico y acc de transito
    private int naturalezaevento;
    private String naturaleza;
    private String direccionocurrencia;
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fecharevento;
    private int hora;
    private int minutos;
    @ManyToOne(optional = true, targetEntity = GenMunicipios.class)
    @JoinColumn(name="GENMUNICIPIOS_ID")
    private GenMunicipios III_genMunicipios;
    @ManyToOne(optional = true, targetEntity = GenZonaResidencia.class)
    @JoinColumn(name="GENZONARESIDENCIA_ID")
    private GenZonaResidencia genZonaResidencia;
    private String descripciobreve;
    //datos del vehiculo
    private int estadoaseguramiento;
    private String marca;
    private String placa;
    private int tiposervicio;
    private String nombreaseguradora;
    private String nopoliza;
    private boolean intervencionautoridad;
    private boolean cobroexcedentepoliza;
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fecharvigenciadesde;
    @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fechavigenciahasta;
    
    //datos del propietario vehiculo
     @ManyToOne(optional = true, targetEntity = GenPersonas.class,cascade = CascadeType.ALL)
     @JoinColumn(name="PROPIETARIOVEHICULO_ID")
     private GenPersonas propietariovehiculo;
     //DATOS DEL CONDUCTOR DEL VEHICULO
     @ManyToOne(optional = true, targetEntity = GenPersonas.class,cascade = CascadeType.ALL)
     @JoinColumn(name="CONDUCTORVEHICULO_ID")
     private GenPersonas consuctorvehiculo;
       @ManyToOne(optional = true, targetEntity = GenPersonas.class,cascade = CascadeType.ALL)
     @JoinColumn(name="VIII_CONDUCTORVEHICULO_ID")
     private GenPersonas VIII_consuctorvehiculo;
     //DATOS DE REMISION
     @Temporal(TemporalType.TIMESTAMP)
    @Basic
    private Date fecharemision;
    private String personaremitidade;
    private String personaqueremite;
    private String direccionipsqueremite;
    private String dptoipsqueremite;
     @ManyToOne(optional = true, targetEntity = GenMunicipios.class)
    @JoinColumn(name="V_GENMUNICIPIOS_ID")
    private GenMunicipios V_genMunicipios;
     @Temporal(TemporalType.TIMESTAMP)
    @Basic
     private Date fechaaceptacion;
     private String personaremitidaa;
     private String personaquerecibe;
     private String direccionipsquerecibe;
    @ManyToOne(optional = true, targetEntity = GenMunicipios.class)
    @JoinColumn(name="VI_GENMUNICIPIOS_ID")
    private GenMunicipios VI_genMunicipios;
    @ManyToOne(optional = true, targetEntity = GenMunicipios.class)
    @JoinColumn(name="VII_GENMUNICIPIOSREMITE_ID")
    private GenMunicipios VII_genMunicipiosRemite;
    @ManyToOne(optional = true, targetEntity = GenMunicipios.class)
    @JoinColumn(name="VII_GENMUNICIPIOSRECIBE_ID")
    private GenMunicipios VII_genMunicipiosRecibe;
    
    private String placamovvictima;
     @ManyToOne(optional = true, targetEntity = GenPersonas.class,cascade = CascadeType.ALL)
     @JoinColumn(name="CONDUCTORVEHICULOMOVVICTIMA_ID")
     private GenPersonas consuctorvehiculomovvictima;
     private String trasnportodesde;
     private String transportehasta;
     private int tipotransporte;
     private int zonarecogevictima;
     @Temporal(TemporalType.TIMESTAMP)
     @Basic
     private Date fechaingreso;
     private int horaingreso;
     private int minutosingreso;
     @Temporal(TemporalType.TIMESTAMP)
     @Basic
     private Date fechaegreso;
     private int horaegreso;
     private int minutosegreso;
     @ManyToOne(optional = true, targetEntity = HclCie10.class)
     @JoinColumn(name="DXMAININIT_ID")
     private HclCie10 dxmaininit;
     @ManyToOne(optional = true, targetEntity = HclCie10.class)
     @JoinColumn(name="DXOTHERINIT_ID")
     private HclCie10 dxtherinit;
     @ManyToOne(optional = true, targetEntity = HclCie10.class)
     @JoinColumn(name="DXOTHER2INIT_ID")
     private HclCie10 dxther2init;
      @ManyToOne(optional = true, targetEntity = HclCie10.class)
     @JoinColumn(name="DXMAINEND_ID")
     private HclCie10 dxmainend;
     @ManyToOne(optional = true, targetEntity = HclCie10.class)
     @JoinColumn(name="DXOTHEREND_ID")
     private HclCie10 dxtherend;
     @ManyToOne(optional = true, targetEntity = HclCie10.class)
     @JoinColumn(name="DXOTHER2END_ID")
     private HclCie10 dxther2end;
     @ManyToOne(optional = true, targetEntity = AgnMedicos.class)
     @JoinColumn(name="AGNMEDICOS_ID")
     private AgnMedicos agnMedicos;
     private boolean gastosmedicosquirurgicos;
     private boolean gastostransportemovvictima;
     private BigDecimal valortotalfacturadogmq;
     private BigDecimal valortotalfacturadotmv;
     private BigDecimal valorreclamadofosygagmq;
     private BigDecimal valorraclamadofosygatmv;
     private int tiporeferencia;
     private String VII_codigoinscripcionremite;
     private String VII_codigoinscripcionrecibe;
     private String VII_profesionalremite;
     private String VII_profesionalrecibe;
     private String VII_profesionalremitecargo;
     private String VII_profesionalrecibecargo;
     private String VII_direccionremite;
     private String VII_direccionrecibe;
     @ManyToOne(optional = true, targetEntity = GenPersonas.class,cascade = CascadeType.ALL)
     @JoinColumn(name="IXPROFESIONALTRATANTE_ID")
     private GenPersonas IX_profesionaltratante;
     private String IX_noregistromedico;
     private BigDecimal X_gastosmedicostotal=BigDecimal.ZERO;
     private BigDecimal X_gastosmedicosfosyga=BigDecimal.ZERO;
     private BigDecimal X_gastostransportetotal=BigDecimal.ZERO;
     private BigDecimal X_gastostransportefosyga=BigDecimal.ZERO;
     @ManyToOne(optional = true, targetEntity = GenZonaResidencia.class)
     @JoinColumn(name="VIII_GENZONA_ID")
     private GenZonaResidencia VIII_zona;
     private String codigoaseguradora;
     private int VI_estadoaseguramiento_vehiculosinvolucrados1;
     @ManyToOne(optional = true, targetEntity = GenTiposDocumentos.class)
     @JoinColumn(name="GENTIPOSDOCUMENTOS_VEHICULOSINVOLUCRADOS1_ID")
     private GenTiposDocumentos VI_tipodocumentos_vehiculosinvolucrados1;
     private String VI_documento_vehiculosinvolucrados1;
     private String VI_placa_vehiculosinvolucrados1;
      private int VI_estadoaseguramiento_vehiculosinvolucrados2;
     @ManyToOne(optional = true, targetEntity = GenTiposDocumentos.class)
     @JoinColumn(name="GENTIPOSDOCUMENTOS_VEHICULOSINVOLUCRADOS2_ID")
     private GenTiposDocumentos VI_tipodocumentos_vehiculosinvolucrados2;
     private String VI_documento_vehiculosinvolucrados2;
     private String VI_placa_vehiculosinvolucrados2;
    public Long getId() 
    {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecharadicacion() {
        return fecharadicacion;
    }

    public void setFecharadicacion(Date fecharadicacion) {
        this.fecharadicacion = fecharadicacion;
    }

    public String getNoradicado() {
        return noradicado;
    }

    public void setNoradicado(String noradicado) {
        this.noradicado = noradicado;
    }

    public boolean isRg() {
        return rg;
    }

    public void setRg(boolean rg) {
        this.rg = rg;
    }

    public Factura getFactura() {
        return factura;
    }

    public void setFactura(Factura factura) {
        this.factura = factura;
    }

    public String getNoradicadoanterior() {
        return noradicadoanterior;
    }

    public void setNoradicadoanterior(String noradicadoanterior) {
        this.noradicadoanterior = noradicadoanterior;
    }

    public GenPacientes getVictima() {
        return victima;
    }

    public void setVictima(GenPacientes victima) {
        this.victima = victima;
    }

    public int getCondicionaccidentado() {
        return condicionaccidentado;
    }

    public void setCondicionaccidentado(int condicionaccidentado) {
        this.condicionaccidentado = condicionaccidentado;
    }

    public int getNaturalezaevento() {
        return naturalezaevento;
    }

    public void setNaturalezaevento(int naturalezaevento) {
        this.naturalezaevento = naturalezaevento;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    public String getDireccionocurrencia() {
        return direccionocurrencia;
    }

    public void setDireccionocurrencia(String direccionocurrencia) {
        this.direccionocurrencia = direccionocurrencia;
    }

    public Date getFecharevento() {
        return fecharevento;
    }

    public void setFecharevento(Date fecharevento) {
        this.fecharevento = fecharevento;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinutos() {
        return minutos;
    }

    public void setMinutos(int minutos) {
        this.minutos = minutos;
    }

    public GenMunicipios getGenMunicipios() {
        return III_genMunicipios;
    }

    public void setGenMunicipios(GenMunicipios genMunicipios) {
        this.III_genMunicipios = genMunicipios;
    }

    public GenZonaResidencia getGenZonaResidencia() {
        return genZonaResidencia;
    }

    public void setGenZonaResidencia(GenZonaResidencia genZonaResidencia) {
        this.genZonaResidencia = genZonaResidencia;
    }

    public String getDescripciobreve() {
        return descripciobreve;
    }

    public void setDescripciobreve(String descripciobreve) {
        this.descripciobreve = descripciobreve;
    }

    public int getEstadoaseguramiento() {
        return estadoaseguramiento;
    }

    public void setEstadoaseguramiento(int estadoaseguramiento) {
        this.estadoaseguramiento = estadoaseguramiento;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public int getTiposervicio() {
        return tiposervicio;
    }

    public void setTiposervicio(int tiposervicio) {
        this.tiposervicio = tiposervicio;
    }

    public String getNombreaseguradora() {
        return nombreaseguradora;
    }

    public void setNombreaseguradora(String nombreaseguradora) {
        this.nombreaseguradora = nombreaseguradora;
    }

    public String getNopoliza() {
        return nopoliza;
    }

    public void setNopoliza(String nopoliza) {
        this.nopoliza = nopoliza;
    }

    public boolean isIntervencionautoridad() {
        return intervencionautoridad;
    }

    public void setIntervencionautoridad(boolean intervencionautoridad) {
        this.intervencionautoridad = intervencionautoridad;
    }

    public boolean isCobroexcedentepoliza() {
        return cobroexcedentepoliza;
    }

    public void setCobroexcedentepoliza(boolean cobroexcedentepoliza) {
        this.cobroexcedentepoliza = cobroexcedentepoliza;
    }

    

    public GenPersonas getPropietariovehiculo() {
        return propietariovehiculo;
    }

    public void setPropietariovehiculo(GenPersonas propietariovehiculo) {
        this.propietariovehiculo = propietariovehiculo;
    }

    public Date getFecharvigenciadesde() {
        return fecharvigenciadesde;
    }

    public void setFecharvigenciadesde(Date fecharvigenciadesde) {
        this.fecharvigenciadesde = fecharvigenciadesde;
    }

    public Date getFechavigenciahasta() {
        return fechavigenciahasta;
    }

    public void setFechavigenciahasta(Date fechavigenciahasta) {
        this.fechavigenciahasta = fechavigenciahasta;
    }

    public GenPersonas getConsuctorvehiculo() {
        return consuctorvehiculo;
    }

    public void setConsuctorvehiculo(GenPersonas consuctorvehiculo) {
        this.consuctorvehiculo = consuctorvehiculo;
    }

    public Date getFecharemision() {
        return fecharemision;
    }

    public void setFecharemision(Date fecharemision) {
        this.fecharemision = fecharemision;
    }

    public String getPersonaremitidade() {
        return personaremitidade;
    }

    public void setPersonaremitidade(String personaremitidade) {
        this.personaremitidade = personaremitidade;
    }

    public String getPersonaqueremite() {
        return personaqueremite;
    }

    public void setPersonaqueremite(String personaqueremite) {
        this.personaqueremite = personaqueremite;
    }

    public String getDireccionipsqueremite() {
        return direccionipsqueremite;
    }

    public void setDireccionipsqueremite(String direccionipsqueremite) {
        this.direccionipsqueremite = direccionipsqueremite;
    }

    public String getDptoipsqueremite() {
        return dptoipsqueremite;
    }

    public void setDptoipsqueremite(String dptoipsqueremite) {
        this.dptoipsqueremite = dptoipsqueremite;
    }

    public GenMunicipios getMunicipioipsqueremite() {
        return V_genMunicipios;
    }

    public void setMunicipioipsqueremite(GenMunicipios municipioipsqueremite) {
        this.V_genMunicipios = municipioipsqueremite;
    }

    public Date getFechaaceptacion() {
        return fechaaceptacion;
    }

    public void setFechaaceptacion(Date fechaaceptacion) {
        this.fechaaceptacion = fechaaceptacion;
    }

    public String getPersonaremitidaa() {
        return personaremitidaa;
    }

    public void setPersonaremitidaa(String personaremitidaa) {
        this.personaremitidaa = personaremitidaa;
    }

    public String getPersonaquerecibe() {
        return personaquerecibe;
    }

    public void setPersonaquerecibe(String personaquerecibe) {
        this.personaquerecibe = personaquerecibe;
    }

    public String getDireccionipsquerecibe() {
        return direccionipsquerecibe;
    }

    public void setDireccionipsquerecibe(String direccionipsquerecibe) {
        this.direccionipsquerecibe = direccionipsquerecibe;
    }

    public GenMunicipios getMunicipioipsquerecibe() {
        return VI_genMunicipios;
    }

    public void setMunicipioipsquerecibe(GenMunicipios municipioipsquerecibe) {
        this.VI_genMunicipios = municipioipsquerecibe;
    }

    public String getPlacamovvictima() {
        return placamovvictima;
    }

    public void setPlacamovvictima(String placamovvictima) {
        this.placamovvictima = placamovvictima;
    }

    public GenPersonas getConsuctorvehiculomovvictima() {
        return consuctorvehiculomovvictima;
    }

    public void setConsuctorvehiculomovvictima(GenPersonas consuctorvehiculomovvictima) {
        this.consuctorvehiculomovvictima = consuctorvehiculomovvictima;
    }

    public String getTrasnportodesde() {
        return trasnportodesde;
    }

    public void setTrasnportodesde(String trasnportodesde) {
        this.trasnportodesde = trasnportodesde;
    }

    public String getTransportehasta() {
        return transportehasta;
    }

    public void setTransportehasta(String transportehasta) {
        this.transportehasta = transportehasta;
    }

    public int getTipotransporte() {
        return tipotransporte;
    }

    public void setTipotransporte(int tipotransporte) {
        this.tipotransporte = tipotransporte;
    }

    public int getZonarecogevictima() {
        return zonarecogevictima;
    }

    public void setZonarecogevictima(int zonarecogevictima) {
        this.zonarecogevictima = zonarecogevictima;
    }

    public Date getFechaingreso() {
        return fechaingreso;
    }

    public void setFechaingreso(Date fechaingreso) {
        this.fechaingreso = fechaingreso;
    }

    public int getHoraingreso() {
        return horaingreso;
    }

    public void setHoraingreso(int horaingreso) {
        this.horaingreso = horaingreso;
    }

    public int getMinutosingreso() {
        return minutosingreso;
    }

    public void setMinutosingreso(int minutosingreso) {
        this.minutosingreso = minutosingreso;
    }

    public Date getFechaegreso() {
        return fechaegreso;
    }

    public void setFechaegreso(Date fechaegreso) {
        this.fechaegreso = fechaegreso;
    }

    public int getHoraegreso() {
        return horaegreso;
    }

    public void setHoraegreso(int horaegreso) {
        this.horaegreso = horaegreso;
    }

    public int getMinutosegreso() {
        return minutosegreso;
    }

    public void setMinutosegreso(int minutosegreso) {
        this.minutosegreso = minutosegreso;
    }

    public HclCie10 getDxmaininit() {
        return dxmaininit;
    }

    public void setDxmaininit(HclCie10 dxmaininit) {
        this.dxmaininit = dxmaininit;
    }

    public HclCie10 getDxtherinit() {
        return dxtherinit;
    }

    public void setDxtherinit(HclCie10 dxtherinit) {
        this.dxtherinit = dxtherinit;
    }

    public HclCie10 getDxther2init() {
        return dxther2init;
    }

    public void setDxther2init(HclCie10 dxther2init) {
        this.dxther2init = dxther2init;
    }

    public HclCie10 getDxmainend() {
        return dxmainend;
    }

    public void setDxmainend(HclCie10 dxmainend) {
        this.dxmainend = dxmainend;
    }

    public HclCie10 getDxtherend() {
        return dxtherend;
    }

    public void setDxtherend(HclCie10 dxtherend) {
        this.dxtherend = dxtherend;
    }

    public HclCie10 getDxther2end() {
        return dxther2end;
    }

    public void setDxther2end(HclCie10 dxther2end) {
        this.dxther2end = dxther2end;
    }

    public AgnMedicos getAgnMedicos() {
        return agnMedicos;
    }

    public void setAgnMedicos(AgnMedicos agnMedicos) {
        this.agnMedicos = agnMedicos;
    }

    public boolean isGastosmedicosquirurgicos() {
        return gastosmedicosquirurgicos;
    }

    public void setGastosmedicosquirurgicos(boolean gastosmedicosquirurgicos) {
        this.gastosmedicosquirurgicos = gastosmedicosquirurgicos;
    }

    public boolean isGastostransportemovvictima() {
        return gastostransportemovvictima;
    }

    public void setGastostransportemovvictima(boolean gastostransportemovvictima) {
        this.gastostransportemovvictima = gastostransportemovvictima;
    }

    public BigDecimal getValortotalfacturadogmq() {
        return valortotalfacturadogmq;
    }

    public void setValortotalfacturadogmq(BigDecimal valortotalfacturadogmq) {
        this.valortotalfacturadogmq = valortotalfacturadogmq;
    }

    public BigDecimal getValortotalfacturadotmv() {
        return valortotalfacturadotmv;
    }

    public void setValortotalfacturadotmv(BigDecimal valortotalfacturadotmv) {
        this.valortotalfacturadotmv = valortotalfacturadotmv;
    }

    public BigDecimal getValorreclamadofosygagmq() {
        return valorreclamadofosygagmq;
    }

    public void setValorreclamadofosygagmq(BigDecimal valorreclamadofosygagmq) {
        this.valorreclamadofosygagmq = valorreclamadofosygagmq;
    }

    public BigDecimal getValorraclamadofosygatmv() {
        return valorraclamadofosygatmv;
    }

    public void setValorraclamadofosygatmv(BigDecimal valorraclamadofosygatmv) {
        this.valorraclamadofosygatmv = valorraclamadofosygatmv;
    }

    public GenMunicipios getIII_genMunicipios() {
        return III_genMunicipios;
    }

    public void setIII_genMunicipios(GenMunicipios III_genMunicipios) {
        this.III_genMunicipios = III_genMunicipios;
    }

    public GenPersonas getVIII_consuctorvehiculo() {
        return VIII_consuctorvehiculo;
    }

    public void setVIII_consuctorvehiculo(GenPersonas VIII_consuctorvehiculo) {
        this.VIII_consuctorvehiculo = VIII_consuctorvehiculo;
    }

    public GenMunicipios getV_genMunicipios() {
        return V_genMunicipios;
    }

    public void setV_genMunicipios(GenMunicipios V_genMunicipios) {
        this.V_genMunicipios = V_genMunicipios;
    }

    public GenMunicipios getVI_genMunicipios() {
        return VI_genMunicipios;
    }

    public void setVI_genMunicipios(GenMunicipios VI_genMunicipios) {
        this.VI_genMunicipios = VI_genMunicipios;
    }

    public GenMunicipios getVII_genMunicipiosRemite() {
        return VII_genMunicipiosRemite;
    }

    public void setVII_genMunicipiosRemite(GenMunicipios VII_genMunicipiosRemite) {
        this.VII_genMunicipiosRemite = VII_genMunicipiosRemite;
    }

    public GenMunicipios getVII_genMunicipiosRecibe() {
        return VII_genMunicipiosRecibe;
    }

    public void setVII_genMunicipiosRecibe(GenMunicipios VII_genMunicipiosRecibe) {
        this.VII_genMunicipiosRecibe = VII_genMunicipiosRecibe;
    }

    public int getTiporeferencia() {
        return tiporeferencia;
    }

    public void setTiporeferencia(int tiporeferencia) {
        this.tiporeferencia = tiporeferencia;
    }

    public String getVII_codigoinscripcionremite() {
        return VII_codigoinscripcionremite;
    }

    public void setVII_codigoinscripcionremite(String VII_codigoinscripcionremite) {
        this.VII_codigoinscripcionremite = VII_codigoinscripcionremite;
    }

    public String getVII_codigoinscripcionrecibe() {
        return VII_codigoinscripcionrecibe;
    }

    public void setVII_codigoinscripcionrecibe(String VII_codigoinscripcionrecibe) {
        this.VII_codigoinscripcionrecibe = VII_codigoinscripcionrecibe;
    }

    public String getVII_profesionalremite() {
        return VII_profesionalremite;
    }

    public void setVII_profesionalremite(String VII_profesionalremite) {
        this.VII_profesionalremite = VII_profesionalremite;
    }

    public String getVII_profesionalrecibe() {
        return VII_profesionalrecibe;
    }

    public void setVII_profesionalrecibe(String VII_profesionalrecibe) {
        this.VII_profesionalrecibe = VII_profesionalrecibe;
    }

    public String getVII_profesionalremitecargo() {
        return VII_profesionalremitecargo;
    }

    public void setVII_profesionalremitecargo(String VII_profesionalremitecargo) {
        this.VII_profesionalremitecargo = VII_profesionalremitecargo;
    }

    public String getVII_profesionalrecibecargo() {
        return VII_profesionalrecibecargo;
    }

    public void setVII_profesionalrecibecargo(String VII_profesionalrecibecargo) {
        this.VII_profesionalrecibecargo = VII_profesionalrecibecargo;
    }

    public String getVII_direccionremite() {
        return VII_direccionremite;
    }

    public void setVII_direccionremite(String VII_direccionremite) {
        this.VII_direccionremite = VII_direccionremite;
    }

    public String getVII_direccionrecibe() {
        return VII_direccionrecibe;
    }

    public void setVII_direccionrecibe(String VII_direccionrecibe) {
        this.VII_direccionrecibe = VII_direccionrecibe;
    }

    public GenPersonas getIX_profesionaltratante() {
        return IX_profesionaltratante;
    }

    public void setIX_profesionaltratante(GenPersonas IX_profesionaltratante) {
        this.IX_profesionaltratante = IX_profesionaltratante;
    }

    public String getIX_noregistromedico() {
        return IX_noregistromedico;
    }

    public void setIX_noregistromedico(String IX_noregistromedico) {
        this.IX_noregistromedico = IX_noregistromedico;
    }

    public BigDecimal getX_gastosmedicostotal() {
        return X_gastosmedicostotal;
    }

    public void setX_gastosmedicostotal(BigDecimal X_gastosmedicostotal) {
        this.X_gastosmedicostotal = X_gastosmedicostotal;
    }

    public BigDecimal getX_gastosmedicosfosyga() {
        return X_gastosmedicosfosyga;
    }

    public void setX_gastosmedicosfosyga(BigDecimal X_gastosmedicosfosyga) {
        this.X_gastosmedicosfosyga = X_gastosmedicosfosyga;
    }

    public BigDecimal getX_gastostransportetotal() {
        return X_gastostransportetotal;
    }

    public void setX_gastostransportetotal(BigDecimal X_gastostransportetotal) {
        this.X_gastostransportetotal = X_gastostransportetotal;
    }

    public BigDecimal getX_gastostransportefosyga() {
        return X_gastostransportefosyga;
    }

    public void setX_gastostransportefosyga(BigDecimal X_gastostransportefosyga) {
        this.X_gastostransportefosyga = X_gastostransportefosyga;
    }

    public GenZonaResidencia getVIII_zona() {
        return VIII_zona;
    }

    public void setVIII_zona(GenZonaResidencia VIII_zona) {
        this.VIII_zona = VIII_zona;
    }

    public String getCodigoaseguradora() {
        return codigoaseguradora;
    }

    public void setCodigoaseguradora(String codigoaseguradora) {
        this.codigoaseguradora = codigoaseguradora;
    }

    public int getVI_estadoaseguramiento_vehiculosinvolucrados1() {
        return VI_estadoaseguramiento_vehiculosinvolucrados1;
    }

    public void setVI_estadoaseguramiento_vehiculosinvolucrados1(int VI_estadoaseguramiento_vehiculosinvolucrados1) {
        this.VI_estadoaseguramiento_vehiculosinvolucrados1 = VI_estadoaseguramiento_vehiculosinvolucrados1;
    }

    public GenTiposDocumentos getVI_tipodocumentos_vehiculosinvolucrados1() {
        return VI_tipodocumentos_vehiculosinvolucrados1;
    }

    public void setVI_tipodocumentos_vehiculosinvolucrados1(GenTiposDocumentos VI_tipodocumentos_vehiculosinvolucrados1) {
        this.VI_tipodocumentos_vehiculosinvolucrados1 = VI_tipodocumentos_vehiculosinvolucrados1;
    }

    
    

    public int getVI_estadoaseguramiento_vehiculosinvolucrados2() {
        return VI_estadoaseguramiento_vehiculosinvolucrados2;
    }

    public void setVI_estadoaseguramiento_vehiculosinvolucrados2(int VI_estadoaseguramiento_vehiculosinvolucrados2) {
        this.VI_estadoaseguramiento_vehiculosinvolucrados2 = VI_estadoaseguramiento_vehiculosinvolucrados2;
    }

    public GenTiposDocumentos getVI_tipodocumentos_vehiculosinvolucrados2() {
        return VI_tipodocumentos_vehiculosinvolucrados2;
    }

    public void setVI_tipodocumentos_vehiculosinvolucrados2(GenTiposDocumentos VI_tipodocumentos_vehiculosinvolucrados2) {
        this.VI_tipodocumentos_vehiculosinvolucrados2 = VI_tipodocumentos_vehiculosinvolucrados2;
    }

    public String getVI_documento_vehiculosinvolucrados1() {
        return VI_documento_vehiculosinvolucrados1;
    }

    public void setVI_documento_vehiculosinvolucrados1(String VI_documento_vehiculosinvolucrados1) {
        this.VI_documento_vehiculosinvolucrados1 = VI_documento_vehiculosinvolucrados1;
    }

    public String getVI_documento_vehiculosinvolucrados2() {
        return VI_documento_vehiculosinvolucrados2;
    }

    public void setVI_documento_vehiculosinvolucrados2(String VI_documento_vehiculosinvolucrados2) {
        this.VI_documento_vehiculosinvolucrados2 = VI_documento_vehiculosinvolucrados2;
    }

    public String getVI_placa_vehiculosinvolucrados2() {
        return VI_placa_vehiculosinvolucrados2;
    }

    public void setVI_placa_vehiculosinvolucrados2(String VI_placa_vehiculosinvolucrados2) {
        this.VI_placa_vehiculosinvolucrados2 = VI_placa_vehiculosinvolucrados2;
    }

    public String getVI_placa_vehiculosinvolucrados1() {
        return VI_placa_vehiculosinvolucrados1;
    }

    public void setVI_placa_vehiculosinvolucrados1(String VI_placa_vehiculosinvolucrados1) {
        this.VI_placa_vehiculosinvolucrados1 = VI_placa_vehiculosinvolucrados1;
    }

   
   
    
    
}
