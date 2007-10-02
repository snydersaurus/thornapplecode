/*
 * Set.java
 *
 * Created on January 13, 2007, 8:17 PM
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 *
 * @author Bill
 */
@Entity
@Table(name = "SONGSET")
public class SongSet implements java.io.Serializable, Comparable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ID")
    private Long id;
    private String name; 
    private String gigDate;
    
    @ManyToMany(
        targetEntity=Song.class,
        cascade={CascadeType.PERSIST, CascadeType.MERGE}
    )
    @JoinTable(
        name="SONGSET_SONG",
        joinColumns={@JoinColumn(name="SONGSET_ID")},
        inverseJoinColumns={@JoinColumn(name="SONG_ID")}
    )
    private List<Song> songs = new ArrayList();
    
    /** Creates a new instance of Set */
    public SongSet() {
    }
    
    public SongSet(String name, String gigDate) {
        this.name = name;
        this.gigDate = gigDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public void addSong(Song song){
        this.songs.add(song);
    }
    
    public void removeSong(Song song){
        this.songs.remove(song);
    }
    
    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public List<Song> getSongs() {
        return songs;
    }
    
    public String getGigDate() {
        return gigDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setGigDate(String gigDate) {
        this.gigDate = gigDate;
    }
    
    public String toString(){
        return getName();
    }
    
    public int compareTo(Object o) {
        if (o instanceof SongSet){
            SongSet a = (SongSet)o;
            if (getName() != null){
                return getName().compareTo(a.getName());
            } else if (a.getName() != null){
                return -1;
            } else
                return 0;
            
        } else
            return -1;
    }

    public void addSongs(List<Song> songs) {
        for (Song song : songs) {
            addSong(song);
        }
    }
    
}
