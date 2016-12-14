package avatar.events;

import avatar.game.areas.Area;
import avatar.managers.AreaManager;
import avatar.managers.Manager;
import avatar.managers.UserManager;
import avatar.user.User;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.MoveEntityEvent;

import java.util.Optional;

public class AreaEvents {

    @Listener
    public void onMove(MoveEntityEvent event){
        if(event.getTargetEntity() instanceof Player){
            if(event.getFromTransform().getPosition().distance(event.getToTransform().getPosition()) < 1)
                return;

            AreaManager am = (AreaManager) Manager.getManager(Manager.ManagerKey.AREA);

            Optional<Area> temp = am.find(event.getToTransform().getLocation());
            Optional<User> user = ((UserManager)Manager.getManager(Manager.ManagerKey.USER)).find(event.getTargetEntity().getUniqueId());

            if(user.isPresent()) {
                if (temp.isPresent()) {
                    //going into area
                    user.get().enterArea(temp.get());
                } else {
                    temp = am.find(event.getFromTransform().getLocation());
                    if (temp.isPresent()) {
                        //leaving area
                        user.get().leaveArea();
                    }
                }
            }
        }
    }

}
