package avatar.game.dialogue.core.containers;

import avatar.Avatar;
import avatar.game.dialogue.core.Displayable;
import avatar.game.dialogue.core.Sentence;
import avatar.utilities.text.Messager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.List;

public class Dialogue {

    /*
     * Container for whole conversation, both sentences and actions
     * Each dialogue should only contain up to one choice
     * If an option in that Choice continues the conversation, that next part of the conversation
     * should be another Dialogue, using the DisplayDialogue DialogueAction as part of that particular Choice
     */

    private List<Displayable> dialogue = new ArrayList<>();
    private Player player;
    private String dialogueID;

    public Dialogue(Player player, Dialogue dialogue){
        this.player = player;
        this.dialogue = dialogue.copyDialogueSequence(player);
    }

    public Dialogue(List<Displayable> displayables, String string){
        this.dialogue = displayables;
        dialogueID = String.valueOf(string);
    }

    public Player getPlayer() {
        return player;
    }

    public String getDialogueID() {
        return dialogueID;
    }

    private List<Displayable> copyDialogueSequence(Player player){
       List<Displayable> give = new ArrayList<>();
       for(Displayable displayable: dialogue){
           if(displayable instanceof Sentence){
                give.add(new Sentence((Sentence) displayable));
           } else {
               give.add(new ChoiceWheel((ChoiceWheel) displayable, player));
           }
       }
        return give;
    }

    public boolean hasChoiceID(String id){
        for(Displayable displayable: dialogue){
            if(displayable instanceof ChoiceWheel && ((ChoiceWheel) displayable).hasID(id)){
                return true;
            }
        }
        return false;
    }

    public void displayNext(){
        if(dialogue.size() > 0){
            boolean shouldContinue = true;
            Messager.sendMessage(player, Text.of(TextColors.GRAY, "=================================== "));
            while(shouldContinue) {
                Messager.sendMessage(player, Text.of(" "));
                dialogue.get(0).display(this.player);
                Messager.sendMessage(player, Text.of(" "));
                if(dialogue.get(0) instanceof ChoiceWheel) {
                    shouldContinue = false;
                    Messager.sendMessage(player, Text.of(" "));
                    Messager.sendMessage(player, Text.of(TextColors.GRAY, "=================================== "));
                } else {
                    dialogue.remove(0);
                    if (dialogue.size() == 0) {
                        Avatar.INSTANCE.getDialogueManager().removeDialogue(player);
                        shouldContinue = false;
                    }
                }
            }
        }
    }

}
