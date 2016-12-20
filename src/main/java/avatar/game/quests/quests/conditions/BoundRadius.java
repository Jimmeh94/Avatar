package avatar.game.quests.quests.conditions;

import avatar.game.quests.quests.Condition;
import avatar.utilities.text.Messager;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;

import java.util.Optional;

public class BoundRadius extends Condition {

    /*
     * Quest checkpoint condition in which the checkpoint can only be complete if
     * the desired entity or location is within the radius to the center location
     */

    private double radius;
    private Location center;
    private Optional<Location> manualTrackedLocation = Optional.empty();

    public BoundRadius(boolean reset, double radius, Location center){
        super(reset, Check.ON_TIMER_TICK);
        this.radius = radius;
        this.center = center;
    }

    /*
     * Can manually update this before the isValid() check if using a manually tracked location
     * For example, making sure an ability fired is within the radius, etc
     */
    public void updateManualLocation(Location location){manualTrackedLocation = Optional.of(location);}

    @Override
    public boolean isValid() {
        Location check;
        if(manualTrackedLocation.isPresent()){
            check = manualTrackedLocation.get();
        } else {
            check = getPlayer().getLocation();
        }

        if(check.getPosition().distance(center.getPosition()) <= radius)
            return true;
        return false;
    }

    @Override
    public void setAdditionalStartInfo() {

    }

    @Override
    public void reset() {
        getPlayer().setLocation(getStartLocation());
    }

    @Override
    public void displayWarningMessage() {
        if(shouldSendWarningMessage()){
            setLastWarningMessage();
            Messager.sendMessage(getPlayer(), Text.of(TextColors.RED, "You're outside of the quest region! Go back to continue the quest!"));
        }
    }
}
