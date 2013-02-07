package com.violentgentlemenstudios.riot;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;

public class Entity {
    protected int x = 0;
    protected int y = 0;
    protected Direction direction = Direction.LEFT;
    protected ImageIcon sprite = null;
    
    protected int w = 0;
    protected int h = 0;
    
    protected int[] boundingBox = new int[4]; //Top, Right, Bottom, Left
    protected int distanceOffset = 0;
    
    public Entity(Point location, Direction direction, Image sprite){
        setLocation(location);
        setDirection(direction);
        setSprite(sprite);
        
        this.distanceOffset = sprite.getWidth(null)/2;
    }
    
    public void setDirection(Direction direction){
        this.direction = direction;
    }
    public Direction getDirection(){
        return direction;
    }
    
    public void setLocation(Point location){
        x = location.x;
        y = location.y;
        
        boundingBox[0] = y;
        boundingBox[1] = x + w;
        boundingBox[2] = y + h;
        boundingBox[3] = x;
    }
    public Point getLocation(){
        return new Point(x,y);
    }
    
    public void setSprite(Image sprite){
        this.sprite = new ImageIcon(sprite);
        this.distanceOffset = sprite.getWidth(null)/2;
        
        this.w = sprite.getWidth(null);
        this.h = sprite.getHeight(null);
    }
    public Image getSprite(){
        return sprite.getImage();
    }
    
    public int getDistanceOffset(){
        return distanceOffset;
    }

    public void move( int distance ){
        switch ( direction ) {
            case UP:
                setLocation( new Point( x , y - distance ) );
                break;
            case DOWN:
                setLocation( new Point( x , y + distance ) );
                break;
            case LEFT:
                setLocation( new Point( x - distance , y ) );
                break;
            case RIGHT:
                setLocation( new Point( x + distance , y ) );
                break;
        }
    }
    
    public void update(){}

    public void draw(Graphics gfx, Point offset) {
        gfx.drawImage( getSprite(), x, y, null );
    }
}