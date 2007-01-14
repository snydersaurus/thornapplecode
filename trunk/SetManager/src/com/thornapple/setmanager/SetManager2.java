/*
 * SetManager2.java
 *
 * Created on September 17, 2006, 11:52 AM
 */

package com.thornapple.setmanager;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import com.thornapple.setmanager.action.LoadSongSetsAction;
import com.thornapple.setmanager.action.LoadSongsAction;
import javax.swing.UIManager;

/**
 *
 * @author  Bill
 */
public class SetManager2 extends javax.swing.JFrame {
    
    private EventList songs = new BasicEventList();
    private EventList songSets = new BasicEventList();
    
    /** Creates new form SetManager2 */
    public SetManager2() {
        initComponents();
        setUI1.setSongList(songs);
        setUI1.setSongSetList(songSets);
        loadSongs();
    }
    
    private void loadSongs(){
        LoadSongsAction songLoader = new LoadSongsAction(songs);
        songLoader.actionPerformed(null);
        
        LoadSongSetsAction songSetLoader = new LoadSongSetsAction(songSets);
        songSetLoader.actionPerformed(null);
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        setUI1 = new com.thornapple.setmanager.SetUI();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(setUI1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(setUI1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exc) {
            // Do nothing...
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SetManager2().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.thornapple.setmanager.SetUI setUI1;
    // End of variables declaration//GEN-END:variables
    
}
