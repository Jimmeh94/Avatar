package avatar.game.ability.type;

import avatar.game.ability.property.AbilityPropertyBoundRange;
import avatar.game.user.User;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.world.Location;

public abstract class AbilityTargetingEntity extends AbilityTargeting {

    private Entity targetEntity;

    public AbilityTargetingEntity(User owner, double x, double y, double z, AbilityPropertyBoundRange range, double speed, Entity targetEntity) {
        super(owner, x, y, z, range, speed);

        this.targetEntity = targetEntity;
        this.setTarget(targetEntity.getLocation().add(0, 1, 0));
    }

    private boolean entityValid(){
        return !targetEntity.isRemoved() && targetEntity.isLoaded();
    }

    @Override
    public boolean update(){
        if(super.update()){
            if(entityValid() && getRangeProperty().validate()){
                setTarget(targetEntity.getLocation());
                return true;
            }
        }
        return false;
    }

    @Override
    protected Location setInitialTarget() {
        return null;
    }


}
