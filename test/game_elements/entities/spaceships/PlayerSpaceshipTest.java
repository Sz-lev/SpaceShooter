package game_elements.entities.spaceships;

import static org.junit.jupiter.api.Assertions.*;

import game_elements.explosions.Explosion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

class PlayerSpaceshipTest {

	ArrayList<Laser> laserList;
	ArrayList<Explosion> explosionList;

	@BeforeEach
	public void setUp() {
		laserList = new ArrayList<>();
		explosionList = new ArrayList<>();
	}

	@Test
	void isAliveAfterCreateTest() {
		PlayerSpaceShip player = new PlayerSpaceShip(new Dimension(0, 0), laserList, explosionList, null);
		assertEquals(true, player.isAlive());
	}

	@Test
	public void isAliveAfterOneHitTest() {
		PlayerSpaceShip player = new PlayerSpaceShip(new Dimension(0, 0), laserList, explosionList, null);
		player.damage();
		assertEquals(true, player.isAlive());
	}

	@Test
	public void isAliveAfterThreeHitTest(){
		PlayerSpaceShip player = new PlayerSpaceShip(new Dimension(0, 0), laserList, explosionList, null);
		player.damage();
		player.damage();
		player.damage();
		assertEquals(true, player.isAlive());
	}

	@Test
	public void shieldPowerupActiveTest(){
		PlayerSpaceShip player = new PlayerSpaceShip(new Dimension(0, 0), laserList, explosionList, null);
		player.collectPowerUp(2);
		assertEquals(true, player.hasShield());
	}

	@Test
	public void shieldActiveAfterHitTest(){
		PlayerSpaceShip player = new PlayerSpaceShip(new Dimension(0, 0), laserList, explosionList, null);
		player.collectPowerUp(2);
		player.damage();
		assertEquals(true, player.hasShield());
	}

	@Test
	public void shieldInactiveAfterHitTest(){
		PlayerSpaceShip player = new PlayerSpaceShip(new Dimension(0, 0), laserList, explosionList, null);
		player.collectPowerUp(2);
		player.damage();
		player.damage();
		player.damage();
		assertEquals(false, player.hasShield());
	}

	@Test
	public void startPosTest(){
		PlayerSpaceShip player = new PlayerSpaceShip(new Dimension(1000, 1000), laserList, explosionList, null);
		assertEquals(450, player.getxCoordinate());
		assertEquals(462, player.getyCoordinate());
	}

	@Test
	public void moveRightTest(){
		PlayerSpaceShip player = new PlayerSpaceShip(new Dimension(1000, 1000), laserList, explosionList, null);
		player.moveX(true);
		assertEquals(458, player.getxCoordinate());
		assertEquals(462, player.getyCoordinate());
	}

	@Test
	public void moveLeftTest(){
		PlayerSpaceShip player = new PlayerSpaceShip(new Dimension(1000, 1000), laserList, explosionList, null);
		player.moveX(false);
		assertEquals(442, player.getxCoordinate());
		assertEquals(462, player.getyCoordinate());
	}

	@Test
	public void moveUpTest(){
		PlayerSpaceShip player = new PlayerSpaceShip(new Dimension(1000, 1000), laserList, explosionList, null);
		player.moveY(true);
		assertEquals(450, player.getxCoordinate());
		assertEquals(470, player.getyCoordinate());
	}

	@Test
	public void moveDownTest(){
		PlayerSpaceShip player = new PlayerSpaceShip(new Dimension(1000, 1000), laserList, explosionList, null);
		player.moveY(false);
		assertEquals(450, player.getxCoordinate());
		assertEquals(454, player.getyCoordinate());
	}


}
