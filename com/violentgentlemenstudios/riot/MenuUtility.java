package com.violentgentlemenstudios.riot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class MenuUtility {
    private static GameCanvas gameCanvas = null;
    
    private static int menuSelection = 0;
    private static long lastMenuChange = 0;
    
    private static final Font MENU_FONT = new Font( "Times New Roman", Font.BOLD, 108 );
    private static final Font MNUSELECT_FONT = new Font( "Times New Roman", Font.BOLD, 48 );
    private static final Color COLOR_TITLE = new Color( 174, 19, 19 );
    
    //TODO: Make exstensible.
    public static void processMenu( boolean[] keys ) {
        if ( ( lastMenuChange + 150 ) < System.currentTimeMillis() ) {
            if ( keys[2] ) {
                menuSelection--;
                if ( menuSelection < 0 ) { menuSelection = 0; }
            } else if ( keys[3] ) {
                menuSelection++;
                if ( menuSelection > 3 ) { menuSelection = 3; }
            }
            if ( keys[4] ) {
                switch ( menuSelection ) {
                    case 0:
                        gameCanvas.gameState = GameState.GAME;
                        clearInstance();
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        System.exit( 0 );
                        break;
                    default:
                        break;
                }
            }
            lastMenuChange = System.currentTimeMillis();
        }
    }
    
    public static void drawMenu( MenuType menu, Graphics graphics ) {
        switch( menu ) {
            case MAIN:
                graphics.setFont( MENU_FONT );
                graphics.setColor( Color.BLACK );
                graphics.fillRect( 0, 0, 1000, 600 );

                graphics.drawImage( ResourceManager.getImage( "MENU_BACKDROP" ) , 0, 386, null );

                graphics.setColor( COLOR_TITLE );
                graphics.drawString( "RIOT:", 344, 100 );
                graphics.drawString( "Total Anarchy", 162, 200 );

                graphics.setFont( MNUSELECT_FONT );
                graphics.setColor( Color.WHITE );
                graphics.drawString( "START", 420, 275 );
                graphics.drawString( "CONTINUE", 370, 350 );
                graphics.drawString( "OPTIONS", 392, 425 );
                graphics.drawString( "QUIT", 438, 500 );

                graphics.drawImage( ResourceManager.getImage( "MENU_CURSOR" ) , 300, 275 + (menuSelection * 75) - 35, null );
                break;
            default:
                break;
        }
    }
    
    public static void setGameCanvas( GameCanvas gameCanvas ) {
        MenuUtility.gameCanvas = gameCanvas;
    }
    
    public static void clearInstance() {
        menuSelection = 0;
        lastMenuChange = 0;
    }
}
