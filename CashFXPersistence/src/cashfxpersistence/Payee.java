/*
 * Payee.java
 * 
 * Created on Oct 23, 2007, 11:15:16 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cashfxpersistence;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author TWXS025
 */
@Entity
@Table(name = "PAYEE")
@NamedQueries({@NamedQuery(name = "Payee.findById", query = "SELECT p FROM Payee p WHERE p.id = :id"), @NamedQuery(name = "Payee.findByName", query = "SELECT p FROM Payee p WHERE p.name = :name")})
public class Payee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @Column(name = "NAME", nullable = false)
    private String name;

    public Payee() {
    }

    public Payee(Integer id) {
        this.id = id;
    }

    public Payee(Integer id, String name) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payee)) {
            return false;
        }
        Payee other = (Payee) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cashfxpersistence.Payee[id=" + id + "]";
    }

}
