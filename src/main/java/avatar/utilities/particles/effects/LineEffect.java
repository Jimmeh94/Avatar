package avatar.utilities.particles.effects;

import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.world.Location;

public class LineEffect extends AbstractEffect {
	
	private Location target;
	
	/**
	 * Displays a line of particles between 2 specified locations.
	 * @param origin The start location of the line.
	 * @param target The end location of the line.
	 */
	public LineEffect(EffectData effectData, Location target) {
		super(effectData);
		this.target = target;
	}
	
	@Override
	public void play() {
		Vector3d v = target.getPosition().sub(effectData.getLocation().getPosition());
		for (double i = 0; i < v.length(); i += 0.5) {
			Location loc = effectData.getLocation().copy().add(v.clone().normalize().mul(i));
			playParticle(loc);
		}
	}

}