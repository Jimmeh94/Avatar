package avatar.event.custom;

import avatar.game.ability.type.Ability;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;

import java.util.List;

/**
 * Shouldn't need any of these
 */

public class AbilityEvent extends CustomEvent{

    private final Ability ability;
    protected boolean cancelled = false;

    public AbilityEvent(Ability ability, Cause cause){
        super(cause);

        this.ability = ability;
        this.cause = cause;
    }

    public Ability getAbility() {
        return ability;
    }

    public static class PreFire extends AbilityEvent implements Cancellable {
        public PreFire(Ability ability, Cause cause) {
            super(ability, cause);
        }

        @Override
        public boolean isCancelled() {
            return this.cancelled;
        }

        @Override
        public void setCancelled(boolean b) {
            this.cancelled = b;
        }
    }

    public static class Fire extends AbilityEvent{

        public Fire(Ability ability, Cause cause) {
            super(ability, cause);
        }
    }

    public static class PostFire extends AbilityEvent{
        public PostFire(Ability ability, Cause cause) {
            super(ability, cause);
        }
    }

    public static class PreHit extends AbilityEvent{
        public PreHit(Ability ability, Cause cause) {
            super(ability, cause);
        }
    }

    public static abstract class Hit extends AbilityEvent implements Cancellable{

        public Hit(avatar.game.ability.type.Ability ability, Cause cause) {
            super(ability, cause);
        }

        @Override
        public boolean isCancelled() {
            return this.cancelled;
        }

        @Override
        public void setCancelled(boolean b) {
            this.cancelled = b;
        }

        public static class Ability extends Hit{
            private List<avatar.game.ability.type.Ability> abilities;

            public Ability(avatar.game.ability.type.Ability ability, Cause cause, List<avatar.game.ability.type.Ability> collidedAbilities) {
                super(ability, cause);

                this.abilities = collidedAbilities;
            }

            public List<avatar.game.ability.type.Ability> getAbilities() {
                return abilities;
            }
        }

        public static class User extends Hit{
            private List<avatar.game.user.User> users;

            public User(avatar.game.ability.type.Ability ability, Cause cause, List<avatar.game.user.User> users) {
                super(ability, cause);

                this.users = users;
            }

            public List<avatar.game.user.User> getUsers() {
                return users;
            }
        }
    }

    public static class PostHit extends AbilityEvent{
        public PostHit(Ability ability, Cause cause) {
            super(ability, cause);
        }
    }

    public static abstract class UpdateTick extends AbilityEvent implements Cancellable{

        public UpdateTick(Ability ability, Cause cause) {
            super(ability, cause);
        }

        @Override
        public boolean isCancelled() {
            return cancelled;
        }

        @Override
        public void setCancelled(boolean b) {
            this.cancelled = b;
        }

        public static class Pre extends UpdateTick{

            public Pre(Ability ability, Cause cause) {
                super(ability, cause);
            }
        }

        public static class Post extends UpdateTick{

            public Post(Ability ability, Cause cause) {
                super(ability, cause);
            }
        }
    }

    public static class RequirementCheck extends AbilityEvent implements Cancellable {
        public RequirementCheck(Ability ability, Cause cause) {
            super(ability, cause);
        }

        @Override
        public boolean isCancelled() {
            return this.cancelled;
        }

        @Override
        public void setCancelled(boolean b) {
            this.cancelled = b;
        }
    }

    /**
     * This is for cancelling the ability during an update tick, not cancelled on a requirement check or pre-fire
     */
    public static class Cancelled extends AbilityEvent{
        public Cancelled(Ability ability, Cause cause) {
            super(ability, cause);
        }
    }
}
