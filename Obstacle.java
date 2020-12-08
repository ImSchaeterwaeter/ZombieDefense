import java.util.Random;

import greenfoot.Actor;
import greenfoot.Color;
import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

public abstract class Obstacle extends Actor {

	private static final int SIZE = 50;
	private static final int HALFSIZE = SIZE / 2;

	@Override
	public void act() {
		checkRemoveObstacleClick();
	}

	private void checkRemoveObstacleClick() {
		if(Greenfoot.mouseClicked(this) && Greenfoot.getMouseInfo().getButton() == 1 && Greenfoot.getMouseInfo() != null) {
			GameState.MouseState mouseState = getWorld().getGameState().getMouseState();
			if(mouseState == GameState.MouseState.DELETE_TOOL) {
				getWorld().getGameState().getCoinsCounter().add(this.getPrice());
				getWorld().removeObject(this);
			}
		}
	}

	public abstract int getPrice();

	@Override
	public GameWorld getWorld() {
		return (GameWorld) super.getWorld();
	}

	public void createImage(Color color, int points) {
		GreenfootImage image = new GreenfootImage(SIZE, SIZE);

		for (int i = 0; i < points; i++) {
			int x = randomCoord();
			int y = randomCoord();

			image.setColorAt(x, y, color);
		}
		setImage(image);
	}

	private int randomCoord() {
		int val = HALFSIZE + (int) (new Random().nextGaussian() * (HALFSIZE / 2));

		if(val < 0) {
			return 0;
		}

		if(val > SIZE - 2) {
			return SIZE - 2;
		} else {
			return val;
		}
	}
}
