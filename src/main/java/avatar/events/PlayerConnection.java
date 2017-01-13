package avatar.events;

import avatar.Avatar;
import avatar.user.UserPlayer;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

public class PlayerConnection {

    @Listener
    public void onJoin(ClientConnectionEvent.Join event){
        event.setMessageCancelled(true);
        event.getTargetEntity().setLocation(new Location(Sponge.getServer().getWorlds().toArray(new World[]{})[0], 50, 50, 50));

        new UserPlayer(event.getTargetEntity().getUniqueId());
        Avatar.INSTANCE.getUserManager().findUserPlayer(event.getTargetEntity()).get().init();

        /*a.getQuestManager().giveQuest(a.getUserManager().findUserPlayer(event.getTargetEntity()).get(), "test");
        a.getQuestManager().setActiveQuest(a.getUserManager().findUserPlayer(event.getTargetEntity()).get(), "test");
        a.getUserManager().findUserPlayer(event.getTargetEntity()).get().generateQuestMenu();*/

        /*a.getDialogueManager().giveDialogue(event.getTargetEntity(), "test");
        a.getUserManager().findUserPlayer(event.getTargetEntity()).get().startDialogue();*/
    }

    @Listener
    public void onLeave(ClientConnectionEvent.Disconnect event){
        event.setMessageCancelled(true);
        Avatar.INSTANCE.getUserManager().findUserPlayer(event.getTargetEntity()).get().cleanUp();
    }

}
