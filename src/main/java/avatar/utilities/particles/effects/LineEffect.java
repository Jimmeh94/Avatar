package avatar.utilities.particles.effects;

import kingdomcrashers.utilities.particles.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LineEffect extends AbstractEffect {
	
	private Location target;
	
	/**
	 * Displays a line of particles between 2 specified locations. Default particle is set to FLAME.
	 * @param origin The start location of the line.
	 * @param target The end location of the line.
	 */
	public LineEffect(Location origin, Location target) {
		super(origin);
		this.target = target;
	}
	/**
	 * Displays a line of particles between 2 specified locations.
	 * @param origin The start location of the line.
	 * @param target The end location of the line.
	 * @param particle THe particle to display.
	 */
	public LineEffect(Location loc, Location target, ParticleEffect particle) {
		super(loc, particle);
		this.target = target;
	}
	
	@Override
	public void play() {
		Vector v = target.toVector().subtract(getLocation().toVector());
		for (double i = 0; i < v.length(); i += 0.5) {
			Location loc = getLocation().clone().add(v.clone().normalize().multiply(i));
			playParticle(loc);
		}
	}

}