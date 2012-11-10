package com.violentgentlemenstudios.riot;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import javax.swing.Timer;

public class GameCanvas extends Canvas {
    private boolean repaintInProgress = false;
    private GameState gameState = GameState.INTRO;
    protected final boolean[] keys = new boolean[5];
    
    private final Font MENU_FONT = new Font( "Times New Roman", Font.BOLD, 108 );
    private final Font MNUSELECT_FONT = new Font( "Times New Roman", Font.BOLD, 48 );
    private final Color COLOR_TITLE = new Color( 174, 19, 19 );
    private float introFade = 0f;
    private boolean fadingIn = true;
    private int menuSelection = 0;
    private long lastMenuChange = 0;
    
    public GameCanvas() {
        setFocusable( false );
        setIgnoreRepaint( true );
        Chrono chrono = new Chrono( this );
        new Timer( 16, chrono ).start();
    }

    public void processFrame() {
        switch ( gameState ) {
            case INTRO:
                introFade += ( fadingIn ? 0.01 : -0.005 );
                if ( introFade <= 0f ) {
                    gameState = GameState.MAIN_MENU;
                    MusicManager.songPlay( "MENU_SONG" );
                } else if ( introFade >= 1 ) {
                    fadingIn = false;
                }
                introFade = (float) ( Math.round( introFade * 1000f ) / 1000f );
                break;
            case MAIN_MENU:
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
                                gameState = GameState.GAME;
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
                break;
            default:
                break;
        }
    }
    
    public void myRepaint() {
        if ( repaintInProgress ) { return; }
        repaintInProgress = true;
        
        processFrame();
        
        BufferStrategy strategy = getBufferStrategy();
        Graphics2D graphics = (Graphics2D) strategy.getDrawGraphics();
        graphics.setRenderingHint( RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON );
        
        graphics.setColor( Color.WHITE );
        graphics.fillRect( 0, 0, getWidth(), getHeight() );
        
        // Do Graphics
        switch ( gameState ) {
            case INTRO:
                graphics.setColor( Color.BLACK );
                graphics.fillRect( 0, 0, 1000, 600 );
                graphics.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, introFade ) );
                graphics.drawImage( ResourceManager.getImage( "MENU_LOGO_STUDIO" ), 290, 90, null );
                break;
            case MAIN_MENU:
                //System.out.println(graphics.getFontMetrics( MNUSELECT_FONT ).getStringBounds( "OPTIONS", graphics ).getWidth());
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
            case GAME:
                break;
            default:
                break;
        }
        
        if(graphics != null) { graphics.dispose(); }
        strategy.show();
        Toolkit.getDefaultToolkit().sync();

        repaintInProgress = false;
    }
    
    public class Chrono implements ActionListener {
	private GameCanvas gc = null;

	public Chrono(GameCanvas gc) {
            this.gc = gc;
	}

	public void actionPerformed( ActionEvent arg0 ) {
            gc.myRepaint();
	}
    }

}
