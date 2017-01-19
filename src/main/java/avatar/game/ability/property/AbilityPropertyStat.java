package avatar.game.ability.property;

import avatar.game.ability.Ability;
import avatar.game.ability.AbilityStage;
import avatar.game.user.stats.Stats;
import org.spongepowered.api.text.Text;

public abstract class AbilityPropertyStat extends AbilityProperty{

    protected Stats.StatType type;

    public AbilityPropertyStat(String displayName, Ability ability, Stats.StatType type) {
        super(displayName, ability, AbilityStage.HIT);

        this.type = type;
    }

    //*** Adjust ***
    public static class Adjust extends AbilityPropertyStat{

        private double adjust;

        public Adjust(String displayName, Ability ability, Stats.StatType type, double amount) {
            super(displayName, ability, type);

            this.adjust = amount;
        }

        @Override
        public boolean validate() {
            return false;
        }

        @Override
        public Text getFailMessage() {
            return null;
        }
    }

    //*** Restore ***
    public static class Restore extends AbilityPropertyStat{

        public Restore(String displayName, Ability ability, Stats.StatType type) {
            super(displayName, ability, type);
        }

        @Override
        public boolean validate() {
            if(ability.getOwner().getStats().getStat(type).isPresent())
                ability.getOwner().getStats().getStat(type).get().restoreMemory();
            return true;
        }

        @Override
        public Text getFailMessage() {
            return null;
        }
    }
}
