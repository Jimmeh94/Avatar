package avatar.game.ability.property;

import avatar.Avatar;
import avatar.game.ability.type.Ability;
import avatar.game.ability.AbilityStage;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

/**
 * How long the ability lasts
 */
public class AbilityPropertyDuration extends AbilityProperty implements Runnable{

    private long period, delay;
    /**
     * The amount of cycles to perform.
     * This isn't counting how many ticks to run.
     * Set to -1 for infinite/cancellation not based on cycles
     */
    private int cycleLifetime, cycleCounter = 0;
    private Task task;

    public AbilityPropertyDuration(String displayName, Ability ability, long period, long delay) {
        this(displayName, ability, period, delay, -1);
    }

    public AbilityPropertyDuration(String displayName, Ability ability, long period, long delay, int cycleLifetime){
        super(displayName, ability, AbilityStage.UPDATE);

        this.period = period;
        this.delay = delay;
        this.cycleLifetime = cycleLifetime;
    }

    @Override
    public void run() {
        ability.update();
    }

    private void start(){
        Task.Builder taskBuilder = Sponge.getScheduler().createTaskBuilder();
        task = taskBuilder.delayTicks(delay).intervalTicks(period).execute(this).submit(Avatar.INSTANCE);
    }

    private void stop(){
        task.cancel();
    }

    private boolean cycleLifetimeExpired(){
        if(cycleLifetime == -1){
            return false;
        } else {
            return cycleCounter++ > cycleLifetime;
        }
    }

    @Override
    public boolean validate() {
        if(cycleLifetimeExpired()){
            this.stop();
            return false;
        } else return true;
    }

    @Override
    public Text getFailMessage() {
        return null;
    }
}
