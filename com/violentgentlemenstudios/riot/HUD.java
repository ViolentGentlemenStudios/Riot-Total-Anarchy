package com.violentgentlemenstudios.riot;

import java.awt.Graphics2D;

public class HUD {
    private static final String KEY = "HUD";
    
    public static void draw(Graphics2D g) {
        g.drawImage(ResourceManager.getImage(KEY), 0, 0, null);
        g.drawString(Double.toString(EntityManager.getPlayer().getLocation().x), 25, 25);
        g.drawString(Double.toString(EntityManager.getPlayer().getLocation().y), 25, 60);
    }
}
