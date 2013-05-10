package com.violentgentlemenstudios.riot;

import com.violentgentlemenstudios.riot.res.cutscenes.*;
import java.awt.Point;

public class CutsceneManager {
    private static Cutscene currentScene = null;
    private static final Cutscene[] AVAILABLE_CUTSCENES = {
        new SceneIntro() //0
    };
    
    static {
        for (Cutscene scene : AVAILABLE_CUTSCENES) {
            scene.setupActors();
        } 
    }
    
    public static void play(int sceneId) {
        currentScene = AVAILABLE_CUTSCENES[sceneId];
        GameCanvas.setState(GameState.CUTSCENE);
    }
    
    public static void process() {
        SceneCommand cmd = currentScene.getCommand();
        switch (cmd.getCommand()) {
            case "move": cmdMove(cmd); break;
            case "end" : cmdEnd (cmd); break;
        }
    }
    
    public static void clear() {
        currentScene = null;
    }
    
    private static void cmdMove(SceneCommand command) {
        Entity actor = currentScene.objPlayer((String) command.getArguments()[0]);
        Direction dir = (Direction) command.getArguments()[1];
        int speed = (int) command.getArguments()[2];
        actor.setDirection(dir);
        actor.move(speed);
    }
    
    private static void cmdEnd(SceneCommand command) {
        GameCanvas.setState(GameState.GAME);
    }
}
