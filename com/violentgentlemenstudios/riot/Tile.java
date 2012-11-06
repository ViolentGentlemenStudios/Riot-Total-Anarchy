package com.violentgentlemenstudios.riot;

import java.awt.Image;

public class Tile{
    private boolean isPassable = true;
    private Image sprite = null;

    public Tile(Image sprite){
        this.sprite = sprite;
    }

    public Image getSprite(){
        return sprite;
    }
}