package avatar.events.custom;

import avatar.Avatar;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public class GameTimerTicked implements Event {

    @Override
    public Cause getCause() {
        return Cause.source(Avatar.INSTANCE.getPluginContainer()).build();
    }
}
