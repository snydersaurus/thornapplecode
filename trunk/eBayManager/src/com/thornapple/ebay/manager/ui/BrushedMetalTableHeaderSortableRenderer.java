package com.thornapple.ebay.manager.ui;

import ca.odell.glazedlists.swing.SortableRenderer;
import com.elevenworks.swing.util.ColorUtil;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicPanelUI;

/**
 * A custom table header renderer to simulate the header's used in
 * Apple's brushed metal applications.
 * <p/>
 * Copyright (C) 2005-2006 by Jon Lipsky
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software d
 * istributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class BrushedMetalTableHeaderSortableRenderer extends JPanel
        implements TableCellRenderer, SortableRenderer {
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    
    public static final Color selectedTopStartColor = ColorUtil.parseHtmlColor("#D0E0F4");
    public static final Color selectedTopEndColor = ColorUtil.parseHtmlColor("#8FBBE8");
    public static final Color selectedBottomStartColor = ColorUtil.parseHtmlColor("#6AAAEB");
    public static final Color selectedBottomEndColor = ColorUtil.parseHtmlColor("#B8FAFF");
    
    public static final Color unselectedTopStartColor = Color.WHITE;
    public static final Color unselectedTopEndColor = ColorUtil.parseHtmlColor("#F2F2F2");
    public static final Color unselectedBottomStartColor = ColorUtil.parseHtmlColor("#E8E8E8");
    public static final Color unselectedBottomEndColor = Color.WHITE;
    
    public static final Color borderMiddleColor = ColorUtil.parseHtmlColor("#666666");
    public static final Color borderVerticalColor = new Color(150, 150, 150, 150);
    
    protected JTable table;
    protected Object value;
    protected boolean isSelected;
    protected boolean hasFocus;
    protected int row;
    protected int column;
    
    private JLabel lblText = new JLabel();
    
    // ------------------------------------------------------------------------------------------------------------------
    //  Constructors and Getter/Setter methods
    // ------------------------------------------------------------------------------------------------------------------
    
    public BrushedMetalTableHeaderSortableRenderer() {
        init();
    }
    
    public String getText(Object aObject) {
        return aObject.toString();
    }
    
    // ------------------------------------------------------------------------------------------------------------------
    //  Overwridden methods from the superclass(es)
    // ------------------------------------------------------------------------------------------------------------------
    
    public Dimension getPreferredSize() {
        Dimension vDimension = super.getPreferredSize();
        vDimension.height = getFont().getSize() + 6;
        return vDimension;
    }
    
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        this.table = table;
        this.value = value;
        this.isSelected = isSelected;
        this.hasFocus = hasFocus;
        this.row = row;
        this.column = column;
        this.value = value;
        if (column == 0) lblText.setIcon(null);
        lblText.setText(getText(value));
        lblText.setFont(table.getFont());
        
        return this;
    }
    
    // ------------------------------------------------------------------------------------------------------------------
    // The following methods override the defaults for performance reasons
    // ------------------------------------------------------------------------------------------------------------------
    
    
    public void setSortIcon(Icon icon) {
        lblText.setIcon(icon);
    }
    
    private void init() {
        setUI(new TableHeaderPanelUI());
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(0,5,0,0));
        lblText.setHorizontalTextPosition(JLabel.LEFT);
        add(lblText,BorderLayout.CENTER);
    }
    
    private class TableHeaderPanelUI extends BasicPanelUI {
        // ------------------------------------------------------------------------------------------------------------------
        //  Custom painting methods
        // ------------------------------------------------------------------------------------------------------------------
        
        public void paint(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g;
            
            Color topStartColor = isSelected ? selectedTopStartColor : unselectedTopStartColor;
            Color topEndColor = isSelected ? selectedTopEndColor : unselectedTopEndColor;
            Color bottomStartColor = isSelected ? selectedBottomStartColor : unselectedBottomStartColor;
            Color bottomEndColor = isSelected ? selectedBottomEndColor : unselectedBottomEndColor;
            
            Dimension vSize = getSize();
            
            int h = vSize.height;
            int w = vSize.width;
            
            int h2 = h / 2;
            int h4 = h / 4;
            
            g2.setPaint(new GradientPaint(0, 0, topStartColor, 0, h4, topEndColor));
            g2.fillRect(0, 0, w, h2);
            
            g2.setColor(topEndColor);
            g2.setPaint(new GradientPaint(0, h4 + 1, topStartColor, 0, h2, topEndColor));
            
            g2.setPaint(new GradientPaint(0, h2, bottomStartColor, 0, h - 2, bottomEndColor));
            g2.fillRect(0, h2, w, h - 2);
            
            g2.setColor(borderMiddleColor);
            g2.drawLine(0, h - 1, w, h - 1);
            
            boolean isLast = column == table.getColumnCount() - 1;
            if (!isLast) {
                g2.setColor(borderVerticalColor);
                //g2.drawLine(w - 1, 0, w - 1, h - 1);
            }
        }
    }
    
}
