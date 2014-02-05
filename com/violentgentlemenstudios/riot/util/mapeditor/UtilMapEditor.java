package com.violentgentlemenstudios.riot.util.mapeditor;

import com.violentgentlemenstudios.riot.Map;
import com.violentgentlemenstudios.riot.MapLoader;
import com.violentgentlemenstudios.riot.ResourceManager;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.Timer;

public class UtilMapEditor extends JFrame {
    private final int MOVE_SPEED = 5;
    
    private Map viewMap = null;
    private Canvas canvas = null;
    private KeyHandler keyHandler = new KeyHandler(this);
    private boolean[] keys = new boolean[4]; //Up, Down, Left, Right
    
    private short xDraw = 0;
    private short yDraw = 0;
    private float scale = 1.0f;
    
    public UtilMapEditor(String levelName) {
        super("RTAe Map Editor");
        viewMap = MapLoader.loadMap(levelName);
        
        canvas = new Canvas();
        add(canvas, BorderLayout.CENTER);
        
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);      
        setResizable(false);
        canvas.setSize(800, 600);
        canvas.setIgnoreRepaint(true);
        canvas.createBufferStrategy(2);
        canvas.setVisible(true);
        
        addKeyListener(keyHandler);
        canvas.addKeyListener(keyHandler);
        
        ResourceManager.loadFromIndex();
        viewMap.initializeGraphics(new Dimension(800, 600));
        
        Chrono chrono = new Chrono(this);
        new Timer(20, chrono).start();
    }
    
    private void myRepaint() {
        Graphics2D graphics = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, //Enable antialiasing
                        RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (keys[0]) { yDraw -= MOVE_SPEED; }
        if (keys[1]) { yDraw += MOVE_SPEED; }
        if (keys[2]) { xDraw -= MOVE_SPEED; }
        if (keys[3]) { xDraw += MOVE_SPEED; }
        
        scale -= 0.01f;
        
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 800, 600);
        viewMap.drawMap(graphics, xDraw, yDraw, scale);
        
        if(graphics != null) { graphics.dispose(); }
        canvas.getBufferStrategy().show();
        Toolkit.getDefaultToolkit().sync();
    }
    
    private class Chrono implements ActionListener {
	private UtilMapEditor gc = null;

	public Chrono(UtilMapEditor gc) {
            this.gc = gc;
	}

	public void actionPerformed(ActionEvent e) {
            gc.myRepaint();
	}
    }
    
    private class KeyHandler implements KeyListener {
        private UtilMapEditor editor = null;
        
        public KeyHandler(UtilMapEditor editor) {
            this.editor = editor;
        }
        
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    editor.keys[0] = true;
                    break;
                case KeyEvent.VK_DOWN:
                    editor.keys[1] = true;
                    break;
                case KeyEvent.VK_LEFT:
                    editor.keys[2] = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    editor.keys[3] = true;
                    break;
            }
        }
        
        public void keyReleased(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    editor.keys[0] = false;
                    break;
                case KeyEvent.VK_DOWN:
                    editor.keys[1] = false;
                    break;
                case KeyEvent.VK_LEFT:
                    editor.keys[2] = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    editor.keys[3] = false;
                    break;
            }
        }

        public void keyTyped(KeyEvent e) {}
    }
}
