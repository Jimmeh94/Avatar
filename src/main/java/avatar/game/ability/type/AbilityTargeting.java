package avatar.game.ability.type;

import avatar.game.ability.property.AbilityPropertyBoundRange;
import avatar.game.user.User;
import avatar.util.misc.LocationUtils;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public abstract class AbilityTargeting extends Ability{

    private Location target;
    /**
     * The scale at which to move the ability per update.
     * example: 1.0 speed would be to advance the ability by 1 block each update
     */
    private double speed;

    protected abstract Location setInitialTarget();

    public AbilityTargeting(User owner, double x, double y, double z, AbilityPropertyBoundRange range, double speed) {
        super(owner, x, y, z, range);

        this.speed = speed;
        this.target = setInitialTarget();
    }

    public Location getTarget() {
        return target;
    }

    @Override
    protected Location adjustCenter(){
        return LocationUtils.getNextLocation(getCenter(), target, speed);
    }

    public void setTarget(Location<World> target) {
        this.target = target;
    }

    public double getSpeed() {
        return speed;
    }
}
