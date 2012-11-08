package com.violentgentlemenstudios.riot;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import javax.imageio.ImageIO;

public class ResourceManager {
    private HashMap<String,BufferedImage> images = new HashMap<>();
    private final String INDEX_LOCATION = "res/imageindex.properties";
    private static ResourceManager instance = new ResourceManager();
    
    private ResourceManager(){}
    
    public static ResourceManager getInstance(){
        return instance;
    }
    
    public void loadFromIndex(){
        Properties properties = new Properties();
        try{
            properties.load(getClass().getResourceAsStream(INDEX_LOCATION));
        }catch(Exception ex){
            ex.printStackTrace();
        }
        Set<String> keys = properties.stringPropertyNames();
        for(String key : keys){
            loadImage(key, properties.getProperty(key));
        }
    }
    
    public Image getImage(String key){
        return (Image)images.get(key);
    }
    
    public void loadImage(String key, String filename){
        BufferedImage img = null;
        try {
            img = ImageIO.read(getClass().getResource("res/"+filename));
        } catch (Exception e) { 
            System.err.println("Unable to load: res/"+filename);
            e.printStackTrace();
        }
        images.put(key, img);
    }
}