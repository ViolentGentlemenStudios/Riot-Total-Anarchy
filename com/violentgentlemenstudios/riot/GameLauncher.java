package com.violentgentlemenstudios.riot;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameLauncher extends JFrame {
    public GameLauncher() {        
        super( "Riot: Total Anarchy" );
        
        init();
        
        GameCanvas canvas = new GameCanvas();
        add( canvas, BorderLayout.CENTER );
        
        addKeyListener( new KeyboardHandler( canvas ) );
        
        setSize( 1000, 600 );
        setDefaultCloseOperation( EXIT_ON_CLOSE );
        setVisible( true );      
        canvas.createBufferStrategy( 2 );
    }
    
    public static void main( String[] args ) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameLauncher();
            }
        });
    }
    
    public void init() {
        ResourceManager.getInstance().loadFromIndex();
    }    
}
