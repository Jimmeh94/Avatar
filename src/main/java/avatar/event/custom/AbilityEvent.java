package avatar.event.custom;

import avatar.game.ability.Ability;
import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.cause.Cause;

import java.util.List;

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

    public static class Hit extends AbilityEvent implements Cancellable{
        private List<Ability> collidedAbilities;

        public Hit(Ability ability, Cause cause, List<Ability> collidedAbilities) {
            super(ability, cause);

            this.collidedAbilities = collidedAbilities;
        }

        public List<Ability> getCollidedAbilities() {
            return collidedAbilities;
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

    public static class PostHit extends AbilityEvent{
        public PostHit(Ability ability, Cause cause) {
            super(ability, cause);
        }
    }

    public static class UpdateTick extends AbilityEvent{
        public UpdateTick(Ability ability, Cause cause) {
            super(ability, cause);
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
