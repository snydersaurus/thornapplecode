/*
 * PersistenceService.java
 *
 * Created on September 17, 2006, 9:45 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.setmanager.adapter;

import com.thornapple.setmanager.Artist;
import com.thornapple.setmanager.Song;
import com.thornapple.setmanager.SongSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * Handles Song CRUD operations.
 *
 * @author Bill
 */
public class PersistenceService {
    EntityManagerFactory factory;
    EntityManager manager;
    
    private static PersistenceService instance;
    
    private final static String DOES_ARTIST_EXIST =
            "select a from Artist a where name = ?1";
    
    private final static String DOES_SONG_EXIST =
            "select s from Song s where name = ?1";
    
    /**
     * Creates a new instance of PersistenceService
     */
    private PersistenceService() {
        startup();
    }
    
    public synchronized static PersistenceService getInstance(){
        if (instance == null)
            instance = new PersistenceService();
        
        return instance;
    }
    
    public void startup() {
        if (factory != null)
            return;
        
        factory = Persistence.createEntityManagerFactory("SetManager");
        manager = factory.createEntityManager();
        //manager.setFlushMode(FlushModeType.COMMIT);
    }
    
    
    public void shutdown() {
        manager.close();
        factory.close();
    }
    
    public boolean artistExists(Artist artist) throws Exception{
        boolean ok = false;
        List artists = getArtistByName(artist.getName());
        if (artists != null &&artists.size() > 0)
            ok = true;
        
        return ok;
    }
    
    public Artist getArtistByID(long id) throws Exception {
        EntityTransaction tx = manager.getTransaction();
        Artist a = null;
        tx.begin();
        try {
            a = manager.find(Artist.class,id);
            tx.commit();
            return a;
        } catch (Exception e){
            e.printStackTrace();
            return a;
        }
    }
    
    public List<Artist> getArtistByName(String name) throws Exception {
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            Query query = manager.createQuery(DOES_ARTIST_EXIST);
            query.setParameter(1,name);
            List<Artist> results = (List<Artist>)query.getResultList();
            tx.commit();
            return results;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean songExists(Song song) throws Exception{
        boolean ok = false;
        List songs = getSongByName(song.getName());
        if (songs != null && songs.size() > 0)
            ok = true;
        
        return ok;
    }
    
    public List<Song> getSongByName(String name) throws Exception {
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            Query query = manager.createQuery(DOES_SONG_EXIST);
            query.setParameter(1,name);
            List<Song> results = (List<Song>)query.getResultList();
            tx.commit();
            return results;
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    public boolean addOrUpdateSong(Long artistID,Song song) throws Exception{
        EntityTransaction tx = manager.getTransaction();
        tx.begin();
        try {
            
            Artist artist = manager.find(Artist.class,artistID);
            
            if (song.getId() != null && song.getId() > 0) {
                manager.merge(song);
                tx.commit();
                return true;
            }
            
            artist.addSong(song);
            manager.persist(artist);
            tx.commit();
        } catch (Exception e){
            //do something
            e.printStackTrace();
        }
        return true;
    }
    
    public boolean addOrUpdateArtist(Artist artist) throws Exception{
        EntityTransaction tx = manager.getTransaction();
        boolean update = false;
        try {
            tx.begin();
            manager.persist(artist);
            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
    
    public boolean addOrUpdateSongSet(SongSet songset) throws Exception{
        EntityTransaction tx = manager.getTransaction();
        boolean update = false;
        try {
            tx.begin();
            if (songset.getId() == null || songset.getId() < 0)
                manager.persist(songset);
            else
                manager.merge(songset);
            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
            tx.rollback();
        }
        return true;
    }
    
    public boolean removeSong(Song song) throws Exception{
        EntityTransaction tx = manager.getTransaction();
        boolean update = false;
        try {
            tx.begin();
            //Artist a = song.getArtist();
            //a.removeSong(song);
            manager.remove(song);
            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
    
    public boolean removeSongs(List<Song> songs) {
        EntityTransaction tx = manager.getTransaction();
        boolean update = false;
        try {
            tx.begin();
            for (Song song : songs) {
                song.setArtist(null);
                manager.remove(song);
                manager.flush();
            }
            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
    
    public List getAllSongs(){
        EntityTransaction tx = manager.getTransaction();
        List<Song> results = null;
        tx.begin();
        try {
            Query query = manager.createQuery("select s from Song s");
            results = (List<Song>)query.getResultList();
            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
    
    public List getAllArtists(){
        EntityTransaction tx = manager.getTransaction();
        List<Song> results = null;
        tx.begin();
        try {
            Query query = manager.createQuery("select a from Artist a");
            results = (List<Song>)query.getResultList();
            //System.out.println("Getting songs");
            //for(Song s : results) {
            //    System.out.println("got a song: " + s.getName());
            //}
            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
    
    public List getAllSongSets(){
        EntityTransaction tx = manager.getTransaction();
        List<SongSet> results = null;
        tx.begin();
        try {
            Query query = manager.createQuery("select s from SongSet s");
            results = (List<SongSet>)query.getResultList();
            tx.commit();
        } catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
    
    public static void main(String[] args) throws Exception {
        //Class.forName("org.hsqldb.jdbcDriver").newInstance();
        //Connection c = DriverManager.getConnection("jdbc:hsqldb:file:test", "sa", "");
        PersistenceService service = PersistenceService.getInstance();
        List<Song> songs = service.getSongByName("Enough");
        Song testSong = songs.get(0);
        SongSet testSet = new SongSet("Test Set", "");
        testSet.addSong(testSong);
        
        service.addOrUpdateSongSet(testSet);
        List<SongSet> songsets = service.getAllSongSets();
        for(SongSet s : songsets) {
            System.out.println("got a songset: " + s.getName());
        }
//        Artist a = new Artist();
//        a.setName("Bill Snyder");
//        service.addArtist(a);
//
//        Song s = new Song("The Great Adventure");
//        s.setTablature("tab.....");
//        a.addSong(s);
//        service.addSong(a.getId(),s);
        
        service.shutdown();
        
        //Statement stmt = c.createStatement();
        //stmt.execute("SHUTDOWN");
        //c.close();
    }
    
}
