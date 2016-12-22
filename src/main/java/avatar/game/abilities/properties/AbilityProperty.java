package avatar.game.abilities.properties;

import avatar.game.abilities.Ability;
import avatar.user.User;
import avatar.user.UserPlayer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventListener;

/**
 * For things like energy/chi cost,
 */
public abstract class AbilityProperty{

    private String displayName;
    private User owner;
    protected Ability ability;

    public AbilityProperty(String displayName, Ability ability){
        this.displayName = displayName;
        this.ability = ability;
        this.owner = ability.getOwner();

        register();
    }

    public String getDisplayName() {
        return displayName;
    }

    public User getOwner() {
        return owner;
    }

    public Ability getAbility() {
        return ability;
    }

    /**
     * Prints to the user why the action was unsuccessful, i.e. didn't have enough energy
     */
    public abstract void printFailMessage(UserPlayer user);

    /**
     * Registers the class as a listener
     */
    protected abstract void register();

    /**
     * Unregisters the class as a listener
     */
    public void unregister(){
        if(this instanceof EventListener)
            Sponge.getEventManager().unregisterListeners(this);
    }

}
