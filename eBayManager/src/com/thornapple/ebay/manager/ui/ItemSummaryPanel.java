/*
 * AuctionItemTableCellRenderer.java
 *
 * Created on August 28, 2006, 9:07 PM
 */

package com.thornapple.ebay.manager.ui;

import ca.odell.glazedlists.swing.EventTableModel;
import com.thornapple.ebay.manager.AuctionItem;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import org.jdesktop.swingworker.SwingWorker;
import org.jdesktop.swingx.JXImagePanel;
import org.jdesktop.swingx.border.DropShadowBorder;

/**
 *
 * @author  Bill
 */
public class ItemSummaryPanel extends javax.swing.JPanel
        implements TableCellRenderer{
    
    private EventTableModel tableModel;
    private static final String TITLE_HTML_OPEN = "<html><b>";
    private static final String TITLE_HTML_CLOSE = "</b></html>";
    private static final String SUB_TITLE_HTML_OPEN = "<html><font size='2'>";
    private static final String SUB_TITLE_HTML_CLOSE = "</font></html>";
    
    
    /** Creates new form AuctionItemTableCellRenderer */
    public ItemSummaryPanel(EventTableModel tableModel) {
        initComponents();
        this.tableModel = tableModel;
        jXImagePanel1.setStyle(JXImagePanel.Style.SCALED);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
      
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        dropShadowBorder1 = new DropShadowBorder(UIManager.getColor("Control"), 1, 5, .5f, 12, false, false, true, false);
        jXPanel1 = new org.jdesktop.swingx.JXPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtTitle = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtSubtitle = new javax.swing.JTextArea();
        jXImagePanel1 = new org.jdesktop.swingx.JXImagePanel();
        lblPrice = new javax.swing.JLabel();
        lblShipping = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblBids = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        lblSubTitle = new javax.swing.JLabel();

        jXPanel1.setAlpha(0.7F);
        jXPanel1.setBorder(dropShadowBorder1);
        org.jdesktop.layout.GroupLayout jXPanel1Layout = new org.jdesktop.layout.GroupLayout(jXPanel1);
        jXPanel1.setLayout(jXPanel1Layout);
        jXPanel1Layout.setHorizontalGroup(
            jXPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 350, Short.MAX_VALUE)
        );
        jXPanel1Layout.setVerticalGroup(
            jXPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 54, Short.MAX_VALUE)
        );
        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setOpaque(false);
        txtTitle.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        txtTitle.setColumns(20);
        txtTitle.setEditable(false);
        txtTitle.setFont(new java.awt.Font("Tahoma", 1, 10));
        txtTitle.setLineWrap(true);
        txtTitle.setRows(5);
        txtTitle.setWrapStyleWord(true);
        txtTitle.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        txtTitle.setOpaque(false);
        jScrollPane1.setViewportView(txtTitle);

        jScrollPane2.setBorder(null);
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane2.setEnabled(false);
        jScrollPane2.setOpaque(false);
        txtSubtitle.setBackground(javax.swing.UIManager.getDefaults().getColor("Panel.background"));
        txtSubtitle.setColumns(20);
        txtSubtitle.setEditable(false);
        txtSubtitle.setFont(new java.awt.Font("Tahoma", 2, 10));
        txtSubtitle.setLineWrap(true);
        txtSubtitle.setRows(5);
        txtSubtitle.setWrapStyleWord(true);
        txtSubtitle.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        txtSubtitle.setOpaque(false);
        jScrollPane2.setViewportView(txtSubtitle);

        jXImagePanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jXImagePanel1.setMaximumSize(new java.awt.Dimension(96, 96));
        jXImagePanel1.setMinimumSize(new java.awt.Dimension(96, 96));
        jXImagePanel1.setPreferredSize(new java.awt.Dimension(96, 96));
        org.jdesktop.layout.GroupLayout jXImagePanel1Layout = new org.jdesktop.layout.GroupLayout(jXImagePanel1);
        jXImagePanel1.setLayout(jXImagePanel1Layout);
        jXImagePanel1Layout.setHorizontalGroup(
            jXImagePanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 92, Short.MAX_VALUE)
        );
        jXImagePanel1Layout.setVerticalGroup(
            jXImagePanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 92, Short.MAX_VALUE)
        );

        lblPrice.setFont(new java.awt.Font("Tahoma", 3, 12));
        lblPrice.setText("Price");

        lblShipping.setText("$1");

        lblTime.setText("time");

        jLabel1.setText("Ends:");

        jLabel3.setText("Bids:");

        lblBids.setText("0");

        jLabel2.setText("Shipping");

        lblTitle.setText("jLabel4");

        lblSubTitle.setText("<html><font size='2'>jLabel4</font></html>");

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createSequentialGroup()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(lblShipping))
                    .add(jXImagePanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(lblPrice))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(lblTitle, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel3)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING, false)
                            .add(lblBids, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(lblTime, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .add(lblSubTitle, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(lblTitle)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(lblSubTitle))
                    .add(jXImagePanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(lblPrice)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(lblShipping)
                            .add(jLabel2))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(lblTime)
                            .add(jLabel1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel3)
                            .add(lblBids))
                        .add(12, 12, 12)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    public Component getTableCellRendererComponent(final JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected)
            setBorder(dropShadowBorder1);
        else
            setBorder(null);
        
        final AuctionItem item = (AuctionItem)tableModel.getElementAt(row);
        txtTitle.setText(item.getItem().getTitle());
        txtSubtitle.setText(item.getItem().getSubTitle());
        lblPrice.setText("$ "+item.getCurrentPrice());
        lblShipping.setText("$ "+item.getShippingCost());
        lblBids.setText(item.getBids());
        lblTime.setText(item.getTimeLeft());
        jXImagePanel1.setImage(item.getGalleryImage());
        lblTitle.setText(TITLE_HTML_OPEN + item.getItem().getTitle()+TITLE_HTML_CLOSE);
        lblSubTitle.setText(SUB_TITLE_HTML_OPEN + item.getItem().getTitle()+ SUB_TITLE_HTML_CLOSE);
        
        //if (!item.isGalleryLoaded()) {
        //    jXImagePanel1.setImage(ItemDetailPanel.LOADING);
        //    ItemSummaryPanel.this.repaint();
        //}
            
//        //swingworker
//        SwingWorker loader = new SwingWorker(){
//            protected Object doInBackground() throws Exception {
//                item.getGalleryImage();
//                return SwingWorker.StateValue.DONE;
//            }
//        };
//        loader.addPropertyChangeListener(new PropertyChangeListener() {
//            public void propertyChange(PropertyChangeEvent evt) {
//                if ("state".equals(evt.getPropertyName()) && evt.getNewValue() == SwingWorker.StateValue.DONE){
//                    System.out.println("done");
//                    jXImagePanel1.setImage(item.getGalleryImage());
//                    ItemSummaryPanel.this.repaint();
//                }
//            }
//        });
//        loader.execute();
        
        return this;
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private org.jdesktop.swingx.JXImagePanel jXImagePanel1;
    private org.jdesktop.swingx.JXPanel jXPanel1;
    private javax.swing.JLabel lblBids;
    private javax.swing.JLabel lblPrice;
    private javax.swing.JLabel lblShipping;
    private javax.swing.JLabel lblSubTitle;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblTitle;
    private javax.swing.JTextArea txtSubtitle;
    private javax.swing.JTextArea txtTitle;
    // End of variables declaration//GEN-END:variables
    
}
