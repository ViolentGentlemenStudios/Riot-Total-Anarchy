package com.violentgentlemenstudios.riot;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Set;

public class EntityManager {
    protected static HashMap<Integer,Entity> entities = new HashMap<>();
    
    public static int addEntity(Entity entity){
        return addEntity(entity, -1);
    }
    
    public static int addEntity(Entity entity, int index){
        if(index == -1){
            Set<Integer> keys = entities.keySet();
            for(Integer key : keys){
                if(key > (index + 1)){
                   break; 
                }
                index = key;
            }
            index += 1;
        }
        entities.put(index, entity);
        return index;
    }
    
    public static void removeEntity(int index){
        entities.remove(index);
    }
    
    public static void drawEntities(Graphics g, Point offset){
        for(Entity entity : entities.values()){
            entity.draw(g, offset);
        }
    }
    
    public static void updateEntities(){
        for(Entity entity : entities.values()){
            entity.update();
        }
    }
    
    public static Entity getEntity(int uid){
        return entities.get(uid);
    }
    public static HashMap<Integer,Entity> getAllEntities(){
        return entities;
    } 
    
    public static void moveEntity( Direction direction, int uid, int speed ){
        getEntity( uid ).setDirection( direction );
        getEntity( uid ).move( speed );
    }
}