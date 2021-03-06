package avatar.game.ability.type;

import avatar.game.ability.AbilityStage;
import avatar.game.user.User;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.world.Location;

public abstract class AbilityTargetingEntity extends AbilityTargeting {

    private Entity targetEntity;

    public AbilityTargetingEntity(User owner, double x, double y, double z, double speed, long interval, Entity targetEntity) {
        super(owner, x, y, z, speed, interval);

        this.targetEntity = targetEntity;
        this.setTarget(targetEntity.getLocation().add(0, 1, 0));
    }

    private boolean entityValid(){
        return !targetEntity.isRemoved() && targetEntity.isLoaded();
    }

    @Override
    public void run(){
        super.run();

        if(this.stage != AbilityStage.FINISH){
            if(entityValid()){
                setTarget(targetEntity.getLocation());
                display();
                return;
            } else {
                this.cancel(null);
            }
        }
    }

    @Override
    protected Location setInitialTarget() {
        return null;
    }


}
