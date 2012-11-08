package com.violentgentlemenstudios.riot;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;

public class Map {
    private byte[][][] tiles = null;
    private short xTiles = 0;
    private short yTiles = 0;
    private Point location = null;
    
    public static final int MAP_SIZE = 2048;
    public static final int MAP_LAYERS = 3;
    public static final short TILE_SIZE = 64;
    
    public Map(byte[][][] tiles){
        this.tiles = tiles;
    }
    
    public Map(){
        this(new byte[MAP_SIZE][MAP_SIZE][MAP_LAYERS]);
    }
    
    public void initializeGraphics(){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        xTiles = (short) Math.ceil(screenSize.width  / TILE_SIZE+2);
        yTiles = (short) Math.ceil(screenSize.height / TILE_SIZE+2);
    }
    
    public void setTiles(byte[][][] tiles){
        this.tiles = tiles;
    }
    
    public Point getLocation(){
        return location;
    }
    
    public void drawMap(Graphics g, short mapx_draw, short mapy_draw){
        if(mapx_draw > 0 && mapy_draw > 0){
            location = new Point(mapx_draw, mapy_draw);
            short mapx = (short) (mapx_draw / TILE_SIZE);
            short mapy = (short) (mapy_draw / TILE_SIZE);

            short mapx_off = (short) (mapx_draw & (TILE_SIZE-1));
            short mapy_off = (short) (mapy_draw & (TILE_SIZE-1));

            for(short t = 0; t < xTiles; ++t){
                for(short i = 0; i < yTiles; ++i){
                    for( short l = 0; l < MAP_LAYERS; ++l ) {
                        g.drawImage(TileDataStore.getTile(tiles[mapx+t][mapy+i][l]).getSprite(),
                            t*TILE_SIZE-mapx_off, i*TILE_SIZE-mapy_off, null);
                    }
                }
            }
        }
    }
}