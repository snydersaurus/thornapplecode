package com.thornapple.ebay.manager.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import org.jdesktop.swingx.JXImagePanel;

class PhotoListCellRenderer extends DefaultListCellRenderer {
    private final Border empty = BorderFactory.createEmptyBorder(3, 3, 5, 3);
    private final Border selection = new CompoundBorder(
            BorderFactory.createEmptyBorder(0, 0, 2, 0),
            BorderFactory.createLineBorder(Color.WHITE, 3));
    
    private JXImagePanel imagePanel = new JXImagePanel();
    
    
    PhotoListCellRenderer() {
        imagePanel.setPreferredSize(new Dimension(96,96));
        imagePanel.setStyle(JXImagePanel.Style.SCALED);
    }
    
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index,
            boolean isSelected, boolean cellHasFocus) {
        if (value instanceof Image){
            try {
                imagePanel.setImage((Image)value);
            } catch (Exception ex) {
                imagePanel.setImage(ItemDetailPanel.LOADING);
                //ex.printStackTrace();
            }
        } else
            imagePanel.setImage(ItemDetailPanel.LOADING);
        
        //if (photo.isSmallSquareImageLoaded()) {
        //BufferedImage img = photo.getSmallSquareImage();
        //img = GraphicsUtil.createThumbnail(img,100);
        //  label.setIcon(photo.getIcon());
        //label.setIcon(new ImageIcon(img));
        //else {
        //label.setText("Loading...");
        //}
        
        imagePanel.setBackground(null);
        imagePanel.setBorder(isSelected ? selection : empty);
        imagePanel.setOpaque(false);
        
        return imagePanel;
    }
    
    @Override
    public boolean isOpaque() {
        return false;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Insets insets = getInsets();
        if (getIcon() != null) {
            getIcon().paintIcon(this, g, insets.left, insets.top);
        }
    }
    
    @Override
    public Dimension getMaximumSize() {
        return this.getPreferredSize();
    }
    
    @Override
    public Dimension getMinimumSize() {
        return this.getPreferredSize();
    }
    
    @Override
    public Dimension getPreferredSize() {
        Insets insets = getInsets();
        if (getIcon() == null) {
            return new Dimension(56, 58);
        }
        return new Dimension(getIcon().getIconWidth() + insets.left + insets.right,
                getIcon().getIconHeight() + insets.top + insets.bottom);
    }
}
