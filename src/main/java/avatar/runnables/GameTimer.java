package avatar.runnables;

import avatar.Avatar;
import avatar.events.custom.GameTimerTicked;
import org.spongepowered.api.Sponge;

/**
 * This is the main game runnable
 */
public class GameTimer extends CoreTimer {

    public GameTimer(long interval) {
        super(interval);

        start();
    }

    @Override
    protected void runTask() {
        //Update necessary player information
        Avatar.INSTANCE.getUserManager().tick();

        //Update Quest trackers
        Avatar.INSTANCE.getQuestManager().tick();

        Sponge.getEventManager().post(new GameTimerTicked());
    }
}
