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
import com.thornapple.setmanager.Artist;
import com.thornapple.setmanager.adapter.PersistenceService;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.AbstractAction;

/**
 *
 * @author Bill
 */
public class LoadArtistsAction extends AbstractAction {
    
    private EventList songs;
    private PersistenceService repository;
    
    /**
     * Creates a new instance of LoadSongsAction
     */
    public LoadArtistsAction(EventList songs) {
        this.songs = songs;
    }
    
    public void actionPerformed(ActionEvent e) {
        repository = PersistenceService.getInstance();
        List _songs = repository.getAllArtists();
        songs.getReadWriteLock().writeLock().lock();
        songs.clear();
        songs.addAll(_songs);
        songs.getReadWriteLock().writeLock().unlock();
    }
    
}
