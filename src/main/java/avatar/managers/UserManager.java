package avatar.managers;

import avatar.Avatar;
import avatar.events.custom.AreaEvent;
import avatar.game.areas.Area;
import avatar.user.User;
import avatar.user.UserPlayer;
import avatar.utilities.misc.LocationUtils;
import org.spongepowered.api.Sponge;
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
        lastRun = System.currentTimeMillis();
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

                if(traveled.size() > 1){
                    Optional<Area> temp = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(traveled.get(0));
                    Optional<Area> temp2 = Avatar.INSTANCE.getAreaManager().getAreaByContainedLocation(traveled.get(traveled.size() - 1));
                    AreaEvent event;
                    if(temp.isPresent()){
                        //the player started inside the area
                        if(!temp2.isPresent()){
                            //the player ended outside the area
                            event = new AreaEvent(user, null, user.getPresentArea());
                            Sponge.getEventManager().post(event);

                            user.leaveArea();
                        }
                        //else they're still inside and nothing to do
                    } else {
                        //started outside the area
                        if(temp2.isPresent()){
                            //ended inside the area
                            event = new AreaEvent(user, temp2.get(), user.getPresentArea());
                            Sponge.getEventManager().post(event);

                            user.enterArea(temp2.get());
                        }
                        //else they're still outside the area
                    }
                }
                player.setLastBlockLocation(player.getPlayer().get().getLocation());
            }
        }
    }
}
