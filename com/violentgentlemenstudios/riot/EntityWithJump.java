package com.violentgentlemenstudios.riot;

import java.awt.Image;
import java.awt.Point;

public class EntityWithJump extends EntityWithGravity {
    protected final int DEFAULT_JUMP = 25; // Must be odd, or else velocity = 0 at top
    
    public EntityWithJump( Point location, Direction direction, Image sprite ){
        super( location, direction, sprite );
    }
    
    public void update() {
        super.update();
    }
    
    public void jump() {
        jump( DEFAULT_JUMP );
    }
    
    public void jump( int force ) {
        if ( gravityVelocity == 0 || Cheats.INFINITE_JUMP ) {
            gravityVelocity = -force;
        }
    }
    
}
