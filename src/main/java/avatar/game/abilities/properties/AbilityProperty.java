package avatar.game.abilities.properties;

import avatar.user.User;

/**
 * For things like energy/chi cost,
 */
public abstract class AbilityProperty {

    private String displayName;
    private AbilityPropertyCheck checkWhen;
    private User owner;

    public AbilityProperty(String displayName, AbilityPropertyCheck checkWhen, User owner){
        this.displayName = displayName;
        this.checkWhen = checkWhen;
        this.owner = owner;
    }

    public String getDisplayName() {
        return displayName;
    }

    public AbilityPropertyCheck getCheckWhen() {
        return checkWhen;
    }

    public User getOwner() {
        return owner;
    }

    /**
     * For things like energy cost, cast times, etc
     * @param user
     */
    public abstract boolean check(User user); //will probably need to take an Ability, too

    public enum AbilityPropertyCheck{
        PRE_FIRING,
        POST_FIRING,
        ON_ABILITY_UPDATE,
    }

}
