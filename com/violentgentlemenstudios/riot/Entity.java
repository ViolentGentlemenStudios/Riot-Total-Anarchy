package com.violentgentlemenstudios.riot;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Entity {
    protected int id = 0;
    protected int x = 0;
    protected int y = 0;
    protected Direction direction = Direction.LEFT;
    protected ImageIcon sprite = null;
    
    protected int w = 0;
    protected int h = 0;
    
    protected Rectangle boundingBox = new Rectangle(); //Top, Right, Bottom, Left
    protected int distanceOffsetX = 0;
    protected int distanceOffsetY = 0;
    
    public Entity(Point location, Image sprite){
        setLocation(location);
        setDirection(Direction.UP);
        setSprite(sprite);
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
        
        boundingBox.setLocation(location);
        boundingBox.setSize(w, h);
    }
    
    public Rectangle getBoundingBox() {
        return boundingBox;
    }
    
    public Point getLocation(){
        return new Point(x,y);
    }
    
    public Point getCenter() {
        return new Point(x + (w / 2), y + (h / 2));
    }
    
    public void setSprite(Image sprite){
        this.sprite = new ImageIcon(sprite);
        this.distanceOffsetX = sprite.getWidth(null)/2;
        this.distanceOffsetY = sprite.getHeight(null)/2;
        
        this.w = sprite.getWidth(null);
        this.h = sprite.getHeight(null);
    }
    public Image getSprite(){
        return sprite.getImage();
    }
    
    public int getDistanceOffsetX(){
        return distanceOffsetX;
    }
    
    public int getDistanceOffsetY(){
        return distanceOffsetY;
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
        if (x < 0 || y < 0) {
            kill();
        }
    }
    
    public void interact() {}
    
    public void kill() {
        onDeath();
        EntityManager.removeEntity(id);
    }
    
    public boolean collided(Entity entity) {
        return entity.getBoundingBox().intersects(boundingBox);
    }
    
    public Direction horizontalCollision(Entity entity) {
        Rectangle intersection = entity.getBoundingBox().intersection(boundingBox);
        if (collided(entity)) {
            if ((x+(w/2))<(intersection.x+(intersection.width /2))) { return Direction.RIGHT; }
            if ((x+(w/2))>(intersection.x+(intersection.width /2))) { return Direction.LEFT;  }
        }
        return null;
    }
    
    public Direction verticalCollision(Entity entity) {
        Rectangle intersection = entity.getBoundingBox().intersection(boundingBox);
        if (collided(entity)) {
            if ((y+(h/2))<(intersection.y+(intersection.height/2))) { return Direction.DOWN;  }
            if ((y+(h/2))>(intersection.y+(intersection.height/2))) { return Direction.UP;    }
        }
        return null;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public int getId() { return id; }
    
    public void update() {
        if (collided(EntityManager.getPlayer())) {
            if (GameCanvas.getKeys()[4]) { interact(); }
            onPlayerCollide();
        }
    }

    public void draw(Graphics gfx, Point offset) {
        gfx.drawImage( getSprite(), getLocation().x - offset.x,
                getLocation().y - offset.y, null );
    }
    
    /* Events */
    public void onDeath() {}
    public void onPlayerCollide() {}
}