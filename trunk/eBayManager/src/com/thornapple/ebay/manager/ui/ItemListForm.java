/*
 * ItemListForm.java
 *
 * Created on August 4, 2006, 10:37 PM
 */

package com.thornapple.ebay.manager.ui;

import binding.BindingContext;
import binding.BindingDescription;
import com.ebay.sdk.util.eBayUtil;
import com.ebay.soap.eBLBaseComponents.ItemType;
import com.ebay.soap.eBLBaseComponents.ListingDetailsType;
import com.thornapple.ebay.manager.AuctionItem;
import com.thornapple.ebay.manager.ItemSearchCriteria;
import com.thornapple.ebay.manager.adapter.EbayItemAdapter;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.decorator.AlternateRowHighlighter;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.HighlighterPipeline;
import org.jdesktop.swingx.decorator.PatternFilter;

/**
 *
 * @author  Bill
 */
public class ItemListForm extends javax.swing.JPanel {
    
    private ItemSearchCriteria criteria = new ItemSearchCriteria();
    private BindingContext context = new BindingContext();
    
    private JFrame detailPopup = new JFrame();
    
    final String[] colNames = new String[] {
        "ItemID", "Type", "Title", "SubTitle", "StartTime", "Price", "BidCount", "EndTime","Shipping"};
    
    
    /** Creates new form ItemListForm */
    public ItemListForm() {
        initComponents();
        bindComponents();
        detailPopup.addMouseListener(new MouseListener() {
            public void mouseClicked(MouseEvent e) {
                if (detailPopup.isVisible())detailPopup.setVisible(false);
            }
            public void mouseEntered(MouseEvent e) {
            }
            public void mouseExited(MouseEvent e) {
                
            }
            
            public void mousePressed(MouseEvent e) {
            }
            public void mouseReleased(MouseEvent e) {
            }
        });
        final PatternFilter patternFilter = new PatternFilter();
        patternFilter.setColumnIndex(2);
        filterTextField.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
                try {
                    patternFilter.setPattern(Pattern.compile(filterTextField.getText()));
                } catch (Exception e1) {
                }
            }
            
            public void keyPressed(KeyEvent e) {
            }
            
            public void keyReleased(KeyEvent e) {
            }
        });
        tblResults.setFilters(new FilterPipeline(new Filter[]{patternFilter}));
        
        HighlighterPipeline highlighter = new HighlighterPipeline();
        highlighter.addHighlighter(new AlternateRowHighlighter());
        tblResults.setHighlighters(highlighter);
        TableModel dataModel = new AbstractTableModel() {
            public int getColumnCount() {
                return colNames.length;
            }
            
            public int getRowCount() {
                return 0;
            }
            
            public String getColumnName(int columnIndex) {
                return colNames[columnIndex];
            }
            
            public Object getValueAt(int row, int col) {
                return "";
            }
        };
        
        tblResults.setModel(dataModel);
        
    }
    
    private String[] itemToColumns(ItemType item) {
        String[] cols = new String[colNames.length];
        int i = 0;
        cols[i++] = item.getItemID().toString();
        cols[i++] = item.getListingType().toString();
        cols[i++] = item.getTitle();
        cols[i++] = item.getSubTitle() == null ? "" : item.getSubTitle();
        
        ListingDetailsType dtl = item.getListingDetails();
        cols[i++] = eBayUtil.toAPITimeString(dtl.getStartTime().getTime());
        
        cols[i++] = (new Double(item.getSellingStatus().getCurrentPrice().getValue())).toString();
        cols[i++] = item.getSellingStatus().getBidCount().toString();
        cols[i++] = eBayUtil.toAPITimeString(dtl.getEndTime().getTime());
        if (item.getShippingDetails() != null && item.getShippingDetails().getDefaultShippingCost() != null)
            cols[i++] = item.getShippingDetails().getDefaultShippingCost().getValue()+"";
        else
            cols[i++] = "";
        return cols;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblResults = new org.jdesktop.swingx.JXTable();
        filterTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        jLabel1.setText("Search for:");

        btnSearch.setText("Find It!");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        tblResults.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblResults);

        jLabel2.setText("Title Filter:");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(org.jdesktop.layout.GroupLayout.LEADING, jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel1)
                            .add(jLabel2))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, filterTextField, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.LEADING, txtSearch, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 277, Short.MAX_VALUE))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnSearch)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(btnSearch)
                    .add(txtSearch, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(filterTextField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel2))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        EbayItemAdapter ebaySearch = new EbayItemAdapter();
        context.commitUncommittedValues();
        
        try {
            criteria.setQuery(txtSearch.getText());
            final List<AuctionItem> results = ebaySearch.findItems(criteria);
            TableModel dataModel = new AbstractTableModel() {
                public int getColumnCount() {
                    return colNames.length;
                }
                
                public int getRowCount() {
                    return results.size();
                }
                
                public String getColumnName(int columnIndex) {
                    return colNames[columnIndex];
                }
                
                public Object getValueAt(int row, int col) {
                    ItemType item = results.get(row).getItem();
                    return itemToColumns(item)[col];
                }
            };
            
            tblResults.setModel(dataModel);
//            DefaultListModel model = new DefaultListModel();
//            for (AuctionItem item : results) {
//                model.addElement(item);
//            }
//            jList1.setModel(model);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_btnSearchActionPerformed
    
    private void bindComponents() {
        context.addDescription(new BindingDescription(criteria, "query",txtSearch, "text"));
        context.bind();
        
//        jList1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//            public void valueChanged(ListSelectionEvent e) {
//                detailPopup = new JFrame();
//                //detailPopup.add(new JLabel("asdfsdf"),BorderLayout.CENTER);
//
//                String itemID = ((AuctionItem)jList1.getSelectedValue()).getItem().getItemID().getValue();
//                AuctionItem item;
//                try {
//                    item = new EbayItemAdapter().getItemDetails(itemID);
//                    //item = (AuctionItem)jList1.getSelectedValue();
//                    detailPopup.add(new AuctionForm((item)),BorderLayout.CENTER);
//                    detailPopup.setSize(getWidth()+50,getHeight()+50);
//                    detailPopup.setLocationRelativeTo(jList1);
//                    detailPopup.setVisible(true);
//                } catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//
//            }
//        });
        
        
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSearch;
    private javax.swing.JTextField filterTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane2;
    private org.jdesktop.swingx.JXTable tblResults;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
    
}
