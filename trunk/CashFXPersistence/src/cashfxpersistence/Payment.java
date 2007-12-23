/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cashfxpersistence;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author TWXS025
 */
@Entity
@Table(name = "PAYMENT")
//@NamedQueries({@NamedQuery(name = "Payment.findById", query = "SELECT p FROM Payment p WHERE p.id = :id"), @NamedQuery(name = "Payment.findByPayeeId", query = "SELECT p FROM Payment p WHERE p.payeeId = :payeeId"), @NamedQuery(name = "Payment.findByAmount", query = "SELECT p FROM Payment p WHERE p.amount = :amount"), @NamedQuery(name = "Payment.findByStartDate", query = "SELECT p FROM Payment p WHERE p.startDate = :startDate"), @NamedQuery(name = "Payment.findByEndDate", query = "SELECT p FROM Payment p WHERE p.endDate = :endDate"), @NamedQuery(name = "Payment.findByOccurence", query = "SELECT p FROM Payment p WHERE p.occurence = :occurence")})
public class Payment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private long id;
    @ManyToOne
    private Payee payee;
    @Column(name = "AMOUNT", nullable = false)
    private float amount;
    @Column(name = "START_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date startDate;
    @Column(name = "END_DATE", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date endDate;
    @Column(name = "OCCURENCE", nullable = false)
    private String occurence;
    @OneToMany(mappedBy="payment", cascade=CascadeType.ALL)
    private List<PaymentOverride> overrides = new ArrayList();
    @ManyToMany(
        targetEntity=Label.class,
        cascade={CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
        name="PAYMENT_LABEL",
        joinColumns={@JoinColumn(name="LABEL_ID")},
        inverseJoinColumns={@JoinColumn(name="PAYMENT_ID")}
    )
    private List<Label> labels = new ArrayList();

    public Payment() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Payee getPayee() {
        return payee;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
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
    
    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public List<Label> getLabels() {
        return labels;
    }
    
    public void setPaymentOverrides(List<PaymentOverride> overrides) {
        this.overrides = overrides;
    }

    public List<PaymentOverride> getPaymentOverrides() {
        return overrides;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        //hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payment)) {
            return false;
        }
        Payment other = (Payment) object;
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "cashfxpersistence.Payment[id=" + id + "]";
    }

    public void addLabel(Label label) {
        this.labels.add(label);
    }
    
    public void addPaymentOverride(PaymentOverride override){
        override.setPayment(this);
        this.overrides.add(override);
    }

}
