/*
 * ItemSearchCriteria.java
 *
 * Created on August 3, 2006, 9:18 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.ebay.manager;

/**
 *
 * @author Bill
 */
public class ItemSearchCriteria {
    
    private String query;
    private double minimumPrice;
    private double maximumPrice;
    private boolean useTotalPrice;
 
    public ItemSearchCriteria() {
    }
    
    public boolean isUseTotalPrice() {
        return useTotalPrice;
    }

    public void setUseTotalPrice(boolean useTotalPrice) {
        this.useTotalPrice = useTotalPrice;
    }
    
    public double getMaximumPrice() {
        return maximumPrice;
    }

    public void setMaximumPrice(double maximumPrice) {
        this.maximumPrice = maximumPrice;
    }
    
    public double getMinimumPrice() {
        return minimumPrice;
    }

    public void setMinimumPrice(double minimumPrice) {
        this.minimumPrice = minimumPrice;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
    
}
