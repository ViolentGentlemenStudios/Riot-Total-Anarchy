package com.violentgentlemenstudios.riot;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import javax.swing.ImageIcon;

public class Entity {
    protected int x = 0;
    protected int y = 0;
    protected Direction direction = Direction.LEFT;
    protected int speed = 0;
    protected ImageIcon sprite = null;
    
    protected int distanceOffset = 0;
    
    public Entity(Point location, Direction direction, int speed, Image sprite){
        this.x = location.x;
        this.y = location.y;
        this.direction = direction;
        this.sprite = new ImageIcon(sprite);
        this.speed = speed;
        
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
    }
    public Point getLocation(){
        return new Point(x,y);
    }
    
    public void setSpeed(int speed){
        this.speed = speed;
    }
    public int getSpeed(){
        return speed;
    }
    
    public void setSprite(Image sprite){
        this.sprite = new ImageIcon(sprite);
        this.distanceOffset = sprite.getWidth(null)/2;
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