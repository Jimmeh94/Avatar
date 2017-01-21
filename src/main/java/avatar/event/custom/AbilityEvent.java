package avatar.event.custom;

import avatar.game.ability.type.Ability;
import org.spongepowered.api.event.cause.Cause;

public abstract class AbilityEvent extends CustomEvent {

    private Ability ability;

    public AbilityEvent(Ability ability, Cause cause) {
        super(cause);

        this.ability = ability;
    }

    public Ability getAbility() {
        return ability;
    }

    public static class PostFire extends AbilityEvent{

        public PostFire(Ability ability, Cause cause) {
            super(ability, cause);
        }
    }
}
