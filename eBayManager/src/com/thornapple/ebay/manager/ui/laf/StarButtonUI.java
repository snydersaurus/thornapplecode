/*
 * StarButtonUI.java
 *
 * Created on September 1, 2006, 7:48 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.ebay.manager.ui.laf;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;
import javax.swing.AbstractButton;
import javax.swing.ButtonModel;
import javax.swing.JComponent;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * Used the star inthe IGE to get this working.
 *
 * @author Bill
 */
public class StarButtonUI extends BasicButtonUI {
    
    private static final Color selectedClrHi = new Color(255, 229, 63);
    private static final Color selectedClrLo = new Color(255, 105, 0);
    
    private static final Color clrHi = Color.WHITE;//new Color(255, 229, 63);
    private static final Color clrLo = Color.LIGHT_GRAY;//new Color(255, 105, 0);
    
    private static final Color clrGlowInnerHi = new Color(253, 239, 175, 148);
    private static final Color clrGlowInnerLo = new Color(255, 209, 0);
    private static final Color clrGlowOuterHi = new Color(253, 239, 175, 124);
    private static final Color clrGlowOuterLo = new Color(255, 179, 0);
    
    private int width;
    private int height;
    private Graphics2D g;
    private Shape clipShape;
    
    /**
     * Creates a new instance of StarButtonUI
     */
    public StarButtonUI() {
    }
    
    protected void installDefaults(AbstractButton b) {
        super.installDefaults(b);
        b.setBorder(new EmptyBorder(0, 0, 0, 0));
        //b.setOpaque(false);
    }
    
    public static ComponentUI createUI(JComponent c) {
        return new StarButtonUI();
    }
    
    protected void paintButtonPressed(Graphics g, AbstractButton b) {
        //super.paintButtonPressed(g, b);
    }
    
    public void update(Graphics g, JComponent c) {
        //super.paint(g, c);
        width = c.getWidth();
        height = c.getHeight();
        this.g = (Graphics2D) g;
        clipShape = createClipShape();
        
        AbstractButton b = (AbstractButton)c;
        ButtonModel model = b.getModel();
        
        //Shape clipShape = new Ellipse2D.Float(width/4, height/4, width/2, height/2);
        
        // Clear the background to white
        g.setColor(c.getBackground());
        g.fillRect(0, 0, width, height);
        
        // Set the clip shape
        BufferedImage clipImage = createClipImage(clipShape);
        Graphics2D g2 = clipImage.createGraphics();
        
        // Fill the shape with a gradient
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setComposite(AlphaComposite.SrcAtop);
        if (model.isRollover()){
            paintBorderGlow(g2,2);
        }
        
        if (model.isSelected() || (model.isArmed() && model.isPressed())) {
            g2.setPaint(new GradientPaint(0, 0, selectedClrHi, 0, height, selectedClrLo));
        }else {
            g2.setPaint(new GradientPaint(0, 0, clrHi, 0, height, clrLo));
        }
        
        g2.fill(clipShape);
        
        // Apply the border glow effect
        //paintBorderGlow(g2, 8);
        paintBorderShadow(g2,1);
        g2.dispose();
        
        g.drawImage(clipImage, 0, 0, null);
    }
    
    private Shape createClipShape() {
        float border = 20.0f;
        
        float x1 = border;
        float y1 = border;
        float x2 = width - border;
        float y2 = height - border;
        
        float adj = 3.0f; // helps round out the sharp corners
        float arc = 8.0f;
        float dcx = 0.18f * width;
        float cx1 = x1-dcx;
        float cy1 = 0.40f * height;
        float cx2 = x1+dcx;
        float cy2 = 0.50f * height;
        GeneralPath gp = new GeneralPath();
        
//        gp.moveTo(55, 0);
//        gp.lineTo(67,36);
//        gp.lineTo(109,36);
//        gp.lineTo(73,54);
//        gp.lineTo(83,96);
//        gp.lineTo(55,68);
//        gp.lineTo(27,96);
//        gp.lineTo(37,54);
//        gp.lineTo(1,36);
//        gp.lineTo(43,36);
        //another one
        gp.moveTo(55, 0);
        gp.lineTo(67,30);
        gp.lineTo(100,30);
        gp.lineTo(77,52);
        gp.lineTo(90,90);
        gp.lineTo(55,70);
        gp.lineTo(20,90);
        gp.lineTo(37,52);
        gp.lineTo(5,30);
        gp.lineTo(43,30);
        
        //gp.quadTo(x1, y1, x1+adj, y1);
        //gp.lineTo(x2-arc, y1);
        //gp.quadTo(x2, y1, x2, y1+arc);
        //gp.lineTo(x2, y2-arc);
        //gp.quadTo(x2, y2, x2-arc, y2);
        //gp.lineTo(x1+adj, y2);
        //gp.quadTo(x1, y2, x1, y2-adj);
        //gp.curveTo(cx2, cy2, cx1, cy1, x1-adj, y1+adj);
        gp.closePath();
        return gp.createTransformedShape(AffineTransform.getScaleInstance(.25,.25));
        //return gp;
    }
    
