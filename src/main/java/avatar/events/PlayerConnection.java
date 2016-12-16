package avatar.events;

import avatar.Avatar;
import avatar.user.UserPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class PlayerConnection {

    @Listener
    public void onJoin(ClientConnectionEvent.Join event){
        event.setMessageCancelled(true);
        Avatar.INSTANCE.getUserManager().add(new UserPlayer(event.getTargetEntity().getUniqueId()));
    }

    @Listener
    public void onLeave(ClientConnectionEvent.Disconnect event){
        event.setMessageCancelled(true);
    }

}
