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
    @NamedQuery(name = "Entry.findByRaceId", query = "SELECT e FROM Entry e WHERE e.key.raceId = :raceId"),
    @NamedQuery(name = "Entry.findByContestantId", query = "SELECT e FROM Entry e WHERE e.key.contestantId = :contestantId"),
    @NamedQuery(name = "Entry.findByEntrytime", query = "SELECT e FROM Entry e WHERE e.entrytime = :entrytime"),
    @NamedQuery(name = "Entry.findByRacenum", query = "SELECT e FROM Entry e WHERE e.racenum = :racenum"),
    @NamedQuery(name = "Entry.findByRacetime", query = "SELECT e FROM Entry e WHERE e.racetime = :racetime"),
    @NamedQuery(name = "Entry.findByStatus", query = "SELECT e FROM Entry e WHERE e.status = :status"),
    @NamedQuery(name = "Entry.findByLicencenum", query = "SELECT e FROM Entry e WHERE e.licencenum = :licencenum")})
public class Entry implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected EntryPK key;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_entrytime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date entrytime;
    @Basic(optional = false)
    @NotNull
    @Column(name = "e_racenum")
    private short racenum;
    @Column(name = "e_racetime")
    @Temporal(TemporalType.TIME)
    private Date racetime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "e_status")
    private String status;
    @Size(max = 50)
    @Column(name = "e_licencenum")
    private String licencenum;
    @JoinColumn(name = "race_id", referencedColumnName = "r_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Race race;
    @JoinColumn(name = "contestant_id", referencedColumnName = "c_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Contestant contestant;
    @JoinColumn(name = "category_id", referencedColumnName = "c_id")
    @ManyToOne(optional = false)
    private Category category;
    @JoinColumn(name = "agegroup_id", referencedColumnName = "a_id")
    @ManyToOne(optional = false)
    private Agegroup agegroup;

    public Entry() {
    }

    public Entry(EntryPK key) {
        this.key = key;
    }

    public Entry(EntryPK key, Date entrytime, short racenum, String status) {
        this.key = key;
        this.entrytime = entrytime;
        this.racenum = racenum;
        this.status = status;
    }

    public Entry(int raceId, int contestantId) {
        this.key = new EntryPK(raceId, contestantId);
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

    public short getRacenum() {
        return racenum;
    }

    public void setRacenum(short racenum) {
        this.racenum = racenum;
    }

    public Date getRacetime() {
        return racetime;
    }

    public void setRacetime(Date racetime) {
        this.racetime = racetime;
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

    public void setCategory(Category category) {
        this.category = category;
    }

    public Agegroup getAgegroup() {
        return agegroup;
    }

    public void setAgegroup(Agegroup agegroup) {
        this.agegroup = agegroup;
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
    
}