    private BufferedImage createClipImage(Shape s) {
        // Create a translucent intermediate image in which we can perform
        // the soft clipping
        GraphicsConfiguration gc = g.getDeviceConfiguration();
        BufferedImage img = gc.createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        Graphics2D g2 = img.createGraphics();
        
        // Clear the image so all pixels have zero alpha
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, width, height);
        
        // Render our clip shape into the image.  Note that we enable
        // antialiasing to achieve the soft clipping effect.  Try
        // commenting out the line that enables antialiasing, and
        // you will see that you end up with the usual hard clipping.
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g2.setColor(Color.WHITE);
        g2.fill(s);
        g2.dispose();
        
        return img;
    }
    
    private static Color getMixedColor(Color c1, float pct1, Color c2, float pct2) {
        float[] clr1 = c1.getComponents(null);
        float[] clr2 = c2.getComponents(null);
        for (int i = 0; i < clr1.length; i++) {
            clr1[i] = (clr1[i] * pct1) + (clr2[i] * pct2);
        }
        return new Color(clr1[0], clr1[1], clr1[2], clr1[3]);
    }
    
    // Here's the trick... To render the glow, we start with a thick pen
// of the "inner" color and stroke the desired shape.  Then we repeat
// with increasingly thinner pens, moving closer to the "outer" color
// and increasing the opacity of the color so that it appears to
// fade towards the interior of the shape.  We rely on the "clip shape"
// having been rendered into our destination image already so that
// the SRC_ATOP rule will take care of clipping out the part of the
// stroke that lies outside our shape.
    private void paintBorderGlow(Graphics2D g2, int glowWidth) {
        int gw = glowWidth*2;
        for (int i=gw; i >= 2; i-=2) {
            float pct = (float)(gw - i) / (gw - 1);
            
            Color mixHi = getMixedColor(clrGlowInnerHi, pct,
                    clrGlowOuterHi, 1.0f - pct);
            Color mixLo = getMixedColor(clrGlowInnerLo, pct,
                    clrGlowOuterLo, 1.0f - pct);
            g2.setPaint(new GradientPaint(0.0f, height*0.25f,  mixHi,
                    0.0f, height, mixLo));
            g2.setColor(Color.WHITE);
            
            // See my "Java 2D Trickery: Soft Clipping" entry for more
            // on why we use SRC_ATOP here
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, pct));
            //g2.setStroke(new BasicStroke(i));
            g2.draw(clipShape);
        }
    }
    
    private void paintBorderShadow(Graphics2D g2, int shadowWidth) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        int sw = shadowWidth*2;
        for (int i=sw; i >= 2; i-=2) {
            float pct = (float)(sw - i) / (sw - 1);
            g2.setColor(getMixedColor(Color.WHITE, pct,
                    Color.LIGHT_GRAY, 1.0f-pct));
            g2.setStroke(new BasicStroke(i));
            g2.draw(clipShape);
        }
    }
    
    protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
        //super.paintFocus(g, b, viewRect, textRect, iconRect);
    }
    
    public Dimension getPreferredSize(JComponent c) {
        Dimension retValue = new Dimension();
        if (clipShape == null) clipShape = createClipShape();
        retValue.height = clipShape.getBounds().height + 3; //super.getPreferredSize(c);
        retValue.width = clipShape.getBounds().width + 3; //super.getPreferredSize(c);
        return retValue;
    }
    
}
