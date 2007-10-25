/*
 * Payment.java
 * 
 * Created on Oct 23, 2007, 11:15:16 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cashfxpersistence;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author TWXS025
 */
@Entity
@Table(name = "PAYMENT")
@NamedQueries({@NamedQuery(name = "Payment.findById", query = "SELECT p FROM Payment p WHERE p.id = :id"), @NamedQuery(name = "Payment.findByPayeeId", query = "SELECT p FROM Payment p WHERE p.payeeId = :payeeId"), @NamedQuery(name = "Payment.findByAmount", query = "SELECT p FROM Payment p WHERE p.amount = :amount"), @NamedQuery(name = "Payment.findByStartDate", query = "SELECT p FROM Payment p WHERE p.startDate = :startDate"), @NamedQuery(name = "Payment.findByEndDate", query = "SELECT p FROM Payment p WHERE p.endDate = :endDate"), @NamedQuery(name = "Payment.findByOccurence", query = "SELECT p FROM Payment p WHERE p.occurence = :occurence")})
public class Payment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;
    @ManyToOne
    //@Column(name = "PAYEE_ID", nullable = false)
    private Payee payee;
    @Column(name = "AMOUNT", nullable = false)
    private double amount;
    @Column(name = "START_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "END_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "OCCURENCE", nullable = false)
    private String occurence;

    public Payment() {
    }

    public Payment(Integer id, Payee payee, double amount, Date startDate, Date endDate, String occurence) {
        this.id = id;
        this.payee = payee;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.occurence = occurence;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Payee getPayee() {
        return payee;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getOccurence() {
        return occurence;
    }

    public void setOccurence(String occurence) {
        this.occurence = occurence;
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
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cashfxpersistence.Payment[id=" + id + "]";
    }

}
