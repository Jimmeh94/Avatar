package avatar.game.quests.quests.conditions;

import avatar.game.quests.quests.Condition;
import org.spongepowered.api.world.Location;

public class ReachLocation extends Condition{

    /*
     * Simply a condition to where a player must reach a location
     */

    private Location targetLocation;
    private double completionRadius = 1.5;

    public ReachLocation(boolean reset, Location location, double radius) {
        super(reset);
        targetLocation = location;
        completionRadius = radius;
    }

    @Override
    public boolean isValid() {
        return getPlayer().getLocation().getPosition().distance(targetLocation.getPosition()) <= completionRadius;
    }

    @Override
    public void setAdditionalStartInfo() {

    }

    @Override
    public void displayWarningMessage() {

    }
}
