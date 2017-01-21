package avatar.game.quest.condition;

import avatar.event.custom.AbilityEvent;
import avatar.game.ability.type.Ability;
import avatar.manager.ListenerManager;
import org.spongepowered.api.event.EventListener;

public class FireAbility extends Condition implements EventListener<AbilityEvent.PostFire> {

    private Class<? extends Ability> ability;

    public FireAbility(Class<? extends Ability> ability) {
        this.ability = ability;
    }

    @Override
    public void reset(){
        super.reset();

        unregisterListener();
        setAdditionalStartInfo();
    }

    @Override
    public void setAdditionalStartInfo() {
        ListenerManager.register(AbilityEvent.PostFire.class, this);
    }

    @Override
    public void handle(AbilityEvent.PostFire postFire) throws Exception {
        if(postFire.getAbility().getClass().getCanonicalName().equals(ability.getCanonicalName())){
            valid = true;

            unregisterListener();
        }
    }
}
