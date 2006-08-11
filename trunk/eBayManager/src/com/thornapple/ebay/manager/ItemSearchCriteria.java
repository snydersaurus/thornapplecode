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
    private boolean useDescription;
    private double minimumPrice;
    private double maximumPrice;
    private boolean useTotalPrice;
    private int maximumDistance;
    private String zipCode;
    
    public ItemSearchCriteria() {
    }
    
    
    public String getZipCode() {
        return zipCode;
    }
    
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public void setMaximumDistance(int maximumDistance) {
        this.maximumDistance = maximumDistance;
    }
    
    public int getMaximumDistance() {
        return maximumDistance;
    }
    
    public boolean isUseDescription() {
        return useDescription;
    }
    
    public void setUseDescription(boolean useDescription) {
        this.useDescription = useDescription;
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
