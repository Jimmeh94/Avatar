package avatar.game.quests.quests.conditions;

import avatar.events.custom.AbilityEvent;
import avatar.game.abilities.Ability;
import avatar.game.quests.quests.Condition;
import avatar.managers.ListenerManager;
import org.spongepowered.api.event.EventListener;

public class FireAbility extends Condition implements EventListener<AbilityEvent.PostFire> {

    private Ability ability;

    public FireAbility(boolean reset, Ability ability) {
        super(reset);

        this.ability = ability;
    }

    @Override
    public void reset(){
        super.reset();

        ListenerManager.register(AbilityEvent.PostFire.class, this);
    }

    @Override
    public void setAdditionalStartInfo() {
        ListenerManager.register(AbilityEvent.PostFire.class, this);
    }

    @Override
    public void displayWarningMessage() {

    }

    @Override
    public void handle(AbilityEvent.PostFire postFire) throws Exception {
        if(postFire.getAbility().equals(ability)){
            valid = true;

            unregisterListener();
        }
    }
}
