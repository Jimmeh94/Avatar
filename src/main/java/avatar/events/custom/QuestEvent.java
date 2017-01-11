package avatar.events.custom;

import avatar.game.quests.quests.Checkpoint;
import avatar.game.quests.quests.Quest;
import avatar.user.UserPlayer;
import org.spongepowered.api.event.cause.Cause;

public class QuestEvent extends CustomEvent {

    private UserPlayer player;
    private Quest quest;

    public QuestEvent(Cause cause, UserPlayer player, Quest quest) {
        super(cause);

        this.player = player;
        this.quest = quest;
    }

    public Quest getQuest() {
        return quest;
    }

    public UserPlayer getPlayer() {
        return player;
    }

    public static class Start extends QuestEvent{

        public Start(Cause cause, UserPlayer player, Quest quest) {
            super(cause, player, quest);
        }
    }

    public static class CheckpointComplete extends QuestEvent{

        private Checkpoint checkpoint;

        public CheckpointComplete(Cause cause, UserPlayer player, Quest quest, Checkpoint checkpoint) {
            super(cause, player, quest);

            this.checkpoint = checkpoint;
        }

        public Checkpoint getCheckpoint() {
            return checkpoint;
        }
    }

    public static class Complete extends QuestEvent{

        public Complete(Cause cause, UserPlayer player, Quest quest) {
            super(cause, player, quest);
        }
    }

}
