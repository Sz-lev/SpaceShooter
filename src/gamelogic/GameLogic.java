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
    private List<Meteor> meteorList;
    private GamePanel gp;
    private int time;
    private int fpsCounter;
    private int points;
    private int nextMeteorInterval;

    public GameLogic(GamePanel gamepanel) {
        gp = gamepanel;
        gameInit();
    }

    public void gameInit() {
        exit = false;
        time = 0;
        fpsCounter = 0;
        points = 0;
        nextMeteorInterval = 3;
        backGround = new BackGround();
        laserList = new ArrayList<>();
        player = new PlayerSpaceShip(gp, laserList);
        enemyList = new ArrayList<>();
        meteorList = new ArrayList<>();
        enemyList.add(new EnemySpaceShip(gp.getScreenDimension(), enemyList, laserList));
        enemyList.add(new EnemySpaceShip(gp.getScreenDimension(), enemyList, laserList));
        enemyList.add(new EnemySpaceShip(gp.getScreenDimension(), enemyList, laserList));
        enemyList.add(new EnemySpaceShip(gp.getScreenDimension(), enemyList, laserList));


    }
    public void gameUpdate() {
        fpsCounter++;
        if(fpsCounter % 60 == 0) {
            fpsCounter = 0;
            time++;
            System.out.println(laserList.size());
            if(time % nextMeteorInterval == 0)
                meteorInvoke();
        }
        backGround.update();
        player.update();

        for(EnemySpaceShip enemy : enemyList){
            enemy.update();
        }
        updateLasers();
        updateMeteors();


    }

    public boolean end() {
        return exit;
    }

    public void draw(Graphics2D g) {
        backGround.draw(g);
        drawLasers(g);
        drawMeteors(g);
        player.draw(g);
        for(EnemySpaceShip enemy : enemyList) {
            enemy.draw(g);
        }
    }
    private void updateLasers() {
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

    private void meteorInvoke() {
        Meteor newMeteor = new Meteor(gp.getScreenDimension());
        meteorList.add(newMeteor);
    }

    private void updateMeteors() {
        Iterator<Meteor> meteorIterator = meteorList.iterator();
        while(meteorIterator.hasNext()) {
            Meteor meteor = meteorIterator.next();
            meteor.update();
            if(meteor.isOutOfBounds()) {
                meteorIterator.remove();
            }
        }
    }

    private void drawMeteors(Graphics2D g) {
        for(Meteor meteor : meteorList) {
            meteor.draw(g);
        }
    }
}
