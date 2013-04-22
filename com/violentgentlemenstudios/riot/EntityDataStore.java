package com.violentgentlemenstudios.riot;

import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;

public class EntityDataStore {
    private static final HashMap<String,Object[]> entities = new HashMap<>();
    
    static {
        entities.put("player", new Object[]{EntityWithJump.class, "PLAYER"});
        entities.put("goomba", new Object[]{EntityGoomba.class,   "GOOMBA"});
    }
    
    public static Entity makeEntity(String name, Point location){
        try {
            return (Entity)((Class)(entities.get(name)[0])).getConstructor(Point.class, Image.class)
                    .newInstance(location, ResourceManager.getImage((String)entities.get(name)[1]));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
