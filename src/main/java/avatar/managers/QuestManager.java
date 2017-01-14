package avatar.managers;

import avatar.Avatar;
import avatar.game.quests.quests.Quest;
import avatar.game.quests.quests.test.TestQuestLocation;
import avatar.user.UserPlayer;

public class QuestManager extends Manager<Quest> {

    public void loadQuests(){
        objects.add(TestQuestLocation.createLocationTest());
    }

    public void giveQuest(UserPlayer playerInfo, String id){
        Quest quest = null;
        for(Quest q: objects){
            if(q.getID().equals(id)) {
                quest = q;
            }
        }
        if(quest != null && !playerInfo.getQuestManager().has(quest.getID()) && !playerInfo.getQuestManager().hasCompleted(quest.getID())){
            playerInfo.getQuestManager().add(new Quest(quest, playerInfo));
        }
    }

    public void setActiveQuest(UserPlayer playerInfo, String id){
        playerInfo.getQuestManager().setActiveQuest(id);
    }

    public void tick(){
        for(UserPlayer playerInfo: Avatar.INSTANCE.getUserManager().getPlayers()){
            playerInfo.tick();
        }
    }
}
