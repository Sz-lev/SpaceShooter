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
    private GameKeyListener gameKL = new GameKeyListener();
    private GameLogic gamelogic;
    private BufferedImage background1, background2;

    /*
     * A GamePanel konstruktora
     */
    public GamePanel() {
        setSize(WIDTH, HEIGTH);
        setDoubleBuffered(true);
        setFocusable(true);
        addKeyListener(gameKL);
        play();
    }

    private void play() {
        gameThread = new GameThread(this);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        gameThread.start();
    }

    public void update() {}
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

}
