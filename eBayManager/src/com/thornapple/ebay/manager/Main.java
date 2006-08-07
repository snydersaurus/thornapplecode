/*
 * Main.java
 *
 * Created on April 15, 2006, 12:32 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.ebay.manager;

import com.thornapple.ebay.manager.adapter.EbayItemAdapter;
import java.util.List;

/**
 *
 * @author Bill
 */
public class Main {
    
    /** Creates a new instance of Main */
    public Main() {
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        EbayItemAdapter ebaySearch = new EbayItemAdapter();
        ItemSearchCriteria criteria = new ItemSearchCriteria();
        criteria.setQuery("guild acoustic guitar");
        criteria.setMaximumPrice(500);
        criteria.setUseTotalPrice(true);
        
        List<AuctionItem> results = ebaySearch.findItems(criteria);
        for (AuctionItem item : results) {
            //System.out.println(item.getItem().getItemID() + " | "+ item.getItem().getTitle() + " | " + item.getCurrentPrice() + " | " + item.getTotalCost());
            //System.out.println(ebaySearch.getItemDetails(item.getID()).getItem().getPictureDetails().getPictureURL(0));
        }
    }
    
}
