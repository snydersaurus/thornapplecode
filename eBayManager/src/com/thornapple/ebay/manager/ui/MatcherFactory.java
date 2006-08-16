/*
 * MatcherFactory.java
 *
 * Created on August 12, 2006, 4:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.ebay.manager.ui;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.CollectionList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.UniqueList;
import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import ca.odell.glazedlists.matchers.CompositeMatcherEditor;
import ca.odell.glazedlists.matchers.Matcher;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.EventListModel;
import ca.odell.glazedlists.swing.EventSelectionModel;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import com.thornapple.ebay.manager.AuctionItem;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

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
    
    public MatcherEditor createMatcher(EventList source, ItemFilterPanel panel){
        TextComponentMatcherEditor titleMatcher =
                new TextComponentMatcherEditor(panel.getTitleComponent(),new ItemTextFilter());
        
        TextComponentMatcherEditor minPriceMatcher =
                new PriceMatcherEditor(panel.getMinPriceComponent(),new ItemPriceFilter(),PriceMatcherEditor.Mode.MINIMUM);
        
        TextComponentMatcherEditor maxPriceMatcher =
                new PriceMatcherEditor(panel.getMaxPriceComponent(),new ItemPriceFilter(),PriceMatcherEditor.Mode.MAXIMUM);
        
        LabelsSelectMatcher labelsMatcher =
                new LabelsSelectMatcher(source,panel.getLabelListComponent());
        
        EventList matchers = new BasicEventList();
        matchers.add(titleMatcher);
        matchers.add(labelsMatcher);
        matchers.add(maxPriceMatcher);
        matchers.add(minPriceMatcher);
        
        CompositeMatcherEditor matcher =
                new CompositeMatcherEditor(matchers);
        matcher.setMode(CompositeMatcherEditor.AND);
        
        
        return matcher;//new TextComponentMatcherEditor(panel.getTitleComponent(),new ItemTextFilter());
    }
    
    
    class ItemsToLabelsModel implements CollectionList.Model<AuctionItem, String> {
        
        public List<String> getChildren(AuctionItem item ) {
            return item.getLabels();
        }
    }
    
    class LabelsForItemsMatcher implements Matcher {
        
        /** the users to match */
        private Set labels = new HashSet();
        
        /**
         * Create a new {@link IssuesForUsersMatcher} that matches only
         * {@link Issue}s that have one or more user in the specified list.
         */
        public LabelsForItemsMatcher(Collection labels) {
            // make a defensive copy of the users
            this.labels.addAll(labels);
        }
        
        /**
         * Test whether to include or not include the specified issue based
         * on whether or not their user is selected.
         */
        public boolean matches(Object o) {
            if(o == null) return false;
            if(labels.isEmpty()) return true;
            
            AuctionItem item = (AuctionItem)o;
            List _labels = item.getLabels();
            for (Object _label : _labels) {
                if (labels.contains(_label))
                    return true;
            }
            
            return false;
            
        }
    }
    
    class LabelsSelectMatcher extends AbstractMatcherEditor implements ListSelectionListener {
        
        /** a list of labels */
        EventList labelsEventList;
        EventList labelsSelectedList;
        
        /**
         * Create a {@link LabelsForItemsMatcherEditor} that matches users from the
         * specified {@link EventList} of {@link AuctionItems}s.
         */
        public LabelsSelectMatcher(EventList source, JList list) {
            // derive the users list from the issues list
            CollectionList<AuctionItem, String> labelsNonUnique =
                    new CollectionList<AuctionItem, String>(source, new ItemsToLabelsModel());
            //EventList labelsNonUnique = new ItemsToLabelsList(source);
            labelsEventList = new UniqueList(labelsNonUnique);
            
            // create a JList that contains users
            EventListModel usersListModel = new EventListModel(labelsEventList);
            list.setModel(usersListModel);
            
            // create an EventList containing the JList's selection
            EventSelectionModel labelSelectionModel = new EventSelectionModel(labelsEventList);
            list.setSelectionModel(labelSelectionModel);
            labelsSelectedList = labelSelectionModel.getSelected();
            
            // handle changes to the list's selection
            list.addListSelectionListener(this);
        }
        
        
        /**
         * When the JList selection changes, create a new Matcher and fire
         * an event.
         */
        public void valueChanged(ListSelectionEvent e) {
            Matcher newMatcher = new LabelsForItemsMatcher(labelsSelectedList);
            fireChanged(newMatcher);
            
        }
    }
    
}
