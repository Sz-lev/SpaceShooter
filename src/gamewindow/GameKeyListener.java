package gamewindow;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * A billentyűzet változásit figyelő osztály.
 */
public class GameKeyListener implements KeyListener {

    public boolean up, left, down, right, space, enter, pause;

    @Override
    public void keyTyped(KeyEvent e) {}
    /**
     * A játékban használt gombok megnyomása esetén True-ra állítja a hozzá tartozó változót.
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch(keyCode) {
            case KeyEvent.VK_UP:
                up = true;
                break;

            case KeyEvent.VK_DOWN:
                down = true;
                break;

            case KeyEvent.VK_LEFT:
                left = true;
                break;

            case KeyEvent.VK_RIGHT:
                right = true;
                break;

            case KeyEvent.VK_SPACE:
                space = true;
                break;

            case KeyEvent.VK_P:
                if(pause)
                    pause = false;
                else
                    pause = true;
                break;

            case KeyEvent.VK_ENTER:
                enter = true;
                break;
        }
    }

    /**
     * A játékban használt gombok esetén False-ra állítja a megfelelőnek az értékét a gomb elengedésekor.
     * @param e the event to be processed
     */

    @Override
    public void keyReleased(KeyEvent e) {
        int keyCode = e.getKeyCode();

        switch(keyCode) {
            case KeyEvent.VK_UP:
                up = false;
                break;

            case KeyEvent.VK_DOWN:
                down = false;
                break;

            case KeyEvent.VK_LEFT:
                left = false;
                break;

            case KeyEvent.VK_RIGHT:
                right = false;
                break;

            case KeyEvent.VK_SPACE:
                space = false;
                break;

            case KeyEvent.VK_ENTER:
                enter = false;
                break;
        }
    }
}
