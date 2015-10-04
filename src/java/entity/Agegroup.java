/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author pekmil
 */
@Entity
@Table(name = "agegroup")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Agegroup.findAll", query = "SELECT a FROM Agegroup a"),
    @NamedQuery(name = "Agegroup.findByAId", query = "SELECT a FROM Agegroup a WHERE a.aId = :aId"),
    @NamedQuery(name = "Agegroup.findByAStartyear", query = "SELECT a FROM Agegroup a WHERE a.aStartyear = :aStartyear"),
    @NamedQuery(name = "Agegroup.findByAName", query = "SELECT a FROM Agegroup a WHERE a.aName = :aName")})
public class Agegroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "a_id")
    private Integer aId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "a_startyear")
    @Temporal(TemporalType.DATE)
    private Date aStartyear;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "a_name")
    private String aName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agegroupId")
    private Collection<Entry> entryCollection;

    public Agegroup() {
    }

    public Agegroup(Integer aId) {
        this.aId = aId;
    }

    public Agegroup(Integer aId, Date aStartyear, String aName) {
        this.aId = aId;
        this.aStartyear = aStartyear;
        this.aName = aName;
    }

    public Integer getAId() {
        return aId;
    }

    public void setAId(Integer aId) {
        this.aId = aId;
    }

    public Date getAStartyear() {
        return aStartyear;
    }

    public void setAStartyear(Date aStartyear) {
        this.aStartyear = aStartyear;
    }

    public String getAName() {
        return aName;
    }

    public void setAName(String aName) {
        this.aName = aName;
    }

    @XmlTransient
    public Collection<Entry> getEntryCollection() {
        return entryCollection;
    }

    public void setEntryCollection(Collection<Entry> entryCollection) {
        this.entryCollection = entryCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (aId != null ? aId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agegroup)) {
            return false;
        }
        Agegroup other = (Agegroup) object;
        if ((this.aId == null && other.aId != null) || (this.aId != null && !this.aId.equals(other.aId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Agegroup[ aId=" + aId + " ]";
    }
    
}
