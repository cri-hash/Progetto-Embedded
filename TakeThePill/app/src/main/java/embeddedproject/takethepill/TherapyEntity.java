package embeddedproject.takethepill;

import java.sql.Date;
import java.util.Calendar;

public class TherapyEntity {

// Variabili
    private Integer id;
    private Date dataInizio;
    private Date dataFine;
    private Integer nGiorni;
    private Integer minNotifica;
    private Boolean lun, mar, mer, gio, ven, sab, dom;
    private Integer idFarmaco;
    private Integer dosaggio;
    private String ore;
    private String nomeFarmaco;

// Costruttore
    public TherapyEntity(Date dataFine, Integer nGiorni, Integer minNotifica,
                         Boolean lun, Boolean mar, Boolean mer, Boolean gio,
                         Boolean ven, Boolean sab, Boolean dom,
                         Integer idFarmaco, Integer dosaggio,
                         String ore, String nomeFarmaco){

        id = null; //?????

        Calendar c = Calendar.getInstance();    // Data di oggi
        dataInizio= new Date(c.YEAR, c.MONTH, c.DAY_OF_MONTH);

        // Uno tra dataFine e nGiorni deve essere NULL
        this.dataFine=dataFine;
        this.nGiorni=nGiorni;

        this.minNotifica=minNotifica;
        this.lun=lun;
        this.mar=mar;
        this.mer=mer;
        this.gio=gio;
        this.ven=ven;
        this.sab=sab;
        this.dom=dom;
        this.idFarmaco=idFarmaco;
        this.dosaggio=dosaggio;

        this.ore=ore;
        this.nomeFarmaco=nomeFarmaco;
    }

    // Metodo per ottere la lista dei giorni della settimana in stringa
    public String getGiorni(){
        String s="";
        Integer count=0;
        if(lun){
            s="Lun";
            count++;
        }
        if(mar){
            if(count>0) s+=", ";
            s+="Mar";
            count++;
        }
        if(mer){
            if(count>0) s+=", ";
            s+="Mer";
            count++;
        }
        if(gio){
            if(count>0) s+=", ";
            s+="Gio";
            count++;
        }
        if(ven){
            if(count>0) s+=", ";
            s+="Ven";
            count++;
        }
        if(sab){
            if(count>0) s+=", ";
            s+="Sab";
            count++;
        }
        if(dom){
            if(count>0) s+=", ";
            s+="Dom";
            count++;
        }
        return s;
    }

// Metodi GET e SET
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDataInizio() {
        return dataInizio;
    }

    public void setDataInizio(Date dataInizio) {
        this.dataInizio = dataInizio;
    }

    public Date getDataFine() {
        return dataFine;
    }

    public void setDataFine(Date dataFine) {
        this.dataFine = dataFine;
    }

    public Integer getnGiorni() {
        return nGiorni;
    }

    public void setnGiorni(Integer nGiorni) {
        this.nGiorni = nGiorni;
    }

    public Integer getMinNotifica() {
        return minNotifica;
    }

    public void setMinNotifica(Integer minNotifica) {
        this.minNotifica = minNotifica;
    }

    public Boolean getLun() {
        return lun;
    }

    public void setLun(Boolean lun) {
        this.lun = lun;
    }

    public Boolean getMar() {
        return mar;
    }

    public void setMar(Boolean mar) {
        this.mar = mar;
    }

    public Boolean getMer() {
        return mer;
    }

    public void setMer(Boolean mer) {
        this.mer = mer;
    }

    public Boolean getGio() {
        return gio;
    }

    public void setGio(Boolean gio) {
        this.gio = gio;
    }

    public Boolean getVen() {
        return ven;
    }

    public void setVen(Boolean ven) {
        this.ven = ven;
    }

    public Boolean getSab() {
        return sab;
    }

    public void setSab(Boolean sab) {
        this.sab = sab;
    }

    public Boolean getDom() {
        return dom;
    }

    public void setDom(Boolean dom) {
        this.dom = dom;
    }

    public Integer getIdFarmaco() {
        return idFarmaco;
    }

    public void setIdFarmaco(Integer idFarmaco) {
        this.idFarmaco = idFarmaco;
    }

    public Integer getDosaggio() {
        return dosaggio;
    }

    public void setDosaggio(Integer dosaggio) {
        this.dosaggio = dosaggio;
    }

    public String getOre() {
        return ore;
    }

    public void setOre(String ore) {
        this.ore = ore;
    }

    public String getNomeFarmaco() {
        return nomeFarmaco;
    }

    public void setNomeFarmaco(String nomeFarmaco) {
        this.nomeFarmaco = nomeFarmaco;
    }


}
