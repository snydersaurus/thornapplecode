/*
 * SongSearchService.java
 *
 * Created on September 16, 2006, 10:51 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.setmanager.adapter;

import com.thornapple.setmanager.Artist;
import com.thornapple.setmanager.Song;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Bill
 */
public class SongSearchService {
    
    private static final String SONG_LIST = "http://www.christianguitar.org/csongsletter/";
    private static final String ARTIST_LIST = "http://www.christianguitar.org/cartist/";
    private static final String ARTIST_SONG_LIST = "http://www.christianguitar.org/csongs";
    private static final String SONG_TAB = "http://www.christianguitar.org/csongplain/";
    
    
    /**
     * Creates a new instance of SongSearchService
     */
    public SongSearchService() {
    }
    
    public Song findSongBySongID(int songID) throws Exception {
        Song s = new Song("N/A");
        
        URL u2 = new URL(SONG_TAB+songID);
        System.out.println("contacting service " + SONG_TAB+songID);
        InputStream is2 = u2.openConnection().getInputStream();
        StringBuffer data2 = new StringBuffer();
        String line2 = null;
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(is2));
        while ( (line2 = reader2.readLine()) != null){
            data2.append(line2);data2.append(System.getProperty("line.separator"));
        }
        //System.out.println(data2.toString());
        String tab = data2.toString();
        tab = tab.substring(tab.indexOf("<pre>")+5,tab.indexOf("</pre>"));
        //tab = "<html><body>"+tab+"</body></html>";
        s.setTablature(tab);
        
        return s;
    }
    
    public List findSongsByArtistName(String name) throws Exception{
        List results = new ArrayList();
        //get first letter
        char firstLetter = name.charAt(0);
        
        URL u = new URL(ARTIST_LIST+firstLetter);
        System.out.println("contacting service " + ARTIST_LIST+firstLetter);
        InputStream is = u.openConnection().getInputStream();
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while ( (line = reader.readLine()) != null){
            if (line.contains("/csong"))
                data.append(line);
        }
        System.out.println("read data");
        
        String[] artists = data.toString().split("<A HREF=");
        for (int i = 0; i < artists.length; i++) {
            int artistIndex = artists[i].indexOf("csongs");
            if (artistIndex < 0) continue;
            artists[i] = artists[i].substring(artistIndex+6,artists[i].indexOf("<"));
            //System.out.println(artists[i]);
            //System.out.println(songIndex + " " + artistIndex);
            String artistID = artists[i].substring(0,artists[i].lastIndexOf("/"));
            String artistName = artists[i].substring(artists[i].indexOf(">")+1);
            //System.out.println(artistID + " " + artistName);
            if (artistName.equalsIgnoreCase(name)) {
                //System.out.println(artistID);
                List songs = findSongsByArtistID(artistID);
                Artist a = new Artist(artistName);
                a.setCgrID(artistID);
                a.addSongs(songs);
                
                results.addAll(songs);
            }
        }
        
        return results;
    }
    
    
    
    public List findSongsByArtistID(String id) throws Exception {
        List results = new ArrayList();
        
        URL u = new URL(ARTIST_SONG_LIST+id);
        System.out.println("contacting service " + ARTIST_SONG_LIST+id);
        InputStream is = u.openConnection().getInputStream();
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while ( (line = reader.readLine()) != null){
            if (line.contains("/csong"))
                data.append(line);
        }
        System.out.println("read data");
        String[] songs = data.toString().split("<A HREF=");
        for (int i = 0; i < songs.length; i++) {
            //System.out.println(songs[i]);
            songs[i] = songs[i].substring(0,songs[i].indexOf("<"));
            int songIndex = songs[i].indexOf("csong");
            int artistIndex = songs[i].indexOf("csongs");
            //System.out.println(songIndex + " " + artistIndex);
            if (artistIndex >= 0 || songIndex < 0) continue;
            String songID = songs[i].substring(songIndex+5,songs[i].lastIndexOf("/"));
            String songName = songs[i].substring(songs[i].indexOf(">")+1);
            //if (songName.equalsIgnoreCase(name)) {
            //load tab and print
            //System.out.println(songID);
            //URL u2 = new URL(SONG_TAB+songID);
            //System.out.println("contacting service " + SONG_TAB+songID);
            //InputStream is2 = u2.openConnection().getInputStream();
            //StringBuffer data2 = new StringBuffer();
            //String line2 = null;
            //BufferedReader reader2 = new BufferedReader(new InputStreamReader(is2));
            //while ( (line2 = reader2.readLine()) != null){
            //    data2.append(line2);data2.append(System.getProperty("line.separator"));
            //}
            //System.out.println(data2.toString());
            Song s = new Song(songName);
            s.setCgrID(songID);
            //String tab = data2.toString();
            //tab = tab.substring(tab.indexOf("<pre>")+5,tab.indexOf("</pre>"));
            //tab = "<html><body>"+tab+"</body></html>";
            //System.out.println(tab);
            //s.setTablature(tab);
            results.add(s);
            //}
            
        }
        //load that page
        //check for matches
        //load the data
        
        return results;
    }
    
    public List findSongsByName(String name) throws Exception {
        List results = new ArrayList();
        
        //get first letter
        char firstLetter = name.charAt(0);
        
        URL u = new URL(SONG_LIST+firstLetter);
        System.out.println("contacting service " + SONG_LIST+firstLetter);
        InputStream is = u.openConnection().getInputStream();
        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        while ( (line = reader.readLine()) != null){
            if (line.contains("/csong"))
                data.append(line);
        }
        System.out.println("read data");
        Artist artist = null;
        Song song = null;
        String[] songs = data.toString().split("<A HREF=");
        for (int i = 0; i < songs.length; i++) {
            //System.out.println(songs[i]);
            songs[i] = songs[i].substring(0,songs[i].indexOf("<"));
            int songIndex = songs[i].indexOf("csong");
            int artistIndex = songs[i].indexOf("csongs");
            String artistID = null;
            //System.out.println(songIndex + " " + artistIndex);
            if (artistIndex >= 0 && song != null){
                //System.out.println(songs[i]);
                artist = new Artist(songs[i].substring(songs[i].indexOf(">")+1));
                //System.out.println(artist.getName());
                artistID = songs[i].substring(songs[i].indexOf("csongs")+6,
                        songs[i].lastIndexOf("/"));
                artist.setCgrID(artistID);
                artist.addSong(song);
                System.out.println("artist:"+song.getArtist());
                results.add(song);
                song = null;
                continue;
            }
            if (songIndex < 0) continue;
            String songID = songs[i].substring(songIndex+5,songs[i].lastIndexOf("/"));
            String songName = songs[i].substring(songs[i].indexOf(">")+1).trim();
            if (songName.equalsIgnoreCase(name)) {
                //load tab and print
//                System.out.println(songID);
//                URL u2 = new URL(SONG_TAB+songID);
//                System.out.println("contacting service " + SONG_TAB+songID);
//                InputStream is2 = u2.openConnection().getInputStream();
//                StringBuffer data2 = new StringBuffer();
//                String line2 = null;
//                BufferedReader reader2 = new BufferedReader(new InputStreamReader(is2));
//                while ( (line2 = reader2.readLine()) != null){
//                    data2.append(line2);data2.append(System.getProperty("line.separator"));
//                }
                //System.out.println(data2.toString());
                song = new Song(songName);
                song.setCgrID(songID);
                System.out.println(songName);
                //String tab = data2.toString();
                //tab = tab.substring(tab.indexOf("<pre>")+5,tab.indexOf("</pre>"));
                //tab = "<html><body>"+tab+"</body></html>";
//                System.out.println(tab);
                //song.setTablature(tab);
            }
            
        }
        //load that page
        //check for matches
        //load the data
        
        return results;
    }
