package com.violentgentlemenstudios.riot;

import com.violentgentlemenstudios.riot.util.mapeditor.UtilMapEditor;
import java.awt.BorderLayout;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GameLauncher extends JFrame {
    public GameLauncher() {        
        super("Riot: Total Anarchy");
        
        init();
        
        GameCanvas canvas = new GameCanvas();
        add(canvas, BorderLayout.CENTER);
        
        addKeyListener(new KeyboardHandler(canvas));
        
        setSize(1000, 600);
        setLocation(Toolkit.getDefaultToolkit().getScreenSize().width / 2 - 500, 
                Toolkit.getDefaultToolkit().getScreenSize().height / 2 - 300);
        setIconImage(ResourceManager.getImage("ICON"));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);      
        setResizable(false);
        canvas.createBufferStrategy(2);
    }
    
    public static void main(String[] args) {
        if (args.length > 0) {
            switch (args[0]) {
                case "mapedit":
                    if (args.length >= 2) {
                        UtilMapEditor utilEditor = new UtilMapEditor(args[1]);
                    } else {
                        System.out.println("Usage: mapedit <levelName>");
                    }
                    break;
                default:
                    System.out.println("Unknown utility: " + args[0]);
                    break;
            }
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    new GameLauncher();
                }
            });
        }
    }
    
    public void init() {
        ResourceManager.loadFromIndex();
        MusicManager.loadFromIndex();
    }    
}
