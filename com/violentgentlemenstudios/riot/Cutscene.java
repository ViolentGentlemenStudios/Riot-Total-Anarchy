package com.violentgentlemenstudios.riot;

import java.util.HashMap;

public abstract class Cutscene {
    protected HashMap<String, Entity> actors = new HashMap<>();
    protected SceneCommand[] commands = null;
    protected int currentCommand = -1;
            
    public abstract void setupActors();
    
    protected SceneCommand getCommand() {
        currentCommand++;
        if (currentCommand == commands.length) {
            currentCommand = -1;
            return new SceneCommand("end");
        }
        return commands[currentCommand];
    }
    
    protected Entity objPlayer(String key) {
        return actors.get(key);
    }
}
