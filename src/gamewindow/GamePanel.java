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

public class GamePanel extends JPanel{
    private final int WIDTH = 1080;
    private final int HEIGTH = 720;
    private Thread gameThread;
    private GameKeyListener gameKL;
    public GameMouseListener gameML;
    private GameLogic gamelogic;
    private GameWindow gameWindow;

    /*
     * A GamePanel konstruktora
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

    private void play() {
        gameThread = new GameThread(this);
        gameThread.start();
    }

    public void update() {
        gameWindow.addMenu();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D graphics = (Graphics2D) g;

        gamelogic.draw(graphics);

        graphics.dispose();

    }

    public Dimension getScreenDimension() {
        return new Dimension(this.WIDTH,this.HEIGTH);
    }

    public GameKeyListener getGameKeyListener() {
        return gameKL;
    }

    public void setGameLogic(GameLogic gamelogic) {
        this.gamelogic = gamelogic;
    }

    public Dimension getPanelDimension() {
        return  new Dimension(WIDTH, HEIGTH);
    }

}
