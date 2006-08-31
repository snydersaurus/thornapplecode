/*
 * AuctionItemTableCellRenderer.java
 *
 * Created on August 28, 2006, 9:07 PM
 */

package com.thornapple.ebay.manager.ui;

import ca.odell.glazedlists.swing.EventTableModel;
import com.thornapple.ebay.manager.AuctionItem;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;
import org.jdesktop.swingx.border.DropShadowBorder;

/**
 *
 * @author  Bill
 */
public class ItemSummaryPanel extends javax.swing.JPanel
        implements TableCellRenderer{
    
    private EventTableModel tableModel;
    
    /** Creates new form AuctionItemTableCellRenderer */
    public ItemSummaryPanel(EventTableModel tableModel) {
        initComponents();
        this.tableModel = tableModel;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        dropShadowBorder1 = new DropShadowBorder(UIManager.getColor("Control"), 1, 5, .5f, 12, false, false, true, false);
        jXImagePanel1 = new org.jdesktop.swingx.JXImagePanel();
        lblTitle = new javax.swing.JLabel();
        lblSubtitle = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();

        org.jdesktop.layout.GroupLayout jXImagePanel1Layout = new org.jdesktop.layout.GroupLayout(jXImagePanel1);
        jXImagePanel1.setLayout(jXImagePanel1Layout);
        jXImagePanel1Layout.setHorizontalGroup(
            jXImagePanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 73, Short.MAX_VALUE)
        );
        jXImagePanel1Layout.setVerticalGroup(
            jXImagePanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 67, Short.MAX_VALUE)
        );

        lblTitle.setFont(new java.awt.Font("Tahoma", 1, 10));
        lblTitle.setText("Title");

        lblSubtitle.setFont(new java.awt.Font("Tahoma", 2, 10));
        lblSubtitle.setText("Subtitle");

        jCheckBox1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jCheckBox1.setMargin(new java.awt.Insets(0, 0, 0, 0));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jXImagePanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(layout.createSequentialGroup()
                        .add(20, 20, 20)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(lblTitle)
                            .add(lblSubtitle))
                        .addContainerGap(241, Short.MAX_VALUE))
                    .add(layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(jCheckBox1)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(jCheckBox1)
                .add(5, 5, 5)
                .add(lblTitle)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(lblSubtitle))
            .add(jXImagePanel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (isSelected)
            setBorder(dropShadowBorder1);
        else
            setBorder(null);
        
        AuctionItem item = (AuctionItem)tableModel.getElementAt(row);
        lblTitle.setText(item.getItem().getTitle());
        lblSubtitle.setText(item.getItem().getSubTitle());
        jXImagePanel1.setImage(item.getGalleryImage());
        return this;
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.jdesktop.swingx.border.DropShadowBorder dropShadowBorder1;
    private javax.swing.JCheckBox jCheckBox1;
    private org.jdesktop.swingx.JXImagePanel jXImagePanel1;
    private javax.swing.JLabel lblSubtitle;
    private javax.swing.JLabel lblTitle;
    // End of variables declaration//GEN-END:variables
    
}
