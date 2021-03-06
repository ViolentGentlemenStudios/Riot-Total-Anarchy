package com.violentgentlemenstudios.riot;

//import java.awt.Image;
//import java.util.HashMap;

public class TileDataStore {
    //private static HashMap<Byte,Tile> tiles = new HashMap<>();
    private static Tile[] tiles = {
        new Tile(ResourceManager.getImage("TILE_CLEAR"), false),
        new Tile(ResourceManager.getImage("TILE_TEST"), false),
        new Tile(ResourceManager.getImage("TILE_GRASS"), true)
    };
    
    /*public static void addTile(byte id, Image sprite){
        tiles.put(id, new Tile(sprite));
    }*/
    
    public static Tile getTile(byte id){
        return tiles[id];
    }
}