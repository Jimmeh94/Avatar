package avatar.managers;

import avatar.game.areas.Area;
import avatar.game.areas.AreaReferences;
import org.spongepowered.api.world.Location;

import java.util.Optional;

public class AreaManager extends Manager<Area> {

    public AreaManager(){
        //load all parent areas

        add(new Area(AreaReferences.GLOBAL));
    }

    public Optional<Area> getAreaByContainedLocation(Location location){
        Optional<Area> give = Optional.empty();

        for(Area area: this.objects){
            if(area.contains(location)){
                give = Optional.of(area.getAreaThatContains(location));
            }
        }

        return give;
    }

    public Optional<Area> getAreaByReference(AreaReferences reference) {
        for(Area area: objects){
            if(area.is(reference)){
                return Optional.of(area);
            } else if(area.hasChild(reference)){
                return area.getChild(reference);
            }
        }

        return Optional.empty();
    }
}
