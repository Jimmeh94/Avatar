package avatar.game.quests.quests.conditions;

import avatar.Avatar;
import avatar.game.areas.Area;
import avatar.game.quests.quests.Condition;
import avatar.utilities.text.Messager;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class BoundArea extends Condition{

    /*
     * Quest checkpoint condition in which the checkpoint can only be complete if
     * the desired entity or location is within the radius to the center location
     */

    private Area bound;

    public BoundArea(boolean reset, Area bound){
        super(reset);

        this.bound = bound;
    }

    @Override
    public boolean isValid() {
        return Avatar.INSTANCE.getUserManager().find(getPlayer().getUniqueId()).get().getPresentArea() == bound;
    }

    @Override
    public void setAdditionalStartInfo() {

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
            Messager.sendMessage(getPlayer(), Text.of(TextColors.RED, "You're outside of the quest region! Go back to continue the quest!"));
        }
    }
}
