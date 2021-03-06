package com.violentgentlemenstudios.riot;

import java.awt.Image;
import java.awt.Point;

public class EntityWithGravity extends Entity {
    protected int gravityVelocity = 0;
    
    public EntityWithGravity(Point location, Image sprite){
        super(location, sprite);
    }
    
    public void update() {
        super.update();
        setDirection(Direction.DOWN);
        move(gravityVelocity);
        
        Tile belowTile = GameCanvas.getMap().getTileAt((int) boundingBox.getMaxX(),
                (int) boundingBox.getMaxY(), 1);
        if (!belowTile.collides()) {
            gravityVelocity += (gravityVelocity < 24 ? 2 : 0); 
        } else if (gravityVelocity > 0) {
            setLocation(new Point(x, (y + Map.TILE_SIZE - (int)(boundingBox.getMaxY() % Map.TILE_SIZE))));
            gravityVelocity = 0;
        }
    }
}
