/*
 * AuctionItem.java
 *
 * Created on August 3, 2006, 9:33 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.ebay.manager;

import com.ebay.soap.eBLBaseComponents.ItemType;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author Bill
 */
public class AuctionItem {
    
    private ItemType item;
    
    private boolean starred;
    
    private boolean shippingCostAvailable;
    
    
    /** Creates a new instance of AuctionItem */
    public AuctionItem(ItemType item) {
        this.item = item;
    }
    
    public ItemType getItem() {
        return item;
    }
    
    public boolean isShippingCostAvailable() {
        return shippingCostAvailable;
    }
    
    public void setShippingCostAvailable(boolean shippingCostAvailable) {
        this.shippingCostAvailable = shippingCostAvailable;
    }
    
    public boolean isStarred() {
        return starred;
    }
    
    public void setStarred(boolean starred) {
        this.starred = starred;
    }
    
    
    public double getCurrentPrice(){
        BigDecimal result =
                new BigDecimal(item.getSellingStatus().getCurrentPrice().getValue());
        result.setScale(2, RoundingMode.HALF_UP);
        return result.doubleValue();
    }
    
    public double getShippingCost(){
        if (item.getShippingDetails() != null && item.getShippingDetails().getDefaultShippingCost() != null){
            BigDecimal result =
                    new BigDecimal(item.getShippingDetails().getDefaultShippingCost().getValue());
            result.setScale(2, RoundingMode.HALF_UP);
            return result.doubleValue();
        } else
            return (Double)null;
    }
    
    public double getTotalCost(){
        if (item.getShippingDetails() != null && item.getShippingDetails().getDefaultShippingCost() != null) {
            setShippingCostAvailable(false);
            return item.getSellingStatus().getCurrentPrice().getValue() +
                    item.getShippingDetails().getDefaultShippingCost().getValue();
        } else
            return item.getSellingStatus().getCurrentPrice().getValue();
    }
    
    public String toString(){
        return item.getItemID() + " | "+ item.getTitle() + " | " + getCurrentPrice() + " | " + getTotalCost();
    }
    
    String getID() {
        return item.getItemID().getValue();
    }
}