//http://www.christianguitar.org/csong17304/Rebecca-St.-James--God-Of-Wonders> God Of Wonders</A></TD><TD align=center>
    
    
    public static void main(String args[]) throws Exception{
        SongSearchService searchService = new SongSearchService();
        List songs = searchService.findSongsByArtistName("Byrd, Marc");
        //for (int i = 0; i < songs.size(); i++) {
        //    System.out.println(((Song)songs.get(i)).getTablature());
        //}
        //searchService.findSongsByName("Grace Like Rain");
        //Song s = searchService.findSongBySongID(10032);
        //System.out.println(s.getTablature());
    }
    
    public void getTab(Song song) throws Exception {
        URL u2 = new URL(SONG_TAB+song.getCgrID());
        //System.out.println("contacting service " + SONG_TAB+song.getCgrID());
        InputStream is2 = u2.openConnection().getInputStream();
        StringBuffer data2 = new StringBuffer();
        String line2 = null;
        BufferedReader reader2 = new BufferedReader(new InputStreamReader(is2));
        while ( (line2 = reader2.readLine()) != null){
            data2.append(line2);data2.append(System.getProperty("line.separator"));
        }
        //System.out.println(data2.toString());
        String tab = data2.toString();
        tab = tab.substring(tab.indexOf("<pre>")+5,tab.indexOf("</pre>"));
        //tab = "<html><body>"+tab+"</body></html>";
        //System.out.println(tab);
        song.setTablature(tab);
    }

    public Artist getArtist(Song song) {
        Artist artist = null;
        artist = song.getArtist();
        if (artist != null)
            return artist;
        
        return artist;
    }
    
}
