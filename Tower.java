import java.util.List;
import java.util.Random;

import greenfoot.Actor;
import greenfoot.Greenfoot;

public abstract class Tower extends Actor {

	public static final String INIT_IMAGE_NAME = "Tower1.png";

	private int range;
	private int reloadTime;
	private int shootCountDown;
	private int damage;

	public Tower(int range, int reloadTime, int damage) {
		setImage(INIT_IMAGE_NAME);
		this.range = range;
		this.reloadTime = reloadTime;
		this.damage = damage;
		this.shootCountDown = 0;
	}

	@Override
	public void act() {
		if(!getWorld().isPaused()) {
			if(reload()) {
				shoot();
			}
		}
		checkRemoveTowerClick();
	}

	private void checkRemoveTowerClick() {
		if(Greenfoot.mouseClicked(this) && Greenfoot.getMouseInfo().getButton() == 1) {
			GameState.MouseState mouseState = getWorld().getGameState().getMouseState();
			if(mouseState == GameState.MouseState.DELETE_TOOL) {
				getWorld().getGameState().getCoinsCounter().add(this.getPrice());
				getWorld().removeObject(this);
			}
		}
	}

	private void shoot() {
		if(areZombiesInRange()) {
			int index = new Random().nextInt(getZombiesInRange().size());
			Zombie target = getZombiesInRange().get(index);
			shootProjectileAt(target, damage);
			shootCountDown = reloadTime;
		}
	}

	public void shootProjectileAt(Zombie target, int damage) {
		int destinationX = (int) Math.round(target.getX() + target.getMovement().getX() * getZombieMovementForwardPrediction());
		int destinationY = (int) Math.round(target.getY() + target.getMovement().getY() * getZombieMovementForwardPrediction());
		getWorld().addObject(getProjetile(destinationX, destinationY, damage), getX(), getY());
	}

	private boolean reload() {
		if(shootCountDown > 0) {
			shootCountDown--;
			return false;
		} else {
			return true;
		}
	}

	private boolean areZombiesInRange() {
		return getZombiesInRange().size() > 0;
	}

	private List<Zombie> getZombiesInRange() {
		return getObjectsInRange(range, Zombie.class);
	}

	public abstract int getPrice();

	public abstract int getZombieMovementForwardPrediction();

	public abstract Projectile getProjetile(int destinationX, int destinationY, int damage);

	@Override
	public GameWorld getWorld() {
		return (GameWorld) super.getWorld();
	}
}
