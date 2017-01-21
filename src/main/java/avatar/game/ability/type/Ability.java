package avatar.game.ability.type;

import avatar.Avatar;
import avatar.event.custom.AbilityEvent;
import avatar.game.ability.AbilityStage;
import avatar.game.ability.property.AbilityProperty;
import avatar.game.area.Area;
import avatar.game.user.User;
import avatar.game.user.UserPlayer;
import avatar.manager.ListenerManager;
import avatar.util.text.Messager;
import com.flowpowered.math.vector.Vector3i;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class Ability{

    /**
     * An ability should be created once the player tries to fire it. No need to store these otherwise.
     * In extended ability classes, probably want to override setLocationInfo() as well as initiateAbility
     */

    protected User owner;
    protected Location center;
    private Location firedFrom;
    protected Location oldCenter;
    private Element element;
    protected List<AbilityProperty> properties;
    protected Vector3i locationChunk; //current chunk location
    protected AbilityStage stage;
    protected Area area;

    /**
     * To move the ability
     */
    protected abstract Location adjustCenter();
    protected abstract void loadProperties(List<AbilityProperty> properties);

    public Ability(User owner, double x, double y, double z){
        this.owner = owner;
        loadProperties(properties = new ArrayList<>());

        //set location information
        Optional<Entity> optional = owner.getEntity();
        if(optional.isPresent()){
            this.firedFrom = optional.get().getLocation().add(0, 1, 0);
            this.center = this.firedFrom.copy();
            this.locationChunk = center.getChunkPosition();
            this.area = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(this.center).get();
            this.area.getAbilityManager().add(this);
            if(area.isInstanced(owner)){
                area.getInstance(owner).get().addAbility(this);
            }
            fire();
        }
    }

    public Optional<AbilityProperty> getProperty(Class<? extends AbilityProperty> clazz){
        for(AbilityProperty property: properties){
            if(property.getClass().getCanonicalName().equals(clazz.getCanonicalName())){
                return Optional.of(property);
            }
        }
        return Optional.empty();
    }

    protected void fire(){
        //checks
        for (AbilityStage abilityStage : AbilityStage.firingSequence()) {
            stage = abilityStage;
            for (AbilityProperty property : properties) {
                if (property.checkNow(stage)) {
                    if (!property.validate()) {
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

        stage = AbilityStage.UPDATE;

        Sponge.getEventManager().post(new AbilityEvent.PostFire(this, ListenerManager.getDefaultCause()));
    }


    protected void addProperty(AbilityProperty property) {
        if(!getProperty(property.getClass()).isPresent()){
            properties.add(property);
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

    public List<AbilityProperty> getProperties() {
        return properties;
    }
}
