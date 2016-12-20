package avatar.events;

import avatar.Avatar;
import avatar.user.UserPlayer;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class PlayerConnection {

    @Listener
    public void onJoin(ClientConnectionEvent.Join event){
        event.setMessageCancelled(true);

        Avatar a = Avatar.INSTANCE;

        a.getUserManager().add(new UserPlayer(event.getTargetEntity().getUniqueId()));

        a.getQuestManager().giveQuest(a.getUserManager().findUserPlayer(event.getTargetEntity()).get(), "test");
        a.getQuestManager().setActiveQuest(a.getUserManager().findUserPlayer(event.getTargetEntity()).get(), "test");
        a.getUserManager().findUserPlayer(event.getTargetEntity()).get().generateQuestMenu();

        a.getDialogueManager().giveDialogue(event.getTargetEntity(), "test");
        a.getUserManager().findUserPlayer(event.getTargetEntity()).get().startDialogue();
    }

    @Listener
    public void onLeave(ClientConnectionEvent.Disconnect event){
        event.setMessageCancelled(true);
    }

}
