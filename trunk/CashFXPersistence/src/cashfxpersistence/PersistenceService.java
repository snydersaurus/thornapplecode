/*
 * PersistenceManager.java
 * 
 * Created on Oct 23, 2007, 11:07:01 PM
 * 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cashfxpersistence;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.Persistence;

/**
 *
 * @author TWXS025
 */
public class PersistenceService {

    EntityManagerFactory factory;
    EntityManager manager;
    private static PersistenceService instance;
    private final static String DOES_PAYEE_EXIST =
            "select a from Payee a where name = ?1";
    private final static String DOES_PAYMENT_EXIST =
            "select s from Payment s where amount = ?1";

    /**
     * Creates a new instance of PersistenceService
     */
    private PersistenceService() {
        startup();
    }

    public synchronized static PersistenceService getInstance() {
        if (instance == null) {
            instance = new PersistenceService();
        }

        return instance;
    }

    public void startup() {
        if (factory != null) {
            return;
        }

        factory = Persistence.createEntityManagerFactory("CashFXPersistence");
        manager = factory.createEntityManager();
        //manager.setFlushMode(FlushModeType.COMMIT);
    }

    public void shutdown() {
        manager.close();
        factory.close();
    }

    public boolean addOrUpdatePayment(Payment payment) throws Exception {
        EntityTransaction tx = manager.getTransaction();
        try {
            tx.begin();
            //if (payment.getId() < 0)
                manager.persist(payment);
            //else
            //    manager.merge(payment);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    
    public Payee getPayeeByID(long id) throws Exception {
        EntityTransaction tx = manager.getTransaction();
        Payee a = null;
        tx.begin();
        try {
            a = manager.find(Payee.class,id);
            tx.commit();
            return a;
        } catch (Exception e){
            e.printStackTrace();
            return a;
        }
    }
    
    public Payment getPaymentByID(long id) throws Exception {
        EntityTransaction tx = manager.getTransaction();
        Payment a = null;
        tx.begin();
        try {
            a = manager.find(Payment.class,id);
            tx.commit();
            return a;
        } catch (Exception e){
            e.printStackTrace();
            return a;
        }
    }
    
    public boolean addOrUpdatePayee(Payee payee) throws Exception {
        EntityTransaction tx = manager.getTransaction();
        try {
            tx.begin();
            manager.persist(payee);
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        PersistenceService service = PersistenceService.getInstance();
//        Payee walmart = new Payee();
//        walmart.setName("Wal-Mart");
//        boolean ok = service.addOrUpdatePayee(walmart);
//        System.out.println("added ok." + " id is " + walmart.getId());
//        walmart = service.getPayeeByID(1);
//        Payment firstPayment = new Payment();
//        firstPayment.setPayee(walmart);
//        firstPayment.setAmount(30.40);
        Date now = Calendar.getInstance().getTime();
//        firstPayment.setStartDate(now);
//        firstPayment.setEndDate(now);
//        firstPayment.setOccurence("ONCE");
//        ok = service.addOrUpdatePayment(firstPayment);
//        System.out.println("added first payment: " + firstPayment.getId());
        Payment firstPayment = service.getPaymentByID(1);
          System.out.println("found first payment: " + firstPayment.getAmount());
        List<Label> labels = firstPayment.getLabels();
        if (labels != null) {
            for (Label label : labels) {
                System.out.println(label.getName());
            }
        }
        List<PaymentOverride> overrides = firstPayment.getPaymentOverrides();
        if (labels != null) {
            for (PaymentOverride override : overrides) {
                System.out.println(override.getAmount());
            }
        }

//    PaymentOverride override = new PaymentOverride();
//    override.setAmount(23.00);
//    override.setPaymentDate(now);
//    firstPayment.addPaymentOverride(override);
//          Label groceries = new Label();
//        groceries.setName("Groceries");
//        firstPayment.addLabel(groceries);
//        service.addOrUpdatePayment(firstPayment);
        service.shutdown();
    }
}
