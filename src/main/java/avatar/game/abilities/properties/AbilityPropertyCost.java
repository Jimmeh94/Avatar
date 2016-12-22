package avatar.game.abilities.properties;

import avatar.Avatar;
import avatar.game.abilities.Ability;
import avatar.game.abilities.AbilityEvent;
import avatar.user.User;
import avatar.user.UserPlayer;
import avatar.user.stats.Stats;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.Order;

/**
 * The cost to use this ability
 */
public class AbilityPropertyCost extends AbilityProperty implements EventListener<AbilityEvent.RequirementCheck>{

    private int cost;
    private Stats.StatType costType;

    public AbilityPropertyCost(String displayName, Ability ability, int cost, Stats.StatType type) {
        super(displayName, ability);

        this.cost = cost;
        this.costType = type;
    }

    @Override
    protected void register() {
        Sponge.getEventManager().registerListener(Avatar.INSTANCE, AbilityEvent.RequirementCheck.class, Order.FIRST, this);
    }

    public void refund(){
        User user = this.ability.getOwner();
        if(user.getStats().hasStat(costType)){
            user.getStats().getStat(costType).get().add(cost);
        }
    }

    @Override
    public void handle(AbilityEvent.RequirementCheck requirementCheck) throws Exception {
        User user = this.ability.getOwner();
        if(user.getStats().hasStat(costType)){
            if(user.getStats().getStat(costType).get().canAfford(cost)){
                user.getStats().getStat(costType).get().subtract(cost);
            }
        }
        requirementCheck.setCancelled(true);
    }

    @Override
    public void printFailMessage(UserPlayer user) {

    }
}
