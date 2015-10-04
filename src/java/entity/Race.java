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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "race")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Race.findAll", query = "SELECT r FROM Race r"),
    @NamedQuery(name = "Race.findByRId", query = "SELECT r FROM Race r WHERE r.rId = :rId"),
    @NamedQuery(name = "Race.findByRName", query = "SELECT r FROM Race r WHERE r.rName = :rName"),
    @NamedQuery(name = "Race.findByRDesc", query = "SELECT r FROM Race r WHERE r.rDesc = :rDesc"),
    @NamedQuery(name = "Race.findByRStartdate", query = "SELECT r FROM Race r WHERE r.rStartdate = :rStartdate"),
    @NamedQuery(name = "Race.findByREnddate", query = "SELECT r FROM Race r WHERE r.rEnddate = :rEnddate")})
public class Race implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "r_id")
    private Integer rId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "r_name")
    private String rName;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "r_desc")
    private String rDesc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "r_startdate")
    @Temporal(TemporalType.DATE)
    private Date rStartdate;
    @Column(name = "r_enddate")
    @Temporal(TemporalType.DATE)
    private Date rEnddate;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "race")
    private Collection<Entry> entryCollection;
    @JoinColumn(name = "tournament_id", referencedColumnName = "t_id")
    @ManyToOne
    private Tournament tournamentId;

    public Race() {
    }

    public Race(Integer rId) {
        this.rId = rId;
    }

    public Race(Integer rId, String rName, String rDesc, Date rStartdate) {
        this.rId = rId;
        this.rName = rName;
        this.rDesc = rDesc;
        this.rStartdate = rStartdate;
    }

    public Integer getRId() {
        return rId;
    }

    public void setRId(Integer rId) {
        this.rId = rId;
    }

    public String getRName() {
        return rName;
    }

    public void setRName(String rName) {
        this.rName = rName;
    }

    public String getRDesc() {
        return rDesc;
    }

    public void setRDesc(String rDesc) {
        this.rDesc = rDesc;
    }

    public Date getRStartdate() {
        return rStartdate;
    }

    public void setRStartdate(Date rStartdate) {
        this.rStartdate = rStartdate;
    }

    public Date getREnddate() {
        return rEnddate;
    }

    public void setREnddate(Date rEnddate) {
        this.rEnddate = rEnddate;
    }

    @XmlTransient
    public Collection<Entry> getEntryCollection() {
        return entryCollection;
    }

    public void setEntryCollection(Collection<Entry> entryCollection) {
        this.entryCollection = entryCollection;
    }

    public Tournament getTournamentId() {
        return tournamentId;
    }

    public void setTournamentId(Tournament trournamentId) {
        this.tournamentId = trournamentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rId != null ? rId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Race)) {
            return false;
        }
        Race other = (Race) object;
        if ((this.rId == null && other.rId != null) || (this.rId != null && !this.rId.equals(other.rId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Race[ rId=" + rId + " ]";
    }
    
}
