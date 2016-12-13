package avatar.user.stats;

import avatar.user.User;

import java.util.List;


public interface IStatsPreset {

    /**
     * Returns a list of default stats.
     * Allows for multiple "presets" based on the User
     * @return
     */
    List<Stats.Stat> loadStats(User owner);

}
