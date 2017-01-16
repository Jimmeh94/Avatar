package avatar.game.ability.property;

import avatar.Avatar;
import avatar.event.custom.AbilityEvent;
import avatar.game.ability.Ability;
import avatar.game.user.UserPlayer;
import avatar.manager.ListenerManager;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.scheduler.Task;

public class AbilityPropertyCharge extends AbilityProperty implements Runnable, EventListener<AbilityEvent.RequirementCheck> {

    private int duration, countdown;
    private Task task;
    private boolean charged = false;

    public AbilityPropertyCharge(String displayName, Ability ability, int duration) {
        super(displayName, ability);

        this.duration = duration;
        this.countdown = duration;
    }

    @Override
    public void printFailMessage(UserPlayer user) {

    }

    @Override
    protected void register() {
        ListenerManager.register(AbilityEvent.RequirementCheck.class, Order.FIRST, this);
    }

    public void stop(){
        if(this.task != null)
            task.cancel();
    }

    @Override
    public void run() {
        countdown--;
        if(countdown == 0){
            charged = true;
        }
    }

    @Override
    public void handle(AbilityEvent.RequirementCheck requirementCheck) throws Exception {
        if(!charged){
            if(task == null){
                Task.Builder taskBuilder = Sponge.getScheduler().createTaskBuilder();
                task = taskBuilder.delayTicks(0L).intervalTicks(20L).execute(this).submit(Avatar.INSTANCE);
            }
            requirementCheck.setCancelled(true);
        }
    }
}
