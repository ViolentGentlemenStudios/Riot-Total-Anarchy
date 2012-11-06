package com.violentgentlemenstudios.riot;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import javax.swing.Timer;

public class GameCanvas extends Canvas {
    private boolean repaintInProgress = false;
    protected boolean[] keys = new boolean[5];
    
    public GameCanvas() {
        setIgnoreRepaint( true );
        Chrono chrono = new Chrono( this );
        new Timer( 16, chrono ).start();
    }

    public void processFrame() {
        
    }
    
    public void myRepaint() {
        if ( repaintInProgress ) { return; }
        repaintInProgress = true;
        
        processFrame();
        
        BufferStrategy strategy = getBufferStrategy();
        Graphics graphics = strategy.getDrawGraphics();
        
        graphics.setColor( Color.WHITE );
        graphics.fillRect( 0, 0, getWidth(), getHeight() );
        
        graphics.fillRect( 0, 300, getWidth(), 100 );
        
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
