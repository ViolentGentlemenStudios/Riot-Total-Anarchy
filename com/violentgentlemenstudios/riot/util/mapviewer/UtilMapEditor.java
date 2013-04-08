package com.violentgentlemenstudios.riot.util.mapviewer;

import com.violentgentlemenstudios.riot.Map;
import com.violentgentlemenstudios.riot.MapLoader;
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;

public class UtilMapEditor extends JFrame {
    private Map viewMap = null;
    private Canvas canvas = null;
    
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
        
        viewMap.initializeGraphics(new Dimension(800, 600));
        
        Chrono chrono = new Chrono(this);
        new Timer(20, chrono).start();
    }
    
    private void myRepaint() {
        Graphics2D graphics = (Graphics2D) canvas.getBufferStrategy().getDrawGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, //Enable antialiasing
                        RenderingHints.VALUE_ANTIALIAS_ON);
        
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, 800, 600);
        viewMap.drawMap(graphics, (short) 800, (short) 800);
        
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
}
