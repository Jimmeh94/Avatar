package avatar.game.areas;

import avatar.Avatar;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.world.Location;

public class PassiveArea extends Area {

    public PassiveArea(AreaShape shape, String displayName) {
        super(shape, displayName, AreaReferences.TEST_PASSIVE);

        for(Location location: shape.threshold){
            location.setBlockType(BlockTypes.REDSTONE_BLOCK, Cause.source(Avatar.INSTANCE.getPluginContainer()).build());
        }
    }
}
