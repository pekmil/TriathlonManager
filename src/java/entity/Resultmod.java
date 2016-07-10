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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import util.Utils;

/**
 *
 * @author pekmil
 */
@Entity
@Table(name = "resultmod")
@XmlRootElement
public class Resultmod extends StaticParameter implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "r_id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "r_idname")
    private String idname;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "r_name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "r_plus")
    private boolean plus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "r_time")
    @Temporal(TemporalType.TIME)
    private Date time;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "resultmod")
    private Collection<Raceadjustment> raceadjustments;

    public Resultmod() {
    }

    public Resultmod(Integer id) {
        this.id = id;
    }

    public Resultmod(Integer id, String idname, String name, boolean plus, Date time) {
        this.id = id;
        this.idname = idname;
        this.name = name;
        this.plus = plus;
        this.time = time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIdname() {
        return idname;
    }

    public void setIdname(String idname) {
        this.idname = idname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isPlus() {
        return plus;
    }

    public void setPlus(boolean plus) {
        this.plus = plus;
    }

    public Date getTime() {
        return Utils.truncateDate(time);
    }

    public void setTime(Date time) {
        this.time = Utils.truncateDate(time);
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
        if (!(object instanceof Resultmod)) {
            return false;
        }
        Resultmod other = (Resultmod) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Resultmod[ id=" + id + " ]";
    }

    @Override
    public String getParameterName() {
        return this.idname;
    }

    @XmlTransient
    public Collection<Raceadjustment> getRaceadjustments() {
        return raceadjustments;
    }

    public void setRaceadjustmentCollection(Collection<Raceadjustment> raceadjustments) {
        this.raceadjustments = raceadjustments;
    }
    
}
