package game_elements.entities.spaceships;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * A pajzs osztály.
 */
public class Shield {

    private static List<BufferedImage> imageList;
    static {
        imageList = new ArrayList<>();
        File file1 = new File("./resource/SpaceShooterRedux/PNG/Effects/shield3.png");
        File file2 = new File("./resource/SpaceShooterRedux/PNG/Effects/shield2.png");
        File file3 = new File("./resource/SpaceShooterRedux/PNG/Effects/shield1.png");
        try {
            imageList.add(ImageIO.read(ImageIO.createImageInputStream(file1)));
            imageList.add(ImageIO.read(ImageIO.createImageInputStream(file2)));
            imageList.add(ImageIO.read(ImageIO.createImageInputStream(file3)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private PlayerSpaceShip spaceShip;
    private int shieldHit;
    private int posX, posY, sizeX, sizeY;
    private BufferedImage image;
    private final int timeLast = 20;
    private double startTime;

    /**
     * A pajzs osztály konstruktora
     *
     * @param spaceShip A játkos űrhajójának objektuma.
     */
    public Shield(PlayerSpaceShip spaceShip) {
        this.spaceShip = spaceShip;
        shieldInit();
    }

    /**
     * A pajzs értékeit inícializáló függvény.
     */
    public void shieldInit() {
        shieldHit = 0;
        image = imageList.get(shieldHit);
        sizeX = image.getWidth();
        sizeY = image.getHeight();
        calculatePos();
        startTime = System.currentTimeMillis()/1000.0;
    }

    /**
     * A tulajdonos űrhajó pozíciója és a saját méretei alapján kiszámítja az x és y értéket.
     */
    private void calculatePos() {
        int spaceshipSizeX = spaceShip.getSize().width;
        int spaceshipSizeY = spaceShip.getSize().height;
        posX = spaceShip.getxCoordinate() + spaceshipSizeX/2 - sizeX/2;
        posY = spaceShip.getyCoordinate() + spaceshipSizeY/2 - sizeY/2;
    }

    /**
     * A sérülést megvalósító függvény. találatkor növeli a pajzs találat számlálóját és frissíti a számlálóhoz tartozó képet.
     * Ha a pajzs elérte a maximális találatot, akkor meghívja a tulajdonos objektumnak a pajzsot elveszítő függvényét.
     */
    public void damage() {
        shieldHit++;
        if(shieldHit < imageList.size())
            image = imageList.get(shieldHit);
        else
            spaceShip.loseShield();
    }

    /**
     * A pajzsot frissítő függvény. Meghívja a koordináta számolásáért felelős függvényt. Ellenőrzi mennyi ideje van még a pajzsnak hátra.
     * Ha letelt az idő, akkor meghívja a tulajdonos űrhajó pajzsot elvesztő függvényét.
     */
    public void update() {
        calculatePos();
        if(System.currentTimeMillis()/1000.0 - startTime > timeLast)
            spaceShip.loseShield();
    }

    /**
     * A pajzs megjelenítéséért felelős függvény.
     *
     * @param g A megjelenítésért felelős Graphics2D objektum
     */
    public void draw(Graphics2D g) {
        double timeTicked = System.currentTimeMillis()/1000.0 - startTime;
        if(timeTicked < 16 || (int) (timeTicked*10) % 3 != 0)
            g.drawImage(image, posX, posY, null);
    }

    /**
     * A pajzs határait visszaadó füvvény.
     *
     * @return Ellipszis formát ad vissza, ami részben megegyezik a pajzs határaival.
     */
    public Ellipse2D.Double getShieldBounds() {
        return new Ellipse2D.Double(posX, posY, sizeX, sizeY);
    }
}
