package avatar.game.quest.condition;

import avatar.manager.ListenerManager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.world.Location;

public abstract class Condition {

    /*
     * Conditions have to do with the environment in which the quest checkpoint must be completed in
     * The Check enum describes when the condition should be checked for validity.
     * These are essentially the goals of each checkpoint
     */

    private Player player;
    private Long lastWarningMessage;
    private Location startLocation;
    protected boolean valid;

    /**
     * Called every game timer iteration
     * Do logic here if needed to
     * @return
     */
    public boolean isValid(){return valid;}

    /*
     * In case there's any additional info the condition will need once it becomes active
     */
    public void setAdditionalStartInfo(){};

    /*
     * Reset the current condition
     * If boolean is true, can/should teleport player back to where they started the checkpoint at
     */
    public void reset(){
        valid = false;
        unregisterListener();
    }

    /*
     * Warning message should be sent to the player if they are being reset
     */
    public void displayWarningMessage(){};

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Condition(){}

    public void setStartingInfo() {
        startLocation = getPlayer().getLocation().copy();
        setAdditionalStartInfo();
    }

    protected Location getStartLocation(){return startLocation;}

    public Player getPlayer() {
        return player;
    }

    protected void setLastWarningMessage(){lastWarningMessage = System.currentTimeMillis();}

    /*
     * Will only send warning message every 10 seconds
     */
    protected boolean shouldSendWarningMessage(){
        if(lastWarningMessage == null)
            return true;
        return ((System.currentTimeMillis() - lastWarningMessage)/1000 >= 10);
    }

    public void unregisterListener() {
        if(this instanceof EventListener)
            ListenerManager.unregister((EventListener)this);
    }
}
