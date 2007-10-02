/*
 * LoadSongsAction.java
 *
 * Created on October 7, 2006, 9:00 PM
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
public class LoadSongSetsAction extends AbstractAction {
    
    private EventList songSets;
    private PersistenceService repository;
            
    /**
     * Creates a new instance of LoadSongsAction
     */
    public LoadSongSetsAction(EventList songSets) {
        this.songSets = songSets;
    }

    public void actionPerformed(ActionEvent e) {
        repository = PersistenceService.getInstance();
        List _songs = repository.getAllSongSets();
        songSets.getReadWriteLock().writeLock().lock();
        songSets.clear();
        songSets.addAll(_songs);
        songSets.getReadWriteLock().writeLock().unlock();
    }
    
}
