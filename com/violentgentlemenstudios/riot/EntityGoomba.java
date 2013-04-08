package com.violentgentlemenstudios.riot;

import java.awt.Image;
import java.awt.Point;

public class EntityGoomba extends EntityWithGravity {
    
    public EntityGoomba( Point location, Direction direction, Image sprite ) {
        super( location, direction, sprite );
    }
    
    public void update() {
        super.update();
        setDirection( Direction.LEFT );
        move( 1 );
    }
    
    public void onPlayerCollide() {
        super.onPlayerCollide();
        if (verticalCollision(EntityManager.getPlayer()) == Direction.UP){
            EntityManager.removeEntity(id);
        }
    }
}
