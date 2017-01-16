package avatar.game.ability.property;

import avatar.event.custom.AbilityEvent;
import avatar.game.ability.Ability;
import avatar.manager.ListenerManager;
import avatar.game.user.UserPlayer;
import org.spongepowered.api.event.EventListener;

public class AbilityPropertyDamage extends AbilityProperty implements EventListener<AbilityEvent.Hit> {

    public AbilityPropertyDamage(String displayName, Ability ability) {
        super(displayName, ability);
    }

    @Override
    protected void register() {
        ListenerManager.register(AbilityEvent.Hit.class, this);
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
