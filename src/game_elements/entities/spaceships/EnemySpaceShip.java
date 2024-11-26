package game_elements.entities.spaceships;

import game_elements.explosions.BigExplosion;
import game_elements.explosions.Explosion;

import java.util.List;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


/**
 * Az ellenséges űrhajók osztálya.
 */
public class EnemySpaceShip extends SpaceShip{


    /**
     * Statikus adattag ami az űrhajók képeit tárolja.
     */
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

    /**
     * Az űrhajó életét jelző érték.
     */
    private int health;
    private BufferedImage image;
    /**
     * A következő x és y koordináták változói.
     */
    private int nextX, nextY;
    /**
     * A horizontális sebesséf komponens.
     */
    double speedX,
    /**
     * A vertikális sebesség komponens
     */
    speedY;
    private List<Laser> laserList;
    private List<Explosion> explosionList;
    private double xDoublecoord, yDoubleCoord;
    private boolean exploded;
    private HealthBar healthBar;

    private EnemySpaceShip() {}
    /**
     * Az ellenséges űrhajó osztályának konstruktora.
     *
     * @param coords        A maximális koordinátát meghatározó érték.
     * @param laserList     A lézerek listájá.
     * @param explosionList A robbanások listája.
     */
    public EnemySpaceShip(Dimension coords, List<Laser> laserList, List<Explosion> explosionList) {
        maxCoordinateOfX = coords.width;
        maxCoordinateOfY = coords.height;
        this.laserList = laserList;
        this.explosionList = explosionList;
        enemyInit();

    }

    /**
     * Az osztály tagváltozóit inicializáló metódus.
     * Meghívja az űrhajó kinézetéért felelős randomizűló függvényt, a következő koordinátát randomizáló függvényt és kiszámítja az első útvonalat.
     */
    private void enemyInit() {
        enemyRandomizer();
        size_x = image.getWidth();
        size_y = image.getHeight();
        health = 3;
        exploded = false;
        speed = 2;
        laserRechargeTime = 1;
        laserSpeed = 15;
        xCoordinate = (int) ((maxCoordinateOfX-size_x)*Math.random());
        xDoublecoord = xCoordinate;
        yCoordinate = -size_y;
        yDoubleCoord = yCoordinate;
        healthBar = new HealthBar(new Dimension((int) xCoordinate, (int) yCoordinate));
        nextCoordRandomizer();
        calculateRoute();

    }

    /**
     * Véletlenszerűen választ az űrhajók kinézetei közül egyet és beállítja azt a kinézetet.
     */
    private void enemyRandomizer() {
        int enemyImageNum = (int) (4*Math.random());
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


    /**
     * Az osztály logikáját tartalmazza. Minden hívással egy lépést tesz a logikában az osztály.
     * Módosítja a jelenlegi koordinátákat a sebesség alapján. Meghívja a megérkezést ellenőrző függvényt, és szükség esetén meghívja a következő
     * koordintátát randomizáló és az útvonalat kiszámító fügvényt.
     * Ha az űrhajó elérte a célját, akkor meghívja a lézer lövéséért felelős függvényt.
     */
    public void update() {

        xDoublecoord += speedX;
        yDoubleCoord += speedY;
        xCoordinate = (int) xDoublecoord;
        yCoordinate = (int) yDoubleCoord;
        healthBar.update(new Dimension((int) xCoordinate+size_x/2,(int)  yCoordinate));
        if(calculateArrival()) {
            nextCoordRandomizer();
            calculateRoute();
            if(!isRecharging()) {
                shootLaser();
            }
        }

    }

    /**
     * Az űrhajó megjelenítéséért felelős függvény.
     * @param g A grafikus megjelenítésért felelős elem.
     */
    public void draw(Graphics2D g) {
        g.drawImage(image, (int) xDoublecoord, (int) yDoubleCoord, null);
        healthBar.draw(g);
    }

    /**
     * A maximum x és y értékek alapján a következő koordintát véletlenszerűen jelöli ki.
     * Az űrhajó nem a teljes vertikális teret használja, csak a 40%-át. A horizontális részt csak a méretének függvényében járja be.
     */
    private void nextCoordRandomizer() {
        nextX = (int) ((maxCoordinateOfX-size_x)*Math.random());
        nextY = (int) ((maxCoordinateOfY*0.4)*Math.random());
    }

    /**
     * Kiszámítja a sebesség érték, a jelenlegi koordináta és a következő koordináta alapján a sebesség x és y irányú komponenseit.
     *
     * @return A két sebesség vektor összege double értékkel.
     */
    public double calculateRoute() {
        double distanceX = Math.abs(nextX - xDoublecoord);
        double distanceY = Math.abs(nextY - yDoubleCoord);

        double routeAngle = Math.atan(distanceY/distanceX);

        speedX = Math.cos(routeAngle)*speed;
        if(nextX < xDoublecoord)
            speedX *= -1;

        speedY = Math.sin(routeAngle)*speed;
        if(nextY < yDoubleCoord)
            speedY *= -1;

        return Math.sqrt(speedX*speedX+speedY*speedY);
    }


    /**
     * A lézer lövéséért felelős függvény. Létrehozza és hozzáadja a lézert a lézer listához. A lövés időpontját elmenti.
     */
    public void shootLaser() {
        xCoordinate = (int) xDoublecoord;
        yCoordinate = (int) yDoubleCoord;
        Laser laser = new Laser(this, laserSpeed, explosionList, 0);
        laserList.add(laser);
        lastLaserShoot = System.currentTimeMillis()/1000.0;
    }

    /**
     * Ellenőrzi, hogy az űrhajó újratöltési fázisban van-e.
     * @return True - Ha még mindig újratöltési fázisban van. False - Ha már letelt az újratöltés.
     */
    public boolean isRecharging() {
        double currentTime = System.currentTimeMillis()/1000.0;
        return currentTime - lastLaserShoot < laserRechargeTime;
    }

    /**
     * A következő koordinátára érkezést ellenőrző függvény.
     * @return True - Ha megérkezett a következő koordintára. False - Ha még nem érkezett meg.
     */
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

        return lowX >= highX && lowY >= highY;
    }

    /**
     * A robbanás létrehozásáért felelős függvény. Létrehoz egy robbanás objektumok, átadja neki a koordinátáit, és átadja a robbanások listájának.
     */
    private void explode() {
        int explosionPosX = (int) xCoordinate+size_x/2;
        int explosionPosY = (int) yCoordinate+size_y/2;
        BigExplosion bigExplosion = new BigExplosion(new Dimension(explosionPosX, explosionPosY));
        explosionList.add(bigExplosion);
        exploded = true;
    }

    /**
     * Az űrhajó állapotát jelző függvény.
     *
     * @return True - Ha az űrhajó felrobbant. False - Ha az űrhajó még életben van.
     */
    public boolean isExploded() {
        return exploded;
    }

    /**
     * Az űrhajót érő sérülésért felelős függvény. Sérülés esetén csökkneti az űrhajó életét és meghívja a HealthBar sérülésért felelős függvényét.
     * Ha az élet elfogyott, akkor meghívja a robbanásért felelős függvényt.
     */
    public void damageShip() {
        health--;
        healthBar.damage();
        if(health == 0)
            explode();
    }

    /**
     * Az életet jelző függvény.
     *
     * @return Az űrhajó jelenlegi életszámát adja.
     */
    public int getHealth() {
        return health;
    }
}
