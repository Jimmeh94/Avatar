package avatar.game.ability;

import avatar.Avatar;
import avatar.event.custom.AbilityEvent;
import avatar.game.ability.property.AbilityProperty;
import avatar.game.ability.property.AbilityPropertyCharge;
import avatar.game.ability.property.AbilityPropertyCost;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import avatar.util.misc.LocationUtils;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.world.Location;

import java.util.List;
import java.util.Optional;

public abstract class Ability {

    /**
     * An ability should be created once the player tries to fire it. No need to store these otherwise.
     * In extended ability classes, probably want to override setLocationInfo() as well as initiateAbility
     */

    private User owner;
    private Location center, firedFrom, oldCenter;
    private AABB hitbox;
    private Element element;
    private Text displayName;
    private int id;
    private List<AbilityProperty> properties;
    private Vector3i locationChunk; //current chunk location

    protected abstract void initiateAbility();
    protected abstract void adjustCenter();

    public Ability(User owner, double x, double y, double z){
        this.owner = owner;

        //set location information
        Optional<Entity> optional = owner.getEntity();
        if(optional.isPresent()){
            this.firedFrom = optional.get().getLocation();
            this.center = this.firedFrom.copy();

            Location temp = center.copy();
            this.hitbox = new AABB(temp.getX() - x/2, temp.getY() - y/2, temp.getZ() - z/2,
                                    temp.getX() + x/2, temp.getY() + y/2, temp.getZ() + z/2);
            this.locationChunk = center.getChunkPosition();
            fire();
        }
    }

    private void fire(){
        AbilityEvent.RequirementCheck check = new AbilityEvent.RequirementCheck(this, Avatar.INSTANCE.getDefaultCause());
        Sponge.getEventManager().post(check);

        if(!check.isCancelled()){
            AbilityEvent.PreFire event = new AbilityEvent.PreFire(this, Avatar.INSTANCE.getDefaultCause());
            Sponge.getEventManager().post(event);

            if(!event.isCancelled()){
                //initiate ability
                initiateAbility();

                AbilityEvent.PostFire post = new AbilityEvent.PostFire(this, Avatar.INSTANCE.getDefaultCause());
                Sponge.getEventManager().post(post);

            } else {
                cancel();
                //event was cancelled, print why?
            }
        } else {
            cancel();
        }

        //update the player's HUD
        if(owner.isPlayer()){
            ((UserPlayer)owner).updateScoreboard();
        }
    }

    public void cancel(){
        for(AbilityProperty property: properties){
            if(property instanceof AbilityPropertyCharge){
                ((AbilityPropertyCharge)property).stop();
            } else if(property instanceof AbilityPropertyCost){
                ((AbilityPropertyCost)property).refund();
            }
        }
    }

    protected void setLocationInfo(){
        //adjust center
        this.oldCenter = center.copy();
        adjustCenter();
        this.locationChunk = center.getChunkPosition();
        this.hitbox = hitbox.offset(LocationUtils.getOffsetBetween(oldCenter, center));
    }

    public User getOwner() {
        return owner;
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

    public Text getDisplayName() {
        return displayName;
    }

    public int getId() {
        return id;
    }

    public Vector3i getLocationChunk() {
        return locationChunk;
    }

    public Location getFiredFrom() {
        return firedFrom;
    }
}
