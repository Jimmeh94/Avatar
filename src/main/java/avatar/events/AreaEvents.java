package avatar.events;

import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;

public class AreaEvents {

    @Listener
    public void onMove(MoveEntityEvent event){
        /*if(event.getTargetEntity() instanceof Player){
            Optional<User> user = Avatar.INSTANCE.getUserManager().find(event.getTargetEntity().getUniqueId());

            if(!user.isPresent()){
                return;
            } else {
                UserPlayer player = (UserPlayer)user.get();

                if(player.getLastBlockLocation().isPresent()){
                    if(player.getLastBlockLocation().get().getPosition().distance(event.getFromTransform().getPosition()) < 1) {
                        return;
                    }
                } else {
                    player.setLastBlockLocation(event.getFromTransform().getLocation());
                    return;
                }

                Optional<Area> temp = Avatar.INSTANCE.getAreaManager().find(player.getLastBlockLocation().get());
                if (temp.isPresent()) {
                    //They crossed the threshold in some direction
                    if(temp.get().inside(player)) {
                        user.get().enterArea(temp.get());
                    } else {
                        user.get().leaveArea();
                    }
                }
                player.setLastBlockLocation(event.getFromTransform().getLocation());
            }
        }*/
    }

}
