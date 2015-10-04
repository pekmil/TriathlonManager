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
    @NamedQuery(name = "Tournament.findByTId", query = "SELECT t FROM Tournament t WHERE t.tId = :tId"),
    @NamedQuery(name = "Tournament.findByTName", query = "SELECT t FROM Tournament t WHERE t.tName = :tName"),
    @NamedQuery(name = "Tournament.findByTDesc", query = "SELECT t FROM Tournament t WHERE t.tDesc = :tDesc")})
public class Tournament implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "t_id")
    private Integer tId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "t_name")
    private String tName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "t_desc")
    private String tDesc;
    @OneToMany(mappedBy = "tournamentId")
    private Collection<Race> raceCollection;

    public Tournament() {
    }

    public Tournament(Integer tId) {
        this.tId = tId;
    }

    public Tournament(Integer tId, String tName, String tDesc) {
        this.tId = tId;
        this.tName = tName;
        this.tDesc = tDesc;
    }

    public Integer getTId() {
        return tId;
    }

    public void setTId(Integer tId) {
        this.tId = tId;
    }

    public String getTName() {
        return tName;
    }

    public void setTName(String tName) {
        this.tName = tName;
    }

    public String getTDesc() {
        return tDesc;
    }

    public void setTDesc(String tDesc) {
        this.tDesc = tDesc;
    }

    @XmlTransient
    public Collection<Race> getRaceCollection() {
        return raceCollection;
    }

    public void setRaceCollection(Collection<Race> raceCollection) {
        this.raceCollection = raceCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tId != null ? tId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tournament)) {
            return false;
        }
        Tournament other = (Tournament) object;
        if ((this.tId == null && other.tId != null) || (this.tId != null && !this.tId.equals(other.tId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Tournament[ tId=" + tId + " ]";
    }
    
}
