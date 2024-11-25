package gamelogic;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import game_elements.background.BackGround;
import game_elements.entities.Meteor;
import game_elements.entities.PowerUp;
import game_elements.entities.spaceships.EnemySpaceShip;
import game_elements.entities.spaceships.Laser;
import game_elements.entities.spaceships.PlayerSpaceShip;
import game_elements.explosions.Explosion;
import gamewindow.GamePanel;
import playerdata.LeaderBoard;
import playerdata.PlayerData;
import playerdata.Result;

public class GameLogic {
    private BackGround backGround;
    public PlayerSpaceShip player;
    private List<EnemySpaceShip> enemyList;
    private List<Laser> laserList;
    private List<Meteor> meteorList;
    private List<Explosion> explosionList;
    private List<PowerUp> powerUpList;
    private GamePanel gp;
    private int time;
    private int fpsCounter;
    private int points;
    private int level;
    private double nextMeteorInterval;
    private double lastMeteorTime;
    private int enemyCounter;
    private Font spaceFont;
    private final int fontSize = 22;
    private int enemiesDestroyed;
    private int meteorsDestroyed;
    private int powerupsCollected;
    private final double nextPowerUpTime = 20;
    private GamePauseMenu gamePauseMenu;
    public int gameState;

    public GameLogic(GamePanel gamepanel) {
        gp = gamepanel;
        gameInit();
    }

    public void gameInit() {
        time = 0;
        fpsCounter = 0;
        points = 0;
        level = 0;
        nextMeteorInterval = 3.0;
        enemyCounter = 0;
        enemiesDestroyed = 0;
        meteorsDestroyed = 0;
        powerupsCollected = 0;
        backGround = new BackGround();
        laserList = new ArrayList<>();
        explosionList = new ArrayList<>();
        player = new PlayerSpaceShip(gp, laserList, explosionList);
        enemyList = new ArrayList<>();
        meteorList = new ArrayList<>();
        powerUpList = new ArrayList<>();
        spaceFont = new Font("OCR A Extended", Font.BOLD, fontSize);
        System.out.println(spaceFont);
    }
    public void gameUpdate() {
        if(!gp.getGameKeyListener().pause) {
            fpsCounter++;
            if(fpsCounter % 60 == 0) {
                fpsCounter = 0;
                time++;
                points++;
                if(time != 0 && time % nextPowerUpTime == 0)
                    addPowerUp();
            }
            backGround.update();
            checkCollision();
            player.update();
            updateEnemies();
            updateLasers();
            updateMeteors();
            updateExplosions();
            if(gamePauseMenu != null) {
                gamePauseMenu = null;
                gameState = 0;
            }
        } else if (gamePauseMenu == null) {
            gamePauseMenu = new GamePauseMenu(gp.getPanelDimension(), gp.getGameKeyListener(), gp.gameML);
            gameState = 1;
        }
        else
            gameState = gamePauseMenu.update();
    }

    public void nextLevel() {
        level++;
        if(level % 2 == 0 && nextMeteorInterval > 1)
            nextMeteorInterval -= 0.2;
        else
            enemyCounter++;
    }
    public boolean end() {
        if(!player.isAlive()) {
            PlayerData plyr = gp.gameWindow.player;
            plyr.addEnemyShipDestroyed(enemiesDestroyed);
            plyr.addMeteorsDestroyed(meteorsDestroyed);
            plyr.addTimePlayed(time+fpsCounter/60.0);
            plyr.setHighScore(points);
            plyr.setMaxLevel(level-1);
            plyr.addPUCollected(powerupsCollected);
            plyr.addGamesPlayed(1);
            LeaderBoard.addResult(new Result(plyr.name, points, time+fpsCounter/60.0));

        }
        return (gameState == 2 || !player.isAlive());
    }

    public void draw(Graphics2D g) {

            backGround.draw(g);
            drawLasers(g);
            drawMeteors(g);
            player.draw(g);
            drawEnemies(g);
            drawExplosions(g);
            updatePowerUps();
            drawPoints(g);
            drawPowerUps(g);
        if(gamePauseMenu != null)
            gamePauseMenu.draw(g);
    }

    private void addPowerUp() {
        powerUpList.add(new PowerUp(gp.getScreenDimension()));
    }
    private void updatePowerUps() {

        Iterator<PowerUp> powerUpIterator = powerUpList.iterator();
        while(powerUpIterator.hasNext()) {
            PowerUp powerup = powerUpIterator.next();
            powerup.update();
            if(powerup.isOutOfBounds)
                powerUpIterator.remove();
        }
    }

    private void drawPowerUps(Graphics2D g) {
        for(PowerUp powerup : powerUpList) {
            powerup.draw(g);
        }
    }

    private void updateEnemies() {
        if(enemyList.isEmpty()) {
            nextLevel();
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
                    enemiesDestroyed++;
                    points += 50;
                    if (enemiesDestroyed != 0 && enemiesDestroyed % 5 == 0)
                        addPowerUp();
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
        if(System.currentTimeMillis()/1000.0 - lastMeteorTime > nextMeteorInterval) {
            lastMeteorTime = System.currentTimeMillis()/1000.0;
            meteorInvoke();
        }
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
                    if(enemyBounds.intersects(laserBounds) && !laser.isOutOfBounds) {
                        enemy.damageShip();
                        laser.hitEntity();
                    }
                }
                for(Meteor meteor : meteorList) {
                    Ellipse2D.Double meteorBounds = meteor.getMeteorBounds();
                    if (meteorBounds.intersects(laserBounds) && !laser.isOutOfBounds) {
                        meteor.explode();
                        laser.hitEntity();
                        meteorsDestroyed++;
                        if(meteorsDestroyed != 0 && meteorsDestroyed % 5 == 0)
                            addPowerUp();
                        points += 15;
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

        Iterator<PowerUp> powerUpIterator = powerUpList.iterator();
        while(powerUpIterator.hasNext()) {
            PowerUp powerup = powerUpIterator.next();
            if(playerBounds.intersects(powerup.getPowerUpBounds()) && !powerup.isOutOfBounds) {
                powerupsCollected++;
                powerup.isOutOfBounds = true;
                player.collectPowerUp(powerup.getType());
                powerUpIterator.remove();
            }
        }

    }

    private void drawPoints(Graphics2D g) {
        g.setFont(spaceFont);
        g.setColor(Color.WHITE);
        g.drawString("HIGHSCORE: "+0, 900, 30);
        g.drawString("SCORE: "+points, 900, 52);
        g.drawString("TIME: "+time+" s", 900, 74);
        g.drawString("Level: "+level, 15, 32);
    }
}

