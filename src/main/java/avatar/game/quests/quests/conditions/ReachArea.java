package avatar.game.quests.quests.conditions;

import avatar.events.custom.AreaEvent;
import avatar.game.areas.Area;
import avatar.game.quests.quests.Condition;
import avatar.managers.ListenerManager;
import org.spongepowered.api.event.EventListener;

public class ReachArea extends Condition implements EventListener<AreaEvent.Enter> {

    /*
     * Simply a condition to where a player must reach a location
     */

    private Area targetArea;
    private double completionRadius = 1.5;

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
}
