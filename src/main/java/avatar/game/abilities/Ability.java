package avatar.game.abilities;

import avatar.Avatar;
import avatar.game.abilities.properties.AbilityProperty;
import avatar.game.abilities.properties.AbilityPropertyCost;
import avatar.user.User;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.util.AABB;
import org.spongepowered.api.world.Chunk;
import org.spongepowered.api.world.Location;

import java.util.List;

public abstract class Ability {

    private User owner;
    private Location center, firedFrom;
    private AABB hitbox;
    private Element element;
    private Text displayName;
    private int id;
    private List<AbilityProperty> properties;
    private Chunk locationChunk; //current chunk location

    protected abstract void initiateAbility();

    public Ability(){

    }

    public boolean hasProperty(AbilityPropertyReference reference){
        for(AbilityProperty property: properties){
            if(property.getClass() == reference.getClassType()){
                return true;
            }
        }
        return false;
    }

    public AbilityProperty getProperty(AbilityPropertyReference reference){
        for(AbilityProperty property: properties){
            if(property.getClass() == reference.getClassType()){
                return property;
            }
        }
        return null;
    }

    protected Cause getDefaultCause(){return Cause.source(Avatar.INSTANCE.getPluginContainer()).build();}

    public void fire(){
        AbilityEvent.RequirementCheck check = new AbilityEvent.RequirementCheck(this, getDefaultCause());
        Sponge.getEventManager().post(check);

        if(!check.isCancelled()){
            AbilityEvent.PreFire event = new AbilityEvent.PreFire(this, getDefaultCause());
            Sponge.getEventManager().post(event);

            if(!event.isCancelled()){
                //initiate ability
                initiateAbility();

                AbilityEvent.PostFire post = new AbilityEvent.PostFire(this, getDefaultCause());
                Sponge.getEventManager().post(post);

                if(!post.isCancelled()){}
            } else {
                if(hasProperty(AbilityPropertyReference.COST)){
                    ((AbilityPropertyCost)getProperty(AbilityPropertyReference.COST)).refund();
                }
                //event was cancelled, print why?
            }
        }

        //update the player's HUD
    }

    public void updateTick(){
        AbilityEvent.UpdateTick event = new AbilityEvent.UpdateTick(this, getDefaultCause());
        Sponge.getEventManager().post(event);

        if(!event.isCancelled()){ //update
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

    public Location getFiredFrom() {
        return firedFrom;
    }
}
