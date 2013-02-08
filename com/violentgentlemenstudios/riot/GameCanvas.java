package com.violentgentlemenstudios.riot;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import javax.swing.Timer;

public class GameCanvas extends Canvas {
    private boolean repaintInProgress = false;
    protected GameState gameState = GameState.INTRO;
    protected final boolean[] keys = new boolean[5]; //Left, Right, Up, Down, Select
    protected Map gameMap = null;
    
    private float introFade = 0f;
    private boolean fadingIn = true;
    
    public GameCanvas() {
        MenuUtility.setGameCanvas(this);
        
        setFocusable(false);
        setIgnoreRepaint(true);
        Chrono chrono = new Chrono(this);
        new Timer(20, chrono).start();

        gameMap = MapLoader.loadMap("level1");
        gameMap.initializeGraphics();
    EntityManager.setPlayer(EntityManager.addEntity( new EntityWithJump( new Point( 300, 200) , Direction.UP, ResourceManager.getImage( "PLAYER" ) ) ));
        EntityManager.addEntity( new EntityGoomba( new Point( 800, 100) , Direction.LEFT, ResourceManager.getImage( "GOOMBA" ) ) );
    }
    
    public void processFrame() {
        switch ( gameState ) {
            case INTRO: //Runs on game start
                introFade += ( fadingIn ? 0.01 : -0.005 ); // Adjust the alpha on the fade. Fade in twice as fast as fade out.
                if ( introFade <= 0f ) { gameState = GameState.MAIN_MENU; } //Once fade complete, switch to main menu.
                if ( introFade >= 1 ) { fadingIn = false; } //At end of fade in, fade out
                introFade = (float) ( Math.round( introFade * 1000f ) / 1000f ); //Fancy math rounding crap
                break;
            case MAIN_MENU: //Main menu wooo~~
                MenuUtility.processMenu( keys );
                break;
            case GAME:
                EntityManager.updateEntities();
                if ( keys[0] ) {
                    EntityManager.moveEntity( Direction.LEFT, EntityManager.getPlayerId(), 7 );
                } else if ( keys[1] ) {
                    EntityManager.moveEntity( Direction.RIGHT, EntityManager.getPlayerId(), 7 );
                }
                if ( keys[2] ) {
                    ( (EntityWithJump) EntityManager.getEntity( EntityManager.getPlayerId() ) ).jump();
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
        graphics.setRenderingHint( RenderingHints.KEY_ANTIALIASING, //Enable antialiasing
                        RenderingHints.VALUE_ANTIALIAS_ON );
        
        graphics.setColor( Color.WHITE );
        graphics.fillRect( 0, 0, getWidth(), getHeight() );
        
        // Do Graphics
        switch ( gameState ) {
            case INTRO:
                graphics.setColor( Color.BLACK );
                graphics.fillRect( 0, 0, 1000, 600 );
                graphics.setComposite( AlphaComposite.getInstance( AlphaComposite.SRC_OVER, introFade ) ); // Transparentish logo
                graphics.drawImage( ResourceManager.getImage( "MENU_LOGO_STUDIO" ), 290, 90, null );
                break;
            case MAIN_MENU:
                //System.out.println(graphics.getFontMetrics( MNUSELECT_FONT ).getStringBounds( "OPTIONS", graphics ).getWidth());
                MenuUtility.drawMenu( MenuType.MAIN, graphics );
                break;
            case GAME:
                gameMap.drawMap( graphics, (short)0, (short)0 );
                EntityManager.drawEntities( graphics, new Point( 0, 0 ) );
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
