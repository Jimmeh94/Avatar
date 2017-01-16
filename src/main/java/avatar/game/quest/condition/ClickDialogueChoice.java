package avatar.game.quest.condition;

import avatar.event.custom.DialogueEvent;
import avatar.manager.ListenerManager;
import org.spongepowered.api.event.EventListener;

public class ClickDialogueChoice extends Condition implements EventListener<DialogueEvent.ChoiceClicked>{

    private String choiceID;

    public ClickDialogueChoice(String choiceID) {
        this.choiceID = choiceID;
    }

    @Override
    public void reset() {
        super.reset();
        getPlayer().setLocation(getStartLocation());

        unregisterListener();
        setAdditionalStartInfo();
    }

    @Override
    public void setAdditionalStartInfo() {
        ListenerManager.register(DialogueEvent.ChoiceClicked.class, this);
    }

    @Override
    public void handle(DialogueEvent.ChoiceClicked choiceClicked) throws Exception {
        if(choiceClicked.getUserPlayer().getPlayer().get()
                == this.getPlayer() &&
                choiceClicked.getChoiceID().equals(this.choiceID)){
            valid = true;
        }
    }
}
