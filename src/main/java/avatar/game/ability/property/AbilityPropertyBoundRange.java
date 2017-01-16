package avatar.game.ability.property;

import avatar.game.ability.Ability;
import avatar.event.custom.AbilityEvent;
import avatar.game.user.UserPlayer;
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
        //if user's current location .distance from ability.firedFrom > range cancel

    }
}
