package avatar.game.quests.quests.conditions;

import avatar.events.custom.AreaEvent;
import avatar.game.areas.Area;
import avatar.game.quests.quests.Condition;
import avatar.managers.ListenerManager;
import avatar.utilities.text.Messager;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class BoundArea extends Condition implements EventListener<AreaEvent.Exit> {

    /*
     * Quest checkpoint condition in which the checkpoint can only be complete if
     * the desired entity or location is within the radius to the center location
     */

    private Area bound;

    public BoundArea(Area bound){
        this.bound = bound;
    }

    @Override
    public void reset() {
        super.reset();
        getPlayer().setLocation(getStartLocation());

        unregisterListener();
        setAdditionalStartInfo();
    }

    @Override
    public void setAdditionalStartInfo() {
        ListenerManager.register(AreaEvent.Exit.class, this);
    }

    @Override
    public void displayWarningMessage() {
        if(shouldSendWarningMessage()){
            setLastWarningMessage();
            Messager.sendMessage(getPlayer(), Text.of(TextColors.RED, "You're outside of the quest region! Go back to " + bound.getDisplayName() + " continue the quest!"));
        }
    }

    @Override
    public void handle(AreaEvent.Exit exit) throws Exception {
        if(exit.getArea() == bound){
            valid = false;
        }
    }
}
