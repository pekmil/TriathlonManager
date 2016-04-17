package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Date;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import util.Utils;

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
public class Entry implements Serializable {            
    
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

    public Entry(int raceId, short racenum) {
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

    public void setStatus(String status) {
        this.status = status;
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
    
}