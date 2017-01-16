package avatar.game.quest.condition;

import avatar.event.custom.AreaEvent;
import avatar.game.area.Area;
import avatar.manager.ListenerManager;
import com.flowpowered.math.vector.Vector3d;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.world.Location;

public class ReachArea extends Condition implements EventListener<AreaEvent.Enter> {

    /*
     * Simply a condition to where a player must reach a location
     */

    private Area targetArea;

    public ReachArea(Area area) {
        this.targetArea = area;
    }

    @Override
    public void reset(){
        super.reset();

        unregisterListener();
        setAdditionalStartInfo();
    }

    @Override
    public void setAdditionalStartInfo() {
        ListenerManager.register(AreaEvent.Enter.class, this);
    }

    @Override
    public void handle(AreaEvent.Enter event) throws Exception {
        if(event.getArea() == this.targetArea){
            if(event.getUser().isPlayer()){
                if(event.getUser().getUUID().equals(this.getPlayer().getUniqueId())){
                    valid = true;
                    unregisterListener();
                }
            }
        }
    }

    public int getTrackerDistance(int distance) {
        if(targetArea.getShape().isYWithinBounds(getPlayer().getLocation().getY())){
            Location use = targetArea.getCenter();
            use.setPosition(new Vector3d(use.getX(), getPlayer().getLocation().getY(), use.getZ()));
            distance = (int) use.getPosition().distance(getPlayer().getLocation().getPosition());
        }

        distance -= targetArea.getShape().getRadius();
        return distance;
    }
}
