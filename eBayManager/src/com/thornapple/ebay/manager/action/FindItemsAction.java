/*
 * FindItemsAction.java
 *
 * Created on August 11, 2006, 11:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.ebay.manager.action;

import ca.odell.glazedlists.EventList;
import com.thornapple.ebay.manager.AuctionItem;
import com.thornapple.ebay.manager.ItemSearchCriteria;
import com.thornapple.ebay.manager.adapter.EbayItemAdapter;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;
import org.jdesktop.swingworker.SwingWorker;

/**
 *
 * @author Bill
 */
public class FindItemsAction extends AbstractAction{
    
    EventList itemEventList;
    ItemSearchCriteria criteria;
    
    /** Creates a new instance of FindItemsAction */
    public FindItemsAction(EventList itemEventList, ItemSearchCriteria criteria) {
        this.itemEventList = itemEventList;
        this.criteria = criteria;
    }
    
    public void actionPerformed(ActionEvent e) {
        final EbayItemAdapter ebaySearch = new EbayItemAdapter();
        final SwingWorker worker = new SwingWorker() {
            
            public Object doInBackground() {
                try {
                    final List<AuctionItem> results = ebaySearch.findItems(criteria);
                    itemEventList.getReadWriteLock().writeLock().lock();
                    itemEventList.clear();
                    itemEventList.addAll(results);
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    itemEventList.getReadWriteLock().writeLock().unlock();
                }
                
                return null;
            }
            
            //Runs on the event-dispatching thread.
            public void finished() {
                //nothing
            }
        };
        worker.execute();
        
    }
    
    
}
