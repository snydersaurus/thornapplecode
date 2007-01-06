/*
 * SongComparator.java
 *
 * Created on January 1, 2007, 10:06 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.setmanager;

import java.util.Comparator;

/**
 *
 * @author Bill
 */
public class SongComparator implements Comparator {
  
    public int compare(Object a, Object b) {
        Song songA = (Song)a;
        Song songB = (Song)b;
        
        //initially sort by cost, lower is more important
        String songAValue = songA.getName();
        String songBValue = songB.getName();
        
        return songAValue.compareTo(songBValue);
    }
    
}
