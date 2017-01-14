package avatar.game.abilities.properties;

import avatar.events.custom.AbilityEvent;
import avatar.game.abilities.Ability;
import avatar.managers.ListenerManager;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import avatar.game.user.stats.Stats;
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
        ListenerManager.register(AbilityEvent.RequirementCheck.class, Order.FIRST, this);
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
            } else requirementCheck.setCancelled(true);
        } else requirementCheck.setCancelled(true);
    }

    @Override
    public void printFailMessage(UserPlayer user) {

    }
}
