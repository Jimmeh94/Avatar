package avatar.utilities.particles.effects;

import kingdomcrashers.utilities.particles.ParticleEffect;
import org.bukkit.Location;

public class SphereEffect extends AbstractEffect {

	private double[][][] sphereCoordinates;

	public SphereEffect(Location loc, double radius) {
		super(loc);
		init(radius);
	}

	public SphereEffect(Location loc, double radius, ParticleEffect particle) {
		super(loc, particle);
		init(radius);
	}

	private void init(double radius) {
		sphereCoordinates = new double[24][][];
		for (int i = 0; i < sphereCoordinates.length; i++) {
			sphereCoordinates[i] = new double[16][];
			double x, y, z;
			double r = Math.sin((Math.PI / 12) * i);
			for (int i2 = 0; i2 < 16; i2++) {
				double theta = i2 * (Math.PI / 8);
				x = radius * Math.cos(theta) * r;
				z = radius * Math.sin(theta) * r;
				y = radius * Math.cos((Math.PI / 12) * i);
				sphereCoordinates[i][i2] = new double[] { x, y, z };
			}
		}
	}

	@Override
	public void play() {
		for (int i = 0; i < sphereCoordinates.length; i++)
			for (int i2 = 0; i2 < sphereCoordinates[i].length; i2++) {
				playParticle(getLocation().add(sphereCoordinates[i][i2][0], sphereCoordinates[i][i2][1], sphereCoordinates[i][i2][2]));
				getLocation().subtract(sphereCoordinates[i][i2][0], sphereCoordinates[i][i2][1], sphereCoordinates[i][i2][2]);
			}
	}

}