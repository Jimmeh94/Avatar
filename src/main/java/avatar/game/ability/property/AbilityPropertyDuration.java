package avatar.game.ability.property;

import avatar.event.custom.AbilityEvent;
import avatar.game.ability.Ability;
import avatar.manager.ListenerManager;
import avatar.game.user.UserPlayer;
import org.spongepowered.api.event.EventListener;

/**
 * How long the ability lasts
 */
public class AbilityPropertyDuration extends AbilityProperty implements EventListener<AbilityEvent.UpdateTick>{

    public AbilityPropertyDuration(String displayName, Ability ability) {
        super(displayName, ability);
    }

    @Override
    public void printFailMessage(UserPlayer user) {

    }

    @Override
    protected void register() {
        ListenerManager.register(AbilityEvent.UpdateTick.class, this);
    }

    @Override
    public void handle(AbilityEvent.UpdateTick updateTick) throws Exception {
        if(!updateTick.isCancelled()){

        }
    }
}
