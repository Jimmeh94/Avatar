package avatar.managers;

import avatar.Avatar;
import avatar.game.quests.quests.Quest;
import avatar.game.quests.quests.test.TestQuestLocation;
import avatar.user.UserPlayer;
import avatar.utilities.text.Messager;

import java.util.Optional;

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
        if(quest != null){
            playerInfo.getQuests().add(new Quest(quest, playerInfo));
            Messager.sendTitleAndSubTitle(playerInfo.getPlayer().get(), quest.getTitle(), quest.getDescription());
        }
    }

    public void setActiveQuest(UserPlayer playerInfo, String id){
        Quest quest = null;
        for(Quest q: playerInfo.getQuests()){
            if(q.getID().equals(id)) {
                quest = q;
            }
        }

        if(quest != null && !quest.isActive()){
            Optional<Quest> temp = getActiveQuest(playerInfo);
            if(temp.isPresent()){
                temp.get().toggleActive();
            }
            quest.toggleActive();
        }
    }

    public void setActiveQuest(UserPlayer playerInfo, Quest quest){
        if(!playerInfo.getQuests().contains(quest))
            playerInfo.getQuests().add(quest);
        if(quest != null && !quest.isActive()){
            quest.toggleActive();
        }
    }

    public Optional<Quest> getActiveQuest(UserPlayer playerInfo){
        Optional<Quest> quest = Optional.empty();
        for(Quest q: playerInfo.getQuests()){
            if(q.isActive())
                quest = Optional.of(q);
        }
        return quest;
    }

    public void tick(){
        Optional<Quest> quest;
        for(UserPlayer playerInfo: Avatar.INSTANCE.getUserManager().getPlayers()){
            quest = getActiveQuest(playerInfo);
            if(quest.isPresent()){
                if(quest.get().tick())
                    removeQuest(playerInfo, quest.get());
            }
        }
    }

    public void removeQuest(UserPlayer playerInfo, Quest quest){
        playerInfo.getQuests().remove(quest);
    }

    public void removeQuest(UserPlayer playerInfo, String id){
        Quest remove = null;
        for(Quest quest: playerInfo.getQuests()){
            if(quest.getID() == id)
                remove = quest;
        }
        if(remove != null){
            playerInfo.getQuests().remove(remove);
        }
    }
}
