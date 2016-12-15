package avatar.game.abilities.properties;

import avatar.user.User;

/**
 * How long the ability lasts
 */
public class AbilityPropertyDuration extends AbilityProperty {

    public AbilityPropertyDuration(String displayName, User owner) {
        super(displayName, AbilityPropertyCheck.ON_ABILITY_UPDATE, owner);
    }

    @Override
    public boolean check(User user) {
        return false;
    }
}
