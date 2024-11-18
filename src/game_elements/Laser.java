package game_elements;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Laser extends GameEntity{

    private SpaceShip spaceShip;
    public boolean isOutOfBounds;
    private static BufferedImage laserImage, reversedLaserImage;
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


    public Laser(SpaceShip spaceShip, int speed, List<Explosion> explosionList) {
        this.spaceShip = spaceShip;
        this.speed = speed;
        this.explosionList = explosionList;
        laserInit();
    }

    public void laserInit() {
        size_y = laserImage.getHeight();
        size_x = laserImage.getWidth();
        xCoordinate = spaceShip.xCoordinate+ (spaceShip.size_x-size_x)/2;
        yCoordinate = spaceShip.yCoordinate;
        isOutOfBounds = false;
        if(speed > 0)
            image = reversedLaserImage;
        else
            image = laserImage;
    }

    @Override
    public void update() {
        if(!isOutOfBounds) {
            yCoordinate += speed;
            checkPosition();
        }
    }

    @Override
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

    public void setBounds(Dimension boundary) {
        maxCoordinateOfY = boundary.height;
        maxCoordinateOfX = boundary.width;
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
