package gamewindow;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {

    public boolean up, left, down, right, space, enter, pause;
    public String word;

    public GameKeyListener() {
        word = new String();
    }

    public GameKeyListener(String string) {
        word = string;
    }

    @Override
    public void keyTyped(KeyEvent e) {
//        word += e.getKeyChar();
//        System.out.println(word);
    }

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
