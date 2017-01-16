package avatar.game.quest.condition;

import org.spongepowered.api.world.Location;

public class ReachLocation extends Condition{

    /*
     * Simply a condition to where a player must reach a location
     */

    private Location targetLocation;
    private double completionRadius = 1.5;

    public ReachLocation(Location location, double radius) {
        targetLocation = location;
        completionRadius = radius;
    }

    @Override
    public boolean isValid() {
        return getPlayer().getLocation().getPosition().distance(targetLocation.getPosition()) <= completionRadius;
    }
}
