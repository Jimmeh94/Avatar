package avatar.game.ability.property;

import avatar.Avatar;
import avatar.event.custom.AbilityEvent;
import avatar.game.ability.type.Ability;
import avatar.game.ability.AbilityStage;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;

public class AbilityPropertyCharge extends AbilityProperty implements Runnable{

    private int duration, countdown;
    private Task task;
    private boolean charged = false;

    public AbilityPropertyCharge(String displayName, Ability ability, int duration) {
        super(displayName, ability, AbilityStage.REQUIREMENT_CHECK);

        this.duration = duration;
        this.countdown = duration;
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

   // @Override
    public void handle(AbilityEvent.RequirementCheck event) throws Exception {
        if(event.getAbility() != ability)
            return;
        if(!charged){
            if(task == null){
                Task.Builder taskBuilder = Sponge.getScheduler().createTaskBuilder();
                task = taskBuilder.delayTicks(0L).intervalTicks(20L).execute(this).submit(Avatar.INSTANCE);
            }
            event.setCancelled(true);
        }
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
