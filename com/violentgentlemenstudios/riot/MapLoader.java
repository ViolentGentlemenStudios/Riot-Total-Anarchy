package com.violentgentlemenstudios.riot;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;

public class MapLoader {
    private static final String LEVEL_FOLDER = "res/lvl/";
    private static final String LEVEL_SUFFIX = ".lvl";
    private static final String MDATA_SUFFIX = ".rta";
    private static final Class thisClass = com.violentgentlemenstudios.riot.MapLoader.class;
    
    public static Map loadMap( String levelName ) {
        Map map = new Map();
        byte[][][] mapData = new byte[Map.MAP_LAYERS][Map.MAP_SIZE][Map.MAP_SIZE];
        int squareLayerSize = (int) Math.pow( Map.MAP_SIZE, 2 );
        try {
            File levelFile = new File( thisClass.getResource( LEVEL_FOLDER + levelName + LEVEL_SUFFIX ).toURI() );
            byte[] fileBytes = Files.readAllBytes(levelFile.toPath());
            for ( int t = 0; t < Map.MAP_LAYERS; t++ ) {
                byte[] layerBytes = Arrays.copyOfRange( fileBytes, t * squareLayerSize, ( t + 1 ) * squareLayerSize );
                byte[][] layerFinal = new byte[Map.MAP_SIZE][Map.MAP_SIZE];
                for ( int i = 0; i < Map.MAP_SIZE; i++ ) {
                    layerFinal[i] = Arrays.copyOfRange( layerBytes, i * Map.MAP_SIZE, ( i + 1 ) * Map.MAP_SIZE );
                }
                mapData[t] = layerFinal;
            }
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
        map.setTiles( mapData );        
        return map;
    }
}
