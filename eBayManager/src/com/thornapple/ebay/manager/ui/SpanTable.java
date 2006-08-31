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
import java.awt.Rectangle;
import org.jdesktop.swingx.JXTable;

/**
 *
 * @author Bill
 */
public class SpanTable extends JXTable{
    
    
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
        //if(/*rowValue instanceof SeparatorList.Separator*/column == 1) {
            Rectangle firstColumn = super.getCellRect(row, 0, includeSpacing);
            Rectangle lastColumn = super.getCellRect(row, getColumnCount() - 1, includeSpacing);
            return lastColumn.union(firstColumn);

         //otherwise it's business as usual
        //} else {
            //return super.getCellRect(row, column, includeSpacing);
        //}
    }
    public Rectangle getCellRectWithoutSpanning(int row, int column, boolean includeSpacing) {
        return super.getCellRect(row, column, includeSpacing);
    }
}
