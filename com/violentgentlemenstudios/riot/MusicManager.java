package com.violentgentlemenstudios.riot;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import javax.media.*;

public class MusicManager {
    private static final HashMap<String,URL> songs = new HashMap<>();
    private static final String INDEX_LOCATION = "res/music/musicindex.properties";
    private static final Class thisClass = com.violentgentlemenstudios.riot.MusicManager.class;
    
    private static final ArrayList<MusicPlayerThread> musicThreads = new ArrayList<>();
    
    public MusicManager() {
    
    }
    
    public static void loadFromIndex(){
        Properties properties = new Properties();
        try{
            properties.load( thisClass.getResourceAsStream( INDEX_LOCATION ) );
        }catch(Exception ex){
            ex.printStackTrace();
        }
        Set<String> keys = properties.stringPropertyNames();
        for(String key : keys){
            loadSong( key, properties.getProperty( key ) );
        }
    }
    
    public static void songPlay( String key ) {
        MusicPlayerThread thread = new MusicPlayerThread( songs.get( key ) );
        thread.start();
        musicThreads.add( thread );
    }
    
    public static void songStopAll() {
        for ( MusicPlayerThread thread : musicThreads ) {
            thread.songStop();
            musicThreads.remove( thread );
        }
    }
    
    public static void loadSong( String key, String filename ) {
        URL songURL = null;
        try {
            songURL = thisClass.getResource( "res/music/" + filename ).toURI().toURL();
        } catch (Exception e) { 
            System.err.println( "Unable to load: res/music/" + filename );
            e.printStackTrace();
        }
        songs.put( key, songURL );
    }
    
    private static class MusicPlayerThread extends Thread {
        private URL songURL = null;
        private MediaLocator mediaLocator = null;
        private Player mediaPlayer = null;
        
        public MusicPlayerThread( URL songURL ) {
            this.songURL = songURL;
        }
        
        public void run() {
            try{
                mediaLocator = new MediaLocator( songURL );     
                mediaPlayer = Manager.createPlayer( mediaLocator );
            } catch(Exception ex) {
                ex.printStackTrace();
            }

            mediaPlayer.addControllerListener(new ControllerListener() {
                public void controllerUpdate( ControllerEvent e ) {
                    if (e instanceof EndOfMediaEvent) {
                        mediaPlayer.stop();
                        mediaPlayer.close();
                    }
                }
            });

            mediaPlayer.realize();
            mediaPlayer.start();
        }
        
        public void songStop() {
            mediaPlayer.stop();
        }
    }
}
