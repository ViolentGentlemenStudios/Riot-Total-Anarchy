package com.violentgentlemenstudios.riot;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyboardHandler implements KeyListener {
    private GameCanvas gc = null;
    public KeyboardHandler ( GameCanvas gc ) {
        this.gc = gc;
    }

    public void keyTyped(KeyEvent e) {}

    public void keyPressed(KeyEvent e) {
        switch ( e.getKeyCode() ) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                gc.keys[0] = true;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                gc.keys[1] = true;
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                gc.keys[2] = true;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                gc.keys[3] = true;
                break;
            case KeyEvent.VK_SPACE:
                gc.keys[4] = true;
                break;
        }
    }  
    public void keyReleased(KeyEvent e) {
        switch ( e.getKeyCode() ) {
            case KeyEvent.VK_LEFT:
            case KeyEvent.VK_A:
                gc.keys[0] = false;
                break;
            case KeyEvent.VK_RIGHT:
            case KeyEvent.VK_D:
                gc.keys[1] = false;
                break;
            case KeyEvent.VK_UP:
            case KeyEvent.VK_W:
                gc.keys[2] = false;
                break;
            case KeyEvent.VK_DOWN:
            case KeyEvent.VK_S:
                gc.keys[3] = false;
                break;
            case KeyEvent.VK_SPACE:
                gc.keys[4] = false;
                break;
        }
    }
}