package game_elements.explosions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SmallExplosion extends Explosion{
    protected static List<BufferedImage> staticImageList;
    static {
        staticImageList = new ArrayList<>();
        File file1 = new File("./resource/SpaceShooterRedux/PNG/Lasers/laserRed09.png");
        File file2 = new File("./resource/SpaceShooterRedux/PNG/Lasers/laserRed08.png");
        try {
            BufferedImage expImage1 = ImageIO.read(ImageIO.createImageInputStream(file1));
            BufferedImage expImage2 = ImageIO.read(ImageIO.createImageInputStream(file2));
            staticImageList.add(expImage1);
            staticImageList.add(expImage2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private SmallExplosion() {}
    public SmallExplosion(Dimension coords) {
        entityPosX = coords.width;
        entityPosY = coords.height;
        xPos = entityPosX - imageList.getFirst().getWidth()/2;
        yPos = entityPosY - imageList.getFirst().getHeight()/2;
        explosionInit();
    }

    protected void explosionInit() {
        imageList = staticImageList;
        animationTime = 0.1;
    }
}
