/*
 * SetManagerTest.java
 *
 * Created on January 3, 2007, 10:47 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.setmanager;

import com.thornapple.setmanager.adapter.PersistenceService;
import com.thornapple.setmanager.adapter.SongSearchService;
import java.util.List;

/**
 *
 * @author Bill
 */
public class SetManagerTest {
    
    PersistenceService repository = PersistenceService.getInstance();
    SongSearchService songService = new SongSearchService();
    
    /** Creates a new instance of SetManagerTest */
    public SetManagerTest() {
    }
    
    public void testAddArtist() throws Exception {
        Artist a1 = new Artist();
        a1.setName("Bill Snyder");
        repository.addOrUpdateArtist(a1);
        long newId = a1.getId();
        
        Artist saved = repository.getArtistByID(newId);
        System.out.println(saved.getId()+":"+saved.getName());
        
        repository.shutdown();
    }
    
    public void testAddArtistAndSong() throws Exception {
        List<Song> songs = songService.findSongsByArtistName("Kutless");
        Song s1 = songs.get(0);
        Artist a1 = songService.getArtist(s1);
        a1.addSong(s1);
        repository.addOrUpdateArtist(a1);
    }
    
    public static void main(String args[]) throws Exception {
        SetManagerTest test = new SetManagerTest();
        test.testAddArtist();
    }
    
}
