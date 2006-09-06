/*
 * EbayManager2.java
 *
 * Created on August 22, 2006, 9:23 PM
 */

package com.thornapple.ebay.manager;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.GlazedLists;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.gui.AdvancedTableFormat;
import ca.odell.glazedlists.gui.WritableTableFormat;
import ca.odell.glazedlists.swing.EventSelectionModel;
import ca.odell.glazedlists.swing.EventTableModel;
import ca.odell.glazedlists.swing.TableComparatorChooser;
import com.ebay.sdk.util.eBayUtil;
import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebay.soap.eBLBaseComponents.ListingDetailsType;
import com.elevenworks.swing.border.BrushedMetalBevelBorder;
import com.elevenworks.swing.panel.BrushedMetalScrollPaneUI;
import com.elevenworks.swing.panel.BrushedMetalSplitPaneUI;
import com.elevenworks.swing.panel.TigerInfoPanelUI;
import com.thornapple.ebay.manager.action.FindItemsAction;
import com.thornapple.ebay.manager.ui.BrushedMetalTableHeaderSortableRenderer;
import com.thornapple.ebay.manager.ui.ItemSummaryPanel;
import com.thornapple.ebay.manager.ui.MatcherFactory;
import com.thornapple.ebay.manager.ui.SpanTable;
import com.thornapple.ebay.manager.ui.laf.SpanTableUI;
import com.thornapple.ebay.manager.ui.laf.StarButtonUI;
import java.util.Comparator;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import org.jdesktop.swingworker.SwingWorker;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.AlternateRowHighlighter;
import org.jdesktop.swingx.decorator.HighlighterPipeline;

/**
 *
 * @author  Bill
 */
public class EbayManager2 extends javax.swing.JFrame {
    
    /*The main event list. Drives everything we do here in the manager.*/
    private EventList itemEventList = new BasicEventList();
    
    /*Create a sorted list for the table*/
    private SortedList sortedItems = new SortedList(itemEventList, new AuctionItemComparator());
    
    /*Create a list filtered by the ItemFilterPanel*/
    private FilterList filteredList;
    
    /*Create the criteria bean.*/
    private ItemSearchCriteria criteria = new ItemSearchCriteria();
    
    //table setup
    final String[] colNames = new String[] {
        "","Title", "Price", "Shipping", "Bids", "Time End"};
    
    boolean firing;
    
