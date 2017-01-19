package avatar.game.ability.property;

import avatar.game.ability.Ability;
import avatar.game.ability.AbilityStage;
import org.spongepowered.api.text.Text;

/**
 * How far the ability can travel
 */
public class AbilityPropertyBoundRange extends AbilityProperty{

    private double range;

    public AbilityPropertyBoundRange(String displayName, Ability ability, double range) {
        super(displayName, ability, AbilityStage.UPDATE);

        this.range = range;
    }

    @Override
    public boolean validate() {
        return ability.getCenter().getPosition().distance(ability.getFiredFrom().getPosition()) < range;
    }

    @Override
    public Text getFailMessage() {
        return null;
    }
}
