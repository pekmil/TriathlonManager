package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import util.Utils;
import viewmodel.ResultData;

/**
 *
 * @author pekmil
 */
@Entity
@Table(name = "entry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entry.findAll", query = "SELECT e FROM Entry e"),
    @NamedQuery(name = "Entry.findByRaceId", query = "SELECT e FROM Entry e WHERE e.key.raceId = :raceId"),
    @NamedQuery(name = "Entry.findByEntrytime", query = "SELECT e FROM Entry e WHERE e.entrytime = :entrytime"),
    @NamedQuery(name = "Entry.findByRacetime", query = "SELECT e FROM Entry e WHERE e.racetime = :racetime"),
    @NamedQuery(name = "Entry.findByStatus", query = "SELECT e FROM Entry e WHERE e.status = :status"),
    @NamedQuery(name = "Entry.findByLicencenum", query = "SELECT e FROM Entry e WHERE e.licencenum = :licencenum")})
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="key")
public class Entry implements Serializable, Comparable<Entry> {    
    
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EntryPK key;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_entrytime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entrytime;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "e_racenum")
//    private short racenum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_preentry")
    private boolean preentry;
    @Column(name = "e_racetime")
    @Temporal(TemporalType.TIME)
    private Date racetime;
    @Size(max = 1000)
    @Column(name = "e_racetimemods")
    private String racetimemods;
    @Size(max = 500)
    @Column(name = "e_resultmods")
    private String resultmods;
    @Column(name = "e_finishtime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finishtime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "e_status")
    private String status;
    @Size(max = 50)
    @Column(name = "e_licencenum")
    private String licencenum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_paid")
    private boolean paid;
    @Column(name = "e_remainingpayment")
    private Integer remainingpayment;
    @JoinColumn(name = "race_id", referencedColumnName = "r_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Race race;
    @JoinColumn(name = "contestant_id", referencedColumnName = "c_id")
    @ManyToOne(optional = false)
    private Contestant contestant;
    @JoinColumn(name = "category_id", referencedColumnName = "c_id")
    @ManyToOne(optional = false)
    private Category category;
    @JoinColumn(name = "agegroup_id", referencedColumnName = "a_id")
    @ManyToOne(optional = false)
    private Agegroup agegroup;
    @JoinColumn(name = "familyentry_id", referencedColumnName = "f_id")
    @ManyToOne
    private Familyentry familyentry;
    @JoinColumn(name = "invoice_id", referencedColumnName = "i_id")
    @ManyToOne
    private Invoice invoice;

    public Entry() {
    }

    public Entry(EntryPK key) {
        this.key = key;
    }

    public Entry(EntryPK key, Date entrytime, String status) {
        this.key = key;
        this.entrytime = entrytime;
        this.status = status;
    }

    public Entry(int raceId, String racenum) {
        this.key = new EntryPK(raceId, racenum);
    }

    public EntryPK getKey() {
        return key;
    }

    public void setKey(EntryPK key) {
        this.key = key;
    }

    public Date getEntrytime() {
        return entrytime;
    }

    public void setEntrytime(Date entrytime) {
        this.entrytime = entrytime;
    }

    public Date getRacetime() {
        return Utils.truncateDate(racetime);
    }

    public void setRacetime(Date racetime) {        
        this.racetime = Utils.truncateDate(racetime);
    }
    
    public Date getFinishtime() {
        return finishtime;
    }

    public void setFinishtime(Date finishtime) {
        this.finishtime = finishtime;
    }

    public String getStatus() {
        return status;
    }
    
    public String getStatusString(){
        switch(this.status){
            case "CHECKED":
	        return "Bejelentkezett";
	    case "FINISHED":
	        return "Célba ért";
            case "NOTPRESENT":
                return "Nem jött el";
            case "DSQ":
                return "Kizárt";
            case "DNF":
                return "Feladta"; 
            case "PRE":
                return "Előnevezett";
            case "NOTSTARTED":
                return "Nem indult";
	    default:
	        return "N/A";
        }
    }

    public void setStatus(String status) {
        if(this.status != null && !this.status.equals(status) && !enabledStatusTransitions().contains(status)){
            throw new IllegalArgumentException("A beállítandó státusz nem megfelelő: " + this.status + " -> " + status);
        }
        this.status = status;
    }
    
    private List<String> enabledStatusTransitions(){
        switch(this.status){
            case "PRE":
                return Arrays.asList("CHECKED", "NOTPRESENT");
            case "CHECKED":
                return Arrays.asList("FINISHED", "DNF", "DSQ", "NOTSTARTED");
            default:
                return Arrays.asList("");
        }
    }

    public String getLicencenum() {
        return licencenum;
    }

    public void setLicencenum(String licencenum) {
        this.licencenum = licencenum;
    }

    @JsonIgnore
    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Contestant getContestant() {
        return contestant;
    }

    public void setContestant(Contestant contestant) {
        this.contestant = contestant;
    }

    public Category getCategory() {
        return category;
    }
    
    public String getCategoryName(){
        return category.getName();
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @JsonIgnore
    public Agegroup getAgegroup() {
        return agegroup;
    }

    public void setAgegroup(Agegroup agegroup) {
        this.agegroup = agegroup;
    }

    public boolean isPreentry() {
        return preentry;
    }

    public void setPreentry(boolean preentry) {
        this.preentry = preentry;
    }

    @JsonIgnore
    public Familyentry getFamilyentry() {
        return familyentry;
    }

    public void setFamilyentry(Familyentry familyentry) {
        this.familyentry = familyentry;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (key != null ? key.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entry)) {
            return false;
        }
        Entry other = (Entry) object;
        if ((this.key == null && other.key != null) || (this.key != null && !this.key.equals(other.key))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Entry[ key=" + key + " ]";
    }
    
    public String getAgegroupName(){
        return agegroup.getName();
    }    
    
    public String getTeamAgegroup(Map<String, String> mapper){
        String agegroupName = agegroup.getName();
        String agegroupNamePrefix = agegroupName.substring(0, !agegroupName.contains(" ") ? agegroupName.length() : agegroupName.indexOf(" "));
        String teamAgegroupName = mapper.get(agegroupNamePrefix);
        return teamAgegroupName == null ? agegroupName : teamAgegroupName;
    }

    public Invoice getInvoice() {
        return invoice;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public Integer getRemainingpayment() {
        return remainingpayment;
    }

    public void setRemainingpayment(Integer remainingpayment) {
        this.remainingpayment = remainingpayment;
    }

    public String getRacetimemods() {
        return racetimemods;
    }

    public void setRacetimemods(String racetimemods) {
        this.racetimemods = racetimemods;
    }
    
    public void appendRacetimeMod(String modDesc){
        StringBuilder sb = new StringBuilder("MOD");
        sb.append(",PREVTIME:").append(Utils.formatTime(racetime))
          .append(",MODTIME:").append(Utils.formatDateTime(new Date()))
          .append(",MODDESC:").append(modDesc);
        if(this.racetimemods != null){
            sb.insert(0, this.racetimemods + "|");
        }
        this.setRacetimemods(sb.toString());
    }
    
    public void applyRaceadjustments(List<Raceadjustment> raceadjustments){
        if(raceadjustments != null){
            for(Raceadjustment ra : raceadjustments){
                if(ra.getGender().equals(this.getContestant().getGender()) &&
                    ra.getKey().getCategoryId() == this.getCategory().getId()){
                        Resultmod rm = ra.getResultmod();
                        adjustRacetime(rm.getTime(), rm.isPlus(), false);
                }
            }
        }
    }
    
    @Transient
    public void applyResultmods(ResultData data, Map<String, Resultmod> resultmods){
        if(data.getResultmodIds() != null){
            for(String id : data.getResultmodIds()){
                Resultmod rm = resultmods.get(id);
                if(!data.isRollback() && this.resultmods != null && this.resultmods.contains(rm.getIdname())){
                    throw new IllegalArgumentException("Már alkalmazott eredmény módosító tétel ismételt hozzáadásának kísérlete!");
                }
                else if(data.isRollback() && (this.resultmods == null || !this.resultmods.contains(rm.getIdname()))){
                    throw new IllegalArgumentException("Még nem alkalmazott eredmény módosító tétel törlésének kísérlete!");
                }
                adjustRacetime(rm.getTime(), rm.isPlus(), data.isRollback());
                appendResultmod(rm, data.isRollback());
            }
        }
    }
    
    private void adjustRacetime(Date time, boolean plus, boolean rollback){
        Duration racetimeDuration = Utils.dateToDuration(this.racetime);
        Duration adjustment = Utils.dateToDuration(time);
        long adjustmentInMillis = 0;
        if((plus && !rollback) || (!plus && rollback)){
            adjustmentInMillis = racetimeDuration.plus(adjustment).toMillis();
        }
        else if((!plus && !rollback) || (plus && rollback)){
            adjustmentInMillis = racetimeDuration.minus(adjustment).toMillis();
        }
        this.racetime = Utils.truncateDateWithDatetime(new Date(adjustmentInMillis));
    }
    
    private void appendResultmod(Resultmod rm, boolean rollback){
        if(rollback){
            int start = this.resultmods.indexOf(rm.getIdname());
            int end = this.resultmods.indexOf("|", start) + 1;
            String replacement = this.resultmods.replace(this.resultmods.subSequence(start, end), "");
            this.setResultmods(replacement.equals("") ? null : replacement);
        }
        else{
            StringBuilder sb = new StringBuilder(rm.getIdname());
            sb.append(",").append(rm.getName())
              .append(",").append(rm.isPlus() ? "+" : "-")
              .append(Utils.simpleTimeFormat.format(rm.getTime())).append("|");
            if(this.resultmods != null){
                sb.insert(0, this.resultmods);
            }
            this.setResultmods(sb.toString());
        }
    }

    @JsonIgnore
    @XmlTransient
    public String getResultmods() {
        return resultmods;
    }

    public void setResultmods(String resultmods) {
        this.resultmods = resultmods;
    }
    
    @Transient
    @JsonIgnore
    public List<String> getResultmodList(){
        if(this.resultmods == null) return null;
        else{
            List<String> rmids = new ArrayList<>();
            for(String rm : this.resultmods.split("\\|")){
                rmids.add(rm.split(",")[0]);
            }
            return rmids;
        }
    }
    
    public void setResultmodList(List<String> rml){}
    
    @Transient
    @JsonIgnore
    public List<String> getResultmodNames(){
        if(this.resultmods == null) return null;
        else{
            List<String> rmids = new ArrayList<>();
            for(String rm : this.resultmods.split("\\|")){
                String[] parts = rm.split(",");
                rmids.add(parts[1] + " (" + parts[2] + ")");
            }
            return rmids;
        }
    }
    
    public void setResultmodNames(List<String> rml){}

    @Override
    public int compareTo(Entry e) {
        if(this.racetime != null && e.racetime != null){
            return this.getRacetime().compareTo(e.getRacetime());
        }
        else if(this.racetime == null && e.racetime != null){
            return 1;
        }
        else if(this.racetime != null && e.racetime == null){
            return -1;
        }
        else{
            return 0;
        }
    }
    
}