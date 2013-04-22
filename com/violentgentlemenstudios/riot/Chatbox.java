package com.violentgentlemenstudios.riot;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class Chatbox {
    private static final String BOX_IMAGE = "CHATBUBBLE";
    private static final int    DEF_DELAY = 3500;
    private static int    delay   = 2000;
    private static Entity speaker = null;
    private static String text    = "";
    private static long   time    = 0;
    
    public static void draw(Graphics2D graphics) {
        if (System.currentTimeMillis() > (time + delay)) {
            speaker = null;
            text = "";
        }
        if (speaker != null) {
            int boxX = speaker.getLocation().x - GameCanvas.getMap().getLocation().x + 65;
            int boxY = speaker.getLocation().y - GameCanvas.getMap().getLocation().y - 135;
            graphics.drawImage(ResourceManager.getImage(BOX_IMAGE), boxX, boxY, null);
            graphics.setColor(Color.BLACK);
            graphics.setFont(new Font("Arial", Font.BOLD, 36));
            graphics.drawString(text, boxX + 10, boxY + 30);
        }
    }
    
    public static void talk(Entity speaker, String text) {
        talk(speaker, text, DEF_DELAY);
    }
    
    public static void talk(Entity speaker, String text, int delay) {
        Chatbox.speaker = speaker;
        Chatbox.text    = text;
        Chatbox.delay   = delay;
        Chatbox.time    = System.currentTimeMillis();
    }
}
