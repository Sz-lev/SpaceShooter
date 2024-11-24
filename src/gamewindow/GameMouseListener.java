package gamewindow;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GameMouseListener extends MouseAdapter {

    public int xPos, yPos;
    public boolean clicked, entered;

    public void mouseEntered(MouseEvent e) {
        entered = true;
    }

    public void mousePressed(MouseEvent e) {
        if(entered)
            clicked = true;
    }

    public void mouseExited(MouseEvent e) {
        clicked = false;
        entered = false;
    }

    public void mouseMoved(MouseEvent e) {
        if(entered) {
            xPos = e.getX();
            yPos = e.getY();
        }
        if(clicked) {
            clicked = false;
        }
    }

    public void mouseReleased(MouseEvent e) {
        clicked = false;
    }

}
