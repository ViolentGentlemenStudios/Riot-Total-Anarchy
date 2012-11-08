package com.violentgentlemenstudios.riot;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import javax.imageio.ImageIO;

public class ResourceManager {
    private final static HashMap<String,BufferedImage> images = new HashMap<>();
    private final static String INDEX_LOCATION = "res/imageindex.properties";
    private final static Class thisClass = com.violentgentlemenstudios.riot.ResourceManager.class;
    
    public ResourceManager() {
    
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
            loadImage( key, properties.getProperty( key ) );
        }
    }
    
    public static Image getImage( String key ){
        return (Image) images.get( key );
    }
    
    public static void loadImage( String key, String filename ){
        BufferedImage img = null;
        try {
            img = ImageIO.read( thisClass.getResource("res/"+filename) );
        } catch (Exception e) { 
            System.err.println( "Unable to load: res/" + filename );
            e.printStackTrace();
        }
        images.put( key, img );
    }
}