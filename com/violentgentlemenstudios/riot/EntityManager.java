package com.violentgentlemenstudios.riot;

import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Set;

public class EntityManager {
    protected static HashMap<Integer,Entity> entities = new HashMap<>();
    protected static int playerId = 0;
    
    public static int addEntity(Entity entity){
        return addEntity(entity, -1);
    }
    
    public static int addEntity(Entity entity, int id){
        if(id == -1){
            Set<Integer> keys = entities.keySet();
            for(Integer key : keys){
                if(key > (id + 1)){
                   break; 
                }
                id = key;
            }
            id += 1;
        }
        entities.put(id, entity);
        entity.setId(id);
        return id;
    }
    
    public static void removeEntity(int id){
        entities.remove(id);
        if (id == playerId) {
            // End GAME
        }
    }
    
    public static void drawEntities(Graphics g, short x, short y){
        Point offset = new Point(x, y);
        for(Entity entity : entities.values()){
            entity.draw(g, offset);
        }
    }
    
    public static void updateEntities(){
        for(Entity entity : entities.values()){
            entity.update();
        }
    }
    
    public static Entity getEntity(int id){
        return entities.get(id);
    }
    
    public static HashMap<Integer,Entity> getAllEntities(){
        return entities;
    } 
    
    public static Entity getPlayer() {
        return getEntity(playerId);
    }
    public static int getPlayerId() {
        return playerId;
    }
    public static void setPlayer(int playerId) {
        EntityManager.playerId = playerId;
    }
    
    public static void moveEntity( Direction direction, int id, int speed ){
        getEntity( id ).setDirection( direction );
        getEntity( id ).move( speed );
    }
}