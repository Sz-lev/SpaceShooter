package gamewindow;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {

    public boolean up, left, down, right;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode) {
            case KeyEvent.VK_UP: {
                up = true;
                break;
            }
            case KeyEvent.VK_DOWN: {
                down = true;
                break;
            }
            case KeyEvent.VK_LEFT: {
                left = true;
                break;
            }
            case KeyEvent.VK_RIGHT: {
                right = true;
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch(keyCode) {
            case KeyEvent.VK_UP: {
                up = false;
                break;
            }
            case KeyEvent.VK_DOWN: {
                down = false;
                break;
            }
            case KeyEvent.VK_LEFT: {
                left = false;
                break;
            }
            case KeyEvent.VK_RIGHT: {
                right = false;
                break;
            }
        }
    }
}
