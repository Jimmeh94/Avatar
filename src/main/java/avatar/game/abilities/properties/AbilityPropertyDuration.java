package avatar.game.abilities.properties;

import avatar.Avatar;
import avatar.game.abilities.Ability;
import avatar.events.custom.AbilityEvent;
import avatar.user.UserPlayer;
import org.spongepowered.api.Sponge;
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
        Sponge.getEventManager().registerListener(Avatar.INSTANCE, AbilityEvent.UpdateTick.class, this);
    }

    @Override
    public void handle(AbilityEvent.UpdateTick updateTick) throws Exception {
        if(!updateTick.isCancelled()){

        }
    }
}
