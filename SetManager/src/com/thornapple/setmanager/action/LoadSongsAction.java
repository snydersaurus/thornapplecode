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
public class LoadSongsAction extends AbstractAction {
    
    private EventList songs;
    private PersistenceService repository;
            
    /**
     * Creates a new instance of LoadSongsAction
     */
    public LoadSongsAction(EventList songs) {
        this.songs = songs;
    }

    public void actionPerformed(ActionEvent e) {
        repository = PersistenceService.getInstance();
        List _songs = repository.getAllSongs();
        songs.getReadWriteLock().writeLock().lock();
        songs.clear();
        System.out.println("Got " +_songs.size() + " songs");
        songs.addAll(_songs);
        songs.getReadWriteLock().writeLock().unlock();
    }
    
}
