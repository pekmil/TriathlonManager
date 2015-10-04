/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
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

/**
 *
 * @author pekmil
 */
@Entity
@Table(name = "entry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Entry.findAll", query = "SELECT e FROM Entry e"),
    @NamedQuery(name = "Entry.findByRaceId", query = "SELECT e FROM Entry e WHERE e.entryPK.raceId = :raceId"),
    @NamedQuery(name = "Entry.findByContestantId", query = "SELECT e FROM Entry e WHERE e.entryPK.contestantId = :contestantId"),
    @NamedQuery(name = "Entry.findByEEntrytime", query = "SELECT e FROM Entry e WHERE e.eEntrytime = :eEntrytime"),
    @NamedQuery(name = "Entry.findByERacenum", query = "SELECT e FROM Entry e WHERE e.eRacenum = :eRacenum"),
    @NamedQuery(name = "Entry.findByERacetime", query = "SELECT e FROM Entry e WHERE e.eRacetime = :eRacetime"),
    @NamedQuery(name = "Entry.findByEStatus", query = "SELECT e FROM Entry e WHERE e.eStatus = :eStatus"),
    @NamedQuery(name = "Entry.findByELicencenum", query = "SELECT e FROM Entry e WHERE e.eLicencenum = :eLicencenum")})
public class Entry implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EntryPK entryPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_entrytime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date eEntrytime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_racenum")
    private short eRacenum;
    @Column(name = "e_racetime")
    @Temporal(TemporalType.TIME)
    private Date eRacetime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "e_status")
    private String eStatus;
    @Size(max = 50)
    @Column(name = "e_licencenum")
    private String eLicencenum;
    @JoinColumn(name = "race_id", referencedColumnName = "r_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Race race;
    @JoinColumn(name = "contestant_id", referencedColumnName = "c_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Contestant contestant;
    @JoinColumn(name = "category_id", referencedColumnName = "c_id")
    @ManyToOne(optional = false)
    private Category categoryId;
    @JoinColumn(name = "agegroup_id", referencedColumnName = "a_id")
    @ManyToOne(optional = false)
    private Agegroup agegroupId;

    public Entry() {
    }

    public Entry(EntryPK entryPK) {
        this.entryPK = entryPK;
    }

    public Entry(EntryPK entryPK, Date eEntrytime, short eRacenum, String eStatus) {
        this.entryPK = entryPK;
        this.eEntrytime = eEntrytime;
        this.eRacenum = eRacenum;
        this.eStatus = eStatus;
    }

    public Entry(int raceId, int contestantId) {
        this.entryPK = new EntryPK(raceId, contestantId);
    }

    public EntryPK getEntryPK() {
        return entryPK;
    }

    public void setEntryPK(EntryPK entryPK) {
        this.entryPK = entryPK;
    }

    public Date getEEntrytime() {
        return eEntrytime;
    }

    public void setEEntrytime(Date eEntrytime) {
        this.eEntrytime = eEntrytime;
    }

    public short getERacenum() {
        return eRacenum;
    }

    public void setERacenum(short eRacenum) {
        this.eRacenum = eRacenum;
    }

    public Date getERacetime() {
        return eRacetime;
    }

    public void setERacetime(Date eRacetime) {
        this.eRacetime = eRacetime;
    }

    public String getEStatus() {
        return eStatus;
    }

    public void setEStatus(String eStatus) {
        this.eStatus = eStatus;
    }

    public String getELicencenum() {
        return eLicencenum;
    }

    public void setELicencenum(String eLicencenum) {
        this.eLicencenum = eLicencenum;
    }

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

    public Category getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Category categoryId) {
        this.categoryId = categoryId;
    }

    public Agegroup getAgegroupId() {
        return agegroupId;
    }

    public void setAgegroupId(Agegroup agegroupId) {
        this.agegroupId = agegroupId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (entryPK != null ? entryPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Entry)) {
            return false;
        }
        Entry other = (Entry) object;
        if ((this.entryPK == null && other.entryPK != null) || (this.entryPK != null && !this.entryPK.equals(other.entryPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Entry[ entryPK=" + entryPK + " ]";
    }
    
}
