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

/**
 * A játékos űrhajójának osztálya.
 */
public class PlayerSpaceShip extends SpaceShip{

    private GameKeyListener gameKL;
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

    /**
     * A játékos űrhajójának konstruktora. Meghívja az inicializáló függvényt.
     *
     * @param maxCoords     A maximum x és y értékeket jelző koordináta.
     * @param laserList     A lézerek listája.
     * @param explosionList A robbanások listája.
     * @param gkl           A billentyűzet változásait figyelő objektum.
     */
    public PlayerSpaceShip(Dimension maxCoords, List<Laser> laserList, List<Explosion> explosionList, GameKeyListener gkl) {
        maxCoordinateOfX = maxCoords.width;
        maxCoordinateOfY = maxCoords.height;
        this.laserList = laserList;
        this.explosionList = explosionList;
        gameKL = gkl;
        playerInit();
    }

    /**
     * A játékos űrhajójának értékeit inícializáló függvény.
     */
    public void playerInit() {
        size_x = 99;
        size_y = 75;
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


    /**
     * Ellenőrzi a billentyűzet inputot és annak függvényében mozgatja az űrhajót, vagy lő a lézerrel, ha az nincs újratöltési fázisban.
     * Az újratöltés csökkentésért felelős power hatásának határidejét is ellenőrzi. Ha letelt az idő, akkor visszaállítja a kezdő értéket.
     * Frissíti az űrhajó pajzsát, ha az létezik.
     *
     */
    public void update() {
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

    /**
     * A vízszintes síkon mozgásért felelős függvény. Ha túllépné valamelyik határt a mozgás, akkor a méretnek megfelelő határt állítja be a jelenlegi x értéknek.
     *
     * @param rigth Ha True, akkor jobbra mozgás történik, ellenkező esetben balra mozgás.
     */
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

    /**
     * A vízszintes síkon mozgásért felelős függvény. Ha túllépné valamelyik határt a mozgás, akkor a méretnek megfelelő határt állítja be a jelenlegi y értéknek.
     *
     * @param down Ha True, akkor lefele mozgás történik, ellenkező esetben felfele mozgás.
     */
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

    /**
     * Az űrhajó és az életek számának megjelenítéséért felelős függvény. Találat esetén a villogó megjelenését végzi.
     * Ha az űrhajó rendelkezik pajzzsal, akkor meghívja annak a megjelenítésért felelős függvényét.
     *
     * @param g A rajzolást végző Graphics2D objektum.
     */
    public void draw(Graphics2D g) {

        if(!hit)
            g.drawImage(shipImage, (int) xCoordinate, (int) yCoordinate, size_x, size_y, null);
        else if(blinkAnimation() % 2 == 0) {
            g.drawImage(shipImage, (int) xCoordinate, (int) yCoordinate, size_x, size_y, null);
        }

        for(int i = 0; i < health; i++) {
            g.drawImage(healthImage, 15+i*(healthSizeX+10), 42, healthSizeX, healthSizeY, null);
        }

        if(shield != null) {
           shield.draw(g);
        }
    }

    /**
     * Az űrhajó és az élet képének beolvasásáért felelős függvény.
     */
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

    /**
     * A lézer létrehozásáért/lövéséért felelős függvény. Ellenőrzi, hogy van-e extra lézer powerup érvényben,
     * ha igen, akkor annak megfelelően hozza létre a lézereket.
     *
     */
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

    /**
     * A lézer újratöltési idejének ellenőrzését végzi.
     * @return True - Ha újratöltési fázisban van, False egyébként.
     */
    public boolean isRecharging() {
        return (System.currentTimeMillis() - lastLaserShoot) / 1000 < laserRechargeTime;
    }

    /**
     * A sérülés okozta módosításokat végző függvény. A hit változó értéke jelzi, hogy még halhatatlan állappotban van-e a hajó.
     * Ha a pajzs sérül, akkor a hajó nem kerül halhatatlan állapotba. A hajó sérülése esetén a sérülés időpontját menti.
     */
    public void damage() {
        double currentTime = System.currentTimeMillis()/1000.0;
        if(hit == false) {
            if(shield != null)
                shield.damage();
            else {
                health--;
                lastHitTime = currentTime;
                hit = true;
            }
        }
    }

    /**
     * A hajó sérülése utáni villogó megjelenítés animációjáanak időzítéséért felel. Ha letelt a halhatatlansági idő, akkor a hit értékét False-ra állítja.
     *
     * @return Az előző találat óta eltelt idő 10-szerese.
     */
    public int blinkAnimation() {
        double currentTime = System.currentTimeMillis()/1000.0;
        double diff = currentTime - lastHitTime;
        if(diff > invincibleTime)
            hit = false;
        return (int) ((currentTime-lastHitTime)*10);
    }

    /**
     * A hajó élet állapotát visszaadó függvény.
     *
     * @return True - Ha a hajó életben van, egyébként False.
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * A powerup hatását létrehozó függvény
     *
     * @param powerupType A powerup típúsa. 0 - extra lézer. 1 - plusz élet. 2 - pajzs. 3 - rövidebb újratöltési idő.
     */
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

    /**
     * A pajzs állapotát jelző függvény.
     *
     * @return True - Ha van működő pajzs, egyébként False.
     */
    public boolean hasShield() {
        if(shield != null)
            return true;
        return false;
    }

    /**
     * A pajzs elvesztéséért felelős függvény.
     */
    public void loseShield() {
        shield = null;
    }

    /**
     * A játékos űrhajójának sérülés lehetőségének jelzője.
     * @return True - Ha tudja találat érni a hajó, egyébként False.
     */
    public boolean isHitable() {
        return !hit;
    }

    /**
     * Az űrhajó határait visszaadó függvény. Felülírja az ősosztályban található fóggvényt. Ha van pajzs, akkor annak értékét is beleszámolja.
     * @return Ellipszis alakot ad vissza, ami részben illeszkedik a hajó határaira.
     */
    @Override
    public Ellipse2D.Double getSpaceShipBounds() {
        if(shield != null)
            return shield.getShieldBounds();
        return super.getSpaceShipBounds();
    }
}
