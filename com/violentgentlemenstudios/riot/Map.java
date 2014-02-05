package com.violentgentlemenstudios.riot;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.HashMap;

public class Map {
    private byte[][][] tiles = null;
    private HashMap<String, String> metadata = new HashMap<>();
    private short xTiles = 0;
    private short yTiles = 0;
    private Point location = null;
    private Dimension drawableArea = new Dimension(1000, 600);
    
    public static final int MAP_SIZE = 1024;
    public static final int MAP_LAYERS = 3;
    public static final short TILE_SIZE = 64;
    
    public Map(byte[][][] tiles, HashMap<String, String> metadata){
        this.tiles = tiles;
        this.metadata = metadata;
    }
    
    public Map(){
        this(new byte[MAP_LAYERS][MAP_SIZE][MAP_SIZE], new HashMap<String, String>());
    }
    
    public void initializeGraphics(Dimension drawableArea){
        this.drawableArea = drawableArea;
    }
    
    public void setTiles(byte[][][] tiles){
        this.tiles = tiles;
    }
    
    public void setData(String key, String value) {
        metadata.put(key, value);
    }
    
    public Point getLocation(){
        return location;
    }
    
    public void drawMap(Graphics g, short mapx_draw, short mapy_draw) {
        drawMap(g, mapx_draw, mapy_draw, 1.0f);
    }
    
    public void drawMap(Graphics g, short mapx_draw, short mapy_draw, float scale){
        if(mapx_draw >= 0 && mapy_draw >= 0){
            short scaledTilesize = (short)(TILE_SIZE * scale);
            xTiles = (short) Math.ceil((drawableArea.width  / scaledTilesize) + 2);
            yTiles = (short) Math.ceil((drawableArea.height / scaledTilesize) + 2);
        
            location = new Point(mapx_draw, mapy_draw);
            short mapx = (short) (mapx_draw / scaledTilesize);
            short mapy = (short) (mapy_draw / scaledTilesize);

            short mapx_off = (short) (mapx_draw & (scaledTilesize-1));
            short mapy_off = (short) (mapy_draw & (scaledTilesize-1));
            
            for(short l = 0; l < MAP_LAYERS; ++l) {
                for(short t = 0; t < xTiles; ++t) {
                    for(short i = 0; i < yTiles; ++i) {  
                        Image sprite = TileDataStore.getTile(tiles[l][mapx+t][mapy+i]).getSprite();
                        if (scale != 1.0f) {
                            sprite = sprite.getScaledInstance((int) (sprite.getWidth(null) * scale), 
                                    (int) (sprite.getHeight(null) * scale), Image.SCALE_FAST);
                        }
                        g.drawImage(sprite, t*scaledTilesize-mapx_off, i*scaledTilesize-mapy_off, null);
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
    
    public String getDataValue(String key) {
        if (metadata.containsKey(key)) {
            return metadata.get(key);
        } else {
            return null;
        }
    }
}