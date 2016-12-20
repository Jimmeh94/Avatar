package avatar.game.dialogue.core.builders;

import avatar.game.dialogue.core.*;
import avatar.game.dialogue.core.containers.ChoiceWheel;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DisplayableBuilder {

    /*
     * Here, if choice wheel is left false, this will build a sentence
     * Otherwise, it will build a choice wheel
     * Get access to this via DialogueBuilder to build sentences/choices
     */

    private List<Text> sentences = new ArrayList<>();
    private boolean choiceWheel = false;
    private List<Choice> choices = new ArrayList<>();
    private Condition condition;

    public DisplayableBuilder addSentence(Text text){
        sentences.add(text);
        return this;
    }

    public DisplayableBuilder addChoice(Text name, Text hover, DialogueAction... action){
        choices.add(new Choice(name, hover, Arrays.asList(action)));
        return this;
    }

    public DisplayableBuilder addCondition(Condition condition){
        this.condition = condition;
        return this;
    }

    public DisplayableBuilder setChoiceWheel(boolean value){
        choiceWheel = value;
        return this;
    }

    public Displayable load(){
        Displayable give;
        if(choiceWheel){
            give = new ChoiceWheel(new ArrayList<>(choices), condition);
        } else {
            give = new Sentence((new ArrayList<>(sentences)).toArray(new Text[]{}));
        }
        reset();
        return give;
    }

    private void reset(){
        sentences.clear();
        choices.clear();
        choiceWheel = false;
    }
}
