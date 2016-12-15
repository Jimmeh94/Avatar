package avatar.game.abilities.properties;

import avatar.user.User;
import avatar.user.stats.Stats;

/**
 * The cost to use this ability
 */
public class AbilityPropertyCost extends AbilityProperty {

    private int cost;
    private Stats.StatType costType;

    public AbilityPropertyCost(String displayName, User owner, int cost, CostType type) {
        super(displayName, AbilityPropertyCheck.PRE_FIRING, owner);

        this.cost = cost;
        this.costType = type.getType();
    }

    @Override
    public boolean check(User user) {
        if(user.getStats().hasStat(costType)){
            if(user.getStats().getStat(costType).get().canAfford(cost)){
                user.getStats().getStat(costType).get().subtract(cost);
                //fire custom event to update the player's HUD information
                return true;
            }
        }
        return false;
    }

    public enum CostType{
        CHI(Stats.StatType.CHI),
        STAMINA(Stats.StatType.STAMINA);

        private Stats.StatType type;

        CostType(Stats.StatType type){this.type = type;}

        public Stats.StatType getType() {
            return type;
        }
    }
}
