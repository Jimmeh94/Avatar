package avatar.game.abilities.properties;

import avatar.Avatar;
import avatar.game.abilities.Ability;
import avatar.events.custom.AbilityEvent;
import avatar.user.UserPlayer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventListener;

public class AbilityPropertyDamage extends AbilityProperty implements EventListener<AbilityEvent.Hit> {

    public AbilityPropertyDamage(String displayName, Ability ability) {
        super(displayName, ability);
    }

    @Override
    protected void register() {
        Sponge.getEventManager().registerListener(Avatar.INSTANCE, AbilityEvent.Hit.class, this);
    }

    @Override
    public void handle(AbilityEvent.Hit hit) throws Exception {
        if(!hit.isCancelled()){
            //deal damage here
        }
    }

    @Override
    public void printFailMessage(UserPlayer user) {

    }
}
