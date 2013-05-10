package com.violentgentlemenstudios.riot;

import java.awt.AlphaComposite;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
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
    protected static GameState gameState = GameState.INTRO;
    protected static final boolean[] keys = new boolean[5]; //Left, Right, Up, Down, Select
    protected static Map gameMap = null;
    
    private float introFade = 0f;
    private boolean fadingIn = true;
    
    public GameCanvas() {
        MenuUtility.setGameCanvas(this);
        
        setFocusable(false);
        setIgnoreRepaint(true);
        Chrono chrono = new Chrono(this);
        new Timer(20, chrono).start();

        gameMap = MapLoader.loadLevel("level1");
        gameMap.initializeGraphics(new Dimension(1000, 600));
    }
    
    public void processFrame() {
        try {
            switch ( gameState ) {
                case INTRO: //Runs on game start
                    introFade += ( fadingIn ? 0.01 : -0.005 ); // Adjust the alpha on the fade. Fade in twice as fast as fade out.
                    if ( introFade <= 0f ) { gameState = GameState.MAIN_MENU; } //Once fade complete, switch to main menu.
                    if ( introFade >= 1 ) { fadingIn = false; } //At end of fade in, fade out
                    introFade = (float) ( Math.round( introFade * 1000f ) / 1000f ); //Fancy math rounding crap
                    if (keys[4]) { gameState = GameState.MAIN_MENU; }
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
                        CutsceneManager.play(0);
                    }
                    break;
                case CUTSCENE:
                    CutsceneManager.process();
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        requestFocus();
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
            case CUTSCENE:
            case GAME:
                gameMap.drawMap(graphics, (short) (EntityManager.getPlayer().getCenter().getX() - 500),
                            (short) (EntityManager.getPlayer().getLocation().getY() + EntityManager.getPlayer().getDistanceOffsetY() - 300 - Map.TILE_SIZE));
                EntityManager.drawEntities(graphics, (short) (EntityManager.getPlayer().getCenter().getX() - 500),
                            (short) (EntityManager.getPlayer().getCenter().getY() - 300));
                HUD.draw(graphics);
                Chatbox.draw(graphics);
                break;
            default:
                break;
        }
        
        if(graphics != null) { graphics.dispose(); }
        strategy.show();
        Toolkit.getDefaultToolkit().sync();

        repaintInProgress = false;
    }
    
    public static Map getMap() {
        return gameMap;
    }
    
    public static boolean[] getKeys() {
        return keys;
    }
    
    public static void setState(GameState state) {
        gameState = state;
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
