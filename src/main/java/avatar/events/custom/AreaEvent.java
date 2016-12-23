package avatar.events.custom;

import avatar.Avatar;
import avatar.game.areas.Area;
import avatar.user.User;
import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.cause.Cause;

public abstract class AreaEvent implements Event {
    private Area area;
    private User user;

    public AreaEvent(User user, Area area){
        this.user = user;
        this.area = area;
    }

    public User getUser() {
        return user;
    }

    public Area getArea() {
        return area;
    }

    @Override
    public Cause getCause() {
        return Cause.source(Avatar.INSTANCE.getPluginContainer()).build();
    }

    public static class Enter extends AreaEvent{
        public Enter(User user, Area area) {
            super(user, area);
        }
    }

    public static class Exit extends AreaEvent{
        public Exit(User user, Area area) {
            super(user, area);
        }
    }
}
