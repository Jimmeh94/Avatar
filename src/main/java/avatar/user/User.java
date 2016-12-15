package avatar.user;

import avatar.Avatar;
import avatar.game.areas.Area;
import avatar.user.stats.IStatsPreset;
import avatar.user.stats.Stats;
import avatar.user.stats.presets.DefaultBenderPreset;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.Entity;

import java.util.Optional;
import java.util.UUID;

/**
 * Base class for any entity that will need stats or abilities
 */
public class User {

    private UUID user;
    private Stats stats;
    private Area presentArea;

    public User(UUID user){
        this(user, new DefaultBenderPreset());
    }

    public User(UUID user, IStatsPreset preset){
        this.user = user;
        stats = new Stats(preset, this);

        Avatar.INSTANCE.getUserManager().add(this);
    }

    /**
     * Cleans up all loose ends this might have, if the user was just directly removed
     */
    public void cleanUp(){
        Optional<Entity> entity = getEntity();
        if(!getEntity().isPresent())
            return;

        leaveArea();
    }

    public Stats getStats() {
        return stats;
    }

    public Area getPresentArea(){return presentArea;}

    public UUID getUUID() {
        return user;
    }

    public Optional<Entity> getEntity(){
        return Sponge.getGame().getServer().getWorld(user).get().getEntity(user);
    }

    public boolean isPlayer(){return Sponge.getServer().getPlayer(user) != null;}

    /**
     * Use this to have this User enter an area rather than Area#enterArea(User user)
     * @param area
     */
    public void enterArea(Area area){
        if(presentArea != null){
            leaveArea();
        }

        presentArea = area;
        presentArea.entering(this);
    }

    /**
     * Use this to have this User leave the current area, rather than Area#leaveArea()
     */
    public void leaveArea() {
        presentArea.leaving(this);
        presentArea = null;
    }
}
