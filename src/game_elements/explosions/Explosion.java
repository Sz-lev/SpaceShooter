package game_elements.explosions;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public abstract class Explosion {
    protected int xPos, yPos, entityPosX, entityPosY;

    protected List<BufferedImage> imageList;
    protected double animationTime;
    protected double lastTime;
    protected int imageCount;
    protected boolean animationEnd;

    public Explosion() {
        explosionInit();
    }
    protected void explosionInit() {
        lastTime = System.currentTimeMillis()/1000.0;
        animationEnd = false;
        imageCount = 0;
    }

    public void draw(Graphics2D g) {
        g.drawImage(imageList.get(imageCount), xPos, yPos, null);
    }

    public void update() {
        if(imageCount == imageList.size()-1) {
            animationEnd = true;
        } else if(System.currentTimeMillis()/1000.0 - lastTime > animationTime) {
            lastTime = System.currentTimeMillis()/1000.0;
            imageCount++;
            xPos = entityPosX - imageList.get(imageCount).getWidth()/2;
            yPos = entityPosY - imageList.get(imageCount).getHeight()/2;
        }
    }
    public boolean isAnimationEnd() {
        return animationEnd;
    }

}
