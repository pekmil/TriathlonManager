/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pekmil
 */
@Entity
@Table(name = "licence")
@XmlRootElement
public class Licence implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "l_id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "l_name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "l_licencenum")
    private String licencenum;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "l_birthplace")
    private String birthplace;
    @Basic(optional = false)
    @NotNull
    @Column(name = "l_birthyear")
    private short birthyear;

    public Licence() {
    }

    public Licence(Integer id) {
        this.id = id;
    }

    public Licence(Integer id, String name, String licencenum, String birthplace, short birthyear) {
        this.id = id;
        this.name = name;
        this.licencenum = licencenum;
        this.birthplace = birthplace;
        this.birthyear = birthyear;
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

    public String getLicencenum() {
        return licencenum;
    }

    public void setLicencenum(String licencenum) {
        this.licencenum = licencenum;
    }

    public String getBirthplace() {
        return birthplace;
    }

    public void setBirthplace(String birthplace) {
        this.birthplace = birthplace;
    }

    public short getBirthyear() {
        return birthyear;
    }

    public void setBirthyear(short birthyear) {
        this.birthyear = birthyear;
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
        if (!(object instanceof Licence)) {
            return false;
        }
        Licence other = (Licence) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Licence[ id=" + id + " ]";
    }
    
}
