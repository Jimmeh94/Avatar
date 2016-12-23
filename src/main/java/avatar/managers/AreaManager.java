package avatar.managers;

import avatar.game.areas.Area;
import org.spongepowered.api.world.Location;

import java.util.Optional;

public class AreaManager extends Manager<Area> {

    public AreaManager(){
        //load all areas

        //add(new PassiveArea(new Area.AreaCircle(new Location(Sponge.getServer().getWorlds().toArray(new World[]{})[0], 50, 50, 50), 10, 256), "Test Area"));
    }

    public Optional<Area> getAreaByContainedLocation(Location location){
        Optional<Area> give = Optional.empty();

        for(Area area: this.objects){
            if(area.contains(location)){
                give = Optional.of(area);
            }
        }

        return give;
    }

    public int getAvailableID() {
        return objects.size();
    }
}
