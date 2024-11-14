package game_elements;

import gamewindow.GameKeyListener;
import gamewindow.GamePanel;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class PlayerSpaceShip extends SpaceShip{

    private GamePanel gp;
    private BufferedImage image;
    private List<Laser> laserList;
    private double laserRechargeTime;
    private double lastLaserShoot;
    public PlayerSpaceShip(GamePanel gamepanel, List<Laser> laserList) {
        gp = gamepanel;
        this.laserList = laserList;
        playerInit();
    }

    public void playerInit() {
        size_x = 99;
        size_y = 75;
        maxCoordinateOfX = gp.getScreenDimension().width;
        maxCoordinateOfY = gp.getScreenDimension().height;
        xCoordinate = (maxCoordinateOfX-size_x)/2;
        yCoordinate = (maxCoordinateOfY - size_y)/2;
        speed = 8;
        laserRechargeTime = 0.5;
        getPlayerImage();
    }



    public void update() {
        GameKeyListener gameKL = gp.getGameKeyListener();
        if(gameKL.up && !gameKL.down) {
            moveY(false);
        } else if(gameKL.down && !gameKL.up) {
            moveY(true);
        }
        if(gameKL.left && !gameKL.right) {
            moveX(false);
        } else if(gameKL.right && !gameKL.left) {
            moveX(true);
        }
        if(gameKL.space && !isRecharging()) {
            shootLaser();
        }
    }
    public void moveX(boolean rigth) {
        if(rigth && xCoordinate >=maxCoordinateOfX-size_x)
            xCoordinate = maxCoordinateOfX-size_x;
        else if (!rigth && xCoordinate <= speed)
            xCoordinate = 0;
        else if(rigth) {
            xCoordinate += speed;
        } else {
            xCoordinate -= speed;
        }

    }
    public void moveY(boolean down) {
        if(down && yCoordinate >=maxCoordinateOfY-size_y)
            yCoordinate = maxCoordinateOfY-size_y;
        else if (!down && yCoordinate <= speed)
            yCoordinate = 0;
        else if(down) {
            yCoordinate += speed;
        } else if(!down) {
            yCoordinate -= speed;
        }
    }

    public void draw(Graphics2D g) {

        g.drawImage(image, xCoordinate, yCoordinate, size_x, size_y, null);
    }

    public void getPlayerImage() {

        try {
            File imageFile = new File("./resource/SpaceShooterRedux/PNG/Player/playerShip1_red.png");
            ImageInputStream imageInputStream = ImageIO.createImageInputStream(imageFile);
            image = ImageIO.read(imageInputStream);
            size_x = image.getWidth();
            size_y = image.getHeight();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void shootLaser() {
        Laser laser = new Laser(this, -15);
        laser.setBounds(new Dimension(maxCoordinateOfX, maxCoordinateOfY));
        laserList.add(laser);
        lastLaserShoot = System.currentTimeMillis();
    }

    private boolean isRecharging() {
        return (System.currentTimeMillis() - lastLaserShoot) / 1000 < laserRechargeTime;
    }

}
