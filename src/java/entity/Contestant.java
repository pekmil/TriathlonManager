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
@Table(name = "contestant")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Contestant.findAll", query = "SELECT c FROM Contestant c"),
    @NamedQuery(name = "Contestant.findByCId", query = "SELECT c FROM Contestant c WHERE c.cId = :cId"),
    @NamedQuery(name = "Contestant.findByCName", query = "SELECT c FROM Contestant c WHERE c.cName = :cName"),
    @NamedQuery(name = "Contestant.findByCBirthdate", query = "SELECT c FROM Contestant c WHERE c.cBirthdate = :cBirthdate"),
    @NamedQuery(name = "Contestant.findByCGender", query = "SELECT c FROM Contestant c WHERE c.cGender = :cGender"),
    @NamedQuery(name = "Contestant.findByCFromtown", query = "SELECT c FROM Contestant c WHERE c.cFromtown = :cFromtown")})
public class Contestant implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "c_id")
    private Integer cId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "c_name")
    private String cName;
    @Basic(optional = false)
    @NotNull
    @Column(name = "c_birthdate")
    @Temporal(TemporalType.DATE)
    private Date cBirthdate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "c_gender")
    private String cGender;
    @Size(max = 100)
    @Column(name = "c_fromtown")
    private String cFromtown;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contestant")
    private Collection<Entry> entryCollection;
    @JoinColumn(name = "club_id", referencedColumnName = "c_id")
    @ManyToOne
    private Club clubId;

    public Contestant() {
    }

    public Contestant(Integer cId) {
        this.cId = cId;
    }

    public Contestant(Integer cId, String cName, Date cBirthdate, String cGender) {
        this.cId = cId;
        this.cName = cName;
        this.cBirthdate = cBirthdate;
        this.cGender = cGender;
    }

    public Integer getCId() {
        return cId;
    }

    public void setCId(Integer cId) {
        this.cId = cId;
    }

    public String getCName() {
        return cName;
    }

    public void setCName(String cName) {
        this.cName = cName;
    }

    public Date getCBirthdate() {
        return cBirthdate;
    }

    public void setCBirthdate(Date cBirthdate) {
        this.cBirthdate = cBirthdate;
    }

    public String getCGender() {
        return cGender;
    }

    public void setCGender(String cGender) {
        this.cGender = cGender;
    }

    public String getCFromtown() {
        return cFromtown;
    }

    public void setCFromtown(String cFromtown) {
        this.cFromtown = cFromtown;
    }

    @XmlTransient
    public Collection<Entry> getEntryCollection() {
        return entryCollection;
    }

    public void setEntryCollection(Collection<Entry> entryCollection) {
        this.entryCollection = entryCollection;
    }

    public Club getClubId() {
        return clubId;
    }

    public void setClubId(Club clubId) {
        this.clubId = clubId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cId != null ? cId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Contestant)) {
            return false;
        }
        Contestant other = (Contestant) object;
        if ((this.cId == null && other.cId != null) || (this.cId != null && !this.cId.equals(other.cId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Contestant[ cId=" + cId + " ]";
    }
    
}
