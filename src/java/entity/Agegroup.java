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
    @NamedQuery(name = "Agegroup.findById", query = "SELECT a FROM Agegroup a WHERE a.id = :id"),
    @NamedQuery(name = "Agegroup.findByStartyear", query = "SELECT a FROM Agegroup a WHERE a.startyear = :startyear"),
    @NamedQuery(name = "Agegroup.findByName", query = "SELECT a FROM Agegroup a WHERE a.name = :name")})
public class Agegroup implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "a_id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "a_startyear")
    @Temporal(TemporalType.DATE)
    private Date startyear;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "a_name")
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agegroup")
    private Collection<Entry> entries;

    public Agegroup() {
    }

    public Agegroup(Integer id) {
        this.id = id;
    }

    public Agegroup(Integer id, Date startyear, String name) {
        this.id = id;
        this.startyear = startyear;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartyear() {
        return startyear;
    }

    public void setStartyear(Date startyear) {
        this.startyear = startyear;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public Collection<Entry> getEntries() {
        return entries;
    }

    public void setEntries(Collection<Entry> entries) {
        this.entries = entries;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Agegroup)) {
            return false;
        }
        Agegroup other = (Agegroup) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Agegroup[ id=" + id + " ]";
    }
    
}
