package avatar.user;

import avatar.user.stats.IStatsPreset;
import avatar.user.stats.Stats;
import avatar.user.stats.presets.DefaultBenderPreset;

import java.util.UUID;

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
}
