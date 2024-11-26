package gamewindow;

import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Az egér állapotát figyelő osztály.
 */
public class GameMouseListener extends MouseAdapter {

    public int xPos, yPos;
    public boolean clicked, entered;

    /**
     * Az egér belépésekor az ablakba az entered értéket True-ra állítja.
     * @param e the event to be processed
     */
    public void mouseEntered(MouseEvent e) {
        entered = true;
    }

    /**
     * Ha az egér a kijelzőben van és a bal gombjának megnyomása történik, akkor a clicked True értéket kap.
     * @param e the event to be processed
     */
    public void mousePressed(MouseEvent e) {
        if(entered && e.getButton() == MouseEvent.BUTTON1)
            clicked = true;
    }


    /**
     * Ha az egér kimegy a kijelzőből, akkor az entered és a clicked False értéket kap.
     * @param e the event to be processed
     */
    public void mouseExited(MouseEvent e) {
        clicked = false;
        entered = false;
    }

    /**
     * Ha az egér az ablakban van, akkor frissíti az egér mozgásával a mutató x és y pozícióját.
     * A clicked értéket meg False-ra állítja.
     * @param e the event to be processed
     */
    public void mouseMoved(MouseEvent e) {
        if(entered) {
            xPos = e.getX();
            yPos = e.getY();
        }
        if(clicked) {
            clicked = false;
        }
    }

    /**
     * Az egér bármelyik gombjának elengedése a clicked értéket False-ra állítja.
     * @param e the event to be processed
     */
    public void mouseReleased(MouseEvent e) {
        clicked = false;
    }

}
