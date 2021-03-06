package avatar.game.dialogue.core.displayable;

import avatar.game.dialogue.core.conditions.Condition;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChoiceWheel implements Displayable {

    /*
     * Container for holding choices
     */

    private List<Choice> choices = new ArrayList<>();
    private List<Condition> conditions;

    public ChoiceWheel(Choice... choices){
        this.choices = Arrays.asList(choices);
    }

    public ChoiceWheel addConditions(List<Condition> condition){
        this.conditions = condition;
        for(Choice choice: choices){
            choice.setConditions(condition);
        }
        return this;
    }

    public List<Condition> getCondition() {
        return conditions;
    }

    private List<Choice> getChoices(){return choices;}

    @Override
    public void display(Player player) {
        for(Choice choice: choices){
            choice.display(player);
        }
    }

    public boolean hasID(String id) {
        for(Choice choice: choices){
            if(choice.getId() == id)
                return true;
        }
        return false;
    }
}
