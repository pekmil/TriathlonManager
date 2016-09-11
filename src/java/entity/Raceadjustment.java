/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pekmil
 */
@Entity
@Table(name = "raceadjustment")
@XmlRootElement
public class Raceadjustment implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RaceadjustmentPK key;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "ra_gender")
    private String gender;
    @JoinColumn(name = "race_id", referencedColumnName = "r_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Race race;
    @JoinColumn(name = "resultmod_id", referencedColumnName = "r_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Resultmod resultmod;
    @JoinColumn(name = "category_id", referencedColumnName = "c_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Category category;

    public Raceadjustment() {
    }

    public Raceadjustment(RaceadjustmentPK key) {
        this.key = key;
    }

    public Raceadjustment(RaceadjustmentPK key, String gender) {
        this.key = key;
        this.gender = gender;
    }

    public Raceadjustment(int raceId, int resultmodId, int categoryId) {
        this.key = new RaceadjustmentPK(raceId, resultmodId, categoryId);
    }

    public RaceadjustmentPK getKey() {
        return key;
    }

    public void setKey(RaceadjustmentPK key) {
        this.key = key;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @XmlTransient
    @JsonIgnore
    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    //@XmlTransient
    @JsonIgnore
    public Resultmod getResultmod() {
        return resultmod;
    }

    public void setResultmod(Resultmod resultmod) {
        this.resultmod = resultmod;
    }
    
    //@XmlTransient
    @JsonIgnore
    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
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
        if (!(object instanceof Raceadjustment)) {
            return false;
        }
        Raceadjustment other = (Raceadjustment) object;
        if ((this.key == null && other.key != null) || (this.key != null && !this.key.equals(other.key))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Raceadjustment[ key=" + key + " ]";
    }
    
}
