package avatar.managers;

import avatar.Avatar;
import avatar.game.dialogue.core.containers.Dialogue;
import avatar.game.dialogue.core.test.TestDialogue;
import avatar.user.UserPlayer;
import org.spongepowered.api.entity.living.player.Player;

import java.util.Optional;
import java.util.Random;

public class DialogueManager extends Manager<Dialogue>{

    public void loadDialogue() {
        new TestDialogue();
    }

    public int getChoiceID(){
        Random random = new Random();
        int id = random.nextInt();
        while(isChoiceIDTaken(id)){
            id = random.nextInt();
        }
        return id;
    }

    private boolean isChoiceIDTaken(int id){
        for(UserPlayer playerInfo: Avatar.INSTANCE.getUserManager().getPlayers()){
            if(playerInfo.getCurrentDialogue() != null && playerInfo.getCurrentDialogue().hasChoiceID(id))
                return true;
        }
        return false;
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
