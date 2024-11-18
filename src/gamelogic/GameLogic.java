package gamelogic;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game_elements.*;
import gamewindow.GamePanel;

public class GameLogic {
    private boolean exit;
    private BackGround backGround;
    public PlayerSpaceShip player;
    private List<EnemySpaceShip> enemyList;
    private List<Laser> laserList;
    private List<Meteor> meteorList;
    private List<Explosion> explosionList;
    private GamePanel gp;
    private int time;
    private int fpsCounter;
    private int points;
    private int nextMeteorInterval;
    private int enemyCounter;

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
        enemyCounter = 0;
        backGround = new BackGround();
        laserList = new ArrayList<>();
        explosionList = new ArrayList<>();
        player = new PlayerSpaceShip(gp, laserList, explosionList);
        enemyList = new ArrayList<>();
        meteorList = new ArrayList<>();

    }
    public void gameUpdate() {
        fpsCounter++;
        if(fpsCounter % 60 == 0) {
            fpsCounter = 0;
            time++;
            if(time % nextMeteorInterval == 0)
                meteorInvoke();
        }
        backGround.update();
        checkCollision();
        player.update();
        updateEnemies();
        updateLasers();
        updateMeteors();
        updateExplosions();
    }

    public boolean end() {
        if(exit || !player.isAlive())
            return true;
        return false;
    }

    public void draw(Graphics2D g) {
        backGround.draw(g);
        drawLasers(g);
        drawMeteors(g);
        player.draw(g);
        drawEnemies(g);
        drawExplosions(g);
    }

    private void updateEnemies() {
        if(enemyList.isEmpty()) {
            enemyCounter++;
            for(int i = 0; i < enemyCounter; i++) {
                enemyList.add(new EnemySpaceShip(gp.getScreenDimension(), laserList, explosionList));
            }

        } else {
            Iterator<EnemySpaceShip> enemyIterator = enemyList.iterator();
            while(enemyIterator.hasNext()) {
                EnemySpaceShip enemy = enemyIterator.next();
                enemy.update();
                if(enemy.isExploded()) {
                    enemyIterator.remove();
                }
            }
        }
    }

    private void drawEnemies(Graphics2D g) {
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
        Meteor newMeteor = new Meteor(gp.getScreenDimension(), explosionList);
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

    private void updateExplosions() {
        Iterator<Explosion> explosionIterator = explosionList.iterator();
        while(explosionIterator.hasNext()) {
            Explosion explosion = explosionIterator.next();
            explosion.update();
            if(explosion.isAnimationEnd())
                    explosionIterator.remove();
        }
    }
    private void drawExplosions(Graphics2D g) {
        for(Explosion exp : explosionList) {
            exp.draw(g);
        }
    }

    private void checkCollision(){
        Ellipse2D.Double playerBounds = player.getSpaceShipBounds();
        // laser találat ellenőrzése
        for(Laser laser : laserList) {
            Rectangle laserBounds = laser.getLaserBounds();
            if(laser.isPlayerLaser()) {
                for(EnemySpaceShip enemy : enemyList) {
                    Ellipse2D.Double enemyBounds = enemy.getSpaceShipBounds();
                         if(enemyBounds.intersects(laserBounds)) {
                        enemy.damageShip();
                        laser.hitEntity();
                    }
                }
                for(Meteor meteor : meteorList) {
                    Ellipse2D.Double meteorBounds = meteor.getMeteorBounds();
                    if (meteorBounds.intersects(laserBounds)) {
                        meteor.explode();
                        laser.hitEntity();
                    }
                }
            } else {
                if(playerBounds.intersects(laserBounds)) {
                    player.damage();
                    laser.hitEntity();
                }
            }
        }
        for(Meteor meteor : meteorList) {
            Ellipse2D.Double meteorBounds = meteor.getMeteorBounds();
            if(playerBounds.intersects(meteorBounds.getBounds2D())) {
                player.damage();
                meteor.explode();
            }
        }
    }
}
