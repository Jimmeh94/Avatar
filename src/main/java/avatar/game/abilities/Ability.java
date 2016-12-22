package avatar.game.abilities;

import avatar.Avatar;
import avatar.game.abilities.properties.AbilityProperty;
import avatar.user.User;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;

import java.util.List;

public abstract class Ability {

    private double baseDamage;
    private User owner;
    private Location center;
    private AABB hitbox;
    private Element element;
    private Text displayName;
    private int id;
    private List<AbilityProperty> properties;
    private Chunk locationChunk;

    protected abstract void initiateAbility();

    public Ability(){

    }

    protected Cause getDefaultCause(){return Cause.source(Avatar.INSTANCE.getPluginContainer()).build();}

    protected boolean canFire(){
        for(AbilityProperty property: properties){
            if(property.check(owner) == false){
                return false;
            }
        }
        return true;
    }

    public void fire(){
        if(canFire()){
            AbilityEvent.PreFire event = new AbilityEvent.PreFire(this, getDefaultCause());
            Sponge.getEventManager().post(event);

            if(!event.isCancelled()){
                //initiate ability
                initiateAbility();

                AbilityEvent.PostFire post = new AbilityEvent.PostFire(this, getDefaultCause());
                Sponge.getEventManager().post(post);
            } else {
                //print some message or something
            }
        }
    }

    public void updateTick(){
        AbilityEvent.UpdateTick event = new AbilityEvent.UpdateTick(this, getDefaultCause());
        Sponge.getEventManager().post(event);

        if(!event.isCancelled()){
            //update
            List<Ability> nearby = Avatar.INSTANCE.getAbilityManager().getNearbyAbilitiesInChunk(this);
            for(Ability ability: nearby){
                if(this.hitbox.intersects(ability.getHitbox())){
                    //made contact with another ability
                }
            }

        }
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

    public Chunk getLocationChunk() {
        return locationChunk;
    }

    public double getBaseDamage() {
        return baseDamage;
    }
}
