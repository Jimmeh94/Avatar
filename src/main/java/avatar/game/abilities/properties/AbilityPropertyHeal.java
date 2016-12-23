package avatar.game.abilities.properties;

import avatar.events.custom.AbilityEvent;
import avatar.game.abilities.Ability;
import avatar.managers.ListenerManager;
import avatar.user.UserPlayer;
import org.spongepowered.api.event.EventListener;

public class AbilityPropertyHeal extends AbilityProperty implements EventListener<AbilityEvent.PostFire>{
    public AbilityPropertyHeal(String displayName, Ability ability) {
        super(displayName, ability);
    }

    @Override
    public void printFailMessage(UserPlayer user) {

    }

    @Override
    protected void register() {
        ListenerManager.register(AbilityEvent.PostFire.class, this);
    }

    @Override
    public void handle(AbilityEvent.PostFire postFire) throws Exception {
        if(!postFire.isCancelled()){
            //heal target
        }
    }
}
