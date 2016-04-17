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
@Table(name = "familyentry")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Familyentry.findAll", query = "SELECT f FROM Familyentry f"),
    @NamedQuery(name = "Familyentry.findByFId", query = "SELECT f FROM Familyentry f WHERE f.id = :id"),
    @NamedQuery(name = "Familyentry.findByFName", query = "SELECT f FROM Familyentry f WHERE f.name = :name")})
public class Familyentry implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "f_id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "f_name")
    private String name;
    @OneToMany(mappedBy = "familyentry")
    private Collection<Entry> entries;

    public Familyentry() {
    }

    public Familyentry(Integer id) {
        this.id = id;
    }

    public Familyentry(Integer id, String name) {
        this.id = id;
        this.name = name;
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
        if (!(object instanceof Familyentry)) {
            return false;
        }
        Familyentry other = (Familyentry) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.e2.Familyentry[ id=" + id + " ]";
    }
    
}
