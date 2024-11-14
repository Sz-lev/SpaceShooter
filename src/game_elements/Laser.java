package game_elements;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Laser extends GameEntity{

    private SpaceShip spaceShip;
    private BufferedImage image;
    public boolean isOutOfBounds;
    public Laser(SpaceShip spaceShip, int speed) {
        this.spaceShip = spaceShip;
        this.speed = speed;
        laserInit();
    }

    public void laserInit() {
        getLaserImage();
        xCoordinate = spaceShip.xCoordinate+ (spaceShip.size_x-size_x)/2;
        yCoordinate = spaceShip.yCoordinate;
        isOutOfBounds = false;
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

    private void getLaserImage() {
        File file = new File("./resource/SpaceShooterRedux/PNG/Lasers/laserRed01.png");
        try {
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(file);
            image = ImageIO.read(imageInputStream);
            size_y = image.getHeight();
            size_x = image.getWidth();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
}
