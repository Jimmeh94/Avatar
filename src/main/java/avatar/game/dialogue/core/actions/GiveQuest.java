package avatar.game.dialogue.core.actions;

import avatar.Avatar;
import avatar.game.dialogue.core.DialogueAction;
import org.spongepowered.api.entity.living.player.Player;

public class GiveQuest extends DialogueAction {

    public String questID;

    public GiveQuest(String id){
        this.questID = id;
    }

    @Override
    public void doWork(Player player) {
        Avatar.INSTANCE.getQuestManager().giveQuest(Avatar.INSTANCE.getUserManager().findUserPlayer(player).get(), questID);
    }
}
