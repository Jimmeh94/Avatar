package avatar.game.dialogue.core.containers;

import avatar.game.dialogue.core.Choice;
import avatar.game.dialogue.core.Condition;
import avatar.game.dialogue.core.Displayable;
import org.spongepowered.api.entity.living.player.Player;

import java.util.ArrayList;
import java.util.List;

public class ChoiceWheel implements Displayable {

    /*
     * Container for holding choices
     */

    private List<Choice> choices = new ArrayList<>();
    private List<Condition> conditions;

    public ChoiceWheel(List<Choice> choices, List<Condition> condition){
        this.choices = choices;
        this.conditions = condition;
    }

    public ChoiceWheel(ChoiceWheel wheel, Player player){
        for(Choice choice: wheel.getChoices()){
            choices.add(new Choice(choice, player, wheel.getCondition()));
        }
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
