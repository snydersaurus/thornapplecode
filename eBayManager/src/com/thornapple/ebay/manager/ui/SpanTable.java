/*
 * SpanTable.java
 *
 * Created on August 30, 2006, 5:41 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.ebay.manager.ui;

import ca.odell.glazedlists.swing.EventTableModel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Bill
 */
public class SpanTable extends JXTable{
    
    public SpanTable(){
        super();
        addComponentListener(new ComponentAdapter() {
            // This method is called after the component's size changes
            public void componentResized(ComponentEvent evt) {
                Component c = (Component)evt.getSource();
                // Get new size
                Dimension newSize = c.getSize();
                System.out.println(newSize);
            }
        });
    }
    
    /** {@inheritDoc} */
    public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
        EventTableModel eventTableModel = (EventTableModel) getModel();
        
        // sometimes JTable asks for a cellrect that doesn't exist anymore, due
        // to an editor being installed before a bunch of rows were removed.
        // In this case, just return an empty rectangle, since it's going to
        // be discarded anyway
        if(eventTableModel.getRowCount() < 1 || row >= eventTableModel.getRowCount()) {
            return new Rectangle(0, 0, 0, 0);
        }
        
        // if it's the separator row, return the entire row as one big rectangle
        Object rowValue = eventTableModel.getElementAt(row);
        if(/*rowValue instanceof SeparatorList.Separator*/column > 0) {
            Rectangle firstColumn = super.getCellRect(row, 1, includeSpacing);
            Rectangle lastColumn = super.getCellRect(row, getColumnCount() - 1, includeSpacing);
            return lastColumn.union(firstColumn);
            
            //otherwise it's business as usual
        } else {
            return super.getCellRect(row, column, includeSpacing);
        }
    }
    public Rectangle getCellRectWithoutSpanning(int row, int column, boolean includeSpacing) {
        return super.getCellRect(row, column, includeSpacing);
    }
}
