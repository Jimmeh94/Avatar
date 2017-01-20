package avatar.game.ability.type;

import avatar.Avatar;
import avatar.game.ability.AbilityStage;
import avatar.game.ability.property.AbilityProperty;
import avatar.game.ability.property.AbilityPropertyBoundRange;
import avatar.game.ability.property.AbilityPropertyCollisionLogic;
import avatar.game.user.User;
import avatar.util.misc.LocationUtils;
import avatar.util.particles.effects.EffectData;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.effect.particle.ParticleTypes;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

/**
 * Use this if the ability needs to move somewhere.
 * Self casting/instant ability should just extend Ability
 */
public abstract class AbilityTargeting extends Ability implements Runnable{

    private Location target;
    /**
     * The scale at which to move the ability per update.
     * example: 1.0 speed would be to advance the ability by 1 block each update
     */
    private double speed;
    private Task task;
    private Long interval;
    protected EffectData effectData;

    protected abstract Location setInitialTarget();
    protected abstract void display();

    public AbilityTargeting(User owner, double x, double y, double z, double speed, long interval) {
        super(owner, x, y, z);

        this.interval = interval;

        if(!getProperty(AbilityPropertyBoundRange.class).isPresent()){
            addProperty(new AbilityPropertyBoundRange(null, this, AbilityPropertyBoundRange.INFINITE));
        }

        this.speed = speed;
        this.target = setInitialTarget();

        effectData = EffectData.builder().amount(50).center(getCenter()).particle(ParticleTypes.FLAME).build();
    }

    @Override
    protected void fire(){
        super.fire();

        if(this.stage != AbilityStage.FINISH){
            Task.Builder taskBuilder = Sponge.getScheduler().createTaskBuilder();
            task = taskBuilder.intervalTicks(interval).execute(this).submit(Avatar.INSTANCE);
        }
    }

    @Override
    public void cancel(Text cause){
        super.cancel(cause);

        task.cancel();
    }

    @Override
    public void run(){
        //set the location and check if at target
        //check on properties for UPDATE
        //if any of those !validate, stop the ability
        setLocationInfo();
        effectData.setDisplayAt(getCenter());
        if(this.getCenter().getPosition().distance(this.getTarget().getPosition()) <= 0.5){
            this.cancel(null);
        }

        for(AbilityProperty property: properties){
            if(property.checkNow(stage)){
                if(!property.validate()){
                    this.cancel(property.getFailMessage());
                }
            }
        }
    }



    protected void setLocationInfo(){
        this.oldCenter = center.copy();
        this.center = adjustCenter();
        if(this.center == null)
            return;

        this.locationChunk = center.getChunkPosition();

        if(getProperty(AbilityPropertyCollisionLogic.SquareCollisionLogic.class).isPresent()){
            ((AbilityPropertyCollisionLogic.SquareCollisionLogic)getProperty(AbilityPropertyCollisionLogic.SquareCollisionLogic.class).get()).offset(oldCenter, center);
        }

        if(!this.area.contains(this.center)){
            if(this.area != null){
                area.getAbilityManager().remove(this);
                if(area.isInstanced(this)){
                    area.getInstance(this).get().removeAbility(this);
                }
            }

            this.area = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(this.center).get();
            this.area.getAbilityManager().add(this);
            if (area.isInstanced(owner)) {
                area.getInstance(owner).get().addAbility(this);
            }
        }
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
