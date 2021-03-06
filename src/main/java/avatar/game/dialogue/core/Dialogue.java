package avatar.game.dialogue.core;

import avatar.game.dialogue.core.displayable.ChoiceWheel;
import avatar.game.dialogue.core.displayable.Displayable;
import avatar.util.text.Messager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private boolean used = false;

    public Dialogue(List<Displayable> displayables, String string, Player player){
        this.dialogue = displayables;
        dialogueID = String.valueOf(string);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public String getDialogueID() {
        return dialogueID;
    }

    public boolean hasChoiceID(String id){
        for(Displayable displayable: dialogue){
            if(displayable instanceof ChoiceWheel && ((ChoiceWheel) displayable).hasID(id)){
                return true;
            }
        }
        return false;
    }

    /**
     * Don't directly call this to display a player's dialogue. Use UserPlayer#startDialogue
     */
    public void displayNext(){
        Messager.sendMessage(player, Text.of(TextColors.GRAY, "=================================== "), Optional.empty());
        for(Displayable displayable: dialogue){
            displayable.display(player);
        }
        Messager.sendMessage(player, Text.of(TextColors.GRAY, "=================================== "), Optional.empty());
        Messager.sendMessage(player, Text.of(TextColors.GRAY, " "), Optional.<Messager.Prefix>empty());
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public boolean isUsed() {
        return used;
    }
}
