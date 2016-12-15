package avatar.game.abilities.properties;

import avatar.user.User;

/**
 * The auto/manual targeting of the ability
 */
public abstract class AbilityPropertyTargeting extends AbilityProperty {

    private TargetingFilter filter;

    public AbilityPropertyTargeting(String displayName, AbilityPropertyCheck checkWhen, User owner) {
        super(displayName, checkWhen, owner);
    }

    public AbilityPropertyTargeting(String displayName, AbilityPropertyCheck checkWhen, User owner, TargetingFilter filter){
        super(displayName, checkWhen, owner);

        this.filter = filter;
    }

    public TargetingFilter getFilter() {
        return filter;
    }

    public static class Self extends AbilityPropertyTargeting{

        public Self(String displayName, User owner) {
            super(displayName, AbilityPropertyCheck.PRE_FIRING, owner);
        }

        @Override
        public boolean check(User user) {
            return true;
        }
    }

    public static class Entity extends AbilityPropertyTargeting{

        public Entity(String displayName, User owner, TargetingFilter filter) {
            super(displayName, AbilityPropertyCheck.PRE_FIRING, owner, filter);
        }

        @Override
        public boolean check(User user) {
            return true;
        }
    }

    public static class Group extends AbilityPropertyTargeting{

        public Group(String displayName, User owner, TargetingFilter filter) {
            super(displayName, AbilityPropertyCheck.PRE_FIRING, owner, filter);
        }

        @Override
        public boolean check(User user) {
            return true;
        }
    }

    public enum TargetingFilter{
        ALLIES,
        ENEMIES
    }
}
