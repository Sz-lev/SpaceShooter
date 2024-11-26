package game_elements.entities;

import game_elements.entities.GameEntity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

/**
 * A powerup osztály.
 */
public class PowerUp extends GameEntity {

    private static List<BufferedImage> powerupImageList;
    static {
        powerupImageList = new ArrayList<>();
        try {
            File file1 = new File("./resource/SpaceShooterRedux/PNG/Power-ups/selected/laser.png");
            File file2 = new File("./resource/SpaceShooterRedux/PNG/Power-ups/selected/pill.png");
            File file3 = new File("./resource/SpaceShooterRedux/PNG/Power-ups/selected/shield.png");
            File file4 = new File("./resource/SpaceShooterRedux/PNG/Power-ups/selected/star.png");
            powerupImageList.add(ImageIO.read(ImageIO.createImageInputStream(file1)));
            powerupImageList.add(ImageIO.read(ImageIO.createImageInputStream(file2)));
            powerupImageList.add(ImageIO.read(ImageIO.createImageInputStream(file3)));
            powerupImageList.add(ImageIO.read(ImageIO.createImageInputStream(file4)));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage puImage;
    private int powerupType;

    public boolean isOutOfBounds;

    public PowerUp(Dimension screen) {
        maxCoordinateOfX = screen.width;
        maxCoordinateOfY = screen.height;

        powerupInit();
    }

    /**
     * Az értékek inícializásáért felelős függvény.
     */
    public void powerupInit() {
        speed = 5;
        powerupType = (int) (4*Math.random());
        puImage = powerupImageList.get(powerupType);
        size_x = puImage.getWidth();
        size_y = puImage.getHeight();
        xCoordinate = (int) ((maxCoordinateOfX - size_x)*Math.random());
        yCoordinate = -size_y;
        isOutOfBounds = false;

    }

    /**
     * Frissíti a powerup pozícióját és ellenőrzi, hogy még a határain belül tartózkodik-e.
     */
    public void update() {
        yCoordinate += speed;
        if(yCoordinate > maxCoordinateOfY)
            isOutOfBounds = true;
    }

    /**
     * A megjelenítésért felelős függvény.
     * @param g A megjelenítést végző Graphics2D objektum.
     */
    public void draw(Graphics2D g) {
        g.drawImage(puImage, (int) xCoordinate, (int) yCoordinate, null);
    }

    /**
     * A powerup határait visszaadó függvény.
     * A powerup köré egy téglalapot von, ami a határait jelzi.
     *
     * @return Egy Rectangle objektumként a határát.
     */
    public Rectangle getPowerUpBounds() {
        return new Rectangle((int) xCoordinate, (int) yCoordinate, size_x, size_y);
    }

    /**
     * A powerup típusát adja vissza int értékként.
     *
     * @return 0 - extra lézer, 1 - extra élet, 2 - pajzs, 3 - rövidebb újratöltődési idő.
     */
    public int getType() {
        return powerupType;
    }
}
