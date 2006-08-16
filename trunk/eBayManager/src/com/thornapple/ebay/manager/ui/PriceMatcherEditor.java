/*
 * PriceMatcherEditor.java
 *
 * Created on August 15, 2006, 8:43 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.ebay.manager.ui;

import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.matchers.Matcher;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import com.thornapple.ebay.manager.AuctionItem;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Bill
 */
public class PriceMatcherEditor extends TextComponentMatcherEditor {
    
    public enum Mode {MINIMUM,MAXIMUM};
    private Matcher matcher;
    private JTextComponent textComp;
    
    public PriceMatcherEditor(JTextComponent comp, TextFilterator filter, Mode m){
        super(comp,filter);
        this.textComp = comp;
        if (Mode.MAXIMUM.equals(m))
            matcher = new PriceMatcher(Mode.MAXIMUM);
        else
            matcher = new PriceMatcher(Mode.MINIMUM);
    }
    
    public Matcher getMatcher(){
        return matcher;
    }
    
    class PriceMatcher implements Matcher {
        
    public Mode mode;
        
        public PriceMatcher(Mode mode){
            this.mode = mode;
        }
        
        public boolean matches(Object o) {
            String text = PriceMatcherEditor.this.textComp.getText();
            if (text == null || text.length() < 1)
                return true;
            
            try {
                Double d = new Double(text);
                AuctionItem item = (AuctionItem)o;
                double price = item.getCurrentPrice();
                if (this.mode == PriceMatcherEditor.Mode.MINIMUM )
                    return price >= d;
                else 
                    return price <= d;
                
            } catch (Exception e){
                return true;
            }

        }
        
    }
}
