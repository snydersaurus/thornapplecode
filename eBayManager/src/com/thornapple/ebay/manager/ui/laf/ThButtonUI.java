/*
 * ThButtonUIDelegate.java
 *
 * Created on August 16, 2006, 9:55 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.ebay.manager.ui.laf;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.Shape;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.basic.BasicHTML;
import javax.swing.plaf.metal.MetalButtonUI;
import javax.swing.text.View;
import org.jdesktop.swingx.painter.CompoundPainter;
import org.jdesktop.swingx.painter.GlossPainter;
import org.jdesktop.swingx.painter.MattePainter;

/**
 *
 * @author Bill
 */
public class ThButtonUI extends BasicButtonUI {
    private static Rectangle viewRect = new Rectangle();
    
    private static Rectangle textRect = new Rectangle();
    
    private static Rectangle iconRect = new Rectangle();
    
    
    Color GLOSS_BASE_COLOR = new Color(1.0f, 1.0f, 1.0f, 0.2f);
    Color MATTE_BASE_COLOR = new Color(51, 51, 51);
    GlossPainter gloss = new GlossPainter(GLOSS_BASE_COLOR,
            GlossPainter.GlossPosition.TOP);
    MattePainter matte = new MattePainter(MATTE_BASE_COLOR);
    CompoundPainter cp =
            new CompoundPainter(matte, gloss);
    
    /** Creates a new instance of ThButtonUIDelegate */
    public ThButtonUI() {
    }
    
    public void paint(Graphics g, JComponent c) {
        AbstractButton b = (AbstractButton)c;
        ButtonModel model = b.getModel();
        
        FontMetrics fm = g.getFontMetrics();
        
        Insets i = c.getInsets();

        viewRect.x = i.left;
        viewRect.y = i.top;
        viewRect.width = b.getWidth() - (i.right + viewRect.x);
        viewRect.height = b.getHeight() - (i.bottom + viewRect.y);
        
        textRect.x = textRect.y = textRect.width = textRect.height = 0;
        iconRect.x = iconRect.y = iconRect.width = iconRect.height = 0;
        
        Font f = c.getFont();
        g.setFont(f);
        
        String text = SwingUtilities.layoutCompoundLabel(c, fm, b.getText(), b
                .getIcon(), b.getVerticalAlignment(), b
                .getHorizontalAlignment(), b.getVerticalTextPosition(), b
                .getHorizontalTextPosition(), viewRect, iconRect, textRect, b
                .getText() == null ? 0 : b.getIconTextGap());
        
        if (model.isArmed() && model.isPressed()) {
            matte.setPaint(GLOSS_BASE_COLOR);
            paintButtonPressed(g, b);new MetalButtonUI();
        }
        
        if (b.getIcon() != null) {
            paintIcon(g, c, iconRect);
        }
        
        if ( b.isFocusPainted() && b.hasFocus()){
            System.out.println("focused or selected");
            matte.setPaint(MATTE_BASE_COLOR);
            gloss.setPosition(GlossPainter.GlossPosition.TOP);
            paintFocus(g, b, viewRect, textRect, iconRect);
        } else {
            System.out.println("regular");
            matte.setPaint(MATTE_BASE_COLOR);
            gloss.setPosition(GlossPainter.GlossPosition.TOP);
        }
        
        if (model.isRollover()){
            matte.setPaint(GLOSS_BASE_COLOR);
            gloss.setPosition(GlossPainter.GlossPosition.BOTTOM);
        }
        
        cp.paint((Graphics2D) g, c);
        //super.paint(g, c);
        if (text != null && !text.equals("")) {
            View v = (View) c.getClientProperty(BasicHTML.propertyKey);
            if (v != null) {
                textRect.x += getTextShiftOffset();
                textRect.y += getTextShiftOffset();
                v.paint(g, textRect);
                textRect.x -= getTextShiftOffset();
                textRect.y -= getTextShiftOffset();
            } else {
                paintText(g, b, textRect, text);
            }
        }
        
    }
    
    private static class ShapeJComponent extends JComponent {
        private Shape shape;
        public ShapeJComponent(Shape shape){
            super();
            this.shape = shape; 
        }

        public int getWidth() {
            int retValue;
            retValue = shape.getBounds().width;
            return retValue;
        }

        public int getHeight() {
            int retValue;
            retValue = shape.getBounds().height;
            return retValue;
        }
        
    }
    
}
