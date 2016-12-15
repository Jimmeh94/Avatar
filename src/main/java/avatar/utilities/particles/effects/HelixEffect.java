package avatar.utilities.particles.effects;

import kingdomcrashers.utilities.particles.ParticleEffect;
import org.bukkit.Location;

public class HelixEffect extends AbstractEffect {

	private double[][][] coordinates;
	private int lines;
	private int spinner;
	private int circleCoordinates;

	public HelixEffect(Location loc, double top, double heightStep, double radius) {
		super(loc);
		init(top, heightStep, radius);
	}

	public HelixEffect(Location loc, double top, double heightStep, double radius, ParticleEffect particle) {
		super(loc, particle);
		init(top, heightStep, radius);
	}

	private void init(double height, double heightStep, double radius) {
		circleCoordinates = 120;
		coordinates = new double[(int) (height / heightStep) + 1][][];
		for (int i = 0; i < height / heightStep; i++) {
			double y = heightStep * i;
			coordinates[i] = new double[circleCoordinates][];
			int i2 = 0;
			for (double a = 0; a < Math.PI * 2; a += Math.PI / (circleCoordinates / 2)) {
				double x = Math.cos(a) * radius;
				double z = Math.sin(a) * radius;
				coordinates[i][i2++] = new double[] { x, y, z };
			}
			i2 = 0;
		}
	}

	@Override
	public void play() {
		int i = 0;
		spinner++;
		for (double[][] array2d : coordinates) {
			if (array2d == null) continue;
			for (int line = 0; line < lines; line++) {
				int stepPerLine = circleCoordinates / lines;
				double[] array = array2d[(((stepPerLine * line) % circleCoordinates) + (i * 2 % circleCoordinates)
						+ (circleCoordinates - 1 - ((spinner) % circleCoordinates))) % circleCoordinates];
				playParticle(getLocation().add(array[0], array[1], array[2]));
				getLocation().subtract(array[0], array[1], array[2]);
			}
			i++;
		}
	}

	public HelixEffect setLines(int i) {
		lines = i;
		return this;
	}

}