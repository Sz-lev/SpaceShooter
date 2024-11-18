package game_elements.explosions;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BigExplosion extends Explosion{

    protected static List<BufferedImage> staticImageList;
    static {
        staticImageList = new ArrayList<>();
        File file1 = new File("./resource/SpaceShooterRedux/PNG/Damage/explosion/boom1.png");
        File file2 = new File("./resource/SpaceShooterRedux/PNG/Damage/explosion/boom3.png");
        File file3 = new File("./resource/SpaceShooterRedux/PNG/Damage/explosion/boom5.png");
        File file4 = new File("./resource/SpaceShooterRedux/PNG/Damage/explosion/boom6.png");
        try {
            BufferedImage expImage1 = ImageIO.read(ImageIO.createImageInputStream(file1));
            BufferedImage expImage2 = ImageIO.read(ImageIO.createImageInputStream(file2));
            BufferedImage expImage3 = ImageIO.read(ImageIO.createImageInputStream(file3));
            BufferedImage expImage4 = ImageIO.read(ImageIO.createImageInputStream(file4));
            staticImageList.add(expImage1);
            staticImageList.add(expImage2);
            staticImageList.add(expImage3);
            staticImageList.add(expImage4);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private BigExplosion() {}
    public BigExplosion(Dimension coords) {
        entityPosX = coords.width;
        entityPosY = coords.height;
        xPos = entityPosX - imageList.getFirst().getWidth()/2;
        yPos = entityPosY - imageList.getFirst().getHeight()/2;
        explosionInit();
    }

    protected void explosionInit() {
        imageList = staticImageList;
        animationTime = 0.05;
    }
}
