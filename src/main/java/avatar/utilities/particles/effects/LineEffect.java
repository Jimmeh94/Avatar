package avatar.utilities.particles.effects;

import avatar.utilities.misc.LocationUtils;
import org.spongepowered.api.world.Location;

import java.util.List;

public class LineEffect extends AbstractEffect {
	
	private Location target;
	private List<Location> draw;
	private int display = 0;
	
	/**
	 * Displays a line of particles between 2 specified locations.
	 * @param target The end location of the line.
	 */
	public LineEffect(EffectData effectData, Location target) {
		super(effectData);
		this.target = target;
		draw = LocationUtils.getConnectingLine(effectData.getLocation(), target);
	}
	
	@Override
	public void play() {
		for(int i = 0; i <= display; i++){
			playParticle(draw.get(i));
		}
		if(display < draw.size() - 1)
			display++;
		else {
			for(Location location: draw){
				System.out.println(location.toString());
			}
		}
		/*Vector3d v = target.getPosition().sub(effectData.getLocation().getPosition());
		for (double i = 0; i < v.length(); i += 0.5) {
			Location loc = effectData.getLocation().copy().add(v.clone().normalize().mul(i));
			playParticle(loc);
		}*/
	}

}