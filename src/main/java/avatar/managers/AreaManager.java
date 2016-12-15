package avatar.managers;

import avatar.game.areas.Area;
import org.spongepowered.api.world.Location;

import java.util.Optional;

public class AreaManager extends Manager<Area> {

    public Optional<Area> find(Location location){
        Optional<Area> give = Optional.empty();

        for(Area area: this.objects){
            if(area.has(location)){
                give = Optional.of(area);
            }
        }

        return give;
    }

}
