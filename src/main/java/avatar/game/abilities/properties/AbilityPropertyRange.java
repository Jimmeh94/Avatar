package avatar.game.abilities.properties;

import avatar.user.User;

/**
 * How far the ability can travel
 */
public class AbilityPropertyRange extends AbilityProperty {

    public AbilityPropertyRange(String displayName, User owner) {
        super(displayName, AbilityPropertyCheck.ON_ABILITY_UPDATE, owner);
    }

    @Override
    public boolean check(User user) {
        return false;
    }
}
