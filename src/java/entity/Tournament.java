/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
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
@Table(name = "tournament")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tournament.findAll", query = "SELECT t FROM Tournament t"),
    @NamedQuery(name = "Tournament.findById", query = "SELECT t FROM Tournament t WHERE t.id = :id"),
    @NamedQuery(name = "Tournament.findByName", query = "SELECT t FROM Tournament t WHERE t.name = :name"),
    @NamedQuery(name = "Tournament.findByDesc", query = "SELECT t FROM Tournament t WHERE t.desc = :desc")})
public class Tournament implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "t_id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "t_name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "t_desc")
    private String desc;
    @OneToMany(mappedBy = "tournament")
    private Collection<Race> races;

    public Tournament() {
    }

    public Tournament(Integer id) {
        this.id = id;
    }

    public Tournament(Integer id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.desc = desc;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @XmlTransient
    public Collection<Race> getRaces() {
        return races;
    }

    public void setRaces(Collection<Race> races) {
        this.races = races;
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
        if (!(object instanceof Tournament)) {
            return false;
        }
        Tournament other = (Tournament) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Tournament[ id=" + id + " ]";
    }
    
}