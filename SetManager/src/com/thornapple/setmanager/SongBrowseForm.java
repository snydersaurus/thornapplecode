/*
 * SongBrowseForm.java
 *
 * Created on September 24, 2006, 8:32 PM
 */

package com.thornapple.setmanager;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.event.ListEventListener;
import ca.odell.glazedlists.swing.EventListModel;
import ca.odell.glazedlists.swing.EventSelectionModel;
import com.thornapple.setmanager.action.SaveSongSetAction;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JList;
import javax.swing.JTextField;

/**
 *
 * @author  Bill
 */
public class SongBrowseForm extends javax.swing.JPanel {
    
    EventList songs = new BasicEventList();
    EventList songSets = new BasicEventList();
    
    private SortedList sortedItems;
    private FilterList filteredList;
    
    private EventListModel listModel;
    private EventSelectionModel selectionModel;
    private MatcherFactory matcherFactory = MatcherFactory.getInstance();
    
    /** Creates new form SongBrowseForm */
    public SongBrowseForm() {
        initComponents();
    }
    
    public void setSongSets(EventList songSets){
        this.songSets = songSets;
        System.out.println("songsets:"+songSets.size());
        cboSet.setModel(new SongSetComboBoxModel(songSets));
    }
    
    public void setSongs(EventList songs){
        sortedItems =
                new SortedList(songs);
        
        filteredList = new FilterList(sortedItems,
                matcherFactory.createMatcher(songs,this));
        
        selectionModel = new EventSelectionModel(filteredList);
        songList.setSelectionModel(selectionModel);
        listModel = new EventListModel(filteredList);
        songList.setModel(listModel);
        
        sortedItems.addListEventListener(new ListEventListener() {
            public void listChanged(ListEvent event ) {
                listModel.listChanged(event);
            }
        });
        
    }
    
    public JTextField getArtistTitleComponent(){
        return this.txtArtistName;
    }
    
    public JTextField getSongTitleComponent(){
        return this.txtSongName;
    }
    
    public JList getSongListComponent(){
        return songList;
    }
    
    public JList getArtistListComponent(){
        return artistList;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtArtistName = new javax.swing.JTextField();
        txtSongName = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        artistList = new javax.swing.JList();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        songList = new javax.swing.JList();
        cboSet = new javax.swing.JComboBox();
        btnAddToSet = new javax.swing.JButton();

        jLabel1.setText("Song Name");

        jLabel2.setText("Artist Name");

        txtArtistName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtArtistNameActionPerformed(evt);
            }
        });

        jLabel3.setText("Artists");

        jScrollPane1.setViewportView(artistList);

        jLabel4.setText("Songs");

        jScrollPane2.setViewportView(songList);

        cboSet.setEditable(true);

        btnAddToSet.setText("Add to Set");
        btnAddToSet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddToSetActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel2)
                            .add(jLabel3)
                            .add(jLabel4)
                            .add(jLabel1))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .add(txtSongName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .add(txtArtistName, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(cboSet, 0, 132, Short.MAX_VALUE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(btnAddToSet)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(btnAddToSet)
                    .add(cboSet, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(7, 7, 7)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel1)
                    .add(txtSongName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(txtArtistName, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel3)
                    .add(jScrollPane1))
                .add(11, 11, 11)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jLabel4)
                    .add(jScrollPane2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnAddToSetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddToSetActionPerformed
        Object item = cboSet.getSelectedItem();
        SongSet set = null;
        if (item instanceof String){
           set = new SongSet(item.toString(),""); 
        } else
            set = (SongSet) item;
        List songs = getSelectedSongs();
        new SaveSongSetAction(songSets,set,songs).actionPerformed(null);
        
    }//GEN-LAST:event_btnAddToSetActionPerformed
    
    private void txtArtistNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtArtistNameActionPerformed
// TODO add your handling code here:
    }//GEN-LAST:event_txtArtistNameActionPerformed
    
    List getSelectedSongs() {
        Object[] selected =
                songList.getSelectedValues();
        return Arrays.asList(selected);
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList artistList;
    private javax.swing.JButton btnAddToSet;
    private javax.swing.JComboBox cboSet;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JList songList;
    private javax.swing.JTextField txtArtistName;
    private javax.swing.JTextField txtSongName;
    // End of variables declaration//GEN-END:variables
    
    private class SongSetComboBoxModel extends DefaultComboBoxModel{
        EventList<SongSet> songSets;
        public SongSetComboBoxModel(EventList<SongSet> songSets){
            super();
            this.songSets = songSets;
        }
        
        public Object getElementAt(int index) {
            Object retValue;
            retValue = songSets.get(index);
            return retValue;
        }
        
        public int getSize() {
            int retValue;
            retValue = songSets.size();
            return retValue;
        }
        
    }
}
