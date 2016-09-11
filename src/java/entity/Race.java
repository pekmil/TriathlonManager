/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @NamedQuery(name = "Race.findById", query = "SELECT r FROM Race r WHERE r.id = :id"),
    @NamedQuery(name = "Race.findByName", query = "SELECT r FROM Race r WHERE r.name = :name"),
    @NamedQuery(name = "Race.findByDesc", query = "SELECT r FROM Race r WHERE r.desc = :desc"),
    @NamedQuery(name = "Race.findByStartdate", query = "SELECT r FROM Race r WHERE r.startdate = :startdate"),
    @NamedQuery(name = "Race.findByEnddate", query = "SELECT r FROM Race r WHERE r.enddate = :enddate")})
public class Race implements Serializable {    
   
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "r_id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "r_name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1000)
    @Column(name = "r_desc")
    private String desc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "r_startdate")
    @Temporal(TemporalType.DATE)
    private Date startdate;
    @Column(name = "r_enddate")
    @Temporal(TemporalType.DATE)
    private Date enddate;
    @Column(name = "r_national")
    private Boolean national;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "race")
    private Collection<Entry> entries;
    @JoinColumn(name = "tournament_id", referencedColumnName = "t_id")
    @ManyToOne
    private Tournament tournament;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "race")
    private Collection<Raceadjustment> raceadjustments;

    public Race() {
    }

    public Race(Integer id) {
        this.id = id;
    }

    public Race(Integer id, String name, String desc, Date startdate) {
        this.id = id;
        this.name = name;
        this.desc = desc;
        this.startdate = startdate;
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

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    @XmlTransient
    public Collection<Entry> getEntryCollection() {
        return entries;
    }

    public void setEntries(Collection<Entry> entries) {
        this.entries = entries;
    }
    
    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament trournament) {
        this.tournament = trournament;
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
        if (!(object instanceof Race)) {
            return false;
        }
        Race other = (Race) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Race[ id=" + id + " ]";
    }

    public Boolean isNational() {
        return national;
    }

    public void setNational(Boolean national) {
        this.national = national;
    }

    //@XmlTransient
    @JsonIgnore
    public Collection<Raceadjustment> getRaceadjustments() {
        return raceadjustments;
    }

    public void setRaceadjustments(Collection<Raceadjustment> raceadjustments) {
        this.raceadjustments = raceadjustments;
    }
    
}