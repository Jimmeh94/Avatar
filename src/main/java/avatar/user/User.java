package avatar.user;

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

    public User(UUID user){
        this(user, new DefaultBenderPreset());
    }

    public User(UUID user, IStatsPreset preset){
        this.user = user;
        stats = new Stats(preset, this);
    }

    public UUID getUUID() {
        return user;
    }

    public Optional<Entity> getEntity(){
        return Sponge.getGame().getServer().getWorld(user).get().getEntity(user);
    }

    public boolean isPlayer(){return Sponge.getServer().getPlayer(user) != null;}
}
