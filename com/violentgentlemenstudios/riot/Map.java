package com.violentgentlemenstudios.riot;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

public class Map {
    private byte[][][] tiles = null;
    private short xTiles = 0;
    private short yTiles = 0;
    private Point location = null;
    
    public static final int MAP_SIZE = 1024;
    public static final int MAP_LAYERS = 3;
    public static final short TILE_SIZE = 64;
    
    public Map(byte[][][] tiles){
        this.tiles = tiles;
    }
    
    public Map(){
        this(new byte[MAP_LAYERS][MAP_SIZE][MAP_SIZE]);
    }
    
    public void initializeGraphics(Dimension drawableArea){
        xTiles = (short) Math.ceil((drawableArea.width  / TILE_SIZE) + 2);
        yTiles = (short) Math.ceil((drawableArea.height / TILE_SIZE) + 2);
    }
    
    public void setTiles(byte[][][] tiles){
        this.tiles = tiles;
    }
    
    public Point getLocation(){
        return location;
    }
    
    public void drawMap(Graphics g, short mapx_draw, short mapy_draw){
        if(mapx_draw >= 0 && mapy_draw >= 0){
            location = new Point(mapx_draw, mapy_draw);
            short mapx = (short) (mapx_draw / TILE_SIZE);
            short mapy = (short) (mapy_draw / TILE_SIZE);

            short mapx_off = (short) (mapx_draw & (TILE_SIZE-1));
            short mapy_off = (short) (mapy_draw & (TILE_SIZE-1));
            for(short l = 0; l < MAP_LAYERS; ++l) {
                for(short t = 0; t < xTiles; ++t) {
                    for(short i = 0; i < yTiles; ++i) {  
                        g.drawImage(TileDataStore.getTile(tiles[l][mapx+t][mapy+i]).getSprite(),
                            t*TILE_SIZE-mapx_off, i*TILE_SIZE-mapy_off, null);
                    }
                }
            }
        }
    }
    
    public byte getIdAt(int x, int y, int layer) {
        return tiles[layer][x/TILE_SIZE][y/TILE_SIZE];
    }
    
    public Tile getTileAt(int x, int y, int layer) {
        return TileDataStore.getTile(getIdAt(x, y, layer));
    }
}