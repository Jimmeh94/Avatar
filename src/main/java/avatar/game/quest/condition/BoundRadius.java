package avatar.game.quest.condition;

import avatar.util.text.Messager;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;

import java.util.Optional;

public class BoundRadius extends Condition{

    /*
     * Quest checkpoint condition in which the checkpoint can only be complete if
     * the desired entity or location is within the radius to the center location
     */

    private double radius;
    private Location center;

    public BoundRadius(double radius, Location center){
        this.radius = radius;
        this.center = center;
    }

    @Override
    public boolean isValid() {
        return center.getPosition().distance(getPlayer().getLocation().getPosition()) <= radius;
    }

    @Override
    public void reset() {
        super.reset();
        getPlayer().setLocation(getStartLocation());
    }

    @Override
    public void displayWarningMessage() {
        if(shouldSendWarningMessage()){
            setLastWarningMessage();
            Messager.sendMessage(getPlayer(), Text.of(TextColors.GRAY, "You're outside of the quest region! Go back to continue the quest!"), Optional.of(Messager.Prefix.ERROR));
        }
    }

}
