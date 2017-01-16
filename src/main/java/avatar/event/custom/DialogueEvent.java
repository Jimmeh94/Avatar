package avatar.event.custom;

import avatar.game.dialogue.core.Dialogue;
import avatar.game.user.UserPlayer;
import org.spongepowered.api.event.cause.Cause;

public abstract class DialogueEvent extends CustomEvent {

    private Dialogue dialogue;
    private UserPlayer userPlayer;

    public DialogueEvent(Cause cause, UserPlayer userPlayer) {
        super(cause);

        this.dialogue = userPlayer.getCurrentDialogue();
        this.userPlayer = userPlayer;
    }

    public UserPlayer getUserPlayer() {
        return userPlayer;
    }

    public static class Displayed extends DialogueEvent{

        public Displayed(Cause cause, UserPlayer userPlayer) {
            super(cause, userPlayer);
        }
    }

    public static class ChoiceClicked extends DialogueEvent{

        private String choiceID;

        public ChoiceClicked(Cause cause, String choiceID, UserPlayer userPlayer) {
            super(cause, userPlayer);

            this.choiceID = choiceID;
        }

        public String getChoiceID() {
            return choiceID;
        }
    }
}
