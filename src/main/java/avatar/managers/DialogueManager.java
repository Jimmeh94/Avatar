package avatar.managers;

import avatar.Avatar;
import avatar.game.dialogue.core.containers.Dialogue;
import avatar.game.dialogue.core.test.TestDialogue;
import avatar.game.user.UserPlayer;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;

public class DialogueManager extends Manager<Dialogue>{

    public void loadDialogue() {
        new TestDialogue();
    }

    public void giveDialogue(Player player, String dialogueID){
        for(Dialogue dialogue: objects){
            if(dialogue.getDialogueID().equalsIgnoreCase(dialogueID)){
                Optional<UserPlayer> temp = Avatar.INSTANCE.getUserManager().findUserPlayer(player);
                if(temp.isPresent()){
                    temp.get().setCurrentDialogue(new Dialogue(player, dialogue));
                }
            }
        }
    }

    public void removeDialogue(Player player) {
        Optional<UserPlayer> temp = Avatar.INSTANCE.getUserManager().findUserPlayer(player);
        if(temp.isPresent()){
            temp.get().resetDialogue();
        }
    }
}
