package com.violentgentlemenstudios.riot;

import java.awt.Image;
import java.awt.Point;

public class EntityWithGravity extends Entity {
    protected int gravityVelocity = 0;
    
    public EntityWithGravity( Point location, Direction direction, Image sprite ){
        super( location, direction, sprite );
    }
    
    public void update() {
        super.update();
        setDirection( Direction.DOWN );
        move( gravityVelocity );
        
        if ( boundingBox.getMaxY() < 400 ) { //Yay shitty gravity
            gravityVelocity += 2; 
        } else {
            y = 400 - h;
            gravityVelocity = 0;
        }
    }
}
