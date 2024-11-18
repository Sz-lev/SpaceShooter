package game_elements.background;

import gamewindow.GamePanel;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class BackGround {

    private BufferedImage background1, background2;

    private int background1_y;
    private int background2_y;
    private int speed;

    public BackGround() {
        background1_y = 0;
        background2_y = -720;
        speed = 4;
        getBackgroundImage();
    }

    public void update() {
        background1_y += speed;
        background2_y += speed;
        if(background1_y >= 720) {
            background1_y = -720;
        } else if(background2_y >= 720) {
            background2_y = -720;
        }
    }

    public void draw(Graphics2D g) {
        g.drawImage(background1, 0, background1_y, 1080, 720, null);
        g.drawImage(background2, 0, background2_y, 1080, 720, null);
    }
    public void getBackgroundImage() {
        File imageFile = new File("./resource/SpaceShooterRedux/Backgrounds/blue.png");
        try {
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(imageFile);
            background1 = ImageIO.read(imageInputStream);
            background2 = background1;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
