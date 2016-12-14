package avatar.utilities.particles.effects;


import avatar.Avatar;
import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.particle.ParticleType;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.scheduler.Scheduler;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.world.Location;

public abstract class AbstractEffect {

	private Location loc;
	protected ParticleType particle;
	private double xOffset = 0, yOffset = 0, zOffset = 0;
	private Scheduler scheduler = Sponge.getScheduler();
	private Task.Builder taskBuilder = scheduler.createTaskBuilder();
	private Task task;

	/**
	 * A super constructor to set some fields in the super class. Particle is
	 * set to FLAME by default. Use {@link #AbstractEffect(Location, ParticleType)}
	 * to set the particle type directly.}
	 * 
	 * @param loc
	 *            The center location of the effect.
	 */
	public AbstractEffect(Location loc) {
		this(loc, ParticleTypes.FLAME);
	}

	/**
	 * A super constructor to set some fields in the super class.
	 * 
	 * @param loc
	 * @param particle
	 */
	public AbstractEffect(Location loc, ParticleType particle) {
		this.loc = loc;
		this.particle = particle;
	}

	/**
	 * An abstract method used to display the animation.
	 */
	public abstract void play();

	/**
	 * Starts the runnable, which makes the effect display itself every tick.
	 * 
	 * @return The current instance of the effect to allow chaining of methods.
	 */
	public AbstractEffect start(long delay, long interval, final long cancel) {
		task = taskBuilder.delayTicks(delay).intervalTicks(interval).execute(
			new Runnable() {
				int c = 0;

				@Override
				public void run() {
					play();
					c++;
					if (c >= cancel / interval)
						stop();
				}
			}
		).submit(Avatar.INSTANCE);
		return this;
	}

	/**
	 * A getter for the center location of the effect.
	 * 
	 * @return The center location of the effect.
	 */
	public Location getLocation() {
		return loc;
	}

	/**
	 * Stops the effect from automaticly displaying.
	 * 
	 * @return The current instance of the effect, to allow 'chaining' of
	 *         methods.
	 */
	public AbstractEffect stop() {
		if (task == null)
			return this;
		try {
			task.cancel();
			task = null;
		} catch (IllegalStateException exc) {
		}
		return this;
	}

	/**
	 * Spawns a particle using the set particle effect.
	 * 
	 * @param loc
	 *            The location to spawn the particle at.
	 */
	protected abstract void playParticle(Location loc);

	/**
	 * Sets the particle type to be used in playParticle.
	 * 
	 * @param particle
	 *            The new particle type.
	 * @return The current instance of the effect, to allow 'method chaining'.
	 */
	public AbstractEffect setParticle(ParticleType particle) {
		this.particle = particle;
		return this;
	}

	public double getxOffset() {
		return xOffset;
	}

	public void setxOffset(double xOffset) {
		this.xOffset = xOffset;
	}

	public double getyOffset() {
		return yOffset;
	}

	public void setyOffset(double yOffset) {
		this.yOffset = yOffset;
	}

	public double getzOffset() {
		return zOffset;
	}

	public void setzOffset(double zOffset) {
		this.zOffset = zOffset;
	}

	public ParticleType getParticle() {
		return particle;
	}

	protected Vector3d rotateAroundAxisX(Vector3d v, double angle) {
		angle = Math.toRadians(angle);
		double y, z, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		y = v.getY() * cos - v.getZ() * sin;
		z = v.getY() * sin + v.getZ() * cos;
		return new Vector3d(v.getX(), y, z);
		//return v.set(y).setZ(z);
	}

	protected Vector3d rotateAroundAxisY(Vector3d v, double angle) {
		angle = Math.toRadians(angle);
		double x, z, cos, sin;
		cos = Math.cos(angle);
		sin = Math.sin(angle);
		x = v.getX() * cos + v.getZ() * sin;
		z = v.getX() * -sin + v.getZ() * cos;
		return new Vector3d(x, v.getY(), z);
		//return v.setX(x).setZ(z);
	}

	public float[] vectorToYawPitch(Vector3d v) {
		Location loc = new Location(null, 0, 0, 0);
		loc.setDirection(v);
		return new float[] { loc.getYaw(), loc.getPitch() };
	}

	public Vector3d yawPitchToVector(float yaw, float pitch) {
		yaw += 90;
		return new Vector3d(Math.cos(Math.toRadians(yaw)), Math.sin(Math.toRadians(pitch)),
				Math.sin(Math.toRadians(yaw)));
	}

}