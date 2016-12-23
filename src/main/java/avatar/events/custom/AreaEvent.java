package avatar.events.custom;

import avatar.Avatar;
import avatar.game.areas.Area;
import avatar.user.User;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public class AreaEvent implements Event {
    private Area leaving, entering;
    private User user;

    public AreaEvent(User user, Area entering, Area leaving){
        this.user = user;
        this.entering = entering;
        this.leaving = leaving;
    }

    public User getUser() {
        return user;
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
