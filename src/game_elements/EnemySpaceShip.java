package game_elements;

import java.util.List;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;



public class EnemySpaceShip extends SpaceShip{


    private static BufferedImage ship1, ship2, ship3, ship4;
    static {
        File file1 = new File("./resource/SpaceShooterRedux/PNG/Enemies/enemyBlack1.png");
        File file2 = new File("./resource/SpaceShooterRedux/PNG/Enemies/enemyBlue1.png");
        File file3 = new File("./resource/SpaceShooterRedux/PNG/Enemies/enemyGreen1.png");
        File file4 = new File("./resource/SpaceShooterRedux/PNG/Enemies/enemyRed1.png");
        try {
            ship1 = ImageIO.read(ImageIO.createImageInputStream(file1));
            ship2 = ImageIO.read(ImageIO.createImageInputStream(file2));
            ship3 = ImageIO.read(ImageIO.createImageInputStream(file3));
            ship4 = ImageIO.read(ImageIO.createImageInputStream(file4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int health;
    private BufferedImage image;
    private int nextX, nextY;
    double speedX, speedY;
    private List<EnemySpaceShip> enemyList;
    private List<Laser> laserList;
    private double xDoublecoord, yDoubleCoord;

    public EnemySpaceShip(int x, int y, int speed) {
        super(x, y, speed);
    }

    public EnemySpaceShip(Dimension coords, List<EnemySpaceShip> enemyList, List<Laser> laserList) {
        maxCoordinateOfX = coords.width;
        maxCoordinateOfY = coords.height;
        this.enemyList = enemyList;
        this.laserList = laserList;
        enemyInit();

    }

    private void enemyInit() {
        enemyRandomizer();
        size_x = image.getWidth();
        size_y = image.getHeight();
        health = 4;
        speed = 2;
        laserRechargeTime = 2;
        xDoublecoord = (int) ((maxCoordinateOfX-size_x)*Math.random());
        yDoubleCoord = -size_y;
        nextCoordRandomizer();
        calculateRoute();
    }

    private void enemyRandomizer() {
        int enemyImageNum = (int) (4*Math.random());
        System.out.println(enemyImageNum);
        switch (enemyImageNum) {
            case 0: {
                image = ship1;
                break;
            }
            case 1: {
                image = ship2;
                break;
            }
            case 2: {
                image = ship3;
                break;
            }
            case 3: {
                image = ship4;
                break;
            }
        }
    }




    public void update() {

        xDoublecoord += speedX;
        yDoubleCoord += speedY;
        if(calculateArrival()) {
            nextCoordRandomizer();
            calculateRoute();
            if(!isRecharging())
                shootLaser();
        }


    }


    public void draw(Graphics2D g) {
        g.drawImage(image, (int) xDoublecoord, (int) yDoubleCoord, null);
    }

    private void nextCoordRandomizer() {
        nextX = (int) ((maxCoordinateOfX-size_x)*Math.random());
        nextY = (int) ((maxCoordinateOfY*0.4)*Math.random());
    }

    public void calculateRoute() {
        double distanceX = Math.abs(nextX - xDoublecoord);
        double distanceY = Math.abs(nextY - yDoubleCoord);

        double routeAngle = Math.atan(distanceY/distanceX);


        speedX = Math.cos(routeAngle)*speed;
        if(nextX < xDoublecoord)
            speedX *= -1;
        speedY = Math.sin(routeAngle)*speed;
        if(nextY < yDoubleCoord)
            speedY *= -1;

    }

    private void shootLaser() {
        xCoordinate = (int) xDoublecoord;
        yCoordinate = (int) yDoubleCoord;
        Laser laser = new Laser(this, 15);
        laser.setBounds(new Dimension(maxCoordinateOfX, maxCoordinateOfY));
        laserList.add(laser);
        lastLaserShoot = System.currentTimeMillis();
    }

    public boolean isRecharging() {
        return System.currentTimeMillis() - lastLaserShoot < laserRechargeTime;
    }

    public boolean calculateArrival() {
        double lowX, highX, lowY, highY;
        if(speedX < 0) {
            lowX = nextX;
            highX = xDoublecoord;
        } else {
            lowX = xDoublecoord;
            highX = nextX;
        }
        if(speedY < 0) {
            lowY = nextY;
            highY = yDoubleCoord;
        } else {
            lowY = yDoubleCoord;
            highY = nextY;
        }

        return lowX > highX && lowY > highY;
    }


}
