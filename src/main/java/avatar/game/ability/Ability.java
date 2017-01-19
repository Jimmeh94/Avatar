package avatar.game.ability;

import avatar.Avatar;
import avatar.game.ability.property.AbilityProperty;
import avatar.game.area.Area;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import avatar.util.misc.LocationUtils;
import avatar.util.text.Messager;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.world.Location;

import java.util.List;
import java.util.Optional;

public abstract class Ability{

    /**
     * An ability should be created once the player tries to fire it. No need to store these otherwise.
     * In extended ability classes, probably want to override setLocationInfo() as well as initiateAbility
     */

    private User owner;
    private Location center, firedFrom, oldCenter;
    private AABB hitbox;
    private Element element;
    private List<AbilityProperty> properties;
    private Vector3i locationChunk; //current chunk location
    private AbilityStage stage;
    private Area area;

    /**
     * To move the ability
     */
    protected abstract void adjustCenter();
    protected abstract List<AbilityProperty> loadProperties();

    public Ability(User owner, double x, double y, double z){
        this.owner = owner;
        this.properties = loadProperties();

        //set location information
        Optional<Entity> optional = owner.getEntity();
        if(optional.isPresent()){
            this.firedFrom = optional.get().getLocation();
            this.center = this.firedFrom.copy();

            Location temp = center.copy();
            this.hitbox = new AABB(temp.getX() - x/2, temp.getY() - y/2, temp.getZ() - z/2,
                                    temp.getX() + x/2, temp.getY() + y/2, temp.getZ() + z/2);
            this.locationChunk = center.getChunkPosition();
            this.area = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(this.center).get();
            this.area.getAbilityManager().add(this);
            if(area.isInstanced(owner)){
                area.getInstance(owner).get().addAbility(this);
            }
            fire();
        }
    }

    private void fire(){
        //checks
        for(AbilityStage abilityStage: AbilityStage.firingSequence()){
            stage = abilityStage;
            for(AbilityProperty property: properties){
                if(property.checkNow(stage)){
                    if(!property.validate()){
                        this.cancel(property.getFailMessage());
                        return;
                    }
                }
            }
        }

        //update the player's HUD
        //Shouldn't need to do this since it updates so frequently
        /*if(owner.isPlayer()){
            ((UserPlayer)owner).updateScoreboard();
        }*/
    }

    public boolean update(){
        stage = AbilityStage.UPDATE;
        for(AbilityProperty property: properties){
            if(property.checkNow(stage)){
                if(!property.validate()){
                    this.cancel(property.getFailMessage());
                    return false;
                }
            }
        }
        setLocationInfo();
        return true;
    }

    protected void setLocationInfo(){
        this.oldCenter = center.copy();
        adjustCenter();
        this.locationChunk = center.getChunkPosition();
        this.hitbox = hitbox.offset(LocationUtils.getOffsetBetween(oldCenter, center));

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

    public User getOwner() {
        return owner;
    }

    public Area getArea() {
        return area;
    }

    public Location getCenter() {
        return center;
    }

    public AABB getHitbox() {
        return hitbox;
    }

    public Element getElement() {
        return element;
    }

    public Vector3i getLocationChunk() {
        return locationChunk;
    }

    public Location getFiredFrom() {
        return firedFrom;
    }

    /**
     * Used to cancel the ability whether it's reached its destination or had a problem before
     * @param cancelCause
     */
    public void cancel(Text cancelCause) {
        stage = AbilityStage.FINISH;

        for(AbilityProperty property: properties){
            if(property.checkNow(stage)){
                property.validate();
            }
        }

        if(owner.isPlayer() && cancelCause != null)
            Messager.sendMessage(((UserPlayer)owner).getPlayer().get(), cancelCause, Optional.of(Messager.Prefix.ERROR));

        if(this.area != null){
            area.getAbilityManager().remove(this);
            if(area.isInstanced(this)){
                area.getInstance(this).get().removeAbility(this);
            }
        }
    }
}
