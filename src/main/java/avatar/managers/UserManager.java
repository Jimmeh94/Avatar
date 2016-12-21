package avatar.managers;

import avatar.Avatar;
import avatar.game.areas.Area;
import avatar.user.User;
import avatar.user.UserPlayer;
import avatar.utilities.misc.LocationUtils;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.world.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserManager extends Manager<User>{

    private long lastRun = System.currentTimeMillis();

    public Optional<User> find(UUID uuid){
        Optional<User> give = Optional.empty();

        for(User user: this.objects){
            if(user.getUUID().equals(uuid)){
                give = Optional.of(user);
            }
        }

        return give;
    }

    public void tick() {
        //Checking if they have entered or left an area
        areaTick();

        //Check combat status
        combatTick();
    }

    public List<UserPlayer> getPlayers() {
        List<UserPlayer> players = new ArrayList<>();
        for(User user: objects){
            if(user instanceof UserPlayer)
                players.add(((UserPlayer)user));
        }
        return players;
    }

    public Optional<UserPlayer> findUserPlayer(Entity entity) {
        Optional<UserPlayer> give = Optional.empty();

        for(User user: objects){
            if(user instanceof UserPlayer && user.getUUID().equals(entity.getUniqueId())){
                give = Optional.of((UserPlayer)user);
                return give;
            }
        }

        return give;
    }

    private void combatTick(){
        //only need to check this every second
        if((System.currentTimeMillis() - lastRun)/1000 < 1)
            return;

        for(User user: objects){
            user.getCombatLogger().tickInCombat();
        }
    }

    private void areaTick(){
        for(User user: objects){
            if(user.isPlayer()){
                UserPlayer player = (UserPlayer)user;

                if(player.getLastBlockLocation().isPresent()){
                    if(player.getLastBlockLocation().get().getPosition().distance(player.getPlayer().get().getLocation().getPosition()) < 1) {
                        continue;
                    }
                } else {
                    player.setLastBlockLocation(player.getPlayer().get().getLocation());
                    continue;
                }

                //Get a connecting path of locations from where they were to where they are
                List<Location> traveled = LocationUtils.getConnectingLine(player.getLastBlockLocation().get(), player.getPlayer().get().getLocation());
                for(Location location: traveled){
                    Optional<Area> temp = Avatar.INSTANCE.getAreaManager().find(location);
                    if (temp.isPresent()) {
                        //They crossed the threshold in some direction
                        if(temp.get().inside(player)) {
                            user.enterArea(temp.get());
                        } else {
                            user.leaveArea();
                        }
                        break;
                    }
                }
                player.setLastBlockLocation(player.getPlayer().get().getLocation());
            }
        }
    }
}
