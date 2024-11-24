package game_elements.entities.spaceships;

import game_elements.entities.PowerUp;
import game_elements.explosions.Explosion;
import gamewindow.GameKeyListener;
import gamewindow.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class PlayerSpaceShip extends SpaceShip{

    private GamePanel gp;
    private BufferedImage shipImage;
    private BufferedImage healthImage;
    private int healthSizeX, healthSizeY;
    private final List<Laser> laserList;
    private final List<Explosion> explosionList;
    private int health;
    private double lastHitTime;
    private final double invincibleTime = 2.0;
    private final double powerupLast = 5.0;
    private double extraLaserTime;
    private Shield shield;
    private double fasterRechargeTime;
    private boolean hit;

    public PlayerSpaceShip(GamePanel gamepanel, List<Laser> laserList, List<Explosion> explosionList) {
        gp = gamepanel;
        this.laserList = laserList;
        this.explosionList = explosionList;
        playerInit();
    }

    public void playerInit() {
        size_x = 99;
        size_y = 75;
        maxCoordinateOfX = gp.getScreenDimension().width;
        maxCoordinateOfY = gp.getScreenDimension().height;
        xCoordinate = (maxCoordinateOfX-size_x)/2;
        yCoordinate = (maxCoordinateOfY - size_y)/2;
        speed = 8;
        health = 3;
        hit = false;
        laserSpeed = -15;
        laserRechargeTime = 0.5;
        extraLaserTime = -1;
        fasterRechargeTime = -1;
        getImages();
    }



    public void update() {
        GameKeyListener gameKL = gp.getGameKeyListener();
        if(gameKL.up && !gameKL.down) {
            moveY(false);
        } else if(gameKL.down && !gameKL.up) {
            moveY(true);
        }
        if(gameKL.left && !gameKL.right) {
            moveX(false);
        } else if(gameKL.right && !gameKL.left) {
            moveX(true);
        }
        if(gameKL.space && !isRecharging()) {
            shootLaser();
        }

        if(fasterRechargeTime > 0 && System.currentTimeMillis()/1000.0 - fasterRechargeTime > powerupLast)
            laserRechargeTime = 0.5;
        if(shield != null)
            shield.update();
    }
    public void moveX(boolean rigth) {
        if(rigth && xCoordinate >= maxCoordinateOfX-size_x)
            xCoordinate = maxCoordinateOfX-size_x;
        else if (!rigth && xCoordinate <= speed)
            xCoordinate = 0;
        else if(rigth) {
            xCoordinate += speed;
        } else {
            xCoordinate -= speed;
        }

    }
    public void moveY(boolean down) {
        if(down && yCoordinate >=maxCoordinateOfY-size_y)
            yCoordinate = maxCoordinateOfY-size_y;
        else if (!down && yCoordinate <= speed)
            yCoordinate = 0;
        else if(down) {
            yCoordinate += speed;
        } else if(!down) {
            yCoordinate -= speed;
        }
    }

    public void draw(Graphics2D g) {

        if(!hit)
            g.drawImage(shipImage, xCoordinate, yCoordinate, size_x, size_y, null);
        else if(blinkAnimation() % 2 == 0) {
            g.drawImage(shipImage, xCoordinate, yCoordinate, size_x, size_y, null);
        }


        for(int i = 0; i < health; i++) {
            g.drawImage(healthImage, 15+i*(healthSizeX+10), 42, healthSizeX, healthSizeY, null);
        }

        if(shield != null) {
           shield.draw(g);
        }

    }

    public void getImages() {

        try {
            File shipImageFile = new File("./resource/SpaceShooterRedux/PNG/Player/playerShip1_red.png");
            File healthImageFile = new File("./resource/SpaceShooterRedux/PNG/UI/playerLife1_red.png");
            shipImage = ImageIO.read(ImageIO.createImageInputStream(shipImageFile));
            healthImage = ImageIO.read(ImageIO.createImageInputStream(healthImageFile));
            size_x = shipImage.getWidth();
            size_y = shipImage.getHeight();
            healthSizeX = healthImage.getWidth();
            healthSizeY = healthImage.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shootLaser() {
        if(extraLaserTime > 0) {
            double currentTime = System.currentTimeMillis()/1000.0;
            if(currentTime - extraLaserTime < powerupLast) {
                Laser extraLaser1  = new Laser(this, laserSpeed, explosionList, 1);
                laserList.add(extraLaser1);

                Laser extraLaser2  = new Laser(this, laserSpeed, explosionList, 2);
                laserList.add(extraLaser2);
            } else
                extraLaserTime = -1;

        }
        Laser laser = new Laser(this, laserSpeed, explosionList, 0);
        laserList.add(laser);
        lastLaserShoot = System.currentTimeMillis();

    }

    public boolean isRecharging() {
        return (System.currentTimeMillis() - lastLaserShoot) / 1000 < laserRechargeTime;
    }

    public void damage() {
        double currentTime = System.currentTimeMillis()/1000.0;
        if(hit == false || currentTime - lastHitTime > invincibleTime) {
            if(shield != null)
                shield.damage();
            else {
                health--;
                lastHitTime = currentTime;
            }
            hit = true;
        }
    }

    public int blinkAnimation() {
        double currentTime = System.currentTimeMillis()/1000.0;
        double diff = currentTime - lastHitTime;
        if(diff > invincibleTime)
            hit = false;
        return (int) ((currentTime-lastHitTime)*10);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void collectPowerUp(int powerupType) {
        switch (powerupType) {
            case 0:
                extraLaserTime = System.currentTimeMillis()/1000.0;
                break;
            case 1:
                health++;
                break;
            case 2:
                shield = new Shield(this);
                break;
            case 3:
                fasterRechargeTime = System.currentTimeMillis()/1000.0;
                laserRechargeTime = laserRechargeTime / 2;
                break;
        }
    }

    public void loseShield() {
        shield = null;
    }

    @Override
    public Ellipse2D.Double getSpaceShipBounds() {
        if(shield != null)
            return shield.getShieldBounds();
        return super.getSpaceShipBounds();
    }
}
