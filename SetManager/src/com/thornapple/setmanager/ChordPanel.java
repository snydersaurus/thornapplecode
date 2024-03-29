/*
 * ChordPanel.java
 *
 * Created on September 30, 2007, 5:36 PM
 */

package com.thornapple.setmanager;

import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author  Bill
 */
public class ChordPanel extends javax.swing.JPanel {
    
    private char[] notes = "000000".toCharArray();
    
    /** Creates new form ChordPanel */
    public ChordPanel() {
        initComponents();
    }
    
    public ChordPanel(char[] chord) {
        initComponents();
        setChord(chord);
        addMouseListener(new MouseAdapter() {
             public void mouseClicked(MouseEvent me) {
                int centerX = me.getX();
                int centerY = me.getY();
                double innerSize = 20;
                double outerSize = 20;
                Area a1 = new Area(
                        new Ellipse2D.Double(centerX - outerSize/2,
                        centerY - outerSize/2, innerSize, outerSize));
                //loop through and see if the 20 intersections are in the area?
                //a better way? components on the intersections with mouselisteners?
                System.out.println(centerX + ", " + centerY);
                
             }
        });
        
    }
    
    public void setChord(char[] chord){
        this.notes = chord;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Graphics2D g2d = (Graphics2D)g;
        //g2d.setBackground(Color.RED);
        //g2d.fillRect(0, 0, getWidth(), getHeight());
        int x = 25;
        int y = 25;
        int h = getHeight() - (y*2);
        int w = getWidth()- (x*2);
        int stringSpacing = w/5;
        g.drawRect(x, y, w, h);
        for (int s = 0; s < 5; s++)
            g.drawLine(x + stringSpacing*s, y, x + stringSpacing*s, y + h);
        
        int fretSpacing = h/ 5;
        for (int f = 0; f < 5; f++)
            g.drawLine(x, y + fretSpacing*f, x + w, y + fretSpacing*f);
        
        //draw circle on the intersections
        int noteSize = fretSpacing/2;
        
        int fret = 0;
        for (int n = 0;n<6;n++){
            fret = Integer.parseInt(notes[n]+"");
            g.drawOval(x + stringSpacing*n - noteSize/2, 
                    y + fretSpacing*fret - noteSize/2, noteSize, noteSize);
        }
    }
    
    private static void createAndShowGUI() {
        JFrame f = new JFrame("Chord Panel Demo");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(300, 100);
        f.add(new ChordPanel("022100".toCharArray()));
        f.setVisible(true);
    }
    
    public static void main(String args[]) {
        Runnable doCreateAndShowGUI = new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        };
        SwingUtilities.invokeLater(doCreateAndShowGUI);
    }
    
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
