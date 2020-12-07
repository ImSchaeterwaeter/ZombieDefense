import java.util.List;

import greenfoot.Greenfoot;
import greenfoot.GreenfootImage;

public class Bomb extends Projectile {

	public static final String BOMB_IMAGE_NAME = "bomb-projectile.png";
	private static final int DEFAULT_RANGE = PathCell.PATH_WIDTH - 5;
	private static final String EXPLOSION_SOUND = "Bomb_Explosion.wav";
	private static final double DEFAULT_SPEED = 1.5;

	public Bomb(int destinationX, int destinationY, int damage) {
		super(destinationX, destinationY, DEFAULT_SPEED, BOMB_IMAGE_NAME, damage);
		GreenfootImage img = new GreenfootImage(BOMB_IMAGE_NAME);
		img.scale((int) (img.getWidth() / 1.5), (int) (img.getHeight() / 1.5));
		setImage(img);
	}

	@Override
	protected void behaviourIfReachedDestination() {
		List<Zombie> zombies = getObjectsInRange((int) Math.floor(DEFAULT_RANGE), Zombie.class);
		if(zombies.size() > 0) {
			for (Zombie zombie : zombies) {
				zombie.absorbDamage(getDamage());
			}
		}
		Greenfoot.playSound(EXPLOSION_SOUND);
		getWorld().removeObject(this);
	}

	@Override
	protected void behaviorWhileMoving() {
		this.setRotation(getRotation() + 5);
	}

}
