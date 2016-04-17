/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Collection;
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
    @NamedQuery(name = "Contestant.findById", query = "SELECT c FROM Contestant c WHERE c.id = :id"),
    @NamedQuery(name = "Contestant.findByName", query = "SELECT c FROM Contestant c WHERE c.name = :name"),
    @NamedQuery(name = "Contestant.findByBirthdate", query = "SELECT c FROM Contestant c WHERE c.birthyear = :birthyear"),
    @NamedQuery(name = "Contestant.findByGender", query = "SELECT c FROM Contestant c WHERE c.gender = :gender"),
    @NamedQuery(name = "Contestant.findByFromtown", query = "SELECT c FROM Contestant c WHERE c.fromtown = :fromtown")})
@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")
public class Contestant implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "c_id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "c_name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "c_birthyear")
    private short birthyear;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "c_gender")
    private String gender;
    @Size(max = 100)
    @Column(name = "c_fromtown")
    private String fromtown;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contestant")
    private Collection<Entry> entries;
    @JoinColumn(name = "club_id", referencedColumnName = "c_id")
    @ManyToOne
    private Club club;

    public Contestant() {
    }

    public Contestant(Integer id) {
        this.id = id;
    }

    public Contestant(Integer id, String name, short birthyear, String gender) {
        this.id = id;
        this.name = name;
        this.birthyear = birthyear;
        this.gender = gender;
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

    public short getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(short birthyear) {
        this.birthyear = birthyear;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFromtown() {
        return fromtown;
    }

    public void setFromtown(String fromtown) {
        this.fromtown = fromtown;
    }

    @XmlTransient
    public Collection<Entry> getEntries() {
        return entries;
    }

    public void setEntries(Collection<Entry> entries) {
        this.entries = entries;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
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
        if (!(object instanceof Contestant)) {
            return false;
        }
        Contestant other = (Contestant) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Contestant[ id=" + id + " ]";
    }
    
}