package avatar.game.dialogue.core.displayable;

import avatar.util.text.Messager;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Sentence implements Displayable {
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
                Messager.sendMessage(player, text, Optional.<Messager.Prefix>empty());
        }
    }

    public boolean isAllDisplayed(){
        return sentences.size() == 0;
    }

    public List<Text> getSentences() {
        return sentences;
    }
}
