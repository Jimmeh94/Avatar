package avatar.game.abilities;

import avatar.Avatar;
import avatar.events.custom.AbilityEvent;
import avatar.game.abilities.properties.AbilityProperty;
import avatar.game.abilities.properties.AbilityPropertyCost;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import org.spongepowered.api.Sponge;
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

    public void fire(){
        AbilityEvent.RequirementCheck check = new AbilityEvent.RequirementCheck(this, Avatar.INSTANCE.getDefaultCause());
        Sponge.getEventManager().post(check);

        if(!check.isCancelled()){
            AbilityEvent.PreFire event = new AbilityEvent.PreFire(this, Avatar.INSTANCE.getDefaultCause());
            Sponge.getEventManager().post(event);

            if(!event.isCancelled()){
                //set chunk location

                //initiate ability
                initiateAbility();

                AbilityEvent.PostFire post = new AbilityEvent.PostFire(this, Avatar.INSTANCE.getDefaultCause());
                Sponge.getEventManager().post(post);

            } else {
                for(AbilityProperty property: properties){
                    if(property instanceof AbilityPropertyCost){
                        ((AbilityPropertyCost)property).refund();
                    }
                }
                //event was cancelled, print why?
            }
        }

        //update the player's HUD
        if(owner.isPlayer()){
            ((UserPlayer)owner).updateScoreboard();
        }
    }

    public void updateTick(){
        //update chunk position after movement

        AbilityEvent.UpdateTick event = new AbilityEvent.UpdateTick(this, Avatar.INSTANCE.getDefaultCause());
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
