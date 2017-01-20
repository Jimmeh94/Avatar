package avatar.game.ability.type;

import avatar.game.ability.property.AbilityPropertyBoundRange;
import avatar.game.user.User;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;

public abstract class AbilityTargetingLocation extends AbilityTargeting {

    public AbilityTargetingLocation(User owner, double x, double y, double z, AbilityPropertyBoundRange range, double speed) {
        super(owner, x, y, z, range, speed);
    }

    @Override
    public boolean update(){
        if(super.update()){
            return this.getRangeProperty().validate() && this.getTarget() != null;
        } else return false;
    }

    @Override
    protected Location setInitialTarget() {
        BlockRay blockRay;
        if(getRangeProperty().getRange() == -1){
            blockRay = BlockRay.from(getOwner().getEntity().get()).build();
        } else blockRay = BlockRay.from(getOwner().getEntity().get()).distanceLimit(getRangeProperty().getRange()).build();

        if(blockRay.end().isPresent()){
           return ((BlockRayHit)blockRay.end().get()).getLocation();
        }
        return null;
    }
}
