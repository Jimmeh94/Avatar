package avatar.game.dialogue.core;

import avatar.utilities.text.Messager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Sentence implements Displayable{
    /*
     * This is text presented by the server/NPC to a player
     */

    private List<Text> sentences;

    public Sentence(Text... texts){
        sentences = Arrays.asList(texts);
    }

    public Sentence(Sentence sentence){
        this.sentences = new ArrayList<>(sentence.getSentences());
    }

    @Override
    public void display(Player player) {
        for(Text text: sentences) {
                Messager.sendMessage(player, text);
        }
    }

    public boolean isAllDisplayed(){
        return sentences.size() == 0;
    }

    public List<Text> getSentences() {
        return sentences;
    }
}
