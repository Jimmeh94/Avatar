package avatar.game.ability.type;

import avatar.game.ability.AbilityStage;
import avatar.game.ability.property.AbilityPropertyBoundRange;
import avatar.game.user.User;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;

public abstract class AbilityTargetingLocation extends AbilityTargeting {

    public AbilityTargetingLocation(User owner, double x, double y, double z, double speed, long interval) {
        super(owner, x, y, z, speed, interval);
    }

    @Override
    public void run(){
        super.run();

        if(this.stage != AbilityStage.FINISH){
            display();
        }
    }

    @Override
    protected Location setInitialTarget() {
        BlockRay blockRay;
        if(((AbilityPropertyBoundRange)getProperty(AbilityPropertyBoundRange.class).get()).getRange() == -1){
            blockRay = BlockRay.from(getOwner().getEntity().get()).build();
        } else blockRay = BlockRay.from(getOwner().getEntity().get()).distanceLimit(((AbilityPropertyBoundRange)getProperty(AbilityPropertyBoundRange.class).get()).getRange()).build();

        if(blockRay.end().isPresent()){
           return ((BlockRayHit)blockRay.end().get()).getLocation();
        }
        return null;
    }
}
