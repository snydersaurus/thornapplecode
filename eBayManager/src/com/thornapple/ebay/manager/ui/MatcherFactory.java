/*
 * MatcherFactory.java
 *
 * Created on August 12, 2006, 4:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.ebay.manager.ui;

import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;

/**
 *
 * @author Bill
 */
public class MatcherFactory {
    
    private static MatcherFactory instance;
    
    /** Creates a new instance of MatcherFactory */
    private MatcherFactory() {
    }
    
    public synchronized static MatcherFactory getInstance(){
        if (instance == null)
            instance = new MatcherFactory();
        
        return instance;
    }
    
    public MatcherEditor createMatcher(ItemFilterPanel panel){
        return new TextComponentMatcherEditor(panel.getTitleComponent(),new ItemTextFilter());
    }
}
