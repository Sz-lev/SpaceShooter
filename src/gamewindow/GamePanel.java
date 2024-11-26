package gamewindow;

import gamelogic.GameLogic;
import gamelogic.GameThread;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A játék panelének osztálya.
 */
public class GamePanel extends JPanel{
    private final int WIDTH = 1080;
    private final int HEIGTH = 720;
    private Thread gameThread;
    private GameKeyListener gameKL;
    public GameMouseListener gameML;
    private GameLogic gamelogic;
    public GameWindow gameWindow;

    /**
     * A GamePanel konstuktora.
     * Menti a tulajdonos ablakot. Beállítja a panel méretét, focusát, és hozzáadja a billentyűzet és egér figyelőket.
     * Meghívja a play függvényt, ami a játék futtatását kezdi.
     * @param gameWindow A játékablak, amin a panel található.
     */
    public GamePanel(GameWindow gameWindow) {
        gameKL = new GameKeyListener();
        gameML = new GameMouseListener();

        setSize(WIDTH, HEIGTH);
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(gameKL);
        addMouseListener(gameML);
        addMouseMotionListener(gameML);
        this.gameWindow = gameWindow;
        play();
    }

    /**
     * Létrehozza a játék futását végző szálat és meghívja annak a start függvényét.
     */
    private void play() {
        gameThread = new GameThread(this);
        gameThread.start();
    }

    /**
     * A menübe visszatérés függvénye, ami meghívja a főablak menü függvényét.
     */
    public void returnToMenu() {
        gameWindow.addMenu();
    }

    /**
     * A komponensek megjelenítéséért felelős függvény.
     * Meghívja a gamelogic draw metódusát.
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics = (Graphics2D) g;

        gamelogic.draw(graphics);

        graphics.dispose();
    }

    /**
     * A panel méretét visszaadó függvény.
     * @return Az ablak dimenzióját adja vissza.
     */
    public Dimension getScreenDimension() {
        return new Dimension(this.WIDTH,this.HEIGTH);
    }

    /**
     * A billentyűzet állapotát figyelő objektumok adja vissza.
     * @return A panel GameKeyListenere.
     */
    public GameKeyListener getGameKeyListener() {
        return gameKL;
    }

    /**
     * Beállítja a panelnek a játék logika objektumát.
     * @param gamelogic A játék logikája
     */
    public void setGameLogic(GameLogic gamelogic) {
        this.gamelogic = gamelogic;
    }
}
