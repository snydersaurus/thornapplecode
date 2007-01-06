/*
 * SaveSongAction.java
 *
 * Created on January 4, 2007, 10:25 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.setmanager.action;

import ca.odell.glazedlists.EventList;
import com.thornapple.setmanager.adapter.PersistenceService;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;

/**
 *
 * @author Bill
 */
public class RemoveSongAction extends AbstractAction {
    
    private EventList songs;
    private List oldSongs;
    private PersistenceService repository;
    
    /** Creates a new instance of SaveSongAction */
    public RemoveSongAction(EventList songs, List oldSongs) {
        this.songs = songs;
        this.oldSongs = oldSongs;
    }

    public void actionPerformed(ActionEvent e) {
        repository = PersistenceService.getInstance();
        if (oldSongs == null) return;
        try {
            songs.removeAll(oldSongs);
            repository.removeSongs(oldSongs);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
