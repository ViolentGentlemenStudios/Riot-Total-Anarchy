package com.violentgentlemenstudios.riot;

import java.io.File;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Properties;

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
                for (int u = 0; u < Map.MAP_SIZE; u++) {
                    for ( int i = 0; i < Map.MAP_SIZE; i++ ) {
                        layerFinal[u][i] = layerBytes[(i * Map.MAP_SIZE) + u];
                    }
                }
                mapData[t] = layerFinal;
            }
            
            Properties metaData = new Properties();
            metaData.load(thisClass.getResourceAsStream(LEVEL_FOLDER + levelName + MDATA_SUFFIX));
            
        } catch ( Exception ex ) {
            ex.printStackTrace();
        }
        map.setTiles( mapData );        
        return map;
    }
}
