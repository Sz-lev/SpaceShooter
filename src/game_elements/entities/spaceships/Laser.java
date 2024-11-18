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


    public Laser(SpaceShip spaceShip, int speed, List<Explosion> explosionList, int side) {
        maxCoordinateOfX = spaceShip.maxCoordinateOfX;
        maxCoordinateOfY = spaceShip.maxCoordinateOfY;
        this.spaceShip = spaceShip;
        this.speed = speed;
        this.side = side;
        this.explosionList = explosionList;
        laserInit();
    }

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

    public void update() {
        if(!isOutOfBounds) {
            yCoordinate += speed;
            checkPosition();
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(image, xCoordinate, yCoordinate, null);
    }

    private void checkPosition() {
        if(speed > 0 && yCoordinate > maxCoordinateOfY) {
            isOutOfBounds = true;
        } else if(speed < 0 && yCoordinate < -size_y) {
            isOutOfBounds = true;
        }
    }

    public Rectangle getLaserBounds() {
        return new Rectangle(xCoordinate, yCoordinate, size_x, size_y);
    }

    public boolean isPlayerLaser() {
        return speed < 0;
    }

    public void hitEntity() {
        int explosionPosX = xCoordinate+size_x/2;
        int explosionPosY;
        if(speed > 0)
            explosionPosY = yCoordinate+size_y;
        else
            explosionPosY = yCoordinate;
        explosionList.add(new SmallExplosion(new Dimension(explosionPosX, explosionPosY)));
        isOutOfBounds = true;
    }
}
