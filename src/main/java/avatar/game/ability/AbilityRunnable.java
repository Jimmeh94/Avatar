package avatar.game.ability;

import avatar.Avatar;
import avatar.event.custom.AbilityEvent;
import avatar.game.user.User;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.scheduler.Task;

public abstract class AbilityRunnable extends Ability implements Runnable, EventListener<AbilityEvent.Cancelled> {

    private long period, delay;
    /**
     * The amount of cycles to perform.
     * This isn't counting how many ticks to run.
     * Set to -1 for infinite/cancellation not based on cycles
     */
    private int cycleLifetime, cycleCounter = 0;
    private Task task;

    public AbilityRunnable(User owner, long period, long delay) {
        this(owner, period, delay, -1);
    }

    public AbilityRunnable(User owner, long period, long delay, int cycleLifetime){
        super(owner);

        this.period = period;
        this.delay = delay;
        this.cycleLifetime = cycleLifetime;
    }

    @Override
    public void run(){
        if(cycleLifetimeExpired()){
            this.stop();
            return;
        }

        AbilityEvent.UpdateTick event = new AbilityEvent.UpdateTick(this, Avatar.INSTANCE.getDefaultCause());
        Sponge.getEventManager().post(event);

        this.setLocationInfo();
    }

    protected void start(){
        Task.Builder taskBuilder = Sponge.getScheduler().createTaskBuilder();
        task = taskBuilder.delayTicks(delay).intervalTicks(period).execute(this).submit(Avatar.INSTANCE);
    }

    @Override
    public void handle(AbilityEvent.Cancelled event) throws Exception {
        Sponge.getEventManager().post(new AbilityEvent.Cancelled(this, Avatar.INSTANCE.getDefaultCause()));
    }

    protected void stop(){
        task.cancel();
    }

    protected boolean cycleLifetimeExpired(){
        if(cycleLifetime == -1){
            return false;
        } else {
            return cycleCounter++ > cycleLifetime;
        }
    }
}
