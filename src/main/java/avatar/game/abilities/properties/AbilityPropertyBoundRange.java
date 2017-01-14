package avatar.game.abilities.properties;

import avatar.game.abilities.Ability;
import avatar.events.custom.AbilityEvent;
import avatar.user.UserPlayer;
import org.spongepowered.api.event.EventListener;

/**
 * How far the ability can travel
 */
public class AbilityPropertyBoundRange extends AbilityProperty implements EventListener<AbilityEvent.UpdateTick>{

    private double range;

    public AbilityPropertyBoundRange(String displayName, Ability ability, double range) {
        super(displayName, ability);
    }

    @Override
    public void printFailMessage(UserPlayer user) {

    }

    @Override
    protected void register() {

    }

    @Override
    public void handle(AbilityEvent.UpdateTick updateTick) throws Exception {
        if(!updateTick.isCancelled()){
            //if user's current location .distance from ability.firedFrom > range cancel
        }
    }
}
