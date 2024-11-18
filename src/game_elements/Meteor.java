package game_elements;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class Meteor extends GameEntity {

    private int meteorSize;
    private boolean outOfBounds;
    private List<Explosion> explosionList;
    private BufferedImage image;
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

    public Meteor(Dimension coords, List<Explosion> explosionList) {
        maxCoordinateOfX = coords.width;
        maxCoordinateOfY = coords.height;
        this.explosionList = explosionList;
        meteorInit();
    }

    public void meteorInit() {
        meteorRandomizer();
        yCoordinate = -size_y;
        outOfBounds = false;
    }



    public void update() {
        yCoordinate += speed;
        if(yCoordinate > maxCoordinateOfY)
            outOfBounds = true;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(image, xCoordinate, yCoordinate, null);
    }

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
        xCoordinate = (int) ((maxCoordinateOfX - size_x)*Math.random());
        speed = (int) (4 + 6*Math.random());
    }

    public boolean isOutOfBounds() {
        return outOfBounds;
    }

    public Ellipse2D.Double getMeteorBounds() {
        return new Ellipse2D.Double(xCoordinate, yCoordinate, size_x, size_y);
    }

    public void explode() {
        int explosionPosX = xCoordinate+size_x/2;
        int explosionPosY = yCoordinate+size_y/2;
        explosionList.add(new BigExplosion(new Dimension(explosionPosX, explosionPosY)));
        outOfBounds = true;
    }
}
