/*
 * ItemFilter.java
 *
 * Created on August 11, 2006, 10:58 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.ebay.manager.ui;

import ca.odell.glazedlists.TextFilterator;
import com.thornapple.ebay.manager.AuctionItem;
import java.util.List;

/**
 *
 * @author Bill
 */
public class ItemPriceFilter implements TextFilterator {
    
    public void getFilterStrings(List baseList, Object element) {
        AuctionItem item = (AuctionItem)element;
        baseList.add(Double.toString(item.getCurrentPrice()));
    }
    
}
