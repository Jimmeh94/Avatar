package avatar.game.ability.property;

import avatar.event.custom.AbilityEvent;
import avatar.game.ability.Ability;
import avatar.manager.ListenerManager;
import avatar.game.user.UserPlayer;
import org.spongepowered.api.event.EventListener;

/**
 * The auto/manual targeting of the ability
 */
public abstract class AbilityPropertyTargeting extends AbilityProperty implements EventListener<AbilityEvent.PreFire> {

    private TargetingFilter filter;

    public AbilityPropertyTargeting(String displayName, Ability ability) {
        super(displayName, ability);
    }

    public AbilityPropertyTargeting(String displayName, Ability ability, TargetingFilter filter){
        super(displayName, ability);

        this.filter = filter;
    }

    @Override
    protected void register(){
        ListenerManager.register(AbilityEvent.PreFire.class, this);
    }

    public TargetingFilter getFilter() {
        return filter;
    }

    public static class Location extends AbilityPropertyTargeting{

        public Location(String displayName, Ability ability) {
            super(displayName, ability);
        }

        @Override
        public void printFailMessage(UserPlayer user) {

        }

        @Override
        public void handle(AbilityEvent.PreFire preFire) throws Exception {

        }
    }

    public static class Self extends AbilityPropertyTargeting{

        public Self(String displayName, Ability ability) {
            super(displayName, ability);
        }


        @Override
        public void printFailMessage(UserPlayer user) {

        }

        @Override
        public void handle(AbilityEvent.PreFire preFire) throws Exception {
            if(!preFire.isCancelled()){

            }
        }
    }

    public static class Entity extends AbilityPropertyTargeting{

        public Entity(String displayName, Ability ability, TargetingFilter filter) {
            super(displayName, ability, filter);
        }

        @Override
        public void printFailMessage(UserPlayer user) {

        }

        @Override
        public void handle(AbilityEvent.PreFire preFire) throws Exception {
            if(!preFire.isCancelled()){

            }
        }
    }

    public static class Group extends AbilityPropertyTargeting{

        public Group(String displayName, Ability ability, TargetingFilter filter) {
            super(displayName, ability, filter);
        }


        @Override
        public void printFailMessage(UserPlayer user) {

        }

        @Override
        public void handle(AbilityEvent.PreFire preFire) throws Exception {
            if(!preFire.isCancelled()){

            }
        }
    }

    public enum TargetingFilter{
        ALLIES,
        ENEMIES
    }
}
