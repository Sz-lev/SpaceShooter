package gamelogic;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game_elements.*;
import gamewindow.GameKeyListener;
import gamewindow.GamePanel;

public class GameLogic {
    private boolean exit;
    private BackGround backGround;
    public PlayerSpaceShip player;
    private List<EnemySpaceShip> enemyList;
    private List<Laser> laserList;
    private GamePanel gp;
    private int time;
    private int fpsCounter;
    private int points;

    public GameLogic(GamePanel gamepanel) {
        gp = gamepanel;
        gameInit();
    }

    public void gameInit() {
        exit = false;
        time = 0;
        fpsCounter = 0;
        points = 0;
        backGround = new BackGround();
        laserList = new ArrayList<>();
        player = new PlayerSpaceShip(gp, laserList);
        enemyList = new ArrayList<>();

    }
    public void gameRun() {
        fpsCounter++;
        if(fpsCounter % 60 == 0) {
            fpsCounter = 0;
            time++;
            System.out.println(time);
        }
        backGround.update();
        player.update();
        updateLasers();

    }

    public boolean end() {
        return exit;
    }

    public void draw(Graphics2D g) {
        backGround.draw(g);
        drawLasers(g);
        player.draw(g);
    }
    private void updateLasers() {
        System.out.println(laserList.size());
        Iterator<Laser> laserIterator = laserList.iterator();
        while(laserIterator.hasNext()) {
            Laser laser = laserIterator.next();
            laser.update();
            if(laser.isOutOfBounds) {
                laserIterator.remove();
            }
        }
    }

    private void drawLasers(Graphics2D g) {
        for(Laser laser : laserList) {
            laser.draw(g);
        }
    }
}
