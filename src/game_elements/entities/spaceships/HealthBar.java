package game_elements.entities.spaceships;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Az életjelzősáv osztálya.
 */
public class HealthBar {


    private static BufferedImage maxHealth, yellowHealth, redHealth, blankHealth;
    private static List<BufferedImage> imageList;

    static {
        imageList = new ArrayList<>();
        File file1 = new File("./resource/SpaceShooterRedux/PNG/HealthBar/VIDA_10.png");
        File file2 = new File("./resource/SpaceShooterRedux/PNG/HealthBar/VIDA_6.png");
        File file3 = new File("./resource/SpaceShooterRedux/PNG/HealthBar/VIDA_2.png");
        File file4 = new File("./resource/SpaceShooterRedux/PNG/HealthBar/VIDA_0.png");
        try {
            maxHealth = ImageIO.read(ImageIO.createImageInputStream(file1));
            yellowHealth = ImageIO.read(ImageIO.createImageInputStream(file2));
            redHealth = ImageIO.read(ImageIO.createImageInputStream(file3));
            blankHealth = ImageIO.read(ImageIO.createImageInputStream(file4));
            imageList.add(maxHealth);
            imageList.add(yellowHealth);
            imageList.add(redHealth);
            imageList.add(blankHealth);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int posX, posY, sizeX, sizeY;
    private BufferedImage image;
    private int healthLostCounter;
    private final int imageScale = 3;

    public HealthBar(Dimension coords) {
        healthLostCounter = 0;
        image = imageList.get(healthLostCounter);
        sizeX = image.getWidth()/imageScale;
        sizeY = image.getHeight()/imageScale;
        posX = coords.width;
        posY = coords.height;
    }

    /**
     * Az életjelző koordintáját frissítő függvény.
     * @param coords Az új koordintáta értéke.
     */
    public void update(Dimension coords) {
        posX = coords.width-sizeX/2;
        posY = coords.height-sizeY-10;
    }

    /**
     * Az életjelző rajzolásáért felelős függvény.
     * @param g A rajzolást végző Graphics2D objektum.
     */
    public void draw(Graphics2D g) {
        g.drawImage(image, posX, posY, sizeX, sizeY, null);
    }

    /**
     * A sérülést jelző függvény. Növeli az osztály sérülés számlálóját, ami a megjelenített képért felel.
     * Ha a sérülés mértéke elérte az határt, akkor nem növeli tovább.
     */
    public void damage() {
        if(healthLostCounter < imageList.size()-1) {
            healthLostCounter++;
            image = imageList.get(healthLostCounter);
        }
    }

    /**
     * A sérülés számlálóját visszaadó függvény.
     * @return A sérülés mértéke.
     */
    public int getHealthLostCounter() {
        return healthLostCounter;
    }
}
