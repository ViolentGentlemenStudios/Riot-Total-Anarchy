package com.violentgentlemenstudios.riot;

import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;

public class EntityWithGravity extends Entity {
    protected int gravityVelocity = 0;
    
    public EntityWithGravity( Point location, Direction direction, int speed, Image sprite ){
        super( location, direction, speed, sprite );
        this.x = location.x;
        this.y = location.y;
        this.direction = direction;
        this.sprite = new ImageIcon(sprite);
        this.speed = speed;
        
        this.distanceOffset = sprite.getWidth(null)/2;
    }
    
    public void update() {
        if ( y < 400 ) { //Yay shitty gravity
            gravityVelocity += 2;
            setDirection( Direction.DOWN );
            move( gravityVelocity );
        } else {
            y = 400;
            gravityVelocity = 0;
        }
    }
}
