package game_elements.entities.spaceships;

import static org.junit.jupiter.api.Assertions.*;

import game_elements.explosions.Explosion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

class EnemyShipTest {

    ArrayList<Laser> laserList;
    ArrayList<Explosion> explosionList;

    @BeforeEach
    public void setUp() {
        laserList = new ArrayList<>();
        explosionList = new ArrayList<>();
    }

    @Test
    public void shootLaser() {
        EnemySpaceShip enemy = new EnemySpaceShip(new Dimension(0, 0), laserList, explosionList);
        enemy.shootLaser();
        assertEquals(1, laserList.size());
    }

    @Test
    public void shootMoreLaser() {
        EnemySpaceShip enemy = new EnemySpaceShip(new Dimension(0, 0), laserList, explosionList);
        enemy.shootLaser();
        enemy.shootLaser();
        enemy.shootLaser();
        assertEquals(3, laserList.size());
    }

    @Test
    public void isRecharging() {
        EnemySpaceShip enemy = new EnemySpaceShip(new Dimension(0, 0), laserList, explosionList);
        assertFalse(enemy.isRecharging());
        enemy.shootLaser();
        assertTrue(enemy.isRecharging());
    }

    @Test
    public void calculatedSpeed() {
        EnemySpaceShip enemy = new EnemySpaceShip(new Dimension(400, 400), laserList, explosionList);
        assertEquals(true, enemy.calculateRoute() <= 2 && enemy.calculateRoute() > 1.9);
    }


    @Test
    public void damage() {
        EnemySpaceShip enemy = new EnemySpaceShip(new Dimension(400, 400), laserList, explosionList);
        assertEquals(3, enemy.getHealth());
        enemy.damageShip();
        assertEquals(2, enemy.getHealth());
    }

    @Test
    public void exploded() {
        EnemySpaceShip enemy = new EnemySpaceShip(new Dimension(400, 400), laserList, explosionList);
        assertFalse(enemy.isExploded());
        assertEquals(3, enemy.getHealth());
        enemy.damageShip();
        enemy.damageShip();
        enemy.damageShip();
        assertTrue(enemy.isExploded());
    }

    @Test
    public void healthbarTest() {
        HealthBar hBar = new HealthBar(new Dimension(20, 20));
        assertEquals(0, hBar.getHealthLostCounter());
        hBar.damage();
        hBar.damage();
        assertEquals(2, hBar.getHealthLostCounter());
    }

    @Test
    public void healthbarTest2() {
        HealthBar hBar = new HealthBar(new Dimension(20, 20));
        assertEquals(0, hBar.getHealthLostCounter());
        hBar.damage();
        hBar.damage();
        hBar.damage();
        hBar.damage();
        assertEquals(3, hBar.getHealthLostCounter());
    }

}
