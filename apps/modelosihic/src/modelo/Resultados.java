/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author karol
 */

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
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
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "RESULTADOS")
public class Resultados
    implements Serializable
{
    @Column(name = "ID", table = "RESULTADOS", unique = false, updatable = true, insertable = true, nullable = false, length = 255, scale = 0, precision = 22)
    @Id
    @GeneratedValue(generator = "SEQ_RESULTADOS_ID", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SEQ_RESULTADOS_ID", sequenceName = "SEQ_RESULTADOS_ID", allocationSize = 1, initialValue = 1)
    private Long       id;
    private int plantilla;
    private String     descripcion;
    private String     descripcion_corta;
    private String     fur;
    private String     fpp;
    private String     opinion;
    private String     examensolicitado;
    private String     dbp; 
    private String     cc; 
    private String     ca; 
    private String     lf;
    private String     peso; 
    private String     placenta; 
    private String     cervix; 
    private String     liquidoamniotico;
    private String     tonofetal;
    private String     movfetal;
    private String     movrespiratorio;
    private String     liquidoamniotico2;
    private String     velmax;
    private String     velmin;
    private String     ir;
    private String     testiculoder;
    private String     testiculoizq;
    private String     epidimoder;
    private String     epidimoizq;
    private String     rinonder;
    private String     rinosizq;
    private String     arcuatavelmaxima;
    private String     arcuatavelminima;
    private String     arcuatair;
    private String     segmentariaelmaxima;
    private String     segmentariavelminima;
    private String     segmentariair;
    private String     renalvelmaxima;
    private String     renalvelminima;
    private String     renalir;
    private String     bulillarvelmaxima;
    private String     bulillarvelminima;
    private String     bulillarir;
    private String     aortavelmaxima;
    private String     aortavelminima;
    private String     aortair;
    private String     utero;
    private String     pelv_endometrio;
    private String     pelv_ovaderecho;
    private String     pelv_ovaizquierdo;
    private String     fet_artuterinaindresist; 
    private String     fet_artuterinaindpulsati; 
    private String     fet_artumbilicalindresist; 
    private String     fet_artumbilicalindpulsati; 
    private String     fet_artcerebralindresist; 
    private String     fet_artcerebralindpulsati; 
    private String     fet_aortoracicaindresist; 
    private String     fet_aortoracicaindpulsati; 
    private String     dopmieminf_femoralcomun_velmax;
    private String     dopmieminf_femoralcomun_velmin;
    private String     dopmieminf_femoralcomun_indresist;
    private String     dopmieminf_femoralsuperf_velmax;
    private String     dopmieminf_femoralsuperf_velmin;
    private String     dopmieminf_femoralsuperf_indresist;
    private String     dopmieminf_poplitea_velmax;
    private String     dopmieminf_poplitea_velmin;
    private String     dopmieminf_poplitea_indresist;
    private String     dopmieminf_tibialanterior_velmax;
    private String     dopmieminf_tibialanterior_velmin;
    private String     dopmieminf_tibialanterior_indresist;
     private String     dopmieminf_tibialposterior_velmax;
    private String     dopmieminf_tibialposterior_velmin;
    private String     dopmieminf_tibialposterior_indresist;
    private String     dopmieminf_pedia_velmax;
    private String     dopmieminf_pedia_velmin;
    private String     dopmieminf_pedia_indresist;
    private String     dopvemieminf_ldersvp;
    private String     dopvemieminf_ldersvs;
    private String     dopvemieminf_lizqsvp;
    private String     dopvemieminf_lizqsvs;
    private String     dopartmieminf_lderafc;
    private String     dopartmieminf_lderafs;
    private String     dopartmieminf_lderapoplitea;
    private String     dopartmieminf_lderata;
    private String     dopartmieminf_lderatp;
    private String     dopartmieminf_lizqafc;
    private String     dopartmieminf_lizqafs;
    private String     dopartmieminf_lizqapoplitea;
    private String     dopartmieminf_lizqata;
    private String     dopartmieminf_lizqatp;
    private String     dopvemieminf_lizqvenafemoralc;
    private String     dopvemieminf_lizqvenafemoralsp;
    private String     dopvemieminf_lizqcayadosafenainterna;
    private String     dopvemieminf_lizqsafena;
    private String     dopvemieminf_lizqvenapoplitea;
    private String     dopvemieminf_lizqcayadosafenaexterna;
    private String     dopvemieminf_lizqcayadosafenaexternaperforantes;
    private String     dopvemieminf_lizqtibialposterior;
    private String     dopvemieminf_ldervenafemoralc;
    private String     dopvemieminf_ldervenafemoralsp;
    private String     dopvemieminf_ldercayadosafenainterna;
    private String     dopvemieminf_ldersafena;
    private String     dopvemieminf_ldervenapoplitea;
    private String     dopvemieminf_ldercayadosafenaexterna;
    private String     dopvemieminf_ldercayadosafenaexternaperforantes;
    private String     dopvemieminf_ldertibialposterior;
    private String     procedimiento;
    private String     frecuenciacardiacafetal;
    private String birads_mamaderecha;
    private String birads_mamaizquierda;
    
    @ManyToOne
    @JoinColumn(name="HCLCIE10_ID")
    private HclCie10 hclCie10;
    @ManyToOne
    @JoinColumn(name="HCLCONSULTAS_ID")
    private HclConsultas   hclConsultas;
    @ManyToOne
    @JoinColumn(name="FACTURAITEM_ID")
    private FacturaItem   facturaItem;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

  

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getExamensolicitado() {
        return examensolicitado;
    }

    public void setExamensolicitado(String examensolicitado) {
        this.examensolicitado = examensolicitado;
    }

    

    public String getUtero() {
        return utero;
    }

    public void setUtero(String utero) {
        this.utero = utero;
    }

    public String getPelv_endometrio() {
        return pelv_endometrio;
    }

    public void setPelv_endometrio(String pelv_endometrio) {
        this.pelv_endometrio = pelv_endometrio;
    }

    public String getPelv_ovaderecho() {
        return pelv_ovaderecho;
    }

    public void setPelv_ovaderecho(String pelv_ovaderecho) {
        this.pelv_ovaderecho = pelv_ovaderecho;
    }

    public String getPelv_ovaizquierdo() {
        return pelv_ovaizquierdo;
    }

    public void setPelv_ovaizquierdo(String pelv_ovaizquierdo) {
        this.pelv_ovaizquierdo = pelv_ovaizquierdo;
    }

    public String getFet_artuterinaindresist() {
        return fet_artuterinaindresist;
    }

    public void setFet_artuterinaindresist(String fet_artuterinaindresist) {
        this.fet_artuterinaindresist = fet_artuterinaindresist;
    }

    public String getFet_artuterinaindpulsati() {
        return fet_artuterinaindpulsati;
    }

    public void setFet_artuterinaindpulsati(String fet_artuterinaindpulsati) {
        this.fet_artuterinaindpulsati = fet_artuterinaindpulsati;
    }

    public String getFet_artumbilicalindresist() {
        return fet_artumbilicalindresist;
    }

    public void setFet_artumbilicalindresist(String fet_artumbilicalindresist) {
        this.fet_artumbilicalindresist = fet_artumbilicalindresist;
    }

    public String getFet_artumbilicalindpulsati() {
        return fet_artumbilicalindpulsati;
    }

    public void setFet_artumbilicalindpulsati(String fet_artumbilicalindpulsati) {
        this.fet_artumbilicalindpulsati = fet_artumbilicalindpulsati;
    }

    public String getFet_artcerebralindresist() {
        return fet_artcerebralindresist;
    }

    public void setFet_artcerebralindresist(String fet_artcerebralindresist) {
        this.fet_artcerebralindresist = fet_artcerebralindresist;
    }

    public String getFet_artcerebralindpulsati() {
        return fet_artcerebralindpulsati;
    }

    public void setFet_artcerebralindpulsati(String fet_artcerebralindpulsati) {
        this.fet_artcerebralindpulsati = fet_artcerebralindpulsati;
    }

    public String getFet_aortoracicaindresist() {
        return fet_aortoracicaindresist;
    }

    public void setFet_aortoracicaindresist(String fet_aortoracicaindresist) {
        this.fet_aortoracicaindresist = fet_aortoracicaindresist;
    }

    public String getFet_aortoracicaindpulsati() {
        return fet_aortoracicaindpulsati;
    }

    public void setFet_aortoracicaindpulsati(String fet_aortoracicaindpulsati) {
        this.fet_aortoracicaindpulsati = fet_aortoracicaindpulsati;
    }

    public String getDopmieminf_femoralcomun_velmax() {
        return dopmieminf_femoralcomun_velmax;
    }

    public void setDopmieminf_femoralcomun_velmax(String dopmieminf_femoralcomun_velmax) {
        this.dopmieminf_femoralcomun_velmax = dopmieminf_femoralcomun_velmax;
    }

    public String getDopmieminf_femoralcomun_velmin() {
        return dopmieminf_femoralcomun_velmin;
    }

    public void setDopmieminf_femoralcomun_velmin(String dopmieminf_femoralcomun_velmin) {
        this.dopmieminf_femoralcomun_velmin = dopmieminf_femoralcomun_velmin;
    }

    public String getDopmieminf_femoralcomun_indresist() {
        return dopmieminf_femoralcomun_indresist;
    }

    public void setDopmieminf_femoralcomun_indresist(String dopmieminf_femoralcomun_indresist) {
        this.dopmieminf_femoralcomun_indresist = dopmieminf_femoralcomun_indresist;
    }

    public String getDopmieminf_femoralsuperf_velmax() {
        return dopmieminf_femoralsuperf_velmax;
    }

    public void setDopmieminf_femoralsuperf_velmax(String dopmieminf_femoralsuperf_velmax) {
        this.dopmieminf_femoralsuperf_velmax = dopmieminf_femoralsuperf_velmax;
    }

    public String getDopmieminf_femoralsuperf_velmin() {
        return dopmieminf_femoralsuperf_velmin;
    }

    public void setDopmieminf_femoralsuperf_velmin(String dopmieminf_femoralsuperf_velmin) {
        this.dopmieminf_femoralsuperf_velmin = dopmieminf_femoralsuperf_velmin;
    }

    public String getDopmieminf_femoralsuperf_indresist() {
        return dopmieminf_femoralsuperf_indresist;
    }

    public void setDopmieminf_femoralsuperf_indresist(String dopmieminf_femoralsuperf_indresist) {
        this.dopmieminf_femoralsuperf_indresist = dopmieminf_femoralsuperf_indresist;
    }

    public String getDopmieminf_poplitea_velmax() {
        return dopmieminf_poplitea_velmax;
    }

    public void setDopmieminf_poplitea_velmax(String dopmieminf_poplitea_velmax) {
        this.dopmieminf_poplitea_velmax = dopmieminf_poplitea_velmax;
    }

    public String getDopmieminf_poplitea_velmin() {
        return dopmieminf_poplitea_velmin;
    }

    public void setDopmieminf_poplitea_velmin(String dopmieminf_poplitea_velmin) {
        this.dopmieminf_poplitea_velmin = dopmieminf_poplitea_velmin;
    }

    public String getDopmieminf_poplitea_indresist() {
        return dopmieminf_poplitea_indresist;
    }

    public void setDopmieminf_poplitea_indresist(String dopmieminf_poplitea_indresist) {
        this.dopmieminf_poplitea_indresist = dopmieminf_poplitea_indresist;
    }

    public String getDopmieminf_tibialanterior_velmax() {
        return dopmieminf_tibialanterior_velmax;
    }

    public void setDopmieminf_tibialanterior_velmax(String dopmieminf_tibialanterior_velmax) {
        this.dopmieminf_tibialanterior_velmax = dopmieminf_tibialanterior_velmax;
    }

    public String getDopmieminf_tibialanterior_velmin() {
        return dopmieminf_tibialanterior_velmin;
    }

    public void setDopmieminf_tibialanterior_velmin(String dopmieminf_tibialanterior_velmin) {
        this.dopmieminf_tibialanterior_velmin = dopmieminf_tibialanterior_velmin;
    }

    public String getDopmieminf_tibialanterior_indresist() {
        return dopmieminf_tibialanterior_indresist;
    }

    public void setDopmieminf_tibialanterior_indresist(String dopmieminf_tibialanterior_indresist) {
        this.dopmieminf_tibialanterior_indresist = dopmieminf_tibialanterior_indresist;
    }

    public String getDopmieminf_pedia_velmax() {
        return dopmieminf_pedia_velmax;
    }

    public void setDopmieminf_pedia_velmax(String dopmieminf_pedia_velmax) {
        this.dopmieminf_pedia_velmax = dopmieminf_pedia_velmax;
    }

    public String getDopmieminf_pedia_velmin() {
        return dopmieminf_pedia_velmin;
    }

    public void setDopmieminf_pedia_velmin(String dopmieminf_pedia_velmin) {
        this.dopmieminf_pedia_velmin = dopmieminf_pedia_velmin;
    }

    public String getDopmieminf_pedia_indresist() {
        return dopmieminf_pedia_indresist;
    }

    public void setDopmieminf_pedia_indresist(String dopmieminf_pedia_indresist) {
        this.dopmieminf_pedia_indresist = dopmieminf_pedia_indresist;
    }

    public String getDopvemieminf_ldersvp() {
        return dopvemieminf_ldersvp;
    }

    public void setDopvemieminf_ldersvp(String dopvemieminf_ldersvp) {
        this.dopvemieminf_ldersvp = dopvemieminf_ldersvp;
    }

    public String getDopvemieminf_ldersvs() {
        return dopvemieminf_ldersvs;
    }

    public void setDopvemieminf_ldersvs(String dopvemieminf_ldersvs) {
        this.dopvemieminf_ldersvs = dopvemieminf_ldersvs;
    }

    public String getDopvemieminf_lizqsvp() {
        return dopvemieminf_lizqsvp;
    }

    public void setDopvemieminf_lizqsvp(String dopvemieminf_lizqsvp) {
        this.dopvemieminf_lizqsvp = dopvemieminf_lizqsvp;
    }

    public String getDopvemieminf_lizqsvs() {
        return dopvemieminf_lizqsvs;
    }

    public void setDopvemieminf_lizqsvs(String dopvemieminf_lizqsvs) {
        this.dopvemieminf_lizqsvs = dopvemieminf_lizqsvs;
    }

    public String getDopartmieminf_lderafc() {
        return dopartmieminf_lderafc;
    }

    public void setDopartmieminf_lderafc(String dopartmieminf_lderafc) {
        this.dopartmieminf_lderafc = dopartmieminf_lderafc;
    }

    public String getDopartmieminf_lderafs() {
        return dopartmieminf_lderafs;
    }

    public void setDopartmieminf_lderafs(String dopartmieminf_lderafs) {
        this.dopartmieminf_lderafs = dopartmieminf_lderafs;
    }

    public String getDopartmieminf_lderapoplitea() {
        return dopartmieminf_lderapoplitea;
    }

    public void setDopartmieminf_lderapoplitea(String dopartmieminf_lderapoplitea) {
        this.dopartmieminf_lderapoplitea = dopartmieminf_lderapoplitea;
    }

    public String getDopartmieminf_lderata() {
        return dopartmieminf_lderata;
    }

    public void setDopartmieminf_lderata(String dopartmieminf_lderata) {
        this.dopartmieminf_lderata = dopartmieminf_lderata;
    }

    public String getDopartmieminf_lderatp() {
        return dopartmieminf_lderatp;
    }

    public void setDopartmieminf_lderatp(String dopartmieminf_lderatp) {
        this.dopartmieminf_lderatp = dopartmieminf_lderatp;
    }

    public String getDopartmieminf_lizqafc() {
        return dopartmieminf_lizqafc;
    }

    public void setDopartmieminf_lizqafc(String dopartmieminf_lizqafc) {
        this.dopartmieminf_lizqafc = dopartmieminf_lizqafc;
    }

    public String getDopartmieminf_lizqafs() {
        return dopartmieminf_lizqafs;
    }

    public void setDopartmieminf_lizqafs(String dopartmieminf_lizqafs) {
        this.dopartmieminf_lizqafs = dopartmieminf_lizqafs;
    }

    public String getDopartmieminf_lizqapoplitea() {
        return dopartmieminf_lizqapoplitea;
    }

    public void setDopartmieminf_lizqapoplitea(String dopartmieminf_lizqapoplitea) {
        this.dopartmieminf_lizqapoplitea = dopartmieminf_lizqapoplitea;
    }

    public String getDopartmieminf_lizqata() {
        return dopartmieminf_lizqata;
    }

    public void setDopartmieminf_lizqata(String dopartmieminf_lizqata) {
        this.dopartmieminf_lizqata = dopartmieminf_lizqata;
    }

    public String getDopartmieminf_lizqatp() {
        return dopartmieminf_lizqatp;
    }

    public void setDopartmieminf_lizqatp(String dopartmieminf_lizqatp) {
        this.dopartmieminf_lizqatp = dopartmieminf_lizqatp;
    }

    public String getDopvemieminf_lizqvenafemoralc() {
        return dopvemieminf_lizqvenafemoralc;
    }

    public void setDopvemieminf_lizqvenafemoralc(String dopvemieminf_lizqvenafemoralc) {
        this.dopvemieminf_lizqvenafemoralc = dopvemieminf_lizqvenafemoralc;
    }

    public String getDopvemieminf_lizqvenafemoralsp() {
        return dopvemieminf_lizqvenafemoralsp;
    }

    public void setDopvemieminf_lizqvenafemoralsp(String dopvemieminf_lizqvenafemoralsp) {
        this.dopvemieminf_lizqvenafemoralsp = dopvemieminf_lizqvenafemoralsp;
    }

    public String getDopvemieminf_lizqcayadosafenainterna() {
        return dopvemieminf_lizqcayadosafenainterna;
    }

    public void setDopvemieminf_lizqcayadosafenainterna(String dopvemieminf_lizqcayadosafenainterna) {
        this.dopvemieminf_lizqcayadosafenainterna = dopvemieminf_lizqcayadosafenainterna;
    }

    public String getDopvemieminf_lizqsafena() {
        return dopvemieminf_lizqsafena;
    }

    public void setDopvemieminf_lizqsafena(String dopvemieminf_lizqsafena) {
        this.dopvemieminf_lizqsafena = dopvemieminf_lizqsafena;
    }

    public String getDopvemieminf_lizqvenapoplitea() {
        return dopvemieminf_lizqvenapoplitea;
    }

    public void setDopvemieminf_lizqvenapoplitea(String dopvemieminf_lizqvenapoplitea) {
        this.dopvemieminf_lizqvenapoplitea = dopvemieminf_lizqvenapoplitea;
    }

    public String getDopvemieminf_lizqcayadosafenaexterna() {
        return dopvemieminf_lizqcayadosafenaexterna;
    }

    public void setDopvemieminf_lizqcayadosafenaexterna(String dopvemieminf_lizqcayadosafenaexterna) {
        this.dopvemieminf_lizqcayadosafenaexterna = dopvemieminf_lizqcayadosafenaexterna;
    }

    public String getDopvemieminf_lizqcayadosafenaexternaperforantes() {
        return dopvemieminf_lizqcayadosafenaexternaperforantes;
    }

    public void setDopvemieminf_lizqcayadosafenaexternaperforantes(String dopvemieminf_lizqcayadosafenaexternaperforantes) {
        this.dopvemieminf_lizqcayadosafenaexternaperforantes = dopvemieminf_lizqcayadosafenaexternaperforantes;
    }

    public String getDopvemieminf_lizqtibialposterior() {
        return dopvemieminf_lizqtibialposterior;
    }

    public void setDopvemieminf_lizqtibialposterior(String dopvemieminf_lizqtibialposterior) {
        this.dopvemieminf_lizqtibialposterior = dopvemieminf_lizqtibialposterior;
    }

    public String getDopvemieminf_ldervenafemoralc() {
        return dopvemieminf_ldervenafemoralc;
    }

    public void setDopvemieminf_ldervenafemoralc(String dopvemieminf_ldervenafemoralc) {
        this.dopvemieminf_ldervenafemoralc = dopvemieminf_ldervenafemoralc;
    }

    public String getDopvemieminf_ldervenafemoralsp() {
        return dopvemieminf_ldervenafemoralsp;
    }

    public void setDopvemieminf_ldervenafemoralsp(String dopvemieminf_ldervenafemoralsp) {
        this.dopvemieminf_ldervenafemoralsp = dopvemieminf_ldervenafemoralsp;
    }

    public String getDopvemieminf_ldercayadosafenainterna() {
        return dopvemieminf_ldercayadosafenainterna;
    }

    public void setDopvemieminf_ldercayadosafenainterna(String dopvemieminf_ldercayadosafenainterna) {
        this.dopvemieminf_ldercayadosafenainterna = dopvemieminf_ldercayadosafenainterna;
    }

    public String getDopvemieminf_ldersafena() {
        return dopvemieminf_ldersafena;
    }

    public void setDopvemieminf_ldersafena(String dopvemieminf_ldersafena) {
        this.dopvemieminf_ldersafena = dopvemieminf_ldersafena;
    }

    public String getDopvemieminf_ldervenapoplitea() {
        return dopvemieminf_ldervenapoplitea;
    }

    public void setDopvemieminf_ldervenapoplitea(String dopvemieminf_ldervenapoplitea) {
        this.dopvemieminf_ldervenapoplitea = dopvemieminf_ldervenapoplitea;
    }

    public String getDopvemieminf_ldercayadosafenaexterna() {
        return dopvemieminf_ldercayadosafenaexterna;
    }

    public void setDopvemieminf_ldercayadosafenaexterna(String dopvemieminf_ldercayadosafenaexterna) {
        this.dopvemieminf_ldercayadosafenaexterna = dopvemieminf_ldercayadosafenaexterna;
    }

    public String getDopvemieminf_ldercayadosafenaexternaperforantes() {
        return dopvemieminf_ldercayadosafenaexternaperforantes;
    }

    public void setDopvemieminf_ldercayadosafenaexternaperforantes(String dopvemieminf_ldercayadosafenaexternaperforantes) {
        this.dopvemieminf_ldercayadosafenaexternaperforantes = dopvemieminf_ldercayadosafenaexternaperforantes;
    }

    public String getDopvemieminf_ldertibialposterior() {
        return dopvemieminf_ldertibialposterior;
    }

    public void setDopvemieminf_ldertibialposterior(String dopvemieminf_ldertibialposterior) {
        this.dopvemieminf_ldertibialposterior = dopvemieminf_ldertibialposterior;
    }

    public String getProcedimiento() {
        return procedimiento;
    }

    public void setProcedimiento(String procedimiento) {
        this.procedimiento = procedimiento;
    }

    public String getFrecuenciacardiacafetal() {
        return frecuenciacardiacafetal;
    }

    public void setFrecuenciacardiacafetal(String frecuenciacardiacafetal) {
        this.frecuenciacardiacafetal = frecuenciacardiacafetal;
    }

    public String getBirads_mamaderecha() {
        return birads_mamaderecha;
    }

    public void setBirads_mamaderecha(String birads_mamaderecha) {
        this.birads_mamaderecha = birads_mamaderecha;
    }

    public String getBirads_mamaizquierda() {
        return birads_mamaizquierda;
    }

    public void setBirads_mamaizquierda(String birads_mamaizquierda) {
        this.birads_mamaizquierda = birads_mamaizquierda;
    }

    public HclCie10 getHclCie10() {
        return hclCie10;
    }

    public void setHclCie10(HclCie10 hclCie10) {
        this.hclCie10 = hclCie10;
    }

    public HclConsultas getHclConsultas() {
        return hclConsultas;
    }

    public void setHclConsultas(HclConsultas hclConsultas) {
        this.hclConsultas = hclConsultas;
    }

    public FacturaItem getFacturaItem() {
        return facturaItem;
    }

    public void setFacturaItem(FacturaItem facturaItem) {
        this.facturaItem = facturaItem;
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

    public int getPlantilla() {
        return plantilla;
    }

    public void setPlantilla(int plantilla) {
        this.plantilla = plantilla;
    }

    public String getFur() {
        return fur;
    }

    public void setFur(String fur) {
        this.fur = fur;
    }

    public String getFpp() {
        return fpp;
    }

    public void setFpp(String fpp) {
        this.fpp = fpp;
    }

    public String getDbp() {
        return dbp;
    }

    public void setDbp(String dbp) {
        this.dbp = dbp;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getLf() {
        return lf;
    }

    public void setLf(String lf) {
        this.lf = lf;
    }

    public String getPeso() {
        return peso;
    }

    public void setPeso(String peso) {
        this.peso = peso;
    }

    public String getPlacenta() {
        return placenta;
    }

    public void setPlacenta(String placenta) {
        this.placenta = placenta;
    }

    public String getCervix() {
        return cervix;
    }

    public void setCervix(String cervix) {
        this.cervix = cervix;
    }

    public String getLiquidoamniotico() {
        return liquidoamniotico;
    }

    public void setLiquidoamniotico(String liquidoamniotico) {
        this.liquidoamniotico = liquidoamniotico;
    }

    public String getTonofetal() {
        return tonofetal;
    }

    public void setTonofetal(String tonofetal) {
        this.tonofetal = tonofetal;
    }

    public String getMovfetal() {
        return movfetal;
    }

    public void setMovfetal(String movfetal) {
        this.movfetal = movfetal;
    }

    public String getMovrespiratorio() {
        return movrespiratorio;
    }

    public void setMovrespiratorio(String movrespiratorio) {
        this.movrespiratorio = movrespiratorio;
    }

    public String getVelmax() {
        return velmax;
    }

    public void setVelmax(String velmax) {
        this.velmax = velmax;
    }

    public String getVelmin() {
        return velmin;
    }

    public void setVelmin(String velmin) {
        this.velmin = velmin;
    }

    public String getIr() {
        return ir;
    }

    public void setIr(String ir) {
        this.ir = ir;
    }

    public String getTesticuloder() {
        return testiculoder;
    }

    public void setTesticuloder(String testiculoder) {
        this.testiculoder = testiculoder;
    }

    public String getTesticuloizq() {
        return testiculoizq;
    }

    public void setTesticuloizq(String testiculoizq) {
        this.testiculoizq = testiculoizq;
    }

    public String getEpidimoder() {
        return epidimoder;
    }

    public void setEpidimoder(String epidimoder) {
        this.epidimoder = epidimoder;
    }

    public String getEpidimoizq() {
        return epidimoizq;
    }

    public void setEpidimoizq(String epidimoizq) {
        this.epidimoizq = epidimoizq;
    }

    public String getRinonder() {
        return rinonder;
    }

    public void setRinonder(String rinonder) {
        this.rinonder = rinonder;
    }

    public String getRinosizq() {
        return rinosizq;
    }

    public void setRinosizq(String rinosizq) {
        this.rinosizq = rinosizq;
    }

    public String getArcuatavelmaxima() {
        return arcuatavelmaxima;
    }

    public void setArcuatavelmaxima(String arcuatavelmaxima) {
        this.arcuatavelmaxima = arcuatavelmaxima;
    }

    public String getArcuatavelminima() {
        return arcuatavelminima;
    }

    public void setArcuatavelminima(String arcuatavelminima) {
        this.arcuatavelminima = arcuatavelminima;
    }

    public String getArcuatair() {
        return arcuatair;
    }

    public void setArcuatair(String arcuatair) {
        this.arcuatair = arcuatair;
    }

    public String getSegmentariaelmaxima() {
        return segmentariaelmaxima;
    }

    public void setSegmentariaelmaxima(String segmentariaelmaxima) {
        this.segmentariaelmaxima = segmentariaelmaxima;
    }

    public String getSegmentariavelminima() {
        return segmentariavelminima;
    }

    public void setSegmentariavelminima(String segmentariavelminima) {
        this.segmentariavelminima = segmentariavelminima;
    }

    public String getSegmentariair() {
        return segmentariair;
    }

    public void setSegmentariair(String segmentariair) {
        this.segmentariair = segmentariair;
    }

    public String getRenalvelmaxima() {
        return renalvelmaxima;
    }

    public void setRenalvelmaxima(String renalvelmaxima) {
        this.renalvelmaxima = renalvelmaxima;
    }

    public String getRenalvelminima() {
        return renalvelminima;
    }

    public void setRenalvelminima(String renalvelminima) {
        this.renalvelminima = renalvelminima;
    }

    public String getRenalir() {
        return renalir;
    }

    public void setRenalir(String renalir) {
        this.renalir = renalir;
    }

    public String getBulillarvelmaxima() {
        return bulillarvelmaxima;
    }

    public void setBulillarvelmaxima(String bulillarvelmaxima) {
        this.bulillarvelmaxima = bulillarvelmaxima;
    }

    public String getBulillarvelminima() {
        return bulillarvelminima;
    }

    public void setBulillarvelminima(String bulillarvelminima) {
        this.bulillarvelminima = bulillarvelminima;
    }

    public String getBulillarir() {
        return bulillarir;
    }

    public void setBulillarir(String bulillarir) {
        this.bulillarir = bulillarir;
    }

    public String getAortavelmaxima() {
        return aortavelmaxima;
    }

    public void setAortavelmaxima(String aortavelmaxima) {
        this.aortavelmaxima = aortavelmaxima;
    }

    public String getAortavelminima() {
        return aortavelminima;
    }

    public void setAortavelminima(String aortavelminima) {
        this.aortavelminima = aortavelminima;
    }

    public String getAortair() {
        return aortair;
    }

    public void setAortair(String aortair) {
        this.aortair = aortair;
    }

    public String getLiquidoamniotico2() {
        return liquidoamniotico2;
    }

    public void setLiquidoamniotico2(String liquidoamniotico2) {
        this.liquidoamniotico2 = liquidoamniotico2;
    }

    public String getDescripcion_corta() {
        return descripcion_corta;
    }

    public void setDescripcion_corta(String descripcion_corta) {
        this.descripcion_corta = descripcion_corta;
    }

    public String getDopmieminf_tibialposterior_velmax() {
        return dopmieminf_tibialposterior_velmax;
    }

    public void setDopmieminf_tibialposterior_velmax(String dopmieminf_tibialposterior_velmax) {
        this.dopmieminf_tibialposterior_velmax = dopmieminf_tibialposterior_velmax;
    }

    public String getDopmieminf_tibialposterior_velmin() {
        return dopmieminf_tibialposterior_velmin;
    }

    public void setDopmieminf_tibialposterior_velmin(String dopmieminf_tibialposterior_velmin) {
        this.dopmieminf_tibialposterior_velmin = dopmieminf_tibialposterior_velmin;
    }

    public String getDopmieminf_tibialposterior_indresist() {
        return dopmieminf_tibialposterior_indresist;
    }

    public void setDopmieminf_tibialposterior_indresist(String dopmieminf_tibialposterior_indresist) {
        this.dopmieminf_tibialposterior_indresist = dopmieminf_tibialposterior_indresist;
    }

   
    
    
     
}