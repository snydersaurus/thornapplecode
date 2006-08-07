/*
 * EbayAdapterConfiguration.java
 *
 * Created on August 6, 2006, 5:49 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.ebay.manager.adapter;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Bill
 */
public class EbayAdapterConfiguration {
    
    private String token;
    private String url;
    public final static String PROP_TOKEN = "token";
    public final static String PROP_URL = "url";
    private static  EbayAdapterConfiguration instance;
    
    /** Creates a new instance of EbayAdapterConfiguration */
    private EbayAdapterConfiguration() {
        Properties props = new Properties();
        try {
            props.load(getClass().getResourceAsStream("ebay.properties"));
            token = props.getProperty(PROP_TOKEN);
            url = props.getProperty(PROP_URL);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public static synchronized EbayAdapterConfiguration getInstance() {
        if (instance == null)
            instance = new EbayAdapterConfiguration();
        
        return instance;
    }
    
    public String getToken(){
        return token;
    }
    
    public String getApiIURL(){
        return url;
    }

}
