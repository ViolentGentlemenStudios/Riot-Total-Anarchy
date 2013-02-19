package com.violentgentlemenstudios.riot;

import java.awt.Image;

public class Tile{
    private boolean collides = false;
    private Image sprite = null;

    public Tile(Image sprite, boolean collides){
        this.sprite = sprite;
        this.collides = collides;
    }

    public Image getSprite(){
        return sprite;
    }
    
    public boolean collides() {
        return collides;
    }
}