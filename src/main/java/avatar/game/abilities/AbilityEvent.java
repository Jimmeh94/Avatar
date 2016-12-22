package avatar.game.abilities;

import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public class AbilityEvent implements Event, Cancellable {

    private final Ability ability;
    private final Cause cause;
    private boolean cancelled = false;

    public AbilityEvent(Ability ability, Cause cause){
        this.ability = ability;
        this.cause = cause;
    }

    @Override
    public Cause getCause() {
        return cause;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    public static class PreFire extends AbilityEvent{
        public PreFire(Ability ability, Cause cause) {
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
}
