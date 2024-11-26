package game_elements.entities;

import game_elements.entities.GameEntity;
import game_elements.explosions.BigExplosion;
import game_elements.explosions.Explosion;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Meteort megvalósító osztály
 */
public class Meteor extends GameEntity {


    private static BufferedImage meteorSmall, meteorMedium, meteorBig;
    static {
        File small = new File("./resource/SpaceShooterRedux/PNG/Meteors/meteorBrown_small1.png");
        File medium = new File("./resource/SpaceShooterRedux/PNG/Meteors/meteorBrown_med1.png");
        File big = new File("./resource/SpaceShooterRedux/PNG/Meteors/meteorBrown_big4.png");
        try {
            meteorSmall = ImageIO.read(ImageIO.createImageInputStream(small));
            meteorMedium = ImageIO.read(ImageIO.createImageInputStream(medium));
            meteorBig = ImageIO.read(ImageIO.createImageInputStream(big));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int meteorSize;
    private boolean outOfBounds;
    private List<Explosion> explosionList;
    private BufferedImage image;
    private int endPosX;
    private double speedX, speedY;

    /**
     * Konstruktor
     *
     * @param coords        A maximális koordináta értéke.
     * @param explosionList A robbanások listája.
     */
    public Meteor(Dimension coords, List<Explosion> explosionList) {
        maxCoordinateOfX = coords.width;
        maxCoordinateOfY = coords.height;
        this.explosionList = explosionList;
        meteorInit();
    }

    /**
     * Az objektumok értékeit inícializáló függvény.
     */
    public void meteorInit() {
        meteorRandomizer();
        yCoordinate = -size_y;
        outOfBounds = false;
        calculateRoute();
    }


    /**
     * Frissíti a meteor értékeit az idő múlásával.
     * A jelenlegi x és y értékeket a sebességnek megfelelően változtatja.
     * Ellenőrzi, hogy a meteor a határokon belül tartozkodik-e. Ha nem, úgy az outOfBounds értékét True-ra állítja.
     */
    public void update() {
        xCoordinate += speedX;
        yCoordinate += speedY;

        if(yCoordinate > maxCoordinateOfY)
            outOfBounds = true;
    }

    /**
     * A megjelenítésért felelős függvény.
     * @param g A megjelenítést végző Graphics2D objektum.
     */
    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, (int) xCoordinate, (int) yCoordinate, null);
    }

    /**
     * A meteor randomizált megjelenéséért felelős függvény. Véletlenszerűen választ a meteor típusok közül, állítja be az x értékét, választja ki a végpontot és a sebességet.
     * A sebesség értéke 4-9 értéket vehetnek fel.
     */
    private void meteorRandomizer() {
        meteorSize = (int) (3*Math.random());
        switch(meteorSize) {
            case 0: {
                image = meteorSmall;
                break;
            }
            case 1: {
                image = meteorMedium;
                break;
            }
            case 2: {
                image = meteorBig;
                break;
            }
        }
        size_x = image.getWidth();
        size_y = image.getHeight();

        xCoordinate =  (maxCoordinateOfX - size_x)*Math.random();
        speed = (int) (4 + 6*Math.random());
        endPosX =(int) ((maxCoordinateOfX - size_x)*Math.random());
        yCoordinate = -size_y;
    }

    /**
     * Kiszámolja a jelenlegi pont és a végpont közötti úthoz és a sebességhez tartozó x és y komponensű sebességet.
     */
    private void calculateRoute() {
        double distanceX = endPosX - xCoordinate;
        double distanceY = maxCoordinateOfY - yCoordinate;

        double angleOfRoute = Math.atan((distanceY/distanceX));
        speedX = Math.cos(angleOfRoute)*speed;
        if(endPosX < xCoordinate)
            speedX *= -1;

        speedY = Math.sin(angleOfRoute)*speed;
        speedY =Math.abs(speedY);
    }

    /**
     * A határokon kívüliség jelzésére szolgáló függvény.
     *
     * @return True - Ha a kijelző kívűlre került, egyébként False.
     */
    public boolean isOutOfBounds() {
        return outOfBounds;
    }

    /**
     * A meteor határait visszaadó függvény.
     * Egy ellpiszis formát von a meteor köré, ami a határait reprezentálja.
     * @return Az ellipszis alakot adja.
     */
    public Ellipse2D.Double getMeteorBounds() {
        return new Ellipse2D.Double(xCoordinate, yCoordinate, size_x, size_y);
    }

    /**
     * A robbanás létrehozásáért és a robbanás listáho adásért felelős függvény.
     * Az outOfBounds értékét True-ra állítja, amivel jelzi, hogy már nincs szükség a meteorra.
     */
    public void explode() {
        int explosionPosX = (int) xCoordinate+size_x/2;
        int explosionPosY = (int) yCoordinate+size_y/2;
        explosionList.add(new BigExplosion(new Dimension(explosionPosX, explosionPosY)));
        outOfBounds = true;
    }
}
