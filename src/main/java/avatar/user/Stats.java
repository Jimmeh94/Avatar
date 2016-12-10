package avatar.user;

import java.util.List;
import java.util.Optional;

public class Stats {

    private List<Stat> stats;

    public Stats(IStatsPreset preset){
        stats = preset.loadStats();
    }

    public boolean hasStat(StatType type){
        Optional<Stat> temp = stats.stream().filter(stat -> stat.getType().equals(type)).findFirst();
        if(temp.isPresent()){
            return true;
        }
        return false;
    }

    public Optional<Stat> getStat(final StatType type){
        return stats.stream().filter(stat -> stat.getType().equals(type)).findFirst();
    }

    public void removeStat(StatType type){
        Optional<Stat> temp = stats.stream().filter(stat -> stat.getType().equals(type)).findFirst();
        if(temp.isPresent()){
            stats.remove(temp.get());
        }
    }

    public void addStat(Stat stat){
        if(hasStat(stat.getType())){
            removeStat(stat.getType());
        }
        stats.add(stat);
    }

    public List<Stat> getStats() {
        return stats;
    }

    //------------------------------------------------------

    public enum StatType{
        HEALTH,
        CHI,
        STAMINA,
        ARMOR,
        SPEED,
        RESISTANCE_PHYSICAL,
        RESISTANCE_FIRE,
        RESISTANCE_EARTH,
        RESISTANCE_WATER,
        RESISTANCE_AIR,
        REGENERATION_HEALTH,
        REGENERATION_CHI,
        REGENERATION_STAMINA,
        CRITICAL_PERCENTAGE_PHYSICAL,
        CRITICAL_PERCENTAGE_FIRE,
        CRITICAL_PERCENTAGE_EARTH,
        CRITICAL_PERCENTAGE_WATER,
        CRITICAL_PERCENTAGE_AIR,
    }

    //-----------------------------------------------------

    public class Stat {

        private StatType type;
        private double current, max;
        private StatMemory memory;
        private User owner;

        public Stat(StatType type, User owner){
            this(type, owner, 1);
        }

        public Stat(StatType type, User owner, double current){
            this(type, owner, current, 1);
        }

        public Stat(StatType type, User owner, double current, double max){
            this.type = type;
            this.owner = owner;
            this.current = current;
            this.max = max;
        }

        public void alter(double newMax){
            alter(newMax, current);
        }

        public void alter(double newMax, double newCurrent){
            alter(newMax, newCurrent, null);
        }

        /**
         * Used to apply
         * @param newMax
         * @param newCurrent
         * @param completionAction
         */
        public void alter(double newMax, double newCurrent, IStatMemoryCompletion completionAction){
            memory = new StatMemory(max, completionAction);
            this.max = newMax;
            this.current = newCurrent;

            valueCheck();
        }

        private void valueCheck(){
            if(this.current > this.max){
                this.current = this.max;
            }
        }

        public boolean isAltered(){
            return memory != null;
        }

        /**
         * Restores this stat back to its pre-altered state
         */
        public void restoreMemory(){
            this.max = memory.restore();
            memory = null;

            valueCheck();
        }

        public StatType getType() {
            return type;
        }

        /**
         * Gets the currently available max value, whether it's altered or the base
         * @return max
         */
        public double getAvailableMax(){
            return max;
        }

        /**
         * Gets the base max value of this stat, unaltered
         * @return max
         */
        public double getMax() {
            if(memory != null){
                double memMax = memory.getReturnTo();
                if(memMax != max)
                    return max;
            }
            return max;
        }

        public double getCurrent() {
            return current;
        }

        public StatMemory getMemory() {
            return memory;
        }

        public User getOwner() {
            return owner;
        }

        public double getCurrentAvailablePercent(){
            return (current/getAvailableMax()) * 100;
        }
    }

    //--------------------------------------------------------------

    public class StatMemory{

        private double returnTo;
        private IStatMemoryCompletion completionAction;
        private Stat owningStat;

        public StatMemory(double returnTo){this(returnTo, null);}

        public StatMemory(double returnTo, IStatMemoryCompletion completionAction){
            this.returnTo = returnTo;
            this.completionAction = completionAction;
        }

        public double getReturnTo() {
            return returnTo;
        }

        public double restore(){
            completionAction();
            return getReturnTo();
        }

        private void completionAction(){
            if(completionAction != null){
                completionAction.doAction(this);
            }
        }

        protected Stat getOwningStat(){return owningStat;}
    }

    //----------------------------------------------------------------

    public interface IStatMemoryCompletion{
        void doAction(StatMemory memory);
    }

}
