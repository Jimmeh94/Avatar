package avatar.game.abilities.properties;

import avatar.Avatar;
import avatar.game.abilities.Ability;
import avatar.game.abilities.AbilityEvent;
import avatar.user.UserPlayer;
import org.spongepowered.api.Sponge;
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
        Sponge.getEventManager().registerListener(Avatar.INSTANCE, AbilityEvent.PreFire.class, this);
    }

    public TargetingFilter getFilter() {
        return filter;
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
