package game_elements.entities.spaceships;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    public Shield(PlayerSpaceShip spaceShip) {
        this.spaceShip = spaceShip;
        shieldInit();
    }

    public void shieldInit() {
        shieldHit = 0;
        image = imageList.get(shieldHit);
        sizeX = image.getWidth();
        sizeY = image.getHeight();
        calculatePos();
    }

    private void calculatePos() {
        int spaceshipSizeX = spaceShip.getSize().width;
        int spaceshipSizeY = spaceShip.getSize().height;
        posX = spaceShip.getxCoordinate() + spaceshipSizeX/2 - sizeX/2;
        posY = spaceShip.getyCoordinate() + spaceshipSizeY/2 - sizeY/2;
    }

    public void damage() {
        shieldHit++;
        if(shieldHit < imageList.size())
            image = imageList.get(shieldHit);
        else
            spaceShip.loseShield();
    }

    public void update() {
        calculatePos();
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, posX, posY, null);
    }

    public Ellipse2D.Double getShieldBounds() {
        return new Ellipse2D.Double(posX, posY, sizeX, sizeY);
    }
}
