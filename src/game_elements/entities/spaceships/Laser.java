package game_elements.entities.spaceships;

import game_elements.explosions.Explosion;
import game_elements.entities.GameEntity;
import game_elements.explosions.SmallExplosion;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * A lézer osztálya.
 */
public class Laser extends GameEntity {

    private SpaceShip spaceShip;
    public boolean isOutOfBounds;
    private static BufferedImage laserImage, reversedLaserImage;
    private int side;
    private List<Explosion> explosionList;
    private BufferedImage image;

    static {
        File file1 = new File("./resource/SpaceShooterRedux/PNG/Lasers/laserRed01.png");
        File file2 = new File("./resource/SpaceShooterRedux/PNG/Lasers/laserRed01_reversed.png");
        try {
            laserImage = ImageIO.read(ImageIO.createImageInputStream(file1));
            reversedLaserImage = ImageIO.read(ImageIO.createImageInputStream(file2));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * Beállítja a tulajdonos űrhajót magának, a sebességét, a robbanás listát, és hogy melyik oldalról lövi ki a hajó.
     * Az űrhajótól megkapja a max x és y érétéket. Meghívja az inícializáló függvényt.
     * @param spaceShip A tulajdonos űrhajó.
     * @param speed A sebesség értéke.
     * @param explosionList A robbanások listája.
     * @param side A kilövés oldala. 0 - Közép, 1 - Bal, 2 - jobb.
     */
    public Laser(SpaceShip spaceShip, int speed, List<Explosion> explosionList, int side) {
        maxCoordinateOfX = spaceShip.maxCoordinateOfX;
        maxCoordinateOfY = spaceShip.maxCoordinateOfY;
        this.spaceShip = spaceShip;
        this.speed = speed;
        this.side = side;
        this.explosionList = explosionList;
        laserInit();
    }

    /**
     * Beállítja a méretét a kép méretének megfelelően.
     * A kilövés oldalának megfelelően és a tulajdonos űrhajó koordinátái alapján kiszámolja a saját koordinátáit.
     * A sebességnek megfelelően kiválasztja, hogy a rendes vagy a megfordított képet használja.
     */
    public void laserInit() {
        size_y = laserImage.getHeight();
        size_x = laserImage.getWidth();
        switch (side) {
            case 0:
                xCoordinate = spaceShip.getxCoordinate()+ (spaceShip.getSize().width-size_x)/2;
                yCoordinate = spaceShip.getyCoordinate();
                break;
            case 1:
                xCoordinate = spaceShip.getxCoordinate() + size_x/2;
                yCoordinate = spaceShip.getyCoordinate();
                break;
            case 2:
                xCoordinate = spaceShip.getxCoordinate() + spaceShip.getSize().width-size_x;
                yCoordinate = spaceShip.getyCoordinate();
                break;
        }
        isOutOfBounds = false;
        if(speed > 0)
            image = reversedLaserImage;
        else
            image = laserImage;
    }

    /**
     * A lézer y-tengelyű mozgását frissíti.
     * Meghívja a határokat ellenőrző függvényt.
     */
    public void update() {
        if(!isOutOfBounds) {
            yCoordinate += speed;
            checkPosition();
        }
    }

    /**
     * A lézer megjelenítéséért felelős függvény.
     * @param g A megjelenítést végző Graphics2D objektum.
     */
    public void draw(Graphics2D g) {
        g.drawImage(image, (int) xCoordinate, (int) yCoordinate, null);
    }

    /**
     * A pozíciót ellenőrző függvény.
     * Ellenőrzi, hogy a sebességnek és a jelenlegi pozíciónak megfelelően átlépte-e már a határt.
     * Ha átlépte, akkor az outOfBounds értékét True-ra állítja.
     */
    private void checkPosition() {
        if(speed > 0 && yCoordinate > maxCoordinateOfY) {
            isOutOfBounds = true;
        } else if(speed < 0 && yCoordinate < -size_y) {
            isOutOfBounds = true;
        }
    }

    /**
     * A lézer körvonal határait adja vissza egy téglalap alakzatként.
     * @return A lézer köré rajzolt téglalap.
     */
    public Rectangle getLaserBounds() {
        return new Rectangle((int) xCoordinate, (int) yCoordinate, size_x, size_y);
    }

    /**
     * Visszaadja, hogy a lézer a játékoshoz tartozik-e.
     * @return True - Ha a lézer a játékoshoz tartozik, egyébként False.
     */
    public boolean isPlayerLaser() {
        return speed < 0;
    }

    /**
     * A találatot jelző függvény.
     * Létrehoz egy kis robbanás objektumok, amit elhelyez a robbanások listájában.
     * Az outOfBounds értékét igazra állítja.
     */
    public void hitEntity() {
        int explosionPosX = (int) xCoordinate+size_x/2;
        int explosionPosY;
        if(speed > 0)
            explosionPosY = (int) yCoordinate+size_y;
        else
            explosionPosY = (int) yCoordinate;
        explosionList.add(new SmallExplosion(new Dimension(explosionPosX, explosionPosY)));
        isOutOfBounds = true;
    }
}
