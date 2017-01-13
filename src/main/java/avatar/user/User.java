package avatar.user;

import avatar.Avatar;
import avatar.game.areas.Area;
import avatar.game.combatlog.EntityCombatLogger;
import avatar.user.stats.IStatsPreset;
import avatar.user.stats.Stats;
import avatar.user.stats.presets.DefaultBenderPreset;
import org.spongepowered.api.Sponge;

import java.util.UUID;

/**
 * Base class for any entity that will need stats or abilities
 */
public class User {

    private UUID user;
    private Stats stats;
    private Area presentArea;
    private EntityCombatLogger combatLogger;
    private Long lastRun = System.currentTimeMillis();

    public User(UUID user){
        this(user, new DefaultBenderPreset());
    }

    public User(UUID user, IStatsPreset preset){
        this.user = user;
        stats = new Stats(preset, this);
        combatLogger = new EntityCombatLogger(user);

        Avatar.INSTANCE.getUserManager().add(this);
    }

    public boolean canBeAttacked(){return combatLogger.canReceiveDamage();}

    /**
     * Cleans up all loose ends this might have, if the user was just directly removed
     */
    public void cleanUp(){
        leaveArea();
        Avatar.INSTANCE.getUserManager().remove(this);
    }

    public boolean isPlayer(){return Sponge.getServer().getPlayer(user) != null;}

    /**
     * Use this to have this User enter an area rather than Area#enterArea(User user)
     * @param area
     */
    public void enterArea(Area area){
        if(area == presentArea)
            return;

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
        if(presentArea == null)
            return;

        presentArea.leaving(this);
        presentArea = null;
    }

    //--- Getters ---
    public Stats getStats() {
        return stats;
    }

    public Area getPresentArea(){return presentArea;}

    public UUID getUUID() {
        return user;
    }

    public EntityCombatLogger getCombatLogger() {
        return combatLogger;
    }

    public void tick() {
        //combat logger
        if((System.currentTimeMillis() - lastRun)/1000 >= 1) {
                getCombatLogger().tickInCombat();
            lastRun = System.currentTimeMillis();
        }
    }
}
