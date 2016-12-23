package avatar.game.dialogue.core.actions;

import avatar.Avatar;
import avatar.game.dialogue.core.DialogueAction;
import avatar.user.UserPlayer;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;

public class DisplayDialogue extends DialogueAction {

    /**
     * Starts another dialogue sequence
     * The ID is of the next desired dialogue
     */

    private String id;

    public DisplayDialogue(String id){
        this.id = id;
    }

    @Override
    public void doWork(Player player) {
        Optional<UserPlayer> temp = Avatar.INSTANCE.getUserManager().findUserPlayer(player);
        if(temp.isPresent()){
            Avatar.INSTANCE.getDialogueManager().giveDialogue(player, id);
            temp.get().startDialogue();
        }
    }
}
