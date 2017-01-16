package avatar.game.ability.property;

import avatar.event.custom.AbilityEvent;
import avatar.game.ability.Ability;
import avatar.manager.ListenerManager;
import avatar.game.user.UserPlayer;
import avatar.game.user.stats.Stats;
import org.spongepowered.api.event.EventListener;

import java.util.HashMap;
import java.util.Map;

public abstract class AbilityPropertyHeal extends AbilityProperty{

    private Map<Stats.StatType, Double> toheal = new HashMap<>();

    public AbilityPropertyHeal(String displayName, Ability ability, Map<Stats.StatType, Double> toheal) {
        super(displayName, ability);

        this.toheal = toheal;
    }

    @Override
    public void printFailMessage(UserPlayer user) {

    }

    public static class Immediate extends AbilityPropertyHeal implements EventListener<AbilityEvent.PostFire>{

        public Immediate(String displayName, Ability ability, Map<Stats.StatType, Double> toheal) {
            super(displayName, ability, toheal);
        }

        @Override
        public void handle(AbilityEvent.PostFire postFire) throws Exception {
            if(!postFire.isCancelled()){
                //heal target
            }
        }

        @Override
        protected void register() {
            ListenerManager.register(AbilityEvent.PostFire.class, this);
        }
    }

    public static class Hit extends AbilityPropertyHeal implements EventListener<AbilityEvent.Hit>{

        public Hit(String displayName, Ability ability, Map<Stats.StatType, Double> toheal) {
            super(displayName, ability, toheal);
        }

        @Override
        public void handle(AbilityEvent.Hit hit) throws Exception {
            if(!hit.isCancelled()){
                //heal target
            }
        }

        @Override
        protected void register() {
            ListenerManager.register(AbilityEvent.PostFire.class, this);
        }
    }
}