    /** Creates new form EbayManager2 */
    public EbayManager2() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exc) {
            // Do nothing...
        }
        initComponents();
        
        MatcherFactory matcherFactory = MatcherFactory.getInstance();
        filteredList = new FilterList(sortedItems,matcherFactory.createMatcher(itemEventList,itemFilterPanel1));
        
        final EventTableModel itemTableModel = new EventTableModel(filteredList, new ItemTableFormat());
        tblResults.setModel(itemTableModel);
        tblResults.getTableHeader().setDefaultRenderer(new BrushedMetalTableHeaderSortableRenderer());
        TableComparatorChooser tableSorter = new TableComparatorChooser(tblResults, sortedItems, true);
        tblResults.setDefaultRenderer(String.class,new ItemSummaryPanel(itemTableModel));
        //System.out.println(tblResults.getDefaultRenderer(Boolean.class).getClass().toString());
        //System.out.println(tblResults.getDefaultEditor(Boolean.class).getClass().toString());
        //tblResults.setDefaultEditor(Boolean.class,new StarCheckboxTableCellRenderer());
        //tblResults.setDefaultRenderer(Boolean.class,new StarCheckboxTableCellRenderer());
        //get the renderer set ui, get the editor.getCompoent.setui
        TableCellRenderer booleanRenderer = tblResults.getDefaultRenderer(Boolean.class);
        ((JCheckBox)booleanRenderer).setUI(new StarButtonUI());
        DefaultCellEditor booleanEditor = (DefaultCellEditor)tblResults.getDefaultEditor(Boolean.class);
        ((JCheckBox)booleanEditor.getComponent()).setUI(new StarButtonUI());
       
        //tblResults.getColumn(1).setCellRenderer(new ItemSummaryPanel(itemTableModel));
        tblResults.setRowHeight(150);
        tblResults.getColumnModel().setColumnMargin(0);
        tblResults.setUI(new SpanTableUI());
        tblResults.getColumn(0).setWidth(40);
        tblResults.getTableHeader().setReorderingAllowed(false);
        tblResults.getTableHeader().setResizingAllowed(false);
        
        final EventSelectionModel eventSelectionModel = new EventSelectionModel(filteredList);
        tblResults.setSelectionModel(eventSelectionModel);
        eventSelectionModel.addListSelectionListener(new ListSelectionListener() {
            private String lastItemSelected = "";
            public void valueChanged(ListSelectionEvent e) {
                //if (firing || e.getFirstIndex() < 0) return;
                //firing = true;
                
                if(!eventSelectionModel.isSelectionEmpty()) {
                    EventList selectedItems = eventSelectionModel.getSelected();
                    final AuctionItem item = (AuctionItem)selectedItems.get(0);
                    if (lastItemSelected.equals(item.getID())) return;
                    lastItemSelected = item.getID();
                    SwingWorker worker = new SwingWorker(){
                        protected Object doInBackground() throws Exception {
                            itemDetailPanel1.transitionOut();
                            itemDetailPanel1.setItem(item);
                            itemDetailPanel1.transitionIn();
                            firing = false;
                            return SwingWorker.StateValue.DONE;
                        }};
                        worker.execute();
                }
            }
        });
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        brushedMetalPanel1 = new com.elevenworks.swing.panel.BrushedMetalPanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        jSplitPane1.setUI(new BrushedMetalSplitPaneUI());
        jXPanel1 = new org.jdesktop.swingx.JXPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jScrollPane1.setUI(new BrushedMetalScrollPaneUI());
        jScrollPane1.setBorder(new BrushedMetalBevelBorder("Filters"));
        itemFilterPanel1 = new com.thornapple.ebay.manager.ui.ItemFilterPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jScrollPane3.setUI(new BrushedMetalScrollPaneUI());
        jScrollPane3.setBorder(new BrushedMetalBevelBorder("Search"));
        itemSearchPanel1 = new com.thornapple.ebay.manager.ui.ItemSearchPanel();
        itemSearchPanel1.setCriteria(criteria);
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jSplitPane2.setUI(new BrushedMetalSplitPaneUI());
        jScrollPane2 = new javax.swing.JScrollPane();
        tblResults = new SpanTable();
        HighlighterPipeline highlighter = new HighlighterPipeline();
        highlighter.addHighlighter(new AlternateRowHighlighter());
        tblResults.setHighlighters(highlighter);

        itemDetailPanel1 = new com.thornapple.ebay.manager.ui.ItemDetailPanel();
        itemDetailPanel1.setUI(new TigerInfoPanelUI());

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jSplitPane1.setDividerLocation(260);
        jSplitPane1.setResizeWeight(0.5);
        jSplitPane1.setOpaque(false);
        jXPanel1.setOpaque(false);
        jScrollPane1.setOpaque(false);
        jScrollPane1.setViewportView(itemFilterPanel1);

        jScrollPane3.setViewportView(itemSearchPanel1);

        jButton1.setText("Find It!");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jXPanel1Layout = new org.jdesktop.layout.GroupLayout(jXPanel1);
        jXPanel1.setLayout(jXPanel1Layout);
        jXPanel1Layout.setHorizontalGroup(
            jXPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jXPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jXPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jScrollPane3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE)
                    .add(jButton1)
                    .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
                .addContainerGap())
        );
        jXPanel1Layout.setVerticalGroup(
            jXPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jXPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jScrollPane3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jButton1)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                .addContainerGap())
        );
        jSplitPane1.setLeftComponent(jXPanel1);

        jSplitPane2.setDividerLocation(300);
        jSplitPane2.setResizeWeight(0.5);
        tblResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblResults.setShowHorizontalLines(false);
        tblResults.setShowVerticalLines(false);
        tblResults.setSortable(false);
        jScrollPane2.setViewportView(tblResults);

        jSplitPane2.setLeftComponent(jScrollPane2);

        itemDetailPanel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        itemDetailPanel1.setMaximumSize(new java.awt.Dimension(500, 500));
        jSplitPane2.setRightComponent(itemDetailPanel1);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jSplitPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jSplitPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
                .addContainerGap())
        );
        jSplitPane1.setRightComponent(jPanel1);

        org.jdesktop.layout.GroupLayout brushedMetalPanel1Layout = new org.jdesktop.layout.GroupLayout(brushedMetalPanel1);
        brushedMetalPanel1.setLayout(brushedMetalPanel1Layout);
        brushedMetalPanel1Layout.setHorizontalGroup(
            brushedMetalPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(brushedMetalPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 828, Short.MAX_VALUE)
                .addContainerGap())
        );
        brushedMetalPanel1Layout.setVerticalGroup(
            brushedMetalPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(brushedMetalPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .add(jSplitPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 514, Short.MAX_VALUE)
                .addContainerGap())
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(brushedMetalPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(brushedMetalPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        doSearch();
    }//GEN-LAST:event_jButton1ActionPerformed
    
    private void doSearch(){
        itemSearchPanel1.commit();
        new FindItemsAction(itemEventList,criteria).actionPerformed(null);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EbayManager2().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.elevenworks.swing.panel.BrushedMetalPanel brushedMetalPanel1;
    private com.thornapple.ebay.manager.ui.ItemDetailPanel itemDetailPanel1;
    private com.thornapple.ebay.manager.ui.ItemFilterPanel itemFilterPanel1;
    private com.thornapple.ebay.manager.ui.ItemSearchPanel itemSearchPanel1;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private org.jdesktop.swingx.JXPanel jXPanel1;
    private org.jdesktop.swingx.JXTable tblResults;
    // End of variables declaration//GEN-END:variables
    
    
    class AuctionItemComparator implements Comparator {
        public int compare(Object a, Object b) {
            AuctionItem itemA = (AuctionItem)a;
            AuctionItem itemB = (AuctionItem)b;
            
            //initially sort by cost, lower is more important
            double itemAValue = itemA.getCurrentPrice();
            double itemBValue = itemB.getCurrentPrice();
            
            return new Double(itemAValue - itemBValue).intValue();
        }
    }
    
    class AuctionItemTextComparator implements Comparator {
        public int compare(Object a, Object b) {
            AuctionItem itemA = (AuctionItem)a;
            AuctionItem itemB = (AuctionItem)b;
            
            //initially sort by cost, lower is more important
            double itemAValue = itemA.getCurrentPrice();
            double itemBValue = itemB.getCurrentPrice();
            
            return new Double(itemAValue - itemBValue).intValue();
        }
    }
    
    class AuctionItemDateComparator implements Comparator {
        public int compare(Object a, Object b) {
            AuctionItem itemA = (AuctionItem)a;
            AuctionItem itemB = (AuctionItem)b;
            
            //initially sort by cost, lower is more important
            double itemAValue = itemA.getCurrentPrice();
            double itemBValue = itemB.getCurrentPrice();
            
            return new Double(itemAValue - itemBValue).intValue();
        }
    }
    
    class AuctionItemCostComparator implements Comparator {
        public int compare(Object a, Object b) {
            
            double itemAValue = 0;
            double itemBValue = 0;
            
            if (a == null)
                itemAValue = 0;
            else if (a instanceof String && ((String)a).length() > 0)
                itemAValue = new Double((String)a);
            else if (a instanceof Double)
                itemAValue = (Double)a;
            else
                itemAValue = 0;
            
            if (b == null)
                itemBValue = 0;
            else if (b instanceof String && ((String)b).length() > 0)
                itemAValue = new Double((String)b);
            else if (b instanceof Double)
                itemBValue = (Double)b;
            else
                itemBValue = 0;
            
            Double result = itemBValue - itemAValue;
            
            if (result > 0)
                return -1;
            else if (result < 0)
                return 1;
            else
                return 0;
            
        }
    }
    
    class ItemTableFormat implements AdvancedTableFormat, WritableTableFormat  {
        
        public int getColumnCount() {
            return colNames.length;
        }
        
        public String getColumnName(int column) {
            return colNames[column];
        }
        
        public Object getColumnValue(Object baseObject, int column) {
            AuctionItem auctionItem = (AuctionItem)baseObject;
            ItemType item = auctionItem.getItem();
            ListingDetailsType dtl = item.getListingDetails();
            
            if (column == 0) return auctionItem.isStarred();
            else if (column == 1) return item.getTitle();
            else if (column == 2) return auctionItem.getCurrentPrice();
            else if (column == 3 && auctionItem.isShippingCostAvailable()) return auctionItem.getShippingCost();
            else if (column == 4) return item.getSellingStatus().getBidCount();
            else if (column == 5) return eBayUtil.toAPITimeString(dtl.getEndTime().getTime());
            else
                return "";
        }
        
        public Class getColumnClass(int i) {
            if (i == 0)
                return Boolean.class;
            else
                return String.class;
        }
        
        public Comparator getColumnComparator(int column) {
            if (column == 0) return GlazedLists.booleanComparator();
            else if (column == 1) return GlazedLists.caseInsensitiveComparator();
            else if (column == 2) return GlazedLists.caseInsensitiveComparator();
            else if (column == 3) return new AuctionItemCostComparator();
            else if (column == 4) return new AuctionItemCostComparator();
            else if (column == 5) return GlazedLists.comparableComparator();
            else if (column == 6) return GlazedLists.comparableComparator();
            else return GlazedLists.caseInsensitiveComparator();
        }
        
        public boolean isEditable(Object o, int i) {
            if (i == 0) return true;
            else return false;
        }
        
        public Object setColumnValue(Object baseObject, Object editedObject, int i ) {
            System.out.println((Boolean)editedObject);
            if ( i == 0 )
                ((AuctionItem)baseObject).setStarred( (Boolean)editedObject );
            return baseObject;
        }
    }
    
}
