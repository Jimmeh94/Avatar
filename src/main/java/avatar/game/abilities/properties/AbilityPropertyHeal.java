package avatar.game.abilities.properties;

import avatar.Avatar;
import avatar.game.abilities.Ability;
import avatar.game.abilities.AbilityEvent;
import avatar.user.UserPlayer;
import org.spongepowered.api.Sponge;
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
        Sponge.getEventManager().registerListener(Avatar.INSTANCE, AbilityEvent.PostFire.class, this);
    }

    @Override
    public void handle(AbilityEvent.PostFire postFire) throws Exception {
        if(!postFire.isCancelled()){
            //heal target
        }
    }
}
