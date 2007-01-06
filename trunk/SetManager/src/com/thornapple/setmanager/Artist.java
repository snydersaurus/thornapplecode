/*
 * Artist.java
 *
 * Created on September 16, 2006, 10:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.setmanager;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Bill
 *
 */
@Entity
@Table(name = "ARTIST")
public class Artist implements java.io.Serializable, Comparable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private long id;
    private String name;
    @OneToMany(mappedBy="artist", cascade=CascadeType.ALL)
    private List<Song> songs = new ArrayList();
    @Column(name="CGR_ID")
    private String cgrID;

    
    public Artist() {
    }
    
    public Artist(String name) {
        this.name = name;
    }
    
    public String getCgrID() {
        return cgrID;
    }

    public void setCgrID(String cgrID) {
        this.cgrID = cgrID;
    }
    
    public List getSongs() {
        return songs;
    }
    
    public void setSongs(List songs) {
        this.songs = songs;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void addSong(Song song) {
        song.setArtist(this);
        songs.add(song);
    }
    
    public void removeSong(Song song) {
        songs.remove(song);
        song.setArtist(null);
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String toString() {
        return this.name;
    }

    public void addSongs(List<Song> songs) {
        for (Song song : songs) {
            addSong(song);
        }
    }

    public int compareTo(Object o) {
        if (o instanceof Artist){
            Artist a = (Artist)o;
            if (getName() != null){
                return getName().compareTo(a.getName());
            } else if (a.getName() != null){
                return -1;
            } else
                return 0;
                
        } else
            return -1;
    }

    public void clearSongs() {
        for (Song song : songs) {
            song.setArtist(null);
        }
        songs.clear();
    }
    
    
}
