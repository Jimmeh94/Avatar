package avatar.game.ability.property;

import avatar.game.ability.type.Ability;
import avatar.game.ability.AbilityStage;
import avatar.game.user.User;
import org.spongepowered.api.text.Text;

/**
 * For things like energy/chi cost,
 */
public abstract class AbilityProperty{

    private String displayName;
    private User owner;
    protected Ability ability;
    protected AbilityStage checkWhen;

    public abstract boolean validate();

    public AbilityProperty(String displayName, Ability ability, AbilityStage checkWhen){
        this.displayName = displayName;
        this.ability = ability;
        this.owner = ability.getOwner();
        this.checkWhen = checkWhen;
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
    public abstract Text getFailMessage();


    public boolean checkNow(AbilityStage stage) {
        return stage == checkWhen;
    }

}
