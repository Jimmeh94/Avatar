package avatar.events.custom;

import avatar.Avatar;
import avatar.game.areas.Area;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public class AreaEvent implements Event {
    private Area leaving, entering;

    public AreaEvent(Area entering, Area leaving){
        this.entering = entering;
        this.leaving = leaving;
    }

    public Area getLeaving() {
        return leaving;
    }

    public Area getEntering() {
        return entering;
    }

    @Override
    public Cause getCause() {
        return Cause.source(Avatar.INSTANCE.getPluginContainer()).build();
    }
}
