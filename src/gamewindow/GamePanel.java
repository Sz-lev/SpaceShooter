package gamewindow;

import gamelogic.GameLogic;
import gamelogic.GameThread;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel{
    private final int WIDTH = 1080;
    private final int HEIGTH = 720;

    private Thread gameThread;
    private GameKeyListener gameKL = new GameKeyListener();
    private GameLogic gamelogic;
    public GamePanel() {
        setSize(WIDTH, HEIGTH);
        setBackground(Color.BLACK);
        setDoubleBuffered(true);
        setFocusable(true);
        requestFocusInWindow();
        addKeyListener(gameKL);

        play();

    }

    private void play() {
        gameThread = new GameThread(this);
        gameThread.start();
    }

    public void update() {
        if(gameKL.up && !gameKL.down) {
            gamelogic.player.moveY(false);
            System.out.println("UP");
        } else if(gameKL.down && !gameKL.up) {
            gamelogic.player.moveY(true);
            System.out.println("DOWN");
        }
        if(gameKL.left && !gameKL.right) {
            gamelogic.player.moveX(false);
            System.out.println("LEFT");
        } else if(gameKL.right && !gameKL.left) {
            gamelogic.player.moveX(true);
            System.out.println("RIGHT");
        }
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D graphics = (Graphics2D) g;

        if(gamelogic != null) {
            graphics.setColor(Color.WHITE);
            graphics.fillRect(gamelogic.player.getxCoordinate(), gamelogic.player.getyCoordinate(), 50, 50);
            graphics.dispose();
        }


    }

    public void setGamelogic(GameLogic gl) {
        gamelogic = gl;
        gamelogic.setScreenDimensions(WIDTH, HEIGTH);
        gamelogic.init();
    }

}
