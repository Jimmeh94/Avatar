package avatar.game.ability.property;

import avatar.game.ability.Ability;
import avatar.game.ability.AbilityStage;
import org.spongepowered.api.text.Text;

/**
 * The auto/manual targeting of the ability
 */
public abstract class AbilityPropertyTargeting extends AbilityProperty{

    private TargetingFilter filter;

    public AbilityPropertyTargeting(String displayName, Ability ability, TargetingFilter filter){
        super(displayName, ability, AbilityStage.PRE_FIRE);

        this.filter = filter;
    }

    public TargetingFilter getFilter() {
        return filter;
    }

    public static class Location extends AbilityPropertyTargeting{

        public Location(String displayName, Ability ability, TargetingFilter filter) {
            super(displayName, ability, filter);
        }

        @Override
        public boolean validate() {
            return false;
        }

        @Override
        public Text getFailMessage() {
            return null;
        }
    }

    public static class Self extends AbilityPropertyTargeting{

        public Self(String displayName, Ability ability, TargetingFilter filter) {
            super(displayName, ability, filter);
        }

        @Override
        public boolean validate() {
            return false;
        }

        @Override
        public Text getFailMessage() {
            return null;
        }
    }

    public static class Entity extends AbilityPropertyTargeting{

        public Entity(String displayName, Ability ability, TargetingFilter filter) {
            super(displayName, ability, filter);
        }

        @Override
        public boolean validate() {
            return false;
        }

        @Override
        public Text getFailMessage() {
            return null;
        }
    }

    public static class Group extends AbilityPropertyTargeting{

        public Group(String displayName, Ability ability, TargetingFilter filter) {
            super(displayName, ability, filter);
        }

        @Override
        public boolean validate() {
            return false;
        }

        @Override
        public Text getFailMessage() {
            return null;
        }
    }

    public enum TargetingFilter{
        ALLIES,
        ENEMIES
    }
}
