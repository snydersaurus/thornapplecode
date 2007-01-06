/*
 * MatcherFactory.java
 *
 * Created on August 12, 2006, 4:23 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.thornapple.setmanager;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.TransformedList;
import ca.odell.glazedlists.UniqueList;
import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import ca.odell.glazedlists.matchers.CompositeMatcherEditor;
import ca.odell.glazedlists.matchers.Matcher;
import ca.odell.glazedlists.matchers.MatcherEditor;
import ca.odell.glazedlists.swing.EventListModel;
import ca.odell.glazedlists.swing.EventSelectionModel;
import ca.odell.glazedlists.swing.TextComponentMatcherEditor;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.JTextComponent;

/**
 *
 * @author Bill
 */
public class MatcherFactory {
    
    private static MatcherFactory instance;
    
    /** Creates a new instance of MatcherFactory */
    private MatcherFactory() {
    }
    
    public synchronized static MatcherFactory getInstance(){
        if (instance == null)
            instance = new MatcherFactory();
        
        return instance;
    }
    
    public MatcherEditor createMatcher(EventList source, SongBrowseForm panel){
        
        
        TextComponentMatcherEditor songTitleMatcher =
                new TextComponentMatcherEditor(panel.getSongTitleComponent(),new SongTitleFilter());
        
        TextComponentMatcherEditor artistTitleMatcher =
                new TextComponentMatcherEditor(panel.getArtistTitleComponent(),new ArtistNameFilter());
        
        FilterList filteredSource = 
                new FilterList(source,songTitleMatcher);

        ArtistsSelectMatcher artistsMatcher =
                new ArtistsSelectMatcher(filteredSource,
                    panel.getArtistListComponent(),
                    panel.getArtistTitleComponent());
        
        EventList matchers = new BasicEventList();
        matchers.add(songTitleMatcher);
        matchers.add(artistTitleMatcher);
        matchers.add(artistsMatcher);
        
        CompositeMatcherEditor matcher =
                new CompositeMatcherEditor(matchers);
        matcher.setMode(CompositeMatcherEditor.AND);
        
        
        return matcher;
    }
    
    private class SongTitleFilter implements TextFilterator {
        
        public void getFilterStrings(List baseList, Object element) {
            Song song = (Song)element;
            baseList.add(song.getName());
        }
        
    }
    
    private class ArtistNameFilter implements TextFilterator {
        
        public void getFilterStrings(List baseList, Object element) {
            if (element instanceof Song){
                Song song = (Song)element;
                baseList.add(song.getArtist().getName());
            } else if (element instanceof Artist){
                Artist artist = (Artist)element;
                baseList.add(artist.getName());
            }
                
        }
        
    }
    
    class ArtistsForSongsMatcher implements Matcher {
        
        /** the users to match */
        private Set artists = new HashSet();
        
        /**
         * Create a new {@link IssuesForUsersMatcher} that matches only
         * {@link Issue}s that have one or more user in the specified list.
         */
        public ArtistsForSongsMatcher(Collection artists) {
            // make a defensive copy of the users
            this.artists.addAll(artists);
        }
        
        /**
         * Test whether to include or not include the specified issue based
         * on whether or not their user is selected.
         */
        public boolean matches(Object o) {
            if(o == null) return false;
            if(artists.isEmpty()) return true;
            
            Song song = (Song)o;
            return artists.contains(song.getArtist());
        }
    }
    
    class ArtistsSelectMatcher extends AbstractMatcherEditor implements ListSelectionListener {
        
        /** a list of labels */
        EventList artistsEventList;
        EventList artistsSelectedList;
        FilterList filteredArtists;
        
        public ArtistsSelectMatcher(EventList source, JList list, JTextComponent text) {
            EventList artistsNonUnique = new SongsToArtistsList(source);
            artistsEventList = new UniqueList(artistsNonUnique);
            
            //filter this by the artist too!
            filteredArtists =
                    new FilterList(artistsEventList,
                    new TextComponentMatcherEditor(text,new ArtistNameFilter()));
            
            // create a JList that contains users
            EventListModel artistsListModel = new EventListModel(filteredArtists);
            list.setModel(artistsListModel);
            
            // create an EventList containing the JList's selection
            EventSelectionModel artistsSelectionModel = new EventSelectionModel(artistsEventList);
            list.setSelectionModel(artistsSelectionModel);
            artistsSelectedList = artistsSelectionModel.getSelected();
            
            // handle changes to the list's selection
            list.addListSelectionListener(this);
            
        }
        
        
        /**
         * When the JList selection changes, create a new Matcher and fire
         * an event.
         */
        public void valueChanged(ListSelectionEvent e) {
            Matcher newMatcher = new ArtistsForSongsMatcher(artistsSelectedList);
            fireChanged(newMatcher);
            
        }
    }
    
    class SongsToArtistsList extends TransformedList {
        
        /**
         * Construct an IssuesToUserList from an EventList that contains only
         * Issue objects.
         */
        public SongsToArtistsList(EventList source) {
            super(source);
            source.addListEventListener(this);
        }
        
        /**
         * Gets the user at the specified index.
         */
        public Object get(int index) {
            Song song = (Song)source.get(index);
            return song.getArtist();
        }
        
        /**
         * When the source issues list changes, propogate the exact same changes
         * for the users list.
         */
        public void listChanged(ListEvent listChanges) {
            updates.forwardEvent(listChanges);
        }
    }
}
