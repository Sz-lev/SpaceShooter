package game_elements.entities.spaceships;

import static org.junit.jupiter.api.Assertions.*;

import game_elements.explosions.Explosion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;

import java.awt.*;


class LaserTest {

    Laser laser;
    ArrayList<Explosion> explList;

    @BeforeEach
    public void setUp() {
        ArrayList<Laser> laserList = new ArrayList<>();
        explList = new ArrayList<>();

        PlayerSpaceShip player = new PlayerSpaceShip(new Dimension(500, 500), laserList, explList, null);
        laser = new Laser(player, 10, explList, 0);
    }

    @Test
    public void notOutOfBoundsTest() {
        assertFalse(laser.isOutOfBounds);
    }

    @Test
    public void isOutOfBoundsTest() {
        assertTrue(explList.isEmpty());
        laser.hitEntity();
        assertTrue(laser.isOutOfBounds);
        assertFalse(explList.isEmpty());
    }

}
